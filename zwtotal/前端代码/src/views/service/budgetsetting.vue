<template>
  <div class='container'>
    <div class='meatitle'>预算设置</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row >
          <el-col :span='1.5'  class='iptcol'>
            <el-form-item>
              <button id='personnel' style="background:white;color:black;" class='search' @click.prevent @click="personnel">办公用品</button>
            </el-form-item>
          </el-col>
          <el-col :span='20'  class='iptcol'>
            <el-form-item>
              <button id='sideboard' class='search' @click.prevent @click="sideboard">打印纸</button>
            </el-form-item>
          </el-col>
          <el-col :span='1.5'  class='iptcol' >
              <el-form-item>
                <input style="display:none;" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel()">
                <button @click.prevent type="button" class='leadingin'  @click="leadingin"><i ></i>  导入</button>
              </el-form-item>

          </el-col>
          <el-col :span='1.5'  class='iptcol' >
            <el-form-item>
              <button class='search' @click.prevent @click="oppoyssz">预算设置</button>
            </el-form-item>
          </el-col>
          <el-dialog class="adddialog"  title="预算设置" :visible.sync="dialogaddVisible" @close="clear">
                        <div class='fontname'>部门名称:</div>
                        <div class='fontname'>预算类型:{{budget}}</div>
                            <el-form :model="addform" ref="addform" class="addcategory">
                                <el-row class='addrow'>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item label="预算年度:" prop="equipmentName">
                                            <el-select  v-model="addform.theyear">
                                              <el-option v-for="item in date" :key="item.id" :label="item.text" :value="item.id">
                                              </el-option>
                                          </el-select>
                                            </el-form-item>
                                    </el-col>
                                    <el-col :span='12' class='iptcol'>
                                        <el-form-item  label="总预算(￥):" prop="equipmentCode">
                                        <el-input class='zys'  v-model="addform.maxmoney"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row class='addrow'>
                                    <el-col :span='11' class='iptcol'>
                                         <el-form-item label="预算时间:">
                                          <el-date-picker
                                            v-model="addform.beginDate"
                                            format="yyyy-MM-dd"
                                            value-format="yyyy-MM-dd"
                                            type="date"
                                            placeholder="请选择日期">
                                          </el-date-picker><span class="fhbox">-</span>
                                        </el-form-item>
                                    </el-col>
                                      <el-col :span='12' class='iptcol'>
                                        <el-form-item>
                                          <el-date-picker
                                            v-model="addform.endDate"
                                            format="yyyy-MM-dd"
                                            value-format="yyyy-MM-dd"
                                            type="date"
                                            placeholder="请选择日期">
                                          </el-date-picker>
                                        </el-form-item>
                                      </el-col>
                                </el-row>

                                <el-form-item label="备注:" prop="remarks">
                                  <el-input type="textarea" v-model="addform.remark"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="search"  type="primary" @click="clear">清空</el-button>
                                <el-button style="margin-top:30px;" class="search"  type="primary" @click="submit">提交</el-button>
                              </div>
                        </el-dialog>


        </el-row>
      </el-form>
      <div v-if="yuangong">
        <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
              color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                  size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
          <el-table-column align="center"  min-width="10%" type="selection" width="55"> </el-table-column>
          <el-table-column align="center"  min-width="12%" prop="ORGNAME" label="部门名称" show-overflow-tooltip> </el-table-column>
          <el-table-column align="center"  min-width="8%" prop="IS_PAPER" label="预算类型" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="THEYEAR" label="预算年度" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="BEGIN_DATE" label="预算开始时间" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="END_DATE" label="预算结束时间" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="depmoney" label="预算金额" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="leftmoney" label="剩余金额" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="25%" prop="REMARK"  label="备注" show-overflow-tooltip></el-table-column>
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
      </div>
      <div v-if="cangui">
        <el-table stripe ref="multipleTable" :data="tableData2" :cell-style="{padding:'0',
              color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
              fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                  size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
          <el-table-column align="center" min-width="10%" type="selection" width="55"> </el-table-column>
          <el-table-column align="center"  min-width="12%" prop="ORGNAME" label="部门名称" show-overflow-tooltip> </el-table-column>
          <el-table-column align="center"  min-width="8%" prop="IS_PAPER" label="预算类型" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="THEYEAR" label="预算年度" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="BEGIN_DATE" label="预算开始时间" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="END_DATE" label="预算结束时间" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="depmoney" label="预算金额" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="8%"  prop="leftmoney" label="剩余金额" show-overflow-tooltip></el-table-column>
          <el-table-column align="center"  min-width="25%" prop="REMARK"  label="备注" show-overflow-tooltip></el-table-column>
        </el-table>

        <div style="text-align: right; margin-top: 10px;height:'15%'">
          <el-pagination
            @size-change="handleSizeChange2"
            @current-change="handleCurrentChange2"
            :current-page="pageIndex2"
            :page-sizes="pageSizes2"
            :page-size="pageSize2"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total2">
          </el-pagination>
        </div>
      </div>

    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dialogaddVisible:false,
        yuangong:true,
        cangui:false,
        budget:'办公用品',
        isPaper:'0',
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        pageIndex2: 1,
        pageSize2:10,
        pageSizes2:[ 10, 20 , 30, 50, 100],
        total2:0,
        restaurantCode:'',//树状图id
        date: [],
        queryform: {
          mealTypevalue:'',
          name:'',//人员姓名
          sideboardname:''//餐柜名称
        },
         addform: {
          maxmoney:'',
          theyear:'',
          beginDate:'',
          endDate:'',
          remark:''
        },
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
    },
    mounted(){
      this.personnel()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        this.personnel()
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.personnel()
      },
      handleSizeChange2 (val) {
        this.pageSize2 = val
        this.sideboard()
      },
      handleCurrentChange2 (val) {
        this.pageIndex2=val
        this.sideboard()
      },
      handleNodeClick (data) {

      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      // 树状图接口
      treelist(){
        // this.$axios({
        //   url:'StOperationrestaurant.do/getStOperationList',
        //   method:'get'
        // }).then(res=>{
        //   // console.log(res)
        //   this.data=res.data
        // })
      },
      // 下拉框数据
      selectlist(){
       this.$office({
         url:'TdeptBudget.do/dropDownBox',
         method:'get',
       }).then(res=>{
         this.date=res.data.date
       })
      },
      // 点击办公用品按钮
      personnel(){
        var personnel=document.getElementById('personnel')
        var sideboard=document.getElementById('sideboard')
        personnel.style.background='white'
        personnel.style.color='black'
        sideboard.style.background='none'
        sideboard.style.color='white'
        this.yuangong=true
        this.cangui=false
        this.budget='办公用品'
        this.isPaper='0'
        this.$office({
          url:'TdeptBudget.do/getBudget',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "depId":"A000000000000003",
            "isPaper":0,
          }
        }).then(res=>{
          this.total=res.data.totalRecord
          this.tableData=res.data.list
        })
      },
      //点击打印纸按钮
      sideboard(){
        personnel.style.background='none'
        personnel.style.color='white'
        sideboard.style.background='white'
        sideboard.style.color='black'
        this.yuangong=false
        this.cangui=true
        this.budget='打印纸'
        this.isPaper='1'
        this.$office({
          url:'TdeptBudget.do/getBudget',
          method:'post',
          data:{
            "pageNum": this.pageIndex2,
            "pageSize":this.pageSize2,
            "depId":"A000000000000003",
            "isPaper":1,
          }
        }).then(res=>{
          this.total2=res.data.totalRecord
          this.tableData2=res.data.list
        })
      },
      // 预算设置按钮
      oppoyssz(){
        debugger
        this.dialogaddVisible=true
      },
      // 清空预算设置表单
      clear(){
        this.addform={
          maxmoney:'',
          theyear:'',
          beginDate:'',
          endDate:'',
          remark:''
        }
      },
      //提交预算设置
      submit(){
        if(this.addform.theyear==''||this.addform.maxmoney==''||this.addform.beginDate==''||this.addform.endDate==''||this.addform.remark==''){
          this.$message('请补全表单')
          return
        }
        this.$office({
          url:'TdeptBudget.do/insertBudget',
          method:'post',
          data:{
            "orgid":"A000000000000003",
            "orgname": "办公室（党委办公室)",
            "maxmoney":this.addform.maxmoney,
            "theyear":this.addform.theyear,
            "beginDate":this.addform.beginDate,
            "endDate":this.addform.endDate,
            "remark":this.addform.remark,
            "isPaper":this.isPaper,
          }
        }).then(res=>{
          if(res.data=='设置成功'){
            this.$message.success('设置成功')
            if(this.isPaper=='0'){
              this.personnel()
            }else if(this.isPaper=='1'){
              this.sideboard()
            }
          }else{
            this.$message.error('设置失败')
          }
          this.dialogaddVisible=false
          this.addform={
          maxmoney:'',
          theyear:'',
          beginDate:'',
          endDate:'',
          remark:''
        }
        })
      },
      // 导入
      importExcel(){
        console.log(this.$refs.articleImageFile.files[0])
        // for(var i=0;i<this.import.length;i++){
        //   if(this.$refs.articleImageFile.files[0].name==this.import[i]){
        //     this.$message.error('该文件名已经导入过')
        //     this.$refs.articleImageFile.value=''
        //     return
        //   }
        // }
        var formData = new FormData();
        formData.append("file",this.$refs.articleImageFile.files[0]);
        this.$office({
          url:'TdeptBudget.do/importBudget',
          method: 'POST',
          data:formData,
          // headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        }).then(res=>{
          if(res){
            this.$message.success('导入成功')
            // this.importarray()
            this.personnel()
            this.sideboard()

          }else{
            this.$message.error('导入失败')

          }
        })
        this.$refs.articleImageFile.value=''

      },
      leadingin(){
        this.$refs.articleImageFile.click()
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
    padding: 0 30px;
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
  /deep/.el-date-editor{
    width: 200px;
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
  .adddialog .point{
    width: 240px;
    height:100px;
    position: absolute;
    top:100px;
    right:40px;
}
.addcategory /deep/.el-textarea__inner{
    height: 100px;
    color: white;
    background: black;
    border:1px solid rgb(52,57,82);

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
/deep/.el-dialog__body{
  font-size: 12px;
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 10px;
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
  .fhbox{
    margin-left: 20px;
    font-size: 16px;
    color: white;
  }
  .fontname{
    margin: 0 0 10px 0;
  }
  .zys{
    width: 200px;
  }
  .floatright{
    float: right;
  }
  .floatleft{
    float:left;
  }
  .floatleft span{
    display: inline-block;
    margin: 0 10px 0 0 ;
  }
  .sqpeople{
    margin: 0 20px 0 0 ;
    display: inline-block;
  }
  .search,.increase,.leadingin,.leadingout{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width:80px;
    height:32px;
    line-height: 32px;
    box-sizing: border-box;
    padding: 0;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
</style>

