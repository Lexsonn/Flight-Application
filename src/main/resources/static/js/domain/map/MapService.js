angular.module('app').service('MapService', ['$http', function($http) {
	this.getMarkerByCityName = function(map, name) {
		//Send city name param to URL
		return $http.get('locations/name', {params: { name : name }}).then(function(result) {
			return new google.maps.Marker({
				map : map,
				position : {
					lat : +result.data.latitude,
					lng : +result.data.longitude
				}
			});
		})
	}
	this.getMarkersByCities = function(map, names) {
		var n = [];
		n.push(names[0].departure)
		n.push(names[0].destination)
		for (var i = 1; i < names.length; i++) {
			n.push(names[i].destination)
		}
		
		return $http.get('locations/names', {params: { names : n }}).then(function(result) {
			var res = [];
			for (var i = 0; i < result.data.length; i++) {
				res.push(new google.maps.Marker({
					map : map,
					position : {
						lat : +result.data[i].latitude,
						lng : +result.data[i].longitude
					}
				}));
			}
			console.dir(res)
			return res;
		})
	}
}]);