# Url-Shortener
A tiny url creator that offers the possibility to get statistics about a specified short-url.

##How to install Url-Shortener:

1. Clone the repository
'git clone https://github.com/GruppoPBDMNG-13/Url-Shortener.git'

2. Build the image 
docker build -t gruppopbdmng-13/url-shortener .

3. Run it!
docker run -p 8080:80 -p 4567:4567 -d gruppopbdmng-13/url-shortener /Script.sh

##How to use Url-Shortener
Once correctly installed and running, you can use it going to the Url-Shortener home at "http://localhost:8080"
You can go to your own long url at "http://localhost:4567/custom", where custom is your chosen short url.

##Server API
1. Request to insert a new custom url: 
	**[POST]** "http://localhost:4567/newUrl"
	

2. Request to get a long url associated to an existing short url:
	**[GET]** "http://localhost:4567/"
	Parameters:
	tiny: the short url which associated long url you want to be redirected to	

3. Request to get statistics for a specified short url:
	**[GET]** "http://localhost:4567/getStatistics/"
	Parameters:
	tiny: the short url which statistics you want to get
	

##Contributors

@honestus https://github.com/honestus
@Mattia26 https://github.com/Mattia26


