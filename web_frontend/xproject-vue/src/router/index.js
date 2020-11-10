import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import TestPage from '@/views/test/TestPage'
import Login from '@/views/login/Login'
import Register from '@/views/login/Register'
import MainPage from '@/views/main/MainPage'
import WelcomePage from '@/views/welcome/WelcomePage'

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
    name: 'MainPage',
    component: MainPage,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/test',
    name: 'Test',
    component: TestPage,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: WelcomePage
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
