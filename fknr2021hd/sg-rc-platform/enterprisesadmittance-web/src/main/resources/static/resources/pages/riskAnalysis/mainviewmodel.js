define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        commonPageBridge({ 
            name: 'riskAnalysis.riskAnalysis', 
            params: {} 
        });
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});