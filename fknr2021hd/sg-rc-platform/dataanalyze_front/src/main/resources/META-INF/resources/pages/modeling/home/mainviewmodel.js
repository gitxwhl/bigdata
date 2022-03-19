define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;
        self.content2 = '数据加载中';
        self.isShow = ko.observable("")
        self.id=ko.observable("")
        self.titleName=ko.observable("")
        self.province = [
            { id: '', text: '全部' },
            { id: '1', text: '北京' },
            { id: '2', text: '上海' },
        ];
        self.provinceContent = '';
        self.unitContent = [
            { id: '', text: '全部' }
        ];
        self.primaryKey=''
        self.unit = '';
        self.size = 'middle';
        self.count = '';
        self.pageTotalCount = '';
        var mask = document.querySelector('.mask')


        self.pageSize = 1;
        self.pageIndex = ko.observable(1);


        self.pageVisibleCount = 1;
        self.msgType = 'confirm';
        self.isShowDialog = false;
        self.title = '温馨提示';
        self.content = '删除后将不能恢复，是否确定删除?';

        self.onPageIndexChanged = function(p_pageIndex) {
            if (typeof(console) != "undefined" && typeof(console.log) != "undefined") {
                self.pageIndex(p_pageIndex);
                RiskList()
            }
        }

        self.confirmDelete = function (e) {
            // self.id=item
            // console.log(self.id)
            var params = {"typeId":self.id}
            axios.post(cube.gatewayURL2 + "atlasInformation/deleteModeing",params).then(function(res) {
                if (res.data.successful==true) {
                    cube.indicate("删除成功");
                    self.isShowDialog(false);
                    RiskList()
                } else {
                    cube.indicate(res.data.resultHint);
                }
            })
        };


        self.name=ko.observableArray([
            {id:'1',text:'作业计划'},
            {id:'2',text:'外包单位'},
            {id:'3',text:'人员'},
            {id:'4',text:'风险'},
            {id:'5',text:'隐患'},
            {id:'6',text:'事件'},
            {id:'7',text:'违章信息'},
        ])
        //生产关系编号
        self.genrelas= ko.observable("")
        function genrela(){
            axios.post(cube.gatewayURL2 + "atlasInformation/generatedNumber").then(function(res){
                self.genrelas(res.data.resultValue)
                console.log(self.genrelas())
            })
        }
        genrela()

        self.unit = ko.observable("1")
        self.describe=''
        self.icon='pages/hapMap/icon/u12012.png'
        $('.icon').click(function(){
            $('.icontb').css('display','inline-block')
        })
        self.gbicon=function(){
            $('.icontb').css('display','none')

        }
        $('.tbhz1').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12012.png')
            self.icon('u12012')
            $('.icontb').css('display','none')
        })
        $('.tbhz2').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12014.png')
            self.icon('u12014')
            $('.icontb').css('display','none')
        })
        $('.tbhz3').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12016.png')
            self.icon('u12016')
            $('.icontb').css('display','none')
        })
        $('.tbhz4').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12018.png')
            self.icon('u12018')
            $('.icontb').css('display','none')
        })
        $('.tbhz5').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12020.png')
            self.icon('u12020')
            $('.icontb').css('display','none')
        })
        $('.tbhz6').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12022.png')
            self.icon('u12022')
            $('.icontb').css('display','none')
        })
        $('.tbhz7').click(function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12024.png')
            self.icon('u12024')
            $('.icontb').css('display','none')
        })
        //刷新
        self.refresh = function () {
            var params = {
                "page":	1,
                "size":10,
                "typeName": '',
            }
            self.titleName('')
            self.isShow(true)
            // var params = {"typeName": ''}
            axios.post(cube.gatewayURL2 + "atlasInformation/getModeling",params).then(function(res) {
                if (res.data.successful=true) {
                    self.count(res.data.resultValue.totalSize)
                    self.pageIndex(res.data.resultValue.page)
                    self.pageTotalCount(res.data.resultValue.totalPage)
                    var risklist=res.data.resultValue.content
                    self.cityContent(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            })

        }
        // self.onPageIndexChanged = function (p_pageIndex) {}
        self.customOperations = [
            {
                caption: "查看",
                click: function (item) {
                    cube.showDialog({
                        title: '查看',
                        width: "800px",
                        height: '400px',
                        submitFormOnConfirm: false,
                        isShowCloseBtn: false, // 是否显示取消按钮
                        isShowConfirmBtn: false, // 是否显示保存按钮
                        templateOptions: {
                            name: 'modeling.home.ssdetail',
                            params: { item: item, isEditor: false }
                        }
                    });
                }
            },
            {
                caption: "编辑",
                click: function (item) {
                    cube.showDialog({
                        title: '编辑',
                        width: "800px",
                        height: '440px',
                        submitFormOnConfirm: false,
                        isShowCloseBtn: false, // 是否显示取消按钮
                        isShowConfirmBtn: false, // 是否显示保存按钮
                        templateOptions: {
                            name: 'modeling.home.detail',
                            params: { item: item, isEditor: true }
                        }
                    });
                }
            },
            {
                caption: "删除",
                click: function (item) {
                    // console.log(item.TYPE_ID)
                    self.id=item.TYPE_ID
                    self.isShowDialog(true)
                }
            }
        ]
        self.style = "background:#cccccc;";
        RiskList()
        self.columns = [
            { name: "TYPE_ID", width: "30%", readOnly: true, caption: "ID", editorType: "TextEditor", align: "center"
         },
            {
                name: "TYPE_NAME", width: "15%", caption: "名称", editorType: "TextEditor", align: "center",
                renderCell: function(cellValue, element, dictValue) {
                    if(cellValue==='1') {
                        return'作业计划'
                    }else if(cellValue==='2') {
                        return '外包单位'
                    }else if(cellValue==='3') {
                        return '人员'
                    }else if(cellValue==='4') {
                        return '风险'
                    }else if(cellValue==='5') {
                        return '隐患'
                    }else if(cellValue==='6') {
                        return '事件'
                    }else if(cellValue==='7') {
                        return '违章信息'
                    }
                }
            },
            { name: "TYPE_CODE", width: "10%", caption: "编码", editorType: "TextEditor", align: "center"
            },
            { name: "TYPE_ICON", width: "10%", caption: "图标", editorType: "TextEditor", align: "center",
                renderCell: function (cellValue, element, dictValue) {
                    // console.log(cellValue,1, element,1, dictValue)
                    if(cellValue==='u12012'){
                        return "<img src='pages/hapMap/icon/u12012.png'></img>"
                    }else if(cellValue==='u12014'){
                        return "<img src='pages/hapMap/icon/u12014.png'></img>"
                    }else if(cellValue==='u12016'){
                        return "<img src='pages/hapMap/icon/u12016.png'></img>"
                    }else if(cellValue==='u12018'){
                        return "<img src='pages/hapMap/icon/u12018.png'></img>"
                    }else if(cellValue==='u12020'){
                        return "<img src='pages/hapMap/icon/u12020.png'></img>"
                    }else if(cellValue==='u12022'){
                        return "<img src='pages/hapMap/icon/u12022.png'></img>"
                    }else if(cellValue==='u12024'){
                        return "<img src='pages/hapMap/icon/u12024.png'></img>"
                    }
                },
            },
            { name: "TYPE_DESCRIBE", width: "10%", caption: "描述", editorType: "TextEditor", align: "center"
         },
            { name: "UPDATE_TIME", width: "10%", caption: "更新时间", editorType: "TextEditor", align: "center"}

        ];
        self.cityContent = []
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
            if(ipt1.value.length > 50) {
                ipt1.value = ipt1.value.substring(0,50)
                cube.indicate("编码长度不能超过50！");
            }
        }
        //输入框onchange事件
        self.iptChange3 = function() {
            var ipt1 = document.querySelector("#ipt3")
            if(ipt1.value.length > 200) {
                ipt1.value = ipt1.value.substring(0,200)
                cube.indicate("描述长度不能超过200！");
            }
        }
        //新增 待做
        self.gbClick=function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12051.png')
            div1.className='xinzeng'
            mask.style.display='none'
        }
        var div1 = document.querySelector('.xinzeng')
        self.search = function() {
            div1.className='xzh'
            self.describe('')
            self.icon('')
            mask.style.display='block'
            $('.icontb').css('display','none')
        }


        //查询
        self.query=function(){
            self.pageIndex(1)
            RiskList()
        }
        self.xinzeng=function(){
            // 必填项校验
            if(!self.unit().trim()){
                cube.indicate("请选择名称");
                return
            }         
            if(!self.genrelas().trim()){
                cube.indicate("请输入编码");
                return
            }
            if(!self.describe().trim()){
                cube.indicate("请输入描述");
                return
            }
            if(!self.icon().trim()){
                cube.indicate("请选择图标");
                return
            }
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            var iptRep2 = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》:;：；/￥——{}\"‘”“'_()?？·（）【】’#]")
            if(iptRep.test(self.genrelas().trim()) || iptRep2.test(self.describe().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.genrelas().trim().length !== self.genrelas().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            if(self.describe().trim().length !== self.describe().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.genrelas().trim().length > 50) {
                cube.indicate("编码长度不能超过50！");
                return;
            }
            if(self.describe().trim().length > 200) {
                cube.indicate("描述长度不能超过200！");
                return;
            }
            $('.icon img').attr('src','pages/hapMap/icon/u12051.png')



            var params = {"params":{
                    "typeName":self.unit(),
                    "typeCode":self.genrelas(),
                    "typeDescribe":self.describe(),
                    "typeIcon":self.icon(),
                }}

            if($("#addBtn").attr('dataClick') == 'true') {
                $("#addBtn").attr('dataClick','false')
                $("#addBtn").removeClass('know-btn').addClass('know-btn-disabled')
                axios.post(cube.gatewayURL2 + "atlasInformation/insertModeing",params).then(function(res) {
                    console.log("============》》" + res.data.successful )

                    // 新增成功
                    if (res.data.successful) {
                        if (res.data.resultValue === '新增成功') {
                            cube.indicate("新增成功");

                        } else if (res.data.resultValue === '新增失败') {
                            cube.indicate("新增失败，该名称已存在");
                        }
                        RiskList()
                        genrela()
                        // var risklist=res.data.resultValue
                        // self.cityContent(risklist);

                    } else {
                        cube.indicate("数据加载失败");
                    }
                    $("#addBtn").attr('dataClick','true')
                    $("#addBtn").removeClass('know-btn-disabled').addClass('know-btn')
                    div1.className='xinzeng'
                    mask.style.display='none'
                })
            }

        }


        function RiskList(searchParams) {
            // 输入框内容校验
            var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
            if(iptRep.test(self.titleName().trim())) {
                cube.indicate("输入框含违法字符，请重新输入！");
                return;
            }
            // 输入框空格校验
            if(self.titleName().trim().length !== self.titleName().length) {
                cube.indicate("输入框内容不能全空格或首尾有空格！");
                return;
            }
            // 输入框长度校验
            if(self.titleName().trim().length > 100) {
                cube.indicate("输入框内容长度不能超过100！");
                return;
            }
            // var params = {"typeName": self.titleName()}
            self.isShow(true)
            var params = {
                "page":	self.pageIndex(),
                "size":10,
                "typeName": self.titleName(),
            }
            axios.post(cube.gatewayURL2 + "atlasInformation/getModeling",params).then(function(res) {
                if (res.data.successful=true) {
                    self.count(res.data.resultValue.totalSize)
                    self.pageIndex(res.data.resultValue.page)
                    self.pageTotalCount(res.data.resultValue.totalPage)
                    var risklist=res.data.resultValue.content
                    self.cityContent(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }
                self.isShow(false)
            }).catch(err=>{
                self.isShow(false)
                cube.indicate("请求超时，稍后重试！");
            })

        }

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});