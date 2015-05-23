var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

var traqplugin = {

    startReception: function (successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'RECEIVE_SMS', []);
    },

    stopReception: function (successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'STOP_RECEIVE_SMS', []);
    },

    getImei: function (successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'IMEI', []);
    },

    getString: function (key, successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'GET_STRING', [key]);
    },

    setString: function (key, value, successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'SET_STRING', [key, value]);
    },

    removeKey: function (key, successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'REMOVE', [key]);
    },

    clearAll: function (successCallback, failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'CLEAR', []);
    }

};

module.exports = traqplugin;