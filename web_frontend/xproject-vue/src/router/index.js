import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import TestPage from '@/views/test/TestPage'
import Login from '@/views/login/components/Login'
import Register from '@/views/login/components/Register'
import MainPage from '@/views/main/MainPage'
import WelcomePage from '@/views/welcome/WelcomePage'
import LRPage from "@/views/login/LRPage";
import Top from '@/views/welcome/Top'
import Overview from '@/views/Overview'
import ProjectList from "@/views/home/projectList";
import PersonalInfo from "@/views/home/personalInfo"
import Announcement from "@/views/project/Announcement";
import MyTeam from "@/views/project/team/myTeam"

Vue.use(Router)

const routes = [
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
    name: 'TestPage',
    component: TestPage
    // meta: {
    //   requireAuth: true
    // }
  },
  {
    path: '/welcome',
    name: 'Welcome',
    component: WelcomePage
  },
  {
    path: '/account',
    name: 'Account',
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
    redirect: '/index'
  },
  // YZX: 有用的放到这个上面
  {
    path: '/project/announcement',
    name: 'Announcement',
    component: Announcement
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
    path: '/home/projectlist',
    name: 'projectlist',
    component: ProjectList
  },
  {
    path: '/homepage',
    name: 'personalInfo',
    component: PersonalInfo
  },
  {
    path: '/team/myTeam',
    name: 'myTeam',
    component: MyTeam
  }
]

export default new Router({
  mode: 'history',
  routes
})
