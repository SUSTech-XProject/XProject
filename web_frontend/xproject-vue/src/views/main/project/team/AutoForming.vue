<template>
  <el-dialog title="Auto Forming"
             :before-close="closeManaging"
             :visible.sync="dialogFormVisible"
             width="70%"
             class="autoForming">
    <div class="title">Teams</div>
    <el-table
      height="205"
      ref="multipleTable"
      :data="teamList"
      empty-text="No Data Found"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="teamChange">
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
        :filter-method="filtTopic">
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

    </el-table>
    <el-divider></el-divider>
    <div class="title">Students</div>
    <el-table
      height="205"
      ref="stdTable"
      :data="stuList"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%"
      @selection-change="stuChange">
      <el-table-column type="selection"/>
      <el-table-column label="" type="index" width="50px" sortable/>
      <el-table-column label="Student Name" prop="stdName" sortable/>
      <el-table-column label="SID" prop="stdNo" sortable/>
      <el-table-column label="Group Mark" prop="groupMark" sortable/>
      <el-table-column label="Team Index" prop="teamIndex" sortable
                       :filters="teamIndexFList" :filter-method="teamIndexFMethod"/>
      <el-table-column label="Topic" prop="teamTopic" sortable/>
      <el-table-column label="Team Status" prop="teamStatus" sortable/>
    </el-table>


    <el-form :model="form" style="padding-top: 20px">
      <el-form-item label="Strategy" :label-width="formLabelWidth">
        <el-select v-model="form.strategy" placeholder="selecting..." style="margin-left: 20px">
          <el-option v-for="item in options"
                     :key="item"
                     :value="item">
          </el-option>

        </el-select>

      </el-form-item>
      <el-form-item label="Auto Submit" :label-width="formLabelWidth">
        <el-checkbox v-model="form.autoSubmit" style="margin-left: 20px"></el-checkbox>
      </el-form-item>

    </el-form>



    <div slot="footer" class="dialog-footer">
      <el-button @click="closeManaging">Cancel</el-button>
      <el-button type="primary" @click="submit">Create</el-button>
    </div>
  </el-dialog>

</template>

<script>
import {getTeamInfoList} from '@/api/team'
import {postAutoForming} from '@/api/team'

export default {
name: "AutoForming",
  data(){
    return{
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
      //
      teamIndexFList: [
        {text: 1, value: 1},
        {text: 2, value: 2},
      ],
      stuList: [
        {
          index: 1,
          stdName: 'Xiaoming Wang',
          stdNo: '11816401',
          groupMark: 'Lab 1',
          teamIndex: 1,
          teamTopic: 'Project Helper',
          teamStatus: 'Row'
        },
        {
          index: 3,
          stdName: 'Wushuang Ye',
          stdNo: '11816403',
          groupMark: 'Lab 2',
          teamIndex: 2,
          teamTopic: 'Project Helper',
          teamStatus: 'Row'
        },
      ],
      //
      topicFilter:[],
      options:['Random','Team First','Size Balance'],
      form:{
        teamSelection: [],
        stuSelection:[],
        strategy:'',
        autoSubmit:''
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
      //this.initStu
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
    initStu(){},

    //form managing
    closeManaging(){
      this.$emit('closeManaging','msg')
    },
    submit(){
      console.log(this.form)
      //this.closeManaging()
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
      return value === row.teamIndex;
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
      this.stuList = val
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
