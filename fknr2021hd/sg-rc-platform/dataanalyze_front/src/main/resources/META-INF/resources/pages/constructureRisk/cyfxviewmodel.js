define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        // var colors = ['#5793f3', '#d14a61', '#675bba'];
        self.option3 ={
            // title: {
            //     text: '各单位电网风险电压等级分布情况',
            //     left:'40%',
            //     top: "35",
            //     textStyle: {
            //         color: '#fff',
            //         fontSize: 18
            //     }
            // },
            // color: ['#388BFF', '#05C3FA', '#F6931C', '#FFD52E'],
            tooltip: {
                trigger: 'axis',
            },
            legend: {
                right:'4%',
                top: '8%',
                textStyle: {  //图标文字颜色大小设置
                    color: '#fff',
                    fontSize: 16
                },
                data:['本周开始','本周解除'],
            },
            grid: { //图表的位置
                top: '20%',
                left: '1%',
                right: '2%',
                bottom: '5%',
                containLabel: true
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
                    show: true,
                    lineStyle: {
                        color: '#0080FF'
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
                    splitNumber: 13,
                    textStyle: {
                        fontSize: 14,
                        color: 'white'
                    },

                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#0080FF'
                    }
                },
                data: [],
            }],
            series: [{
                name: '本周开始',
                type: 'bar',
                barWidth:15,
                stack: 'sum',
                itemStyle: {
                    normal: {
                        color: '#05c3fa'
                    }
                },
                data:[]       //数据
            },{
                name: '本周解除',
                type: 'bar',
                barWidth:15,
                stack: 'sum',
                itemStyle: {
                    normal: {
                        color: '#388bff'  //柱子颜色
                    }
                },
                data:[],        //数据
            }
            ]
        };
        ConstWarnBeginOrEnd()
        function ConstWarnBeginOrEnd(){
            axios.get(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWarnBeginOrEndCNT?areaId=" + params).then(
                function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        // console.log(data)
                        var option = self.option3();
                        var xData = [],yData = [],yData1 = [];
                        for(var i=0;i<data['constWarnBeginCNT'].length;i++){
                            xData.push(data['constWarnBeginCNT'][i]['DATAREPORT_ORG'])
                            yData.push(data['constWarnBeginCNT'][i]['GRIDWARN_CNT'])
                        }
                        for(var i=0;i<data['constWarnEndCNT'].length;i++){
                            yData1.push(data['constWarnEndCNT'][i]['GRIDWARN_CNT'])
                        }
                        option.xAxis[0].data = xData;
                        option.series[0].data = yData;
                        option.series[1].data = yData1;
                       
                        self.option3(option)
                    }
                    
                }
            )
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});