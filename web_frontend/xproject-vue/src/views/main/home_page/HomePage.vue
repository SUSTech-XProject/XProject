<template>
  <el-card class="base-card" style="min-height: 95.7%;">
    <el-col :span="4" :offset="2">
      <el-avatar src="https://ww4.sinaimg.cn/thumb150/006GJQvhgy1fxwx1568khj3036034mx2.jpg"
                 :size="240" :fit="'fill'"></el-avatar>

      <div>{{ this.$store.state.role.username }}</div>

      <div style="margin-top: 15px" v-if="this.roleType==='S'">
        <!--                <el-tag>hardworking</el-tag>-->
        <!--                <el-tag type="success">efficient</el-tag>-->
        <!--                <el-tag type="danger">earnest</el-tag>-->
        <el-tag v-for="tag in impressionList" :key="tag.name"
                :type="skill.type" class="el-tag" effect="plain">
          {{ tag.name }}
        </el-tag>
      </div>

      <div style="width: 100%; margin-top: 20px" align="left">
        {{ bio }}
      </div>

      <div style="margin-top: 15px"><i class="el-icon-office-building"> {{ this.company }}</i></div>
      <div style="margin-top: 15px"><i class="el-icon-location-outline"> {{ this.location }}</i></div>
      <div style="margin-top: 15px"><i class="el-icon-message"> {{ this.email }}</i></div>
    </el-col>

    <el-col :span="15" :offset="1">
      <el-tabs :tab-position="tabPosition" type="card" style="height: 100%; margin-top: 20px" v-model="activeName">
        <el-tab-pane label="Overview" name="overview">
          <div style="margin-top: 10px">Recent project</div>
          <el-row>
            <el-col :span="12" v-for="proj in firstThreeProjList" :key="proj.projName">
              <el-card class="box-card" style="width: 90%; margin-top: 20px" shadow="never">
                <div class="title">{{ proj.projName }}</div>
                <p></p>
                <div class="text">{{ proj.courseName }} {{ proj.createTime }}</div>
              </el-card>
            </el-col>
          </el-row>

          <div v-if="this.roleType==='S'">
            <div style="margin-top: 40px">Skill List</div>
            <div style="margin-top: 15px">
              <!--            <el-tag>Java</el-tag>-->
              <!--            <el-tag type="success">cpp</el-tag>-->
              <!--            <el-tag type="danger">Spring Boot</el-tag>-->
              <!--            <el-tag>vue</el-tag>-->
              <!--            <el-tag type="success">UI Design</el-tag>-->
              <el-tag v-for="skill in skillList" :key="skill.name"
                      :type="skill.type" effect="plain" class="el-tag">
                {{ skill.name }}
              </el-tag>
            </div>
          </div>

          <div style="margin-top: 40px">Statistic</div>
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
                   v-if="selectivelyDisplay(proj)" @click.native="gotoProjOverview(proj.projId, proj.name)">
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
import {getProjList, getUserHomeInfo} from '@/api/home_page'

export default {
  name: 'HomePage',
  components: {},
  data () {
    return {
      roleType: 'S',

      bio: '',
      company: '',
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
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      console.log('init home page')

      this.roleType = this.$store.state.role.roleType

      getUserHomeInfo().then(resp => {
        if (resp.data.code === 200) {
          let infoDict = resp.data.data

          this.bio = infoDict
          // this.bio = infoDict.bio
          // this.company=infoDict.company
          // this.location=infoDict.location
          // this.email = infoDict.email
          //
          // let impressionList = infoDict.impressionList
          // for (let i = 0; i < impressionList.length; ++i) {
          //   this.impressionList.push({name: impressionList[i].name, type: ''})
          // }
          //
          // let skillList = infoDict.skillList
          // for (let i = 0; i < skillList.length; ++i) {
          //   this.impressionList.push({name: skillList[i].name, type: ''})
          // }
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
  font-size: 20px
}

.small-text {
  font-size: 13px;
  color: #aaaaaa
}

.box-card {
  width: 480px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.el-select-dropdown .el-scrollbar >>> .el-scrollbar__wrap {
  overflow: scroll;
}

.base-card {
  margin: 15px 10px
}
</style>
