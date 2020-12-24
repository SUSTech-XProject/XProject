<template>
  <el-card class="base-card" ref="out">
    <!--    <div style="font-size: 30px; font-weight: bold">{{ this.$store.state.proj.projName }}</div>-->

    <div v-if="isStudent()">
      <el-col style="width: 48.5%; margin-left: 0.75%; margin-top: 10px" class="el-tabs_item">
        <el-card style="height: 570px">
          <div slot="header" class="clearfix" style="font-weight: bold">Project Information</div>
          {{ this.siteInfo }}
        </el-card>
      </el-col>

      <el-col style="width: 48.5%; margin-left: 1.5%; margin-top: 10px;">
        <el-card style="height: 450px">
          <div slot="header" class="clearfix" style="font-weight: bold">Recent Announcements</div>
          <div v-if="this.annList.length===0">No Recent Announcements</div>
          <el-table
            v-else
            :data="annList"
            empty-text="No Data Found"
            :default-sort="{prop: 'index', order: 'increasing'}"
            style="width: 100%">
            <el-table-column label="Title" prop="title"/>
            <el-table-column label="Modified time" prop="modifiedTime"/>
            <el-table-column label="Creator" prop="tchName"/>
          </el-table>
        </el-card>
      </el-col>
    </div>

    <div v-if="isTeacher()">
      <el-tabs v-model="activeName" type="card"
               :tab-position="tabPosition" style="height: 100%">
        <el-tab-pane label="Overview" name="info" style="height: 100%">
          <el-col style="width: 48.5%; margin-left: 0.75%" class="el-tabs_item">
            <el-card style="height: 570px">
              <div slot="header" class="clearfix" style="font-weight: bold">Project Information</div>
              {{ this.siteInfo }}
            </el-card>
          </el-col>

          <el-col style="width: 48.5%; margin-left: 1.5%; margin-right: 0.65%">
            <el-card style="height: 450px">
              <div slot="header" class="clearfix" style="font-weight: bold">Recent Announcements</div>
              <div v-if="this.annList.length===0">No Recent Announcements</div>
              <el-table
                v-else
                :data="annList"
                empty-text="No Data Found"
                :default-sort="{prop: 'index', order: 'increasing'}"
                style="width: 100%">
                <el-table-column label="Title" prop="title"/>
                <el-table-column label="Modified time" prop="modifiedTime"/>
                <el-table-column label="Creator" prop="tchName"/>
              </el-table>
            </el-card>
          </el-col>
        </el-tab-pane>

        <el-tab-pane label="Settings" name="setting">
          <!--          <div style="font-size: 25px; font-weight:bold; margin-left: 20px; width: 85%">-->
          <!--            Settings-->
          <!--            <el-divider></el-divider>-->
          <!--          </div>-->

          <el-col :span="9">
            <div class="settingTitle" style="margin-left: 20px;">
              Project Information
            </div>
            <el-form style="margin-left: 20px; margin-top: 20px;"
                     label-width="auto">
              <el-form-item label="description">
                <el-input type="textarea" :rows="2" v-model="siteInfo"></el-input>
              </el-form-item>
              <el-form-item label="Year">
                <el-input v-model="newYear"></el-input>
              </el-form-item>
              <el-form-item label="Semester">
                <el-radio v-model="newSemester" label="01">Spring</el-radio>
                <el-radio v-model="newSemester" label="02">Fall</el-radio>
              </el-form-item>
            </el-form>

            <div style="margin-left: 20px">
              <el-button type="primary" @click="handleProjInfoUpdate">Update</el-button>
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
                <el-input v-model="topic.max_team_size" placeholder="Unlimited size"
                          style="width: 30%; margin-left: 10px"></el-input>
                <el-button @click.prevent="removeDomain(topic)" style="margin-left: 10px">delete</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleFormUpdate()">Update</el-button>
                <el-button @click="addDomain()">Add</el-button>
                <el-button @click="resetForm()">Reset</el-button>
              </el-form-item>
            </el-form>

            <div class="settingTitle" style="width: 85%">
              Access
            </div>
            <el-form style="margin-left: 20px; margin-top: 20px;"
                     label-width="auto">
              <el-form-item label="Student access">
                <el-switch v-model="stuAccess"></el-switch>
              </el-form-item>
              <el-form-item label="Teacher access">
                <el-switch v-model="tchAccess"></el-switch>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="updateAccess">Update</el-button>
                <el-button @click="resetAccess">Reset</el-button>
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
import {getAnnouncementList} from '@/api/announcement'
import {getDatetimeStr} from '@/utils/parse-day-time'

export default {
  name: 'ProjOverview',
  components: {},
  data () {
    return {
      //adding
      originAcc:{
        stuAccess:true,
        tchAccess:true,
      },
      stuAccess:true,
      tchAccess:true,

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
      initTopicBo: [],

      year: '',
      semester: '',
      newYear: '',
      newSemester: '',

      annList: []
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    //adding
    resetAccess(){
      this.stuAccess = this.originAcc.stuAccess
      this.tchAccess = this.originAcc.tchAccess
    },
    updateAccess(){
      this.$confirm('Confirm to update?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let projectVO = {
          'courseName': null,
          'description': null,
          'projId': this.$store.state.proj.projId,
          'projName': null,
          'projSettings': null,
          'term': null,
          'topics': null,
          'stdJoin':this.stuAccess,
          'tchJoin':this.tchAccess,
        }

        postProjectOverview(projectVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.originAcc.stuAccess = this.stuAccess
            this.originAcc.tchAccess = this.tchAccess

            this.$message.success('Update success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Update canceled')
      })
    },

    init () {
      console.log('init proj overview page')
      getProjOverview(this.$store.state.proj.projId).then(resp => {
        console.log(resp)
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }

        let infoDict = resp.data.data
        console.log(infoDict)

        this.originAcc.stuAccess = infoDict.stdJoin
        this.stuAccess = infoDict.stdJoin
        this.originAcc.tchAccess = infoDict.tchJoin
        this.tchAccess = infoDict.tchJoin

        this.initSiteInfo = infoDict.description
        this.siteInfo = this.initSiteInfo

        let term = infoDict.term
        this.year = term.substring(0, 4)
        this.semester = term.substring(4)
        this.newYear = this.year
        this.newSemester = this.semester

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
        console.log(this.topicBO)
        for (let i = 0; i < this.topicBO.length; ++i) {
          if (parseInt(this.topicBO[i].max_team_size) === -1) {
            this.topicBO[i].max_team_size = ''
          }
        }
        this.initTopicBo = JSON.parse(infoDict.topics)
        for (let i = 0; i < this.initTopicBo.length; ++i) {
          if (parseInt(this.initTopicBo[i].max_team_size) === -1) {
            this.initTopicBo[i].max_team_size = ''
          }
        }

      }).catch(failResp => {
        this.$message.error(failResp.message)
        console.log(failResp)
      })

      getAnnouncementList(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$message(resp.data.message)
          return false
        }

        let cnt = resp.data.data.length > 3 ? 3 : resp.data.data.length

        resp.data.data.sort(function (a, b) {
          return Date.parse(b.modifiedTime) - Date.parse(a.modifiedTime)
        })

        this.annList.splice(0, this.annList.length)
        for (let i = 0; i < cnt; ++i) {
          resp.data.data[i].modifiedTime = getDatetimeStr(resp.data.data[i].modifiedTime)
          resp.data.data[i].createdTime = getDatetimeStr(resp.data.data[i].createdTime)
          this.annList.push(resp.data.data[i])
        }
      }).catch(failResp => {
        console.log('fail in getAnnouncementList. message=' + failResp.message)
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
      this.$confirm('Confirm to update?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let projectVO = {
          'courseName': null,
          'description': null,
          'projId': this.$store.state.proj.projId,
          'projName': null,
          'projSettings': JSON.stringify(this.form),
          'term': null,
          'topics': null,
          'stdJoin':null,
          'tchJoin':null
        }

        postProjectOverview(projectVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.initForm = this.form

            this.$message.success('Update success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Update canceled')
      })
    },
    handleRecruitReset () {
      this.form.use_recruit = this.initForm.use_recruit
      this.form.min_size = this.initForm.min_size
      this.form.max_size = this.initForm.max_size
      this.form.due_time = this.initForm.due_time
      this.form.allow_cross_mark = this.initForm.allow_cross_mark
    },

    handleProjInfoUpdate () {
      this.$confirm('Confirm to update?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let projectVO = {
          'courseName': null,
          'description': this.siteInfo,
          'projId': this.$store.state.proj.projId,
          'projName': null,
          'projSettings': null,
          'term': this.newYear + this.newSemester,
          'topics': null,
          'stdJoin':null,
          'tchJoin':null
        }

        postProjectOverview(projectVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.initSiteInfo = this.siteInfo
            this.year = this.newYear
            this.semester = this.newSemester

            this.$message.success('Update success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Update canceled')
      })
    },
    handleSiteInfoReset () {
      this.siteInfo = this.initSiteInfo
      this.newYear = this.year
      this.newSemester = this.semester
    },

    //dynamic form
    handleFormUpdate () {
      this.$confirm('Confirm to update?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        for (let i = 0; i < this.topicBO.length; i++) {
          let topic = this.topicBO[i]
          if (topic.max_team_size !== '' && !topic.max_team_size.match(/^\d+$/)) {
            this.$message.error('Max team size is neither empty nor integer')
            return false
          }
          if (!topic.topic_name) {
            this.$message.error('Team name can\'t be null')
            return false
          }
        }

        let projectVO = {
          'courseName': null,
          'description': null,
          'projId': this.$store.state.proj.projId,
          'projName': null,
          'projSettings': null,
          'term': null,
          'topics': JSON.stringify(this.topicBO),
          'stdJoin':null,
          'tchJoin':null
        }

        postProjectOverview(projectVO).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.initTopicBo = []
            for (let i = 0; i < this.topicBO.length; ++i) {
              this.initTopicBo.push(({
                topic_name: this.topicBO[i].topic_name,
                max_team_size: this.topicBO[i].max_team_size
              }))
            }
            this.$message('Update success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Update canceled')
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
  min-height: 95.2%;
}

.settingTitle {
  font-size: 22px;
  font-weight: bold;
}

/*tab*/
.el-tabs_item {
  height: 590px;
}

.form-content-style {
  margin-left: 10px;
  width: 80%;
}
</style>


<!--          <el-col style="width: 49.6%; margin-top: 20px">-->
<!--            <el-card style="height: 630px">-->
<!--              <div slot="header" class="clearfix">Project Information</div>-->
<!--            </el-card>-->
<!--          </el-col>-->
<!--          <el-col style="width: 49.6%; margin-left: 0.8%; margin-top: 20px">-->
<!--            <el-card style="height: 400px">-->
<!--              <div slot="header" class="clearfix">Recent Announcements</div>-->
<!--            </el-card>-->
<!--          </el-col>-->
