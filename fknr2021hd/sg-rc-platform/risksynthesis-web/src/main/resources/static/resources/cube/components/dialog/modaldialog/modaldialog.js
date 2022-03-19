define([], function() {

	/**
	 * 模态对话框 
	 * <code language="html">
	 * 		<modaldialog params="
	 *						width: width,
	 *						height: height,
	 *						isShowDialog: isShowDialog,
	 *						title: title,
	 *						content: content,
	 *						templateOptions: templateOptions,
	 *						isShowCloseBtn: isShowCloseBtn,
	 *						isShowConfirmBtn: isShowConfirmBtn"></modaldialog>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.width = "600px";
	 * 				self.height = "300px";
	 * 				self.isShowDialog = false;
	 * 				self.conten = "内容";//当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
	 * 				self.templateOptions = {name: 'app_cubedemo.componentDemos.containerDemos.vsplit.Templ',
																params: {title: '第二列的模板'}}
																
	 * 				self.title = "弹出窗口";
	 * 				self.isShowCloseBtn = true;
	 * 				self.isShowConfirmBtn = false;				
	 * 
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
	function ViewModel(params) {
		var self = this;
		
		/**
		 * 宽度
		 * 
		 * @default 640px
		 */
		self.width = null;

		/**
		 * 高度
		 * 
		 * @default 480px
		 */
		self.height = null;
		
		/**
		 * 顶部边距
		 * 
		 * @default  
		 */
		self.top = null;

		/**
		 * 底部边距
		 * 
		 * @default
		 */
		self.bottom = null;
		
		/**
		 * 高宽未定义时是否自适应
		 * 
		 * @default
		 */
		self.isWidthHeightAuto = false;
		
		/**
		 * 底部栏高度
		 * @default auto
		 */
		self.footerHeight = "auto";
		
		/**
		 * 最大化显示
		 * 
		 * @default false
		 */
		self.isMax = false;

		/**
		 * 最小化显示
		 * 
		 * @default false
		 */
		self.isMin = false;

		/**
		 * 标题
		 * 
		 * @default 空
		 */
		self.title = "";

		/**
		 * 内容, 当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 * 
		 * @default 空
		 */
		self.content = null;

		/**
		 * 要加载的页面,当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 * 
		 * <code language="JavaScript">
		 *	  {
		 *			 name: "app_cubedemo.componentDemos.dialogcontainer.Templ",
		 *		  	 params: {title: '页面模板'}
		 *			 //type: "iframe"
		 *	  }
		 *  </code>
		 * 
		 * name :为页面路径，
		 * params : 为要传递给该页面对应ViewModel的参数
		 * type: 为页面加载方式，可选ajax、 iframe、viewModel，默认值为ajax，当页面为外部页面时生效（name以http开头）。
		 * 
		 * 
		 * 可通过页面ViewModel中的params.dialog获取该弹出框的对象
		 */
		self.templateOptions = null;
		
		/**
		 * 是否外部页面
		 * @ignore
		 */
		self.isOut = false;
		
		/**
		 * @ignore
		 */
		self.ajaxHtml = "";
		
		/**
		 * 设置返回值，该属性值将作为onConfirmBtnClick和onCloseBtnClick的参数传递，如果该属性值为函数，则将函数返回值作为参数。
		 * 示例，在弹框页面的viewmodel中：
		 *  <code language="JavaScript">
		 *		params.dialog.result = [{id:"1",name:"a"},{id:"2",name:"b"}];
		 *  </code>
		 * 在弹框操作的onConfirmBtnClick事件中：
		 *  <code language="JavaScript">
		 *		cube.showDialog({
		 *			isShowDialog: true,
		 *			title:"窗口标题",
		 *			templateOptions: {
		 *				name: 'app_factoryLayout.zhywmx.select'
		 *			},
		 *			onConfirmBtnClick: function(args) {
		 *				console.log(args.result);
		 *			}
		 *		});
		 *  </code>
		 */
		self.result = null;

		/**
		 * 是否显示对话框
		 * 
		 * @default false
		 */
		self.isShowDialog = false;

		/**
		 * 是否显示关闭按钮
		 * 
		 * @default true
		 */
		self.isShowCloseBtn = true;
		
		/**
		 * 是否显示最小化按钮
		 * 
		 * @default true
		 */
		self.isShowMinBtn = true;

		/**
		 * 是否显示最大化按钮
		 * 
		 * @default true
		 */
		self.isShowMaxBtn = true;
		
		/**
		 * 关闭按钮文本内容
		 * 
		 * @default 关闭
		 */
		self.closeBtnText = cube.msg('CLOSE');

		/**
		 * 是否显示确定按钮
		 * 
		 * @default true
		 */
		self.isShowConfirmBtn = true;
		
		/**
		 * 确定按钮文本内容
		 * 
		 * @default 确定
		 */
		self.confirmBtnText = cube.msg('CONFIRM');
		
		/**
		 * 是否点击确定按钮时提交窗口内表单（如果窗口内存在表单）
		 * 
		 *  @default false
		 */
		self.submitFormOnConfirm = false;

		/**
		 * 自定义按钮
		 * [{
		 *		text : "收藏",
		 *	  visible: true, //不设置时默认为true
		 *	  type: "primary", //按钮样式，可选的值包括：default、primary、success、info、warning、danger、inverse、link
		 *		click: function(dialog) {
		 *		  //点击按钮时触发
		 *		  dialog.closeDialog();
		 *	  }
		 *	}, {
		 *		text : "查看",
		 *		click: function() {
		 *		  //点击按钮时触发
		 *	  }
		 *	}]
		 *
		 * @default []
		 */
		self.customBtns = [];
		
		/**
		 * 是否翻转关闭按钮和确定按钮的位置
		 * 
		 * @default false
		 */
		self.reverseBtn = false;
		
		/**
		 * 确定按钮点击事件，接收args参数，当设置args.cancel为true时取消后续关闭窗口操作
		 */
		self.onConfirmBtnClick = null;
		
		/**
		 * 关闭按钮点击事件，接收args参数，当设置args.cancel为true时取消后续关闭窗口操作
		 */
		self.onCloseBtnClick = null;
		
		/**
		 * 组件渲染完成事件(只包括对话框组件本身，不包含弹框内页面)
		 */
		self.onRendered = null;

		/**
		 * 内部属性，动画显示
		 * 
		 * @ignore
		 */
		self.animatShowBck = false;

		/**
		 * 内部属性，动画显示
		 * 
		 * @ignore
		 */
		self.animatShowDialog = false;

		/**
		 * 控件id（窗口节点的id），该属性不设置时，当时使用cube.showDialog方法创建窗口时，会自动设置eleId
		 * 
		 * @default null
		 */
		self.eleId = null;
		
		/**
		 * 窗口堆叠顺序，当使用cube.showDialog方法创建窗口时，如果没有传入该属性值，该属性值会自动累加
		 * 
		 * @default 1050
		 */
		self.zIndex = 1050;
		
		/**
		 * @ignore
		 */
		self._footerHeight = null;
		
		/**
		 * @ignore
		 */
		self.$e = null;
		
		/**
		 * @ignore
		 */
		self.isdrag = false;
		
		/**
		 * @ignore
		 */
		self.tx = 0;
		
		/**
		 * @ignore
		 */
		self.ty = 0;
		
		/**
		 * @ignore
		 */
		self.bodyHeight = 0;
		
		/**
		 * @ignore
		 */
		self.ismedDialog = true;

		
		var node = null;
		var boxWidth = null;
		var boxHeight = null;

		/**
		 * 初始化方法
		 * 
		 * @ignore
		 */
		self._init = function() {
			self.isdrag = false;
			var templateOptions = self.templateOptions();
			if (cube.notEmpty(templateOptions) && cube.notEmpty(templateOptions.name) 
					&& templateOptions.name.indexOf("http") == 0 && templateOptions.type != "viewModel") {
				self.isOut(true);
			}
			
			animat(self.isShowDialog());
			self.isShowDialogSub = cube.subscribe(self.isShowDialog, animat);
			
			if (templateOptions != null) {
				if (typeof(templateOptions.params) == "undefined") {
					templateOptions.params = {};
				}
				
				templateOptions.params.dialog = this;
				
				if (self.isOut()) {
					if (cube.isEmpty(templateOptions.type)) {
						templateOptions.type = "ajax";
					}
					
					if ("ajax" == templateOptions.type) {
						_loadInAjax(templateOptions.name);
					} else if ("iframe" == templateOptions.type) {
						templateOptions.params["dialog"] = {};
						templateOptions.name += (templateOptions.name.indexOf("?") > 0 ? "&" : "?") + "params=" + JSON.stringify(templateOptions.params);
					}
				}
			}
		};

		/**
		 * @ignore
		 */
		self.headmousedown = function(viewModel, event){
			var width = self.$e.find('.modal').width();
			var height = self.$e.find('.modal').height();
			boxHeight = document.documentElement.clientHeight - 30;
			boxWidth = document.documentElement.clientWidth - 10;
			if( self.ismedDialog()){
				self.isdrag = true;
				$(document).unbind('mousemove').bind('mousemove',self.headmousemove);
				
				self.tx = event.clientX;
				self.ty = event.clientY;
				
				node = event.currentTarget ? event.currentTarget : event.srcElement; 
				node = node.parentNode;
				node.style.top = $(node).position().top + "px";
				if(cube.isEmpty(node.style.left)){
					node.style.left = "0px";
				}
				node.style.width = width + "px";
				node.style.height = height + "px";
				node.style.bottom = "";
				node.style.right = "";
			}
		};
		
		/**
		 * @ignore
		 */
		self.headmousemove = function(event){
			if (self.isdrag && self.ismedDialog()){
				var ox = event.clientX - self.tx;
				var oy = event.clientY - self.ty;
				var top = node.style.top;
				var left = node.style.left;
				top = parseInt(top.substr(0,top.indexOf("px")));
				left = parseInt(left.substr(0,left.indexOf("px")));

				var stationY = top + oy;
				var stationX = left + 2*ox;
				if((stationY < boxHeight) && (stationY > 0) && (stationX < boxWidth)){
					node.style.top = stationY + "px";
					node.style.left = stationX + "px";
				}
				
				self.tx = event.clientX;
				self.ty = event.clientY;
			}
		};
		
		/**
		 * @ignore
		 */
		self.headmouseup = function(){
			self.isdrag = false;
			$(document).unbind('mousemove' ,self.headmousemove);
		};
		
		window.onmouseup = document.onmouseup = function(){
			self.isdrag = false;
		};
		
		/**
		 * @ignore
		 */
		self.mouseout = function($element,e){
			var reltg = e.relatedTarget ? e.relatedTarget : e.toElement;  
			while( reltg && reltg != $element ) {
				reltg = reltg.parentNode;
			}
		};
		
		/**
		 * 关闭对话框
		 */
		self.closeDialog = function() {
			if (self.onCloseBtnClick != null && !cube.isObservable(self.onCloseBtnClick)) {
				if (self.isOut() && "iframe" == self.templateOptions().type) {
					self._getCrosResult("close");
				} else {
					var result = null;
					if (cube.notEmpty(self.result)) {
						if (cube.isFunction(self.result)) {
							result = self.result();
						} else {
							result = self.result;
						}
					}
					
					var args = {cancel: false, result: result};
					self.onCloseBtnClick(args);
					if (args.cancel) {
						return false;
					}
				}
			}
			
			close();
		};

		/**
		 * 最大化对话框
		 */
		var _top = 0;
		var _bottom = 0;
		var _width = 0;
		var _height = 0;
		self.maxDialog = function() {
			self.ismedDialog(false);
			if (!self.isMax() && !self.isMin()) {
				_top = self.top();
				_bottom = self.bottom();
				if(self.isWidthHeightAuto()){
					_width = self.$e.find('.modal').width()+"px";
					_height = self.$e.find('.modal').height()+"px";
				} else {
					_width = self.width();
					_height = self.height();
				}
			}

			self.isMax(true);
			self.isMin(false);

			self.top("0px");
			self.bottom("0px");
			self.$e.find('.modal').css("left", "0px");
//			self.$e.css("main", "0px");
//			self.$e.css("right", "0px");
			self.width("100%");
			self.height("100%");
		};

		/**
		 * 最小化对话框
		 */
		self.minDialog = function() {
			self.ismedDialog(false);
			if (!self.isMax() && !self.isMin()) {
				_top = self.top();
				_bottom = self.bottom();
				if(self.isWidthHeightAuto()){
					_width = self.$e.find('.modal').width()+"px";
					_height = self.$e.find('.modal').height()+"px";
				} else {
					_width = self.width();
					_height = self.height();
				}
			}

			self.isMin(true);
			self.isMax(false);

			self.top("");
			self.bottom('0px');
			self.$e.find('.modal').css("left", "0px");
//			self.$e.css("right", "0px");
			self.width("300px");
			self.height("50px");
		};

		/**
		 * 还原对话框
		 */
		self.normalDialog = function() {
			self.ismedDialog(true);
			self.isMin(false);
			self.isMax(false);

			self.top(_top);
			self.bottom(_bottom);
			self.width(_width);
			self.height(_height);
		};
		
		/**
		 * 点击确定按钮
		 */
		self.confirmDialog = function() {
			
			if (self.onConfirmBtnClick !== null && !cube.isObservable(self.onConfirmBtnClick)) {
				if (self.isOut() && "iframe" == self.templateOptions().type) {
					self._getCrosResult("confirm");
				} else {
					var result = null;
					if (cube.notEmpty(self.result)) {
						if (cube.isFunction(self.result)) {
							result = self.result();
						} else {
							result = self.result;
						}
					}
					
					var args = {cancel: false, result: result};
					self.onConfirmBtnClick(args);
					if (args.cancel) {
						return false;
					}
				}
			}
			
			if (self.submitFormOnConfirm()) {
				if (self.$e !== null) {
					var dataform = self.$e.find("dataform");
					if (dataform && dataform.length > 0) {
						for (var i = 0; i < dataform.length; i++) {
							var viewModel = cube.getPageViewModelByNode(dataform.eq(i));
							if (viewModel && viewModel.submitForm) {
								if (viewModel.onSaved == null || cube.isObservable(viewModel.onSaved)) {
									viewModel.onSaved = function() {
										cube.indicate(cube.msg("SAVE_SUCCESS"));
										close();
									}
								}
								
								viewModel.submitForm();
							}
						}
					}
				}
			} else {
				close();
			}
		};
		
		/**
		 * @ignore
		 */
		self._getCrosResult = function (type) {
			var onmessage = function(event) {
				var source = event.source;
				var data = event.data;
				var origin = event.origin;
				data = JSON.parse(data);
				var args = {cancel: false, result: data};
				
				if (type == "close") {
					self.onCloseBtnClick(args);
				} else {
					self.onConfirmBtnClick(args);
				}
				
				if (args.cancel) {
					return false;
				}
			};
			
			if (window.addEventListener) {
				window.addEventListener("message", onmessage, false);
			} else {
				window.attachEvent("onmessage", onmessage);
			}
			
			if (document.iframe_dialog) {
				document.iframe_dialog.postMessage("{\"__result__\": true}", "*");
			}
		};
		
		/**
		 * @ignore
		 */
		self.customClick = function(viewMode, event) {
			if (cube.notEmpty(viewMode.click) && typeof(viewMode.click) == "function") {
				viewMode.click(self);
			}
		};
		
		var  _initNum= 0;
		var thisBodyHeight = 0;
		self._resize = function (numb,thisBody,modalHeader,modalFooter,modalBodyPadding) {
			var boxHeader = modalHeader + modalFooter + self._footerHeight() + modalBodyPadding;
			if(_initNum=="0"){
				thisBodyHeight = thisBody.outerHeight();
				_initNum++;
			}
			
			if(self.height() == "auto" && self.isWidthHeightAuto()){
				thisBodyHeight = self.$e.find('.modal-body').outerHeight();
			}
			 
			if(numb == "a"){
				rationalHeight = parseInt($(window).height()*.8 - boxHeader);
			}
			
			if(numb == "b"){
				rationalHeight = parseInt($(window).height()*.9 - parseInt(self.bottom().replace("px","")) - boxHeader);
			}
			
			if(numb == "c"){
				rationalHeight = parseInt($(window).height()*.9 - parseInt(self.top().replace("px","")) - boxHeader);
			}
			
			if(numb == "d"){
				rationalHeight = parseInt($(window).height() - parseInt(self.top().replace("px","")) - parseInt(self.bottom().replace("px","")) - boxHeader);
			}
			
			if(!self.isWidthHeightAuto() && !(numb == "d")){
				return
			}
			
   		 	if (thisBodyHeight > rationalHeight && !self.isMax() && !self.isMin()){
	   			 if (rationalHeight < 0){
	   				 self.bodyHeight('0px');
	   			 } else {
	   				 self.bodyHeight(rationalHeight+'px');
	   			 }
	   			
	   			 if(numb == "a" || numb == "c"){
		   			 self.bottom(parseInt($(window).height()*.1) + "px");
	   			 }
	   			 if(numb == "b"){
		   			 self.top(parseInt($(window).height()*.1) + "px"); 
	   			 }
			} else if(thisBodyHeight <= rationalHeight){ 
				if(numb == "b"){
					self.top("auto");
				}
				 self.bodyHeight('auto');
			} else {
				if(numb == "b"){
					self.top("auto");
				}
				if (numb == "d"){
					if (rationalHeight < 0){
		   				 self.bodyHeight('0px');
		   			 } else {
		   				 self.bodyHeight(rationalHeight+'px');
		   			 }
				}
			}
		}
								
		function close() {
			self.isShowDialog(false);
			if (self.eleId() != null) {
				window.setTimeout(function() {
					$("#"+self.eleId()).remove();
					$(".cube.modal.fade.in:last").focus();
				}, 310);
			}
		}
		
		function animat(newValue) {
			if (newValue == true) {
				self.animatShowBck(newValue);
				window.setTimeout(function() {
					self.animatShowDialog(newValue);
				}, 150);
			} else {
				window.setTimeout(function() {
					self.animatShowBck(newValue);
				}, 310);
				window.setTimeout(function() {
					self.animatShowDialog(newValue);
				}, 10);
			}
		}
		
		/**
		 * 以 AJAX 方式加载 url。
		 */
		function _loadInAjax(p_url) {
			$.ajax({
				url: p_url,
				crossDomain: cube.crosDomain(p_url),
				success: function(response,status,xhr){
					var ajaxHtml = response;
					self.ajaxHtml(ajaxHtml);
				}
			});
		}
		
		/**
		 * @ignore
		 */
		self._tabindexKeydown = function(event) {
			var keyCode = event.which || event.keyCode;
			var element = $(document.activeElement);
			if (keyCode == 27) {
				self.closeDialog();
			} if (keyCode == 13 && (!element.is("input") && !element.is("textarea"))) {
				self.confirmDialog();
			}
		}
		
		/**
		 * @ignore
		 */
		self.onload = function(node, viewModel) {
			self.$e = $(node);
			
			self.$e.children(".modal").attr("tabindex", 1).keydown(self._tabindexKeydown)
			self.$e.children(".modal-backdrop").attr("tabindex", 1).keydown(self._tabindexKeydown)
			self.$e.children(".modal").focus();
			
			var doc = document;
			if (window.name && window.name.indexOf("iframe_") == 0) {
				try {
					if (top.document && top.cube) {
						doc = top.document;
					}					
				} catch (e) {
				}
			}
			
			var docHeight = doc.documentElement.clientHeight;			
			var modalHeader = self.$e.find('.modal-header').outerHeight();
			var modalFooter = self.$e.find('.modal-footer').outerHeight();
			var modalBody = self.$e.find('.modal-body');
			var modalBodyPadding = self.$e.find('.modal-body').outerHeight();
			var blankHeight = parseInt(docHeight-modalHeader - modalFooter - self._footerHeight())/2+'px';
			var rationalHeight = 0;
			
			if(!self.isShowCloseBtn() && !self.isShowConfirmBtn() && self.customBtns() == [] && self.footerHeight() == 'auto'){
				self._footerHeight(modalFooter);
			} else {
				self._footerHeight(0);
			}
			
			//top为空
			if(cube.isEmpty(self.top())){
				//height为空
				if(cube.isEmpty(self.height())){	
					if(self.isWidthHeightAuto()){
						self.height('auto');
					}else{
						self.height('480px');
					}
					//bottom为空
					if(cube.isEmpty(self.bottom())){
						$(window).resize(function(){ 
							var thisBody  = $(this);
							var numb = "a";
							self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);
						});
						modalBody.resize(function(){
							var thisBody  = $(this);
							var numb = "a";
							self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);
						});
					//bottom不为空
					} else {
						if(parseInt(self.bottom().replace("px",""))  > docHeight){
							self.bottom(blankHeight);   
							self.top(blankHeight);
						} else {
							if(parseInt(self.bottom().replace("px",""))+parseInt(self.height().replace("px",""))>docHeight){
								self.bottom("auto");
							}
						}
						$(window).resize(function(){
							var thisBody  = $(this);
							var numb = "b";
							self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);						 
						});
						modalBody.resize(function(){
							var thisBody  = $(this);
							var numb = "b";
							self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);							 
						});		
					}	
				//height不为空
				} else {
					//bottom为空
					if(cube.isEmpty(self.bottom())){
						self.top(parseInt(docHeight - parseInt(self.height().replace("px","")))/2  + "px");
						self.bottom(parseInt(docHeight - parseInt(self.top().replace("px","")) - parseInt(self.height().replace("px",""))) + "px");						
					//bottom不为空
					} else {
						if(parseInt(self.bottom().replace("px",""))  > docHeight){
							self.bottom(blankHeight); 
							self.top(blankHeight);
						}  else {
							if(parseInt(self.bottom().replace("px",""))+parseInt(self.height().replace("px",""))>docHeight){
								self.bottom("auto");
							}
						}
						self.top(parseInt(docHeight - parseInt(self.bottom().replace("px","")) - parseInt(self.height().replace("px",""))) + "px");	
					}
				}
				
				if(cube.isEmpty(self.width())){
					if(self.isWidthHeightAuto()){
						self.width('auto');
					} else {
						self.width('640px');
					}								
				} else {
					self.width(self.width());
				}				
			//top不为空
			} else {
				   
				if(parseInt(self.top().replace("px",""))  > docHeight){
					self.top(blankHeight);	
				}
				//bottom为空
				if(cube.isEmpty(self.bottom())){
					//height为空
					if(cube.isEmpty(self.height())){
						if(self.isWidthHeightAuto()){
							self.height('auto');
						}else{
							self.height('480px');
						}
						$(window).resize(function(){
							var thisBody  = $(this);
							var numb = "c";
							self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);
						});
						modalBody.resize(function(){
							var thisBody  = $(this);
							var numb = "c";
							self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);
						});
					//height不为空	
					} else {
						self.height(self.height());
						self.top(self.top());
						self.bottom(parseInt(docHeight) - parseInt(self.top().replace("px","")) - parseInt(self.height().replace("px","")) + "px");
					}
				//bottom不为空	
				} else {					
					//height为空
					if(cube.isEmpty(self.height())){						
							self.height(  docHeight - parseInt(self.top().replace("px","")) - parseInt(self.bottom().replace("px","")));						
							$(window).resize(function(){
								var thisBody  = $(this);
								var numb = "d";
								self._resize(numb,thisBody,modalHeader,modalFooter,modalBodyPadding);
							});
					//height不为空
					} else {
						self.height(self.height());					
						self.bottom(self.bottom());	
						self.top(self.top());
					}
					
					if(parseInt(self.top().replace("px","")) + parseInt(self.bottom().replace("px","")) > docHeight){
						self.bodyHeight('0px');
						self.top(blankHeight);
						self.bottom(blankHeight);
					}
				}						
				if(cube.isEmpty(self.width())){
					if(self.isWidthHeightAuto()){
						self.width('auto');
					} else {
						self.width('640px');
					}								
				} else {
					self.width(self.width());
				}				
			}			
								
			var bodyHeight = parseInt(self.height())- modalHeader - modalFooter - self._footerHeight() - modalBodyPadding;

			if(!isNaN(bodyHeight)){
				if(bodyHeight<0  && !self.isWidthHeightAuto()){
					self.bodyHeight('0px');
				} else {
					self.bodyHeight(bodyHeight+'px');
				}
			}
			
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}			
		}			
		cube.endViewModel(self, params);
	}

	// 释放资源，包括compoted/subscrib/setInterval资源等。
	ViewModel.prototype.dispose = function() {
		this.isShowDialogSub.dispose();
	}
	return ViewModel;
});