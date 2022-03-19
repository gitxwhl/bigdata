define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        commonPageBridge({ 
            name: 'SecurityCredit.SecurityCredit',
            params: {} 
        });
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});