<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Team Management</span>
    </div>
    <div style="text-align: right;padding-right: 30px">
      <el-button type="primary" plain
                 icon="el-icon-circle-plus-outline" @click="addTeam">Create</el-button>
      <el-button type="danger" plain
                 icon="el-icon-delete" @click="deleteTeam">Delete</el-button>
      <el-button type="success" plain
                 icon="el-icon-circle-check" @click="confirmTeam">Confirm</el-button>
      <el-button type="warning" plain
                 icon="el-icon-edit" @click="manageTeam">Manage</el-button>
    </div>
    <el-card style="margin: 15px 0">
      <el-table
        height="250"
        ref="multipleTable"
        v-loading = "tableLoading"
        :data="tableData"
        empty-text="No Data Found"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop = "index"
          label="Index"
          width="120"
          sortable>
        </el-table-column>
        <el-table-column
          prop="name"
          label="Team Name"
          sortable
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="topic"
          label="Topic"
          sortable
          show-overflow-tooltip
          :filters="topicFilter"
          :filter-method="filterHandler">
        </el-table-column>
        <el-table-column
          prop ="targetMem"
          label="Size"
          width="150px"
          sortable>
        </el-table-column>
        <el-table-column
          prop = "status"
          label="Status"
          width="150px"
          sortable>
        </el-table-column>
        <el-table-column>
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary" plain
              icon="el-icon-search"
              @click="openDrawer(scope.row.index)">Detail</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block" style="text-align: center">
        <el-pagination
          layout="prev, pager, next"
          :total="tableData.length">
        </el-pagination>
      </div>
    </el-card>

    <drawer :drawer.sync="drawerCtrl"
            :id="drawerId"
            @closeDrawer = "closeDrawer"></drawer>

    <create :visible.sync="formVisible" @closeForm = 'closeForm'></create>
  </el-card>
</template>

<script>
import teamdrawer from '@/views/main/project/team/teamdrawer'
import {getTeamInfoList} from '@/api/team'
import CreateTeam from '@/views/main/project/team/CreateTeam'
import {postTeamDeletion} from '@/api/team'
import {postTeamConfirmation} from '@/api/team'

export default {
  name: "TeamManaging",
  components:{
    drawer:teamdrawer,
    create:CreateTeam
  },
  data(){
    return {
      drawerCtrl:false,
      drawerId:1,
      tableData: [
        {
        index: 1,
        name: 'Project Helper 0',
        topic: 'Project Helper',
        targetMem:4,
        status:'R',},
        {
        index: 2,
        name: 'Project Helper 1',
        topic: 'Project Helper',
        targetMem:3,
        status:'R'},
        {
          index: 3,
          name: 'DBOJ 1',
          topic: 'DBOJ',
          targetMem:3,
          status:'R'},
      ],
      topicFilter:[],
      tableLoading:false,
      formVisible:false,
      multipleSelection: []
    }
  },
  mounted () {
    this.initTeams()
  },
  methods: {
    initTeams(){
      let id = this.$store.state.proj.projId
      console.log(id)
      getTeamInfoList(parseInt(id)).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }

        let teamList = resp.data.data
        console.log(teamList)
        this.tableData.splice(0,this.tableData.length)
        this.topicFilter.splice(0,this.topicFilter.length)
        for (let i = 0; i < teamList.length; i++) {
          let team = teamList[i]
          this.tableData.push({
            index:team.projInstId,
            name: team.teamName,
            topic: team.topic,
            targetMem:team.targetMemNum,
            status:team.status,
          })
          if(!this.topicFilter.includes(team.topic)){
            this.topicFilter.push({
              text:team.topic,
              value:team.topic
            })
          }

        }
        console.log(this.topicFilter)
      }).catch(failResp=>{
        console.log('fail in getProjList. message=' + failResp.message)
      })
    },
    openDrawer(val){
      console.log(val)
      this.drawerId = val
      this.drawerCtrl = true
    },
    closeDrawer(){
      this.drawerCtrl = false
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    addTeam(){
      console.log(this.formVisible)
      this.formVisible = true
    },
    closeForm(){
      this.formVisible = false
    },
    deleteTeam(){
      console.log(this.multipleSelection)
      if(this.multipleSelection.length===0){
        this.$message.error('No team selected yet')
      }else {
        this.$confirm('Delete selected teams?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          postTeamDeletion(
            this.multipleSelection
          ).then(resp => {
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: 'Delete ' + num + ' teams successfully'
              });
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
          });
        });
      }
    },
    confirmTeam(){
      console.log(this.multipleSelection)
      if(this.multipleSelection.length===0){
        this.$message.error('No team selected yet')
      }else {
        this.$confirm('Confirm selected teams?', 'Alert', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'info'
        }).then(() => {
          postTeamConfirmation(
            this.multipleSelection
          ).then(resp => {
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: 'Confirm ' + num + ' teams successfully'
              });
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
          });
        });
      }
    },
    manageTeam(){
      console.log(this.multipleSelection)
      //调用组队表单？
    },

  }
}
</script>

<style scoped>
#base-card{
  margin: 15px 10px
}
#title-text {
  font-size: 20px;
}
</style>
