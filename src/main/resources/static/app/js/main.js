var libraryApp = angular.module("libraryApp",["ngRoute"]);

libraryApp.controller("homeCtrl", function($scope){
	$scope.message="Library";
});

libraryApp.controller("booksCtrl", function($scope, $http, $location){
	
	$scope.books = [];
	$scope.publishers = [];

	$scope.newBook = {};
	$scope.newBook.title = "";

	$scope.newBook.publisherId = "";
	
	$scope.searchParams = {};
	$scope.searchParams.title = "";
	
	$scope.pageNum = 0;
	$scope.totalPages = 1;
	
	var booksUrl = "/api/books";
	var publishersUrl = "/api/publishers";
	
	var getBooks = function(){
		
		var config = { params: {} };		
		
		if($scope.searchParams.title != ""){
			config.params.title = $scope.searchParams.title;
		}
		
		config.params.pageNum = $scope.pageNum;
		
		$http.get(booksUrl, config).then(
			function success(res){
				$scope.books = res.data;
				$scope.totalPages = res.headers("totalPages");
				getPublishers();
			},
			function error(){
				alert("Failure getting books.");
			}
		);
	}
	
	var getPublishers = function(){
		$http.get(publishersUrl).then(
			function success(res){
				$scope.publishers = res.data;
			},
			function error(){
				alert("Failure getting publishers.");
			}
		);
	}
	
	getBooks();
	
	$scope.doAdd = function(){
		
		$http.post(booksUrl, $scope.newBook).then(
			function success(){
				getBooks();
				
				$scope.newBook.title = "";

				$scope.newBook.publisherId = "";
			},
			function error(){
				alert("Failed to save books!");
			}
		);
	}
	
	$scope.doDelete = function(id){
		$http.delete(booksUrl + "/" + id).then(
			function success(){
				getBooks();
			},
			function error(){
				alert("Failed to delete the book.");
			}
		);
	}
	
	$scope.goToEdit = function(id){
		$location.path("/edit/" + id);
	}
	
	$scope.doSearch = function(){
		$scope.pageNum = 0;
		getBooks();
		
		$scope.searchParams.title = "";
	}
	
	$scope.changePage = function(direction){
		$scope.pageNum = $scope.pageNum + direction;
		getBooks();
	}
	
});

libraryApp.controller("editBookCtrl", function($scope, $http, $routeParams, $location){
	
	var bookUrl = "/api/books/" + $routeParams.id;
	var publishersUrl = "/api/publishers";

	$scope.publishers = [];
	
	$scope.book = {};
	$scope.book.title = "";

	$scope.book.publisherId = "";
	
	var getPublisher = function(){
		$http.get(publishersUrl).then(
			function success(res){
				$scope.publishers = res.data;
				getBook();
			},
			function error(){
				alert("Failure getting publishers.");
			}
		);
	}
	
	var getBook = function(){
		$http.get(bookUrl).then(
			function success(res){
				$scope.book = res.data;
			},
			function error(){
				alert("Failure getting books.");
			}
		);
	}
	
	getPublisher();

	$scope.doEdit = function(){
		$http.put(bookUrl, $scope.book).then(
			function success(){
				$location.path("/");
			},
			function error(){
				alert("Failed to save the book.");
			}
		);
	}
});


libraryApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : '/app/html/home.html',
			controller: "homeCtrl"
		})
		.when('/books', {
			templateUrl : '/app/html/books.html'
		})
		.when('/books/edit/:id', {
			templateUrl : '/app/html/edit-book.html'
		})
		.otherwise({
			redirectTo: '/'
		});
}]);