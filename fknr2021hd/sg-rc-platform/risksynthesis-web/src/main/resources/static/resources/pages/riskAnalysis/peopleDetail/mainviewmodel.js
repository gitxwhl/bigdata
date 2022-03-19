define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		$('.content-bg').scrollTop(0);
		var self = this;
		self.page = 'list';
		self.title = '人员信息 ';
		self.type = ''
		var fzjb = {
            "01":'一般违章',
            "02":'严重违章',
            "03":'红线禁令',
		}
		var xjdw = {
			"01":"省公司级",
			"02":"市公司级",
			"03":"区县级",
			"04":"班组级",
		}
		var xingb = {
			"01":"男",
			"02":"女"
		}
		var biaoshi = {
			"01":"工作负责人",
			"02":"工作许可证",
			"03":"工作票签发人"
		}
		var zylx = {
			"01": "输电",
			"02": "变电",
			"03": "配电",
			"04": "其他",
		}
		var sanzr = {
			"01": "工作负责人",
			"02": "工作许可人",
			"03": "工作票签发人"
		}
		setTimeout(function(){
			if(params.type){
				self.type(params.type)
				if(params.type == 'renyuan'){
					self.title('人员信息 ')
				}else if(params.type == 'hmd'){
					self.title('黑名单、负面清单 ')
				}else if(params.type == 'kaoshi'){
					self.title('安全考试 ')
				}
			}
			
			if(params.id){
				getOrgids(params.id)
				getExamination(params.id)
				getDatareport(params.id)
				getViolationfile(params.id)
			}
		})
		self.style ="background:#cccccc;";
		self.isShowRowNumbers = true;
		self.isShowRowNumber = false;
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
		self.pageSize = 10;

        self.personnelName = ko.observable('');//姓名
		self.personnelPhone = ko.observable('');//手机号
		self.personnelCard = ko.observable('');//身份证号
		self.personnelSex = ko.observable('');//性别
		self.personnelHead = ko.observable('');//工作负责人
		self.personnelUnit = ko.observable('');//单位名称
		self.personnelAge = ko.observable('');//年龄
		self.personnelType = ko.observable('');//专业类型

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
		function getSrpWorkViolationfile (siteinfo_id,basicinfo_creditcode) {
            // console.log(siteinfo_id,basicinfo_creditcode)
            axios.get(cube.safetygatewayURL + 'enterprisesAdmittance/getSrpWorkViolationfile/'+siteinfo_id).then(function(res) {
                if (res.data.successful && res.data.resultValue.length) {
                    var data = res.data.resultValue[0];
                    
                   self.ViolationUnit(data['violation_org']);//违章单位
                   self.ViolationTime(data['violation_date']);//违章时间
                   self.natureUnit(data['violation_nature']);//单位性质
                   self.ViolationGrade(data['violation_level']);//违章级别
                   self.InspectionUnit(data['inspection_org']);//巡检单位
                   self.ISshutdown(data['reform_states']);//停工状态
                   self.ViolationPersonnel(data['violation_staff']);//违章人员
                   self.ViolationNature(data['violation_nature']);//违章性质
                   self.ViolationContents(data['violation_content']);//违章内容
                   self.ViolationPlace(data['work_place']);//违章地点
                   self.penalizeContent(data['penalty_amount']);//处罚内容

                }

            }).finally(
                function() {
                    cube.showLoading(false);
                }
            );
        }
		//人员信息查看
		function getOrgids (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getOrgids?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful && res.data.resultValue.length) {
					var data = res.data.resultValue[0];
					// console.log(data)
					self.personnelName(data['name']);//姓名
					self.personnelPhone(data['contact']);//手机号
					self.personnelCard(data['id_card']);//身份证号
					self.personnelSex(xingb[data['sex']]);//性别
					self.personnelHead(biaoshi[data['threekinds_identification']]);//三种人标识
					self.personnelUnit(data['org_name']);//单位名称
					self.personnelAge(data['age']);//年龄
					self.personnelType(zylx[data['profession']]);//专业类型
                }

            }).finally(
                function() {
                    cube.showLoading(false);
                }
            );
		}
		self.TestRecords=function (flag) {
			$(".TestRecords").css("background-color", "#1B6B9D");
			$(".EnterpriseRecords").css("background-color", "#284BA3");
			$(".endorsement").css("background-color", "#284BA3");
			$("#EnterpriseRecords").hide();
			$("#endorsement").hide();
			$("#TestRecords").show();
		}
		self.EnterpriseRecords=function (flag) {
			$(".EnterpriseRecords").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".endorsement").css("background-color", "#284BA3");
			$("#EnterpriseRecords").show();
			$("#endorsement").hide();
			$("#TestRecords").hide();
		}
		self.endorsement=function (flag) {
			$(".endorsement").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".EnterpriseRecords").css("background-color", "#284BA3");
			$("#EnterpriseRecords").hide();
			$("#endorsement").show();
			$("#TestRecords").hide();
		}

		//考试记录
		self.examination = [
			{name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "test_name", width:"15%", readOnly:true, caption: "考试名称" , editorType: "TextEditor",align : "center",},
			{name: "apply_post", width:"15%", readOnly:true, caption: "报考岗位" , editorType: "TextEditor",align : "center",},
			{name: "apply_specialty", width:"10%", caption: "报考专业" , editorType: "TextEditor",align : "center",},
			{name: "org_name", width:"15%", caption: "所在单位" , editorType: "TextEditor",align : "center",},
			{name: "datareport_org", width:"15%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
			{name: "datareport_org", width:"15%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
			{name: "test_date", width:"10%",caption: "考试时间" , editorType: "TextEditor",align : "center",},
			{name: "test_score", width:"10%", caption: "考试成绩" , editorType: "TextEditor",align : "center",},
			// {name: "major", width:"107px", caption: "个人积分" , editorType: "TextEditor"},
			{name: "validity_period", width:"10%", caption: "有效时间" , editorType: "TextEditor",align : "center",},
		];
		//考试记录
		function getExamination (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getExamination?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful) {
					self.examinationContent(res.data.resultValue);
                }

            }).finally(
                function() {
                    cube.showLoading(false);
                }
            );
        }
		self.examinationContent=ko.observableArray([])

		//企业变更记录
		self.enterprise = [
			{name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
			{name: "update_time", width:"20%", readOnly:true, caption: "变更日期" , editorType: "TextEditor",align : "center",},
			{name: "datareport_org", width:"15%", readOnly:true, caption: "所在单位" , editorType: "TextEditor",align : "center",},
		];
		//企业变更记录
		function getDatareport (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getDatareport?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful) {
					self.enterpriseContent(res.data.resultValue);
                }

            }).finally(
                function() {
                    cube.showLoading(false);
                }
            );
        }
		self.enterpriseContent=ko.observableArray([])

		//违章记录
		self.unprofessional = [
			{name: "violation_org_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "violation_level", width:"20%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					return fzjb[cellValue]
				}
			},
			{name: "violation_clause_description", width:"15%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					element.style.color = "#0ec4da";
				},
				onClick: function (pkValue, cellValue) {
					self.page('ViolationDetails')
					getSrpWorkViolationfile(pkValue)
				}
			},
			{name: "violation_org", width:"10%", caption: "违章单位" , editorType: "TextEditor",align : "center",},
			{name: "create_time", width:"10%", caption: "违章时间" , editorType: "TextEditor",align : "center",},
			{name: "check_level", width:"15%", caption: "巡检单位" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					return xjdw[cellValue]
				}
			},
		];
		//违章记录
		function getViolationfile (id) {//RYBJCYDWID001
            axios.get(cube.safetygatewayURL + `personnelInformation/getViolationfile?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful && res.data.resultValue.length) {
					self.unprofessionalContent(res.data.resultValue);
                }

            }).finally(
                function() {
                    cube.showLoading(false);
                }
            );
        }
		self.unprofessionalContent=ko.observableArray([])
		self.goBack = function(){
			var type = params.type;
			if(type == 'renyuan'){
				commonPageBridge({ 
					name: 'riskAnalysis.riskAnalysis', 
					params: {} 
				});
			}else if(type == 'hmd'){
				commonPageBridge({ 
					name: 'blacklist.information.main', 
					params: {siteinfo_id:params.siteinfo_id,basicinfo_creditcode:params.basicinfo_creditcode} 
				});
			}else if(type == 'kaoshi'){
				commonPageBridge({ 
					name: 'sureness.details.main', 
					params: {testroom_no: params.testroom_no} 
				});
			}
		}
		self.refresh = function(){
			self.page('list')
		}
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});