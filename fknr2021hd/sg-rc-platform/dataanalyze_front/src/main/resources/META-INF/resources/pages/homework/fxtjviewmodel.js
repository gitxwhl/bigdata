define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        $('.btnfx>div').click(function(){
            if($(this).hasClass('bjys')){
                return;
            }
            switch($(this).text()){
                case "网络风险":
                    self.warningLevel('5');
                    break;
                case "产业风险":
                    self.warningLevel('4');
                    break;
                case "作业风险":
                    self.warningLevel('2');
                    break;
                case "电网风险":
                    self.warningLevel('1');
                    break;
            }
            WorkWarnArea()
            $(this).addClass('bjys').siblings().removeClass('bjys')
        })
        self.warningLevel = ko.observable('01');
        
        var xData = [],
            yData = [];
        
        self.option = {
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'line',
                    lineStyle: {
                        opacity: 0
                    }
                }
            },
            grid: {
                left: '10',
                right: '10',
                bottom: '10',
                top: '40',
                containLabel: true,
            },
            xAxis: [{
                type: 'category',
                gridIndex: 0,
                data: xData,
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#0080FF'
                    }
                },
                axisLabel: {
                    interval:0,
                    show: true,
                    color: '#ffffff'
                },
                splitLine: {
                    show: false
                }
            }],
            yAxis: [{
                    type: 'value',
                    gridIndex: 0,
                    minInterval:1,
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#0080FF'
                    }
                },
                    axisLabel: {
                        show: true,
                        color: '#ffffff'
                    }
                }
            ],
            series: [{
                    type: 'bar',
                    barWidth: '30%',
                    xAxisIndex: 0,
                    yAxisIndex: 0,
                    itemStyle: {
                        normal: {
                            barBorderRadius: 30,
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [{
                                        offset: 0,
                                        color: '#00feff'
                                    },
                                    {
                                        offset: 0.5,
                                        color: '#027eff'
                                    },
                                    {
                                        offset: 1,
                                        color: '#0286ff'
                                    }
                                ]
                            )
                        }
                    },
                    data: yData,
                    zlevel: 11

                }
            
            ]
        };
        WorkWarnArea()
        function WorkWarnArea(){
            var jsonStr = {
                "warningLevel":"",
                "warnType":self.warningLevel()
            }
            axios.post(cube.gatewayURL + "riskWorkWarn/srpRiskWorkWarnAreaCNT",jsonStr).then(function(res) {
                if (res.data.successful == true) {
                    var data = res.data.resultValue;
                    // console.log(data)
                    xData = [], yData = [];
                    var option = self.option()
                    for(var i=0;i<data.length;i++){
                        xData.push(data[i]['DATAREPORT_ORG'])
                        yData.push(data[i]['GRIDWARN_CNT'])
                    }
                    option.xAxis[0].data = xData;
                    option.series[0].data = yData;
                    self.option(option)
                }
    
            })
        }

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});