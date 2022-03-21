<template>
  <div class='container'>
    <div class='meatitle'>菜品量价管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
       <el-row class="top">
          <el-col :span='15' class='iptcol'>
            <el-form-item label="菜品名称:">
              <el-input class="inputwidth"  v-model="queryform.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="querylist">查询</button>
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
        <el-table-column align="center"  min-width="15%" prop="dishname" label="菜品名称"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="choosingmeals" label="参考价格(元)"></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="choosingmeals" label="售价(元)" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="timepice" label="订餐时间内价格(元)" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.timepice" placeholder="请输入内容" @blur="handleEdit(scope.row)"></el-input> <span></span>
          </template>
        </el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="outtimepice" label="订餐时间外价格(元)" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.outtimepice" placeholder="请输入内容" @blur="handleEdit(scope.row)"></el-input> <span></span>
          </template>
        </el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="employeecardprice" label="员工卡价格(元)" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.employeecardprice" placeholder="请输入内容" @blur="handleEdit(scope.row)"></el-input> <span></span>
          </template>
        </el-table-column>
        <el-table-column align="center" min-width="15%"  prop="temporarycardPrice" label="临时卡价格(元)" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="small" v-model="scope.row.temporarycardPrice" placeholder="请输入内容" @blur="handleEdit(scope.row)"></el-input> <span></span>
          </template>
        </el-table-column>
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
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id

        queryform: {
          name:'',
        },
        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.treelist()
      this.list()
    },
    methods: {

      handleSizeChange (val) {
        this.pageSize = val
        this.list()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.list()
        // console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.list()
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      // datachange(arr){
      //   if(arr){
      //     this.queryform.startTime=arr[0]
      //     this.queryform.endTime=arr[1]
      //   }else{
      //     this.queryform.startTime=''
      //     this.queryform.startTime=''
      //   }
      // },
      // 树状图接口
      treelist(){
        this.$axios({
          url:'StOperationrestaurant.do/getStOperationList',
          method:'get'
        }).then(res=>{
          console.log(res.data)
          this.data=res.data
        })
      },

      // 菜品量价管理查询
      list(){
        this.$axios({
          url:'StQuantityprice.do/getCommodityList ',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "param":{
              "dishname":this.queryform.name,
              "restaurant":this.restaurantCode
      }
          }
        }).then(res=>{
          console.log(res.data)
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      querylist(){
        this.list()
      },
      handleEdit(row) {
        // console.log(row.id);
        this.$axios({
          url:'StQuantityprice.do/amendPrice',
          method:'post',
          data:{
            "id":row.id,
            "timepice":row.timepice,
            "Outtimepice":row.outtimepice,
            "employeecardprice":row.employeecardprice,
            "temporarycardPrice":row.temporarycardPrice
          }
        }).then(res=>{
          this.list()
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
  .inputwidth{
    width: 160px;
  }
  .top{
    margin-top: 15px;
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
    height: 620px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 580px!important;
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
</style>

