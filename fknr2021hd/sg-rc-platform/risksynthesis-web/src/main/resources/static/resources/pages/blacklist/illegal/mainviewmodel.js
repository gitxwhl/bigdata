define(["RESTClient"], function(RestClient) {
	var PageViewModel = function(params) {
        var self = this;
        var fzjb = {
            "01":'一般违章',
            "02":'严重违章',
            "03":'红线禁令',
        }
        var wzxz = {
            "01":'行为违章',
            "02":'管理违章',
            "03":'装置违章',
        }
		self.refresh = function(){
			commonPageBridge({ 
				name: 'blacklist.information.main', 
				params: {siteinfo_id:params.siteinfo_id,basicinfo_creditcode:params.basicinfo_creditcode} 
			});
        }
        setTimeout(function(){
                self.ViolationUnit(params.wzxqData['violation_org']);//违章单位
                self.ViolationTime(params.wzxqData['violation_date']);//违章时间
                self.natureUnit(params.wzxqData['violation_nature']);//单位性质
                self.ViolationGrade(params.wzxqData['violation_level']);//违章级别
                self.InspectionUnit(params.wzxqData['inspection_org']);//巡检单位
                self.ISshutdown(params.wzxqData['reform_states']);//停工状态
                self.ViolationPersonnel(params.wzxqData['violation_staff']);//违章人员
                self.ViolationNature(params.wzxqData['violation_nature']);//违章性质
                self.ViolationContents(params.wzxqData['violation_content']);//违章内容
                self.ViolationPlace(params.wzxqData['work_place']);//违章地点
                self.penalizeContent(params.wzxqData['penalty_amount']);//处罚内容
        })
        self.ViolationUnit='';//违章单位
        self.ViolationTime='';//违章时间
        self.natureUnit='';//单位性质
        self.ViolationGrade='';//违章级别
        self.InspectionUnit='';//巡检单位
        self.ISshutdown='';//停工状态
        self.ViolationPersonnel='';//违章人员
        self.ViolationNature='';//违章性质
        self.ViolationContents='';//违章内容
        self.ViolationPlace='';//违章地点
        self.penalizeContent='';//处罚内容
        self.IntegralDetails='';//积分详情
		
		
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});