<template>
    <div class='container'>
        <div class='meatitle'>计量选餐排菜</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform'>
            <el-row class="iptrow">
                <el-col :span='6' class='iptcol'>
                    <el-form-item label="计量设备:">
                        <el-input v-model="queryform.equipmentName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='7' class='iptcol'>
                    <el-form-item  label="菜品名称:">
                        <el-input v-model="queryform.dishName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='search' @click.prevent @click="querylist">查询</button>
                    </el-form-item>
                </el-col>
                 <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='increase' @click="dialogaddVisible = true" @click.prevent>新增</button>
                        <el-dialog class="adddialog"  title="添加类别" :visible.sync="dialogaddVisible" @close="closeaddDialog('addform')">
                            <el-form :model="addform" ref="addform"  class="addcategory">
                                <el-form-item   label="计量设备:" prop="meteringEquipment">
                                   <el-select class='selipt' v-model="addform.meteringEquipment">
                                    <el-option v-for="item in equipment" :key="item.id" :label="item.equipmentname" :value="item.id">
                                  </el-option>
                              </el-select>
                              <span class="tab">*</span>
                                </el-form-item>
                               <el-form-item label="菜品名称:" prop="dishName">
                                  <el-select class='selipt' v-model="addform.dishName">
                                    <el-option v-for="item in food" :key="item.dishCode" :label="item.dishName" :value="item.dishCode">
                                  </el-option>
                              </el-select>
                              <span class="tab">*</span>
                                </el-form-item>
                                <el-form-item  label="所属餐厅:" prop="restaurant">
                                  <el-select class='selipt' v-model="addform.restaurant">
                                    <el-option v-for="item in restaurant" :key="item.RestaurantCode" :label="item.RestaurantName" :value="item.RestaurantCode">
                                  </el-option>
                              </el-select>
                              <span class="tab">*</span>
                                </el-form-item>
                                <el-form-item label="菜品数量:"   prop="numbers">
                                      <el-input  v-model="addform.numbers"></el-input> 份<span class="tab">*</span>
                                </el-form-item>
                                <el-form-item label="备注:" prop="remarks">
                                  <el-input style="width:180px;height:60px;" type="textarea" v-model="addform.remarks"></el-input>
                                </el-form-item>
                              </el-form>
                                <span class="point">提示：XX1份xx克，参考价格x元，实际装入xx份，实际重量xxx克，实际参考价格xx元，实际价格x元。</span>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="addfoodbtn('addform')">添加</el-button>
                              </div>
                        </el-dialog>
                    </el-form-item>
                </el-col>
                 <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <input style="display:none;" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel()">
                        <button @click.prevent type="button" class='leadingin'  @click="leadingin"><i ></i>  导入</button>
                    </el-form-item>
                </el-col>
                 <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <button class='leadingout' @click.prevent @click="exportout">导出</button>
                    </el-form-item>
                </el-col>
                <el-col :span='1.5'  class='iptcol'>
                    <el-form-item>
                        <a href="./测试排菜导入.xlsx" download="模板下载.xlsx" class="download">模板下载</a>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
        <el-table stripe ref="multipleTable" class='table' :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="740px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="meteringEquipment" label="计量设备"> </el-table-column>
                <el-table-column align="center"  min-width="10%" prop="dishName" label="菜品名称"></el-table-column>
                <el-table-column align="center"  min-width="15%"  prop="restaurant" label="所属餐厅" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="numbers" label="数量(份)" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="6%"  prop="weight" label="重量(g)" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="referencePrice" label="参考价格" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="20%"  prop="remarks" label="备注" show-overflow-tooltip></el-table-column>
                <el-table-column min-width="20%" label="操作" >
                    <template slot-scope="scope">
                            <span  class="binicon"><img src="../../assets/u349.svg" alt=""  @click="deletedishes(scope.row.id)">删除</span>
                            <span  class="editicon"><img src="../../assets/u363.svg" alt=""  @click="dialogrevVisible = true"> 修改</span>
                            <el-dialog class="revdialog"  title="修改" :visible.sync="dialogrevVisible" @close="closereviseDialog('reviseform')">
                            <el-form :model="reviseform" ref="reviseform"  class="addcategory">
                                <el-form-item   label="计量设备:" prop="meteringEquipment">
                                   <el-select v-model="reviseform.meteringEquipment">
                                    <el-option v-for="item in equipment" :key="item.id" :label="item.equipmentname" :value="item.id">
                                  </el-option>
                              </el-select>
                              <span class="tab">*</span>
                                </el-form-item>
                               <el-form-item  label="菜品名称:" prop="dishName">
                                  <el-select v-model="reviseform.dishName">
                                    <el-option v-for="item in food" :key="item.dishCode" :label="item.dishName" :value="item.dishCode">
                                  </el-option>
                              </el-select>
                              <span class="tab">*</span>
                                </el-form-item>
                                <el-form-item  label="所属餐厅:" prop="restaurant">
                                  <el-select v-model="reviseform.restaurant">
                                    <el-option v-for="item in restaurant" :key="item.RestaurantCode" :label="item.RestaurantName" :value="item.RestaurantCode">
                                  </el-option>
                              </el-select>
                              <span class="tab">*</span>
                                </el-form-item>
                                <el-form-item label="菜品数量:"  style="margin-bottom:15px;" prop="numbers">
                                      <el-input v-model="reviseform.numbers"></el-input> 份<span class="tab">*</span>
                                </el-form-item>
                                <el-form-item label="备注:" prop="remarks">
                                  <el-input style="width:180px;height:60px;"  type="textarea" v-model="reviseform.remarks"></el-input>
                                </el-form-item>
                                <span class="point">提示：XX1份xx克，参考价格x元，实际装入xx份，实际重量xxx克，实际参考价格xx元，实际价格x元。</span>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="revisefood(scope.row.id)">修改</el-button>
                              </div>
                        </el-dialog>
                    </template>
                </el-table-column>
        </el-table>
      <div style="text-align: right; margin-top: 10px;height:'15%'">
        <el-pagination
        style="height:40px"
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
      import:[],
      dialogaddVisible: false,
      dialogrevVisible: false,
      restaurantCode:'',//树状图id
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      queryform: {
        equipmentName: '',
        dishName: ''
      },
      addform: {
        meteringEquipment: '',
        dishName: '',
        restaurant: '',
        numbers: '',
        remarks:''
      },
      reviseform:{
        meteringEquipment: '',
        dishName: '',
        restaurant: '',
        numbers: '',
        remarks:''
      },
      equipment:[],
      food:[],
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
    this.treelist()
    this.selectlist()
    this.pagelist()
    this.importarray()
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
    // 树状图接口
    treelist(){
      this.$axios({
        url:'StOperationrestaurant.do/getStOperationList',
        method:'get'
      }).then(res=>{
        console.log(res)
        this.data=res.data
      })
    },
    // 下拉框数据
    selectlist(){
      this.$axios({
        url:'StMeasurechoicedishes.do/getRelevance',
        method:'get'
      }).then(res=>{
        console.log(res)
        this.equipment=res.data.equipment
        this.food=res.data.food
        this.restaurant=res.data.restaurant
      })
    },
    // 排菜列表
    pagelist(){
      this.$axios({
        url:'StMeasurechoicedishes.do/choiceDishList',
        method:'post',
        data:{
        "pageNum": this.pageIndex,
        "pageSize":this.pageSize,
        "param": {
                "restaurantName": this.restaurantCode,
                "equipmentName": this.queryform.equipmentName,
                "dishName":this.queryform.dishName
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
    this.queryform.equipmentName=''
    this.queryform.dishName=''
  },
  // 删除排菜
  deletedishes(id){
      this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StMeasurechoicedishes.do/deleteChoiceDish',
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
    // 新增菜品
    addfoodbtn(queryform){
      if(this.addform.meteringEquipment==''||this.addform.dishName==''||this.addform.restaurant==''||this.addform.numbers==''){
          this.dialogaddVisible = false
          this.$refs[queryform].resetFields()
          this.$message('带*号项不能为空');
          return
      }
        this.$axios({
          url:'StMeasurechoicedishes.do/insertChoiceDish',
          method:'post',
          data:{
            "meteringEquipment":this.addform.meteringEquipment,
            "dishName": this.addform.dishName,
            "restaurant": this.addform.restaurant,
            "numbers":this.addform.numbers,
            "remarks": this.addform.remarks
        }
        }).then(res=>{
          console.log(res)
          this.pagelist()
          this.$message.success('新增成功')
        })
        this.$refs[queryform].resetFields()
        this.dialogaddVisible = false
    },
    // 修改排菜
    revisefood(id){
      if(this.reviseform.meteringEquipment==''||this.reviseform.dishName==''||this.reviseform.restaurant==''||this.reviseform.numbers==''){
      this.dialogrevVisible = false
      this.closereviseDialog('reviseform')
      this.$message('带*号项不能为空');
      return
      }
      this.$axios({
        url:'StMeasurechoicedishes.do/updateChoiceDish',
        method:'post',
        data:{
                "id": id,
                "meteringEquipment": this.reviseform.meteringEquipment,
                "dishName":this.reviseform.dishName,
                "restaurant":this.reviseform.restaurant,
                "numbers":this.reviseform.numbers,
                "remarks":this.reviseform.remarks
            }
      }).then(res=>{
        this.pagelist()
        this.$message.success('修改成功')
      })
      this.closereviseDialog('reviseform')
      this.dialogrevVisible = false
    },
    // 导出
    exportout(){
      this.$axios({
        url:'StMeasurechoicedishes.do/choiceDishExport',
        method:'post',
        data:{
          "param":{
          "restaurantName":this.restaurantCode,
          "equipmentName":this.queryform.equipmentName,
          "dishName":this.queryform.dishName
          }
        },

        responseType:'blob'
      }).then(res=>{
           let data = res;
            let blob = new Blob([data.data], { type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"});
            let url = window.URL.createObjectURL(blob);
            let link = document.createElement("a");
            link.style.display = "none";
            link.href = url;
            link.setAttribute("download", "导出排菜列表.xls");
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
      })
    },
    // 导入过的数据
    importarray(){
      this.$axios({
        url:'StMeasurechoicedishes.do/getfileName',
        method:'post'
      }).then(res=>{
        this.import=res.data
        console.log(this.import)
      })
    },
    // 导入
    importExcel(){
      console.log(this.$refs.articleImageFile.files[0])
            for(var i=0;i<this.import.length;i++){
                  if(this.$refs.articleImageFile.files[0].name==this.import[i]){
                          this.$message.error('该文件名已经导入过')
                          this.$refs.articleImageFile.value=''
                          return
                  }
            }
             var formData = new FormData();
            formData.append("file",this.$refs.articleImageFile.files[0]);
                  this.$axios({
                          url:'StMeasurechoicedishes.do/choiceDishImport',
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
    overflow: auto;
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
.el-input{
    width: 180px;

}
/deep/.el-form-item__label{
  font-size: 12px;
  line-height: 28px;
  padding-right:12px;
  box-sizing: border-box;
  color: rgb(255,255,255);
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
.binicon img,.editicon img:hover{
  cursor: pointer;
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
    width: 500px;
    height: 80px;
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
    line-height:30px;
    font-size: 12px;

    position: absolute;
    top:100px;
    right:40px;
}
.revdialog .point{
    width: 240px;
    height:100px;
    line-height:30px;
    font-size: 12px;
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
/deep/.el-upload-list{
  display:none;
} 
/deep/.el-form-item__content{
    font-size: 10px;
    color:white;
    margin:0px 5px 20px 0;
}
.selipt{
  width: 200px;
}
/deep/.el-input__icon{
  width: 25px;
  line-height: 40px;
}
</style>
