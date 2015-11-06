import static spark.Spark.before;
import static spark.Spark.post;
import static spark.Spark.get;
import static spark.Spark.options;

import services.GetLongUrlHandler;
import services.GetStatisticHandler;
import services.NewUrlHandler;

/**
 * Servlet starter
 * @author Mattia Menna
 * @author Giuseppe Onesto
 */

public class Main {
	
	
	private final static String NOT_EXISTS_SCRIPT = "<script>alert('The requested tiny url not exists!');</script>";
	private final static String ERROR_SCRIPT = "<script>alert('An internal error has occurred!');</script>";		
	
    
	
	public static void main(String[] args) {
    	
		setup();
		
		post("/newUrl", "application/json", (request, response)
				-> {
					NewUrlHandler b = new NewUrlHandler();
					return b.process(request);
                });
		
		get("/:tiny","application/json",(request,response)
	   			 -> {
	   				GetLongUrlHandler b = new GetLongUrlHandler();
	   				 String result = b.process(request);
	   				 
	   				 switch(result){
	   				 case "ERROR":
	   					 		return ERROR_SCRIPT;	 		
	   				 case "NOT_EXISTS":
	   					 		return NOT_EXISTS_SCRIPT;
	   				 default:
	   					 		response.redirect(result);
	   					 		return null;
	   				 }
	   				 
	   			 });
		
		get("/getStatistics/:tiny","application/json",(request,response)
	   			 -> {
	   				 GetStatisticHandler b = new GetStatisticHandler();
	   				 return b.process(request);
	   			 });

    		
    }
    /**
     * Method that allows origins
     */
    static private void setup(){
    	
    	before((request, response) -> {

			response.header("Access-Control-Allow-Origin", "*");
		});

		options("/newUrl", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});
		
		
    }
}
    

