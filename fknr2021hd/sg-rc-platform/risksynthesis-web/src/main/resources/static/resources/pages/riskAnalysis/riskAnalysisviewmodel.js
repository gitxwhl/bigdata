define(["RESTClient","axios"], function (RestClient,axios) {
	var PageViewModel = function (params) {
		var self = this;
		// var day2 = new Date();
		// day2.setTime(day2.getTime());
		// var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1);
		self.moduleOptions = ko.observable({ name: 'riskAnalysis.peopleDetail.main',params: {} });
		var xingb = {
			"01":"男",
			"02":"女"
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
		self.page = ko.observable('list');
		self.width = '100%';
		self.height = '310px';
		self.heightMap = '500px';
		self.startTime=ko.observable('');//开始时间
		self.endTime=ko.observable('');//结束时间
		self.operatingPersonnel = ko.observable('');//作业人员总数
		self.majorWorks= ko.observable('');//主业
		self.collective= ko.observable('');//集体
		self.epiboly= ko.observable('');//外包
		self.CurrentHeadcount= ko.observable('');//当前有效准入人数
		self.newNumber= ko.observable('');//本月新增准入人数
		self.CancelNumber= ko.observable('');//本月取消资格人数
		self.Name=ko.observable('');//姓名
		// 单位
		self.monadContent=ko.observableArray([{id:'',text:'全部'},]);
		self.monad=ko.observable('')
		//省份
		self.provinceContent=ko.observableArray([{id:'',text:'全部'},]);
		self.province=ko.observable('');
		//专业
		self.professionContent=ko.observableArray([{id:'',text:'全部'},]);
		self.profession=ko.observable('');
		// //用工性质
		//用工性质
		self.NatureEmploymentContent=[{id:'',text:'全部'},]
		self.NatureEmployment='';
		// self.NatureEmployment='';
		//企业性质
		self.enterprisePropertyContent=ko.observableArray([{id:'',text:'全部'},]);
		self.enterpriseProperty =ko.observable('');
		// //技能等级
		// self.SkillLlevelContent=ko.observableArray()
		// self.SkillLlevel='';
		self.time='';//开始时间
		self.endtime='';//截至时间
		//三种人标识
		self.roleAssignmentContent=ko.observableArray([
			{id:'',text:'全部'},
			{id:'01',text:'工作负责人'},
			{id:'02',text:'工作许可人'},
			{id:'03',text:'工作票签发人'}
		]);
		self.roleAssignment=ko.observable('');
		//是否有效
		self.efficaciousContent=[
			{id:'',text:'全部'},
			{id:'01',text:'有效'},
			{id:'02',text:'无效'},
		]
		self.efficacious=ko.observable('');

		self.personnelName = ko.observable('');//姓名
		self.personnelPhone = ko.observable('');//手机号
		self.personnelCard = ko.observable('');//身份证号
		self.personnelSex = ko.observable('');//性别
		self.personnelHead = ko.observable('');//工作负责人
		self.personnelUnit = ko.observable('');//单位名称
		self.personnelAge = ko.observable('');//年龄
		self.personnelType = ko.observable('');//专业类型

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
		self.id='';
		self.count =ko.observable('');
		self.pageTotalCount = ko.observable('');
		self.pageNo = ko.observable(0);
		self.pageIndex = ko.observable(1);
		self.onPageIndexChanged = function(p_pageIndex) {
            self.pageNo(p_pageIndex);
            getPersonnelIn();
		}
		self.query = function(){
			// if(self.startTime()>self.endTime()){
            //     cube.indicate({
            //         msgType:'error',
            //         content:'开始时间不能大于结束时间！！',
            //         isShowCancelBtn:false,
            //         showTime: '2000'
            //     });
            //     return
            // }
			self.pageNo(0)
            self.pageIndex(1)
			getPersonnelIn();
		}
		setTimeout(function(){
			totalStaff();
			getRegionalMonth();
			getPersonnelIn();
			getDatareportOrg();
			getProfession();
			getOrgNature();
		})
		//省份
		function getDatareportOrg () {
            axios.get(cube.safetygatewayURL + "personnelInformation/getDatareportOrg").then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue;
					for(var i = 0;i<data.length;i++){
						self.provinceContent.push({
							id:data[i]['column_type_code'],
							text:data[i]['column_type_name']
						})
					}
                }

            })
		}
		self.eventChange = function(){
			getOrgId(self.province())
		}
		//根据省份查询单位
		function getOrgId (id) {
			self.monadContent([{id:'',text:'全部'}])
			self.monad('')
            axios.get(cube.safetygatewayURL + `personnelInformation/getOrgId?OrgId=${id}`).then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue;
					for(var i = 0;i<data.length;i++){
						self.monadContent.push({
							id:data[i]['column_type_code'],
							text:data[i]['column_type_name']
						})
					}
                }

            })
        }
		//专业
		function getProfession () {
            axios.get(cube.safetygatewayURL + `personnelInformation/getProfession`).then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue;
					for(var i = 0;i<data.length;i++){
						self.professionContent.push({
							id:data[i]['column_type_code'],
							text:data[i]['column_type_name']
						})
					}
                }

            })
        }
		//企业
		function getOrgNature () {
            axios.get(cube.safetygatewayURL + `personnelInformation/getOrgNature`).then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue;
					for(var i = 0;i<data.length;i++){
						self.enterprisePropertyContent.push({
							id:data[i]['column_type_code'],
							text:data[i]['column_type_name']
						})
					}
                }

            })
        }
		//全网准入人员情况
		function totalStaff () {
			var data=new Date();
			var y=data.getFullYear();
			var m=data.getMonth()+1;
			m=m<10?'0'+m:n;
			var time=y+"-"+m
            axios.get(cube.safetygatewayURL + "personnelInformation/totalStaff?Month="+time).then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue;
					for(var i = 0;i<data.length; i++){
						switch(data[i]['作业人员总数']){
							case '作业人员总数':
								self.operatingPersonnel(data[i]['count(1)']);
								break;
							case '业主':
								self.majorWorks(data[i]['count(1)']);
								break;
							case '集体':
								self.collective(data[i]['count(1)']);
								break;
							case '外包':
								self.epiboly(data[i]['count(1)']);
								break;
							case '当前有效人数':
								self.CurrentHeadcount(data[i]['count(1)']);
								break;
							case '本月新增人数':
								self.newNumber(data[i]['count(1)']);
								break;
							case '本月取消资格人数':
								self.CancelNumber(data[i]['count(1)']);
								break;
						}
					}
                }

            })
        }
		var weizhang = {
			0: '违章',
			1:'未违章'
		}
		self.message = [
			{name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
			{name: "name", width:"10%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					element.style.color = "#0ec4da";
				},
				onClick: function (pkValue, cellValue) {
					self.goparticulars(pkValue);
				},
			},
			// {name: "registerTime", width:"50px", readOnly:true, caption: "用工性质" , editorType: "TextEditor"},
			{name: "sex", width:"10%", caption: "性别" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					return xingb[cellValue]
				}
			},
			{name: "id_card", width:"15%", caption: "身份证号" , editorType: "TextEditor",align : "center",},
			{name: "datareport_org", width:"10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
			{name: "org_name", width:"15%", readOnly:true, caption: "所属单位" , editorType: "TextEditor",align : "center",},
			{name: "nature", width:"10%", caption: "企业性质" , editorType: "TextEditor",align : "center",
				renderCell: function(item){
					var data = self.enterprisePropertyContent();
					for(var i=0;i<data.length;i++){
						if(item == data[i]['id']){
							return data[i]['text']
						}
					}
				}
			},
			{name: "test_score", width:"10%", caption: "考试成绩" , editorType: "TextEditor",align : "center",},
			{name: "penalty_staff_points", width:"10%", caption: "违章" , editorType: "TextEditor",align : "center",
				renderCell: function(item, element) {
					return weizhang[item];
				}},
			{name: "contact", width:"10%", readOnly:true, caption: "手机号" , editorType: "TextEditor",align : "center",},
			{name: "threekinds_identification", width:"10%", caption: "三种人标识" , editorType: "TextEditor",align : "center",
				renderCell: function(item, element) {
					return sanzr[item];
				}
			},
			{name: "access_state", width:"10%", caption: "是否有效" , editorType: "TextEditor",align : "center",
				renderCell: function(item, element) {
					return item == '01'?"有效":"无效";
				}
			},
		];
		//人员详情查看
		self.goparticulars = function (id) {
			commonPageBridge({ 
				name: 'riskAnalysis.peopleDetail.main', 
				params: {id:id,type:'renyuan'} 
			});
			// self.page('PersonalDetails')
			// setTimeout(function(){
			// 	getOrgids(id)
			// 	getExamination(id)
			// 	getViolationfile(id)
			// 	getDatareport(id)
			// })
		}
		var xingb = {
			"01":"男",
			"02":"女"
		}
		var zylx = {
			"01": "输电",
			"02": "变电",
			"03": "配电",
			"04": "其他",
		}
		//人员信息查看
		function getOrgids (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getOrgids?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful && res.data.resultValue.length) {
					var data = res.data.resultValue[0];
					// console.log(res.data.resultValue)
					self.personnelName(data['name']);//姓名
					self.personnelPhone(data['contact']);//手机号
					self.personnelCard(data['id_card']);//身份证号
					self.personnelSex(xingb[data['sex']]);//性别
					// self.personnelHead(xingb[data['sex']]);//工作负责人
					self.personnelUnit(data['org_name']);//单位名称
					// self.personnelAge = '';//年龄
					self.personnelType(zylx[data['profession']]);//专业类型
                }

            })
		}
		function getPersonnelIn () {
			// var url = 'personnelInformation/getPersonnelIn'
			//     + 'Page=' + self.pageNo()
			//     + '&Number=10&'
			//     + 'Name=' + self.Name()
			//     + 'OrgId=' + self.monad()
			//     + '&DatareportOrg=' + self.province()
			//     + 'profession=' +self.profession()
			//     + '&Nature=' + self.enterpriseProperty()
			//     + '&ThreekindsIdentification=' + self.roleAssignment()
			//     + '&starttime=' + self.startTime()
			//     + '&begintime=' + self.endTime()
			//     + '&AccessState=' + self.efficacious()


			var url = `personnelInformation/getPersonnelIn?
			Page=${self.pageNo()}&
			Number=10&
			Name=${self.Name()}&
			OrgId=${self.monad()}&
			DatareportOrg=${self.province()}&
			profession=${self.profession()}&
			Nature=${self.enterpriseProperty()}&
			ThreekindsIdentification=${self.roleAssignment()}&
			starttime=${self.startTime()}&
			begintime=${self.endTime()}&
			AccessState=${self.efficacious()}`
            axios.get(cube.safetygatewayURL + url).then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue[0];
					var list = JSON.parse(JSON.stringify(res.data.resultValue));
					list.shift()
					self.count(data['listSize']);
        			self.pageTotalCount(Math.ceil(data['listSize']/10));
					self.messageContent(list)
                }

            })
        }
		self.messageContent=ko.observableArray([])
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

            })
        }
		self.examinationContent=ko.observableArray([
			// {
			// 	id:1,
			// 	personNames:'安全管控风险测试',
			// 	gender:"风险测试",
			// 	age:'配电专业',
			// 	certificateName:'北京瑞兹科技有限公司',
			// 	belongLevel:"北京瑞兹科技有限公司",
			// 	Certificate_number:'北京',
			// 	belongUnit:'2020-3-6',
			// 	major1:'87',
			// 	major2:'2020-3-16',
			// },
		])

		
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

            })
        }
		self.enterpriseContent=ko.observableArray([
			// {
			// 	id:'',
			// 	personNames:'2020-3-6',
			// 	Companyname:"北京瑞兹科技有限公司"
			// },


		])
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
		//违章记录
		self.unprofessional = [
			{name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "violation_level", width:"20%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					return fzjb[cellValue]
				}
			},
			{name: "violation_clause_description", width:"15%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",},
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
                if (res.data.successful) {
					self.unprofessionalContent(res.data.resultValue);
                }

            })
        }
		self.unprofessionalContent=ko.observableArray([
			// {
			// 	id:'',
			// 	name:'一般违章',
			// 	IDnumber:"未戴安全帽",
			// 	affiliatedUnit:'四川分公司',
			// 	major:'2020-2-26',
			// 	skill:"北京总部",
			// },


		])
		//各单位风险信息统计情况
		var colors = ['#b9b96a', '#7d80d9', '#91d1ea'];
		self.option = ko.observable({
			color: colors,
			tooltip: {
				trigger: 'axis'
			},
			grid: {
				right: '3%',
				left:'3%'
			},
			legend: {
				right: '100px',
				y: '15px',
				data:['主业','集体','外包'],
				textStyle: {
					color: 'white'                              // 图例文字颜色
				}
			},
			xAxis: [
				{
					// type: 'category',
					
					axisTick:{
						show:false,
					},
					axisLine: {
						lineStyle: {
							color: '#5793f3',
							// width: 0, //这里是为了突出显示加上的
						}
					},
					data: []
				},

			],
			yAxis: [
				{
					splitLine: {
                        show: false,
                        lineStyle:{
                            color: '#40A1EA',                                         //网格横线颜色
                            width: 1,
                            // type: 'solid'
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#fff',
                            fontSize:16,
                        }
                    },
                    axisLine: {
                        show:true,
                        lineStyle:{
                            color: '#40A1EA',                                         //网格横线颜色
                            width: 1,
                        }
                    },
				}
			],
			series: [
				{
					name:'主业',
					type:'bar',
					data:[],
				},
				{
					name:'集体',
					type:'bar',
					data:[],
				},
				{
					name:'外包',
					type:'bar',
					data:[],
				}
			]
		});
		function getRegionalMonth () {
			var data=new Date();
			var y=data.getFullYear();
			var m=data.getMonth()+1;
			m=m<10?'0'+m:n;
			var time=y+"-"+m
            axios.get(cube.safetygatewayURL + "personnelInformation/getRegionalMonth?Month="+time).then(function(res) {
                if (res.data.successful) {
					var data = res.data.resultValue;
					var jiti = [],waibao = [],yezhu = [],sf = [];
					for(var i = 0;i<data.length;i++){
						jiti.push(data[i]['nature_jiti'])
						waibao.push(data[i]['nature_waibao'])
						yezhu.push(data[i]['nature_yezhu'])
						sf.push(data[i]['column_type_name'])
					}
					var option = self.option()
					option.xAxis[0].data = sf;
					option.series[0].data = yezhu;
					option.series[1].data = jiti;
					option.series[2].data = waibao;
					self.option(option);
                }

            })
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
		//返回人员信息
		self.goBack = function() {
			self.page('list')
		}

	};

	return PageViewModel;
});