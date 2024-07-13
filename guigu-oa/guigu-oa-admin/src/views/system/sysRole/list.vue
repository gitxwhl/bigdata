<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input style="width: 100%" v-model="searchObj.roleName" placeholder="角色名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" :loading="loading" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>

    <!-- 表格 -->
    <!-- 工具条 -->
    <div class="tools-div">
<!--      <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>-->
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add" :disabled="$hasBP('bnt.sysRole.add')  === false">添 加</el-button>
      <el-button type="danger" icon="el-icon-delete" size="mini" @click="batchRemove" :disabled="isDisabled">批量删除</el-button>
<!--      <el-button class="btn-add" size="mini" @click="batchRemove()" >批量删除</el-button>-->
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;"

      @selection-change="handleSelectionChange">
<!--  @selection-change="handleSelectionChange">    事件中的方法表示 取到选择的值，传到方法中去,方法中可以得到传的每一行的内容-->
<!--      表示多选框-->
      <el-table-column type="selection"/>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="createTime" label="创建时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除"/>
          <el-button type="warning" icon="el-icon-baseball" size="mini" @click="showAssignAuth(scope.row)" title="分配权限"/>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />

<!--    添加  dialogVisible值为true时，dialog显示 false时不显示 弹窗-->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" >
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
/* 引入定义接口的js文件 */
import api from '@/api/system/sysRole'
import th from 'element-ui/src/locale/lang/th'
export default {
  // vue 代码结构
  // 初始值
  data() {
    return {
      list: [], // 角色列表
      page: 1, // 当前页
      limit: 3, // 每页显示多少条记录
      total: 0, // 总记录数
      searchObj: {},// 查询条件对象
      sysRole: {},//封装表单中角色的数据
      dialogVisible: false
    }
  },
  created() { // 在渲染之前執行 不传参数 默认值 current = 1
    this.fetchData()
  },
  methods: { // 操作方法
// 条件分页查询
    fetchData(current = 1) {
      this.page = current
      api.getPageList(this.page, this.limit, this.searchObj)
        .then(response => {
          console.log(response.data.records)
          console.log(response.data.total)
          this.list = response.data.records
          this.total = response.data.total
        })
    },

    // 点击分配权限调用，方法通过路由跳转新页面，跳转到分配菜单的页面
    showAssignAuth(row) {
      this.$router.push('/system/assignAuth?id='+row.id+'&roleName='+row.roleName);
    },

    //点击修改，弹出框，根据id查询数据显示
    edit(id) {
      //弹出框
      this.dialogVisible = true
      //根据id查询
      this.fetchDataById(id)
    },
    //根据id查询
    fetchDataById(id){
      api.getById(id)
        .then(response => {
          console.log(response)
          this.sysRole = response.data
        })
    },

    // 根据id删除数据   如果点击确定，将执行then方法，如果点击取消，将执行catch方法，点取消什么都不需要做，
    // 所以catch不需要写任何代码,只需要写then方法即可
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax return 表示调用成功之后会执行下一个then方法
        return api.removeById(id)
      }).then((response) => {
        // 刷新页面
        this.fetchData(this.page)
        // 提示信息
        this.$message.success(response.message || '删除成功')
      })
    },
    // 点击添加弹出框
    add(){
      this.dialogVisible = true
    },

    saveOrUpdate(){
      //根据id 判断是添加还是修改
      console.log(this.sysRole.id)
      if(this.sysRole.id){
        this.update()
      }else{
        console.log(this.sysRole.id)
        this.save()
      }
    },
    save(){ //添加
      api.saveRole(this.sysRole).then(response =>{
        //提示
        this.$message.success(response.message || '添加成功')
        //关闭弹窗
        this.dialogVisible = false
        //刷新页面
        this.fetchData()
      })

    },
    update(){ //修改
      api.updateById(this.sysRole).then(
        response => {
          //提示
          this.$message.success(response.message || '修改成功')
          //关闭弹窗
          this.dialogVisible = false
          //刷新页面
          this.fetchData()
        }
      )
    },
    //批量删除
    // 选择复选框，把复选框所在的内容传递
    handleSelectionChange(selection){
      this.selections=selection
      console.log(selection)
    },
    // 批量删除
    batchRemove() {
      //判断
      if(this.selections.length == 0){
        this.$message.warning('请选择要删除的数据')
        return;
      }
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax return 表示调用成功之后会执行下一个then方法
        var idList=[]
        //选择复选框数据在数组里  this.selections
        this.selections.forEach(item => {
          var id=item.id
          idList.push(id)
        })
        return api.batchRemove(idList);
      }).then(response => {
        // 提示信息
        this.$message.success(response.message || '删除成功')
        // 刷新页面
        this.fetchData()
      })
    }


  }

}
</script>
