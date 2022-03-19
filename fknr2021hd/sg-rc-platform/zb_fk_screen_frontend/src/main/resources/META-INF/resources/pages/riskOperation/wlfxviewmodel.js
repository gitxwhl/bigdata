define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        console.log(params)
        var self = this;
        /**
         * 初始化方法
         * @ignore
         */
        self._init = function () {
            WarnWorkState()
        };
        
        var xData = ["未开工","已开工","已完工","计划延时","计划取消"],
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
                    barWidth: '16',
                    itemStyle: {
                        normal: {
                            barBorderRadius: 30,
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [{
                                        offset: 0,
                                        color: '#F82F06'
                                    },
                                    {
                                        offset: 0.5,
                                        color: '#F54825'
                                    },
                                    {
                                        offset: 1,
                                        color: '#F1694E'
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
        
        function WarnWorkState(){
            axios.post(cube.gatewayURL + "riskWorkWarnForAll/srpRiskWorkWarnWorkStateCNT?areaId=" + params).then(
                function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        var option = self.option();
                        yData = [];
                        for(var key in data){
                            yData.push(data[key])
                        }
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