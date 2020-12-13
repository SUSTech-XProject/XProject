<template>
  <el-card class="base-card" ref="out">
    <!--    <div style="font-size: 30px; font-weight: bold">{{ this.$store.state.proj.projName }}</div>-->

    <div v-if="isStudent()">
      <el-col style="width: 48.5%; margin-left: 0.75%; margin-top: 10px" class="el-tabs_item">
        <el-card style="height: 670px">
          <div slot="header" class="clearfix" style="font-weight: bold">Site Information</div>
          {{ this.siteInfo }}
        </el-card>
      </el-col>

      <el-col style="width: 48.5%; margin-left: 1.5%; margin-top: 10px;">
        <el-card style="height: 450px">
          <div slot="header" class="clearfix" style="font-weight: bold">Recent Announcements</div>
          <!--              TODO: Recent Announcements-->
          <div v-if="this.firstThreeAnnoList.length===0">No Recent Announcements</div>
          <div v-else v-for="(anno,index) in this.firstThreeAnnoList" :key="index" style="margin-bottom: 20px">
          </div>
        </el-card>
      </el-col>
    </div>

    <div v-if="isTeacher()">
      <el-tabs v-model="activeName" type="card"
               :tab-position="tabPosition" style="height: 100%">
        <el-tab-pane label="Overview" name="info" style="height: 100%">
          <el-col style="width: 48.5%; margin-left: 0.75%" class="el-tabs_item">
            <el-card style="height: 670px">
              <div slot="header" class="clearfix" style="font-weight: bold">Site Information</div>
              {{ this.siteInfo }}
            </el-card>
          </el-col>

          <el-col style="width: 48.5%; margin-left: 1.5%; margin-right: 0.65%">
            <el-card style="height: 450px">
              <div slot="header" class="clearfix" style="font-weight: bold">Recent Announcements</div>
              <!--              TODO: Recent Announcements-->
              <div v-if="this.firstThreeAnnoList.length===0">No Recent Announcements</div>
              <div v-else v-for="(anno,index) in this.firstThreeAnnoList" :key="index" style="margin-bottom: 20px">
              </div>
            </el-card>
          </el-col>
        </el-tab-pane>

        <el-tab-pane label="Settings" name="setting">
          <div style="font-size: 25px; font-weight:bold; margin-left: 20px; width: 85%">
            Settings
            <el-divider></el-divider>
          </div>

          <el-col :span="9">
            <div class="settingTitle" style="margin-left: 20px;">
              Site Information
            </div>
            <el-input type="textarea" :rows="4" v-model="siteInfo"
                      style="margin-left: 20px; margin-top: 20px; width: 90%">
            </el-input>
            <div style="margin-top: 25px; margin-left: 20px">
              <el-button type="primary" @click="handleSiteInfoUpdate">Update</el-button>
              <el-button @click="handleSiteInfoReset">Reset</el-button>
            </div>

            <div class="settingTitle" style="margin-left: 20px; width: 85%; margin-top: 20px">
              Recruit
            </div>
            <div style="margin-left: 20px; width: 85%; margin-top: 10px;">
              <el-form ref="form" :model="form" label-width="auto">
                <el-form-item label="Use recruit system">
                  <el-switch v-model="form.use_recruit" class="form-content-style"></el-switch>
                </el-form-item>
                <el-form-item label="Minimum size">
                  <el-input v-model="form.min_size" class="form-content-style"></el-input>
                </el-form-item>
                <el-form-item label="Maximum size">
                  <el-input v-model="form.max_size" class="form-content-style"></el-input>
                </el-form-item>
                <el-form-item label="Due time">
                  <el-date-picker type="date" placeholder="Choose one date" v-model="form.due_time"
                                  class="form-content-style"></el-date-picker>
                </el-form-item>
                <el-form-item label="Allow cross-shift teaming">
                  <el-switch v-model="form.allow_cross_mark" class="form-content-style"></el-switch>
                </el-form-item>
              </el-form>
              <div style="margin-top: -5px;">
                <el-button type="primary" @click="handleRecruitUpdate">Update</el-button>
                <el-button @click="handleRecruitReset">Reset</el-button>
              </div>
            </div>
          </el-col>

          <el-col :span="10" :offset="1">
            <div class="settingTitle" style="width: 85%">
              Project Topics
            </div>

            <el-form ref="topicDict" label-width="auto" class="demo-dynamic">
              <el-form-item
                v-for="(topic, index) in topicBO"
                :label="'Topic ' + index"
                :key="index"
                :prop="'topic.' + index + '.value'"
                style="margin-top: 20px"
              >
                <!--                :rules="{required: true, message: 'Information can not be empty', trigger: 'blur'}"-->
                <el-input v-model="topic.topic_name" placeholder="topic name"
                          style="width: 40%; margin-left: 10px"></el-input>
                <el-input v-model="topic.max_team_size" placeholder="empty when unlimited"
                          style="width: 30%; margin-left: 10px"></el-input>
                <el-button @click.prevent="removeDomain(topic)" style="margin-left: 10px">delete</el-button>
              </el-form-item>
              <el-form-item>
                <el-button @click="addDomain()">Add</el-button>
                <el-button type="primary" @click="submitForm('topicDict')">Update</el-button>
                <el-button @click="resetForm()">reset</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-tab-pane>

      </el-tabs>
    </div>
  </el-card>
</template>

<script>
import {getUserHomeInfo} from '@/api/home_page'
import {isStudent, isTeacher} from '@/utils/role'
import {getAccountInfo} from '@/api/account'
import {getProjOverview, postProjectOverview} from '@/api/proj_overview'

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
        use_recruit: false,
        min_size: '',
        max_size: '',
        due_time: '',
        allow_cross_mark: true
      },
      initForm: {
        use_recruit: false,
        min_size: '',
        max_size: '',
        due_time: '',
        allow_cross_mark: true
      },

      siteInfo: '',
      initSiteInfo: '',

      firstThreeAnnoList: [],

      topicBO: [],
      initTopicBo: []
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init proj overview page')
      getProjOverview(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data

          this.initSiteInfo = infoDict.description
          this.siteInfo = this.initSiteInfo

          let settings = JSON.parse(infoDict.projSettings)
          this.initForm.use_recruit = settings.use_recruit
          this.initForm.min_size = parseInt(settings.min_size)
          this.initForm.max_size = parseInt(settings.max_size)
          this.initForm.due_time = settings.due_time
          this.initForm.allow_cross_mark = settings.allow_cross_mark

          this.form.use_recruit = settings.use_recruit
          this.form.min_size = parseInt(settings.min_size)
          this.form.max_size = parseInt(settings.max_size)
          this.form.due_time = settings.due_time
          this.form.allow_cross_mark = settings.allow_cross_mark

          this.topicBO = JSON.parse(infoDict.topics)
          this.initTopicBo = JSON.parse(infoDict.topics)
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error: ' + failResp.message, 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    },
    isStudent () {
      // return false
      return isStudent()
    },
    isTeacher () {
      // return true
      return isTeacher()
    },

    //el-form
    handleRecruitUpdate () {
      postProjectOverview(
        this.$store.state.proj.projId, this.initSiteInfo,
        JSON.stringify(this.initTopicBo), JSON.stringify(this.form)
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.$alert('Update success', 'Tip', {
            confirmButtonText: 'OK'
          })
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error ' + failResp.message, 'Tip', {
          confirmButtonText: 'OK'
        })
      })
      this.initForm = this.form
    },
    handleRecruitReset () {
      this.form.use_recruit = this.initForm.use_recruit
      this.form.min_size = this.initForm.min_size
      this.form.max_size = this.initForm.max_size
      this.form.due_time = this.initForm.due_time
      this.form.allow_cross_mark = this.initForm.allow_cross_mark
    },

    handleSiteInfoUpdate () {
      postProjectOverview(
        this.$store.state.proj.projId, this.siteInfo,
        JSON.stringify(this.initTopicBo), JSON.stringify(this.initForm)
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.$alert('Update success', 'Tip', {
            confirmButtonText: 'OK'
          })
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error ' + failResp.message, 'Tip', {
          confirmButtonText: 'OK'
        })
      })
      this.initSiteInfo = this.siteInfo
    },
    handleSiteInfoReset () {
      this.siteInfo = this.initSiteInfo
    },

    //dynamic form
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          postProjectOverview(
            this.$store.state.proj.projId, this.initSiteInfo,
            JSON.stringify(this.topicBO), JSON.stringify(this.initForm)
          ).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.$alert('Update success', 'Tip', {
                confirmButtonText: 'OK'
              })
            } else if (resp.data.code === 400) {
              console.log(resp.data.message)
              this.$alert(resp.data.message, 'Tip', {
                confirmButtonText: 'OK'
              })
            }
          }).catch(failResp => {
            this.$alert('Error ' + failResp.message, 'Tip', {
              confirmButtonText: 'OK'
            })
          })

          this.initTopicBo = []
          for (let i = 0; i < this.topicBO.length; ++i) {
            this.initTopicBo.push(({
              topic_name: this.topicBO[i].topic_name,
              max_team_size: this.topicBO[i].max_team_size
            }))
          }
        } else {
          console.log('Topic information can not be empty')
          return false
        }
      })
    },
    resetForm () {
      this.topicBO = []
      for (let i = 0; i < this.initTopicBo.length; ++i) {
        this.topicBO.push(({
          topic_name: this.initTopicBo[i].topic_name,
          max_team_size: this.initTopicBo[i].max_team_size
        }))
      }
    },
    removeDomain (item) {
      let index = this.topicBO.indexOf(item)
      if (index !== -1) {
        this.topicBO.splice(index, 1)
      }
    },
    addDomain () {
      this.topicBO.push({
        topic_name: '',
        max_team_size: ''
      })
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

/*tab*/
.el-tabs_item {
  height: 680px;
}

.form-content-style {
  margin-left: 10px;
  width: 80%;
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

<!--// for (let i = 0; i < this.topicBO.length; ++i) {-->
<!--//   if (parseInt(this.topicBO[i].max_team_size) === -1) {-->
<!--//     this.topicBO[i].max_team_size = 'Unlimited'-->
<!--//   }-->
<!--// }-->
