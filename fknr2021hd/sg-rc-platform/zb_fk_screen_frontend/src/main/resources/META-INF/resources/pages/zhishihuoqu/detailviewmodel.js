define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        setTimeout(function(){
			if(params.item){
				var data = params.item;
				self.name(data.name);
				self.nodeName(data.nodeName);
				self.attribute(data.attribute);
            }
		})
		self.releaseUnit =[
            {id:'',text:'全部'},
        ];
        self.Unit = ko.observable("")
		self.gbClick = function(){
            params.dialog.closeDialog();
		}
        self.name = '';
        self.nodeName = '';
        self.attribute = '';
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});