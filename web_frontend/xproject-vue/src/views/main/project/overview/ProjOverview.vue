<!--YZX-->
<!--Overview page for proj-->

<template>
  <el-card class="base-card" ref="out">
<!--    <div style="font-size: 30px; font-weight: bold">{{ this.$store.state.proj.projName }}</div>-->

        <div v-if="isStudent()">
          <el-col style="width: 49%; margin-left: 0.6%; margin-top: 10px" class="el-tabs_item">
            <el-card style="height: 670px">
              <div slot="header" class="clearfix" style="font-weight: bold">Site Information</div>
              {{ this.siteInfo }}
            </el-card>
          </el-col>

          <el-col style="width: 49%; margin-left: 0.8%; margin-top: 10px;">
            <el-card style="height: 450px">
              <div slot="header" class="clearfix" style="font-weight: bold">Recent Announcements</div>
              <!--              TODO: Recent Announcements-->
              <div v-if="this.firstThreeAnnoList.length===0">No Recent Announcements</div>
              <div v-else v-for="anno in this.firstThreeAnnoList" :key="anno" style="margin-bottom: 20px">
              </div>
            </el-card>
          </el-col>
        </div>

    <div v-if="isTeacher()">
      <el-tabs v-model="activeName" type="card"
               :tab-position="tabPosition" style="height: 100%">
        <el-tab-pane label="Overview" name="info" style="height: 100%">
          <el-col style="width: 49%; margin-left: 0.6%" class="el-tabs_item">
            <el-card style="height: 670px">
              <div slot="header" class="clearfix" style="font-weight: bold">Site Information</div>
              {{ this.siteInfo }}
            </el-card>
          </el-col>

          <el-col style="width: 49%; margin-left: 1.4%;">
            <el-card style="height: 450px">
              <div slot="header" class="clearfix" style="font-weight: bold">Recent Announcements</div>
              <!--              TODO: Recent Announcements-->
              <div v-if="this.firstThreeAnnoList.length===0">No Recent Announcements</div>
              <div v-else v-for="anno in this.firstThreeAnnoList" :key="anno" style="margin-bottom: 20px">
              </div>
            </el-card>
          </el-col>
        </el-tab-pane>

        <el-tab-pane label="Settings" name="setting">
          <div style="font-size: 25px; font-weight:bold; margin-left: 20px; width: 85%">
            Settings
            <el-divider></el-divider>
          </div>

          <div class="settingTitle" style="margin-left: 20px; width: 85%">
            Site Information
          </div>
          <el-input type="textarea" :rows="5" placeholder="" v-model="siteInfo"
                    style="margin-left: 20px; margin-top: 20px; width: 70%">
          </el-input>

          <div class="settingTitle" style="margin-left: 20px; width: 85%; margin-top: 20px">
            Recruit
          </div>
          <div style="margin-left: 20px; width: 85%; margin-top: 10px;">
            <el-form ref="form" :model="form" label-width="auto">
              <el-form-item label="Use recruit system">
                <el-switch v-model="form.useRecruitSystem" class="form-content-style"></el-switch>
              </el-form-item>
              <el-form-item label="Minimum size">
                <el-input v-model="form.minSize" class="form-content-style"></el-input>
              </el-form-item>
              <el-form-item label="Maximum size">
                <el-input v-model="form.maxSize" class="form-content-style"></el-input>
              </el-form-item>
              <el-form-item label="Due time">
                <el-date-picker type="date" placeholder="Choose one date" v-model="form.dueTime"
                                class="form-content-style"></el-date-picker>
              </el-form-item>
              <el-form-item label="Allow cross-shift teaming">
                <el-switch v-model="form.allowCrossShiftTeaming" class="form-content-style"></el-switch>
              </el-form-item>
              <el-form-item style="margin-left: 10px">
                <el-button type="primary" @click="handleUpdate">Update</el-button>
                <el-button @click="handleReset">Reset</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>

      </el-tabs>
    </div>
  </el-card>
</template>

<script>
import {getUserHomeInfo} from '@/api/home_page'
import {isStudent, isTeacher} from '@/utils/role'
import {getAccountInfo} from '@/api/account'
import {getProjOverviewStudent, getProjOverviewTeacher, postProjectOverview} from '@/api/proj_overview'

export default {
  name: 'ProjOverview',
  components: {},
  data () {
    return {
      //tab pane
      activeName: 'info',
      tabPosition: 'left',

      //el-form
      form: {
        useRecruitSystem: false,
        minSize: '',
        maxSize: '',
        dueTime: '',
        allowCrossShiftTeaming: true
      },
      initForm: {
        useRecruitSystem: false,
        minSize: '',
        maxSize: '',
        dueTime: '',
        allowCrossShiftTeaming: true
      },
      siteInfo: 'Digital logic A Course Project',
      initSiteInfo: 'Digital logic A Course Project',
      firstThreeAnnoList: []
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init proj overview page')
      // if(isStudent()){
      //   getProjOverviewStudent().then(resp => {
      //     if (resp.data.code === 200) {
      //
      //     } else if (resp.data.code === 400) {
      //       console.log(resp.data.message)
      //       this.$alert(resp.data.message, 'Tip', {
      //         confirmButtonText: 'OK'
      //       })
      //     }
      //   }).catch(failResp => {
      //     this.$alert('Error: ' + failResp.message, 'Tips', {
      //       confirmButtonText: 'OK'
      //     })
      //   })
      // }else if(isTeacher()){
      //   getProjOverviewTeacher().then(resp => {
      //     if (resp.data.code === 200) {
      //
      //     } else if (resp.data.code === 400) {
      //       console.log(resp.data.message)
      //       this.$alert(resp.data.message, 'Tip', {
      //         confirmButtonText: 'OK'
      //       })
      //     }
      //   }).catch(failResp => {
      //     this.$alert('Error: ' + failResp.message, 'Tips', {
      //       confirmButtonText: 'OK'
      //     })
      //   })
      // }
    },
    isStudent () {
      return isStudent()
    },
    isTeacher () {
      return isTeacher()
    },

    //el-form
    handleUpdate () {
      // postProjectOverview(
      //   this.form.useRecruitSystem,
      //   this.form.minSize,
      //   this.form.maxSize,
      //   this.form.allowCrossShiftTeaming
      // ).then(resp => {
      //   console.log('get response : ' + resp)
      //   if (resp.data.code === 200) {
      //     this.$alert('Update success', 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   } else if (resp.data.code === 400) {
      //     console.log(resp.data.message)
      //     this.$alert(resp.data.message, 'Tip', {
      //       confirmButtonText: 'OK'
      //     })
      //   }
      // }).catch(failResp => {
      //   this.$alert('Error ' + failResp.message, 'Tip', {
      //     confirmButtonText: 'OK'
      //   })
      // })
      this.initForm = this.form
      this.initSiteInfo = this.siteInfo
    },
    handleReset () {
      console.log('reset')
      this.form.useRecruitSystem = this.initForm.useRecruitSystem
      this.form.minSize = this.initForm.minSize
      this.form.maxSize = this.initForm.maxSize
      this.form.allowCrossShiftTeaming = this.initForm.allowCrossShiftTeaming

      this.siteInfo = this.initSiteInfo
    }
  }
}
</script>

<style scoped>
.base-card {
  margin: 15px 10px;
  min-height: 95.7%;
}

.settingTitle {
  font-size: 22px;
  font-weight: bold;
}

/*el-form*/
.form-item-height >>> .el-form-item__label {
  font-weight: bold;
  text-align: left;
}

.form-item-height >>> .el-form-item__content {
  height: 20px;
}

/*tab*/
.el-tabs_item {
  height: 680px;
}

.form-content-style {
  margin-left: 10px;
  width: 200px;
}
</style>


<!--          <el-col style="width: 49.6%; margin-top: 20px">-->
<!--            <el-card style="height: 630px">-->
<!--              <div slot="header" class="clearfix">Site Information</div>-->
<!--            </el-card>-->
<!--          </el-col>-->
<!--          <el-col style="width: 49.6%; margin-left: 0.8%; margin-top: 20px">-->
<!--            <el-card style="height: 400px">-->
<!--              <div slot="header" class="clearfix">Recent Announcements</div>-->
<!--            </el-card>-->
<!--          </el-col>-->
