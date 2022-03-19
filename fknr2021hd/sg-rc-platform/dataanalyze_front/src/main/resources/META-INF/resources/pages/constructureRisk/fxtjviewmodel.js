define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.option2 ={
            // title: {
            //     text: '各单位电网风险停电事由分布情况',
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
                data: ['2019', '2020'],
            },
            grid: { //图表的位置
                top: '20%',
                left: '3%',
                right: '4%',
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
                    show: true,
                    lineStyle: {
                        color: '#0080FF'
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
                    show: true,
                    lineStyle: {
                        color: '#0080FF'
                    }
                },
                data:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
            }],
            series: [{
                name: '2019',
                color:'#8ddaeb',
                type: 'bar',
                stack: 'sum',
                barWidth: '15px',
                data:[]

            },
                {
                    name: '2020',
                    color:'#6cc518',
                    type: 'bar',
                    barWidth: '15px',
                    stack: 'sum2',
                    data:[]
                },

            ]
        };
        ConstWarnYear()
        function ConstWarnYear(){
            axios.get(cube.gatewayURL + "riskGridConstWarnn/srpRiskGridConstWarnYearCNT?areaId=" + params).then(
                function(res){
                    if(res.data.successful){
                        var data = res.data.resultValue;
                        // console.log(data)
                        var option = self.option2();
                        var yData1 = [],yData2=[];
                        for(var key in data['lastYearCNT']){
                            yData1.push(data['lastYearCNT'][key])
                        }
                        for(var key in data['thisYearCNT']){
                            yData2.push(data['thisYearCNT'][key])
                        }
                        option.series[0].data = yData1;
                        option.series[1].data = yData2;
                        self.option2(option)
                        
                    }
                    
                }
            )
        }

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});