<template>
  <el-drawer title="Auto Forming"
             :before-close="closeManaging"
             :visible.sync="dialogFormVisible"
             size="70%"
             class="autoForming">
    <el-tabs v-model="activeName" style="width: 90%;padding-left: 40px">
      <el-tab-pane label="Teams" name="team">
        <el-card class="base-card">
          <el-table
            height="380px"
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
            <el-table-column prop = "status" label="Team Status" width="150px" sortable :filters="teamStatus" :filter-method="teamStatusFMethod"></el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="Students" name="student">
        <el-card class="base-card">
          <el-table
            height="380px"
            ref="stdTable"
            :data="stdList"
            empty-text="No Data Found"
            :default-sort = "{prop: 'index', order: 'increasing'}"
            style="width: 100%"
            @selection-change="stuChange">
            <el-table-column type="selection"/>
            <el-table-column label="" type="index" width="50px" sortable/>
            <el-table-column label="Student Name" prop="stdName" sortable/>
            <el-table-column label="SID" prop="stdNo" sortable/>
            <el-table-column label="Group Mark" prop="groupMark" sortable/>
            <el-table-column label="Team Index" prop="projInstId" sortable
                             :filters="teamIndexFList"
                             :filter-method="teamIndexFMethod"/>
            <el-table-column label="Topic" prop="topicStr" sortable/>
            <el-table-column label="Team Status" prop="status" sortable
                             :filters="teamStatusFList"
                             :filter-method="teamStatusFMethod"/>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>

<!--    <div class="title">Teams</div>-->



<!--    <el-divider></el-divider>-->
<!--    <div class="title">Students</div>-->


    <el-form :model="form" style="padding-top: 20px">
      <el-form-item label="Strategy" :label-width="formLabelWidth">
        <el-select v-model="form.strategy" placeholder="selecting..." style="margin-left: 20px">
          <el-option v-for="item in options"
                     :key="item"
                     :value="item">
          </el-option>

        </el-select>
        <el-switch style="margin-left: 40px" v-model="form.autoSubmit" active-text="Auto Submit">
        </el-switch>

      </el-form-item>
<!--      <el-form-item label="Auto Submit" :label-width="formLabelWidth">-->
<!--        -->
<!--      </el-form-item>-->

    </el-form>


    <footer>
      <div style="margin-left: 80px">
        <el-button @click="closeManaging">Cancel</el-button>
        <el-button type="primary" @click="submit">Create</el-button>
      </div>
    </footer>

  </el-drawer>

</template>

<script>
import {getTeamInfoList} from '@/api/team'
import {postAutoForming} from '@/api/team'
import {getProjStdList} from '@/api/std_manage'

export default {
name: "AutoForming",
  data(){
    return{
      activeName:'team',
      dialogFormVisible: this.visible,
      teamList: [],
      teamStatus: [
        {text: 'Raw', value: 'Raw'},
        {text: 'Confirm', value: 'Confirm'},
      ],
      //
      teamIndexFList: [
        {text: 1, value: 1},
        {text: 2, value: 2},
      ],
      teamStatusFList: [
        {text: 'Raw', value: 'Raw'},
        {text: 'Confirm', value: 'Confirm'},
        {text: 'No Team', value: null},
      ],
      stdList: [],
      //
      topicFilter:[],
      options:['Random','Team First','Size Balance'],
      form:{
        teamSelection: [],
        stuSelection:[],
        strategy:'',
        autoSubmit:false
      },
      formLabelWidth: '145px'
    }
  },
  mounted () {
    this.init()
  },
  methods:{
    //initialize
    init(){
      this.initTeams()
      this.initStdManage()
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
    initStdManage () {
      this.stdList.splice(0, this.stdList.length)   // remove all
      let projId = this.$store.state.proj.projId

      getProjStdList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.stdList.splice(0, this.stdList.length)   // remove all
        this.teamIndexFList.splice(0, this.teamIndexFList.length)   // remove all
        // this.teamStatusFList.splice(0, this.teamStatusFList.length)   // remove all
        let stdListRecv = resp.data.data
        for (let i = 0; i < stdListRecv.length; i ++) {
          let record = stdListRecv[i]
          record['listIdx'] = i
          console.log(record)
          this.stdList.push(record)

          let teamIndex = record.projInstId;
          this.teamIndexFList.push({text:teamIndex, value:teamIndex})
          // let status = record.status;
          // this.teamStatusFList.push({text:status, value:status})
        }
        this.teamIndexFList = this.unique(this.teamIndexFList)
        // this.teamStatusFList = this.unique(this.teamStatusFList)
        console.log(this.stdList)
      }).catch(failResp => {
        console.log('fail in getGradeList. message=' + failResp.message)
      })
    },

    //form managing
    closeManaging(){
      this.$emit('closeManaging','msg')
    },
    submit(){
      console.log(this.form)
      //this.closeManaging()
      if(this.form.teamSelection.length===0){
        this.$message.error('No team selected yet')
      }else if(this.form.stuSelection.length===0){
        this.$message.error('No student selected yet')
      }else if(this.form.strategy===''){
        this.$message.error('No strategy selected yet')
      } else{
        this.$confirm('Execute auto forming?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'info'
        }).then(()=>{
          postAutoForming(this.form).then(resp=>{
            if (resp.data.code === 200) {
              let num = resp.data.data
              this.$message({
                type: 'success',
                message: 'Success'
              });
              this.clearForm()
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
    clearForm(){
      this.form.teamSelection=[]
      this.form.stuSelection=[]
      this.form.strategy=''
    },

    //选择
    teamChange(val) {
      this.form.teamSelection = val;
    },
    stuChange(val){
      this.form.stuSelection = val;
    },

    //筛选
    filtTopic(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    teamIndexFMethod (value, row, column) {
      return value === row.projInstId;
    },
    teamStatusFMethod (value, row, column) {
      return value === row.status;
    },

  },

  watch:{
    visible(val){
      this.dialogFormVisible = val
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
    stuIn(val){
      if(val.length===0){
        this.initStdManage()
      }else{
        this.stuList = val
      }
    }

  },
  props:{
    visible:{type: Boolean,default:false},
    teamIn:{type:Array},
    stuIn:{type:Array}
  }
}
</script>

<style scoped>
/deep/ :focus {
  outline: 0;
}
.base-card{
  margin: 15px 10px;
  /*height: 90%;*/
}
.title{
  padding-left: 10px;
  padding-top: 2px;
  font-size: 18px;
  color: #919497;
}
#team_table{
  padding:0;
}
#title-text {
  font-size: 20px;
}
#card-body{

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
