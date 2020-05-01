var libraryApp = angular.module("libraryApp",["ngRoute"]);

libraryApp.controller("homeCtrl", function($scope){
	$scope.message="Library";
});

libraryApp.controller("booksCtrl", function($scope, $http, $location){
	
	$scope.books = [];
	$scope.publishers = [];

	$scope.newBook = {};
	$scope.newBook.title = "";
	$scope.newBook.edition = "";
	$scope.newBook.writer = "";
	$scope.newBook.isbn = "";
	$scope.newBook.bookCount = "";

	$scope.newBook.publisherId = "";
	
	$scope.searchParams = {};
	$scope.searchParams.title = "";
	$scope.searchParams.writer = "";
	$scope.searchParams.minVotesCount = "";
	
	$scope.highestVotedBook = {};
	
	$scope.pageNum = 0;
	$scope.totalPages = 1;
	
	var booksUrl = "/api/books";
	var publishersUrl = "/api/publishers";
	
	var getBooks = function(){
		
		var config = { params: {} };		
		
		if($scope.searchParams.title != ""){
			config.params.title = $scope.searchParams.title;
		}
		
		if($scope.searchParams.writer != ""){
			config.params.writer = $scope.searchParams.writer;
		}
		
		if($scope.searchParams.minVotesCount != ""){
			config.params.minVotesCount = $scope.searchParams.minVotesCount;
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
	
	var getHighestVotedBook = function(){
		$http.get(booksUrl + "/votes").then(
			function success(res){
				$scope.highestVotedBook = res.data;
			},
			function error(){
				alert("Failed to find the book.");
			}
		);
	}
	
	getHighestVotedBook();
	
	$scope.doAdd = function(){
		
		$http.post(booksUrl, $scope.newBook).then(
			function success(){
				getBooks();
				
				$scope.newBook.title = "";
				$scope.newBook.edition = "";
				$scope.newBook.writer = "";
				$scope.newBook.isbn = "";
				$scope.newBook.bookCount = "";

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
		$location.path("/books/edit/" + id);
	}
	
	$scope.doReserve = function(id){
		$http.post(booksUrl + "/count/" + id).then(
			function success(){
				alert("Book reserved succesfuly.")
				getBooks();
			},
			function error(){
				alert("Failed to make reservation.");
				getBooks();
			}
		);
	}
	
	$scope.doVote = function(id){
		$http.post(booksUrl + "/votes/" + id).then(
			function success(){
				getBooks();
				getHighestVotedBook();
			},
			function error(){
				alert("Failed to vote.");
			}
		);
	}
	
	$scope.doSearch = function(){
		$scope.pageNum = 0;
		getBooks();
		
		$scope.searchParams.title = "";
		$scope.searchParams.writer = "";
		$scope.searchParams.minVotesCount = "";
	}
	
	$scope.doFastSearch = function(){
		$scope.searchParams.minVotesCount = 5;
		$scope.pageNum = 0;
		getBooks();
	}
	
	$scope.changePage = function(direction){
		$scope.pageNum = $scope.pageNum + direction;
		getBooks();
	}
	
});

libraryApp.controller("editBookCtrl", function($scope, $http, $routeParams, $location){
	
	var oldBookUrl = "/api/books/" + $routeParams.id;
	var publishersUrl = "/api/publishers";

	$scope.publishers = [];
	
	$scope.oldBook = {};
	$scope.oldBook.title = "";
	$scope.oldBook.edition = "";
	$scope.oldBook.writer = "";
	$scope.oldBook.isbn = "";
	$scope.oldBook.bookCount = "";
	$scope.oldBook.publisher = "";

	$scope.oldBook.publisherId = "";
	
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
		$http.get(oldBookUrl).then(
			function success(res){
				$scope.oldBook = res.data;
			},
			function error(){
				alert("Failure getting books.");
			}
		);
	}
	
	getPublisher();

	$scope.doEdit = function(){
		$http.put(oldBookUrl, $scope.oldBook).then(
			function success(){
				$location.path("/books");
			},
			function error(){
				alert("Failed to save the book.");
			}
		);
	}
});

libraryApp.controller("publishersCtrl", function($scope, $http, $location){
	
	$scope.publishers = [];

	$scope.newPublisher = {};
	$scope.newPublisher.name = "";
	$scope.newPublisher.address = "";
	$scope.newPublisher.phone = "";
	
	var publishersUrl = "/api/publishers";
	
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
	
	getPublishers();
	
	$scope.doAdd = function(){
		
		$http.post(publishersUrl, $scope.newPublisher).then(
			function success(){
				getPublishers();
				
				$scope.newPublisher.name = "";
				$scope.newPublisher.address = "";
				$scope.newPublisher.phone = "";

			},
			function error(){
				alert("Failed to save publishers!");
			}
		);
	}
	
	$scope.doDelete = function(id){
		$http.delete(publishersUrl + "/" + id).then(
			function success(){
				getPublishers();
			},
			function error(){
				alert("Failed to delete the book.");
			}
		);
	}
	
	$scope.goToEdit = function(id){
		$location.path("/publishers/edit/" + id);
	}
	
});

libraryApp.controller("editPublisherCtrl", function($scope, $http, $routeParams, $location){
	
	var oldPublisherUrl = "/api/publishers/" + $routeParams.id;

	$scope.oldPublisher = {};
	$scope.oldPublisher.name = "";
	$scope.oldPublisher.address = "";
	$scope.oldPublisher.phone = "";
	
	var getPublisher = function(){
		$http.get(oldPublisherUrl).then(
			function success(res){
				$scope.oldPublisher = res.data;
			},
			function error(){
				alert("Failure getting publisher.");
			}
		);
	}
	
	getPublisher();

	$scope.doEdit = function(){
		$http.put(oldPublisherUrl, $scope.oldPublisher).then(
			function success(){
				$location.path("/publishers");
			},
			function error(){
				alert("Failed to save the publisher.");
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
		.when('/publishers', {
			templateUrl : '/app/html/publishers.html'
		})
		.when('/publishers/edit/:id', {
			templateUrl : '/app/html/edit-publisher.html'
		})
		.otherwise({
			redirectTo: '/'
		});
}]);