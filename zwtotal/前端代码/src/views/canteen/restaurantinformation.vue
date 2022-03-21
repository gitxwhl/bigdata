<template>
    <div class='container'>
        <div class='meatitle'>餐厅信息</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree class='treeclass' :data="data" :highlight-current='true' ref='treelist' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        <div v-show="resnoshow" class='resnoshow'>
            请选择到具体的子运维餐厅
        </div>
        <div v-show='resisshow'>
            <div class='reshead'>
            <div class='resintroduce'>餐厅介绍:</div>
            <div class='resxx' @click='inputshow' v-show='restextshow'>{{introduction}}</div>
            <el-input v-model='introduction' v-show='resinputshow' @blur='updateMealNumber(introduction,capacity)' ref='inputfocus'></el-input>
            </div>
            <div class='rescontent clearfix'>
            <div class='resintroducet'>餐厅承载人数:</div>
            <div class='capxx' @click='inputrsshow' v-show='restextrsshow'>{{capacity}}</div>
            <el-input class='elinput' v-model='capacity' v-show='resinputrsshow' @blur='updateMealNumberrs(introduction,capacity)' ref='inputfocust'></el-input>
            <div>
                <div class='imgbox'>
                    <img src="../../assets/u7262.jpg" alt="">
                    <div class='absotype' v-show='breakfastleave' @mouseenter="breakfastone" ref='breakfast'>
                        <div class='iconbox'><img src="../../assets/u7271.svg" alt=""><div class='icontext'>早餐</div></div>
                    </div>
                    <div class='enterabsotype' v-show='breakfastenter' @mouseleave="breakfasttwo" ref='breakfasttwo'>
                        <div class='iconbox'><img src="../../assets/u7271.svg" alt=""><div class='icontext'>早餐</div></div>
                        <div class='periodinformation' @click='clickrevisebreak'>
                            <div>供应时段: {{querydata.morning!=null?querydata.morning.supplystarttime:''}}-{{querydata.morning!=null?querydata.morning.supplyendtime:''}}</div>
                            <div>配送时段: {{querydata.morning!=null?querydata.morning.givestarttime:''}}-{{querydata.morning!=null?querydata.morning.giveendtime:''}}</div>
                            <div>上柜时段: {{querydata.morning!=null?querydata.morning.upperstarttime:''}}-{{querydata.morning!=null?querydata.morning.upperendtime:''}}</div>
                            <div>{{querydata.morning!=null?querydata.morning.mealintroduction:''}}</div>
                        </div>
                        
                    </div>
                    <div class='enterabsotype' v-show='breakfastenterclick'>
                        <div class='iconbox'><img src="../../assets/u7271.svg" alt=""><div class='icontext'>早餐</div><button class='bcbtn' 
                        @click='savechanges(morningid,morningsupplystarttime,morningsupplyendtime,morninggivestarttime,morninggiveendtime,morningupperstarttime,morningupperendtime,
                        morningmealintroduction,morningdictionary)'>保存</button><button class='bcbtn' @click='removebreak'>取消</button></div>
                        <div class='periodinformation'>
                            <div class='marbottime'>供应时段:
                                    <el-time-select v-model="morningsupplystarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="morningsupplyendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                            </div>
                            <div class='marbottime'>配送时段:
                                    <el-time-select v-model="morninggivestarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="morninggiveendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                                
                            </div>
                            <div class='marbottime'>上柜时段:
                                    <el-time-select v-model="morningupperstarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~    
                                    <el-time-select v-model="morningupperendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                                
                            </div>
                            <div><el-input type='textarea' v-model="morningmealintroduction"></el-input></div>
                        </div>
                        
                    </div>


                </div>


                <div class='imgbox'><img src="../../assets/u7263.jpg" alt="">

                    <div class='absotype' v-show='lunchleave' @mouseenter="lunchone" ref='lunchfast'>
                        <div class='iconbox'><img src="../../assets/u7267.svg" alt=""><div class='icontext'>午餐</div></div>
                    </div>

                    <div class='enterabsotype' v-show='lunchenter' @mouseleave="lunchtwo" ref='lunchfasttwo'>
                        <div class='iconbox'><img src="../../assets/u7267.svg" alt=""><div class='icontext'>午餐</div></div>
                        <div class='periodinformation' @click='clickreviselunch'>
                            <div>供应时段: {{querydata.centre!=null?querydata.centre.supplystarttime:''}}-{{querydata.centre!=null?querydata.centre.supplyendtime:''}}</div>
                            <div>配送时段: {{querydata.centre!=null?querydata.centre.givestarttime:''}}-{{querydata.centre!=null?querydata.centre.giveendtime:''}}</div>
                            <div>上柜时段: {{querydata.centre!=null?querydata.centre.upperstarttime:''}}-{{querydata.centre!=null?querydata.centre.upperendtime:''}}</div>
                            <div>{{querydata.centre!=null?querydata.centre.mealintroduction:''}}</div>
                        </div>
                    </div>


                    <div class='enterabsotype' v-show='lunchenterclick'>
                        <div class='iconbox'><img src="../../assets/u7267.svg" alt=""><div class='icontext'>午餐</div><button class='bcbtn'
                         @click='savechanges(centreid,centresupplystarttime,centresupplyendtime,centregivestarttime,centregiveendtime,centreupperstarttime,centreupperendtime
                         ,centremealintroduction,centredictionary)'>保存</button><button class='bcbtn' @click='removelunch'>取消</button></div>
                        <div class='periodinformation'>
                            <div class='marbottime'>供应时段:
                                    <el-time-select v-model="centresupplystarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="centresupplyendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                               
                            </div>
                            <div class='marbottime'>配送时段: 
                                    <el-time-select v-model="centregivestarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="centregiveendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                                
                            </div>
                            <div class='marbottime'>上柜时段: 
                                    <el-time-select v-model="centreupperstarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="centreupperendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                               
                            </div>
                            <div><el-input type='textarea' v-model="centremealintroduction"></el-input></div>
                        </div>
                    </div>



                </div>
                <div class='imgbox'><img src="../../assets/u7264.png" alt="">

                    <div class='absotype' v-show='dinnerleave' @mouseenter="dinnerone" ref='dinnerfast'>
                        <div class='iconbox'><img src="../../assets/u7268.svg" alt=""><div class='icontext'>晚餐</div></div>
                    </div>

                    <div class='enterabsotype' v-show='dinnerenter' @mouseleave="dinnertwo" ref='dinnerfasttwo'>
                        <div class='iconbox'><img src="../../assets/u7268.svg" alt=""><div class='icontext'>晚餐</div></div>
                        <div class='periodinformation' @click='clickrevisedinner'>
                            <div>供应时段: {{querydata.supper!=null?querydata.supper.supplystarttime:''}}-{{querydata.supper!=null?querydata.supper.supplyendtime:''}}</div>
                            <div>配送时段: {{querydata.supper!=null?querydata.supper.givestarttime:''}}-{{querydata.supper!=null?querydata.supper.giveendtime:''}}</div>
                            <div>上柜时段: {{querydata.supper!=null?querydata.supper.upperstarttime:''}}-{{querydata.supper!=null?querydata.supper.upperendtime:''}}</div>
                            <div>{{querydata.supper!=null?querydata.supper.mealintroduction:''}}</div>
                        </div>
                    </div>


                    <div class='enterabsotype' v-show='dinnerenterclick'>
                        <div class='iconbox'><img src="../../assets/u7268.svg" alt=""><div class='icontext'>晚餐</div><button class='bcbtn' 
                        @click='savechanges(supperid,suppersupplystarttime,suppersupplyendtime,suppergivestarttime,suppergiveendtime,supperupperstarttime,supperupperendtime
                        ,suppermealintroduction,supperdictionary)'>保存</button><button class='bcbtn' @click='removedinner'>取消</button></div>
                        <div class='periodinformation'>
                            <div class='marbottime'>供应时段:
                                    <el-time-select v-model="suppersupplystarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="suppersupplyendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                                
                            </div>
                            <div class='marbottime'>配送时段: 
                                    <el-time-select v-model="suppergivestarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="suppergiveendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                                
                            </div>
                            <div class='marbottime'>上柜时段: 
                                    <el-time-select v-model="supperupperstarttime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>~
                                    <el-time-select v-model="supperupperendtime" :picker-options="{ start: '00:00', step: '00:10',  end: '23:59' }" placeholder="选择时间"></el-time-select>
                               
                            </div>
                            <div><el-input type='textarea' v-model="suppermealintroduction"></el-input></div>
                        </div>
                    </div>
                </div>
            </div>

            </div>
        </div>
    </el-card>
    </div>
</template>

<script>
export default {
  data () {
    return {
      resisshow:true,
      resnoshow:false,
      restextshow:true,
      resinputshow:false,
      restextrsshow:true,
      resinputrsshow:false,
      breakfastleave:true,
      lunchleave:true,
      dinnerleave:true,
      breakfastenter:false,
      lunchenter:false,
      dinnerenter:false,
      breakfastenterclick:false,
      lunchenterclick:false,
      dinnerenterclick:false,

      morningsupplystarttime:'',
      morningsupplyendtime:'',
      morninggivestarttime:'',
      morninggiveendtime:'',
      morningupperstarttime:'',
      morningupperendtime:'',
      centresupplystarttime:'',
      centresupplyendtime:'',
      centregivestarttime:'',
      centregiveendtime:'',
      centreupperstarttime:'',
      centreupperendtime:'',
      suppersupplystarttime:'',
      suppersupplyendtime:'',
      suppergivestarttime:'',
      suppergiveendtime:'',
      supperupperstarttime:'',
      supperupperendtime:'',

      morningid:'',
      centreid:'',
      supperid:'',
      morningmealintroduction:'',
      centremealintroduction:'',
      suppermealintroduction:'',

      morningdictionary:'1',
      centredictionary:'2',
      supperdictionary:'3',

      introduction:'',
      capacity:'',
      num:1,
      querydata:[],
      type:[],
      restaurantCode:3,//树状图id
      rescode:"gs001",
      data: [],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    this.treelist()
    this.querylist()
  },
  mounted(){
      
  },
  watch:{
    data(val){
        if(val){
            this.$nextTick(() => {
                for(var i=0;i<document.querySelectorAll('.el-tree-node__content').length;i++){
                    document.querySelectorAll('.el-tree-node__content')[2].click()
                }
            })
        }
    }
  },
  methods: {
    handleNodeClick (data) {
      this.restaurantCode=data.id
      this.rescode=data.restaurantCode
      console.log(this.restaurantCode)
       if(this.restaurantCode==1||this.restaurantCode==2){
         this.resisshow=false
         this.resnoshow=true
     }else{
         this.resisshow=true
         this.resnoshow=false
         this.querylist()

     }
      console.log(data)
    },
    breakfastone(){
        this.breakfastenter=true
        this.breakfastleave=false
    },
    breakfasttwo(){
        this.breakfastenter=false
        this.breakfastleave=true
    },
    lunchone(){
        this.lunchenter=true
        this.lunchleave=false
    },
    lunchtwo(){
        this.lunchenter=false
        this.lunchleave=true
    },
    dinnerone(){
        this.dinnerenter=true
        this.dinnerleave=false
    },
    dinnertwo(){
        this.dinnerenter=false
        this.dinnerleave=true
    },
    inputshow(){
        this.restextshow=false
        this.resinputshow=true
        this.$nextTick(()=>{
        this.$refs.inputfocus.focus()
        })
    },
    inputrsshow(){
        this.restextrsshow=false
        this.resinputrsshow=true
        this.$nextTick(()=>{
        this.$refs.inputfocust.focus()
        })
    },
    clickrevisebreak(){
        this.breakfastenterclick=true,
        this.breakfastenter=false,
        this.$refs.breakfast.style.opacity="0";
        this.$refs.breakfasttwo.style.opacity="0";
    },
    clickreviselunch(){
        this.lunchenterclick=true,
        this.lunchenter=false,
        this.$refs.lunchfast.style.opacity="0";
        this.$refs.lunchfasttwo.style.opacity="0";
    },
    clickrevisedinner(){
        this.dinnerenterclick=true,
        this.dinnerenter=false,
        this.$refs.dinnerfast.style.opacity="0";
        this.$refs.dinnerfasttwo.style.opacity="0";
    },
    removebreak(){
        this.breakfastenterclick=false
        this.$refs.breakfast.style.opacity="1";
        this.$refs.breakfasttwo.style.opacity="1";

    },
    removelunch(){
        this.lunchenterclick=false,
        this.$refs.lunchfast.style.opacity="1";
        this.$refs.lunchfasttwo.style.opacity="1";

    },
    removedinner(){
        this.dinnerenterclick=false,
        this.$refs.dinnerfast.style.opacity="1";
        this.$refs.dinnerfasttwo.style.opacity="1";

    },
    // 修改餐厅介绍
    updateMealNumber(introduction,capacity){
        this.restextshow=true
        this.resinputshow=false
        this.$axios({
            url:'stRestaurantinformation.do/updateMealNumber',
            method:'post',
            data:{
                "id":this.restaurantCode,
                "capacity":capacity,
                "introduction":introduction
            }
        }).then(res=>{
            if(res.data=='修改成功'){
                this.$message.success('修改成功')
                this.querylist()
            }else{
                this.$message.error('修改失败')

            }
        })
    },
    // 修改人数
    updateMealNumberrs(introduction,capacity){
        this.restextrsshow=true
        this.resinputrsshow=false
        this.$axios({
            url:'stRestaurantinformation.do/updateMealNumber',
            method:'post',
            data:{
                "id":this.restaurantCode,
                "capacity":capacity,
                "introduction":introduction
            }
        }).then(res=>{
            if(res.data='修改成功'){
                this.$message.success('修改成功')
                this.querylist()
            }else{
                this.$message.error('修改失败')

            }
        })
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
    // 查询接口
    querylist(){
        this.$axios({
            url:'stRestaurantinformation.do/insertStRestaurantinformation',
            method:'post',
            data:{
                "restaurant":this.restaurantCode
            }
        }).then(res=>{
            this.querydata=res.data
            console.log(this.querydata)
            this.introduction=res.data.Introduce.introduction
            this.capacity=res.data.Introduce.capacity
            this.morningsupplystarttime=res.data.morning!=null?res.data.morning.supplystarttime:'';
            this.morningsupplyendtime=res.data.morning!=null?res.data.morning.supplyendtime:'';
            this.morninggivestarttime=res.data.morning!=null?res.data.morning.givestarttime:'';
            this.morninggiveendtime=res.data.morning!=null?res.data.morning.giveendtime:'';
            this.morningupperstarttime=res.data.morning!=null?res.data.morning.upperstarttime:'';
            this.morningupperendtime=res.data.morning!=null?res.data.morning.upperendtime:'';
            this.centresupplystarttime=res.data.centre!=null?res.data.centre.supplystarttime:'';
            this.centresupplyendtime=res.data.centre!=null?res.data.centre.supplyendtime:'';
            this.centregivestarttime=res.data.centre!=null?res.data.centre.givestarttime:'';
            this.centregiveendtime=res.data.centre!=null?res.data.centre.giveendtime:'';
            this.centreupperstarttime=res.data.centre!=null?res.data.centre.upperstarttime:'';
            this.centreupperendtime=res.data.centre!=null?res.data.centre.upperendtime:'';
            this.suppersupplystarttime=res.data.supper!=null?res.data.supper.supplystarttime:'';
            this.suppersupplyendtime=res.data.supper!=null?res.data.supper.supplyendtime:'';
            this.suppergivestarttime=res.data.supper!=null?res.data.supper.givestarttime:'';
            this.suppergiveendtime=res.data.supper!=null?res.data.supper.giveendtime:'';
            this.supperupperstarttime=res.data.supper!=null?res.data.supper.upperstarttime:'';
            this.supperupperendtime=res.data.supper!=null?res.data.supper.upperendtime:'';
            this.morningid=res.data.morning!=null?res.data.morning.id:'';
            this.centreid=res.data.centre!=null?res.data.centre.id:'';
            this.supperid=res.data.supper!=null?res.data.supper.id:'';
            this.morningmealintroduction=res.data.morning!=null?res.data.morning.mealintroduction:'';
            this.centremealintroduction=res.data.centre!=null?res.data.centre.mealintroduction:'';
            this.suppermealintroduction=res.data.supper!=null?res.data.supper.mealintroduction:'';
        })
    },
    // 保存修改
    savechanges(id,supplystarttime,supplyendtime,givestarttime,giveendtime,upperstarttime,upperendtime,mealintroduction,dictionary){
        this.$axios({
            url:'stRestaurantinformation.do/updateMeal',
            method:'post',
            data:{
                "id":id,
                "supplystarttime":supplystarttime,
                "supplyendtime":supplyendtime,
                "givestarttime":givestarttime,
                "giveendtime":giveendtime,
                "upperstarttime":upperstarttime,
                "upperendtime":upperendtime,
                "mealintroduction":mealintroduction,
                "dictionary":dictionary,
                "dishes":"1",
                "restaurant":this.rescode,
            }
        }).then(res=>{
            if(res.data=='保存成功'){
                this.$message.success('修改成功')
                window.setTimeout(function(){
                    location.reload()
                },1000)
            }
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
.reshead{
    color: white;
    margin-top:30px;
    margin-bottom: 140px;
    font-size: 12px;
}
.resintroduce{
    display: inline-block;
    color: white;
    font-size: 18px;
    font-weight: 700;
    margin-bottom: 10px;
    margin-right: 10px;
}
.resintroducet{
    display: inline-block;
    color: white;
    font-size: 18px;
    font-weight: 700;
    margin-bottom: 30px;
    margin-right: 10px;
}
.rescontent{
    margin-bottom: 30px;
}
.imgbox{
    width: 32%;
    height: 700px;
    float: left;
    margin-right: 20px;
    background-color: red;
    position: relative;
}
.imgbox:nth-child(3){
    margin-right: 0px;

}
.imgbox img{
    width: 100%;
    height: 100%;
}
.absotype{
    position: absolute;
    background:rgba(170, 170, 170, 0.8);
    left:0;
    bottom:0;
    width: 100%;
    height: 180px;
    line-height: 180px;
}
.enterabsotype{
    position: absolute;
    background:rgba(170, 170, 170, 0.8);
    left:0;
    bottom:0;
    width: 100%;
    height: 360px;
}
.iconbox{
    width: 100%;
    height:180px;
    line-height: 180px;

}
.iconbox img{
    width: 35%;
    vertical-align:middle;
    margin-left:20px;
    margin-right: 40px;
    box-sizing: border-box;
}
.icontext{
    display: inline-block;
    vertical-align:middle;
    font-size: 50px;
    color: white;
}
.periodinformation{
    color: white;
    font-size: 12px;
    padding: 4px 6px 4px 20px;
}
.resxx,.capxx:hover{
    cursor: pointer;
}
.capxx{
    display: inline-block;
    font-size: 16px;
    color: white;
}
.elinput{
    width: 90%;
    height: 30px;
}
.elinput /deep/.el-input__inner{
    height: 38px;
}

/deep/.el-icon-arrow-up{
    font-size: 12px;
}
.marbottime{
    margin-bottom: 5px;
}
/deep/.el-textarea{
    height: 54px;
}
/deep/.el-textarea__inner{
    color:rgb(255,255,255) ;
    font-size: 10px;
    height: 50px;
    padding:5px 15px ;
    vertical-align: middle;
    background-color:black;
    border:1px solid rgb(52,57,82);
    box-sizing: border-box;
    border-radius:0;
    margin-top:4px;
}
.bcbtn{
    margin:0px 0 0 10px;
    height: 30px;
    line-height: 30px;
    width: 60px;
    color:white;
    background-color: rgba(170,169,167,0.7);
    border: none;
}
/deep/.el-date-editor{
    width: 140px;
    height:30px;
    font-size: 14px;
    background-color:white;
}
/deep/.el-date-editor /deep/.el-input__inner{
    width: 100%;
    height: 100%;
    padding: 0 30px;
    color:black!important;

}
/deep/.el-input__icon{
    line-height: 30px;
    width: 25px;
}
.resnoshow{
    margin: 60px;
    color: goldenrod;
    font-size: 20px;
}
</style>
