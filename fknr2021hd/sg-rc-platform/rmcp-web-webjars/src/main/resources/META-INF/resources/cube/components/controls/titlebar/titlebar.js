define([], function() {

	/**
	 * 标题组件
	 * <code language="html">
	 * 		<titlebar params="
     *      					width: width,
     *      					height: height,
     *      					bgcolor: bgcolor,
     *							icon: icon,
     *							type: type, 
     *							marginBottom :marginBottom,   
     *      					visible: visible,
     *      					title: title,
     *      					customBtns: customBtns
	 *		"></titlebar>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 				
	 * 				self.width = "auto";
	 *  			self.height = "40px";
	 *    			self.bgcolor = "#fff";
	 *    			self.icon = "map-marker";
	 *    			self.type = "default";
	 *    			self.marginBottom = '2px';
	 *     			self.visible = true;
	 *     			self.title = "标题内容";
	 * 				self.customBtns = [{
	 * 						text : "按钮1",
	 * 						visible: true
	 * 					},{//标题栏自定义按钮
	 * 						text : "按钮2",
	 *				 		visible: true, //不设置时默认为true
	 *			 			btnColor:"#fff",//文字颜色
	 *						btnWidth:'80px',//按钮宽度
	 *						btnHeight: '30px',//按钮高度
	 *				 		backgroundImage:'none',//背景图片
	 *				 		backgroundColor:'#aaa',//背景颜色
	 *				 		btnIcon: 'map-marker',//图标
	 *				 		click: function() {//点击事件
	 *				           //点击按钮时触发
	 *				       	}
	 *				 	}];
	 * 				cube.endViewModel(self, params);
	 * 			};
	 * 			return PageViewModel;
	 * 		});
	 * </code>
	 */
    function ViewModel(params) {
        var self = this;
        
        /**
         * 标题宽度
         * @default
         * 		400px
         */
        self.width = "400px";
        
         /**
         * 标题高度
         * @default
         * 		40px
         */
        self.height = "40px";
        
        /**
         * 标题背景色
         * @default
         * 		#fff
         */
        self.bgcolor = "#fff";
        
        /**
         * 标题图标
         * @default
         * 		null
         */
        self.icon = null;
        
		/**
		 * 标题样式风格，可选：default, tab ,box
		 * 
		 * @default
		 * 		default
		 */
		self.type = "default";
        
        /**
         * 标题底边距
         * @default
         * 		2px
         */
        self.marginBottom = '2px';
        
         /**
         * 是否显示标题内容
         * @default
         * 		true
         */
        self.visible = true;
        
         /**
         * 标题内容
         * @default
         * 		空
         */
        self.title = "";
        
		/**
		 * 自定义按钮
		 * [{
		 *		text : "收藏",
		 *      visible: true, //不设置时默认为true
		 *      type: "primary", //按钮样式，可选的值包括：default、primary、success、info、warning、danger、inverse、link
		 *		click: function(dialog) {
		 *          //点击按钮时触发
		 *          dialog.closeDialog();
		 *      }
		 *	}, {
		 *		text : "查看",
		 *		click: function() {
		 *          //点击按钮时触发
		 *      }
		 *	}]
		 *
		 * @default []
		 */
		self.customBtns = [];
		
		/**
		 * @ignore
		 */
		self.customClick = function(viewMode, event) {
			if (cube.notEmpty(viewMode.click) && typeof(viewMode.click) == "function") {
				viewMode.click(self);
			}
		};

        cube.endViewModel (self,params);
    }
    
    //释放资源，包括compoted/subscrib/setInterval资源等。
    ViewModel.prototype.dispose = function () {
    	
    };
    return ViewModel;
});


