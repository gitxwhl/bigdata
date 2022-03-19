define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        commonPageBridge({ 
            name: 'InformationLinkage.InformationLinkage',
            params: {} 
        });
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});