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
			$scope.keyFreq={
				"arrows":0,
				"chars":0,
				"returns":0,
				"undos":0,
				"errors":0,
				"helps":0
			};
			$scope.task=0;
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
					command:"break iceblock",
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
				"look - describes the room",
				"look <object> - describes the object ",
				"look inventory - shows what objects you are holding",
				"use <object> - performs some action with the object ",
				"use <object> on <target> - applies the object to the target in some way ",
				"pickup <object> - puts an object in your inventory ",
				"every <query> - searches the room for all objects with names matching the query (used in the context of other commands) ",
				"place <object> on <target> - places or attaches an object to the target in some manner",
				"break <object> - attempts to destroy the object",
				"open <object> - opens a container of some kind",
				"help - shows this list",
				"undo - reverses the effect of the last command"
			];
			$scope.scrollModeToggle = (toggle)=>{
				$scope.scrollMode = toggle;
			}

			$scope.addOutputLine = (line)=>{
				$scope.selectedOutput = $scope.outputLines.length;
				// console.log(line);
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
				// console.log($scope.FilteredSuggestions);
				// console.log(id);
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
				// console.log(id);
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
                	var content = JSON.parse(greeting.body).content;
                	if(content===""){
                		content = "Invalid command.";
                	}
					if(content==="Invalid command.") {
            			$scope.keyFreq["errors"]++;
            		}
                	if(content==="undone"){
            			$scope.undoLastCommand();
            			$scope.keyFreq["undos"]++;
            		}  else if(content==="restarting") {
            			$scope.outputLines = [
							{
								output:"Welcome! Type 'help' for more.",
								id:0
							}
						];
						$scope.task++;
						$scope.keyFreq={
							"arrows":0,
							"chars":0,
							"returns":0,
							"undos":0,
							"errors":0,
							"helps":0
						};
                	} else {
                		$scope.outputLines[$scope.outputLines.length-1].output =
                    		(content).replace(/\n/g, "<br />");
                	}

                    $timeout();

                });
            });

            var sendCommand = function(command){
            	// console.log('tryina senddd '+command);
            	if(command==="help"){
            		$scope.keyFreq["helps"]++;
            	}
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
			$scope.task=0;
			$scope.keyFreq={
				"arrows":0,
				"chars":0,
				"returns":0,
				"undos":0,
				"errors":0,
				"helps":0
			};

			$scope.suggestions = [];
			$scope.FilteredSuggestions = [];

			$scope.helpItems = [];
			$scope.scrollModeToggle = (toggle)=>{
				$scope.scrollMode = toggle;
			}

			$scope.addOutputLine = (line)=>{
				$scope.selectedOutput = $scope.outputLines.length;
				// console.log(line);
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
				// console.log($scope.FilteredSuggestions);
				// console.log(id);
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
				// console.log(id);
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
                    if(content===""){
                		content = "Invalid command.";
                	}
                    if(content==="Invalid command.") {
            			$scope.keyFreq["errors"]++;
            		}
                	if(content==="undone"){
            			$scope.undoLastCommand();
            			$scope.keyFreq["undos"]++;
            		}  else if(content==="restarting") {
            			$scope.outputLines = [
							{
								output:"Welcome! Type 'help' for more.",
								id:0
							}
						];
						$scope.task++;
						$scope.keyFreq={
							"arrows":0,
							"chars":0,
							"returns":0,
							"undos":0,
							"errors":0,
							"helps":0
						};
                	} else {
                		$scope.outputLines[$scope.outputLines.length-1].output =
                    		(content).replace(/\n/g, "<br />");
                	}

                    $timeout();

                });
            });

            var sendCommand = function(command){
            	if(command==="help"){
            		$scope.keyFreq["helps"]++;
            	}
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
				// console.log("enter pressed!");

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
            	// console.log("down pressed!");
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
        		var arrows = [37,38,39,40];
        		var enters = [13];
        		if(arrows.indexOf(event.which)!=-1){
        			scope.keyFreq["arrows"]++;
        		} else if(enters.indexOf(event.which)!=-1){
        			scope.keyFreq["returns"]++;
                	var ts = Date.now();
        			localStoreCumulativeTime(localStorageService,$location.url(),"INTENT_ACT_"+scope.task,ts);
        		} else {
        			scope.keyFreq["chars"]++;
        		}
        	});
        }
    };
}]);

function localStoreCumulativeTime(localStorageService, key, subkey, value){
	var sessionData = localStorageService.get(key);
	if(sessionData){
		if(sessionData[subkey]) 
			sessionData[subkey].push(value-sessionData[subkey+"_LAST_TS"]);
		else
			sessionData[subkey] = [];
		sessionData[subkey+"_LAST_TS"]=value;
	} else {
		sessionData = {};
		sessionData[subkey] = [];
		sessionData[subkey+"_LAST_TS"] = value;
	}
	localStorageService.set(key,sessionData);
}

function isOutputSignal(newValue,signal){
	var output = newValue[newValue.length-1].output;
	if(!output) return false;
	// console.log(output);
	return output.includes(signal)
}

function locallyStore(localStorageService, key, subkey, value,dontOverwrite){
	var sessionData = localStorageService.get(key);
	if(sessionData){
		if(dontOverwrite&&(sessionData[subkey])) return;
		sessionData[subkey]=value;
	} else {
		sessionData = {};
		sessionData[subkey] = value;
	}
	localStorageService.set(key,sessionData);
}

function locallyStoreIncrement(localStorageService, key, subkey){
	var sessionData = localStorageService.get(key);
	if(sessionData){
		if(!sessionData[subkey])
			sessionData[subkey]=1;
		else
			sessionData[subkey]++;
	} else {
		sessionData = {};
		sessionData[subkey]=1;
	}
	localStorageService.set(key,sessionData);
}

diplomat.directive('outputMonitor', ["localStorageService","$location",function(localStorageService,$location){
    return {
        link: function (scope, element, attrs) {
			scope.$watch('outputLines', function(newValue, oldValue) {
				// console.log(newValue);
				// console.log(isOutputSignal(newValue,"Congratulations."));
                if (isOutputSignal(newValue,"Congratulations.")){
                	var ts = Date.now();
                	locallyStore(localStorageService,$location.url(),"TASK_SUCCEED_"+scope.task,true,true);
                	locallyStore(localStorageService,$location.url(),"TASK_TIME_END_"+scope.task,ts,true);
                	locallyStore(localStorageService,$location.url(),"TASK_ARROWS_"+scope.task,scope.keyFreq["arrows"],true);
                	locallyStore(localStorageService,$location.url(),"TASK_ENTER_"+scope.task,scope.keyFreq["returns"],true);
                	locallyStore(localStorageService,$location.url(),"TASK_CHARS_"+scope.task,scope.keyFreq["chars"],true);
                	locallyStore(localStorageService,$location.url(),"COMMANDS_UNDONE_"+scope.task,scope.keyFreq["undos"],true);
                	locallyStore(localStorageService,$location.url(),"INVALID_SUBMITS_"+scope.task,scope.keyFreq["errors"],true);
                	locallyStore(localStorageService,$location.url(),"HELP_REFERS_"+scope.task,scope.keyFreq["helps"],true);
                } 
                if(isOutputSignal(newValue,"Welcome!")){
                	var ts = Date.now();
                	locallyStore(localStorageService,$location.url(),"TASK_TIME_BEGIN_"+scope.task,ts,true);
                }
            
    			
        	}, true);
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
