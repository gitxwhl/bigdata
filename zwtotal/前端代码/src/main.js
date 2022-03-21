import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.css'

import App from './App.vue'
import router from './router'
import store from './store'
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
import axios,{instance,office} from '@/api'
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$instance = instance
Vue.prototype.$office=office
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
