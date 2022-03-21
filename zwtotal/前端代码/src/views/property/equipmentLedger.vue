<template>
  <div class='container'>
    <div class='meatitle'>设备台账</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='10' class='iptcol'>
            <el-form-item label="">
              <el-input class="inputwidth" placeholder="请输入设备信息进行检索"  v-model="queryform.settlementamount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="querylist">搜索</button>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>
              <el-dialog class="adddialog" style="margin-top:100px;" width="700px" title="添加" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">
                <el-form :model="addform" ref='addform' label-width="80px" class="addcategory">
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="设备名称:" prop="policyName">
                        <el-input style="width:220px;" v-model="addform.policyName"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="设备编码:" prop="policyCode">
                        <el-input style="width:220px;" v-model="addform.policyCode"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="规格型号:" prop="restaurant">

                        <el-input style="width:220px;" v-model="addform.restaurant"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'> <el-form-item label="设备参数:"  style="margin-bottom:15px;" prop="meteringequipment">

                      <el-input style="width:220px;" v-model="addform.meteringequipment"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>

                      <el-form-item style="margin-bottom:15px;"  label="设备状态:" prop="equipmentstatus">
                        <el-input style="width:220px;" v-model="addform.equipmentstatus"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="当前位置:" prop="settlementamount">
                        <el-input style="width:220px;" v-model="addform.settlementamount"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>

                      <el-form-item style="margin-bottom:15px;"  label="生产厂家:" prop="manufacturer">
                        <el-input style="width:220px;" v-model="addform.manufacturer"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="出厂日期:" prop="outdate">
                        <el-input style="width:220px;" v-model="addform.outdate"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>

                      <el-form-item style="margin-bottom:15px;"  label="质保期:" prop="guaranteeperiod">
                        <el-input style="width:220px;" v-model="addform.guaranteeperiod"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="使用年限:" prop="durableyears">
                        <el-input style="width:220px;" v-model="addform.durableyears"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>


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
        <el-table-column align="center" min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
        <el-table-column align="center" min-width="15%" prop="name" label="设备名称"> </el-table-column>
        <el-table-column align="center" min-width="10%" prop="code" label="设备编码"></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="model" label="规格型号" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="parameter" label="参数" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="status" label="设备状态" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="location" label="当前位置" show-overflow-tooltip></el-table-column>
        <el-table-column align="center"  min-width="15%"  prop="manufacturer" label="生产厂家" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="production" label="出厂日期 " show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="period" label="质保期" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="servicelife" label="使用年限" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="26%" label="操作" >
          <template slot-scope="scope">

            <span  class="binicon"><img src="../../assets/u349.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="deletepolicy(scope.row.id)">删除</span>
            <span  class="binicon" @click="amend(scope.row.id)"><img src="../../assets/u363.svg" alt="" style="width:18px;height:16px;vertical-align: middle;"> 修改</span>
            <span  class="binicon" @click="QRcode(scope.row.id)">生成二维码</span>

          </template>
        </el-table-column>

      </el-table>
      <el-dialog  class="adddialog" style="margin-top:100px;" width="400px" title="二维码生成" :visible.sync="dialogCode" @close="closeaddDialog('addform')">
        <div id='dayinimg'>
            <el-image
                style="width: 300px; height: 300px;margin-left:50px;"
                :src="image"
                ></el-image>
            </div>
                <div class='btnbox'>
                  <button class='search' @click='dayin'>
                  打印
                </button>
                 <button class='search' @click='dialogCode=false'>
                  关闭
                </button>
                </div>
            </el-dialog>
            <el-dialog class="adddialog" style="margin-top:100px;" width="700px" title="添加" :visible.sync="dialogrevVisible" @close="closeaddDialog('addform')">
              <el-form :model="amendform" ref='amendform' label-width="80px" class="addcategory">
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="设备名称:" prop="policyName">
                      <el-input style="width:220px;" v-model="amendform.name"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;" label="设备编码:" prop="policyCode">
                      <el-input style="width:220px;" v-model="amendform.code"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;" label="规格型号:" prop="restaurant">

                      <el-input style="width:220px;" v-model="amendform.model"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'> <el-form-item label="设备参数:"  style="margin-bottom:15px;" prop="meteringequipment">

                    <el-input style="width:220px;" v-model="amendform.parameter"></el-input>
                    <span class="tab">*</span>
                  </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>

                    <el-form-item style="margin-bottom:15px;"  label="设备状态:" prop="equipmentstatus">
                      <el-input style="width:220px;" v-model="amendform.status"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="当前位置:" prop="settlementamount">
                      <el-input style="width:220px;" v-model="amendform.location"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>

                    <el-form-item style="margin-bottom:15px;"  label="生产厂家:" prop="manufacturer">
                      <el-input style="width:220px;" v-model="amendform.manufacturer"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="出厂日期:" prop="outdate">
                      <el-input style="width:220px;" v-model="amendform.production"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>

                    <el-form-item style="margin-bottom:15px;"  label="质保期:" prop="guaranteeperiod">
                      <el-input style="width:220px;" v-model="amendform.period"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="使用年限:" prop="durableyears">
                      <el-input style="width:220px;" v-model="amendform.servicelife"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>


              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="amendbtn()">修改</el-button>
              </div>
            </el-dialog>
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
        image:'',
        dialogaddVisible: false,
        dialogrevVisible:false,
        dialogCode:false,
        id:'',
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
        amendform:{},
        addform: {
          policyName:'',//设备名称
          policyCode:'',//设备编码
          restaurant:'',//规格型号
          meteringequipment: '',//设备参数
          equipmentstatus:'',//设备状态
          settlementamount:'',//当前位置
          manufacturer:'',//生产厂家
          outdate:'',//出厂日期
          guaranteeperiod:'',//质保期
          durableyears:'',//使用年限
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

      },
      // 列表
      pagelist(){
        this.$instance({
          url:'StEquipment.do/selectEquipment',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "company": this.restaurantCode,
            "name": this.queryform.settlementamount,
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
        // this.queryform.settlementamount=''
      },
      // 删除
      deletepolicy(id){
        console.log(id)
        this.$confirm('确认删除?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'StEquipment.do/deleteEquipment',
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
      //修改
      amend(id){
        this.id=id
        this.dialogrevVisible = true
        this.$instance({
          url:'StEquipment.do/selectEquipmentById',
          method:'post',
          data:{

              id:id
          }
        }).then(res=>{
          this.amendform=res.data
          // console.log(res.data)

        })
      },
      amendbtn(){
        // if(this.amendform.consumablename==''||this.amendform.consumabletype==''||this.amendform.consumablekind==''
        //   ||this.amendform.consumablenum==''||this.amendform.stock==''||this.amendform.recipient==''||this.amendform.recipientTime==''||this.amendform.recipientnumber==''
        //   ||this.amendform.Returnnumber==''||this.amendform.utilizationnumber==''||this.amendform.bumen==''){
        //   // this.dialogaddVisible = false
        //   this.$refs[amendform].resetFields()
        //   this.$message('带*号项不能为空');
        //   return
        // }
        this.$instance({
          url:'StEquipment.do/updateEquipment',
          method:'post',
          data:{

              "id":this.id,
              "name":this.amendform.name,
              "code":this.amendform.code,
              "model":this.amendform.model,
              "parameter":this.amendform.parameter,
              "status":this.amendform.status,
              "location":this.amendform.location,
              "manufacturer":this.amendform.manufacturer,
              "production":this.amendform.production,
              "period":this.amendform.period,
              "servicelife":this.amendform.servicelife,
              "company":""

          }
        }).then(res=>{
          this.pagelist()
          this.$message.success('修改成功')

        })
        // this.$refs[amendform].resetFields()

        this.dialogrevVisible = false
      },
      // 新增策略
      addpolicybtn(queryform){

        if(this.addform.policyName==''||this.addform.policyCode==''||this.addform.restaurant==''
          ||this.addform.meteringequipment==''||this.addform.equipmentstatus==''||this.addform.settlementamount==''||this.addform.manufacturer==''||this.addform.outdate==''||this.addform.guaranteeperiod==''||this.addform.durableyears==''){
          this.dialogaddVisible = false
          this.$refs[queryform].resetFields()
          this.$message('带*号项不能为空');
          return
        }
        this.$instance({
          url:'StEquipment.do/insertEquipment',
          method:'post',
          data:{

              "name":this.addform.policyName,//设备名字
              "code":this.addform.policyCode,//设备编码
              "model":this.addform.restaurant, //规格型号
              "parameter":this.addform.meteringequipment, //参数
              "status":this.addform.equipmentstatus,//设备状态
              "location":this.addform.settlementamount,//当前位置
              "manufacturer":this.addform.manufacturer,//生产厂家
              "production":this.addform.outdate,//出厂日期
              "period":this.addform.guaranteeperiod, //质保期
              "servicelife":this.addform.durableyears,//使用年限
              "company":''//公司外键

          }
        }).then(res=>{
          // console.log(res)
          this.pagelist()
          this.$message.success('新增成功')
        })
        this.$refs[queryform].resetFields()
        this.dialogaddVisible = false
      },
      // 修改排菜
      revisepolicy(id){
        if(this.reviseform.policyName==''||this.reviseform.policyCode==''||this.reviseform.restaurant==''
          ||this.reviseform.meteringequipment==''||this.reviseform.applicabletime==''||this.reviseform.settlementamount==''||this.reviseform.remarks==''){
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
      //二维码
      QRcode(id){
        this.dialogCode=true
        this.$instance({
          url:'StEquipment.do/getCode',
          method:'post',
          data:{
            id:id
          }
        }).then(res=>{
          this.image=res.data
          // console.log(this.image)

        })

      },
      // 打印
      dayin(){
        var newstr = document.getElementById('dayinimg').innerHTML;
        var oldstr = document.body.innerHTML;
        document.body.innerHTML = newstr;
        window.print();
        document.body.innerHTML = oldstr;
      },
      //关闭事件
      closeaddDialog(queryform){
        // this.$refs[queryform].resetFields()
      },
      closereviseDialog(queryform){
        // this.$refs[queryform].resetFields()
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
    height:56px;
    margin-left:300px;
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
    width: 600px;
  }
.btnbox{
  text-align: center;
  margin: 10px 0 10px 0;
}
</style>
