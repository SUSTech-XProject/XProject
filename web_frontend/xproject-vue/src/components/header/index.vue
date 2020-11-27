<template>
  <div id = "header">
    <el-header>
<!--      <i v-bind:class="[{'el-icon-d-arrow-right':isFolded},'el-icon-d-arrow-left']"></i>-->
<!--      <div v-if="this.$store.state.proj.projId !== null">-->
<!--        <Breadcrumb></Breadcrumb>-->
<!--      </div>-->
<!--      <div v-else id="partname"></div>-->
      <el-row class="header">
<!--        <span>{{part}}</span>-->
        <el-col v-if="this.$store.state.proj.projId !== null">
          <Breadcrumb></Breadcrumb>
        </el-col>
        <el-col :span="20">test</el-col>
        <el-col :span="2">
          <el-dropdown>
        <span class="el-dropdown-link">
          <i style="margin-left: 80%" class="el-icon-help"></i>
        </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-question">Help</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>

        <el-col :span="2">
          <el-dropdown @command="handleCommand" trigger="click">
        <span class="el-dropdown-link">
          <i style="margin-left: 0px" class ="el-icon-user"></i>
        </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-s-custom">Account</el-dropdown-item>
              <el-dropdown-item icon="el-icon-menu">Overview</el-dropdown-item>
              <el-dropdown-item icon="el-icon-error">Log out</el-dropdown-item>
              <el-dropdown-item icon="el-icon-check">Others</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>

    </el-header>
  </div>
</template>

<script>
import Breadcrumb from '@/components/breadcrumb/index'

export default {
  name: 'index',

  data () {
    return {
      isFolded: false,
      part: this.$route.path.split('/')[2]
    }
  },
  components: {
    Breadcrumb
  },
  methods: {
    handleCommand(command){
      this.$store.commit('logout')
      this.$router.push({name:'Welcome'})
    }
  }
}
</script>

<style>
#header {
  background-color: #edf4ff;
  color: #333;
  line-height: 60px;
  width:100%;
  font-size: 20px;
  text-align: left;
}
#Breadcrumb {
  margin-top: 20px;
  position: center;
}
#partname{
  width: 20px;
  position: relative;
  height: 10%;
  bottom: 10%;
}

</style>
