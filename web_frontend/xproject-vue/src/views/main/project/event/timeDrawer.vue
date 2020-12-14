<template>
  <el-drawer
      :destroy-on-close = "true"
      :size = "size"
      :with-header = "false"
      :visible.sync="eventDrawer"
      :before-close="refresh">
    <div class="header">
      <el-divider direction="vertical"></el-divider>
      <span>Timing List</span>
    </div>
    <el-card id="base-card">
      <el-table
        :data="tableData"
        empty-text="No Data Found"
        style="width: 100%">
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          property="date"
          label="Date"
          width="120"
          sortable>
        </el-table-column>
        <el-table-column
          property="week"
          label="Week"
          width="120"
          sortable>
        </el-table-column>
        <el-table-column
          property="time"
          label="Duration"
          sortable>
        </el-table-column>
        <el-table-column
          label="Statue"
          property="statue"
          sortable>
        </el-table-column>
        <el-table-column
          property="teamInfo"
          label="Team">
          <template slot-scope="scope">
            <span v-if="scope.row.teamInfo===''">
              <el-button type="primary" plain
                         size="mini"
                         icon="el-icon-circle-plus-outline"
                         @click="apply(scope.row.id)">Apply</el-button>
            </span>
            <span v-else>
            <el-button type="info" plain disabled
                       icon="el-icon-circle-check"
                       size="mini">{{scope.row.teamInfo}}</el-button></span>

          </template>
        </el-table-column>
      </el-table>
    </el-card>

    </el-drawer>
</template>

<script>
import {postEventInstApply} from '@/api/event'

export default {
name: "StuEvents",
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
      size:'80%'
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
    ID:{type:Number,default:1}
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
  margin: 15px 10px;
  font-size: 20px;
}
#title-text {
  font-size: 20px;
}
</style>
