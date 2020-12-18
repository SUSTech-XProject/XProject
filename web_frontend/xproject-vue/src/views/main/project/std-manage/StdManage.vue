<template>
  <el-card type="box-card" class="base-card">
    <div class="table-btm-group">
      <el-button @click="clearFilter" style="margin-right: auto">Clear all filters</el-button>
      <!--      <el-radio-group v-model="teamRadioModel" @change="handleTeamRadioChange" id="teamRadio">-->
      <!--        <el-radio-button label="all">All</el-radio-button>-->
      <!--        <el-radio-button label="haveTeam">Have a team</el-radio-button>-->
      <!--        <el-radio-button label="noTeam">No team</el-radio-button>-->
      <!--      </el-radio-group>-->
      <el-button type="primary" plain icon="el-icon-plus" @click="initAddDrawer()">Add</el-button>
      <el-button type="primary" plain @click="releaseStd">Release</el-button>
      <el-button type="warning" plain icon="el-icon-edit" @click="manageTeam">Manage</el-button>
    </div>

    <el-card style="height: 610px">
      <el-table
        ref="stdTable"
        :data="stdList"
        v-loading="tableLoading"
        empty-text="No Data Found"
        :default-sort="{prop: 'index', order: 'increasing'}"
        style="width: 100%">
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

      <!--      <div class="block">-->
      <!--        <el-pagination-->
      <!--          @size-change="handleSizeChange"-->
      <!--          @current-change="handleCurrentChange"-->
      <!--          :current-page="page.current"-->
      <!--          :page-size="page.page"-->
      <!--          layout="total, sizes, prev, pager, next, jumper"-->
      <!--          :total="page.total">-->
      <!--        </el-pagination>-->
      <!--      </div>-->

    </el-card>

    <AutoForming :visible.sync="formingVisible"
                 :stu-in="stdSelectedList"
                 @closeManaging="closeManaging(false)"></AutoForming>

    <el-drawer
      title="Add selected students to this project"
      :visible.sync="addDrawerVisible"
      :direction="direction"
      :before-close="closeAddDrawer"
      size="80%">

      <div align="right" style="margin-right: 40px;">
        <el-input placeholder="Group mark here..."
                  v-model="groupMarkOfAdd"
                  style="width: 40%; margin-right: 10px;"></el-input>
        <el-button type="primary" icon="el-icon-plus" @click="addStd">Add</el-button>
        <el-button @click="closeAddDrawer">Cancel</el-button>
      </div>

      <el-card style="margin-top: 20px; margin-left: 1%; margin-right: 1%;">
        <el-table
          ref="allStdTable"
          :data="allStdList"
          empty-text="No Data Found"
          :default-sort="{prop: 'index', order: 'increasing'}"
          @filter-change="handleFilterChange"
          style="width: 100%">
          <el-table-column type="selection"/>
          <el-table-column label="" type="index" width="50px" sortable/>
          <el-table-column label="Student Name" prop="stdName" sortable/>
          <el-table-column label="SID" prop="stdNo" sortable/>
          <el-table-column label="Username" prop="username" sortable/>
          <el-table-column label="Class" prop="stdClass" sortable column-key="stdClass"
                           :filters="classFList" :filter-method="classFMethod" :filter-multiple="false"/>
          <el-table-column label="Email" prop="email" width="300px" sortable/>
        </el-table>
      </el-card>
    </el-drawer>

  </el-card>
</template>

<script>
import {getProjectListBySch, getProjStdList, postAddStdIntoProj} from '@/api/std_manage'
import AutoForming from '@/views/main/project/team/AutoForming'

export default {
  name: 'StdManage',
  components: {
    AutoForming
  },
  data () {
    return {
      formingVisible: false,
      tableLoading: false,
      stdList: [],
      teamIndexFList: [],
      // page: {
      //   current: 1,
      //   size: 100,
      //   total: 400,
      // },
      teamStatusFList: [
        {text: 'Raw', value: 'Raw'},
        {text: 'Confirm', value: 'Confirm'},
        {text: 'No Team', value: null},
      ],
      stdSelectedList: [],

      addDrawerVisible: false,
      direction: 'rtl',
      allStdList: [],
      classFList: [],
      groupMarkOfAdd: '',
    }
  },
  mounted () {
    this.initStdManage()
  },
  methods: {
    clearFilter () {
      this.$refs.stdTable.clearFilter()
    },
    teamIndexFMethod (value, row, column) {
      return value === row.projInstId
    },
    teamStatusFMethod (value, row, column) {
      return value === row.status
    },
    unique (arr) {
      let result = {}
      let finalResult = []
      for (let i = 0; i < arr.length; i++) {
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
        for (let i = 0; i < stdListRecv.length; i++) {
          let record = stdListRecv[i]
          this.stdList.push(record)

          let teamIndex = record.projInstId
          this.teamIndexFList.push({text: teamIndex, value: teamIndex})
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
    // handleSizeChange(val) {
    //   console.log(`每页 ${val} 条`);
    // },
    // handleCurrentChange(val) {
    //   console.log(`当前页: ${val}`);
    // },
    manageTeam () {
      console.log(this.stdSelectedList)
      this.stdSelectedList = this.$refs.stdTable.selection
      this.formingVisible = true
      //调用组队表单？
    },
    closeManaging (val) {
      this.formingVisible = false
      if (val) {
        this.reLoad()
      }
    },
    releaseStd () {
      let stdList = this.$refs.stdTable.selection
    },
    reLoad () {
      this.tableLoading = true
      this.initStdManage()
      setTimeout(() => {
        this.tableLoading = false
      }, 1000)
    },

    closeAddDrawer () {
      this.$confirm('Cancel add？')
        .then(_ => {
          this.addDrawerVisible = false
          this.groupMarkOfAdd = ''
          this.$message.info('Add canceled')
        })
    },
    classFMethod (value, row, column) {
      return value === row.stdClass
    },
    handleFilterChange (filters) {
      console.log('filter value: %o', filters)
    },
    initAddDrawer () {
      this.addDrawerVisible = true

      getProjectListBySch().then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.allStdList.splice(0, this.allStdList.length)   // remove all
        let stdListRecv = resp.data.data
        for (let i = 0; i < stdListRecv.length; i++) {
          let record = stdListRecv[i]
          record['listIdx'] = i
          this.allStdList.push(record)

          let stdClass = record.stdClass
          this.classFList.push({text: stdClass, value: stdClass})
        }
        this.classFList = this.unique(this.classFList)
      }).catch(failResp => {
        console.log('fail in getGradeList. message=' + failResp.message)
      })
    },
    addStd () {
      this.$confirm('Confirm to add?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let chosenList = this.$refs.allStdTable.selection
        let roleIdList = []

        for (let i = 0; i < chosenList.length; ++i) {
          roleIdList.push(chosenList[i].roleId)
        }

        let pasVO = {
          'groupMark': this.groupMarkOfAdd,
          'projId': this.$store.state.proj.projId,
          'stdRoleIdList': roleIdList
        }

        postAddStdIntoProj(pasVO).then(resp => {
          if (resp.data.code === 200) {
            this.initStdManage()
            this.groupMarkOfAdd = ''

            this.$message.success('Add success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Add canceled')
      })
    }
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

.base-card {
  margin: 15px 10px;
  min-height: 95.7%;
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

> > .el-drawer :focus {
  outline: 0;
}
</style>


<!--      <div class="block">-->
<!--        <el-pagination-->
<!--          @size-change="handleSizeChange"-->
<!--          @current-change="handleCurrentChange"-->
<!--          :current-page="page.current"-->
<!--          :page-size="page.page"-->
<!--          layout="total, sizes, prev, pager, next, jumper"-->
<!--          :total="page.total">-->
<!--        </el-pagination>-->
<!--      </div>-->
