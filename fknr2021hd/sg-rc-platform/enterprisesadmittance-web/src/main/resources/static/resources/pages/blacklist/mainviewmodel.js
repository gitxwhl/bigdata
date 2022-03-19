define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        commonPageBridge({ 
            name: 'blacklist.blacklist', 
            params: {} 
        });
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});