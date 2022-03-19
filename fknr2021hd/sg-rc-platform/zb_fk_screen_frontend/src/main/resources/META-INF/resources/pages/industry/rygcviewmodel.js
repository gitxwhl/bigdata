define(["RESTClient","axios"], function(RestClient,axios) {
	var PageViewModel = function(params) {
        var self = this;
        self.staffcnt = 0;
        self.securguardcnt = 0;
        self.option = {
            color:['#2FD5DF','#6076B2','#218BF2'],
            tooltip: {
                trigger: 'item',
            },
            legend: {
                bottom: 10,
                left: 'center',
                textStyle: {
                    color: '#fff'
                },
                itemWidth: 16,
                itemHeight: 10,
                data: ['派遣类', '合同制', '劳务外包']
            },
            series: [
                {
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '40%'],
                    data: [
                        {value: 0, name: '派遣类'},
                        {value: 0, name: '合同制'},
                        {value: 0, name: '劳务外包'}
                    ]
                }
            ]
        };
        axios.get(cube.gatewayURL + "riskIndusRiskWarn/srpRiskDkyCompanyStaffCNT").then(function(res) {
            if (true) {
                var data = res.data.resultValue;
                self.staffcnt(data.STAFF_CNT||0)
                self.securguardcnt(data.SECUR_GUARD_CNT||0)
                var option = self.option();
                option.series[0].data[0].value = data['DISPATCHEDPERSONNEL_CNT'] || 0;
                option.series[0].data[1].value = data['CONTRACT_CNT'] || 0;
                option.series[0].data[2].value = data['OUTSOURCING_LABOR_SERVICES_CNT'] || 0;
                self.option(option)
            } else {
                cube.indicate("数据加载失败");
            }

        })
		cube.endViewModel(self, params);
	};
	return PageViewModel;
});