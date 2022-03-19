define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
        var day2 = new Date();
        day2.setTime(day2.getTime());
        var yue = '';
        if((day2.getMonth()+1)<10){
            yue = '0' + (day2.getMonth()+1)
        }else {
            yue = day2.getMonth()+1
        }
        var s2 = day2.getFullYear()+"-" + yue + "-" + day2.getDate();
        self.isShow = true;
        self.number = '';//考试次数
        self.numberTest = '';//考试人数
        self.through = '';//考试通过人数
        self.passRate = '';//考试通过率
        self.ksmcName = '';//考试名称
        //考场
        self.qyxzOption =[{id:'',text:'全部'},];
        self.qyxz = '';
        //省份
        self.sssfOption =[{id:'',text:'全部'},];
        self.sssf = '';
        //考试状态
        self.zrztOption =[
            {id:'',text:'全部'},
            {id:'01',text:'未开始'},
            {id:'02',text:'进行中'},
            {id:'03',text:'已结束'},
        ];
        self.zrzt = '';
        //联系单位
        self.lxdwOption =[{id:'',text:'全部'},];
        self.lxdw = '';
        // self.startTime = day2.getFullYear() + '-01-01 00:00:00'//开始时间
        // self.endTime = s2 + ' 23:59:59'//结束时间
        self.startTime = ko.observable('')//开始时间
        self.endTime = ko.observable('')//结束时间
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;
        self.pageNo = ko.observable(0);
        self.onPageIndexChanged = function(p_pageIndex) {
            self.pageNo(p_pageIndex-1);
            getExaminationList();
        }
        self.query = function(){
            if(self.startTime()>self.endTime()){
                cube.indicate({
                    msgType:'error',
                    content:'开始时间不能大于结束时间！！',
                    isShowCancelBtn:false,
                    showTime: '2000'
                });
                return
            }
            self.pageNo(0)
            self.pageIndex(1)
            getExaminationList();
        }
        setTimeout(function(){
            getNetworkWide();
            getUnitTestData();
            getDatareportOrg();
            getTestroomName();
            getExaminationList();
        })
        //省份
        function getDatareportOrg() {
            axios.get(cube.safetygatewayURL + "examination/getDatareportOrg").then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue;
                    for(var i = 0;i<data.length;i++){
                        self.sssfOption.push({
                            id:data[i]['column_type_code'],
                            text: data[i]['column_type_name']
                        })
                    }
                }
            })
        }
        //考场
        function getTestroomName() {
            axios.get(cube.safetygatewayURL + "examination/getTestroomName").then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue;
                    for(var i = 0;i<data.length;i++){
                        self.qyxzOption.push({
                            id:data[i]['testroom_no'],
                            text: data[i]['testroom_name']
                        })
                    }
                }
            })
        }
        //考场状态
        // function getExaminationStatus() {
        //     axios.get(cube.safetygatewayURL + "examination/getExaminationStatus").then(function(res) {
                
        //     })
        // }
        //全国考试情况
        function getNetworkWide() {
            axios.get(cube.safetygatewayURL + "examination/getNetworkWide").then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue[0];
                    self.number(data['examination_number']);//考试次数
                    self.numberTest(data['examination_person_number']);//考试人数
                    self.through(data['passing_number']);//考试通过人数
                    self.passRate(data['probability'] + '%');//考试通过率
                }
            })
        }
        var option= {
            tooltip: {
                trigger: 'axis'
            },
            grid:{
                top:'10%',
                left: '60',
                right: '60'
            },
            legend: {
                data:['考试人数','通过人数','考试次数','通过率'],
                right:'80',
                textStyle:{
                    color:'#fff',
                    fontSize:14,
                }
            },
            xAxis: {
                data:[],       //横坐标
                axisTick:{
                    show:false,
                },
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color:'#fff',
                        width:'1  ',                                                //坐标线的宽度

                    }
                },
            },
            yAxis: 
            [
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
                    
                },
                {

                    splitLine: {show: false},
                    axisLine: {
                        lineStyle: {
                            color: '#B4B4B4',
                        }
                    },
                    max:100,
                    min:0
                }
            ],
            series: [{
                name: '考试人数',
                type: 'bar',
                itemStyle: {
                    normal: {
                        barBorderRadius: [6,6,0,0],
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#FAC029"
                        },
                            {
                                offset: 1,
                                color: "#48ADDB"
                            }
                        ])
                    }
                },
                data:[],        //数据
            },
            {
                name: '通过人数',
                type: 'bar',
                itemStyle: {
                    normal: {
                        barBorderRadius: [6,6,0,0],
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#9370D4"
                        },
                            {
                                offset: 1,
                                color: "#43A8E4"
                            }
                        ])
                    }
                },
                data: [],        //数据
            },
            {
                name: '考试次数',
                type: 'bar',
                itemStyle: {
                    normal: {
                        barBorderRadius: [6,6,0,0],
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#C7E6EA"
                        },
                            {
                                offset: 1,
                                color: "#6BC0E8"
                            }
                        ])
                    }
                },
                data: [],        //数据
            },
            {
                name: '通过率',
                type: 'line',
                smooth: true,
                showAllSymbol: true,
                symbol: 'emptyCircle',
                symbolSize: 8,
                yAxisIndex: 1,
                itemStyle: {
                        normal: {
                        color:'#F02FC2'},
                },
                data: []
            }, 
            ]
        };
        self.option = option;
        //各单位考试情况
        function getUnitTestData() {
            // var data=new Date();
            // var y=data.getFullYear();
            // var m=data.getMonth()+1;
            // m=m<10?'0'+m:m;
            // var time=y+"-"+m

            var data=new Date();
            var y=data.getFullYear();
            var m=data.getMonth()+1;

            m=m<10?'0'+m:m;
            var time=y+"-"+m
            axios.get(cube.safetygatewayURL + "examination/getUnitTestData?Month="+time).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue;
                    var sf = [],ksrs = [],tgrs = [],kscs = [],tgl = [];
                    for(var i = 0;i<data.length;i++){
                        sf.push(data[i]['column_type_name'])
                        ksrs.push(data[i]['examination_person_number'])
                        tgrs.push(data[i]['passing_number'])
                        kscs.push(data[i]['examination_number'])
                        tgl.push(data[i]['probability'])
                    }
                    option.xAxis.data = sf;
                    option.series[0].data = ksrs;
                    option.series[1].data = tgrs;
                    option.series[2].data = kscs;
                    option.series[3].data = tgl;
                    self.option(option);
                }
            })
        }
        self.style ="background:#cccccc;";
        self.pageSize = 10;
        var kksszt = {
            '01':'未开始',
            '02':'进行中',
            '03':'已结束',
        }
        self.blacklistMeter = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center"},
            {name: "datareport_org_id", width: "10%", readOnly: true, caption: "省份", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    var data = self.sssfOption();
                        for(var i = 0; i<data.length; i++){
                            if(cellValue == data[i]['id']){
                                return data[i]['text']
                            }
                        }
                }
            },
            {name: "testroom_name", width:"10%", readOnly:true, caption: "考场" , editorType: "TextEditor",align : "center"},
            {name: "testroom_no", width:"10%", caption: "考场编号 " , editorType: "TextEditor",align : "center"},
            {name: "test_name", width:"20%", caption: "考试名称" , editorType: "TextEditor",align : "center"},
            {name: "number", width:"10%", readOnly:true, caption: "考试人数" , editorType: "TextEditor",align : "center"},
            {name: "starttime", width:"10%", readOnly:true, caption: "开始日期" , editorType: "TextEditor",align : "center"},
            {name: "begintime", width:"10%", caption: "结束日期" , editorType: "TextEditor",align : "center"},
            {name: "test_state", width:"10%", caption: "考题状态" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    return kksszt[cellValue]
                }
            }

        ];
        self.customOperations = [
			{
				caption : "查看",
				click: function(item) {
                    self.isShow(false);
                    commonPageBridge({ 
                        name: 'sureness.details.main', 
                        params: {testroom_no: item['testroom_no']} 
                    });
				}
			}
        ];
        //考试列表
        function getExaminationList() {

            var url = 'examination/getExaminationList?'
            + 'Page=' + self.pageNo()
            + '&Number=10&'
            + 'datareport_org_id=' + self.sssf()
            + '&testroom_no=' + self.qyxz()
            + '&test_name=' + self.ksmcName()
            + '&test_state=' + self.zrzt()
            + '&starttime=' + self.startTime()
            + '&begintime=' + self.endTime()
            axios.get(cube.safetygatewayURL + url).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue[0];
                    // console.log(res.data.resultValue)
					var list = JSON.parse(JSON.stringify(res.data.resultValue));
					list.shift()
					self.count(data['listSize']);
                    self.pageTotalCount(Math.ceil(data['listSize']/10));
                    for(var i=0;i<list.length;i++){
                        list[i]['id'] = i+1;
                    }
					self.blacklistContent(list)
                }
            })
        }
        self.blacklistContent = []
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});