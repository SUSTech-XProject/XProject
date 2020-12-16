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
                   icon="el-icon-remove-outline" @click="removeTeams">Remove</el-button>
        <el-button type="warning" plain
                   icon="el-icon-edit" @click="manageInst">Manage</el-button>

      </div>
      <el-table
        class="inst-table"
        :data="tableData"
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
            <el-date-picker v-model="form.date" type="date" placeholder="selecting...">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="Start time" :label-width="formLabelWidth">
            <el-time-picker v-model="form.staTime"
              placeholder="Selecting..."
            ></el-time-picker>
          </el-form-item>
          <el-form-item label="Duration" :label-width="formLabelWidth">
            <el-input v-model="form.duration" style="width: 60%" clearable></el-input>
          </el-form-item>
          <el-form-item label="Team counts" :label-width="formLabelWidth">
            <el-input v-model="form.cnt" style="width: 60%" clearable></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="CreateVisible = false">Cancel</el-button>
          <el-button type="primary" @click="createInst">Confirm</el-button>
        </div>
      </el-dialog>
      <managing :visible.sync="ManageVisible"
                :event-in="multipleSelection"
                @closeManaging="closeManaging"></managing>

    </el-card>



    </el-drawer>
</template>

<script>
import {postEventInstApply} from '@/api/event'
import ManageInst from '@/views/main/project/event/ManageInst'
export default {
name: "StuEvents",
  components:{managing:ManageInst},
  data(){
    return {
      tableData: [
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
      currentRow: null,
      eventDrawer:'',
      taskId:'',
      size:'80%',
      isTeacher:this.role,
      multipleSelection:[],
      //
      CreateVisible: false,
      form: {
        date:'',
        staTime:'',
        duration:'',
        cnt:''
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
    this.init()
  },
  methods:{
    init(){},
    refresh(){
      this.$emit("closeEvent","msg")
    },
    //stu
    apply(eventId){
      this.$confirm('Confirm selected teams?', 'Alert', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'info'
      }).then(()=>{
        postEventInstApply().then(resp=>{
          if (resp.data.code === 200) {
            let num = resp.data.data
            this.$message({
              type: 'success',
              message: 'Applied successfully'
            });
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

    },
    deleteInst(){},
    removeTeams(){},
    manageInst(){
      console.log(this.multipleSelection)
      this.ManageVisible = true
    },
    closeManaging(val){
      console.log('drawer recv')
      this.ManageVisible = false
      if(val){
        //this.reLoad()
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
    }
  },

  watch:{
    drawer(val){
      this.eventDrawer = val
    },
    ID(val){
      this.taskId = val
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
