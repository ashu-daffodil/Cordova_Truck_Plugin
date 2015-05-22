var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

module.exports = {

    startReception:function(successCallback,failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'RECEIVE_SMS', []);
    },

    stopReception:function(successCallback,failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'STOP_RECEIVE_SMS', []);
    },

    getImei:function(successCallback,failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'IMEI', []);
    }
};

