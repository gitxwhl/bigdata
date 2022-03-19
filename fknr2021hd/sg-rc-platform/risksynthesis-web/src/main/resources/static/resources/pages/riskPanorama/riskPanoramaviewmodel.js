define(["RESTClient","echarts", "axios",], function(RestClient, echarts, axios) {
    var PageViewModel = function(params) {
        var self = this;
        var colors = ['#5793f3', '#d14a61', '#675bba'];
        var restClient = new RestClient();
        self.page='list'
       cityName=[];//城市
      powerGridSum=[];//电网风险总数
        OperationalRiskNumber=[];//作业风险数
       BlackoutSchedule=[];//停电计划数

        var cityNameNumber=[];//预警数量城市
        var number1=[]
        var number2=[]
        var number3=[]
        var number4=[]
        var number5=[]
        var number6=[]
        var number7=[]
        var number8=[]

        PowercityNames=[]//电压等级
        fiveHundred=[]//500以上
        Twohundred=[]

        capitalconstructionCity=[]//停电事由城市
        capitalconstruction=[]
        project=[]
        facilitymaintenance=[]
        PowerPlantCustomers=[]
        MunicipalCooperate=[]
        //停电设备
        equipmentCity=[]
        riskdevzl_cnt=[]
        riskdevmx_cnt=[]
        riskdevjl_cnt=[]
        riskdevqt_cnt=[]
        riskdevzb_cnt=[]
        sustainCity=[]//持续时间
        actualday15_cnt=[]
        actualday31_cnt=[]
        actualday30_cnt=[]
        actualday1_cnt=[]
        actualday3_cnt=[]
        actualday7_cnt=[]
        affectCity=[]//影响类型
        warndevtypemxn1_cnt=[]
        warndevtypexln1_cnt=[]
        warndevtypezbn1_cnt=[]
        warndevtypelxn2_cnt=[]
        warndevtypeqt_cnt=[]
        warndevtypejzn1_cnt=[]
        warndevtypelxn3_cnt=[]
        var informationStatistics=[]
        self.selectList = [
            { id: '1', text: '预警数量' },
            { id: '2', text: '电压等级' },
            { id: '3', text: '停电事由' },
            { id: '4', text: '停电设备' },
            { id: '5', text: '预警事件持续时间' },
            { id: '6', text: '预警影响类型' },
            // { id: '7', text: '评价指标统计情况' }

        ];
        self.eventChange = function(e) {
            // console.log(e)
            if (self.selectId() == '1') {
                $(".WarningNumber").show();
                $(".voltageClasses").hide();
                $(".PowerFailureReason").hide();
                $(".PowerEquipment").hide();
                $(".earlyWarningTime").hide();
                $(".earlyWarningType").hide();
                $(".evaluateStatistics").hide();
            }
            if (self.selectId() == '2') {
                $(".voltageClasses").show();
                $(".WarningNumber").hide();
                $(".PowerFailureReason").hide();
                $(".PowerEquipment").hide();
                $(".earlyWarningTime").hide();
                $(".earlyWarningType").hide();
                $(".evaluateStatistics").hide();
            }
            if (self.selectId() == '3') {
                $(".PowerFailureReason").show();
                $(".WarningNumber").hide();
                $(".voltageClasses").hide();
                $(".PowerEquipment").hide();
                $(".earlyWarningTime").hide();
                $(".earlyWarningType").hide();
                $(".evaluateStatistics").hide();
            }
            if (self.selectId() == '4') {
                $(".PowerEquipment").show();
                $(".WarningNumber").hide();
                $(".PowerFailureReason").hide();
                $(".voltageClasses").hide();
                $(".earlyWarningTime").hide();
                $(".earlyWarningType").hide();
                $(".evaluateStatistics").hide();

            }
            if (self.selectId() == '5') {
                $(".earlyWarningTime").show();
                $(".WarningNumber").hide();
                $(".PowerFailureReason").hide();
                $(".PowerEquipment").hide();
                $(".voltageClasses").hide();
                $(".earlyWarningType").hide();
                $(".evaluateStatistics").hide();

            }
            if (self.selectId() == '6') {
                $(".earlyWarningType").show();
                $(".WarningNumber").hide();
                $(".PowerFailureReason").hide();
                $(".PowerEquipment").hide();
                $(".earlyWarningTime").hide();
                $(".voltageClasses").hide();
                $(".evaluateStatistics").hide();

            }
            if (self.selectId() == '7') {
                $(".evaluateStatistics").show();
                $(".WarningNumber").hide();
                $(".PowerFailureReason").hide();
                $(".PowerEquipment").hide();
                $(".earlyWarningTime").hide();
                $(".earlyWarningType").hide();
                $(".voltageClasses").hide();

            }
        }

        self.selectId = '1';
        self.release=ko.observable("0");//发布数
        self.stair=ko.observable("0");//一级
        self.second=ko.observable("0");//二级
        self.three=ko.observable("0");//三级
        self.four=ko.observable("0");//四级
        self.five=ko.observable("0");//五级
        self.six=ko.observable("0");//六级
        self.risk='';//作业风险数
        self.relieve='';//解除数
        // self.width = '100%';
        self.height = '310px';
        self.heightMap = '500px';
        self.title=''//预警标题
        self.number=''//预警编号
        self.hierarchy=''//预警等级
        self.startTime=''//开始时间
        self.endtTime=''//结束时间
        self.monad=''//发布单位
        self.staff=''//发布人员
        self.status=''//状态
        self.primaryKey = 'gridwarnnotice_id'

       //预警通知单
        self.informNum=""//预警编号
        self.informUnit=''//发布单位
        self.informData=''//预警日期
        self.informPowercut=''//停电设备
        self.informMatter=''//预警事由
        self.informTimeframe=''//预警时段
        self.informgrade=''//风险等级
        self.informbranch=''//主送部门
        self.informaccountability=''//责任单位
        self.informanalyze=''//风险分析
        self.informrequire=''//管控措施及要求
        self.informdepartment=''//签收部门
        self.informauthorizedStrength=''//编制
        self.annex=''//附件
        //预警反馈单
        self.feedbackNum=''//预警编号
        self.feedbackUnit=''//反馈单位
        self.feedbackTime=''//反馈日期
        self.feedbackoddNum=''//预警单编号
        self.feedbackaudit=''//审核
        self.feedbackratify=''//批准
        self.feedbackTimeframe=''//预警时段
        self.feedbacksent=''//主送单位
        self.feedbackrequire=''//管控措施落实情况
        self.feedbackauthorizedStrength=''//编制
        //预警报告单
        self.reportNum=''//预警编号
        self.reportTime=''//报送日期
        self.reportunit=''//主送单位
        self.reportTimeframe=''//预警时段
        self.reportcondition=''//预警情况
        self.reportscope=''//涉及区域和影响范围
        self.reportsuggest=''//需政府协调的事项建议
        self.reportmonad=''//报告单位
        self.reportName=''//联系人
        self.reportPhone=''//联系电话
        //预警告知单
        self.notifysNum=''//预警编号
        self.notifysTime=''//报送日期
        self.notifysmonad=''//送达单位
        self.notifysMatter=''//预警事由
        self.notifysTimeframe=''//预警时段
        self.notifysanalyze=''//风险分析
        self.notifysmeasure=''//电网风险管控措施
        self.notifysunit=''//告知单位
        self.notifysName=''//联系人
        self.notifysPhone=''//联系电话
        //预警实施单
        self.exploitingsNum=''//预警编号
        self.exploitingsPowercut=''//停电设备
        self.exploitingsMatter=''//预警事由
        self.exploitingsclass=''//预警等级
        self.exploitingsTimeframe=''//预警时段
        self.exploitingsunit=''//主送部门
        self.exploitingsmonad=''//责任单位
        //预警解除
        self.relievesmessage=''//解除信息
        self.relievesyardman=''//调度员
        self.relievesphone=''//联系方式
        self.relievesTime=''//解除时间

        self.option2 ={
            title: {
                text: '各单位电网风险停电事由分布情况',
                left:'20%',
                top: "15",
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                }
            },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                left:'47%',
                top: '4%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },
                data: ['一级', '二级', '三级', '四级','五级','六级','七级','八级'],
            },
            grid: { //图表的位置
                // top: '20%',
                // left: '3%',
                // right: '4%',
                // bottom: '5%',
                // containLabel: true
                right: '2%',
                left:'2%'
            },
            yAxis: [{
                minInterval:1,
                type: 'value',
                textStyle: {
                    fontSize: 16,
                    color: 'red'
                },
                axisLabel: {
                    interval: 0,
                    minInterval:1,
                    show: true,
                    // interval: 'auto',
                    formatter: '{value} ',
                    textStyle: {
                        color: '#fff'
                    }
                },
                splitLine: {
                    show: false,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                show: true

            }],
            xAxis: [{
                type: 'category',
                axisLabel: {
                    interval: 0,
                    minInterval:1,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontSize: 16,
                        color: 'white'
                    },

                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                data:cityNameNumber
            }],
            series: [{
                name: '一级',
                color:'#FF0000',
                type: 'bar',
                stack: 'sum',
                barWidth: '20px',
                data:number1

            },
                {
                    name: '二级',
                    color:' #FF8C00',
                    type: 'bar',
                    barWidth: '20px',
                    stack: 'sum',
                    data:number2
                },
                {
                    name: '三级',
                    type: 'bar',
                    color: ' #FFFF00',
                    stack: 'sum',
                    barWidth: '20px',
                    data:number3

                },
                {
                    name: '四级',
                    type: 'bar',
                    color: '#C0FF3E',
                    stack: 'sum',
                    barWidth: '20px',
                    data: number4

                },
                {
                    name: '五级',
                    type: 'bar',
                    color: '#C1FFC1',
                    stack: 'sum',
                    barWidth: '20px',
                    data: number5

                },
                {
                    name: '六级',
                    type: 'bar',
                    color: '#BBFFFF',
                    stack: 'sum',
                    barWidth: '20px',
                    data:number6

                },
                {
                    name: '七级',
                    type: 'bar',
                    color: '#9F79EE',
                    stack: 'sum',
                    barWidth: '20px',
                    data: number7

                },
                {
                    name: '八级',
                    type: 'bar',
                    color: '#EEB4B4',
                    stack: 'sum',
                    barWidth: '20px',
                    data: number8

                },
            ]
        };
        //各单位风险信息统计情况
        self.pieoption = {
            color: colors,
            tooltip: {
                trigger: 'axis',
                // axisPointer: {type: 'cross'}
            },
            grid: {
                // top:'',
                right: '2%',
                left:'2%'
            },
            legend: {
                x: '70%',
                y: '-5px',
                data:['电网风险总数','关联作业风险数','停电计划数'],
                textStyle: {
                    color: 'white'                              // 图例文字颜色
                }
            },
            xAxis: [
                {
                    show: true,
                    type: 'category',
                    data:cityName,
                    // axisTick: {
                    //     alignWithLabel: false
                    // },
                    axisLine: {
                        lineStyle: {
                            color: '#5793f3',
                            width: 3, //这里是为了突出显示加上的
                        }
                    },

                    // splitLine: {
                    //     show: false
                    // },
                    axisLabel: {
                        interval: 0,
                        show: true
                    }

                },

            ],
            yAxis: [
                {
                    type: 'value',
                    "splitLine": {     //网格线
                        "show": false
                    },
                    name: '',
                    min: 0,
                    // minInterval: 1,
                    position: 'left',
                    axisLine: {
                        lineStyle: {
                            color: '#5793f3',
                            width: 3, //这里是为了突出显示加上的
                        },
                    },
                    axisLabel: {
                        show: true,
                        formatter: '{value}'
                    }

                },
                {
                    type: 'value',
                    splitLine: {
                        show: false
                    },
                    name: '',
                    min: 0,
                    // minInterval: 1,
                    position: 'left',
                    offset:1,
                    axisLine: {
                        lineStyle: {
                            color: ''
                        }
                    },
                    axisLabel: {
                        show: true,
                        // textStyle: {
                        //     color: "red",
                        //     fontSize:16,
                        // },
                        formatter: '{value}'
                    },

                },
                // {
                //     type: 'value',
                //     splitLine: {
                //         show: false
                //     },
                //     name: '管控措施完成率%',
                //     min: 0,
                //     max: 100,
                //     position: 'right',
                //     axisLine: {
                //         lineStyle: {
                //             color:'#5793f3'
                //         }
                //     },
                //     // axisLabel: {
                //     //     formatter: '{value}'
                //     // }
                // }
            ],
            axisLine: {
                lineStyle: {
                    color: 'white',
                    width: 1, //这里是为了突出显示加上的
                }
            },
            series: [
                {
                    name:'电网风险总数',
                    type:'bar',
                    data:powerGridSum
                },
                {
                    name:'关联作业风险数',
                    type:'bar',
                    yAxisIndex: 1,
                    data:OperationalRiskNumber
                },
                {
                    name:'停电计划数',
                    type:'bar',
                    data:BlackoutSchedule
                },
                // {
                //     name:'管控措施完成率',
                //     type:'line',
                //     yAxisIndex: 2,
                //     data:[]
                // }
            ]
        };
        //电压等级
        self.option3 ={
            title: {
                text: '各单位电网风险电压等级分布情况',
                left:'20%',
                top: "15",
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                }
            },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                right:'25%',
                top: '3%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },
                data:['500kV以上发布数','220（330）kV及以下发布数'],
            },
            grid: { //图表的位置
                // top: '20%',
                // left: '3%',
                // right: '4%',
                // bottom: '5%',
                // containLabel: true
                right: '2%',
                left:'2%'
            },
            yAxis: [{
                minInterval:1,
                type: 'value',
                textStyle: {
                    fontSize: 16,
                    color: 'red'
                },
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    formatter: '{value} ',
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                splitLine: {
                    show: false,  //刻度线设置
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                show: true

            }],
            xAxis: [{
                type: 'category',
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontSize: 16,
                        color: 'white'
                    },

                }, axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                data: PowercityNames,
            }],
            series: [{
                name: '500kV以上发布数',
                type: 'bar',
                barWidth:15,
                stack: 'sum',
                itemStyle: {
                    normal: {
                        color: '#e97914'
                    }
                },
                data:fiveHundred,        //数据
            },{
                name: '220（330）kV及以下发布数',
                type: 'bar',
                barWidth:15,
                stack: 'sum',
                itemStyle: {
                    normal: {
                        color: '#36dde7'  //柱子颜色
                    }
                },
                data:Twohundred,        //数据
            }
            ]
        };
        //停电事由
        self.option4 ={
            title: {
                text: '各单位电网风险停电事由分布情况',
                left:'20%',
                top: "15",
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                }
            },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                right:'13%',
                top: '3%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },
                data: ['电网基建', '电厂客户', '市政配合', '技能工程','设备检修'],
            },
            grid: { //图表的位置
                // top: '20%',
                // left: '3%',
                // right: '4%',
                // bottom: '5%',
                // containLabel: true
                right: '2%',
                left:'2%'
            },
            yAxis: [{
                minInterval:1,
                type: 'value',
                textStyle: {
                    fontSize: 16,
                    color: 'red'
                },
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    formatter: '{value} ',
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                splitLine: {
                    show: false,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                show: true

            }],
            xAxis: [{
                type: 'category',
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontSize: 16,
                        color: 'white'
                    },

                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                data: capitalconstructionCity,
            }],
            series: [{
                name: '电网基建',
                color:'#388BFF',
                type: 'bar',
                stack: 'sum',
                barWidth: '20px',
                data:capitalconstruction

            },
                {
                    name: '电厂客户',
                    color:'#05C3FA',
                    type: 'bar',
                    barWidth: '20px',
                    stack: 'sum',
                    data:PowerPlantCustomers
                },
                {
                    name: '市政配合',
                    type: 'bar',
                    color: '#F6931C',
                    stack: 'sum',
                    barWidth: '20px',
                    data:MunicipalCooperate

                },
                {
                    name: '技能工程',
                    type: 'bar',
                    color: '#FFD52E',
                    stack: 'sum',
                    barWidth: '20px',
                    data: project

                },
                {
                    name: '设备检修',
                    type: 'bar',
                    color: '#36dde7',
                    stack: 'sum',
                    barWidth: '20px',
                    data: facilitymaintenance

                },
            ]
        };
        //停电设备
        self.option5 ={
            title: {
                text: '各单位电网风险电压等级分布情况',
                textStyle: {
                    align: 'center',
                    color: '#fff',
                    fontSize: 18,
                },
                top: '15',
                left: '20%',
            },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                right:'24%',
                top: '4%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },

                data: ['直流', '母线', '交流线路', '其他','主变'],
            },
            grid: { //图表的位置
                // top: '20%',
                // left: '3%',
                // right: '4%',
                // bottom: '5%',
                // containLabel: true
                right: '2%',
                left:'2%'
            },
            yAxis: [{
                minInterval:1,
                type: 'value',
                textStyle: {
                    fontSize: 16,
                    color: 'red'
                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    formatter: '{value} ',
                    textStyle: {
                        color: '#fff'
                    }
                },
                splitLine: {
                    show: false,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                show: true

            }],
            xAxis: [{
                type: 'category',
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontSize: 16,
                        color: 'white'
                    },

                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                data: equipmentCity,
            }],
            series: [{
                name: '直流',
                color:'#388BFF',
                type: 'bar',
                stack: 'sum',
                barWidth: '20px',
                data:riskdevzl_cnt,

            },

                {
                    name: '母线',
                    color:'#05C3FA',
                    type: 'bar',
                    barWidth: '20px',
                    stack: 'sum',
                    data:riskdevmx_cnt,

                },
                {
                    name: '交流线路',
                    type: 'bar',
                    color: '#F6931C',
                    stack: 'sum',
                    barWidth: '20px',
                    data: riskdevjl_cnt

                },
                {
                    name: '其他',
                    type: 'bar',
                    color: '#FFD52E',
                    stack: 'sum',
                    barWidth: '20px',
                    data: riskdevqt_cnt

                },
                {
                    name: '主变',
                    type: 'bar',
                    color: '#36dde7',
                    stack: 'sum',
                    barWidth: '20px',
                    data: riskdevzb_cnt

                },
            ]
        };
        //预警事件持续时间
        self.option6 ={
            title: {
                text: '各单位电网风险预警事件持续时间分布情况',
                left:'20%',
                top: "2",
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                }
            },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                right:'24%',
                top: '6%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },
                data: ['持续时间≤1天', '1天＜持续时间≤3天','3天＜持续时间≤7天', '7天＜持续时间≤15天','15天＜持续时间≤30天','30天＜持续时间'],
            },
            grid: { //图表的位置
                // top: '20%',
                // left: '3%',
                // right: '4%',
                // bottom: '5%',
                // containLabel: true
                right: '2%',
                left:'2%'
            },
            yAxis: [{
                minInterval:1,
                type: 'value',
                textStyle: {
                    fontSize: 16,
                    color: 'red'
                },
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    formatter: '{value} ',
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                splitLine: {
                    show: false,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                show: true

            }],
            xAxis: [{
                type: 'category',
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontSize: 16,
                        color: 'white'
                    },

                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                data:sustainCity

            }],
            series: [{
                name: '7天＜持续时间≤15天',
                color:'#388BFF',
                type: 'bar',
                stack: 'sum',
                barWidth: '20px',
                data:actualday15_cnt,

            },
                {
                    name: '30天＜持续时间',
                    color:'#05C3FA',
                    type: 'bar',
                    barWidth: '20px',
                    stack: 'sum',
                    data:actualday31_cnt,

                },



                {
                    name: '15天＜持续时间≤30天',
                    type: 'bar',
                    color: '#F6931C',
                    stack: 'sum1',
                    barWidth: '20px',
                    data: actualday30_cnt

                },
                {
                    name: '持续时间≤1天',
                    type: 'bar',
                    color: '#FFD52E',
                    stack: 'sum1',
                    barWidth: '20px',
                    data:  actualday1_cnt

                },
                {
                    name: '1天＜持续时间≤3天',
                    type: 'bar',
                    color: '#36dde7',
                    stack: 'sum1',
                    barWidth: '20px',
                    data: actualday3_cnt

                },
                {
                    name: '3天＜持续时间≤7天',
                    type: 'bar',
                    color: '#675bba',
                    stack: 'sum1',
                    barWidth: '20px',
                    data: actualday7_cnt

                },
            ]
        };
        //预警影响类型
        self.option7 ={
            title: {
                text: '各单位电网风险预警事件停电影响类型分布情况',
                left:'20%',
                top: "13",
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                }
            },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                left:'44%',
                top: '7%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },

                data: ['主变N-1', '母线N-1','机组N-1', '线路N-1','线路N-2','其它','线路N-3'],
            },
            grid: { //图表的位置
                // top: '20%',
                left: '2%',
                right: '2%',
                // bottom: '5%',
                // containLabel: true
            },
            yAxis: [{
                minInterval:1,
                type: 'value',
                textStyle: {
                    fontSize: 16,
                    color: 'red'
                },
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    formatter: '{value} ',
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },
                splitLine: {
                    show: false,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                show: true

            }],
            xAxis: [{
                type: 'category',
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                    textStyle: {
                        fontSize: 16,
                        color: 'white'
                    },

                },
                axisLine: {
                    lineStyle: {
                        color: '#5793f3',
                        width: 3, //这里是为了突出显示加上的
                    }
                },

                data:affectCity
            }],
            series: [{
                name: '母线N-1',
                color:'#388BFF',
                type: 'bar',
                stack: 'sum',
                barWidth: '20px',
                data:warndevtypemxn1_cnt,

            },
                {
                    name: '线路N-1',
                    color:'#05C3FA',
                    type: 'bar',
                    barWidth: '20px',
                    stack: 'sum',
                    data:warndevtypexln1_cnt

                },
                {
                    name: '主变N-1',
                    type: 'bar',
                    color: '#F6931C',
                    stack: 'sum',
                    barWidth: '20px',
                    data: warndevtypezbn1_cnt

                },
                {
                    name: '线路N-2',
                    type: 'bar',
                    color: '#FFD52E',
                    stack: 'sum',
                    barWidth: '20px',
                    data: warndevtypelxn2_cnt

                },
                {
                    name: '其他',
                    type: 'bar',
                    color: '#36dde7',
                    stack: 'sum',
                    barWidth: '20px',
                    data: warndevtypeqt_cnt

                },
                {
                    name: '机组N-1',
                    type: 'bar',
                    color: '#675bba',
                    stack: 'sum',
                    barWidth: '20px',
                    data: warndevtypejzn1_cnt
                },
                    {
                        name: '线路N-3',
                            type: 'bar',
                        color: '#675bba',
                        stack: 'sum',
                        barWidth: '20px',
                        data: warndevtypelxn3_cnt
                    },
            ]
        };
        //评价指标统计情况
        self.option8 = {
            color: colors,
            title: {
                text: '各单位电网风险评价指标统计情况',
                left:'40%',
                top: "5",
                bottom:'10',
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'cross'}
            },
            grid: {
                right: '1%',
                left:'2%'
            },
            legend: {
                right:'4%',
                top: '2%',
                data:['预警总数','发布及时数','抽检资料份数','合格份数','及时率','资料完备率'],
                textStyle: {
                    color: 'white'                              // 图例文字颜色
                }
            },
            xAxis: [
                {
                    type: 'category',
                    axisTick: {
                        alignWithLabel: false
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#5793f3',
                            width: 3, //这里是为了突出显示加上的
                        }
                    },
                    data: ['北京','天津','河北','冀北','山西','山东','上海','江苏','浙江','安徽','福建','湖北','湖南','河南','江西','辽宁','吉林','黑龙江','孟东','陕西','甘肃','宁夏','新疆','四川','重庆','西藏']
                },

            ],
            yAxis: [
                {
                    type: 'value',
                    name: '预警数',
                    min: 0,
                    max: 250,
                    position: 'right',
                    axisLine: {
                        lineStyle: {
                            color: '#5793f3'
                        },
                    },
                    axisLabel: {
                        formatter: '{value}'
                    }
                },
                {
                    type: 'value',
                    name: '',
                    min: 0,
                    max: 250,
                    position: 'right',
                    offset:1,
                    axisLine: {
                        lineStyle: {
                            color: '#5793f3'
                        }
                    },
                    axisLabel: {
                        formatter: '{value}'
                    }
                },
                {
                    type: 'value',
                    name: '及时率',
                    min: 0,
                    max: 25,
                    position: 'left',
                    axisLine: {
                        lineStyle: {
                            color:'#5793f3'
                        }
                    },
                    // axisLabel: {
                    //     formatter: '{value}'
                    // }
                }
            ],
            // axisLine: {
            //     lineStyle: {
            //         color: 'white',
            //         width: 1, //这里是为了突出显示加上的
            //     }
            // },
            series: [
                {
                    name:'预警总数',
                    type:'bar',
                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,3,5]
                },
                {
                    name:'发布及时数',
                    type:'bar',
                    yAxisIndex: 1,
                    data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3,2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2,3,4]
                },
                {
                    name:'抽检资料份数',
                    type:'bar',
                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,3,5,4.6]
                },
                {
                    name:'合格份数',
                    type:'bar',
                    color: '#FFD52E',
                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,3,5]
                },
                {
                    name:'及时率',
                    type:'line',
                    yAxisIndex: 2,
                    data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2,2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2,6,5]
                },
                {
                    name:'资料完备率',
                    type:'line',
                    yAxisIndex: 2,
                    data:[5,3, 6, 6, 2, 10, 12, 21.4, 20.0, 14.5, 11.0, 5.2,2.9,3.2, 5.3, 6.5, 6.3, 10.2, 6, 7, 5, 9, 8, 7,6]
                }
            ]
        };
        self.items=[];
        //分页
        self.size = 'middle';
        self.showArrow = true;
        self.showTotal = true;
        self.showGoto = true;
        self.showAllPage = false;
        self.count ='';
        self.pageTotalCount = '';
        self.pageIndex =ko.observable(1);
        self.pageVisibleCount = 1;
        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                  RiskList()
            }
        }

        // self.trBackground={column: "personName",backgroundColor:'#093f9a',value: '标题',};
        self.style ="background:#cccccc;";
        self.isShowRowNumber = true;
        self.isShowCheckBox = false;
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


        self.onSelectedItems = function(e) {
            selectedItems = e;
        };
        self.diaHeight = document.documentElement.clientHeight *1+ 'px';
        var work_plan=[]

        self.columns = [
            // {name: "id",  readOnly:true ,caption: "" ,editorType: "TextEditor",},
            {name: "title",  width: "15%", readOnly:true, caption: "标题" , editorType: "TextEditor",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (pkValue, cellValue) {
                    self.goDetail(pkValue);
                }},
            {name: "warnnum", width: "10%", readOnly:true, caption: "预警编号" , editorType: "TextEditor",align : "center",},
            {name: "warninglevel", width: "5%", caption: "预警等级" , editorType: "TextEditor",align : "center",},
            {name: "warnbegintime",width: "10%", caption: "预警计划开始时间" , editorType: "TextEditor",align : "center",},
            {name: "warnendtime", width: "10%", caption: "预警计划解除时间" , editorType: "TextEditor",align : "center",},
            {name: "datareport_org", width: "10%", caption: "所属省份" , editorType: "TextEditor",align : "center",},
            {name: "publish_staff",width: "10%",caption: "发布人员" , editorType: "TextEditor",align : "center",},
            {name: "warnstatus", width: "5%", caption: "状态" , editorType: "TextEditor",align : "center",},
            {name: "work_plan_code_day_cnt", width: "10%", caption: "关联作业数" , editorType: "TextEditor",align : "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                },
                onClick: function (id,cellValue) {
                    // console.log(cellValue)
                    if (cellValue!==0){


                        var arr = self.items(), item = [];
                        var title = '作业计划信息'
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i]['gridwarnnotice_id'] == id) {
                                item = arr[i];
                            }
                        }
                        cube.showDialog({
                            title: title,
                            width: "90%",
                            height: self.diaHeight,
                            submitFormOnConfirm: false,
                            isShowCloseBtn: false, // 是否显示取消按钮
                            isShowConfirmBtn: false, // 是否显示保存按钮
                            templateOptions: {
                                name: 'operationRisk.detail.main',
                                params: {item: item}
                            },
                        });

                    }else{
                        cube.indicate("未有相关作业计划");
                    }
                }
            },
        ];

         var listdata=[]
            //标题查看
        self.goDetail = function (gridwarnnotice_id) {
             console.log(gridwarnnotice_id)
            self.page('edit')
            $(".requisition").css("background", "#1b6b9d");
                axios.get(cube.gatewayURL +'riskInfos/gridWarnContent?str=' +gridwarnnotice_id).then(function(res) {
                    if (res.data.resultValue) {

                        var requisition=res.data.resultValue.srpRiskGridwarnnotice[0] //预警通知单

                        var feedback=res.data.resultValue.srpRiskGridwarnfeedback[0]//反馈单
                        console.log(res.data.resultValue.srpRiskGridwarnfeedback[0])
                        var report=res.data.resultValue.srpRiskGridwarnreport[0]//报告单
                        var notifys=res.data.resultValue.srpRiskGridwarninform[0]//告知单
                        var relieve=res.data.resultValue.srpRiskGridwarnrelinfo[0]//预警解除

                        self.title(requisition.title)
                        self.number(requisition.warnnum)
                        self.hierarchy(requisition.warninglevel)
                        self.startTime(requisition.warnbegintime)
                        self.endtTime(requisition.warnendtime)
                        self.monad(requisition.publish_org)
                        self.staff(requisition.publish_staff)
                        self.status(requisition.warnstatus)
                        //预警实施单

                          self.exploitingsNum(requisition.warnnum)//预警编号
                        self.exploitingsPowercut(requisition.riskdev)//停电设备
                        self.exploitingsMatter(requisition.warncontents)//预警事由
                        self.exploitingsclass(requisition.warninglevel)//预警等级
                        self.exploitingsTimeframe(requisition.warnbegintime+'至'+requisition.warnendtime)//预警时段
                        // self.exploitingsunit(feedback.mainsend)//主送部门
                        // self.exploitingsmonad(report.up_dept)//责任单位

                        self.informNum(requisition.warnnum)//预警编号
                        self.informUnit(requisition.publish_org);//发布单位
                        self.informData(requisition.warnbegintime)//预警日期
                        self.informPowercut(requisition.riskdev)//停电设备
                        self.informMatter(requisition.warncontents)//预警事由
                        self.informTimeframe(requisition.warnbegintime+'至'+requisition.warnendtime)//预警时段
                        self.informgrade(requisition.warninglevel)//风险等级
                        self.informbranch(requisition.mainsend)//主送部门
                        self.informaccountability(requisition.responsible_org)//责任单位
                        self.informanalyze(requisition.riskanalysis)//风险分析
                        self.informrequire(requisition.riskmeasure)//管控措施及要求
                        self.informdepartment(requisition.countersign_dep)//签收部门
                        self.informauthorizedStrength(requisition.editor)//编制
                        self.annex(requisition.annex)//附件

                        //预警反馈单
                        if(res.data.resultValue.srpRiskGridwarnfeedback.length===''){
                            self.feedbackNum()//预警编号
                            self.feedbackUnit()//反馈单位
                            // self.feedbackTime(feedback.feedback_time)//反馈日期
                            self.feedbackoddNum()//预警单编号
                            self.feedbackaudit()//审核
                            self.feedbackratify()//批准
                            self.feedbackTimeframe()//预警时段
                            self.feedbacksent()//主送单位
                            self.feedbackrequire()//管控措施落实情况
                            self.feedbackauthorizedStrength()//编制
                            self.annex()//附件
                        }
                        self.feedbackNum(feedback.warnnum)//预警编号
                        self.feedbackUnit(requisition.feedback_org)//反馈单位
                        // self.feedbackTime(feedback.feedback_time)//反馈日期
                        self.feedbackoddNum(feedback.feedbacknum)//预警单编号
                        self.feedbackaudit(feedback.auditor)//审核
                        self.feedbackratify(feedback.appro_staff)//批准
                        self.feedbackTimeframe(feedback.warnbegintime+'至'+feedback.warnendtime)//预警时段
                        self.feedbacksent(feedback.mainsend)//主送单位
                        self.feedbackrequire(feedback.implementation)//管控措施落实情况
                        self.feedbackauthorizedStrength(feedback.editor)//编制
                        self.annex(feedback.annex)//附件
                        //预警报告单
                        self.reportNum(requisition.warnnum)//预警编号
                        self.reportTime(report.submit_time)//报送日期
                        self.reportunit(report.mainsend)//主送单位
                        self.reportTimeframe(report.warnbegintime+'至'+report.warnendtime)//预警时段
                        self.reportcondition(report.warncontents)//预警情况
                        self.reportscope(report.area)//涉及区域和影响范围
                        self.reportsuggest(report.forgovercoordiation)//需政府协调的事项建议
                        self.reportmonad(report.up_dept)//报告单位
                        self.reportName(report.phoneuser)//联系人
                        self.reportPhone(report.phonenumber)//联系电话
                        self.annex(report.annex)//附件

                        //预警告知单
                        self.notifysNum(notifys.informnum)//预警编号
                        self.notifysTime(notifys.submit_time)//报送日期
                        self.notifysmonad(notifys.delivery_dept)//送达单位
                        self.notifysMatter(notifys.warncontents)//预警事由
                        self.notifysTimeframe(notifys.warnbegintime+'至'+notifys.warnendtime)//预警时段
                        self.notifysanalyze(notifys.riskanalysis)//风险分析
                        self.notifysmeasure(notifys.riskmeasure)//电网风险管控措施
                        self.notifysunit(notifys.inform_dep)//告知单位
                        self.notifysName(notifys.phoneuser)//联系人
                        self.notifysPhone(notifys.phonenumber)//联系电话
                        self.annex(notifys.annex)//附件
                        //预警解除
                        self.relievesmessage(relieve.relinfonum)//解除信息
                        self.relievesyardman(relieve.warnremove_staff)//调度员
                        self.relievesphone(notifys.phonenumber)//联系方式
                        self.relievesTime(relieve.removedate)//解除时间
                        // console.log(res.data.resultValue.srpRiskGridwarnfeedback[0])
                    } else {
                        // cube.indicate("warning", res.data);
                        cube.indicate("数据加载失败");
                    }

                })


        }
        //当日
        self.day=function (flag) {
            $(".fabu").show();
            $(".yujing").hide();
            axios.get(cube.gatewayURL + "riskInfos/areaRiskCountInfo/0").then(function(res) {
                console.log()
                if ( res.data.successful === true) {
                    // self.informationStatistics(res.data)
                    var data=res.data.resultValue
                    console.log(data)
                    var pieoption = self.pieoption();
                    var cityName = [],powerGridSum = [],OperationalRiskNumber = [],BlackoutSchedule = [];
                    for (var i in data){
                        cityName.push(data[i].datareport_org)
                        powerGridSum.push(data[i].gridrisk_cnt) //电网风险数
                        OperationalRiskNumber.push(data[i].workrisk_cnt) //关联作业风险数
                        BlackoutSchedule.push(data[i].power_cnt)  //停电计划数

                        var pieoption = self.pieoption();
                        pieoption.xAxis.data = cityName;
                        pieoption.series[0].data = powerGridSum;
                        pieoption.series[1].data = OperationalRiskNumber;
                        pieoption.series[2].data = BlackoutSchedule;
                        self.pieoption(pieoption)
                    }
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCnt/0").then(function(res) {
                if ( res.data.successful === true) {
                    var data=res.data.resultValue
                    console.log(data)
                    self.risk(data.workRiskLevelCount.plan_cnt)
                    self.stair(res.data.resultValue.stateGridRiskLevelCount.level01);
                    self.second(res.data.resultValue.stateGridRiskLevelCount.level02);
                    self.three(res.data.resultValue.stateGridRiskLevelCount.level03);
                    self.four(res.data.resultValue.stateGridRiskLevelCount.level04);
                    self.five(res.data.resultValue.stateGridRiskLevelCount.level05);
                    self.six(res.data.resultValue.stateGridRiskLevelCount.level06);
                    self.relieve(res.data.resultValue.warnRiskCount.plan_cnt)
                    self.release(res.data.resultValue.planPublishCount.plan_cnt)
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            $(".day").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".tomorrow").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".week").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".month").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".year").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".aa").show();
        }
        //明日
        self.tomorrow=function (flag) {
            $(".fabu").hide();
            $(".yujing").show();
            // tomorrow()
            axios.get(cube.gatewayURL + "riskInfos/areaRiskCountInfo/1").then(function(res) {
                if ( res.data.successful === true) {
                    // self.informationStatistics(res.data)
                    var data=res.data.resultValue
                    var pieoption = self.pieoption();
                    var cityName = [],powerGridSum = [],OperationalRiskNumber = [],BlackoutSchedule = [];
                    for (var i in data){
                        cityName.push(data[i].datareport_org)
                        powerGridSum.push(data[i].gridrisk_cnt)
                        OperationalRiskNumber.push(data[i].workrisk_cnt)
                        BlackoutSchedule.push(data[i].power_cnt)

                        var pieoption = self.pieoption();
                        pieoption.xAxis.data = cityName;
                        pieoption.series[0].data = powerGridSum;
                        pieoption.series[1].data = OperationalRiskNumber;
                        pieoption.series[2].data = BlackoutSchedule;
                        self.pieoption(pieoption)
                    }
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCnt/1").then(function(res) {
                if ( res.data.successful === true) {
                    var data=res.data.resultValue
                    self.risk(data.workRiskLevelCount.plan_cnt)
                    self.stair(res.data.resultValue.stateGridRiskLevelCount.level01);
                    self.second(res.data.resultValue.stateGridRiskLevelCount.level02);
                    self.three(res.data.resultValue.stateGridRiskLevelCount.level03);
                    self.four(res.data.resultValue.stateGridRiskLevelCount.level04);
                    self.five(res.data.resultValue.stateGridRiskLevelCount.level05);
                    self.six(res.data.resultValue.stateGridRiskLevelCount.level06);
                    self.relieve(res.data.resultValue.warnRiskCount.plan_cnt)
                    self.release(res.data.resultValue.planPublishCount.plan_cnt)
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            $(".tomorrow").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".day").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".week").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".month").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".year").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".aa").hide();

        }
        //本周
        self.week=function (flag) {
            $(".fabu").show();
            $(".yujing").hide();
            axios.get(cube.gatewayURL + "riskInfos/areaRiskCountInfo/2").then(function(res) {
                if ( res.data.successful === true) {
                    // self.informationStatistics(res.data)
                    var data=res.data.resultValue
                    var pieoption = self.pieoption();
                    var cityName = [],powerGridSum = [],OperationalRiskNumber = [],BlackoutSchedule = [];
                    for (var i in data){
                        cityName.push(data[i].datareport_org)
                        powerGridSum.push(data[i].gridrisk_cnt)
                        OperationalRiskNumber.push(data[i].workrisk_cnt)
                        BlackoutSchedule.push(data[i].power_cnt)

                        var pieoption = self.pieoption();
                        pieoption.xAxis.data = cityName;
                        pieoption.series[0].data = powerGridSum;
                        pieoption.series[1].data = OperationalRiskNumber;
                        pieoption.series[2].data = BlackoutSchedule;
                        self.pieoption(pieoption)
                    }
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCnt/2").then(function(res) {
                if ( res.data.successful === true) {
                    var data=res.data.resultValue
                    self.risk(data.workRiskLevelCount.plan_cnt)
                    self.stair(res.data.resultValue.stateGridRiskLevelCount.level01);
                    self.second(res.data.resultValue.stateGridRiskLevelCount.level02);
                    self.three(res.data.resultValue.stateGridRiskLevelCount.level03);
                    self.four(res.data.resultValue.stateGridRiskLevelCount.level04);
                    self.five(res.data.resultValue.stateGridRiskLevelCount.level05);
                    self.six(res.data.resultValue.stateGridRiskLevelCount.level06);
                    self.relieve(res.data.resultValue.warnRiskCount.plan_cnt)
                    self.release(res.data.resultValue.planPublishCount.plan_cnt)
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            $(".week").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".day").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".tomorrow").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".month").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".year").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".aa").hide();
        }
        //本月
        self.month=function (flag) {
            $(".fabu").show();
            $(".yujing").hide();
            axios.get(cube.gatewayURL + "riskInfos/areaRiskCountInfo/3").then(function(res) {
                if ( res.data.successful === true) {
                    // self.informationStatistics(res.data)
                    var data=res.data.resultValue
                    var pieoption = self.pieoption();
                    var cityName = [],powerGridSum = [],OperationalRiskNumber = [],BlackoutSchedule = [];
                    for (var i in data){
                        cityName.push(data[i].datareport_org)
                        powerGridSum.push(data[i].gridrisk_cnt)
                        OperationalRiskNumber.push(data[i].workrisk_cnt)
                        BlackoutSchedule.push(data[i].power_cnt)

                        var pieoption = self.pieoption();
                        pieoption.xAxis.data = cityName;
                        pieoption.series[0].data = powerGridSum;
                        pieoption.series[1].data = OperationalRiskNumber;
                        pieoption.series[2].data = BlackoutSchedule;
                        self.pieoption(pieoption)
                    }
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCnt/3").then(function(res) {
                if ( res.data.successful === true) {
                    var data=res.data.resultValue
                    self.risk(data.workRiskLevelCount.plan_cnt)
                    self.stair(res.data.resultValue.stateGridRiskLevelCount.level01);
                    self.second(res.data.resultValue.stateGridRiskLevelCount.level02);
                    self.three(res.data.resultValue.stateGridRiskLevelCount.level03);
                    self.four(res.data.resultValue.stateGridRiskLevelCount.level04);
                    self.five(res.data.resultValue.stateGridRiskLevelCount.level05);
                    self.six(res.data.resultValue.stateGridRiskLevelCount.level06);
                    self.relieve(res.data.resultValue.warnRiskCount.plan_cnt)
                    self.release(res.data.resultValue.planPublishCount.plan_cnt)
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            $(".month").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".week").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".day").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".tomorrow").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".year").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".aa").hide();
        }
        //本年
        self.year=function (flag) {
            $(".fabu").show();
            $(".yujing").hide();
            axios.get(cube.gatewayURL + "riskInfos/areaRiskCountInfo/4").then(function(res) {
                if ( res.data.successful === true) {
                    // self.informationStatistics(res.data)
                    var data=res.data.resultValue
                    var pieoption = self.pieoption();
                    var cityName = [],powerGridSum = [],OperationalRiskNumber = [],BlackoutSchedule = [];
                    for (var i in data){
                        cityName.push(data[i].datareport_org)
                        powerGridSum.push(data[i].gridrisk_cnt)
                        OperationalRiskNumber.push(data[i].workrisk_cnt)
                        BlackoutSchedule.push(data[i].power_cnt)

                        var pieoption = self.pieoption();
                        pieoption.xAxis.data = cityName;
                        pieoption.series[0].data = powerGridSum;
                        pieoption.series[1].data = OperationalRiskNumber;
                        pieoption.series[2].data = BlackoutSchedule;
                        self.pieoption(pieoption)
                    }
                } else {

                    cube.indicate("数据加载失败");
                }

            })
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCnt/4").then(function(res) {
                if ( res.data.successful === true) {
                    var data=res.data.resultValue
                    self.risk(data.workRiskLevelCount.plan_cnt)
                    self.stair(res.data.resultValue.stateGridRiskLevelCount.level01);
                    self.second(res.data.resultValue.stateGridRiskLevelCount.level02);
                    self.three(res.data.resultValue.stateGridRiskLevelCount.level03);
                    self.four(res.data.resultValue.stateGridRiskLevelCount.level04);
                    self.five(res.data.resultValue.stateGridRiskLevelCount.level05);
                    self.six(res.data.resultValue.stateGridRiskLevelCount.level06);
                    self.relieve(res.data.resultValue.warnRiskCount.plan_cnt)
                    self.release(res.data.resultValue.planPublishCount.plan_cnt)
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
            $(".year").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".week").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".day").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".tomorrow").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".month").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".aa").hide();
        }

        //日
        self.gridDistributionday=function (flag) {
             //预警数量
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=0&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var earlyNum=res.data.resultValue
                    var option2 = self.option2();
                    var cityNameNumber = [],number1 = [],number2 = [],number3 = [],number4=[],number5=[],number6=[],number7=[],number8=[];
                    for (var i in earlyNum){
                        cityNameNumber.push(earlyNum[i].datareport_org)
                        number1.push(earlyNum[i].number01)
                        number2.push(earlyNum[i].number02)
                        number3.push(earlyNum[i].number03)
                        number4.push(earlyNum[i].number04)
                        number5.push(earlyNum[i].number05)
                        number6.push(earlyNum[i].number06)
                        number7.push(earlyNum[i].number07)
                        number8.push(earlyNum[i].number08)
                        var option2 = self.option2();
                        option2.xAxis.data = cityNameNumber;
                        option2.series[0].data = number1;
                        option2.series[1].data = number2;
                        option2.series[2].data = number3;
                        option2.series[3].data = number4;
                        option2.series[4].data = number5;
                        option2.series[5].data = number6;
                        option2.series[6].data = number7;
                        option2.series[7].data = number8;

                        self.option2(option2)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //电压等级
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=1&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option3= self.option3();
                    var PowercityNames = [],fiveHundred = [],Twohundred = [];
                    for (var i in list){
                        PowercityNames.push(list[i].datareport_org)
                        fiveHundred.push(list[i].voltage3_cnt)
                        Twohundred.push(list[i].voltage5_cnt)

                        var option3 = self.option3();
                        option3.xAxis.data = PowercityNames;
                        option3.series[0].data = fiveHundred;
                        option3.series[1].data = Twohundred;

                        self.option3(option3)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电事由
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=2&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var list=res.data.resultValue
                    var option4= self.option4();
                    var capitalconstructionCity = [],capitalconstruction = [],project = [],facilitymaintenance = [],PowerPlantCustomers = [],MunicipalCooperate = [];

                    for (var i in list){
                        capitalconstructionCity.push(list[i].datareport_org)
                        capitalconstruction.push(list[i].dwjj_cnt)//电网基建
                        project.push(list[i].jggc_cnt)//技改工程
                        facilitymaintenance.push(list[i].sbjx_cnt)//设备检修
                        PowerPlantCustomers.push(list[i].dckh_cnt)//电厂客户
                        MunicipalCooperate.push(list[i].szph_cnt)//市政配合

                        var option4 = self.option4();
                        option4.xAxis.data = capitalconstructionCity;
                        option4.series[0].data = capitalconstruction;
                        option4.series[3].data = project;
                        option4.series[4].data = facilitymaintenance;
                        option4.series[1].data = PowerPlantCustomers;
                        option4.series[2].data = MunicipalCooperate;

                        self.option4(option4)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电设备
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=3&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var PowerShe=res.data.resultValue
                    var option5= self.option5();
                    var equipmentCity = [],riskdevzl_cnt = [],riskdevmx_cnt = [],riskdevjl_cnt = [],riskdevqt_cnt = [],riskdevzb_cnt = [];
                    for (var i in PowerShe){
                        equipmentCity.push(PowerShe[i].datareport_org)
                        riskdevzl_cnt.push(PowerShe[i].riskdevzl_cnt)//直流
                        riskdevmx_cnt.push(PowerShe[i].riskdevmx_cnt)//母线
                        riskdevjl_cnt.push(PowerShe[i].riskdevjl_cnt)//交流线路
                        riskdevqt_cnt.push(PowerShe[i].riskdevqt_cnt)//其他
                        riskdevzb_cnt.push(PowerShe[i].riskdevzb_cnt)//主变

                        var option5 = self.option5();
                        option5.xAxis.data = equipmentCity;
                        option5.series[0].data = riskdevzl_cnt;
                        option5.series[1].data = riskdevmx_cnt;
                        option5.series[2].data = riskdevjl_cnt;
                        option5.series[3].data = riskdevqt_cnt;
                        option5.series[4].data = riskdevzb_cnt;

                        self.option5(option5)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警事件持续时间
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=4&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option6= self.option6();
                    var sustainCity = [],actualday15_cnt = [],actualday31_cnt = [],actualday30_cnt = [],actualday1_cnt = [],actualday3_cnt = [],actualday7_cnt = [];
                    for (var i in list){
                        sustainCity.push(list[i].datareport_org)
                        actualday15_cnt.push(list[i].actualday15_cnt)//7天＜持续时间≤15天
                        actualday31_cnt.push(list[i].actualday31_cnt)//30天＜持续时间
                        actualday30_cnt.push(list[i].actualday30_cnt)//15天＜持续时间≤30天
                        actualday1_cnt.push(list[i].actualday1_cnt)//持续时间≤1天
                        actualday3_cnt.push(list[i].actualday3_cnt)//1天＜持续时间≤3天
                        actualday7_cnt.push(list[i].actualday7_cnt)//3天＜持续时间≤7天

                        var option6 = self.option6();
                        option6.xAxis.data = sustainCity;
                        option6.series[0].data = actualday15_cnt;
                        option6.series[1].data = actualday31_cnt;
                        option6.series[2].data = actualday30_cnt;
                        option6.series[3].data = actualday1_cnt;
                        option6.series[4].data = actualday3_cnt;
                        option6.series[5].data = actualday7_cnt;
                        self.option6(option6)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警影响类型
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=5&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option7= self.option7();
                    var affectCity = [],warndevtypemxn1_cnt = [],warndevtypexln1_cnt = [],warndevtypezbn1_cnt = [],warndevtypelxn2_cnt = [],warndevtypeqt_cnt = [],warndevtypejzn1_cnt = [],warndevtypelxn3_cnt = [];
                    for (var i in list){
                        affectCity.push(list[i].datareport_org)
                        warndevtypemxn1_cnt.push(list[i].warndevtypemxn1_cnt)//母线N-1
                        warndevtypexln1_cnt.push(list[i].warndevtypexln1_cnt)//线路N-1
                        warndevtypezbn1_cnt.push(list[i].warndevtypezbn1_cnt)//主变N-1
                        warndevtypelxn2_cnt.push(list[i].warndevtypelxn2_cnt)//线路N-2
                        warndevtypeqt_cnt.push(list[i].warndevtypeqt_cnt)//其他
                        warndevtypejzn1_cnt.push(list[i].warndevtypejzn1_cnt)//机组N-1
                        warndevtypelxn3_cnt.push(list[i].warndevtypelxn3_cnt)//线路N-3
                        var option7 = self.option7();
                        option7.xAxis.data = sustainCity;
                        option7.series[0].data = warndevtypemxn1_cnt;
                        option7.series[1].data = warndevtypexln1_cnt;
                        option7.series[2].data = warndevtypezbn1_cnt;
                        option7.series[3].data = warndevtypelxn2_cnt;
                        option7.series[4].data = warndevtypeqt_cnt;
                        option7.series[5].data = warndevtypejzn1_cnt;
                        option7.series[6].data = warndevtypelxn3_cnt;
                        self.option7(option7)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".Distributionday").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".Distributionweek").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionmonth").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionyear").css("background-image", "url('../resources/resources/images/u1134.png')");
        }
        //周
        self.Distributionweek=function (flag) {
            //预警数量
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=0&timeType=2&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var earlyNum=res.data.resultValue
                    var option2 = self.option2();
                    var cityNameNumber = [],number1 = [],number2 = [],number3 = [],number4=[],number5=[],number6=[],number7=[],number8=[];
                    for (var i in earlyNum){
                        cityNameNumber.push(earlyNum[i].datareport_org)
                        number1.push(earlyNum[i].number01)
                        number2.push(earlyNum[i].number02)
                        number3.push(earlyNum[i].number03)
                        number4.push(earlyNum[i].number04)
                        number5.push(earlyNum[i].number05)
                        number6.push(earlyNum[i].number06)
                        number7.push(earlyNum[i].number07)
                        number8.push(earlyNum[i].number08)
                    }
                    option2.xAxis[0].data = cityNameNumber;
                    option2.series[0].data = number1;
                    option2.series[1].data = number2;
                    option2.series[2].data = number3;
                    option2.series[3].data = number4;
                    option2.series[4].data = number5;
                    option2.series[5].data = number6;
                    option2.series[6].data = number7;
                    option2.series[7].data = number8;
                    self.option2(option2)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //电压等级
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=1&timeType=2&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option3= self.option3();
                    var PowercityNames = [],fiveHundred = [],Twohundred = [];
                    for (var i in list){
                        PowercityNames.push(list[i].datareport_org)
                        fiveHundred.push(list[i].voltage3_cnt)
                        Twohundred.push(list[i].voltage5_cnt)

                        var option3 = self.option3();
                        option3.xAxis.data = PowercityNames;
                        option3.series[0].data = fiveHundred;
                        option3.series[1].data = Twohundred;

                        self.option3(option3)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电事由
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=2&timeType=2&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option4= self.option4();
                    var capitalconstructionCity = [],capitalconstruction = [],project = [],facilitymaintenance = [],PowerPlantCustomers = [],MunicipalCooperate = [];

                    for (var i in list){
                        capitalconstructionCity.push(list[i].datareport_org)
                        capitalconstruction.push(list[i].dwjj_cnt)//电网基建
                        project.push(list[i].jggc_cnt)//技改工程
                        facilitymaintenance.push(list[i].sbjx_cnt)//设备检修
                        PowerPlantCustomers.push(list[i].dckh_cnt)//电厂客户
                        MunicipalCooperate.push(list[i].szph_cnt)//市政配合

                        var option4 = self.option4();
                        option4.xAxis.data = capitalconstructionCity;
                        option4.series[0].data = capitalconstruction;
                        option4.series[3].data = project;
                        option4.series[4].data = facilitymaintenance;
                        option4.series[1].data = PowerPlantCustomers;
                        option4.series[2].data = MunicipalCooperate;

                        self.option4(option4)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电设备
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=3&timeType=2&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var PowerShe=res.data.resultValue
                    var option5= self.option5();
                    var equipmentCity = [],riskdevzl_cnt = [],riskdevmx_cnt = [],riskdevjl_cnt = [],riskdevqt_cnt = [],riskdevzb_cnt = [];
                    for (var i in PowerShe){
                        equipmentCity.push(PowerShe[i].datareport_org)
                        riskdevzl_cnt.push(PowerShe[i].riskdevzl_cnt)//直流
                        riskdevmx_cnt.push(PowerShe[i].riskdevmx_cnt)//母线
                        riskdevjl_cnt.push(PowerShe[i].riskdevjl_cnt)//交流线路
                        riskdevqt_cnt.push(PowerShe[i].riskdevqt_cnt)//其他
                        riskdevzb_cnt.push(PowerShe[i].riskdevzb_cnt)//主变

                        var option5 = self.option5();
                        option5.xAxis.data = equipmentCity;
                        option5.series[0].data = riskdevzl_cnt;
                        option5.series[1].data = riskdevmx_cnt;
                        option5.series[2].data = riskdevjl_cnt;
                        option5.series[3].data = riskdevqt_cnt;
                        option5.series[4].data = riskdevzb_cnt;

                        self.option5(option5)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警事件持续时间
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=4&timeType=2&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option6= self.option6();
                    var sustainCity = [],actualday15_cnt = [],actualday31_cnt = [],actualday30_cnt = [],actualday1_cnt = [],actualday3_cnt = [],actualday7_cnt = [];
                    for (var i in list){
                        sustainCity.push(list[i].datareport_org)
                        actualday15_cnt.push(list[i].actualday15_cnt)//7天＜持续时间≤15天
                        actualday31_cnt.push(list[i].actualday31_cnt)//30天＜持续时间
                        actualday30_cnt.push(list[i].actualday30_cnt)//15天＜持续时间≤30天
                        actualday1_cnt.push(list[i].actualday1_cnt)//持续时间≤1天
                        actualday3_cnt.push(list[i].actualday3_cnt)//1天＜持续时间≤3天
                        actualday7_cnt.push(list[i].actualday7_cnt)//3天＜持续时间≤7天

                        var option6 = self.option6();
                        option6.xAxis.data = sustainCity;
                        option6.series[0].data = actualday15_cnt;
                        option6.series[1].data = actualday31_cnt;
                        option6.series[2].data = actualday30_cnt;
                        option6.series[3].data = actualday1_cnt;
                        option6.series[4].data = actualday3_cnt;
                        option6.series[5].data = actualday7_cnt;
                        self.option6(option6)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警影响类型
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=5&timeType=2&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option7= self.option7();
                    var affectCity = [],warndevtypemxn1_cnt = [],warndevtypexln1_cnt = [],warndevtypezbn1_cnt = [],warndevtypelxn2_cnt = [],warndevtypeqt_cnt = [],warndevtypejzn1_cnt = [],warndevtypelxn3_cnt = [];
                    for (var i in list){
                        affectCity.push(list[i].datareport_org)
                        warndevtypemxn1_cnt.push(list[i].warndevtypemxn1_cnt)//母线N-1
                        warndevtypexln1_cnt.push(list[i].warndevtypexln1_cnt)//线路N-1
                        warndevtypezbn1_cnt.push(list[i].warndevtypezbn1_cnt)//主变N-1
                        warndevtypelxn2_cnt.push(list[i].warndevtypelxn2_cnt)//线路N-2
                        warndevtypeqt_cnt.push(list[i].warndevtypeqt_cnt)//其他
                        warndevtypejzn1_cnt.push(list[i].warndevtypejzn1_cnt)//机组N-1
                        warndevtypelxn3_cnt.push(list[i].warndevtypelxn3_cnt)//线路N-3
                        var option7 = self.option7();
                        option7.xAxis.data = sustainCity;
                        option7.series[0].data = warndevtypemxn1_cnt;
                        option7.series[1].data = warndevtypexln1_cnt;
                        option7.series[2].data = warndevtypezbn1_cnt;
                        option7.series[3].data = warndevtypelxn2_cnt;
                        option7.series[4].data = warndevtypeqt_cnt;
                        option7.series[5].data = warndevtypejzn1_cnt;
                        option7.series[6].data = warndevtypelxn3_cnt;
                        self.option7(option7)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".Distributionweek").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".Distributionday").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionmonth").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionyear").css("background-image", "url('../resources/resources/images/u1134.png')");
        }
        //月
        self.Distributionmonth=function (flag) {
            //预警数量
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=0&timeType=3&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var earlyNum=res.data.resultValue
                    var option2 = self.option2();
                    var cityNameNumber = [],number1 = [],number2 = [],number3 = [],number4=[],number5=[],number6=[],number7=[],number8=[];
                    for (var i in earlyNum){
                        cityNameNumber.push(earlyNum[i].datareport_org)
                        number1.push(earlyNum[i].number01)
                        number2.push(earlyNum[i].number02)
                        number3.push(earlyNum[i].number03)
                        number4.push(earlyNum[i].number04)
                        number5.push(earlyNum[i].number05)
                        number6.push(earlyNum[i].number06)
                        number7.push(earlyNum[i].number07)
                        number8.push(earlyNum[i].number08)
                    }
                    option2.xAxis[0].data = cityNameNumber;
                    option2.series[0].data = number1;
                    option2.series[1].data = number2;
                    option2.series[2].data = number3;
                    option2.series[3].data = number4;
                    option2.series[4].data = number5;
                    option2.series[5].data = number6;
                    option2.series[6].data = number7;
                    option2.series[7].data = number8;
                    self.option2(option2)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //电压等级
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=1&timeType=3&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option3= self.option3();
                    var PowercityNames = [],fiveHundred = [],Twohundred = [];
                    for (var i in list){
                        PowercityNames.push(list[i].datareport_org)
                        fiveHundred.push(list[i].voltage3_cnt)
                        Twohundred.push(list[i].voltage5_cnt)

                        var option3 = self.option3();
                        option3.xAxis.data = PowercityNames;
                        option3.series[0].data = fiveHundred;
                        option3.series[1].data = Twohundred;

                        self.option3(option3)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电事由
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=2&timeType=3&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option4= self.option4();
                    var capitalconstructionCity = [],capitalconstruction = [],project = [],facilitymaintenance = [],PowerPlantCustomers = [],MunicipalCooperate = [];

                    for (var i in list){
                        capitalconstructionCity.push(list[i].datareport_org)
                        capitalconstruction.push(list[i].dwjj_cnt)//电网基建
                        project.push(list[i].jggc_cnt)//技改工程
                        facilitymaintenance.push(list[i].sbjx_cnt)//设备检修
                        PowerPlantCustomers.push(list[i].dckh_cnt)//电厂客户
                        MunicipalCooperate.push(list[i].szph_cnt)//市政配合

                        var option4 = self.option4();
                        option4.xAxis.data = capitalconstructionCity;
                        option4.series[0].data = capitalconstruction;
                        option4.series[3].data = project;
                        option4.series[4].data = facilitymaintenance;
                        option4.series[1].data = PowerPlantCustomers;
                        option4.series[2].data = MunicipalCooperate;

                        self.option4(option4)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电设备
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=3&timeType=3&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var PowerShe=res.data.resultValue
                    var option5= self.option5();
                    var equipmentCity = [],riskdevzl_cnt = [],riskdevmx_cnt = [],riskdevjl_cnt = [],riskdevqt_cnt = [],riskdevzb_cnt = [];
                    for (var i in PowerShe){
                        equipmentCity.push(PowerShe[i].datareport_org)
                        riskdevzl_cnt.push(PowerShe[i].riskdevzl_cnt)//直流
                        riskdevmx_cnt.push(PowerShe[i].riskdevmx_cnt)//母线
                        riskdevjl_cnt.push(PowerShe[i].riskdevjl_cnt)//交流线路
                        riskdevqt_cnt.push(PowerShe[i].riskdevqt_cnt)//其他
                        riskdevzb_cnt.push(PowerShe[i].riskdevzb_cnt)//主变

                        var option5 = self.option5();
                        option5.xAxis.data = equipmentCity;
                        option5.series[0].data = riskdevzl_cnt;
                        option5.series[1].data = riskdevmx_cnt;
                        option5.series[2].data = riskdevjl_cnt;
                        option5.series[3].data = riskdevqt_cnt;
                        option5.series[4].data = riskdevzb_cnt;

                        self.option5(option5)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警事件持续时间
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=4&timeType=3&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option6= self.option6();
                    var sustainCity = [],actualday15_cnt = [],actualday31_cnt = [],actualday30_cnt = [],actualday1_cnt = [],actualday3_cnt = [],actualday7_cnt = [];
                    for (var i in list){
                        sustainCity.push(list[i].datareport_org)
                        actualday15_cnt.push(list[i].actualday15_cnt)//7天＜持续时间≤15天
                        actualday31_cnt.push(list[i].actualday31_cnt)//30天＜持续时间
                        actualday30_cnt.push(list[i].actualday30_cnt)//15天＜持续时间≤30天
                        actualday1_cnt.push(list[i].actualday1_cnt)//持续时间≤1天
                        actualday3_cnt.push(list[i].actualday3_cnt)//1天＜持续时间≤3天
                        actualday7_cnt.push(list[i].actualday7_cnt)//3天＜持续时间≤7天

                        var option6 = self.option6();
                        option6.xAxis.data = sustainCity;
                        option6.series[0].data = actualday15_cnt;
                        option6.series[1].data = actualday31_cnt;
                        option6.series[2].data = actualday30_cnt;
                        option6.series[3].data = actualday1_cnt;
                        option6.series[4].data = actualday3_cnt;
                        option6.series[5].data = actualday7_cnt;
                        self.option6(option6)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警影响类型
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=5&timeType=3&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option7= self.option7();
                    var affectCity = [],warndevtypemxn1_cnt = [],warndevtypexln1_cnt = [],warndevtypezbn1_cnt = [],warndevtypelxn2_cnt = [],warndevtypeqt_cnt = [],warndevtypejzn1_cnt = [],warndevtypelxn3_cnt = [];
                    for (var i in list){
                        affectCity.push(list[i].datareport_org)
                        warndevtypemxn1_cnt.push(list[i].warndevtypemxn1_cnt)//母线N-1
                        warndevtypexln1_cnt.push(list[i].warndevtypexln1_cnt)//线路N-1
                        warndevtypezbn1_cnt.push(list[i].warndevtypezbn1_cnt)//主变N-1
                        warndevtypelxn2_cnt.push(list[i].warndevtypelxn2_cnt)//线路N-2
                        warndevtypeqt_cnt.push(list[i].warndevtypeqt_cnt)//其他
                        warndevtypejzn1_cnt.push(list[i].warndevtypejzn1_cnt)//机组N-1
                        warndevtypelxn3_cnt.push(list[i].warndevtypelxn3_cnt)//线路N-3
                        var option7 = self.option7();
                        option7.xAxis.data = sustainCity;
                        option7.series[0].data = warndevtypemxn1_cnt;
                        option7.series[1].data = warndevtypexln1_cnt;
                        option7.series[2].data = warndevtypezbn1_cnt;
                        option7.series[3].data = warndevtypelxn2_cnt;
                        option7.series[4].data = warndevtypeqt_cnt;
                        option7.series[5].data = warndevtypejzn1_cnt;
                        option7.series[6].data = warndevtypelxn3_cnt;
                        self.option7(option7)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".Distributionweek").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionday").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionmonth").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".Distributionyear").css("background-image", "url('../resources/resources/images/u1134.png')");
        }
        //年
        self.Distributionyear=function (flag) {
            //预警数量
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=0&timeType=4&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var earlyNum=res.data.resultValue
                    var option2 = self.option2();
                    var cityNameNumber = [],number1 = [],number2 = [],number3 = [],number4=[],number5=[],number6=[],number7=[],number8=[];
                    for (var i in earlyNum){
                        cityNameNumber.push(earlyNum[i].datareport_org)
                        number1.push(earlyNum[i].number01)
                        number2.push(earlyNum[i].number02)
                        number3.push(earlyNum[i].number03)
                        number4.push(earlyNum[i].number04)
                        number5.push(earlyNum[i].number05)
                        number6.push(earlyNum[i].number06)
                        number7.push(earlyNum[i].number07)
                        number8.push(earlyNum[i].number08)
                    }
                    option2.xAxis[0].data = cityNameNumber;
                    option2.series[0].data = number1;
                    option2.series[1].data = number2;
                    option2.series[2].data = number3;
                    option2.series[3].data = number4;
                    option2.series[4].data = number5;
                    option2.series[5].data = number6;
                    option2.series[6].data = number7;
                    option2.series[7].data = number8;
                    self.option2(option2)
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //电压等级
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=1&timeType=4&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option3= self.option3();
                    var PowercityNames = [],fiveHundred = [],Twohundred = [];
                    for (var i in list){
                        PowercityNames.push(list[i].datareport_org)
                        fiveHundred.push(list[i].voltage3_cnt)
                        Twohundred.push(list[i].voltage5_cnt)

                        var option3 = self.option3();
                        option3.xAxis.data = PowercityNames;
                        option3.series[0].data = fiveHundred;
                        option3.series[1].data = Twohundred;

                        self.option3(option3)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电事由
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=2&timeType=4&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option4= self.option4();
                    var capitalconstructionCity = [],capitalconstruction = [],project = [],facilitymaintenance = [],PowerPlantCustomers = [],MunicipalCooperate = [];

                    for (var i in list){
                        capitalconstructionCity.push(list[i].datareport_org)
                        capitalconstruction.push(list[i].dwjj_cnt)//电网基建
                        project.push(list[i].jggc_cnt)//技改工程
                        facilitymaintenance.push(list[i].sbjx_cnt)//设备检修
                        PowerPlantCustomers.push(list[i].dckh_cnt)//电厂客户
                        MunicipalCooperate.push(list[i].szph_cnt)//市政配合

                        var option4 = self.option4();
                        option4.xAxis.data = capitalconstructionCity;
                        option4.series[0].data = capitalconstruction;
                        option4.series[3].data = project;
                        option4.series[4].data = facilitymaintenance;
                        option4.series[1].data = PowerPlantCustomers;
                        option4.series[2].data = MunicipalCooperate;

                        self.option4(option4)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //停电设备
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=3&timeType=4&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var PowerShe=res.data.resultValue
                    var option5= self.option5();
                    var equipmentCity = [],riskdevzl_cnt = [],riskdevmx_cnt = [],riskdevjl_cnt = [],riskdevqt_cnt = [],riskdevzb_cnt = [];
                    for (var i in PowerShe){
                        equipmentCity.push(PowerShe[i].datareport_org)
                        riskdevzl_cnt.push(PowerShe[i].riskdevzl_cnt)//直流
                        riskdevmx_cnt.push(PowerShe[i].riskdevmx_cnt)//母线
                        riskdevjl_cnt.push(PowerShe[i].riskdevjl_cnt)//交流线路
                        riskdevqt_cnt.push(PowerShe[i].riskdevqt_cnt)//其他
                        riskdevzb_cnt.push(PowerShe[i].riskdevzb_cnt)//主变

                        var option5 = self.option5();
                        option5.xAxis.data = equipmentCity;
                        option5.series[0].data = riskdevzl_cnt;
                        option5.series[1].data = riskdevmx_cnt;
                        option5.series[2].data = riskdevjl_cnt;
                        option5.series[3].data = riskdevqt_cnt;
                        option5.series[4].data = riskdevzb_cnt;

                        self.option5(option5)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警事件持续时间
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=4&timeType=4&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option6= self.option6();
                    var sustainCity = [],actualday15_cnt = [],actualday31_cnt = [],actualday30_cnt = [],actualday1_cnt = [],actualday3_cnt = [],actualday7_cnt = [];
                    for (var i in list){
                        sustainCity.push(list[i].datareport_org)
                        actualday15_cnt.push(list[i].actualday15_cnt)//7天＜持续时间≤15天
                        actualday31_cnt.push(list[i].actualday31_cnt)//30天＜持续时间
                        actualday30_cnt.push(list[i].actualday30_cnt)//15天＜持续时间≤30天
                        actualday1_cnt.push(list[i].actualday1_cnt)//持续时间≤1天
                        actualday3_cnt.push(list[i].actualday3_cnt)//1天＜持续时间≤3天
                        actualday7_cnt.push(list[i].actualday7_cnt)//3天＜持续时间≤7天

                        var option6 = self.option6();
                        option6.xAxis.data = sustainCity;
                        option6.series[0].data = actualday15_cnt;
                        option6.series[1].data = actualday31_cnt;
                        option6.series[2].data = actualday30_cnt;
                        option6.series[3].data = actualday1_cnt;
                        option6.series[4].data = actualday3_cnt;
                        option6.series[5].data = actualday7_cnt;
                        self.option6(option6)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //预警影响类型
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=5&timeType=4&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    var option7= self.option7();
                    var affectCity = [],warndevtypemxn1_cnt = [],warndevtypexln1_cnt = [],warndevtypezbn1_cnt = [],warndevtypelxn2_cnt = [],warndevtypeqt_cnt = [],warndevtypejzn1_cnt = [],warndevtypelxn3_cnt = [];
                    for (var i in list){
                        affectCity.push(list[i].datareport_org)
                        warndevtypemxn1_cnt.push(list[i].warndevtypemxn1_cnt)//母线N-1
                        warndevtypexln1_cnt.push(list[i].warndevtypexln1_cnt)//线路N-1
                        warndevtypezbn1_cnt.push(list[i].warndevtypezbn1_cnt)//主变N-1
                        warndevtypelxn2_cnt.push(list[i].warndevtypelxn2_cnt)//线路N-2
                        warndevtypeqt_cnt.push(list[i].warndevtypeqt_cnt)//其他
                        warndevtypejzn1_cnt.push(list[i].warndevtypejzn1_cnt)//机组N-1
                        warndevtypelxn3_cnt.push(list[i].warndevtypelxn3_cnt)//线路N-3
                        var option7 = self.option7();
                        option7.xAxis.data = sustainCity;
                        option7.series[0].data = warndevtypemxn1_cnt;
                        option7.series[1].data = warndevtypexln1_cnt;
                        option7.series[2].data = warndevtypezbn1_cnt;
                        option7.series[3].data = warndevtypelxn2_cnt;
                        option7.series[4].data = warndevtypeqt_cnt;
                        option7.series[5].data = warndevtypejzn1_cnt;
                        option7.series[6].data = warndevtypelxn3_cnt;
                        self.option7(option7)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            $(".Distributionweek").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionday").css("background-image", "url('../resources/resources/images/u1134.png')");
            $(".Distributionyear").css("background-image", "url('../resources/resources/images/u1132.png')");
            $(".Distributionmonth").css("background-image", "url('../resources/resources/images/u1134.png')");



        }
        //预警通知单
        self.requisition_click=function(){
            $(".requisition").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploiting").css("background", "");
            $(".requisitions").show();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
            $(".relieves").hide();
        }
        //预警反馈单
        self.feedback_click=function(){
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploiting").css("background", "");
            $(".feedback").css("background", "#1b6b9d");
            $(".feedbacks").show();
            $(".requisitions").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
            $(".relieves").hide();

        }
        //预警报告单
        self.report_click=function(){
            $(".report").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploiting").css("background", "");
            $(".reports").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
            $(".relieves").hide();
        }
        //预警告知单
        self.notify_click=function(){
            $(".notify").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".exploiting").css("background", "");
            $(".relieve").css("background", "");
            $(".notifys").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".exploitings").hide();
            $(".relieves").hide();
        }
        //预警实施单
        self.exploiting_click=function(){
            $(".exploiting").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".relieve").css("background", "");
            $(".exploitings").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".relieves").hide();
        }
        //预警解除
        self.relieve_click=function(){
            $(".relieve").css("background", "#1b6b9d");
            $(".feedback").css("background", "");
            $(".requisition").css("background", "");
            $(".report").css("background", "");
            $(".notify").css("background", "");
            $(".exploiting").css("background", "");
            $(".relieves").show();
            $(".requisitions").hide();
            $(".feedbacks").hide();
            $(".reports").hide();
            $(".notifys").hide();
            $(".exploitings").hide();
        }
        //电网
        self.power=function () {
            $(".dianwang").attr("src","../resources/resources/images/u1863.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".powers").show();
            $(".personnels").hide();
            $(".facilitys").hide();
            $(".surroundings").hide();
            $(".useNames").hide();
        }
        //人员
        self.personnel=function () {
            $(".crew").attr("src","../resources/resources/images/u1901.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".personnels").show();
            $(".powers").hide();
            $(".facilitys").hide();
            $(".surroundings").hide();
            $(".useNames").hide();
        }
        //设备
        self.facility=function () {
            $(".devicename").attr("src","../resources/resources/images/u1933.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            // $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".facilitys").show();
            $(".powers").hide();
            $(".personnels").hide();
            $(".surroundings").hide();
            $(".useNames").hide();
        }
        //环境
        self.surrounding=function () {
            $(".atmosphere").attr("src","../resources/resources/images/u1965.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".advocate").attr("src","../resources/resources/images/u1849.png");
            $(".surroundings").show();
            $(".powers").hide();
            $(".personnels").hide();
            $(".facilitys").hide();
            $(".useNames").hide();
        }
        //用户
        self.useName=function () {
            $(".advocate").attr("src","../resources/resources/images/u2001.png");
            $(".atmosphere").attr("src","../resources/resources/images/u1847.png");
            $(".devicename").attr("src","../resources/resources/images/u1843.png");
            $(".crew").attr("src","../resources/resources/images/u1839.png");
            $(".dianwang").attr("src","../resources/resources/images/u1883.png");
            $(".useNames").show();
            $(".personnels").hide();
            $(".facilitys").hide();
            $(".surroundings").hide();
            $(".powers").hide();
        }

          Day()
         RiskInformation()
         RiskList()
        self.goBack = function() {
            self.page('list')
        }
        // 全网电网风险情况数据汇总(当日)
        function Day(){
            cube.showLoading();
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCnt/0").then(function(res) {
                if ( res.data.successful === true) {
                    var data=res.data.resultValue
                    self.risk(data.workRiskLevelCount.plan_cnt)
                    self.stair(res.data.resultValue.stateGridRiskLevelCount.level01);
                    self.second(res.data.resultValue.stateGridRiskLevelCount.level02);
                    self.three(res.data.resultValue.stateGridRiskLevelCount.level03);
                    self.four(res.data.resultValue.stateGridRiskLevelCount.level04);
                    self.five(res.data.resultValue.stateGridRiskLevelCount.level05);
                    self.six(res.data.resultValue.stateGridRiskLevelCount.level06);
                    self.relieve(res.data.resultValue.warnRiskCount.plan_cnt)
                    self.release(res.data.resultValue.planPublishCount.plan_cnt)
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
        }

        function RiskInformation() {
            axios.get(cube.gatewayURL + "riskInfos/areaRiskCountInfo/0").then(function(res) {
                if ( res.data.successful === true) {
                     // self.informationStatistics(res.data)
                    var data=res.data.resultValue
                    for (var i in data){
                       cityName.push(data[i].datareport_org)
                        powerGridSum.push(data[i].gridrisk_cnt)
                       OperationalRiskNumber.push(data[i].workrisk_cnt)
                       BlackoutSchedule.push(data[i].power_cnt)

                        var option = self.pieoption();
                        option.xAxis.data = cityName;
                        option.series[0].data = powerGridSum;
                        option.series[1].data = OperationalRiskNumber;
                        option.series[2].data = BlackoutSchedule;
                        self.pieoption(option)
                    }
                } else {
                    // cube.indicate("warning", res.data);
                    cube.indicate("数据加载失败");
                }

            })
        }
        // 各单位风险信息统计情况
        function RiskList(searchParams) {
            var jsonStr = {
                'pageSize':10,
                'pageNo':self.pageIndex()-1,
            }
            axios.post(cube.gatewayURL + "riskInfos/allRiskWarnInfo",jsonStr).then(function(res) {
                if (true) {
                    var list=res.data.resultValue.resultList
                    // console.log(res.data.resultValue)
                    self.count(res.data.resultValue.listSize);
                    self.pageTotalCount(Math.ceil(res.data.resultValue.listSize / 10));
                    self.items(list);

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //预警事件持续时间
        equipment()
        //预警数量
        GridDistribution()
        //电压等级
        LevelPower()
        //停电事由
        PowerFailure()
        //停电设备
        DivisionEquipment()
        //预警影响类型
        affectType()
        // Evaluation()
        //预警数量
        function GridDistribution(){
            cube.showLoading(false);
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=0&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var earlyNum=res.data.resultValue
                    var option2 = self.option2();
                    var cityNameNumber = [],number1 = [],number2 = [],number3 = [],number4=[],number5=[],number6=[],number7=[],number8=[];
                    for (var i in earlyNum){
                        cityNameNumber.push(earlyNum[i].datareport_org)
                        number1.push(earlyNum[i].number01)
                        number2.push(earlyNum[i].number02)
                        number3.push(earlyNum[i].number03)
                        number4.push(earlyNum[i].number04)
                        number5.push(earlyNum[i].number05)
                        number6.push(earlyNum[i].number06)
                        number7.push(earlyNum[i].number07)
                        number8.push(earlyNum[i].number08)
                    }
                    option2.xAxis[0].data = cityNameNumber;
                    option2.series[0].data = number1;
                    option2.series[1].data = number2;
                    option2.series[2].data = number3;
                    option2.series[3].data = number4;
                    option2.series[4].data = number5;
                    option2.series[5].data = number6;
                    option2.series[6].data = number7;
                    option2.series[7].data = number8;

                    self.option2(option2)
                    cube.showLoading(false);
                } else {
                    cube.indicate("数据加载失败");
                }

            })

        }
        //电压等级
        function LevelPower(){
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=1&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue

                    for (var i in list){
                        PowercityNames.push(list[i].datareport_org)
                        fiveHundred.push(list[i].voltage3_cnt)
                        Twohundred.push(list[i].voltage5_cnt)

                        var option3 = self.option3();
                        option3.xAxis.data = PowercityNames;
                        option3.series[0].data = fiveHundred;
                        option3.series[1].data = Twohundred;

                        self.option3(option3)
                        cube.showLoading(false);
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //停电事由
        function PowerFailure(){
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=2&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {

                    var list=res.data.resultValue


                    for (var i in list){
                        capitalconstructionCity.push(list[i].datareport_org)
                        capitalconstruction.push(list[i].dwjj_cnt)//电网基建
                        project.push(list[i].jggc_cnt)//技改工程
                        facilitymaintenance.push(list[i].sbjx_cnt)//设备检修
                        PowerPlantCustomers.push(list[i].dckh_cnt)//电厂客户
                        MunicipalCooperate.push(list[i].szph_cnt)//市政配合

                        var option4 = self.option4();
                        option4.xAxis.data = capitalconstructionCity;
                        option4.series[0].data = capitalconstruction;
                        option4.series[3].data = project;
                        option4.series[4].data = facilitymaintenance;
                        option4.series[1].data = PowerPlantCustomers;
                        option4.series[2].data = MunicipalCooperate;

                        self.option4(option4)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //停电设备
        function DivisionEquipment(){
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=3&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var PowerShe=res.data.resultValue

                    for (var i in PowerShe){
                        equipmentCity.push(PowerShe[i].datareport_org)
                        riskdevzl_cnt.push(PowerShe[i].riskdevzl_cnt)//直流
                        riskdevmx_cnt.push(PowerShe[i].riskdevmx_cnt)//母线
                        riskdevjl_cnt.push(PowerShe[i].riskdevjl_cnt)//交流线路
                        riskdevqt_cnt.push(PowerShe[i].riskdevqt_cnt)//其他
                        riskdevzb_cnt.push(PowerShe[i].riskdevzb_cnt)//主变

                        var option5 = self.option5();
                        option5.xAxis.data = equipmentCity;
                        option5.series[0].data = riskdevzl_cnt;
                        option5.series[1].data = riskdevmx_cnt;
                        option5.series[2].data = riskdevjl_cnt;
                        option5.series[3].data = riskdevqt_cnt;
                        option5.series[4].data = riskdevzb_cnt;

                        self.option5(option5)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //预警事件持续时间
        function equipment(){
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=4&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    for (var i in list){
                        sustainCity.push(list[i].datareport_org)
                        actualday15_cnt.push(list[i].actualday15_cnt)//7天＜持续时间≤15天
                        actualday31_cnt.push(list[i].actualday31_cnt)//30天＜持续时间
                        actualday30_cnt.push(list[i].actualday30_cnt)//15天＜持续时间≤30天
                        actualday1_cnt.push(list[i].actualday1_cnt)//持续时间≤1天
                        actualday3_cnt.push(list[i].actualday3_cnt)//1天＜持续时间≤3天
                        actualday7_cnt.push(list[i].actualday7_cnt)//3天＜持续时间≤7天

                        var option6 = self.option6();
                        option6.xAxis.data = sustainCity;
                        option6.series[0].data = actualday15_cnt;
                        option6.series[1].data = actualday31_cnt;
                        option6.series[2].data = actualday30_cnt;
                        option6.series[3].data = actualday1_cnt;
                        option6.series[4].data = actualday3_cnt;
                        option6.series[5].data = actualday7_cnt;
                        self.option6(option6)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //预警影响类型
        function affectType(){
            axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=5&timeType=0&beginTime=null&endTime=null").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    for (var i in list){
                        affectCity.push(list[i].datareport_org)
                        warndevtypemxn1_cnt.push(list[i].warndevtypemxn1_cnt)//母线N-1
                        warndevtypexln1_cnt.push(list[i].warndevtypexln1_cnt)//线路N-1
                        warndevtypezbn1_cnt.push(list[i].warndevtypezbn1_cnt)//主变N-1
                        warndevtypelxn2_cnt.push(list[i].warndevtypelxn2_cnt)//线路N-2
                        warndevtypeqt_cnt.push(list[i].warndevtypeqt_cnt)//其他
                        warndevtypejzn1_cnt.push(list[i].warndevtypejzn1_cnt)//机组N-1
                        warndevtypelxn3_cnt.push(list[i].warndevtypelxn3_cnt)//线路N-3
                        var option7 = self.option7();
                        option7.xAxis.data = sustainCity;
                        option7.series[0].data = warndevtypemxn1_cnt;
                        option7.series[1].data = warndevtypexln1_cnt;
                        option7.series[2].data = warndevtypezbn1_cnt;
                        option7.series[3].data = warndevtypelxn2_cnt;
                        option7.series[4].data = warndevtypeqt_cnt;
                        option7.series[5].data = warndevtypejzn1_cnt;
                        option7.series[6].data = warndevtypelxn3_cnt;
                        self.option7(option7)
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        //评价指标统计
        // function Evaluation(){
        //     axios.get(cube.gatewayURL + "riskInfos/gridRiskInfosCommonCnt?infoType=6&timeType=1&beginTime=null&endTime=null").then(function(res) {
        //         if (true) {
        //             var list=res.data.resultValue
        //             // console.log(list)
        //
        //             for (var i in list){
        //                 affectCity.push(list[i].datareport_org)
        //                 warndevtypemxn1_cnt.push(list[i].warndevtypemxn1_cnt)//母线N-1
        //                 warndevtypexln1_cnt.push(list[i].warndevtypexln1_cnt)//线路N-1
        //                 warndevtypezbn1_cnt.push(list[i].warndevtypezbn1_cnt)//主变N-1
        //                 warndevtypelxn2_cnt.push(list[i].warndevtypelxn2_cnt)//线路N-2
        //                 warndevtypeqt_cnt.push(list[i].warndevtypeqt_cnt)//其他
        //                 warndevtypejzn1_cnt.push(list[i].warndevtypejzn1_cnt)//机组N-1
        //                 warndevtypelxn3_cnt.push(list[i].warndevtypelxn3_cnt)//线路N-3
        //                 var option7 = self.option7();
        //                 option7.xAxis.data = sustainCity;
        //                 option7.series[0].data = warndevtypemxn1_cnt;
        //                 option7.series[1].data = warndevtypexln1_cnt;
        //                 option7.series[2].data = warndevtypezbn1_cnt;
        //                 option7.series[3].data = warndevtypelxn2_cnt;
        //                 option7.series[4].data = warndevtypeqt_cnt;
        //                 option7.series[5].data = warndevtypejzn1_cnt;
        //                 option7.series[6].data = warndevtypelxn3_cnt;
        //                 self.option7(option7)
        //             }
        //         } else {
        //             cube.indicate("数据加载失败");
        //         }
        //
        //     }).finally(
        //         function() {
        //             cube.showLoading(false);
        //         }
        //     );
        // }
        cube.endViewModel(self, params);
    };



    return PageViewModel;
});