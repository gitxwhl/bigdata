<template>
    <div class='container'>
        <div class='meatitle'>菜品查询</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-form :model="queryform" ref="queryform" size="mini" class='queryform' >
            <el-row class="iptrow">
                <el-col :offset="6" :span='8' class='iptcol'>
                    <el-form-item>
                        <el-input class="input" v-model="queryform.name" placeholder="请输入菜品名称"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span='3' class='iptcol'>
                    <el-form-item>
                     <button class='search' @click.prevent @click="pagelist">搜索</button>
                    </el-form-item>
                </el-col>
            </el-row>
                    <el-form-item class="timeform" label="时间:">
                      <el-date-picker
                        v-model="queryform.time"
                        value-format="yyyy-MM-dd"
                        class="timeinput"
                        type="date"
                        placeholder="请选择日期">
                      </el-date-picker>
                    </el-form-item>
            </el-form>
        <div class="classify">
         <span class="classtitle">餐别:</span>
         <span class="classspan" v-for='(item,i) in dismeals' ref='meals' :key='i' @click='addmeals(item.id,i)'>{{item.text}}</span>
       </div>
       <div class="classify">
         <span class="classtitle">分类:</span>
         <span class="classspan" v-for='(item,i) in dishesCategory' ref='dishesCategory' :key='i' @click='addcategory(item.id,i)'>{{item.text}}</span>
       </div>
       <div class="classify">
         <span class="classtitle">类别:</span>
         <span class="classspan" v-for='(item,i) in dishType' ref='dishType' :key='i' @click='addtype(item.id,i)'>{{item.text}}</span>
       </div>
       <div class="contain">
         <div class="detail" v-for="(item,index) in pagedata" :key="index" :label="item.text" :value="item.id">
           <div style="position:relative;" @mouseenter="opponut(index,item.dishcode)" @mouseleave="closenut(index)">
             <img class="detailimg" src="../../assets/u98.jpg" alt="">
             <span class="nutspan">营养成分表</span>
           </div>
           <span>{{item.name}}</span>
           <span>{{item.dishesCategory}}</span>
           <span>{{item.category}}</span>
           <span>价格：{{item.referencePrice}}</span>
           <div v-show='dialogNutVisible' class="enterbox" ref='nutdialog' @mouseenter="opponut(index,item.dishcode)" @mouseleave="closenut(index)">
                    <div class='nuttitle'>营养成分表</div>
                    <table border="1" class="nuttable">
                       <tr class='tablehead'>
                           <td>能量</td>
                            <td>每100克(g)</td>
                            <td>营养素参考值%</td>
                          </tr>
                            <tr v-for="(item,index) in nuttable" :key="index">
                              <td>{{item.name}}</td>
                              <td>{{item.energy}}</td>
                              <td>{{item.energyPro}}%</td>
                            </tr>
                     </table>
              </div>
         </div>
         
       </div>
              
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
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      dialogNutVisible:false,
      pagedata:[],
      nuttable:[],
      dismeals:[],
      dishesCategory:[],
      dishType:[],
      category:'',
      meals:'',
      timer:null,
      type:[],
      pageIndex: 1,
      pageSize:20,
      pageSizes:[ 20, 30 , 40, 50, 100],
      total:0,
      restaurantCode:'',//树状图id
      queryform: {
        name: '',
        time: ''
      },
      data: [],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
    this.querydishes()
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
    opponut(id,dishcode){
      this.$refs.nutdialog[id].style.display='block'
      window.clearTimeout(this.timer)
      var that=this
      this.timer=window.setTimeout(function(){
        that.$axios({
                url:'StMyfoodController.do/getNutritional',
                method:'post',
                data:{
                  "dishcode":dishcode
                }
              }).then(res=>{
                that.nuttable=res.data
              })
      },200)
    },
    closenut(id){
      this.$refs.nutdialog[id].style.display='none'
    },
    addmeals(id,index){
          for(var i=0;i<this.$refs.meals.length;i++){
                this.$refs.meals[i].style.background='none'
                }
          this.$refs.meals[index].style.background='#0033FF'
          this.meals=id
          this.pagelist()
    },
    addcategory(id,index){
      for(var i=0;i<this.$refs.dishesCategory.length;i++){
      this.$refs.dishesCategory[i].style.background='none'
      }
      this.$refs.dishesCategory[index].style.background='#0033FF'
      this.category=id
      this.pagelist()
    },
    addtype(id,index){
      if(this.$refs.dishType[index].style.background==''){
          this.$refs.dishType[index].style.background='#0033FF'
          this.type.push(id)

      }else{
          this.$refs.dishType[index].style.background=''
          if(this.type.includes(id)){
            this.type.splice(this.type.indexOf(id),1)
          }
      }
        this.pagelist()
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
    // 菜品查询的关联属性
    querydishes(){
      this.$axios({
        url:'StMyfoodController.do/queryDishDic',
        method:'get'
      }).then(res=>{
        this.dismeals=res.data.meals
        this.dishType=res.data.dishType
        this.dishesCategory=res.data.dishesCategory
      })
    },
    // 菜品查询
    pagelist(){
      this.$axios({
        url:'StMyfoodController.do/queryDish',
        method:'post',
        data:{
        "pageNum": this.pageIndex,
        "pageSize":this.pageSize, 
        "restaurant":this.restaurantCode,
        "name":this.queryform.name,
        "category":this.type,
        "meals":this.meals,
        "dishescategory":this.category,
        "time":this.queryform.time
         }
      }).then(res=>{
        this.pagedata=res.data.data.list
        this.total=res.data.data.totalRecord
      })
    },
    // 菜品营养成分分析
    getNutritional(){
      
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
    overflow: auto;
    background-color:rgba(1,5,22,.4);
    padding:0 20px 0 30px;
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
    padding-left:30px;
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
.input{
  width: 492px;
}
.timeinput{
  width: 300px;
}
.timeform{
  margin-bottom: 18px;
}
/deep/.el-input__icon{
  line-height: 28px;
  width: 25px;
}
/deep/.el-input__prefix{
  left:5px;
}
.classify{
  margin-bottom: 10px;
  color: white;
}
.classspan{
  display: inline-block;
  margin-left:20px;
  font-size: 12px;
  width: 45px;
  height: 20px;
  text-align: center;
  line-height: 20px;
  border-radius:5px ;
}
.classspan:hover{
  background:#FE0099!important;

}
.classtitle{
  font-size: 12px;
}
.contain{
  padding-right:100px;
  box-sizing: border-box;
  width: 100%;
  margin-top:30px;
}
.detail{
  position: relative;
  display: inline-block;
  margin-right: 25px;
  margin-bottom: 10px;
}
.detailimg{
  width: 112px;
  height:89px;
}
.detail span{
  display: block;
  color:white;
  font-size: 12px;
}
.el-pagination /deep/.el-input__inner{
    padding:0px;
}
.nutspan{
  position: absolute;
  right: 5px;
  bottom: 10px;
}
.enterbox{
  position: absolute;
  z-index: 10;
  left:35px;
  top: 60px;
  width: 300px;
  background:black;
  border: 1px solid white;
  font-size: 12px;
  padding-bottom: 20px;
  box-sizing: border-box;
}
.nuttitle{
  height: 30px;
  color:white;
  line-height: 30px;
  padding-left: 10px;
  box-sizing: border-box;
}
.nuttable{
    color: white;
    width:80%;
    margin: 20px auto;
    table-layout:fixed;
    background:transparent;
    border-collapse: collapse;
}
.nuttable td{
    height:25px;
    padding:0 0 0 5px;
    font-size: 10px;
    box-sizing: border-box;
    background:transparent;
    border:1px solid rgb(128,128,128);
}
.tablehead{
    background:transparent;

}
.nuttable tr td{
    background:transparent!important;
}
.tablehead td{
    width: 30px;
    height:30px;
    text-align: center;
    font-size: 12px;
}

</style>
