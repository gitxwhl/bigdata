define([], function() {

	/**
	 * 轮播组件
	 * interval属性可以配置轮播时间间隔，showArrow属性配置是否显示左右箭头，autoPlay属性可以配置是否自动轮播。
	 * @example
	 * <code language="html">
	 * 		<carousel params="
	 *					style: style,
	 *					autoPlay: autoPlay,
	 *					showArrow: showArrow,
	 *					showCaption: showCaption,
	 *					interval: interval,
	 *					pageIndex: pageIndex,
	 *					imgItems: imgItems"></carousel>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.style = 'width:600px;height:300px;';
	 *  			self.autoPlay = true;
	 *   			self.showArrow = true;
	 *    			self.showCaption = false;
	 *     			self.interval = "5000";
	 *      		self.pageIndex = 'width:600px;height:300px;';
	 *       		self.imgItems = [
	 *       						  {
	 *       							num:1,
		 		    					imgSrc:"../../resources/images/carousel/slide1.png",
		 		    					title:"",
		 		    					content:"",
		 		    					innerhtml: "<button input='' class='btn' style='position:absolute;top:5px;main:5px;'>按钮</button>"		 		    					
		 		    				  },
		 		    				  {
		 		    					num:2,
		 		    					imgSrc:"../../resources/images/carousel/slide2.png",title:"title2",content:"内容",
		 		    					innerhtml:""
		 		    				  }
					 		    	];
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
		 * 唯一标示
		 * @default
		 * 		空
		 */
		self.id = "";
		
		/**
		 * 组件样式
		 * @default
		 * 		空
		 */
		self.style = "";
		
		/**
		 * 是否自动播放
		 * @default
		 * 		true
		 */
		self.autoPlay = true;

		/**
		 * 是否显示左右箭头
		 * @default
		 * 		true
		 */
		self.showArrow = true;

		/**
		 * 是否显示标题栏
		 * @default
		 * 		false
		 */
    	self.showCaption = false;

		/**
		 * 自动播放每一页的时间间隔
		 * @default
		 * 		5000
		 */
		self.interval = 5000;

		/**
		 * 当前图片页码
		 * @default
		 * 		1
		 */
		self.currentImgIndex = 1;
		
		/**
		 * 图片是否宽高100%显示
		 * @default false
		 */
		self.showImgFull = false;

		/**
		 * 图片数组，数组中的每一项必须具备num、imgSrc、title、innerhtml四种属性。
		 * 可通过增删改此数组实现动态修改轮播
		 * @example
	     * <code language="JavaScript">
		 *     [{
		 *     	 num: 1,
		 *    	 imgSrc: "../../resources/images/carousel/slide1.png",
		 *     	 title: "", 
		 *     	 content: "",
		 *     	 innerhtml: "<button input='' class='btn' style='position:absolute;top:5px;main:5px;'>按钮</button>"
		 *     },
		 *     {
		 *     	 num: 2,
		 *    	 imgSrc: "../../resources/images/carousel/slide2.png",
		 *    	 title: "title2",
		 *    	 content: "内容",
		 *     	 innerhtml: ""
		 *     }]
	     * </code>
		 * 
		 * @default
		 * 		[]
		 */
		self.imgItems =  [];
    
		/**
		 * @ignore
		 */
		self._carousel = null;
		
		/**
		 * 当前图片变化处理事件。
		 */
		self.onCurrentImgIndexChanged = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			
			if(cube.isEmpty(self.id())){
				self.id("carousel_" + cube.random().toString().split("&")[0])
			}
			
			self.autoPlaySub = cube.subscribe(self.autoPlay, function(p_autoPlay) {
				if (p_autoPlay) {
					var intval = getInterval();
					self._carousel.carousel('cycle', intval,self);
				} else
					self._carousel.carousel('pause');
				return ;
			});
	
			self.intervalSub = cube.subscribe(self.interval, function(p_interval) {
				if (!self.autoPlay())
					return;
				var intval = getInterval();
				self._carousel.carousel('pause');
				self._carousel.carousel('cycle', intval,self);
				return ;
			});
	
		    self.currentImgIndexSub = cube.subscribe(self.currentImgIndex, function(p_currentImgIndex) {
		        var totalCount = self.imgItems().length;
		        var currentIndex = parseInt(p_currentImgIndex);
		        if (isNaN(currentIndex) ||  (currentIndex <= 0))
		        	self.currentImgIndex(1);
		        else if(currentIndex > totalCount)
		        	self.currentImgIndex(totalCount);
		        else
		        	self.currentImgIndex(currentIndex);
		        return ;
		    });
		}

		/**
		 * @ignore
		 */
      	self.onload = function(node) {
      		self._carousel = $(node).children(":first");
      		if (self.autoPlay())
				self._carousel.carousel('cycle', self.interval, self);
				
			//调用外部的当前图片变化事件
			self._carousel.on('slide.bs.carousel', function (e) {
				var curPageNum = e.relatedTarget.getAttribute("num");
				
	         	if(self.onCurrentImgIndexChanged!=null && !cube.isObservable(self.onCurrentImgIndexChanged)) {
					self.onCurrentImgIndexChanged(curPageNum);
	      		}
	      	});
			
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
      	}
      	
		//获取轮播间隔时间，最低为500ms
		function getInterval() {
			var intval = parseInt(self.interval());
			if (intval < 500)
				intval = 500;
			return intval;
		}
		
		cube.endViewModel(self, params);
	}
	return ViewModel;
});

//from bootstrap.js
+function ($) { "use strict";

  // CSS TRANSITION SUPPORT (Shoutout: http://www.modernizr.com/)
  // ============================================================

  function transitionEnd() {
    var el = document.createElement('bootstrap')

    var transEndEventNames = {
      'WebkitTransition' : 'webkitTransitionEnd'
    , 'MozTransition'    : 'transitionend'
    , 'OTransition'      : 'oTransitionEnd otransitionend'
    , 'transition'       : 'transitionend'
    }

    for (var name in transEndEventNames) {
      if (el.style[name] !== undefined) {
        return { end: transEndEventNames[name] }
      }
    }
  }

  // http://blog.alexmaccaw.com/css-transitions
  $.fn.emulateTransitionEnd = function (duration) {
    var called = false, $el = this
    $(this).one($.support.transition.end, function () { called = true })
    var callback = function () { if (!called) $($el).trigger($.support.transition.end) }
    setTimeout(callback, duration)
    return this
  }

  $(function () {
    $.support.transition = transitionEnd()
  })

}(jQuery);

+function ($) { "use strict";

  // CAROUSEL CLASS DEFINITION
  // =========================

  var Carousel = function (element, options) {
    this.$element    = $(element)
    this.$indicators = this.$element.find('.carousel-indicators')
    this.options     = options
    this.paused      =
    this.sliding     =
    this.interval    =
    this.$active     =
    this.$items      = null
    
    //added by lxz 2015.07.06: to get the carousel vm property:currentImgIndex
    this.vm = options.vm
    
    this.options.pause == 'hover' && this.$element
      .on('mouseenter', $.proxy(this.pause, this))
      .on('mouseleave', $.proxy(this.cycle, this))
  }

  Carousel.DEFAULTS = {
    interval: 5000
  , pause: 'hover'
  , wrap: true
  }

  Carousel.prototype.cycle =  function (e) {
    e || (this.paused = false)

    this.interval && clearInterval(this.interval)

    this.options.interval
      && !this.paused
      && (this.interval = setInterval($.proxy(this.next, this), this.options.interval))

    return this
  }

  Carousel.prototype.getActiveIndex = function () {
    this.$active = this.$element.find('.item.active')
    this.$items  = this.$active.parent().children()

    return this.$items.index(this.$active)
  }

  Carousel.prototype.to = function (pos) {
    var that        = this
    var activeIndex = this.getActiveIndex()

    if (pos > (this.$items.length - 1) || pos < 0) return

    if (this.sliding)       return this.$element.one('slid.bs.carousel', function () { that.to(pos) })
    if (activeIndex == pos) return this.pause().cycle()

    return this.slide(pos > activeIndex ? 'next' : 'prev', $(this.$items[pos]))
  }

  Carousel.prototype.pause = function (e) {
    e || (this.paused = true)

    if (this.$element.find('.next, .prev').length && $.support.transition.end) {
      this.$element.trigger($.support.transition.end)
      this.cycle(true)
    }

    this.interval = clearInterval(this.interval)

    return this
  }

  Carousel.prototype.next = function () {
    if (this.sliding) return
    return this.slide('next')
  }

  Carousel.prototype.prev = function () {
    if (this.sliding) return
    return this.slide('prev')
  }

  Carousel.prototype.slide = function (type, next) {
    var $active   = this.$element.find('.item.active')
    var $next     = next || $active[type]()
    var isCycling = this.interval
    var direction = type == 'next' ? 'left' : 'right'
    var fallback  = type == 'next' ? 'first' : 'last'
    var that      = this

    if (!$next.length) {
      if (!this.options.wrap) return
      $next = this.$element.find('.item')[fallback]()
    }

    this.sliding = true

    isCycling && this.pause()

    var e = $.Event('slide.bs.carousel', { relatedTarget: $next[0], direction: direction })

    if ($next.hasClass('active')) return

    if (this.$indicators.length) {
      this.$indicators.find('.active').removeClass('active')
      this.$element.one('slid.bs.carousel', function () {
        var $nextIndicator = $(that.$indicators.children()[that.getActiveIndex()]);
        
        $nextIndicator && $nextIndicator.addClass('active')
      })
    }

    if ($.support.transition && this.$element.hasClass('slide')) {
      this.$element.trigger(e)
      if (e.isDefaultPrevented()) return
      $next.addClass(type)
      $next[0].offsetWidth // force reflow
      $active.addClass(direction)
      $next.addClass(direction)
      $active
        .one($.support.transition.end, function () {
          $next.removeClass([type, direction].join(' ')).addClass('active')
          $active.removeClass(['active', direction].join(' '))
          that.sliding = false
          setTimeout(function () { that.$element.trigger('slid.bs.carousel') }, 0)
        })
        .emulateTransitionEnd(600)
        
      //added by lxz 2015.07.06: to modify the carousel vm property:currentImgIndex
      var vm = this.vm
      setTimeout(function(){ vm.currentImgIndex(that.getActiveIndex()+1) },600)
    } else {
      this.$element.trigger(e)
      if (e.isDefaultPrevented()) return
      $active.removeClass('active')
      $next.addClass('active')
      this.sliding = false
      this.$element.trigger('slid.bs.carousel')
      
      //added by lxz 2015.07.06: to modify the carousel vm property:currentImgIndex
      this.vm.currentImgIndex(that.getActiveIndex()+1)
    }
	
    isCycling && this.cycle()

    return this
  }


  // CAROUSEL PLUGIN DEFINITION
  // ==========================

  var old = $.fn.carousel

  //added param[vm] by lxz 2015.07.06: to get the carousel vm property:currentImgIndex
  $.fn.carousel = function (option, interval,vm) {
    return this.each(function () {
      var $this   = $(this)
      var data    = $this.data('bs.carousel')
      var options = $.extend({}, Carousel.DEFAULTS, $this.data(), typeof option == 'object' && option)
      
      //added by lxz 2015.07.06: to get the carousel vm property:currentImgIndex
      options.vm = vm;
      var action  = typeof option == 'string' ? option : options.slide

      if (interval != undefined && data!=undefined && data.options != undefined){
      	data.options.interval = interval
      }
      
      //added by lxz 2015.07.06: to get the carousel vm property:currentImgIndex
      if(vm != undefined && data!=undefined && data.options != undefined) {
    	  data.options.vm = vm;
      }
      
      if (!data) $this.data('bs.carousel', (data = new Carousel(this, options)))
      if (typeof option == 'number') data.to(option)
      else if (action) data[action]()
      else if (options.interval) data.pause().cycle()
    })
  }

  $.fn.carousel.Constructor = Carousel


  // CAROUSEL NO CONFLICT
  // ====================

  $.fn.carousel.noConflict = function () {
    $.fn.carousel = old
    return this
  }


  // CAROUSEL DATA-API
  // =================

  $(document).on('click.bs.carousel.data-api', '[data-slide], [data-slide-to]', function (e) {
    var $this   = $(this), href
    var $target = $($this.attr('data-target') || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '')) //strip for ie7
    var options = $.extend({}, $target.data(), $this.data())
    var slideIndex = $this.attr('data-slide-to')
    if (slideIndex) options.interval = false

    $target.carousel(options)

    if (slideIndex = $this.attr('data-slide-to')) {
      $target.data('bs.carousel').to(slideIndex)
    }

    e.preventDefault()
  })

  $(window).on('load', function () {
    $('[data-ride="carousel"]').each(function () {
      var $carousel = $(this)
      $carousel.carousel($carousel.data())
    })
  })

}(jQuery);