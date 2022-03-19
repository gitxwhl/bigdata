define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
       const dataSet = [
            ['终端弱口令', '敏感文档外发', '终端违规外联', '敏感邮件阻断', '异常文件下载'],
            []
        ];

        self.option = {
            tooltip:{},
            grid: {
                left: '10',
                right: '10',
                bottom: '10',
                top: '20',
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    interval: 0,
                    color: 'rgba(255,255,255, 0.5)',
                },
                splitLine: {
                    show: false,
                },
                data: dataSet[0],
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    color: 'rgba(255,255,255, 0.5)',
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        type: 'dashed',
                        color: 'rgba(255,255,255,0.1)',
                        width: 2,
                    },
                },
            },
            series: [
                {
                    data: dataSet[1],
                    type: 'bar',
                    barWidth: 20,
                    itemStyle: {
                        color: '#165D87',
                        borderWidth: 3,
                        borderColor: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [{
                                    offset: 0,
                                    color: '#156dff'
                                },
                                {
                                    offset: 1,
                                    color: '#00eaeb'
                                },
                            ]
                        ),
                        shadowColor: 'blue',
                        shadowBlur: 12,
                        shadowOffsetX: 0,
                        shadowOffsetY: 0,
                    },
                },
            ],
        };

        //违规行为类型占比情况数
        axios.get(cube.gatewayURL + "riskEventWarn/srpRiskEventWarnViolationCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                var option = self.option();
                for(var i in data){
                      dataSet[1].push(data[i])
                }
                self.option(option);
                // console.log(dataSet)
            } else {
                cube.indicate("数据加载失败");
            }

        })

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});