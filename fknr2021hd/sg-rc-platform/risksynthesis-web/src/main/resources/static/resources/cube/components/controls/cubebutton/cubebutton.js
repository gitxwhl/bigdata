define(["CheckUtil"], function(checkUtil) {
	
	/**
	 * 按钮组件
	 * type属性控制按钮样式类型，onClick控制点击事件。
	 * @example
	 * <code language="html">
	 * 		<cubebutton params="
	 *						width: width,
	 *						height: height,
	 *						border: border,
	 *						bgcolor: bgcolor,
	 *						text: text,
	 *						type: type,
	 *						disabled: disabled,
	 *						onClick:click"></cubebutton>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.width = '300px';
	 *  			self.height = '300px';
	 *   			self.border = '1px solid #ddd';
	 *    			self.bgcolor = '#eee';
	 *     			self.text = 'button';
	 *      		self.type = 'default';
	 *       		self.disabled = false;
	 *        		self.click = function(){
	 *        			alert("事件处理");
	 *        		};
	 * 								
	 *				cube.endViewModel(self, params);
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
		 * 宽度
		 * @default	空
		 */
		self.width = '';
		
		/**
		 * 高度
		 * @default 空
		 */
		self.height = '';
		
		/**
		 * 背景颜色
		 * @default 空
		 */
		self.bgcolor = "";
		
		/**
		 * 边框
		 * @default 空
		 */
		self.border = "";
		
		/**
		 * 文本内容
		 * @default 空
		 */
		self.text = "";
		
		/**
		 * 按钮样式类型
		 * 
		 * <p>
	     * 该字段可选的值包括：
	     * <ul>
	     * <li><b>default</b></li>
	     * <li><b>primary</b></li>
	     * <li><b>success</b></li>
	     * <li><b>info</b></li>
	     * <li><b>warning</b></li>
	     * <li><b>danger</b></li>
	     * <li><b>inverse</b></li>
	     * <li><b>link</b></li>
	     * </ul>
	     * </p>
	     * 
		 * @default	
		 * 		default
		 */
		self.type = "default";
		
		/**
		 * 是否禁用按钮
		 * @default
		 * 		false
		 */
		self.disabled = false;
		
		/**
		 * 是否显示按钮
		 * @default
		 * 		true
		 */
		self.visible = true;
		
		/**
		 * @ignore
		 */
		self._width = "";
		
		/**
		 * @ignore
		 */
		self._height = "";
		
		/**
		 * 点击事件
		 */
		self.onClick = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * @ignore
		 */
		self._init = function() {
			if (checkUtil.isDigit(self.width())) {
				self._width(self.width() + "px");
			} else {
				self._width(self.width());
			}
			
			if (checkUtil.isDigit(self.height())) {
				self._height(self.height() + "px");
			} else {
				self._height(self.height());
			}
			
			self.widthSub = cube.subscribe(self.width, function(p_width) {
				if (checkUtil.isDigit(p_width)) {
					self._width(p_width + "px");
				} else {
					self._width(p_width);
				}
			});
			
			self.heightSub = cube.subscribe(self.height, function(p_height) {
				if (checkUtil.isDigit(p_height)) {
					self._height(p_height + "px");
				} else {
					self._height(p_height);
				}
			});
		};
		
		/**
		 * @ignore
		 */
		self._onclick = function(){
			if(!self.disabled() && self.onClick!=null && !cube.isObservable(self.onClick)){
				self.onClick();
			}
		};
		
		/**
	     * @ignore
	     */
		self.onload = function(node) {
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		};
		
		cube.endViewModel(self,params);
	}
	
	ViewModel.prototype.dispose = function() {
		this.widthSub.dispose();
		this.heightSub.dispose();
	};
	
	return ViewModel;
});