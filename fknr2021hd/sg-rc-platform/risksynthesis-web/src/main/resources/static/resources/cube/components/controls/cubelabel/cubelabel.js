define(["CheckUtil"], function(checkUtil) {
	
	/**
	 * 标签组件
	 * @example
	 * <code language="html">
	 * 		<cubelabel params="
	 *					width: width,
	 *					height: height,
	 *					bgcolor: bgcolor,
	 *					textAlign: textAlign,
	 *					border: border,
	 *					fontSize: fontSize;
	 *					fontWeight: fontWeight
	 *					text: text"></cubelabel>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.width = '200px';
	 * 				self.height = '200px';
	 * 				self.bgcolor = 'red';
	 * 				self.textAlign = 'center';
	 * 				self.border = '1px solid #eee';
	 * 				self.text = 'label';
	 * 				self.fontSize = '14px';
	 * 				self.fontWeight = '900';
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
		 * 宽度
		 * @default 120
		 */
		self.width = "100px";
		
		/**
		 * 高度
		 * @default 30
		 */
		self.height = "30px";
		
		/**
		 * 左右对齐方式
		 * @default center
		 */
		self.textAlign = "center";
		
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
		 * 字体大小
		 * @default 14px
		 */
		self.fontSize = "14px";
		
		/**
		 * 字体加粗
		 * @default normal
		 */
		self.fontWeight = "normal";
		
		/**
		 * 文本内容
		 * @default 空
		 */
		self.text = "";
		
		/**
		 * @ignore
		 */
		self._width = "";
		
		/**
		 * @ignore
		 */
		self._height = "";
		
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