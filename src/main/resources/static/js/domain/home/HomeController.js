angular.module('app').controller('HomeController', 
								['UpdateService', 'UserService', '$route', '$scope', 
								 function(UpdateService, UserService, $route, $scope) {
	$('#userdiv').hide();

	UpdateService.last = '/home';
	
	this.username = '';
	
	if (UserService.user != null && UserService.user != undefined && UserService.user != "") {
		$('#userdiv').show();
		$('#anonymous').hide();
		this.username = UserService.user.username;
	}
	
	this.signOut = function() {
		UserService.user = null;
		swal("Success!", "You have successfully signed out.", "success");
		$route.reload();
	}
	
}]);