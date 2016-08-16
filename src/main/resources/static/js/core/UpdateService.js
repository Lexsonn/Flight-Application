angular.module('app').service('UpdateService', ['$http', function($http) {
	this.update = 1;
	this.check = false;
	this.interval = undefined;
	
	$http.get('flights/update').then(function(result) {
		update = result.data;
	});
	this.checkUpdate = function() {
		if (!this.check) {
			return Promise.resolve(false);
		}
		return $http.get('flights/update').then(function(result) {
			if (update != result.data) {
				update = result.data
				return true;
			}
			return false;
		});
	}
}]);