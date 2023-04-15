'use strict';

App.controller('MainController', ['$scope', 'MainService', function ($scope, MainService) {
    var self = this;
    self.seq_keyword = '';
    self.par_keyword = '';
    self.seq_result = [];
    self.par_result = [];

    self.find_sequencially = function (keyword) {
        MainService.find_sequencially(keyword)
            .then(
                function (data) {
                    self.seq_result = data;
                }
            );
    };

    self.find_in_parallel = function (keyword) {
        MainService.find_in_parallel(keyword)
            .then(
                function (data) {
                    self.par_result = data;
                }
            );
    };

    self.seq_submit = function () {
        self.find_sequencially(self.seq_keyword);
    };

    self.par_submit = function () {
        self.find_in_parallel(self.par_keyword);
    };

    self.seq_reset = function () {
        self.seq_keyword = '';
        self.seq_result = [];
    };

    self.par_reset = function () {
        self.par_keyword = '';
        self.par_result = [];
    };

}]);