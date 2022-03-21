<template>
  <div class='container'>
    <div class='meatitle'>保洁区域管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <div class='cardleft'>
          <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='9' class='iptcol'>
            <el-form-item>
              <el-input class="inputwidth" placeholder="请输入空间名称"  v-model="queryform.spaceName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="pagelist">搜索</button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
        <el-table-column align="center" min-width="8%" prop="spacename" label="区域名称" show-overflow-tooltip> </el-table-column>
        <el-table-column align="center" min-width="5%" prop="builtarea" label="建筑面积" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="equipmentIds" label="设备设施" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="detailedaddress" label="详细地点" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="description" label="详情描述" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="enclosure" label="附件" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="18%" label="操作" >
          <template slot-scope="scope">
            <span  class="editicon"><img src="../../assets/u5958.svg" alt="" > 查看附件</span>
            <span  class="editicon"><img src="../../assets/u363.svg" alt="" @click="changelist(scope.row)"> 修改</span>
            <span  class="binicon"><img src="../../assets/u349.svg" alt="" @click="deletepolicy(scope.row.id)">删除</span>
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
      </div>
      


      <div class="cardright">
        <div style="text-align:center;" class='keep'>区域保洁信息</div>
        <el-form :model="addform" ref="addform" size="mini" class='queryform' >
            <el-form-item  label="区域名称:"  class='casualties'>
              <el-input  type="textarea"  v-model="addform.spaceName"></el-input>
            </el-form-item>
            <el-form-item label="建筑面积:">
                <el-input class='mianjiwidth' v-model="addform.builtArea"></el-input>
            </el-form-item>
            <el-form-item label="设备设施:">
              <el-select multiple v-model="addform.equipmentIds" value-key='id'>
                <el-option v-for="(item,i) in equipment" :key="i" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="详细地点:" class='casualties'>
              <el-input  type="textarea"  v-model="addform.detailedAddress"></el-input>
            </el-form-item>
            <el-form-item label="详情描述:" class='details'>
              <el-input  type="textarea"  v-model="addform.description"></el-input>
            </el-form-item>
            <div>
              附件:点击上传附件。。。
            </div>
            <el-form-item style="text-align:right;margin-top:10px;">
              <button class='search' @click.prevent @click="clearlist">取消</button>
              <button class='search' @click.prevent @click="addlist">确定</button>
            </el-form-item>
      </el-form>
    </div>


    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        status:false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        queryform: {
          spaceName: '',//搜索查询
        },
        addform:{
          spaceName:'',
          builtArea:'',
          detailedAddress:'',
          description:'',
          equipmentIds:[],
          enclosure:[]
        },
        equipment:[],
        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.selectlist()
      this.pagelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize=val
        this.pagelist()
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.pagelist()
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      selectable(row,index){
        return 0
      },
      // 树状图接口
      treelist(){
      },
      // 获取下拉框数据-保洁区域管理
      selectlist(){
        this.$instance({
          url:'Wyspace.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.equipment=res.data.equipment
        })
      },
      // 保洁区域列表
      pagelist(){
        this.$instance({
          url:'Wyspace.do/getCleanSpace',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "spaceName":this.queryform.spaceName
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 新增保洁区域
      addlist(){
        if(this.status==false){
          if(this.addform.spaceName==''||this.addform.builtArea==''||this.addform.detailedAddress==''||this.addform.description==''||this.addform.equipmentIds.length==0){
          this.$message('请补全新增信息')
          return
        }
        this.$instance({
          url:'Wyspace.do/addCleanSpace',
          method:'post',
          data:this.addform
        }).then(res=>{
          if(res.data=='新增成功'){
            this.$message.success('新增成功')
            this.pagelist()
          }else{
            this.$message.error('新增失败')
          }
            this.addform.spaceName=''
            this.addform.builtArea=''
            this.addform.detailedAddress=''
            this.addform.description=''
            this.addform.equipmentIds=[]
        })
        }else if(this.status==true){
          this.$instance({
            url:'Wyspace.do/updateCleanSpace',
            method:'post',
            data:{
              "id":this.id,
              "spaceName":this.addform.spaceName,
              "builtArea":this.addform.builtArea,
              "detailedAddress":this.addform.detailedAddress,
              "description":this.addform.description,
              "equipmentIds":this.addform.equipmentIds,
              "enclosure":this.addform.enclosure
            }
          }).then(res=>{
            if(res.data=='修改成功'){
              this.$message.success('修改成功')
              this.pagelist()
              this.status=false
            }else{
              this.$message.error('修改失败')
              this.pagelist()
              this.status=false
            }
            this.addform.spaceName=''
            this.addform.builtArea=''
            this.addform.detailedAddress=''
            this.addform.description=''
            this.addform.equipmentIds=[]
          })
        }
        
      },
      // 修改保洁区域
      changelist(row){
        this.addform.spaceName=row.spacename
        this.addform.builtArea=row.builtarea
        this.addform.detailedAddress=row.detailedaddress
        this.addform.description=row.description
        this.addform.equipmentIds=row.list
        this.id=row.id
        this.status=true
      },
      // 取消
      clearlist(){
        if(this.status==true){
          this.status=false
        }
        this.addform.spaceName=''
        this.addform.builtArea=''
        this.addform.detailedAddress=''
        this.addform.description=''
        this.addform.equipmentIds=[]
      },
      // 删除
      deletepolicy(id){
        this.$confirm('确认删除该菜品?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'Wyspace.do/deleteCleanSpace',
            method:'post',
            data:{
              "id":id
            }
          }).then(res=>{
            if(res.data=='删除成功'){
              this.$message.success('删除成功')
              this.pagelist()
            }else{
              this.$message.error('删除失败')
            }
          })
        }).catch(()=>{

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
    overflow-y: auto;
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
   .cardleft{
      width: 78%;
      display: inline-block;
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
    width:33%;
    height: 36px;
    line-height: 36px;
    line-height:30px;
    float: left;
  }
  .binicon img:hover{
    cursor: pointer;
  }
  .editicon{
    width:33%;
    height: 36px;
    line-height: 36px;
    line-height:30px;
    float: left;
  }
  .editicon img,.binicon img{
    width: 22px;
    height: 20px;
    vertical-align: middle;
    cursor: pointer;
  }
  /deep/.el-dialog{
    background: transparent;
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
    right: 5px;
  }
  .pagbox{
    text-align: right;
    margin-top: 10px;
    height:'15%'
  }
  .inputwidth /deep/.el-input__inner{
    width: 400px;
  }
    .cardright{
      width: 20%;
      margin: 5px 0 0 20px;
      height:100%;
      padding:15px 10px 10px 20px;
      box-sizing: border-box;
      background-color: #34353D;
      display: inline-block;
      vertical-align: top;
      color: white;
      font-size: 13px;
  }
    .casualties /deep/ .el-textarea__inner{
      background: black;
      border:1px solid #5D5D64;
      color: white;
      width: 96%;
      height: 70px;
  }
  .details /deep/ .el-textarea__inner{
      background: black;
      border:1px solid #5D5D64;
      color: white;
      width: 96%;
      height: 240px;
  }
  .keep{
      text-align: center;
      margin:0 0 20px 0 ;
  }
  .mianjiwidth{
    width: 180px;
  }
 .el-select /deep/.el-input__inner{
    width: 180px;
    height: 32px!important;
  }
</style>
