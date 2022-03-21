<template>
    <div class='container'>
        <div class='meatitle'>保洁执行情况检查维护</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
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
                    <el-form-item label="检查范围:">
                           <el-input  v-model="queryform.reservePerson"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="检查时间:">
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
                <el-table-column align="center"  min-width="6%"  prop="telephone" label="检查范围" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="restaurant" label="检查时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="ordertime" label="检查区域" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="scheduled" label="检查人员" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="dictionary" label="评分标准" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="costtotal" label="备注" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="15%"  prop="dishId" label="操作" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <div class='operate' @click="grade(scope.row.id)"><span>打分</span></div>
                        <div class='operate' @click="amend(scope.row.id)"><span>修改</span></div>
                    </template>
                </el-table-column>
        </el-table>



    <!-- 评价 -->
        <el-dialog class='asessdialog'  title="保洁评估打分" :visible.sync="dialoggradeVisible" @close='qxscoring'>
                    <div class='projectdetails'>
                        <div class='absolute'>计划详情</div>
                        <div class='detailsbox'>计划名称:</div>
                        <div class='detailsbox'>计划类型:</div>
                        <div class='detailsbox'>计划时间:</div>
                        <div class='detailsbox'>检查区域:</div>
                        <div class='detailsbox'>检查部位:</div>
                        <div class='detailsbox'>检查范围:</div>
                        <div class='details'>评分标准:</div>
                        <div>备注:</div>
                    </div>
                    <div class='projectdetails'>
                        <div class='absolute'>评估打分</div>
                        <div class='ratebox'>评分项1:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'>评分项2:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'>评分项3:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'>评分项4:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'></div>
                        <div class='ratebox'>综合评分:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                    </div>
                    <div slot="footer" class="dialog-footer">
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click='scoring'>确定</el-button>
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click="qxscoring">取消</el-button>
                    </div>
          </el-dialog>



          <!-- 修改 -->
        <el-dialog class='asessdialog'  title="保洁评估修改" :visible.sync="dialogamendVisible" @close='qxscoring'>
                    <div class='projectdetails'>
                        <div class='absolute'>计划详情</div>
                        <div class='detailsbox'>计划名称:</div>
                        <div class='detailsbox'>计划类型:</div>
                        <div class='detailsbox'>计划时间:</div>
                        <div class='detailsbox'>检查区域:</div>
                        <div class='detailsbox'>检查部位:</div>
                        <div class='detailsbox'>检查范围:</div>
                        <div class='details'>评分标准:</div>
                        <div>备注:</div>
                    </div>
                    <div class='projectdetails'>
                        <div class='absolute'>评估打分</div>
                        <div class='ratebox'>评分项1:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'>评分项2:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'>评分项3:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'>评分项4:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                        <div class='ratebox'></div>
                        <div class='ratebox'>综合评分:<el-rate v-model="value1" class="rateclass"></el-rate></div>
                    </div>
                    <div slot="footer" class="dialog-footer">
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click='scoring'>确定</el-button>
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click="qxscoring">取消</el-button>
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
      dialoggradeVisible:false,
      dialogamendVisible:false,
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
      tableData: [{},{}],
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
    margin-top:60px;
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
    width: 127px;
    height:40px;
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
    height: 640px!important;
}
/deep/.el-table__body-wrapper{
    height: 600px!important;
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
</style>
