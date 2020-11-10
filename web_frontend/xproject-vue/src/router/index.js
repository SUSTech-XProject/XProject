import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '@/views/test/AppIndex'
import Login from '@/views/login/Login'
import Register from '@/views/login/Register'

Vue.use(Router)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/index',
    name: 'AppIndex',
    component: AppIndex,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/',
    redirect: '/login'
  }
]

export default new Router({
  mode: 'history',
  routes
})
