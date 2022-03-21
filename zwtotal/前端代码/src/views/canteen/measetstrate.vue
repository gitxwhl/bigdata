<template>
    <div class='container'>
        <div class='meatitle'>计量结算策略</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                <el-col :span='18' class='iptcol'>
                    <el-form-item label="结算金额:">
                        <el-select v-model="queryform.settlementamount">
                                    <el-option v-for="item in settlementamount" :key="item.id" :label="item.text" :value="item.id">
                                  </el-option>
                              </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click="querylist">查询</button>
                    </el-form-item>
                </el-col>
                 <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>
                        <el-dialog class="adddialog" title="添加计量结算策略" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">
                            <el-form :model="addform" ref='addform' class="addcategory">
                                <el-row>
                                    <el-col :span='12'>
                                        <el-form-item label="策略名称:" prop="policyName">
                                            <el-input class='ipt' v-model="addform.policyName"></el-input>
                                        <span class="tab">*</span>
                                            </el-form-item>
                                    </el-col>
                                    <el-col :span='12'>
                                        <el-form-item label="策略编码:" prop="policyCode">
                                        <el-input class='ipt' v-model="addform.policyCode"></el-input>
                                    <span class="tab">*</span>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span='12'>
                                         <el-form-item  label="运维餐厅:" prop="restaurant">
                                            <el-select class='ipt' v-model="addform.restaurant"  >
                                            <el-option v-for="item in restaurant" :key="item.RestaurantCode" :label="item.RestaurantName" :value="item.RestaurantCode">
                                        </el-option>
                                    </el-select>
                                        <span class="tab">*</span>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span='12'> <el-form-item label="关联设备:"  prop="meteringequipment">
                                            <el-select class='ipt' v-model="addform.meteringequipment">
                                            <el-option v-for="item in equipment" :key="item.id" :label="item.equipmentname" :value="item.id">
                                        </el-option>
                                    </el-select>
                                    <span class="tab">*</span>
                                        </el-form-item>
                                            </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span='12'>
                                    <el-form-item label="适用时间:"  prop="applicabletime">
                                      <el-select class='ipt' v-model="addform.applicabletime">
                                            <el-option v-for="item in applicabletime" :key="item.id" :label="item.text" :value="item.id">
                                        </el-option>
                                    </el-select><span class="tab">*</span>
                                     </el-form-item>
                                    </el-col>
                                    <el-col :span='12'>
                                    <el-form-item label="结算金额:"  prop="settlementamount">
                                      <el-input class='ipt'  v-model="addform.settlementamount"></el-input> 元<span class="tab">*</span>
                                     </el-form-item>
                                    </el-col>
                                </el-row>

                                <el-form-item label="备注:" prop="remarks">
                                  <el-input style="width:100px;height:60px;" type="textarea" v-model="addform.remarks"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="addpolicybtn('addform')">添加</el-button>
                              </div>
                        </el-dialog>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
        <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="15%" prop="policyname" label="策略名称"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="policycode" label="策略编码"></el-table-column>
                <el-table-column align="center"  min-width="15%"  prop="restaurant" label="运维餐厅" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="meteringequipment" label="计量设备" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="applicabletime" label="适用时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="settlementamount" label="结算金额" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="15%"  prop="remarks" label="备注" show-overflow-tooltip></el-table-column>
                <el-table-column  min-width="20%" label="操作" class='aligncenter'>
                    <template slot-scope="scope">
                            <span  class="binicon"><img src="../../assets/u349.svg" alt=""  @click="deletepolicy(scope.row.id)">删除</span>
                            <span  class="editicon"><img src="../../assets/u363.svg" alt=""  @click="dialogeditVisible = true"> 修改</span>
                           <el-dialog class="editdialog" title="修改计量结算策略" :visible.sync="dialogeditVisible" @close="closereviseDialog('reviseform')">
                            <el-form :model="reviseform" ref="reviseform" class="addcategory">
                                <el-row>
                                    <el-col :span='12'>
                                        <el-form-item   label="策略名称:" prop="policyName">
                                            <el-input  class='ipt' v-model="reviseform.policyName"></el-input>
                                        <span class="tab">*</span>
                                            </el-form-item>
                                    </el-col>
                                    <el-col :span='12'>
                                        <el-form-item  label="策略编码:" prop="policyCode">
                                        <el-input  class='ipt' v-model="reviseform.policyCode"></el-input>
                                    <span class="tab">*</span>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span='12'>
                                         <el-form-item  label="运维餐厅:" prop="restaurant">
                                            <el-select  class='ipt' v-model="reviseform.restaurant"  >
                                            <el-option v-for="item in restaurant" :key="item.RestaurantCode" :label="item.RestaurantName" :value="item.iRestaurantCoded">
                                        </el-option>
                                    </el-select>
                                        <span class="tab">*</span>
                                            </el-form-item>
                                            </el-col>
                                            <el-col :span='12'> <el-form-item label="关联设备:"   prop="meteringequipment">
                                            <el-select  class='ipt' v-model="reviseform.meteringequipment">
                                            <el-option v-for="item in equipment" :key="item.id" :label="item.equipmentname" :value="item.id">
                                        </el-option>
                                    </el-select>
                                    <span class="tab">*</span>
                                        </el-form-item>
                                            </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span='12'>
                                    <el-form-item label="适用时间:" prop="applicabletime">
                                      <el-select  class='ipt' v-model="reviseform.applicabletime">
                                            <el-option v-for="item in applicabletime" :key="item.id" :label="item.text" :value="item.id">
                                        </el-option>
                                    </el-select><span class="tab">*</span>
                                     </el-form-item>
                                    </el-col>
                                    <el-col :span='12'>
                                    <el-form-item label="结算金额:" prop="settlementamount">
                                      <el-input  class='ipt' v-model="reviseform.settlementamount"></el-input> 元<span class="tab">*</span>
                                     </el-form-item>
                                    </el-col>
                                </el-row>

                                <el-form-item label="备注:" prop="remarks">
                                  <el-input style="width:100px;height:60px;" type="textarea" v-model="reviseform.remarks"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="revisepolicy(scope.row.id)">修改</el-button>
                              </div>
                        </el-dialog>
                    </template>
                </el-table-column>
        </el-table>
      <div class='pagbox'>
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
      dialogaddVisible: false,
      dialogeditVisible: false,
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      settlementamount:[],//结算金额
      applicabletime:[],//适用时间
      equipment:[],//关联设备
      restaurant:[],//运维餐厅
      queryform: {
        settlementamount: '',
      },
      reviseform:{
        policyName:'',
        policyCode:'',
        restaurant:'',
        meteringequipment:'',
        applicabletime:'',
        settlementamount:'',
        remarks:''
      },
      addform: {
        policyName:'',
        policyCode:'',
        restaurant:'',
        meteringequipment: '',
        applicabletime:'',
        settlementamount:'',
        remarks:''
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
    selectable(row,index){
      return 0
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
    // 下拉框数据
    selectlist(){
      this.$axios({
        url:'StOperationrestaurant.do/dictionaries',
        method:'get'
      }).then(res=>{
        console.log(res)
        this.settlementamount=res.data.settlementamount
        this.applicabletime=res.data.applicabletime
        this.equipment=res.data.equipment
        this.restaurant=res.data.restaurant
      })
    },
    // 策略列表
    pagelist(){
      this.$axios({
        url:'StOperationrestaurant.do/getStrategyList',
        method:'post',
        data:{
        "pageNum": this.pageIndex,
        "pageSize":this.pageSize,
        "param": {
                "restaurantName": this.restaurantCode,
                "settlementamount": this.queryform.settlementamount,
                  }
         }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.list
        this.total=res.data.totalRecord
      })
    },
    // 查询列表
  querylist(){
    this.pagelist()
    this.queryform.settlementamount=''
  },
  // 删除策略
  deletepolicy(id){
      this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StOperationrestaurant.do/deleteStrategy',
              method:'post',
              data:{
                id:id
              }
            }).then(res=>{
        this.$message.success('删除成功')
        this.pagelist()
        })
      }).catch(()=>{

      })
      
    },
    // 新增策略
    addpolicybtn(queryform){
      if(this.addform.policyName==''||this.addform.policyCode==''||this.addform.restaurant==''
      ||this.addform.meteringequipment==''||this.addform.applicabletime==''||this.addform.settlementamount==''){
          this.dialogaddVisible = false
          this.$refs[queryform].resetFields()
          this.$message('带*号项不能为空');
          return
      }
        this.$axios({
          url:'StOperationrestaurant.do/insertStrategy',
          method:'post',
          data:{
            "param":{
            "policyName":this.addform.policyName,
            "policyCode": this.addform.policyCode,
            "restaurant": this.addform.restaurant,
            "meteringequipment":this.addform.meteringequipment,
            "applicabletime": this.addform.applicabletime,
            "settlementamount":this.addform.settlementamount,
            "remarks":this.addform.remarks
            }
        }
        }).then(res=>{
          console.log(res)
          this.pagelist()
          this.$message.success('新增成功')
        })
        this.$refs[queryform].resetFields()
        this.dialogaddVisible = false
    },
    // 修改排菜
    revisepolicy(id){
      if(this.reviseform.policyName==''||this.reviseform.policyCode==''||this.reviseform.restaurant==''
      ||this.reviseform.meteringequipment==''||this.reviseform.applicabletime==''||this.reviseform.settlementamount==''){
      this.dialogeditVisible = false
      this.closereviseDialog('reviseform')
      this.$message('带*号项不能为空');
      return
      }
      this.$axios({
        url:'StOperationrestaurant.do/updateStrategy',
        method:'post',
        data:{
          "param":{
            "id":id,
            "policyName":this.reviseform.policyName,
            "policyCode": this.reviseform.policyCode,
            "restaurant": this.reviseform.restaurant,
            "meteringequipment":this.reviseform.meteringequipment,
            "applicabletime": this.reviseform.applicabletime,
            "settlementamount":this.reviseform.settlementamount,
            "remarks":this.reviseform.remarks
            }
            }
      }).then(res=>{
        this.pagelist()
        this.$message.success('修改成功')
      })
      this.closereviseDialog('reviseform')
      this.dialogeditVisible = false
    },
    //关闭事件
    closeaddDialog(queryform){
      this.$refs[queryform].resetFields()
    },
    closereviseDialog(queryform){
      this.$refs[queryform].resetFields()
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
    overflow: auto;
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
    
}
.el-row /deep/.el-input__inner{
    width: 200px;
    padding:0 30px 0 15px;
}
/deep/.el-form-item__label{
  font-size: 12px;
  line-height: 28px;
  padding-right:12px ;
  color: rgb(255,255,255);
}
.search,.increase{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search,.increase:hover{
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
.binicon{
  width:80px;
  height: 30px;
  line-height:30px;
  float: left;
}
.binicon img{
    width:18px;
    height:16px;
    vertical-align: middle;
}
.binicon img:hover{
  cursor: pointer;
}
.editicon{
  width:80px;
  height: 20px;
  line-height:30px;
  float: right;
  margin-right:50px ;
}
.editicon img{
  width:18px;
  height:16px;
  vertical-align: middle;
}
.editicon img{
  cursor: pointer;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  margin-top:300px;
  width: 700px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
  margin-bottom:20px;
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 20px;
  margin-bottom: 80px;
  margin-top: 60px;
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
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;

}
.btnaddchild,.btnedit{
  background-color:rgb(32,47,66);
  width: 60px;
  height:30px;
  line-height:0;
  padding:10px 0 10px 5px;
  font-size: 12px;
}
/deep/.btnaddchild span{
  line-height:0;
  margin-right:5px ;
}
.addcategory /deep/.el-textarea__inner{
    background: black;
    border:1px solid rgb(52,57,82);

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

.tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
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

/deep/.el-input__icon{
  line-height: 28px;
  width:25px;
}
/deep/.el-input__suffix{
  right: 25px;
}
.pagbox{
  text-align: right; 
  margin-top: 10px;
  height:'15%'
}
.ipt{
  width: 220px;
}
/deep/.el-form-item__content{
  margin-bottom:20px;
  font-size: 12px;
}
.editdialog /deep/.el-form-item{
  margin-bottom:0;
}
</style>
