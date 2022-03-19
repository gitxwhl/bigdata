define([], function(){
	
	/**
	 * 音频播放组件，文件格式为html5 audio标签支持的格式
	 * @example
	 * <code language="html">
	 * 		<cubeaudio params="
	 *						width: width,
	 *						src: src,
	 *						autoplay: autoplay,
	 *						loop: loop"></cubeaudio>
	 * </code>
	 * <code language="JavaScript">
	 * 		define([], function() {
	 * 			var PageViewModel = function(params) {
	 * 				var self = this;
	 * 
	 * 				self.width = "200px";
	 * 				self.src = "../resource/demo.mp3";
	 *   			self.autoplay = true;
	 *    			self.loop = true;
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
		 * @default auto
		 */
		self.width = "auto";
		
		/**
		 * 指定音频的文件位置，字符串或字符串数组
		 * 
		 * @default auto
		 */
		self.src = null;
		
		/**
		 * 是否自动播放
		 * 
		 * @default false
		 */
		self.autoplay = false;
		
		/**
		 * 是否预加载
		 * 
		 * @default true
		 */
		self.preload = true;
		
		/**
		 * 是否循环播放
		 * 
		 * @default false
		 */
		self.loop = false;
		
		/**
		 * 是否静音
		 * 
		 * @default false
		 */
		self.muted = false;
		
		/**
		 * 设置音量，在0.0到1.0之间
		 * 
		 * @default null
		 */
		self.volume = null;
		
		/**
		 * 以秒为单位返回从开始播放到目前所用时间，也可以设置currentTime的值跳转到指定位置
		 * 
		 * @default null
		 */
		self.currentTime = null;
		
		/**
		 * audio对象
		 */
		self.audio = null;
		
		/**
		 * @ignore
		 */
		self.isplaying = false;
		
		/**
		 * @ignore
		 */
		self.isstop = false;
		
		/**
		 * @ignore
		 */
		self.ismuted = false;
		
		/**
		 * @ignore
		 */
		self.volumelow = false;
		
		/**
		 * @ignore
		 */
		self.currentTime = "00.00";
		
		/**
		 * @ignore
		 */
		self.timeDuration = "00.00";
		
		/**
		 * @ignore
		 */
		self.playedwidth = "0";
		
		/**
		 * @ignore
		 */
		self.volumeheight = "100%";
		
		/**
		 * @ignore
		 */
		self.currentTimeVisible = true;
		
		/**
		 * @ignore
		 */
		self.timeDurationVisible = true;
		
		/**
		 * 准备就绪，可以播放时触发
		 */
		self.onPlay = null;
		
		/**
		 * 开始播放时触发
		 */
		self.onPlaying = null;
		
		/**
		 * 暂停时触发
		 */
		self.onPause = null;
		
		/**
		 * 播放结束时触发
		 */
		self.onEnded = null;
		
		/**
		 * 当前播放时间发生改变时触发
		 */
		self.onTimeupdate = null;
		
		/**
		 * 组件渲染完成事件
		 */
		self.onRendered = null;
		
		/**
		 * 初始化方法
		 * @ignore
		 */
		self._init = function() {
			if (self.autoplay() == true) {
				self.isplaying(true);
			}
			
			if (self.muted() == true) {
				self.ismuted(true);
			}
		};
		
		/**
		 * 播放/暂停
		 */
		self.playpause = function() {
			if (self.osplaying() == true) {
				self.audio.pause();
				self.isplay(false);
			} else {
				self.audio.play();
				self.isplaying(true);
				self.isstop(false);
			}
		};
		
		/**
		 * 停止
		 */
		self.stop = function() {
			self.audio.pause();
			self.isplaying(false);
			self.isstop(true);
			self.audio.currentTime = 0.0;
		};
		
		/**
		 * 静音/停止静音
		 */
		self.setmuted = function() {
			self.audio.muted = !self.audio.muted;
			
			if (self.audio.muted == true) {
				self.ismuted(true);
			} else {
				self.ismuted(false);
			}
		};
		
		/**
		 * @ignore
		 */
		self.barmousedown = function(element, event) {
			adjustCurrentTime(element, event);
			$(element).bind("mousemove", barmousemove);
		};
		
		/**
		 * @ignore
		 */
		self.barmouseup = function(element, event) {
			$(element).unbind("mousemove", barmousemove);
		};
		
		function barmousemove(event) {
			var element = event.currentTarget? event.currentTarget : event.srcElement;
			adjustCurrentTime(element, event);
		}
		
		/**
		 * @ignore
		 */
		self.volumemousedown = function(element, event) {
			adjustVolume(element, event);
			$(element).bind("mousemove", volumemousemove);
		};
		
		/**
		 * @ignore
		 */
		self.volumemouseup = function(element, event) {
			$(element).unbind("mousemove", volumemousemove);
		};
		
		function volumemousemove(event) {
			var element = event.currentTarget? event.currentTarget : event.srcElement;
			adjustVolume(element, event);
		}
		
		function adjustCurrentTime(element, event) {
			element = $(element);
			if (event.pageX == null && event.clientX != null) {
				var doc = document.documentElement, body = document.body;
				event.pageX = event.clientX + (doc && doc.scrollLeft || body && body.scrollLeft || 0) - (doc && doc.clientLeft || body && body.clientLeft || 0);
			}
			
			self.audio.currentTime = Math.round((self.audio.duration * (event.pageX - element.offset().left))/element.width());
		}
		
		function adjustVolume(element, event) {
			element = $(element);
			if (event.pageY == null && event.clientY != null) {
				var doc = document.documentElement, body = document.body;
				event.pageY = event.clientY + (doc && doc.scrollTop || body && body.scrollTop || 0) - (doc && doc.clientTop || body && body.clientTop || 0);
			}
			
			self.audio.volume = Math.abs((event.pageY - (element.offset().top + element.height()))/element.height());
		}
		
		function secondsToTime(secs) {
			var hours = Math.floor(secs / 3600), minutes = Math.floor(secs % 3600 / 60), seconds = Math.ceil(secs % 600 % 60);
			return (hours == 0 ? "" : hours > 0 && hours.toString().length < 2 ? "0"+hours+":" : hours+":") + 
			(minutes.toString().length < 2 ? "0" + minutes : minutes) + ":" +
			(seconds.toString().length < 2 ? "0" + seconds : seconds);
		}
		
		/**
		 * @ignore
		 */
		self.onload = function(node) {
			self.audio = $(node).find("audio").get(0);
			
			var audiodiv = $(node).children(":first");
			if (parseInt(audiodiv.width(), 10) < 180) {
				self.timeDurationVisible(false);
			}
			
			if (parseInt(audiodiv.width(), 10) < 150) {
				self.timeDurationVisible(false);
			}
			
			var volume = self.volume();
			var currentTime = self.currentTime();
			if (volume != null) {
				self.audio.volume = volume;
			}
			
			if (currentTime != null) {
				self.audio.currentTime = currentTime;
			}
			
			self.audio.muted = self.muted();
			
			var $audio = $(self.audio);
			$audio.on("loadeddata", function(){
				self.timeDuration(secondsToTime(self.audio.duration));
				self.volume(self.audio.volume);
				self.volumeheight(self.audio.volume * 100 + "%");
				
				if (self.audio.volume * 100 < 50) {
					self.volumelow(true);
				} else {
					self.volumelow(false);
				}
			});
			
			$audio.on("play", function(){
				if (self.onPlay != null && cube.isObservable(self.onPlay)) {
					self.onPlay();
				}
			});
			
			$audio.on("pause", function(){
				if (self.onPause != null && cube.isObservable(self.onPause)) {
					self.onPause();
				}
			});
			
			$audio.on("playing", function(){
				if (self.onPlaying != null && cube.isObservable(self.onPlaying)) {
					self.onPlaying();
				}
			});
			
			$audio.on("timeupdate", function(){
				self.currentTime(secondsToTime(self.audio.currentTime));
				self.playedwidth((self.audio.currentTime/self.audio.duration)*100 + "%");
				
				if (self.onTimeupdate != null && cube.isObservable(self.onTimeupdate)) {
					self.onTimeupdate(self.audio.currentTime);
				}
			});
			
			$audio.on("volumechange", function(){
				self.volumeheight(self.audio.volume * 100 + "%");
				
				if (self.audio.volume * 100 < 50) {
					self.volumelow(true);
				} else {
					self.volumelow(false);
				}
				
			});
			
			$audio.on("ended", function(){
				self.isplaying(false);
				self.isstop(true);
				
				if (self.onEnded != null && cube.isObservable(self.onEnded)) {
					self.onEnded();
				}
			});
			
			if (self.onRendered != null && !cube.isObservable(self.onRendered)) {
				self.onRendered(node);
			}
		};
		
		cube.endViewModel(self, params);
	}
	
	return ViewModel;
});