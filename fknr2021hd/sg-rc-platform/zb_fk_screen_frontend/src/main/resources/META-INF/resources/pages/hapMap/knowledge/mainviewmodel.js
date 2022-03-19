define(["d3","RESTClient", "echarts", "axios"], function(d3,RestClient, echarts, axios) {

    function ViewModel(params) {
        var self = this;
		self.returnCategory = function(){
			commonPageBridge({ 
				name: 'hapMap.category.main', 
				params: {} 
			});
        }
        var linkq=[]
        
		//实例ID
		var instanceID=JSON.parse(window.localStorage.getItem('instanceId'))
		var bjinstanceID=JSON.parse(window.localStorage.getItem('bjinstanceId'))
		var xzinstanceID=JSON.parse(window.localStorage.getItem('xzinstanceId'))
		console.log(xzinstanceID)
		function huoqutpt(searchParams) {
            var params = {"instanceId":instanceID||bjinstanceID||xzinstanceID}
            axios.post(cube.gatewayURL2 + "atlasInformation/getGplot", params).then(function (res) {
				window.localStorage.removeItem('instanceId')
				window.localStorage.removeItem('bjinstanceId')
				window.localStorage.removeItem('xzinstanceId')

                if (res.data.successful==true) {
                //   console.log(res.data.resultValue)
                //   links=[...res.data.resultValue.principalLine,...res.data.resultValue.branch]
                //   console.log(links)

                linkq=[...res.data.resultValue.principalLine,...res.data.resultValue.branch]
                // console.log(linkq,333)
               links= linkq.map(function(item){
                    return {source:item.SOURCE_INSTANCE_NAME,target:item.TARGET_INSTANCE_NAME,rela:item.RELATION_NAME}
                })
                console.log(links)
                tpu(links)

                } else {
                    cube.indicate("拓扑图形成失败");
                }

            })
		}
        huoqutpt()
        

        var tpu=function(links){
/**
         * 初始化方法
         * @ignore
         */
        self._init = function() {
        };

        /**
         * @ignore
         */
        self.onloading = function(node, viewModel) {
        };

        /**
         * @ignore
         */
        self.onload = function(node, viewModel) {
        };

        self.zoomIn = function () {
            zoomClick(1)
        }
        self.zoomOut = function () {
            zoomClick(-1)
        }
        self.toggleLayout = function () {

        }
        var links=links
		//  var links=[
        //     {
        //         source: "作业计划4",
        //         rela: "下发",
        //       target: "外包单位4",
        //     //   type: "10"
        //     },
        //     {
        //         source: "作业计划4",
        //         rela: "隐含",
        //       target: "隐患4",
        //     //   type: "28"
        //     },
        //     {
        //         source: "作业计划4",
        //         rela: "存在",
        //       target: "风险4",
        //     //   type: "22"
        //     },
        //     {
        //         source: "作业计划4",
        //         rela: "导致",
        //       target: "事件4",
        //     //   type: "34"
        //     },
        //     {
        //         source: "作业计划4",
        //         rela: "巡视",
        //       target: "人员4",
        //     //   type: "16"
        //     },
        //     {
        //         source: "风险4",
        //         rela: "资质等级",
        //         target: "外包单位4"
        //       },
        //       {
        //         source: "风险4",
        //         rela: "引发",
        //         target: "事件4"
        //       },
        //       {
        //         source: "人员4",
        //         rela: "被管理",
        //         target: "外包单位4"
        //       }
        //   ]
        // var links = [
		// 	{ source: "私营企业外包单位", target: "行为违章", type: "resolved", rela: "下发" },
		// 	{ source: "信息通信作业计划", target: "私营企业外包单位", type: "resolved", rela: "下发" },
		// 	{ source: "运行人员", target: "私营企业外包单位", type: "resolved", rela: "下发" },
		// 	{ source: "地调风险", target: "私营企业外包单位", type: "resolved", rela: "下发" },
		// 	{ source: "信息事件", target: "私营企业外包单位", type: "resolved", rela: "下发" },
		// 	{ source: "信息通信作业计划", target: "有限责任公司外包单位", type: "resolved", rela: "下发" },
		// 	{ source: "运行人员", target: "行为违章", type: "resolved", rela: "下发" },
		// 	{ source: "信息通信作业计划", target: "安全事件隐患", type: "resolved", rela: "下发" },
		// 	{ source: "信息通信作业计划", target: "运行人员", type: "resolved", rela: "下发" },
		// 	{ source: "信息通信作业计划", target: "地调风险", type: "resolved", rela: "下发" },
		// 	{ source: "信息通信作业计划", target: "信息事件", type: "resolved", rela: "下发" },
		// 	{ source: "地调风险", target: "信息事件", type: "resolved", rela: "下发" },
		// 	{ source: "运行人员", target: "信息事件", type: "resolved", rela: "下发" }
		// ];

		var nodes = {};

		links.forEach(function (link) {
			link.source = nodes[link.source] || (nodes[link.source] = { name: link.source });
			link.target = nodes[link.target] || (nodes[link.target] = { name: link.target });
		});
		var width = $("#d3").width();
			height = $("#d3").height() - 40;
		var zoom = d3.behavior.zoom()//缩放配置，
			.scaleExtent([0.5, 2])//缩放比例
			.on("zoom", zoomed);


		function zoomed() {//缩放函数
			graph.attr("transform",
				"translate(" + zoom.translate() + ")" +
				"scale(" + zoom.scale() + ")"
			);
		}
		var force = d3.layout.force()//layout将json格式转化为力学图可用的格式
			.nodes(d3.values(nodes))//设定节点数组
			.links(links)//设定连线数组
			.size([width, height])//作用域的大小
			.linkDistance(240)//连接线长度
			.charge(-3000)//顶点的电荷数。该参数决定是排斥还是吸引，数值越小越互相排斥
			.on("tick", tick)//指时间间隔，隔一段时间刷新一次画面
			.start();//开始转换

		var svg = d3.select("#d3").append("svg")
			.attr("width", width)
			.attr("height", height)
		var graph = svg.append("g").attr("class", "graph");

		//箭头
		var marker =
			graph.append("marker")
				.attr("id", "resolved")
				.attr("markerUnits", "userSpaceOnUse")
				.attr("viewBox", "0 -5 10 10")//坐标系的区域
				.attr("refX", 32)//箭头坐标
				.attr("refY", -1)
				.attr("markerWidth", 12)//标识的大小
				.attr("markerHeight", 12)
				.attr("orient", "auto")//绘制方向，可设定为：auto（自动确认方向）和 角度值
				.attr("stroke-width", 2)//箭头宽度
				.append("path")
				.attr("d", "M0,-5L10,0L0,5")//箭头的路径
				.attr('fill', '#fff');//箭头颜色

		//设置连接线    
		var edges_line = graph.selectAll(".edgepath")
			.data(force.links())
			.enter()
			.append("path")
			.attr({
				'd': function (d) { return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y },
				'class': 'edgepath',
				'id': function (d, i) { return 'edgepath' + i; }
			})
			.style("stroke", "#fff")
			.style("pointer-events", "none")
			.style("stroke-width", 1)//线条粗细
			.attr("marker-end", "url(#resolved)");//根据箭头标记的id号标记箭头

		var edges_text = graph.append("g").selectAll(".edgelabel")
			.data(force.links())
			.enter()
			.append("text")
			.style("pointer-events", "none")
			// .attr("class","linetext")
			.attr({
				'class': 'edgelabel',
				'id': function (d, i) { return 'edgepath' + i; },
				'dx': 110,
				'dy': 0,
				'font-size':12,
				'fill':'#C99479'
			});

		//设置线条上的文字
		edges_text.append('textPath')
			.attr('xlink:href', function (d, i) { return '#edgepath' + i })
			.style("pointer-events", "none")
			.text(function (d) { return d.rela; });
		function drag() {//拖拽函数
			return force.drag()
				.on("dragstart", function (d) {
					d3.event.sourceEvent.stopPropagation(); //取消默认事件
					d.fixed = true;    //拖拽开始后设定被拖拽对象为固定

				})
				.on("dragend", function (d) {
					d.fixed = false;
				});

		}
		var nodes = graph.selectAll("node")
			.data(force.nodes())
			.enter()
			.append("g").call(drag());
		//圆圈
		var circle = nodes.selectAll("circle")
			.data(force.nodes())//表示使用force.nodes数据
			.enter().append("circle")
			.style("fill", "rgba(235,67,16,0.8)")
			.attr("r", 28)//设置圆圈半径
			.on("click", function (node) {
				//单击时让连接线变色
				edges_line.style("stroke", function (line) {
					if (line.source.name == node.name || line.target.name == node.name) {
						return '#f31515';
					} else {
						return '#ccc';
					}
				})

				//d3.select(this).style('stroke-width',2);
            })
            .on("mouseover", function (d, i) {
                d3.select(this).style("cursor", "pointer");
            })
			.call(force.drag);//将当前选中的元素传到drag函数中，使顶点可以被拖动



		var text = graph.append("g").selectAll(".nodetext")
			.data(force.nodes())
			//返回缺失元素的占位对象（placeholder），指向绑定的数据中比选定元素集多出的一部分元素。
			.enter()
            .append("text")
            .attr("class", "nodetext")
			.attr("text-anchor", "middle")//在圆圈中加上数据  
            .style('fill', "#fff")
            .attr('x', function (d) {
				var re_en = /[a-zA-Z]+/g;
				//如果是全英文，不换行
				if (d.name.match(re_en)) {
					d3.select(this).append('tspan')
						.attr('x', 0)
						.attr('y', 2)
						.text(function () { return d.name; });
				}
				//如果小于四个字符，不换行
				else if (d.name.length <= 4) {
					d3.select(this).append('tspan')
						.attr('x', 0)
						.attr('y', 2)
						.text(function () { return d.name; });
				} else {
					var top = d.name.substring(0, 4);
					var bot = d.name.substring(4, d.name.length);

					d3.select(this).text(function () { return ''; });

					d3.select(this).append('tspan')
						.attr('x', 0)
						.attr('y', -7)
						.text(function () { return top; });

					d3.select(this).append('tspan')
						.attr('x', 0)
						.attr('y', 10)
						.text(function () { return bot; });
				}
			}).on("mouseover", function (d, i) {
                d3.select(this).style("cursor", "pointer");
            }).call(drag());

		function tick() {
			edges_line.attr("d", linkArc);//连接线
			circle.attr("transform", transform1);//圆圈
			text.attr("transform", transform2);//顶点文字

			edges_text.attr('transform', function (d, i) {
				if (d.target.x < d.source.x) {
					bbox = this.getBBox();
					rx = bbox.x + bbox.width / 2;
					ry = bbox.y + bbox.height / 2;
					return 'rotate(180 ' + rx + ' ' + ry + ')';
				}
				else {
					return 'rotate(0)';
				}
			});
		}

		//设置连接线的坐标,使用椭圆弧路径段双向编码
		function linkArc(d) {
			return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
		}
		//设置圆圈和文字的坐标
		function transform1(d) {
			return "translate(" + d.x + "," + d.y + ")";
		}
		function transform2(d) {
			return "translate(" + (d.x) + "," + d.y + ")";
		}
		function zoomClick(direction) {
			var
				//direction = 1,
				factor = 0.2,
				target_zoom = 1,
				center = [500, 250],
				extent = zoom.scaleExtent(),
				translate = zoom.translate(),
				translate0 = [],
				l = [],
				view = { x: translate[0], y: translate[1], k: zoom.scale() };
			target_zoom = zoom.scale() * (1 + factor * direction);

			if (target_zoom < extent[0] || target_zoom > extent[1]) {
				return false;
			}

			translate0 = [(center[0] - view.x) / view.k, (center[1] - view.y) / view.k];
			view.k = target_zoom;
			l = [translate0[0] * view.k + view.x, translate0[1] * view.k + view.y];

			view.x += center[0] - l[0];
			view.y += center[1] - l[1];

			interpolateZoom([view.x, view.y], view.k);
		}
		function interpolateZoom(translate, scale) {
            var self = this;
            return d3.transition().duration(350).tween("zoom", function () {
                var iTranslate = d3.interpolate(zoom.translate(), translate),
                    iScale = d3.interpolate(zoom.scale(), scale);
                return function (t) {
                    zoom.scale(iScale(t))
                    .translate(iTranslate(t));
                    zoomed();
                };
            });
        }
		svg.call(zoom);
        }
        
        // tpu()

        

        cube.endViewModel(self, params);
    }

    
    ViewModel.prototype.dispose = function() {

    }
    return ViewModel;
});
