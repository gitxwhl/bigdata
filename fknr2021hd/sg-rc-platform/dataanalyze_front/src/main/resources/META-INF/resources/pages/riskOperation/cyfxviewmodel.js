define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        /**
         * 初始化方法
         * @ignore
         */
        self._init = function () {
            kWarnArea()
        };
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
                top: '20',
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
        function kWarnArea(){
            var jsonStr = {
                "areaId":params,
                "warnType":"1"
            }
            axios.post(cube.gatewayURL + "riskWorkWarnForAll/srpRiskWorkWarnAreaCNT",jsonStr).then(
                function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        var option = self.option();
                        xData = [], yData = [];
                        for(var i=0;i<data.length;i++){
                            xData.push(data[i]['DATAREPORT_ORG']);
                            yData.push(data[i]['PLANWARN_CNT']);
                        }
                        option.xAxis[0].data = xData;
                        option.series[0].data = yData;
                        self.option(option)
                    }
                    
                }
            )
        }

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});