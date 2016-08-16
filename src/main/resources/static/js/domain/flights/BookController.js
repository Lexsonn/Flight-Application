angular.module('app').controller('BookController', 
								['UpdateService', 'FlightService', 'allFlights', '$location', '$scope', 
								 function(UpdateService, FlightService,  allFlights, $location, $scope) {
	$scope.flights = allFlights.data;
	$scope.possibleLocations = ['none', 'Orlando', 'Miami', 'Jacksonville', 'Tallahassee'];
	$scope.departure = 'none';
	$scope.destination = 'none';
	$scope.time = 0;
	$scope.itineraryInfo = 'No flight currently exists for the selected origin and destination.';
	
	var ctrl = this;
	
	this.checkFlight = function() {
		$scope.itineraryInfo = 'No flight currently exists for the selected origin and destination.'
		var departureLocation = $scope.departure;
		var destinationLocation = $scope.destination;
		if (departureLocation != 'none' && destinationLocation != 'none') {
			FlightService.getFlightDetails({source: departureLocation, dest: destinationLocation}, false).then(function(result) {
				console.dir(result.data);
				if (result.data != null || result.data != undefined || result.data != "") {
					$scope.time = -result.data[0].offset;
					$scope.time += result.data[result.data.length - 1].offset;
					$scope.time += result.data[result.data.length - 1].flightTime;
					$scope.itineraryInfo = 'Book flight from '+$scope.departure+' to '+$scope.destination+': '+$scope.time+' hour(s) total'
				}
			});
		}
	}
	
	UpdateService.check = true;
	UpdateService.interval = setInterval(function() {
		UpdateService.checkUpdate().then(function(result) {
			if (result) {
				FlightService.getAllFlights().then(function(result){
					allFlights = result;
					$scope.flights = allFlights.data;
					console.dir($scope.flights)
				});
				ctrl.checkFlight();
			}
		});
	}, 1000);
	
	this.selectOrigin = function(origin) {
		if (origin === 'none' || origin != $scope.destination) {
			$scope.departure = origin;
		}
		this.checkFlight();
	}
	
	this.selectDestination= function(destination) {
		if (destination === 'none' || destination != $scope.departure) {
			$scope.destination = destination;
		}
		this.checkFlight();
	}
	
}]);
								