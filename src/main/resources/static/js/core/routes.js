angular.
  module('app').
  config(['$routeProvider', 'baseRoute',
    function config($routeProvider, baseRoute) {

      $routeProvider.when('/home', {
          templateUrl: baseRoute + "home/homeTemplate.html",
          controller: 'HomeController', 
          controllerAs: 'homeController'
      }).when('/register', {
          templateUrl: baseRoute + "register/registerTemplate.html",
          controller: 'RegisterController',
          controllerAs: 'registerController'
      }).when('/login', {
    	  templateUrl: baseRoute + "user/loginTemplate.html",
          controller: 'RegisterController',
          controllerAs: 'registerController'
      }).when('/itineraries/:username', {
    	  templateUrl: baseRoute + "itinerary/itineraryListTemplate.html",
          controller: 'ItineraryController',
          controllerAs: 'itineraryController',
          resolve: {
          	  allItineraries: function(UserService, $route){
          		  return UserService.getItineraries($route.current.params.username);
          	  }
            }
      }).when('/itinerary/view/:id', {
    	  templateUrl: baseRoute + "flights/flightDetailTemplate.html",
          controller: 'ItineraryDetailController',
          controllerAs: 'itineraryDetailController',
          resolve: {
          	  userItinerary: function(UserService, $route){
          		  return UserService.getItinerary($route.current.params.id);
          	  }
            }
      }).when('/flights', {
          templateUrl: baseRoute + "flights/flightsTemplate.html",
          controller: 'FlightController',
          controllerAs: 'flightController',
          resolve: {
        	  allFlights: function(FlightService){
        		  return FlightService.getAllFlights();
        	  }
          }
      }).when('/flights/book', {
          templateUrl: baseRoute + "flights/bookTemplate.html",
          controller: 'BookController',
          controllerAs: 'bookController',
          resolve: {
        	  allFlights: function(FlightService){
        		  return FlightService.getAllFlights();
        	  }
          }
      }).when('/flights/get/:source/:dest', {
        	templateUrl: baseRoute + 'flights/flightDetailTemplate.html',
        	controller: 'FlightDetailController',
        	controllerAs: 'flightDetailController',
        	resolve: {
          	  flightData: function(FlightService, $route){
          		  return FlightService.getFlightDetails($route.current.params, true);
          	  }
            }
      }).when('/map', {
          templateUrl: baseRoute + "map/template.html",
          controller: 'MapController',
          controllerAs: 'mapController',
          resolve: {
        	  allFlights: function(FlightService){
        		  return FlightService.getAllFlights();
        	  }
          }
      }).otherwise('/home');
    }
  ]);