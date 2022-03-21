<template>
  <div class='container'>
    <div class='meatitle'>库存统计</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <div class='topbox'>
            <div class='disinblock'>当前待入库 :{{headerdata.drk}} </div>
            <div class='disinblock'>当前待上架 :{{headerdata.dsj}} </div>
            <div class='disinblock'>当前待出库 : {{headerdata.dck}}</div>
            <div class='disinblock'>当前库存告警数 :{{headerdata.gj}} </div>
      </div>

      <div class='titletop'>
          <div class='imgbox'>
              <div class="imgboxtop" >物品库存种类</div>
              <div class="imgboxleft">{{getCount.stockKind}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >本月新增物品库存种类</div>
              <div class="imgboxleft">{{getCount.addStockKindMonth}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >本年新增物品库存种类</div>
              <div class="imgboxleft">{{getCount.addStockKindYear}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >物品库存数量</div>
              <div class="imgboxleft">{{getCount.stockCnt}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >本月新增物品库存数量</div>
              <div class="imgboxleft">{{getCount.addStockThisMonth}}</div>
          </div>
          <div class='imgbox'>
              <div class="imgboxtop" >本年新增物品库存数量</div>
              <div class="imgboxleft">{{getCount.addStockThisYear}}</div>
          </div>
      </div>


      <div class='contentbox'>


          <div class='contenttop'>

              <div class='contenttopleft'>

                  <div class='repairtype'>入库情况</div>

                  <div class='butbox'>
                    <button class='search' @click.prevent @click="kind">种类</button>
                    <button class='search' @click.prevent @click="quantity">数量</button>
                  </div>

                  <div class='roundbox'>
                    <div class='roundleft'>
                      <div class='textbox'>
                        <div v-show="yeartype" style="font-size:14px">本年入库种类</div>
                        <div v-show="yearnum" style="font-size:14px">本年入库数量</div>
                        <div style="font-size: 20px">{{warehouse.thisYear}}</div>
                      </div>
                    </div>
                    <div class='roundleft'>
                      <div class='textbox'>
                        <div v-show="oldtype" style="font-size:14px">去年入库种类</div>
                        <div v-show="oldnum" style="font-size:14px">去年入库数量</div>
                        <div style="font-size: 20px">{{warehouse.lastYear}}</div>
                      </div>
                    </div>
                  </div>

                  <div class='erlier'>
                    <div class='sonbox bdrb'>
                      <div class='fontbig'>{{warehouse.thisMonth}}</div>
                      <div class='fontsmall'>{{warehouse.thisMonthTime}}</div>
                    </div>
                    <div class='sonbox bdb'>
                      <div class='fontbig'>{{warehouse.lastMonth}}</div>
                      <div class='fontsmall'>{{warehouse.lastMonthTime}}</div>
                    </div>
                    <div class='sonbox'>
                      <div class='fontbig'>{{warehouse.lastYearTm}}</div>
                      <div class='fontsmall'>{{warehouse.lastYearTmTime}}</div>
                    </div>
                    <div class='sonbox bdlt'>
                      <div class='fontbig'>{{warehouse.lastMonth}}</div>
                      <div class='fontsmall'>{{warehouse.lastYearLmTime}}</div>
                    </div>
                    <div class='bgimg'><div>同</div><div>比</div></div>
                  </div>

                  <div class='bottomline'></div>
              </div>

              <div class='contenttopleft'>
                  <div class='repairtype'>出库情况</div>


                   <div class='butbox'>
                    <button class='search' @click.prevent @click="clearkind">种类</button>
                    <button class='search' @click.prevent @click="clearquantity">数量</button>
                  </div>



                  <div class='erlier'>
                    <div class='sonbox bdrb'>
                      <div class='fontbig'>{{shipment.thisMonth}}</div>
                      <div class='fontsmall'>{{shipment.thisMonthTime}}</div>
                    </div>
                    <div class='sonbox bdb'>
                      <div class='fontbig'>{{shipment.lastMonth}}</div>
                      <div class='fontsmall'>{{shipment.lastMonthTime}}</div>
                    </div>
                    <div class='sonbox'>
                      <div class='fontbig'>{{shipment.lastYearTm}}</div>
                      <div class='fontsmall'>{{shipment.lastYearTmTime}}</div>
                    </div>
                    <div class='sonbox bdlt'>
                      <div class='fontbig'>{{shipment.lastYearLm}}</div>
                      <div class='fontsmall'>{{shipment.lastYearLmTime}}</div>
                    </div>
                    <div class='bgimg'><div>同</div><div>比</div></div>
                  </div>



                  <div class='roundbox'>
                    <div class='roundleft'>
                      <div class='textbox'>
                        <div v-show="cleartype" style="font-size:14px">本年出库种类</div>
                        <div v-show="clearnum" style="font-size:14px">本年出库数量</div>
                        <div style="font-size: 20px">{{shipment.thisYear}}</div>
                      </div>
                    </div>
                    <div class='roundleft'>
                      <div class='textbox'>
                        <div v-show="oldcleartype" style="font-size:14px">去年出库种类</div>
                        <div v-show="oldclearnum" style="font-size:14px">去年出库数量</div>
                        <div style="font-size: 20px">{{shipment.lastYear}}</div>
                      </div>
                    </div>
                  </div>



                    <div class='bottomline'></div>
              </div>

              <div class='distribution'>
                  <div class='repairtype'>出库总金额统计</div>
                  <div id="myChart2" class='myChart2'></div>
                  <div class='inputtime'>开始时间:<el-date-picker
                      v-model="time"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd"
                      type="date"
                      placeholder="请选择日期">
                    </el-date-picker>
                    </div>
                <div class='inputtime2'>结束时间:<el-date-picker
                  v-model="endtime"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="请选择日期">
                </el-date-picker>
                  <button style="margin-top: 10px;margin-left: 170px" class='search' @click.prevent @click="queryData">查询</button>
                </div>
                    <div class='countzj'>出库总金额合计:{{this.Totaloutgoingamount.all}}</div>
                    <div class='bottomline'></div>
              </div>


          </div>



          <div class='contentright'>

              <div class='evaluation'>
                <div class='repairtype'>各部门本季度领用情况</div>
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

        time:'',
        endtime:'',
        sendOrders:{},
        statistics:[],
        headerdata:[],
        getCount:[],
        Totaloutgoingamount:[],
        warehouse:[],//入库情况
        shipment:[],//出库情况
        countByBm:[],
        countname:[],
        countmonth:[],
        countquarter:[],
        officestationery:[],
        countyear:[],
        restaurantCode:'',//树状图id
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        },
        yeartype:true,
        yearnum:false,
        oldtype:true,
        oldnum:false,
        cleartype:true,
        clearnum:false,
        oldcleartype:true,
        oldclearnum:false

      }
    },
    created(){
      this.treelist()
      this.getData()
      this.StorageType()
      this.deliveryType()
      // this.deliveryNum()
      // this.StorageNum()
      this.outStockPrice()
      this.Recipients()
    },
    mounted(){
      // this.echarts2()
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
        this.$office({
          url:'Tstock.do/inventoryStatistics',
          method:'post',
      //     data:{
      //       "index": "",			//入库情况标记     种类：1     数量：2
      //       "index1":"",			///出库情况标记     种类：1     数量：2
      //       "date":this.time			//时间
      // }
        }).then(res=>{
          // console.log(res.data.stockInfo)
            this.headerdata=res.data.stockInfo
            this.getCount=res.data.stockCount
            this.countByBm=res.data.receiveInfo

          // console.log(this.countByBm)
          //   this.countname=this.countByBm.map(item=>{
          //     return item.ORGNAME
          //   })
          this.echarts2()
        })
      },
      //入库情况(种类)
      StorageType(){
        this.$office({
          url:'Tstock.do/inStock',
          method:'post',
          data:{
            "index": "1",			//入库情况标记     种类：1     数量：2

          }
        }).then(res=>{

          this.warehouse=res.data
          // console.log(this.warehouse)
        })
      },
      //入库情况(数量)
      StorageNum(){
        this.$office({
          url:'Tstock.do/inStock',
          method:'post',
          data:{
            "index": "2",			//入库情况标记     种类：1     数量：2

          }
        }).then(res=>{

          this.warehouse=res.data
          // console.log(this.warehouse)
        })
      },
      //入库种类
      kind(){
        this.yeartype=true
        this.yearnum=false
        this.oldtype=true
        this.oldnum=false
        this.StorageType()
      },
      //入库数量
      quantity(){
        this.yeartype=false
        this.yearnum=true
        this.oldtype=false
        this.oldnum=true
        this.StorageNum()
      },
      //出库情况(种类)
      deliveryType(){
        this.$office({
          url:'Tstock.do/outStock',
          method:'post',
          data:{
            "index": "1",			//入库情况标记     种类：1     数量：2

          }
        }).then(res=>{
          this.shipment=res.data

        })
      },
      //出库情况(数量)
      deliveryNum(){
        this.$office({
          url:'Tstock.do/outStock',
          method:'post',
          data:{
            "index": "2",			//入库情况标记     种类：1     数量：2

          }
        }).then(res=>{
          // console.log(res)
          this.shipment=res.data

        })
      },
    //出库种类
      clearkind(){
        this.cleartype=true
        this.clearnum=false
        this.oldclearnum=false
        this.oldcleartype=true
        this.deliveryType()
      },
      //出库数量
      clearquantity(){
        this.cleartype=false
        this.clearnum=true
        this.oldclearnum=true
        this.oldcleartype=false
        this.deliveryNum()
       },
      //出库金额统计
      outStockPrice(){
        this.$office({
          url:'Tstock.do/outStockPrice',
          method:'post',
          data:{

            "beginDate": this.time,	//开始时间
            "endDate": this.endtime		//结束时间
          }
        }).then(res=>{
          // console.log(res.data)
          this.Totaloutgoingamount=res.data       //出库总金额统计
          this.echarts2()
        })

      },
      //查询
      queryData(){
        this.outStockPrice()
  },
      //各部门本季度领用情况
      Recipients(){
        this.$office({
          url:'Tstock.do/inventoryStatistics',
          method:'post',
        }).then(res=>{
         // console.log(res.data.receiveInfo)
          this.countByBm=res.data.receiveInfo

          this.countname=this.countByBm.map(function(name){
            return name.ORGNAME
          })
           this.countmonth=this.countByBm.map(item=>{
            return item.equipment  //办公设备
          })
           this.countquarter=this.countByBm.map(item=>{
            return item.equipment  //会议用品
          })
           this.countyear=this.countByBm.map(item=>{
            return item.paper  //打印纸
          })
          this.officestationery=this.countByBm.map(item=>{
            return item.stationery  //办公文具
          })

          this.trigonometry()
        })

      },
      // 报修类型分析图
      trigonometry(){
        var myChart = this.$echarts.init(document.getElementById('myChart1'));
         myChart.setOption({
                legend: {
                data: ['打印纸', '办公文具','办公设备','会议用品'],
                x:'right',
                y:'10px',
                textStyle:{
                  color:'white',
                  fontSize:12,
                },
              },

           grid:{
             // left:"20%",   //grid 组件离容器左侧的距离
             // right:"30px", //grid 组件离容器右侧的距离
             bottom:"45%"  //grid 组件离容器下边距的距离
           },
                xAxis:{
                 type: 'category',
                  data:this.countname,
                  axisLabel: {
                    color: 'white',
                    textStyle: {
                      fontSize: 14
                    },
                    // formatter:function (value) {
                    //   return value.split("").join("\n")
                    // }
                    interval: 0,
                    rotate:45,
                    // formatter: function(value) {
                    //   return value.split("").join("\n");
                    // }
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
                  name:'打印纸',
                  color:'#FFD4B1',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:false,  //在上方显示数值
                    position: 'top',
                    color:'#fff'
                  },
                  data: this.countyear
                },
                {
                  type: 'bar',
                  name:'办公文具',
                  color:'#B3EEFF',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:false,  //在上方显示数值
                    position: 'top',
                    color:'#fff'
                  },
                  data: this.officestationery
                },
                {
                  type: 'bar',
                  name:'办公设备',
                  color:'#D7C9ED',
                  yAxisIndex: 1,
                  barWidth:'20',
                  label:{
                      show:false,  //在上方显示数值
                      position:'top',
                      color:'#fff',
                      formatter:'{c}'
                  },
                  data:this.countmonth
                },
                {
                  type: 'bar',
                  name:'会议用品',
                  color:'#B3EEFF',
                  yAxisIndex: 0,
                  barWidth:'20',
                  label:{
                    show:false,  //在上方显示数值
                    position: 'top',
                    color:'#fff'
                  },
                  data: this.countquarter
                },
            ]
        });
      },

    echarts2(){
        var myChart2 = this.$echarts.init(document.getElementById('myChart2'));
        myChart2.setOption({
                    // title:{
                    //         text:'灌溉计划检查情况分析',
                    //         left:'center',
                    //         top:'10',
                    //         textStyle:{
                    //             color:'white',
                    //             fontSize:'14',
                    //             fontWeight:'100'
                    //         }
                    //     },
                    tooltip: {
                        trigger: 'item',
                        // formatter: '{a} <br/>{b} : {c} ({d}%)'
                      formatter: '{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        right: '10%',
                        bottom:'center',
                        data: ['办公设备出库总金额', '办公文具出库总金额', '打印纸出库总金额', '会议用品出库总金额'],
                        textStyle:{
                          color:'#fff',
                          fontSize:'12'
                        }
                    },
                    series: [
                        {
                            type: 'pie',
                            radius: '75%',
                            center: ['30%', '45%'],
                            clockwise:true,
                            // selectedMode: 'single',
                            data: [
                                {value: this.Totaloutgoingamount.equipment, name: '办公设备出库总金额',itemStyle:{color:'#5E8AC1'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {value:this.Totaloutgoingamount.stationery, name: '办公文具出库总金额',itemStyle:{color:'#C66563'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {value: this.Totaloutgoingamount.paper, name: '打印纸出库总金额',itemStyle:{color:'#B5CC87'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
                                    }},
                                {value: this.Totaloutgoingamount.meeting, name: '会议用品出库总金额',itemStyle:{color:'#917AAE'},label:{
                                        position: 'inner',
                                        show:true,
                                        formatter: '{c} ({d}%)',
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
                })
    },

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
    /*height: 860px;*/
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
      width: 230px;
      height: 102px;
      background:url(../../assets/3333.png) no-repeat;
      background-size:100% 100%;
      display: inline-block;
      margin-right:20px;
      color: white;
  }
  .imgboxtop{
      width: 230px;
      height: 40px;
      line-height:50px;
      text-align: center;
      font-size: 12px;
  }
  .imgboxleft{
      width: 230px;
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
      height: 54%;
      vertical-align: top;
      position: relative;
      margin-bottom: 30px;
  }
  .contenttopleft{
      width: 28%;
      height: 100%;
      display: inline-block;
      vertical-align: top;
      margin: 0 10px 0 0 ;
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
      width: 40%;
      height: 100%;
      display: inline-block;
      position: relative;
  }
  .countzj{
      position: absolute;
      bottom: 16%;
      left: 67%;
      color: white;
      font-size: 12px;
  }
  .inputtime{
    position: absolute;
    top: 10%;
    left: 55%;
    color: white;
    font-size: 12px;
    margin: 20px 0 0 0;
  }
  .inputtime2{
    position: absolute;
    top: 20%;
    left: 55%;
    color: white;
    font-size: 12px;
    margin: 20px 0 0 0;
  }
  .evaluation{
      width: 100%;
      height: 560px;
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
  height: 560px;
}
.myChart2{
  width: 100%;
  height: 100%;
}
.topbox{
  width: 100%;
  height:50px;
  line-height: 50px;
  background-color: #34394E;
  text-align: center;
  color: white;
  font-size: 14px;
}
.disinblock{
  display: inline-block;
  margin-right: 20px;
}
.butbox{
  margin:15px 0 5px 20px;
  box-sizing: border-box;
}
.roundbox{
  width: 100%;
  height: 130px;
  text-align: center;
}
.roundleft{
  display: inline-block;
  vertical-align: top;
  margin:0 10px 10px 0;
  width: 40%;
  height: 100%;
  background: url(../../assets/u7750.svg)no-repeat;
  background-size: cover;
  position: relative;
}
.textbox{
  color: white;
  font-size: 12px;
  position: absolute;
  top:50%;
  left: 50%;
  margin-left: -35px;
}
.erlier{
  width: 100%;
  padding:5px 20px;
  box-sizing: border-box;
  position: relative;
}
.sonbox{
  width: 50%;
  height: 70px;
  display: inline-block;
  vertical-align: top;
  box-sizing: border-box;
}
.bdrb{
  border-right: 1px solid #797979;
  border-bottom: 1px solid #797979;
}
.bdlt{
  border-left: 1px solid #797979;
}
.bdb{
  border-bottom: 1px solid #797979;
}
.bgimg{
  color: white;
  font-size: 12px;
  text-align: center;
  padding:10px 0 0 0;
  box-sizing: border-box;
  width: 60px;
  height: 60px;
  background: url(../../assets/u7753.svg)no-repeat;
  background-size: 100% 100%;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -30px;
  margin-top:-30px;
}
.fontbig{
  font-size: 38px;
  text-align: center;
  color: white;
}
.fontsmall{
  font-size: 12px;
  text-align: center;
  color: white;
}
</style>

