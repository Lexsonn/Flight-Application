angular.module('app').service('UserService', ['$http', function($http) {
	
	var url = 'users/';
	this.user = null;
	this.last = '/home';
		
	this.createUser = function(user) { return $http.post(url, user) }
	this.login = function(user) { return $http.post(url + 'login', user) }
	
	this.getItinerary = function(id) { return $http.get('itineraries/' + id) }
	this.getItineraries = function(name) { return $http.get('itineraries/name/' + name) }
	this.createItinerary = function(flights) { 
		console.dir({ user: this.user, flights: flights });
		return $http.post('itineraries', { user: this.user, flights: flights }); 
		
	}
	
}]);