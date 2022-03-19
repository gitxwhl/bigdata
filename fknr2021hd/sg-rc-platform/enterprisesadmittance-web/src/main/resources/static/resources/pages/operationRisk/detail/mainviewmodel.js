define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
		var self = this;
		var url='http://s4.gcsoft.pub:20293/rmcp-web-front/pages/dist/zyxq.html?'
		self.src=url+params.item.work_plan_id
		// console.log(params.item.work_plan_id)
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});