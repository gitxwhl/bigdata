define(["RESTClient","echarts","axios"], function (RestClient,echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.NumberServers='';//服务器数量
        self.numberEquipment='0';//设备到保数量
        self.NumberTreatments='';//病毒处置数量
        self.NumberViolations='';//违规行为数
        self.memory='';//存储容量
        self.NumberThreats='';//攻击威胁数

        //网络预警各维度数量统计
        axios.get(cube.gatewayURL + "riskEventWarn/srpRiskEventTypeCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                // console.log(data.STORAGE_CAPACITY_SUM)
                self.NumberServers(data.SERVER_CNT);//服务器数量
                self.numberEquipment(data.EQUIPSUPPORT_CNT);//设备到保数量
                self.NumberTreatments(data.ATTACK_THREAT_CNT);//病毒处置数量
                self.NumberViolations(data.VIRUS_DISPOSAL_CNT);//违规行为数
                self.memory(data.STORAGE_CAPACITY_SUM);//存储容量
                self.NumberThreats(data.ILLEGAL_CNT);//攻击威胁数

            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});