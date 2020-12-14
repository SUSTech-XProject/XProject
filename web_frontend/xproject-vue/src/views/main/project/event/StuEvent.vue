<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Events</span>
    </div>
    <el-table
      :data="events"
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
            <el-form-item label="Modify Time">
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
        label="time"
        prop="modifiedTime">
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
            @closeEvent = "closeEvent"></drawer>
  </el-card>

</template>

<script>
import timeDrawer from '@/views/main/project/event/timeDrawer'
export default {
name: "StuEvent",
  components:{
    drawer:timeDrawer,
  },
  data(){
    return{
      events:[
        {
        id:1,
        creator:'Teacher 1',
        title:'Midterm presentation',
        description:'desc1',
        mode:'?',
        createdTime:'2020-12-32',
        modifiedTime:'2021-01-00'
      },{
        id:2,
        creator:'Teacher 2',
        title:'Final presentation',
        description:'desc2',
        mode:'?',
        createdTime:'2020-12-32',
        modifiedTime:'2021-01-00'
      }],
      drawerCtrl:false,
      eventId:0,
    }

  },
  methods:{
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
