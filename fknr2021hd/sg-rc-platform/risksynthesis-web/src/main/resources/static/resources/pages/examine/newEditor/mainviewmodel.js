define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
        self.compayNature = '';
        self.titleTypeContent=ko.observableArray();
        self.titleType = '';
        self.answerContent=ko.observableArray();
        self.answer = '';
        self.ktnr = '';
        self.ktxx = '';
        self.gbClick = function(){
            params.dialog.closeDialog();
        }
        if(params.item){
            self.ktnr = params.item.ktnr;
        }
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});