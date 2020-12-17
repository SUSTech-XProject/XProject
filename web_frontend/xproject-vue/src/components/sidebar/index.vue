<template>
  <div style="height: 100%; width: 100%">
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu :default-active="this.$route.name"
               class="el-menu-vertical-demo"
               id="left-bar"
               @open="handleOpen" @close="handleClose"
               @select="handleSelect"
               background-color="#304156"
               text-color="#bfcbd9"
               active-text-color="#4392f9"
               :collapse="isCollapse" style="height: 100%">
        <el-menu-item id="title" index="Welcome">
          <i style="margin-top: -10px">
            <img :src="logo" alt="logo" style="width: 35px; margin-left: -5px; padding-bottom: 6px"/>
          </i>
          <span slot="default" style="margin-left: 5px; font-size: 22px">XProject</span>
        </el-menu-item>

        <el-menu-item index="HomePage">
          <i class="el-icon-s-home"></i>
          <span slot="title">Home Page</span>
        </el-menu-item>

        <el-menu-item index="ProjectList">
          <i class="el-icon-notebook-1"></i>
          <span slot="title">Project List</span>
        </el-menu-item>

        <el-menu-item index="Roster" v-if="showRoster()">
          <i class="el-icon-notebook-2"></i>
          <span slot="title">Roster</span>
        </el-menu-item>

        <el-menu-item index="Account">
          <i class="el-icon-s-custom"></i>
          <span slot="title">Account</span>
        </el-menu-item>

<!--        <el-menu-item-group v-if="this.$store.state.proj.projId != null" id="proj-items">-->
<!--        </el-menu-item-group>-->
        <div  v-if="showStdProjMenu()" style="padding-top: 10px">
          <el-menu-item index="ProjOverview">
            <i class="el-icon-s-promotion"></i>
            <span slot="title">Overview</span>
          </el-menu-item>

          <el-submenu index="@3">
            <template slot="title">
              <i class="el-icon-connection"></i>
              <span v-if="!isCollapse" slot="title">Team</span>
              <span v-if="isCollapse" slot="title"></span>
            </template>

            <el-menu-item index="MyTeam">My Team</el-menu-item>
            <el-menu-item index="Forming">Forming</el-menu-item>
          </el-submenu>

          <el-menu-item index="AnnouncementStu" >
            <i class="el-icon-bell"></i>
            <span slot="title">Announcements</span>
          </el-menu-item>
          <el-menu-item index="StuEvent" >
            <i class="el-icon-date"></i>
            <span slot="title">Events</span>
          </el-menu-item>
          <el-menu-item index="@5">
            <i class="el-icon-folder-opened"></i>
            <span slot="title">Resources</span>
          </el-menu-item>
          <el-menu-item index="SubmissionStu">
            <i class="el-icon-document"></i>
            <span slot="title">Submissions</span>
          </el-menu-item>
          <el-menu-item index="Gradebook">
            <i class="el-icon-collection"></i>
            <span slot="title">Gradebook</span>
          </el-menu-item>
          <div style="height: 70px"></div>
        </div>

        <div v-if="showTchProjMenu()" style="padding-top: 10px">
          <el-menu-item index="ProjOverview">
            <i class="el-icon-s-promotion"></i>
            <span slot="title">Project Info</span>
          </el-menu-item>

          <el-menu-item index="StdManage" >
            <i class="el-icon-s-order"></i>
            <span slot="title">Students</span>
          </el-menu-item>
          <el-menu-item index="TeamManaging" >
            <i class="el-icon-connection"></i>
            <span slot="title">Team Managing</span>
          </el-menu-item>
          <el-menu-item index="AnnouncementTch" >
            <i class="el-icon-bell"></i>
            <span slot="title">Announcements</span>
          </el-menu-item>
          <el-menu-item index="TchEvent" >
            <i class="el-icon-date"></i>
            <span slot="title">Events</span>
          </el-menu-item>
          <el-menu-item index="@5">
            <i class="el-icon-folder-opened"></i>
            <span slot="title">Resources</span>
          </el-menu-item>
          <el-menu-item index="SubmissionTch">
            <i class="el-icon-document"></i>
            <span slot="title">Submissions</span>
          </el-menu-item>
          <el-menu-item index="Gradebook">
            <i class="el-icon-collection"></i>
            <span slot="title">Gradebook</span>
          </el-menu-item>
          <div style="height: 70px"></div>
        </div>


      </el-menu>
      <!--    <el-radio-button :label="false">Open</el-radio-button>-->
      <!--    <el-radio-button :label="true">Close</el-radio-button>-->
      <!--  <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
      <!--      <el-radio-button :label="false">展开</el-radio-button>-->
      <!--      <el-radio-button :label="true">收起</el-radio-button>-->


      <el-radio-group v-model="isCollapse">
        <el-button circle v-show="!isCollapse" class="el-icon-s-fold" @click="isCollapse=true"></el-button>
        <el-button circle v-show="isCollapse" class="el-icon-s-unfold" @click="isCollapse=false"></el-button>
      </el-radio-group>
    </el-scrollbar>
  </div>
</template>

<script>
  import logo from '@/assets/xproject-icon-v3-1.png'
  import {hasProjState} from "@/utils/project";
  import {isStudent, isTeacher} from "@/utils/role";
  export default {
    data () {
      return {
        isCollapse: false,
        show: false,
        logo,
      }
    },
    methods: {
      showRoster () {
        return isTeacher()
      },
      showStdProjMenu () {
        return hasProjState() && isStudent()
      },
      showTchProjMenu () {
        return hasProjState() && isTeacher()
      },
      handleOpen (key, keyPath) {
        console.log(key, keyPath)
      },
      handleClose (key, keyPath) {
        console.log(key, keyPath)
      },
      handleSelect (key, keyPath) {
        console.log(key, keyPath)
        if (key.indexOf("@") !== -1) {
          console.log('Not finished')
        } else {
          let projId = this.$store.state.proj.projId;
          this.$router.push({name: key, params: {proj_id: projId}})
        }
      }
    }
  }
</script>

<style scoped>
  #left-bar-div {
    height: 100%;
  }
#left-bar{
  text-align: left;
  /*background: blueviolet;*/
  height: 100%;
  background: #303f55;
}

.el-radio-group {
  position: absolute;
  right: 12px;
  bottom: 4%;
  line-height: 100%;
  color: #151d41;
}

.el-radio-group > .el-button {
  font-size: 16px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
  margin: 0;
  padding: 0;
  text-align: left;
}
#title{
  background-color: #303f55;
  color: white;
  font-size: 20px;
  height: 60px;
}
  #proj-title {
    font-size: 17px;
    padding: 10px 0 10px 0;
    background-color: #f8f8f8;
  }
  .img {
    display: inline-block;
    vertical-align: middle;
    width: 30px;
    margin-right: 12px;
    cursor: default;
  }

  .el-scrollbar {
    height: 100%;
    background: #303f55;
    overflow-x: hidden;
  }

  .el-scrollbar >>> .el-scrollbar__wrap {
    overflow-x: hidden;
    height: 100%;
  }
</style>
