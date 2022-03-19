define(["RESTClient","echarts", "axios",], function(RestClient,echarts,axios) {
	var PageViewModel = function(params) {
		var self = this;
		self.id=params.item.TYPE_ID
		// console.log(self.id)
		self.isEditor = true;
		self.releaseUnit =[
			{id:'1',text:'作业计划'},
			{id:'2',text:'外包单位'},
			{id:'3',text:'人员'},
			{id:'4',text:'风险'},
			{id:'5',text:'隐患'},
			{id:'6',text:'事件'},
			{id:'7',text:'违章'},
		];
		self.name = '';
		self.code = '';
		self.content = '';
		// self.isShowDialog = false;
		self.onRendered = function (e) {
			RiskList()

		};
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
		$('.w-100').click(function(){
			console.log($('.w-100'))
		})
        setTimeout(function(){
        	//查看
			var params = {"typeId": self.id()}
			axios.post(cube.gatewayURL2 + "atlasInformation/getModenlingDetails",params).then(function(res) {
				if (true) {
					var risklist=res.data.resultValue
					  // console.log(risklist)
					  if(risklist.TYPE_NAME==='作业计划'){
						risklist.TYPE_NAME='1'
					  }else if(risklist.TYPE_NAME==='外包单位'){
						risklist.TYPE_NAME='2'
					  }else if(risklist.TYPE_NAME==='人员'){
						risklist.TYPE_NAME='3'
					  }else if(risklist.TYPE_NAME==='风险'){
						risklist.TYPE_NAME='4'
					}else if(risklist.TYPE_NAME==='隐患'){
						risklist.TYPE_NAME='5'
					}else if(risklist.TYPE_NAME==='事件'){
						risklist.TYPE_NAME='6'
					}else if(risklist.TYPE_NAME==='违章'){
						risklist.TYPE_NAME='7'
						  
					}
						self.name(risklist.TYPE_NAME);
						console.log(self.name(),risklist.TYPE_NAME)
						self.code(risklist.TYPE_CODE);
						self.content(risklist.TYPE_DESCRIBE);
						var img=document.querySelector('#img')
						if(risklist.TYPE_ICON==='u12012'){
							img.src='pages/hapMap/icon/u12012.png'
						}else if(risklist.TYPE_ICON==='u12014'){
							img.src='pages/hapMap/icon/u12014.png'
						}else if(risklist.TYPE_ICON==='u12016'){
							img.src='pages/hapMap/icon/u12016.png'
						}else if(risklist.TYPE_ICON==='u12018'){
							img.src='pages/hapMap/icon/u12018.png'
						}else if(risklist.TYPE_ICON==='u12020'){
							img.src='pages/hapMap/icon/u12020.png'
						}else if(risklist.TYPE_ICON==='u12022'){
							img.src='pages/hapMap/icon/u12022.png'
						}else if(risklist.TYPE_ICON==='u12024'){
							img.src='pages/hapMap/icon/u12024.png'
						}
				} else {
					cube.indicate("数据加载失败");
				}

			})


			// self.isEditor(params.isEditor)
			// if(params.item){
			// 	var data = params.item;
			// 	self.name(data.name);
			// 	self.code(data.code);
			// 	self.content(data.content);
            // }

		})
		//保存
		self.save = function(){
			//编辑
			var params ={
				params:{
				"typeId":self.id(),
				"typeName":self.name(),
				"typeCode":self.code(),
				"typeDescribe":self.content(),
				"typeIcon":self.icon()
			}
			}
			axios.post(cube.gatewayURL2 + "atlasInformation/updateModeling",params).then(function(res) {
				if (res.data.successful=true) {
					cube.indicate("编辑成功");
					commonPageBridge({
						name: 'modeling.home.main',
					});
					RiskList()

				} else {
					cube.indicate("编辑失败");
				}
			})
			self.gbClick()  //关闭弹窗
			self.onRendered()
		}
		self.gbClick = function(){
            params.dialog.closeDialog();
		}
		//列表数据
		function RiskList(searchParams) {
			var params = {"typeName": ''}
			axios.post(cube.gatewayURL2 + "atlasInformation/getModeling",params).then(function(res) {
				if (res.data.successful=true) {
				

					// var risklist=res.data.resultValue
					//
					// self.cityContent(risklist);
				} else {
					cube.indicate("数据加载失败");
				}

			})
		}

		cube.endViewModel(self, params);
	};
	return PageViewModel;
});