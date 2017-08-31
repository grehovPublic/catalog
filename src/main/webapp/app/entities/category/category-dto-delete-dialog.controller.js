(function() {
    'use strict';

    angular
        .module('catalogApp')
        .controller('CategoryDtoDeleteController',CategoryDtoDeleteController);

    CategoryDtoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Category'];

    function CategoryDtoDeleteController($uibModalInstance, entity, Category) {
        var vm = this;

        vm.category = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Category.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
