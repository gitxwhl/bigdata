<template>
  <div class='container'>
    <div class='meatitle'>食堂经营成本统计</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='dataleftbox'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item >
              <el-date-picker
                v-model="queryform.startTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox">-</span>
            </el-form-item>
          </el-col>

          <el-col :span='5' class='iptcol'>
            <el-form-item>
              <el-date-picker
                v-model="queryform.endTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="pagelist">查询</button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="18%" prop="scheduled" label="时间"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="mealtype" label="餐别"></el-table-column>
        <el-table-column type='index' align="center"  min-width="15%" label="售出菜品数量" show-overflow-tooltip>
          <template slot-scope="scope"  class='relative'>
            <div @mouseenter="enter(scope.$index)" @mouseleave="leave(scope.$index)">
               {{scope.row.cnt}}
               <div style="display:none;" class="enterbox">
                    <table border="1" class="nuttable">
                            <tr class='tablehead'>
                                <td >菜品名称</td>
                                <td >菜品分类</td>
                                <td >菜品口味</td>
                                <td >菜品类别</td>
                                <td >线上销量</td>
                              </tr>
                             <tr v-for="(item,index) in scope.row.saleInfo" :key='index'>
                              <td>{{item.NAME}}</td>
                              <td>{{item.dishescategory}}</td>
                              <td>{{item.taste}}</td>
                              <td>{{item.category}}</td>
                              <td>{{item.dishNum}}</td>
                            </tr>
                     </table>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="cost" label="经营成本" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="income" label="经营收入" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="profit" label="经营利润" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="retainedProfits" label="净利润" show-overflow-tooltip></el-table-column>
      </el-table>


      



      <div style="text-align: right; margin-top: 10px;height:'15%'">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageIndex"
          :page-sizes="pageSizes"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
    <div class='datarightbox'>
        <div class='imgbox'>
            <div class='boxhead'>净利润</div>
            <div class='boxbody'>{{tRetainedProfits}}</div>
        </div>
        <div class='imgbox'>
            <div class='boxhead'>经营利润</div>
            <div class='boxbody'>{{tProfit}}</div>
        </div>
        <div class='imgbox'>
            <div class='boxhead'>经营收入</div>
            <div class='boxbody'>{{tIncome}}</div>
        </div>
        <div class='imgbox'>
            <div class='boxhead'>经营成本</div>
            <div class='boxbody'>{{tCost}}</div>
        </div>
    </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dialogNutVisible:[],
        saleInfo:[],
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        queryform: {
          startTime:'',
          endTime:''
        },
        tableData: [],
        tCost:'',
        tIncome:'',
        tProfit:'',
        tRetainedProfits:'',
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.treelist()
      this.pagelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
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
          console.log(res)
          this.data=res.data
        })
      },
      // 食堂经营成本统计
      pagelist(){
        this.$axios({
          url:'StFinanceManagement.do/costStatistics',
          method:'post',
          data:{
            "pageNum":this.pageIndex,
            "pageSize":this.pageSize,
            "restaurant":this.restaurantCode,
            "startTime":this.queryform.startTime,
            "endTime":this.queryform.endTime
          }
        }).then(res=>{
            this.tableData=res.data.pageBean.list
            this.saleInfo=res.data.pageBean.list.saleInfo
            this.dialogNutVisible=res.data.pageBean.list.map(item=>{return false})
            this.total=res.data.pageBean.totalRecord
            this.tCost=res.data.tCost
            this.tIncome=res.data.tIncome
            this.tProfit=res.data.tProfit
            this.tRetainedProfits=res.data.tRetainedProfits
        })
      },
     enter(a){
       this.dialogNutVisible[a]=true
       console.log(this.dialogNutVisible)
       document.querySelectorAll('.enterbox')[a].style.display='block'
     },
     leave(a){
        document.querySelectorAll('.enterbox')[a].style.display='none'

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
  .inputwidth{
    width: 160px;
  }
  .tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    overflow-y: auto;
    box-sizing: border-box;
    border-radius: 0;
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
  .el-pagination /deep/.el-input__inner{
    padding:0px;
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
  /deep/.el-table{
    background-color:transparent;
    height: 820px!important;
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
  .fhbox{
    margin-left: 20px;
    font-size: 16px;
    color: white;
  }
  .dataleftbox{
      display: inline-block;
      width: 78%;
  }
  .datarightbox{
      width: 20%;
      margin:60px 0 0 20px;
      display: inline-block;
      vertical-align: top;
  }
  /deep/.el-table__body-wrapper{
      height: 780px;
      overflow-y: auto;
  }
  .boxhead{
        font-size: 12px;
        text-align: center;
        background: url(../../assets/top.png)no-repeat;
        background-size:100% 100% ;
        line-height:55px;
        color:white;
        width: 312px;
        height: 36px;
        background-size:cover;
  }
  .boxbody{
        font-size:72px;
        text-align: center;
        line-height: 173px;
        background: url(../../assets/body.png)no-repeat;
        background-size:100% 100% ;
        color:white;
        width: 312px;
        height: 173px;
        background-size:cover;
  }
  .imgbox{
      margin-bottom:20px ;
  }
  .enterbox{
  position: absolute;
  z-index: 99;
  left:10px;
  top: 10px;
  width: 500px;
  background:black;
  border: 1px solid white;
  font-size: 12px;
  box-sizing: border-box;
}
.nuttable{
    color: white;
    width:90%;
    margin: 10px auto;
    table-layout:fixed;
    background:transparent;
    border-collapse: collapse;
}
.nuttable td{
    height:25px;
    padding:0 0 0 5px;
    font-size: 10px;
    box-sizing: border-box;
    background:transparent;
    border:1px solid rgb(128,128,128);
}
.tablehead{
    background:transparent;

}
.nuttable tr td{
    background:transparent!important;
}
.tablehead td{
    width: 30px;
    height:30px;
    text-align: center;
    font-size: 12px;
}
.relative{
  position: relative;
  z-index: 1;
}
</style>