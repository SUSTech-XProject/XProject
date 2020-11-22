<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/test' }">XProject</el-breadcrumb-item>
    <el-breadcrumb-item><a href="/projectlist/team/forming">Project List</a></el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('team')!==-1">Team</el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('myteam')!==-1"><a href="/projectlist/team/myteam">My Team</a></el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('forming')!==-1"><a href="/projectlist/team/forming">Forming</a></el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('events')!==-1"><a href="/projectlist/events">Events</a></el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('resources')!==-1"><a href="/projectlist/resources">Resources</a></el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('submissions')!==-1"><a href="/projectlist/submissions">Submissions</a></el-breadcrumb-item>
    <el-breadcrumb-item v-if="this.$route.path.indexOf('gradebook')!==-1"><a href="/projectlist/gradebook">Gradebook</a></el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  name: 'index'
}
</script>

<style scoped>
.el-breadcrumb{
  /*width: 20px;*/
  position: relative;
  height: 10%;
  bottom: 10%;
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
