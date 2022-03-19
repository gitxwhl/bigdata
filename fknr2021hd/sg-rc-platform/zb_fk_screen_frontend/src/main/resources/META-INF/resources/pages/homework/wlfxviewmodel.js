define(["axios"], function (axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.server = 0;
        self.equip = 0;
        self.virus = 0;
        self.illegal = 0;
        self.storage = 0;
        self.attack = 0;
        axios.get(cube.gatewayURL + "riskWorkWarn/srpRiskEventTypeCNT").then(function(res) {
            if (res.data.successful == true) {
                var data = res.data.resultValue;
                self.server(data['SERVER_CNT'] || 0);  //服务器数量
                self.equip(data['EQUIPSUPPORT_CNT'] || 0);  //设备到保数量
                self.virus(data['ATTACK_THREAT_CNT'] || 0);  //病毒处置数量
                self.illegal(data['VIRUS_DISPOSAL_CNT'] || 0);   //违规行为数
                self.storage(data['STORAGE_CAPACITY_SUM'] || 0);  //存储容量
                self.attack(data['ILLEGAL_CNT'] || 0);  //攻击威胁数
            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});