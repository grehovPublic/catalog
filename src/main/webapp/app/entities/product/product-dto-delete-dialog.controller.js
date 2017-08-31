(function() {
    'use strict';

    angular
        .module('catalogApp')
        .controller('ProductDtoDeleteController',ProductDtoDeleteController);

    ProductDtoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Product'];

    function ProductDtoDeleteController($uibModalInstance, entity, Product) {
        var vm = this;

        vm.product = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Product.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
