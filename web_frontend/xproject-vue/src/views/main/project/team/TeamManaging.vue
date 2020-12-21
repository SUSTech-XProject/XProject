<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Team Management</span>
    </div>

    <div id="card-body">
      <div style="text-align: right;padding-right: 30px">
        <el-button type="primary" plain
                   icon="el-icon-circle-plus-outline" @click="addTeam">Create
        </el-button>
        <el-button type="danger" plain
                   icon="el-icon-delete" @click="deleteTeam">Delete
        </el-button>
        <el-button type="success" plain
                   icon="el-icon-circle-check" @click="confirmTeam">Confirm
        </el-button>
        <el-button type="warning" plain
                   icon="el-icon-edit" @click="manageTeam">Manage
        </el-button>
      </div>

      <el-card style="margin: 15px 0;height: 100%">
        <el-table
          height="415"
          ref="multipleTable"
          v-loading="tableLoading"
          :data="tableData"
          empty-text="No Data Found"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <!--          <el-table-column-->
          <!--            prop = "index"-->
          <!--            label="Index"-->
          <!--            width="120"-->
          <!--            sortable>-->
          <!--          </el-table-column>-->
          <el-table-column prop="name" label="Team Name" sortable show-overflow-tooltip></el-table-column>
          <el-table-column
            prop="topic"
            label="Topic"
            sortable
            show-overflow-tooltip
            :filters="topicFilter"
            :filter-method="filterHandler">
          </el-table-column>
          <el-table-column
            prop="curMem"
            label="Current"
            width="150px"
            sortable>
          </el-table-column>
          <el-table-column
            prop="targetMemNum"
            label="Size"
            width="150px"
            sortable>
          </el-table-column>
          <el-table-column
            prop="status"
            label="Status"
            width="150px"
            :filters="teamStatus"
            :filter-method="teamStatusFMethod"
            sortable>
          </el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary" plain
                icon="el-icon-search"
                @click="openDrawer(scope.row.index)">Detail
              </el-button>
            </template>
          </el-table-column>
          <el-table-column>
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary" plain
                icon="el-icon-document-checked"
                @click="startScoring(scope.row)">Scoring
              </el-button>
            </template>
          </el-table-column>
        </el-table>
<!--        <footer>-->
<!--          <div class="block" style="text-align: center">-->
<!--            <el-pagination-->
<!--              layout="prev, pager, next"-->
<!--              :total="tableData.length">-->
<!--            </el-pagination>-->
<!--          </div>-->
<!--        </footer>-->
      </el-card>
    </div>

    <drawer :drawer.sync="drawerCtrl"
            :id="drawerId"
            @closeDrawer="closeDrawer"></drawer>

    <create :visible.sync="createVisible" @closeForm='closeForm'></create>

    <forming :visible.sync="formingVisible"
             :team-in="multipleSelection"
             @closeManaging="closeManaging"></forming>

    <el-drawer
      :title="drawerTitle"
      :visible.sync="scoringDrawerVisible"
      direction="rtl"
      :before-close="cancelScoring"
      size="80%">

      <div align="right" style="margin-right: 40px;">
        <el-button @click="cancelScoring">Cancel</el-button>
      </div>

      <el-table
        :data="gradeList"
        empty-text="No Data Found"
        :default-sort="{prop: 'index', order: 'increasing'}"
        style="width: 100%; margin-top: 20px;">
        <el-table-column type="expand">
          <template slot-scope="props">
            <div v-for="member in teamMemberList" :key="member.stdId">
              <el-avatar :fit="'fill'" :src="member.iconUrl"
                         style="vertical-align:middle; margin-right: 10px; cursor: pointer"
              ></el-avatar>
              <span style="vertical-align:middle;">{{ member.stdNo }}</span>
              <span style="vertical-align:middle; margin-left: 3px;">{{ member.stdName }}</span>

              <span v-if="props.row.type==='Point'">
                <el-input v-model="props.row.content"
                          style="width: 50px; margin-left: 20px;">
                </el-input>
                / {{ props.row.baseContent }}
              </span>

              <span v-else-if="props.row.type==='Grade'||props.row.type==='PF'">
                <el-select v-model="props.row.content" placeholder=""
                           style="width: 60px; margin-left: 20px;">
                  <el-option v-for="grade in gradeSelector" :key="grade.value"
                             :label="grade.label" :value="grade.value">
                  </el-option>
                </el-select>
              </span>

              <el-input v-model="props.row.comment"
                        placeholder="Comment here..."
                        style="width: 250px; margin-left: 20px;">
              </el-input>
            </div>

            <div align="right" style="margin-right: 40px;">
              <el-button type="primary"
                         @click="updateScore(props.row)">Scoring</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="" prop="index" width="50px" sortable/>
        <el-table-column label="Name" prop="rcdName" sortable/>
        <el-table-column label="Type" prop="type" sortable/>
        <el-table-column label="Creator: " prop="tchName" sortable/>
        <el-table-column label="Modified time" prop="modifiedTime"
                         :formatter="dateTimeFormatter" sortable/>
      </el-table>

    </el-drawer>
  </el-card>
</template>

<script>
import teamdrawer from '@/views/main/project/team/teamdrawer'
import {getMyTeamDetail, getPersonalMessage, getTeamDetail, getTeamInfoList, getTeamMessage} from '@/api/team'
import CreateTeam from '@/views/main/project/team/CreateTeam'
import {postTeamDeletion} from '@/api/team'
import {postTeamConfirmation} from '@/api/team'
import AutoForming from '@/views/main/project/team/AutoForming'
import {getDatetimeStr} from '@/utils/parse-day-time'
import {getGradeList, postNewGrade} from '@/api/grade'

export default {
  name: 'TeamManaging',
  components: {
    drawer: teamdrawer,
    create: CreateTeam,
    forming: AutoForming
  },
  data () {
    return {
      drawerCtrl: false,
      drawerId: 1,
      tableData: [],
      topicFilter: [],
      teamStatus: [
        {text: 'Raw', value: 'Raw'},
        {text: 'Confirm', value: 'Confirm'},
      ],
      tableLoading: false,
      createVisible: false,
      formingVisible: false,

      multipleSelection: [],
      scoringDrawerVisible: false,
      drawerTitle: '',
      scoringTeamMateList: [],
      gradeList: [
        {
          index: 1,
          rcdName: 'assignment1',
          modifiedTime: '12/01/2020 14:00',
          tchName: 'Dehua Liu',
          type: 'Point',
          content: '',
          baseContent: 100,
          comment: '1.2 no pic -2; 1.4 no text -2'
        },
        {
          index: 2,
          rcdName: 'assignment2',
          modifiedTime: '12/01/2020 14:00',
          tchName: 'Dehua Liu',
          type: 'Grade',
          content: '',
          baseContent: '',
          comment: ''
        },
        {
          index: 3,
          rcdName: 'assignment3',
          modifiedTime: '12/01/2020 14:00',
          tchName: 'Dehua Liu',
          type: 'Comment',
          content: '',
          baseContent: '',
          comment: ''
        },
      ],
      teamMemberList: [],
      gradeSelector: [
        {value: 'A', label: 'A'},
        {value: 'B', label: 'B'},
        {value: 'C', label: 'C'},
        {value: 'D', label: 'D'},
        {value: 'E', label: 'E'},
        {value: 'F', label: 'F'},
      ]
    }
  },
  mounted () {
    this.reLoad()
  },
  methods: {
    initTeams () {
      let id = this.$store.state.proj.projId
      //console.log(id)
      getTeamInfoList(parseInt(id)).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }

        let teamList = resp.data.data
        console.log(teamList)
        this.tableData.splice(0, this.tableData.length)
        this.topicFilter.splice(0, this.topicFilter.length)
        for (let i = 0; i < teamList.length; i++) {
          let team = teamList[i]
          this.tableData.push({
            index: team.projInstId,
            name: team.teamName,
            topic: team.topic,
            targetMemNum: team.targetMemNum,
            status: team.status,
            curMem: team.curMemNum
          })

          this.topicFilter.push({
            text: team.topic,
            value: team.topic
          })
        }
        this.topicFilter = this.unique(this.topicFilter)
        console.log(this.topicFilter)
      }).catch(failResp => {
        console.log('fail in getProjList. message=' + failResp.message)
      })
    },
    unique (arr) {
      let result = {}
      let finalResult = []
      for (let i = 0; i < arr.length; i++) {
        result[arr[i].value] = arr[i]
      }
      for (const item in result) {
        finalResult.push(result[item])
      }
      return finalResult
    },
    dateTimeFormatter (row, col) {
      // return getDatetimeStr(row.modifiedTime)
      return row.modifiedTime
    },

    openDrawer (val) {
      console.log(val)
      this.drawerId = val
      this.drawerCtrl = true
    },
    closeDrawer () {
      this.drawerCtrl = false
    },
    filterHandler (value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    teamStatusFMethod (value, row, column) {
      return value === row.status
    },

    reLoad () {
      this.tableLoading = true
      this.initTeams()
      setTimeout(() => {
        this.tableLoading = false
      }, 1000)

    },

    addTeam () {
      console.log(this.createVisible)
      this.createVisible = true
    },
    closeForm (val) {
      this.createVisible = false
      if (val === true) {
        //创建队伍重新挂载
        this.reLoad()
      }
    },
    manageTeam () {
      console.log(this.multipleSelection)
      this.formingVisible = true
      //调用组队表单？
    },
    closeManaging (val) {
      this.formingVisible = false
      if (val) {
        this.reLoad()
      }
    },

    deleteTeam () {
      if (this.multipleSelection.length === 0) {
        this.$message.error('No team selected yet')
      } else {
        this.$confirm('Delete selected teams?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          var list = []
          for (let i = 0; i < this.multipleSelection.length; i++) {
            list.push(parseInt(this.multipleSelection[i].index))
          }
          console.log(list)
          postTeamDeletion(list
          ).then(resp => {
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: 'Delete ' + num + ' teams successfully'
              })
              this.reLoad()
            } else {
              this.$message.error(resp.data.message)
            }
          }).catch(failResp => {
            this.$message.error('Back-end no response')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Canceled'
          })
        })
      }
    },
    confirmTeam () {
      console.log(this.multipleSelection)
      if (this.multipleSelection.length === 0) {
        this.$message.error('No team selected yet')
      } else {
        this.$confirm('Confirm selected teams?', 'Alert', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'info'
        }).then(() => {
          const list = []
          for (let i = 0; i < this.multipleSelection.length; i++) {
            list.push(parseInt(this.multipleSelection[i].index))
          }
          console.log(list)
          postTeamConfirmation(
            list
          ).then(resp => {
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: num + ' teams successfully'
              })
              this.reLoad()
            } else {
              this.$message.error(resp.data.message)
            }
          }).catch(failResp => {
            this.$message.error('Back-end no response')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Canceled'
          })
        })
      }
    },

    startScoring (team) {
      this.drawerTitle = 'Scoring ' + team.name
      this.scoringDrawerVisible = true

      getTeamDetail(team.index).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let team = resp.data.data

        // this.teamIntro = team.descriptions
        // this.teamTags = JSON.parse(team.tags)
        // this.teamName = team.teamName
        this.teamMemberList = team.teamMemberList
        console.log(this.teamMemberList)
        // this.teamSta = this.teamMembers.length + '/' + team.targetMemNum
        // this.teamTopic = team.topic
        // this.teamURL = team.iconUrl
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })

      // getGradeList(this.$store.state.proj.projId).then(resp => {
      //   if (resp.data.code !== 200) {
      //     this.$message.error(resp.data.code + '\n' + resp.data.message)
      //     return false
      //   }
      //   this.gradeList.splice(0, this.gradeList.length)   // remove all
      //   for (let i = 0; i < resp.data.data.length; i++) {
      //     let record = resp.data.data[i]
      //     record['listIdx'] = i
      //     this.gradeList.push(record)
      //   }
      //   console.log(this.gradeList)
      // }).catch(failResp => {
      //   console.log('fail in getGradeList. message=' + failResp.message)
      // })
    },
    updateScore (row) {
      // postNewGrade().then(resp => {
      //   if (resp.data.code === 200) {
      //     // getGradeList(this.$store.state.proj.projId).then(resp => {
      //     //   if (resp.data.code !== 200) {
      //     //     this.$message.error(resp.data.code + '\n' + resp.data.message)
      //     //     return false
      //     //   }
      //     //   this.gradeList.splice(0, this.gradeList.length)   // remove all
      //     //   for (let i = 0; i < resp.data.data.length; i++) {
      //     //     let record = resp.data.data[i]
      //     //     record['listIdx'] = i
      //     //     this.gradeList.push(record)
      //     //   }
      //     //   console.log(this.gradeList)
      //     // }).catch(failResp => {
      //     //   console.log('fail in getGradeList. message=' + failResp.message)
      //     // })
      //
      //     this.$message.success('Add success')
      //   } else if (resp.data.code === 400) {
      //     this.$message.error(resp.data.message)
      //   }
      // }).catch(failResp => {
      //   this.$message.error(failResp.message)
      // })
    },
    cancelScoring () {
      this.$confirm('Cancel scoring？')
        .then(_ => {
          this.scoringDrawerVisible = false
          // getGradeList(this.$store.state.proj.projId).then(resp => {
          //   if (resp.data.code !== 200) {
          //     this.$message.error(resp.data.code + '\n' + resp.data.message)
          //     return false
          //   }
          //   this.gradeList.splice(0, this.gradeList.length)   // remove all
          //   for (let i = 0; i < resp.data.data.length; i++) {
          //     let record = resp.data.data[i]
          //     record['listIdx'] = i
          //     this.gradeList.push(record)
          //   }
          //   console.log(this.gradeList)
          // }).catch(failResp => {
          //   console.log('fail in getGradeList. message=' + failResp.message)
          // })
          this.$message.info('Scoring canceled')
        })
    }
  }
}
</script>

<style scoped>
#base-card {
  margin: 15px 10px;
}

#title-text {
  font-size: 20px;
}

#card-body {

}
</style>
