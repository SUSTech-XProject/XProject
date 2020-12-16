<template>
  <el-drawer :before-close="closeManaging"
             :visible.sync="dialogFormVisible"
             :append-to-body="true"
             size="70%"
             title="Managing"
             class="autoForming">
    <el-scrollbar>
      <el-tabs v-model="activeName" style="width: 90%;padding-left: 40px">
        <el-tab-pane label="Events" name="event">
          <el-card class="base-card">
            <el-table
              class="inst-table"
              :data="eventList"
              empty-text="No Data Found"
              style="width: 100%;margin-top: 0"
              @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55">
              </el-table-column>
              <el-table-column type="index" width="50"></el-table-column>
              <el-table-column property="date" label="Date" width="120" sortable></el-table-column>
              <el-table-column property="week" label="Week" width="120" sortable></el-table-column>
              <el-table-column property="time" label="Duration" sortable></el-table-column>
              <el-table-column label="Statue" property="statue" sortable></el-table-column>
              <el-table-column property="teamInfo" label="Team">
                <template slot-scope="scope">
                  <span v-if="scope.row.teamInfo===''" style="color: #8c939d">- -</span>
                  <span v-else>{{scope.row.teamInfo}}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>

        </el-tab-pane>
        <el-tab-pane label="Teams" name="team">
          <el-card class="base-card">
            <el-table
              ref="multipleTable"
              :data="teamList"
              empty-text="No Data Found"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="teamChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="name" label="Team Name" sortable show-overflow-tooltip></el-table-column>
              <el-table-column prop="topic" label="Topic" sortable show-overflow-tooltip :filters="topicFilter" :filter-method="filtTopic"></el-table-column>
              <el-table-column prop ="curMem" label="Current" width="150px" sortable></el-table-column>
              <el-table-column prop ="targetMem" label="Size" width="150px" sortable></el-table-column>
              <el-table-column prop = "status" label="Team Status"
                               width="150px" sortable :filters="teamStatus" :filter-method="teamStatusFMethod"></el-table-column>

            </el-table>
          </el-card>

        </el-tab-pane>
      </el-tabs>






    <footer>
      <div style="margin-left: 80px">
        <el-button @click="closeManaging">Cancel</el-button>
        <el-button type="primary" @click="submit">Create</el-button>
      </div>
    </footer>
    </el-scrollbar>
  </el-drawer>

</template>

<script>
import {getTeamInfoList} from '@/api/team'
import {postAutoForming} from '@/api/team'


export default {
name: "AutoForming",
  data(){
    return{
      activeName:'event',
      //
      dialogFormVisible: this.visible,
      teamList: [
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
      teamStatus: [
        {text: 'Raw', value: 'Raw'},
        {text: 'Confirm', value: 'Confirm'},
      ],
      //
      eventList: [
        { id:1,
          date:'2020-12-18',
          week:'Friday',
          time:'14:00-14:20',
          statue:'true',
          teamInfo:'Team 1'
        },{
          id:2,
          date:'2020-12-17',
          week:'Thursday',
          time:'14:00-14:20',
          statue:'false',
          teamInfo:''
        },{
          id:3,
          date:'2020-12-18',
          week:'Friday',
          time:'14:00-14:20',
          statue:'true',
          teamInfo:'Team 1'
        },{
          id:4,
          date:'2020-12-17',
          week:'Thursday',
          time:'14:00-14:20',
          statue:'false',
          teamInfo:''
        },
      ],

      //
      topicFilter:[],
      options:['Random','Team First','Size Balance'],
      form:{
        teamSelection: [],
        eventSelection:[],
        strategy:'',
        autoSubmit:false
      },
      formLabelWidth: '120px'
    }
  },
  mounted () {
    this.init()
  },
  methods:{
    //initialize
    init(){
      this.initTeams()
      //initEvent
    },
    initTeams(){
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
        //console.log(teamList)
        this.teamList.splice(0,this.teamList.length)
        this.topicFilter.splice(0,this.topicFilter.length)
        for (let i = 0; i < teamList.length; i++) {
          let team = teamList[i]
          this.teamList.push({
            index:team.projInstId,
            name: team.teamName,
            topic: team.topic,
            targetMem:team.targetMemNum,
            status:team.status,
            curMem:team.curMemNum
          })
          this.topicFilter.push({
            text:team.topic,
            value:team.topic
          })
        }
        this.topicFilter = this.unique(this.topicFilter)
      }).catch(failResp=>{
        console.log('fail in getProjList. message=' + failResp.message)
      })
    },
    unique(arr){
      let result = {}
      let finalResult = []
      for (let i = 0; i <arr.length ; i++) {
        result[arr[i].value] = arr[i]
      }
      for (const item in result) {
        finalResult.push(result[item])
      }
      return finalResult
    },
    //initEvent

    //form managing
    closeManaging(){
      this.$emit('closeManaging','msg')
    },
    submit(){
      console.log(this.form)
      //this.closeManaging()
      if(this.form.teamSelection.length===0){
        this.$message.error('No team selected yet')
      }else if(this.form.eventSelection.length===0){
        this.$message.error('No event selected yet')
      }else{
        this.$confirm('Execute auto forming?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'info'
        }).then(()=>{
          //todo 修改函数
          postAutoForming(this.form).then(resp=>{
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: 'Success'
              });
              this.closeManaging()
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
    clearForm(){},

    //选择
    teamChange(val) {
      this.form.teamSelection = val;
    },
    handleSelectionChange(val){
      this.form.eventSelection = val;
    },
    teamStatusFMethod (value, row, column) {
      return value === row.status;
    },

    //筛选
    filtTopic(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },

  },

  watch:{
    visible(val){
      this.dialogFormVisible = val
      this.initTeams()
    },
    teamIn(val){
      if(val.length===0){
        this.initTeams()
        //this.form.teamSelection = []
      }else{
        this.teamList = val
        //this.form.teamSelection = val
      }
    },
    eventIn(val){
      this.stuList = val
    }

  },
  props:{
    visible:{type: Boolean,default:false},
    eventIn:{type:Array}
  }
}
</script>

<style scoped>
/deep/ :focus {
  outline: 0;
}
.title{
  padding-left: 10px;
  padding-top: 2px;
  font-size: 18px;
  color: #919497;
}
.el-scrollbar {
  height: 100%;
  background: #ffffff;
  overflow-x: hidden;
}

.el-scrollbar >>> .el-scrollbar__wrap {
  overflow-x: hidden;
  height: 100%;
}
#team_table{
  padding:0;
}

footer {
  bottom: 30px;
  position: fixed;
  width: 62%;
}
.base-card{
  margin: 15px 10px;
  /*height: 90%;*/
}
</style>
<style>
.autoForming .el-dialog__body {
  padding-top: 0;
  /*color: #606266;*/
  /*font-size: 14px;*/
  /*word-break: break-all;*/
}
</style>
