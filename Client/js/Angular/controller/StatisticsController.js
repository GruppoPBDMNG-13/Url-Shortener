angular.module('url-shortener').controller('StatisticsController',['$http','$scope',function($http,$scope){
		
	$scope.customUrlRegex = /^([0-9a-zA-Z-]{0,30})$/;
			
			var callback = function(response){
			switch(response.code){
				case "OK":
					$scope.statisticData.statistics = response.stat;
					$scope.table = true;
					break;
				case "NOT_EXISTS":
					$scope.statisticData.statistics = "";
					$scope.table = false;
					alert('The requested tiny url does not exists');
					break;
				case "ERROR":
					$scope.statisticData.statistics = "";
					$scope.table = false;
					alert('An internal error has occured!');
					break;
				default:
					$scope.statisticData.statistics = "";
					$scope.table = false;
					alert('Unknown error!');
					break;
			}
	
		}

		 $scope.filterOptions = {
   				 browser: [
     					    {value: "Browser"},
					    {value: "Firefox" },
      					    {value: "Chrome" },
     					    {value: "Opera" }
    					],
				 click: [
					    {value: 30, name:"Number of clicks"},
					    {value: 5, name: "Last five"},
					    {value: 10, name: "Last ten"},
				            {value: 20, name: "Last twenty"}
					],
				date: [
					    {value: 0, name: "Date"},
					    {value: 1, name: "Last 2 days"},
					    {value: 7, name: "Last 7 days"},
					    {value: 30, name: "Last 30 days"}
				]
					    
  		};
		
		$scope.filterElements = {
			browsers : $scope.filterOptions.browser[0],
			clicks : $scope.filterOptions.click[0],
			dates: $scope.filterOptions.date[0]
		};
		
	
		$scope.browserFilter = function (data) {
			
			    if (data.browser === $scope.filterElements.browsers.value) {
      					return true;
    				} else if ($scope.filterElements.browsers.value === "Browser") {
      					return true;
    				} else {
     					 return false;
    				}
  		}; 

		$scope.dateFilter = function (data) {
			if($scope.filterElements.dates.value === 0)
				return true;

			var date=new Date();
			date.setDate(date.getDate() - $scope.filterElements.dates.value);
			var dateClick=new Date(data.dateTime);
			if(dateClick >= date) {
				return true; 
			} else 
				return false;
		};			
		
		$scope.getStatistics = function(){
			var url = 'http://localhost:4567/getStatistics/' + $scope.statisticData.tiny;
			
			$http.get(url).success(callback).error(callback);
		}
}]);
