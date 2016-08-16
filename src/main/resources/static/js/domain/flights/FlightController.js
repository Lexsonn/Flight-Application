angular.module('app').controller('FlightController', 
								['FlightService', 'allFlights', 'UpdateService', '$location', '$scope', 
								 function(FlightService, allFlights, UpdateService, $location, $scope) {
	$scope.flights = allFlights.data;
	
	UpdateService.check = true;
	UpdateService.interval = setInterval(function() {
		UpdateService.checkUpdate().then(function(result) {
			if (result) {
				FlightService.getAllFlights().then(function(result){
					allFlights = result;
					$scope.flights = allFlights.data;
					console.dir($scope.flights)
				});
			}
		});
	}, 1000);
	
	this.goToMap = function() {
		$location.path('/map');
	}
}]);