angular.module('url-shortener').controller('ShortenerController',['$scope','$http', function($scope,$http){


	$scope.longUrlRegex = /^(http[s]?:\/\/(www\.)?|ftp:\/\/(www\.)?|(www\.)?){1}([0-9A-Za-z-\.@:%_\‌​+~#=]+)+((\.[a-zA-Z]{2,3})+)(\/(.)*)?(\?(.)*)?$/;
	$scope.customUrlRegex = /^([0-9a-zA-Z-]{0,30})$/;

	var checkUrl = function(url){
    	newUrl = url;
    	if(url.substr(0, 8) != 'https://' && url.substr(0, 7) != 'http://') {
    		newUrl = 'http://' + url;
    	}
    	return newUrl;
    }

	var callback = function(response){
		
		switch(response.code){
			case "OK":
				$scope.urlData.custom = response.custom;
				$scope.urlData.thumb = 'https://api.thumbalizr.com/?url='+ $scope.urlData.longUrl +'&width=250';
				$scope.urlData.shortUrl = 'http://localhost:4567/' + $scope.urlData.custom;
				$scope.urlData.custom = null;
				$scope.flag = true; 	
				break;
			case "ALREADY_EXISTS": 
				$scope.urlData.custom = null;
				$scope.urlData.thumb = null;
				$scope.urlData.shortUrl = null;
				$scope.flag = false;
				alert('The requested customizzation already exists! Choose another!');
				break;
			case "BAD_INPUT":
				$scope.urlData.custom = null;
				$scope.urlData.thumb = null;
				$scope.urlData.shortUrl = null;
				$scope.flag = false;
				alert('Ehi! Watch what you say!');
				break;
			default:
				$scope.urlData.custom = null;
				$scope.urlData.thumb = null;
				$scope.urlData.shortUrl = null;
				$scope.flag = false;
				alert('Sorry! Something went wrong. Please try again!');
				break;
		}
			
		
	}


	$scope.newUrl = function(){	 	
		var url = 'http://localhost:4567/newUrl';
		$scope.urlData.longUrl = checkUrl($scope.urlData.longUrl);
                $http.post(url, $scope.urlData).success(callback).error(callback);
	}
}]);
