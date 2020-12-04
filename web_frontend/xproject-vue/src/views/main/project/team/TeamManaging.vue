<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Team Management</span>
    </div>
    <el-table
      height="250"
      ref="multipleTable"
      :data="tableData"
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
        sortable
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="Team Name"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        prop="topic"
        label="Topic"
        sortable
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop ="targetMem"
        label="Size"
        width="150px"
        sortable
      >
      </el-table-column>
      <el-table-column
        prop = "status"
        label="Status"
        width="150px"
        sortable
      >
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-search"
            @click="openDrawer(scope.row.index)">Detail</el-button>
        </template>
      </el-table-column>
    </el-table>
    <drawer :drawer.sync="drawerCtrl"
            :id="drawerId"
            @closeDrawer = "closeDrawer"></drawer>

  </el-card>
</template>

<script>
import teamdrawer from '@/views/main/project/team/teamdrawer'
import {getTeamInfoList} from '@/api/team'

export default {
  name: "TeamManaging",
  components:{
    drawer:teamdrawer
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
        status:'R',
      },],
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

        for (let i = 0; i < teamList.length; i++) {
          let team = teamList[i]
          this.tableData.push({
            index:team.projInstId,
            name: team.teamName,
            topic: team.topic,
            targetMem:team.targetMemNum,
            status:team.status,
          })

        }
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(index, row) {
      console.log(index, row);
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
