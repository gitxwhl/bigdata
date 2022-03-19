define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
    var PageViewModel = function (params) {
        var self = this;

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
        self.count = '20';
        self.pageTotalCount = '2';
        self.pageIndex = 1;
        self.pageVisibleCount = 1;

        self.msgType = 'confirm';
        self.isShowDialog = false;
        self.title = '温馨提示';
        self.content = '删除后将不能恢复，是否确定删除?';
        self.confirmDelete = function (e) {
            // self.id=item
            // console.log(self.id)
            var params = {"typeId":self.id}
            axios.post(cube.gatewayURL2 + "atlasInformation/deleteModeing",params).then(function(res) {
                cube.indicate("删除成功");

                self.isShowDialog(false);
                RiskList()
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
    })
    $('.tbhz2').click(function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12014.png')
        self.icon('u12014')

    })   
    $('.tbhz3').click(function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12016.png')
        self.icon('u12016')

    })   
    $('.tbhz4').click(function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12018.png')
        self.icon('u12018')

    })   
    $('.tbhz5').click(function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12020.png')
        self.icon('u12020')

    })   
    $('.tbhz6').click(function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12022.png')
        self.icon('u12022')

    })   
    $('.tbhz7').click(function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12024.png')
        self.icon('u12024')

    })      
        //刷新
        self.refresh = function () {
            self.titleName('')
            var params = {"typeName": ''}

            axios.post(cube.gatewayURL2 + "atlasInformation/getModeling",params).then(function(res) {
                if (true) {
                    var risklist=res.data.resultValue

                    self.cityContent(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })

        }
        self.onPageIndexChanged = function (p_pageIndex) {}
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
            { name: "TYPE_ID", width: "30%", readOnly: true, caption: "ID", editorType: "TextEditor", align: "center" },
            {
                name: "TYPE_NAME", width: "15%", caption: "名称", editorType: "TextEditor", align: "center",
                // renderCell: function (cellValue, element, dictValue) {
                //     element.style.color = "#0ec4da";
                // },
                // onClick: function (pkValue, cellValue) {
                //     commonPageBridge({
                //         name: 'modeling.category.main',
                //         params: {}
                //     });
                // }
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
            { name: "TYPE_DESCRIBE", width: "10%", caption: "描述", editorType: "TextEditor", align: "center" },
            { name: "UPDATE_TIME", width: "10%", caption: "更新时间", editorType: "TextEditor", align: "center" }

        ];
        self.cityContent = []
        //新增 待做
        self.gbClick=function(){
            $('.icon img').attr('src','pages/hapMap/icon/u12051.png')
            div1.className='xinzeng'
        }
        var div1 = document.querySelector('.xinzeng')
        self.search = function() {
            div1.className='xzh'
            self.describe('')
            self.icon('')
        }


        //查询
        self.query=function(){
            RiskList()
        }
        self.xinzeng=function(){
        $('.icon img').attr('src','pages/hapMap/icon/u12051.png')

            div1.className='xinzeng'

            var params = {"params":{
                "typeName":self.unit(),
                "typeCode":self.genrelas(),
                "typeDescribe":self.describe(),
                "typeIcon":self.icon(),
            }}
            axios.post(cube.gatewayURL2 + "atlasInformation/insertModeing",params).then(function(res) {
                if (res.data.successful=true) {
                    cube.indicate("新增成功");

                    // var risklist=res.data.resultValue
                    // self.cityContent(risklist);
                    RiskList()
                    genrela()
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }
            

        
        
        function RiskList(searchParams) {
            var params = {"typeName": self.titleName()}
            axios.post(cube.gatewayURL2 + "atlasInformation/getModeling",params).then(function(res) {
                if (res.data.successful=true) {
                    var risklist=res.data.resultValue
                    self.cityContent(risklist);
                } else {
                    cube.indicate("数据加载失败");
                }

            })
        }

        cube.endViewModel(self, params);
    };
    return PageViewModel;
});