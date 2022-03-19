define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
        var self = this;
//关系列表
self.titleName=ko.observable("")
self.cityContent = []

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
function RiskList(searchParams) {
    var params = {
                "page":searchParams,
                "size": 10,
            }
    axios.post(cube.gatewayURL2 + "atlasInformation/getRelationInfo",params).then(function(res) {
        if (res.data.successful==true) {
            self.count(res.data.resultValue.totalSize)
            self.pageIndex(res.data.resultValue.page)
            self.pageTotalCount(res.data.resultValue.totalPage)
            var risklist=res.data.resultValue.content
            self.cityContent(risklist);
        } else {
            cube.indicate("数据加载失败");
        }

    })
}
RiskList(self.pageIndex())
//生产关系编号
 self.genrelas= ko.observable("")
function genrela(){
    axios.post(cube.gatewayURL2 + "atlasInformation/relationNumber").then(function(res){
        self.genrelas(res.data.resultValue)
        console.log(self.genrelas())
        
    })
}
genrela()

// 新增
function newadd(searchParams) {
    var params = {
        "params":{"relationName":self.unit(),
        "relationCode":self.genrelas(),
        "sourceType":self.runit(),
        "targetType":self.tunit(),
        "relationType":self.aunit(),
        "relationDescribe":self.nodeName()}}
    axios.post(cube.gatewayURL2 + "atlasInformation/insertRelationInfo",params).then(function(res) {
        if (res.data.successful) {
            cube.indicate("新增成功");

            RiskList(self.pageIndex())
            genrela()
        } else {
            cube.indicate("新增失败");
        }

    })
}


self.unit = ko.observable("1")
self.runit = ko.observable("1")
self.tunit = ko.observable("1")
self.aunit = ko.observable("1")
self.name=ko.observableArray([
    {id:'1',text:'下发'},
    {id:'2',text:'隐含'},
    {id:'3',text:'存在'},
    {id:'4',text:'导致'},
    {id:'5',text:'巡视'},
    {id:'6',text:'资质等级'},
    {id:'7',text:'引发'},
    {id:'8',text:'承担'},
    {id:'9',text:'操作不当'},
    {id:'10',text:'被管理'},
    {id:'11',text:'属于'},

])

self.releaseUnit =ko.observableArray([
    {id:'1',text:'作业计划'},
    {id:'2',text:'外包单位'},
    {id:'3',text:'人员'},
    {id:'4',text:'风险'},
    {id:'5',text:'隐患'},
    {id:'6',text:'事件'},
    {id:'7',text:'违章'},
]);
self.targetUnit=[
    // {id:'1',text:'作业计划'},
    // {id:'2',text:'外包单位'},
    // {id:'3',text:'人员'},
    // {id:'4',text:'风险'},
    // {id:'5',text:'隐患'},    
    // {id:'6',text:'事件'},
    // {id:'7',text:'违章'},
];
self.ylbchange=function(e){
    if(e.runit._latestValue=='1'){
        self.targetUnit([
                    {id:'2',text:'外包单位'},
                    {id:'3',text:'人员'},
                    {id:'4',text:'风险'},
                    {id:'5',text:'隐患'},
                    {id:'6',text:'事件'},
                    {id:'7',text:'违章'},
                ]);
    }else if(e.runit._latestValue=='2'){
        self.targetUnit([
            {id:'1',text:'作业计划'},
            {id:'3',text:'人员'},
            {id:'4',text:'风险'},
            {id:'5',text:'隐患'},
            {id:'6',text:'事件'},
            {id:'7',text:'违章'},
        ]);
    }else if(e.runit._latestValue=='3'){
        self.targetUnit([
            {id:'1',text:'作业计划'},
            {id:'2',text:'外包单位'},
            {id:'4',text:'风险'},
            {id:'5',text:'隐患'},
            {id:'6',text:'事件'},
            {id:'7',text:'违章'},
        ]);
    }else if(e.runit._latestValue=='4'){
        self.targetUnit([
            {id:'1',text:'作业计划'},
            {id:'2',text:'外包单位'},
            {id:'3',text:'人员'},
            {id:'5',text:'隐患'},
            {id:'6',text:'事件'},
            {id:'7',text:'违章'},
        ]);
    }else if(e.runit._latestValue=='5'){
        self.targetUnit([
            {id:'1',text:'作业计划'},
            {id:'2',text:'外包单位'},
            {id:'3',text:'人员'},
            {id:'4',text:'风险'},
            {id:'6',text:'事件'},
            {id:'7',text:'违章'},
        ]);
    }else if(e.runit._latestValue=='6'){
        self.targetUnit([
            {id:'1',text:'作业计划'},
            {id:'2',text:'外包单位'},
            {id:'3',text:'人员'},
            {id:'4',text:'风险'},
            {id:'5',text:'隐患'},
            {id:'7',text:'违章'},
        ]);
    }else if(e.runit._latestValue=='7'){
        self.targetUnit([
             {id:'1',text:'作业计划'},
            {id:'2',text:'外包单位'},
            {id:'3',text:'人员'},
            {id:'4',text:'风险'},
            {id:'5',text:'隐患'},
            {id:'6',text:'事件'},
        ]);
    }
    
} 
self.mblbchange=function(){
   
}
self.assUnit=ko.observableArray([
    {id:'1',text:'一对一'},
    {id:'2',text:'一对多'},
])

self.gbClick = function(){
    div1.className='xinzeng'
}
self.increase=function(){
    newadd()

    div1.className='xinzeng'
}
self.nodeName=''

    self.exportExcel=function(){
        cube.getPageViewModelByNode($("#instanceTable")).exportExcel()
    }

    self.onSelectedItems=function(item){
        console.log(item,1)
    }
       
        var div1 = document.querySelector('.xinzeng')
		self.add = function(){
            div1.className='xzh'
        }



        //关系模板下载
        relationshipmbxz=function relationshipmbxz(){
            // var timestamp = Date.parse(new Date());
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
            var mbName = '关系模板' + timestamp +'.xls';
            $('#relationshipmbxz').attr('href','pages/hapMap/category/关系模板.xls');
            $('#relationshipmbxz').attr('download',mbName);
            var params = {
                "params":
                    { "mbName":mbName}
            }
            axios.post(cube.gatewayURL2 + "fileLead/relationshiptemplateDownload", params).then(function (res) {
                if (res.data.successful==true) {

                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }



            //导入
            importExcel=function(obj){
                var formData = new FormData();
                var name = $("#articleImageFile").val();
                console.log(name)
                var a=name.lastIndexOf('.')
                var b=name.slice(a)
                if(b!=='.xls'){
                    cube.indicate("只能上传xls格式文件");
                    return
                }
                formData.append("file",$("#articleImageFile")[0].files[0]);
                formData.append("name",name);//可以使用formData.append()传递多个参数
                // $.ajax({
                //     url : cube.gatewayURL2+"fileLead/fileRelationLead",
                //     type : 'POST',
                //     async : false,
                //     data : formData,
                //     complete:function(res){
                //         console.log(res)
                //         if(res.data.successful=true){
                //                 cube.indicate("导入成功");
                //         }else{
                //                 cube.indicate("导入失败");

                //         }
                //     },
                //     // 告诉jQuery不要去处理发送的数据
                //     processData : false,
                //     // 告诉jQuery不要去设置Content-Type请求头
                //     contentType : false,
                //     beforeSend:function(){
                //         console.log("正在进行，请稍候");
                //     },
                // });
                var params = formData
                axios.post(cube.gatewayURL2 + "fileLead/fileRelationLead",params).then(function(res) {
                    if (res.data.successful) {
                        cube.indicate("导入成功");
                        RiskList(self.pageIndex())
                    } else {
                        cube.indicate("导入失败");
                    }
            
                })
             }



     
        //导出
		self.bulkImport = function(){
        var timestamp=new Date().getTime();
        var fileName = "知识获取关系" + timestamp;
                axios({
                    url:cube.gatewayURL2 +"fileLead/fileImport",
                    method: 'post', 
                    data:{
                        page:self.pageIndex(),
                        size:10,
                        fileName:fileName
                    },
                    responseType:'blob'
                }).then(function(res){
                    console.log(res)
                        var data = res;
                        var blob = new Blob([data.data], { type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"});
                        var url = window.URL.createObjectURL(blob);
                        var link = document.createElement("a");
                        link.style.display = "none";
                        link.href = url;
                        link.setAttribute("download",  fileName +".xls");
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);
                })
		}

		self.size = 'middle';
        self.style ="background:#cccccc;";
        self.columns = [
            {name: "RELATION_ID", width: "10%", caption: "序号", editorType: "TextEditor",align : "center"},
            {name: "RELATION_NAME", width: "10%", caption: "名称", editorType: "TextEditor",align : "center"},

            {name: "RELATION_CODE", width:"10%", caption: "编码" , editorType: "TextEditor",align : "center"},
            {name: "SOURCE_TYPE", width:"10%", caption: "源类别" , editorType: "TextEditor",align : "center" },
            {name: "TARGET_TYPE", width:"10%", caption: "目标类别" , editorType: "TextEditor",align : "center" },
            {name: "RELATION_TYPE", width:"10%", caption: "关联关系类型" , editorType: "TextEditor",align : "center" },
            {name: "RELATION_DESCRIBE", width:"10%", caption: "描述" , editorType: "TextEditor",align : "center" },
            {name: "PROCESSENDTIME", width:"10%", caption: "更新时间" , editorType: "TextEditor",align : "center" },

        ];
		cube.endViewModel(self, params);
	};
    return PageViewModel;
});