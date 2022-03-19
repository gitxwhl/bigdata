define(["RESTClient",'echarts', "axios",], function (RestClient,echarts, axios) {
	var PageViewModel = function (params) {
		var self = this;
		self.page = 'list';
		var arr = sessionStorage.getItem('arr');
		var array = JSON.parse(arr);
		// console.log(array)
		//       var a=array[0]
		// console.log(a)
		self.isShowRowNumbers = true;
		self.isShowRowNumber = true;
		self.isShowCheckBox = false;
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
		var day2 = new Date();
		var date = new Date();
		var year = date.getFullYear() + "";
		var month = (date.getMonth() + 1) + "";
		// 本月第一天日期
		var begin = year + "-" + month + "-01 00:00:00"
		day2.setTime(day2.getTime());
		var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();

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
			"03": "工作票签发人",
			"04": "非三种人"
		}
		var aaa = {
			"01": "有效",
			"02": "无效",
			" ": "   "
		}
		var biaoshi = {
			"01":"工作负责人",
			"02":"工作许可证",
			"03":"工作票签发人",
			"04": "非三种人",

		}
		self.page = ko.observable('list');
		self.width = '100%';
		self.height = '310px';
		self.heightMap = '500px';
		self.startTime1=  ko.observable('');//开始时间
		self.endTime2 =  ko.observable('');//结束时间
		self.startTime= ko.observable(begin);//开始时间
		self.endTime= ko.observable(s2 + ' 23:59:59');//结束时间
		self.operatingPersonnel = ko.observable('');//作业人员总数
		self.majorWorks= ko.observable('');//主业
		self.collective= ko.observable('');//省管产业
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
		//人员性质
		self.enterprisePropertyContent=ko.observableArray([{id:'',text:'全部'},]);
		self.enterpriseProperty =ko.observable('');
		// //技能
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
			{id:'03',text:'异常'},
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
		self.isShowRowNumber = true;
		self.isShowCheckBox = false;
		self.isShowCheckBoxs = true;
		self.isShowBorder = true;
		self.isShowStripe = false;
		self.isShowHover = false;
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

		self.pageNo = ko.observable('0');

		self.size = 'middle';
		self.showArrow = true;
		self.showTotal = true;
		self.showGoto = true;
		self.showAllPage = false;
		self.count =ko.observable('');
		self.pageTotalCount = ko.observable('');
		self.pageIndex =ko.observable(1);
		self.pageVisibleCount = 10;
		self.onPageIndexChanged = function(p_pageIndex) {
			if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
				self.pageIndex(p_pageIndex);
				getPersonnelIn();
			}
		}


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



		self.size2 = 'middle';
		self.showArrow2 = true;
		self.showTotal2 = true;
		self.showGoto2 = true;
		self.showAllPage2 = false;
		self.count2 =ko.observable('');
		self.pageTotalCount2 = ko.observable('');
		self.pageIndex2 =ko.observable(1);
		self.pageVisibleCount2 = 10;
		self.onPageIndexChanged2 = function(p_pageIndex) {
			if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
				self.pageIndex2(p_pageIndex);
				// self.onRendered()
			// 	var url = `personnelInformation/getPersonnelIn?
			// Page=${self.pageIndex2()}&
			// Number=10&	
			// Name=${self.Name()}&
			// OrgId=${self.monad()}&
			// DatareportOrg=${self.a()}&
			// profession=${self.profession()}&
			// WorkerNature=${self.enterpriseProperty()}&
			// ThreekindsIdentification=${self.roleAssignment()}&
			// starttime=${self.startTime1()}&
			// begintime=${self.endTime2()}&
			// AccessState=${self.efficacious()}`

			axios({
				url:cube.safetygatewayURL + 'personnelInformation/getPersonnelIn',
				method:'post',
				data:{
					'dataStr':{
					'Name':self.Name(),
					'OrgId':self.monad(),
					'DatareportOrg':self.a(),
					'profession':self.profession(),
					'WorkerNature':self.enterpriseProperty(),
					'ThreekindsIdentification':self.roleAssignment(),
					'starttime':self.startTime1(),
					'begintime':self.endTime2(),
					'AccessState':self.efficacious()
					},
					'pageNo':self.pageIndex2()-1,
					'pageSize':'10',
					
				}
			}).then(function(res){
				// axios.get(cube.safetygatewayURL + url).then(function(res) {
					// if (res.data.successful) {
					// 	var data = res.data.resultValue[0];
					// 	var list = JSON.parse(JSON.stringify(res.data.resultValue));
					// 	list.shift()
					// 	self.count2(data['listSize']);
					// 	self.pageTotalCount2(Math.ceil(data['listSize']/10));
					// 	self.messageContent(list)
					// }
					if (res.data.successful==true) {
						var data = res.data.resultValue;
						// var list = JSON.parse(JSON.stringify(res.data.resultValue));
						// list.shift()
						self.count2(data['listSize']);
						self.pageTotalCount2(data.pageCount);
						self.messageContent(data.resultList)
					}

				})
			}
		}
		self.download=function() {
                 // window.open(cube.safetygatewayURL +'personnelInformation/dowlnExcel')

			axios({
				method: 'post',
				url: cube.safetygatewayURL +'personnelInformation/downloadPerExcel',
				responseType: 'arraybuffer'
			}).then(function(res) {
				// console.log(res)
				var blob = new Blob( [res.data], {type: 'application/vnd.ms-excel'} )
				// 兼容不同浏览器的URL对象
				var url = window.URL || window.webkitURL || window.moxURL
				// 创建下载链接
				var downloadHref = url.createObjectURL(blob)
				// 创建a标签并为其添加属性
				var downloadLink = document.createElement('a')
				downloadLink.href = downloadHref
				downloadLink.download = '数据导出.xlsx'
				// 触发点击事件执行下载
				downloadLink.click()
			})
		}
		//重置
		self.reset=function(){
			self.monad('') //单位
			self.Name('');//姓名
			self.province('');//省份
			self.enterpriseProperty('');//人员性质
			self.profession('');//专业
			self.roleAssignment('');//三种人标识
			self.efficacious('');//是否有效
			self.startTime(begin);//开始时间
			self.endTime(s2 + ' 23:59:59');//结束时间
		}
		self.query = function(){
			$(".pageA").css({'display':'block'});
			$(".pageB").css({'display':'none'});
			// if(self.startTime()>self.endTime()){
			//     cube.indicate({
			//         msgType:'error',
			//         content:'开始时间不能大于结束时间！！',
			//         isShowCancelBtn:false,
			//         showTime: '2000'
			//     });
			//     return
			// }
			self.pageNo(1)
			self.pageIndex(1)
			getPersonnelIn();
		}
		// setTimeout(function(){
		// 	totalStaff();
		// 	getRegionalMonth();
		// 	getPersonnelIn();
		// 	getDatareportOrg();
		// 	getProfession();
		// 	getOrgNature();
		// })
		totalStaff();
		getRegionalMonth();
		getPersonnelIn();
		getDatareportOrg();
		getProfession();
		getOrgNature();
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
					// console.log(self.provinceContent())
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
		//人员性质
		function getOrgNature () {
			axios.get(cube.safetygatewayURL + `personnelInformation/getOrgNature`).then(function(res) {
				if (res.data.successful) {
					var data = res.data.resultValue;
					// console.log(res.data)
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
			// var n='';
			m=m<10?'0'+m:m;
			var time=y+"-"+m
			axios.get(cube.safetygatewayURL + "personnelInformation/totalStaff?Month="+time).then(function(res) {
				if (res.data.successful) {
					var data = res.data.resultValue;
					console.log(data)
					self.operatingPersonnel(data[0].crew_count);
					self.majorWorks(data[0].proprietor);
					self.collective(data[0].collectivity);
					self.epiboly(data[0].epiboly);
					self.CurrentHeadcount(data[0].valid);
					self.newNumber(data[0].month_add);
					self.CancelNumber(data[0].month_cancel);
					// console.log(res.data)
					// for(var i = 0;i<data.length; i++){
					// 	switch(data[i]['作业人员总数']){
					// 		case '当前有效人数':
					// 			self.operatingPersonnel(data[i]['count(1)']);
					// 			break;
					// 		case '业主':
					// 			self.majorWorks(data[i]['count(1)']);
					// 			break;
					// 		case '集体':
					// 			self.collective(data[i]['count(1)']);
					// 			break;
					// 		case '外包':
					// 			self.epiboly(data[i]['count(1)']);
					// 			break;
					// 		case '当前有效人数':
					// 			self.CurrentHeadcount(data[i]['count(1)']);
					// 			break;
					// 		case '本月新增人数':
					// 			self.newNumber(data[i]['count(1)']);
					// 			break;
					// 		case '本月取消资格人数':
					// 			self.CancelNumber(data[i]['count(1)']);
					// 			break;
					// 	}
					// }
				}

			})
		}
		var weizhang = {
			0: '违章',
			1:'未违章'
		}
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
		self.message = [
			{name: "siteworkerinfo_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
			{name: "name", width:"10%", readOnly:true, caption: "姓名" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					element.style.color = "#0ec4da";
					element.style.textDecoration = "underline";
					element.style.cursor = "pointer";
				},
				onClick: function (pkValue, cellValue) {
					// self.goparticulars(pkValue);
					self.goparticulars2(pkValue);
					getExamination(pkValue)
					getViolationfile(pkValue)
					getDatareport(pkValue)
					workeplan(pkValue)
					getTmxAttachment(pkValue)
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
			{name: "org_name", width:"15%", readOnly:true, caption: "单位名称" , editorType: "TextEditor",align : "center",},
			{name: "worker_nature", width:"10%", caption: "人员性质" , editorType: "TextEditor",align : "center",
				renderCell: function(item){
					var data = self.enterprisePropertyContent();
					for(var i=0;i<data.length;i++){
						if(item == data[i]['id']){
							return data[i]['text']
						}
					}
				}
			},
			// {name: "test_score", width:"10%", caption: "考试成绩" , editorType: "TextEditor",align : "center",},
			{name: "violation_content", width:"10%", caption: "违章" , editorType: "TextEditor",align : "center",
				// renderCell: function(item, element) {
				// 	return weizhang[item];
				// }
			},
			{name: "contact", width:"10%", readOnly:true, caption: "手机号" , editorType: "TextEditor",align : "center",},
			{name: "threekinds_identification", width:"10%", caption: "三种人标识" , editorType: "TextEditor",align : "center",
				renderCell: function(item, element) {
					return sanzr[item];
				}
			},
			{name: "access_state", width:"10%", caption: "是否有效" , editorType: "TextEditor",align : "center",
				renderCell: function(item, element) {
					// return item == '01'?"有效":"无效";
					return aaa[item];

				}
			},
		];


		//人员详情查看
		self.goparticulars2 = function (id) {
			self.page('list2')
			// var arr=[self.Name(),self.monad(),self.province(),self.profession(),self.enterpriseProperty(),self.roleAssignment(),self.efficacious()]
			// var str = JSON.stringify(arr);
			// window.sessionStorage.setItem('arr', str);
			axios.get(cube.safetygatewayURL + `personnelInformation/getOrgids?siteworkerinfoId=${id}`).then(function(res) {
				if (res.data.successful && res.data.resultValue.length) {
					var data = res.data.resultValue[0];
					data['org_name']=data['org_name'].split(">").join('&gt;')
					self.personnelName(data['name']);//姓名
					self.personnelPhone(data['contact']);//手机号
					self.personnelCard(data['id_card']);//身份证号
					self.personnelSex(xingb[data['sex']]);//性别
					self.personnelHead(biaoshi[data['threekinds_identification']]);//三种人标识
					self.personnelUnit(data['org_name']);//单位名称
					self.personnelAge(data['age']);//年龄
					self.personnelType(zylx[data['profession']]);//专业类型
					var zhuanyi=document.querySelector('#personnelUnit')
					console.log(zhuanyi)
					zhuanyi.innerHTML=data['org_name']
				}

			})
		}
		//二级菜单返回
		self. goreturn=function(){
			self.page('list')
		}
		self.goparticulars = function (id) {
			var arr=[self.Name(),self.monad(),self.province(),self.profession(),self.enterpriseProperty(),self.roleAssignment(),self.efficacious()]
			var str = JSON.stringify(arr);
			window.sessionStorage.setItem('arr', str);

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
					// console.log(res.data.resultValue[0])
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
			// var url = `personnelInformation/getPersonnelIn?
			// Page=${self.pageIndex()}&
			// Number=10&
			// Name=${self.Name()}&
			// OrgId=${self.monad()}&
			// DatareportOrg=${self.province()}&
			// profession=${self.profession()}&
			// WorkerNature=${self.enterpriseProperty()}&
			// ThreekindsIdentification=${self.roleAssignment()}&
			// starttime=${self.startTime()}&
			// begintime=${self.endTime()}&
			// AccessState=${self.efficacious()}`
			// cube.indicate("加载中，请稍后...");
			axios({
				url:cube.safetygatewayURL + 'personnelInformation/getPersonnelIn',
				method:'post',
				data:{
					'dataStr':{
					'Name':self.Name(),
					'OrgId':self.monad(),
					'DatareportOrg':self.province(),
					'profession':self.profession(),
					'WorkerNature':self.enterpriseProperty(),
					'ThreekindsIdentification':self.roleAssignment(),
					'starttime':'',
					'begintime':'',
					'AccessState':self.efficacious()
					},
					'pageNo':self.pageIndex()-1,
					'pageSize':'10',
					
				}
			}).then(function(res){
			// axios.get(cube.safetygatewayURL + url).then(function(res) {
				if (res.data.successful) {
                    // cube.indicate("数据加载成功!");
					var data = res.data.resultValue;
					// console.log(res.data.resultValue)
					// var list = JSON.parse(JSON.stringify(res.data.resultValue));
					// list.shift()
					self.count(data['listSize']);
					self.pageTotalCount(data.pageCount);
					self.messageContent(data.resultList)
				}else{
                    // cube.indicate("数据加载失败!");
					
				}

			}).catch(function(res){
                // cube.indicate("数据加载失败!");

            })
		}
		self.messageContent=ko.observableArray([])
		//考试记录
		self.examination = [
			{name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "test_name", width:"13%", readOnly:true, caption: "考试名称" , editorType: "TextEditor",align : "center",},
			{name: "apply_post", width:"13%", readOnly:true, caption: "报考岗位" , editorType: "TextEditor",align : "center",},
			{name: "apply_specialty",
			renderCell: function(cellValue, element, dictValue){
				console.log(cellValue)
				if(cellValue==null){
					cellValue=''
				}
				if(cellValue=='01'){
					cellValue='输电'
				}else if(cellValue=='02'){
					cellValue='变电'
				}else if(cellValue=='03'){
					cellValue='配电'
				}else if(cellValue=='04'){
					cellValue='其他'
				}
					return "<span>" + cellValue + "</span>";
			},
			width:"10%", caption: "报考专业" , editorType: "TextEditor",align : "center",},
			{name: "org_name", width:"13%", caption: "所在单位" , editorType: "TextEditor",align : "center",},
			{name: "datareport_org", width:"13%", caption: "所属单位" , editorType: "TextEditor",align : "center",},
			{name: "datareport_org", width:"10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
			{name: "test_date", width:"13%",caption: "考试时间" , editorType: "TextEditor",align : "center",},
			{name: "test_score", width:"10%", caption: "考试成绩" , editorType: "TextEditor",align : "center",},
			// {name: "major", width:"107px", caption: "个人积分" , editorType: "TextEditor"},
			{name: "validity_period", width:"10%", caption: "有效时间" , editorType: "TextEditor",align : "center",},
		];
		//考试记录
		function getExamination (id) {
			axios.get(cube.safetygatewayURL + `personnelInformation/getExamination?siteworkerinfoId=${id}`).then(function(res) {
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
			// {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
			{name: "violation_level", width:"20%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					return fzjb[cellValue]
				},
			},
			{name: "violation_content", width:"15%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",},
			{name: "violation_org", width:"10%", caption: "违章单位" , editorType: "TextEditor",align : "center",},
			{name: "create_time", width:"10%", caption: "违章时间" , editorType: "TextEditor",align : "center",},
			{name: "check_level", width:"15%", caption: "巡检单位" , editorType: "TextEditor",align : "center",
				renderCell: function (cellValue, element, dictValue) {
					return xjdw[cellValue]
				}
			},
		];
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
		//违章记录
		function getViolationfile (id) {//RYBJCYDWID001
			axios.get(cube.safetygatewayURL + `personnelInformation/getViolationfile?siteworkerinfoId=${id}`).then(function(res) {
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

		self.option = ko.observable({
			// "title": {
			//     "text": "人流环比图",
			//     "subtext": "昨天 vs 前天",
			//     "x": "center"
			// },
			// "tooltip": {
			// 	"trigger": "axis",
			// 	"axisPointer": {
			// 		"type": "none"
			// 	},
			// },
			"tooltip": {
				"trigger": "axis",
				formatter: function(params) {
					var str = 0
					var strArr = ''
					var name = ''
					for (var i=0; i<params.length; i++) {
						if (i == 0) {
							name = params[i].name
						}
						str += Number(params[i].value)
						strArr += `<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${params[i].color.colorStops[0].color};"></span>` + params[i].seriesName + ':' + params[i].value + '<br />'
					}
					// console.log(params,str,33)

					return name + ':' + str + '<br />' + strArr
				},
				// formatter: function(params) {
				// 	// console.log(params)
				// 	var str = 0
				// 	var strArr = ''
				// 	var name = ''
				// 	params.forEach((item, index) => {
				//
				// 		if (index === 0) {
				// 			name = item.name
				// 		}
				// 		if (item.value === null) {
				// 			str+=0
				// 		}
				// 		str += item.value
				//
				// 		strArr += `<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${item.color.colorStops[0].color};"></span>` + item.seriesName + ':' + item.value + '<br />'
				// 	})
				// 	return name + ':' + str + '<br />' + strArr
				// },
				"axisPointer": {
					"type": "shadow"
				},
			},
			grid: {
				right: '3%',
				left:'4%'
			},

			legend: {
				// selectedMode:false,
				// selected: {
				// 	// 选中'系列1'
				// 	'主业人员': true,
				// 	// 不选中'系列2'
				// 	'省管产业人员': false
				// },
				data:['主业人员','省管产业人员','外包人员'],
				right:"66",
				// top:'10',
				textStyle:{
					color:'#fff',
					fontSize:14,
				}
			},
			
			// "toolbox": {
			//     "show": true,
			//     "feature": {
			//         "restore": { },
			//         "saveAsImage": { }
			//     }
			// },
			"calculable": true,
			"xAxis": [
				{
					"type": "category",

					"axisTick": {
						"show": false
					},
					axisLine: {
						show:true,
						lineStyle: {
							color: '#1B6B9D'
						}
					},
					// "splitArea": {
					//     "show": true
					// },
					"axisLabel": {
						"show": true,
						"splitNumber": 15,
						textStyle: {
							color: "white" //X轴文字颜色
						}
					},
					"data":[],
				}
			],
			"yAxis": [
				{
					minInterval:1,
					"type": "value",
					"splitLine": {
						"show": false
					},
					"axisLabel": {
						"show": true,
						"splitNumber": 15,
						textStyle: {
							color: "white" //X轴文字颜色
						}
					},

					"axisTick": {
						"show": false
					},
					"splitArea": {
						"show": false
					},
					"axisLine": {
						show:true,
						lineStyle: {
							color: '#1B6B9D'
						}
					},

				}
			],
			"series": [
				{
					symbol:'circle',showSymbol: false,
					"name": "主业人员",
					"type": "bar",
					"stack": "总量1",
					"barMaxWidth": 15,
					// "barGap": "10%",
					"itemStyle": {
						"normal": {
							// "barBorderRadius": 0,
							color:new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									{offset: 0, color: '#FFC000'},                   //柱图渐变色
									{offset: 0.5, color: '#FFC000'},                 //柱图渐变色
									{offset: 1, color: '#FFC000'},                   //柱图渐变色
								]
							),
							"label": {
								"show": false,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								// "position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					// "data": [],
				},
				{
					"name": "主业有效人员",
					"type": "bar",
					"stack": "总量1",
					"barMaxWidth": 15,
					"barGap": "10%",
					"itemStyle": {
						"normal": {
							"barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: 'white'},
									// {offset: 1, color: '#FFC000'},
									{offset: 1, color: '#FFF0B3'},
								]
							),
							"label": {
								// "show": true,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								"position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "主业无效人员",
					"type": "bar",
					"stack": "总量1",
					"barMaxWidth": 15,
					"barGap": "10%",
					"itemStyle": {
						"normal": {
							"barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: '#FFC000'},                   //柱图渐变色
									// {offset: 1, color: '#000000'},
									{offset: 1, color: '#FFD652'}, //柱图渐变色
									//柱图渐变色
								]
							),
							"label": {
								// "show": true,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								"position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "主业异常人员",
					"type": "bar",
					"stack": "总量1",
					"itemStyle": {
						"normal": {
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: '#000000'},                   //柱图渐变色
									// {offset: 1, color: '#E4177D'},
									{offset: 1, color: '#8A6800'},//柱图渐变色
									//柱图渐变色
								]
							),
							"barBorderRadius": 0,
							"label": {
								// "show": true,
								// "position": "top",
								// formatter : function(p) {
								//                         return p.value > 0 ? ('▼'
								//                                 + p.value + '')
								//                                 : '';
								//                     }
							}
						}
					},
					"data": []
				},
				{
					"name": "省管产业人员",
					"type": "bar",
					"stack": "总量2",
					"barMaxWidth": 15,
					// "barGap": "10%",
					"itemStyle": {
						"normal": {
							// "barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: 'white'},                   //柱图渐变色
									// {offset: 0.5, color: '#00B0F0'},                 //柱图渐变色
									{offset: 1, color: '#00B0F0'},                   //柱图渐变色
								]
							),
							"label": {
								"show": false,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								// "position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "省管产业有效人员",
					"type": "bar",
					"stack": "总量2",
					"barMaxWidth": 15,
					"barGap": "10%",
					"itemStyle": {
						"normal": {
							"barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: 'white'},
									// {offset: 1, color: '#00B0F0'},                   //柱图渐变色
									{offset: 1, color: '#9CDDF9'},
								]
							),
							"label": {
								// "show": true,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								"position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "省管产业无效人员",
					"type": "bar",
					"stack": "总量2",
					"barMaxWidth": 15,
					"barGap": "10%",
					"itemStyle": {
						"normal": {
							"barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: '#00B0F0'},                   //柱图渐变色
									// {offset: 1, color: '#000000'},                 //柱图渐变色
									{offset: 1, color: '#3ABBF3'},
								]
							),
							"label": {
								// "show": true,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								"position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "省管产业异常人员",
					"type": "bar",
					"stack": "总量2",
					"itemStyle": {
						"normal": {
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: '#000000'},                   //柱图渐变色
									// {offset: 1, color: '#E4177D'},                 //柱图渐变色
									{offset: 1, color: '#017AAF'},
								]
							),
							"barBorderRadius": 0,
							"label": {
								// "show": true,
								// "position": "top",
								// formatter : function(p) {
								//                         return p.value > 0 ? ('▼'
								//                                 + p.value + '')
								//                                 : '';
								//                     }
							}
						}
					},
					"data": []
				},
				{
					"name": "外包人员",
					"type": "bar",
					"stack": "总量",
					"barMaxWidth": 15,
					// "barGap": "10%",
					"itemStyle": {
						"normal": {
							// "barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									{offset: 0, color: '#0B7468'},                   //柱图渐变色
									// {offset: 0.5, color: 'white'},                 //柱图渐变色
									// {offset: 1, color: 'white'},                   //柱图渐变色
								]
							),
							"label": {
								"show": false,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								// "position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					// "data": [],
				},
				{
					"name": "外包有效人员",
					"type": "bar",
					"stack": "总量",
					"barMaxWidth": 15,
					"barGap": "10%",
					"itemStyle": {
						"normal": {
							"barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: 'white'},
									// {offset: 1, color: '#0B7468'},                   //柱图渐变色
									{offset: 1, color: '#AEE4DE'},
								]
							),
							"label": {
								// "show": true,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								"position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "外包无效人员",
					"type": "bar",
					"stack": "总量",
					"barMaxWidth": 15,
					"barGap": "10%",
					"itemStyle": {
						"normal": {
							"barBorderRadius": 0,
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: '#0B7468'},                   //柱图渐变色
									// {offset: 1, color: '#000000'},                 //柱图渐变色
									{offset: 1, color: '#58A99F'},
								]
							),
							"label": {
								// "show": true,
								"textStyle": {
									"color": "rgba(0,0,0,1)"
								},
								"position": "insideTop",
								// formatter : function(p) {
								//                         return p.value > 0 ? (p.value ): '';
								//                     }
							}
						}
					},
					"data": [],
				},
				{
					"name": "外包异常人员",
					"type": "bar",
					"stack": "总量",
					"itemStyle": {
						"normal": {
							color: new echarts.graphic.LinearGradient(
								0, 0, 0, 1,
								[
									// {offset: 0, color: '#000000'},                   //柱图渐变色
									// {offset: 1, color: '#E4177D'},              //柱图渐变色
									{offset: 1, color: '#0A685D'},
								]
							),
							"barBorderRadius": 0,
							"label": {
								// "show": true,
								// "position": "top",
								// formatter : function(p) {
								//                         return p.value > 0 ? ('▼'
								//                                 + p.value + '')
								//                                 : '';
								//                     }
							}
						}
					},
					"data": []
				},

			]
		})

		
		self.onRendered = function(node) {
			// cube.getPageViewModelByNode($("#stop")).chart.off('legendselectchanged')
			// cube.getPageViewModelByNode($("#stop")).chart.on('legendselectchanged', function (params) {
			// 	console.log(params)
			// 	cube.getPageViewModelByNode($("#stop")).chart.setOption({
			// 		legend:{selected:{[params.name]: true}}
			// 	})
			// 	console.log('点击了', params.name);
			// 	// do something
			// });
			cube.getPageViewModelByNode($("#stop")).chart.getZr().on("click",function (param) {
						var pointInPixel = [param.offsetX, param.offsetY]
				if (cube.getPageViewModelByNode($("#stop")).chart.containPixel('grid', pointInPixel)) {
					var xIndex = cube.getPageViewModelByNode($("#stop")).chart.convertFromPixel({ seriesIndex: 0 }, [param.offsetX, param.offsetY])[0]
					console.log(xIndex)
				}

				self.pageIndex2(1);
				$(".pageA").css({'display':'none'});
				$(".pageB").css({'display':'block'});
				self.a=ko.observable("");
				self.Name('')
				self.monad('')
				self.profession('')
				self.enterpriseProperty('')
				self.startTime('')
				self.endTime('')
				self.roleAssignment('')
				self.efficacious('')
				if (param.name=="北京"||xIndex==0){
					self.a(110000);
				}
				if (param.name=="天津"||xIndex==1){
					self.a(120000);
				}if (param.name=="河北"||xIndex==2){
					self.a(130000);
				}
				if (param.name=="冀北"||xIndex==3){
					self.a(130100);
				}
				if (param.name=="山西"||xIndex==4){
					self.a(140000);
				}
				if (param.name=="蒙东"||xIndex==20){
					self.a(150000);
				}
				if (param.name=="辽宁"||xIndex==17){
					self.a(210000);
				}
				if (param.name=="吉林"||xIndex==18){
					self.a(220000);
				}
				if (param.name=="黑龙江"||xIndex==19){
					self.a(230000);
				}
				if (param.name=="上海"||xIndex==6){
					self.a(310000);
				}
				if (param.name=="江苏"||xIndex==7){
					self.a(320000);
				}
				if (param.name=="浙江"||xIndex==8){
					self.a(330000);
				}
				if (param.name=="安徽"||xIndex==9){
					self.a(340000);
				}
				if (param.name=="福建"||xIndex==10){
					self.a(350000);
				}
				if (param.name=="江西"||xIndex==14){
					self.a(360000);
				}
				if (param.name=="山东"||xIndex==5){
					self.a(370000);
				}
				if (param.name=="河南"||xIndex==13){
					self.a(410000);
				}
				if (param.name=="湖北"||xIndex==11){
					self.a(420000);
				}if (param.name=="湖南"||xIndex==12){
					self.a(430000);
				}if (param.name=="重庆"||xIndex==16){
					self.a(500000);
				}if (param.name=="四川"||xIndex==15){
					self.a(510000);
				}if (param.name=="西藏"||xIndex==26){
					self.a(540000);
				}if (param.name=="陕西"||xIndex==21){
					self.a(610000);
				}if (param.name=="甘肃"||xIndex==22){
					self.a(620000);
				}if (param.name=="青海"||xIndex==23){
					self.a(630000);
				}if (param.name=="宁夏"||xIndex==24){
					self.a(640000);
				}if (param.name=="新疆"||xIndex==25){
					self.a(650000);
				}
				self.province(self.a());
				// console.log(param.name)
			// 	var url = `personnelInformation/getPersonnelIn?
			// Page=${self.pageIndex2()}&
			// Number=10&
			// Name=${self.Name()}&
			// OrgId=${self.monad()}&
			// DatareportOrg=${self.a()}&
			// profession=${self.profession()}&
			// WorkerNature=${self.enterpriseProperty()}&
			// ThreekindsIdentification=${self.roleAssignment()}&
			// starttime=${self.startTime1()}&
			// begintime=${self.endTime2()}&
			// AccessState=${self.efficacious()}`
			axios({
				url:cube.safetygatewayURL + 'personnelInformation/getPersonnelIn',
				method:'post',
				data:{
					'dataStr':{
					'Name':self.Name(),
					'OrgId':self.monad(),
					'DatareportOrg':self.a(),
					'profession':self.profession(),
					'WorkerNature':self.enterpriseProperty(),
					'ThreekindsIdentification':self.roleAssignment(),
					'starttime':self.startTime1(),
					'begintime':self.endTime2(),
					'AccessState':self.efficacious()
					},
					'pageNo':self.pageIndex2()-1,
					'pageSize':'10',
					
				}
			}).then(function(res){
				// axios.get(cube.safetygatewayURL + url).then(function(res) {
					if (res.data.successful==true) {
						var data = res.data.resultValue;
						// var list = JSON.parse(JSON.stringify(res.data.resultValue));
						// list.shift()
						self.count2(data['listSize']);
						self.pageTotalCount2(data.pageCount);
						self.messageContent(data.resultList)
					}

				})

			})
		}
		function getRegionalMonth () {
			var data=new Date();
			var y=data.getFullYear();
			var m=data.getMonth()+1;

			m=m<10?'0'+m:m;
			var time=y+"-"+m
			axios.get(cube.safetygatewayURL + "personnelInformation/getRegionalMonth?Month="+time).then(function(res) {
				if (res.data.successful) {
					// var data = res.data.resultValue;
					var data = [];
					// console.log(res.data.resultValue)
					// for (i in data){
					//
					// }
					for(var i=0;i<res.data.resultValue.length;i++){
						if(res.data.resultValue[i].column_type_code==110000){
							data[0]=res.data.resultValue[i]
						}else if(res.data.resultValue[i].column_type_code==120000){
							data[1]=res.data.resultValue[i]

						}else if(res.data.resultValue[i].column_type_code==130000){
							
							data[2]=res.data.resultValue[i]
						}else if(res.data.resultValue[i].column_type_code==130100){
							data[3]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==140000){
							data[4]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==150000){
							data[20]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==210000){
							data[17]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==220000){
							data[18]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==230000){
							data[19]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==310000){
							data[6]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==320000){
							data[7]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==330000){
							data[8]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==340000){
							data[9]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==350000){
							data[10]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==360000){
							data[14]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==370000){
							data[5]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==410000){
							data[13]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==420000){
							data[11]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==430000){
							data[12]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==500000){
							data[16]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==510000){
							data[15]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==540000){
							data[26]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==610000){
							data[21]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==620000){
							data[22]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==630000){
							data[23]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==640000){
							data[24]=res.data.resultValue[i]
							
						}else if(res.data.resultValue[i].column_type_code==650000){
							data[25]=res.data.resultValue[i]
							
						}
					}
					data.forEach(function(val,index,arr){
						for(var i in val) {
							// console.log(i)
							if (!val[i]) {
								val[i] = 0
							}
						}
					});
					var sf = [],
						jiti = [],jitiyouxi = [],jitiwx = [],jitiyc = [],
						waibao = [],waibaoyx = [],waibaowx = [],waibaoyc = [],
						yezhu = [],yezhuyouxiao = [],yezhuwuxiao = [],yezhuyc = [];
					for(var i = 0;i<data.length;i++){
						sf.push(data[i]['column_type_name'])
						jiti.push(data[i]['nature_jiti'])
						jitiyouxi.push(data[i]['nature_jiti_y'])
						jitiwx.push(data[i]['nature_jiti_n'])
						jitiyc.push(data[i]['nature_jiti_w'])
						waibao.push(data[i]['nature_waibao'])
						waibaoyx.push(data[i]['nature_waibao_y'])
						waibaowx.push(data[i]['nature_waibao_n'])
						waibaoyc.push(data[i]['nature_waibao_w'])
						yezhu.push(data[i]['nature_yezhu'])
						yezhuyouxiao.push(data[i]['nature_yezhu_y'])
						yezhuwuxiao.push(data[i]['nature_yezhu_n'])
						yezhuyc.push(data[i]['nature_yezhu_w'])


					}
					// console.log(jiti)
					// 	 yezhu = yezhuyouxiao.reduce(function(prev, cur, index, arr) {
					// 		return prev + cur;
					// 	})
					// 	console.log(yezhu);
					var option = self.option()

					option.xAxis[0].data = sf;
					// option.series[0].data = yezhu;
					option.series[1].data = yezhuyouxiao;
					option.series[2].data = yezhuwuxiao;
					option.series[3].data = yezhuyc;
					// option.series[4].data = jiti;
					option.series[5].data = jitiyouxi;
					option.series[6].data = jitiwx;
					option.series[7].data = jitiyc;
					// option.series[8].data = waibao;
					option.series[9].data = waibaoyx;
					option.series[10].data = waibaowx;
					option.series[11].data = waibaoyc;
					self.option(option);
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
		//返回人员信息
		self.goBack = function() {
			self.page('list')
		}


		//删除
		self.onSelectedItems = function (e) {
			selectedItems = e;
		};
		var selectedItems = [];
		var selectedDelIds = [];
		self.msgType = 'confirm';
		self.isShowDialog=ko.observable(false)
		self.Modaltitle = '事件处理工作评价列表';
		self.deleteTitle = '温馨提示';
		self.content = '删除后将不能恢复，是否确定删除?';
		self.confirmDelete = function (e) {
			var ids = []
			selectedDelIds.forEach(function (item) {
				ids.push(item['siteworkerinfo_id'])
			});
			// console.log(ids)
			var ids1={
				ids:ids
			}
			axios.post(cube.safetygatewayURL + "personnelInformation/deletePersonnel", ids1).then(function (res) {
				getPersonnelIn ()
				totalStaff ()
				getRegionalMonth ()

			});
		};
		self.deleteInstance = function (e) {
			if (selectedItems.length === 0) {
				cube.indicate("请至少勾选一条数据");
				return;
			}
			selectedDelIds = [];
			for (var i = 0, len = selectedItems.length; i < len; i++) {
				selectedDelIds.push(selectedItems[i]);
			}
			self.isShowDialog(true)
		}

	};

	return PageViewModel;
});
