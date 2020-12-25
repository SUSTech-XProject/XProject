<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Team Management</span>
    </div>

    <div id="card-body">
      <div style="display: flex; justify-content: flex-start; align-items: center;">
        <el-button type="primary" plain
                   icon="el-icon-circle-plus-outline" @click="addTeam">Create
        </el-button>
        <el-button type="danger" plain style="margin-right: auto"
                   icon="el-icon-delete" @click="deleteTeam">Delete
        </el-button>
        <el-button type="success" plain
                   icon="el-icon-circle-check" @click="dialogVisible=true">Confirm
        </el-button>
        <el-button type="info" plain
                   icon="el-icon-refresh-right" @click="cancelTeam">Cancel
        </el-button>

        <el-button type="warning" plain
                   icon="el-icon-edit" @click="manageTeam">Manage
        </el-button>
        <el-button type="primary" plain
                   icon="el-icon-download" @click="exportStd">Export
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

    <el-dialog
      title="Alert"
      :visible.sync="dialogVisible"
      width="25%"
    >
      <div style="margin-bottom: 20px">Please select confirm mode:</div>
      <el-switch
        v-model="forceConfirm"
        active-text="Force"
        inactive-text="Ordinary">
      </el-switch>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">Cancel</el-button>
      <el-button type="primary" @click="confirmTeam">Confirm</el-button>
    </span>
    </el-dialog>

    <el-drawer
      :title="drawerTitle"
      :visible.sync="scoringDrawerVisible"
      direction="rtl"
      :before-close="cancelScoring"
      size="80%">

      <div align="right" style="margin-right: 40px;">
        <el-button @click="cancelScoring">Cancel</el-button>
      </div>
      <el-card style="margin-top: 20px; margin-left: 1%; margin-right: 1%; min-height: 89%">
        <el-table
          :data="recordList"
          empty-text="No Data Found"
          row-key="record.rcdId"
          :expand-row-keys="expandRowList"
          :default-sort="{prop: 'index', order: 'increasing'}"
          style="width: 100%;"
          @expand-change="recordExpandChange">
          <el-table-column type="expand">
            <template slot-scope="props">
              <div v-for="inst in recordInstList" :key="inst.stdId" style="margin-bottom: 15px">
                <el-avatar :fit="'fill'" :src="inst.iconUrl"
                           style="vertical-align:middle; margin-right: 10px;"
                ></el-avatar>
                <span style="vertical-align:middle;">{{ inst.stdNo }}</span>
                <span style="vertical-align:middle; margin-left: 3px;">{{ inst.stdName }}</span>

                <span v-if="inst.type==='Point'">
                <el-input v-model="inst.content"
                          style="width: 100px; margin-left: 20px;">
                </el-input>
                / {{ inst.baseContent }}
              </span>

                <span v-else-if="inst.type==='Grade'||inst.type==='PF'">
                <el-select v-model="inst.content" placeholder=""
                           style="width: 60px; margin-left: 20px;">
                 <el-option v-for="grade in gradeSelector" :key="grade.value"
                            :label="grade.label" :value="grade.value">
                  </el-option>
                </el-select>
              </span>

                <el-input v-model="inst.comments"
                          placeholder="Comment here..."
                          style="width: 250px; margin-left: 20px;">
                </el-input>

                <div style="margin-top: 10px; margin-left: 20px;"
                     v-if="inst.modifiedTime==='' || inst.tchName==='' || inst.email===''">
                  Last Modified: {{ inst.modifiedTime }}, {{ inst.tchName }}, {{ inst.email }}
                </div>
                <div style="margin-top: 10px; margin-left: 20px;" v-else>
                  Last Modified: no record
                </div>
              </div>

              <div align="right" style="margin-right: 40px;">
                <el-button type="primary"
                           @click="updateScore(props.row)">Scoring
                </el-button>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="" type="index" width="50px"/>
          <el-table-column label="Name" prop="record.rcdName" sortable/>
          <el-table-column label="Type" prop="record.type"/>
          <el-table-column label="Creator" prop="creator.tchName"/>
          <el-table-column label="Created time" prop="record.createdTime"
                           :formatter="dateTimeFormatter" sortable/>
        </el-table>
      </el-card>
    </el-drawer>
  </el-card>
</template>

<script>
import teamdrawer from '@/views/main/project/team/teamdrawer'
import {getTeamExcel, getTeamInfoList, postTeamCancel} from '@/api/team'
import CreateTeam from '@/views/main/project/team/CreateTeam'
import {postTeamDeletion} from '@/api/team'
import {postTeamConfirmation} from '@/api/team'
import AutoForming from '@/views/main/project/team/AutoForming'
import {getDatetimeStr} from '@/utils/parse-day-time'
import {getAllRecord, getRecordInst, postNewGrade} from '@/api/grade'

export default {
  name: 'TeamManaging',
  components: {
    drawer: teamdrawer,
    create: CreateTeam,
    forming: AutoForming
  },
  data () {
    return {
      dialogVisible: false,
      forceConfirm: false,

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

      //
      recordList: [],
      teamMemberList: [],
      gradeSelector: [
        {value: 'A', label: 'A'},
        {value: 'B', label: 'B'},
        {value: 'C', label: 'C'},
        {value: 'D', label: 'D'},
        {value: 'E', label: 'E'},
        {value: 'F', label: 'F'},
      ],
      expandRowList: [],
      scoringTeamId: '',
      recordInstList: [],
      loading: true
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
      return getDatetimeStr(row.record.createdTime)
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
            this.forceConfirm,
            list
          ).then(resp => {
            if (resp.data.code === 200) {
              let num = resp.data.data

              this.$message({
                type: 'success',
                message: num,
                dangerouslyUseHTMLString: true,
              })
              this.reLoad()
              this.dialogVisible = false
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
    cancelTeam () {
      console.log(this.multipleSelection)
      if (this.multipleSelection.length === 0) {
        this.$message.error('No team selected yet')
      } else {
        this.$confirm('Cancel selected teams?', 'Alert', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'info'
        }).then(() => {
          const list = []
          for (let i = 0; i < this.multipleSelection.length; i++) {
            list.push(parseInt(this.multipleSelection[i].index))
          }
          console.log(list)
          postTeamCancel(
            list
          ).then(resp => {
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: num
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
      this.scoringTeamId = team.index

      getAllRecord(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.code + '\n' + resp.data.message)
          return false
        }

        this.recordList.splice(0, this.recordList.length)   // remove all
        this.recordList = resp.data.data
        console.log(this.recordList)
      }).catch(failResp => {
        console.log('fail in getGradeList. message=' + failResp.message)
      })
    },
    updateScore (row) {
      this.$confirm('Confirm to Scoring?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let recordInstUpdateParamVO = {
          'gradeList': [],
          'rcdId': row.record.rcdId
        }

        for (let i = 0; i < this.recordInstList.length; ++i) {
          let grade = {
            'comments': this.recordInstList[i].comments,
            'content': this.recordInstList[i].content,
            'roleId': this.recordInstList[i].roleId
          }
          recordInstUpdateParamVO.gradeList.push(grade)
        }

        postNewGrade(recordInstUpdateParamVO).then(resp => {
          if (resp.data.code === 200) {
            getRecordInst(
              this.scoringTeamId, row.record.rcdId
            ).then(resp => {
              if (resp.data.code !== 200) {
                this.$message.error(resp.data.code + '\n' + resp.data.message)
                return false
              }

              this.recordInstList = resp.data.data
              for (let i = 0; i < this.recordInstList.length; ++i) {
                if (this.recordInstList[i].modifiedTime != null) {
                  this.recordInstList[i].modifiedTime
                    = getDatetimeStr(this.recordInstList[i].modifiedTime)
                }
              }
              this.loading = false
              console.log(this.recordInstList)
            }).catch(failResp => {
              console.log('fail in getGradeList. message=' + failResp.message)
            })

            this.$message.success('Scoring success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Scoring canceled')
      })
    },
    cancelScoring () {
      this.$confirm('Cancel scoring？')
        .then(_ => {
          this.scoringDrawerVisible = false
          this.expandRowList.splice(0, this.expandRowList.length)
          this.$message.info('Scoring canceled')
        })
    },
    recordExpandChange (row, expandedRows) {
      if (expandedRows.length > 0) {
        // this.loading = true
        this.recordInstList.splice(0, this.recordInstList.length)
        this.expandRowList.splice(0, this.expandRowList.length)
        this.expandRowList.push(row.record.rcdId)

        getRecordInst(
          this.scoringTeamId, row.record.rcdId
        ).then(resp => {
          if (resp.data.code !== 200) {
            this.$message.error(resp.data.code + '\n' + resp.data.message)
            return false
          }

          this.recordInstList = resp.data.data
          for (let i = 0; i < this.recordInstList.length; ++i) {
            if (this.recordInstList[i].modifiedTime != null) {
              this.recordInstList[i].modifiedTime
                = getDatetimeStr(this.recordInstList[i].modifiedTime)
            }
          }
          this.loading = false
          console.log(this.recordInstList)
        }).catch(failResp => {
          console.log('fail in getGradeList. message=' + failResp.message)
        })
      }
    },

    exportStd () {
      window.open('http://localhost:8443/api/teacher/team/excel?projId=' + this.$store.state.proj.projId)
    },
  }
}
</script>

<style scoped>
#base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

#title-text {
  font-size: 20px;
}

#card-body {

}
</style>

<!--// getTeamExcel(projId).then(resp => {-->
<!--//   if (resp.data.code !== 200) {-->
<!--//     this.$message.error(resp.data.message)-->
<!--//     return false-->
<!--//   }-->
<!--// }).catch(failResp => {-->
<!--//   this.$message.error(failResp.message)-->
<!--// })-->

<!--<el-table-column>-->
<!--<template slot-scope="scope">-->
<!--  <el-button-->
<!--    size="mini"-->
<!--    type="primary" plain-->
<!--    icon="el-icon-document-checked"-->
<!--    @click="startScoring(scope.row)">Scoring-->
<!--  </el-button>-->
<!--</template>-->
<!--</el-table-column>-->
