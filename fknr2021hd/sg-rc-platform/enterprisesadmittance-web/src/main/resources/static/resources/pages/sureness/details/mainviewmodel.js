define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.page='list'
        var arr = sessionStorage.getItem('arr');
        var array = JSON.parse(arr);
        // console.log(array)
        var array2=array[0]
        // console.log(array2)
        var zhuangtai=array[1]
        var start=array[2]
        var end=array[3]
        var day2 = new Date();
        var dataArr = [];
        var year = day2.getFullYear() + "";
        var month = (day2.getMonth() + 1) + "";
        month = month.length === 2 ? month : '0' +month

        // 本月第一天日期
        var begin = year + "-" + month + "-01 00:00:00"

        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();

        // var start = ko.observable(begin);//开始时间
        // var end = ko.observable(s2 + ' 23:59:59');//结束时间

        var kksszt = {
            '01':'未开始',
            '02':'进行中',
            '03':'已结束',
        }
		self.isShow = true;
        // if (array2.test_state='03'){
        //     self.ksztText='已结束'//考试状态
        // }
        // if (array2.test_state='01'){
        //     self.ksztText='未开始'
        // }
        // if (array2.test_state='02'){
        //     self.ksztText='进行中'
        // }
        self.ksztText=kksszt[array2['test_state']];//考试状态
		self.sfText = array2.datareport_org_id;//省份
		self.kcText = array2.testroom_name;//考场
		self.kcbhText = array2.testroom_no;//考场编号
		self.kcmcText =array2.test_name;//考试名称
		self.ksscText = array2.duration;//考试时长
		self.ksrsText = array2.number;//考试人数
		// self.ksztText = array.test_state;//考试状态
		self.startTime = array2.starttime;//开始时间
		self.endTime =array2.begintime;//结束时间
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
        self.detailId2 = ''
        self.detailId3 = ''
        self.detailId4 = ''
        setTimeout(function(){
            // console.log(params)
            // if(params.testroom_no){
                self.detailId = params.testroom_no;
                self.detailId2 = params.starttime;
                self.detailId3 = params.begintime;
                self.detailId4 = params.test_state;
                // getExaminationList(self.detailId);
                getPersonnelList(self.detailId,start,end,zhuangtai,self.detailId4);
            // }
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
        function getPersonnelList(testroom_no,start,end,test_state) {
            // console.log(end)
            var url = `examination/getPersonnelList?Page=${self.pageIndex()-1}&Number=10&testroom_no=${testroom_no}&starttime=${start}&test_state=${zhuangtai}&begintime=${end}`
            axios.get(cube.safetygatewayURL + url).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){

                    var nulldata=[]
                    var data=res.data.resultValue;
                    var data2=res.data.resultValue[0]
                    if( data.length==1){
                        self.blacklistContent(nulldata)
                        document.getElementById("kaoshi").style.display="block";

                    }else  if( data.length!==1){
                        document.getElementById("kaoshi").style.display="none";
                        var list = JSON.parse(JSON.stringify(res.data.resultValue));
                        list.shift()
                        self.count(data2['listSize']);
                        self.pageTotalCount(Math.ceil(data2['listSize']/10));
                        self.blacklistContent(list)

                    }





                    // var data = res.data.resultValue[0];
					// var list = JSON.parse(JSON.stringify(res.data.resultValue));
					// list.shift()
					// self.count(data['listSize']);
        			// self.pageTotalCount(Math.ceil(data['listSize']/10));
					// self.blacklistContent(list)
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
            // window.open('sureness.safety.main','_self');

            // console.log(1213)
            // window.history.go(-1)

            // window.history.back();
            var arr2=array
            var str2 = JSON.stringify(arr2);

            window.sessionStorage.setItem('arr2', str2);
			commonPageBridge({
				name: 'sureness.safety.main',
				params: {}
			});
		}
        //二级菜单返回
        self. goreturn=function(){
            // window.history.back(-1)
            commonPageBridge({
                name: 'sureness.main',
                // params: {id:array,}
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
