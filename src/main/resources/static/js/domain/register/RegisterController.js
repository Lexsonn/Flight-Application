angular.module('app').controller('RegisterController', 
								['UpdateService', 'UserService', '$rootScope', '$location', '$scope', 
								 function(UpdateService, UserService, $rootScope, $location, $scope) {							
	activateCSS('register-form');
	activateCSS('register-style');

	var lastPage = UpdateService.last;
	
	if (lastPage === null || lastPage === undefined || lastPage === "") {
		lastPage = '/home';
	}
	
	var returnLast = function(last) {
		removeCSS('register-form');
		removeCSS('register-style');
		$location.path(last);
	}
	
	if (UserService.user != null && UserService.user != undefined && UserService.user != "") {
		returnLast(lastPage);
	}
	
	this.returnHome = function() {
		returnLast('/home');
	}
	
	this.submit = function() {
		UserService.createUser($scope.user).then(function(result) {
			if (result.data === null || result.data === undefined || result.data === '') {
				swal("Sorry...", "Username already exists, please enter in another username.", "error");
			} else {
				swal("Success!", "Registration successful!", "success");
				returnLast(lastPage);
			}
		});
	}
	
	this.login = function() {
		UserService.login($scope.user).then(function(result) {
			UserService.user = result.data;
			if (UserService.user === null || UserService.user === undefined || UserService.user === '') {
				swal("Sorry...", "Login credentials are incorrect.", "error");
				$scope.user.password = '';
			} else {
				UserService.user.password = $scope.user.password;
				swal("Success!", "Login successful!", "success");
				returnLast(lastPage);
			}
		});
	}
}]);
