// define(["echarts","axios"], function (echarts,axios) {
//     var PageViewModel = function (params) {
//         var self = this;
//         self._init = function () {
//             ConstWarnWork()
//         };
//         var xData = ["生产检修","大修技改","基建工程","营销作业","农网工程","配网工程","信息通信","内外部施工"],
//             yData = [];
//         self.option = {
//             color: ['#3398DB'],
//             tooltip: {
//                 trigger: 'axis',
//                 axisPointer: {
//                     type: 'line',
//                     lineStyle: {
//                         opacity: 0
//                     }
//                 }
//             },
//             grid: {
//                 left: '10',
//                 right: '10',
//                 bottom: '10',
//                 top: '20',
//                 containLabel: true,
//             },
//             xAxis: [{
//                 type: 'category',
//                 gridIndex: 0,
//                 data:xData,
//                 axisTick: {
//                     alignWithLabel: true
//                 },
//                 axisLine: {
//                     show: true,
//                     lineStyle: {
//                         color: '#FFFFFF'
//                     }
//                 },
//                 axisLabel: {
//                     show: true,
//                     color: '#ffffff'
//                 }
//             }],
//             yAxis: [{
//                 type: 'value',
//                 gridIndex: 0,
//                 minInterval:1,
//                 splitLine: {
//                     show: false
//                 },
//                 axisTick: {
//                     show: false
//                 },
//                 axisLine: {
//                     show: true,
//                     lineStyle: {
//                         color: '#FFFFFF'
//                     }
//                 },
//                 axisLabel: {
//                     show: true,
//                     color: '#ffffff'
//                 }
//             }
//             ],
//             series: [{
//                 type: 'bar',
//                 barWidth: '30%',
//                 xAxisIndex: 0,
//                 yAxisIndex: 0,
//                 barWidth: '12',
//                 itemStyle: {
//                     normal: {
//                         barBorderRadius: 30,
//                         color: new echarts.graphic.LinearGradient(
//                             0, 0, 0, 1, [{
//                                 offset: 0,
//                                 color: '#56FBFE'
//                             },
//                                 {
//                                     offset: 1,
//                                     color: '#38B2E9'
//                                 }
//                             ]
//                         )
//                     }
//                 },
//                 data:yData,
//                 zlevel: 11
//
//             }
//
//             ]
//         };
//         ConstWarnWork()
//         function ConstWarnWork(){
//             axios.get(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWarnWorkCNT?areaId=" + params).then(
//                 function(res){
//                     if(res.data.successful){
//                         var data = res.data.resultValue;
//                         var option = self.option();
//                         var yData = [];
//                         for(var key in data[0]){
//                             yData.push(data[0][key])
//                         }
//                         option.series[0].data = yData;
//                         self.option(option);
//                     }
//
//                 }
//             )
//         }
//
//         cube.endViewModel(self, params);
//     };
//     return PageViewModel;
// });

define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;

        ConstWarnWork()
        var xData = ["生产检修","大修技改","基建工程","营销作业","农网工程","配网工程","信息通信","内外部施工"],
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
                // barWidth: '30%',
                xAxisIndex: 0,
                yAxisIndex: 0,
                barWidth: '12',
                itemStyle: {
                    normal: {
                        barBorderRadius: 30,
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1, [{
                                offset: 0,
                                color: '#56FBFE'
                            },
                                {
                                    offset: 1,
                                    color: '#38B2E9'
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

        function ConstWarnWork(){
            console.log(params)
            axios.get(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWarnWorkCNT?areaId=" + params).then(
                function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        console.log(data)
                        var option = self.option();
                        yData = [];

                        for(var key in data[0]){
                            yData.push(data[0][key])
                        }

                        // console.log(yData)
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