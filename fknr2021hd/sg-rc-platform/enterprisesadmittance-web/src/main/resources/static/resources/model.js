define([ './app',"sammy", "axios",], function (RestClient,echarts, axios) {

    var PageViewModel = function() {
        var self = this;
        // var href=window.location.search
        // console.log(window.localStorage)



        window.commonPageBridge = cube.obj({
            name: '',
            params: {}
        });
        // var self = this;
        self.moduleOptions = ko.observable({ name: 'enterpriseInformation.enterpriseInformation',params: {} });
          self.showModuleList = ko.observable(false);
            // self.user_token=ko.observable("")

        self.logIn=ko.observable('')
        // GetRequest()
        var log=''
        function GetRequest() {
            var url = location.search; //获取url中"?"符后的字串
            if (url.indexOf("?") != -1) {    //判断是否有参数
                var obj = {};
                var url = location.search;
                var arr = url.substring(1).split("&");
                for (var i=0; i<arr.length; i++) {
                    obj[arr[i].split('=')[0]] = arr[i].split('=')[1]
                }
                if (obj.token) {
                    console.log(obj.token)
                    log=obj.token
                }
                // axios.get(cube.safetygatewayURL + 'personnelInformation/loginToken/'+log).then(function(res) {
                //     if (true) {
                //         var data=res.data.resultValue[0].user_token
                //         self.user_token(data)
                //     } else {
                //         cube.indicate("数据加载失败");
                //     }
                // })

            }
        }
          // if(log==self.user_token()){

          if(window.location.search.includes('?page=1')==true) {
              self.moduleOptions = ko.observable({ name: 'Businsee.Businsee',params: {} })
           }else if(window.location.search.includes('?page=2')==true){
              self.moduleOptions = ko.observable({ name: 'personnel.personnel',params: {} })
           }else{
              self.moduleOptions = ko.observable({ name: 'enterpriseInformation.enterpriseInformation',params: {} });
           }
           // } else {
           //      window.location.href="20.1.60.108:18080/rmcp-web-front/login.html"
           //
           // }

        // self.moduleOptions = ko.observable({ name: 'riskPanorama.riskPanorama',params: {} });
        // self.moduleOptions = ko.observable({ name: 'enterpriseInformation.enterpriseInformation',params: {} });
        // self.moduleOptions = ko.observable({ name: 'head.main',params: {} });
        // self.toggleModuleList = function (e) {
        //    $(".dianWang").attr("src","./resources/images/u17.png");
        //     $(".anQuan").attr("src","./resources/images/u5.png");
        //     $(".Left_column2").css({'display':'none'});
        //     $(".Left_column").css({'display':'block'});
        // }
        // self.riskControl=function(){
        //     $(".anQuan").attr("src","./resources/images/u666.png");
        //     $(".dianWang").attr("src","./resources/images/u222.png");
        //     $(".Left_column").css({'display':'none'});
        //     $(".Left_column2").css({'display':'block'});
        // }
        //安全考场分类
        // self.SafetyTest = function (e) {
        //     $(".examination").toggle();
        // }
        self.toggleModule = function (e) {
            $(".examination").css({'display':'none'});
            // 根据e判断显示对应的组件
            if( e ==1) {
                self.moduleOptions({ name: 'riskPanorama.riskPanorama',params: {} })
            }
            if( e == 2) {
                self.moduleOptions({ name: 'operationRisk.operationRisk',params: {} })
            }
            if( e == 11) {
                self.moduleOptions({ name: 'dataInput.dataInput',params: {} })
            }
            if( e == 3) {
                self.moduleOptions({ name: 'enterpriseInformation.enterpriseInformation',params: {} })
            }
            if(e == 4){
                self.moduleOptions({ name: 'riskAnalysis.main',params: {} })
            }
            if(e == 5){
                self.moduleOptions({ name: 'blacklist.main',params: {} })
            }
            if(e == 6){
                self.moduleOptions({ name: 'notification.notification',params: {} })
            }
            if(e == 7){
                self.moduleOptions({ name: 'learn.learn',params: {} })
            }
            if(e == 8){
                self.moduleOptions({ name: 'examine.main',params: {} })
            }
            if(e == 9){
                self.moduleOptions({ name: 'safetyTest.main',params: {} })
            }
            if(e == 10){
                self.moduleOptions({ name: 'sureness.main',params: {} })
            }
            if(e == 15){
                self.moduleOptions({ name: 'blackSystem.blackSystem',params: {} })
            }
            if(e == 16){
                self.moduleOptions({ name: 'NegativeList.NegativeList',params: {} })
            }

            if(e == 17){
                self.moduleOptions({ name: 'securityExamination.securityExamination',params: {} })
            }
            //self.moduleOptions({ name: 'head.main',params: {} })
            self.showModuleList(!self.showModuleList());
        }

        $(document).on('click',function (event) {
            self.showModuleList(false)
        })

        $('.Left_column2>div,.Left_column>div').click(function(){
            $(this).addClass('ative').siblings().removeClass('ative')
        })
    };

    var pvm = new PageViewModel();

    $(document).ready(function(e) {
        cube.startWebPage(pvm);
        /**
		 * 垂直方向分为24等份
		 * part 份数
		 * exclude 排除高度
		 */
		cube.getHeightByPart = function(part, exclude) {
			!exclude && (exclude = 0);
			return Math.round(($('#section').height() - exclude) * part / 24) + 'px';
		}
    });


});



