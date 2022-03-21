<template>
  <div class='container'>
    <div class='meatitle'>历史维护记录查询</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>运维餐厅</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
        <div class='information'>
            <div class='absolute'>基本信息</div>
            <div>
                <div class='mationbox'>员工编号:</div>
                <div class='mationbox'>姓名:</div>
                <div class='mationbox'>性别:</div>
                <div class='mationbox'>出生年月:</div>
            </div>
            <div>
                <div class='mationbox'>身体指标:</div>
                <div class='mationbox'>体检指标1:</div>
                <div class='mationbox'>体检指标2:</div>
                <div class='mationbox'>体检指标3:</div>
                <div class='mationbox'>体检指标4:</div>
                <div class='mationbox'>体检指标5:</div>
            </div>
        </div>
        <div class='information'>
            <div class='absolute'>用餐建议</div>
            <div class='respropleft'>
                <div class='resprop'>xxxxxxxxxxxxxxxxxxx</div>
                <div>建议每日总能量摄入量xxx，脂肪xx，蛋白质xx,</div>
                <div>早餐:</div>
                <div>午餐:</div>
                <div class='resprop'>晚餐:</div>
                <div>主食:</div>
                <div>蔬菜:</div>
                <div>肉类:</div>
                <div>水果:</div>
                <div>蛋奶:</div>
                <div>其它:</div>
            </div>
            <div class='respropright'>
                根据膳食宝塔建议的各类食物摄入量，每人每天应该吃食物重量如下：
                <div>谷类食物（淀粉）250g-400g,</div>
                <div>蔬菜300g-500g,</div>
                <div>水果200g-400g,</div>
                <div>鱼虾类 50g-100g,</div>
                <div>畜、禽肉50g-75g,</div>
                <div>蛋类 25g-50g,</div>
                <div>相当于鲜奶 300g 的奶类及奶制品,</div>
                <div>相当于干豆 30g-50g的大豆及制品,</div>
                <div>烹调油不超过25g或 30g,</div>
                <div>食盐不超过6g.</div>
            </div>
        </div>

        <div class='information'>
            <div class='absolute'>健康分析</div>
                <div class='foodanalyse'>当日菜品分析:</div>
                <div  class='resprop'>
                    <div>建议食谱：</div>
                    <div>早餐:</div>
                    <div>午餐:</div>
                    <div>晚餐:</div>
                </div>
                
                <div  class='resprop'>
                    <div>就餐菜品分析:</div>
                    <div>早餐:</div>
                    <div>午餐:</div>
                    <div>晚餐:</div>
                </div>
                <div class='foodanalyse'>
                    <div>建议菜品选择:</div>
                    <div>主食:</div>
                    <div>凉菜:</div>
                    <div>热菜:</div>
                    <div>水果:</div>
                    <div>汤类:</div>
                </div>
                
        </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        restaurantCode:'',//树状图id
        data: [],
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        }
      }
    },
    created(){
      this.treelist()
    },
    methods: {
      handleNodeClick (data) {
        this.restaurantCode=data.id
        this.list()
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

  /deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
  }
  /deep/.el-tree-node__content{
    background:transparent;
  }
 .information{
     font-size: 12px;
     color: white;
     border: 1px dashed #797979;
     margin-top:20px;
     padding:15px 0 0 15px;
     box-sizing: border-box;
     position: relative;
 }
 .absolute{
     position: absolute;
     top:-10px;
     left:10px;
 }
 .mationbox{
     display: inline-block;
     margin: 0 100px 20px 0;
 }
 .resprop{
     margin-bottom: 15px;
 }
 .respropleft{
     width: 60%;
     display: inline-block;
 }
 .respropright{
     width: 30%;
     display: inline-block;
     vertical-align: top;
 }
 .foodanalyse{
     margin-bottom: 80px;
 }
</style>

