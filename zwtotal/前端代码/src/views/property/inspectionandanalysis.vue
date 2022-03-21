<template>
    <div class='container'>
        <div class='meatitle'>保洁执行情况检查分析</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <div class='chartsfatherbox'>
                <div class='chartsleft'>
                    <div class='chartsmargin'>
                        <div class='clearbtn'>日常保洁</div>
                        <div class='clearbtn'>专项保洁</div>
                    </div>
                    <el-select v-model="date1" popper-class="selectoption" class='select'>
                        <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                        </el-option>
                    </el-select>
                    <div id="myChart1" class='myChart1'></div>
                </div>
                <div class='chartscenter'>
                    <div class='chartsmargintwo'>
                        <div class='clearbtn'>保洁计划</div>
                        <div class='clearbtn'>检查计划</div>
                    </div>
                    <el-select v-model="date1" popper-class="selectoption" class='select'>
                        <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                        </el-option>
                    </el-select>
                    <div id="myChart2" class='myChart2'></div>
                </div>
                <div class='chartsright'>
                    <div id="myChart3" class='myChart3'></div>
                </div>
            </div>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <el-row class="iptrow">
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="计划名称:">
                           <el-input  v-model="queryform.orderDish"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="计划类型:">
                           <el-input  v-model="queryform.orderNum"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="保洁区域:">
                           <el-input  v-model="queryform.reservePerson"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="保洁区域:">
                        <el-input  v-model="queryform.orderDish"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='pagelist'>查询</button>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
        <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="6%" prop="ordernumber" label="计划名称"> </el-table-column>
                <el-table-column align="center"  min-width="6%" prop="reservepersonnel" label="计划类型"></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="telephone" label="保洁区域" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="restaurant" label="保洁人员配备" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="ordertime" label="工具设备" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="scheduled" label="检查次数（次）" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="dictionary" label="综合评价" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="14%"  prop="costtotal" label="备注" show-overflow-tooltip></el-table-column>
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
      dialoggradeVisible:false,
      dialogamendVisible:false,
      date1:'',
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      queryform: {
        orderDish: '',
        orderNum: '',
        reservePerson:'',
        scheduled:'',
        mealType:'',
        deduction:''
      },
      mealType:[
          {id:'1',text:'1'},
          {id:'2',text:'2'},
          {id:'3',text:'3'}
      ],
      tableData: [{},{}],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    // this.treelist()
    this.pagelist()
  },
  mounted(){
      this.echarts1()
      this.echarts2()
      this.echarts3()
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
    grade(){
        this.dialoggradeVisible=true
    },
    amend(){
        this.dialogamendVisible=true
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
    echarts1(){
    var myChart1 = this.$echarts.init(document.getElementById('myChart1'));
    myChart1.setOption({
            title: {
                text: '日常保洁情况统计',
                top:'30px',
                left:'center',
                textStyle:{
                    color:'white',
                    fontSize:'12px'
                }
            },
            tooltip: {},
            
            radar: {
                // shape: 'circle',
                radius: 110,
                center: ['50%', '60%'],
                name: {
                    textStyle: {
                        color: '#fff',
                        backgroundColor: '',
                        borderRadius: 3,
                    }
                },
                
                splitLine:{ // (这里是指所有圆环)坐标轴在 grid 区域中的分隔线。
                    lineStyle: {
                        color: '#fff',
                        // 分隔线颜色
                        width: 1,
                        // 分隔线线宽
                    }
                },
                splitArea:{
                    show:false,
                },  
                indicator: [
                    { name: '一星' },
                    { name: '二星' },
                    { name: '三星' },
                    { name: '四星' },
                    { name: '五星' },
                ]
            },
            series: [{
                name: '',
                type: 'radar',
                label:{
                    normal:{
                    show:true,
                    color:'white',
                    formatter:'{c}'
                    }
                },
                // areaStyle: {normal: {}},
                data: [
                    {
                        value: [4300, 10000, 28000, 35000, 50000, 19000],
                        name: '保洁情况统计',
                        areaStyle:{
                            normal:{
                                color:'#407684'
                            }
                        },
                        lineStyle:{
                            color:'#407684',
                        }
                    },
                ]
            }]
        })

    },
    echarts2(){
    var myChart2 = this.$echarts.init(document.getElementById('myChart2'));
    myChart2.setOption({
            title:{
                text: '保洁计划完成情况统计',
                top:'10px',
                left:'center',
                textStyle:{
                    color:'white',
                    fontSize:'12px'
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['完成', '未完成'],
                top:10,
                right:'right',
                textStyle:{
                    color:'white'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['一区', '二区', '三区', '四区'],
                    axisLabel: {
                        fontSize: 14,
                        color: 'white'
                    }
                    
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitNumber: 3,
                     axisLabel: {
                        fontSize: 12,
                        color: '#D6FF80'
                    }
                }
            ],
            series: [
                {
                    name: '完成',
                    type: 'bar',
                    color:'#00BFBF',
                    stack: '广告',
                    barWidth:'30',
                    data: [320, 332, 301, 334],
                    label:{
                      show:true,
                      position:'inside',
                      color:'white',
                      formatter:'{c}'
                  },
                },
                {
                    name: '未完成',
                    type: 'bar',
                    color:'#80FFFF',
                    stack: '广告',
                    barWidth:'30',
                    data: [120, 132, 101, 134],
                    label:{
                      show:true,
                      position:'inside',
                      color:'white',
                      formatter:'{c}'
                  },
                },
            ]
        })
    },
    echarts3(){
     var myChart3= this.$echarts.init(document.getElementById('myChart3'));
     myChart3.setOption({
            title: {
                text: '保洁工具耗材使用统计',
                top:'40px',
                left:'center',
                textStyle:{
                    color:'white',
                    fontSize:'12px'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                right: '10px',
                bottom:'100px',
                textStyle:{
                    color:'white',
                },
                data: ['工具', '耗材']
            },
            series: [
                {
                    name: '统计',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '50%'],
                    data: [
                        {value: 335, name: '工具',itemStyle:{color:'#02A7F0'},label:{
                                        show:true,
                                        color:'white',
                                        fontSize:'12px',
                                        formatter: '{c} ({d}%)',
                                    }},
                        {value: 310, name: '耗材',itemStyle:{color:'#00BFBF'},label:{
                                        show:true,
                                        color:'white',
                                        fontSize:'12px',
                                        formatter: '{c} ({d}%)',
                                    }},
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
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
    overflow: auto;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    height: 860px;
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
    margin-top:0;
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
    width: 95px;
    height:30px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: #169BD5;
}
.search:hover{
  cursor: pointer;
}
/deep/.el-table{
    background-color:transparent;
    height:420px!important;
}
/deep/.el-table__body-wrapper{
    height: 380px!important;
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
  font-size: 18px;
  color:rgb(255,255,255);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 10px;
  margin-bottom: 20px;
  margin-top: 30px;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background:transparent;
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
  width: 170px;
}
.operate{
    display: inline-block;
    width: 50%;
    text-align: center;
}
.operate span:hover{
    cursor: pointer;
}
.asessdialog /deep/.el-dialog{
  background: #343645;
  padding:0 40px;
  box-sizing: border-box;
  border:1px solid rgba(255,255,255,.5);
  width: 900px;
}
.projectdetails{
    position: relative;
    width: 100%;
    margin:10px 0 30px 0;
    padding:30px 40px;
    border: 1px solid #444654;
    box-sizing: border-box;
    font-size: 12px;
    background-color:#303240;
}
.absolute{
    position: absolute;
    font-size: 18px;
    top:-10px;
    left:10px;
}
.detailsbox{
    display: inline-block;
    margin:10px 100px 20px 0;
}
.details{
    margin:0 0 20px 0;
}
.rateclass{
    display: inline-block;
    height:100%;
}
/deep/.el-rate__icon{
    font-size: 50px;
}
.define{
    width: 100px;
}
.asessdialog /deep/.el-dialog__header{
  padding:20px;
  box-sizing: border-box;
  text-align: left;
  font-size: 20px;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
}
.ratebox{
    display: inline-block;
    width: 50%;
    margin: 0 0 20px 0;
}
.chartsfatherbox{
    width: 100%;
    height: 450px;
    margin:60px 0 40px 0;
}
.chartsleft{
    width: 33%;
    height: 450px;
    display: inline-block;
    vertical-align: middle;
    position: relative;
}
.chartscenter{
    width: 33%;
    height: 450px;
    display: inline-block;
    vertical-align: middle;
    position: relative;
}
.chartsright{
    width: 33%;
    height: 450px;
    display: inline-block;
    vertical-align: middle;
}
.chartsmargin{
    margin:0 0 0 60px;
}
.chartsmargintwo{
    margin: 60px 0 0 20px;
}
.clearbtn{
    width: 123px;
    height: 28px;
    display: inline-block;
    color: white;
    font-size: 13px;
    text-align: center;
    line-height: 28px;
    background-color:#027DB4;
    border: 1px solid #797979;
    box-sizing:border-box;
}
.clearbtn:hover{
    cursor: pointer;
}
.select{
    position: absolute;
    top:60px;
    right:40px;
    z-index: 999;
}
.select /deep/.el-input__inner{
    background-color: white;
    color: #999;
    width: 100px;
}
.myChart1{
    width: 100%;
    height: 422px;
}
.myChart2{
    width: 90%;
    height: 350px;
}
.myChart3{
    width: 100%;
    height: 100%;
}
</style>
