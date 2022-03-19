define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        commonPageBridge({ 
            name: 'outsourcing.home.main', 
            params: {} 
        });
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});