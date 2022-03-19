define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        var kksszt = {
            '01':'未开始',
            '02':'进行中',
            '03':'已结束',
        }
		self.isShow = true;
		self.sfText = '';//省份
		self.kcText = '';//考场
		self.kcbhText = '';//考场编号
		self.kcmcText = '';//考试名称
		self.ksscText = '';//考试时长
		self.ksrsText = '';//考试人数
		self.ksztText = '';//考试状态
		self.startTime = '';//开始时间
		self.endTime = '';//结束时间
		self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.onPageIndexChanged = function(p_pageIndex) {
            self.pageIndex(p_pageIndex)
            getPersonnelList(self.detailId)
        }
        self.detailId = ''
        setTimeout(function(){
            if(params.testroom_no){
                self.detailId = params.testroom_no;
                getExaminationList(self.detailId);
                getPersonnelList(self.detailId);
            }
        })
        function getExaminationList(testroom_no) {
            axios.get(cube.safetygatewayURL + 'examination/getEssentialInformation?testroom_no=' + testroom_no).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue[0];
					self.sfText(data['datareport_org']);//省份
                    self.kcText(data['testroom_name']);//考场
                    self.kcbhText(data['testroom_no']);//考场编号
                    self.kcmcText(data['test_name']);//考试名称
                    self.ksscText(data['duration']);//考试时长
                    self.ksrsText(data['number']);//考试人数
                    self.ksztText(kksszt[data['test_state']]);//考试状态
                    self.startTime(data['starttime']);//开始时间
                    self.endTime(data['begintime']);//结束时间
                }
            })
        }
        function getPersonnelList(testroom_no) {
            var url = `examination/getPersonnelList?Page=${self.pageIndex()-1}&Number=10&testroom_no=${testroom_no}`
            axios.get(cube.safetygatewayURL + url).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue[0];
					var list = JSON.parse(JSON.stringify(res.data.resultValue));
					list.shift()
					self.count(data['listSize']);
        			self.pageTotalCount(Math.ceil(data['listSize']/10));
					self.blacklistContent(list)
                }
            })
        }
		// if(params.data){
		// 	var data = params.data;
		// 	self.sfText = data.sf;
		// 	self.kcText = data.kc;
		// 	self.kcbhText = data.kcbh;
		// 	self.kcmcText = data.ksmc;
		// 	self.ksrsText = data.ksrs;
		// 	self.ksztText = data.ktzt;
		// 	self.startTime = data.ksrq;
		// 	self.endTime = data.jsrq;
		// }
		self.goBack = function(){
			self.isShow(false);
			commonPageBridge({ 
				name: 'sureness.safety.main', 
				params: {} 
			});
		}
		self.style ="background:#cccccc;";
        self.pageSize = 10;
        self.blacklistMeter = [
            {name: "siteworkerinfo_id", width:"10%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {name: "name", width: "10%", readOnly: true, caption: "姓名", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    commonPageBridge({ 
                        name: 'riskAnalysis.peopleDetail.main', 
                        params: {
                            id:pkValue,
                            type:'kaoshi',
                            testroom_no: params.testroom_no
                        } 
                    });
                },
            },
            {name: "contact", width:"10%", readOnly:true, caption: "手机号" , editorType: "TextEditor",align : "center"},
            {name: "apply_post", width:"10%", caption: "岗位 " , editorType: "TextEditor",align : "center"},
            {name: "apply_specialty", width:"20%", caption: "专业" , editorType: "TextEditor",align : "center"},
            {name: "org_name", width:"10%", readOnly:true, caption: "所属单位" , editorType: "TextEditor",align : "center"},
            {name: "test_score", width:"10%", readOnly:true, caption: "分数" , editorType: "TextEditor",align : "center"},
            {name: "pass_state", width:"10%", caption: "是否通过" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    return cellValue == '01'?"通过":"未通过"
                }
            }

		];
		self.blacklistContent = []
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});