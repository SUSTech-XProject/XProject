import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import TestPage from '@/views/test/TestPage'
import Login from '@/views/login/Login'
import Register from '@/views/login/Register'
import WelcomePage from '@/views/welcome/WelcomePage'
import Top from '@/views/welcome/Top'
import Overview from '@/views/Overview'
import Forming from '@/views/projectpage/team/Forming'

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
    path: '/test',
    name: 'TestPage',
    component: TestPage
  },
  {
    path: '/projectlist/team/forming',
    name: 'Forming',
    component: Forming,
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: WelcomePage
  },
  {
    path: '/top',
    name: 'Top',
    component: Top
  },
  {
    path: '/overview',
    name: 'Overview',
    component: Overview
  },
  {
    path: '/',
    redirect: '/welcome'
  }
]

export default new Router({
  mode: 'history',
  routes
})
