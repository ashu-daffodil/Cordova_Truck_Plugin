var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

var smsplugin = {

    startReception:function(successCallback,failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'RECEIVE_SMS', []);
    },

    stopReception:function(successCallback,failureCallback) {
        exec(successCallback, failureCallback, 'TraqPlugin', 'STOP_RECEIVE_SMS', []);
    }
};

module.exports=smsplugin;