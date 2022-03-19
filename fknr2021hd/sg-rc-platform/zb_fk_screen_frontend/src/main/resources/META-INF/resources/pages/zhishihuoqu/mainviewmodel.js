define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
		self.url = 'fusion.associated'
        $('.outLi').click(function(){
            $(this).addClass('outAtive').siblings().removeClass('outAtive')
		})
		self.menu = function (e){
			self.url(e)
		}
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});