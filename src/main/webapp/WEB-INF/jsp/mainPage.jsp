<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <title>PC - TP 2023</title>
            <style>
                .username.ng-valid {
                    background-color: lightgreen;
                }

                .username.ng-dirty.ng-invalid-required {
                    background-color: red;
                }

                .username.ng-dirty.ng-invalid-minlength {
                    background-color: yellow;
                }

                .email.ng-valid {
                    background-color: lightgreen;
                }

                .email.ng-dirty.ng-invalid-required {
                    background-color: red;
                }

                .email.ng-dirty.ng-invalid-email {
                    background-color: yellow;
                }
            </style>
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
            <link href="<c:url value='css/app.css' />" rel="stylesheet">
            </link>
        </head>

        <body ng-app="myApp" class="ng-cloak">
            <div class="generic-container" ng-controller="MainController as ctrl">

                <div class="panel panel-default">
                    <div class="panel-heading"><span class="lead">PROGRAMACION CONCURRENTE - TP FINAL 2023</span></div>
                    <div class="formcontainer">
                        <form name="myForm" class="form-horizontal">
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <label class="col-md-2 control-lable" for="file">Sequential Search</label>
                                    <div class="col-md-7">
                                        <input type="text" ng-model="ctrl.seq_keyword" name="seq_keyword"
                                            class="username form-control input-sm" placeholder="Ingresar palabra clave"
                                            required ng-minlength="2" />
                                    </div>
                                    <input type="submit" value="Search" class="btn btn-primary btn-sm"
                                        ng-disabled="myForm.seq_keyword.$invalid" ng-click="ctrl.seq_submit()">
                                    <button type="button" ng-click="ctrl.seq_reset()" class="btn btn-warning btn-sm"
                                        ng-disabled="myForm.seq_keyword.$pristine">Reset</button>
                                </div>
                                <div class="form-group col-md-12">
                                    <label class="col-md-2 control-lable" for="file">In Parallel Search</label>
                                    <div class="col-md-7">
                                        <input type="text" ng-model="ctrl.par_keyword" name="par_keyword"
                                            class="username form-control input-sm" placeholder="Ingresar palabra clave"
                                            required ng-minlength="2" />
                                    </div>
                                    <input type="submit" value="Search" class="btn btn-primary btn-sm"
                                        ng-disabled="myForm.par_keyword.$invalid" ng-click="ctrl.par_submit()">
                                    <button type="button" ng-click="ctrl.par_reset()" class="btn btn-warning btn-sm"
                                        ng-disabled="myForm.par_keyword.$pristine">Reset</button>
                                </div>
                        </form>
                    </div>
                </div>

                <div class="">
                    <div class="tablecontainer">
                        <div class="col-xs-6">
                            <h2 class="sub-header">Sequential Search</h2>

                            <div>
                                <ul style="font-size: initial;">
                                    <li><b>Thread name:</b> {{ctrl.seq_result.workersResult[0].workerId}}</li>
                                    <li><b>Thread search time:</b> {{ctrl.seq_result.workersResult[0].searchTime}} sec.
                                    </li>
                                    <li><b>Total search time:</b> {{ctrl.seq_result.totalSearchTime}} sec.</li>
                                </ul>
                            </div>

                            <div class="table-responsive">
                                <label for="table10minus">Files -10 Result</label>
                                <table name="table10minus" class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th class="col-md-1">File Name</th>
                                            <th class="col-md-2">Keyword Count</th>
                                            <th class="col-md-3">Time (Sec.)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="r in ctrl.seq_result.workersResult[0].filesSearch">
                                            <td class="col-md-1"><span ng-bind="r.documentName"></span></td>
                                            <td class="col-md-2"><span ng-bind="r.ocurrencies"></span></td>
                                            <td class="col-md-3"><span ng-bind="r.searchTime.toFixed(5)"></span></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <label for="table10plus">Files 10+ Result</label>
                                <table name="table10plus" class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th class="col-md-1">File Name</th>
                                            <th class="col-md-2">Keyword Count</th>
                                            <th class="col-md-3">Time (Sec.)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="r in ctrl.seq_result.workersResult[0].filesSearch10plus">
                                            <td class="col-md-1"><span ng-bind="r.documentName"></span></td>
                                            <td class="col-md-2"><span ng-bind="r.ocurrencies"></span></td>
                                            <td class="col-md-3"><span ng-bind="r.searchTime.toFixed(5)"></span></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="col-xs-6">
                            <h2 class="sub-header">Parallel Search</h2>

                            <div>
                                <ul style="font-size: initial;">
                                    <li><b>Total search time:</b> {{ctrl.par_result.totalSearchTime}} sec.</li>
                                </ul>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-striped table-datatable table-hover table-border-bottom">
                                    <thead>
                                        <tr>
                                            <th class="col-md-1">Worker Name</th>
                                            <th class="col-md-2">Search Time</th>
                                            <th class="col-md-1"></th>
                                            <th class="col-md-3">File Name</th>
                                            <th class="col-md-1">Keyword Count</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat-start="w in ctrl.par_result.workersResult">
                                            <td class="col-md-1"><span ng-bind="w.workerId"></span></td>
                                            <td class="col-md-2"><span ng-bind="w.searchTime"></span></td>
                                            <td>&nbsp;</td>
                                            <td colspan="2">&nbsp;</td>
                                        </tr>
                                        <tr ng-repeat="f in w.filesSearch">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td class="col-md-3"><span ng-bind="f.documentName"></span></td>
                                            <td class="col-md-1"><span ng-bind="f.ocurrencies"></span></td>

                                        </tr>
                                        <tr ng-show="w.filesSearch10plus.length > 0">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3"><strong>Files 10+ Result</strong></td>
                                        </tr>
                                        <tr ng-repeat="f in w.filesSearch10plus">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td class="col-md-3"><span ng-bind="f.documentName"></span></td>
                                            <td class="col-md-1"><span ng-bind="f.ocurrencies"></span></td>

                                        </tr>
                                        <tr ng-repeat-end>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">&nbsp;</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
                    <script src="<c:url value='js/app.js' />"></script>
                    <script src="<c:url value='js/service/main_service.js' />"></script>
                    <script src="<c:url value='js/controller/main_controller.js' />"></script>
        </body>

        </html>