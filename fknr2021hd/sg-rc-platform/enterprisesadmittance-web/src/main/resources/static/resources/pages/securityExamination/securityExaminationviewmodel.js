define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.page='list'
        var day2 = new Date();
        // var dataArr = [];
        var year = day2.getFullYear() + "";
        var month = (day2.getMonth() + 1) + "";
        month = month.length === 2 ? month : '0' +month
        // 本月第一天日期
        var begin = year + "-" + month + "-01 00:00:00"
        day2.setTime(day2.getTime());
        var s2 = day2.getFullYear()+"-" + (day2.getMonth()+1) + "-" + day2.getDate();

        self.startTime = ko.observable(begin);//开始时间
        self.endTime = ko.observable(s2 + ' 23:59:59');//结束时间
        self.isShow = true;
        self.number = ko.observable('');//考试次数
        self.numberTest =ko.observable('');//考试人数
        self.through = ko.observable('');//考试通过人数
        self.passRate = ko.observable('');//考试通过率
        self.ksmcName = ko.observable('');//考试名称
        //考场
        self.qyxzOption = [{id:'',text:'全部'}];
        self.qyxz =  ko.observable();
        //省份
        self.sssfOption =[{id:'',text:'全部'},];
        self.sssf =  ko.observable();
        //考试状态
        self.zrztOption =[
            {id:'',text:'全部'},
            {id:'01',text:'未开始'},
            {id:'02',text:'进行中'},
            {id:'03',text:'已结束'},
        ];

            self.zrzt =  ko.observable('')
        //联系单位
        self.lxdwOption =[{id:'',text:'全部'},];
        self.lxdw =  ko.observable();


        self.sfText = ko.observable('');//省份
        self.kcText =ko.observable('');//考场
        self.kcbhText = ko.observable('');//考场编号
        self.kcmcText =ko.observable('');//考试名称
        self.ksscText = ko.observable('');//考试时长
        self.ksrsText = ko.observable('');//考试人数
        self.ksztText = ko.observable('');//考试状态
        self.startTime1 = ko.observable('');//开始时间
        self.endTime1 =ko.observable('');//结束时间

        self.size2 = 'middle';
        self.showArrow2 = true;
        self.showTotal2 = true;
        self.showGoto2 = true;
        self.showAllPage2 = false;
        self.count2 ='';
        self.pageTotalCount2 = '';
        self.pageIndex2 = 1;
        self.pageVisibleCount2 = 10;
        self.pageNo2 = ko.observable(0);
        self.onPageIndexChanged2 = function(p_pageIndex) {
            self.pageNo2(p_pageIndex-1);
            // getExaminationList();
            getPersonnelList()
        }






        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex =ko.observable(1);
        self.pageVisibleCount = 10;
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
        //人员信息查看
        function getOrgids (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getOrgids?siteworkerinfoId=${id}`).then(function(res) {
                if (true) {
                    var data = res.data.resultValue[0];
                  // console.log(res.data.resultValue)
                    if(res.data.resultValue.length==0){
                        self.personnelName('');//姓名
                        self.personnelPhone('');//手机号
                        self.personnelCard('');//身份证号
                        self.personnelSex('');//性别
                        self.personnelHead('');//三种人标识
                        self.personnelUnit('');//单位名称
                        self.personnelAge('');//年龄
                        self.personnelType('');//专业类型
                    }else   if( res.data.resultValue.length!==0){
                        self.personnelName(data['name']);//姓名
                        self.personnelPhone(data['contact']);//手机号
                        self.personnelCard(data['id_card']);//身份证号
                        self.personnelSex(xingb[data['sex']]);//性别
                        self.personnelHead(biaoshi[data['threekinds_identification']]);//三种人标识
                        self.personnelUnit(data['org_name']);//单位名称
                        self.personnelAge(data['age']);//年龄
                        self.personnelType(zylx[data['profession']]);//专业类型
                    }

                }

            })
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
        self.examinationContent=ko.observableArray([])
        //考试记录
        function getExamination (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getExamination?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful) {

                    if (res.data.successful) {
                        var nulldata=[]
                        var data=res.data.resultValue
                        if( data.length==0){
                            self.examinationContent(nulldata)
                            document.getElementById("kaoshi2").style.display="block";

                        }else  if( data.length!==0){
                            document.getElementById("kaoshi2").style.display="none";
                            self.examinationContent(data)

                        }
                        // self.examinationContent(res.data.resultValue);
                    }
                    // self.examinationContent(res.data.resultValue);
                }

            })
        }

        //企业变更记录
        self.enterprise = [
            {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "update_time", width:"20%", readOnly:true, caption: "变更日期" , editorType: "TextEditor",align : "center",},
            {name: "datareport_org", width:"15%", readOnly:true, caption: "所在单位" , editorType: "TextEditor",align : "center",},
        ];
        self.enterpriseContent=ko.observableArray([])
        //企业变更记录
        function getDatareport (id) {
            axios.get(cube.safetygatewayURL + `personnelInformation/getDatareport?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful) {
                    var nulldata=[]
                    var data=res.data.resultValue
                    if( data.length==0){
                        self.enterpriseContent(nulldata)
                        document.getElementById("biangeng2").style.display="block";

                    }else  if( data.length!==0){
                        document.getElementById("biangeng2").style.display="none";
                        self.enterpriseContent(data)
                    }
                    // self.enterpriseContent(res.data.resultValue);
                }

            })
        }
        //违章记录
        self.unprofessional = [
            {name: "violation_org_id",  readOnly:true ,caption: "" ,editorType: "TextEditor",align : "center",},
            {name: "violation_level", width:"20%", readOnly:true, caption: "违章级别" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    return fzjb[cellValue]
                }
            },
            {name: "violation_clause_description", width:"15%", readOnly:true, caption: "违章详情" , editorType: "TextEditor",align : "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                // onClick: function (pkValue, cellValue) {
                //     self.page('ViolationDetails')
                //     getSrpWorkViolationfile(pkValue)
                // }
            },
            {name: "violation_org", width:"10%", caption: "违章单位" , editorType: "TextEditor",align : "center",},
            {name: "create_time", width:"10%", caption: "违章时间" , editorType: "TextEditor",align : "center",},
            {name: "check_level", width:"15%", caption: "巡检单位" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    return xjdw[cellValue]
                }
            },
        ];
        self.unprofessionalContent=ko.observableArray([])
        //违章记录
        function getViolationfile (id) {//RYBJCYDWID001
            axios.get(cube.safetygatewayURL + `personnelInformation/getViolationfile?siteworkerinfoId=${id}`).then(function(res) {
                if (res.data.successful) {
                    var nulldata=[]
                    var data=res.data.resultValue
                    if( data.length==0){
                        self.unprofessionalContent(nulldata)

                        document.getElementById("jilu2").style.display="block";
                    }else  if( data.length!==0){
                        document.getElementById("jilu2").style.display="none";
                        self.unprofessionalContent(data)
                    }
                    // self.unprofessionalContent(res.data.resultValue);
                }

            })
        }
        //二级菜单返回
        self. goreturn=function(){
            self.page('list')
        }
        //三级菜单返回二级菜单
        self. goreturn0=function(){
            self.page('details')
        }
        //重置
        self.reset=function(){
            self.sssf('') //省份
            self.qyxz('');//考场
            self.ksmcName('');//考试名称
            self.zrzt('');//考试状态
            self.startTime(begin);//发布开始时间
            self.endTime(s2 + ' 23:59:59');//发布结束时间

        }
        setTimeout(function(){
            getNetworkWide();
            getUnitTestData();
            getDatareportOrg();
            // getTestroomName();
            getExaminationList();
        }),

            self.selectedValue = '02';
        self.isSplit = true;
        self.disabled = false;
        self.isHrefRoute = true;

            self.eventChange = function(item) {
                // console.log(item)
                var code=item.sssf._latestValue
            axios.get(cube.safetygatewayURL + "examination/getProvincialexamination?datareport_org_id="+code ).then(function(res) {
                if(res.data.successful && res.data.resultValue.length) {
                    var data = res.data.resultValue;
                    // console.log(res.data.resultValue)
                    self.qyxzOption([{id:'',text:'全部'}]);
                    for (var i = 0; i < data.length; i++) {
                        self.qyxzOption.push({
                            id: data[i]['testroom_no'],
                            text: data[i]['testroom_name']
                        })
                    }
                }else{
                        self.qyxzOption ([{id:'',text:'全部'}]);
                    }


            })
        }
        //省份
        function getDatareportOrg() {
            axios.get(cube.safetygatewayURL + "examination/getDatareportOrg").then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue;
                    // console.log(data)
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
        // function getTestroomName() {
        //     axios.get(cube.safetygatewayURL + "examination/getTestroomName").then(function(res) {
        //         if(res.data.successful && res.data.resultValue.length){
        //             var data = res.data.resultValue;
        //             // console.log(data)
        //             for(var i = 0;i<data.length;i++){
        //                 self.qyxzOption.push({
        //                     id:data[i]['testroom_no'],
        //                     text: data[i]['testroom_name']
        //                 })
        //             }
        //         }
        //     })
        // }
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
            "tooltip": {
                "trigger": "axis",
                "axisPointer": {
                    "type": "shadow"
                },
            },
            grid:{
                top:'10%',
                left: '3%',
                right: '60'
            },
            legend: {
                data:['考试人数','通过人数','通过率'],
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
                axisLabel: {
                    textStyle: {
                        color: '#fff',

                    }
                },
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color:'#1B6B9D',
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
                            // fontSize:16,
                        }
                    },
                    axisLine: {
                        show:true,
                        lineStyle:{
                            color: '#1B6B9D',                                         //网格横线颜色
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
            series: [
                {
                name: '考试人数',
                type: 'bar',
                    barWidth: 15,
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
                barWidth: 15,
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
            // {
            //     name: '考试次数',
            //     type: 'bar',
            //     itemStyle: {
            //         normal: {
            //             barBorderRadius: [6,6,0,0],
            //             color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
            //                 offset: 0,
            //                 color: "#C7E6EA"
            //             },
            //                 {
            //                     offset: 1,
            //                     color: "#6BC0E8"
            //                 }
            //             ])
            //         }
            //     },
            //     data: [],        //数据
            // },
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
        self.onRendered = function(node) {
            cube.getPageViewModelByNode($("#stop")).chart.getZr().on("click",function (param) {
                var pointInPixel = [param.offsetX, param.offsetY]
          if (cube.getPageViewModelByNode($("#stop")).chart.containPixel('grid', pointInPixel)) {
            var xIndex = cube.getPageViewModelByNode($("#stop")).chart.convertFromPixel({ seriesIndex: 0 }, [param.offsetX, param.offsetY])[0]
          }
            console.log(xIndex)
            $('#starttime input').val('')
            $('#endtime input').val('')
            self.startTime('')
            self.endTime('')
            self.qyxz('')
            self.zrzt('')
            self.ksmcName('')
            if (param.name=="北京"||xIndex==0){
                self.sssf(110000);
            }if (param.name=="天津"||xIndex==1){
                self.sssf(120000);
            }if (param.name=="河北"||xIndex==2){
                self.sssf(130000);
            }if (param.name=="冀北"||xIndex==3){
                self.sssf(130100);
            }if (param.name=="山西"||xIndex==4){
                self.sssf(140000);
            }if (param.name=="蒙东"||xIndex==20){
                self.sssf(150000);
            }if (param.name=="辽宁"||xIndex==17){
                self.sssf(210000);
            }if (param.name=="吉林"||xIndex==18){
                self.sssf(220000);
            }if(param.name=="黑龙江"||xIndex==19){
                self.sssf(230000);
            }if (param.name=="上海"||xIndex==6){
                self.sssf(310000);
            }if (param.name=="江苏"||xIndex==7){
                self.sssf(320000);
            }if (param.name=="浙江"||xIndex==8){
                self.sssf(330000);
            }if (param.name=="安徽"||xIndex==9){
                self.sssf(340000);
            }if (param.name=="福建"||xIndex==10){
                self.sssf(350000);
            }if (param.name=="山东"||xIndex==5){
                self.sssf(370000);
            }if (param.name=="河南"||xIndex==13){
                self.sssf(410000);
            }if (param.name=="江西"||xIndex==14){
                self.sssf(360000);
            }if (param.name=="湖北"||xIndex==11){
                self.sssf(420000);
            }if (param.name=="湖南"||xIndex==12){
                self.sssf(430000);
            }if (param.name=="重庆"||xIndex==16){
                self.sssf(500000);
            }if (param.name=="四川"||xIndex==15){
                self.sssf(510000);
            }if (param.name=="西藏"||xIndex==26){
                self.sssf(540000);
            }if (param.name=="陕西"||xIndex==21){
                self.sssf(610000);
            }if (param.name=="甘肃"||xIndex==22){
                self.sssf(620000);
            }if (param.name=="青海"||xIndex==23){
                self.sssf(630000);
            }if (param.name=="宁夏"||xIndex==24){
                 self.sssf(640000);
            }if (param.name=="新疆"||xIndex==25){
                 self.sssf(650000);
             }
            self.pageIndex(1)
             getExaminationList()




            })
        }
        self.option = option;
        //各单位考试情况
        function getUnitTestData() {
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
                    // option.series[2].data = kscs;
                    option.series[2].data = tgl;
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
                click: function (pkValue, cellValue) {
                    self.page('details')
                    // console.log(pkValue.testroom_no)
                    var item=pkValue
               var arr=[item,self.zrzt(),self.startTime(),self.endTime(),self.sssf(), self.qyxz(),self.ksmcName()]
                   var str = JSON.stringify(arr);
                   window.sessionStorage.setItem('arr', str);
                    var arr = sessionStorage.getItem('arr');
                    self.array = JSON.parse(arr);
                    // console.log(self.array[0])
                    self.testroom_no=self.array[0].testroom_no
                    self.zhuangtai=self.array[1]
                    self.start=self.array[2]
                    self.end=self.array[3]
                    getPersonnelList()
                    self.kcbhText(self.testroom_no);//考场编号
                    self.sfText(self.array[0].datareport_org_id);//省份
                    self.kcText(self.array[0].testroom_name);//考场
                    self.kcmcText(self.array[0].test_name);//考试名称

                    //计算考试时长
                    var date1 = new Date(self.array[0].starttime)
                    var date2 = new Date(self.array[0].begintime)
                    var s1 = date1.getTime(),s2 = date2.getTime();
                    var total = (s2 - s1)/1000;
                    var day = parseInt(total / (24*60*60));//计算整数天数
                    var afterDay = total - day*24*60*60;//取得算出天数后剩余的秒数
                    var hour = parseInt(afterDay/(60*60));//计算整数小时数
                    var afterHour = total - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
                    var min = parseInt(afterHour/60);//计算整数分
                    var afterMin = total - day*24*60*60 - hour*60*60 - min*60;//取得算出分后剩余的秒数

                    self.ksscText(day);//考试时长
                    self.ksrsText(self.array[0].number);//考试人数
                    self.ksztText(kksszt[self.array[0].test_state]);//考试状态
                    self.startTime1(self.array[0].starttime);//开始时间
                    self.endTime1(self.array[0].begintime);//结束时间
                    // TestInformation()
                }

			}
        ];
        self.blacklistdetailsMeter=[{name: "siteworkerinfo_id", width:"10%", readOnly:true, caption: "ID" , editorType: "TextEditor",align : "center"},
            {name: "name", width: "10%", readOnly: true, caption: "姓名", editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {

                    self.page('ViolationDetails')
                    getOrgids(pkValue)
                    getExamination (pkValue)
                    getDatareport (pkValue)
                    getViolationfile (pkValue)
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
        self.blacklistdetailsContent=[]

        //考试人员列表
        function getPersonnelList(testroom_no,start,end,test_state) {
            var url = `examination/getPersonnelList?Page=${self.pageIndex2()-1}&Number=10&testroom_no=${self.testroom_no}&starttime=${self.start}&test_state=${self.zhuangtai}&begintime=${self.end}`
            axios.get(cube.safetygatewayURL + url).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){

                    var nulldata=[]
                    var data=res.data.resultValue;
                    var data2=res.data.resultValue[0]
                    if( data.length==1){
                        self.blacklistdetailsContent(nulldata)
                        document.getElementById("kaoshi").style.display="block";

                    }else  if( data.length!==1){
                        document.getElementById("kaoshi").style.display="none";
                        var list = JSON.parse(JSON.stringify(res.data.resultValue));
                        list.shift()
                        self.count2(data2['listSize']);
                        self.pageTotalCount2(Math.ceil(data2['listSize']/10));
                        self.blacklistdetailsContent(list)

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
        //考试列表
        function getExaminationList() {
            var url = 'examination/getExaminationList?'
            + 'Page=' + self.pageIndex()
            + '&Number=10&'
            + 'datareport_org_id=' + self.sssf()
            + '&testroom_no=' + self.qyxz()
            + '&test_name=' + self.ksmcName()
            + '&test_state=' + self.zrzt()
            + '&starttime=' + self.startTime()
            + '&begintime=' + self.endTime()
            cube.indicate("加载中，请稍后...");
            axios.get(cube.safetygatewayURL + url).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    cube.indicate("数据加载成功!");
                    var data = res.data.resultValue[0];
                    // console.log(res.data)
					var list = JSON.parse(JSON.stringify(res.data.resultValue));
					list.shift()
					self.count(data['listSize']);
                    self.pageTotalCount(Math.ceil(data['listSize']/10));
                    for(var i=0;i<list.length;i++){
                        list[i]['id'] = i+1;
                    }
					self.blacklistContent(list)
                }else {
                    cube.indicate("数据加载失败!");
                }
            }).catch(function(res){
                cube.indicate("数据加载失败!");

            })
        }
        function  TestInformation(testroom_no) {
            // console.log(self.testroom_no)
            axios.get(cube.safetygatewayURL + 'examination/getEssentialInformation?testroom_no=' + self.testroom_no).then(function(res) {
                if(res.data.successful && res.data.resultValue.length){
                    var data = res.data.resultValue[0];
                 // console.log(data.duration)
                    self.sfText(data['datareport_org']);//省份
                    self.kcText(data['testroom_name']);//考场
                    self.kcbhText(data['testroom_no']);//考场编号
                    self.kcmcText(data['test_name']);//考试名称
                    self.ksscText(data['duration']);//考试时长
                    self.ksrsText(data['number']);//考试人数
                    self.ksztText(kksszt[data['test_state']]);//考试状态
                    self.startTime1(data['starttime']);//开始时间
                    self.endTime1(data['begintime']);//结束时间
                }
            })
        }
        self.blacklistContent = []
        //删除
        self.onSelectedItems = function (e) {
            selectedItems = e;
        };
        var selectedItems = [];
        var selectedDelIds = [];
        self.msgType = 'confirm';
        self.isShowDialog = ko.observable(false)
        self.Modaltitle = '事件处理工作评价列表';
        self.deleteTitle = '温馨提示';
        self.content = '删除后将不能恢复，是否确定删除?';
        self.confirmDelete = function (e) {
            var ids = []

            selectedDelIds.forEach(function (item) {
                // console.log(item)
                ids.push(item['testroom_no'])
            });
            console.log(ids)
            var ids1={
                ids:ids
            }
            axios.post(cube.safetygatewayURL + "examination/deleteExamination", ids1).then(function (res) {
                if (res.data.successful) {
                    cube.getPageViewModelByNode($('#instanceTable')).load();
                    getExaminationList()
                    getNetworkWide()

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
                            // option.series[2].data = kscs;
                            option.series[2].data = tgl;
                            self.option(option);
                        }
                    })
                }

                // document.location.reload();//重新加载当前页面
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
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});
