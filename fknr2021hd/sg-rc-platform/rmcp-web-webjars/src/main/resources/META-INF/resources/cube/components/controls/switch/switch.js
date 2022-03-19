define(["CheckUtil"], function(checkUtil) {
	
	/**
	 * 开关组件
	 * type属性控制按钮样式类型，onClick控制点击事件。
	 * @example
	 * <code language="html">
	 * 		<switch params="
	 *						width: width,
	 *						height: height,
	 *						border: border,
	 *						bgcolor: bgcolor,
	 *						trueText: trueText,
	 *						falseText: falseText,
	 *						value: value,
	 *						disabled: disabled,
	 *						onClick:click"></switch>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.width = '48px';
	 *  			self.height = '24px';
	 *   			self.border = '1px solid #ddd';
	 *    			self.bgcolor = '#eee';
	 *     			self.trueText = '开';
	 *     			self.falseText = '关';
	 *     			self.value = true;
	 *       		self.disabled = false;
	 *        		self.click = function(p_value){
	 *        			alert("开关状态："+ p_value);
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
		 * 宽度
		 * @default	48px
		 */
		self.width = '48px';
		
		/**
		 * 高度
		 * @default 24px
		 */
		self.height = '24px';
		
		/**
		 * 边框
		 * @default 空
		 */
		self.border = "";
		
		/**
		 * 选中时背景颜色
		 * @default 空
		 */
		self.bgcolor = "";
		
		/**
		 * 选中时文本内容
		 * @default 空
		 */
		self.trueText = "";
		
		/**
		 * 未选中时文本内容
		 * @default 空
		 */
		self.falseText = "";
		
		/**
		 * 指定当前是否选中
		 * @default false
		 */
		self.value = false;
		
		/**
		 * 是否禁用按钮
		 * @default
		 * 		false
		 */
		self.disabled = false;
		
		/**
		 * @ignore
		 */
		self._width = "";
		
		/**
		 * @ignore
		 */
		self._type = "";
		
		/**
		 * @ignore
		 */
		self._height = "";
		
		/**
		 * @ignore
		 */
		self._sliph = "";
		
		/**
		 * @ignore
		 */
		self._textleft = "";
		
		/**
		 * @ignore
		 */
		self._bgcolor = "";
		
		/**
		 * @ignore
		 */
		self._slipbg = "";
		
		/**
		 * @ignore
		 */
		self._value = false;
		
		/**
		 * @ignore
		 */
		self._text = "";
		
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
				self._sliph((self.height() - 4 ) + "px");
				self._textleft((self.height()) + 4  + "px");
			} else {
				self._height(self.height());
				self._sliph(parseInt((self.height()).replace("px","") - 4)  + "px");
				self._textleft(parseInt((self.height()).replace("px","")) + 4  + "px");
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
			
			self._value(self.value());	
			if(self.value()){
				self.value = false;
				self._text(self.trueText());
				self._bgcolor(self.bgcolor());
				self._type("switch-checked");
				if (checkUtil.isDigit(self.height())) {
					self._textleft(parseInt((self.height())/3)  + "px");
				} else {
					self._textleft(parseInt((self.height()).replace("px","")/3) + "px");
				}
			} else {
				self.value = true;
				self._text(self.falseText());
				self._bgcolor("#ccc");
				self._type("");
				if (checkUtil.isDigit(self.height())) {
					self._textleft(parseInt((self.height()) * 7 /6)  + "px");
				} else {
					self._textleft(parseInt(parseInt((self.height()).replace("px","")) * 7 /6)  + "px");
				}			
			}
			if(self.disabled()){
				self._bgcolor("#f3f3f3");
				self._slipbg("#ccc");
				self._text("");
			}
		};
		
		/**
		 * @ignore
		 */
		self._onclick = function(){
			if(!self.disabled() && self.onClick!=null && !cube.isObservable(self.onClick)){
				self.onClick(self.value);
			
				if(self.value){
					self.value = false;		
					self._text(self.trueText());
					self._bgcolor(self.bgcolor());
					self._type("switch-checked");
					self._value(!self.value);
					if (checkUtil.isDigit(self.height())) {
						self._textleft(parseInt((self.height())/3)  + "px");
					} else {
						self._textleft(parseInt((self.height()).replace("px","")/3) + "px");
					}
				} else {
					self.value = true;
					self._text(self.falseText());
					self._bgcolor("#ccc");
					self._type("");
					self._value(!self.value);
					if (checkUtil.isDigit(self.height())) {
						self._textleft(parseInt((self.height()) * 7 /6)  + "px");
					} else {
						self._textleft(parseInt(parseInt((self.height()).replace("px","")) * 7 /6)  + "px");
					}		
				}
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