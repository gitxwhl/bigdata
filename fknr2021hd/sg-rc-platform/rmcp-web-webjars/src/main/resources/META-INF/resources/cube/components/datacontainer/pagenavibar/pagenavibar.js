define(["CheckUtil"], function(checkUtil) {

	/**
	 * 分页组件
	 * @example
	 * <code language="html">
	 * 		<pagenavibar params="
	 *					size: size,
	 *					showArrow: showArrow,
	 *					showTotal: showTotal,
	 *					showGoto: showGoto,
	 *					showAllPage: showAllPage,
	 *					pageTotalCount: pageTotalCount,
	 *					pageIndex: pageIndex,
	 *					pageVisibleCount: pageVisibleCount,
	 *					onPageIndexChanged: onPageIndexChanged"></pagenavibar>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.size = 'middle';
	 * 				self.showArrow = true;
	 * 				self.showTotal = true;
	 * 				self.showGoto = true;
	 * 				self.showAllPage = false;
	 * 				self.pageTotalCount = 15;	
	 * 				self.pageIndex = 1;
	 * 				self.pageVisibleCount = 5;			
	 * 				self.onPageIndexChanged = function(p_pageIndex){
	 *							if(typeof(console) != "undefined" && typeof(console.log) != "undefined")
	 *								console.log("当前选中："+p_pageIndex);
	 *						}
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
		 * 分页样式大小，可选：large、middle、small、mini
		 * 		@default middle
		 */
		self.size = "middle";

		/**
		 * @ignore
		 */
		self.p_size = cube.comp(function(){
				var sizeClass;
				if (self.size() == "large")
					sizeClass = "pagination-large page-large";
				else if (self.size() == "middle")
					sizeClass = "page-middle";
				else if (self.size() == "small")
					sizeClass = "pagination-small page-small";
				else if (self.size() == "mini")
					sizeClass = "pagination-mini page-mini";
				return sizeClass;
			},self);

		/**
		 * 显示翻页按钮
		 * @default
		 * 		true
		 */
		self.showArrow = true;
		
		/**
		 * 是否显示翻页按钮文字表述，（当设置翻页按钮图标时，不再显示文字表述）。
		 * @default
		 * 		false
		 */
		self.isShowBtnText = false;
		
		/**
		 * 显示总页数
		 * @default
		 * 		true
		 */
		self.showTotal = true;

		/**
		 * 显示到某一页输入框
		 * @default
		 * 		true
		 */
		self.showGoto = true;
		
		/**
		 * 显示全部页数
		 * @default
		 * 		false
		 */
		self.showAllPage = false;

		/**
		 * 总页数
		 * @default
		 * 		0
		 */
		self.pageTotalCount = 0;
		
		/**
		 * 总记录数
		 * @default
		 * 		0
		 */
		self.count = 0;

		/**
		 * 当前页数
		 * @default
		 * 		1
		 */
		self.pageIndex = 1;

		/**
		 * 可见页数
		 * @default
		 * 		5
		 */
		self.pageVisibleCount = 5;
		
		/**
		 * 分页信息居左（总页数、总记录数）
		 * 
		 *  @default false
		 */
		self.pageInfoToLeft = false;
		
		/**
		 * 是否失去焦点时跳转页面，默认输入即跳转
		 * @default
		 * 		false
		 */
		self.blurGoto = false;
		
		/**
		 * 是否在分页条显示数据加载时间
		 * @default false
		 */
		self.isShowLoadTime = false;
		
		/**
		 * 加载时间是否自动淡出，一个数字（毫秒），当值为0时不淡出，否则按设置时间淡出
		 * @default 0
		 * 
		 */
		self.loadTimeFadeout = 0;
		
		/**
		 * 设置“首页”按钮图标（参考Font Awesome 图标库）， 例如：“icon-step-backward”。
		 * @default 空
		 */
		self.firstIcon = "";
		
		/**
		 * 设置“上一页”按钮图标（参考Font Awesome 图标库）， 例如：“icon-caret-main”。
		 * @default 空
		 */
		self.previousIcon = "";
		
		/**
		 * 设置“下一页”按钮图标（参考Font Awesome 图标库），例如：“icon-caret-right”。
		 * @default  空
		 */
		self.nextIcon = "";
		
		/**
		 * 设置“末页”按钮图标（参考Font Awesome 图标库）， 例如：“icon-step-forward”。
		 * @default 空
		 */
		self.endIcon = "";
		
		/**
		 * @ignore
		 */
		self.loadTime = 0;
		
		/**
		 * @ignore
		 */
		self.pageNumbers = cube.comp(function(){
				if (self.showAllPage())
					return getVisiblePageIndexes(self.pageTotalCount(), self.pageTotalCount(), 
						self.pageIndex());
				else
					return getVisiblePageIndexes(self.pageTotalCount(), self.pageVisibleCount(), 
						self.pageIndex());
			},self);

		/**
		 * 选中变化处理事件。
		 */
		self.onPageIndexChanged = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * @ignore
		 */
		self.oldPageIndex = 1;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			//调用外部的当前页码变化事件
			self.pageIndexSub = cube.subscribe(self.pageIndex, function(p_pageIndex) {
				if (cube.isEmpty(p_pageIndex)) {
					return;
				}
				if (!checkUtil.isPlusInteger(p_pageIndex)) {
					self.pageIndex(self.oldPageIndex());
					p_pageIndex = self.oldPageIndex();
					return;
				}
				
				if (p_pageIndex <= 0) {
					self.pageIndex(1);
					p_pageIndex = 1;
				}
				
				if (p_pageIndex > self.pageTotalCount()) {
					p_pageIndex = parseInt(self.pageTotalCount());
					if (p_pageIndex == 0) {
						p_pageIndex = 1;
					}
					
					self.pageIndex(p_pageIndex);
				}
				
				if (self.oldPageIndex() != p_pageIndex && self.onPageIndexChanged != null && !cube.isObservable(self.onPageIndexChanged)) {
					self.oldPageIndex(p_pageIndex);
					self.onPageIndexChanged(parseInt(p_pageIndex));
				}
			});
		}

		/**
		 * 跳转到首页
		 */
		self.onFirstPageClick = function() {
			if (!isNormalize())
				return;
			if (self.pageIndex() == 1)
				return;
			self.pageIndex(1);
		}

		/**
		 * 跳转到前一页
		 */
		self.onPagePreviousClick = function() {
			if (!isNormalize())
				return;
			if (self.pageIndex() == 1)
				return;
			self.pageIndex(self.pageIndex() - 1);
		}

		/**
		 * 跳转到后一页
		 */
		self.onPageNextClick = function() {
			if (!isNormalize())
				return;
			if (self.pageIndex() == self.pageTotalCount())
				return;
			self.pageIndex(parseInt(self.pageIndex()) + 1);
		}

		/**
		 * 跳转到尾页
		 */
		self.onEndPageClick = function() {
			if (!isNormalize())
				return;
			if (self.pageIndex() == self.pageTotalCount())
				return;
			self.pageIndex(self.pageTotalCount());
		}
		
		/**
		 * 跳转到输入框中输入的页
		 */
		self.onPageIndexClick = function() {
			self.pageIndex(this.text);
		}
		
		/**
		 * 不接受非法字符，对很多输入法无法拦截
		 * @ignore
		 */
		self._input_keydown = function(viewModel, event) {
			var element = event.currentTarget ? event.currentTarget : event.srcElement;
			var value = element.value;
			if (cube.notEmpty(value) && !checkUtil.isPlusInteger(value)) {
				return false;
			}
			
			return true;
		}
		
		/**
		 * 不接受非法字符，对很多输入法无法拦截
		 * @ignore
		 */
		self._input_keypress = function(viewMode, event) {
			var pageIndex = self.pageIndex();
			
			if ((event.which >= 48 && event.which <= 57 && event.ctrlKey == false && event.shiftKey == false)
					|| event.which == 0 || event.which == 8) {
				return true;
			} else {
				if (event.ctrlKey == true && (event.which == 99 || event.which == 118)) {
					return true;
				} else {
					return false;
				}
			}
		};

		/**
		 * 输入框清空时自动赋值1
		 * @ignore
		 */
		self.iptNotNull = function() {
			var pageNull = document.querySelector('#iptNotNull')
			if(!pageNull.value) {
				pageNull.value = "1";
			}
		};

		//返回当前显示的数组
		function getVisiblePageIndexes(p_totalCount, p_visibleCount, p_currentIndex) {
			var totalCount = parseInt(p_totalCount);
			var visibleCount = parseInt(p_visibleCount);
			var currentIndex = parseInt(p_currentIndex);

			if (isNaN(currentIndex) ||  (currentIndex <= 0))
				currentIndex = 1;
			else if (currentIndex > totalCount)
				currentIndex = totalCount;

			var pageNumber = new Array();
			if (visibleCount >= totalCount) {
				for (var i = 1; i <= totalCount; i++) {
					pageNumber.push({"text":i});
				}
				return pageNumber;
			}

			var halfCount = parseInt(visibleCount / 2);
			var beginIndex = currentIndex - halfCount;
			var endIndex = beginIndex + visibleCount;

			var beginOffset = 0;
			var endOffset = 0;
			for (var i = beginIndex; i < endIndex; i++) {
				if (i <= 0) {
					beginOffset += 1;
				} else if (i > totalCount) {
					endOffset += 1;
				} else
					pageNumber.push({"text":i});
			}
			
			for (var i = 0; i < beginOffset; i++) {
				pageNumber.push({"text":(i + endIndex)});
			}
			
			if (endOffset > 0) {
				var endArray = new Array();
				for (var i = endOffset; i >= 1; i--) {
					endArray.push({"text":(beginIndex - i)});
				}
				$.merge(endArray, pageNumber);
				return endArray;
			}
			return pageNumber;
		}

		function isNormalize() {
			var currentIndex = parseInt(self.pageIndex());
			var totalCount = parseInt(self.pageTotalCount());

			if (isNaN(currentIndex) || isNaN(totalCount) ||  (currentIndex <= 0) || 
				(currentIndex > totalCount))
				return false;
			else return true;
		}
		
		
		
		/**
	     * @ignore
	     */
		self.onload = function(node) {
			var loadTimeFadeout = self.loadTimeFadeout();
			if (self.isShowLoadTime() && loadTimeFadeout > 0) {
				$(node).find("ul.loadtime").fadeOut(loadTimeFadeout);
			}
			
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		}
		
		cube.endViewModel(self, params);
	}
	
	//释放资源，包括compoted/subscrib资源等。
 	ViewModel.prototype.dispose = function() {
 	    this.p_size.dispose();
 	}
 	
	return ViewModel;
});