define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
		self.size = 'middle';
 				self.showArrow = true;
 				self.showTotal = true;
 				self.showGoto = true;
 				self.showAllPage = false;
 				self.pageTotalCount = 15;
 				self.pageIndex = 1;
 				self.pageVisibleCount = 5;
 				self.onPageIndexChanged = function(p_pageIndex){
							if(typeof(console) != "undefined" && typeof(console.log) != "undefined")
								console.log("当前选中："+p_pageIndex);
						}
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});