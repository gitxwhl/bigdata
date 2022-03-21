<template>
  <div class='container'>
    <div class='meatitle'>空间管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='cardleft'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='8' class='iptcol'>
            <el-form-item>
              <el-input class="inputwidth" placeholder="请输入空间名称"  v-model="queryform.Name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='13'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="querylist">搜索</button>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>
              <el-dialog class="adddialog" title="新增" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">
                <el-form :model="addform" ref='addform'  class="addcategory">
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                      <el-form-item   label="空间名称:" prop="spacename">
                        <el-input class='kjname' v-model="addform.spacename"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row class='iptrow'>
                    <el-col :span='12'>
                      <el-form-item  label="建筑面积:" prop="coveredarea ">
                        <el-input class='lhname'  v-model="addform.coveredarea"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item  label="绿化比例:" prop="Greeningratio">
                        <el-input class='lhname'  v-model="addform.Greeningratio"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                      <el-form-item  label="详细地址:" prop="address">
                        <el-input class='kjname' v-model="addform.address"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>

                  </el-row>
                  <el-row class='iptrow'>
                    <el-col :span='24'> <el-form-item label="描述详情:"  prop="details">
                      <el-input class='kjname' type="textarea" v-model="addform.details"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                    </el-col>
                  </el-row>
                  <div class='fujian'>附件:点击上传附件...</div>


                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button  class="search"  type="primary" @click="addpolicybtn('addform')">添加</el-button>
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
        <el-table-column align="center" min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="spacename" label="空间名称"> </el-table-column>
        <el-table-column align="center" min-width="10%" prop="builtarea" label="建筑面积（㎡）"></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="greeningratio" label="绿化比例（%）" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="detailedaddress" label="详细地址" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="description" label="详情描述" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="enclosure" label="附件" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="25%" label="操作" >
          <template slot-scope="scope">
            <span  class="editicon"><img src="../../assets/u5958.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" > 查看附件</span>
            <span  class="binicon"><img src="../../assets/u349.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="deletepolicy(scope.row.id)">删除</span>
            <span  class="editicon"><img src="../../assets/u363.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" > 修改</span>

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
      <div class='routerbox clearfix'>
            <div class='smallbox'  @click="$router.push('/property/spacemanagement')">
            <div style="float:left;">空间管理</div><img style="float:right;" src="../../assets/gys1.svg" alt=""></div>
            <div class='smallbox' @click="$router.push('/property/slmanagement')">
            <div style="float:right;">供应商管理</div><img style="float:left;" src="../../assets/gys2.png" alt=""></div>
            <div class='smallbox' @click="$router.push('/property/irrigationmanagement')"><img style="float:right;" src="../../assets/gys3.png" alt=""><div style="float:left;">灌溉管理</div></div>
            <div class='smallbox' @click="$router.push('/property/jobplacement')">
            <img style="float:left;" src="../../assets/gys4.svg" alt=""><div style="float:right;">工作安排</div></div>
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
        // fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
        dialogaddVisible: false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        applicabletime:[],//适用时间
        equipment:[],
        restaurant:[],
        queryform: {
          Name: '',//搜索查询
        },

        addform: {
          spacename:'',//空间名称
          coveredarea:'',//建筑面积
          Greeningratio:'',//绿化比例
          address: '',//详细地址
          details:'',//描述详情

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
      // this.treelist()
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
        // console.log(data)
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
          // console.log(res)
          this.data=res.data
        })
      },

      // 列表
      pagelist(){
        this.$instance({
          url:'StSpaceManagement.do/selectSpace',
          method:'post',
          data: {
            "pageNum": this.pageIndex,
            "pageSize": this.pageSize,
            "id": this.restaurantCode,
            "name": this.queryform.Name,
          }
        }).then(res=>{
          // console.log(res)
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 查询列表
      querylist(){
        this.pagelist()
        this.queryform.Name=''
      },
      // 删除
      deletepolicy(id){
        this.$confirm('确认删除', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'StSpaceManagement.do/deleteSpace',
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
        if(this.addform.spacename==''||this.addform.coveredarea==''||this.addform.Greeningratio==''
          ||this.addform.address==''||this.addform.details==''){
          this.dialogaddVisible = false
          this.$refs[queryform].resetFields()
          this.$message('带*号项不能为空');
          return
        }
        this.$instance({
          url:'StOperationrestaurant.do/insertStrategy',
          method:'post',
          data:{
            "param":{
              "policyName":this.addform.spacename,  //空间名称
              "policyCode": this.addform.coveredarea, //建筑面积
              "restaurant": this.addform.Greeningratio, //绿化比例
              "meteringequipment":this.addform.address,//详细地址
              "details":this.addform.details,//描述详情
            }
          }
        }).then(res=>{
          // console.log(res)
          this.pagelist()
          this.$message.success('新增成功')
        })
        this.$refs[queryform].resetFields()
        this.dialogaddVisible = false
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
    margin-bottom:10px;
    padding: 14px 0px 0px 0px;
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
    width:60px;
    height: 30px;
    line-height:30px;
    /*float: left;*/
    margin-left: 20px;
  }
  .binicon img:hover{
    cursor: pointer;
  }
  .editicon{
    width:60px;
    height: 20px;
    line-height:30px;
    /*float: right;*/
    margin-left:20px ;
  }
  .editicon img{
    cursor: pointer;
  }
  /deep/.el-dialog{
    background: rgba(0,0,0,.8);
    border:1px solid rgba(255,255,255,.5);
    // margin-top:300px;
    width: 700px!important;
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
    padding:0 20px 0 20px;
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
    width: 550px;
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
    .cardleft{
      width: 78%;
      display: inline-block;
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
.inputwidth /deep/.el-input__inner{
  width: 400px;
}
.kjname{
  width: 540px;
}
.lhname{
  width: 200px;
}
.fujian{
  font-size: 12px;
  margin-top:30px;
}
</style>
