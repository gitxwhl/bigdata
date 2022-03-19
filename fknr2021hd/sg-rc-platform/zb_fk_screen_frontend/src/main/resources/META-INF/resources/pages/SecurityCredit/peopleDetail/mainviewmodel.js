define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.id=params.item
		// console.log(params.item)
		self.Name=''//姓名
		self.phone=''//手机
		self.IDnumber=''//身份证号
		self.gender=''//性别
		self.TheirIdentity=''//所属身份
		self.PlaceUnit=ko.observable("")//所在单位
		self.age=''//所在单位
		self.style ="background:#cccccc;";

		self.size = 'middle';
		self.count ='1';
		self.pageTotalCount = '1';
		self.pageIndex = ko.observable(1);
		self.pageVisibleCount =ko.observable(1);
		self.pageNo = ko.observable(1);

		self.count2 ='1';
		self.pageTotalCount2 = '1';
		self.pageIndex2 = ko.observable(1);
		self.pageVisibleCount2 =ko.observable(1);
		self.pageNo2 = ko.observable(1);

		self.count3 ='1';
		self.pageTotalCount3 = '1';
		self.pageIndex3 = ko.observable(1);
		self.pageVisibleCount3 =ko.observable(1);
		self.pageNo3 = ko.observable(1);
		// self.onPageIndexChanged = function(p_pageIndex) {
		// 	if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
		// 		self.pageIndex(p_pageIndex);
		// 	}
		// }
		self.pageSize = 10;
		self.examination = [
			// {name: "SAFELEARNING_ID",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "SAFELEARNING_NAME", width:"15%", readOnly:true, caption: "培训名称" , editorType: "TextEditor",align : "center",},
			{name: "SAFELEARNING_CONTENT", width:"15%", readOnly:true, caption: "培训内容" , editorType: "TextEditor",align : "center",},
			{name: "SAFELEARNING_DATE", width:"15%", readOnly:true, caption: "培训时间" , editorType: "TextEditor",align : "center",},
			{name: "SAFELEARNING_RESULT", width:"15%", readOnly:true, caption: "培训结果" , editorType: "TextEditor",align : "center",},
			];
		self.examinationContent=[]
		self.endorsement2 = [
			// {name: "SAFETYTEST_ID",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "TEST_NAME", width:"15%", readOnly:true, caption: "考试名称" , editorType: "TextEditor",align : "center",},
			{name: "TEST_SCORE", width:"15%", readOnly:true, caption: "考试成绩" , editorType: "TextEditor",align : "center",},
			{name: "WHETHER_PASS", width:"10%", caption: "是否通过" , editorType: "TextEditor",align : "center",},

		];
		self.endorsementContent2=[]
		self.recordend = [
			// {name: "VIOLATIONFILE_ID",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_LEVEL", width:"15%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_CONTENT", width:"15%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_ORG", width:"10%", caption: "违章单位" , editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_DATE", width:"15%", caption: "违章时间" , editorType: "TextEditor",align : "center",},
			{name: "CHECK_LEVEL", width:"15%", caption: "巡检单位" , editorType: "TextEditor",align : "center",},
			{name: "PENALTY_STAFF_POINTS", width:"15%", caption: "积分" , editorType: "TextEditor",align : "center",},

		];
		self.recordContent=[]

		self.TestRecords=function () {
			$(".TestRecords").css("background-color", "#1B6B9D");
			$(".endorsement").css("background-color", "#284BA3");
			$(".record").css("background-color", "#284BA3");
			$("#TestRecords").show();
			$("#endorsements").hide();
			$("#record").hide();
		}

		self.endorsement=function () {
			// console.log(222222222222)
			$(".endorsement").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".record").css("background-color", "#284BA3");
			$("#endorsements").show();
			$("#TestRecords").hide();
			$("#record").hide();
		}
		self.record=function () {
			// console.log(222222222222)
			$(".record").css("background-color", "#1B6B9D");
			$(".endorsement").css("background-color", "#284BA3");
			$(".TestRecords").css("background-color", "#284BA3");
			$("#endorsements").hide();
			$("#TestRecords").hide();
			$("#record").show();
		}
		var paramss = {

			"workerId":self.id

		}
		var params = {
			"page":self.pageIndex(),
			"size":10,
			"params": {
				// "name":'',
				// "orgName":'',
				// "datareportOrgId":'',
				"workerId":self.id
			}
		}

		axios.post(cube.gatewayURL2 + "personnelInformation/getWorkerInfo",paramss).then(function(res) {
			if (true) {
				var risklist=res.data.resultValue
				// console.log(risklist)
				self.Name(risklist[0].NAME)//姓名
				self.phone(risklist[0].CONTACT)//手机
				self.IDnumber(risklist[0].ID_CARD)//身份证号
				self.gender(risklist[0].SEX)//性别
				self.TheirIdentity(risklist[0].DATAREPORT_ORG)//所属身份
				self.PlaceUnit(risklist[0].ORG_NAME)//所在单位
				self.age(risklist[0].AGE)//年龄
			} else {
				cube.indicate("数据加载失败");
			}

		})
		$('.goblack').click(function(){
			commonPageBridge({ 
				name: 'SecurityCredit.SecurityCredit', 
				params: {} 
			});
        })
		//人员培训记录列表查询
		var params= {
			"page":self.pageIndex(),
			"size":10,
			"params": {
				"workerId":self.id
			}
		}
		axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerSafeLearnList",params).then(function(res) {
			if (true) {
				var risklist=res.data.resultValue.content
				// console.log(risklist)
				self.count(res.data.resultValue.totalSize);
				self.pageTotalCount(res.data.resultValue.totalPage);
				self.examinationContent(risklist);

			} else {
				cube.indicate("数据加载失败");
			}

		})
		//人员考试记录列表查询
		var params2 = {
			"page":self.pageIndex2(),
			"size":10,
			"params": {
				"workerId":self.id
			}
		}
		axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerSafetyTestList",params2).then(function(res) {
			if (true) {
				var risklist=res.data.resultValue.content
				// console.log(risklist)
				self.count2(res.data.resultValue.totalSize);
				self.pageTotalCount2(res.data.resultValue.totalPage);
				self.endorsementContent2(risklist);

			} else {
				cube.indicate("数据加载失败");
			}

		})
		//人员违章记录列表查询
		var params3 = {
			"page":self.pageIndex3(),
			"size":10,
			"params": {
				"workerId":self.id
			}
		}
		axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerViolationPointsList",params3).then(function(res) {
			if (true) {
				var risklist=res.data.resultValue.content
				// console.log(risklist)
				self.count3(res.data.resultValue.totalSize);
				self.pageTotalCount3(res.data.resultValue.totalPage);
				self.recordContent(risklist);

			} else {
				cube.indicate("数据加载失败");
			}

		})
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});