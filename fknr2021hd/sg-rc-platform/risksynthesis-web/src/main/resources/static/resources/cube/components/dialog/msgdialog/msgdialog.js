define([], function() {

	/**
	 * 消息对话框
	 * <code language="html">
	 * 		<msgdialog params="
	 *					msgType: msgType,
	 *					isShowDialog: isShowDialog,
	 *					title: title,
	 *					templateOptions: templateOptions,
	 *					content: content"></msgdialog>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.msgType = 'info';	
	 * 				self.isShowDialog = false;
	 * 				self.title = '消息对话框组件';
	 * 				self.templateOptions = {
	 *			 							name: 'app_cubedemo.componentDemos.dialogcontainer.Templ',
	 *		  	 							params: {title: '页面模板'}
	 *	  									};
	 * 				self.content = '内容'; //当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
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
		 * 标题
		 * @default 空
		 */
		self.title = "";
		
		/**
		 * 内容，当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 * @default 空
		 */
		self.content = "";
		
		/**
		 * 要加载的页面，当content属性存在时优先显示其内容，否则显示templateOptions配置的页面
		 * 
		 * <code language="JavaScript">
		 *	  {
		 *			 name: 'app_cubedemo.componentDemos.dialogcontainer.Templ',
		 *		  	 params: {title: '页面模板'}
		 *	  }
		 *  </code>
		 *  
		 * name为页面路径，params为要传递给该页面对应ViewModel的参数
		 */
		self.templateOptions = {};
		
		/**
		 * 是否显示对话框
		 * @default false
		 */
		self.isShowDialog = false;
		
		/**
		 * 对话框显示时间，当为0时，鼠标离开对话框时消失，否则将在showTime设置时间后自动消失。
		 * @default 0 单位毫秒
		 */
		self.showTime = 0;
		
		/**
		 * 消息类型，info,warning,error,confirm。
		 * @default info
		 */
		self.msgType = "info";
		
		/**
		 * 窗口堆叠顺序
		 * 
		 * @default 1070
		 */
		self.zIndex = 1070;
		
		/**
		 * 是否显示取消按钮
		 * 
		 * @default true
		 */
		self.isShowCancelBtn = true;
		
		/**
		 * 是否翻转取消按钮和确定按钮的位置，当msgType属性值为confirm时生效。
		 * 
		 * @default false
		 */
		self.reverseBtn = false;
		
		/**
		 * 内部属性，动画显示
		 * @ignore
		 */
		self.animatShowBck = false;
		
		/**
		 * 内部属性，动画显示
		 * @ignore
		 */
		self.animatShowDialog = false;
		
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
		 * 当时使用indicate方法创建窗口时，会传入eleId（窗口节点的id）
		 * 
		 * @ignore
		 */
		self.eleId = null;
		
		/**
		 * 确定按钮点击事件
		 */
		self.onConfirmBtnClick = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		var node = null;
		var boxWidth = null;
		var boxHeight = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function(){
			self.isdrag = false;
			//关闭或打开对话框动画特效
			animat(self.isShowDialog());
			self.isShowDialogSub = cube.subscribe(self.isShowDialog, animat);
			
			var _showTime = self.showTime();
			if (_showTime > 0 && self.msgType() != "confirm") {
				window.setTimeout(function(){
					self.disableMsgDlg();
				}, _showTime);
			}
		};
		
		/**
		 * @ignore
		 */
		self.confirmDialog = function() {
			var args = {cancel: false};
			var result = true;
			if (self.onConfirmBtnClick != null && !cube.isObservable(self.onConfirmBtnClick)) {
				result = self.onConfirmBtnClick(args);
			}
			
			if (!args.cancel && (cube.isEmpty(result) || result)) {
				self.disableMsgDlg();
			}
		};
		
		/**
		 * @ignore
		 */
		self.headmousedown = function(viewModel, event){
			
			boxHeight = document.documentElement.clientHeight - self.$e.find('.modal').height();
			boxWidth = document.documentElement.clientWidth - self.$e.find('.modal').width();
			
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
		};
		
		/**
		 * @ignore
		 */
		self.headmousemove = function(event){
			if (self.isdrag){
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
		
		document.onmouseup = function(){
			self.isdrag = false;
		};
		
		/**
		 * @ignore
		 */
		self.mouseout = function($element,e){
			if (self.msgType() != "confirm") {
				var reltg = e.relatedTarget ? e.relatedTarget : e.toElement;  
				while( reltg && reltg != $element ) {
					reltg = reltg.parentNode;
				}
				
				if( reltg != $element ) {
					self.disableMsgDlg();
				}
			}
		};
		
		/**
		 * @ignore
		 */
		self.disableMsgDlg = function(){  
			if (self.isdrag)
				return;
			
			var animatTime = self.msgType() != "confirm" ? 150 : 0;
			
			// 这里可以编写 onmouseleave 事件的处理代码  
			window.setTimeout(function(){
				self.animatShowDialog(false);
				self.isShowDialog(false);
				
				window.setTimeout(function() {
					self.animatShowBck(false);
					
					//如果通过cube.indicate或cube.showError方法弹框，则关闭时删除节点
					if (self.eleId() != null) {
						$("#"+self.eleId()).remove();
					}
					
				},350);
			}, animatTime);
		
		};
		
		function animat(newValue){
			var animatTime = self.msgType() != "confirm" ? 150 : 0;
			if(newValue == true) {
				self.animatShowBck(newValue);
				window.setTimeout(function(){
					self.animatShowDialog(newValue);
				}, animatTime);
			}
		}
		
		/**
		* @ignore
		*/
		self.onload = function(node) {
			self.$e = $(node);
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		};

		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib/setInterval资源等。
	ViewModel.prototype.dispose = function() {
		this.isShowDialogSub.dispose();
	};
	return ViewModel;
});