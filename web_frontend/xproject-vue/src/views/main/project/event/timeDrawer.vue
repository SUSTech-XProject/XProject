<template>
  <el-drawer
    :destroy-on-close = "true"
    :size = "size"
    :with-header = "false"
    :visible.sync="eventDrawer"
    :before-close="refresh"
  >
    <el-table
      :data="tableData"
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
                         @click="apply(scope.row)">Apply</el-button>
            </span>
          <span v-else>
            <el-button type="success" plain disabled
                       icon="el-icon-circle-check"
                       size="mini">{{scope.row.teamInfo}}</el-button></span>

        </template>
      </el-table-column>
    </el-table>
  </el-drawer>
</template>

<script>
export default {
name: "StuEvents",
  data(){
    return {
      tableData: [
        {
          date:'2020-12-18',
          week:'Friday',
          time:'14:00-14:20',
          statue:'true',
          teamInfo:'Team 1'
        },{
          date:'2020-12-17',
          week:'Thursday',
          time:'14:00-14:20',
          statue:'false',
          teamInfo:''
        },{
          date:'2020-12-18',
          week:'Friday',
          time:'14:00-14:20',
          statue:'true',
          teamInfo:'Team 1'
        },{
          date:'2020-12-17',
          week:'Thursday',
          time:'14:00-14:20',
          statue:'false',
          teamInfo:''
        },
        ],
      currentRow: null,
      eventDrawer:'',
      eventId:'',
      size:'80%'
    }
  },
  created () {
    this.eventDrawer = this.drawer
    this.eventId = this.ID
  },
  mounted () {
    this.init()
  },
  methods:{
    init(){},
    refresh(){
      this.$emit("closeEvent","msg")
      //console.log(this.eventDrawer)
    },

    apply(val){

    },
  },

  watch:{
    drawer(val){
      this.eventDrawer = val
    },
    ID(val){
      this.eventId = val
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
}
#title-text {
  font-size: 20px;
}
</style>
