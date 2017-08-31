(function() {
    'use strict';

    angular
        .module('catalogApp')
        .controller('ProductDtoCatController', ProductDtoCatController);

    ProductDtoCatController.$inject = ['$state', '$stateParams', 'CatProduct', 'Category', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function ProductDtoCatController($state, $stateParams, CatProduct, Category, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {
            CatProduct.query({id : $state.params.id},{
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.products = data;
                vm.page = pagingParams.page;
                vm.currentCatId = $state.params.id;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

            Category.query({
                page: 0,
                size: 100,
                sort: 'name'
            },  onCatSuccess, onCatError);

            function onCatSuccess(data, headers) {
                vm.categories = data;
                vm.page = 0;
            }
            function onCatError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
