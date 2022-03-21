<template>
  <div class='container'>
    <div class='meatitle'>菜品查询</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
        <el-row class="iptrow">
          <el-col :span='4' class='iptcol'>
            <el-form-item label="商品名称:">
              <el-input class='name' v-model="queryform.Name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="订单编号:">
              <el-input class='name' v-model="queryform.Num"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="购买人员:">
              <el-input class='name' v-model="queryform.PurchasingPersonnel"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="购买日期:">
              <el-date-picker
                v-model="queryform.datePurchase"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item  label="扣费情况:">
              <el-select  v-model="queryform.deduction">
                <el-option v-for="item in deduction" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span='4'  class='iptcol'>
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
                height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="6%" prop="reservepersonnel" label="订单编号" show-overflow-tooltip></el-table-column>

        <el-table-column align="center"  min-width="10%" prop="ordernumber" label="商品名称" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="telephone" label="购买人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="12%"  prop="restaurant" label="联系电话" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="ordertime" label="下单商店" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="costtotal" label="下单时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="picktime" label="商品类型" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="deduction" label="费用合计" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="launchtime" label="扣费情况" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="shelf" label="订单状态" show-overflow-tooltip></el-table-column>

                        <el-table-column align="center"  min-width="6%"  prop="dishId" label="订单详情" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <span  class="editicon"><img src="../../assets/u3963.svg" alt=""  @click="getorder(scope.row.ordernumber)"> 查看</span>
                            </template>
                        </el-table-column>
      </el-table>
      <el-dialog class='dialog'  title="预定信息" :visible.sync="dialogaddVisible">
        <div>
          <el-row>
            <el-col :span='8' class='iptcol'>
              订单编号:
            </el-col>
            <el-col :span='8' class='iptcol'>
              下单时间:
            </el-col>
            <el-col :span='8' class='iptcol'>
              下单商店:
            </el-col>
          </el-row>
          <el-row>
            <el-col :span='8' class='iptcol'>
              购买人员:
            </el-col>
            <el-col :span='8' class='iptcol'>
              联系电话:
            </el-col>
            <el-col :span='8' class='iptcol'>
              扣费情况:
            </el-col>
          </el-row>
          <el-row>
            <el-col :span='8' class='iptcol'>
              购买日期:
            </el-col>
            <el-col :span='8' class='iptcol'>
              商品类型:
            </el-col>
          </el-row>
          <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                    height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
            <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
            <el-table-column align="center"  min-width="6%" prop="reservepersonnel" label="订单编号" show-overflow-tooltip></el-table-column>
            <el-table-column align="center"  min-width="10%" prop="ordernumber" label="商品名称" show-overflow-tooltip> </el-table-column>
            <el-table-column align="center"  min-width="6%"  prop="telephone" label="购买人员" show-overflow-tooltip></el-table-column>
            <el-table-column align="center"  min-width="12%"  prop="restaurant" label="联系电话" show-overflow-tooltip></el-table-column>
            <el-table-column align="center"  min-width="6%"  prop="ordertime" label="下单商店" show-overflow-tooltip></el-table-column>
            <el-table-column align="center"  min-width="6%"  prop="costtotal" label="下单时间" show-overflow-tooltip></el-table-column>
          </el-table>
          <el-pagination
            style="height:100px;float: right"
            @size-change="handleSizeChange2"
            @current-change="handleCurrentChange2"
            :current-page="pageIndex2"
            :page-sizes="pageSizes2"
            :page-size="pageSize2"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total2">
          </el-pagination>
        </div>
        <div style="margin-right: 30px">
          <el-row>

            <el-col :span='5' class='iptcol'>
              合计：
            </el-col>
          </el-row>
        </div>
        <div style="margin-top: 30px">
          <el-row>
            <el-col :span='5' class='iptcol'>
              上架时间：
            </el-col>
            <el-col :span='5' class='iptcol'>
              上架人员：
            </el-col>
            <el-col :span='5' class='iptcol'>
              上架餐柜：
            </el-col>
          </el-row>
        </div>

      </el-dialog>
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
        dialogaddVisible:false,
        nuttable:[],
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        pageIndex2: 1,
        pageSize2:10,
        pageSizes2:[ 10, 20 , 30, 50, 100],
        total2:0,
        order:{},
        restaurantCode:'',//树状图id
        queryform: {
          Name: '',//购买名称
          Num: '',//购买数量
          PurchasingPersonnel:'',//购买人员
          datePurchase:'',//购买日期
          Deduction:'',//扣费情况

        },
        mealType:[],
        deduction:[],
        tableData: [],
        tableData2: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.treelist()
      this.selectlist()
      this.pagelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize=val
        this.pagelist()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
        // console.log(`当前页: ${val}`)
      },
      handleSizeChange2 (val) {
        this.pageSize2=val
        // this.pagelist()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange2 (val) {
        this.pageIndex2=val
        // this.pagelist()
        // console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.pagelist()
        // console.log(data)
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

      //查看订单详情
      getorder(ordernumber){
        this.dialogaddVisible=true
        this.$axios({
          url:'StOrderManagement.do/getOrderByNo',
          method:'post',
          data:{
            "orderNumber":ordernumber
          }
        }).then(res=>{
          this.order=res.data
          this.tableDataname=res.data.dishs
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
  .btnbox{
    text-align: right;
    margin:40px 0 0px 0;
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
