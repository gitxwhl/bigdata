define([], function() {

	/**
	 * 消息弹出框
	 * <code language="html">
	 * 		<popoverdialog params="
	 *						isShowDialog: isShowDialog,
	 *						title: title,
	 *						content: content,
	 *						msgType: msgType,
	 *						popoverDirection: popoverDirection"></popoverdialog>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.isShowDialog = false;
	 * 				self.title = '消息弹出框组件';
	 * 				self.content = '内容';
	 * 				self.msgType = 'info';
	 * 				self.popoverDirection = 'right';
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
		 * 是否显示对话框
		 * @default
		 * 		true
		 */
		self.isShowDialog = true
		
		/**
		 * 弹出方向，包括：top\right\main\bottom
		 * @default
		 * 		right
		 */
		self.popoverDirection = "right"
		
		/**
		 * 消息类型。包括：warning/info/error
		 * @default
		 * 		info
		 */
		self.msgType = "info";
		
		/**
		 * 消息标题
		 * @default
		 * 		空
		 */
		self.title = "";
		
		/**
		 * 消息内容
		 * @default
		 * 		空
		 */
		self.content = "";
		
		/**
		 * 窗口堆叠顺序，当使用cube.showPopDialog时，如果没有传入该属性值，该属性值会自动累加
		 * 
		 * @default 1060
		 */
		self.zIndex = 1060;
		
		/**
		 * 设置组件的left样式值，当使用cube.showPopDialog且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.left = null;
		
		/**
		 * @ignore
		 */
		self.arrowLeft = null;
		
		/**
		 * 设置组件的right样式值，当使用cube.showPopDialog且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.right = null;
		
		/**
		 * 设置组件的top样式值，当使用cube.showPopDialog且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.top = null;
		
		/**
		 * 设置组件的bottom样式值，当使用cube.showPopDialog且传入srcDom参数时，该属性值会被重写计算
		 * @default null
		 */
		self.bottom = null;
		
		/**
		 * 是否显示关闭按钮
		 * @default false
		 */
		self.isShowCloseBtn = false;
		
		/**
		 * @ignore
		 */
		self.eleWidth = null;
		
		/**
		 * 当时使用indicate方法创建窗口时，会传入eleId（窗口节点的id）
		 * 
		 * @ignore
		 */
		self.eleId = null;
		
		/**
		 * 标示是否在Fixed样式
		 * @ignore
		 */
		self.isFixed = false;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 初始化方法
		 * 
		 * @ignore
		 */
		self._init = function() {
			if (self.isFixed()) {
				if (self.top() == null) {
					self.top("auto");
				}
				
				if (self.left() == null) {
					self.left("auto");
				}
				
				if (self.right() == null) {
					self.right("auto");
				}
				
				if (self.bottom() == null) {
					self.bottom("auto");
				}
			}
			
			self.isShowDialogSub = cube.subscribe(self.isShowDialog, function(value){
				if (!value) {
					if (self.eleId() != null) {
						$("#"+self.eleId()).remove();
					}
				}
			});
		}
		
		/**
		 * 关闭消息框
		 * @ignore
		 */
		self.closeDialog = function() {
			self.isShowDialog(false);
			if (self.eleId() != null) {
				window.setTimeout(function() {
					$("#"+self.eleId()).remove();
				}, 310);
			}
		}

		/**
	     * @ignore
	     */
		self.onload = function(node) {
			var popover = $(node).children(".popover");
			if (self.isFixed()) {
				popover.css("position", "fixed");
			} else {
				if (self.popoverDirection() == "top") {
					var height = popover.height();
					var top = self.top();
					if(cube.notEmpty(top)) {
						self.top((parseInt(top) - height - 2) + "px");
					}
				}
				
				if (self.popoverDirection() == "top" || self.popoverDirection() == "bottom") {
					self.arrowLeft('50%');
					var width = popover.outerWidth();
					var left = self.left();
					var eleWidth = self.eleWidth();
					if (cube.notEmpty(left) && cube.notEmpty(eleWidth)) {
						popover.width(width);
						self.left((parseInt(left) - (width - eleWidth)/2) + "px");
						if(parseInt(left) - (width - eleWidth)/2 < 0){
							self.left('0px');
							self.arrowLeft((width/2 + parseInt(left) - (width - eleWidth)/2) + 'px');
						}
					}
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