<template>
  <div class='container'>
    <div class='meatitle'>台账明细</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
        <el-row class="iptrow">
          <el-col :span='4' class='iptcol'>
            <el-form-item label="商品名称:">
              <el-input class='name' v-model="queryform.orderDish"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="类别:">
              <el-input class='name' v-model="queryform.orderNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="规格:">
              <el-input class='name' v-model="queryform.price"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span='4' class='iptcol'>
            <el-form-item label="库存量:">
              <el-input class='name' v-model="queryform.kucun"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='4' class='iptcol'>
            <el-form-item label="是否上架:">
              <el-input class='name' v-model="queryform.baozq"></el-input>
            </el-form-item>
          </el-col>
          <!--                <el-col :span='4' class='iptcol'>-->
          <!--                    <el-form-item  label="扣费情况:">-->
          <!--                        <el-select  v-model="queryform.mealType">-->
          <!--                             <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">-->
          <!--                             </el-option>-->
          <!--                        </el-select>-->
          <!--                    </el-form-item>-->
          <!--                </el-col>-->
          <!--                <el-col :span='4' class='iptcol'>-->
          <!--                    <el-form-item  label="订单状态:">-->
          <!--                        <el-select  v-model="queryform.mealType">-->
          <!--                             <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">-->
          <!--                             </el-option>-->
          <!--                        </el-select>-->
          <!--                    </el-form-item>-->
          <!--                </el-col>-->
          <!--                <el-col :span='4' class='iptcol'>-->
          <!--                    <el-form-item  label="库存状态:">-->
          <!--                        <el-select  v-model="queryform.deduction">-->
          <!--                             <el-option v-for="item in deduction" :key="item.id" :label="item.text" :value="item.id">-->
          <!--                             </el-option>-->
          <!--                        </el-select>-->
          <!--                    </el-form-item>-->
          <!--                </el-col>-->
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
        <el-table-column align="center"  min-width="6%" prop="reservepersonnel" label="类别" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="telephone" label="单价" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="12%"  prop="restaurant" label="规格" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="launchtime" label="产品编号" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="costtotal" label="保质期" show-overflow-tooltip></el-table-column>

        <el-table-column align="center"  min-width="6%"  prop="cabinet" label="入库时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="shelf" label="入库数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="shelf" label="库存总量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="6%"  prop="state" label="是否上架" show-overflow-tooltip></el-table-column>
        <!--                <el-table-column align="center"  min-width="6%"  prop="dishId" label="订单详情" show-overflow-tooltip>-->
        <!--                    <template slot-scope="scope">-->
        <!--                        <span  class="editicon"><img src="../../assets/u3963.svg" alt=""  @click="getorder(scope.row.ordernumber)"> 查看</span>-->
        <!--                    </template>-->
        <!--                </el-table-column>-->
      </el-table>
      <!--                    <el-dialog class='dialog'  title="订单信息" :visible.sync="dialogNutVisible">-->
      <!--                        <div class='orderstate'>订单状态:</div>-->
      <!--                        <div class='dialogttext'>-->
      <!--                            <span>预定单号:{{order.ordernumber}}</span>-->
      <!--                            <span>下单时间:{{order.ordertime}}</span>-->
      <!--                            <span>下单餐厅:{{order.restaurant}}</span>-->
      <!--                            <span>预定人员:{{order.reservepersonnel}}</span>-->
      <!--                            <span>联系电话:{{order.telephone}}</span>-->
      <!--                            <span>扣费情况:{{order.deduction}}</span>-->
      <!--                        </div>-->
      <!--                        <el-table  stripe ref="multipleTable" :data="tableDataname" :cell-style="{padding:'0',-->
      <!--                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',-->
      <!--                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',-->
      <!--                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"-->
      <!--                                height="300px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">-->
      <!--                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>-->
      <!--                                    <el-table-column align="center"  min-width="16%" prop="name" label="商品名称"> </el-table-column>-->
      <!--                                    <el-table-column align="center"  min-width="10%"  prop="energy" label="能量/份" show-overflow-tooltip></el-table-column>-->
      <!--                                    <el-table-column align="center"  min-width="10%"  prop="weight" label="重量(g)/份" show-overflow-tooltip></el-table-column>-->
      <!--                                    <el-table-column align="center"  min-width="10%" prop="dishescategory" label="库存数量"></el-table-column>-->
      <!--                                    <el-table-column align="center"  min-width="10%"  prop="dishNum" label="购买数量" show-overflow-tooltip></el-table-column>-->
      <!--                                    <el-table-column align="center"  min-width="10%"  prop="referenceprice" label="单价" show-overflow-tooltip></el-table-column>-->
      <!--                                </el-table>-->
      <!--                <div class='amount'>合计:￥{{order.costtotal}}</div>-->
      <!--                <div class='dialogttext' style="margin-top:20px;">-->
      <!--                    <span>上架时间:{{order.launchtime}}</span>-->
      <!--                    <span>上架人员:{{order.shelf}}</span>-->
      <!--                    <span>上架餐柜:{{order.cabinet}}</span>-->
      <!--                </div>-->
      <!--                    </el-dialog>-->
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
      this.treelist()
      this.selectlist()
      this.pagelist()
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

      //查看订单详情
      getorder(ordernumber){
        this.dialogNutVisible=true
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
