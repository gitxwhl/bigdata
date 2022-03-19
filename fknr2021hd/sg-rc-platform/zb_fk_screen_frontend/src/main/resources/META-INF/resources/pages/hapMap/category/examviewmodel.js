define(["RESTClient", "echarts", "axios"], function (RestClient, echarts, axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.modelingId= ko.observable("1")

        //实例关系编号
        self.genrela = ko.observable("")
        function genrela() {
            axios.post(cube.gatewayURL2 + "atlasInformation/instanceNumber").then(function (res) {
                self.genrela(res.data.resultValue)
            })
        }

        genrela()

        //实例新增
        function examadd(searchParams) {
            if(!self.aaa().length){
                cube.indicate("请选择关联关系");
                return
            }
            var params = {
                "params": {
                    "modelingId":self.modelingId(),
                    "instanceName": self.name(),
                    "instanceCode": self.genrela(),
                    "instanceDescribe": self.describe()
                },
                
                "idList":self.aaa()
            }
            axios.post(cube.gatewayURL2 + "atlasInformation/insertInstanceInfo", params).then(function (res) {
                if (res.data.successful==true) {
            window.localStorage.setItem('xzinstanceId', JSON.stringify(res.data.resultValue.id))
                    console.log(res.data.resultValue.id)
                    commonPageBridge({
                        name: 'hapMap.knowledge.main',
                    });
                    // console.log(res)
                    genrela()
                    RiskList(self.pageIndex())

                } else {
                    cube.indicate("数据加载失败");
                }

            })
            div1.className = 'xingzeng'
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
        }
        self.gbglsx=function(){
            div3.className='glsx'
            div4.className='bjglsx'
        }
        self.name = '';
        self.nodeName = '';
        self.attribute = '';
        self.describe = '';
        self.bjname = '';
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
        
        var idmap=[]
        var bjidmap=[]
        self.aaa=[]
        self.bbb=[]
        //新增保存按钮
        self.pushId=function(){
            for(var i=0;i<$('.select input').length;i++){
                if($('.select input').length!==$('.select input:checked').length){
                    cube.indicate("请全部勾选");

                    return
                }
                    idmap.push({"instanceId":$('.select input').eq(i).attr('instid'),
                    "relationId":$('.select input').eq(i).attr('relaid')})
                var obj = {};
                var peon = idmap.reduce(function(cur,next) {
                    obj[next.instanceId] ? "" : obj[next.instanceId] = true && cur.push(next);
                    return cur;
                },[])
                self.aaa(peon)
            div3.className='bjglsx'

            }

        }
        //编辑保存按钮
        self.bjpushId=function(){
            for(var i=0;i<$('.bjselect input').length;i++){
                    if($('.bjselect input').length!==$('.bjselect input:checked').length){
                        cube.indicate("请全部勾选");
                        return
                    }
                    console.log($('.bjselect input').eq(i).attr('instid'))
                    bjidmap.push({"instanceId":$('.bjselect input').eq(i).attr('instid'),
                    "relationId":$('.bjselect input').eq(i).attr('relaid')})
                var objt = {};
                var peont = bjidmap.reduce(function(cur,next)  {
                    objt[next.instanceId] ? "" : objt[next.instanceId] = true && cur.push(next);
                    return cur;
                },[])
                self.bbb(peont)
    
               
        }
        div4.className='bjglsx'
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
    
        }
       
        //关联属性图标按钮
        self.glsxwxz=''
        felmt.onclick=function(e){
           
            // console.log(e.target.getAttribute('dataid'))
            if (e.target.nodeName == 'I') {
                div3.className='glsxh'
			}
            console.log(e.target.getAttribute('dataid'),e.target,e.target.getAttribute('datatype'))
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
              //根据类别查询实例
            var params ={"targetType":self.glsxwxz(),"relationId":e.target.getAttribute('dataid')}
            axios.post(cube.gatewayURL2 + "atlasInformation/getInstanceInfoByType", params).then(function (res) {
                if (res.data.successful==true) {
                    // console.log(res,1)
                    var str = '';
                    for (var i = 0; i < res.data.resultValue.length; i++) {
                    str += `
                    <label class='inputan'><input class='ipt' instid=${res.data.resultValue[i].INSTANCE_ID} relaId=${res.data.resultValue[i].RELATION_ID}  type="checkbox" value="sleep"> ${res.data.resultValue[i].INSTANCE_NAME}</label>
                       
                         `;
                         
                    }
                    document.querySelector('.unselect').innerHTML = str;

                } else {
                    cube.indicate("无数据");
                }

            })
        }
        //关联编辑图标按钮
        felmtt.onclick=function(e){
            // console.log(e.target.getAttribute('dataid'))
            if(e.target.getAttribute('dataid')&&e.target,e.target.getAttribute('datatype')){
                if (e.target.nodeName == 'I') {
                    div4.className='glsxh'
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
                var params ={"instanceId":self.item[0].INSTANCE_ID,"targetType":self.glsxwxz(),"relationId":e.target.getAttribute('dataid')}
                axios.post(cube.gatewayURL2 + "atlasInformation/getInstancerelationinfoById", params).then(function (res) {
                    if (res.data.successful==true) {
                        console.log(res.data.resultValue,2)
                        var stt = '';
                        for (var i = 0; i < res.data.resultValue.selected.length; i++) {

                        stt += `
                        <label class='inputan'><input instid=${res.data.resultValue.selected[i].SOURCE_INSTANCE_ID}
                         relaId=${res.data.resultValue.selected[i].RELATION_ID} type="checkbox" value="sleep">
                          ${res.data.resultValue.selected[i].INSTANCE_NAME}</label>
                           
                             `;
                             
                        }
                        document.querySelector('.bjselect').innerHTML = stt;

                        var stk = '';
                        for (var i = 0; i < res.data.resultValue.noSelected.length; i++) {

                        stk += `
                        <label class='inputan'><input instid=${res.data.resultValue.noSelected[i].INSTANCE_ID}
                        relaId=${res.data.resultValue.noSelected[i].RELATION_ID} type="checkbox" value="sleep"> 
                        ${res.data.resultValue.noSelected[i].INSTANCE_NAME}</label>
                           
                             `;
                             
                        }
                        document.querySelector('.bjunselect').innerHTML = stk;
    
                    } else {
                        cube.indicate("无数据");
                    }
    
                })
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
            axios.post(cube.gatewayURL2 + "atlasInformation/getInstanceInfo", params).then(function (res) {
                if (res.data.successful==true) {
                    // console.log(res,1)
                    self.count(res.data.resultValue.totalSize)
                    self.pageIndex(res.data.resultValue.page)
                    self.pageTotalCount(res.data.resultValue.totalPage)
                    // self.examId(res.data.resultValue.content.INSTANCE_ID)
                    var risklist = res.data.resultValue.content
                    self.cityContent(risklist);
                    console.log(self.cityContent())
                    // self.cancelSelectedItem()

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
        RiskList(self.pageIndex())
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
            var params = { "typeId": self.id }
            axios.post(cube.gatewayURL2 + "atlasInformation/deleteModeing", params).then(function (res) {
                self.isShowDialog = true;
                RiskList(self.pageIndex())
            })
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
                alert("上传文件必须是excel文件");
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
                        cube.indicate("导入失败");
                    }
            
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

        dd1.className='dianjih'
       
        //新增按钮功能
        self.addin = function () {
                div3.className ='glsx'
                div1.className = 'xingzengh'
                self.name('')
                self.nodeName('')
                self.attribute('')
                // self.describe('')
                params={
                    "modelingId":self.modelingId()
                }
                axios.post(cube.gatewayURL2 + "atlasInformation/getRelationInfoById", params).then(function (res) {
                    if (res.data.successful==true) {
                        var str = '';
                        for (var i = 0; i < res.data.resultValue.length; i++) {
                        str += `
                        <div index=${i} length=${res.data.resultValue.length} style="margin-bottom: 20px; margin-left: 50px;" class='glwbdw'>
                            <label class="glsxLabel" style="float: left;">${res.data.resultValue[i].SOURCE_TYPE}${res.data.resultValue[i].RELATION_NAME}${res.data.resultValue[i].TARGET_TYPE}</label>
                            <div style="line-height: 32px;"><i class="icon-edit" datatype=${res.data.resultValue[i].TARGET_TYPE} dataid=${res.data.resultValue[i].RELATION_ID}></i></div>
                        </div>
                             `;
                             
                        }

                        
                        document.querySelector('.messages').innerHTML = str;
                        // for(var p=0;p<$('.messages .glwbdw').length;p++){
                        //     console.log($('.messages .glwbdw').eq(p).attr('index'))
                        //     arrz.push([])
                        // }
                        
                    } else {
                    }
    
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
        }
        //编辑按钮
        self.edit = function () {
            console.log(self.cishu().length,self.cishu())
            if(self.cishu().length==0){
                cube.indicate("请选择需要编辑的选项");
                return;
            }else if(self.cishu().length>1){
                cube.indicate("请选择一项编辑项");
                return;
            }
            // details(self.cishu())
            div2.className='bianjih'
            div4.className ='bjglsx'

            self.bjname(self.item[0].INSTANCE_NAME)
            self.bjnodeName(self.item[0].INSTANCE_CODE)
            self.bjattribute(self.item[0].INSTANCE_DESCRIBE)
           
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
                                <div style="line-height: 32px;"><i class="icon-edit" datatype=${res.data.resultValue[i].TARGET_TYPE} dataid=${res.data.resultValue[i].RELATION_ID}></i></div>
                            </div>
                                 `;
                                 
                            }
    
                            
                            document.querySelector('.messagest').innerHTML = str;
                            
                            
                        } else {
                        }
        
                    })
    

                
        }
        //编辑保存
        self.editinstance=function(){
            window.localStorage.setItem('bjinstanceId', JSON.stringify(self.item[0].INSTANCE_ID))
            var params={"params":{
                "instanceId":self.item[0].INSTANCE_ID,
                "instanceName":self.bjname(),
                "instanceDescribe":self.bjattribute()},
                "idList":self.bbb(),
                "modelingId":self.modelingId()
                }
            axios.post(cube.gatewayURL2 + "atlasInformation/updateInstanceInfo", params).then(function (res) {
                if (res.data.successful==true) {
                    RiskList(self.pageIndex())
                    cube.indicate("编辑成功");
                    commonPageBridge({
                        name: 'hapMap.knowledge.main',
                    });
                } else {
                    cube.indicate("编辑失败");
                }

            })
            div2.className='bianji'
            
        }

        self.cityContent = []
        //查询
        self.search = function () {
            function cxRiskList(searchParams) {
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
    
                })
            }
            cxRiskList(self.titleName())
            self.titleName('')

        }
        self.item = ''
        self.cishu = []
        self.onSelectedItems = function (item) {
            // console.log(item.length)
            self.cishu([])
            // console.log(item)
            if(item.length==0){
                self.cishu([])

            }
            // console.log(self.cishu())
            self.item=item
            // console.log(self.item)
            for (var i = 0; i < self.item.length; i++) {
                // console.log(self.cishu()[i])
                self.cishu.push({ "instanceId": self.item[i].INSTANCE_ID })
            }
        console.log(self.cishu())

        }

        //删除
        self.dele = function () {
            var params = {
                "params": self.cishu(),
                "modelingId":self.modelingId()
            }
            
            axios.post(cube.gatewayURL2 + "atlasInformation/deleteInstance", params).then(function (res) {
                if (res.data.successful==true) {
                    RiskList(self.pageIndex())
                    self.cishu([])

                    cube.indicate("删除成功");

                } else {
                    // cube.indicate(res.data.resultHint);
                    cube.indicate("请选择删除项");
                }

            })
        }
        cube.endViewModel(self, params);
    };
    return PageViewModel;
}); 