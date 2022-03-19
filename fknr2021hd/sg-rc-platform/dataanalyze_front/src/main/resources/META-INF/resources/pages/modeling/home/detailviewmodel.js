define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.id=params.item.TYPE_ID
		// console.log(self.id)
		self.isEditor = true;
		self.pageIndex = ko.observable(1);
		self.releaseUnit =[
			{id:'1',text:'作业计划'},
			{id:'2',text:'外包单位'},
			{id:'3',text:'人员'},
			{id:'4',text:'风险'},
			{id:'5',text:'隐患'},
			{id:'6',text:'事件'},
			{id:'7',text:'违章'},
		];
		// self.name = '';
		// self.name2 = '';
		self.name = ko.observable("")
		self.name2 = ko.observable("")
		self.code = ko.observable("")
		self.content = ko.observable("")
		switch (params.item.TYPE_NAME) {
			case "1": self.name('作业计划');break;
			case "2": self.name('外包单位');break;
			case "3": self.name('人员');break;
			case "4": self.name('风险');break;
			case "5": self.name('隐患');break;
			case "6": self.name('事件');break;
			case "7": self.name('违章信息');break;
		}
		self.name2(params.item.TYPE_NAME);
		self.code(params.item.TYPE_CODE);
		self.content(params.item.TYPE_DESCRIBE);
		var img=document.querySelector('#img')
		img.src='pages/hapMap/icon/' + params.item.TYPE_ICON +'.png'
		// self.code = '';
		// self.content = '';
		// self.isShowDialog = false;
		// RiskList()

		// self.icon='pages/hapMap/icon/u12012.png'
		self.icon=params.item.TYPE_ICON
		
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
		$('.w-100').click(function(){
			console.log($('.w-100'))
		})
        	//查看
		function RiskList(searchParams) {
			var params = {"typeId": self.id}
			axios.post(cube.gatewayURL2 + "atlasInformation/getModenlingDetails", params).then(function (res) {
				if (res.data.successful) {
					var risklist = res.data.resultValue
					self.name2(risklist.TYPE_NAME);
					// console.log(risklist)
					if (risklist.TYPE_NAME === '作业计划') {
						risklist.TYPE_NAME = '1'
					} else if (risklist.TYPE_NAME === '外包单位') {
						risklist.TYPE_NAME = '2'
					} else if (risklist.TYPE_NAME === '人员') {
						risklist.TYPE_NAME = '3'
					} else if (risklist.TYPE_NAME === '风险') {
						risklist.TYPE_NAME = '4'
					} else if (risklist.TYPE_NAME === '隐患') {
						risklist.TYPE_NAME = '5'
					} else if (risklist.TYPE_NAME === '事件') {
						risklist.TYPE_NAME = '6'
					} else if (risklist.TYPE_NAME === '违章信息') {
						risklist.TYPE_NAME = '7'

					}
					self.name(risklist.TYPE_NAME);
					// console.log(self.name(), risklist.TYPE_NAME)
					self.code(risklist.TYPE_CODE);
					self.content(risklist.TYPE_DESCRIBE);
					var img = document.querySelector('#img')
					if (risklist.TYPE_ICON === 'u12012') {
						img.src = 'pages/hapMap/icon/u12012.png'
					} else if (risklist.TYPE_ICON === 'u12014') {
						img.src = 'pages/hapMap/icon/u12014.png'
					} else if (risklist.TYPE_ICON === 'u12016') {
						img.src = 'pages/hapMap/icon/u12016.png'
					} else if (risklist.TYPE_ICON === 'u12018') {
						img.src = 'pages/hapMap/icon/u12018.png'
					} else if (risklist.TYPE_ICON === 'u12020') {
						img.src = 'pages/hapMap/icon/u12020.png'
					} else if (risklist.TYPE_ICON === 'u12022') {
						img.src = 'pages/hapMap/icon/u12022.png'
					} else if (risklist.TYPE_ICON === 'u12024') {
						img.src = 'pages/hapMap/icon/u12024.png'
					}
				} else {
					cube.indicate("数据加载失败");
				}

				// self.isEditor(params.isEditor)
				// if(params.item){
				// 	var data = params.item;
				// 	self.name(data.name);
				// 	self.code(data.code);
				// 	self.content(data.content);
				// }

			})

		}

		//输入框onchange事件
		self.iptChange = function() {
			var ipt1 = document.querySelector("#shuru1")
			if(ipt1.value.length > 50) {
				ipt1.value = ipt1.value.substring(0,50)
				cube.indicate("编码长度不能超过50！");
			}
		}
		//输入框onchange事件
		self.iptChange2 = function() {
			var ipt1 = document.querySelector("#shuru2")
			if(ipt1.value.length > 200) {
				ipt1.value = ipt1.value.substring(0,200)
				cube.indicate("描述长度不能超过200！");
			}
		}
		//保存
		self.save = function(){
			// 必填项校验
			if(!self.name().trim()){
				cube.indicate("请输入名称");
				return
			}
			if(!self.code().trim()){
				cube.indicate("请输入编码");
				return
			}
			if(!self.content().trim()){
				cube.indicate("请输入描述");
				return
			}
			// 输入框内容校验
			var iptRep = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》，。,.:;：；/￥……——{}\"‘”“'_()?？·（）【】’#、]")
			var iptRep2 = new RegExp("[-`~!！@$^&%*+=|\\\\{}''\\[\\]<>《》:;：；/￥——{}\"‘”“'_()?？·（）【】’#]")
			if(iptRep.test(self.code().trim()) || iptRep2.test(self.content().trim())) {
				cube.indicate("输入框含违法字符，请重新输入！");
				return;
			}
			// 输入框空格校验
			if(self.code().trim().length !== self.code().length) {
				cube.indicate("输入框内容不能全空格或首尾有空格！");
				return;
			}
			if(self.content().trim().length !== self.content().length) {
				cube.indicate("输入框内容不能全空格或首尾有空格！");
				return;
			}
			// 输入框长度校验
			if(self.code().trim().length > 50) {
				cube.indicate("编码长度不能超过50！");
				return;
			}
			if(self.content().trim().length > 200) {
				cube.indicate("描述长度不能超过200！");
				return;
			}
			//编辑
			var params ={
				params:{
				"typeId":self.id(),
				"typeName":self.name2(),
				"typeCode":self.code(),
				"typeDescribe":self.content(),
				"typeIcon":self.icon()
						}
			}
			if($("#saveBtn").attr('dataClick') == 'true') {
				$("#saveBtn").attr('dataClick', 'false')
				$("#saveBtn").removeClass('know-btn').addClass('know-btn-disabled')
				axios.post(cube.gatewayURL2 + "atlasInformation/updateModeling", params).then(function (res) {
					if (res.data.successful = true) {
						cube.indicate("编辑成功");
						self.gbClick()  //关闭弹窗
						commonPageBridge({
							name: 'modeling.home.main',
						});
						RiskList()

					} else {
						cube.indicate("编辑失败");
					}
					$("#saveBtn").attr('dataClick','true')
					$("#saveBtn").removeClass('know-btn-disabled').addClass('know-btn')
				})
			}
			// self.onRendered()
		}
		self.gbClick = function(){
            params.dialog.closeDialog();
		}
		//列表数据
		// function RiskList(searchParams) {
		//
		// 	axios.post(cube.gatewayURL2 + "atlasInformation/getModeling",params).then(function(res) {
		// 		if (res.data.successful=true) {
		// 			// var risklist=res.data.resultValue
		// 			// self.cityContent(risklist);
		// 		} else {
		// 			cube.indicate("数据加载失败");
		// 		}
		//
		// 	})
		// }

		cube.endViewModel(self, params);
	};
	return PageViewModel;
});