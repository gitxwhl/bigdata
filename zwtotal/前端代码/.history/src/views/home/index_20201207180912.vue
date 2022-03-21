<template>
    <div class="header">
        <div class='head'>
<!--            <img src="../../assets/icon1.png" alt="">-->
            <span class='title'>国网河北电力智慧后勤平台—智慧食堂</span>
          <div style="float: right;margin-right: 80px">
            <span style="margin-right: 20px">
               <img src="../../assets/sousuo.png" alt="">
            </span>
            <span style="margin-right: 20px">
               <img src="../../assets/xiaoxi.png" alt="">
            </span>
            <span style="margin-right: 5px">
               <img src="../../assets/touxiang.png" alt="">
            </span>
            <span style="color: white;font-size: 14px">
               管理员
            </span>
          </div>
        </div>
        <div class='body'>
<!--            <el-input class='inputbox' prefix-icon="el-icon-search" placeholder="功能搜索"></el-input>-->
          <div class='inputbox'></div>
            <div class='flexbox'>
            <div class='flexson'>
                <img src="../../assets/caipin.png" alt="" @click='push1'>
                <div class='textalign' style="color: white">菜品管理</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/paicai.png" alt="" @click='push2'>
                <div class='textalign'style="color: white">排菜管理</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/xuancan.png" alt="" @click='push3'>
                <div class='textalign'style="color: white">计量选餐</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/yuding.png" alt="" @click='push4'>
                <div class='textalign'style="color: white">预定管理</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/caigou.png" alt="" @click='push5'>
                <div class='textalign'style="color: white">采购管理</div>
            </div>
            <div class='flexson'style="width: 180px;height: 180px">
                <img src="../../assets/shipin.png" alt="" @click='push6'>
                <div class='textalign'style="color: white">食品安全</div>
            </div>
            <div class='flexson' style="margin-left: 40px">
                <img src="../../assets/fenxi.png" alt=""  @click='push7'>
                <div class='textalign'style="color: white">分析报告</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/wangshang.png" alt=""  @click='push8'>
                <div class='textalign'style="color: white">网上超市</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/caiwu.png" alt="" @click='push9'>
                <div class='textalign'style="color: white">净菜柜</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/paicai.png" alt=""  @click='push10'>
                <div class='textalign'style="color: white">回收线</div>
            </div>
            <div class='flexson'>
                <img src="../../assets/jiankang.png" alt="" @click='push11'>
                <div class='textalign'style="color: white">健康管理</div>
            </div>
              <div class='flexson' style="width: 180px;height: 180px">
                <img src="../../assets/jiesuan.png" alt="" @click='push11'>
                <div class='textalign'style="color: white">结算管理</div>
              </div>
        </div>
        </div>
    </div>
</template>

<script>
export default {
    methods:{
        push1(){
            window.sessionStorage.setItem('key',1)
            this.$router.push('/canteen/dishesquery')
        },
        push2(){
            window.sessionStorage.setItem('key',2)
            this.$router.push('/canteen/onlinedishesmanagement')

        },
        push3(){
            window.sessionStorage.setItem('key',3)
            this.$router.push('/canteen/cupboardmanagement')

        },
        push4(){
            window.sessionStorage.setItem('key',4)
            this.$router.push('/canteen/foodcatemanage')

        },
        push5(){
            window.sessionStorage.setItem('key',5)
            this.$router.push('/canteen/measeldishes')

        },
        push6(){
            window.sessionStorage.setItem('key',6)
            this.$router.push('/canteen/mindmealarr')

        },
        push7(){
            window.sessionStorage.setItem('key',7)
            this.$router.push('/canteen/foodtrace')

        },
        push8(){
            window.sessionStorage.setItem('key',8)
            this.$router.push('/canteen/suppliermanagement')

        },
        push9(){
            window.sessionStorage.setItem('key',9)
            this.$router.push('/canteen/mealsettlementdetails')

        },
        push10(){
            window.sessionStorage.setItem('key',10)
            this.$router.push('/canteen/onlineshopping')

        },
        push11(){
            window.sessionStorage.setItem('key',11)
            this.$router.push('/canteen/healthmanagement')

        },
    }
}
</script>

<style lang="less" scoped>
.header{
  width: 100%;
  height: 100%;
  background: url(../../assets/u0.svg)no-repeat;
  /*background-size:100% 100% ;*/
  position:fixed;
  top: 0;
  left: 0;
  /*width:100%;*/
  /*height:100%;*/
  min-width: 1000px;
  z-index:-10;
  zoom: 1;
  background-color: #fff;
  /*background-repeat: no-repeat;*/
  background-size: cover;
  -webkit-background-size: cover;
  -o-background-size: cover;
  background-position: center 0;
}
 .head{
     width: 100%;
     height: 60px;
     line-height: 60px;
     /*background-color: #2A3B4B;*/
   background: #0F2B54;
     padding:0 10px;
     box-sizing: border-box;
 }
 .head img{
     width:24px;
     height: 24px;
     vertical-align: middle;
     margin:0 20px 0 0;
 }
 .title{
     color: #1A78C8;
     font-size: 18px;
   font-weight: bold;
 }
 .body{

     width: 980px;
     margin:0 auto;
     padding: 100px 0 10px 0;
     box-sizing: border-box;
 }
 .inputbox{
     /*width: 100%;*/
     /*height: 40px!important;*/
     /*border: 1px solid #E0E0E0;*/
     margin-bottom: 80px;
     border-radius: 4px;
 }
 /deep/.el-input__inner{
     padding-left: 30px;
     height: 40px!important;

 }
/deep/.el-input__icon{
    line-height: 34px;
    width: 25px;
}
.flexbox{
    width: 100%;
}
.flexson{
    display: inline-block;
    vertical-align: middle;
    text-align: center;
    font-size: 15px;
    margin: 0 40px 20px 0;
    width: 180px;
   height: 180px;
  background: #0F2B54;
  border-radius: 15px;
}
.flexson:nth-child(6n){
    display: inline-block;
    text-align: center;
    font-size: 15px;
    margin: 0 0 20px 0;
    width: 130px;
}
.flexson img{
  margin-top: 15px;
    width: 130px;
    height: 130px;
}
.flexson img:hover{
    cursor: pointer;
}
</style>
