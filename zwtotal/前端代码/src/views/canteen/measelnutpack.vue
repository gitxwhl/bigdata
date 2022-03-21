<template>
    <div class='container'>
        <div class='nuttitle'>精准营养选餐</div>
        <el-card class='nutcard'>
            <el-form :model="addqueryform" ref="addqueryform" label-width="90px" size="mini" class='queryform' >
                <div style="float:left;margin-right:10px;flex:1;">
                    <el-form-item label-position="left" label-width="65px" label="蛋白质:" prop="protein">
                           <el-input style="width:100px;" v-model="addqueryform.protein"></el-input> 克(g)
                    </el-form-item>
                    <el-form-item label-position="left" label-width="50px" label="脂肪:" prop="fat">
                           <el-input style="width:116px;" v-model="addqueryform.fat"></el-input> 克(g)
                    </el-form-item>
                    <el-form-item label-position="left" label-width="90px" label="饱和脂肪酸:" prop="fattyacids">
                           <el-input style="width:76px;" v-model="addqueryform.fattyacids"></el-input> 克(g)
                    </el-form-item>
                    <el-form-item label-position="left" label-width="63px" label="胆固醇:" prop="cholesterol">
                           <el-input style="width:76px;" v-model="addqueryform.cholesterol"></el-input> 毫克(mg)
                    </el-form-item>
                    <el-form-item label-position="left" label-width="90px" label="碳水化合物:" prop="carbohydrate">
                           <el-input style="width:76px;" v-model="addqueryform.carbohydrate"></el-input> 克(g)
                    </el-form-item>
                    <el-form-item label-position="left" label-width="75px" label="膳食纤维:" prop="dietaryfiber">
                           <el-input style="width:90px;" v-model="addqueryform.dietaryfiber"></el-input> 克(g)
                    </el-form-item>
                </div>
                <div class='vitamin'>
                    <span class='vitamintitle'>维生素</span>
                    <el-row>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="70px" label="维生素A:" prop="vitamina">
                                <el-input style="width:60px;" v-model="addqueryform.vitamina"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="68px" label="维生素B2:" prop="vitaminb2">
                                <el-input style="width:70px;" v-model="addqueryform.vitaminb2"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="54px" label="叶酸:" prop="folicacid">
                                <el-input style="width:72px;" v-model="addqueryform.folicacid"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="70px" label="维生素D:" prop="vitamind">
                                <el-input style="width:60px;" v-model="addqueryform.vitamind"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="70px" label="维生素B6:" prop="vitaminb6">
                                <el-input style="width:64px;" v-model="addqueryform.vitaminb6"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="54px" label="泛酸:" prop="pantothenicacid">
                                <el-input style="width:72px;" v-model="addqueryform.pantothenicacid"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="68px" label="维生素E:" prop="vitamine">
                                <el-input style="width:60px;" v-model="addqueryform.vitamine"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="76px" label="维生素B12:" prop="vitamin12">
                                <el-input style="width:58px;" v-model="addqueryform.vitamin12"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="65px" label="生物素:" prop="biotin">
                                <el-input style="width:60px;" v-model="addqueryform.biotin"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="67px" label="维生素K:" prop="vitamink">
                                <el-input style="width:60px;" v-model="addqueryform.vitamink"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="62px" label="维生素C:" prop="vitaminc">
                                <el-input style="width:72px;" v-model="addqueryform.vitaminc"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="54px" label="胆碱:" prop="choline">
                                <el-input style="width:72px;" v-model="addqueryform.choline"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="72px" label="维生素B1:" prop="vitaminb1">
                                <el-input style="width:56px;" v-model="addqueryform.vitaminb1"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                         <el-col :span="8">
                         <el-form-item label-position="left" label-width="42px" label="烟酸:" prop="nicotinicacid">
                                <el-input style="width:92px;" v-model="addqueryform.nicotinicacid"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                    </el-row>
                </div>
                <div class='mineral'>
                    <span class='mineraltitle'>矿物质</span>
                    <el-row>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="钙:" prop="calcium">
                                <el-input style="width:90px;" v-model="addqueryform.calcium"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="铁:" prop="iron">
                                <el-input style="width:90px;" v-model="addqueryform.iron"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="氟" prop="fluorine">
                                <el-input style="width:90px;" v-model="addqueryform.fluorine"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="磷:" prop="phosphorus">
                                <el-input style="width:90px;" v-model="addqueryform.phosphorus"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="锌:" prop="zinc">
                                <el-input style="width:90px;" v-model="addqueryform.zinc"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="铬:" prop="chromium">
                                <el-input style="width:90px;" v-model="addqueryform.chromium"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="钾:" prop="potassium">
                                <el-input style="width:90px;" v-model="addqueryform.potassium"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="碘:" prop="iodine">
                                <el-input style="width:90px;" v-model="addqueryform.iodine"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="锰:" prop="manganese">
                                <el-input style="width:90px;" v-model="addqueryform.manganese"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="钠:" prop="sodium">
                                <el-input style="width:90px;" v-model="addqueryform.sodium"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="硒:" prop="selenium">
                                <el-input style="width:90px;" v-model="addqueryform.selenium"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="钼:" prop="molybdenum">
                                <el-input style="width:90px;" v-model="addqueryform.molybdenum"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                        <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="镁:" prop="magnesium">
                                <el-input style="width:90px;" v-model="addqueryform.magnesium"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                         <el-col :span="8">
                         <el-form-item label-position="left" label-width="20px" label="铜:" prop="copper">
                                <el-input style="width:90px;" v-model="addqueryform.copper"></el-input> 毫克(mg)
                          </el-form-item>
                        </el-col>
                    </el-row>
                </div>
                <div style="flex:1;float:left;height:280px;position: relative;">
                     <el-form-item label="运维餐厅:" label-position="left" label-width="64px" prop="restaurant">
                         <el-select style="width:140px;" v-model="addqueryform.restaurant">
                             <el-option v-for="item in restaurant" :key="item.RestaurantCode" :label="item.RestaurantName" :value="item.RestaurantCode">
                             </el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="餐盘种类:" label-position="left" label-width="64px" prop="platetype">
                         <el-select style="width:140px;" v-model="addqueryform.platetype">
                             <el-option v-for="item in equipment" :key="item.id" :label="item.equipmentname" :value="item.id">
                             </el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label-position="left" label-width="64px" label="重量:" prop="weight">
                         <el-input style="width:90px;" v-model="addqueryform.weight"></el-input> 克(g)
                     </el-form-item>
                     <el-form-item style="margin-bottom:40px;" label-position="left" label-width="64px" label="能量:" prop="energy">
                         <el-input style="width:90px;" v-model="addqueryform.energy"></el-input> 克(g)
                    </el-form-item>
                    <el-form-item>
                                 <button class='search' @click.prevent @click="querylist('addqueryform')">查询</button>
                                 <button class='add' @click.prevent @click="addlist('addqueryform')">添加</button>
                    </el-form-item>
                </div>
            </el-form>
            <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="460px" size="small"  style="width: 100%;margin-top:20px;" @selection-change="handleSelectionChange">
                <el-table-column align="center" label="自助打菜"  width="80px" show-overflow-tooltip >
                    <template slot-scope="scope">
                            <el-switch v-model="scope.row.state" @change='changestate(scope.row.id,scope.row.state)' active-value="1" inactive-value="0" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                    </template>
                </el-table-column>
                <el-table-column align="center"  width="80px" prop="platetype" label="餐盘种类" show-overflow-tooltip> </el-table-column>
                <el-table-column align="center" width="60px"  prop="weight" label="重量" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"    prop="energy" label="能量" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="protein" label="蛋白质" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="fat" label="脂肪" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"  prop="fattyacids" label="饱和脂肪酸" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="cholesterol" label="胆固醇" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="carbohydrate" label="碳水化合物" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="50px"  prop="dietaryfiber" label="膳食纤维" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="50px"  prop="vitamina" label="维生素A" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="vitamind" label="维生素D" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="vitamine" label="维生素E" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="vitamink" label="维生素K" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="50px"  prop="vitaminb1" label="维生素B1" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="vitaminb2" label="维生素B2" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="vitaminb6" label="维生素B6" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="58px"   prop="vitamin12" label="维生素B12" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="50px"   prop="vitaminc" label="维生素C" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"  prop="nicotinicacid" label="烟酸" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" width="34px"   prop="folicacid" label="叶酸" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"  prop="pantothenicacid" label="泛酸" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"  prop="biotin" label="生物素" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"  prop="choline" label="胆碱" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px" prop="calcium" label="钙" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="phosphorus" label="磷" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="potassium" label="钾" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="sodium" label="钠" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="magnesium" label="镁" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"  prop="iron" label="铁" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="zinc" label="锌" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="iodine" label="碘" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"   prop="selenium" label="硒" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="copper" label="铜" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="fluorine" label="氟" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="chromium" label="铬" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="34px"  prop="manganese" label="锰" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  width="34px"   prop="molybdenum" label="钼" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   width="100px"  prop="restaurant" label="运维餐厅" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"   min-width="70px"  prop="weight" label="操作" show-overflow-tooltip>
                    <template slot-scope="scope">
                       <span  class="binicon"><img src="../../assets/u349.svg" @click="deletelist(scope.row.id)" alt="" style="width:18px;height:16px;vertical-align: middle;">删除</span>
                    </template>
                </el-table-column>
        </el-table>
        <div style="text-align: right; margin-top: 10px;height:'15%'">
        <el-pagination
        style="height:60px"
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
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      addqueryform: {
        restaurant: '',
        platetype: '',
        weight: '',
        energy:'',
        state:'',
        protein:'',
        fat:'',
        fattyacids:'',
        cholesterol:'',
        carbohydrate:'',
        dietaryfiber:'',
        vitamina:'',
        vitamind:'',
        vitamine:'',
        vitamink:'',
        vitaminb1:'',
        vitaminb2:'',
        vitaminb6:'',
        vitamin12:'',
        vitaminc:'',
        nicotinicacid:'',
        folicacid:'',
        pantothenicacid:'',
        biotin:'',
        choline:'',
        calcium:'',
        phosphorus:'',
        potassium:'',
        sodium:'',
        magnesium:'',
        iron:'',
        zinc:'',
        iodine:'',
        selenium:'',
        copper:'',
        fluorine:'',
        chromium:'',
        manganese:'',
        molybdenum:''
      },
      tableData: [],
      restaurant: [],
      equipment:[],
    }
  },
  computed: {

  },
  created () {
    this.selectlist()
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
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    changestate(id,e){
      console.log(e)
      this.$axios({
        url:'stAccurateChoosingMeals.do/updateState',
        method:'post',
        data:{
          id:id,
          state:e
        }
      }).then(res=>{
        this.pagelist()
        console.log(id,e)
      })
    },
    // 获取下拉框数据
    selectlist(){
      this.$axios({
        url:'/stAccurateChoosingMeals.do/getDiningRoom',
        method:'post',
      }).then(res=>{
        this.restaurant=res.data.restaurant
        this.equipment=res.data.equipment
      })
    },
    // 营养列表
    pagelist(){
      this.$axios({
        url:'stAccurateChoosingMeals.do/getNutritionList',
        method:'post',
        data:{
        "pageNum": this.pageIndex,
        "pageSize":this.pageSize,
        "param": {
                "restaurant": this.addqueryform.restaurant,
                "platetype": this.addqueryform.platetype,
                "weight":this.addqueryform.weight,
                "energy":this.addqueryform.energy,
                "state":this.addqueryform.state,
                "protein":this.addqueryform.protein,
                "fat":this.addqueryform.fat,
                "fattyacids":this.addqueryform.fattyacids,
                "cholesterol":this.addqueryform.cholesterol,
                "carbohydrate":this.addqueryform.carbohydrate,
                "dietaryfiber":this.addqueryform.dietaryfiber,
                "vitamina":this.addqueryform.vitamina,
                "vitamind":this.addqueryform.vitamind,
                "vitamine":this.addqueryform.vitamine,
                "vitamink":this.addqueryform.vitamink,
                "vitaminb1":this.addqueryform.vitaminb1,
                "vitaminb2":this.addqueryform.vitaminb2,
                "vitaminb6":this.addqueryform.vitaminb6,
                "vitamin12":this.addqueryform.vitamin12,
                "vitaminc":this.addqueryform.vitaminc,
                "nicotinicacid":this.addqueryform.nicotinicacid,
                "folicacid":this.addqueryform.folicacid,
                "pantothenicacid":this.addqueryform.pantothenicacid,
                "biotin":this.addqueryform.biotin,
                "choline":this.addqueryform.choline,
                "calcium":this.addqueryform.calcium,
                "phosphorus":this.addqueryform.phosphorus,
                "potassium":this.addqueryform.potassium,
                "sodium":this.addqueryform.sodium,
                "magnesium":this.addqueryform.magnesium,
                "iron":this.addqueryform.iron,
                "zinc":this.addqueryform.zinc,
                "iodine":this.addqueryform.iodine,
                "selenium":this.addqueryform.selenium,
                "copper":this.addqueryform.copper,
                "fluorine":this.addqueryform.fluorine,
                "chromium":this.addqueryform.chromium,
                "manganese":this.addqueryform.manganese,
                "molybdenum":this.addqueryform.molybdenum
              }
         }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.list
        this.total=res.data.totalRecord
      })
    },
    // 查询列表
    querylist(queryform){
      this.pagelist()
      this.$refs[queryform].resetFields()
    },
    // 添加功能
    addlist(queryform){
      this.$refs[queryform].validate(valid=>{
        console.log(valid)
        if(valid){
      this.$axios({
        url:'stAccurateChoosingMeals.do/addNutrition',
        method:'post',
        data:{
                "restaurant": this.addqueryform.restaurant,
                "platetype": this.addqueryform.platetype,
                "weight":this.addqueryform.weight,
                "energy":this.addqueryform.energy,
                "state":this.addqueryform.state,
                "protein":this.addqueryform.protein,
                "fat":this.addqueryform.fat,
                "fattyacids":this.addqueryform.fattyacids,
                "cholesterol":this.addqueryform.cholesterol,
                "carbohydrate":this.addqueryform.carbohydrate,
                "dietaryfiber":this.addqueryform.dietaryfiber,
                "vitamina":this.addqueryform.vitamina,
                "vitamind":this.addqueryform.vitamind,
                "vitamine":this.addqueryform.vitamine,
                "vitamink":this.addqueryform.vitamink,
                "vitaminb1":this.addqueryform.vitaminb1,
                "vitaminb2":this.addqueryform.vitaminb2,
                "vitaminb6":this.addqueryform.vitaminb6,
                "vitamin12":this.addqueryform.vitamin12,
                "vitaminc":this.addqueryform.vitaminc,
                "nicotinicacid":this.addqueryform.nicotinicacid,
                "folicacid":this.addqueryform.folicacid,
                "pantothenicacid":this.addqueryform.pantothenicacid,
                "biotin":this.addqueryform.biotin,
                "choline":this.addqueryform.choline,
                "calcium":this.addqueryform.calcium,
                "phosphorus":this.addqueryform.phosphorus,
                "potassium":this.addqueryform.potassium,
                "sodium":this.addqueryform.sodium,
                "magnesium":this.addqueryform.magnesium,
                "iron":this.addqueryform.iron,
                "zinc":this.addqueryform.zinc,
                "iodine":this.addqueryform.iodine,
                "selenium":this.addqueryform.selenium,
                "copper":this.addqueryform.copper,
                "fluorine":this.addqueryform.fluorine,
                "chromium":this.addqueryform.chromium,
                "manganese":this.addqueryform.manganese,
                "molybdenum":this.addqueryform.molybdenum
        }
      }).then(res=>{
        console.log(res)
        this.$refs[queryform].resetFields()
        this.pagelist()
        this.$message.success('添加成功')
      })
        }else{
          return false
        }
      })
      
    },
    // 删除
    deletelist(id){
      this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'stAccurateChoosingMeals.do/deleteNutrition',
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
    }

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
.nuttitle{
    box-sizing: border-box;
    padding-top: 5px;
    font-size: 14px;
    margin-bottom:10px ;
    padding-bottom:5px ;
    border-bottom:1px solid rgb(37,81,108);
}
.nutcard{
    background-color:rgba(1,5,22,.4);
    box-sizing: border-box;
    margin-right: 10px;
    border-radius: 0;
    border:none;
    width: 100%;
}
.queryform{
    color: rgb(255,255,255);
    font-size: 12px;
    width:100%;
    padding-left:20px;
    margin-top:16px;
    box-sizing: border-box;
    display: flex;
}
/deep/.el-form-item__label{
    font-size: 10px;
    color:rgb(255,255,255) ;
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
.el-form-item{
    margin-bottom: 16px;
}
.vitamin,.mineral{
    float:left;
    flex: 2.5;
    border:1px dashed rgb(82,85,97);
    box-sizing:border-box;
    padding:40px 0 0 20px;
    position: relative;
    margin-right:20px;
}
.vitamintitle,.mineraltitle{
    position: absolute;
    top:-10px;
    left:48%;
    text-align: center;
    font-size: 14px;
}
.search,.add{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.search{
    position: absolute;
    bottom:-40px;
    left: -80px;
}
.add{
    position: absolute;
    bottom:-40px;
    left: 30px;
}
.search,.add:hover{
  cursor: pointer;
}
.binicon img:hover{
  cursor: pointer;
}
/deep/.el-table{
    background-color:transparent;
}
 /deep/.el-table__body-wrapper{
    height: 630px!important;
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
/deep/.number,/deep/.btn-prev,/deep/.btn-next{
  background: transparent;
  color: white;
}
/deep/.el-pagination button:disabled{
  background: transparent;
}
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
/deep/.el-switch{
  height: 20px;
  line-height: 20px;
  font-size: 14px;
}
/deep/.el-switch__core{
  vertical-align: middle;
  width: 40px!important;
  height: 20px!important;
}
/deep/.el-switch.is-checked /deep/.el-switch__core::after{
  left: 100%;
    margin-left: -17px;
}
/deep/.el-switch__core:after{
  width: 16px;
  height: 16px;
  top:1px;
  left:1px;
}
</style>
