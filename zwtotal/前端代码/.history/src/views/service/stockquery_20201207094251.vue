<template>
  <div class='container'>
    <div class='meatitle'>库存查询</div>
    <el-card class='opmainrescard'>
      <div class='opmainreshead'>部门</div>
      <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <el-row>
                <el-col :span='6.5' class='iptcol'>
                        <el-input class="input" v-model="goodName" placeholder="请输入物品名称进行检索"></el-input>
                </el-col>
                <el-col :span='13' class='iptcol'>
                     <button class='search' @click.prevent @click="pagelist">搜索</button>
                </el-col>
                <el-col :span='1.5' class='iptcol'>
                     <button class='search' @click.prevent @click="newwp">新建物品</button>
                </el-col>
                <el-col :span='3' class='iptcol'>
                     <button class='search' @click="$router.push('/service/buyplan')">采购计划</button>
                </el-col>
            </el-row>

            
            <div class='mainbox'>
                <div class='vforbox' v-for="(item,index) in list" :key="index">
                    <img :src='item.pictureurl' alt="">
                    <div class='boxfather'>
                        <div class='smallbox'>
                            <div class='boxtop'>{{item.usableamount}}</div>
                            <div class='boxbottom'>可用库存</div>
                        </div>
                        <div class='smallbox'>
                            <div class='boxtop'>{{item.instore}}</div>
                            <div class='boxbottom'>待上架</div>
                        </div>
                        <div class='smallbox'>
                            <div class='boxtop'>{{item.outstore}}</div>
                            <div class='boxbottom'>待出库</div>
                        </div>
                        <div class='smallbox'>
                            <div class='boxtop'>{{item.nowstore}}</div>
                            <div class='boxbottom'>当前库存</div>
                        </div>
                    </div>
                    <div class='goodsname clearfix'>
                        {{item.goodname}}
                        <div style='float:right;margin-right: 15px' class='el-icon-edit' @click="amend(item)"></div>

                    </div>
                    <div class='marginbottom'>
                            <div class='inblock'>评分:</div>
                            <div class='inblock'>状态:{{item.iswarn}}</div>
                            <div class='inblock'>预警数:{{item.expectcount}}</div>
                    </div>
                    <div class='marginbottom'>品牌:{{item.brand}}</div>
                    <div class='marginbottom'>编号:{{item.goodno}}</div>
                    <div class='marginbottom'> <div>规格:{{item.specs}}</div>
                    </div>
                    <div class='marginbottom'>平均单价:{{item.unitprice}}</div>
                    <!-- <div class='marginbottom'>总金额:{{item.allmoney}}</div> -->

                </div>
            </div>





             <el-dialog class="postdialog"  title="新建物品" :visible.sync="dialogpostVisible" @close='clear2'>
              <!-- <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)"> -->
              <div class='addimg'>
                <!-- <img :src='image' alt=""> -->
                <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">

                <div class='absotext'@click="leadingin">请上传物品图片</div>
                <div class='imgbox' v-for="item in img" :key="item.size">
                  <img style="width: 100%;height: 170px" :src="item" alt="">
                </div>
              </div>
              <div class='postinput'>
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                        品牌名称 : <el-input class='inputwidth'  v-model="addform.brand"></el-input>
                    </el-col>
                  </el-row>
                   <el-row class='iptrow'>
                    <el-col :span='24'>
                        物品名称 : <el-input class='inputwidth' v-model="addform.goodName"></el-input>
                    </el-col>
                  </el-row>
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                        物品类别 :
                        <template>
                          <el-select class='inputwidth' @click="categorylist" v-model="typeName" placeholder="请选择">
                            <el-option
                              v-for="item in options"
                              :key="item.id"
                              :label="item.text"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </template>
                    </el-col>
                  </el-row>


                   <!-- <el-row class='iptrow'>
                    <el-col :span='24'>
                        库存状态 : <el-input class='inputwidth' v-model="addform.telephone"></el-input>
                    </el-col>
                  </el-row> -->
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                        规格型号 : <el-input class='inputwidth' v-model="addform.specs"></el-input>
                    </el-col>
                  </el-row>
                  <el-row class='iptrow'>
                    <el-col :span='24'>
                        条形码 : <el-input class='inputwidth' v-model="addform.barCode"></el-input>
                    </el-col>
                  </el-row>

              </div>
               <el-row class='marginrow'>
                    <el-col :span='8'>
                        预警数 : <el-input class='inputsmallwidth' v-model="addform.expectcount"></el-input>
                    </el-col>
                    <el-col :span='8'>
                        单价 : <el-input class='inputsmallwidth' v-model="addform.unitPrice"></el-input> 元
                    </el-col>
                    <el-col :span='8'>
                        单位 : <el-input class='inputsmallwidth' v-model="addform.unit"></el-input>
                    </el-col>
                  </el-row>
                   <el-row class='marginrow'>
                    <el-col :span='24'>
                        仓库 : <el-input class='inputlongwidth' v-model="addform.storeNo"></el-input>
                    </el-col>
                  </el-row>

              <div slot="footer" class="dialog-footer">
                <el-button  class="search"  type="primary" @click="clear">清空</el-button>
                <el-button  class="search"  type="primary" @click="addgood">保存</el-button>
              </div>
            </el-dialog>

      <el-dialog class="postdialog"  title="物品修改" :visible.sync="dialogpostVisible2" >
        <div class='addimg' v-show="sc">
          <!-- <img :src='image' alt=""> -->
          <input style="display:none;" multiple="multiple" ref="articleImageFile" id="articleImageFile" name="excelFile" type="file" @change="importExcel($event)">
          <div class='absotext'@click="leadingin">请上传物品图片</div>

          <div class='imgbox' v-for="item in img" :key="item.size">
            <img style="width: 100%;height: 170px" :src="item" alt="">
          </div>
        </div>
        <div class='addimg' @click="sssss" v-show="zs">
<!--          <input style="display:none;" multiple="multiple" ref="articleImageFile2" id="articleImageFile2" name="excelFile" type="file" @change="importExcel2($event)">-->
            <img style="width: 100%;height: 170px" :src="amendDate.pictureurl" alt="">
        </div>
        <div class='postinput'>
          <el-row class='iptrow'>
            <el-col :span='24'>
              品牌名称 : <el-input class='inputwidth'  v-model="amendDate.brand"></el-input>
            </el-col>
          </el-row>
          <el-row class='iptrow'>
            <el-col :span='24'>
              物品名称 : <el-input class='inputwidth' v-model="amendDate.goodname"></el-input>
            </el-col>
          </el-row>
          <el-row class='iptrow'>
            <el-col :span='24'>
              物品类别 :
              <template>
                <el-select class='inputwidth' @click="categorylist" v-model="amendDate.parenttypeid" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id">
                  </el-option>
                </el-select>
              </template>
            </el-col>
          </el-row>

          <el-row class='iptrow'>
            <el-col :span='24'>
              规格型号 : <el-input class='inputwidth' v-model="amendDate.specs"></el-input>
            </el-col>
          </el-row>
          <el-row class='iptrow'>
            <el-col :span='24'>
              条形码 : <el-input class='inputwidth' v-model="amendDate.barcode"></el-input>
            </el-col>
          </el-row>

        </div>
        <el-row class='marginrow'>
          <el-col :span='8'>
            预警数 : <el-input class='inputsmallwidth' v-model="amendDate.expectcount"></el-input>
          </el-col>
          <el-col :span='8'>
            单价 : <el-input class='inputsmallwidth' v-model="amendDate.unitprice"></el-input> 元
          </el-col>
          <el-col :span='8'>
            单位 : <el-input class='inputsmallwidth' v-model="amendDate.unit"></el-input>
          </el-col>
        </el-row>
        <el-row class='marginrow'>
          <el-col :span='24'>
            仓库 : <el-input class='inputlongwidth' v-model="amendDate.storeno"></el-input>
          </el-col>
        </el-row>

        <div slot="footer" class="dialog-footer">
<!--          <el-button  class="search"  type="primary" @click="clear">清空</el-button>-->
          <el-button  class="search"  type="primary" @click="amendgood">修改</el-button>
        </div>
      </el-dialog>
            <div style="text-align: right; margin-top: 30px;height:'15%'">
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
        zs:true,
        sc:false,
        img:[],
        files:[],
        img2:'',
        files2:'',
        amendDate:[],
        goodName:'',
        dialogpostVisible:false,
        dialogpostVisible2:false,
        list:[],
        restaurantCode:'',//树状图id
        pageIndex: 1,
        pageSize:10,
        pageSizes:[ 10, 20 , 30, 50, 100],
        total:0,
        data: [],
        addform:{
          id:'',
          goodName:'',
          specs:'',
          brand:'',
          storeNo:'',
          unitPrice:'',
          unit:'',
          pictureurl:'',
          expectcount:'',
          barCode:''
        },
       
         options: [],
         typeName: '',
        defaultProps: {
          children: 'subclass',
          label: 'restaurantName'
        },
        ttt:'',
      }
    },
    created(){
      this.pagelist()
      /**
       * 物品类别
       */
      this.categorylist()
    },
    mounted(){
    },
    methods: {
      handleSizeChange (val) {
        this.pageSize = val
        this.pagelist()
      },
      handleCurrentChange (val) {
        this.pageIndex=val
        this.pagelist()
      },
      handleNodeClick (data) {

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
          // console.log(res)
          this.data=res.data
        })
      },
       /**
       * 物品类别
       */
      categorylist(){
            this.$office({
          url:'Tstock.do/getTypeId',
          method:'get',
        }).then(res=>{
          // console.log(res)
          this.options=res.data.goodType
        })

        // this.$axios({
        //   url:'Tstock.do/findgoodTypeTree',
        //   method:'POST'
        // }).then(res=>{
        //   console.log(res)
        //   // this.data=res.data
        // })
      },
      // 库存查询
      pagelist(){
        this.$office({
          url:'Tstock.do/findTstock',
          method:'post',
          data:{
            "pageNum": this.pageIndex,
            "pageSize":this.pageSize,
            "goodName":this.goodName
          }
        }).then(res=>{
          // console.log(res)
          this.total=res.data.totalRecord
          this.list=res.data.list
          // for (var i = 0; i < this.list.length; i++) {
          //   console.log( this.list[i].pictureurl);
          // }

        })
      },

      // 清空按钮
      clear(){
        this.addform={
          goodName:'',
          specs:'',
          brand:'',
          storeNo:'',
          unitPrice:'',
          unit:'',
          pictureurl:'',
          expectcount:'',
          barCode:'',

        }
          // this.img=[]
      },
      newwp(){
        this.dialogpostVisible=true
        this.img=[]
      },

      clear2(){
        this.addform={
          goodName:'',
          specs:'',
          brand:'',
          storeNo:'',
          unitPrice:'',
          unit:'',
          pictureurl:'',
          expectcount:'',
          barCode:'',
        },

        this.typeName=''
      },


      // 单击上传图片按钮
      importExcel(e){
        debugger
        for(var i=0;i<this.$refs.articleImageFile.files.length;i++){
          this.img.push(URL.createObjectURL(this.$refs.articleImageFile.files[i]))
          this.files.push(this.$refs.articleImageFile.files[i])
          var formData = new FormData();
          formData.append("file",this.$refs.articleImageFile.files[0]);
          // console.log(this.files,)
          for(var i=0;i<this.files.length;i++){
            var reader = new FileReader();
            reader.readAsDataURL(this.files[i]);
            // console.log(this.files[i])
            // var ttt=[];
            // var that=this
            reader.onload = function (e) {
              this.ttt=(e.target.result);

            }
          }
        }
      },

      // 单击修改上传图片按钮
      importExcel2(e){
          this.amendDate.pictureurl=URL.createObjectURL(this.$refs.articleImageFile2.files)
          this.files2=this.$refs.articleImageFile2.files
          var formData = new FormData();
          formData.append("file",this.$refs.articleImageFile2.files);
          // console.log(formData)

            var reader = new FileReader();
            reader.readAsDataURL(this.files2);
            reader.onload = function (e) {
              this.ttt=(e.target.result);
            }
      },



      // 新建物品保存
      addgood(){
        if(this.addform.goodName==''||this.addform.specs==''||this.addform.brand==''||this.addform.storeNo==''
          ||this.addform.unitPrice==''||this.addform.unit==''||this.addform.expectcount==''||this.addform.barCode==''){
            this.$message('请补全信息')
            return
          }
          
        for(var i=0;i<this.files.length;i++){
          var reader = new FileReader();
          reader.readAsDataURL(this.files[i]);
          // var ttt='';
          var that=this
            reader.onload = function (e) {
             
            that.ttt=(e.target.result);
              // console.log(that.ttt)
              that.$office({
                url:'Tstock.do/addGood',
                method:'post',
                data:
                  {
                    "goodName": that.addform.goodName,		///物品名称
                    "specs": that.addform.specs,			//规格型号
                    "brand": that.addform.brand,			//品牌名称
                    "storeNo":that.addform.storeNo,			//仓库
                    "unitPrice": that.addform.unitPrice,			//单价
                    "unit": that.addform.unit,			//单位
                    "pictureurl":that.ttt,		//图片base64
                    "expectcount":that.addform.expectcount,		//预警数
                    "barCode": that.addform.barCode,	//条形码
                    "parenttypeId":that.typeName		//物品类别，传下拉框对应id
                  }
              }).then(res=>{
                if(res.data=='新建成功'){
                  that.$message.success("新建成功")
                  that.dialogpostVisible=false
                  that.pagelist()
                 // that.img=[]
                  that.addform={
                    goodName:'',
                    specs:'',
                    brand:'',
                    storeNo:'',
                    unitPrice:'',
                    unit:'',
                    pictureurl:'',
                    expectcount:'',
                    barCode:'',

                  }
                    }else{
                      that.$message.error('新建失败')
                    }
                    that.dialogpostVisible=false

              })
          }


        }
      },


      // 上传图片按钮
      leadingin(){
        this.$refs.articleImageFile.click()
      },
      sssss(){
        this.zs=false,
          this.sc=true
      },
      leadingin2(){
        this.$refs.articleImageFile2.click()

      },
      amend(list){
        this.dialogpostVisible2=true
        this.amendDate=list
        console.log(this.amendDate)

      },
      // amendgood(){
      //   for(var i=0;i<this.files.length;i++) {
      //     var reader = new FileReader();
      //     var that=this
      //     reader.readAsDataURL(this.files[i]);
      //     reader.onload = function (e) {
      //       that.ttt = (e.target.result);
      //       // console.log(this.ttt)
      //       that.$office({
      //         url: 'Tstock.do/updateGood',
      //         method: 'post',
      //         data: {
      //
      //           "id": that.amendDate.id,			//物品id
      //           "goodName": that.amendDate.goodname,		//物品名称
      //           "specs": that.amendDate.specs,			//规格型号
      //           "brand": that.amendDate.brand,				//品牌名称
      //           "storeNo": that.amendDate.storeno,				//仓库
      //           "unitPrice": that.amendDate.unitprice,				//单价
      //           "unit": that.amendDate.unit,			//单位
      //           "pictureurl": that.ttt,				//图片base64
      //           "expectcount": that.amendDate.expectcount,			//预警数
      //           "barCode": that.amendDate.barcode,		//条形码
      //           "parenttypeId": that.amendDate.parenttypeid,			//物品类别，传下拉框对应id
      //
      //         }
      //       }).then(res => {
      //         if (res.data.message == '修改成功') {
      //
      //           that.dialogpostVisible2 = false
      //           that.$message.success("修改成功")
      //           location.reload();
      //           that.pagelist()
      //         } else {
      //           that.$message.error('修改失败')
      //         }
      //         that.dialogpostVisible2 = false
      //
      //       })
      //     }
      //   }
      //
      //   // this.$office({
      //   //   url: 'Tstock.do/updateGood',
      //   //   method: 'post',
      //   //   data: {
      //   //
      //   //     "id": this.amendDate.id,			//物品id
      //   //     "goodName": this.amendDate.goodname,		//物品名称
      //   //     "specs": this.amendDate.specs,			//规格型号
      //   //     "brand": this.amendDate.brand,				//品牌名称
      //   //     "storeNo": this.amendDate.storeno,				//仓库
      //   //     "unitPrice": this.amendDate.unitprice,				//单价
      //   //     "unit": this.amendDate.unit,			//单位
      //   //     "pictureurl": this.ttt,				//图片base64
      //   //     "expectcount": this.amendDate.expectcount,			//预警数
      //   //     "barCode": this.amendDate.barcode,		//条形码
      //   //     "parenttypeId": this.amendDate.parenttypeid,			//物品类别，传下拉框对应id
      //   //
      //   //   }
      //   // }).then(res => {
      //   //   if (res.data.message == '修改成功') {
      //   //     that.$message.success("修改成功")
      //   //     // this.pagelist()
      //   //   } else {
      //   //     that.$message.error('修改失败')
      //   //   }
      //   //   this.dialogpostVisible2 = false
      //   //
      //   // })
      // },
      amendgood(){

        if(this.files.length==0){
          this.$office({
            url:'Tstock.do/updateGood',
            method:'post',
            data:{
              "id": this.amendDate.id,			//物品id
              "goodName": this.amendDate.goodname,		//物品名称
              "specs": this.amendDate.specs,			//规格型号
              "brand": this.amendDate.brand,				//品牌名称
              "storeNo": this.amendDate.storeno,				//仓库
              "unitPrice": this.amendDate.unitprice,				//单价
              "unit": this.amendDate.unit,			//单位
              "pictureurl":this.amendDate.pictureurl,				//图片base64
              "expectcount": this.amendDate.expectcount,			//预警数
              "barCode": this.amendDate.barcode,		//条形码
              "parenttypeId": this.amendDate.parenttypeid,			//物品类别，传下拉框对应id
            }
          }).then(res=>{
            if(res.data.message == '修改成功'){
              this.$message.success("修改成功")
              // window.setTimeout(function(){
              //   location.reload()
              // },1500)
              this.pagelist()
              this.dialogpostVisible2 = false
            }
          })
        }else {
          for(var i=0;i<this.files.length;i++){
            var reader = new FileReader();
            reader.readAsDataURL(this.files[i]);
            // console.log(this.files[i])
            // var ttt=[];
            var that=this
            reader.onload = function (e) {
              that.ttt=(e.target.result);
              // console.log(ttt)
                // console.log(13)

                that.$office({
                  url:'Tstock.do/updateGood',
                  method:'post',
                  data:{
                    "id": that.amendDate.id,			//物品id
                    "goodName": that.amendDate.goodname,		//物品名称
                    "specs": that.amendDate.specs,			//规格型号
                    "brand": that.amendDate.brand,				//品牌名称
                    "storeNo": that.amendDate.storeno,				//仓库
                    "unitPrice": that.amendDate.unitprice,				//单价
                    "unit": that.amendDate.unit,			//单位
                    "pictureurl": that.ttt,				//图片base64
                    "expectcount": that.amendDate.expectcount,			//预警数
                    "barCode": that.amendDate.barcode,		//条形码
                    "parenttypeId": that.amendDate.parenttypeid,			//物品类别，传下拉框对应id
                  }
                }).then(res=>{
                  if(res.data.message == '修改成功'){
                    that.$message.success("修改成功")
                    window.setTimeout(function(){
                      location.reload()
                    },1500)
                    that.pagelist()
                    that.dialogpostVisible2 = false
                  }
                })

            }
          }
        }
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
    overflow-y: auto;
    background-color:rgba(1,5,22,.4);
    padding:0 15px;
    box-sizing: border-box;
    border-radius: 0;
    border:none;
    border-top: 1px solid rgb(40,88,121);
    width:83%;
    /*height: 860px;*/
    float: right;
  }
  /deep/.number,/deep/.btn-prev,/deep/.btn-next{
    background: transparent;
    color: white;
  }
  /deep/.el-pagination button:disabled{
    background: transparent;
  }
  .el-pagination /deep/.el-input__inner{
    padding:0px;
  }
  .queryform{
    width:100%;
    margin-top:0px;
    margin-bottom:0px;
  }
  .iptrow{
    width:100%;
    height:46px;
    margin-bottom:0px;
    padding: 0 0px 14px 20px;
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
    padding: 0 30px;
  }

  /deep/.el-form-item__label{
    font-size: 12px;
    line-height: 28px;
    padding-right:12px ;
    color: rgb(255,255,255);
  }
  .search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .longsearch{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 94px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
  }
  .search,.longsearch:hover{
    cursor: pointer;
  }
  /deep/.el-table{
    background-color:transparent;
    height: 720px!important;
  }
   /deep/.el-table__body-wrapper{
    height: 680px!important;
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
  /deep/.el-tree-node:focus > .el-tree-node__content {
    background-color: transparent !important;
  }
  /deep/.el-tree-node__content{
    background:transparent;
  }
  /deep/.el-range-input{
    background-color:transparent;
  }
  /deep/.el-range-separator{
    color:white;
  }
  button:hover{
    background:url(../../assets/zy2.png) no-repeat;
    background-size:100% 100%;
  }
  /deep/.el-date-editor{
    width: 200px;
  }
  /deep/.el-range__icon{
    line-height: 20px;
    margin-left: -5px;
  }
  /deep/.el-input__icon{
    width: 25px;
  }
  /deep/.el-range-input{
    font-size: 12px;
  }
  /deep/.el-range-separator{
    font-size: 12px;
    line-height: 28px;
  }
  /deep/.el-input__icon{
    line-height: 28px;
    width:25px;
  }
  /deep/.el-input__suffix{
    right: 5px;
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
  margin-bottom:20px;
}
/deep/.el-dialog__body{
  font-size: 12px;
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 10px;
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
.input{
  width: 400px;
}
.mainbox{
    color:white;
    font-size: 12px;
    width: 100%;
    margin: 10px 0 10px 0;
}
.vforbox{
    width:290px;
    display: inline-block;
    vertical-align: top;
    margin: 0 15px 10px 0;
}
.vforbox img{
    width: 289px;
    height: 225px;
}
.boxfather{
    margin: 2px 0 5px 0;
    text-align: center;
}
.smallbox{
    background-color:#010A2D;
    border: 1px solid #012863;
    box-sizing: border-box;
    display: inline-block;
    width:70px;
}
.boxtop{
    line-height: 25px;
    text-align: center;
    width: 100%;
    height: 25px;
}
.boxbottom{
    line-height: 25px;
    text-align: center;
    width: 100%;
    height: 25px;
}
.goodsname{
    font-size: 18px;
    margin-bottom: 5px;
}
.buttonbox{
    width: 100%;
    margin-bottom: 5px;
}
.inblock{
    vertical-align: bottom;
    display: inline-block;
    margin: 0 10px 0 0 ;
}
.marginbottom{
    margin: 0 0 15px 0;
}
.iconimg{
    width: 28px;
    height: 28px;
    background: url(../../assets/u675.svg)no-repeat;
    background-size: 100% 100%;
}
 .postdialog /deep/.el-dialog{
    width: 750px;
  }
  .postdialog  /deep/.el-dialog__body{
    padding: 10px 10px 0 40px;
  }
  .addimg img{

    width: 60%;
    height: 100%;
  }
  .addimg{
    width: 160px;
    height: 170px;
    background: url(../../assets/u3519.svg)no-repeat;
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
    color: black;
    z-index: 1000;
    cursor: pointer;
  }

  .postinput{
  display: inline-block;
  vertical-align: top;
}
.marginrow{
  margin: 0 0 15px 0;
}
.inputwidth,.inputwidth /deep/.el-input__inner{
  width: 320px!important;
}
.inputsmallwidth{
  width: 160px;
}
.inputlongwidth{
  width: 625px;
}
 .addbtn{
    width: 100px;
    height: 40px;
    color:rgb(255,255,255) ;
    font-size: 14px;
    padding: 12px 20px;
    background-color: rgb(1,7,34);
  }
</style>

