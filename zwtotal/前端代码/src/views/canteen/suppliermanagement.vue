<template>
    <div class='container'>
        <div class='managetitle'>供应商管理</div>
        <el-card class='managecard'>
                <el-row class="iptrow" :gutter="0">
            <el-form :model="queryform" ref="queryform"  size="mini" class='queryform' >
                    <el-col :span='4' class='iptcol'>
                        <el-form-item label="供应商名称:">
                           <el-input  v-model="queryform.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='3' class='number'>
                        <el-form-item label="供应商编号:">
                        <el-input v-model="queryform.number"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span='4' class='points'>
                          <el-form-item label="统一社会信用代码:">
                            <el-input style="width: 140px" v-model="queryform.min"></el-input> <div class='rod'></div>
                          </el-form-item>
                        </el-col>
                        <el-col :span='4' class='points'>
                          <el-form-item label="采购食材数量:">
                            <el-input style="width: 140px" v-model="queryform.max"></el-input>
                          </el-form-item>
                        </el-col>
            </el-form>
                    <el-col :span='4' class='iptcol'>
                            <button class='search' @click.prevent @click='querylist'>查询</button>
                      <button style="margin-left: 10px" class='search' @click.prevent @click="reset">重置</button>
                      <button style="margin-left: 10px" class='search' @click.prevent @click="exportout" >导出</button>
                    </el-col>
                    <el-col :span='2' class='iptcol'>
                            <button class='increase' @click.prevent @click="dialogaddVisible = true">新增准入供应商</button>

                            <!-- 新增准入供应商弹框内容 -->
                            <el-dialog class="adddialog" style="margin-top:-50px;" title="新增准入供应商" :visible.sync="dialogaddVisible" >
                                <el-form :model="addition" ref="queryform" size="mini" class='addform'>
                                <div>
                                   <div class="titletext">基本信息</div>
                                    <el-row class="iptrow">
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item label="供应商名称:">
                                                <el-input v-model="addition.equipmentName"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="供应商编号:">
                                                <el-input v-model="addition.equipmentCode"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="统一社会信用代码:">
                                                <el-input v-model="addition.creditcode"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
<!--                                            <el-form-item  label="成立时间:">-->
<!--                                                <el-input v-model="addition.foundingTime"></el-input>-->
<!--                                            </el-form-item>-->
                                          <el-form-item class="timeform" label="成立时间:">
                                            <el-date-picker
                                              v-model="addition.foundingTime"
                                              value-format="yyyy-MM-dd"
                                              class="timeinput"
                                              type="date"
                                              placeholder="请选择日期">
                                            </el-date-picker>
                                          </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row class="iptrow">
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item label="注册资本(万元):">
                                                <el-input v-model="addition.registeredassets"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="企业地址:">
                                                <el-input v-model="addition.BusinessAddress"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="营业执照有效期:">
                                                <el-input v-model="addition.businessLicense"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="法人代表:">
                                                <el-input v-model="addition.representatives"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row class="iptrow">
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item label="法人电话:">
                                                <el-input v-model="addition.legalpersonphone"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="法人身份证号:">
                                                <el-input v-model="addition.IDnumber"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
<!--                                            <el-form-item  label="开始时间:">-->
<!--                                                <el-input v-model="addition.startime "></el-input>-->
<!--                                            </el-form-item>-->
                                          <el-form-item class="timeform" label="开始时间:">
                                            <el-date-picker
                                              v-model="addition.startime"
                                              value-format="yyyy-MM-dd"
                                              class="timeinput"
                                              type="date"
                                              placeholder="请选择日期">
                                            </el-date-picker>
                                          </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
<!--                                            <el-form-item  label="解除时间:">-->
<!--                                                <el-input v-model="addition.LiftTime"></el-input>-->
<!--                                            </el-form-item>-->
                                          <el-form-item class="timeform" label="解除时间:">
                                            <el-date-picker
                                              v-model="addition.LiftTime"
                                              value-format="yyyy-MM-dd"
                                              class="timeinput"
                                              type="date"
                                              placeholder="请选择日期">
                                            </el-date-picker>
                                          </el-form-item>
                                        </el-col>
                                    </el-row>
                            </div>
                            <div>
                                <div class="titletext">资质文件</div>
                                <div class="imgboxone">
                                    <img src="../../assets/u7713.svg" alt="">
                                </div>
                                <div class="imgboxtwo">
                                    <img src="../../assets/u7714.svg" alt="">
                                </div>
                                <div class="imgboxtwo">
                                    <img src="../../assets/u7714.svg" alt="">
                                </div>

                                <div class="imgboxthree">
                                    <img src="../../assets/u7715.svg" alt="">
                                </div>
                            </div>
                            <div>
                                <div class="titletext">资格证明</div>
                                <el-table  stripe ref="multipleTable" :data="tableData" :cell-style="{padding:'0',
                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                height="300px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                                    <el-table-column align="center"  min-width="10%" prop="meteq" label="项目法人"> </el-table-column>
                                    <el-table-column align="center"  min-width="25%" prop="verdish" label="项目名称"></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="restaurant" label="工程规模" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="15%"  prop="mount" label="服务范围" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="15%"  prop="weight" label="服务期间" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="weight" label="操作" show-overflow-tooltip></el-table-column>
                                </el-table>
                            </div>
                            <div class='clearfix'>
<!--                                <div class="titletext">评审结果</div>-->
<!--                                <div style="float:left;">-->
<!--                                    <el-form-item>-->
<!--                                     <el-radio-group v-model="addition.result">-->
<!--                                        <el-radio label="合格" style="margin-bottom:20px;color:white;"></el-radio><br>-->
<!--                                        <el-radio label="不合格" style="margin-bottom:20px;color:white;"></el-radio>-->
<!--                                    </el-radio-group>-->
<!--                                    </el-form-item>-->
<!--                                </div>-->
                                <div style="float:left;width:90%;">
                                    <el-row>
                                        <el-col :span='24'>
                                            <el-form-item  label="说明:">
                                            <el-input class='dltextarea'  type="textarea" v-model="addition.remark"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </div>
                            </div>
                        </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:0px;" class="search"  type="primary" >添加</el-button>
                              </div>
                        </el-dialog>


                    </el-col>
                    <el-col :span='2' class='iptcol'>
<!--                            <button class='supply' @click.prevent @click="dialoggysVisible = true">供应商遴选</button>-->



                    <!--供应商遴选弹框  -->
                    <el-dialog class="adddialog" style="margin-top:-50px;" title="供应商遴选" :visible.sync="dialoggysVisible" >
                                <el-form :model="chooseform" ref="chooseform" size="mini" class='addform'>
                                <div>
                                   <div class="titletext">筛选条件</div>
                                    <el-row class="iptrow">
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item label="供应商名称:">
                                                <el-input v-model="chooseform.name"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="供应商编号:">
                                                <el-input v-model="chooseform.number"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="企业地址:">
                                                <el-input v-model="chooseform.address"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="成立时间:">
                                                <el-input v-model="chooseform.establishTime"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row class="iptrow">
                                        <el-col :span='4' class='mimitcol'>
                                            <el-form-item label="注册资本(万元):">
                                                <el-input v-model="chooseform.startMoney"></el-input> <div class='rod'>-</div>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='2' class='mimitcol'>
                                            <el-form-item>
                                                <el-input v-model="chooseform.endMoney"></el-input>
                                            </el-form-item>
                                        </el-col>

                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="上年度是否被惩罚:">
                                                <el-select v-model="chooseform.lastPenalty">
                                                <el-option v-for="item in selectlast" :key="item.id" :label="item.text" :value="item.id">
                                            </el-option>
                                        </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='6' class='iptcol'>
                                            <el-form-item  label="本年度是否被惩罚:">
                                                <el-select v-model="chooseform.thisPenalty">
                                    <el-option v-for="item in selectthis" :key="item.id" :label="item.text" :value="item.id">
                                  </el-option>
                                    </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='4' class='mimitcol'>
                                            <el-form-item label="上年度评价积分:">
                                                <el-input v-model="chooseform.startScore"></el-input> <div class='rod'>-</div>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span='2' class='mimitcol'>
                                            <el-form-item>
                                                <el-input v-model="chooseform.endScore"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row class="iptrow">
                                        <el-col :span='2' class='iptcol' :offset="22">
                                            <button class='supply' @click.prevent @click="getChooseList">查询</button>
                                        </el-col>
                                    </el-row>
                            </div>
                            <div>
                                <div class="titletext">符合条件的供应商</div>
                                <el-table  stripe ref="multipleTable" :data="fhtableData" :cell-style="{padding:'0',
                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                height="700px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                                    <el-table-column align="center"  min-width="25%" prop="name" label="供应商名称"> </el-table-column>
                                    <el-table-column align="center"  min-width="10%" prop="representative" label="法人代表"></el-table-column>
                                    <el-table-column align="center"  min-width="20%"  prop="code" label="统一社会信用代码" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="access" label="准入状态" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="15%"  prop="score" label="上年度评价积分" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%" label="操作" show-overflow-tooltip>
                                        <template slot-scope="scope">
                                            <a href="javascript:;" style='color:white;' @click="choose(scope.row.id,1)">添加备选</a>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                            <div>
                                <div class="titletext">备选供应商</div>
                                <el-table  stripe ref="multipleTable" :data="bxtableData" :cell-style="{padding:'0',
                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                height="700px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                                    <el-table-column align="center"  min-width="25%" prop="name" label="供应商名称"> </el-table-column>
                                    <el-table-column align="center"  min-width="10%" prop="representative" label="法人代表"></el-table-column>
                                    <el-table-column align="center"  min-width="20%"  prop="code" label="统一社会信用代码" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="access" label="准入状态" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="15%"  prop="score" label="上年度评价积分" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%" label="操作" show-overflow-tooltip>
                                        <template slot-scope="scope">
                                            <a href="javascript:;" style='color:white;' @click="choose(scope.row.id,2)">选定</a>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                            <div>
                                <div class="titletext">选定供应商</div>
                                <el-table  stripe ref="multipleTable" :data="xdtableData" :cell-style="{padding:'0',
                                color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                height="700px" size="small"  style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
                                    <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                                    <el-table-column align="center"  min-width="25%" prop="name" label="供应商名称"> </el-table-column>
                                    <el-table-column align="center"  min-width="10%" prop="representative" label="法人代表"></el-table-column>
                                    <el-table-column align="center"  min-width="20%"  prop="code" label="统一社会信用代码" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="access" label="准入状态" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="15%"  prop="score" label="上年度评价积分" show-overflow-tooltip></el-table-column>
                                    <el-table-column align="center"  min-width="10%"  prop="weight" label="关联采购" show-overflow-tooltip></el-table-column>
                                </el-table>
                            </div>
                        </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button style="margin-top:0px;" class="search"  type="primary">确定</el-button>
                              </div>
                        </el-dialog>
                    </el-col>
                </el-row>
                <el-table stripe ref="multipleTable" class='table' :data="tableData" :cell-style="{padding:'0',
            color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
            height="700px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                <el-table-column align="center"  min-width="5%" type="selection" width="55"> </el-table-column>
                <el-table-column align="center"  min-width="12%" prop="name" label="供应商名称" show-overflow-tooltip> </el-table-column>
                <el-table-column align="center"  min-width="7%" prop="number" label="供应商编号" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="13%"  prop="code" label="统一社会信用代码" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="establishTime" label="成立时间" show-overflow-tooltip></el-table-column>
<!--                <el-table-column align="center"  min-width="8%"  prop="registeredCapital" label="注册资本(万元)" show-overflow-tooltip></el-table-column>-->
                <el-table-column align="center"  min-width="15%"  prop="address" label="企业地址" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="10%"  prop="businessvalidity" label="营业执照有效期" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="7%"  prop="representative" label="法人代表" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="phone" label="法人电话" show-overflow-tooltip></el-table-column>
                <el-table-column align="center"  min-width="12%"  prop="idCard" label="法人身份证号" show-overflow-tooltip></el-table-column>
               <el-table-column align="center"  min-width="8%"     label="采购食材数量" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <!-- <template slot-scope="scope"> -->
                    <div @click="IngredientNumber(scope.row)" style="width:100%;height:100%;" class='xiaoshou'>
                      {{scope.row.number}}
                    </div>

                  </template>
                </el-table-column>



                <el-table-column align="center" min-width="8%"  prop="payablesurplus" label="剩余应付账款" show-overflow-tooltip></el-table-column>
                  <el-table-column align="center" min-width="10%"  label="操作" show-overflow-tooltip>
                    <template slot-scope="scope">
                      <el-button style="margin-top:0px;color: greenyellow" class="search"  type="primary" @click="addingredient(scope.row)">添加食材</el-button>
                    </template>
                  </el-table-column>
            </el-table>

            <!-- 采购食材数量弹框 -->
          <el-dialog  style="margin-top:-50px;" title="采购食材数量" :visible.sync="dialogstaVisible" >
                              <el-table  stripe ref="multipleTable" class='table' :data="IngredienttableData" :cell-style="{padding:'0',
                                    color:'rgb(255,255,255)'}" :row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                                 fontWeight:'normal'}"  :header-row-style="{height:'38px',lineHeight:'10px',fontSize:'13px',
                            fontWeight:'normal'}" :header-cell-style="{padding:'0',color:'rgb(255,255,255)'}" tooltip-effect="dark"
                                        height="700px" size="small"  style="width: 100%;margin-top:0px;" @selection-change="handleSelectionChange">
                                <el-table-column align="center"  min-width="5%" label="序号"  type="index" :index="indexMethod" width="55"> </el-table-column>
                                <el-table-column align="center"  min-width="12%" prop="name" label="供应商" show-overflow-tooltip> </el-table-column>
                                <el-table-column align="center"  min-width="7%" prop="number" label="入库食材" show-overflow-tooltip></el-table-column>
                                <el-table-column align="center"  min-width="13%"  prop="code" label="入库食堂" show-overflow-tooltip></el-table-column>
                                <el-table-column align="center"  min-width="12%"  prop="establishTime" label="入库时间" show-overflow-tooltip></el-table-column>
                                <el-table-column align="center"  min-width="15%"  prop="address" label="入库经办人" show-overflow-tooltip></el-table-column>
                              </el-table>
          </el-dialog>



                <div style="float: right; margin-top: 10px;height:'15%'">
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
      dialogdetailVisible: false,
      dialogpointVisible:false,
      dialogstaVisible:false,
      dialogblackVisible:false,
      dialogpunVisible:false,
      dialogselVisible:false,
      dialogresVisible:false,
      dialogaddVisible:false,
      dialoggysVisible:false,
      pageIndex: 1,
      pageSize:10,
      pageSizes:[ 10, 20 , 30, 50, 100],
      total:0,
      score:'',
      tableData: [],
      IngredienttableData:[],
      fhtableData:[],
      bxtableData:[],
      xdtableData:[],
      blacktableData:[],
      puntableData:[],
      historytableData:[],
      blacklisttableData:[],
      tableDatares: [
        {
          meter: '2',
          verdish: '王小虎',
          restaurant: '3',
          mount: '1',
          weight: '2',
          price: '2',
          remark: '2',
          oeratio: '2'
        },
        {
          meter: '2',
          verdish: '王小虎',
          restaurant: '3',
          mount: '1',
          weight: '2',
          price: '2',
          remark: '2',
          oeratio: '2'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }
      ],
      selectlast:[
          {text:"全部",id:""},
          {text:"是",id:"是"},
          {text:"否",id:"否"}
      ],
      selectthis:[
          {text:"全部",id:""},
          {text:"是",id:"是"},
          {text:"否",id:"否"}
      ],
      queryform: {
        name: '',
        number: '',
        min: '',
        max:''
      },
      addition:{   //供应商增添
        equipmentName:'',  //供应商名称
        equipmentCode:'',//供应商编号
        creditcode:'',  //统一社会信用代码
        foundingTime:'',//成立时间
        registeredassets:'',  //注册资本
        BusinessAddress:'',//企业地址
        businessLicense:'',  //营业执照有效期
        representatives:'',//法人代表
        legalpersonphone:'',  //法人电话
        IDnumber:'',//身份证号
        startime:'',  //开始时间
        LiftTime:'',//解除时间
        result:'',//评审结果
        remark:''//说明
      },
      chooseform:{
        name:'',
        number:'',
        address:'',
        establishTime:'',
        startMoney:'',
        endMoney:'',
        lastPenalty:'',
        thisPenalty:'',
        startScore:'',
        endScore:''
      },
      addform:{

      }

    }
  },
  computed: {

  },
  created () {
    this.querylist()
  },
  methods: {
    indexMethod(index) {
      return index * 1+1;
    },
    handleSizeChange (val) {
      this.pageSize=val
      this.querylist()
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.pageIndex=val
      this.querylist()
      console.log(`当前页: ${val}`)
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 供应商列表
    querylist(){

        this.$axios({
            url:'StSupplier.do/getSupplier',
            method:'post',
            data:{
                "pageNum":this.pageIndex,
                "pageSize":this.pageSize,
                "name":this.queryform.name,
                "number":this.queryform.number,
                "min":this.queryform.min,
                "max":this.queryform.max
            }
        }).then(res=>{
            this.tableData=res.data.list
            this.total=res.data.totalRecord
        })
    },
    // 导出
    exportout(){

      this.$axios({
        url:'',
        method:'post',
        data:{
          // "param":{
          //   "restaurantName":this.restaurantCode,
          //   "equipmentName":this.queryform.equipmentName,
          //   "dishName":this.queryform.dishName
          // }
        },

        responseType:'blob'
      }).then(res=>{
        let data = res;
        let blob = new Blob([data.data], { type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=utf-8"});
        let url = window.URL.createObjectURL(blob);
        let link = document.createElement("a");
        link.style.display = "none";
        link.href = url;
        link.setAttribute("download", "供应商管理列表.xls");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      })
    },
    //食材数量查看
    IngredientNumber(row){
      // console.log(row)
      // var arr2=row
      // var str2 = JSON.stringify(arr2);
      // window.sessionStorage.setItem('key', str2)
      this.dialogstaVisible=true
    },
    //添加食材
    addingredient(row){
      // console.log(row)
      // var arrss = window.sessionStorage.getItem('key');
      // var arrayaaa = JSON.parse(arrss);
      // console.log(arrayaaa.id)

    },
    // 供应商遴选
    getChooseList(){
        this.$axios({
            url:'StSupplier.do/getChooseList',
            method:'post',
            data:{
                "name":this.chooseform.name,
                "number":this.chooseform.number,
                "address":this.chooseform.address,
                "establishTime":this.chooseform.establishTime,
                "startMoney":this.chooseform.startMoney,
                "endMoney":this.chooseform.endMoney,
                "lastPenalty":this.chooseform.lastPenalty,
                "thisPenalty":this.chooseform.thisPenalty,
                "startScore":this.chooseform.startScore,
                "endScore":this.chooseform.endScore,
            }
        }).then(res=>{
            this.fhtableData=res.data.fh
            this.bxtableData=res.data.bx
            this.xdtableData=res.data.xd
        })
    },
    // 供应商遴选操作
    choose(id,state){
        this.$axios({
            url:'StSupplier.do/choose',
            method:'post',
            data:{
                "id":id,
                "state":state
            }
        }).then(res=>{
            if(res.data=='添加备选成功'){
                this.$message.success('添加备选成功')
                this.getChooseList()
            }else if(res.data=='选定成功'){
                this.$message.success('选定成功')
                this.getChooseList()
            }else{
                this.$message.error('操作失败')
            }
        })
    },
    // 黑名单详情
    getBlacklist(id){
        this.dialogblackVisible=true
        this.$axios({
            url:'StSupplier.do/getBlacklist',
            method:'post',
            data:{
                "id":id
            }
        }).then(res=>{
            this.blacktableData=res.data.blacklist
        })
    },
    // 惩罚详情
    getPunishment(id){
        this.dialogpunVisible=true
        this.$axios({
            url:'StSupplier.do/getPunishment',
            method:'post',
            data:{
                "id":id
            }
        }).then(res=>{
            this.puntableData=res.data.punishment
        })

    },
    // 本年度评价积分
    getEvaluate(id){
        this.dialogpointVisible=true
        this.$axios({
            url:'StSupplier.do/getEvaluate',
            method:'post',
            data:{
                "id":id
            }
        }).then(res=>{
            this.historytableData=res.data.history
            this.blacklisttableData=res.data.blacklist
            this.score=res.data.score
        })
    },
    //重置
    reset(){
      this.queryform={
        name: '',
        number: '',
        min: '',
        max:''
      }
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
    height:50px;
}
.queryform{
    color: rgb(255,255,255);
    font-size: 12px;
    width:100%;
    padding-left:50px;
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
    padding:0 30px;
}
.search{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 84px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}
.increase,.supply{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width:120px;
    height:32px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color: rgb(1,7,34);
}

.detail{
    color:rgb(255,255,255) ;
    font-size: 10px;
    width: 90px;
    height:24px;
    border:1px solid rgb(52,57,77);
    border-radius: 3px;
    vertical-align: middle;
    background-color:rgb(64,158,255);
}
.search,.increase,.supply,.detail:hover{
    cursor: pointer;
}
/deep/.el-form-item__label{
    font-size: 10px;
    color:rgb(255,255,255) ;
}
/deep/.el-card__body{
  height:100%;
}
.timeform{
  margin-bottom: 18px;
}
/deep/.el-table{
    background-color:transparent;

}
 /deep/.el-table__body-wrapper{
    height: 630px!important;
    overflow-y:auto;
}
.adddialog /deep/.el-table{
    background-color:transparent;
    height: 160px!important;
    margin-bottom: 20px;
}
.adddialog /deep/.el-table__body-wrapper{
    height: 120px!important;
}
.condition /deep/.el-table{
    background-color:transparent;
    height: 400px!important;
    margin-bottom: 20px;
}
.condition /deep/.el-table__body-wrapper{
    height: 400px!important;
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
/deep/.el-table tbody tr:nth-child(odd)>td {
    background-color:transparent!important;
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
/deep/.el-pagination{
  background-color: transparent;
}
/deep/.el-dialog{
  background: rgba(0,0,0,.8);
  border:1px solid rgba(255,255,255,.5);
  margin-top:300px;
}
/deep/.el-dialog__header{
  padding:10px;
  text-align: center;
  background:rgba(0,0,0,.4);
  border-bottom:1px solid rgba(255,255,255,.5);
  margin-bottom:20px;
}
.editdialog .point{
    width: 240px;
    height:100px;
    line-height:30px;
    position: absolute;
    top:100px;
    right:40px;
}
/deep/.el-dialog__body{
  color:rgb(255,255,255);
  background:transparent;
  padding-bottom:2px;
  padding:0 70px;
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
.detailbody{
    padding-left:40px;
}
button:hover{
      background:url(../../assets/zy2.png) no-repeat;
      background-size:100% 100%;
}
/deep/.el-form-item__label{
    line-height:28px;
    padding-right:12px ;
}
.el-input{
  width: 180px;
}
.number .el-input{
    width: 120px;
}
.points .el-input{
    width: 90px;
}
.rod{
    display: inline-block;
    margin-left:5px;
    font-size: 14px;
}
.adddialog /deep/.el-dialog{
    width:1400px;
}
.titletext{
    font-size: 20px;
    font-weight: 700;
    margin-bottom: 20px;
    text-align: left;
}
.addform /deep/.el-input{
    width: 180px;
}
.addform /deep/.el-form-item__label{
    width: 120px;
}
.addform /deep/.el-textarea{
    width: 1000px;
}
.addform /deep/.el-textarea__inner{
    height: 100px;
    background-color:black;
    color:white;
}
/deep/.el-radio__label{
    font-size: 12px;
}
.imgboxtwo,.imgboxone,.imgboxthree{
    display: inline-block;
    margin:0 20px 20px 0 ;
    width: 132px;
    height:75px;
}
.imgboxone img{
    width: 99px;
    height: 75px;
}
.imgboxtwo img{
    width: 132px;
    height: 75px;
}
.imgboxthree img{
    width: 81px;
    height:75px;
}
.mimitcol /deep/.el-input{
    width: 70px;
}
/deep/.el-input__icon{
    height: 34px;
    line-height: 34px;

}
/deep/.el-form-item__content{
    font-size: 12px;
}
.upload{
    font-size: 12px;
    margin-bottom: 20px;
}
.bigarea /deep/.el-textarea__inner{
    height: 200px;
}
.xiaoshou{
    cursor: pointer;
    color:greenyellow;
}
</style>
