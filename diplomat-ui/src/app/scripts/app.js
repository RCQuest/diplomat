/**
 * Main application file
 */

import 'angular';
import 'angular-ui-router';
import 'angular-sanitize';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

import config from './config';
import templatesModule from './_templates';

// define diplomat module
var diplomat = angular.module('diplomat', [
  'ui.router',
  'ngSanitize',
  templatesModule.name
]);

diplomat.directive("cli",()=>{
	return {
		controller:($scope,$anchorScroll,$location,$timeout,$sce)=>{
			var gotoBottom = function() {
				$location.hash('lastCommand');
				$anchorScroll(); //TODO: fix!
			};

			$scope.outputLines = [
				{output:"Welcome!"}
			];

			$scope.selectedOutput = 1;
			$scope.addOutputLine = (line)=>{
				$scope.selectedOutput = $scope.outputLines.length;
				console.log(line);
				if(!line) return;
				sendCommand(line);
				$scope.outputLines.push({input:line});
				$scope.selectedOutput = $scope.outputLines.length;
				$scope.command = "";
				$timeout(()=>{
					gotoBottom();
				});
			}

			$scope.decrementOutputSelection = ()=>{
				if($scope.selectedOutput > 0) 
					$scope.selectedOutput--;
				$scope.command = $scope.outputLines[$scope.selectedOutput].input;
				console.log($scope.outputLines)
			}
			$scope.incrementOutputSelection = ()=>{
				$scope.selectedOutput++;
				if($scope.selectedOutput >= $scope.outputLines.length) 
					$scope.selectedOutput = $scope.outputLines.length;
				else
					$scope.command = $scope.outputLines[$scope.selectedOutput].input;
			}

			var getLastDoneCommandIndexBefore = (before)=>{
				for (var i = before-1; i >= 0; i--) {
					if(!$scope.outputLines[i].undone) return i;
				}
				return -1;
			};

			$scope.undoLastCommand = ()=>{
				$scope.outputLines.pop(); // remove undo box
				var lastCommandIndex = getLastDoneCommandIndexBefore($scope.outputLines.length);
				if(lastCommandIndex==-1) return;
				$scope.outputLines[lastCommandIndex].undone = true;
				// $scope.outputLines[lastCommandIndex].commandBefore = ?
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
                	} else {
                		$scope.outputLines[$scope.outputLines.length-1].output =
                    		(content).replace(/\n/g, "<br />");
                	}

                	gotoBottom();

                    $timeout(()=>{
						gotoBottom();
					});

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
        	console.log("key pressed! "+ event.which);
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
				console.log("up pressed!");

                scope.$apply(function (){
                    scope.$eval(attrs.onPressUp);
                });

                event.preventDefault();
            }
        });
    };
});

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

// configure diplomat
diplomat.config(config);

// bootstrap diplomat
angular.element(document).ready(function() {
	angular.bootstrap(document, [diplomat.name]);
});
