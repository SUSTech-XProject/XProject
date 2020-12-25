<template>
  <el-drawer
      :destroy-on-close = "true"
      :size = "size"
      :with-header = "false"
      :visible.sync="eventDrawer"
      :before-close="refresh" >
    <div class="header">
      <el-divider direction="vertical"></el-divider>
      <span>Timing List</span>
    </div>


    <el-card id="base-card">
      <div style="text-align: right;padding-right: 30px" v-if="isTeacher">
        <el-button type="primary" plain
                   icon="el-icon-circle-plus-outline" @click="CreateVisible=true">Create</el-button>
        <el-button type="danger" plain
                   icon="el-icon-delete" @click="deleteInst">Delete</el-button>
        <el-button type="info" plain
                   icon="el-icon-remove-outline" @click="removeTeams">Clear</el-button>
        <el-button type="warning" plain
                   icon="el-icon-edit" @click="manageInst">Manage</el-button>
        <el-button type="primary" plain
                   icon="el-icon-download" @click="exportEvent">Export</el-button>

      </div>
      <el-table
        class="inst-table"
        :data="tableData"
        height="580px"
        :default-sort="{prop: 'date', order: 'increasing'}"
        v-loading="tableLoading"
        :row-class-name="tableRowClassName"
        empty-text="No Data Found"
        style="width: 100%;margin-top: 0"
        @selection-change="handleSelectionChange">
        <el-table-column v-if="isTeacher"
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column property="date" label="Date" width="120" sortable></el-table-column>
        <el-table-column property="week" label="Week" width="120" sortable></el-table-column>
        <el-table-column property="time" label="Duration" sortable></el-table-column>
        <el-table-column label="Statue" property="statue" sortable></el-table-column>
        <el-table-column v-if="!isTeacher" property="teamInfo" label="Team">
          <template slot-scope="scope">
            <span v-if="scope.row.teamInfo===''">
              <el-button type="primary" plain size="mini" icon="el-icon-circle-plus-outline"
                         @click="apply(scope.row.id)">Apply</el-button>
            </span>
            <span v-else-if="scope.row.teamId===myTeamId">
              <el-button type="success" plain disabled icon="el-icon-circle-check"
                         size="mini">{{scope.row.teamInfo}}</el-button>
              <el-button type="danger" plain icon="el-icon-remove-outline" @click="undo(scope.row.id)"
                         size="mini">undo</el-button>
            </span>
            <span v-else>
            <el-button type="info" plain disabled icon="el-icon-circle-check"
                       size="mini">{{scope.row.teamInfo}}</el-button></span>
          </template>
        </el-table-column>
        <el-table-column v-else property="teamInfo" label="Team">
          <template slot-scope="scope">
            <span v-if="scope.row.teamInfo===''" style="color: #8c939d">- -</span>
            <span v-else>{{scope.row.teamInfo}}</span>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="Create"
                 :visible.sync="CreateVisible"
                 :append-to-body="true" width="450px">
        <el-form :model="form">
          <el-form-item label="Date" :label-width="formLabelWidth">
            <el-date-picker v-model="form.date" type="date" value-format="yyyy-MM-dd" placeholder="selecting...">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="Start time" :label-width="formLabelWidth">
            <el-time-picker v-model="form.startTime" value-format="HH:mm:ss"
                            placeholder="Selecting..."
            ></el-time-picker>
          </el-form-item>
          <el-form-item label="Duration" :label-width="formLabelWidth">
            <el-input v-model="form.duration" style="width: 60%" clearable></el-input>
          </el-form-item>
          <el-form-item label="Team counts" :label-width="formLabelWidth">
            <el-input v-model="form.counts" style="width: 60%" clearable></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="CreateVisible = false">Cancel</el-button>
          <el-button type="primary" @click="createInst">Confirm</el-button>
        </div>
      </el-dialog>
      <div v-if="this.$store.state.role.roleType==='Teacher'">
        <managing :visible.sync="ManageVisible"
                  :event-in="multipleSelection"
                  :event-id="taskId"
                  @closeManaging="closeManaging"></managing>
      </div>


    </el-card>



    </el-drawer>
</template>

<script>
import {
  getEventTaskList,
  postEventInstApply, postEventInstClearStd,
  postEventInstClearTch,
  postEventInstCreation,
  postEventInstDeletion
} from '@/api/event'
import ManageInst from '@/views/main/project/event/ManageInst'
import {getMyTeamDetail} from '@/api/team'
export default {
name: "StuEvents",
  components:{managing:ManageInst},
  data(){
    return {
      tableData: [],
      tableLoading:false,
      currentRow: null,
      eventDrawer:'',
      taskId:'',
      size:'80%',
      isTeacher:this.role,
      multipleSelection:[],
      myTeamId:-1,
      //
      CreateVisible: false,
      form: {
        date:'',
        startTime:'',
        duration:'',
        counts:'',
        eaTaskId:'',
      },
      formLabelWidth: '120px',
      //
      ManageVisible:false
    }
  },
  created () {
    this.eventDrawer = this.drawer
    this.taskId = this.ID
  },
  mounted () {
    this.reLoad()
  },
  methods:{
    init(){
      console.log(this.taskId)
      let projId = this.$store.state.proj.projId
      if(this.$store.state.role.roleType==='Student'){
        getMyTeamDetail(projId).then(resp=>{
          if(resp.data.code===200){
            if(resp.data.data!==null){
              this.myTeamId = resp.data.data.projInstId
            }
          }
        })
      }


      getEventTaskList(this.taskId).then(resp=>{
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.code + '\n' + resp.data.message)
          return false
        }
        let tasks = resp.data.data
        console.log(tasks)
        this.tableData.splice(0,this.tableData.length)
        for (let i = 0; i <tasks.length ; i++) {
          let task = tasks[i]
          this.tableData.push({
            id:task.eventInst.eventId,
            date:task.eventInst.date.substr(0,10),
            week:task.eventInst.week,
            time:task.eventInst.startTime.substr(11,5)+"-"+task.eventInst.endTime.substr(11,5),
            statue:task.eventInst.status,
            teamInfo:task.eventInst.projInstId===null?'':task.projInst.teamName,
            teamId:task.eventInst.projInstId===null?-1:task.projInst.projInstId,
          })
        }

      }).catch(failResp=>{
        console.log('fail in getEventTasklist. message=' + failResp.message)
      })

    },
    reLoad(){
      this.tableLoading = true
      this.init()
      setTimeout(()=>{
        this.tableLoading = false
      },1000)
    },
    refresh(){
      this.$emit("closeEvent","msg")
    },
    //stu
    apply(eventId){
      let projId = this.$store.state.proj.projId
      console.log(eventId)
      this.$confirm('Confirm selected teams?', 'Alert', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'info'
      }).then(()=>{
        postEventInstApply(eventId,projId).then(resp=>{
          if (resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Applied successfully'
            });
            this.reLoad()
          } else {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error('Back-end no response')
        })
      }).catch(() =>{
        this.$message({
          type: 'info',
          message: 'Canceled'
        });
      })

    },
    undo(eventId){
      let projId = this.$store.state.proj.projId
      console.log(eventId)
      this.$confirm('Confirm selected teams?', 'Alert', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'info'
      }).then(()=>{
        postEventInstClearStd(eventId,projId).then(resp=>{
          if (resp.data.code === 200) {
            let num = resp.data.data
            this.$message({
              type: 'success',
              message: 'Undo successfully'
            });
            this.reLoad()
          } else {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error('Back-end no response')
        })
      }).catch(() =>{
        this.$message({
          type: 'info',
          message: 'Canceled'
        });
      })
    },
    //tch
    createInst(){
      console.log(this.form)
      this.$confirm('Submit this form?', 'Alert', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(()=>{
        postEventInstCreation(this.form).then(resp=>{
          console.log("submit")
          if (resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Create taskInst successfully'
            });
            this.CreateVisible = false
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
        });
      });

    },
    deleteInst(){
      console.log(this.multipleSelection)
      if(this.multipleSelection.length===0){
        this.$message.error('No eventInst selected')
      }else{
        this.$confirm('Delete selected eventInst?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(()=>{
          let list = []
          for (let i = 0; i < this.multipleSelection.length; i++) {
            list.push(parseInt(this.multipleSelection[i].id))
          }
          console.log(list)
          postEventInstDeletion(list).then(resp=>{
            if (resp.data.code === 200) {
              this.$message({
                type: 'success',
                message: 'Delete successfully'
              });
              this.reLoad()
            } else {
              this.$message.error(resp.data.message)
            }
          }).catch(failResp => {
            this.$message.error('Back-end no response')
          })
        }).catch(() =>{
          this.$message({
            type: 'info',
            message: 'Canceled'
          });
        })
      }


    },
    removeTeams(){
      console.log(this.multipleSelection)
      if(this.multipleSelection.length===0){
        this.$message.error('No eventInst selected')
      }else{
        this.$confirm('Clear selected eventInst?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(()=>{
          let list = []
          for (let i = 0; i < this.multipleSelection.length; i++) {
            list.push(parseInt(this.multipleSelection[i].id))
          }
          console.log(list)
          postEventInstClearTch(list).then(resp=>{
            if (resp.data.code === 200) {
              this.$message({
                type: 'success',
                message: 'Clear successfully'
              });
              this.reLoad()
            } else {
              this.$message.error(resp.data.message)
            }
          }).catch(failResp => {
            this.$message.error('Back-end no response')
          })
        }).catch(() =>{
          this.$message({
            type: 'info',
            message: 'Canceled'
          });
        })
      }
    },
    manageInst(){
      console.log(this.multipleSelection)
      this.ManageVisible = true
    },
    closeManaging(val){
      this.ManageVisible = false
      if(val){
        this.reLoad()
      }
    },
    //
    tableRowClassName({row, rowIndex}) {
      if (row.teamInfo!=='') {
        return 'info-row';
      }
      return '';
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    exportEvent(){
      window.open('http://localhost:8443/api/teacher/event/export?eaId=' + this.form.eaTaskId)
    }
  },

  watch:{
    drawer(val){
      this.eventDrawer = val
    },
    ID(val){
      this.taskId = val
      this.form.eaTaskId = val
      this.reLoad()
    }
  },
  props:{
    drawer:{type:Boolean,default:false},
    ID:{type:Number,default:1},
    role:{type:Boolean,default:false}
  }
}
</script>

<style scoped>
#base-card{
  margin: 15px 10px;
  /*height: 90%;*/
  min-height: 90%;
}
.header{
  padding-top: 20px;
  padding-bottom: 10px;
  margin: 15px 10px;
  font-size: 20px;
}
#title-text {
  font-size: 20px;
}
.inst-table .info-row {
  background: oldlace;
}


</style>

<style>
.inst-table .info-row {
  background: #f5f5f5;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
