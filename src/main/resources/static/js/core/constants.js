angular.module('app').constant('baseRoute', 'js/domain/');
// Check for page changes, and disable CSS when needed.
angular.module('app').run(function($rootScope, $route, UpdateService, $location) {

	$rootScope.$on('$locationChangeSuccess', function() {
		UpdateService.check = false;
		clearInterval(UpdateService.interval);
	    $rootScope.actualLocation = $location.path();
	});

	$rootScope.$watch(function () {return $location.path()}, function (newLocation, oldLocation) {
	    if($rootScope.actualLocation === newLocation && newLocation != '/register'  && newLocation != '/login') {
	    	removeCSS('register-form')
			removeCSS('register-style');
	    }
	});
})

var loadCSS = function(href, id) {
  var cssLink = $("<link>");
  $("head").append(cssLink); //IE hack: append before setting href

  cssLink.attr({
    rel:  "stylesheet",
	type: "text/css",
	id: id,
	href: href
  });
}

var removeCSS = function(id) {
	$('#' + id).attr('disabled', 'disabled');
}
var activateCSS = function(id) {
	$('#' + id).removeAttr('disabled');
}

//default disabled css
removeCSS('register-form');
removeCSS('register-style');
