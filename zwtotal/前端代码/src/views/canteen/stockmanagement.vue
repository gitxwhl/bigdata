<template>
  <div class='container'>
    <div class='meatitle'>入库管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>

    <el-card class='tablecard'>
      <div class='repairtype'>库存出入库统计</div>
      <div  style="width: 100%;height: 350px;background: #010B31;display: flex">
        <div style="width:60%;height: 550px;position: relative">
          <div  class='inputtime2' style="color: white;margin-left: 20px">开始时间:<el-date-picker
            v-model="time"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="请选择日期">
          </el-date-picker>
            <button  class='search' @click.prevent>查询</button>
          </div>

          <div id="myChart1" class='myChart1'></div>

        </div>

        <div style="width:40%;height: 350px;">
          <div class='inputtime'>开始时间:<el-date-picker
            v-model="time"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="请选择日期">
          </el-date-picker>
            <button style="margin-left: 120px"  class='search' @click.prevent>查询</button>
          </div>
          <div id="myChart2" class='myChart2'></div>





        </div>

      </div>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
        <el-row class="iptrow">
          <el-col :span='4' class='iptcol'>
            <el-form-item label="商品名称:">
              <el-input class='name' v-model="queryform.orderDish"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="商品类别:">
              <el-input class='name' v-model="queryform.orderNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="商品价格:">
              <el-input class='name' v-model="queryform.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="保质期:">
              <el-input class='name' v-model="queryform.baozq"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="库存量:">
              <el-input class='name' v-model="queryform.kucun"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span='4'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click='pagelist'>查询</button>
              <!--                        <button class='search' @click.prevent @click='pagelist'>查询</button>-->

            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="10%" prop="ordernumber" label="商品名称" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center"  min-width="6%" prop="reservepersonnel" label="商品类别" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="telephone" label="商品价格" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="12%"  prop="restaurant" label="货号" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="ordertime" label="生产日期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="costtotal" label="保质期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="picktime" label="生产厂商" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="deduction" label="生产许可证" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="launchtime" label="产品编号" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="shelf" label="库存量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="cabinet" label="最后入库时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="state" label="设置是否上架" show-overflow-tooltip></el-table-column>
        <!--                <el-table-column align="center"  min-width="6%"  prop="dishId" label="订单详情" show-overflow-tooltip>-->
        <!--                    <template slot-scope="scope">-->
        <!--                        <span  class="editicon"><img src="../../assets/u3963.svg" alt=""  @click="getorder(scope.row.ordernumber)"> 查看</span>-->
        <!--                    </template>-->
        <!--                </el-table-column>-->
      </el-table>

      <div style="text-align: right; margin-top: 10px;height:'15%'">
        <el-pagination
          style="height:100px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageIndex"
          :page-sizes="pageSizes"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        // dialogNutVisible:false,
        time:'',
        nuttable:[],
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        order:{},
        restaurantCode:'',//树状图id
        queryform: {
          orderDish: '',
          orderNum: '',
          price:'',
          baozq:'',
          kucun:'',

        },
        mealType:[],
        deduction:[],
        tableData: [],
        tableDataname:[],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      // this.treelist()
      // this.selectlist()
      // this.pagelist()

    },
    mounted() {
      this.trigonometry()
      this.echarts2()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize=val
        this.pagelist()
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
        console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.pagelist()
        console.log(data)
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      // 树状图接口
      treelist(){
        this.$axios({
          url:'StOperationrestaurant.do/getStOperationList',
          method:'get'
        }).then(res=>{
          // console.log(res)
          this.data=res.data
        })
      },
      // 下拉框数据
      selectlist(){
        this.$axios({
          url:'StOrderManagement.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.mealType=res.data.mealType
          this.deduction=res.data.deduction
        })
      },
      //订单列表查询
      pagelist(){
        this.$axios({
          url:'StOrderManagement.do/getOrder',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "restaurant":this.restaurantCode,
            "orderDish":this.queryform.orderDish,
            "orderNum":this.queryform.orderNum,
            "reservePerson":this.queryform.reservePerson,
            "scheduled":this.queryform.scheduled,
            "mealType":this.queryform.mealType,
            "deduction":this.queryform.deduction
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },

      trigonometry(){
        var myChart = this.$echarts.init(document.getElementById('myChart1'));
        myChart.setOption({
          title:{
            text:'各类商品销售占比',
            left:'360',
            top:'0',
            bottom:'0',
            right: '130',
            textStyle:{
              color:'white',
              fontSize:'18',
              fontWeight:'100'
            }
          },
          legend: {
            data: ['入库总件数', '出售总件数'],
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
            data:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"] ,
            axisLabel: {
              color: 'white',
              textStyle: {
                fontSize: 14
              },
              // formatter:function (value) {
              //   return value.split("").join("\n")
              // }
              interval: 0,
              // rotate:45,
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
              name:'入库总件数',
              color:'#FFD4B1',
              yAxisIndex: 0,
              barWidth:'20',
              label:{
                show:true,  //在上方显示数值
                position: 'top',
                color:'#fff'
              },
              data: [23,45,34,67,45,43,52,34,67,45,43,52]
            },
            {
              type: 'bar',
              name:'出售总件数',
              color:'#B3EEFF',
              yAxisIndex: 0,
              barWidth:'20',
              label:{
                show:true,  //在上方显示数值
                position: 'top',
                color:'#fff'
              },
              data:[12,34,54,34,43,45,50,34,67,45,43,52]
            },

          ]
        });

      },
      echarts2(){
        var myChart2 = this.$echarts.init(document.getElementById('myChart2'));
        myChart2.setOption({
          title:{
                  text:'各类商品销售占比',
                  left:'160',
                  top:'20',

                   right: '130',
                  textStyle:{
                      color:'white',
                      fontSize:'18',
                      fontWeight:'100'
                  }
              },
          tooltip: {
            trigger: 'item',
            // formatter: '{a} <br/>{b} : {c} ({d}%)'
            formatter: '{b} : {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            right: '10%',
            bottom:'center',
            data: ['粮油调味', '食品牛奶', '生鲜果蔬', '熟食卤味','干活杂粮'],
            textStyle:{
              color:'#fff',
              fontSize:'12'
            }
          },
          series: [
            {
              type: 'pie',
              radius: '65%',
              center: ['40%', '55%'],
              clockwise:true,
              // selectedMode: 'single',
              data: [
                {value: 4, name: '粮油调味',itemStyle:{color:'#5E8AC1'},label:{
                    position: 'inner',
                    show:true,
                    formatter: '{c} ({d}%)',
                  }},
                {value:5, name: '食品牛奶',itemStyle:{color:'#C66563'},label:{
                    position: 'inner',
                    show:true,
                    formatter: '{c} ({d}%)',
                  }},
                {value:5, name: '生鲜果蔬',itemStyle:{color:'red'},label:{
                    position: 'inner',
                    show:true,
                    formatter: '{c} ({d}%)',
                  }},
                {value:6, name: '熟食卤味',itemStyle:{color:'#B5CC87'},label:{
                    position: 'inner',
                    show:true,
                    formatter: '{c} ({d}%)',
                  }},
                {value: 7, name: '干活杂粮',itemStyle:{color:'#917AAE'},label:{
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

    },

  }
</script>

<style lang="less" scoped>
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
    font-size: 16px;
  }
  .inputtime{
    /*position: absolute;*/
    top: 12%;
    left: 85%;
    color: white;
    font-size: 12px;
    margin: 0px 0 -50px 350px;
  }
  .myChart1{
    width: 100%;
    height: 100%;
  }
  .myChart2{
    width: 100%;
    height: 100%;
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
    overflow: auto;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    /*height: 860px;*/
    float: right;
  }
  /deep/.number,/deep/.btn-prev,/deep/.btn-next{
    background: transparent;
    color: white;
  }
  /deep/.el-pagination button:disabled{
    background: transparent;
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
    padding: 14px 0px 0px 10px;
    box-sizing: border-box;
  }
  .iptcol{
    height:40px;
    line-height: 40px;
    margin-bottom:0;
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
  }
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px;
    color: rgb(255,255,255);
  }
  .search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .search:hover{
    cursor: pointer;
  }
  /deep/.el-table{
    background-color:transparent;
  }
  /deep/.el-table__body-wrapper{
    height: 660px!important;
    overflow-y:auto;
  }
  /deep/.cell{
    line-height: 16px;
  }
  /deep/.el-table td,
  /deep/.el-table th.is-leaf{
    border-top:none;
    border-bottom:1px solid rgb(121,121,121);
    border-collapse: collapse;

  }
  /deep/.el-table tr{
    background-color: transparent;
  }
  /deep/.el-table__row{
    background: transparent;
  }
  /deep/.el-table th, .el-table tr {
    background-color:transparent;
  }

  /deep/.el-table--striped .el-table__body tr.el-table__row--striped td{
    background-color:rgb(30,33,66);
  }
  /deep/.el-table tbody tr:nth-child(odd):hover>td {
    background-color:transparent!important;
  }
  /deep/.el-dialog{
    background: rgba(0,0,0,.8);
    border:1px solid rgba(255,255,255,.5);
    width: 700px;
  }
  /deep/.el-dialog__header{
    padding:10px;
    text-align: center;
    background:transparent;
    border-bottom:1px solid rgba(255,255,255,.5);
    margin-bottom:20px;
  }
  /deep/.el-dialog__title{
    line-height: 24px;
    font-size: 14px;
    color:rgb(255,255,255);
  }
  /deep/.el-dialog__body{
    color:rgb(255,255,255);
    background:transparent;
    padding-bottom:2px;
    padding:0 20px 0 10px;
    margin-bottom: 80px;
    margin-top: 30px;
  }
  /deep/.el-dialog__footer{
    color:rgb(255,255,255);
    padding-top: 0px;
    background:transparent;
  }
  /deep/.el-dialog__title{
    font-size: 14px;
    color:rgb(255,255,255);
  }
  /deep/.el-dialog__headerbtn{
    top:20px;
    right:20px;
    height: 18px;
    line-height: 18px;
  }
  /deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;
  }
  /deep/.el-textarea__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 600px;
    height: 140px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:4px;
  }
  /deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
  }
  /deep/.el-tree-node__content{
    background:transparent;
  }
  button:hover{
    background:url(../../assets/zy2.png) no-repeat;
    background-size:100% 100%;
  }
  .el-input{
    width: 140px;
  }
  /deep/.el-input__icon{
    width: 25px;
    line-height: 28px;
  }
  .editicon img{
    width:22px;
    height:26px;
    vertical-align: middle;
  }
  .editicon img:hover{
    cursor: pointer;
  }
  .updateReserve:hover{
    cursor: pointer;
  }
  .nuttable tr td{
    background:transparent!important;
  }
  .nuttable{
    width:100%;
    table-layout:fixed;
    background:transparent;
    border-collapse: collapse;
  }
  .nuttable td{
    height:30px;
    padding:0 0 0 5px;
    font-size: 10px;
    box-sizing: border-box;
    background:transparent;
    border:1px solid rgb(128,128,128);
  }

  .tablehead td{
    width: 120px;
    height:30px;
    text-align: center;
    font-size: 12px;
  }
  /deep/.el-date-editor{
    width: 160px;
  }
  /deep/.el-date-editor /deep/.el-input__inner{
    padding: 0 30px;
  }
  .el-select /deep/.el-input{
    width: 123px;
  }
  .el-select /deep/.el-input__inner{
    padding: 0 30px 0 15px;
  }
  .dialogttext span{
    display: inline-block;
    width: 220px;
    font-size: 12px;
    margin: 0 0 15px 0;
  }
  .dialog /deep/.el-table{
    background-color:transparent;
    height: 200px!important;
    margin-bottom: 20px;
  }
  .dialog /deep/.el-table__body-wrapper{
    height: 200px!important;
  }
  .amount{
    height: 40px;
    background: url(../../assets/1_u3579.png) no-repeat;
    background-size: 100% 100%;
    line-height: 40px;
    text-align: right;
    padding-right:30px ;
    font-size: 14px;
  }
  .orderstate{
    font-size: 12px;
    text-align: right;
    padding:0 40px 20px 0;
    box-sizing: border-box;
  }
</style>
