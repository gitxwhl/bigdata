<template>
    <div class='container'>
        <div class='managetitle'>菜品管理</div>
        <el-card class='managecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
                <el-row class="iptrow" :gutter="0">
                    <el-col :span='8' class='iptcol'>
                        <el-form-item label="名称/编码">
                           <el-input class="name" v-model="queryform.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='4' class='iptcol'>
                        <el-form-item label="类别">
                        <el-select v-model="queryform.category">
                            <el-option v-for="item in foodType" :key="item.id" :label="item.varietiesName" :value="item.id">
                            </el-option>
                        </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span='4' class='iptcol'>
                        <el-form-item label="状态">
                        <el-select v-model="queryform.status">
                            <el-option v-for="item in foodState" :key="item.id" :label="item.text" :value="item.id">
                            </el-option>
                        </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span='2' class='iptcol'>
                        <el-form-item>
                            <button class='search' @click.prevent @click="querylist">查询</button>
                        </el-form-item>
                    </el-col>
                    <el-col :span='2' class='iptcol'>
                        <el-form-item>
                            <button class='add' @click="oppoaddfood" @click.prevent>添加</button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
             <!-- 菜品添加弹出框 -->
                            <el-dialog  title="添加菜品" :visible.sync="dialogFoodVisible" @close="closeadd" class='foodadddialog'>
                                <div class='topbox'>
                                  <div class='topleft'>
                                    <el-form :model="addfoodform" ref="addfoodform" size="mini" class='addfoodform' >
                                            <el-row>
                                              <el-col :span='12'>
                                                <el-form-item label="菜品名称:">
                                                  <el-input class="inputname" v-model="addfoodform.dishname"></el-input>
                                                </el-form-item>
                                              </el-col>
                                              <el-col :span='12'>
                                                <el-form-item label="菜品分类:">
                                                  <el-select class="inputname" v-model="addfoodform.dishesCategory">
                                                    <el-option v-for="item in foodClassify" :key="item.id" :label="item.text" :value="item.id">
                                                    </el-option>
                                                </el-select>
                                                </el-form-item>
                                              </el-col>
                                            </el-row>
                                      </el-form>
                                            <div>
                                              <div class='inblock'>编码: <el-input class="inputname" v-model="addfoodform.dishcode"></el-input></div>
                                              <div class='inblock'>类别: 
                                                <el-select class="inputname" multiple v-model="addfoodform.dishcategory">
                                                    <el-option v-for="item in foodType" :key="item.id" :label="item.varietiesName" :value="item.id">
                                                    </el-option>
                                                </el-select>
                                              </div>
                                            </div>
                                            <div>
                                              <div class='inblock'>菜品口味: 
                                                <el-select class="inputname" v-model="addfoodform.taste">
                                                    <el-option v-for="item in taste" :key="item.id" :label="item.text" :value="item.id">
                                                    </el-option>
                                                </el-select>
                                              </div>
                                              <div class='inblock'>辣度: <el-input class="inputname" v-model="addfoodform.degree"></el-input></div>
                                              <div class='inblock'>参考价格: <el-input class="inputname" v-model="addfoodform.referenceprice"></el-input> 元</div>
                                              <div class='inblock'>单位: 
                                                <el-select class="inputname" v-model="addfoodform.company">
                                                    <el-option v-for="item in foodCompany" :key="item.id" :label="item.text" :value="item.id">
                                                    </el-option>
                                                </el-select>
                                              </div>
                                            </div>
                                            <div>
                                              <!-- <div class='block'>特殊人群慎选:</div> -->
                                              <!-- <div class='block'>每100克食材配料: <el-input class="inputname" v-model="addfoodform.name"></el-input></div> -->
                                              <div>每100克膳食结构分配:</div>
                                            </div>
                                  </div>
                                  <div class='topright'>
                                    图片上传。。
                                    <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">
                                    <img :src='img' alt="" @click="leadingin">
                                  </div>
                                </div>
                                <div class='amount'>
                                    <div class='inblockjg'>谷类: 15g</div>
                                    <div class='inblockjg'>蔬菜和水果: 20g</div>
                                    <div class='inblockjg'>鱼、禽、肉、蛋: 20g</div>
                                    <div class='inblockjg'>烹调油（脂肪）、食盐: 20g</div>
                                    <div class='inblockjg'>奶类、豆类: 25g</div>
                                </div>
                                <div class='fontsize'>每100克所含营养成分:</div>
                                <div class='nutbox'>
                                  <div class='nengliang'>
                                    <div  class='nlbox'>能量: 300千焦(kj)</div>
                                    <div  class='nlbox'>蛋白质: 10克(g)</div>
                                    <div  class='nlbox'>脂肪: 11克(g)</div>
                                    <div  class='nlbox'>饱和脂肪酸: 13克(g)</div>
                                    <div  class='nlbox'>胆固醇: 20毫克(mg)</div>
                                    <div  class='nlbox'>碳水化合物: 5克(g)</div>
                                    <div  class='nlbox'>膳食纤维: 7克(g)</div>
                                  </div>
                                  <div class='weiss'>
                                      <div class='absolute'>维生素</div>
                                      <div  class='wssbox'>维生素A: 10毫克(mg)</div>
                                      <div  class='wssbox'>维生素B12: 22毫克(mg)</div>
                                      <div  class='wssbox'>维生素D: 13毫克(mg)</div>
                                      <div  class='wssbox'>维生素C: 15毫克(mg)</div>
                                      <div  class='wssbox'>维生素E: 23毫克(mg)</div>
                                      <div  class='wssbox'>烟酸: 21毫克(mg)</div>
                                      <div  class='wssbox'>维生素K: 11毫克(mg)</div>
                                      <div  class='wssbox'>叶酸: 15毫克(mg)</div>
                                      <div  class='wssbox'>维生素B1(硫胺素): 16毫克(mg)</div>
                                      <div  class='wssbox'>泛酸: 11毫克(mg)</div>
                                      <div  class='wssbox'>维生素B2(核黄素): 23毫克(mg)</div>
                                      <div  class='wssbox'>生物素: 31毫克(mg)</div>
                                      <div  class='wssbox'>维生素B6: 15毫克(mg)</div>
                                      <div  class='wssbox'>胆碱: 12毫克(mg)</div>
                                  </div>
                                  <div class='weiss'>
                                      <div class='absolute'>矿物质</div>
                                      <div  class='wssbox'>钙: 23毫克(mg)</div>
                                      <div  class='wssbox'>碘: 25毫克(mg)</div>
                                      <div  class='wssbox'>磷: 32毫克(mg)</div>
                                      <div  class='wssbox'>硒: 41毫克(mg)</div>
                                      <div  class='wssbox'>钾: 33毫克(mg)</div>
                                      <div  class='wssbox'>铜: 13毫克(mg)</div>
                                      <div  class='wssbox'>钠: 21毫克(mg)</div>
                                      <div  class='wssbox'>氟: 11毫克(mg)</div>
                                      <div  class='wssbox'>镁: 12毫克(mg)</div>
                                      <div  class='wssbox'>铬: 21毫克(mg)</div>
                                      <div  class='wssbox'>铁: 22毫克(mg)</div>
                                      <div  class='wssbox'>锰: 25毫克(mg)</div>
                                      <div  class='wssbox'>锌: 33毫克(mg)</div>
                                      <div  class='wssbox'>钼: 32毫克(mg)</div>
                                  </div>
                                </div>
                                <div class='fontsize'>
                                  菜品描述:
                                  <el-input type="textarea" class='textarea' v-model="addfoodform.remarks"></el-input>
                                </div>
                                 <div slot="footer" class="dialog-footer">
                                <el-button class='btnaddchild'  type="primary" @click="addfoodbtn">添加</el-button>
                              </div>
                            </el-dialog>
                <div class='foodtable'>
                    <button class='export' @click.prevent @click="exportout">导出</button>
                    <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="300px" size="small"  style="width: 100%;margin-bottom:10px;" @selection-change="handleSelectionChange">
                         <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                        <el-table-column align="center" min-width="23%" prop="dishname" label="名称" ></el-table-column>
                        <el-table-column align="center" min-width="10%" prop="dishcode" label="编码"></el-table-column>
                        <el-table-column align="center" min-width="22%" prop="dishcategory" label="类别" ></el-table-column>
                        <el-table-column align="center" min-width="8%" prop="referenceprice" label="参考价格(元)"></el-table-column>
                        <el-table-column align="center" min-width="8%" prop="company" label="单位"></el-table-column>
                        <el-table-column align="center" min-width="8%" prop="state" label="状态"></el-table-column>
                        <el-table-column align="center" min-width="10%" prop="remarks" label="备注"></el-table-column>
                        <el-table-column align="center" min-width="25%" prop="oeratio" label="操作">
                        <template slot-scope="scope">
                            <span class='binspan'>
                                <img src="../../assets/u349.svg" alt=""  @click="deletedishes(scope.row.dishcode)">
                                删除
                            </span>
                            <span class='statusspan'>
                              <el-switch v-model="scope.row.delFlag" @change='oppochangestatus(scope.row.id,scope.row.delFlag)' 
                            active-value="0" inactive-value="1" active-color="#13ce66" inactive-color="#ff4949"></el-switch>变更状
                                
                            </span>
                           
                            <span class='nutspan'>
                                <img src="../../assets/u677.svg" alt="" @click="nutanalyse(scope.row.dishcode)" >
                                营养成分分析
                            </span>

                            
                            
                        </template>
                        </el-table-column>
                    </el-table>
                    <!-- 营养成分分析弹出框 -->
                            <el-dialog  title="营养成分表" :visible.sync="dialogNutVisible">
                               <table border="1" class="nuttable">
                                    <tr class='tablehead'>
                                        <td>能量</td>
                                        <td>每100克(g)</td>
                                        <td>营养素参考值%</td>
                                    </tr>
                                    <tr v-for="item in nutanalysed" :key="item.name">
                                        <td>{{item.name}}</td>
                                        <td>{{item.energy}}</td>
                                        <td>{{item.energyPro}}%</td>
                                    </tr>
                                </table>
                            </el-dialog>
                    <el-pagination
                        style="height:100px;float:right;"
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
      value:[],
      files:'',
      img:'',
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      dishcode:null,
      nutanalysed:[],//营养成分
      dialogNutVisible: false,
      dialogFoodVisible: false,
      dialogChangeVisible: false,
      queryform: {
        name: '',
        category: '',
        status:''
      },
      changeform: {
        status:null
      },
      addfoodform:{
        dishname:'',
        dishesCategory:'',
        dishcode:'',
        dishcategory:[],
        taste:'',
        degree:'',
        referenceprice:'',
        company:'',
        remarks:'',
      },
      foodCompany:[],
      foodState:[],
      foodType:[],
      foodClassify:[],
      taste:[],
      tableData: []
    }
  },
  computed: {

  },
  created () {
    // this.submitForm()
    this.selectlist()
    this.pagelist()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize = val
      this.pageList()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
      this.pagelist()
      console.log(`当前页: ${val}`)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 获取下拉框数据
    selectlist(){
      this.$axios({
        url:'/StfoodManagement.do/dictionaries',
        method:'get',
      }).then(res=>{
        this.foodCompany=res.data.foodCompany
        this.foodState=res.data.foodState
        this.foodType=res.data.foodType
        this.foodClassify=res.data.foodClassify
        this.taste=res.data.taste
      })
    },
    // 获取列表
    pagelist(){
      this.$axios({
        url:'/StfoodManagement.do/selectByProperty',
        method:'post',
        data:{
          "pageNum":this.pageIndex,
          "pageSize":this.pageSize,
          "param":{
            "dishcode":this.queryform.name,
            "dishcategory":this.queryform.category,
            "state":this.queryform.status
            }
        }
      }).then(res=>{
        this.total=res.data.totalRecord
        this.tableData=res.data.list
      })
    },
    // 查询列表
    querylist(){
      this.pagelist()
      this.queryform.name=''
      this.queryform.category=''
      this.queryform.status=''
    },
    // 删除菜品
    deletedishes(id){
      this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'/StfoodManagement.do/deleteFood',
              method:'post',
              data:{
                dishcode:id
              }
            }).then(res=>{
        this.$message.success('删除成功')
        this.pagelist()
        })
      }).catch(()=>{

      })
      
    },
    // 变更状
    oppochangestatus(id,state){
      
      if(state=="1"){
        state='0'
      }else if(state=='0'){
        state='1'
      }
      this.$axios({
        url:'StfoodManagement.do/updateState',
        method:'post',
        data:{
          "id":id,
          "state":state,
        }
      }).then(res=>{
        if(res.data=='状态变更成功'){
            this.$message.success('状态变更成功')
            this.pagelist()
        }else{
          this.$message.error('状态变更失败')
        }
      })
    },
   
    // 添加菜品生成编号
    oppoaddfood(){
      this.dialogFoodVisible = true
      this.$axios({
        url:'/StfoodManagement.do/codeGenerate',
        method:'get'
      }).then(res=>{
        this.addfoodform.dishcode=res.data
      })
    },
    // 添加菜品
    addfoodbtn(queryform){
      if(this.addfoodform.dishname.trim()==''||this.addfoodform.dishesCategory.trim()==''||this.addfoodform.dishcode==''||
      this.addfoodform.taste.trim()==''||this.addfoodform.degree.trim()==''||this.addfoodform.referenceprice.trim()==''||this.addfoodform.company.trim()==''||this.addfoodform.remarks.trim()==''){
      this.$message('带*号项不能为空');
      return
      }
     
      if(this.files==''){
          this.$axios({
        url:'/StfoodManagement.do/insertFood',
        method:'post',
        data:{
                  "param": {
                      "dishname": this.addfoodform.dishname,		
                      "dishesCategory":this.addfoodform.dishesCategory,		
                      "dishcode":this.addfoodform.dishcode,		
                      "dishcategory":this.addfoodform.dishcategory,
                      "taste":this.addfoodform.taste,			
                      "degree":this.addfoodform.degree,			
                      "referenceprice":this.addfoodform.referenceprice,		
                      "company":this.addfoodform.company,		
                      "files": this.files,			
                      "remarks":this.addfoodform.remarks,
                      "feature":"",		
                      "weight":"",
                  },
                  "param1": {
                      "energy": "",			
                      "protein": "",			
                      "fat": "",			
                      "saturated": "",			
                      "cholesterol": "",		
                      "carbohydrate": "",		
                      "dietaryfiber": "",		
                      "vitaminA": "",			
                      "vitaminD": "",			
                      "vitaminE": "",		
                      "vitaminK": "",		
                      "vitaminB1": "",		
                      "vitaminB2": "",		
                      "vitaminB6": "",		
                      "vitamin16": "",		
                      "vitaminC": "",			
                      "nicotinicacid": "",		
                      "folicacid": "",			
                      "pantothenicacid": "",		
                      "biotin": "",			
                      "choline": "",		
                      "calcium": "",			
                      "phosphorus": "",		
                      "potassium": "",		
                      "sodium": "",		
                      "magnesium": "",		
                      "iron": "",			
                      "zinc": "",			
                      "iodine": "",			
                      "selenium": "",				
                      "copper": "",			
                      "fluorine": "",			
                      "chromium": "",		
                      "manganese": "",		
                      "molybdenum": ""		
                  },
                  "param2": {
                      "cereal": "",			
                      "vegetable": "",		
                      "fishmeat": "",		
                      "milk": "",			
                      "oilsalt": ""			
                  }
              }
          }).then(res=>{
            if(res.data=='添加成功'){
              this.$message.success('添加类别成功')
              this.pagelist()
            }else{
              this.$message.error('添加类别失败')
            }
          this.dialogFoodVisible= false
          this.addfoodform={
                dishname:'',
                dishesCategory:'',
                dishcode:'',
                dishcategory:[],
                taste:'',
                degree:'',
                referenceprice:'',
                company:'',
                remarks:'',
              }
          })
      }else{
         var reader = new FileReader();
            reader.readAsDataURL(this.files);
            reader.onload=(e)=>{
              this.$axios({
        url:'/StfoodManagement.do/insertFood',
        method:'post',
        data:{
                  "param": {
                      "dishname": this.addfoodform.dishname,		
                      "dishesCategory":this.addfoodform.dishesCategory,		
                      "dishcode":this.addfoodform.dishcode,		
                      "dishcategory":this.addfoodform.dishcategory,
                      "taste":this.addfoodform.taste,			
                      "degree":this.addfoodform.degree,			
                      "referenceprice":this.addfoodform.referenceprice,		
                      "company":this.addfoodform.company,		
                      "files": e.target.result,			
                      "remarks":this.addfoodform.remarks,
                      "feature":"",		
                      "weight":"",
                  },
                  "param1": {
                      "energy": "",			
                      "protein": "",			
                      "fat": "",			
                      "saturated": "",			
                      "cholesterol": "",		
                      "carbohydrate": "",		
                      "dietaryfiber": "",		
                      "vitaminA": "",			
                      "vitaminD": "",			
                      "vitaminE": "",		
                      "vitaminK": "",		
                      "vitaminB1": "",		
                      "vitaminB2": "",		
                      "vitaminB6": "",		
                      "vitamin16": "",		
                      "vitaminC": "",			
                      "nicotinicacid": "",		
                      "folicacid": "",			
                      "pantothenicacid": "",		
                      "biotin": "",			
                      "choline": "",		
                      "calcium": "",			
                      "phosphorus": "",		
                      "potassium": "",		
                      "sodium": "",		
                      "magnesium": "",		
                      "iron": "",			
                      "zinc": "",			
                      "iodine": "",			
                      "selenium": "",				
                      "copper": "",			
                      "fluorine": "",			
                      "chromium": "",		
                      "manganese": "",		
                      "molybdenum": ""		
                  },
                  "param2": {
                      "cereal": "",			
                      "vegetable": "",		
                      "fishmeat": "",		
                      "milk": "",			
                      "oilsalt": ""			
                  }
              }
          }).then(res=>{
            if(res.data=='添加成功'){
              this.$message.success('添加类别成功')
              this.pagelist()
            }else{
              this.$message.error('添加类别失败')
            }
          this.dialogFoodVisible= false
          this.addfoodform={
                dishname:'',
                dishesCategory:'',
                dishcode:'',
                dishcategory:[],
                taste:'',
                degree:'',
                referenceprice:'',
                company:'',
                remarks:'',
              }
          })
            }
      }
      
    },
    closeadd(){
      this.addfoodform={
                dishname:'',
                dishesCategory:'',
                dishcode:'',
                dishcategory:[],
                taste:'',
                degree:'',
                referenceprice:'',
                company:'',
                remarks:'',
              }
    },
    // 营养成分分析
    nutanalyse(id){
      this.dialogNutVisible = true
      this.$axios({
        url:'StfoodManagement.do/foodAnalyze',
        method:'post',
        data:{
          "dishcode":id
        }
      }).then(res=>{
        console.log(res)
        this.nutanalysed=res.data
      })
    },
    // 导出
    exportout(){
      this.$axios({
        url:'StfoodManagement.do/foodExport',
        method:'post',
        data:{
          "param":
          {"dishcode":this.queryform.name,
          "dishcategory":this.queryform.category,
          "state":this.queryform.status}
          },
        responseType:'blob'
      }).then(res=>{
           let data = res;
            let blob = new Blob([data.data], { type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"});
            let url = window.URL.createObjectURL(blob);
            let link = document.createElement("a");
            link.style.display = "none";
            link.href = url;
            link.setAttribute("download", "菜品导出.xls");
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
      })
    },
    //关闭事件
    closeaddDialog(queryform){
      this.$refs[queryform].resetFields()
    },
    //关闭事件
    closeDialog(queryform){
      this.changeform.status=null
    },
    importExcel(e){
            this.img=URL.createObjectURL(this.$refs.articleImageFile.files[0])
            this.files=this.$refs.articleImageFile.files[0]
            // console.log(this.files,this.$refs.articleImageFile.files[0],e)
            
    },
    leadingin(){
        this.$refs.articleImageFile.click()
      },
  }
}
</script>

<style lang="less" scoped>
.container{
   padding:10px 10px 5px 10px;
   box-sizing: border-box;
   color: rgb(255,255,255);
   font-size: 10px;
   height: 100%;
}
.managetitle{
    box-sizing: border-box;
    padding-top: 5px;
    font-size: 14px;
    margin-bottom:10px ;
    padding-bottom:5px ;
    border-bottom:1px solid rgb(37,81,108);
}
.managecard{
    background-color:rgba(1,5,22,.4);
    box-sizing: border-box;
    margin-right: 10px;
    border-radius: 0;
    border:none;
    width: 100%;
    height: 860px;
}
.iptrow{
    width:100%;
    height:60px;
    margin-top:40px;
    padding-left:120px;
    box-sizing: border-box;
}
.iptcol{
    height:30px
}
.queryform{
    color: rgb(255,255,255);
    font-size: 12px;
    width:100%;
    padding-left:64px;
    margin-top:16px;
}
 /deep/.el-input__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    vertical-align: middle;
    background-color:rgb(0,2,9);
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:0px;
    height:32px;
    padding:0 5px;
}
.search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    padding: 0;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.add{
   color:rgb(255,255,255) ;
    font-size: 10px;
    padding: 0;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search,.add:hover{
    cursor: pointer;
}
/deep/.el-form-item__label{
    font-size: 10px;
    line-height: 32px;
    padding-right:12px!important ;
    color:rgb(255,255,255) ;
}
/deep/.el-card__body{
  height:100%;
  overflow-y: auto;
  overflow-x: hidden;
}
.foodtable{
    height:700px;
    margin:0 20px ;
    padding:40px 20px 0 42px;
    margin-top:15px;
    background:url(../../assets/zy2.png) no-repeat;
    background-size:100% 100%;
}
.export{
    width:60px;
    height:32px;
    padding: 0;
    border: 1px solid rgb(52,57,77);
    border-radius: 5px;
    background-color:rgb(1,37,85);
    color:rgb(255,255,255);
    font-size:10px;
    margin-bottom:4px;
}

/deep/.el-table{
    background-color:transparent;
}
 /deep/.el-table__body-wrapper{
    height: 560px!important;
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
    // background-color:rgb(65, 69, 119);
    background:transparent;
}
/deep/.el-table tbody tr:nth-child(odd):hover>td {
    background-color:transparent!important;
}
img{
    width: 12px;
    vertical-align: middle;
}
.binspan{
    float: left;
  margin-right:80px;
}
.binspan img,.statusspan img,.nutspan img{
  width: 20px;
}
.binspan img:hover,.statusspan img:hover,.nutspan img:hover,.export:hover{
    cursor: pointer;
}
.nutspan{
    float: right;
  margin-right:10px ;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  // background: transparent;
  border:1px solid rgba(255,255,255,.5);
  margin-top:300px;
}
/deep/.el-dialog__header{
  padding:5px 10px;
  text-align: center;
   background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__body{
    padding:20px 40px 20px 40px;
    color:rgb(255,255,255);
    background:rgba(0,0,0,.4);
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);

   background:rgba(0,0,0,.4);
}
/deep/.el-dialog__title{
  font-size: 12px;
  color:rgb(255,255,255);

}
.nuttable{
    width:100%;
    table-layout:fixed;
    background:transparent;
    border-collapse: collapse;
}
.nuttable td{
    height:30px;
    padding:0 0 0 5px;
    font-size: 10px;
    background:transparent;
    border:1px solid rgb(128,128,128);
}
.tablehead{
    background:transparent;

}
.nuttable tr td{
    background:transparent;
}
.tablehead td{
    width: 100px;
    height:30px;
    text-align: center;
    font-size: 12px;
}
tr:hover > td{
    background-color:transparent!important;
}
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
}
/deep/.el-form-item__content{
    font-size: 10px;
    color:white;
    margin-right: 5px;
}
.el-form-item{
    margin:0px;
}
.nutcard{
    background:transparent;
    padding-top:15px;
    height:350px;
    overflow: auto;
    border:1px solid rgb(52,57,82);
}

.btnaddchild,.btnedit{
  background-color:rgb(32,47,66);
  margin-top: 5px;
  width: 80px;
  height:30px;
  line-height:0;
  padding:10px 0 10px 5px;
  font-size: 12px;
}
/deep/.btnaddchild span{
  line-height:0;
  margin-right:5px ;
}
/deep/.el-dialog__headerbtn{
    margin-top:-12px;
}
/deep/.el-textarea__inner{
    background-color: rgb(0,2,9);
    color:white;
    height:80px;
    border:1px solid rgb(52,57,82);
}
/deep/.el-textarea{
  width: 1000px;
  vertical-align: top;
}
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
}
/deep/.el-pagination{
  background-color: transparent;
}
.tab{
    font-size: 18px;
    color:red;
    padding-left:16px;
}
.vitamin{
    float:left;
    width:446px;
    height:340px;
    border:1px dashed rgb(82,85,97);
    box-sizing:border-box;
    padding:20px 0 0 0px;
    position: relative;
    margin-right:20px;
    box-sizing: border-box;

}
.mineral{
    box-sizing: border-box;
    float:left;
    width:446px;
    height:340px;
    border:1px dashed rgb(82,85,97);
    box-sizing:border-box;
    padding:20px 0 0 30px;
    position: relative;
}
.vitamintitle,.mineraltitle{
    color: white;
    position: absolute;
    top:-15px;
    left:210px;
    font-size: 14px;
}
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
.el-table{
    height:600px!important;
}
.name{
  width: 400px;
}
/deep/.el-input__icon{
    line-height: 28px!important;
    width: 25px!important;
}
/deep/.el-input__suffix{
  right:5px;
}
.anaydialog /deep/.el-dialog{
  width: 340px;
}
.foodadddialog /deep/.el-dialog{
  width: 1200px;
}
.topbox{
  width: 1100px;
  font-size: 12px;
}
.topleft{
  width: 700px;
  display: inline-block;
}
.topright{
  margin-top: 20px;
  width: 400px;
  vertical-align: top;
  display: inline-block;
}
.topright img{
  width: 197px;
  height: 125px;
  vertical-align: top;
  background: url(../../assets/u9601.svg)no-repeat;
  background-size: 100% 100%;
}
.inputname{
  width: 200px;
}
.inblock{
  width: 290px;
  display: inline-block;
  margin:10px 60px 10px 0;
  font-size: 12px;
}
.inblockjg{
  display: inline-block;
  margin:0 100px 0 0 ;
}
.block{
  margin:10px 60px 30px 0;
}
.fontsize{
  font-size: 12px;
}
.amount{
    height: 50px;
    background: url(../../assets/1_u3579.png) no-repeat;
    background-size: 100% 100%;
    line-height: 50px;
    padding-left:30px;
    box-sizing: border-box;
    margin:5px 0 10px 0;
    font-size: 12px;
}
.nutbox{
  width: 100%;
  font-size: 12px;
  padding:10px;
  margin-bottom: 10px;
  box-sizing: border-box;
  background: url(../../assets/1_u3579.png) no-repeat;
  background-size: 100% 100%;
}
.nengliang{
  display: inline-block;
  width: 200px;
  margin:10px 0  20px 30px;
}
.nlbox{
  margin: 0 0 25px 0;
}
.weiss{
  display: inline-block;
  width: 400px;
  margin:10px 30px  20px 0;
  border: 1px dashed #527994;
  vertical-align: top;
  position: relative;

}
.wssbox{
  display: inline-block;
  width: 180px;
  margin:15px 0 10px 20px;
  box-sizing: border-box;
}
.absolute{
  position: absolute;
  top:-10px;
  left: 190px;
  
}
</style>