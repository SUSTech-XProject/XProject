<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Events</span>
    </div>
    <el-table
      :data="events"
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="Title">
              <span>{{ props.row.title }}</span>
            </el-form-item>
            <el-form-item label="Creator">
              <span>{{ props.row.creator }}</span>
            </el-form-item>
            <el-form-item label="Create Time">
              <span>{{ props.row.createdTime }}</span>
            </el-form-item>
            <el-form-item label="Due Time">
              <span>{{ props.row.modifiedTime }}</span>
            </el-form-item>
            <el-form-item label="Mode">
              <span>{{ props.row.mode }}</span>
            </el-form-item>
            <el-form-item label="Description">
              <span>{{ props.row.description }}</span>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="Title"
        prop="title">
      </el-table-column>
      <el-table-column
        label="Creator"
        prop="creator">
      </el-table-column>
      <el-table-column
        label="Due time"
        prop="modifiedTime"
        sortable>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary" plain
            icon="el-icon-search"
            @click="openEvent(scope.row.id)">Detail</el-button>
        </template>
      </el-table-column>
    </el-table>



    <drawer :drawer.sync="drawerCtrl"
            :ID="eventId"
            :role="false"
            @closeEvent = "closeEvent"></drawer>
  </el-card>

</template>

<script>
import timeDrawer from '@/views/main/project/event/InstDrawer'
import {getEATaskList} from '@/api/event'

export default {
name: "StuEvent",
  components:{
    drawer:timeDrawer,
  },
  data(){
    return{
      events:[],
      drawerCtrl:false,
      eventId:0,
      tableLoading:false,
    }

  },
  mounted () {
    this.init()
  },
  methods:{
    init(){
      this.tableLoading = true
      let id = this.$store.state.proj.projId
      getEATaskList(parseInt(id)).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let EAlist = resp.data.data
        console.log(EAlist)
        this.events.splice(0,this.events.length)
        for (let i = 0; i <EAlist.length ; i++) {
          let EA = EAlist[i]
          this.events.push({
            id:EA.eaTask.eaTaskId,
            creator:EA.creator.tchName ,
            title: EA.eaTask.title,
            description:EA.eaTask.description ,
            mode: EA.eaTask.stdAdaptable,
            createdTime: EA.eaTask.createdTime.substr(0,10)+' '+EA.eaTask.createdTime.substr(11,8),
            modifiedTime: EA.eaTask.dueTime.substr(0,10)+' '+EA.eaTask.dueTime.substr(11,8),
          })
        }


      }).catch(failResp=>{
        console.log('fail in getEAlist. message=' + failResp.message)
      })
      setTimeout(()=>{
        this.tableLoading = false
      },1000)
    },
    openEvent(val){
      this.drawerCtrl = true
      this.eventId = val
      console.log(val)
    },
    closeEvent(){
      this.drawerCtrl = false
    },
  }

}
</script>

<style scoped>
#base-card{
  margin: 15px 10px;
}
#title-text {
  font-size: 20px;
}
</style>
<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 120px;
  color: #99a9bf;
  padding-right: 20px;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
