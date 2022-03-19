// define(["RESTClient"], function(RestClient) {
// 	var PageViewModel = function(params) {
// 		var self = this;
// 		console.log(params.items)
// 		self.url = 'outsourcing.details.info'
//         $('.outLi').click(function(){
define(["RESTClient"], function (RestClient) {
    var PageViewModel = function (params) {
        var self = this;
        $('.goblack').click(function(){
			commonPageBridge({ 
				name: 'outsourcing.home.main', 
				params: {} 
			});
        })
        self.url = 'outsourcing.details.info'
        self.item = cube.obj();
        // console.log(params)
        setTimeout(function () {
            self.item({ item: params.items })
        })
        $('.outLi').click(function () {
            $(this).addClass('outAtive').siblings().removeClass('outAtive')
		})
		self.menu = function (e){
			self.url(e)
		}
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});