<template>
    <div class='container'>
        <div class='foodtitle'>菜品类别管理</div>
    <el-card class='genrecard'>
        <div class='genrehead'>菜品类别</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                <el-col :span='7' class='iptcol'>
                    <el-form-item>
                        <el-input class='name' v-model="queryform.search"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click='pagelist' >搜索</button>
                        <!-- <button @click="submitForm('queryform')">搜索</button> -->
                    </el-form-item>
                </el-col>
                <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='add' @click.prevent  @click="dialogFormVisible = true">添加</button>

                        <!-- <button @click="submitForm('queryform')">添加</button> -->
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
            <!-- 类别添加弹出框 -->
                        <el-dialog  title="添加类别" :visible.sync="dialogFormVisible" @close="closeDialog('addform')">
                              <el-form :model="addform" ref="addform"  class="addcategory">
                                <div class='category'>所属类别: 全部
                                </div>
                               <el-form-item style="margin-bottom:10px;" label="名称:" prop="name">
                                  <el-input   v-model="addform.name" ></el-input><span class="tab">*</span>
                                </el-form-item>
                                <el-form-item style="margin-bottom:10px;" label="编码:" prop="code">
                                  <el-input   v-model="addform.code"></el-input><span class="tab">*</span>
                                </el-form-item>
                                <el-form-item label="备注:" prop="remark">
                                  <el-input  type="textarea" v-model="addform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="addcategory('addform')">添加</el-button>
                              </div>
                        </el-dialog>
            <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
              size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
         <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="20%" prop="varietiesName" label="名称" show-overflow-tooltip>
        </el-table-column>
        <el-table-column align="center"  min-width="13%" prop="varietiesCode" label="编码" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="23%" prop="affiliatedrestaurant" label="关联餐厅" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%" prop="remarks" label="备注" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%" label="关联菜品种类" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-switch v-model="scope.row.delFlag" @change='changestatus(scope.row.id,scope.row.delFlag)'
                            active-value="0" inactive-value="2" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
          </template>
        </el-table-column>
        <el-table-column align="center"  min-width="25%" prop="oeratio" label="操作" show-overflow-tooltip>
          <template slot-scope="scope">
                <img src="../../assets/u349.svg" alt="" class="binicon" @click="delect(scope.row.id)">
                <img src="../../assets/u362.svg" alt="" class="addicon" @click="addsonopen(scope.row)">
                <img src="../../assets/u363.svg" alt="" class="editicon" @click="revisesonopen(scope.row)">
          <!-- 子类别添加弹出框 -->
            <el-dialog  title="添加子类别" :visible.sync="dialogAddVisible" :append-to-body="true" @close="closesonDialog('addsonform')">
                              <el-form :model="addsonform" ref="addsonform">
                                  <div class='category'>
                                    所属类别:{{row.varietiesName}}
                                  </div>
                               <el-form-item  style="margin-bottom:0px;" label="名称:"  prop="name">
                                  <el-input  v-model="addsonform.name" ></el-input><span class="tab">*</span>
                                </el-form-item>
                                <el-form-item  style="margin-bottom:4px;" label="编码:"  prop="code">
                                  <el-input  v-model="addsonform.code"></el-input><span class="tab">*</span>
                                </el-form-item>
                                <el-form-item style="margin-bottom:0px;" label="备注:"  prop="remark">
                                  <el-input   type="textarea" v-model="addsonform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:22px;" class='btnaddchild'  type="primary" @click="addsoncate('addsonform')">添加</el-button>
                              </div>
              </el-dialog>
              <!-- 修改类别弹出框 -->
              <el-dialog  title="修改类别" :visible.sync="dialogEditVisible" :append-to-body="true" @close="closeupdateDialog('updateform')">
                              <el-form :model="updateform" ref="updateform">
                                <div class='category'>
                                    所属类别:{{row.parentName}}
                                  </div>
                               <el-form-item style="margin-bottom:0px;" label="名称:" prop="name">
                                  <el-input  v-model="updateform.name" ></el-input><span class="tab">*</span>
                                </el-form-item>
                                <el-form-item style="margin-bottom:4px;" label="编码:" prop="code">
                                  <el-input   v-model="updateform.code"></el-input><span class="tab">*</span>
                                </el-form-item>
                                <el-form-item style="margin-bottom:0px;" label="备注:" prop="remark">
                                  <el-input  type="textarea" v-model="updateform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:22px;" class='btnedit' type="primary" @click="updatedishes('updateform')">修改</el-button>
                              </div>
                </el-dialog>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagbox">
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
      row:{},
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      id:'',
      varietiesName:'全部',
      varietiesCode:null,
      parentIds:null,
      remarks:null,
      sort:null,
      createBy:null,
      updateBy:null,
      delFlag:null,
      level:null,
      queryform: {
        search: ''
      },
      data: [],
      tableData: [],
      foodcategory: [
        { value: '猪肉', text: '猪肉' },
        { value: '牛肉', text: '牛肉' },
        { value: '鸡肉', text: '鸡肉' }
      ],
      defaultProps: {
        children: 'subclass',
        label: 'varietiesName'
      },
      dialogFormVisible: false,
      dialogAddVisible: false,
      dialogEditVisible: false,
      addform: {
        name: '',
        code: '',
        genre:'',
        remark: ''
      },
       addsonform: {
        name: '',
        code: '',
        genre:'',
        remark: ''
      },
      updateform:{
        name: '',
        code: '',
        genre:'',
        remark: ''
      },
      formLabelWidth: '100px'
    }
  },
  computed: {

  },
  created () {
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
      console.log(data)
      this.id=data.id
      this.varietiesCode=data.varietiesCode
      this.varietiesName=data.varietiesName
      this.parentIds=data.parentIds
      this.remarks=data.remarks
      this.sort=data.sort
      this.createBy=data.created
      this.updateBy=data.updateBy
      this.delFlag=data.delFlag
      this.level=data.level
      this.pagelist()
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    //树状图接口
    treelist(){
      this.$axios({
        url: '/StCategorydishes.do/getStCategorydishes',
        method: 'post',
      }).then(res=>{
          console.log(res.data)
          this.data=res.data.data
      })
    },
    // 查询
    pagelist(){
        this.$axios({
                url:'/StCategorydishes.do/getStCategorydishesPage',
                method:'get',
                params:{
                  "pageNum": this.pageIndex,
                  "pageSize":this.pageSize,
                  "name":this.queryform.search,
                  "id":this.id
                }
              }).then(res=>{
                this.tableData=res.data.data.list
                this.total=res.data.data.totalRecord
              })
    },
    // 添加类别
    addcategory(queryform){
   
      if(this.addform.name==''||this.addform.code==''){
      this.dialogFormVisible= false
      this.$refs[queryform].resetFields()
      this.$message('带*号项不能为空');
      return
      }
      this.$axios({
        url:'/StCategorydishes.do/saveStCategorydishes',
        method:'post',
        data:{
          "parentIds":1,
          "varietiesName":this.addform.name,
          "varietiesCode":this.addform.code,
          "remarks":this.addform.remark,

        }
      }).then(res=>{
          if(res.data.message=="数据保存"){
          this.$message.success('添加类别成功')
          this.treelist()
          this.pagelist()
        }else{
          this.$message.error('添加类别失败')

        }
      })
      this.dialogFormVisible = false
      this.$refs[queryform].resetFields()
    },
    // 添加子类别
    addsoncate(queryform){
      if(this.addsonform.name==''||this.addsonform.code==''){
      this.dialogAddVisible= false
      this.$refs[queryform].resetFields()
      this.$message('带*号项不能为空');
      return
      }
      this.$axios({
        url:'/StCategorydishes.do/saveStCategorydishes',
        method:'post',
        data:{
          "parentIds":this.row.id,
          "varietiesName":this.addsonform.name,
          "varietiesCode":this.addsonform.code,
          "remarks":this.addsonform.remark,
        }
      }).then(res=>{
          if(res.data.message=="数据保存"){
          this.$message.success('添加子类别成功')
          this.treelist()
          this.pagelist()
        }else{
          this.$message.error('添加子类别失败')
        }
      })
      this.dialogAddVisible = false
      this.$refs[queryform].resetFields()
    },
    // 删除菜品类别
    delect(id){
      this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StCategorydishes.do/delStCategorydishes',
              method:'post',
              data:{
                id:id
              }
            }).then(res=>{
              if(res.data.data==true){
                this.$message.success('删除成功')
                this.treelist()
                this.pagelist()
              }else if(res.data.message=='存在子类无法删除'){
                this.$message.error('存在子类无法删除')
              }else{
                this.$message.error('删除失败')
              }
        })
      }).catch(()=>{

      })

    },
    // 打开子类别开关
    addsonopen(data){
      this.dialogAddVisible=true
      this.row=data
    },
    // 获取所属类别
    revisesonopen(data){
      this.dialogEditVisible=true
      this.row=data
      console.log(this.row)
    },
    // 修改类别
    updatedishes(queryform){
       if(this.updateform.name==''||this.updateform.code==''){
      this.dialogEditVisible= false
      this.$message('带*号项不能为空');
      return
      }
      this.$axios({
        url:'StCategorydishes.do/updateStCategorydishes',
        method:'post',
        data:{
          "id":this.row.id,
          "parentIds":this.row.parentIds,
          "varietiesName":this.updateform.name,
          "varietiesCode":this.updateform.code,
          "remarks":this.updateform.remark,
        }
      }).then(res=>{
        if(res.data.message=="更新成功"){
          this.$message.success('修改成功')
          this.treelist()
          this.pagelist()
        }else{
          this.$message.error('修改失败')

        }

      })
      this.$refs[queryform].resetFields()
      this.dialogEditVisible= false
    },
    // 停启用
    changestatus(id,delflag){
      console.log(id,delflag)
      this.$axios({
        url:'/StCategorydishes.do/updateStCategorydishesState',
        method:'get',
        params:{
          'id':id,
          'delFlag':delflag
        }
      }).then(res=>{
        if(res.data.message=='更新成功'){
          this.$message.success('更新成功')
          this.pagelist()
        }else{
          this.$message.error('更新失败')
        }
      })
    },
      // 关闭事件
    closeDialog(queryform){
      this.$refs[queryform].resetFields()
  },
  closesonDialog(queryform){
      this.$refs[queryform].resetFields()

  },
  closeupdateDialog(queryform){
      this.$refs[queryform].resetFields()
  },
  },
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
.foodtitle{
    margin-bottom:6px;
}
.genrecard{
    background-color:rgba(1,5,22,.4);
    overflow: auto;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width: 16%;
    height: 860px;
    float: left;
}
.genrehead{
    height:44px;
    text-align: center;
    padding-top:6px;
    box-sizing: border-box;
    font-size: 16px;
    color:rgba(255,255,255);
    margin-bottom:26px ;
}
.tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
}
.tablecard{
    float: right;
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    overflow: auto;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    height: 860px;
}
.queryform{
    width:100%;
    height: 56px;
    margin-top:0px;
    margin-bottom:0px;
}
/deep/.el-input{
    width: 110px;
}
/deep/.el-textarea{
    width: 180px;
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
.addcategory /deep/.el-input__inner{
    background: black;
}
.addcategory /deep/.el-textarea__inner{
    background: black;
    border:1px solid rgb(52,57,82);

}
/deep/.el-textarea__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:4px;
}
.search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 80px;
    padding: 0;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.add{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 80px;
    padding: 0;

    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);

}
.search,.add:hover{
    cursor: pointer;
}
.iptrow{
   width:100%;
   height:56px;
   margin-bottom:0px;
   padding: 14px 0px 0px 0px;
   box-sizing: border-box;
}
.iptcol{
    height:40px;
    margin-bottom:0;
}
/deep/.el-table{
    background-color:transparent;
    height: 720px!important;
}
 /deep/.el-table__body-wrapper{
    height: 680px!important;
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
/deep/.el-card__body{
  height:100%;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  margin-top:300px;
  width: 320px!important;

}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:rgba(0,0,0,.4);
  padding-bottom:2px;
  padding:20px 20px 0 0;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;

  background:rgba(0,0,0,.4);
}
/deep/.el-dialog__title{
  font-size: 12px;
  color:rgb(255,255,255);

}
/deep/.el-form-item__label{
  font-size: 12px;
  width: 80px;
  color: rgb(255,255,255);
}
img{
  width: 12px;
  vertical-align: middle;
}
img:hover{
  cursor: pointer;
}
.binicon{
  width: 24px;
  height: 20px;
  float: left;
  margin-top:10px;
}
.addicon{
  width: 24px;
  height: 20px;
  margin-top:1px;
}
.editicon{
  width: 24px;
  height: 20px;
  float: right;
  margin-top:10px ;
  margin-right:30px ;
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
/deep/.btnedit span{
   line-height:0;
  margin-right:5px ;
}
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;

}
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
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
.category{
  font-size: 12px;
  height: 30px;
  margin:10px  0 0 40px;
  box-sizing: border-box;
}
.pagbox{
   text-align: right;
   margin-top: 20px!important;
   height:'15%'
}
.name{
  width: 360px;
  margin-left: 20px;
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
