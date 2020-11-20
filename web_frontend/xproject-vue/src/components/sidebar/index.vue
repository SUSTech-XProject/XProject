<template>
  <div id = "left-bar">
    <el-menu :default-active="$route.path"
             class="el-menu-vertical-demo"
             @open="handleOpen" @close="handleClose"
             @select="handleSelect"
             :collapse="isCollapse" style="height: 100%" router>
      <el-menu-item id = "Title">
        <i class="el-icon-first-aid-kit" ></i>
        <span slot="title">XProject</span>
      </el-menu-item>

      <el-menu-item index="/index/home-page">
        <i class="el-icon-s-home"></i>
        <span slot="default">Home Page</span>
      </el-menu-item>

      <el-menu-item index="/index/project-list">
        <i class="el-icon-notebook-1"></i>
        <span slot="default">Project List</span>
      </el-menu-item>

      <div v-if="show">
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-connection"></i>
            <span slot="title">Team</span>
          </template>

          <el-menu-item-group>
            <el-menu-item index="/index/my-team">My Team</el-menu-item>
            <el-menu-item index="/index/forming">Forming</el-menu-item>
          </el-menu-item-group>

        </el-submenu>

        <el-menu-item index="4" >
          <i class="el-icon-date"></i>
          <span slot="title">Events</span>
        </el-menu-item>
        <el-menu-item index="5">
          <i class="el-icon-folder-opened"></i>
          <span slot="title">Resources</span>
        </el-menu-item>
        <el-menu-item index="6">
          <i class="el-icon-document"></i>
          <span slot="title">Submissions</span>
        </el-menu-item>
        <el-menu-item index="7">
          <i class="el-icon-collection"></i>
          <span slot="title">Gradebook</span>
        </el-menu-item>
        </div>
  <!--    <el-submenu index="3">-->
  <!--      <template slot="title">-->
  <!--        <i class="el-icon-connection"></i>-->
  <!--        <span slot="title">Team</span>-->
  <!--      </template>-->
  <!--      <el-menu-item-group>-->

  <!--        <el-menu-item index="3-1">My Team</el-menu-item>-->
  <!--        <el-menu-item index="3-2">Forming</el-menu-item>-->
  <!--      </el-menu-item-group>-->

  <!--    </el-submenu>-->

  <!--    <el-menu-item index="4" >-->
  <!--      <i class="el-icon-date"></i>-->
  <!--      <span slot="title">Events</span>-->
  <!--    </el-menu-item>-->
  <!--    <el-menu-item index="5">-->
  <!--      <i class="el-icon-folder-opened"></i>-->
  <!--      <span slot="title">Resources</span>-->
  <!--    </el-menu-item>-->
  <!--    <el-menu-item index="6">-->
  <!--      <i class="el-icon-document"></i>-->
  <!--      <span slot="title">Submissions</span>-->
  <!--    </el-menu-item>-->
  <!--    <el-menu-item index="7">-->
  <!--      <i class="el-icon-collection"></i>-->
  <!--      <span slot="title">Gradebook</span>-->
  <!--    </el-menu-item>-->
        <el-radio-group v-model="isCollapse">
          <i v-show="!isCollapse" class="el-icon-s-fold expandBtn" @click="isCollapse=true"></i>
          <i v-show="isCollapse" class="el-icon-s-fold shrinkBtn" @click="isCollapse=false"></i>
        </el-radio-group>


      <el-menu-item index="/index/account" >
        <i class="el-icon-s-custom"></i>
        <span slot="title">Account</span>
      </el-menu-item>

    </el-menu>
  <!--    <el-radio-button :label="false">Open</el-radio-button>-->
  <!--    <el-radio-button :label="true">Close</el-radio-button>-->
  <!--  <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
  <!--      <el-radio-button :label="false">展开</el-radio-button>-->
  <!--      <el-radio-button :label="true">收起</el-radio-button>-->
  </div>
</template>

<script>
export default {
  data () {
    return {
      isCollapse: false,
      show: /*this.$route.path.indexOf('project-list') !== -1*/ true
    }
  },
  methods: {
    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    },
    handleSelect (key, keyPath) {

    }
  }
}
</script>

<style scoped>
#left-bar{
  padding: 0;
  margin: 0;
  height: 100%;
  /*background: blueviolet;*/
}
.el-radio-group {
  /*position: center;*/
  position: absolute;
  bottom: 5%;
  font-size: 20px;
  /*line-height: 300%;*/
  line-height: 100%;
  /*margin-bottom: 10px;*/
  /*margin-top: 10%;*/
  /*margin-bottom: 10%;*/
  /*margin-top: 20px;*/
  /*margin-left: 0px;*/
  /*width: 200px;*/
}
.shrinkBtn {
  position: center;
  /*left: -18px;*/
  /*top: 38px;*/
  color: #151d41;
  /*margin-bottom: 10%;*/
  /*margin-left: 100px;*/
  transform: rotate(180deg);
}
.expandBtn {
  /*position: center;*/
  /*left: -18px;*/
  /*top: 38px;*/
  color: #151d41;
  margin-left: 160px;
  /*transform: rotate(0deg);*/
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
  margin: 0;
  padding: 0;
  text-align: left;
}
#Title{
  background-color: #9acafd;
  color: white;
  font-size: 20px;
  height: 60px;
}
#left-bar{
  height: 100%;
}
</style>
