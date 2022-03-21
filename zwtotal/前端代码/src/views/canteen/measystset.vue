<template>
    <div class='container'>
        <div class='meatitle'>计量系统设置</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current="true" :props="defaultProps" ref='tree' @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                <el-col :span='6' class='iptcol'>
                    <el-form-item label="餐盘检测时间:">
                        <el-input v-model="queryform.platedetectionTime"></el-input>  <span style="color:white;"> 秒</span>
                    </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                    <el-form-item label="菜品同步时间:">
                        <el-input v-model="queryform.dishTime"></el-input>  <span style="color:white;"> 秒</span>
                    </el-form-item>
                </el-col>
                <el-col :span='6' class='iptcol'>
                    <el-form-item label="剩余预警重量:">
                        <el-input v-model="queryform.warningWeight"></el-input>  <span style="color:white;"> 秒</span>
                    </el-form-item>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click="querylist">查询</button>
                    </el-form-item>
                </el-col>
                 <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='increase' @click.prevent @click="addlist">设置</button>

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
                <el-table-column align="center"  min-width="10%" prop="dishTime" label="菜品同步时间(秒)"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="platedetectionTime" label="餐盘检测时间(秒)"></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="warningWeight" label="剩余预警重量(秒)" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="operationreStaurant" label="运维餐厅" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="synchronizationTime" label="同步时间" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="8%"  prop="state" label="状态" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="20%" label="操作" >
                    <template slot-scope="scope">
                            <div class="binicon"><img src="../../assets/u349.svg" alt="" @click="delectfood(scope.row.id)">删除</div>
                            <div class='switbox'>
                            <el-switch v-model="scope.row.delFlag" @change='changestate(scope.row.id,scope.row.delFlag,scope.row)' 
                            active-value="1" inactive-value="0" active-color="#13ce66" inactive-color="#ff4949"></el-switch><div>变更状</div>
                            </div>
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
      switchvalue: true,
      pageIndex: 1,
      restaurantCode:'',
      code:'',
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      queryform: {
        platedetectionTime: '',
        dishTime: '',
        warningWeight:''
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
    this.querylist()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize=val
      this.querylist()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
      this.querylist()
      console.log(`当前页: ${val}`)
    },
    handleNodeClick (data) {
      this.restaurantCode=data.id
      this.code=data.restaurantCode
      this.querylist()
      console.log(data,this.restaurantCode)
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
        this.data=res.data
      })
    },
    //系统计量设置列表
    querylist(){
        this.$axios({
            url:'Stmeteringsystemsettings.do/getStmetering',
            method:'post',
            data:{
                "restaurant":this.restaurantCode,
                "platedetectionTime":this.queryform.platedetectionTime,
                "dishTime":this.queryform.dishTime,
                "warningWeight":this.queryform.warningWeight,
                "pageNum": this.pageIndex,
                "pageSize":this.pageSize,
            }
        }).then(res=>{
            this.tableData=res.data.list
            this.total=res.data.totalRecord
        })
    },
    // 删除
    delectfood(id){
      this.$confirm('确认删除该条评论?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'Stmeteringsystemsettings.do/deleteStmeteringsystemsettings',
              method:'post',
              data:{
                id:id
              }
            }).then(res=>{
              if(res.data=='删除成功'){
              this.$message.success('删除成功')
              this.querylist()
          }
        
        })
      }).catch(()=>{

      })

    },
    //状态变更
    changestate(id,state){
      if(state=="0"){
        state="1"
      }else if(state=="1"){
        state="0"
      }
      this.$axios({
        url:'Stmeteringsystemsettings.do/updateState',
        method:'post',
        data:{
          "id":id,
          "state":state
        }
      }).then(res=>{
        if(res.data=='变更成功'){
          this.$message.success('变更成功')
          this.querylist()
        }else{
          this.$message.error('变更失败')

        }
      })
    },
    // 设置新增
    addlist(){
      console.log(this.restaurantCode)
      if(this.restaurantCode==''||this.restaurantCode=='1'||this.restaurantCode=="2"){
        this.$message('请选择子餐厅')
        return
      }
      if(this.queryform.dishTime==''||this.queryform.platedetectionTime==''||this.queryform.warningWeight==''){
        this.$message('请填写时间')
        return
      }
      if(!Number(this.queryform.dishTime)||!Number(this.queryform.platedetectionTime)||!Number(this.queryform.warningWeight)){
        this.$message('时间需为数字')
        return
      }
      this.$axios({
        url:'Stmeteringsystemsettings.do/addStmeteringsystemsettings',
        method:'post',
        data:{
          "dishTime":this.queryform.dishTime,
          "platedetectionTime":this.queryform.platedetectionTime,
          "warningWeight":this.queryform.warningWeight,
          "operationreStaurant":this.code,
        }
      }).then(res=>{
        if(res.data=="设置成功"){
        this.$message.success('新增成功')
        this.querylist()
        }
      })
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
    margin-right: 20px;
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
    width:82%;
    height: 860px;
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
.el-input {
  width: 190px;
}
.el-row span{
  font-size: 10px;
  line-height: 18px;
  display: inline-block;
  vertical-align: middle;
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
  padding-right:12px ;
  color: rgb(255,255,255);
}
.search,.increase{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    padding: 0;
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

/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  margin-top:300px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
  margin-bottom:20px;
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:rgba(0,0,0,.4);
  padding-bottom:2px;
  padding:0 20px 0 0;
  margin-bottom: 80px;
  margin-top: 60px;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background:rgba(0,0,0,.4);
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
.adddialog .point{
    width: 240px;
    height:100px;
    position: absolute;
    top:100px;
    right:40px;
}
.editdialog .point{
    width: 240px;
    height:100px;
    line-height:30px;
    position: absolute;
    top:100px;
    right:40px;
}
.tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
}
.el-switch{
    margin-left:100px;
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
/deep/.el-switch__core{
  vertical-align: middle;
  width: 40px!important;
  height: 20px!important;
}
/deep/.el-switch{
  height: 20px;
  line-height: 20px;
  font-size: 14px;
}
.switbox{
  height: 36px;
  line-height: 36px;
  display: inline-block;
}
.switbox div{
  display:inline-block;
}
/deep/.el-switch.is-checked /deep/.el-switch__core::after{
  left: 100%;
    margin-left: -17px;
}
/deep/.el-switch__core:after{
  width: 16px;
  height: 16px;
  top:1px;
  left:1px;
}
</style>
