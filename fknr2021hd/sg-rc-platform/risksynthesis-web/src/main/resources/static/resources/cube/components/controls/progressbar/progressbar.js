define([], function() {

	/**
	 * 进度条组件
	 * @example
	 * <code language="html">
	 * 		<progressbar params="
	 *				style: style,
	 *				progressValue: progressValue,
	 *				warningValue: warningValue,
	 *				dangerValue: dangerValue"></progressbar>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.style = 'border: 1px solid red;';
	 * 				self.progressValue = 0.7;
	 * 				self.warningValue = 0.3;
	 * 				self.dangerValue = 1;
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
		 * 进度条样式
		 * @default
		 * 		空
		 */
		self.style = "";
		
		/**
		 * 当前进度值
		 * @default
		 * 		0
		 */
		self.progressValue = 0;
		
		/**
		 * 超过warningValue，进度处于警告
		 * @default
		 * 		1
		 */
		self.warningValue = 1;
		
		/**
		 * 超过dangerValue，进度处于危险
		 * @default
		 * 		1
		 */
		self.dangerValue =  1;
		
		/**
		 * 进度条值变化回调事件
		 */
		self.onProgressChanged = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			//调用外部的selectedChange事件
			cube.subscribe(self.progressValue, function(newValue) {
				if (self.onProgressChanged != null && !cube.isObservable(self.onProgressChanged)) {
					self.onProgressChanged(newValue);
				}
			});
		};
		
		/**
		 * 进度条的进度状态数组，内部属性
		 * @ignore
		 */
		self.progressItems = cube.comp(function(){
			var items = [];
			if(self.progressValue()< self.warningValue()) {
				items.push(self.progressValue());
				items.push(0);
				items.push(0);
				return items;
			}
			else if(self.progressValue()> self.dangerValue()) {
				items.push(self.warningValue());
				items.push(self.dangerValue()-self.warningValue());
				items.push(self.progressValue()-self.dangerValue());
				return items;
			}
			else {
				items.push(self.warningValue());
				items.push(self.progressValue()-self.warningValue());
				items.push(0);
				return items;
			}
		},self);
		
		/**
	     * @ignore
	     */
		self.onload = function(node) {
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		};
		
		cube.endViewModel(self, params);
	}
	
	return ViewModel;
});