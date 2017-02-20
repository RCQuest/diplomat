/**
 * Main application file
 */

import 'angular';
import 'angular-ui-router';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

import config from './config';
import templatesModule from './_templates';

// define diplomat module
var diplomat = angular.module('diplomat', [
  'ui.router',
  templatesModule.name
])

.directive("cli",()=>{
	return {
		controller:($scope,$anchorScroll,$location,$timeout)=>{
			var gotoBottom = function() {
				$location.hash('lastCommand');
				$anchorScroll(); //TODO: fix!
			};

			$scope.outputLines = [
				{output:"Welcome!"}
			];
			$scope.addOutputLine = (line)=>{
				if(line==="") return;
				sendCommand(line);
				$scope.outputLines.push({input:line});
				$scope.command = "";
				$timeout(()=>{
					gotoBottom();
				});
			}

			var socket = new SockJS('http://localhost:8080/command');
			

			var stompClient = Stomp.over(socket);
			stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/response', function(greeting){
                    console.log(JSON.parse(greeting.body).content);
                    $scope.outputLines[$scope.outputLines.length-1].output =
                    	(JSON.parse(greeting.body).content);
                	gotoBottom();
                    $timeout(()=>{
						gotoBottom();
					});
                });
            });

            var sendCommand = function(command){
            	console.log('tryina senddd '+command);
            	stompClient.send("/diplomat/command",{},JSON.stringify({"commandString":command}));
            	
            };
		},
		templateUrl: "scripts/cli.html"
	}
})
.directive('onPressEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
			console.log("key pressed!");

            if(event.which === 13 && scope.outputLines[scope.outputLines.length-1].output) {
				console.log("enter pressed!");

                scope.$apply(function (){
                    scope.$eval(attrs.onPressEnter);
                });

                event.preventDefault();
            }
        });
    };
});

// configure diplomat
diplomat.config(config);

// bootstrap diplomat
angular.element(document).ready(function() {
  angular.bootstrap(document, [diplomat.name]);
});
