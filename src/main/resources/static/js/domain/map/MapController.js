angular.module('app').controller('MapController', 
								['MapService', 'FlightService', 'allFlights', 'UpdateService', '$scope',
								 function(MapService, FlightService, allFlights, UpdateService, $scope) {	
	var ctrl = this;
	
	this.geodesicPoly = [];
	$scope.colorWheel = [
	  '#EE2222', 
	  '#EE6622', 
	  '#FFAA22', 
	  '#EEFF22', 
	  '#AAFF22', 
	  '#66EE44', 
	  '#33DD88', 
	  '#22CCDD', 
	  '#22AAFF',
	  '#2266FF',
	  '#2222EE',
	  '#4422FF',
	  '#6622EE',
	  '#9922CC',
	  '#AA2299',
	  '#CC2266',
	  '#DD2244'
	];
	$scope.currentColor = 0;
	
	//Map object
	var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 7,
        center: {lat: 27.6648, lng: -81.5158}
    });
	
    $scope.addPoly = function(pointA, pointB, color) {
		ctrl.geodesicPoly.push(new google.maps.Polyline({
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
        ctrl.geodesicPoly[ctrl.geodesicPoly.length - 1].setPath([pointA.getPosition(), pointB.getPosition()]);
    }
    
    $scope.flights = allFlights.data
    
	for(var i = 0; i < $scope.flights.length; i++) {
		$scope.flights[i].departure = $scope.flights[i].origin;
		var locationNames = [$scope.flights[i]];
		MapService.getMarkersByCities(map, locationNames).then(function(result) {
			$scope.addPoly(result[0], result[1], $scope.colorWheel[($scope.currentColor++) % $scope.colorWheel.length]);
		});
	}
	
	$scope.removeAllPoly = function(num) {
		ctrl.geodesicPoly.map(function(poly) {
			poly.setMap(null);
		});
		ctrl.geodesicPoly = [];
	}
	
	UpdateService.check = true;
	UpdateService.interval = setInterval(function() {
		UpdateService.checkUpdate().then(function(result) {
			if (result) {
				FlightService.getAllFlights().then(function(result){
					$scope.removeAllPoly();
					allFlights = result;
					$scope.flights = allFlights.data;
					for(var i = 0; i < $scope.flights.length; i++) {
						$scope.flights[i].departure = $scope.flights[i].origin;
						var locationNames = [$scope.flights[i]];
						MapService.getMarkersByCities(map, locationNames).then(function(result) {
							$scope.addPoly(result[0], result[1], $scope.colorWheel[($scope.currentColor++) % $scope.colorWheel.length]);
						});
					}
					console.dir($scope.flights)
				});
			}
		});
	}, 1000);
	
}]);