/**
 * App configuration
 */

export default /* @ngInject */ ($stateProvider, $urlRouterProvider) => {

  $urlRouterProvider.otherwise('/');

  $stateProvider

    .state('home', {
      url  : '/',
      views: {
        content: {
          templateUrl: 'scripts/home-view.html',
          controller : /* @ngInject */ ($scope) => {
          }
        }
      }
    }).state('reduced', {
      url  : '/red',
      views: {
        content: {
          templateUrl: 'scripts/reduced-view.html',
          controller : /* @ngInject */ ($scope) => {
          }
        }
      }
    });

};
