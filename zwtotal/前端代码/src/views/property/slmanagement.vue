<template>
  <div class='container'>
    <div class='meatitle'>供应商管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='cardleft'>
        <el-row class="iptrow">
          <el-col :span='8' class='iptcol'>
              <el-input v-model="search" placeholder="请输入您要查询的供应商名称/植被名称"></el-input>
          </el-col>
          <el-col :span='13'  class='iptcol'>
              <button class='search' @click.prevent @click="pagelist">搜索</button>
          </el-col>
          <el-col :span='2'  class='iptcol'>
              <button class='search' @click.prevent @click="dialogaddVisible=true">新增</button>
          </el-col>
        </el-row>
        <el-dialog class="adddialog"  title="新增" :visible.sync="dialogaddVisible" @close='closedialog'>
                    <el-form :model="addform" ref="addform"  class="addcategory">
                        <el-row class="iptrow">
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item  label="供应商名称:" prop="meteringEquipment">
                                    <el-input  v-model="addform.name" class='gysname'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row class="iptrow">
                            <el-col :span='10'  class='iptcol'>
                                <el-form-item   label="统一社会信用代码:" prop="meteringEquipment">
                                    <el-input  v-model="addform.code" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span='7'  class='iptcol'>
                                <el-form-item   label="联系人:" prop="meteringEquipment">
                                    <el-input  v-model="addform.people" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span='7'  class='iptcol'>
                                <el-form-item   label="联系电话:" prop="meteringEquipment">
                                    <el-input  v-model="addform.contactperson" class='code'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    <div class='formbox' v-for='(item,index) in vegetations' :key='index'>
                        <el-row>
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item  label="植被名称:" prop="meteringEquipment">
                                    <el-input  v-model="item.vegetationname" class='zbname'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='12'  class='iptcol'>
                                <el-form-item  label="数量:" prop="meteringEquipment">
                                    <el-input  v-model="item.number" class='count'></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span='12'  class='iptcol'>
                                <el-form-item  label="单位:" prop="meteringEquipment">
                                    <el-input  v-model="item.company" class='count'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='24'  class='iptcol'>
                                <el-form-item  label="植被简介:" prop="meteringEquipment">
                                    <el-input type="textarea" v-model="item.introduction" class='textarea'></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class='addbox'>
                        <span class='clickadd' @click='addbox'>点此添加种植植被</span>
                    </div>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button style="margin-top:30px;" class="search"  type="primary" @click="addmanagement">确定</el-button>
                    </div>
                </el-dialog>
      <el-table stripe ref="multipleTable"  :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="12%" prop="suppliername" label="供应商名称"> </el-table-column>
        <el-table-column align="center"  min-width="12%" prop="code" label="统一社会信用代码"></el-table-column>
        <el-table-column align="center"  min-width="12%"  prop="contacts" label="联系人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="12%"  prop="contactperson" label="联系电话" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="40%"  prop="inventory" label="供应详情" show-overflow-tooltip>
            <template slot-scope='scope'>
                <div class='hover' @click='information(scope.row.id)'>详细信息</div>
            </template>
        </el-table-column>
      </el-table>





          <el-dialog class="lookdialog"  title="详细信息" :visible.sync="dialoglookVisible">
            <el-table stripe ref="multipleTable"  :data="tableDatatwo" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
            <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
              <el-table-column align="center" min-width="12%"  prop="vegetationname" label="植被名称" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" min-width="8%"  prop="number" label="数量" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" min-width="8%"  prop="company" label="单位" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" min-width="30%"  prop="introduction" label="植被简介" show-overflow-tooltip></el-table-column>
          </el-table>
                <div style="text-align: right; margin-top: 10px;height:'15%'">
              <el-pagination
                @size-change="handleSizeChangetwo"
                @current-change="handleCurrentChangetwo"
                :current-page="pageIndextwo"
                :page-sizes="pageSizestwo"
                :page-size="pageSizetwo"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totaltwo">
              </el-pagination>
            </div>
          </el-dialog>



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
    <div class="cardright">
        <div class='routerbox clearfix'>
            <div class='smallbox'  @click="$router.push('/property/spacemanagement')"><div style="float:left;">空间管理</div><img style="float:right;" src="../../assets/gys1.svg" alt=""></div>
            <div class='smallbox'><div style="float:right;">供应商管理</div><img style="float:left;" src="../../assets/gys2.png" alt=""></div>
            <div class='smallbox' @click="$router.push('/property/irrigationmanagement')"><img style="float:right;" src="../../assets/gys3.png" alt=""><div style="float:left;">灌溉管理</div></div>
            <div class='smallbox' @click="$router.push('/property/jobplacement')"><img style="float:left;" src="../../assets/gys4.svg" alt=""><div style="float:right;">工作安排</div></div>
        </div>
        <div class='workreminder'>
            <div class='workminbox'>工作提醒</div>
        </div>
    </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        search:'',
        id:'',
        dialogaddVisible:false,
        dialoglookVisible:false,
        addform:{
          name:'',
          code:'',
          people:'',
          contactperson:''
        },
        vegetations:[{
            vegetationname:'',
            number:'',
            company:'',
            introduction:''
        }],
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        pageIndextwo: 1,
        pageSizetwo:10,
        pageSizestwo:[ 10, 20 , 30, 50, 100],
        totaltwo:0,
        restaurantCode:'',//树状图id
        mealType: [],//餐别,
        queryform: {
          mealTypevalue:'',
          jiliang:'',
          startTime:'',
          endTime:''
        },
        tableData: [],
        tableDatatwo:[],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
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
      handleSizeChangetwo (val) {
        this.pageSizetwo = val
        this.information(this.id)
        console.log(`每页 ${val} 条`)
      },
      handleCurrentChangetwo (val) {
        this.pageIndextwo=val
        this.information(this.id)
        console.log(`当前页: ${val}`)
      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      addbox(){
          this.vegetations.push({
            vegetationname:'',
            number:'',
            company:'',
            introduction:''
        })
      },
      // 植被列表
      information(id){
        this.id=id
        this.dialoglookVisible=true
        this.$instance({
          url:'WySpacemanagement.do/WyvegetationtList',
          method:'post',
          data:{
            "pageNum":this.pageIndextwo,
            "pageSize":this.pageSizetwo,
            "param":{
              "id":this.id
            }
          }
        }).then(res=>{
          this.totaltwo=res.data.totalRecord
          this.tableDatatwo=res.data.list
        })
      },
      // 树状图接口
      treelist(){
        
      },
    //查询列表接口
    pagelist(){
        this.$instance({
            url:'WySpacemanagement.do/WySpacemanagementList',
            method:'post',
            data:{
                "pageNum":this.pageIndex,
                "pageSize":this.pageSize,
                "param":{
                    "suppliername":this.search,
                    "attr1":''
                }
            }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
    },
    //新增确认
    addmanagement(){
      for(var i=0;i<this.vegetations.length;i++){
        if(this.vegetations[i].vegetationname==''||this.vegetations[i].number==''||this.vegetations[i].company==''||this.vegetations[i].introduction==''){
        this.$message('请补全输入框信息')
        return
        }
      }
      if(this.addform.name==''||this.addform.code==''||this.addform.people==''||this.addform.contactperson==''){
        this.$message('请补全输入框信息')
        return
      }
      this.$instance({
        url:'WySpacemanagement.do/getSupplierManagement',
        method:'post',
        data:{
          "suppliername":this.addform.name,
          "code":this.addform.code,
          "contacts":this.addform.people,
          "contactperson":this.addform.contactperson,
          "param":{
            "vegetations":this.vegetations
          }
        }
      }).then(res=>{
        if(res.data=='新增成功'){
          this.$message.success('新增成功')
          this.pagelist()
        }else{
          this.$message.error('新增失败')
        }
          this.dialogaddVisible=false
          this.addform.name='',
          this.addform.code='',
          this.addform.people='',
          this.addform.contactperson='',
          this.vegetations=[{
            vegetationname:'',
            number:'',
            company:'',
            introduction:''
        }]
      })
    },
    // 关闭新增
    closedialog(){
          this.addform.name='',
          this.addform.code='',
          this.addform.people='',
          this.addform.contactperson='',
          this.vegetations=[{
            vegetationname:'',
            number:'',
            company:'',
            introduction:''
        }]
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
    width: 120px;
    border:1px solid #5D5D64;
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
    padding: 14px 0px 0px 0;
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
  /deep/.el-form-item{
      margin-bottom: 18px;
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
    height: 700px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 660px!important;
    overflow-y:auto;
}
  .lookdialog /deep/.el-table{
    background-color:transparent;
    height: 500px!important;
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
  .cardleft{
      width: 78%;
      display: inline-block;
  }
  /deep/.el-input--suffix{
    width: 190px;
    border:1px solid #5D5D64;
  }
  .cardright{
      width: 20%;
      margin: 5px 0 0 20px;
      height:100%;
      padding:15px 0 10px 20px;
      box-sizing: border-box;
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
      height: 90px;
  }
  .details /deep/ .el-textarea__inner{
      background: black;
      border:1px solid #5D5D64;
      color: white;
      width: 96%;
      height: 270px;
  }
  .keep{
      text-align: center;
      margin:0 0 20px 0 ;
  }
  .routerbox{
      background-color: #34353D;
      box-sizing: border-box;
      width: 100%;
      margin-bottom: 20px;
  }
  .smallbox{
      width: 50%;
      height:120px;
      float: left;
      box-sizing: border-box;
      padding:10px;
  }
  .smallbox:nth-child(1){
      border-right:2px solid #416A82;
      border-bottom:2px solid #416A82;
      height:122px;
  }
  .smallbox:nth-child(2){
      border-bottom:2px solid #416A82;
      height:122px;
  }
  .smallbox:nth-child(3){
      border-right:2px solid #416A82;
  }
.smallbox img{
    width: 80px;
    height: 80px;
}
.smallbox:hover{
    cursor: pointer;
}
.workreminder{
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    background-color: #34353D;
    height: 550px;
}
.workminbox{
    width: 100%;
    text-align: center;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 800px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:20px 20px 0 20px;
  margin-bottom: 20px;
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
.gysname{
    width: 500px;
}
.code{
    width: 130px;
}
.formbox{
    display: inline-block;
    margin: 10px 20px 0 0 ;
    padding: 10px;
    box-sizing: border-box;
    border: 1px solid #626468;
    width:350px;
    height:180px;
    background-color: #3B3D43;
    vertical-align: middle;

}
.addbox{
    display: inline-block;
    box-sizing: border-box;
    background: url(../../assets/u6171.svg)no-repeat center center;
    font-size: 12px;
    margin: 10px 20px 0 0 ;
    border: 1px solid #626468;
    width:350px;
    height:180px;
    background-color: #3B3D43;
    text-align: center;
    line-height: 180px;
    vertical-align: middle;
}
.zbname{
    width: 260px;
    border: 1px solid #626468;
}
.count{
    width: 100px;
    border: 1px solid #626468;
}
.textarea /deep/.el-textarea__inner{
    background-color: black;
    color: white;
    border: 1px solid #626468;
}
.clickadd:hover{
    cursor: pointer;
}
 .lookdialog /deep/.el-table__body-wrapper{
    height: 450px!important;
    overflow-y:auto;
}
.hover:hover{
  cursor: pointer;
}
</style>

