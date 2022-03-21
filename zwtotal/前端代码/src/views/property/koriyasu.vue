<template>
  <div class='container'>
    <div class='meatitle'>设备维保策略</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item label="策略名称:">
              <el-input v-model="queryform.equipmentName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item  label="策略分类:">
              <el-select v-model="queryform.equipmentCode">
                <el-option v-for="item in strategytype" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span='5' class='iptcol'>
            <el-form-item label="计划时间:">
              <el-date-picker
                v-model="queryform.startTime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择日期">
              </el-date-picker><span class="fhbox"></span>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click='querylist'>查询</button>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>
              <el-dialog class="adddialog"  title="新增" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">
                <el-form :model="addform" ref="addform"  class="addcategory">
                  <el-row class='addrow'>
                    <el-col :span='9'>
                      <el-form-item label="策略名称:" prop="equipmentName">
                        <el-input class='name' v-model="addform.equipmentName"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row class='addrow'>
                    <el-col :span='12'> <el-form-item label="策略分类:"   prop="restaurant">
                      <el-select class='restaurant' v-model="addform.strategy">
                        <el-option v-for="item in strategytype" :key="item.id" :label="item.text" :value="item.id">
                        </el-option>
                      </el-select>
                      <span class="tab">*</span>
                    </el-form-item></el-col>

                  </el-row>
                  <el-row>
                  <el-col :span='10' class='iptcol'>
                    <el-form-item label="计划时间:">
                      <el-date-picker
                        v-model="addform.Time"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="请选择日期">
                      </el-date-picker>

                    </el-form-item>
                  </el-col>
                    <el-col :span='9' class='iptcol'>
                      <el-form-item label="-">
                        <el-date-picker
                          v-model="addform.endTime"
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd"
                          type="date"
                          placeholder="请选择日期">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-form-item label="任务内容:" prop="taskdefinition">
                      <el-input style="width:100px;height:60px;" type="textarea" v-model="addform.taskdefinition"></el-input>
                    </el-form-item>
                  </el-row>
                  <el-row style="margin-top: 100px">
                    <el-form-item label="匹配资源:" prop="Matchresources">
                      <el-input style="width:100px;height:60px;" type="textarea" v-model="addform.Matchresources"></el-input>
                    </el-form-item>
                  </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="adddevice('addform')">提交</el-button>
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
        <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="policyname" label="策略名称"> </el-table-column>
        <el-table-column align="center"  min-width="15%" prop="policy" label="策略分类"></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="planningtime" label="计划时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="20%"  prop="content" label="任务内容" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="20%"  prop="resources" label="匹配资源" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="20%" label="操作" >
          <template slot-scope="scope">
            <span  class="binicon"><img src="../../assets/u349.svg" alt=""  @click="deletedevice(scope.row.id)">删除</span>
            <span  class="editicon"><img src="../../assets/u363.svg" alt="" @click="amend(scope.row.id)"  > 修改</span>
            <el-dialog class="adddialog"  title="修改" :visible.sync="dialogrevVisible" @close="closeaddDialog('addform')">
              <el-form :model="amendform" ref="amendform"  class="addcategory">
                <el-row class='addrow'>
                  <el-col :span='9'>
                    <el-form-item label="策略名称:" prop="equipmentName">
                      <el-input class='name' v-model="amendform.policyname"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row class='addrow'>
                  <el-col :span='12'> <el-form-item label="策略分类:"   prop="restaurant">
                    <el-select class='restaurant' v-model="amendform.policy">
                      <el-option v-for="item in strategytype" :key="item.id" :label="item.text" :value="item.id">
                      </el-option>
                    </el-select>
                    <span class="tab">*</span>
                  </el-form-item></el-col>

                </el-row>
                <el-row>
                  <el-col :span='10' class='iptcol'>
                    <el-form-item label="计划时间:">
                      <el-date-picker
                        v-model="amendform.planningtime"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="请选择日期">
                      </el-date-picker>

                    </el-form-item>
                  </el-col>
                  <el-col :span='9' class='iptcol'>
                    <el-form-item label="-">
                      <el-date-picker
                        v-model="amendform.planendtime"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        type="date"
                        placeholder="请选择日期">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-form-item label="任务内容:" prop="taskdefinition">
                    <el-input style="width:100px;height:60px;" type="textarea" v-model="amendform.content"></el-input>
                  </el-form-item>
                </el-row>
                <el-row style="margin-top: 100px">
                  <el-form-item label="匹配资源:" prop="Matchresources">
                    <el-input style="width:100px;height:60px;" type="textarea" v-model="amendform.resources"></el-input>
                  </el-form-item>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="amendbtn(scope.row.id)">修改</el-button>
              </div>
            </el-dialog>
          </template>
        </el-table-column>
      </el-table>
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
        id:'',
        dialogaddVisible: false,
        dialogrevVisible: false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        mealType:[],
        queryform: {
          equipmentName: '',
          equipmentCode: '',
          startTime:''
        },
        strategytype:[
          {
            "text": "临时",
            "id": "1"
          },
          {
            "text": "专项",
            "id": "2"
          }

        ],//策略分类
        amendform:{},
        addform: {
          equipmentName: '',//策略名称
          strategy:'',//策略分类
          Time:'',//计划开始时间
          endTime:'',//计划结束时间
          taskdefinition:'',//任务内容
          Matchresources:''//匹配资源
        },
        equipmentState:[],//设备状态
        restaurant:[],//运维餐厅

        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
    if (this.$route.query.id===10){
      this.dialogaddVisible = true
    }

      // this.treelist()
      this.selectlist()
      this.pagelist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize=val
        this.pagelist()
        // console.log(`每页 ${val} 条`)
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
        // console.log(`当前页: ${val}`)
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
          this.data=res.data
        })
      },
      // 下拉框数据
      selectlist(){
        this.$instance({
          url:'StEquipmentstrategy.do/getPolicy',
          method:'post'
        }).then(res=>{
          // console.log(res)
          this.strategytype=res.data.policy
          // this.restaurant=res.data.restaurant
        })
      },
      // 设备列表
      pagelist(){
        this.$instance({
          url:'StEquipmentstrategy.do/getEquipmentStrategy',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "policyname":this.queryform.equipmentName, //策略名称
            "policy":this.queryform.equipmentCode, //策略分类
            "planningtime":this.queryform.startTime, //计划时间
            "company":this.restaurantCode,// 公司外键
            // "param": {
            //   "restaurantName": this.restaurantCode,
            //   "equipmentName": this.queryform.equipmentName,
            //   "equipmentCode":this.queryform.equipmentCode
            // }
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
        // this.queryform.equipmentName=''
        // this.queryform.equipmentCode=''
      },
      // 删除设备
      deletedevice(id){
        this.$confirm('确认删除该菜品?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'StEquipmentstrategy.do/deleteEquipmentStrategy',
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
      // 提交
      adddevice(queryform){
        if(this.addform.equipmentName==''||this.addform.strategy==''||this.addform.Time==''
          ||this.addform.endTime==''||this.addform.taskdefinition==''||this.addform.Matchresources==''){
          // this.dialogaddVisible = false
          // this.$refs[queryform].resetFields()
          this.$message('所有选项不能为空');
          return
        }
        this.$instance({
          url:'StEquipmentstrategy.do/insertEquipmentStrategy',
          method:'post',
          data:{
            // "equipmentName":this.addform.equipmentName,
            // "equipmentCode": this.addform.strategy,
            // "restaurant": this.addform.Time,
            // "purchaseBatch":this.addform.endTime,
            // "totalDevices":this.addform.taskdefinition,
            // "remarks": this.addform.Matchresources

            "policyname":this.addform.equipmentName,//策略名称
            "policy":this.addform.strategy,//策略分类
            "planningtime":this.addform.Time,//计划开始时间
            "planendtime":this.addform.endTime,//计划结束时间
            "content":this.addform.taskdefinition,//任务内容
            "resources":this.addform.Matchresources,//匹配资源
            "company":this.restaurantCode,
            "policyid":"",  //对应维护工单id外键
            "workno":""  //对应维护工单的工单编号

          }
        }).then(res=>{
          // console.log(res)
          this.pagelist()
          this.$message.success('新增成功')
        })
        this.$refs[queryform].resetFields()
        this.dialogaddVisible = false
      },
      //修改
      amend(id){
        this.dialogrevVisible = true
        this.$instance({
          url:'StEquipmentstrategy.do/getEquipmentStrategyById',
          method:'post',
          data:{
            id:id
          }
        }).then(res=>{
          this.amendform=res.data
          // console.log(res.data)

        })
      },
      amendbtn(id){
        this.dialogrevVisible = false

        this.$instance({
          url:'StEquipmentstrategy.do/updateEquipmentStrategy',
          method:'post',
          data:{
            "id":id,
            "policyname":this.amendform.policyname,
            "policy":this.amendform.policy,
            "planningtime":this.amendform.planningtime,
            "planendtime":this.amendform.planendtime,
            "content":this.amendform.content,
            "resources":this.amendform.resources,
            "company":this.restaurantCode,

          }
        }).then(res=>{
          this.pagelist()
          this.$message.success('修改成功')

        })
        // this.$refs[amendform].resetFields()

        this.dialogrevVisible = false
      },
      //关闭事件
      closeaddDialog(queryform){
        // this.$refs[queryform].resetFields()
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
  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px;
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
  .binicon img:hover{
    cursor: pointer;
  }
  .editicon img:hover{
    cursor: pointer;
  }
  /deep/.el-dialog{
    background: transparent;
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
    color:rgb(255,255,255);
    background:transparent;
    padding-bottom:2px;
    padding:0 20px 0 10px;
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
  .adddialog .point{
    width: 240px;
    height:100px;
    position: absolute;
    top:100px;
    right:40px;
  }
  .revdialog .point{
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
  .el-input{
    width: 200px;
  }
  .name{
    width: 160px;
  }
  .code{
    width: 90px;
  }
  .number{
    width: 90px;
  }
  .restaurant{
    width: 200px;
  }
  .count{
    width: 100px;
  }
  .addrow{
    margin-bottom:20px ;
  }
  /deep/.el-input__icon{
    width: 25px;
    line-height: 40px;
  }
</style>
