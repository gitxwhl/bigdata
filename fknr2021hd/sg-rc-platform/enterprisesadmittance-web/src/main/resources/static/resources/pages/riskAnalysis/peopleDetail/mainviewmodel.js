define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		$('.content-bg').scrollTop(0);
		var self = this;
		var arr = sessionStorage.getItem('arr');
		var array = JSON.parse(arr);
		// console.log(array)
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
				workeplan(params.id)
				getTmxAttachment(params.id)

			}
		})

		// 作业计划
		self.size4 = 'middle';
        self.showArrow4 = true;
        self.showTotal4 = true;
        self.showGoto4 = true;
        self.showAllPage4 = false;
        self.count4 =ko.observable('');
        self.pageTotalCount4= ko.observable('');
        self.pageIndex4 =ko.observable(1);
        self.pageVisibleCount4 = 10;
        self.onPageIndexChanged4 = function(p_pageIndex) {
            self.pageIndex4(p_pageIndex)
            JobLeader(province_code,siteinfo_id)
            
        }




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

            })
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

            })
		}
		self.TestRecords=function (flag) {
			$(".TestRecords").css("background-color", "#1B6B9D");
			$(".EnterpriseRecords").css("background-color", "#284BA3");
			$(".endorsement").css("background-color", "#284BA3");
			$(".productionplan").css("background-color", "#284BA3");
			$("#EnterpriseRecords").hide();
			$("#endorsement").hide();
			$('#productionplan').hide();
			$("#TestRecords").show();
			// getExamination()
		}
		self.EnterpriseRecords=function (flag) {
			$(".EnterpriseRecords").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".endorsement").css("background-color", "#284BA3");
			$(".productionplan").css("background-color", "#284BA3");
			$("#EnterpriseRecords").show();
			$("#endorsement").hide();
			$("#TestRecords").hide();
			$('#productionplan').hide();

			// getViolationfile()

		}
		self.endorsement=function (flag) {
			$(".endorsement").css("background-color", "#1B6B9D");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".EnterpriseRecords").css("background-color", "#284BA3");
			$(".productionplan").css("background-color", "#284BA3");
			$("#EnterpriseRecords").hide();
			$("#endorsement").show();
			$("#TestRecords").hide();
			$('#productionplan').hide();

			 // getDatareport(pkValue)
		}
		self.productionplan=function(){
			$(".endorsement").css("background-color", "#284BA3");
			$(".TestRecords").css("background-color", "#284BA3");
			$(".EnterpriseRecords").css("background-color", "#284BA3");
			$(".productionplan").css("background-color", "#1B6B9D");
			$("#EnterpriseRecords").hide();
			$("#endorsement").hide();
			$("#TestRecords").hide();
			$('#productionplan').show();

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
                // if (res.data.successful) {
				// 	self.examinationContent(res.data.resultValue);
				// }
				if (res.data.successful) {
					var nulldata=[]
					var data=res.data.resultValue
					if( data.length==0){
						self.examinationContent(nulldata)
						document.getElementById("kaoshi").style.display="block";

					}else  if( data.length!==0){
						document.getElementById("kaoshi").style.display="none";
						self.examinationContent(data)

					}
					// self.examinationContent(res.data.resultValue);
				}

            })
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
                // if (res.data.successful) {
				// 	self.enterpriseContent(res.data.resultValue);
                // }
				if (res.data.successful) {
					var nulldata=[]
					var data=res.data.resultValue
					if( data.length==0){
						self.enterpriseContent(nulldata)
						document.getElementById("biangeng").style.display="block";

					}else  if( data.length!==0){
						document.getElementById("biangeng").style.display="none";
						self.enterpriseContent(data)
					}
					// self.enterpriseContent(res.data.resultValue);
				}

            })
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
		self.imgsrc=ko.observable('');
		function getTmxAttachment(id){
			axios({
				url:cube.safetygatewayURL+`personnelInformation/getTmxAttachment?siteworkerinfoId=${id}`,
				method:'get',
			}).then(function(res){
				if(res.data.successful==true&&res.data.resultValue.length>0){
					var id=res.data.resultValue[0].document_id
					document.querySelector('#imgshow').style.display='block'
					document.querySelector('#divshow').style.display='none'
					self.imgsrc(`http://20.1.60.108:18080/rmcp-service-captain/api/uds/downFileByDocumentId?documentId=${id}`)
				}
			})
		}
		// 作业计划
		self.iframesrc=ko.observable('');
        self.epibolyplanContent=ko.observableArray([])
		self.production=[
			{name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "work_plan_name", width:"14.2%", readOnly:true, caption: "作业计划名称" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
					console.log(pkValue)
                    self.page('jump')
                    self.iframesrc(`http://20.1.60.108:18080/rmcp-web-workrisk/pages/dist/zyxq2.html?id=${pkValue}`)
                },
            },
            {name: "work_state", width:"14.2%", readOnly:true, caption: "执行状态" , editorType: "TextEditor",align : "center",
             renderCell: function (cellValue, element, dictValue) {
                 if(cellValue=='01'){
                    return '未开工' 
                 }else if(cellValue=='02'){
                    return '已开工' 
                 }else if(cellValue=='03'){
                    return '未开工' 
                 }else if(cellValue=='04'){
                    return '计划延期' 
                 }else if(cellValue=='05'){
                    return '已完工' 
                 }else if(cellValue=='06'){
                    return '计划取消' 
                 }
                },
        },
            {name: "substation_name", width:"14.2%", caption: "变电站/线路名称" , editorType: "TextEditor",align : "center",},
            {name: "work_type", width:"14.2%", caption: "专业类型" , editorType: "TextEditor",align : "center",
            renderCell: function (cellValue, element, dictValue) {
                if(cellValue=='01'){
                   return '生产检修改造' 
                }else if(cellValue=='02'){
                   return '输变电工程' 
                }else if(cellValue=='03'){
                   return '配农网工程' 
                }else if(cellValue=='04'){
                   return '装表接点' 
                }else if(cellValue=='05'){
                   return '业扩工程' 
                }else if(cellValue=='06'){
                   return '迁改工程' 
                }else if(cellValue=='07'){
                    return '网络信息作业' 
                 }else if(cellValue=='08'){
                    return '通信检修施工' 
                 }else if(cellValue=='09'){
                    return '外部工程' 
                 }else if(cellValue=='10'){
                    return '发电检修改造' 
                 }else if(cellValue=='11'){
                    return '发电基建工程' 
                 }else if(cellValue=='12'){
                    return '综合能源项目' 
                 }else if(cellValue=='13'){
                    return '设备租赁项目' 
                 }else if(cellValue=='14'){
                    return '电工制造施工总承包项目' 
                 }else if(cellValue=='15'){
                    return '充电桩工程' 
                 }else if(cellValue=='16'){
                    return '小型基建工程' 
                 }else if(cellValue=='17'){
                    return '其他' 
                 }
               },
        },
            // {name: "skill", width:"150px", caption: "当前技能" , editorType: "TextEditor"},
            {name: "workrisk_level", width:"14.2%", caption: "作业风险等级" , editorType: "TextEditor",align : "center",
            renderCell: function (cellValue, element, dictValue) {
                if(cellValue=='01'){
                   return '一' 
                }else if(cellValue=='02'){
                   return '二' 
                }else if(cellValue=='03'){
                   return '三' 
                }else if(cellValue=='04'){
                   return '四' 
                }else if(cellValue=='05'){
                   return '五' 
                }else if(cellValue=='06'){
                   return '无' 
                }
               },
        },
            {name: "begin_time", width:"14.2%", caption: "计划开始时间" , editorType: "TextEditor",align : "center",},
            {name: "end_time", width:"14.2%", caption: "计划结束时间" , editorType: "TextEditor",align : "center",},
		]
		self. goreturn2=function(){
            self.page('list2')
		}
		// 作业计划
		function  workeplan(siteinfo_id) {//RYBJCYDWID001
			axios({
				url:cube.safetygatewayURL+'personnelInformation/getSrpWorkePlanDay',
				method:'post',
				data:{
                    'dataStr':{
                        'siteworkerinfoId':siteinfo_id,
                    },
                    'pageSize':10,
                    'pageNo':self.pageIndex4()-1,
                }
			}).then(function(res){
				if (res.data.successful) {
					var nulldata=[]
					var data=res.data.resultValue.resultList
					self.pageTotalCount4(res.data.resultValue.pageCount)
                    self.count4(res.data.resultValue.listSize)
					if( data.length==0){
						self.epibolyplanContent(nulldata)

						document.getElementById("plan").style.display="block";
					}else  if( data.length!==0){
						document.getElementById("plan").style.display="none";
						self.epibolyplanContent(data)
					}
					// self.unprofessionalContent(res.data.resultValue);
				}

			})
		}
		//违章记录
		function getViolationfile (id) {//RYBJCYDWID001
            axios.get(cube.safetygatewayURL + `personnelInformation/getViolationfile?siteworkerinfoId=${id}`).then(function(res) {
                // if (res.data.successful && res.data.resultValue.length) {
				// 	self.unprofessionalContent(res.data.resultValue);
                // }
				if (res.data.successful) {
					var nulldata=[]
					var data=res.data.resultValue
					if( data.length==0){
						self.unprofessionalContent(nulldata)

						document.getElementById("jilu").style.display="block";
					}else  if( data.length!==0){
						document.getElementById("jilu").style.display="none";
						self.unprofessionalContent(data)
					}
					// self.unprofessionalContent(res.data.resultValue);
				}
            })
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
		//二级菜单返回
		self. goreturn=function(){
			window.history.go(-1); //返回上一页
			// commonPageBridge({
			// 	name: 'riskAnalysis.main',
			// 	params: {id:array,}
			// });

		}

		cube.endViewModel(self, params);
	};
	return PageViewModel;
});