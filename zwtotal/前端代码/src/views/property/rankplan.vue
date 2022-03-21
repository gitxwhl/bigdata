<template>
  <div class='container'>
    <div class='meatitle'>秩维计划</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
<el-card class='tablecard'>
  <div class='topbox'>
      <span class='listbox'  @click.prevent @click="show">排班计划</span>
      <span class='listbox' style="position:relative;"  @click.prevent @click="show2">巡检计划<button @click.prevent @click="dialogaddVisible = true" class='absobtn' v-show="Rankshow2">新增</button></span>
      <span class='listbox' style="position:relative;"  @click.prevent @click="show3">岗位资料<button @click.prevent @click="dialogpostVisible=true" class='absobtn' v-show="Rankshow3">新增</button></span>
  </div>

  <div v-show="Rankshow1">排班计划</div>
  <div v-show="Rankshow2">
    <!-- 新增询价计划 -->
    <el-dialog class="adddialog"  title="添加" :visible.sync="dialogaddVisible" @close="closeInspection">
              <el-form :model="queryform" ref='addform'  class="addcategory">
                <el-row class='iptrow'>
                  <el-col :span='24'>
                    <el-form-item style="margin-bottom:15px;"  label="场所:" prop="spacename">
                      <el-input class='w220px' v-model="queryform.place"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row  class='iptrow'>
                  <el-col :span='24'>
                    <el-form-item style="margin-bottom:15px;"  label="巡检任务:" prop="coveredarea ">
                      <el-input class='w220px'  v-model="queryform.inspectiontask"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row  class='iptrow'>
                  <el-col :span='24'>
                    <el-form-item style="margin-bottom:15px;" label="时间安排:" prop="Greeningratio">
                      <el-input class='w220px' v-model="queryform.schedule"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row  class='iptrow'>
                  <el-col :span='24'>
                    <el-form-item  style="margin-bottom:15px;" label="巡检人员:" prop="address">
                      <el-select multiple v-model="queryform.personnel" value-key='id'>
                        <el-option v-for="(item,i) in person" :key="i" :label="item.text" :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>

                </el-row>
                <el-row  class='iptrow'>

                  <el-col :span='24'> <el-form-item label="工作内容:"  style="margin-bottom:15px;" prop="details">

                    <el-input class='w220px' type="textarea" v-model="queryform.content"></el-input>
                  </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button style="margin-top:30px;" class="search"  type="primary" @click="addInspection">确定</el-button>
              </div>
            </el-dialog>
    <el-table stripe ref="multipleTable" class='plantable' :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
              size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
      <el-table-column align="center" min-width="5%" type="selection" :selectable="selectable" width="55"> </el-table-column>
      <el-table-column align="center" min-width="15%" prop="place" label="场所"> </el-table-column>
      <el-table-column align="center" min-width="10%" prop="inspectiontask" label="巡检任务"></el-table-column>
      <el-table-column align="center" min-width="15%"  prop="schedule" label="时间安排" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" min-width="20%"  prop="personnel" label="巡检人员" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" min-width="20%"  prop="content" label="工作内容" show-overflow-tooltip></el-table-column>
<!--      <el-table-column align="center" min-width="8%"  prop="settlementamount" label="附件" show-overflow-tooltip></el-table-column>-->
      <el-table-column align="center" min-width="20%" label="操作" >
        <template slot-scope="scope">
          <span  class="binicon"><img src="../../assets/u349.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="deleteInspection(scope.row.id)">删除</span>
          <span  class="editicon"><img src="../../assets/u363.svg" alt="" style="width:18px;height:16px;vertical-align: middle;" @click="updateInspection(scope.row)"> 修改</span>
        </template>
      </el-table-column>
    </el-table>


          <!-- 修改巡检计划 -->
          <el-dialog class="adddialog"  title="修改" :visible.sync="dialogchangeVisible" @close="closeInspection">
                        <el-form :model="changeform" ref='addform'  class="addcategory">
                          <el-row class='iptrow'>
                            <el-col :span='24'>
                              <el-form-item style="margin-bottom:15px;"  label="场所:" prop="spacename">
                                <el-input class='w220px' v-model="changeform.place"></el-input>
                              </el-form-item>
                            </el-col>
                          </el-row>
                          <el-row  class='iptrow'>
                            <el-col :span='24'>
                              <el-form-item style="margin-bottom:15px;"  label="巡检任务:" prop="coveredarea ">
                                <el-input class='w220px'  v-model="changeform.inspectiontask"></el-input>
                              </el-form-item>
                            </el-col>
                          </el-row>
                          <el-row  class='iptrow'>
                            <el-col :span='24'>
                              <el-form-item style="margin-bottom:15px;" label="时间安排:" prop="Greeningratio">
                                <el-input class='w220px' v-model="changeform.schedule"></el-input>
                              </el-form-item>
                            </el-col>
                          </el-row>
                          <el-row  class='iptrow'>
                            <el-col :span='24'>
                              <el-form-item  style="margin-bottom:15px;" label="巡检人员:" prop="address">
                                <el-select multiple v-model="changeform.personnel" value-key='id'>
                                  <el-option v-for="(item,i) in person" :key="i" :label="item.text" :value="item.id">
                                  </el-option>
                                </el-select>
                              </el-form-item>
                            </el-col>

                          </el-row>
                          <el-row  class='iptrow'>

                            <el-col :span='24'> <el-form-item label="工作内容:"  style="margin-bottom:15px;" prop="details">

                              <el-input class='w220px' type="textarea" v-model="changeform.content"></el-input>
                            </el-form-item>
                            </el-col>
                          </el-row>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                          <el-button style="margin-top:30px;" class="search"  type="primary" @click="changeInspection">确定</el-button>
                        </div>
            </el-dialog>




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



  <div v-show="Rankshow3" class='contentbox'>
      <div class='vforbox clearfix' v-for="(item,index) in list" :key="index">
          <img :src="item.photo" alt="">
          <div class='infobox'>
              <div class='infoname'>{{item.name}}</div>
              <div class='infopost'>岗位:{{item.post}}</div>
              <div class='infopost'>电话:{{item.telephone}}</div>
          </div>
      </div>
      <el-dialog class="postdialog"  title="新增" :visible.sync="dialogpostVisible" @close="closepost">
              <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">
              <div class='addimg' @click="leadingin">
                <img :src='image' alt="">
                <div class='absotext'>点此上传照片</div>
              </div>
              <div class='postinput'>
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                        姓名 : <el-input class='inputwidth'  v-model="addform.name"></el-input>
                    </el-col>
                  </el-row>
                   <el-row class='iptrow'>
                    <el-col :span='24'>
                        岗位 : <el-input class='inputwidth' v-model="addform.post"></el-input>
                    </el-col>
                  </el-row>
                   <el-row class='iptrow'>
                    <el-col :span='24'>
                        联系电话 : <el-input class='inputwidth' v-model="addform.telephone"></el-input>
                    </el-col>
                  </el-row>
              </div>

              <div slot="footer" class="dialog-footer">
                <el-button  class="addbtn"  type="primary" @click="insertperson">保存</el-button>
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

</el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        id:'',
        image:'',
        file:'',
        list:[],
        person:[],
        frequency:[],
        Rankshow1:true,
        Rankshow2:false,
        Rankshow3:false,
        dialogaddVisible: false,
        dialogpostVisible:false,
        dialogchangeVisible:false,
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        pageIndex2: 1,
        pageSize2:10,
        pageSizes2:[ 10, 20 , 30, 50, 100],
        total2:0,
        restaurantCode:'',//树状图id
        queryform: {
          place: '',
          inspectiontask:'',
          schedule:'',
          personnel:[],
          content:''
        },
        changeform:{
          id:'',
          place: '',
          inspectiontask:'',
          schedule:'',
          personnel:[],
          content:''
        },
        addform: {
          name:'',
          post:'',
          telephone:''
        },
        tableData: [],
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'name'
        }
      }
    },
    created(){
      this.treelist()
      // this.pagelist()
      this.selectlist()
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize=val
        this.personlist()

      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.personlist()

      },
      handleSizeChange2 (val) {
        this.pageSize2=val

      },
      handleCurrentChange2 (val) {
        this.pageIndex2=val

      },
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.personlist()
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
        this.$instance({
          url:'Department.do/getDepartment',
          method:'get'
        }).then(res=>{
          this.data=res.data
        })
      },
      show () {
        this.Rankshow1 = true
        this.Rankshow2 = false
        this.Rankshow3 = false
      },
      show2 () {
        this.Rankshow2 = true
        this.Rankshow1 = false
        this.Rankshow3 = false
        this.inspectionList()
      },
      show3 () {
        this.Rankshow2 = false
        this.Rankshow1 = false
        this.Rankshow3 = true
        this.personlist()
      },
      // 下拉框数据
      selectlist(){
        this.$instance({
          url:'Wyperson.do/dropDownBox',
          method:'get'
        }).then(res=>{
          this.person=res.data.person
          this.frequency=res.data.frequency
        })
      },
      // 巡检计划列表
      inspectionList(){
        this.$instance({
          url:'Wyperson.do/inspectionList',
          method:'post',
          data:{
          "pageNum": this.pageIndex2,
          "pageSize":this.pageSize2,
        }
        }).then(res=>{
          this.total2=res.data.totalRecord
          this.tableData=res.data.list
        })
      },
      // 新增巡检计划确定
      addInspection(){
        if(this.queryform.place==''||this.queryform.inspectiontask==''||this.queryform.schedule==''||this.queryform.personnel.length==0||this.queryform.content==''){
          this.$message('请补全表单信息')
          return
        }
        this.$instance({
          url:'Wyperson.do/addInspection',
          method:'post',
          data:this.queryform
        }).then(res=>{
          if(res.data=='新增成功'){
            this.$message.success('新增成功')
            this.inspectionList()
          }else{
            this.$message.error('新增失败')
          }
          this.queryform.place=''
          this.queryform.inspectiontask=''
          this.queryform.schedule=''
          this.queryform.content=''
          this.queryform.personnel=[]
          this.dialogaddVisible=false
        })
      },
      // 关闭事件
      closeInspection(){
          this.queryform.place=''
          this.queryform.inspectiontask=''
          this.queryform.schedule=''
          this.queryform.content=''
          this.queryform.personnel=[]
          this.dialogaddVisible=false
      },
      // 修改巡检计划
      updateInspection(row){
        this.dialogchangeVisible=true
        this.changeform.place=row.place
        this.changeform.inspectiontask=row.inspectiontask
        this.changeform.schedule=row.schedule
        this.changeform.content=row.content
        this.changeform.personnel=row.ids
        this.changeform.id=row.id

      },
      // 修改确认按钮
      changeInspection(){
        this.$instance({
          url:'Wyperson.do/updateInspection',
          method:'post',
          data:this.changeform
        }).then(res=>{
          if(res.data=='修改成功'){
            this.$message.success('修改成功')
            this.inspectionList()
          }else{
            this.$message.error('修改失败')
          }
          this.dialogchangeVisible=false
        })
      },
      // 删除巡检计划
      deleteInspection(id){
        this.$confirm('确认删除巡检计划?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          this.$instance({
            url:'Wyperson.do/deleteInspection',
            method:'post',
            data:{
              id:id
            }
          }).then(res=>{
            if(res.data=='删除成功'){
              this.$message.success('删除成功')
              this.inspectionList()
            }else{
              this.$message.error('删除失败')
            }
          })
        }).catch(()=>{

        })
      },
       // 单击上传图片按钮
    importExcel(e){
            this.image=URL.createObjectURL(this.$refs.articleImageFile.files[0])
            this.file=this.$refs.articleImageFile.files[0]
            console.log(this.file)
    },
      // 上传图片按钮
    leadingin(){
        this.$refs.articleImageFile.click()
      },
    // 新增岗位资料
    insertperson(){
        if(this.addform.name==''||this.addform.post==''||this.addform.telephone==''){
            this.$message('请补全信息')
            return
        }
        if(this.file==''){
          this.$message('请上传照片')
            return
        }
        var reader = new FileReader();
        reader.readAsDataURL(this.file);
        var that=this
        reader.onload=function(e){
          console.log(e)
          that.$instance({
            url:'Wyperson.do/insertWyPerson',
            method:'post',
            data:{
              "name":that.addform.name,
              "post":that.addform.post,
              "telephone":that.addform.telephone,
              "photo":e.target.result
            }
          }).then(res=>{
              if(res.data=='新增成功'){
                that.$message.success('新增成功')
                that.personlist()
              }else{
                that.$message.error('新增失败')
              }
              that.addform.name=''
              that.addform.post=''
              that.addform.telephone=''
              // that.file=''
              // that.image=''
              that.dialogpostVisible=false
          })
        }
    },
    // 新增关闭
    closepost(){
      this.addform.name=''
      this.addform.post=''
      this.addform.telephone=''
      // this.image=''
      // this.file=''
      this.dialogpostVisible=false
    },
    // 岗位资料列表
    personlist(){
      this.$instance({
        url:'Wyperson.do/wypersonList',
        method:'post',
        data:{
          "pageNum": this.pageIndex,
          "pageSize":this.pageSize,
        }
      }).then(res=>{
        this.total=res.data.totalRecord
        this.list=res.data.list
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
    font-size: 12px;
    margin-bottom:0px;
    padding: 14px 0px 0px 30px;
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
    width: 400px;
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
  .addbtn{
    width: 100px;
    height: 40px;
    color:rgb(255,255,255) ;
    font-size: 14px;
    padding: 12px 20px;
    background-color: rgb(1,7,34);
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
    margin-top: 0px;
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
  .topbox{
    border-bottom:1px solid #797979 ;
    box-sizing: border-box;
  }
  .listbox{
    display: inline-block;
    color:white;
    font-size: 14px;
    padding:10px 40px;
    box-sizing: border-box;
    border-bottom:2px solid #001D92;
    margin-bottom: -1px;
  }
  .listbox:hover{
    cursor: pointer;
    border-bottom: 2px solid #0033FF;
  }
  .absobtn{
    position: absolute;
    width: 100%;
    height: 100%;
    bottom: -100%;
    left:0;
    z-index: 99;
    font-size: 14px;
    background-color:transparent;
    border: 2px solid #001D92;
    border-radius: 5px;
    color:white;
    cursor: pointer;
  }
  .contentbox{
    width: 100%;
    margin-top: 50px;
  }
  .vforbox{
    width: 300px;
    height: 183px;
    display: inline-block;
    background:url(../../assets/u739.png)no-repeat;
    background-size: 100% 100%;
    padding: 15px;
    box-sizing: border-box;
    margin:0 5px 20px 0;
  }
  .vforbox img{
    width: 117px;
    height: 150px;
    float: left;
  }
  .infobox{
    float: left;
    padding:10px 0 0 8px;
    color:white;
    width: 130px;
  }
  .infoname{
    font-size: 18px;
    font-weight: 500;
    margin-bottom:20px;
  }
  .infopost{
    font-size: 14px;
    margin-bottom: 20px;
  }
  .postdialog /deep/.el-dialog{
    width: 500px;
  }
  .postdialog  /deep/.el-dialog__body{
    padding: 10px 10px 0 40px;
  }
  .addimg{
    width: 117px;
    height: 150px;
    background: url(../../assets/u740.svg)no-repeat;
    background-size: 100% 100%;
    position: relative;
    color: black;
    text-align: center;
    font-size: 12px;
    display: inline-block;

  }
  .absotext{
    position: absolute;
    top:50%;
    left: 50%;
    margin-left: -36px;
    margin-top: -8px;
    color: white;
    z-index: 1000;
    cursor: pointer;
  }
  .addimg img{

    width: 100%;
    height: 100%;
  }
.postinput{
  display: inline-block;
  vertical-align: top;
}
.inputwidth,.inputwidth /deep/.el-input__inner{
  width: 200px!important;
}
.plantable{
  margin-top:40px!important;
}
.w220px{
  width: 220px;
}
/deep/.el-input--suffix{
  width: 400px;
}
</style>
