/**
 * Main application file
 */

import 'angular';
import 'angular-ui-router';

import config from './config';
import templatesModule from './_templates';

// define diplomat module
var diplomat = angular.module('diplomat', [
  'ui.router',
  templatesModule.name
])

.directive("cli",()=>{
	return {
		controller:($scope)=>{
			$scope.outputLines = [
				"hello world",
				"this is a new line",
				"and this one",
				"anD THIS IS A REALLY LONG LINEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"
			]
			$scope.addOutputLine = (line)=>{
				$scope.outputLines.push(line);
				$scope.command = "";
			}
		},
		templateUrl: "scripts/cli.html"
	}
})
.directive('onPressEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
			console.log("key pressed!");

            if(event.which === 13) {
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
