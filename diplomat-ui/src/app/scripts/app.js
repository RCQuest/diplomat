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

function compareTwoStrings(sit,sug,bonus){
	var disallowedBonus = ["look","pickup","use","on"];
	sit = sit.replace(/[^a-zA-Z ]/g, "");
	sug = sug.replace(/[^a-zA-Z ]/g, "");
	sit = sit.toLowerCase().split(" ");
	sug = sug.toLowerCase().split(" ");
	var score = 0;
	// console.log(sug);
	// console.log(sit);

	sit.forEach(a=>{
		sug.forEach(b=>{
			if(a===b) {
				if(bonus.indexOf(a)!=-1&&disallowedBonus.indexOf(a)==-1)
					score+=5;
				else
					score++;
				// console.log("equal!");
			}
		});
	});
	return score;
}

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

			
			$scope.suggestions = [
				{
					command: "help",
					history: ["invalid"]
				},
				{
					command: "undo",
					history: ["invalid","snaps","tension"]
				},
				{
					command: "look inventory",
					history: ["pickup","help","invalid"]
				},
				{
					command: "look",
					history: ["use","help","welcome","help","invalid"]
				},
				{
					command:"pickup key",
					history: ["key","door","room","reveals","floats","water","tube","snowglobe","debris","displaces","stone","crate","open","melt","puddle"]
				},
				{
					command:"use key on door",
					history: ["key","pickup"]
				},
				{
					command:"use door",
					history: ["unlock", "door","key","unlocks","click","heft"]
				},
				{
					command:"look door",
					history: ["unlock", "door","key","room","look"]
				},
				{
					command:"start",
					history: ["congratulations","escaped","start"]
				},
				{
					command:"pickup cloth",
					history: ["cloth","look"]
				},
				{
					command:"use pail on tube",
					history: ["pail","tube","door","look","room"]
				},
				{
					command:"look pail",
					history: ["pail","tube","door","look","room"]
				},
				{
					command:"look tube",
					history: ["pail","tube","door","look","room","stone","displaces","insert","water"]
				},
				{
					command:"look snowglobe",
					history: ["snowglobe","look","room","door"]
				},
				{
					command:"use snowglobe",
					history: ["snowglobe","look","room","door"]
				},
				{
					command:"break snowglobe",
					history: ["snowglobe","look","room","door"]
				},
				{
					command:"place weight on floorpad",
					history: ["floorpad","weight","door"]
				},
				{
					command:"look weight",
					history: ["floorpad","weight","door","portcullis","chain"]
				},
				{
					command:"look floorpad",
					history: ["floorpad","weight","door"]
				},
				{
					command:"look portcullis",
					history: ["portcullis","chain","weight","room","look"]
				},
				{
					command:"look chain",
					history: ["portcullis","chain","weight","room","look"]
				},
				{
					command:"place weight on chain",
					history: ["portcullis","chain","weight","room","look"]
				},
				{
					command:"use chain",
					history: ["portcullis","chain","weight","room","look"]
				},
				{
					command:"place every weight on chain",
					history: ["portcullis","chain","weight","room","look"]
				},
				{
					command:"use portcullis",
					history: ["portcullis","chain","weight","heavy","opened","hook"]
				},
				{
					command:"look stone",
					history: ["stone","tube","door","look"]
				},
				{
					command:"use stone on tube",
					history: ["stone","tube","door","look"]
				},
				{
					command:"use every stone on tube",
					history: ["stone","tube","door","look"]
				},
				{
					command:"use rope",
					history: ["portcullis","rope","weight","room","look"]
				},
				{
					command:"look rope",
					history: ["portcullis","rope","weight","room","look"]
				},
				{
					command:"place weight on rope",
					history: ["portcullis","rope","weight","room","look","place","on"]
				},
				{
					command:"place every weight on rope",
					history: ["portcullis","rope","weight","room","look"]
				},
				{
					command:"look ladder",
					history: ["ladder","hatch","room"]
				},
				{
					command:"look hatch",
					history: ["ladder","hatch","room"]
				},
				{
					command:"use ladder on hatch",
					history: ["ladder","hatch","room"]
				},
				{
					command:"look crate",
					history: ["crate","door","room"]
				},
				{
					command:"open crate",
					history: ["crate","door","room"]
				},
				{
					command:"open every crate",
					history: ["crate","door","room"]
				},
				{
					command:"break every crate",
					history: ["crate","door","room"]
				},
				{
					command:"break crate",
					history: ["crate","door","room","nothing"]
				},
				{
					command:"look iceblock",
					history: ["iceblock","hairdrier","door","room","look"]
				},
				{
					command:"look hairdrier",
					history: ["iceblock","hairdrier","door","room","look"]
				},
				{
					command:"use hairdrier on iceblock",
					history: ["iceblock","hairdrier","door","room","look"]
				},
				{
					command:"use hairdrier on every iceblock",
					history: ["iceblock","hairdrier","door","room","look"]
				},
				{
					command:"use trapdoor",
					history: ["stomp","crate","trapdoor","splinters","rubble"]
				}

			];

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

diplomat.directive('analyticsMonitor', ["localStorageService","$location",function(localStorageService,$location){
    return {
        link: function (scope, element, attrs) {
        	element.bind("keydown", function (event) {
    			var ts = Date.now();
        		var sessionData = localStorageService.get($location.url());
        		if(sessionData){
        			sessionData[ts]=event.which;
        		} else {
        			sessionData = {};
        			sessionData[ts] = event.which;
        		}
        		localStorageService.set($location.url(),sessionData);
        		console.log(localStorageService.get($location.url()));

            	
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
	        var sim = compareTwoStrings(
					item.history.join(" "), 
					lastCommand.input+" "+lastCommand.output,
					item.command.split(" "));
			var command = item.command;
			if(sim>5) suggestions.push({"command":command, "suitability":sim});
	    });

	    suggestions.sort(function(a,b){
	        if(a.command==="undo") return -1;
	        else if(a.suitability < b.suitability) return 1;
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
