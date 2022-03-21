<template>
    <div class='container'>
        <div class='meatitle'>智能排餐</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                <el-col :span='17' class='iptcol'>
                    <el-form-item label="自定义时间:">
                        <el-date-picker
                            v-model="queryform.time"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择日期">
                            </el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span='2'  class='iptcol' >
                    <el-form-item>
                        <button class='search' @click.prevent @click="foodlist">查询</button>
                    </el-form-item>
                </el-col>
                 <el-col :span='2'  class='iptcol' >
                    <el-form-item>
                        <button class='mealarr' @click.prevent @click="arrangemeals">一键排餐</button>

                    </el-form-item>
                </el-col>
                  <el-col :span='2'  class='iptcol'>
                    <el-form-item>
                        <button class='leadout' @click.prevent @click="exportout">一键导出</button>
                    </el-form-item>
                </el-col>
            </el-row>
            </el-form>
            <div class='breakfast'>
                  <div style="overflow:hidden;">
                  <img v-if="imgshowr" src="../../assets/1_u7373.svg" alt="" class='triangle' style='transform: rotate(90deg);' @click="changer">
                  <img v-if="imgshowb" src="../../assets/1_u7373.svg" alt="" class='triangle' style='transform: rotate(180deg);' @click="changeb">
                  <div class='titlebox'>早餐<div style="float:right;"><button class='search' @click.prevent @click="oneclickrearr(1)">一键重排</button>
                  <button class='search' @click.prevent @click="dishesreplace(1)">菜品替换</button></div></div>
                </div>
                <div class='colltop' v-if='collshow'>
                      <div style='margin-bottom:10px;'>主食: <button class='search' @click.prevent @click="breakstaple">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname1" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>汤类: <button class='search' @click.prevent @click="breaksoup">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname2" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>水果: <button class='search' @click.prevent @click="breakfruit">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname3" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>凉菜: <button class='search' @click.prevent @click="breakcolddish">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname4" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>热菜: <button class='search' @click.prevent @click="breakhotdish">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname5" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                </div>
            </div>
            <div class='lunch'>
                  <div style="overflow:hidden;">
                  <img v-if="imgshowlr" src="../../assets/1_u7373.svg" class='triangle' alt="" style='transform: rotate(90deg);' @click="changelr">
                  <img v-if="imgshowlb" src="../../assets/1_u7373.svg" class='triangle' alt="" style='transform: rotate(180deg);' @click="changelb">
                  <div class='titlebox'>午餐<div style="float:right;"><button class='search' @click.prevent @click="oneclickrearr(2)">一键重排</button>
                  <button class='search' @click.prevent @click="dishesreplace(2)">菜品替换</button></div></div>
                  </div>
                  <div class='colltop' v-if="collshowl">
                      <div style='margin-bottom:10px;'>主食: <button class='search' @click.prevent @click="lunchstaple">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname6" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>汤类: <button class='search' @click.prevent @click="lunchsoup">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname7" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>水果: <button class='search' @click.prevent @click="lunchfruit">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname8" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>凉菜: <button class='search' @click.prevent @click="lunchcolddish">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname9" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>热菜: <button class='search' @click.prevent @click="lunchhotdish">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname10" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                </div>
            </div>
              <div class='dinner'>
                <div style="overflow:hidden;">
              <img v-if="imgshowdr" src="../../assets/1_u7373.svg" alt="" class='triangle' style='transform: rotate(90deg);' @click="changedr">
              <img v-if="imgshowdb" src="../../assets/1_u7373.svg" alt="" class='triangle' style='transform: rotate(180deg);' @click="changedb">
              <div class='titlebox'>晚餐<div style="float:right;"><button class='search' @click.prevent @click="oneclickrearr(3)">一键重排</button>
              <button class='search' @click.prevent @click="dishesreplace(3)">菜品替换</button></div></div>
              </div>
              <div class='colltop' v-if="collshowd">
                      <div style='margin-bottom:10px;'>主食: <button class='search' @click.prevent @click="dinnerstaple">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname11" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>汤类: <button class='search' @click.prevent @click="dinnersoup">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname12" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>水果: <button class='search' @click.prevent @click="dinnerfruit">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname13" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>凉菜: <button class='search' @click.prevent @click="dinnercolddish">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname14" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                      <div style='margin-bottom:10px;'>热菜: <button class='search' @click.prevent @click="dinnerhotdish">手动添加</button>
                      <div class='foodname'>
                        <span v-for="(item,id) in foodname15" :key='id'>{{item.name}}、<img src="../../assets/x_u6386.svg" alt="" @click="delectfood(item.id)"> </span>
                      </div>
                      </div>
                </div>
            </div>
              <el-dialog class="adddialog"  title="添加" :visible.sync="dialogaddVisible">
                            <el-form :model="addform" class="addcategory">
                                <el-row>
                                    <el-col :span='9'>
                                        <el-form-item  label="菜品名称:" >
                                            <el-input class='name' v-model="addform.name"></el-input>
                                            </el-form-item>
                                    </el-col>
                                    <el-col :span='7'>
                                        <el-form-item>
                                             <button class='search' @click="querylist" @click.prevent>搜索</button>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                              </el-form>
                              <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
             height="280px" size="small"  style="width: 100%;margin-bottom:15px;"  @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="15%" prop="name" label="名称"> </el-table-column>
                <el-table-column align="center"  min-width="15%" prop="category" label="类别"></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="referenceprice" label="参考价格(元)" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="weight" label="重量" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="energy" label="能量" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="15%"  prop="remarks" label="备注" show-overflow-tooltip></el-table-column>
        </el-table>
        <span>确认添加菜品:{{multipleSelection}}</span>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:30px;" class="btnaddchild"  type="primary" @click="addcatefood">添加</el-button>
                              </div>
                        </el-dialog>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      dialogaddVisible: false,
      multipleSelection:'',//菜品名字
      ids:[], //菜品id
      collshow: true,
      imgshowr: false,
      imgshowb: true,
      collshowl: true,
      imgshowlr: false,
      imgshowlb: true,
      collshowd: true,
      imgshowdr: false,
      imgshowdb: true,
      restaurantCode:'',//树状图code码
      meals:'',//餐别
      dishescategory:'',//餐类编号
      queryform:{
        time: ''
      },
      foodname1:[],
      foodname2:[],
      foodname3:[],
      foodname4:[],
      foodname5:[],
      foodname6:[],
      foodname7:[],
      foodname8:[],
      foodname9:[],
      foodname10:[],
      foodname11:[],
      foodname12:[],
      foodname13:[],
      foodname14:[],
      foodname15:[],
      addform: {
        name: '',
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
    this.treelist()
    this.foodlist()
  },
  computed: {
    timeDefault() {
      var date = new Date();
      var s1 = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + (date.getDate());
      return s1;
    }
  },
  mounted() {
    // 初始化查询，默认为前一天
    this.queryform.time = this.timeDefault;
  },
  methods: {
    
    handleSelectionChange (val) {
      this.multipleSelection=val.map(item=>{
        return item.name
      })
      this.ids=val.map(item=>{
        return item.id
      })
      console.log(this.multipleSelection)
    },
    handleNodeClick (data) {
      this.restaurantCode=data.restaurantCode
      console.log(data)
    },
    
    changer () {
      this.imgshowr = false
      this.imgshowb = true
      this.collshow = true
    },
    changeb () {
      this.imgshowr = true
      this.imgshowb = false
      this.collshow = false
    },
    changelr () {
      this.imgshowlr = false
      this.imgshowlb = true
      this.collshowl = true
    },
    changelb () {
      this.imgshowlr = true
      this.imgshowlb = false
      this.collshowl = false
    },
    changedr () {
      this.imgshowdr = false
      this.imgshowdb = true
      this.collshowd = true
    },
    changedb () {
      this.imgshowdr = true
      this.imgshowdb = false
      this.collshowd = false
    },
    breakstaple(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=1
      this.dishescategory=1
      this.pagelist()
     }
      
    },
    breaksoup(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=1
      this.dishescategory=2
      this.pagelist()
     }
      
    },
    breakfruit(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=1
      this.dishescategory=3
      this.pagelist()
     }
      
    },
    breakcolddish(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=1
      this.dishescategory=4
      this.pagelist()
     }
      
    },
    breakhotdish(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=1
      this.dishescategory=5
      this.pagelist()
     }
      
    },
    lunchstaple(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=2
      this.dishescategory=1
      this.pagelist()
     }
      
    },
    lunchsoup(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=2
      this.dishescategory=2
      this.pagelist()
     }
      
    },
    lunchfruit(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=2
      this.dishescategory=3
      this.pagelist()
     }
      
    },
    lunchcolddish(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=2
      this.dishescategory=4
      this.pagelist()
     }
      
    },
    lunchhotdish(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=2
      this.dishescategory=5
      this.pagelist()
     }
      
    },
    dinnerstaple(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=3
      this.dishescategory=1
      this.pagelist()
     }
      
    },
    dinnersoup(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=3
      this.dishescategory=2
      this.pagelist()
     }
      
    },
    dinnerfruit(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=3
      this.dishescategory=3
      this.pagelist()
     }
      
    },
    dinnercolddish(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=3
      this.dishescategory=4
      this.pagelist()
     }
      
    },
    dinnerhotdish(){
      if(this.restaurantCode==''){
        this.$message('请选择运维餐厅');
     }else{
       this.dialogaddVisible = true
      this.meals=3
      this.dishescategory=5
      this.pagelist()
     }
      
    },
    //树状图接口
    treelist(){
      this.$axios({
        url: 'StOperationrestaurant.do/getStOperationList',
        method: 'post',
      }).then(res=>{
          this.data=res.data
      })
    },
   //手动添加—菜品查询
   pagelist(){
     
     this.$axios({
        url:'StIntelligenceMeals.do/SelectMyFood',
        method:'post',
        data:{
            "restaurant": this.restaurantCode,
            "meals":this.meals,
            "dishescategory":this.dishescategory,
            "name":this.addform.name
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data
      })
   },
  //  添加菜品
  addcatefood(){
    this.$axios({
      url:'StIntelligenceMeals.do/UpdateDishescategory',
      method:'post',
      data:{
        "ids":this.ids
      }
    }).then(res=>{
      if(res.data.message=='添加菜品成功'){
        this.$message.success('添加菜品成功');
        this.foodlist()
      }else {
        this.$message('添加菜品失败');
      this.foodlist()
      }
      
    })
    this.dialogaddVisible = false
  },
  // 菜品搜索
  querylist(){
    this.pagelist()
  },
// 页面餐食查询
  foodlist(){
    this.$axios({
      url:'StIntelligenceMeals.do/SelectByRestaurant',
      method:'post',
      data:{
        "restaurant":this.restaurantCode,
        "date":this.queryform.time
      }
    }).then(res=>{
      this.foodname1=res.data.breakfast[0].breakfastZs
      this.foodname2=res.data.breakfast[0].breakfastSoup
      this.foodname3=res.data.breakfast[0].breakfastSg
      this.foodname4=res.data.breakfast[0].breakfastLc
      this.foodname5=res.data.breakfast[0].breakfastRc
      this.foodname6=res.data.lunch[0].breakfastZs
      this.foodname7=res.data.lunch[0].breakfastSoup
      this.foodname8=res.data.lunch[0].breakfastSg
      this.foodname9=res.data.lunch[0].breakfastLc
      this.foodname10=res.data.lunch[0].breakfastRc
      this.foodname11=res.data.dinner[0].breakfastZs
      this.foodname12=res.data.dinner[0].breakfastSoup
      this.foodname13=res.data.dinner[0].breakfastSg
      this.foodname14=res.data.dinner[0].breakfastLc
      this.foodname15=res.data.dinner[0].breakfastRc
    })
  },
  // 食物删除
  delectfood(id){
    this.$confirm('确认删除该菜品?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
      this.$axios({
              url:'StIntelligenceMeals.do/DeleteMyFoodByID',
              method:'post',
              data:{
              "id":id
      }
            }).then(res=>{
        this.$message.success('删除成功')
        this.foodlist()
        })
      }).catch(()=>{

      })
  },
  // 一键排餐
  arrangemeals(){
    this.$axios({
      url:'StIntelligenceMeals.do/SelectMyFoodByRestaurant',
      method:'post',
      data:{
        "restaurant":this.restaurantCode
      }
    }).then(res=>{
      if(res.data.msg=="一键排餐成功"){
      this.$message.success('一键排餐成功');
      this.foodlist()
      }
    })
  },
  // 一键重排
  oneclickrearr(meals){
    this.$axios({
      url:'StIntelligenceMeals.do/SelectMyFoodByRepeat',
      method:'post',
      data:{
        "restaurant":this.restaurantCode,
        "meals":meals
      }
    }).then(res=>{
      if(res.data.msg=="重排成功"){
      this.$message.success('重排成功');
      this.foodlist()
      }
    })
  },
  // 菜品替换
  dishesreplace(meals){
    this.$axios({
      url:'StIntelligenceMeals.do/dishesReplacement',
      method:'post',
      data:{
        "restaurant":this.restaurantCode,
        "meals":meals,
        "date":this.queryform.time
      }
    }).then(res=>{
      if(res.data.msg=="菜品替换成功"){
      this.$message.success('菜品替换成功');
      this.foodlist()
      }else{
      this.$message.success('菜品替换失败');

      }
    })
  },
  // 一键导出
  exportout(){
    this.$axios({
        url:'StIntelligenceMeals.do/choiceDishExport',
        method:'post',
        data:{
          "restaurant":this.restaurantCode,
          "date":this.queryform.time
        },

        responseType:'blob'
      }).then(res=>{
           let data = res;
            let blob = new Blob([data.data], { type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"});
            let url = window.URL.createObjectURL(blob);
            let link = document.createElement("a");
            link.style.display = "none";
            link.href = url;
            link.setAttribute("download", "一键导出.xls");
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
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
    margin-right: 20px;
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
// /deep/.el-tree-node__content{
//   background:transparent;
// }
.tablecard{
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    overflow: auto;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:82%;
    height: 860px;
}

.queryform{
    width:100%;
    margin-top:0px;
}
.iptrow{
   width:100%;
   height:56px;
   margin-bottom:0px;
   padding: 10px 0px 0px 10px;
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
    padding:0 28px;
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
  color: rgb(255,255,255);
  padding-right: 12px;
}
.search,.mealarr,.leadout{
    color:rgb(255,255,255) ;
    font-size: 10px;
    padding:0;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
button:hover{
  cursor: pointer;
}
.titlebox{
  border-bottom:1px solid rgb(0,80,145);
  overflow: hidden;
  float:right;
  width:98%;
  line-height:33px;
  color:white;
}
.colltop{
  color: white;
  padding: 14px 0 0px 40px;
}
.breakfast{
  margin-bottom: 20px;
}
.lunch{
  margin-bottom: 20px;
}
/deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
}
/deep/.el-tree-node__content{
background:transparent;
}
.adddialog .point{
    width: 240px;
    height:100px;
    position: absolute;
    top:100px;
    right:40px;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  width: 700px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:rgba(0,0,0,.4);
  padding:30px 20px 2px 20px;
  
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
/deep/.el-table{
    background-color:transparent;
    height: 200px!important;
}
 /deep/.el-table__body-wrapper{
    height: 160px!important;
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
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
.foodname{
  display: inline-block;
  font-size: 12px;
}
.foodname span{
  position: relative;
}
.foodname img{
  position:absolute;
  display: none;
  width: 8px;
  top:0;
  right:10px;
  background-color: red;
}
.foodname span:hover>img{
  display:block;
}
.colltop button{
  margin-right: 10px;
}
.triangle{
  width: 25px;
  margin-top:10px;
}
.iptrow /deep/.el-input{
  width: 220px;
}
/deep/.el-input__icon{
  width: 25px;
  line-height: 28px;
}
/deep/.el-input__prefix{
  left: 5px;
}
.name{
  width: 160px;
}
.el-dialog span{
  font-size: 14px;
}
</style>
