<template>
  <el-card class="base-card">
    <el-col :span="5" :offset="2">
      <el-avatar :src="avatar"
                 :size="250" :fit="'fill'" style="margin-top: 20px"></el-avatar>

      <div style="font-size: 25px; font-weight:bold; margin-top: 10px;">
        {{ this.$store.state.role.username }}
      </div>

      <div v-if="isStudent()">
        <el-tag v-for="imp in impressionList" :key="imp"
                class="el-tag" effect="plain">
          {{ imp }}
        </el-tag>
      </div>

      <div style="width: 100%; margin-top: 20px" align="left">
        {{ bio }}
      </div>

      <div style="margin-top: 15px" class="handle-overflow">
        <i class="el-icon-office-building"> {{ this.school }}</i>
      </div>
      <div style="margin-top: 15px" class="handle-overflow">
        <i class="el-icon-location-outline"> {{ this.location }}</i>
      </div>
      <div style="margin-top: 15px" class="handle-overflow">
        <i class="el-icon-message"> {{ this.email }}</i>
      </div>
    </el-col>

    <el-col :span="14" :offset="1">
      <el-tabs :tab-position="tabPosition"
               type="card" v-model="activeName"
               style="height: 100%; margin-top: 10px">
        <el-tab-pane label="Overview" name="overview">
          <div style="margin-top: 10px">
            Recent project
          </div>

          <div v-if="firstThreeProjList.length===0"
               style="margin-top: 15px;">
            <div style="margin-top: 10px;">
              <el-row>
                <el-col :span="12">
                  <el-card class="box-card" shadow="never" align="center"
                           style="width: 95%; margin-top: 15px; min-height: 100px;">
                    <p></p>
                    <div style="font-size: 18px; color: #585858;">No data</div>
                    <p></p>
                  </el-card>
                </el-col>
              </el-row>
            </div>
          </div>

          <div v-else>
            <el-row>
              <el-col :span="12" v-for="proj in firstThreeProjList" :key="proj.projName">
                <el-card class="box-card" style="width: 95%; margin-top: 15px" shadow="never">
                  <div class="title">{{ proj.projName }}</div>
                  <p></p>
                  <div class="text">{{ proj.courseName }} {{ proj.createTime }}</div>
                </el-card>
              </el-col>
            </el-row>
          </div>

          <div v-if="isStudent()">
            <div style="margin-top: 20px">
              Skill List
            </div>
            <div v-if="skillList.length!==0">
              <el-tag v-for="skill in skillList" :key="skill"
                      effect="plain" class="el-tag">
                {{ skill }}
              </el-tag>
            </div>
            <div v-else style="margin-top: 20px;">
              <el-button class="button-new-tag" size="small">No tag</el-button>
            </div>

            <div style="margin-top: 20px">
              Comment List
            </div>
            <div v-if="commentList.length!==0">
              <el-tag v-for="comments in commentList" :key="comments"
                      effect="plain" class="el-tag">
                {{ comments }}
              </el-tag>
            </div>
            <div v-else
                 style="margin-top: 20px;">
              <el-button class="button-new-tag" size="small">No comment</el-button>
            </div>
          </div>

          <!--          <div style="margin-top: 40px">Statistic</div>-->
        </el-tab-pane>

        <el-tab-pane label="History" name="history">
          <el-row>
            <el-col :span="13">
              <el-input v-model="projNameFilter" placeholder="Find a project..."
                        style="width: 100%"></el-input>
            </el-col>

            <el-col :span="4" style="margin-left: 20px">
              <el-select v-model="valueYear" placeholder="">
                <el-option v-for="year in yearList" :key="year.value"
                           :label="'Year: '+year.label" :value="year.value">
                </el-option>
              </el-select>
            </el-col>

            <el-col :span="4" style="margin-left: 20px">
              <el-select v-model="valueCourse" placeholder="">
                <el-option v-for="course in courseList" :key="course.value"
                           :label="'Course: '+course.label" :value="course.value">
                </el-option>
              </el-select>
            </el-col>
          </el-row>

          <el-card class="box-card" style="width: 93%; margin-top: 20px" shadow="never"
                   v-for="proj in fullProjList" :key="proj.projName"
                   v-if="selectivelyDisplay(proj)">
            <div class="title">{{ proj.projName }}</div>
            <div class="text">{{ proj.description }}</div>
            <p></p>
            <div class="small-text">{{ proj.courseName }} {{ proj.createTime }}</div>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </el-col>
  </el-card>
</template>

<script>
import {getComments, getProjList} from '@/api/home_page'
import {isStudent, isTeacher} from '@/utils/role'
import {getAccountInfo} from '@/api/account'

export default {
  name: 'HomePage',
  components: {},
  data () {
    return {
      roleType: '',

      avatar: '',

      bio: '',
      school: '',
      location: '',
      email: '',

      //tag
      impressionList: [],
      skillList: [],

      //projList
      fullProjList: [],
      firstThreeProjList: [],

      //tab
      tabPosition: 'top',
      activeName: 'overview',

      //project history filter
      projNameFilter: '',
      projNameList: [],
      //year filter
      yearList: [{value: 0, label: 'All'}],
      valueYear: 0,
      //course filter
      courseList: [{value: 0, label: 'All'}],
      valueCourse: 0,

      commentList: []
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init home page')

      this.roleType = this.$store.state.role.roleType

      getAccountInfo().then(resp => {
        if (resp.data.code === 200) {
          console.log(resp.data)
          if (isStudent()) {
            let infoDict = resp.data.data.student

            this.bio = infoDict.bio

            this.school = resp.data.data.school.schName
            this.location = resp.data.data.school.location
            this.email = infoDict.email

            this.avatar = resp.data.data.role.iconUrl

            if (infoDict.flags != null) {
              this.impressionList = JSON.parse(infoDict.flags)
            }
            if (infoDict.skills != null) {
              this.skillList = JSON.parse(infoDict.skills)
            }
          } else if (isTeacher()) {
            let infoDict = resp.data.data.teacher

            this.avatar = resp.data.data.role.iconUrl
            this.email = infoDict.email
            this.school = resp.data.data.school.schName
            this.location = resp.data.data.school.location
          }
        } else if (resp.data.code === 400) {
          this.$message.error(resp.data.message)
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })

      getProjList().then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data

          for (let i = 0; i < infoDict.length; ++i) {
            let year = infoDict[i].createTime.substring(0, 4)

            if (!this.inYearList(year)) {
              let len = this.yearList.length
              this.yearList.push({value: len, label: year})
            }
            infoDict[i].createTime = year

            let projName = infoDict[i].projName
            this.projNameList.push(projName)

            let courseName = infoDict[i].courseName
            if (!this.inCourseList(courseName)) {
              let len = this.courseList.length
              this.courseList.push({value: len, label: courseName})
            }
          }

          this.fullProjList = infoDict
          for (let i = 0; i < infoDict.length; ++i) {
            if (i >= 3) break
            this.firstThreeProjList.push(infoDict[i])
          }

        } else if (resp.data.code === 400) {
          this.$message.error(resp.data.message)
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })

      getComments().then(resp => {
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }

        if (resp.data.data != null) {
          this.commentList = resp.data.data
        }
        console.log(resp.data.data)
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })
    },
    inYearList (year) {
      for (let i = 0; i < this.yearList.length; ++i) {
        if (year === this.yearList[i].label) {
          return true
        }
      }
      return false
    },
    inCourseList (courseName) {
      for (let i = 0; i < this.courseList.length; ++i) {
        if (courseName === this.courseList[i].label) {
          return true
        }
      }
      return false
    },
    selectivelyDisplay (proj) {
      let display = true

      if (this.projNameFilter !== '' &&
        proj.projName.toLowerCase().search(this.projNameFilter.toLowerCase()) === -1) {
        display = false
      }
      if (this.valueYear !== 0 &&
        proj.createTime !== this.yearList[this.valueYear].label) {
        display = false
      }
      if (this.valueCourse !== 0 &&
        proj.courseName !== this.courseList[this.valueCourse].label) {
        display = false
      }

      return display
    },
    gotoProjOverview (projId, projName) {
      let projRecord = {projId: projId, projName: projName}
      console.log('goto proj overview.')
      console.log(projRecord)
      this.$store.commit('setProj', projRecord)
      this.$router.push({name: 'ProjOverview', params: {proj_id: projId}})
    },
    isStudent () {
      return isStudent()
    }
  }
}
</script>

<style scoped>
/*project history card style*/
.text {
  font-size: 15px;
  color: #aaaaaa
}

.title {
  font-size: 18px
}

.small-text {
  font-size: 13px;
  color: #aaaaaa
}

.box-card {
  width: 480px;
}

.el-tag {
  margin-right: 10px;
  margin-top: 15px;
}

.el-select-dropdown .el-scrollbar >>> .el-scrollbar__wrap {
  overflow: scroll;
}

.base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

.handle-overflow {
  overflow: hidden;
  white-space: nowrap;
  /*text-overflow: ellipsis;*/
  width: 100%;
}
</style>
