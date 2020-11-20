import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import TestPage from '@/views/test/TestPage'
import Login from '@/views/visitor/components/Login'
import Register from '@/views/visitor/components/Register'
import MainPage from '@/views/main/MainPage'
import WelcomePage from '@/views/welcome/WelcomePage'
import LRPage from "@/views/visitor/LRPage";
import ProjectList from "@/views/main/project_list/ProjectList";
import HomePage from "@/views/main/home_page/HomePage";
import Account from "@/components/Account";
import MyTeam from "@/views/main/project/team/MyTeam";
import Forming from "@/views/main/project/team/Forming";

Vue.use(Router)

const routes = [
  {
    path: '/welcome',
    name: 'Welcome',
    component: WelcomePage
  },
  {
    path: '/index',
    name: 'MainPage',
    component: MainPage,
    // meta: {
    //   requireAuth: true
    // },
    children: [
      {
        path: 'home-page',
        name: 'HomePage',
        component: HomePage
      },
      {
        path: 'project-list',
        name: 'ProjectList',
        component: ProjectList
      },
      {
        path: 'account',
        name: 'Account',
        component: Account
      },
      {
        path: 'my-team',
        name: 'MyTeam',
        component: MyTeam
      },
      {
        path: 'forming',
        name: 'Forming',
        component: Forming
      }
    ]
  },
  {
    path: '/visitor',
    name: 'Visitor',
    children: [
      {
        path: 'login',
        name: 'Login',
        component: Login
      },
      {
        path: 'register',
        name: 'Register',
        component: Register
      }
    ],
    component: LRPage
  },
  {
    path: '/',
    redirect: '/welcome'
  },
  // YZX: 有用的放到这个上面，临时的放到下面
  {
    path: '/test',
    name: 'TestPage',
    component: TestPage
    // meta: {
    //   requireAuth: true
    // }
  },
]

export default new Router({
  mode: 'history',
  routes
})
