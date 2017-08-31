(function() {
    'use strict';

    angular
        .module('catalogApp')
        .controller('CategoryDtoDetailController', CategoryDtoDetailController);

    CategoryDtoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Category', 'Product'];

    function CategoryDtoDetailController($scope, $rootScope, $stateParams, previousState, entity, Category, Product) {
        var vm = this;

        vm.category = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('catalogApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
