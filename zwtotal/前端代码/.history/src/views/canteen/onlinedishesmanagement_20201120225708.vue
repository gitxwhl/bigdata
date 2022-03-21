<template>
    <div class='container'>
        <div class='meatitle'>线上菜品管理</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                <el-col :span='5' class='iptcol'>
                    <el-form-item label="用餐日期:">
                        <el-date-picker
                            v-model="queryform.date"
                            value-format="yyyy-MM-dd"
                            type="date"
                            placeholder="请选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span='15' class='iptcol'>
                    <el-form-item  label="餐别:">
                        <el-select  v-model="queryform.mealType">
                             <el-option v-for="item in mealType" :key="item.id" :label="item.text" :value="item.id">
                             </el-option>
                        </el-select>
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
             height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="restaurant" label="餐厅"> </el-table-column>
                <el-table-column align="center"  min-width="6%" prop="timestamp" label="日期"></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="mealType" label="餐别" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="name" label="菜品名称" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="5%"  prop="dishescategory" label="分类" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="taste" label="口味" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="referenceprice" label="价格（元）" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="weight" label="重量（g）" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="energy" label="能量(KJ)" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="onlineId" label="营养成分分析" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span  class="editicon"><img src="../../assets/u8075.svg" alt=""  @click="nutanaly(scope.row.dishcode)"> 查看详情</span>
                    </template>
                </el-table-column>
                <el-table-column align="center"  min-width="7%" label="是否支持线上预定" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span @click="updateReserve(scope.row.onlineId,scope.row.isreserve)" class='updateReserve'>{{scope.row.isreserve}}</span>
                    </template>
                </el-table-column>
                <el-table-column align="center"  min-width="7%" label="是否支持配送" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span @click="updateDistribution(scope.row.onlineId,scope.row.isdistribution)" class='updateReserve'>{{scope.row.isdistribution}}</span>
                    </template>
                </el-table-column>
        </el-table>
        <el-dialog  title="营养成分表" :visible.sync="dialogNutVisible">
                               <table border="1" class="nuttable">
                                    <tr class='tablehead'>
                                        <td>能量</td>
                                        <td>每100克(g)</td>
                                        <td>营养素参考值%</td>
                                    </tr>
                                    <tr v-for="item in nuttable" :key="item.name">
                                        <td>{{item.name}}</td>
                                        <td>{{item.energy}}</td>
                                        <td>{{item.energyPro}}%</td>
                                    </tr>
                                </table>
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
      dialogNutVisible:false,
      nuttable:[],
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      queryform: {
        date: '',
        mealType: ''
      },
      mealType:[],
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
        url:'StOnlinemanagement.do/dropDownBox',
        method:'get'
      }).then(res=>{
        this.mealType=res.data.mealType
      })
    },
    //线上菜品管理列表
    pagelist(){
        this.$axios({
            url:'StOnlinemanagement.do/getOnlineList',
            method:'post',
            data:{
                "pageNum": this.pageIndex,
                "pageSize":this.pageSize,
                "data":this.queryform.date,
                "mealType":this.queryform.mealType,
                "restaurant":this.restaurantCode
            }
        }).then(res=>{
            this.tableData=res.data.list
            this.total=res.data.totalRecord
        })
    },
    // 是否支持线上预订
    updateReserve(id,isReserve){
        this.$axios({
            url:'StOnlinemanagement.do/updateReserve',
            method:'post',
            data:{
                "id":id,
                "isReserve":isReserve
            }
        }).then(res=>{
            if(res.data=='修改成功'){
                this.$message.success('修改成功')
                this.pagelist()
            }else{
                this.$message.error('修改失败')
            }
        })
    },
    //是否支持配送
    updateDistribution(id,isDistribution){
        this.$axios({
            url:'StOnlinemanagement.do/updateDistribution',
            method:'post',
            data:{
                "id":id,
                "isDistribution":isDistribution
            }
        }).then(res=>{
            if(res.data=='修改成功'){
                this.$message.success('修改成功')
                this.pagelist()
            }else{
                this.$message.error('修改失败')
            }
        })
    },
    // 营养成分分析
    nutanaly(discode){
        this.dialogNutVisible=true
        this.$axios({
            url:'StOnlinemanagement.do/getNutritional',
            method:'post',
            data:{
                "dishcode":discode
            }
        }).then(res=>{
            this.nuttable=res.data
        })
    },
     //关闭事件
    closeaddDialog(queryform){
      this.$refs[queryform].resetFields()
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
  width: 200px;
}
/deep/.el-input__icon{
  width: 25px;
  line-height: 28px;
}
.editicon img{
  width:18px;
  height:16px;
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

</style>
