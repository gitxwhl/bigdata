define(["RESTClient", "echarts", "axios"], function (RestClient, echarts, axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.contentLoading = '数据加载中';
        self.isShow = ko.observable("")
        self.isShow(true)
        var cishu = []
        var cishuItem = {}
        var cishuClone = []
        var cishuItemClone = {}
        var modelingIdClone = ''
        self.modelingId= ko.observable("1")

        //实例关系编号
        self.genrela = ko.observable("")
        function genrela() {
            axios.post(cube.gatewayURL2 + "atlasInformation/instanceNumber").then(function (res) {
                self.genrela(res.data.resultValue)
            })
        }

        genrela()
        //输入框onchange事件
        self.iptChange = function() {
            var ipt1 = document.querySelector("#ipt1")
            if(ipt1.value.length > 100) {
                ipt1.value = ipt1.value.substring(0,100)
                cube.indicate("输入框内容长度不能超过100！");
            }
        }
        //输入框onchange事件
        self.iptChange2 = function() {
            var ipt1 = document.querySelector("#ipt2")
            if(ipt1.value.length > 10) {
                ipt1.value = ipt1.value.substring(0,10)
                cube.indicate("名称长度不能超过10！");
            }
        }
        //输入框onchange事件
        self.iptChange3 = function() {
            var ipt1 = document.querySelector("#ipt3")
            if(ipt1.value.length > 50) {
                ipt1.value = ipt1.value.substring(0,50)
                cube.indicate("编码长度不能超过50！");
            }
        }
        //输入框onchange事件
        self.iptChange4 = function() {
            var ipt1 = document.querySelector("#ipt4")
            if(ipt1.value.length > 200) {
                ipt1.value = ipt1.value.substring(0,200)
                cube.indicate("描述长度不能超过200！");
            }
        }
        //输入框onchange事件
        self.iptChange5 = function() {
            var ipt1 = document.querySelector("#ipt5")
            if(ipt1.value.length > 10) {
                ipt1.value = ipt1.value.substring(0,10)
                cube.indicate("名称长度不能超过10！");
            }
        }
        //输入框onchange事件
        self.iptChange6 = function() {
            var ipt1 = document.querySelector("#ipt6")
            if(ipt1.value.length > 200) {
                ipt1.value = ipt1.value.substring(0,200)
                cube.indicate("描述长度不能超过200！");
            }
        }

        //实例新增
        function examadd(searchParams) {
            // 必填项校验
            if(!self.name().trim()){
                cube.indicate("请输入名称");
                return
            }
            if(!self.genrela().trim()){
                cube.indicate("请输入编码");
                return
            }
            if(!self.attribute().trim()){
                cube.indicate("请输入描述");
                return
            }
            if(!self.describe().trim()){
                cube.indicate("请输入属性");
                return
            }
            // if(!self.aaa().length){
            //     cube.indicate("请选择关联关系");
            //     return
            // }
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            var iptRep2 = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》:;：；/￥——{}\"‘”“'_()?？·（）【】’#]")
            if(iptRep.test(self.name().trim()) || iptRep.test(self.genrela().trim()) || iptRep2.test(self.attribute().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.name().trim().length !== self.name().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.genrela().trim().length !== self.genrela().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.attribute().trim().length !== self.attribute().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.describe().trim().length !== self.describe().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.name().trim().length > 10) {
                cube.indicate("名称长度不能超过10！");
                return;
            }
            if(self.genrela().trim().length > 50) {
                cube.indicate("编码长度不能超过50！");
                return;
            }
            if(self.attribute().trim().length > 200) {
                cube.indicate("描述长度不能超过200！");
                return;
            }
            var idList = []
            $('.messages i').each((i,ele)=>{
                if(ele.getAttribute('dataselected')) {
                    idList.push(...JSON.parse(ele.getAttribute('dataselected')))
                }
            })
            var params = {
                "params": {
                    "modelingId":self.modelingId(),
                    "instanceName": self.name(),
                    "instanceCode": self.genrela(),
                    "instanceDescribe": self.attribute()
                },

                // "idList":self.aaa()
                "idList":idList
            }
            if($("#addBtn").attr('dataClick') == 'true') {
                $("#addBtn").attr('dataClick', 'false')
                $("#addBtn").removeClass('know-btn').addClass('know-btn-disabled')
                axios.post(cube.gatewayURL2 + "atlasInformation/insertInstanceInfo", params).then(function (res) {
                    if (res.data.successful == true) {
                        window.localStorage.setItem('xzinstanceId', JSON.stringify(res.data.resultValue.id))
                        console.log(res.data.resultValue.id)
                        cube.indicate("新增成功");
                        // commonPageBridge({
                        //     name: 'hapMap.knowledge.main',
                        // });
                        // console.log(res)
                        genrela()
                        RiskList(self.pageIndex())
                        div1.className = 'xingzeng'
                        mask.style.display = 'none'
                    } else {
                        cube.indicate(res.data.resultHint);
                    }
                    $("#addBtn").attr('dataClick','true')
                    $("#addBtn").removeClass('know-btn-disabled').addClass('know-btn')
                })
            }
        }
        //实例详情
        // function details(searchParams) {
        //     console.log(searchParams[0].instanceId,444)
        //     var params = {
        //         paramJson
        //         :{"instanceId":searchParams[0].instanceId}}
        //     axios.post(cube.gatewayURL2 + "atlasInformation/getInstanceInfoById",params).then(function(res) {
        //         if (true) {
        //             console.log(res,333)
        //         } else {
        //             cube.indicate("数据加载失败");
        //         }

        //     }).finally(
        //         function() {
        //             cube.showLoading(false);
        //         }
        //     );
        // }


        setTimeout(function () {
            if (params.item) {
                var data = params.item;
                self.name(data.name);
                self.nodeName(data.nodeName);
                self.attribute(data.attribute);
            }
        })
        self.exadd = function () {
            examadd()
        }
        self.gbClick = function () {
            div1.className = 'xingzeng'
            div2.className='bianji'
            mask.style.display='none'
        }
        self.gbglsx=function(){
            div3.className='glsx'
            div4.className='bjglsx'
            mask2.style.display='none'
            mask3.style.display='none'
        }
        self.name = '';
        self.nodeName = '';
        self.attribute = '';
        self.describe = '';
        self.bjname = '';
        self.bjhxinstanceId = '';
        self.bjnodeName = '';
        self.bjattribute = '';
        self.bjdescribe = ''




        var div1 = document.querySelector('.xingzeng')
        var div2 = document.querySelector('.bianji')
        var div3 = document.querySelector('.glsx')
        var div4 = document.querySelector('.bjglsx')

        var felmt=document.querySelector('.messages')
        var felmtt=document.querySelector('.messagest')
        var notselect=document.querySelector('.unselect')
        var select=document.querySelector('.select')
        var bjunselect=document.querySelector('.bjunselect')
        var bjselect=document.querySelector('.bjselect')
        var ipt=document.querySelectorAll('.ipt')

        // var idmap=[]
        // var bjidmap=[]
        // var bjirizhimap = []
        // self.aaa=[]
        // self.bbb=[]
        // 按对象的属性值进行排序
        function compare(property){
            return function(a,b){
                var value1 = a[property];
                var value2 = b[property];
                return value1 - value2;
            }
        }
        //新增保存按钮
        self.pushId=function(){
            // var idmap= [];
            var selectArr = []
            for(var i=0;i<$('.select input').length;i++){
                if($('.select input').length!==$('.select input:checked').length){
                    cube.indicate("右侧请全部勾选");

                    return
                }
                selectArr.push({glsxName:$('.select input').eq(i).attr('glsxName'),selfname:$('.select input').eq(i).attr('selfname'),"instanceId":$('.select input').eq(i).attr('instid'),
                "relationId":$('.select input').eq(i).attr('relaid')})
                // idmap.push({glsxName:$('.select input').eq(i).attr('glsxName'),"instanceId":$('.select input').eq(i).attr('instid'),
                //     "relationId":$('.select input').eq(i).attr('relaid')})
                // var obj = {};
                // var peon = idmap.reduce(function(cur,next) {
                //     obj[next.instanceId] ? "" : obj[next.instanceId] = true && cur.push(next);
                //     return cur;
                // },[])
                // self.aaa(peon)
                // var params ={
                //     "instanceId":cishuItem.INSTANCE_ID,
                //     "instanceName":self.bjname(),
                //     "idList": idmap,
                //     "modelingId":self.modelingId()
                // }
                // axios.post(cube.gatewayURL2 + "atlasInformation/InsertInstanceInfoInclude", params).then(function (res) {
                //     if (res.data.successful==true) {
                //         // console.log(res,1)
                //         cube.indicate("保存成功");
                //
                //     } else {
                //         cube.indicate("保存失败");
                //     }
                //
                // })
                // div3.className='bjglsx'
            }
            $('.messages i[datathis="this"]').attr('dataselected',JSON.stringify(selectArr))

            var unselectArr = []
            for(var i=0;i<$('.unselect input').length;i++){
                unselectArr.push({glsxName:$('.unselect input').eq(i).attr('glsxName'),selfname:$('.unselect input').eq(i).attr('selfname'),"instanceId":$('.unselect input').eq(i).attr('instid'),
                "relationId":$('.unselect input').eq(i).attr('relaid')})
            }
            // 排序
            var newUnselectArr = unselectArr.sort(compare('instanceId'))
            $('.messages i[datathis="this"]').attr('dataunselected',JSON.stringify(newUnselectArr))

            div3.className='bjglsx'
            mask2.style.display='none'

        }
        //编辑保存按钮
        self.bjpushId=function(){
            var bjidmap= [];
            var selectArr = []
            for(var i=0;i<$('.bjselect input').length;i++){
                if($('.bjselect input').length!==$('.bjselect input:checked').length){
                    cube.indicate("右侧请全部勾选");
                    return
                }
                // console.log($('.bjselect input').eq(i).attr('instid'))
                bjidmap.push({"instanceId":$('.bjselect input').eq(i).attr('instid'),
                    "relationId":$('.bjselect input').eq(i).attr('relaid')})

                selectArr.push({glsxName:$('.bjselect input').eq(i).attr('glsxName'),selfname:$('.bjselect input').eq(i).attr('selfname'),"instanceId":$('.bjselect input').eq(i).attr('instid'),
                    "relationId":$('.bjselect input').eq(i).attr('relaid')})
                // var objt = {};
                // var peont = bjirizhimap.reduce(function(cur,next)  {
                //     objt[next.instanceId] ? "" : objt[next.instanceId] = true && cur.push(next);
                //     return cur;
                // },[])
            }
            // console.log("=====================================")
            // console.log(bjidmap)
            // console.log("=======================================")
            // self.bbb(peont)
            $('.messagest i[datathis="this"]').attr('datanewselected',JSON.stringify(selectArr))

            var unselectArr = []
            for(var i=0;i<$('.bjunselect input').length;i++){
                unselectArr.push({glsxName:$('.bjunselect input').eq(i).attr('glsxName'),selfname:$('.bjunselect input').eq(i).attr('selfname'),"instanceId":$('.bjunselect input').eq(i).attr('instid'),
                    "relationId":$('.bjunselect input').eq(i).attr('relaid')})
            }
            // 排序
            var newUnselectArr = unselectArr.sort(compare('instanceId'))
            $('.messagest i[datathis="this"]').attr('dataunselected',JSON.stringify(newUnselectArr))

            $('.messagest i[datathis="this"]').attr('datatime',"1")
            // var editoldList = []
            // if($('.messagest i[datathis="this"]').attr('dataeditoldselected')) {
            //     editoldList = JSON.parse($('.messagest i[datathis="this"]').attr('dataeditoldselected'))
            // }

            // var params ={
            //     "instanceId":cishuItem.INSTANCE_ID,
            //     "instanceName":self.bjname(),
            //     "idList": bjidmap,
            //     "oldIdList": editoldList,
            //     "modelingId":self.modelingId()
            // }
            // axios.post(cube.gatewayURL2 + "atlasInformation/InsertInstanceInfoInclude", params).then(function (res) {
            //     if (res.data.successful==true) {
            //         // console.log(res,1)
            //         cube.indicate("保存成功");
            //         $('.messagest i[datathis="this"]').attr('datatime',"1")
            //     } else {
            //         cube.indicate("保存失败");
            //     }
            //
            // })


            div4.className='bjglsx'
            mask3.style.display='none'
        }

        // arrz=[]
        // console.log(arrz)
        //新增图片点击选择
        self.wselect=function(){
            var arr=[]
            var sss= notselect.children
            for(var i=0;i<sss.length;i++){
                if(sss[i].firstChild.checked==true){
                    var node=sss[i].cloneNode(true);
                    select.append(node)
                    // select.appendChild(sss[sss[i].index].firstChild.parentNode)
                    arr.push(sss[i])

                }
            }
            console.log(select)

            for(var i=0;i<arr.length;i++){
                notselect.removeChild(arr[i])
            }

        }
        //新增图片点击取消
        self.qxelect=function(){
            // alert(1)
            var arr2=[]
            var sss2= select.children
            for(var i=0;i<sss2.length;i++){
                if(sss2[i].firstChild.checked==true){
                    var node=sss2[i].cloneNode(true);
                    notselect.append(node)

                    // select.appendChild(sss[sss[i].index].firstChild.parentNode)
                    arr2.push(sss2[i])
                }
            }
            for(var i=0;i<arr2.length;i++){
                select.removeChild(arr2[i])
            }
            var unselectArr = []
            for(var i=0;i<$('.unselect input').length;i++){
                unselectArr.push({glsxName:$('.unselect input').eq(i).attr('glsxName'),selfname:$('.unselect input').eq(i).attr('selfname'),"instanceId":$('.unselect input').eq(i).attr('instid'),
                    "relationId":$('.unselect input').eq(i).attr('relaid')})
            }
            // 排序
            var unarr = unselectArr.sort(compare('instanceId'))
            var str = '';
            for (var i = 0; i < unarr.length; i++) {
                str += `
                <label class='inputan' title="${unarr[i].selfname}"><input class='ipt' selfname=${unarr[i].selfname} instid=${unarr[i].instanceId} relaId=${unarr[i].relationId} glsxName=${unarr[i].glsxName}  type="checkbox" value="sleep"> ${unarr[i].selfname}</label>                  
                        `;
            }
            document.querySelector('.unselect').innerHTML = str;

        }
        //编辑图片点击选择
        self.bjwselect=function(){
            var arr3=[]
            var sss3= bjunselect.children
            for(var i=0;i<sss3.length;i++){
                if(sss3[i].firstChild.checked==true){
                    var node=sss3[i].cloneNode(true);
                    bjselect.append(node)
                    // select.appendChild(sss[sss[i].index].firstChild.parentNode)
                    arr3.push(sss3[i])
                }
            }
            for(var i=0;i<arr3.length;i++){
                bjunselect.removeChild(arr3[i])
            }

        }
        //编辑图片点击取消
        self.bjqxelect=function(){
            // alert(1)
            var arr4=[]
            var sss4= bjselect.children
            for(var i=0;i<sss4.length;i++){
                if(sss4[i].firstChild.checked==true){
                    var node=sss4[i].cloneNode(true);
                    bjunselect.append(node)
                    // select.appendChild(sss[sss[i].index].firstChild.parentNode)
                    arr4.push(sss4[i])
                }
            }
            for(var i=0;i<arr4.length;i++){
                bjselect.removeChild(arr4[i])
            }
            var unselectArr = []
            for(var i=0;i<$('.bjunselect input').length;i++){
                unselectArr.push({glsxName:$('.bjunselect input').eq(i).attr('glsxName'),selfname:$('.bjunselect input').eq(i).attr('selfname'),"instanceId":$('.bjunselect input').eq(i).attr('instid'),
                    "relationId":$('.bjunselect input').eq(i).attr('relaid')})
            }
            // 排序
            var unarr = unselectArr.sort(compare('instanceId'))
            var str = '';
            for (var i = 0; i < unarr.length; i++) {
                str += `
                <label class='inputan' title="${unarr[i].selfname}"><input class='ipt' selfname=${unarr[i].selfname} instid=${unarr[i].instanceId} relaId=${unarr[i].relationId} glsxName=${unarr[i].glsxName}  type="checkbox" value="sleep"> ${unarr[i].selfname}</label>
                        `;
            }
            document.querySelector('.bjunselect').innerHTML = str;

        }

        //关联属性图标按钮
        self.glsxwxz=''
        felmt.onclick=function(e){

            // console.log(e.target.getAttribute('dataid'))
            if(e.target.getAttribute('dataid')&&e.target,e.target.getAttribute('datatype')&&e.target,e.target.getAttribute('dataname')) {
                if (e.target.nodeName == 'I') {
                    div3.className = 'glsxh'
                    mask2.style.display = 'block'
                    $(this).find('i').attr('datathis', '')
                    e.target.setAttribute('datathis', 'this')
                }
                console.log(e.target.getAttribute('dataid'), e.target, e.target.getAttribute('datatype'))
                if (e.target.getAttribute('datatype') === '作业计划') {
                    self.glsxwxz('1')
                } else if (e.target.getAttribute('datatype') === '外包单位') {
                    self.glsxwxz('2')
                } else if (e.target.getAttribute('datatype') === '人员') {
                    self.glsxwxz('3')
                } else if (e.target.getAttribute('datatype') === '风险') {
                    self.glsxwxz('4')
                } else if (e.target.getAttribute('datatype') === '隐患') {
                    self.glsxwxz('5')
                } else if (e.target.getAttribute('datatype') === '事件') {
                    self.glsxwxz('6')
                } else if (e.target.getAttribute('datatype') === '违章') {
                    self.glsxwxz('7')
                }
                //根据类别查询实例
                // 已选择的有值走缓存 没值发请求
                document.querySelector('.unselect').innerHTML = '';
                document.querySelector('.select').innerHTML = '';
                if (e.target.getAttribute('dataselected')) {
                    var unarr = JSON.parse(e.target.getAttribute('dataunselected'))
                    var str = '';
                    for (var i = 0; i < unarr.length; i++) {
                        str += `
                <label class='inputan' title="${unarr[i].selfname}"><input class='ipt' selfname=${unarr[i].selfname} instid=${unarr[i].instanceId} relaId=${unarr[i].relationId} glsxName=${e.target.getAttribute('dataname')}  type="checkbox" value="sleep"> ${unarr[i].selfname}</label>
                    
                        `;

                    }
                    document.querySelector('.unselect').innerHTML = str;
                    var searr = JSON.parse(e.target.getAttribute('dataselected'))
                    var str2 = '';
                    for (var i = 0; i < searr.length; i++) {
                        str2 += `
                <label class='inputan' title="${searr[i].selfname}"><input class='ipt' selfname=${searr[i].selfname} instid=${searr[i].instanceId} relaId=${searr[i].relationId} glsxName=${e.target.getAttribute('dataname')}  type="checkbox" checked value="sleep"> ${searr[i].selfname}</label>
                    
                        `;

                    }
                    document.querySelector('.select').innerHTML = str2;
                } else {
                    var params = {"targetType": self.glsxwxz(), "relationId": e.target.getAttribute('dataid')}
                    self.isShow(true)
                    axios.post(cube.gatewayURL2 + "atlasInformation/getInstanceInfoByType", params).then(function (res) {
                        if (res.data.successful == true) {
                            // console.log(res,1)
                            var str = '';
                            for (var i = 0; i < res.data.resultValue.length; i++) {
                                str += `
                        <label class='inputan' title="${res.data.resultValue[i].INSTANCE_NAME}"><input class='ipt' selfname=${res.data.resultValue[i].INSTANCE_NAME} instid=${res.data.resultValue[i].INSTANCE_ID} relaId=${res.data.resultValue[i].RELATION_ID} glsxName=${e.target.getAttribute('dataname')}  type="checkbox"  value="sleep"> ${res.data.resultValue[i].INSTANCE_NAME}</label>
                           
                             `;

                            }
                            document.querySelector('.unselect').innerHTML = str;
                            document.querySelector('.select').innerHTML = '';
                        } else {
                            cube.indicate("暂无数据");
                        }
                        self.isShow(false)
                    })
                }
            }
        }
        //关联编辑图标按钮
        felmtt.onclick=function(e){
            // console.log(e.target.getAttribute('dataid'))
            if(e.target.getAttribute('dataid')&&e.target,e.target.getAttribute('datatype')&&e.target,e.target.getAttribute('dataname')){
                if (e.target.nodeName == 'I') {
                    $(this).find('i').attr('datathis','')
                    e.target.setAttribute('datathis','this')
                    div4.className='glsxh'
                    mask3.style.display='block'
                }
                // console.log(e.target.getAttribute('dataid'),e.target,e.target.getAttribute('datatype'))
                if(e.target.getAttribute('datatype')==='作业计划'){
                    self.glsxwxz('1')
                }else if(e.target.getAttribute('datatype')==='外包单位'){
                    self.glsxwxz('2')
                }else if(e.target.getAttribute('datatype')==='人员'){
                    self.glsxwxz('3')
                }else if(e.target.getAttribute('datatype')==='风险'){
                    self.glsxwxz('4')
                }else if(e.target.getAttribute('datatype')==='隐患'){
                    self.glsxwxz('5')
                }else if(e.target.getAttribute('datatype')==='事件'){
                    self.glsxwxz('6')
                }else if(e.target.getAttribute('datatype')==='违章'){
                    self.glsxwxz('7')
                }
                //根据ID类别查询实例
                // 第一次点击发请求，以后走缓存
                document.querySelector('.bjunselect').innerHTML = '';
                document.querySelector('.bjselect').innerHTML = '';
                if(e.target.getAttribute('datatime') != '0') {
                    var unarr = JSON.parse(e.target.getAttribute('dataunselected'))
                    var str = '';
                    for (var i = 0; i < unarr.length; i++) {
                        str += `
                <label class='inputan' title="${unarr[i].selfname}"><input class='ipt' selfname=${unarr[i].selfname} instid=${unarr[i].instanceId} relaId=${unarr[i].relationId} glsxName=${e.target.getAttribute('dataname')}  type="checkbox" value="sleep"> ${unarr[i].selfname}</label>
                    
                        `;

                    }
                    document.querySelector('.bjunselect').innerHTML = str;
                    var searr = JSON.parse(e.target.getAttribute('datanewselected'))
                    var str2 = '';
                    for (var i = 0; i < searr.length; i++) {
                        str2 += `
                <label class='inputan' title="${searr[i].selfname}"><input class='ipt' selfname=${searr[i].selfname} instid=${searr[i].instanceId} relaId=${searr[i].relationId} glsxName=${e.target.getAttribute('dataname')}  type="checkbox" checked value="sleep"> ${searr[i].selfname}</label>
                    
                        `;

                    }
                    document.querySelector('.bjselect').innerHTML = str2;
                }else {
                    // console.log(cishu)
                    var params ={"instanceId":cishuItem.INSTANCE_ID,"targetType":self.glsxwxz(),"relationId":e.target.getAttribute('dataid'),glsxName:e.target.getAttribute('dataname')}
                    self.isShow(true)
                    axios.post(cube.gatewayURL2 + "atlasInformation/getInstancerelationinfoById", params).then(function (res) {
                        if (res.data.successful==true) {
                            console.log(res.data.resultValue,2)
                            var stt = '';
                            var selectArr = []
                            for (var i = 0; i < res.data.resultValue.selected.length; i++) {

                                stt += `
                            <label class='inputan' title="${res.data.resultValue.selected[i].INSTANCE_NAME}"><input selfname=${res.data.resultValue.selected[i].INSTANCE_NAME} instid=${res.data.resultValue.selected[i].INSTANCE_ID}
                         relaId=${res.data.resultValue.selected[i].RELATION_ID} glsxName=${e.target.getAttribute('dataname')} type="checkbox" checked value="sleep">
                          ${res.data.resultValue.selected[i].INSTANCE_NAME}</label>
                           
                             `;
                                selectArr.push({glsxName:e.target.getAttribute('dataname'),selfname:res.data.resultValue.selected[i].INSTANCE_NAME,"instanceId":res.data.resultValue.selected[i].INSTANCE_ID,
                                    "relationId":res.data.resultValue.selected[i].RELATION_ID})
                            }
                            document.querySelector('.bjselect').innerHTML = stt;
                            $('.messagest i[datathis="this"]').attr('dataoldselected',JSON.stringify(selectArr))

                            var stk = '';
                            for (var i = 0; i < res.data.resultValue.noSelected.length; i++) {

                                stk += `
                        <label class='inputan' title="${res.data.resultValue.noSelected[i].INSTANCE_NAME}"><input selfname=${res.data.resultValue.noSelected[i].INSTANCE_NAME} instid=${res.data.resultValue.noSelected[i].INSTANCE_ID}
                        relaId=${res.data.resultValue.noSelected[i].RELATION_ID} glsxName=${e.target.getAttribute('dataname')} type="checkbox" value="sleep"> 
                        ${res.data.resultValue.noSelected[i].INSTANCE_NAME}</label>
                           
                             `;

                            }
                            document.querySelector('.bjunselect').innerHTML = stk;

                        } else {
                            cube.indicate(res.data.resultHint);
                        }
                        self.isShow(false)
                    })
                }

            }

        }



        //总页数
        self.pageTotalCount=ko.observable('1'),
            //总记录数
            self.count =ko.observable('1'),
            //当前页数
            self.pageIndex=ko.observable('1'),
            //可见页数
            self.pageVisibleCount=ko.observable('1'),
            self.onPageIndexChanged = function (p_pageIndex) {
                self.pageIndex(p_pageIndex);
                // console.log(self.pageIndex())

                RiskList(self.pageIndex())
            }
        //  self.cancelSelectedItem=function(){
        //      console.log( cube.getPageViewModelByNode($("#instanceTable")).cancelSelectedItem())
        //     cube.getPageViewModelByNode($("#instanceTable")).cancelSelectedItem()

        //  }
        //实例列表
        self.titleName = ko.observable("")
        function RiskList(searchParams) {
            // console.log(self.pageIndex())
            var params = {
                "page":searchParams,
                "size": 10,
                "params":
                    { "modelingId":self.modelingId(), "instanceName": '' }
            }
            self.isShow(true)
            axios.post(cube.gatewayURL2 + "atlasInformation/getInstanceInfo", params).then(function (res) {
                if (res.data.successful==true) {
                    // self.cityContent([]);
                    // console.log(res,1)
                    self.count(res.data.resultValue.totalSize)
                    self.pageIndex(res.data.resultValue.page)
                    self.pageTotalCount(res.data.resultValue.totalPage)
                    // self.examId(res.data.resultValue.content.INSTANCE_ID)
                    var risklist = res.data.resultValue.content
                    self.cityContent(risklist);
                    console.log(self.cityContent())
                    // var ceshiArr = document.getElementsByName('rowCheckbox')
                    // ceshiArr.forEach(ele=> {
                    //     ele.checked = false
                    // })
                    // self.cancelSelectedItem()

                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            }).catch(err=>{
                self.isShow(false)
                cube.indicate("请求超时，稍后重试！");
            })
        }
        TypeList()
        function TypeList() {
            // console.log(self.pageIndex())
            var params = {
                "page": 1,
                "size": 10,
                "typeName": ""
            }
            axios.post(cube.gatewayURL2 + "atlasInformation/getModeling", params).then(function (res) {
                if (res.data.successful==true) {
                    if(res.data.resultValue.content.length > 0) {
                        self.modelingId(res.data.resultValue.content[0].TYPE_NAME)
                        if(res.data.resultValue.content[0].TYPE_NAME == "1") {
                            dd1.className="dianjih"
                        }else if(res.data.resultValue.content[0].TYPE_NAME == "2") {
                            dd2.className="dianjih"
                        }else if(res.data.resultValue.content[0].TYPE_NAME == "3") {
                            dd3.className="dianjih"
                        }else if(res.data.resultValue.content[0].TYPE_NAME == "4") {
                            dd4.className="dianjih"
                        }else if(res.data.resultValue.content[0].TYPE_NAME == "5") {
                            dd5.className="dianjih"
                        }else if(res.data.resultValue.content[0].TYPE_NAME == "6") {
                            dd6.className="dianjih"
                        }else if(res.data.resultValue.content[0].TYPE_NAME == "7") {
                            dd7.className="dianjih"
                        }
                        res.data.resultValue.content.forEach(ele=> {
                            if(ele.TYPE_NAME == "1") {
                                dd1.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd1sun = document.querySelector('.zyjh')
                                    dd1sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }else if(ele.TYPE_NAME == "2") {
                                dd2.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd2sun = document.querySelector('.wbdan')
                                    dd2sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }else if(ele.TYPE_NAME == "3") {
                                dd3.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd3sun = document.querySelector('.renyuan')
                                    dd3sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }else if(ele.TYPE_NAME == "4") {
                                dd4.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd4sun = document.querySelector('.fengxian')
                                    dd4sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }else if(ele.TYPE_NAME == "5") {
                                dd5.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd5sun = document.querySelector('.yinhuan')
                                    dd5sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }else if(ele.TYPE_NAME == "6") {
                                dd6.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd6sun = document.querySelector('.shijian')
                                    dd6sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }else if(ele.TYPE_NAME == "7") {
                                dd7.style.display = 'block'
                                if(ele.TYPE_ICON) {
                                    var dd7sun = document.querySelector('.wzxin')
                                    dd7sun.src = "pages/hapMap/icon/" + ele.TYPE_ICON + ".png"
                                }
                            }
                        })
                        RiskList(self.pageIndex())
                    }
                } else {
                    cube.indicate("数据加载失败");
                }

            }).catch(err=>{
                self.isShow(false)
                cube.indicate("请求超时，稍后重试！");
            })
        }

        self.id = ko.observable("")
        self.examId = ''
        self.primaryKey = ''
        self.size = 'middle';

        self.msgType = 'confirm';
        self.isShowDialog = false;
        self.title = '温馨提示';
        self.content = '删除后将不能恢复，是否确定删除?';
        self.confirmDelete = function (e) {
            // self.id=item
            // console.log(self.id)
            // var params = {
            //     "params": self.cishu(),
            //     "modelingId":self.modelingId()
            // }
            //
            // axios.post(cube.gatewayURL2 + "atlasInformation/deleteInstance", params).then(function (res) {
            //     if (res.data.successful==true) {
            //         RiskList(self.pageIndex())
            //         self.cishu([])
            //         self.isShowDialog = false;
            //         cube.indicate("删除成功");
            //
            //     } else {
            //         // cube.indicate(res.data.resultHint);
            //         cube.indicate("删除失败，请稍后重试！");
            //     }
            //
            // })
        };


        //实例模板下载
        mbxz=function mbxz(){
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var timestamp = year + month + strDate;
            var mbName = '实例模板' + timestamp +'.xls';
            $('#mbxz').attr('href','pages/hapMap/category/实例模板.xls');
            $('#mbxz').attr('download',mbName);
            var params = {
                "params":
                    { "mbName":mbName}
            }
            axios.post(cube.gatewayURL2 + "fileLead/exampletemplateDownload", params).then(function (res) {

                if (res.data.successful==true) {

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }


        //导入
        importExcel = function (obj) {
            var formData = new FormData();
            var name = $("#articleImageFile").val();
            var importFile = $("#articleImageFile")[0].files[0];
            var sizefile = $("#articleImageFile")[0].files[0].size;
            var fileName=$("#articleImageFile")[0].files[0].name;
            //文件类型校验
            if(!name.match(/.\bxls\b$/i)){
                alert("只能上传xls格式文件");
                return;
            }
            //文件空值校验
            if (importFile == undefined || importFile == null || importFile == "") {
                alert("上传文件不可为空");
                return;
            }
            //文件大小校验
            if (sizefile/1024 > 1024) {
                alert('上传文件1M ,请重新选择！');
                $('#testFile').val('');
                return;
            }
            //文件名校验
            var reg = new RegExp('[\\\\/:*?\"<>|]');
            if (reg.test(fileName)) {
                //"上传的文件名不能包含【\\\\/:*?\"<>|】这些非法字符,请修改后重新上传!";
                return false;
            }

            var a=name.lastIndexOf('.')
            var b=name.slice(a)
            if(b!=='.xls'){
                cube.indicate("只能上传xls格式文件");
                return;
            }
            formData.append("file", $("#articleImageFile")[0].files[0]);
            formData.append("name", name);//可以使用formData.append()传递多个参数
            // formData.append("",);
            // $.ajax({
            //     url: cube.gatewayURL2 + "fileLead/fileExample",
            //     type: 'POST',
            //     async: false,
            //     data: formData,
            //     // 告诉jQuery不要去处理发送的数据
            //     processData: false,
            //     // 告诉jQuery不要去设置Content-Type请求头
            //     contentType: false,
            //     beforeSend: function () {
            //         console.log("正在进行，请稍候");
            //     },
            // });
            var params = formData
            axios.post(cube.gatewayURL2 + "fileLead/fileExample",params).then(function(res) {
                if (res.data.successful) {
                    cube.indicate("导入成功");
                    RiskList(self.pageIndex())
                } else {
                    cube.indicate(res.data.resultHint);
                }
                // 上传文件框值置空 可以上传相同文件
                $("#articleImageFile").val("")
            })
        }

        self.theImport = function () {
            axios.post(cube.gatewayURL2 + "fileLead/fileExample").then(function (res) {

            });
        }
        //刷新
        self.refresh = function () {
            self.titleName('')
            RiskList(self.pageIndex())
        }


        self.style = "background:#cccccc;";
        self.columns = [
            { name: "INSTANCE_ID", width: "10%", readOnly: true, caption: "序号",
             editorType: "TextEditor", align: "center" },
            {
                name: "INSTANCE_NAME", width: "15%", caption: "名称",
                editorType: "TextEditor", align: "center",
                renderCell: function (cellValue, element, dictValue) {
                    element.style.color = "#0ec4da";
                    element.style.cursor = "pointer";
                },
                onClick:function(pkValue){
                    window.localStorage.setItem('instanceId', JSON.stringify(pkValue))
                    // console.log(pkValue)
                    commonPageBridge({
                        name: 'hapMap.knowledge.main',
                    });
                }

            },
            { name: "INSTANCE_CODE", width: "10%", caption: "编号", editorType: "TextEditor", align: "center" },
            { name: "INSTANCE_DESCRIBE", width: "10%", caption: "描述", editorType: "TextEditor", align: "center" },
            { name: "UPDATE_TIME", width: "10%", caption: "更新时间", editorType: "TextEditor", align: "center" }

        ];
        var dd1 = document.querySelector('.xzzt')
        var dd2 = document.querySelector('.wbdw')
        var dd3 = document.querySelector('.reny')
        var dd4 = document.querySelector('.fengx')
        var dd5 = document.querySelector('.yingh')
        var dd6 = document.querySelector('.shij')
        var dd7 = document.querySelector('.wzxx')
        var dd8 = document.querySelectorAll('dd')
        var mask = document.querySelector('.mask')
        var mask2 = document.querySelector('.mask2')
        var mask3 = document.querySelector('.mask3')
        // dd1.className='dianjih'

        //新增按钮功能
        self.addin = function () {
            div3.className ='glsx'
            div1.className = 'xingzengh'
            mask.style.display = 'block';
            self.name('')
            self.nodeName('')
            self.attribute('')
            // self.describe('')
            params={
                "modelingId":self.modelingId()
            }
            self.isShow(true)
            axios.post(cube.gatewayURL2 + "atlasInformation/getRelationInfoById", params).then(function (res) {
                if (res.data.successful==true) {
                    var str = '';
                    for (var i = 0; i < res.data.resultValue.length; i++) {
                        str += `
                        <div index=${i} length=${res.data.resultValue.length} style="margin-bottom: 20px; margin-left: 50px;" class='glwbdw'>
                            <label class="glsxLabel" style="float: left;">${res.data.resultValue[i].SOURCE_TYPE}${res.data.resultValue[i].RELATION_NAME}${res.data.resultValue[i].TARGET_TYPE}</label>
                            <div style="line-height: 32px;"><i class="icon-edit" datathis="" dataselected="" dataunselected="" datatype=${res.data.resultValue[i].TARGET_TYPE} dataid=${res.data.resultValue[i].RELATION_ID} dataname=${res.data.resultValue[i].SOURCE_TYPE}${res.data.resultValue[i].RELATION_NAME}${res.data.resultValue[i].TARGET_TYPE}></i></div>
                        </div>
                             `;

                    }


                    document.querySelector('.messages').innerHTML = str;
                    // for(var p=0;p<$('.messages .glwbdw').length;p++){
                    //     console.log($('.messages .glwbdw').eq(p).attr('index'))
                    //     arrz.push([])
                    // }

                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })


        }
        if(self.modelingId= ko.observable("1")){
            self.bjdescribe='作业计划'
            self.describe='作业计划'
        }
        //点击类别切换建模ID和列表
        self.jobplan=function(){

            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }
            self.modelingId('1')
            self.pageIndex('1')
            self.describe('作业计划')
            self.bjdescribe('作业计划')
            RiskList(self.pageIndex())
            dd1.className="dianjih"

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }
        }
        self.outperson=function(){
            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }
            self.modelingId('2')
            self.pageIndex('1')
            self.describe('外包单位')
            self.bjdescribe('外包单位')
            RiskList(self.pageIndex())
            dd2.className="dianjih"

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }

        }
        self.person=function(){
            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }
            self.modelingId('3')
            self.pageIndex('1')
            self.describe('人员')
            self.bjdescribe('人员')
            RiskList(self.pageIndex())
            dd3.className="dianjih"

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }

        }
        self.hazard=function(){
            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }
            self.modelingId('4')
            self.pageIndex('1')
            self.describe('风险')
            self.bjdescribe('风险')
            RiskList(self.pageIndex())
            dd4.className="dianjih"

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }

        }
        self.trouble=function(){
            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }

            self.modelingId('5')
            self.pageIndex('1')
            self.describe('隐患')
            self.bjdescribe('隐患')

            dd5.className="dianjih"

            RiskList(self.pageIndex())

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }
        }
        self.event=function(){
            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }
            self.pageIndex('1')

            self.modelingId('6')
            dd6.className="dianjih"
            self.describe('事件')
            self.bjdescribe('事件')

            RiskList(self.pageIndex())

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }
        }
        self.violate=function(){

            for(var i=0;i<dd8.length;i++){
                dd8[i].className="dianjiq"
            }
            self.modelingId('7')
            self.pageIndex('1')

            dd7.className="dianjih"
            self.describe('违章信息')
            self.describe('违章信息')

            RiskList(self.pageIndex())

            if(self.modelingId() !== modelingIdClone) {
                cishu = []
                cishuItem = {}
            }else {
                cishu = cishuClone
                cishuItem = cishuItemClone
            }
        }
        //编辑按钮
        self.edit = function () {
            // console.log(cishu().length,self.cishu())
            if(cishu.length==0){
                cube.indicate("请选择需要编辑的选项");
                return;
            }
            // else if(self.cishu().length>1){
            //     cube.indicate("请选择一项编辑项");
            //     return;
            // }
            // details(self.cishu())
            div2.className='bianjih'
            div4.className ='bjglsx'
            mask.style.display='block'
            // console.log(cishuItem)
            // self.bjname(cishuItem.INSTANCE_NAME)
            // self.bjnodeName(cishuItem.INSTANCE_CODE)
            // self.bjattribute(cishuItem.INSTANCE_DESCRIBE)
            // 编辑回显
            hxparams={
                "instanceId":cishuItem.INSTANCE_ID
            }
            self.isShow(true)
            axios.post(cube.gatewayURL2 + "atlasInformation/findinstancexq", hxparams).then(function (res) {
                if (res.data.successful==true) {
                    var result = res.data.resultValue
                    self.bjname(result.instanceName)
                    self.bjnodeName(result.instanceCode)
                    self.bjattribute(result.instanceDescribe)
                } else {
                    cube.indicate(res.data.resultHint);
                }
                self.isShow(false)
            })

            div1.className = 'xingzeng'
            params={
                "modelingId":self.modelingId()
            }
            axios.post(cube.gatewayURL2 + "atlasInformation/getRelationInfoById", params).then(function (res) {
                if (res.data.successful==true) {
                    var str = '';
                    for (var i = 0; i < res.data.resultValue.length; i++) {
                        str += `
                            <div style=" margin-bottom: 20px; margin-left: 50px;" class='glwbdw'>
                                <label class="glsxLabel" style="float: left;">${res.data.resultValue[i].SOURCE_TYPE}${res.data.resultValue[i].RELATION_NAME}${res.data.resultValue[i].TARGET_TYPE}</label>
                                <div style="line-height: 32px;"><i class="icon-edit" datatime="0" datathis="" dataunselected="" dataoldselected="" datanewselected="" datatype=${res.data.resultValue[i].TARGET_TYPE} dataid=${res.data.resultValue[i].RELATION_ID} dataname=${res.data.resultValue[i].SOURCE_TYPE}${res.data.resultValue[i].RELATION_NAME}${res.data.resultValue[i].TARGET_TYPE}></i></div>
                            </div>
                                 `;

                    }


                    document.querySelector('.messagest').innerHTML = str;


                } else {
                    cube.indicate(res.data.resultHint);
                }

            })



        }
        //编辑保存
        self.editinstance=function(){
            // 必填项校验
            if(!self.bjname().trim()){
                cube.indicate("请输入名称");
                return
            }
            if(!self.bjattribute().trim()){
                cube.indicate("请输入描述");
                return
            }
            // if(!self.bbb().length){
            //     cube.indicate("请选择关联关系");
            //     return
            // }
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            var iptRep2 = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》:;：；/￥——{}\"‘”“'_()?？·（）【】’#]")
            if(iptRep.test(self.bjname().trim()) || iptRep2.test(self.bjattribute().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.bjname().trim().length !== self.bjname().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.bjattribute().trim().length !== self.bjattribute().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.bjname().trim().length > 10) {
                cube.indicate("名称长度不能超过10！");
                return;
            }
            if(self.bjattribute().trim().length > 200) {
                cube.indicate("描述长度不能超过200！");
                return;
            }
            window.localStorage.setItem('bjinstanceId', JSON.stringify(cishuItem.INSTANCE_ID))
            var oldIdList = []
            var newIdList = []
            $('.messagest i').each((i,ele)=>{
                if(ele.getAttribute('datatime') == '1') {
                    if(ele.getAttribute('dataoldselected')) {
                        oldIdList.push(...JSON.parse(ele.getAttribute('dataoldselected')))
                    }
                }
                if(ele.getAttribute('datanewselected')) {
                    newIdList.push(...JSON.parse(ele.getAttribute('datanewselected')))
                }
            })
            var params={"params":{
                    "instanceId":cishuItem.INSTANCE_ID,
                    "instanceName":self.bjname(),
                    "instanceDescribe":self.bjattribute()},
                "oldIdList":oldIdList,
                "newIdList":newIdList,
                "modelingId":self.modelingId()
            }
            if($("#editBtn").attr('dataClick') == 'true') {
                $("#editBtn").attr('dataClick', 'false')
                $("#editBtn").removeClass('know-btn').addClass('know-btn-disabled')
                axios.post(cube.gatewayURL2 + "atlasInformation/updateInstanceInfo", params).then(function (res) {
                    if (res.data.successful == true) {
                        genrela()
                        RiskList(self.pageIndex())
                        cube.indicate("编辑成功");
                        div2.className = 'bianji'
                        mask.style.display = 'none'
                        // commonPageBridge({
                        //     name: 'hapMap.knowledge.main',
                        // });
                    } else {
                        cube.indicate(res.data.resultHint);
                    }
                    $("#editBtn").attr('dataClick','true')
                    $("#editBtn").removeClass('know-btn-disabled').addClass('know-btn')
                })
            }

        }

        self.cityContent = []
        //查询
        self.search = function () {
            function cxRiskList(searchParams) {
                // 输入框内容校验
                var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
                if(iptRep.test(searchParams.trim())) {
                    cube.indicate("输入框含违法字符，请重新输入！");
                    return;
                }
                // 输入框空格校验
                if(searchParams.trim().length !== searchParams.length) {
                    cube.indicate("输入框内容不能全空格或首尾有空格！");
                    return;
                }
                // 输入框长度校验
                if(searchParams.trim().length > 100) {
                    cube.indicate("输入框内容长度不能超过100！");
                    return;
                }
                // console.log(self.pageIndex())
                var params = {
                    "page":self.pageIndex(),
                    "size": 10,
                    "params":
                        { "modelingId":self.modelingId(), "instanceName": searchParams }
                }
                axios.post(cube.gatewayURL2 + "atlasInformation/getInstanceInfo", params).then(function (res) {
                    if (res.data.successful==true) {
                        console.log(res,1)
                        self.count(res.data.resultValue.totalSize)
                        self.pageIndex(res.data.resultValue.page)
                        self.pageTotalCount(res.data.resultValue.totalPage)
                        // self.examId(res.data.resultValue.content.INSTANCE_ID)
                        var risklist = res.data.resultValue.content
                        self.cityContent(risklist);
                        console.log(self.cityContent())

                    } else {
                        cube.indicate("数据加载失败");
                    }

                }).catch(err=>{
                    self.isShow(false)
                    cube.indicate("请求超时，稍后重试！");
                })
            }
            cxRiskList(self.titleName())
            // self.titleName('')

        }
        // self.item = ''

        self.onSelectedItems = function (item) {
            console.log(item)
            // console.log(item.length)
            cishu = []
            // console.log(item)
            if(item.length==0){
                cishu = []
            } else {
                cishu.push({ "instanceId": item[item.length-1].INSTANCE_ID })
                cishuItem = item[item.length-1]
            }
            // console.log(self.cishu())
            // self.item=item
            // console.log(self.item)
            // for (var i = 0; i < self.item.length; i++) {
            //     // console.log(self.cishu()[i])
            //     self.cishu.push({ "instanceId": self.item[i].INSTANCE_ID })
            // }
            // console.log("===>"+self.cishu())
            cishuClone = cishu
            cishuItemClone = cishuItem
            modelingIdClone = self.modelingId()

        }

        //删除
        self.dele = function () {
            // console.log(self.cishu())
            if(cishu.length==0){
                cube.indicate("请选择需要删除的选项");
                return;
            }
            // else if(self.cishu().length>1){
            //     cube.indicate("请选择一项删除项");
            //     return;
            // }
            // self.isShowDialog(true)
            cube.confirm('温馨提示','删除后将不能恢复，是否确定删除?',function(){
                var params = {
                    "params": cishu,
                    "modelingId":self.modelingId()
                }

                axios.post(cube.gatewayURL2 + "atlasInformation/deleteInstance", params).then(function (res) {
                    if (res.data.successful==true) {
                        RiskList(1)
                        // self.cishu([])
                        cishu = []

                        cube.indicate("删除成功");

                    } else {
                        cube.indicate(res.data.resultHint);
                        // cube.indicate("删除失败，请稍后重试！");
                    }

                })
            })
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
});