<template>
    <div class='container'>
        <div class='meatitle'>保洁计划管理</div>
    <el-card class='opmainrescard'>
        <div class='opmainreshead'>运维餐厅</div>
        <el-tree :data="data" :highlight-current='true' :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
    </el-card>
    <el-card class='tablecard'>
            <div class='chartsmargin'>
                        <div class='clearbtn' @click='boxonechange'>保洁计划</div>
                        <div class='clearbtn' @click='boxtwochange'>检查计划</div>
            </div>

            <!-- 保洁计划页面 -->
            <div class='dayclear clearfix' v-show="boxoneshow">
                <div>
                    <el-form :model="queryform" ref="queryform" size="mini">
                    <el-row class="iptrow">
                    <el-col :span='5' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='5' class='iptcol'>
                        <el-form-item label="计划类型:">
                            <el-input  v-model="queryform.orderNum"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='5' class='iptcol'>
                        <el-form-item label="实施范围:">
                            <el-input  v-model="queryform.reservePerson"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='4' class='iptcol'>
                        <el-form-item label="周期:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    
                    <el-col :span='2'  class='iptcol'>
                        <el-form-item>
                            <button class='search' @click.prevent @click='pagelist'>查询</button>
                        </el-form-item>
                    </el-col>
                </el-row>
                </el-form>
                </div>
                <div class='addbtnfather'>
                        <button class='addbtn' @click.prevent @click='dialogaddVisible=true'>新建</button>
                        <button class='addbtn' @click.prevent @click='pagelist'>批量计划导入</button>
                        <button class='addbtn' @click.prevent @click='pagelist'>导出</button>
                </div>
            <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                    <el-table-column align="center"  min-width="6%" prop="ordernumber" label="序号"> </el-table-column>
                    <el-table-column align="center"  min-width="8%" prop="reservepersonnel" label="计划名称"></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="telephone" label="计划类型" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="restaurant" label="保洁时间" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="ordertime" label="实施范围" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="scheduled" label="周期" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="dictionary" label="保洁标准" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="dictionary" label="清洁用品" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="costtotal" label="备注" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="14%"  prop="dishId" label="操作" show-overflow-tooltip>
                        <template slot-scope="scope">
                            <div class='operate' @click="dialogamendVisible=true"><span>修改</span></div>
                            <div class='operate' @click="amend(scope.row.id)"><span>删除</span></div>
                        </template>
                    </el-table-column>
            </el-table>



        <!-- 保洁计划新建弹框 -->
            <el-dialog class='asessdialog'  title="新建保洁计划" :visible.sync="dialogaddVisible" @close='qxscoring'>
                        <el-form :model="queryform" ref="queryform" size="mini">
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划类型:">
                            <el-select v-model="queryform.orderDish">
                                <el-option v-for="item in jieguo" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划开始时间:">
                            <el-date-picker
                                v-model="value1"
                                class='inputdate'
                                type="datetime"
                                placeholder="选择日期时间">
                                </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <div class='clearfix spanbtnfather' >
                        <button class='spanbtn' @click.prevent @click='contentchange'>保洁内容</button>
                        <button class='spanbtn' @click.prevent @click='planchange'>保洁方案</button>
                </div>

                <div v-show='contentshow'>
                    <div class='vforbox' v-for='(item,index) in content' :key='index'>
                    <el-row>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="周期:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <div class='textareabox'>内容范围:<el-input type="textarea" v-model="queryform.details"></el-input></div>
                    <div class='textareabox'>保洁标准:<el-input type="textarea" v-model="queryform.details"></el-input></div>
                </div>
                <div class='imgbox clearfix'>
                    <img @click='addcontent' src="../../assets/u8497.png" alt="">
                </div>
                </div>
                
                <div v-show='planshow'>
                    <div class='vforbox' v-for='(item,index) in plan' :key='index'>
                    <el-row>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="人员:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <div class='textareabox'>备注:<el-input type="textarea" v-model="queryform.details"></el-input></div>
                </div>
                <div class='imgbox clearfix'>
                    <img @click='addplan' src="../../assets/u8497.png" alt="">
                </div>
                </div>

                </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button  class="footerbtn"  type="primary" @click='scoring'>确定</el-button>
                            <el-button  class="footerbtn"  type="primary" @click="qxscoring">取消</el-button>
                        </div>
            </el-dialog>



            <!-- 保洁计划修改弹框 -->
            <el-dialog class='asessdialog'  title="修改保洁计划" :visible.sync="dialogamendVisible" @close='qxscoring'>
                        <el-form :model="queryform" ref="queryform" size="mini">
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划类型:">
                            <el-select v-model="queryform.orderDish">
                                <el-option v-for="item in jieguo" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划开始时间:">
                            <el-date-picker
                                v-model="value1"
                                class='inputdate'
                                type="datetime"
                                placeholder="选择日期时间">
                                </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <div class='clearfix spanbtnfather' >
                        <button class='spanbtn' @click.prevent @click='contentchange'>保洁内容</button>
                        <button class='spanbtn' @click.prevent @click='planchange'>保洁方案</button>
                </div>

                <div v-show='contentshow'>
                    <div class='vforbox' v-for='(item,index) in content' :key='index'>
                    <el-row>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="周期:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <div class='textareabox'>内容范围:<el-input type="textarea" v-model="queryform.details"></el-input></div>
                    <div class='textareabox'>内容范围:<el-input type="textarea" v-model="queryform.details"></el-input></div>
                </div>
                <div class='imgbox clearfix'>
                    <img @click='addcontent' src="../../assets/u8497.png" alt="">
                </div>
                </div>
                
                <div v-show='planshow'>
                    <div class='vforbox' v-for='(item,index) in plan' :key='index'>
                    <el-row>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="人员:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <div class='textareabox'>备注:<el-input type="textarea" v-model="queryform.details"></el-input></div>
                </div>
                <div class='imgbox clearfix'>
                    <img @click='addplan' src="../../assets/u8497.png" alt="">
                </div>
                </div>

                </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button  class="footerbtn"  type="primary" @click='scoring'>确定</el-button>
                            <el-button  class="footerbtn"  type="primary" @click="qxscoring">取消</el-button>
                        </div>
            </el-dialog>
        <div style="text-align: right; margin-top: 10px;height:'15%'">
            <el-pagination
            style="height:100px"
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
    





                <!--检查计划页面  -->
                <div v-show='boxtwoshow'>
                    <div>
                    <el-form :model="queryform" ref="queryform" size="mini">
                    <el-row class="iptrow">
                    <el-col :span='5' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='5' class='iptcol'>
                        <el-form-item label="计划类型:">
                            <el-input  v-model="queryform.orderNum"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='5' class='iptcol'>
                        <el-form-item label="检查区域:">
                            <el-input  v-model="queryform.reservePerson"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='4' class='iptcol'>
                        <el-form-item label="检查时间:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    
                    <el-col :span='2'  class='iptcol'>
                        <el-form-item>
                            <button class='search' @click.prevent @click='pagelist'>查询</button>
                        </el-form-item>
                    </el-col>
                </el-row>
                </el-form>
                </div>
                <div class='addbtnfather'>
                        <button class='addbtn' @click.prevent @click='dialogaddtwoVisible=true'>新建</button>
                        <button class='addbtn' @click.prevent @click='pagelist'>批量计划导入</button>
                        <button class='addbtn' @click.prevent @click='pagelist'>导出</button>
                </div>
            <el-table stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                    <el-table-column align="center"  min-width="6%" prop="ordernumber" label="序号"> </el-table-column>
                    <el-table-column align="center"  min-width="8%" prop="reservepersonnel" label="计划名称"></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="telephone" label="计划类型" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="restaurant" label="检查区域" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="ordertime" label="检查时间" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="8%"  prop="scheduled" label="评分标准" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="8%"  prop="dictionary" label="检查人员" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="6%"  prop="costtotal" label="备注" show-overflow-tooltip></el-table-column>
                    <el-table-column align="center"  min-width="10%"  prop="dishId" label="操作" show-overflow-tooltip>
                        <template slot-scope="scope">
                            <div class='operate' @click="dialogamendtwoVisible=true"><span>修改</span></div>
                            <div class='operate' @click="amend(scope.row.id)"><span>删除</span></div>
                        </template>
                    </el-table-column>
            </el-table>



        <!-- 检查计划新建弹框 -->
            <el-dialog class='asessdialog'  title="新建检查计划" :visible.sync="dialogaddtwoVisible" @close='qxscoring'>
                        <el-form :model="queryform" ref="queryform" size="mini">
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划类型:">
                            <el-select v-model="queryform.orderDish">
                                <el-option v-for="item in jieguo" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="检查区域:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="检查时间:">
                            <el-date-picker
                                v-model="value1"
                                class='inputdate'
                                type="datetime"
                                placeholder="选择日期时间">
                                </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="检查人员:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrowtwo">
                        <el-col :span='24'>
                        <el-form-item label="评分标准:">
                            <el-input type="textarea" v-model="queryform.details"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrowtwo">
                        <el-col :span='24'>
                        <el-form-item label="备注:" class='formitem'>
                            <el-input type="textarea" v-model="queryform.details"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button  class="footerbtn"  type="primary" @click='scoring'>确定</el-button>
                            <el-button  class="footerbtn"  type="primary" @click="qxscoring">取消</el-button>
                        </div>
            </el-dialog>



            <!-- 检查计划修改弹框 -->
            <el-dialog class='asessdialog'  title="修改检查计划" :visible.sync="dialogamendtwoVisible" @close='qxscoring'>
                        <el-form :model="queryform" ref="queryform" size="mini">
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划名称:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="计划类型:">
                            <el-select v-model="queryform.orderDish">
                                <el-option v-for="item in jieguo" :key="item.parakey" :label="item.paravalue" :value="item.parakey">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="检查区域:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="检查时间:">
                            <el-date-picker
                                v-model="value1"
                                class='inputdate'
                                type="datetime"
                                placeholder="选择日期时间">
                                </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                    <el-row class="iptrow">
                    <el-col :span='12' class='iptcol'>
                        <el-form-item label="检查人员:">
                            <el-input  v-model="queryform.orderDish"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrowtwo">
                        <el-col :span='24'>
                        <el-form-item label="评分标准:">
                            <el-input type="textarea" v-model="queryform.details"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row class="iptrowtwo">
                        <el-col :span='24'>
                        <el-form-item label="备注:" class='formitem'>
                            <el-input type="textarea" v-model="queryform.details"></el-input>
                        </el-form-item>
                    </el-col>
                    </el-row>
                </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button  class="footerbtn"  type="primary" @click='scoring'>确定</el-button>
                            <el-button  class="footerbtn"  type="primary" @click="qxscoring">取消</el-button>
                        </div>
            </el-dialog>
        <div style="text-align: right; margin-top: 10px;height:'15%'">
            <el-pagination
            style="height:100px"
        @size-change="handleSizeChangetwo"
        @current-change="handleCurrentChangetwo"
        :current-page="pageIndextwo"
        :page-sizes="pageSizestwo"
        :page-size="pageSizetwo"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totaltwo">
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
      contentshow:true,
      planshow:false,
      boxoneshow:true,
      boxtwoshow:false,
      dialogaddVisible:false,
      dialogaddtwoVisible:false,
      dialogamendVisible:false,
      dialogamendtwoVisible:false,
      value1:'',
      content:[{}],
      plan:[{}],
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      pageIndextwo: 1,
      pageSizetwo:10,
      pageSizestwo:[ 10, 20 , 30, 50, 100],
      totaltwo:0,
      restaurantCode:'',//树状图id
      queryform: {
        orderDish: '',
        orderNum: '',
        reservePerson:'',
        scheduled:'',
        mealType:'',
        deduction:''
      },
      tableData: [{},{}],
      defaultProps: {
        children: 'subclass',
        label: 'restaurantName'
      }
    }
  },
  created(){
    // this.treelist()
    this.pagelist()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize=val
    //   this.pagelist()
      console.log(`每页 ${val} 条`)
    },
    handleSizeChangetwo (val) {
      this.pageSizetwo=val
    //   this.pagelist()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
    //   this.pagelist()
      console.log(`当前页: ${val}`)
    },
    handleCurrentChangetwo (val) {
      this.pageIndextwo=val
    //   this.pagelist()
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
    contentchange(){
        this.contentshow=true
        this.planshow=false
    },
    planchange(){
        this.contentshow=false
        this.planshow=true
    },
    boxonechange(){
        this.boxoneshow=true
        this.boxtwoshow=false
    },
    boxtwochange(){
        this.boxoneshow=false
        this.boxtwoshow=true
    },
    //+号按钮 
   addcontent(){
       this.content.push({})
   },
   addplan(){
       this.plan.push({})
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
    overflow: auto;
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
.iptrow{
   width:100%;
   height:56px;
   margin-bottom:0px;
   padding: 24px 0px 0px 60px;
   box-sizing: border-box;
}
.iptrowtwo{
   width:100%;
   margin-bottom:0px;
   padding:24px 0px 0px 60px;
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
    padding:0 15px;
    box-sizing: border-box;
    vertical-align: middle;
    background-color:black;
    border:1px solid #7D8399;
    box-sizing: border-box;
    border-radius:0;
    margin-top:0px;
    height:32px;
}
/deep/.el-form-item__label{
  font-size: 12px;
  line-height: 28px;
  padding-right:12px;
  color: rgb(255,255,255);
}
.search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 65px;
    height:31px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: #169BD5;
}
.search:hover{
  cursor: pointer;
}
/deep/.el-table{
    background-color:transparent;
    height: 600px!important;
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
    background-color:rgb(30,33,66);
}
/deep/.el-table tbody tr:nth-child(odd):hover>td {
    background-color:transparent!important;
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
/deep/.el-dialog__title{
  font-size: 18px;
  color:rgb(255,255,255);
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 20px 0 10px;
  margin-bottom: 20px;
  margin-top: 30px;
}
/deep/.el-dialog__footer{
  color:rgb(255,255,255);
  padding-top: 0px;
  background:transparent;
}

/deep/.el-dialog__headerbtn{
    top:20px;
    right:20px;
    height: 18px;
    line-height: 18px;
}
/deep/.el-dialog__headerbtn .el-dialog__close{
    color:rgb(255,255,255);
    font-weight: 900;
    font-size: 24px;
    margin-top:-8px;
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
.el-input{
  width: 170px;
}
.el-select{
    width: 190px;
}
.operate{
    display: inline-block;
    width: 50%;
    text-align: center;
}
.operate span:hover{
    cursor: pointer;
}
.asessdialog /deep/.el-dialog{
  background: #343645;
  padding:0 40px;
  box-sizing: border-box;
  border:1px solid rgba(255,255,255,.5);
  width: 900px;
}
.projectdetails{
    position: relative;
    width: 100%;
    margin:10px 0 30px 0;
    padding:30px 40px;
    border: 1px solid #444654;
    box-sizing: border-box;
    font-size: 12px;
    background-color:#303240;
}
.absolute{
    position: absolute;
    font-size: 18px;
    top:-10px;
    left:10px;
}
.detailsbox{
    display: inline-block;
    margin:10px 100px 20px 0;
}
.details{
    margin:0 0 20px 0;
}
.rateclass{
    display: inline-block;
    height:100%;
}
/deep/.el-rate__icon{
    font-size: 50px;
}
.define{
    width: 100px;
}
.asessdialog /deep/.el-dialog__header{
  padding:20px;
  box-sizing: border-box;
  text-align: left;
  font-size: 20px;
  background:transparent;
  border-bottom:1px solid rgba(255,255,255,.5);
}
.ratebox{
    display: inline-block;
    width: 50%;
    margin: 0 0 20px 0;
}
.chartsmargin{
    margin:20px 0 0 0;
}
.clearbtn{
    width: 147px;
    height: 38px;
    display: inline-block;
    color: white;
    font-size: 13px;
    text-align: center;
    line-height: 38px;
    background-color:#02A7F0;
    border: 1px solid #797979;
    box-sizing:border-box;
}
.clearbtn:hover{
    cursor: pointer;
}
.addbtn{
    color:rgb(255,255,255);
    margin: 0 10px 0 0;
    font-size: 10px;
    width: 65px;
    height:31px;
    border:1px solid rgb(52,57,77);
    border-radius: 5px;
    vertical-align: middle;
    background-color: #169BD5;
}
.addbtn:hover{
    cursor: pointer;
}
.addbtn:nth-child(2){
    width: 90px;
}
.addbtnfather{
    float:right;
    margin: 20px 0 20px 0;
}
.inputdate{
    width: 220px;
}
.inputdate /deep/.el-input__inner{
    width: 220px;
    padding: 0 30px;
}
.spanbtn{
    width:140px;
    height: 40px;
    line-height: 40px;
    color: white;
    font-size: 12px;
    text-align: center;
    border-radius: 5px;
    background-color: #169BD5;
    box-sizing: border-box;
    border: none;
    float: left;
}
.spanbtn:hover{
    cursor: pointer;
}
.spanbtnfather{
    margin-top:20px;
}
.vforbox{
    margin:20px 0 40px 0;
    padding:20px;
    box-sizing: border-box;
    padding-left:50px;
    border: 1px solid  #7D8399;
}
.textareabox{
    font-size: 12px;
    margin:10px 0;
}
/deep/.el-textarea{
    width: 550px;
    height: 100px;
    vertical-align: top;
}
/deep/.el-textarea__inner{
    width: 550px;
    color: white;
    font-size: 12px;
    height: 100px;
    background-color:black;
}
.footerbtn{
    width: 140px;
    height: 40px;
    background-color: #169AD4;
}
.imgbox img{
    width: 28px;
    height: 27px;
    float: right;
    margin-right:0;
}
.imgbox img:hover{
    cursor: pointer;
}
.formitem /deep/.el-form-item__label{
    width: 63px;
}
</style>
