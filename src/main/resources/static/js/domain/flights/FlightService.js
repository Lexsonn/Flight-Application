angular.module('app').service('FlightService', ['$http', function($http) {
	
	var url = 'flights/';
	
	this.getAllFlights = function() { return $http.get(url + 'all') }
	this.getFlightDetails = function(params, displayMessage) { 
		return $http.get(url, {params: { to : params.dest, from: params.source }}).then(function(result) {
			var resultChk = result.data
			if ((resultChk === null || resultChk === undefined || resultChk === "") && displayMessage) {
				swal("Flight Unavailable", "We're sorry, there seems to have been a problem. The flight is no longer available.", "error");
			}
			return result;
		});
	}
	this.getFlightById = function(id) { return $http.get(url + 'id') }
	
}]);