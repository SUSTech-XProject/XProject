<template>
  <el-breadcrumb separator-class="el-icon-arrow-right" style="">

    <el-breadcrumb-item v-if="this.$route.name === 'HomePage'">
      <span class="bc-text">Home Page</span>
    </el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.name === 'ProjectList'">
      <span class="bc-text">Projects</span>
    </el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.name === 'Account'">
      <span class="bc-text">Account</span>
    </el-breadcrumb-item>

    <el-breadcrumb-item v-if="this.$route.path.indexOf('/project/') !== -1"
                        :to="{name: 'ProjectList'}">
      <span class="bc-text">Projects</span>
    </el-breadcrumb-item>

    <el-breadcrumb-item v-if="this.$route.path.indexOf('/project/') !== -1"
                        :to="{name: 'ProjOverview'}">
      <span class="bc-text">{{this.$store.state.proj.projName}}</span>
    </el-breadcrumb-item>

    <el-breadcrumb-item v-if="this.bcDict[this.$route.name] != null">
      <span class="bc-text">{{this.bcDict[this.$route.name]}}</span>
    </el-breadcrumb-item>

  </el-breadcrumb>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      bcDict: {
        'StdManage': 'Students',
        'MyTeam': 'My Team',
        'Forming': 'Forming',
        'Announcements': 'Announcements',
        'Events': 'Events',
        'Resources': 'Resources',
        'Submissions': 'Submissions',
        'Gradebook': 'Gradebook'
      }
    }
  }
}
</script>

<style scoped>
  .el-breadcrumb{
    display: flex;
    align-items: center;
  }
  .bc-text {
    font-weight: bold;
    font-size: 14px;
    padding-top: 2px;
  }
</style>


<!--<template>-->
<!--  <el-breadcrumb class="app-breadcrumb" separator="/">-->
<!--    <transition-group name="breadcrumb">-->
<!--      <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">-->
<!--        <span v-if="item.redirect==='noRedirect'||index==levelList.length-1" class="no-redirect">{{ item.meta.title }}</span>-->
<!--        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>-->
<!--      </el-breadcrumb-item>-->
<!--    </transition-group>-->
<!--  </el-breadcrumb>-->
<!--</template>-->

<!--<script>-->
<!--import pathToRegexp from 'path-to-regexp'-->
<!--export default {-->
<!--  data () {-->
<!--    return {-->
<!--      levelList: null-->
<!--    }-->
<!--  },-->
<!--  watch: {-->
<!--    $route (route) {-->
<!--      // if you go to the redirect page, do not update the breadcrumbs-->
<!--      if (route.path.startsWith('/redirect/')) {-->
<!--        return-->
<!--      }-->
<!--      this.getBreadcrumb()-->
<!--    }-->
<!--  },-->
<!--  created () {-->
<!--    this.getBreadcrumb()-->
<!--  },-->
<!--  methods: {-->
<!--    getBreadcrumb () {-->
<!--      // only show routes with meta.title-->
<!--      let matched = this.$route.matched.filter(item => item.meta && item.meta.title)-->
<!--      const first = matched[0]-->
<!--      if (!this.isDashboard(first)) {-->
<!--        // eslint-disable-next-line standard/object-curly-even-spacing-->
<!--        matched = [{ path: '/test', meta: { title: 'Test' }}].concat(matched)-->
<!--      }-->
<!--      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)-->
<!--    },-->
<!--    isDashboard (route) {-->
<!--      const name = route && route.name-->
<!--      if (!name) {-->
<!--        return false-->
<!--      }-->
<!--      return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()-->
<!--    },-->
<!--    pathCompile (path) {-->
<!--      const { params } = this.$route-->
<!--      var toPath = pathToRegexp.compile(path)-->
<!--      return toPath(params)-->
<!--    },-->
<!--    handleLink (item) {-->
<!--      const { redirect, path } = item-->
<!--      if (redirect) {-->
<!--        this.$router.push(redirect)-->
<!--        return-->
<!--      }-->
<!--      this.$router.push(this.pathCompile(path))-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang="scss" scoped>-->
<!--.app-breadcrumb.el-breadcrumb {-->
<!--  display: inline-block;-->
<!--  font-size: 14px;-->
<!--  line-height: 50px;-->
<!--  margin-left: 8px;-->
<!--  .no-redirect {-->
<!--    color: #97a8be;-->
<!--    cursor: text;-->
<!--  }-->
<!--}-->
<!--</style>-->
