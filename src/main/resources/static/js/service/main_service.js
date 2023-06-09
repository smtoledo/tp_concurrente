'use strict';

App.factory('MainService', ['$http', '$q', function($http, $q){

	return {
		
            find_sequencially: function(keyword) {
					return $http.get('http://localhost:8080/find_sequencially/'+keyword)
					//return $http.get('http://localhost:8080/trabajo-practico-pc/find_sequencially/'+keyword)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching users');
										return $q.reject(errResponse);
									}
							);
			},

		    find_in_parallel: function(keyword) {
					return $http.get('http://localhost:8080/find_in_parallel/'+keyword)
					//return $http.get('http://localhost:8080/trabajo-practico-pc/find_in_parallel/'+keyword)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching users');
										return $q.reject(errResponse);
									}
							);
			},

			find_in_parallel_ms: function(keyword) {
				return $http.get('http://localhost:8080/find_in_parallel_ms/'+keyword)
				//return $http.get('http://localhost:8080/trabajo-practico-pc/find_in_parallel_ms/'+keyword)
						.then(
								function(response){
									return response.data;
								}, 
								function(errResponse){
									console.error('Error while fetching users');
									return $q.reject(errResponse);
								}
						);
			},

	};

}]);