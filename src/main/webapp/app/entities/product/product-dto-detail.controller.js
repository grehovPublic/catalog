(function() {
    'use strict';

    angular
        .module('catalogApp')
        .controller('ProductDtoDetailController', ProductDtoDetailController);

    ProductDtoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'Category'];

    function ProductDtoDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, Category) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('catalogApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
