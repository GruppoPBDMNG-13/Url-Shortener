import static spark.Spark.before;
import static spark.Spark.options;
import static spark.Spark.post;

import services.BaseHandler;
import services.NewUrlHandler;



public class Main {
	
	private static BaseHandler b;
	
	public static void main(String[] args) {
    	
		setup();
		
		post("/newUrl", "application/json", (request, response)
				-> {
					b = new NewUrlHandler();
					return b.process(request);
                });	
    		
    }
    
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
    

