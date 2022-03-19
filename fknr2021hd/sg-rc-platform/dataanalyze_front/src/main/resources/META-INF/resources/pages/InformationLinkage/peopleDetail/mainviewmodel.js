define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.content = '数据加载中';
		self.isShow = ko.observable("")
		// self.id=params.item
		self.id=ko.observable(params.item)
		// console.log(params.item)
		self.Name=''//姓名
		self.phone=''//手机
		self.IDnumber=''//身份证号
		self.gender=''//性别
		self.TheirIdentity=''//所属身份
		self.PlaceUnit=''//所在单位
		self.age=''//所在单位
		self.style ="background:#cccccc;";
		self.isShowRowNumbers = true;
		self.isShowRowNumber = true;
		self.isShowCheckBox = true;
		self.isShowCheckBoxs = false;
		self.isShowBorder = true;
		self.isShowStripe = false;
		self.isShowHover = true;
		self.isShowCondense = false;
		self.isAllowEdit = false;
		self.isAllowDelete = false;
		self.isAllowOperations = false;
		self.isAllowShift = false;
		self.isAllowSort = false;
		self.isAllowAppend = false;
		self.isAllowPaging = false;
		self.primaryKey="WORKER_ID"
		self.primaryKey2="VIOLATION_STAFF_ID"
		self.pageSize = 10;
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
		self.examination = [
			{name: "WORKER_ID",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "CREATE_TIME", width:"15%", readOnly:true, caption: "日期" , editorType: "TextEditor",align : "center",},
			{name: "PROJECT", width:"15%", readOnly:true, caption: "单位名称" , editorType: "TextEditor",align : "center",},
			];
		self.examinationContent=[]
		self.endorsement2 = [
			{name: "VIOLATION_STAFF_ID",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_LEVEL", width:"15%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_CONTENT", width:"15%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_ORG", width:"10%", caption: "违章单位" , editorType: "TextEditor",align : "center",},
			{name: "VIOLATION_DATE", width:"15%", caption: "违章时间" , editorType: "TextEditor",align : "center",},
			{name: "CHECK_LEVEL", width:"15%", caption: "巡检单位" , editorType: "TextEditor",align : "center",},

		];
		self.endorsementContent=[]
		self.admittance2 = [
			// {name: "",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "DATAREPORT_ORG", width:"15%", readOnly:true, caption: "所属省份" , editorType: "TextEditor",align : "center",},
			{name: "ACCEPT_ORG", width:"15%", readOnly:true, caption: "报备单位" , editorType: "TextEditor",align : "center",},
			{name: "BASICINFO_CONTRACTOR", width:"10%", caption: "单位名称" , editorType: "TextEditor",align : "center",},
			{name: "accessPeriod", width:"15%", caption: "准入期限" , editorType:"TextEditor" ,align : "center",},
			{name: "ACCESS_STATE", width:"15%", caption: "准入状态" , editorType: "TextEditor",align : "center",},
			{name: "BREACH_FAITH", width:"15%", caption: "是否失信" , editorType: "TextEditor",align : "center",},
		];
		self.admittanceContent=[]

		self.admittance=function () {
			$(".admittance").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$("#admittances").show();
			$("#endorsements").hide();
			$("#TestRecords").hide();
			$("#zhunru").show();
			$("#danwei").hide();
			$("#weizhang").hide();
			getRiskWorkerAccessList()
		}
		self.TestRecords=function () {
			$(".TestRecords").css("background-color", "#1B6B9D");
			$(".endorsement").css("background-color", "#284BA3");
			$(".admittance").css("background-color", "#284BA3");
			$("#TestRecords").show();
			$("#endorsements").hide();
			$("#admittances").hide();
			$("#danwei").show();
			$("#zhunru").hide();
			$("#weizhang").hide();
			srpRiskWorkerUnitChangeList()
		}

		self.endorsement=function () {
			$(".endorsement").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".admittance").css("background-color", "#284BA3");
			$("#endorsements").show();
			$("#admittances").hide();
			$("#TestRecords").hide();
			$("#danwei").hide();
			$("#zhunru").hide();
			$("#weizhang").show();
			srpRiskWorkerViolationPointsList()
		}
		$('.goblack').click(function(){
			commonPageBridge({ 
				name: 'InformationLinkage.InformationLinkage', 
				params: {} 
			});
        })
		
		var params = {"workerId":self.id()}
		axios.post(cube.gatewayURL2 + "personnelInformation/getWorkerInfo",params).then(function(res) {
			if (res.data.successful) {
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
		getRiskWorkerAccessList()
		function getRiskWorkerAccessList() {
			var params1 = {
				"page":	self.pageIndex(),
				"size":10,
				"params":{"workerId":self.id()}
			}
			self.isShow(true)
			//准入情况
			axios.post(cube.gatewayURL2 + "personnelInformation/getRiskWorkerAccessList",params1).then(function(res) {
				if (res.data.successful) {
					var risklist4=res.data.resultValue.content
					// console.log(risklist4)
					self.count(res.data.resultValue.totalSize);
					self.pageTotalCount(res.data.resultValue.totalPage);

					self.admittanceContent(risklist4);

				} else {
					cube.indicate("数据加载失败");
				}
				self.isShow(false)
			})
		}

		//人员单位变更记录列表查询
		function srpRiskWorkerUnitChangeList() {
			var params2 = {
				"page":	self.pageIndex2(),
				"size":10,
				"params":{"workerId":self.id()}
			}
			self.isShow(true)
			axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerUnitChangeList",params2).then(function(res) {
				if (res.data.successful) {
					var risklist=res.data.resultValue.content
					// console.log(risklist)
					// self.count(res.data.resultValue.totalSize);
					// self.pageTotalCount(res.data.resultValue.totalPage);
					self.count2(res.data.resultValue.totalSize);
					self.pageTotalCount2(res.data.resultValue.totalPage);
					self.examinationContent(risklist);

				} else {
					cube.indicate("数据加载失败");
				}
				self.isShow(false)
			})
		}

		//人员违章记录列表查询
		function srpRiskWorkerViolationPointsList() {
			var params3 = {
				"page":	self.pageIndex3(),
				"size":10,
				"params":{"workerId":self.id()}
			}
			self.isShow(true)
			axios.post(cube.gatewayURL2 + "personnelInformation/srpRiskWorkerViolationPointsList",params3).then(function(res) {
				if (res.data.successful) {
					var risklist=res.data.resultValue.content
					// console.log(risklist)
					// self.count(res.data.resultValue.totalSize);
					// self.pageTotalCount(res.data.resultValue.totalPage);
					self.count3(res.data.resultValue.totalSize);
					self.pageTotalCount3(res.data.resultValue.totalPage);
					self.endorsementContent(risklist);

				} else {
					cube.indicate("数据加载失败");
				}
				self.isShow(false)

			})
		}

		cube.endViewModel(self, params);
	};
	return PageViewModel;
});