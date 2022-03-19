define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        commonPageBridge({ 
            name: 'sureness.safety.main', 
            params: {} 
        });
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});