<template>
  <div id = "left-bar">
  <el-menu  class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
    <el-menu-item index="1" id = "Title">
      <i class="el-icon-first-aid-kit" ></i>
      <span slot="title">XProject</span>
    </el-menu-item>

    <el-menu-item index="2">
      <i class="el-icon-bell"></i>
      <span slot="default">Announcement</span>
    </el-menu-item>

    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-connection"></i>
        <span slot="title">Team</span>
      </template>
      <el-menu-item-group>

        <el-menu-item index="3-1">My Team</el-menu-item>
        <el-menu-item index="3-2">Forming</el-menu-item>
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
  </el-menu>
<!--  <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
<!--      <el-radio-button :label="false">展开</el-radio-button>-->
<!--      <el-radio-button :label="true">收起</el-radio-button>-->
<!--    </el-radio-group>-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: false
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
  margin: 0;
  padding: 0;
  text-align: left;
  height: 100%;
}
#Title{
  background-color: #9acafd;
  color: white;
  font-size: 20px;
  height: 60px;
}
</style>
