<template>
  <div class='container'>

    <div class='meatitle'>食谱营养分析</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>

    <el-card class='tablecard'>
      <div style="display: flex">

        <div class="maps" style="width: 75%;">

          <!-- 引用地图-->
            <div style="margin-left:20%;margin-top:20px;">
              <el-date-picker
                v-model="Time"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                @change="pagelist"
                placeholder="选择日期">
              </el-date-picker>
            </div>
          <div id="mapChart" class="chart" style="height:850px;width:40%;"></div>
          </div>

        <div class="zhu">
                   <div  class='echartbox'>
         <div class="bg_pic"><p  style="color: white;margin-left:50px;font-size: 18px;padding-top: 7px;"> 排餐总能量分析</p></div>
          <div id="myChart3" style="width: 100%;height:100%;"></div>
          </div>
          <div class='echartbox'>
          <div class="bg_pic"><p  style="color: white;margin-left:50px;font-size: 18px;padding-top: 7px"> 排餐能量分布分析</p></div>
          <div id="myChart4" style="width: 100%;height:100%;"></div>
          </div>
          <div class='echartbox'>
          <div class="bg_pic"><p  style="color: white;margin-left:50px;font-size: 18px;padding-top: 7px"> 排餐营养素分析</p></div>
          <div id="myChart5" style="width: 100%;height:90%;"></div>
          </div>
           <div class='echartbox'>
          <div class="bg_pic"><p  style="color: white;margin-left:50px;font-size: 18px;padding-top: 7px"> 排餐膳食结构分析</p></div>
            <div id="myChart6" style="width: 100%;height:100%;"></div>
          </div>


        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
  import axios from 'axios'
  import echarts from 'echarts'
export default {
  data () {
    return {
      Time:'',
      data: [],
      totalEnergy:[],//总能量
      DietaryStructure:[],//膳食结构
      Nutrient:[],//营养分析
      distributed:[],//分布分析
      restaurantCode:'ct001',//树状图code码
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  computed: {
    timeDefault(){
      var date = new Date();
      var s1 = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + (date.getDate());
      return s1;
    }
  },
  mounted () {
    this.energyAnalysis()
    this.energyDistribution()
    this.fenxi()
    this.energyDistributions()
    this.mapChart()
  },
  created(){
    this.Time = this.timeDefault;
    this.treelist()
    this.pagelist()
  },
  methods: {
    handleNodeClick (data) {
      this.restaurantCode=data.restaurantCode
      this.pagelist()
      console.log(data)
    },
    //树状图接口
    treelist(){
      this.$axios({
        url: 'StOperationrestaurant.do/getStOperationList',
        method: 'post',
      }).then(res=>{
        this.data=res.data
      })
    },
    // 数据接口
    pagelist(){
      this.$axios({
        url:'StNutritionalAnalysis.do/totalEnergyAnalysis',
        method:'post',
        data:{
          "restaurant":this.restaurantCode,
          "date":this.Time
        }
      }).then(res=>{
        this.totalEnergy=res.data.map.totalEnergy
        this.distributed=res.data.map.distributed
        this.Nutrient=res.data.map.Nutrient
        this.DietaryStructure=res.data.map.DietaryStructure
        this.energyAnalysis()
        this.energyDistribution()
        this.fenxi()
       this.energyDistributions()
       this.mapChart()
        console.log(res,this.Nutrient)
      })
    },
    energyAnalysis () {
      const myChart3 = this.$echarts.init(document.getElementById('myChart3'))
      myChart3.setOption({
        // title: {
        //   text: '2019年销售水量和主营业务收入对比',
        //   textStyle: {
        //     align: 'center',
        //     color: '#fff',
        //     fontSize: 20
        //   },
        //   top: '3%',
        //   left: '10%'
        // },
        // backgroundColor: '#0f375f',
        grid: {
          top: '9%',
          bottom: '10%', // 也可设置left和right设置距离来控制图表的大小
          right: '5%'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: '',
            label: {
              show: true
            }
          }
        },
        legend: {
          // eslint-disable-next-line no-sparse-arrays
          data: ['本月', '当日',, '本周'],
          top: '1%',
          right: '10%',
          textStyle: {
            color: '#ffffff'
          }
        },
        xAxis: {
          data: [
            '早餐',
            '午餐',
            '晚餐'
          ],
          axisLine: {
            show: true, // 隐藏X轴轴线
            lineStyle: {
              // color: 'white', // X轴轴线颜色
              show: false
            }
          },
          axisTick: {
            show: true // 隐藏X轴刻度
          },
          axisLabel: {
            show: true,
            textStyle: {
              color: 'white' // X轴文字颜色
            }
          }

        },
        yAxis: [{
          type: 'value',
          // name: '亿元',
          nameTextStyle: {
            color: '#ebf8ac'
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: true
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#FFFFFF'
            }
          },
          axisLabel: {
            show: true,
            textStyle: {
              color: '#FFFFFF'
            }
          }

        },
        {
          type: 'value',
          // name: '同比',
          nameTextStyle: {
            color: '#ebf8ac'
          },
          position: 'right',
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          axisLabel: {
            show: true,
            formatter: '{value} ', // 右侧Y轴文字显示
            textStyle: {
              color: 'white'
            }
          }
        },
        {
          type: 'value',
          gridIndex: 0,
          min: 50,
          max: 100,
          splitNumber: 8,
          splitLine: {
            show: false
          },
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false
          },
          splitArea: {
            show: true,
            areaStyle: {
              color: ['rgba(250,250,250,0.0)', 'rgba(250,250,250,0.05)']
            }
          }
        }
        ],
        series: [{
          name: '本月',
          type: 'line',
          yAxisIndex: 1, // 使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用
          smooth: true, // 平滑曲线显示
          showAllSymbol: true, // 显示所有图形。
          symbol: 'circle', // 标记的图形为实心圆
          symbolSize: 10, // 标记的大小
          itemStyle: {
            // 折线拐点标志的样式
            color: '#058cff'
          },
          lineStyle: {
            color: '#058cff'
          },
          areaStyle: {
            color: 'yellow' // 包含区域的阴影背景颜色设置
          },
          data: [this.totalEnergy.BreakTotal2,this.totalEnergy.lunchTotal2, this.totalEnergy.DinnerTotal2]
        },
        {
          name: '当日',
          type: 'bar',
          barWidth: 15,
          itemStyle: {
            normal: {
              color: '#2BFAFF'

            }
          },
          // data: [4.2, 9, 4.8]
          data:[this.totalEnergy.BreakTotal,this.totalEnergy.lunchTotal, this.totalEnergy.DinnerTotal]
        },
        {
          name: '本周',
          type: 'line',
          yAxisIndex: 1, // 使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用
          barWidth: 15,
          itemStyle: {
            normal: {
              color: '#2BFAFF'

            }
          },
          data:[this.totalEnergy.BreakTotal1,this.totalEnergy.lunchTotal1, this.totalEnergy.DinnerTotal1]
        }
        ]
      })
    },
    energyDistribution () {
      const myChart4 = this.$echarts.init(document.getElementById('myChart4'))
      myChart4.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: ''
          }
        },
        grid: {
          top: '15%',
          right: '3%',
          // left: '5%',
          bottom: '15%'
        },
        xAxis: [{
          type: 'category',
          data: this.distributed.map(item=>{
          return item.name
        }),
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.12)'
            }
          },
          axisLabel: {
            interval: 0,
            rotate: 40,
            margin: 10,
            color: '#e2e9ff',
            textStyle: {
              fontSize: 14
            }
          }

        }],
        yAxis: [{
          // splitNumber: 3,
          axisLabel: {
            formatter: '{value}',
            color: '#e2e9ff'
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: 'white'
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: '#233e64'
            }
          }
        }],
        series: [{
          type: 'bar',
          data: this.distributed.map(item=>{
            return item.energy
          }),
          barWidth: '15px',
          itemStyle: {

            normal: {
              label: {
                // eslint-disable-next-line no-tabs
                show: true,		// 开启显示
                // eslint-disable-next-line no-tabs
                position: 'top',	// 在上方显示
                textStyle: { // 数值样式
                  color: 'white',
                  fontSize: 14
                }
              },
              // eslint-disable-next-line no-undef
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(0,244,255,1)' // 0% 处的颜色
              }, {
                offset: 1,
                color: 'rgba(0,77,167,1)' // 100% 处的颜色
              }], false),
              barBorderRadius: [30, 30, 30, 30],
              shadowColor: 'rgba(0,160,221,1)',
              shadowBlur: 4
            }
          }

        }]
      })
    },
    fenxi () {
      const myChart5 = this.$echarts.init(document.getElementById('myChart5'))
      myChart5.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: ''
          }
        },
        grid: {
          top: '15%',
          right: '3%',
          // left: '5%',
          bottom: '15%'
        },
        xAxis: [{
          type: 'category',
          data: ['蛋白质', '脂肪', '饱和脂', '胆固醇','碳水化合物', '膳食纤维', '维生素A', '维生素D', '维生素E', '维生素K', '维生素B1', '维生素B2', '维生素B6','维生素B12', '维生素C', '烟酸', '叶酸', '泛酸', '生物素', '胆碱', '钙', '磷', '钾', '钠', '镁', '铁', '锌', '碘', '硒', '铜', '氯', '铭', '锰', '钼'],

          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.12)'
            }
          },
          axisLabel: {
            formatter: function (value) {
              return value.split('').join('\n')
            },
            interval: 0,
            // rotate: 40,
            margin: 10,
            color: '#e2e9ff',
            textStyle: {
              fontSize: 14
            }
          }

        }],
        yAxis: [{
          // splitNumber: 3,
          axisLabel: {
            formatter: '{value}',
            color: '#e2e9ff'
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: 'white'
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: '#233e64'
            }
          }
        }],
        series: [{
          type: 'bar',
          data: [this.Nutrient.proteinTotal,this.Nutrient.fatTotal,this.Nutrient.saturatedTotal,this.Nutrient.cholesterolTotal,this.Nutrient.carbohydrateTotal,this.Nutrient.dietaryfiberTotal,
          this.Nutrient.vitaminATotal,this.Nutrient.vitaminDTotal,this.Nutrient.vitaminETotal,this.Nutrient.vitaminKTotal,this.Nutrient.vitaminB1Total,this.Nutrient.vitaminB2Total,
          this.Nutrient.vitaminB6Total,this.Nutrient.vitamin16Total,this.Nutrient.vitaminCTotal,this.Nutrient.nicotinicacidTotal,this.Nutrient.folicacidTotal,this.Nutrient.pantothenicacidTotal,
          this.Nutrient.biotinTotal,this.Nutrient.cholineTotal,this.Nutrient.calciumTotal,this.Nutrient.phosphorusTotal,this.Nutrient.potassiumTotal,this.Nutrient.sodiumTotal,
          this.Nutrient.magnesiumTotal,this.Nutrient.ironTotal,this.Nutrient.zincTotal,this.Nutrient.iodineTotal,this.Nutrient.seleniumTotal,this.Nutrient.copperTotal,
          this.Nutrient.fluorineTotal,this.Nutrient.chromiumTotal,this.Nutrient.manganeseTotal,this.Nutrient.molybdenumTotal],
          barWidth: '12px',
          itemStyle: {

            normal: {
              label: {
                // eslint-disable-next-line no-tabs
                show: true,		// 开启显示
                // eslint-disable-next-line no-tabs
                position: 'top',	// 在上方显示
                textStyle: { // 数值样式
                  color: 'white',
                  fontSize: 14
                }
              },
              // eslint-disable-next-line no-undef
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(0,244,255,1)' // 0% 处的颜色
              }, {
                offset: 1,
                color: 'rgba(0,77,167,1)' // 100% 处的颜色
              }], false),
              barBorderRadius: [30, 30, 30, 30],
              shadowColor: 'rgba(0,160,221,1)',
              shadowBlur: 4
            }
          }

        }]
      })
    },
    energyDistributions () {
      const myChart6 = this.$echarts.init(document.getElementById('myChart6'))
      myChart6.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: ''
          }
        },
        grid: {
          top: '15%',
          right: '3%',
          // left: '5%',
          bottom: '15%'
        },
        xAxis: [{
          type: 'category',
          data: ['谷类食物', '蔬菜,水果', '食,肉,蛋等动物性食物', '奶类和豆类食物', '烹调油,食盐'],
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.12)'
            }
          },
          axisLabel: {
            interval: 0,
            // rotate: 40,
            margin: 10,
            color: '#e2e9ff',
            textStyle: {
              fontSize: 14
            }
          }

        }],
        yAxis: [{
          // splitNumber: 3,ban
          axisLabel: {
            formatter: '{value}',
            color: '#e2e9ff'
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: 'white'
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: '#233e64'
            }
          }
        }],
        series: [
          {
          type: 'bar',
          // data: [300, 500, 150, 333, 352],
          data: [this.DietaryStructure.cerealTotal,this.DietaryStructure.vegetableTotal,this.DietaryStructure.fishmeatToatl,this.DietaryStructure.milkTotal,this.DietaryStructure.oilsaltTotal],
          barWidth: '15px',
          itemStyle: {

            normal: {
              label: {
                // eslint-disable-next-line no-tabs
                show: true,		// 开启显示
                // eslint-disable-next-line no-tabs
                position: 'top',	// 在上方显示
                textStyle: { // 数值样式
                  color: 'white',
                  fontSize: 14
                }
              },
              // eslint-disable-next-line no-undef
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(0,244,255,1)' // 0% 处的颜色
              }, {
                offset: 1,
                color: 'rgba(0,77,167,1)' // 100% 处的颜色
              }], false),
              barBorderRadius: [30, 30, 30, 30],
              shadowColor: 'rgba(0,160,221,1)',
              shadowBlur: 4
            }
          }

        }
        ]
      })
    },
    mapChart(divid) {

      axios.get('http://localhost:8080/api/hebei.json').then(response => {
        let myChart = this.$echarts.init(document.getElementById('mapChart'))
        const mapJson = response.data;
        // console.log(mapJson)
        // myChart = echarts.init(document.getElementById(mapChart));
        echarts.registerMap('hebei', mapJson);
        myChart.hideLoading();
        var geoCoordMap = {
          '石家庄':[114.12,38.33],
          '河北本部':[114.32,38.03],
          '培训中心':[114.62,37.81],
          '保定':[115.48,38.85],
          '张家口':[114.87,40.82],
          '承德':[117.93,40.97] ,
          '秦皇岛':[119.27,39.95],
          '唐山':[118.02,39.63],
          '廊坊':[116.7,39.53],
          '沧州':[116.83,38.33],
          '邯郸':[114.47,36.6],
          '衡水':[115.72,37.72],
          '邢台':[114.48,37.05],
        };
        var data1 = [
          {
          name: '石家庄',
          value: '国网石家庄供电公司餐厅'
        },
          {
            name: '河北本部',
            value: '国网河北电力公司本部餐厅'
          },
          {
            name: '培训中心',
            value: '国网河北培训中心餐厅'
          },
          {
            name: '保定',
            value: '国网保定供电公司餐厅'
          },
          // {
          //   name: '张家口',
          //   value: '',
          // },
          // {
          //   name: '承德',
          //   value:  ''
          // },
          // {
          //   name: '秦皇岛',
          //   value: ''
          // },
          // {
          //   name: '唐山',
          //   value:  ''
          // },
          // {
          //   name: '廊坊',
          //   value: ''
          // },
          {
            name: '沧州',
            value:  '国网沧州供电公司餐厅'
          },
          // {
          //   name: '邯郸',
          //   value: ''
          // },
          {
            name: '衡水',
            value: '国网衡水供电公司餐厅'
          },
          // {
          //   name: '邢台',
          //   value:  ''
          // },

        ];
        var max = 480,
          min = 9;
        var maxSize4Pin = 100,
          minSize4Pin = 20;


        var convertData = function (data) {
          var res = [];
          for (var i = 0; i < data.length; i++) {
            var geoCoord = geoCoordMap[data[i].name];
            if (geoCoord) {
              res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
              });
            }
          }
          return res;
        };


        var option = {
          // backgroundColor: '#020933',
          tooltip: {
            trigger: 'item',
            formatter: function (params) {
              if (typeof (params.value)[2] == "undefined") {
                // return params.name + ' : ' + params.value;
              } else {
                // return params.name + ' : ' + params.value[2];
                return  params.value[2];
              }
            }
          },
          legend: {
            orient: 'vertical',
            id: 1,
            y: 'bottom',
            x: 'right',
            itemWidth: 15,
            textStyle: {
              color: '#fff'
            }
          },
          geo: {
            show: true,
            map: 'hebei',
            label: {
              normal: {
                show: false
              },
              emphasis: {
                show: false,
              }
            },
            zoom: 1,
            roam: true,
            itemStyle: {
              normal: {
                areaColor: 'transparent',
                borderColor: '#3fdaff',
                borderWidth: 2,
                shadowColor: 'rgba(63, 218, 255, 0.5)',
                shadowBlur: 30
              },
              emphasis: {
                areaColor: '#2B91B7',
              }
            },

          },

          series: [{
            type: 'map',
            map: 'hebei',
            geoIndex: 0,
            aspectScale: 0.75, //长宽比
            showLegendSymbol: false, // 存在legend时显示
            label: {
              normal: {
                show: false
              },
              emphasis: {
                show: false,
                textStyle: {
                  color: '#fff'
                }
              }
            },
            roam: true,
            itemStyle: {
              normal: {
                areaColor: '#031525',
                borderColor: '#3B5077',
              },
              emphasis: {
                areaColor: '#2B91B7'
              }
            },
            animation: false,

          },
            // 图标的位置
            {
              name: '',
              type: 'effectScatter',
              coordinateSystem: 'geo',
              data: convertData(data1.sort(function (a, b) {
                return b.value - a.value;
              })),
              symbolSize: 12,
              showEffectOn: 'render',
              rippleEffect: {
                brushType: 'stroke'
              },
              hoverAnimation: true,
              label: {
                normal: {
                  formatter: '{b}',
                  position: 'right',
                  show: true
                }
              },
              itemStyle: {
                normal: {
                  color: '#05C3F9',
                  shadowBlur: 10,
                  shadowColor: '#05C3F9'
                }
              },
              zlevel: 1
            },
          ]
        };
        myChart.setOption(option);
        //地图点击事件
        myChart.on('click', function(data1){
          console.log(data1.name,data1);
        });
      })
    }

  }

}
</script>

<style lang="less" scoped>

  .zhu{
    position: relative;
    width:760px;
    left: 700px;
    font-size: 12px;
  }
  .maps{
    position: fixed;
  }
  .bg_pic{
    width:760px;
    height:38px;
    background: url(../../assets/u719.png) ;
    /*background-size: 683px 38px;*/
  }
  .container{
    padding:15px 10px 0px 10px;
    box-sizing: border-box;
    color: rgb(255,255,255);
    font-size: 14px;
    height: 100%;
  }
  .meatitle{
    margin-bottom:6px;
  }

  .opmainrescard{
    background-color:rgba(1,5,22,.4);
    box-sizing: border-box;
    margin-right: 20px;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width: 16%;
    height: 860px;
    float: left;
  }
  .opmainreshead{
    height:44px;
    text-align: center;
    padding-top:6px;
    box-sizing: border-box;
    font-size: 16px;
    color:rgba(255,255,255);
    margin-bottom:26px;
  }
  .el-tree{
    background:transparent;
    color: white;
  }
  /deep/.el-tree-node__content{
  background:transparent;
  }
  /deep/.el-tree-node__label{
    font-size:14px;
  }
  /deep/.el-tree-node.is-current > .el-tree-node__content {
    background-color: transparent !important;
  }
  /deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
}
  .tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    box-sizing: border-box;
    overflow-y: auto;
    overflow-x: hidden;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:82%;
    height: 860px;
  }
.echartbox{
  width: 100%;
  height: 550px;
}
.echartbox:nth-child(3){
  height: 700px;
}
/deep/.el-input__inner{
  width: 220px;
  height: 40px;
}
/deep/.el-input__icon{
  width: 25px;
  line-height: 40px;
}
</style>
