define([ 'axios',"md5","./app","sammy" ], function(axios,md5) {
    axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
    axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
    // axios.interceptors.response.use(
    //     response => {
    //         const status = response.status
    //         if (status === 302) {
    //             window.location.href=response.headers.Location
    //         }
    //         return response
    //     },
    //     error => {
    //         return Promise.reject(error)
    //     }
    // )
    // 请求拦截器
    axios.interceptors.request.use(config => {
        //增加签名
        let p_method = config.method;
        // let _key='c32ad1415f6c89fee76d8457c31efb4b';
        let url = config.url;
        // let userId = localStorage.getItem("userId");
        // if(!userId){
        //     userId=_key;
        // }
        // let userId = _key
        var params = "";
        var p_data = config.data;
        var sIndex = url.indexOf("/api/");
        var urlt = url.substring(sIndex);
        var lastUrl = ""
        console.log("urlt="+urlt+" method="+p_method);
        if(url.indexOf("?") > 0) {
            lastUrl = urlt.substring(urlt.indexOf("?")+1).replace("=","");
            urlt = urlt.substring(0, urlt.indexOf("?"));
        }
        console.log("urlt="+urlt)
        if('get'==p_method){
            //var subIndex = url.indexOf("?");
            var pdata=config.params;
            if(pdata) {
                //var pdata = url.substring(subIndex + 1, urlTemp.length);
                //pdata = objectToStr(pdata);
                console.log("pdata="+pdata);
                var pdata = getSign(pdata, "");
                console.log("pdata2="+pdata);
                params = urlt + encodeURIComponent(pdata) + "userIdc32ad1415f6c89fee76d8457c31efb4b";

            }else {
                params = urlt + lastUrl + "userIdc32ad1415f6c89fee76d8457c31efb4b";
            }
        }else {
            params = urlt + encodeURIComponent(JSON.stringify(p_data)) + "userIdc32ad1415f6c89fee76d8457c31efb4b";
        }

        console.log("params:"+params);
        let signValue = md5(params);
        if(url.indexOf("?") > 0){
            config.url=url+"&sign=" + signValue;
        }else{
            config.url=url+"?sign=" + signValue;
        }

        return config;
    }, err => {
        // 错误处理
        return Promise.resolve(err);
    });

    // 响应拦截器
    axios.interceptors.response.use(res => {
            if(res.status == 302){
                window.top.location.href = "/logout";
                return Promise.reject('error')
            }
            if(res.status == 200 && res.headers.authstatus == 401){
                window.top.location.href = "/logout";
                return Promise.reject('error')
            }
            if(res.data.resultHint=="用户未登录"){
                window.top.location.href = "/logout";
                return Promise.reject('error')
            }
            if(res.status == 200 && ['timeout','nouser','tokenTimeout'].includes( res.headers.sessionstate)){
                //refreshToken.resConfig(response.config)
                window.top.location.href = "/logout";
                return Promise.reject('error')
            }
            return res;
        },
        error => {
            console.log(error)
            return Promise.reject(error);
        }
    )

    // 方法
    /**
     * @param url 请求的url,应该包含请求参数(url的?后面的参数)
     * @param requestParams 请求参数(POST的JSON参数)
     * @returns {string} 获取签名
     */
    function getSign(url, requestParams) {
        var signString = "";
        //var urlParams = parseQueryString(url);
        var urlParams=url;
        var requestBody = sortObject(mergeObject(urlParams, requestParams));
        for (var i in requestBody) {
            signString += i + requestBody[i];
        }
        return signString;
//	        return md5.hex(signString).toUpperCase();
    }
    /**
     * @param url 请求的url
     * @returns {{}} 将url中请求参数组装成json对象(url的?后面的参数)
     */
    function parseQueryString(url) {
        var urlReg = /^[^\?]+\?([\w\W]+)$/,
            paramReg = /([^&=]+)=([\w\W]*?)(&|$|#)/g,
            urlArray = urlReg.exec(url),
            result = {};
        if (urlArray && urlArray[1]) {
            var paramString = urlArray[1], paramResult;
            while ((paramResult = paramReg.exec(paramString)) != null) {
                result[paramResult[1]] = paramResult[2];
            }
        }
        return result;
    }
    /**
     * @param object 传入要进行属性排序的对象
     * @returns {{}} 将对象进行属性排序(按首字母顺序进行排序)
     */
    function sortObject(object) {
        var objectKeys = Object.keys(object).sort();
        var result = {};
        for (var i in objectKeys) {
            result[objectKeys[i]] = object[objectKeys[i]];
        }
        return result;
    }

    function objectToStr(obj){
        let str = "";
        for(let key  in obj){
            //console.log(key + '---' + obj[key])
            str+=key+"="+obj[key]+"&";
        }
        if(str.length>0){
            str=str.substring(0,str.length-1);
        }
        return str;
    }

    /**
     * @returns {*} 将两个对象合并成一个
     */
    function mergeObject(objectOne, objectTwo) {
        if (objectTwo != null) {
            for (var key in objectTwo) {
                objectOne[key] = objectTwo[key]
            }
        }
        return objectOne;
    }

    var PageViewModel = function() {
        window.commonPageBridge = cube.obj({
            name: '',
            params: {}
        });
        var self = this;
        /*self.isShow = ko.observable(false);
        self.url = ko.observable('dataLoading.main');*/
        self.isShow = ko.observable(true);
        self.url = ko.observable('menuPage.main');
        self.toggleModule = function(e){
            self.url(e);
        }

        axios.get(cube.gatewayURL3 + "app/menu/getMenuTree").then(function(res) {
            if (res.data.successful) {
                var result=res.data.resultValue
                var arr = []
                result.forEach(ele=>{
                    if(ele.menu.name == "安全风险全景感知") {
                        arr = ele.childMenu
                    }
                })
                if(arr.length>0) {
                    var menulist = document.querySelector('#menuList')
                    menulist.innerHTML = ``
                    var menustr = ``
                    arr.forEach(ele=>{
                            if(ele.childMenu.length>0) {
                                menustr += `<div>
                            <div class="list hand" style="margin-top:10px;">${ele.menu.name}</div>`
                                ele.childMenu.forEach(ele2=>{
                                    menustr += `<div class="listLi hand" datapage="${ele2.menu.url}">${ele2.menu.name}</div>`
                                })
                                menustr += `</div>`
                            }else {
                                menustr += `<div>
                                   <div class="list hand" style="margin-top:10px;" datapage="menuPage.main">${ele.menu.name}</div>
                                </div>`
                            }
                    })
                    menulist.innerHTML = menustr
                }else {
                    cube.indicate("该用户没有权限查看全景感知");
                }
                $('.list').click(function(){
                    $(this).addClass('ative')
                    $('.list').not(this).removeClass('ative')
                })
                $('.listLi').click(function(){
                    var str = $(this).attr('datapage')
                    self.toggleModule(str)
                    $(this).addClass('ative')
                    $('.listLi').not(this).removeClass('ative')
                    $(this).parent().parent().find('.list').removeClass('ative')
                    $(this).parent().find('.list').addClass('ative')
                })
                $('.icon-arrow-right').click(function(){
                    $('.nav').show()
                })
                $('.icon-arrow-left').click(function(){
                    $('.nav').hide()
                })
                // 菜单显示隐藏
                $('.list').click(function(){
                    if($(this).siblings().length>0) {
                        $(this).siblings().toggle(10,function () {
                        })
                    }else {
                        var str = $(this).attr('datapage')
                        self.toggleModule(str)
                    }
                })
            } else {
                cube.indicate("菜单获取失败");
            }
        })

        // self.url = ko.observable('menuPage.main');
        self.url = ko.observable('safetyRisk.main');
        self.toggleModule = function(e){
            console.log(e)
            self.url(e);
        }
        /*$('.list').click(function(){
            $(this).addClass('ative')
            $('.list').not(this).removeClass('ative')
        })
        $('.listLi').click(function(){
            $(this).addClass('ative')
            $('.listLi').not(this).removeClass('ative')
            $(this).parent().parent().find('.list').removeClass('ative')
            $(this).parent().find('.list').addClass('ative')
        })
        $('.icon-arrow-right').click(function(){
            $('.nav').show()
        })
        $('.icon-arrow-left').click(function(){
            $('.nav').hide()
        })
        // 菜单显示隐藏
        $('.list').click(function(){
            $(this).siblings().toggle(10,function () {
            })
        })*/

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



