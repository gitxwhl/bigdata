import request from '@/utils/request'
// 统一抽取
const api_name = '/admin/system/sysRole'
export default {
/* 角色列表条件分页查询 */
  getPageList(current, limit, searchObj) {
    return request({
      url: `${api_name}/${current}/${limit}`,
      method: 'get',
      /* 如果是普通对象参数写法，params：对象参数名称
      如果是json对象参数写法，data：对象参数名称 */
      params: searchObj
    })
  },


}
