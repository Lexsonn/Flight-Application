angular.module('app').controller('ItineraryController', 
								['UpdateService', 'UserService', 'allItineraries', '$route', '$scope', 
								 function(UpdateService, UserService, allItineraries, $route, $scope) {
	$scope.itineraries = allItineraries.data.itinerary;
	$scope.username = allItineraries.data.username;
	$scope.id = allItineraries.data.id;
	
	for (var i = 0; i < $scope.itineraries.length; i++) {
		$scope.itineraries[i].origin = $scope.itineraries[i].flights[0].origin;
		$scope.itineraries[i].destination = $scope.itineraries[i].flights[$scope.itineraries[i].flights.length - 1].destination;
		$scope.itineraries[i].totalTime = -$scope.itineraries[i].flights[0].offset;
		$scope.itineraries[i].totalTime += $scope.itineraries[i].flights[$scope.itineraries[i].flights.length - 1].offset;
		$scope.itineraries[i].totalTime += $scope.itineraries[i].flights[$scope.itineraries[i].flights.length - 1].flightTime;
	}
	
}]);
								