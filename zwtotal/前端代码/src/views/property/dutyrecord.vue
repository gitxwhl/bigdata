<template>
  <div class='container'>
    <div class='meatitle'>值班记录查看</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
    <div class='cardleft'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item >
              <el-date-picker
                v-model="queryform.beginTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox">~</span>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item>
              <el-date-picker
                v-model="queryform.endTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span='6' class='iptcol'>
            <el-form-item label="班次:" class='selectwhite'>
              <el-select v-model="queryform.frequency">
                <el-option v-for="item in frequency" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item label="值班人员:">
              <el-input class="inputwidth"  v-model="queryform.gatekeeper"></el-input>
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
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="10%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="12%" prop="timestamp" label="日期"> </el-table-column>
        <el-table-column align="center" min-width="12%" prop="frequency" label="班次"></el-table-column>
        <el-table-column align="center" min-width="12%"  prop="gatekeeper" label="值班人员确认" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="40%"  prop="events" label="特殊事件" show-overflow-tooltip></el-table-column>
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
    <div class="cardright">
        <div style="text-align:center;" class='keep'>值班记录</div>
        <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-form-item  label="日期:">
              <el-date-picker
                v-model="addform.timestamp"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="班次:">
              <el-select v-model="addform.frequency">
                <el-option v-for="item in frequency" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="特殊事件:">
              <el-input  type="textarea"  v-model="addform.events"></el-input>
            </el-form-item>
            <el-form-item label="值班人:">
              <el-select v-model="addform.gatekeeper">
                <el-option v-for="item in person" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <button class='search' @click.prevent @click='addlist'>提交</button>
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
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        person: [],
        frequency:[],
        queryform: {
          beginTime:'',
          endTime:'',
          frequency:'',
          gatekeeper:''
        },
        addform:{
          timestamp:'',
          frequency:'',
          events:'',
          gatekeeper:''
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
      this.selectlist()
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
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.pagelist()
        // console.log(data)
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
      // 树状图接口
      treelist(){
        
      },
    //获取下拉框数据-值班记录
      selectlist(){
        this.$instance({
          url:'Wydutyrecord.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.person=res.data.person
          this.frequency=res.data.frequency
        })
      },
      // 查询值班记录
      pagelist(){
        this.$instance({
          url:'Wydutyrecord.do/getDutyRecord',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "beginTime":this.queryform.beginTime,
            "endTime":this.queryform.endTime,
            "frequency":this.queryform.frequency,
            "gatekeeper":this.queryform.gatekeeper
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 添加值班记录
      addlist(){
        if(this.addform.timestamp==''||this.addform.frequency==''||this.addform.events==''||this.addform.gatekeeper==''){
            this.$message('请补全值班记录信息')
            return
        }
        this.$instance({
          url:'Wydutyrecord.do/addDutyRecord',
          method:'post',
          data:this.addform
        }).then(res=>{
          if(res.data=='添加值班记录成功'){
            this.$message.success('添加值班记录成功')
            this.pagelist()
          }else{
            this.$message.error('添加值班记录失败')
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
  /deep/.el-range__icon{
    line-height: 20px;
    margin-left: -5px;
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
      padding:15px 10px 10px 20px;
      box-sizing: border-box;
      background-color: #34353D;
      display: inline-block;
      vertical-align: top;
      color: white;
      font-size: 13px;
  }
  /deep/ .el-textarea__inner{
      background: black;
      border:1px solid #5D5D64;
      color: white;
      width: 96%;
      height: 540px;
  }
  .keep{
      text-align: center;
      margin:0 0 20px 0 ;
  }
  .selectwhite /deep/.el-input{
    width: 150px;
  }
</style>

