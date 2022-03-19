define([], function() {

	/**
	 * 面包屑导航
	 * splitTxt属性可以配置分隔符。
	 * @example
	 * <code language="html">
	 * 		<breadcrumb params="
	 *						items: items,
	 *						selectedRoute: selectedRoute,
	 *						splitTxt: splitTxt,
	 *						isHrefRoute: isHrefRoute"></breadcrumb>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.items = [
	 *	   							{
	 *		   							text : '导航1',
	 *		   							route : '#one'
	 *	   							},
	 *	  						 	{
	 *		   							text : '导航1.1',
	 *		   							route : '#one-one'
	 *	   							},
	 *	  	 						{
	 *		   							text : '导航1.1.1',
	 *		   							route : '#one-one-one'
	 *	   							}
	 *							];
	 *  			self.selectedRoute = '#linelossmenu/province_statistic';
	 *   			self.splitTxt = '/';
	 *     			self.isHrefRoute = true;
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
		 * 导航条内容
		 * @example
		 * <code language="JavaScript">
		 * [
		 *	   {
		 *		   text : '导航1',
		 *		   route : '#one'
		 *	   },
		 *	   {
		 *		   text : '导航1.1',
		 *		   route : '#one-one'
		 *	   },
		 *	   {
		 *		   text : '导航1.1.1',
		 *		   route : '#one-one-one'
		 *	   }
		 *	]
		 * </code>
		 * 
		 * @default
		 * 		[]
		 */
		self.items = [];
		
		/**
		 * 当前选中的导航条
		 * @default
		 * 		null
		 */
		self.selectedRoute = null;
		
		/**
		 * 导航之间的分隔符
		 * @default
		 * 		"/"
		 */
		self.splitTxt = "/";
		
		/**
		 * 导航头部图标
		 * @default
		 * 		null
		 */
		self.icon = null;
		
		/**
		 * 是否设置锚定值
		 */
		self.isHrefRoute = true;
		
		/**
		 * 选中变化处理事件。
		 */
		self.onSelectedChanged = null;
		
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
			self.selectedRouteSub = cube.subscribe(self.selectedRoute, function(newValue) {
				//获得节点index
				var isDel=false;
				var delItems = [];
				$.each(self.items(),function() {
					if(newValue== this.route) {
						isDel = true;
					}else if(isDel == true){
						delItems.push(this);
					}
				});
				self.items.removeAll(delItems);
				
				if(self.onSelectedChanged!=null && !cube.isObservable(self.onSelectedChanged)) {
					self.onSelectedChanged(newValue);
				}
				if(self.isHrefRoute()) {
					window.location.hash = newValue;
				}
			});
		
		};
		
		/**
		 * 设置选中导航项
		 * @ignore
		 */
		self.setSelectRoute = function() {
			self.selectedRoute(this.route);
		};
		
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
 	
 	ViewModel.prototype.dispose = function(){
 		this.selectedRouteSub.dispose();
 	};
 	
	return ViewModel;
});