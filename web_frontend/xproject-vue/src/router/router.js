import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '@/components/home/AppIndex'
import Login from '@/components/Login'

Vue.use(Router)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/index',
    name: 'AppIndex',
    component: AppIndex
  },
  {
    path: '/',
    redirect: '/login'
  }
]

export default new Router({
  routes
})