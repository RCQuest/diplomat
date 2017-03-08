/**
 * Main application file
 */

import 'angular';
import 'angular-ui-router';
import 'angular-sanitize';
import 'angularjs-scroll-glue';
import 'angular-cookies';
import 'angular-local-storage';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

import config from './config';
import templatesModule from './_templates';
import * as StringSimiliarity from 'string-similarity';
import * as _ from 'underscore';

// define diplomat module
var diplomat = angular.module('diplomat', [
  'ui.router',
  'ngSanitize',
  'luegg.directives',
  'ngCookies',
  'LocalStorageModule',
  templatesModule.name
]);

diplomat.directive("cli",()=>{
	return {
		controller:($scope,$anchorScroll,$location,$timeout,$sce)=>{

			$scope.outputLines = [
				{
					output:"Welcome! Type 'help' for more.",
					id:0
				}
			];
			$scope.nextOutputId = 1;
			$scope.selectedOutput = 2;
			$scope.scrollMode = false;

			$scope.suggestions = [{
				command: "help",
				history: ["invalid"]
			},
			{
				command: "look inventory",
				history: ["pickup","help"]
			},
			{
				command: "look",
				history: ["use","help"]
			}];
			$scope.FilteredSuggestions = [];

			$scope.helpItems = [
				"help - displays help",
				"look - describes the room",
				"look (object) - describes the object",
				"use (object) on (target) - uses one item on another",
				"pickup (object) - puts an object in your inventory",
				"use (object) - uses an item",
				"every (object) - searches objects in the room",
				"undo - reverses the previous command"
			];
			$scope.scrollModeToggle = (toggle)=>{
				$scope.scrollMode = toggle;
			}

			$scope.addOutputLine = (line)=>{
				$scope.selectedOutput = $scope.outputLines.length;
				console.log(line);
				if(!line) return;
				sendCommand(line);
				$scope.outputLines.push({input:line,id:$scope.nextOutputId});
				$scope.nextOutputId++;
				$scope.selectedOutput = $scope.nextOutputId;
				$scope.command = "";
			}

			$scope.decrementOutputSelection = ()=>{
				if($scope.selectedOutput > 0) 
					$scope.selectedOutput--;
				$scope.command = $scope.findSelectionWithId($scope.selectedOutput,$scope.outputLines);
				
			}

			$scope.findSelectionWithId = (id,output) =>{
				console.log($scope.FilteredSuggestions);
				console.log(id);
				var o = $scope.findOutputWithId(id,output).input;
				if(o===""){
					return $scope.findSuggestionWithId(id);
				} else {
					return o;
				}
			}

			$scope.findSuggestionWithId = (id) =>{
				id -= $scope.nextOutputId;
				id -= 1;
				console.log(id);
				if(id<0||id<$scope.FilteredSuggestions.length)
					return $scope.FilteredSuggestions[id];
				else 
					return "";
			}

			$scope.findOutputWithId = (id,outputLines)=>{
				for (var i = outputLines.length - 1; i >= 0; i--) {
					if(outputLines[i].id == id) return outputLines[i];
					if(Array.isArray(outputLines[i].undone)){
						var recRes = $scope.findOutputWithId(id,outputLines[i].undone);
						if(recRes.input!="")
							return recRes;
					}
				}
				return {input:""};
			}

			$scope.incrementOutputSelection = ()=>{
				$scope.selectedOutput++;
				var total = $scope.nextOutputId+$scope.FilteredSuggestions.length+1;
				if($scope.selectedOutput >= total) 
					$scope.selectedOutput = total;
				else
					$scope.command = $scope.findSelectionWithId($scope.selectedOutput,$scope.outputLines);				
			}

			$scope.undoLastCommand = ()=>{
				$scope.outputLines.pop(); // remove undo box
				$scope.nextOutputId --;
				$scope.selectedOutput = $scope.nextOutputId;
				if($scope.outputLines.length>1){
					var lastCommand = $scope.outputLines.pop();
					var context = $scope.outputLines[$scope.outputLines.length-1];
					if(Array.isArray(context.undone))
						$scope.outputLines[$scope.outputLines.length-1].undone.push(lastCommand);
					else
						$scope.outputLines[$scope.outputLines.length-1].undone = [lastCommand];
				}
				
			}

			$scope.setFilteredSuggestions = (num) =>{
				$scope.FilteredSuggestions = num;
			}

			$scope.focusInput = true;

			var socket = new SockJS('http://localhost:8080/command');
			

			var stompClient = Stomp.over(socket);
			stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/response', function(greeting){
                	var content = JSON.parse(greeting.body).content
                    console.log(content);
                	if(content==="undone"){
            			$scope.undoLastCommand();
            		} else if(content==="restarting") {
            			$scope.outputLines = [
							{
								output:"Welcome! Type 'help' for more.",
								id:0
							}
						];
                	} else {
                		$scope.outputLines[$scope.outputLines.length-1].output =
                    		(content).replace(/\n/g, "<br />");
                	}

                    $timeout();

                });
            });

            var sendCommand = function(command){
            	console.log('tryina senddd '+command);
            	stompClient.send(
            		"/diplomat/command",
            		{},
            		JSON.stringify({
            			"commandString":command
            		}));
            	
            };
		},
		templateUrl: "scripts/cli.html"
	}
});
diplomat.directive("cliReduced",()=>{
	return {
		controller:($scope,$anchorScroll,$location,$timeout,$sce)=>{

			$scope.outputLines = [
				{
					output:"Welcome! Type 'help' for more.",
					id:0
				}
			];
			$scope.nextOutputId = 1;
			$scope.selectedOutput = 2;
			$scope.scrollMode = false;

			$scope.suggestions = [{
				command: "help",
				history: ["invalid"]
			},
			{
				command: "look inventory",
				history: ["pickup","help"]
			},
			{
				command: "look",
				history: ["use","help"]
			}];
			$scope.FilteredSuggestions = [];

			$scope.helpItems = [
				"help - displays help",
				"look - describes the room",
				"look (object) - describes the object",
				"use (object) on (target) - uses one item on another",
				"pickup (object) - puts an object in your inventory",
				"use (object) - uses an item",
				"every (object) - searches objects in the room",
				"undo - reverses the previous command"
			];
			$scope.scrollModeToggle = (toggle)=>{
				$scope.scrollMode = toggle;
			}

			$scope.addOutputLine = (line)=>{
				$scope.selectedOutput = $scope.outputLines.length;
				console.log(line);
				if(!line) return;
				sendCommand(line);
				$scope.outputLines.push({input:line,id:$scope.nextOutputId});
				$scope.nextOutputId++;
				$scope.selectedOutput = $scope.nextOutputId;
				$scope.command = "";
			}

			$scope.decrementOutputSelection = ()=>{
				if($scope.selectedOutput > 0) 
					$scope.selectedOutput--;
				$scope.command = $scope.findSelectionWithId($scope.selectedOutput,$scope.outputLines);
				
			}

			$scope.findSelectionWithId = (id,output) =>{
				console.log($scope.FilteredSuggestions);
				console.log(id);
				var o = $scope.findOutputWithId(id,output).input;
				if(o===""){
					return $scope.findSuggestionWithId(id);
				} else {
					return o;
				}
			}

			$scope.findSuggestionWithId = (id) =>{
				id -= $scope.nextOutputId;
				id -= 1;
				console.log(id);
				if(id<0||id<$scope.FilteredSuggestions.length)
					return $scope.FilteredSuggestions[id];
				else 
					return "";
			}

			$scope.findOutputWithId = (id,outputLines)=>{
				for (var i = outputLines.length - 1; i >= 0; i--) {
					if(outputLines[i].id == id) return outputLines[i];
					if(Array.isArray(outputLines[i].undone)){
						var recRes = $scope.findOutputWithId(id,outputLines[i].undone);
						if(recRes.input!="")
							return recRes;
					}
				}
				return {input:""};
			}

			$scope.incrementOutputSelection = ()=>{
				$scope.selectedOutput++;
				var total = $scope.nextOutputId+$scope.FilteredSuggestions.length+1;
				if($scope.selectedOutput >= total) 
					$scope.selectedOutput = total;
				else
					$scope.command = $scope.findSelectionWithId($scope.selectedOutput,$scope.outputLines);				
			}

			$scope.undoLastCommand = ()=>{
				$scope.outputLines.pop(); // remove undo box
				$scope.nextOutputId --;
				$scope.selectedOutput = $scope.nextOutputId;
				if($scope.outputLines.length>1){
					var lastCommand = $scope.outputLines.pop();
					var context = $scope.outputLines[$scope.outputLines.length-1];
					if(Array.isArray(context.undone))
						$scope.outputLines[$scope.outputLines.length-1].undone.push(lastCommand);
					else
						$scope.outputLines[$scope.outputLines.length-1].undone = [lastCommand];
				}
				
			}

			$scope.setFilteredSuggestions = (num) =>{
				$scope.FilteredSuggestions = num;
			}

			$scope.focusInput = true;

			var socket = new SockJS('http://localhost:8080/command');
			

			var stompClient = Stomp.over(socket);
			stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/response', function(greeting){
                	var content = JSON.parse(greeting.body).content
                    console.log(content);
                	if(content==="undone"){
            			$scope.undoLastCommand();
                	} else if(content==="restarting") {
            			$scope.outputLines = [
							{
								output:"Welcome! Type 'help' for more.",
								id:0
							}
						];
					} else {
                		$scope.outputLines[$scope.outputLines.length-1].output =
                    		(content).replace(/\n/g, "<br />");
					}

                    $timeout();

                });
            });

            var sendCommand = function(command){
            	console.log('tryina senddd '+command);
            	stompClient.send(
            		"/diplomat/command",
            		{},
            		JSON.stringify({
            			"commandString":command
            		}));
            	
            };
		},
		templateUrl: "scripts/cli-reduced.html"
	}
});


diplomat.directive('onPressEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
			

            if(event.which === 13 && 
            	scope.outputLines[scope.outputLines.length-1].output /*bit of a hack*/) {
				console.log("enter pressed!");

                scope.$apply(function (){
                    scope.$eval(attrs.onPressEnter);
                });

                event.preventDefault();
            }
        });
    };
});

diplomat.directive('onPressDown', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 40) {
            	console.log("down pressed!");
                scope.$apply(function (){
                    scope.$eval(attrs.onPressDown);
                });

                event.preventDefault();
            }
        });
    };
});

diplomat.directive('onPressUp', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 38) {

                scope.$apply(function (){
                    scope.$eval(attrs.onPressUp);
                });

                event.preventDefault();
            }
        });
    };
});

diplomat.directive('scrollModeToggler', function () {
    return function (scope, element, attrs) {
    	scope.FilteredSuggestions
        element.bind("keydown keypress", function (event) {
            if(event.which === 38 || event.which == 40) {
                scope.$apply(function (){
                    scope.scrollModeToggle(true);
                });
            } else {
            	scope.$apply(function (){
                    scope.scrollModeToggle(false);
                });
            }
        });
    };
});

// diplomat.directive('analyticsMonitor', ["$cookies",function($cookies){
//     return {
//         link: function (scope, element, attrs) {
//         	element.bind("keydown", function (event) {
//     			var ts = Date.now();
//         		var sessionData = $cookies.getObject("sessionData");
//         		if(sessionData){
//         			sessionData[ts]=event.which;
//         		} else {
//         			sessionData = {};
//         			sessionData[ts] = event.which;
//         		}
//         		$cookies.putObject("sessionData",sessionData);
//         		console.log($cookies.getObject("sessionData"));

            	
//         	});
//         }
//     };
// }]);

diplomat.directive('analyticsMonitor', ["localStorageService",function(localStorageService){
    return {
        link: function (scope, element, attrs) {
        	element.bind("keydown", function (event) {
    			var ts = Date.now();
        		var sessionData = localStorageService.get("sessionData");
        		if(sessionData){
        			sessionData[ts]=event.which;
        		} else {
        			sessionData = {};
        			sessionData[ts] = event.which;
        		}
        		localStorageService.set("sessionData",sessionData);
        		console.log(localStorageService.get("sessionData"));

            	
        	});
        }
    };
}]);

diplomat.filter('to_trusted', ['$sce', function($sce){
    return function(text) {
        return $sce.trustAsHtml(text);
    };
}]);

diplomat.directive('focusMe', ['$timeout', '$parse', function ($timeout, $parse) {
    return {
        //scope: true,   // optionally create a child scope
        link: function (scope, element, attrs) {
            var model = $parse(attrs.focusMe);
            scope.$watch(model, function (value) {
                console.log('value=', value);
                if (value === true) {
                    $timeout(function () {
                        element[0].focus();
                    });
                }
            });
        }
    };
}]);

// diplomat.directive('taskMonitor', function(){
// 	return {
// 		link: function(scope,el,attrs){
// 			element.bind("keydown", function (event) {

// 			}
// 		}
// 	}
// });

diplomat.filter('help', function() {
   return function(items, words) {
   	if(words===""||!words) return [];

   	var wordsList = words.split(" ");

    var filtered = [];

    angular.forEach(items, function(item) {
    	angular.forEach(wordsList, (word)=>{
    		if(item.indexOf(word) !== -1 && word.length > 1){
	            filtered.push(item);
	        }
    	});
        
    });

    filtered = _.uniq(filtered);

    // filtered.sort(function(a,b){
    //     if(a.indexOf(words) < b.indexOf(words)) return -1;
    //     else if(a.indexOf(words) > b.indexOf(words)) return 1;
    //     else return 0;
    // });

    return filtered;
  };
});

diplomat.filter('suggest', function() {
	return function(items, command, history, setNum) {
		var suggestions = [];
	    var sortedSuggestions = [];
	    var lastCommand = history[history.length-1];

	   	if(command!==""||!lastCommand.input) 
	   		return sortedSuggestions;

	    angular.forEach(items, function(item) {
	        var sim = StringSimiliarity
				.compareTwoStrings(
					item.history.join(), 
					lastCommand.input+" "+lastCommand.output);
			var command = item.command;
			if(sim>0.1) suggestions.push({"command":command, "suitability":sim});
	    });

	    suggestions.sort(function(a,b){
	        if(a.suitability < b.suitability) return 1;
	        else if(a.suitability > b.suitability) return -1;
	        else return 0;
	    });

	    angular.forEach(suggestions, (suggestion)=>{
    		sortedSuggestions.push(suggestion.command);
	    });

	    setNum(sortedSuggestions);

	    return sortedSuggestions;
    }
});

// configure diplomat
diplomat.config(config);

// bootstrap diplomat
angular.element(document).ready(function() {
	angular.bootstrap(document, [diplomat.name]);
});
