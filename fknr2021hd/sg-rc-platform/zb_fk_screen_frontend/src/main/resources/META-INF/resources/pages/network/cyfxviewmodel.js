define(["echarts","axios"], function (echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        
    var m2R2Data= [
        {value:0, name:"远程代码破解",itemStyle:{color:"#8d7fec"}},
        {value:0, name:"一般暴力破解",itemStyle:{color:"#5085f2"}},
        {value:0, name:"目录遍历",itemStyle:{color:"#e75fc3"}},
        {value:0, name:"未授访问权限",itemStyle:{color:"#f87be2"}},
        {value:0, name:"漏洞扫描",itemStyle:{color:"#f2719a"}},
        {value:0, name:"注入漏洞",itemStyle:{color:"#fca4bb"}},
        {value:0, name:"其他",itemStyle:{color:"#f59a8f"}}
    ];


    self.option = {
        title: [    
        {
            text: '合计',
            subtext: 0,
            textStyle:{
                fontSize:20,
                color:"#fff"
            },
            subtextStyle: {
                fontSize: 20,
                color: '#fff'
            },
            textAlign:"center",
            x: '34.5%',
            y: '40%',
        }],
        tooltip: {
            trigger: 'item',
            formatter:function (parms){
                var str=
                parms.marker+""+parms.data.name+"</br>"+
                "数量："+ parms.data.value+"</br>"+
                "占比："+ parms.percent+"%";
                return  str ;
        }
        },
        legend: {
            left:'72%',
            top:'center',
            textStyle: {
                color:'#8C8C8C'
            },
        },
    series: [
        {
            type:'pie',
            center: ['35%', '50%'],
            radius: ['40%', '60%'],
            clockwise: false, //饼图的扇区是否是顺时针排布
            avoidLabelOverlap: true,
            label: {
                normal: {
                    show: true,
                    position: 'outter',
                    formatter:function (parms){
                        return parms.data.name
                    }
                }
            },
            labelLine: {
                normal: {
                    length:15,
                    length2:8,
                    smooth:true,
                }
            },
            data:m2R2Data
        }
    ]
    };
        //攻击威胁类型占比情况数
        axios.get(cube.gatewayURL + "riskEventWarn/srpRiskEventWarnAttackThreatCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(data)
                var option = self.option();
                var yxzs = 0;
                for(var i=0;i<m2R2Data.length;i++){
                    switch(m2R2Data[i]['name']){
                        case '远程代码破解':
                            yxzs += data['ATTACK_THREAT1'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT1'];
                            break;
                        case '一般暴力破解':
                            yxzs += data['ATTACK_THREAT2'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT2'];
                            break;
                        case '目录遍历':
                            yxzs += data['ATTACK_THREAT3'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT3'];
                            break;
                        case '未授访问权限':
                            yxzs += data['ATTACK_THREAT4'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT4'];
                            break;
                        case '漏洞扫描':
                            yxzs += data['ATTACK_THREAT5'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT5'];
                            break;
                        case '注入漏洞':
                            yxzs += data['ATTACK_THREAT6'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT6'];
                            break;
                        case '其他':
                            yxzs += data['ATTACK_THREAT7'];
                            m2R2Data[i]['value'] = data['ATTACK_THREAT7'];
                            break;
                    }
                }
                // console.log(m2R2Data)
                option.title[0]['subtext'] = yxzs + '';
                option.series[0]['data'] = m2R2Data;
                self.option(option);
            } else {
                cube.indicate("数据加载失败");
            }

        })

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});