import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router';

Vue.config.productionTip = false

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'

Vue.use(ElementUI)

new Vue({
  router, // 将 router 添加到 Vue 实例中
  render: h => h(App),
}).$mount('#app')
