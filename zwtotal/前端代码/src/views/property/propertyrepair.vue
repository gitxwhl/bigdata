<template>
  <div class='container'>
    <div class='meatitle'>物业报修</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>

      <div class='titletop'>
          <div class='imgbox'>
              <div class="imgboxtop" >报修总量</div>
              <div class="imgboxleft">{{getCount.bx}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >派单总量</div>
              <div class="imgboxleft">{{getCount.pd}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >维修总量</div>
              <div class="imgboxleft">{{getCount.wx}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >评价总数</div>
              <div class="imgboxleft">{{getCount.pj}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >好评总数</div>
              <div class="imgboxleft">{{getCount.hp}}</div>
          </div>
      </div>


      <div class='contentbox'>


          <div class='contenttop'>

              <div class='contenttopleft'>
                  <div class='repairtype'>报修类型分析</div>
                    <div id="myChart3" class='myChart3'></div>
                    <div class='bottomline'></div>
              </div>

              <div class='distribution'>
                  <div class='repairtype'>派单情况分析</div>
                  <div id="myChart2" class='myChart2'></div>
                    <div class='bottomline'></div>
              </div>


          </div>



          <div class='contentright'>

              <div class='evaluation'>
                <div class='repairtype'>报修情况分析</div>
                  <div id="myChart1" class='myChart1'></div>
                    <div class='bottomline'></div>
              </div>

          </div>



      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        sendOrders:{},
        statistics:[],
        getCount:[],
        countByBm:[],
        countname:[],
        countmonth:[],
        countquarter:[],
        countyear:[],
        restaurantCode:'',//树状图id
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.treelist()
      this.getData()
    },
    mounted(){
      // this.trigonometry()
      // this.distribution()
      // this.evaluation()
    },
    methods: {
      handleNodeClick (data) {
        this.restaurantCode=data.id
      },

      // 树状图接口
      treelist(){

      },
      // 获取页面数据
      getData(){
        this.$instance({
          url:'Ufrepair.do/getData',
          method:'post',
        }).then(res=>{
          this.getCount=res.data.getCount
          this.sendOrders=res.data.sendOrders
          this.statistics=res.data.evaluationAnalysis
          this.countByBm=res.data.countByBm
          this.countname=this.countByBm.map(item=>{
            return item.M_BXDW
          })
           this.countmonth=this.countByBm.map(item=>{
            return item.month
          })
           this.countquarter=this.countByBm.map(item=>{
            return item.quarter
          })
           this.countyear=this.countByBm.map(item=>{
            return item.year
          })
          this.trigonometry()
          this.distribution()
          this.evaluation()
        })
      },
      // 报修类型分析图
      trigonometry(){
        var myChart = this.$echarts.init(document.getElementById('myChart1'));
         myChart.setOption({
                legend: {
                data: ['本年', '本季度','本月'],
                x:'right',
                y:'10px',
                textStyle:{
                  color:'white',
                  fontSize:12,
                },
              },
                xAxis:{
                 type: 'category',
                  data:this.countname,
                  axisLabel: {
                    color: 'white',
                    textStyle: {
                      fontSize: 14
                    }
                  },
                  nameLocation:{
                    color:'red'
                  },
                  axisTick: {
                        alignWithLabel: true
                    }
                },
                tooltip: {},
                dataset: {

            },
            yAxis: [{
              type:'value',
              splitNumber: 7,
              splitLine:{show: true},//去除网格线
               axisLabel: {
                   textStyle: {
                       color: '#fff'
                   }
               }
            },
                  {
                  type: 'value',
                  position: 'right',
                  splitLine:{show: false},//去除网格线
                  axisLabel: {
                      show:false,
                      interval: 'right',
                      formatter: '{value}%',
                      textStyle: {
                          color: '#fff'
                      }
                },
              }
            ],
            series: [
                {
                  type: 'bar',
                  name:'本年',
                  color:'#FFD4B1',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:true,
                    position: 'top',
                    color:'#fff'
                  },
                  data: this.countyear
                },
                {
                  type: 'bar',
                  name:'本季度',
                  color:'#B3EEFF',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:true,
                    position: 'top',
                    color:'#fff'
                  },
                  data: this.countquarter
                },
                {
                  type: 'bar',
                  name:'本月',
                  color:'#D7C9ED',
                  yAxisIndex: 1,
                  barWidth:'20',
                  label:{
                      show:true,
                      position:'top',
                      color:'#fff',
                      formatter:'{c}'
                  },
                  data:this.countmonth
                }
            ]
        });
      },
      // 派单情况分析图
      distribution(){
                var myChart2 = this.$echarts.init(document.getElementById('myChart2'));
                myChart2.setOption({
                legend: {
                data: ['派单量', '保修量','派单率'],
                x:'center',
                y:'bottom',
                textStyle:{
                  color:'white',
                  fontSize:12,
                },
              },
                xAxis:{
                 type: 'category',
                  data:[{
                    value: '本月',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '上月',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '本季度',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '上季度',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '本年',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  {
                    value: '上年',
                    textStyle: {
                        fontSize: 14,
                        color: '#fff'
                    }
                  },
                  ],
                  nameLocation:{
                    color:'red'
                  },
                  axisTick: {
                        alignWithLabel: true
                    }
                },
                tooltip: {},
                dataset: {

            },
            yAxis: [{
              type:'value',
              splitNumber: 7,
              splitLine:{show: true},//去除网格线
               axisLabel: {
                   textStyle: {
                       color: '#fff'
                   }
               }
            },
                  {
                  type: 'value',
                  position: 'right',
                  splitLine:{show: false},//去除网格线
                  axisLabel: {
                      show:true,
                      interval: 'right',
                      formatter: '{value}%',
                      textStyle: {
                          color: '#fff'
                      }
                },
              }
            ],
            series: [
                {
                  type: 'bar',
                  name:'派单量',
                  color:'#C2D7FF',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:true,
                    position: 'top',
                    color:'#fff'
                  },
                  data: [this.sendOrders.thisMonthPd,this.sendOrders.lastMonthPd, this.sendOrders.thisQuarterPd,this.sendOrders.lastQuarterPd, this.sendOrders.thisYearPd, this.sendOrders.lastYearPd]
                },
                {
                  type: 'bar',
                  name:'保修量',
                  color:'#FFC4C3',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:true,
                    position: 'top',
                    color:'#fff'
                  },
                  data: [this.sendOrders.thisMonthBx, this.sendOrders.lastMonthBx, this.sendOrders.thisQuarterBx,this.sendOrders.lastQuarterBx, this.sendOrders.thisYearBx, this.sendOrders.lastYearBx]
                },
                {
                  type: 'line',
                  name:'派单率',
                  color:'#9BBB59',
                  yAxisIndex: 1,
                  label:{
                      show:true,
                      position:'inside',
                      color:'#fff',
                      formatter:'{c}%'
                  },
                  data: [this.sendOrders.thisMonthRate,this.sendOrders.lastMonthRate, this.sendOrders.thisQuarterRate,this.sendOrders.lastQuarterRate, this.sendOrders.thisYearRate, this.sendOrders.lastYearRate]
                }
            ]
        })
      },
    //报修评价分析图
      evaluation(){
        var myChart3 = this.$echarts.init(document.getElementById('myChart3'));
        myChart3.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        top: 10,
                        left: 200,
                        data: ['1星', '2星', '3星', '4星', '5星'],
                        textStyle:{
                          color:'#fff',
                          fontSize:'12'
                        }
                    },
                    series: [
                        {
                            type: 'pie',
                            radius: '75%',
                            center: ['50%', '55%'],

                            clockwise:false,
                            // selectedMode: 'single',
                            data: [
                                {value: this.statistics.oneStar, name: '1星',itemStyle:{color:'#F79646'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {value: this.statistics.twoStar, name: '2星',itemStyle:{color:'#4BACC6'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {value: this.statistics.threeStar, name: '3星',itemStyle:{color:'#8064A2'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {value: this.statistics.fourStar, name: '4星',itemStyle:{color:'#B65708'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c}',
                                    }},
                                {
                                  value: this.statistics.fiveStar,
                                  name: '5星',
                                  itemStyle:{color:'#6FBDD1'},
                                  label: {
                                        position: 'inner',
                                        show:true,
                                        formatter:'{c}',
                                    }}
                            ],
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },

                    ]
                }

        )
      }
    }
  }
</script>

<style lang="less" scoped>
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
    margin-bottom:26px ;
  }
  .el-tree{
    background:transparent;
    color: white;
  }
  /deep/.el-tree-node__label{
    font-size:14px;
  }
  /deep/.el-tree-node.is-current > .el-tree-node__content {
    background-color: transparent !important;
  }
  .tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    height: 860px;
    float: right;
    overflow-y:auto ;
  }

  .queryform{
    width:100%;
    margin-top:0px;
    margin-bottom:0px;
  }
  .iptrow{
    width:100%;
    height:56px;
    margin-bottom:0px;
    padding: 14px 0px 0px 50px;
    box-sizing: border-box;
  }
  .iptcol{
    height:40px;
    line-height: 40px;
    margin-bottom:0;
  }
  /deep/.el-input{
      width: 200px;
  }
  /deep/.el-input__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:0px;
    height:32px;
    padding: 0 30px;
  }
  /deep/.el-textarea {
    width: 1300px;
    height: 200px;
}
/deep/.el-textarea__inner {
    width: 1300px;
    height: 200px;
    background-color: black;
    color: white;
    font-size: 12px;
}
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
  .search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .search:hover{
    cursor: pointer;
  }

  /deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
  }
  /deep/.el-tree-node__content{
    background:transparent;
  }
  /deep/.el-range-input{
    background-color:transparent;
  }
  /deep/.el-range-separator{
    color:white;
  }
  button:hover{
    background:url(../../assets/zy2.png) no-repeat;
    background-size:100% 100%;
  }
  .bcol /deep/.el-form-item__content{
    width:70%;
  }
  /deep/.el-date-editor{
    width: 200px;
  }
  /deep/.el-range__icon{
    line-height: 20px;
    margin-left: -5px;
  }
  /deep/.el-input__icon{
    width: 25px;
  }
  /deep/.el-range-input{
    font-size: 12px;
  }
  /deep/.el-range-separator{
    font-size: 12px;
    line-height: 28px;
  }
  /deep/.el-input__icon{
    line-height: 28px;
    width:25px;
  }
  /deep/.el-input__suffix{
    right: 5px;
  }
  .titletop{
      margin:20px 0;
  }
  .imgbox{
      width: 270px;
      height: 102px;
      background:url(../../assets/u239.svg)no-repeat;
      background-size:100% 100%;
      display: inline-block;
      margin-right:30px;
      color: white;
  }
  .imgboxtop{
      width: 270px;
      height: 40px;
      line-height:50px;
      text-align: center;
      font-size: 12px;
  }
  .imgboxleft{
      width: 270px;
      height: 62px;
      line-height: 62px;
      font-size: 32px;
      font-weight: 500;
      text-align: center;
  }

  .contentbox{
      width: 100%;
      height: 700px;
  }
  .contenttop{
      width: 100%;
      height: 48%;
      vertical-align: top;
      position: relative;
      margin-bottom: 30px;
  }
  .contenttopleft{
      width: 48%;
      height: 100%;
      display: inline-block;
      margin: 0 40px 0 0 ;
      position: relative;
  }
  .contentright{
      width: 100%;
      height: 70%;
      display: inline-block;
      vertical-align: top;
  }
  .repairtype{
      width: 100%;
      height: 40px;
      line-height: 40px;
      padding:0 0 0 5px;
      display: inline-block;
      box-sizing: border-box;
      color: white;
      background: url(../../assets/topline.png)no-repeat;
      background-size: 100% 100%;
      font-size: 12px;
  }
  .bottomline{
      width: 100%;
      height: 5px;
      background: url(../../assets/botline.png)no-repeat;
      background-size:100% 100%;
      position: absolute;
      bottom:0;
  }
  .distribution{
      width: 48%;
      height: 100%;
      display: inline-block;
      position: relative;
  }
  .evaluation{
      width: 100%;
      height: 460px;
      position: relative;
  }
/deep/.el-rate{
    display: inline-block;
    vertical-align: middle;
}

/deep/.el-rate__icon{
    font-size: 32px;
}
.myChart1{
  width: 100%;
  height: 400px;
}
.myChart2{
  width: 100%;
  height: 290px;
}
.myChart3{
  width: 100%;
  height: 290px;
}

</style>

