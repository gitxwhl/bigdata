define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios){
    var PageViewModel = function (params) {
        var self = this;
        self.monthTotal=0;
        var dataStyle = {
            normal: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                },
                shadowBlur: 0,
                shadowColor: '#203665'
            }
        };
        self.option = {
            series: [{
                type: 'pie',
                clockWise: false,
                radius: [40, 50],
                itemStyle: dataStyle,
                hoverAnimation: false,
                center: ['20%', '50%'],
                data: [{
                    value: 0,
                    label: {
                        normal: {
                            rich: {
                                a: {
                                    color: '#3a7ad5',
                                    align: 'center',
                                    fontSize: 16
                                },
                                b: {
                                    align: 'center',
                                    fontSize: 14
                                }
                            },
                            formatter: function(params){
                                if(params.value){
                                    return "{b|一般风险}\n"+"{a|"+params.value+"次}";
                                }else{
                                    return "{b|一般风险}\n"+"{a|"+"0次}";
                                }
                            },
                            position: 'center',
                            show: true,
                            textStyle: {
                                fontSize: '14',
                                fontWeight: 'normal',
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#2c6cc4',
                            shadowColor: '#2c6cc4',
                            shadowBlur: 0
                        }
                    }
                }, {
                    value: 100,
                    name: 'invisible',
                    itemStyle: {
                        normal: {
                            color: '#24375c'
                        },
                        emphasis: {
                            color: '#24375c'
                        }
                    }
                }]
            }, {
                type: 'pie',
                clockWise: false,
                radius: [40, 50],
                itemStyle: dataStyle,
                hoverAnimation: false,
                center: ['50%', '50%'],
                data: [{
                    value: 0,
                    label: {
                        normal: {
                            rich: {
                                a: {
                                    color: '#603dd0',
                                    align: 'center',
                                    fontSize: 16,
                                },
                                b: {
                                    align: 'center',
                                    fontSize: 14
                                }
                            },
                            formatter: function(params){
                                if(params.value){
                                    return "{b|重大风险}\n"+"{a|"+params.value+"次}";
                                }else{
                                    return "{b|重大风险}\n"+"{a|"+"0次}";
                                }
                            },
                            position: 'center',
                            show: true,
                            textStyle: {
                                fontSize: '14',
                                fontWeight: 'normal',
                                color: '#fff'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#EF45AC',
                            shadowColor: '#613fd1',
                            shadowBlur: 0
                        }
                    }
                }, {
                    value: 100,
                    name: 'invisible',
                    itemStyle: {
                        normal: {
                            color: '#412A4E'
                        },
                        emphasis: {
                            color: '#412A4E'
                        }
                    }
                }]
            }, {
                type: 'pie',
                clockWise: false,
                radius: [40, 50],
                itemStyle: dataStyle,
                hoverAnimation: false,
                center: ['80%', '50%'],
                data: [{
                    value: 0,
                    label: {
                        normal: {
                            rich: {
                                a: {
                                    color: '#603dd0',
                                    align: 'center',
                                    fontSize: 16
                                },
                                b: {
                                    align: 'center',
                                    fontSize: 14
                                }
                            },
                            formatter: function(params){
                                if(params.value){
                                    return "{b|特别重大}\n"+"{a|"+params.value+"次}";
                                }else{
                                    return "{b|特别重大}\n"+"{a|"+"0次}";
                                }
                            },
                            position: 'center',
                            show: true,
                            textStyle: {
                                fontSize: '14',
                                fontWeight: 'normal',
                                color: '#fff'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#FF0000',
                            shadowColor: '#613fd1',
                            shadowBlur: 0
                        }
                    }
                }, {
                    value: 100,
                    name: 'invisible',
                    itemStyle: {
                        normal: {
                            color: '#453284'
                        },
                        emphasis: {
                            color: '#453284'
                        }
                    }
                }]
            }]
        }
        //产业安全风险预警数
        axios.get(cube.gatewayURL + "riskIndusRiskWarn/riskIndusRiskWarnCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                self.monthTotal(data.WARNINGLEVEL_CNT)
                var option = self.option();
                option.series[0].data[0].value = data['WARNINGLEVEL1'];
                option.series[0].data[1].value = data.WARNINGLEVEL_CNT - data['WARNINGLEVEL1'];
                option.series[1].data[0].value = data['WARNINGLEVEL2'];
                option.series[1].data[1].value = data.WARNINGLEVEL_CNT - data['WARNINGLEVEL2'];
                option.series[2].data[0].value = data['WARNINGLEVEL3'];
                option.series[2].data[1].value = data.WARNINGLEVEL_CNT - data['WARNINGLEVEL3'];
                self.option(option)
            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});