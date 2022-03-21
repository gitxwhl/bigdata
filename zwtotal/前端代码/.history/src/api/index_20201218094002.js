import axios from 'axios'
// 餐厅服务器地址
// axios.defaults.baseURL = 'http://10.40.70.95:8087/'
// axios.defaults.baseURL = 'http://10.40.70.95:8082/canteen-server'
// axios.defaults.baseURL = 'http://10.40.70.95:8082/canteen-server'
// axios.defaults.baseURL = 'http://10.60.0.51:8081/canteen-server/'

// 物业服务器地址
// export const instance = axios.create({
//     baseURL: 'http://10.40.70.95:9090/property-server',
//     // baseURL: 'http://10.40.70.95:8082/property-server',
//     // baseURL: 'http://10.60.0.51:9090/property-server',
//
//   })
  // 办公服务器地址
  // export const office = axios.create({
  //   baseURL: 'http://10.40.70.95:8083/officeServices-server',
  //   // baseURL: 'http://10.40.70.95:8085/',
  //
  // })

let baseURLService;
// 服务端端口号
let portService = "8089";
// 获取请求的方式  如: http:
let protocol = window.location.protocol;
console.log(protocol)
// 获取浏览器url地址 如：192.168.25.33:3000、www.baidu.com
let url = window.location.host;

let number = url.indexOf(":");
// 没有端口的url
let newUrl = url.substr(0, number) + ":" + portService;

// 服务端：拼接url
baseURLService = protocol + "//" + newUrl;
// axios.defaults.baseURL = baseURLService;

export const office = axios.create({
  baseURL: baseURLService,
})
export default axios
