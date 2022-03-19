define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.LEVEL1=ko.observable("");
        self.LEVEL2=ko.observable("");
        self.LEVEL3=ko.observable("");
        self.powernumber=ko.observable("");//电网运行风险总数
        self.constructionnumber=ko.observable("");//电网建设风险总数
        self.industrynumber=ko.observable("");//产业风险总数
        self.networknumber=ko.observable("");//网络风险总数
        self.transmitElectricity=ko.observableArray([
            {id:'',text:'输电线路'},
            {id:'01',text:'变电站'},
            {id:'02',text:'换流站'},
            {id:'03',text:'抽水蓄能电站'},
        ]);
        self.Electricity =ko.observable("");
        // self.classificationRisks=ko.observableArray([
        //     // {id:'',text:'全部'},
        //     {id:'1',text:'电网建设'},
        //     {id:'2',text:'电网运行'},
        //     {id:'3',text:'产业风险'},
        //     {id:'4',text:'网络风险'},
        //
        // ]);
        // self.classification = ko.observable("2");
        // self.riskGrade=ko.observableArray([
        //     // {id:'',text:'风险等级'},
        //     {id:'1',text:'一级'},
        //     {id:'2',text:'二级'},
        //     {id:'3',text:'三级'},
        //     {id:'4',text:'四级'},
        //     {id:'5',text:'五级'},
        //     {id:'6',text:'六级'},
        //     {id:'7',text:'七级'},
        //     {id:'8',text:'八级'},
        // ]);
        // self.Grade = ko.observable("");
        // self.riskGrade2=ko.observableArray([
        //     // {id:'',text:'风险等级'},
        //     {id:'1',text:'一级'},
        //     {id:'2',text:'二级'},
        //     {id:'3',text:'三级'},
        //     {id:'4',text:'四级'},
        //     {id:'5',text:'五级'},
        //
        // ]);
        // self.Grade = ko.observable("");
        // self.chanye=ko.observableArray([
        //     // {id:'',text:'风险等级'},
        //     {id:'1',text:'一般'},
        //     {id:'2',text:'重大'},
        //     {id:'3',text:'特别重大'},
        // ]);
        // self.Grade = ko.observable("");
        var fxObj = {
            "01":[
                {id:'',text:'全部'},
                {id:'01',text:'一级'},
                {id:'02',text:'二级'},
                {id:'03',text:'三级'},
                {id:'04',text:'四级'},
                {id:'05',text:'五级'},
                {id:'06',text:'六级'},
                {id:'07',text:'七级'},
                {id:'08',text:'八级'}
            ],
            "02":[
                {id:'',text:'全部'},
                {id:'01',text:'一级'},
                {id:'02',text:'二级'},
                {id:'03',text:'三级'},
                {id:'04',text:'四级'},
                {id:'05',text:'五级'}


            ],
            "03":[
                // {id:'',text:'等级'},
                {id:'01',text:'一般'},
                {id:'02',text:'重大'},
                {id:'03',text:'特别重大'}
            ],
            "04":[
                {id:'',text:'全部'},
                {id:'01',text:'一级'},
                {id:'02',text:'二级'},
                {id:'03',text:'三级'},
                // {id:'04',text:'四级'},
                // {id:'05',text:'五级'},
            ],


        }
        //电网风险
        self.dwfxContent=ko.observableArray([
            {id:'02',text:'电网建设'},
            {id:'01',text:'电网运行'},
            {id:'03',text:'产业风险'},
            {id:'04',text:'网络风险'},
        ]);
        self.classification = ko.observable('02');
        self.zyfxContent=ko.observableArray([
            {id:'',text:'全部'},
            {id:'01',text:'一级'},
            {id:'02',text:'二级'},
            {id:'03',text:'三级'},
            {id:'04',text:'四级'},
            {id:'05',text:'五级'},
            // {id:'06',text:'六级'},
            // {id:'07',text:'七级'},
            // {id:'08',text:'八级'}
        ]);
        self.Grade = ko.observable('');
        self.eventChange = function(){
            safetyRisk()
            if (self.classification() == '01') {
                $(".riskGrade2").show();
            }
            if (self.classification() == '02') {
                $(".riskGrade2").hide();
            }
            if (self.classification() == '03') {
                $(".riskGrade2").hide();
            }
            if (self.classification() == '04') {
                $(".riskGrade2").hide();
            }
            self.Grade('')
            self.zyfxContent(fxObj[self.classification()])

        }
        self.eventChange1 = function(){
            safetyRisk()
        }
        self.eventChange2= function(){
            safetyRisk()
        }



        // self.eventChange = function(e) {
        //     safetyRisk()
        //     if (self.classification() == '2') {
        //         $(".riskGrade2").hide();
        //         $(".riskGrade").show();
        //         $(".Electri").show();
        //         $(".chanye").hide();
        //     }
        //     if (self.classification() == '1') {
        //         $(".riskGrade2").show();
        //         $(".riskGrade").hide();
        //         $(".Electri").hide();
        //         $(".chanye").hide();
        //     }
        //     if (self.classification() == '3') {
        //         $(".chanye").show();
        //         $(".riskGrade2").hide();
        //         $(".riskGrade").hide();
        //         $(".Electri").hide();
        //     }
        //     if (self.classification() == '4') {
        //         $(".Electri").hide();
        //         $(".riskGrade2").show();
        //         $(".riskGrade").hide();
        //         $(".chanye").hide();
        //
        //     }
        // }
        // self.eventChanges = function(e) {
        //     safetyRisk()
        // }
        // self.eventChangess = function(e) {
        //     safetyRisk()
        // }

        self.data=[]
        var Number = [
            {name:'1-4级',value:0},
            {name:'5级',value:0},
            {name:'6级',value:0},
            {name:'7级',value:0},
            {name:'8级',value:0}
        ]
        var Number2 = [
            {value: 0, name: '1级'},
            {value: 0,name: '2级'},
            {value: 0, name: '3级'},
            {value: 0, name: '4级'}
        ]
        var Number3=[
            {value:0, name:'1级',},
            {value:0, name:'2级',itemStyle: {color: '#FFC145'}},
            {value:0, name:'3级',itemStyle: {color: '#F7C885'}},
            {value:0, name:'4级',itemStyle: {color: '#2eb2fa'}},
            {value:0, name:'5级',itemStyle: {color: '#d48265'}}
            ]
        var Number4=[
                    // {value:0},
                    // {value:0},
                    // {value:0},
            {value: 0, name: '110kV及以下'},
            {value: 0,name: '220kV（330kV）'},
            {value: 0, name: '500kV及以上'},
                     ]

        var geoCoordMap = {};
        initMap()
        safetyRisk()
        var option = {
            tooltip: {
                textStyle: {
                    color: '#fff'
                },
                backgroundColor: '#1F4FAF',
                formatter: function(params) {
                    var name = params.name;
                    var val = params.data.value[2]
                    return name+'：'+val;
                }

            },
            geo: {
                show: true,
                map: "china",
                label: {
                    normal: {
                        show: true,
                        color: "#1DE9B6",


                    },
                    emphasis: {
                        show: false,
                        color: "#1DE9B6"
                    }
                },
                roam: false,
                zoom: 1.2,
                itemStyle: {
                    normal: {
                        borderColor: "#87B9EE", //每个区域的边框色
                        areaColor: {//区域背景色
                            type: 'radial',
                            x: 0.5,
                            y: 0.5,
                            r: 0.8,
                            colorStops: [{
                                offset: 0,
                                color: 'rgba(147, 235, 248, 0)' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: 'rgba(147, 235, 248, .2)' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        },
                        borderWidth: 2
                    },
                    emphasis: {
                        areaColor: "#4499d0"
                    }
                },
                regions: [
                    {
                        name: '广东',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '云南',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '广西',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '贵州',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '台湾',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '海南',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '香港',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '澳门',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    },
                    {
                        name: '蒙西',
                        label: {
                            normal: {
                                show: false,
                                color: "#1DE9B6"
                            }
                        },
                        itemStyle: {
                            normal: {
                                areaColor: 'rgba(15, 154, 142, .2)'
                            },
                            emphasis: {
                                show:false,
                                areaColor: "rgba(15, 154, 142, .2)"
                            }
                        }
                    }
                ]
            },
            series: [
                {
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    data: [],
                    symbolSize: function(val) {
                        var v = val[2] / 10;
                        // return v<10?10:v>30?30:v;
                        var a = v==0?0:v<10?10:v>30?30:v;
                        return a;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    hoverAnimation: true,
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'left',
                            show: false,
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#1DE9B6',
                            shadowBlur: 10,
                            shadowColor: '#1DE9B6'
                        }
                    },
                    zlevel: 1
                },

            ]
        }
        function convertData(data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name];
                if (geoCoord) {
                    res.push({
                        name: data[i].name,
                        value: geoCoord.concat(data[i].value),
                    });
                }
            }
            return res;
        };
        var myEcharts=""
        function initMap(name) {
            if (myEcharts !=null && myEcharts != "" && myEcharts !=undefined){
                myEcharts.dispose();//销毁
            }
            var mapUrl = 'resources/map/china.json';
            $.get(mapUrl, function (mapJson) {
                 myEcharts = echarts.init(document.getElementById('china_map'))
                echarts.registerMap('china', mapJson);
                var mapFeatures = echarts.getMap('china').geoJson.features;
                mapFeatures.forEach(function(v) {
                    // 地区名称
                    var name = v.properties.name;
                    // 地区经纬度
                    geoCoordMap[name] = v.properties.cp;

                });
                option.series[0].data = convertData(self.data())
                // console.log(option)
                myEcharts.setOption(option);
            })
        }
        self.pieoption = {
            title: [{
                // text: '今日煤矿煤炭产品销售预览',
                x: 'center',
                text: '风险总数',
                subtext: '0',
                textStyle:{
                    fontSize:18,
                    color:"#0da78d",
                    fontWeight: 800,

                },
                subtextStyle: {
                    fontSize: 36,
                    color: '#0da78d',
                    fontWeight: 800,
                },
                textAlign:"center",
                x: '55%',
                y: '40%',
            }],
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: '20',
                y: '60',
                left:'85%',
                itemWidth:30,             // 图例图形宽度
                itemHeight:20,
                // padding: 5,
                itemGap: 15,
                data:Number,
                textStyle:{
                    color:'#e9f3f1'
                }
            },
            series: [
                {
                    name:'电网运行风险',
                    type:'pie',
                    x: 'left',
                    // left:'-5%',
                    center:['55%','50%'],
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: false,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold',

                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:Number
                }
            ]
        };
        self.option2 = {

            title: {
                text: '电网建设风险等级',
                left: 'center',
                top: 10,
                textStyle: {
                    color: 'white'
                }
            },

            tooltip: {
                trigger: 'item',
                formatter: "{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 500,
                max: 600,
                inRange: {
                    //colorLightness: [0, 1]
                }
            },
            legend: {
                orient: 'vertical',
                x: '-30',
                y: '60',
                left:'86%',
                itemWidth:30,             // 图例图形宽度
                itemHeight:20,
                // // padding: 5,
                itemGap: 15,
                data:["1级", "2级", "3级", "4级"],
                textStyle:{
                    color:'#e9f3f1'
                }
            },
            series: [{
                name: '访问来源',
                type: 'pie',
                radius: '70%',
                center: ['50%', '50%'],
                color: ['rgb(131,249,103)', '#FBFE27', '#FE5050', '#1DB7E5'], //'#FBFE27','rgb(11,228,96)','#FE5050'
                data: Number2.sort(function(a, b) {
                    return a.value - b.value
                }),
                roseType: 'radius',

                label: {
                    normal: {
                        formatter: ['{c|{c}次}', '{b|{b}}'].join('\n'),
                        rich: {
                            c: {
                                color: 'rgb(241,246,104)',
                                fontSize: 20,
                                fontWeight:'bold',
                                lineHeight: 5
                            },
                            b: {
                                color: 'rgb(98,137,169)',
                                fontSize: 15,
                                height: 40
                            },
                        },
                    }
                },
                labelLine: {
                    normal: {

                        lineStyle: {
                            color: 'rgb(98,137,169)',
                        },
                        smooth: 0.2,
                        length:10,
                        length2:10,

                    }
                },

                itemStyle: {
                    normal: {
                        shadowColor: 'rgba(0, 0, 0, 0.8)',
                        shadowBlur: 50,
                    }
                }
            }]
        };
        // 数据及颜色设置
        // let bgColor = '#0A2E6A';
        // let circleColor = '#0075ff'; // 三个圆环的颜色
        // let gradientColor = ['#000204', '#0A2E6A']; // 中心圆渐变色
        // let color = ['#2EB2FA', '#43CC31', '#FFC145', '#FFA8A8']; // 数据图表颜色数组
        // let scale = 1;
        var echartData = [
            {name: '一般', value: 0,},
            {name: '重大', value: 0,},
            {name: '特别重大',value: 0, }
        ];
        var total = echartData.reduce(function(a, b) {
            return a + b.value * 1
        }, 0)
       self.Num=''
       self.option3 = {
           title: {
               text: '各电压等级作业风险数',
               left: 'center',
               top: 15,
               textStyle: {
                   color: 'white'
               }
           },
            "color": ["#3cefff"],
            "tooltip": {},
            "grid": {
                "containLabel": true
            },
            "xAxis": [{
                "type": "category",
                "data": ["110kV及以下", "220kV（330kV）", "500kV及以上"],
                // "axisTick": {
                //     "alignWithLabel": true
                // },
                "nameTextStyle": {
                    "color": "#82b0ec"
                },
                // "axisLine": {
                //     "lineStyle": {
                //         "color": "#82b0ec"
                //     }
                // },
                "axisLabel": {
                    "textStyle": {
                        "color": "#82b0ec"
                    }
                },
                axisTick:{
                    show:false//不显示坐标轴刻度线
                },
                axisLine: {
                    show: false,//不显示坐标轴线
                },
                splitLine:{
                    show:false,  //去掉背景的网格线
                },
            }],
            "yAxis": [{
                "type": "value",
                "axisLabel": {
                    "textStyle": {
                        "color": "#82b0ec"
                    },
                    // "formatter": "{value}%"
                },
                "splitLine": {
                    "lineStyle": {
                        "color": "#0c2c5a"
                    }
                },
                "axisLine": {
                    "show": false
                }
            }],
            "series": [{
                "name": "",
                "type": "pictorialBar",
                "symbolSize": [20, 10],
                "symbolOffset": [0, -5],
                "symbolPosition": "end",
                "z": 15,
                "label": {
                    "normal": {
                        "show": true,
                        "position": "top",
                        // "formatter": "{c}%"
                    }
                },
                "data": Number4
            },
                // {
                //     "name": "",
                //     "type": "pictorialBar",
                //     "symbolSize": [20, 10],
                //     "symbolOffset": [0, 5],
                //     "z": 12,
                //     "data": [60, 70, 80, 90, 60, 70, 80, 90]
                // },
                {
                    "type": "bar",
                    "itemStyle": {
                        "normal": {
                            "opacity": 0.7
                        }
                    },
                    "barWidth": "25",
                    "data": Number4,
                    "markLine": {
                        "silent": false,
                        "symbol": "none",
                        "label": {
                            "position": "middle",
                            // "formatter": "{b}"
                        },
                        // "data": [{
                        //     "name": "目标值",
                        //     "yAxis": 80,
                        //     "lineStyle": {
                        //         "color": "#ffc832"
                        //     },
                        //     "label": {
                        //         "position": "end",
                        //         "formatter": "{b}\n {c}%"
                        //     }
                        // }]
                    }
                },
                {
                    type: 'effectScatter',
                    silent: true,
                    tooltip: {
                        show: false
                    },
                    zlevel: 5,
                    symbolSize: 20,
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke',
                        color: '#3cefff',
                        scale: 7
                    },
                    itemStyle: {
                        color: '#3cefff'
                    },
                    hoverAnimation: false,
                    data: [0,0,0]
                },
            ]
        }
        // self.option4 = {
        //     // backgroundColor: bgColor,
        //     color: color,
        //     title: {
        //
        //         text: '产业风险总数',
        //         subtext: '0',
        //         x: 'center',
        //         y: 'center',
        //         subtextStyle: {
        //             fontSize: 24,
        //             color: 'white',
        //             fontWeight: 800,
        //         },
        //         textStyle: {
        //             fontWeight: 800,
        //             fontSize: 14 * scale,
        //             color: "#fff",
        //
        //         }
        //     },
        //     tooltip: {
        //         show: false
        //     },
        //     legend: {
        //         icon: 'rect',
        //         itemWidth: 14 * scale,
        //         itemHeight: 14 * scale,
        //         orient: 'vertical',
        //         top: 'center',
        //         right: 0,
        //         formatter: function(name) {
        //             let res = echartData.filter(v => v.name === name);
        //             res = res[0];
        //             let percent = (res.value * 100 / (self.Num())).toFixed(1);
        //             return '{percent|' + percent + '}{unit| %}\n' + res.name + '{value|' + res.value + '}' + (res.unit || '')
        //         },
        //         textStyle: {
        //             color: '#fff',
        //             fontSize: 12 * scale,
        //             align: 'right',
        //             padding: [0, 0, 20 * scale, 0],
        //             rich: {
        //                 percent: {
        //                     fontSize: 22 * scale,
        //                     color: 'red',
        //                     align: 'right'
        //                 },
        //                 unit: {
        //                     fontSize: 14 * scale,
        //                     align: 'right',
        //                     padding: [0, 0, 4 * scale, 0]
        //                 },
        //                 value: {
        //                     fontSize: 14 * scale,
        //                     align: 'right',
        //                     padding: [0, 5 * scale, 0, 30 * scale]
        //                 }
        //             }
        //         }
        //     },
        //     series: [{
        //         type: 'pie',
        //         name: '最内层径向渐变圆心',
        //         clockWise: false,
        //         radius: '55%',
        //         center: ['50%', '50%'],
        //         z: 1,
        //         // itemStyle: {
        //         //     normal: {
        //         //         color: new echarts.graphic.RadialGradient(.5, .5, .6, [{
        //         //             offset: 0,
        //         //             color: gradientColor[0]
        //         //         },
        //         //             {
        //         //                 offset: 1,
        //         //                 color: gradientColor[1] || bgColor
        //         //             }
        //         //         ], false)
        //         //     },
        //         // },
        //         // hoverAnimation: false,
        //         label: {
        //             show: false,
        //         },
        //         tooltip: {
        //             show: false
        //         },
        //         data: [100],
        //     },
        //         {
        //             type: 'pie',
        //             name: '内层细圆环1',
        //             radius: ['56%', '46%'],
        //             hoverAnimation: false,
        //             clockWise: false,
        //             itemStyle: {
        //                 normal: {
        //                     borderColor: circleColor,
        //                     borderWidth: 1,
        //                 }
        //             },
        //             label: {
        //                 show: false
        //             },
        //             data: [100]
        //         },
        //         {
        //             type: 'pie',
        //             name: '内层细圆环2',
        //             radius: ['52%', '42%'],
        //             hoverAnimation: false,
        //             clockWise: false,
        //             itemStyle: {
        //                 normal: {
        //                     borderColor: circleColor,
        //                     borderWidth: 1,
        //                 }
        //             },
        //             label: {
        //                 show: false
        //             },
        //             data: [100]
        //         },
        //         {
        //             type: 'pie',
        //             name: '最外层细圆环',
        //             hoverAnimation: false,
        //             clockWise: false,
        //             radius: ['70%', '60%'],
        //             itemStyle: {
        //                 normal: {
        //                     borderColor: circleColor,
        //                     borderWidth: 1,
        //                 }
        //             },
        //             label: {
        //                 show: false
        //             },
        //             data: [100]
        //         },
        //         {
        //             name: '饼图内容区',
        //             type: 'pie',
        //             clockWise: false,
        //             radius: ['60%', '56%'],
        //             hoverAnimation: false,
        //             data: echartData,
        //             itemStyle: {
        //                 normal: {
        //                     shadowBlur: 20,
        //                     shadowColor: '#00204',
        //                 }
        //             },
        //             label: {
        //                 show: false
        //             }
        //         }
        //     ]
        // };
        self.option4 = {
            title: {

                text: '产业风险总数',
                subtext: '0',
                x: 'center',
                y: 'center',
                subtextStyle: {
                    fontSize: 24,
                    color: 'white',
                    fontWeight: 800,
                },
                textStyle: {
                    color: '#fff',
                }
            },

            // backgroundColor: '#090d1a',
            legend: {
                orient: 'vertical',
                show: true,
                right: '3%',
                y: 'center',
                itemWidth: 3,
                itemHeight: 30,
                itemGap: 20,
                textStyle: {
                    color: '#7a8c9f',
                    fontSize: 14,
                    lineHeight: 20,
                    rich: {
                        percent: {
                            color: '#fff',
                            fontSize: 16,
                        },
                    },
                },
                formatter: function(name) {
                    switch (name) {
                        case '一般':
                            return (
                                '一般' +
                                ' ' +  self.LEVEL1()
                            );
                        case '重大':
                            return (
                                '重大' +
                                ' ' + self.LEVEL2()
                            );
                        case '特别重大':
                            return (
                                '特别重大' +
                                ' ' + self.LEVEL3()
                            );
                        default:
                            break;
                    }
                },
            },
            tooltip: {
                show: true,
            },
            series: [
                {
                    type: 'pie',
                    radius: ['65%', '75%'],
                    center: ['50%', '50%'],
                    hoverAnimation: false,
                    z: 10,
                    label: {
                        position: 'center',
                        //   formatter: () => {
                        //     return '作业总数\r\n 100 个';
                        //   },
                        //   rich: {
                        //     total: {
                        //       fontSize: 30,
                        //       color: '#fff',
                        //     },
                        //   },
                        color: '#7a8c9f',
                        fontSize: 16,
                        lineHeight: 30,
                    },
                    data: [
                        {
                            value: 2,
                            name: '',
                            itemStyle: {
                                color: '#0286ff',
                            },
                        },
                        {
                            value:3,
                            name: '',
                            itemStyle: {
                                color: '#ffd302',
                            },
                        },
                        {
                            value: 4,
                            name: '',
                            itemStyle: {
                                color: '#fb5274',
                            },
                        },
                    ],
                    labelLine: {
                        show: false,
                    },
                },
                {
                    type: 'pie',
                    radius: ['54%', '64%'],
                    center: ['50%', '50%'],
                    hoverAnimation: false,
                    label: {
                        show: false,
                    },
                    data: [
                        {
                            value:2,
                            name: '一般',
                            itemStyle: {
                                color: '#0286ff',
                                opacity: 0.4,
                            },
                        },
                        {
                            value: 3,
                            name: '重大',
                            itemStyle: {
                                color: '#ffd302',
                                opacity: 0.4,
                            },
                        },
                        {
                            value:4,
                            name: '特别重大',
                            itemStyle: {
                                color: '#fb5274',
                                opacity: 0.4,
                            },
                        },
                    ],
                    labelLine: {
                        show: false,
                    },
                },
                {
                    type: 'pie',
                    radius: ['43%', '53%'],
                    center: ['50%', '50%'],
                    hoverAnimation: false,
                    label: {
                        show: false,
                    },
                    data: [
                        {
                            value:2,
                            name: '一般',
                            itemStyle: {
                                color: '#0286ff',
                                opacity: 0.1,
                            },
                        },
                        {
                            value: 3,
                            name: '重大',
                            itemStyle: {
                                color: '#ffd302',
                                opacity: 0.1,
                            },
                        },
                        {
                            value: 4,
                            name: '特别重大',
                            itemStyle: {
                                color: '#fb5274',
                                opacity: 0.1,
                            },
                        },
                    ],
                    labelLine: {
                        show: false,
                    },
                },
            ],

        };
        self.option5 = {
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} ({d}%)"
            },
            // title: {
            //     text: '事件总数',
            //     subtext: "0",
            //     left: 'center',
            //     top: 'center',
            //     textStyle: {
            //         color: '#F99C56',
            //         fontSize: 14
            //     },
            //     subtextStyle: {
            //         fontSize: 14,
            //         color: ['#F99C56']
            //     }
            // },
            legend: {
                orient: 'vertical',
                top: 'center',
                right: "6",
                itemWidth:30,             // 图例图形宽度
                itemHeight:20,
                // padding: 5,
                itemGap: 15,
                textStyle: {
                    color: '#A3E2F4',
                    fontSize: 12,
                    fontWeight: 0
                },
                data: ['1级','2级','3级','4级','5级']
            },
            series: [
                // 主要展示层的
                {
                    radius: ['50%', '70%'],
                    center: ['50%', '50%'],
                    type: 'pie',
                    label: {
                        normal: {
                            show: true,
                            formatter: "{d}%",
                            textStyle: {
                                fontSize: 14

                            },
                            position: 'outside'
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 6
                        }
                    },
                    data: [
                        {value:17, name:'1级',},
                        {value:23, name:'2级'},
                        {value:27, name:'3级'},
                        {value:33, name:'4级'},
                        {value:9, name:'5级'}]

                },
                // 边框的设置
                {
                    radius: ['50%', '45%'],
                    center: ['50%', '50%'],
                    type: 'pie',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    animation: false,
                    tooltip: {
                        show: false
                    },
                    data: [{
                        value: 1,
                        itemStyle: {
                            color: "rgba(250,250,250,0.3)"
                        }
                    }]
                },
                {
                    name: '外边框',
                    type: 'pie',
                    clockWise: false, //顺时加载
                    hoverAnimation: false, //鼠标移入变大
                    center: ['50%', '50%'],
                    radius: ['70%', '72%'],
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    tooltip: {
                        show: false
                    },
                    data: [{
                        value: 9,
                        name: '',
                        itemStyle: {
                            normal: {
                                borderWidth: 2,
                                borderColor: '#4DDCE4'
                            }
                        }
                    }]
                }
            ]
        };
              // 各风险总数统计
            axios.get(cube.gatewayURL + "riskManagement/riskManagementCNT").then(function(res) {
                if (true) {
                    var list=res.data.resultValue
                    // console.log(res.data.resultValue)
                    self.powernumber(list.GRIDWARN_CNT);//电网运行风险总数
                    self.constructionnumber(list.GRIDCONSTWARN_CNT);//电网建设风险总数
                    self.industrynumber(list.INDUSRISKWARN_CNT);//产业风险总数
                    self.networknumber(list.EVENTEWARNING_CNT);//网络风险总数
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            //安全风险综合情况
        function safetyRisk() {
        var jsonStr = {
             "warnType":self.classification(), //风险类型
            "typeGridStructure":self.Electricity(), //输电线路
            "warnLevel":self.Grade()      //风险等级
           }
        axios.post(cube.gatewayURL + "riskManagement/riskWarnAreaCNT",jsonStr).then(function(res) {
            if (res.data.successful) {
                var data = res.data.resultValue;
                // console.log(data)
                self.data([]);
                for(var i = 0;i<data.length;i++){
                    self.data.push({
                        name:data[i]['DATAREPORT_ORG'],
                        value:data[i]['CNT']
                    })
                }
                // console.log(self.data())
            initMap()

            } else {
                cube.indicate("数据加载失败");
            }

        })
        }
             // 电网运行风险等级
            axios.get(cube.gatewayURL + "riskManagement/riskGridWarnLevelCNT").then(function(res) {
                if (true) {
                    var data = res.data.resultValue;
                    var option = self.pieoption();
                    var yxzs = 0;
                    for(var i=0;i<Number.length;i++){
                        switch(Number[i]['name']){
                            case '1-4级':
                                yxzs += data['LEVEL4'];
                                Number[i]['value'] = data['LEVEL4'];
                                break;
                            case '5级':
                                yxzs += data['LEVEL5'];
                                Number[i]['value'] = data['LEVEL5'];
                                break;
                            case '6级':
                                yxzs += data['LEVEL6'];
                                Number[i]['value'] = data['LEVEL6'];
                                break;
                            case '7级':
                                yxzs += data['LEVEL7'];
                                Number[i]['value'] = data['LEVEL7'];
                                break;
                            case '8级':
                                yxzs += data['LEVEL8'];
                                Number[i]['value'] = data['LEVEL8'];
                                break;
                        }
                    }
                    option.title[0]['subtext'] = yxzs + '';
                    option.series[0]['data'] = Number;
                    self.pieoption(option);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
            // 电网建设风险等级
            axios.get(cube.gatewayURL + "riskManagement/riskGridConstWarnLevelCNT").then(function(res) {
                if (true) {
                    var data = res.data.resultValue;
                    var option = self.option2();
                    var yxzs = 0;
                    for(var i=0;i<Number2.length;i++){
                        switch(Number2[i]['name']){
                            case '1级':
                                yxzs += data['LEVEL1'];
                                Number2[i]['value'] = data['LEVEL1'];
                                break;
                            case '2级':
                                yxzs += data['LEVEL2'];
                                Number2[i]['value'] = data['LEVEL2'];
                                break;
                            case '3级':
                                yxzs += data['LEVEL3'];
                                Number2[i]['value'] = data['LEVEL3'];
                                break;
                            case '4级':
                                yxzs += data['LEVEL4'];
                                Number2[i]['value'] = data['LEVEL4'];
                                break;
                        }
                    }
                    // console.log(Number2)
                    // option.title[0]['subtext'] = yxzs + '';
                    option.series[0]['data'] = Number2;
                    self.option2(option);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        //产业风险等级
        axios.get(cube.gatewayURL + "riskManagement/riskIndusRiskWarnLevelCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(data)
                var option = self.option4();
                self.LEVEL1(data.LEVEL1)
                self.LEVEL2(data.LEVEL2)
                self.LEVEL3(data.LEVEL3)
                var yxzs = 0;
                // for(var i=0;i<echartData.length;i++){
                //     switch(echartData[i]['name']){
                //         case '一般':
                //             yxzs += data['LEVEL1'];
                //             echartData[i]['value'] = data['LEVEL1'];
                //             break;
                //         case '重大':
                //             yxzs += data['LEVEL2'];
                //             echartData[i]['value'] = data['LEVEL2'];
                //             break;
                //         case '特别重大':
                //             yxzs += data['LEVEL3'];
                //             echartData[i]['value'] = data['LEVEL3'];
                //             break;
                //         // case '4级':
                //         //     yxzs += data['LEVEL7'];
                //         //     Number2[i]['value'] = data['LEVEL7'];
                //         //     break;
                //     }
                // }
                //
                // self.Num(yxzs)
                // console.log( self.Num())
                option.title['subtext'] =data.LEVEL1+data.LEVEL2+data.LEVEL3;
                option.series[0]['data'][0]['value'] = data.LEVEL1;
                option.series[0]['data'][1]['value'] = data.LEVEL2;
                option.series[0]['data'][2]['value'] = data.LEVEL3;
                option.series[1]['data'][0]['value'] = data.LEVEL1;
                option.series[1]['data'][1]['value'] = data.LEVEL2;
                option.series[1]['data'][2]['value'] = data.LEVEL3;
                option.series[2]['data'][0]['value'] = data.LEVEL1;
                option.series[2]['data'][1]['value'] = data.LEVEL2;
                option.series[2]['data'][2]['value'] = data.LEVEL3;

                self.option4(option);
            } else {
                cube.indicate("数据加载失败");
            }

        })
        //网络安全
        axios.get(cube.gatewayURL + "riskManagement/riskEventWarningLevelCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                var option = self.option5();
                var yxzs = 0;
                for(var i=0;i<Number3.length;i++){
                    switch(Number3[i]['name']){
                        case '1级':
                            yxzs += data['LEVEL1'];
                            Number3[i]['value'] = data['LEVEL1'];
                            break;
                        case '2级':
                            yxzs += data['LEVEL2'];
                            Number3[i]['value'] = data['LEVEL2'];
                            break;
                        case '3级':
                            yxzs += data['LEVEL3'];
                            Number3[i]['value'] = data['LEVEL3'];
                            break;
                        case '4级':
                            yxzs += data['LEVEL4'];
                            Number3[i]['value'] = data['LEVEL4'];
                            break;
                        case '5级':
                            yxzs += data['LEVEL5'];
                            Number3[i]['value'] = data['LEVEL5'];
                            break;
                    }
                }
                // console.log(Number3)
                // option.title[0]['subtext'] = yxzs + '';
                option.series[0]['data'] = Number3;
                self.option5(option);
            } else {
                cube.indicate("数据加载失败");
            }

        })
        //各电压等级作业风险数
        axios.get(cube.gatewayURL + "riskManagement/workplanVoltageLevelCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                var option = self.option3();
                var yxzs = 0;
                for(var i=0;i<Number4.length;i++){
                    switch(Number4[i]['name']){
                        case '110kV及以下':
                            yxzs += data['LEVEL1'];
                            Number4[i]['value'] = data['LEVEL110'];
                            break;
                        case '220kV（330kV）':
                            yxzs += data['LEVEL2'];
                            Number4[i]['value'] = data['LEVEL220'];
                            break;
                        case '500kV及以上':
                            yxzs += data['LEVEL3'];
                            Number4[i]['value'] = data['LEVEL500'];
                            break;
                    }
                }
                // console.log(Number4)
                // option.title[0]['subtext'] = yxzs + '';
                option.series[0]['data'] = Number4;
                self.option3(option);
            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});