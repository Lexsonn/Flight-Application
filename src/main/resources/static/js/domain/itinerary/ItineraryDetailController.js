angular.module('app').controller('ItineraryDetailController', 
								['UpdateService', 'MapService', 'userItinerary', '$location', '$scope', 
								 function(UpdateService, MapService, userItinerary, $location, $scope) {
	$('#multiflight').hide();
	$('#layoverdiv').hide();
	$('#bookdiv').hide();
									
	this.geodesicPoly = [];
	this.colorsList =  ['#6622EE', '#EE2222', '#EE6622', '#FFAA22', '#EEFF22', '#AAFF22'];
	var ctrl = this;
	var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 7,
        center: {lat: 27.6648, lng: -82.5158}
    });
	
	$scope.flights = userItinerary.data.flights;
	$scope.flight = userItinerary.data.flights[0];
	$scope.flight.departure = $scope.flight.origin;
	$scope.flightTime = 0;
	$scope.layoverTime = 0;
	$scope.currentFlight = 'first';
	
	$scope.possibleFlights = [];
	switch ($scope.flights.length) {
	case 4: $scope.possibleFlights.push('fourth');
	case 3: $scope.possibleFlights.push('third');
	case 2: $scope.possibleFlights.push('second');
		var totalTime = 0;
		for (var i = 0; i < $scope.flights.length; i++) {
			$scope.flights[i].layover = $scope.flights[i].offset - totalTime;
			if (i === 0)
				$scope.flights[i].layover = 0;
			totalTime = $scope.flights[i].offset + $scope.flights[i].flightTime;
			$scope.flights[i].departure = $scope.flights[i].origin
			$scope.flightTime += $scope.flights[i].flightTime;
			$scope.layoverTime += $scope.flights[i].layover;
		}
		$('#layoverdiv').show();
		$('#multiflight').show();
	default: $scope.possibleFlights.push('first');
	}
	$scope.possibleFlights.reverse();
	
	this.addPoly = function(pointA, pointB, color) {
    	this.geodesicPoly.push(new google.maps.Polyline({
            strokeColor: color,
            strokeOpacity: 1.0,
            strokeWeight: 3,
            geodesic: true,
            map: map,
            icons: [{
                icon: {path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW},
                offset: '100%'
            }]
          }));
          this.geodesicPoly[this.geodesicPoly.length - 1].setPath([pointA.getPosition(), pointB.getPosition()]);
    }
	
	var origin = null;
	MapService.getMarkersByCities(map, $scope.flights).then(function(result) {
		origin = result[0];
		//addPoly(origin, result[1], '#FF3388');
		var col = ctrl.colorsList[0]
		for (var i = 1; i < result.length; i++) {
			ctrl.addPoly(origin, result[i], col);
			origin = result[i];
			col = ctrl.colorsList[i+1]
		}
	});
	
	$scope.selectFlight = function(flightNum) {
		$scope.currentFlight = flightNum;
		
		for (var i = 0; i < ctrl.geodesicPoly.length; i++) {
			ctrl.geodesicPoly[i].setOptions({strokeColor: ctrl.colorsList[i + 1]})
		}
		switch (flightNum) {
		case 'first': $scope.flight = $scope.flights[0]; ctrl.geodesicPoly[0].setOptions({strokeColor: ctrl.colorsList[0]}); break;
		case 'second': $scope.flight = $scope.flights[1]; ctrl.geodesicPoly[1].setOptions({strokeColor: ctrl.colorsList[0]}); break;
		case 'third': $scope.flight = $scope.flights[2]; ctrl.geodesicPoly[2].setOptions({strokeColor: ctrl.colorsList[0]}); break;
		case 'fourth': $scope.flight = $scope.flights[3]; ctrl.geodesicPoly[3].setOptions({strokeColor: ctrl.colorsList[0]}); break;
		}
	}
	
	$scope.returnHome = function() {
		$location.path('/home')
	}
	
}]);