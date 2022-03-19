define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
		var self = this;
        self.id=params.item
        self.style ="background:#cccccc;";
        self.columns = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "DATAREPORT_ORG", width:"10%", caption: "所属省份" , editorType: "TextEditor",align : "center"},
            {name: "ACCEPT_ORG", width:"10%", caption: "报备单位" , editorType: "TextEditor",align : "center"},
            {name: "BASICINFO_CONTRACTOR", width:"10%", caption: "单位名称" , editorType: "TextEditor",align : "center" },
            {name: "accessPeriod", width:"10%", caption: "准入期限" , editorType: "TextEditor",align : "center" },
            {name: "ACCESS_STATE", width:"10%", caption: "准入状态" , editorType: "TextEditor",align : "center" },
            {name: "BREACH_FAITH", width:"10%", caption: "是否失信" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent = []
        self.columns1 = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "DATAREPORT_ORG", width:"10%", caption: "所属省份" , editorType: "TextEditor",align : "center"},
            {name: "ACCEPT_ORG", width:"10%", caption: "报备单位" , editorType: "TextEditor",align : "center"},
            {name: "VIOLATION_ORG", width:"10%", caption: "单位名称" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_LEVEL", width:"10%", caption: "违章级别" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_CONTENT", width:"10%", caption: "违章内容" , editorType: "TextEditor",align : "center" },
            {name: "VIOLATION_ORG_POINTS", width:"10%", caption: "积分" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent1 = []
        self.columns2 = [
            {name: "siteinfo_id", width:"30%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
			{name: "DATAREPORT_ORG", width:"10%", caption: "所属省份" , editorType: "TextEditor",align : "center"},
            {name: "PROJECT_NAME", width:"10%", caption: "项目名称" , editorType: "TextEditor",align : "center"},
            {name: "PROJECT_CODE", width:"10%", caption: "项目编号" , editorType: "TextEditor",align : "center" },
            {name: "cnt", width:"10%", caption: "事故数量" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENT_LEVEL", width:"10%", caption: "最高事故级别" , editorType: "TextEditor",align : "center" },
            {name: "ACCIDENT_HANDLING", width:"10%", caption: "事故处理情况" , editorType: "TextEditor",align : "center" }

        ];
        self.cityContent2 = []
        RiskList()
        function RiskList(searchParams) {
            var params = {"id":self.id }
            axios.post(cube.gatewayURL2 + "accidentInformation/networkInformation",params).then(function(res) {
                if (true) {
                    var list=res.data.resultValue.access
                    self.cityContent(list);

                    var list2=res.data.resultValue.illegalSituation
                    self.cityContent1(list2);

                    var list3=res.data.resultValue.accidentConditions
                    self.cityContent2(list3);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});