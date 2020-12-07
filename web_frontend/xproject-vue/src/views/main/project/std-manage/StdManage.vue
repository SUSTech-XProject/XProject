<template>
  <el-tabs type="border-card" id="base-card">
    <el-tab-pane label="Team of Students">

      <div class="table-btm-group">
        <el-button @click="clearFilter" style="margin-right: auto">Clear all filters</el-button>
        <el-radio-group v-model="teamRadioModel" @change="handleTeamRadioChange" id="teamRadio">
          <el-radio-button label="all">All</el-radio-button>
          <el-radio-button label="haveTeam">Have a team</el-radio-button>
          <el-radio-button label="noTeam">No team</el-radio-button>
        </el-radio-group>
        <el-button type="primary" plain>Forming</el-button>
        <el-button type="primary" plain>Release</el-button>
      </div>

      <el-card>
        <el-table
          ref="stdTable"
          :data="stdList"
          empty-text="No Data Found"
          :default-sort = "{prop: 'index', order: 'increasing'}"
          style="width: 100%">
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

        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="page.current"
            :page-size="page.page"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.total">
          </el-pagination>
        </div>

      </el-card>

    </el-tab-pane>
  </el-tabs>
</template>

<script>
  import {getGradeList} from "@/api/grade";
  import {getDatetimeStr} from "@/utils/parse-day-time";

  export default {
    name: "StdManage",
    components:{
    },
    data() {
      return {
        teamRadioModel: 'all',
        stdList: [
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
        teamIndexFList: [
          {text: 1, value: 1},
          {text: 2, value: 2},
        ],
        page: {
          current: 1,
          size: 100,
          total: 400,
        }
      }
    },
    mounted () {
      // this.initGradebook()
    },
    methods:{
      handleTeamRadioChange () {

      },
      clearFilter () {
        this.$refs.stdTable.clearFilter();
      },
      teamIndexFMethod (value, row, column) {
        return value === row.teamIndex;
      },
      initGradebook () {
        this.stdList.splice(0, this.stdList.length)   // remove all
        let projId = this.$store.state.proj.projId

        getGradeList(projId).then(resp => {
          if (resp.data.code !== 200) {
            this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
              confirmButtonText: 'OK'
            })
            return false
          }
          this.stdList.splice(0, this.stdList.length)   // remove all
          for (let i = 0; i < resp.data.data.length; i ++) {
            let record = resp.data.data[i]
            record['listIdx'] = i
            console.log(record)
            this.stdList.push(record)
          }
          console.log(this.stdList)
        }).catch(failResp => {
          console.log('fail in getGradeList. message=' + failResp.message)
        })
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
    }
  }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  #base-card {
    margin: 15px 10px
  }
  #title-text {
    font-size: 20px;
  }

  .table-btm-group {
    margin: 10px 0 20px 0;
    display: flex;
    align-items: center;
    /*justify-content: flex-end;*/
  }

  #teamRadio {
    margin-right: 10px;
  }

  .el-pagination {
    margin: 30px 0 10px 0;
    text-align: center;
  }
</style>
