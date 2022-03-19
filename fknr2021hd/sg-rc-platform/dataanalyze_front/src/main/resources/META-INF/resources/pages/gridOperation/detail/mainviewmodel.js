define(["RESTClient","echarts","axios"], function(RestClient,echarts,axios) {
    var PageViewModel = function(params) {
        var self = this;
        self.id=params.items
        // console.log(params.items)
        self.title=''//预警标题
        self.number=''//预警编号
        self.hierarchy=''//预警等级
        self.startTime=''//预警计划开始时间
        self.endtTime=''//预警计划结束时间
        self.monad=''//发布单位
        self.staff=''//发布人员
        self.status=''//预警状态

        self.warnrel=''//解除信息
        self.name=''//调度员
        self.phone=''//联系方式
        self.removedate=''//解除时间
        self.src='./resources/images/gridOperation/u801.png'
        self.first=function (flag) {
            $(".feedbacks").css("color","#3466ad");
            $(".releases").css("color","#49f9f3");
            $(".reports").css("color","#3466ad");
            $(".informs").css("color","#3466ad");
            $(".relieves").css("color","#3466ad");
            $(".release").css("background-image", "url('./resources/images/gridOperation/u744.png')");
            $(".feedback").css("background-image", "url('./resources/images/gridOperation/u749.png')");
            $(".report").css("background-image", "url('./resources/images/gridOperation/u754.png')");
            $(".inform").css("background-image", "url('./resources/images/gridOperation/u759.png')");
            $(".relieve").css("background-image", "url('./resources/images/gridOperation/u764.png')");
            $(".relieves_content").hide()
            $(".publish").show()
            $(".feedbackss").hide()
            $(".reportss").hide()
            $(".InformSingle").hide()

        }
        self.second=function (flag) {
            $(".feedbacks").css("color","#49f9f3");
            $(".releases").css("color","#3466ad");
            $(".reports").css("color","#3466ad");
            $(".informs").css("color","#3466ad");
            $(".relieves").css("color","#3466ad");

            $(".release").css("background-image", "url('./resources/images/gridOperation/u797.png')");
            $(".feedback").css("background-image", "url('./resources/images/gridOperation/u792.png')");
            $(".report").css("background-image", "url('./resources/images/gridOperation/u754.png')");
            $(".inform").css("background-image", "url('./resources/images/gridOperation/u759.png')");
            $(".relieve").css("background-image", "url('./resources/images/gridOperation/u764.png')");
            $(".relieves_content").hide()
            $(".publish").hide()
            $(".feedbackss").show()
            $(".reportss").hide()
            $(".InformSingle").hide()
        }
        self.three=function (flag) {
            $(".feedbacks").css("color","#3466ad");
            $(".releases").css("color","#3466ad");
            $(".reports").css("color","#49f9f3");
            $(".informs").css("color","#3466ad");
            $(".relieves").css("color","#3466ad");
            $(".release").css("background-image", "url('./resources/images/gridOperation/u797.png')");
            $(".feedback").css("background-image", "url('./resources/images/gridOperation/u749.png')");
            $(".report").css("background-image", "url('./resources/images/gridOperation/u817.png')");
            $(".inform").css("background-image", "url('./resources/images/gridOperation/u759.png')");
            $(".relieve").css("background-image", "url('./resources/images/gridOperation/u764.png')");
            $(".relieves_content").hide()
            $(".publish").hide()
            $(".feedbackss").hide()
            $(".reportss").show()
            $(".InformSingle").hide()

        }
        self.four=function (flag) {
            $(".feedbacks").css("color","#3466ad");
            $(".releases").css("color","#3466ad");
            $(".reports").css("color","#3466ad");
            $(".informs").css("color","#49f9f3");
            $(".relieves").css("color","#3466ad");
            $(".release").css("background-image", "url('./resources/images/gridOperation/u797.png')");
            $(".feedback").css("background-image", "url('./resources/images/gridOperation/u749.png')");
            $(".report").css("background-image", "url('./resources/images/gridOperation/u754.png')");
            $(".inform").css("background-image", "url('./resources/images/gridOperation/u852.png')");
            $(".relieve").css("background-image", "url('./resources/images/gridOperation/u764.png')");
            $(".relieves_content").hide()
            $(".publish").hide()
            $(".feedbackss").hide()
            $(".reportss").hide()
            $(".InformSingle").show()

        }
        self.five=function (flag) {
            $(".feedbacks").css("color","#3466ad");
            $(".releases").css("color","#3466ad");
            $(".reports").css("color","#3466ad");
            $(".informs").css("color","#3466ad");
            $(".relieves").css("color","#49f9f3");
            $(".release").css("background-image", "url('./resources/images/gridOperation/u797.png')");
            $(".feedback").css("background-image", "url('./resources/images/gridOperation/u749.png')");
            $(".report").css("background-image", "url('./resources/images/gridOperation/u754.png')");
            $(".inform").css("background-image", "url('./resources/images/gridOperation/u759.png')");
            $(".relieve").css("background-image", "url('./resources/images/gridOperation/u902.png')");
            $(".relieves_content").show()
            $(".publish").hide()
            $(".feedbackss").hide()
            $(".reportss").hide()
            $(".InformSingle").hide()

        }
        axios.get(cube.gatewayURL + "riskGridWarn/riskGridWarnInfo/" +self.id,).then(function(res) {
            if (true) {
                var data = res.data.resultValue.SrpRiskGridWarnInfo;
                var list = res.data.resultValue.SrpRiskGridWarnRelInfo;
                // console.log(res.data.resultValue.SrpRiskGridWarnRelInfo)
                self.title(data.TITLE)//预警标题
                self.number(data.WARNNUM)//预警编号
                self.hierarchy(data.WARNINGLEVEL)//预警等级
                self.startTime(data.WARNBEGINTIME)//预警计划开始时间
                self.endtTime(data.WARNENDTIME)//预警计划结束时间
                self.monad(data.PUBLISH_ORG)//发布单位
                self.staff(data.PUBLISH_STAFF)//发布人员
                self.status(data.WARNSTATUS)//预警状态

                self.warnrel(list.WARNREL)//解除信息
                self.name(list.WARNREMOVE_STAFF)//调度员
                self.phone(list.PHONENUMBER)//联系方式
                self.removedate(list.REMOVEDATE)//解除时间

            } else {
                cube.indicate("数据加载失败");
            }

        })
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});