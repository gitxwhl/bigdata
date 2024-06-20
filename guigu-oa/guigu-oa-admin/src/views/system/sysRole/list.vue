<script>
/* 引入定义接口的js文件 */
import api from '@/api/system/sysRole.js'
export default {
  //vue 代码结构
  //初始值
  data() {
    return{

    }
    },
    created() {  //在渲染之前執行
    this.fetchData();
    },
  methods: function() {  //操作方法
    //条件分页查询方法
    fetchData()
    {

    }

  }
  }


</script>
