define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        // self.data = [{
        //     month: "北京",
        //     value: 12,
        //     ratio: 87.3
        // },
        //
        //     {
        //         month: "天津",
        //         value: 23,
        //         ratio: 87.3
        //     },
        //
        //     {
        //         month: "河北",
        //         value: 24,
        //         ratio: 87.3
        //     },
        //
        //     {
        //         month: "冀北",
        //         value: 35,
        //         ratio: 87.3
        //     },
        //
        //     {
        //         month: "山西",
        //         value:34,
        //         ratio: 87.3
        //     },
        //
        //
        //     {
        //         month: "山东",
        //         value: 57,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "上海",
        //         value: 34,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "江苏",
        //         value: 45,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "浙江",
        //         value: 46,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "安徽",
        //         value: 78,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "福建",
        //         value: 35,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "湖北",
        //         value: 56,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "湖南",
        //         value: 25,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "河南",
        //         value: 84,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "江西",
        //         value: 97,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "辽宁",
        //         value: 53,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "吉林",
        //         value: 87,
        //         ratio: 87.3
        //     },
        //     {
        //         month: "黑龙江",
        //         value: 97,
        //         ratio: 87.3
        //     },
        //
        // ];
        self.data = [];
        var xData = [],
        yData = [];
        // self.data.map(function(a, b) {
        //     xData.push(a.month);
        //     if (a.value === 0) {
        //         yData.push(a.value);
        //     } else {
        //         yData.push(a.value);
        //     }
        // });
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
                data: [],
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: '#FFFFFF'
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
                    splitLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: false,
                        lineStyle: {
                            color: '#FFFFFF'
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
                    data: [],
                    zlevel: 11

                }
            
            ]
        };

        axios.get(cube.gatewayURL + "riskEventWarn/srpRiskEventWarnCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                var option = self.option();
                for(var i=0;i<data.length;i++){
                    xData.push(data[i]['DATAREPORT_ORG'])
                    yData.push(data[i]['EVENTEWARN_CNT'])
                }

                option.xAxis[0]['data'] = xData;
                option.series[0]['data'] = yData;
                self.option(option);
            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});