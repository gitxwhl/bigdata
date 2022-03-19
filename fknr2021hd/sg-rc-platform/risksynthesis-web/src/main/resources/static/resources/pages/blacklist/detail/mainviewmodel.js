define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
		var self = this;
		var hmddddj = {
			"01": "Ⅰ级",
			"02": "Ⅱ级",
			"03": "Ⅲ级"
		}
		self.page = 0;
		setTimeout(function(){
			if(params.item && params.id == 0){
				self.page(params.id)
				var data = params.item;
				self.name(data.basicinfo_contractor);
				self.fddbr(data.legal_representative);
				self.fbdw(data.publish_org);
				self.hmddj(hmddddj[data.blacklist_level]);
				self.fbsj(data.report_date);
				self.jcsj(data.blacklistrel_date);
				self.tbr(data.report_person);
				self.tbsj(data.report_date);
				self.yy(data.inclusion_condition);
				self.fj(data.annex);
			}
			if(params.item && params.id == 1){
				self.page(params.id)
				var data = params.item;
				self.name(data.basicinfo_contractor);
				self.fddbr(data.legal_representative);
				self.fbdw(data.publish_org);
				self.fbsj(data.release_date);
				self.jcsj(data.negativelistrel_date);
				self.tbr(data.report_person);
				self.tbsj(data.report_date);
				self.yy(data.inclusion_condition);
				self.fj(data.annex);
			}
		})
		
        self.name = '';//企业名称
        self.fddbr = '';//法定代表人
        self.fbdw = '';//发包单位
        self.hmddj = '';//黑名单等级
        self.fbsj = '';//发布时间
        self.jcsj = '';//解除时间
        self.tbr = '';//填报人
        self.tbsj = '';//填报时间
        self.yy = '';//原因
        self.fj = '';//附件
        
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});