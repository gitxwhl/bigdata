
<template>
  <div class='container'>
    <div class='meatitle'>设备巡检</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='5' class='iptcol'>
            <el-form-item label="线路名称:">
              <el-input v-model="queryform.equipmentName"></el-input>
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
          <el-col :span='5' class='iptcol'>
            <el-form-item label="巡检时间:">
              <el-date-picker
                v-model="queryform.Time"
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
<!--          <el-col :span='2'  class='iptcol'>-->
<!--            <el-form-item>-->
<!--              <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>-->
<!--              <el-dialog class="adddialog"  title="新增" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">-->
<!--                <el-form :model="addform" ref="addform"  class="addcategory">-->
<!--                  <el-row class='addrow'>-->
<!--                    <el-col :span='9'>-->
<!--                      <el-form-item label="策略名称:" prop="equipmentName">-->
<!--                        <el-input class='name' v-model="addform.equipmentName"></el-input>-->
<!--                        <span class="tab">*</span>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
<!--                  </el-row>-->
<!--                  <el-row class='addrow'>-->
<!--                    <el-col :span='12'> <el-form-item label="策略分类:"   prop="restaurant">-->
<!--                      <el-select class='restaurant' v-model="addform.strategy">-->
<!--                        <el-option v-for="item in strategytype" :key="item.id" :label="item.text" :value="item.id">-->
<!--                        </el-option>-->
<!--                      </el-select>-->
<!--                      <span class="tab">*</span>-->
<!--                    </el-form-item></el-col>-->

<!--                  </el-row>-->
<!--                  <el-row>-->
<!--                    <el-col :span='10' class='iptcol'>-->
<!--                      <el-form-item label="计划时间:">-->
<!--                        <el-date-picker-->
<!--                          v-model="addform.Time"-->
<!--                          format="yyyy-MM-dd"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          type="date"-->
<!--                          placeholder="请选择日期">-->
<!--                        </el-date-picker>-->

<!--                      </el-form-item>-->
<!--                    </el-col>-->
<!--                    <el-col :span='9' class='iptcol'>-->
<!--                      <el-form-item label="-">-->
<!--                        <el-date-picker-->
<!--                          v-model="addform.endTime"-->
<!--                          format="yyyy-MM-dd"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          type="date"-->
<!--                          placeholder="请选择日期">-->
<!--                        </el-date-picker>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
<!--                  </el-row>-->
<!--                  <el-row>-->
<!--                    <el-form-item label="任务内容:" prop="taskdefinition">-->
<!--                      <el-input style="width:100px;height:60px;" type="textarea" v-model="addform.taskdefinition"></el-input>-->
<!--                    </el-form-item>-->
<!--                  </el-row>-->
<!--                  <el-row style="margin-top: 100px">-->
<!--                    <el-form-item label="匹配资源:" prop="Matchresources">-->
<!--                      <el-input style="width:100px;height:60px;" type="textarea" v-model="addform.Matchresources"></el-input>-->
<!--                    </el-form-item>-->
<!--                  </el-row>-->
<!--                </el-form>-->
<!--                <div slot="footer" class="dialog-footer">-->
<!--                  <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="adddevice('addform')">提交</el-button>-->
<!--                </div>-->
<!--              </el-dialog>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
      </el-form>
      <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
        <el-table-column align="center" min-width="5%" type="selection" width="55"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="inspection" label="巡检线路"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="incequipment" label="包含设备"></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="plantime" label="计划时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="planner" label="计划人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="begintime  " label="巡检时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="inspectionperson" label="巡检人员" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="results" label="巡检结果" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="remarks" label="备注" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="missing" label="漏检设备" show-overflow-tooltip></el-table-column>
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
      <div style="display: flex;width: 100%">
        <div style="width: 50%">
          <p style="color: white;font-size: 16px;font-weight: bold;text-align: center;margin-left: 100px">巡检计划</p>
          <el-form :model="plan" ref="plan" size="mini" class='queryform' >
            <el-row class="iptrow">
              <el-col :span='13' class='iptcol'>
<!--                <el-form-item label="线路名称:">-->
<!--                  <el-input v-model="plan.equipmentName"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="线路名称:" style="margin-bottom:15px;">
                  <el-select v-model="plan.equipmentName">
                    <el-option v-for="item in LuName" :key="item.id" :label="item.inspection" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              </el-row>
            <el-row class="iptrow">
              <el-col :span='13' class='iptcol'>
                <el-form-item label="包含设备:">
                  <el-input v-model="plan.planequipment"></el-input>
<!--                  <el-select v-model="plan.planequipment">-->
<!--                    <el-option v-for="item in shebei" :key="item.id" :label="item.name" :value="item.id">-->
<!--                    </el-option>-->
<!--                  </el-select>-->
                </el-form-item>
              </el-col>
            </el-row>
            <el-row  class="iptrow">
              <el-col :span='9' class='iptcol'>
                <el-form-item label="巡检时间:">
                  <el-date-picker
                    v-model="plan.planstartTime"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="请选择日期">
                  </el-date-picker><span class="fhbox">-</span>
                </el-form-item>
              </el-col>
              <el-col :span='9' class='iptcol'>
                <el-form-item label="-">
                  <el-date-picker
                    v-model="plan.plannameEndTime"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="请选择日期">
                  </el-date-picker><span class="fhbox">-</span>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row class="iptrow">
              <el-col :span='13' class='iptcol'>
                <el-form-item label="巡检人员:">
                  <el-input v-model="plan.planpersonnel"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left: 400px">
              <el-col :span='8'  class='iptcol'>
                <el-form-item>
                  <button class='search' >取消</button>
                </el-form-item>
              </el-col>
            <el-col :span='8'  class='iptcol'>
              <el-form-item>
                <button class='search' @click="addpolicybtn('plan')">保存</button>
              </el-form-item>
            </el-col>
            </el-row>
          </el-form>
        </div>
        <div style="width: 50%">
          <p style="color: white;font-size: 16px;font-weight: bold;text-align: center;">巡检记录</p>
          <el-form :model="record" ref="record" size="mini" class='queryform' >
            <el-row class="iptrow">
              <el-col :span='13' class='iptcol'>
<!--                <el-form-item label="线路名称:">-->
<!--                  <el-input v-model="record.equipmentName"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="线路名称:" style="margin-bottom:15px;">
                  <el-select v-model="record.recordequipment">
                    <el-option v-for="item in LuName" :key="item.id" :label="item.inspection" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row class="iptrow">
              <el-col :span='29' class='iptcol'>
<!--                <el-form-item label="涉及设备:">-->
<!--&lt;!&ndash;                  <el-input v-model="plan.planequipment"></el-input>&ndash;&gt;-->
<!--                  <el-checkbox-group v-model="checkList">-->
<!--                    <el-checkbox label="复选框1"></el-checkbox>-->
<!--                    <el-checkbox label="复选框2"></el-checkbox>-->
<!--                    <el-checkbox label="复选框3"></el-checkbox>-->
<!--                    <el-checkbox label="复选框4" ></el-checkbox>-->
<!--                    <el-checkbox label="复选框5" ></el-checkbox>-->
<!--                    <el-checkbox label="复选框6"></el-checkbox>-->
<!--                    <el-checkbox label="复选框7"></el-checkbox>-->
<!--                    <el-checkbox label="复选框8" ></el-checkbox>-->
<!--                    <el-checkbox label="复选框9" ></el-checkbox>-->
<!--                  </el-checkbox-group>-->
<!--                </el-form-item>-->
                <el-form-item label="涉及设备">
                  <el-checkbox-group v-model="checkList">
                    <el-checkbox v-for="(h,name) in hobbyCheckbox"
                                 :key="name"
                                 :label="h.id">{{h.name}}</el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row  class="iptrow">
              <el-col :span='9' class='iptcol'>
                <el-form-item label="巡检时间:">
                  <el-date-picker
                    v-model="record.recordstartTime"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="请选择日期">
                  </el-date-picker><span class="fhbox">-</span>
                </el-form-item>
              </el-col>
              <el-col :span='9' class='iptcol'>
                <el-form-item label="-">
                  <el-date-picker
                    v-model="record.recordnameEndTime"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="请选择日期">
                  </el-date-picker><span class="fhbox">-</span>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row class="iptrow">
              <el-col :span='9' class='iptcol'>
                <el-form-item label="巡检人员:">
                  <el-input v-model="record.recordpersonnel"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span='9' class='iptcol'>
<!--                <el-form-item label="巡检结果:">-->
<!--                  <el-input v-model="record.recordresult"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="巡检结果:" style="margin-bottom:15px;">
                  <el-select v-model="record.recordresult">
                    <el-option v-for="item in jieguo" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row class="iptrow">
              <el-col :span='9' class='iptcol'>
                <el-form-item label="备注:">
                  <el-input  v-model="record.recordremarks"></el-input>
                </el-form-item>
              </el-col>


<!--            <el-row style="margin-left: 400px">-->
              <el-col :span='6'  class='iptcol'>
                <el-form-item>
                  <button class='search' >取消</button>
                </el-form-item>
              </el-col>
              <el-col :span='6'  class='iptcol'>
                <el-form-item>
                  <button class='search' @click="addjilu('record')">保存</button>
                </el-form-item>
              </el-col>
<!--            </el-row>-->
            </el-row>
          </el-form>
        </div>
      </div>
    </el-card>

  </div>

</template>

<script>
  export default {
    data () {
      return {
        checkList: [],
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
          Time: '',
          startTime:''
        },
        plan:{
          equipmentName:'',//线路名称
          planequipment:'',//包含设备
          planstartTime:'',//巡检开始时间
          plannameEndTime:'',//巡检结束时间
          planpersonnel:'',//巡检人员
        },
        LuName:{},
        hobbyCheckbox:{},
        jieguo:{},
        // checkList:'',//涉及设备
        record:{
          recordequipment:'',//线路名称
          recordstartTime:'',//巡检开始时间
          recordnameEndTime:'',//巡检结束时间
          recordpersonnel:'',//巡检人员
          recordresult:'',//巡检结果
          recordremarks:'',//备注
        },
        // strategytype:[
        //   {
        //     id:'1',
        //     text:'临时'
        //   },
        //   {
        //     id:'2',
        //     text:'专项'
        //   }
        // ],

        equipmentState:[],
        restaurant:[],

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
          // console.log(res)
          this.data=res.data
        })
      },
      // 下拉框数据
      selectlist(){
        this.$instance({
          url:'StEquipmentinspection.do/getInspectionAndResults',
          method:'post'
        }).then(res=>{
          console.log(res)
          this.LuName=res.data.inspection
          this.hobbyCheckbox=res.data.incequipment
          this.jieguo=res.data.results
          // this.restaurant=res.data.restaurant
        })
      },
      // 设备列表
      pagelist(){
        this.$instance({
          url:'StEquipmentinspection.do/selectEquipmentInspection',
          method:'post',
          data:{

            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "inspection":this.queryform.equipmentName, //线路名称
            "begintime":this.queryform.Time, //巡检时间
            "plantime":this.queryform.startTime, //计划时间
            "company":this.restaurantCode,// 公司外键
          }
        }).then(res=>{
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 查询列表
      querylist(){
        this.pagelist()
      },
      // 添加巡检计划
      addpolicybtn(queryform){
        // if(this.addform.equipmentName==''||this.addform.strategy==''||this.addform.Time==''
        //   ||this.addform.endTime==''||this.addform.taskdefinition==''||this.addform.Matchresources==''){
        //   // this.dialogaddVisible = false
        //   // this.$refs[queryform].resetFields()
        //   this.$message('所有选项不能为空');
        //   return
        // }
        this.$instance({
          url:'StEquipmentinspection.do/insertEquipmentInspection',
          method:'post',
          data:{
            param:{
              "inspection":this.plan.equipmentName,//线路名称
              "incequipment":this.plan.planequipment,  //包含设备，用‘，’隔开
               "plantime":this.plan.planstartTime,//巡检开始时间
               "planendtime":this.plan.plannameEndTime,//巡检结束时间
              "planner":this.plan.planpersonnel,//巡检人员
              "company":""

      }
          }
        }).then(res=>{
          // this.pagelist()
          this.$message.success('新增成功')
        })
        // this.$refs[plan].resetFields()
        this.dialogaddVisible = false
      },
      // 添加巡检记录
      addjilu(queryform){
        this.$instance({
          url:'StEquipmentinspection.do/updateEquipmentInspection',
          method:'post',
          data:{
            param:{
              "inspection":this.record.recordequipment,//线路名称
              "incequipment":this.checkList,  //包含设备，用‘，’隔开
              "begintime":this.record.recordstartTime,//巡检开始时间
              "endtime":this.record.recordnameEndTime,//巡检结束时间
              "inspectionperson":this.record.recordpersonnel,//巡检人员
              "results":this.record.recordresult,//巡检结果
              "remarks":this.record.recordremarks//备注
      }
          }
        }).then(res=>{
          // console.log(res)
          // this.pagelist()
          this.$message.success('新增成功')
        })
        // this.$refs[record].resetFields()
        this.dialogaddVisible = false
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
    height: 1160px;
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
