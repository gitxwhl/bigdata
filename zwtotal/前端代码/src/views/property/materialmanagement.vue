<script src="../../api/index.js"></script>
<template>
  <div class='container'>
    <div class='meatitle'>保洁耗材管理</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
      <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
        <el-row class="iptrow">
          <el-col :span='4' class='iptcol'>
            <el-form-item label="">
              <el-input class="inputwidth" placeholder="请输入耗材名称"  v-model="queryform.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='search' @click.prevent @click="querylist">搜索</button>

            </el-form-item>

          </el-col>
          <el-col :span='2'  class='iptcol'>
              <el-form-item>
                <input style="display:none;" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel()">
                <button @click.prevent type="button" class='leadingin'  @click="leadingin"><i ></i>  导入</button>
              </el-form-item>

          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='leadingout' @click.prevent @click="exportout">导出</button>
            </el-form-item>

          </el-col>
          <el-col :span='2'  class='iptcol'>
            <el-form-item>
              <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>
              <el-dialog class="adddialog" style="margin-top:100px;" width="700px" title="新建工具/耗材信息" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">
                <el-form :model="addform" ref='addform' label-width="80px" class="addcategory">
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="耗材名称:" prop="consumablename">
                        <el-input style="width:220px;" v-model="addform.consumablename"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
<!--                      <el-form-item style="margin-bottom:15px;"  label="耗材分类:" prop="consumabletype">-->
<!--                        <el-input style="width:220px;" v-model="addform.consumabletype"></el-input>-->
<!--                        <span class="tab">*</span>-->
<!--                      </el-form-item>-->
                      <el-form-item label="耗材分类:">
                        <el-select v-model="addform.consumabletype">
                          <el-option v-for="item in haocaitype" :key="item.id" :label="item.text" :value="item.text">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>
<!--                      <el-form-item style="margin-bottom:15px;"  label="耗材种类:" prop="consumablekind ">-->
<!--                        <el-input style="width:220px;" v-model="addform.consumablekind"></el-input>-->
<!--                        <span class="tab">*</span>-->
<!--                      </el-form-item>-->
                      <el-form-item label="耗材种类:">
                        <el-select v-model="addform.consumablekind">
                          <el-option v-for="item in kind" :key="item.id" :label="item.text" :value="item.text">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="耗材编号:" prop="consumablenum">
                        <el-input style="width:220px;" v-model="addform.consumablenum"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="库存量:" prop="stock">
                        <el-input style="width:220px;" v-model="addform.stock"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
              <span style="font-size: 16px;color: white;margin-left: 15px;margin-top: 10px;font-weight: 700;">新建工具/耗材领用信息</span>
                  <el-row style="margin-top: 40px">
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="领用人:" prop="recipient">
                        <el-input style="width:220px;" v-model="addform.recipient"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="领用时间:" prop="recipientTime">
                        <el-input style="width:220px;" v-model="addform.recipientTime"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;"  label="领用数量:" prop="recipientnumber ">
                        <el-input style="width:220px;" v-model="addform.recipientnumber"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="归还数量:" prop="Returnnumber">
                        <el-input style="width:220px;" v-model="addform.Returnnumber"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span='12'>
                      <el-form-item style="margin-bottom:15px;" label="使用数量:" prop="utilizationnumber">

                        <el-input style="width:220px;" v-model="addform.utilizationnumber"></el-input>
                        <span class="tab">*</span>
                      </el-form-item>
                    </el-col>
<!--                    <el-col :span='12'>-->
<!--                      <el-form-item style="margin-bottom:15px;" label="部门:" prop="utilizationnumber">-->

<!--                        <el-input style="width:220px;" v-model="addform.bumen"></el-input>-->
<!--                        <span class="tab">*</span>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
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
        <el-table-column align="center" min-width="10%" prop="name" label="工具/耗材名称"> </el-table-column>
        <el-table-column align="center" min-width="10%" prop="classification" label="分类">
<!--          <template slot-scope="scope">-->
<!--            <span v-if="scope.row.classification == 1">工具</span>-->
<!--            <span v-if="scope.row.classification == 2">材料</span>-->
<!--          </template>-->
        </el-table-column>
        <el-table-column align="center" min-width="10%"  prop="type" label="工具/耗材种类" show-overflow-tooltip>
<!--          <template slot-scope="scope">-->
<!--            <span v-if="scope.row.type == 1">大型工具</span>-->
<!--            <span v-if="scope.row.type == 2">小型工具</span>-->
<!--          </template>-->
        </el-table-column>
        <el-table-column align="center" min-width="15%"  prop="no" label="编号" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%"  prop="inventory" label="库存数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="8%"  prop="received" label="领用数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="15%"  prop="recipients" label="领用人" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="collectiontime" label="领用时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="returntime" label="归还时间" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="10%"  prop="usenum" label="耗材使用数量" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" min-width="20%" label="操作" >
          <template slot-scope="scope">
            <span  class="binicon"><img src="../../assets/u349.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="deletepolicy(scope.row.id)">删除</span>
            <span  class="editicon"><img src="../../assets/u363.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="amend(scope.row.id)"> 修改</span>
            <el-dialog class="adddialog" style="margin-top:100px;" width="700px" title="新建工具/耗材信息" :visible.sync="dialogrevVisible" @close="closeaddDialog('addform')">
              <el-form :model="addform" ref='addform' label-width="80px" class="addcategory">
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="耗材名称:" prop="consumablename">
                      <el-input style="width:220px;" v-model="amendform.name"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <!--                      <el-form-item style="margin-bottom:15px;"  label="耗材分类:" prop="consumabletype">-->
                    <!--                        <el-input style="width:220px;" v-model="addform.consumabletype"></el-input>-->
                    <!--                        <span class="tab">*</span>-->
                    <!--                      </el-form-item>-->
                    <el-form-item label="耗材分类:">
                      <el-select v-model="amendform.classification">
                        <el-option v-for="item in haocaitype" :key="item.id" :label="item.text" :value="item.text">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>
                    <!--                      <el-form-item style="margin-bottom:15px;"  label="耗材种类:" prop="consumablekind ">-->
                    <!--                        <el-input style="width:220px;" v-model="addform.consumablekind"></el-input>-->
                    <!--                        <span class="tab">*</span>-->
                    <!--                      </el-form-item>-->
                    <el-form-item label="耗材种类:">
                      <el-select v-model="amendform.type">
                        <el-option v-for="item in kind" :key="item.id" :label="item.text" :value="item.text">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;" label="耗材编号:" prop="consumablenum">
                      <el-input style="width:220px;" v-model="amendform.no"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;" label="库存量:" prop="stock">

                      <el-input style="width:220px;" v-model="amendform.inventory"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>

                </el-row>

                <span style="font-size: 16px;color: white;margin-left: 15px;margin-top: 10px;font-weight: 700;">新建工具/耗材领用信息</span>
                <el-row style="margin-top: 40px">
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="领用人:" prop="recipient">
                      <el-input style="width:220px;" v-model="amendform.recipients"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="领用时间:" prop="recipientTime">
                      <el-input style="width:220px;" v-model="amendform.collectiontime"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;"  label="领用数量:" prop="recipientnumber ">
                      <el-input style="width:220px;" v-model="amendform.received"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;" label="归还数量:" prop="Returnnumber">
                      <el-input style="width:220px;" v-model="amendform.returntime"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span='12'>
                    <el-form-item style="margin-bottom:15px;" label="使用数量:" prop="utilizationnumber">

                      <el-input style="width:220px;" v-model="amendform.usenum"></el-input>
                      <span class="tab">*</span>
                    </el-form-item>
                  </el-col>
<!--                  <el-col :span='12'>-->
<!--                    <el-form-item style="margin-bottom:15px;" label="部门:" prop="utilizationnumber">-->

<!--                      <el-input style="width:220px;" v-model="amendform.departmentId"></el-input>-->
<!--                      <span class="tab">*</span>-->
<!--                    </el-form-item>-->
<!--                  </el-col>-->
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="amendbtn(scope.row.id)">修改</el-button>
              </div>
            </el-dialog>
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
    </el-card>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        dialogaddVisible: false,
        dialogrevVisible:false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        restaurantCode:'',//树状图id
        applicabletime:[],//适用时间
        equipment:[],//关联设备
        restaurant:[],//运维餐厅
        queryform: {
          name: '',//搜索查询
        },
        haocaitype:[{   //耗材分类
          id:'',
          text:'全部'
        },
          {
            id:'1',
            text:'工具'
          }
          ,
          {
            id:'2',
            text:'材料'
          }
        ],
        kind:[{   //耗材种类
          id:'',
          text:'全部'
        },
          {
            id:'1',
            text:'大型工具'
          }
          ,
          {
            id:'2',
            text:'小型工具'
          }
        ],
        amendform:{},
        addform: {
          consumablename:'',//耗材名称
          consumabletype:'',//耗材分类
          consumablekind:'',//耗材种类
          consumablenum: '',//耗材编号
          stock:'',//库存量
          recipient:'',//领用人
          recipientTime:'',//领用时间
          recipientnumber:'',//领用数量
          Returnnumber:'',//归还数量
          utilizationnumber:'',//使用数量
          bumen:'',//部门


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
          url:'wyToolsconsumables.do/getToolConsumption',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "param": {
              "departmentId": this.restaurantCode,
              "name": this.queryform.name,
              "type": '',
            }
          }
        }).then(res=>{
          console.log(res)
          this.tableData=res.data.list
          this.total=res.data.totalRecord
        })
      },
      // 查询列表
      querylist(){
        this.pagelist()
        // this.queryform.Name=''
      },
      // 删除
      deletepolicy(id){
        // console.log(id)
        this.$confirm('确认删除该菜品?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'wyToolsconsumables.do/deleteMaterialsStaf',
            method:'post',
            data:{
              "param": {
                id:id
              }

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
        this.dialogrevVisible = true
        this.$instance({
          url:'wyToolsconsumables.do/insertDetails',
          method:'post',
          data:{
            "param": {
              id:id
            }
          }
        }).then(res=>{
          this.amendform=res.data
          // console.log(this.amendform)

        })
      },
      amendbtn(id){
     // console.log(id)
        this.dialogrevVisible = false
        // if(this.amendform.consumablename==''||this.amendform.consumabletype==''||this.amendform.consumablekind==''
        //   ||this.amendform.consumablenum==''||this.amendform.stock==''||this.amendform.recipient==''||this.amendform.recipientTime==''||this.amendform.recipientnumber==''
        //   ||this.amendform.Returnnumber==''||this.amendform.utilizationnumber==''||this.amendform.bumen==''){
        //   // this.dialogaddVisible = false
        //   this.$refs[amendform].resetFields()
        //   this.$message('带*号项不能为空');
        //   return
        // }
        this.$instance({
          url:'wyToolsconsumables.do/updateMaterialsStaff',
          method:'post',
          data:{
            "param": {
              "id":id,
              "name":this.amendform.name,  //耗材名称
              "classification": this.amendform.classification, //耗材分类
              "type": this.amendform.type, //耗材种类
              "no":this.amendform.no,//耗材编号
              "inventory":this.amendform.inventory,//库存量
              "recipients":this.amendform.recipients,  //领用人
              "collectiontime": this.amendform.collectiontime, //领用时间
              "received": this.amendform.received, //领用数量
              "returntime":this.amendform.returntime,//归还数量
              "usenum":this.amendform.usenum,//使用数量
              "departmentId": this.amendform.departmentId  //部门
            }
          }
        }).then(res=>{
          this.pagelist()
          this.$message.success('修改成功')

        })
        // this.$refs[amendform].resetFields()

        this.dialogrevVisible = false
      },
      addpolicybtn(queryform){
        if(this.addform.consumablename==''||this.addform.consumabletype==''||this.addform.consumablekind==''
          ||this.addform.consumablenum==''||this.addform.stock==''||this.addform.recipient==''||this.addform.recipientTime==''||this.addform.recipientnumber==''
          ||this.addform.Returnnumber==''||this.addform.utilizationnumber==''){
          // this.dialogaddVisible = false
          this.$refs[queryform].resetFields()
          this.$message('所有选项不能为空');
          return
        }
        this.$instance({
          url:'wyToolsconsumables.do/insertMaterialsStaff',
          method:'post',
          data:{
            "param":{
              "name":this.addform.consumablename,  //耗材名称
              "classification": this.addform.consumabletype, //耗材分类
              "type": this.addform.consumablekind, //耗材种类
              "no":this.addform.consumablenum,//耗材编号
              "inventory":this.addform.stock,//库存量
              "recipients":this.addform.recipient,  //领用人
              "collectiontime": this.addform.recipientTime, //领用时间
              "received": this.addform.recipientnumber, //领用数量
              "returntime":this.addform.Returnnumber,//归还数量
              "usenum":this.addform.utilizationnumber,//使用数量
              "departmentId":this.restaurantCode,//部门
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
      // 导出
      exportout(){
        this.$instance({
          url:'wyToolsconsumables.do/fileImport',
          method:'post',
          // data:{
          //   "param":{
          //     "restaurantName":this.restaurantCode,
          //     "equipmentName":this.queryform.equipmentName,
          //     "dishName":this.queryform.dishName
          //   }
          // },

          responseType:'blob'
        }).then(res=>{
          let data = res;
          let blob = new Blob([data.data], { type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"});
          let url = window.URL.createObjectURL(blob);
          let link = document.createElement("a");
          link.style.display = "none";
          link.href = url;
          link.setAttribute("download", "保洁耗材管理列表.xls");
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        })
      },
      // 导入过的数据
      importarray(){
        this.$instance({
          url:'StMeasurechoicedishes.do/getfileName',
          method:'post'
        }).then(res=>{
          this.import=res.data
          console.log(this.import)
        })
      },
      // 导入
      importExcel(){
        console.log(this.$refs.articleImageFile.files[0].name)
        for(var i=0;i<this.import.length;i++){
          if(this.$refs.articleImageFile.files[0].name==this.import[i]){
            this.$message.error('该文件名已经导入过')
            this.$refs.articleImageFile.value=''
            return
          }
        }
        var formData = new FormData();
        formData.append("file",this.$refs.articleImageFile.files[0]);
        this.$instance({
          url:'wyToolsconsumables.do/fileExample',
          method: 'POST',
          data:formData,
          // headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        }).then(res=>{
          if(res){
            this.$message.success('导入成功')
            this.importarray()
            this.pagelist()
          }else{
            this.$message.error('导入失败')

          }
        })
        this.$refs.articleImageFile.value=''

      },
      leadingin(){
        this.$refs.articleImageFile.click()
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
  .download{
    color:rgb(255,255,255) ;
    box-sizing: border-box;
    font-size: 10px;
    width:80px;
    height:32px;
    line-height: 32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
    display:inline-block;
    text-decoration:none;
    text-align:center;
  }
  .load{
    color:rgb(255,255,255) ;
    box-sizing: border-box;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
    display:inline-block;
    text-decoration:none;
    text-align:center;
  }
  .download:hover{
    background:url(../../assets/zy2.png) no-repeat;
    background-size:100% 100%;
  }
  .search,.increase,.leadingin,.leadingout{
    cursor: pointer;
  }
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
</style>
