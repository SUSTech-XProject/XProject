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
import Account from "@/views/main/account/Account";
import MyTeam from "@/views/main/project/team/MyTeam";
import Forming from "@/views/main/project/team/Forming";
import ProjOverview from "@/views/main/project/overview/ProjOverview";
import ProjPage from "@/views/main/project/ProjPage";
import Announcement from '@/views/main/project/announcement/Announcement'

Vue.use(Router)

// 修改路由重复点击报错
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

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
    children: [
      {
        path: 'home-page',
        name: 'HomePage',
        component: HomePage,
        meta: {
          requireAuth: true
        },
      },
      {
        path: 'project-list',
        name: 'ProjectList',
        component: ProjectList,
        meta: {
          requireAuth: true
        },
      },
      {
        path: 'account',
        name: 'Account',
        component: Account,
        meta: {
          requireAuth: true
        },
      },
      {
        path: 'project/:proj_id',
        component: ProjPage,
        children: [
          {
            path: 'overview',
            name: 'ProjOverview',
            component: ProjOverview,
            meta: {
              requireAuth: true
            },
          },
          {
            path: 'my-team',
            name: 'MyTeam',
            component: MyTeam,
            meta: {
              requireAuth: true
            },
          },
          {
            path: 'forming',
            name: 'Forming',
            component: Forming,
            meta: {
              requireAuth: true
            },
          },
          {
            path: 'announcement',
            name: 'Announcement',
            component: Announcement,
            meta: {
              requireAuth: true
            },
          }
        ]
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
    redirect: '/test'
  },
  // YZX: 有用的放到这个上面，临时的放到下面
  {
    path: '/test',
    name: 'TestPage',
    component: Announcement
    // meta: {
    //   requireAuth: true
    // }
  },
]

export default new Router({
  mode: 'history',
  routes
})
