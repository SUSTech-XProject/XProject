<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Gradebook</span>
    </div>

    <div v-if="isStudent()">
      <el-table
        :data="gradeList"
        empty-text="No Data Found"
        :default-sort = "{prop: 'index', order: 'increasing'}"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" class="demo-table-expand">
              <el-form-item label="Creator: ">
                <span>{{ props.row.tchName }}</span>
                <span style="margin-left: 10px">{{ props.row.email }}</span>
              </el-form-item>
              <el-form-item label="Type: ">
                <span>{{ props.row.type }}</span>
              </el-form-item>
              <el-form-item v-if="props.row.derivedStr"
                            label="Derived from: ">
                <span>{{ props.row.derivedStr }}</span>
              </el-form-item>
              <br>
              <el-form-item label="Comment: " v-if="props.row.comments">
                <span>{{ props.row.comments }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="" type="index" width="50px" sortable/>
        <el-table-column label="Name" prop="rcdName" sortable/>
        <el-table-column label="Modified time" prop="modifiedTime" sortable :sort-method="gradeSorter"/>
        <el-table-column label="Grade" sortable :sort-method="gradeSorter">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.content }}</span>
            <span v-if="scope.row.baseContent"
                  style="margin-left: 2px">/ {{ scope.row.baseContent }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div v-if="isTeacher()">
      <div style="margin-right: 40px;" align="right">
        <el-button type="primary" icon="el-icon-plus"
                   @click="addDialogVisible=true">
          Add
        </el-button>
        <el-button icon="el-icon-minus"
                   @click="deleteRecord">
          Delete
        </el-button>
      </div>

      <el-dialog title="Add a record"
                 width="30%"
                 :visible.sync="addDialogVisible">
        <el-form :model="newRecord" label-width="auto" style="width: 90%; margin-left: 5%;">
          <el-form-item label="Record name">
            <el-input v-model="newRecord.name"></el-input>
          </el-form-item>
          <el-form-item label="Record type">
            <el-select v-model="newRecord.type" placeholder="">
              <el-option v-for="type in typeList" :key="type.value"
                         :label="type.label" :value="type.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Upper limit of score">
            <el-input v-model="newRecord.baseContent"
                      :disabled="newRecord.type!=='Point'">
            </el-input>
          </el-form-item>
        </el-form>

        <div align="right" style="margin-top: 40px;">
          <el-button type="primary" icon="el-icon-plus"
                     @click="addRecord">Add
          </el-button>
          <el-button @click="addDialogVisible = false">Cancel</el-button>
        </div>
      </el-dialog>

      <el-table
        ref="recordTable"
        :data="recordList"
        empty-text="No Data Found"
        :default-sort="{prop: 'index', order: 'increasing'}"
        style="width: 100%">
        <el-table-column type="selection"></el-table-column>

        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="Email of creator: ">
                <span>{{ props.row.creator.email }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="" type="index" width="50px"/>
        <el-table-column label="Name" prop="record.rcdName" sortable/>
        <el-table-column label="Type" prop="record.type"/>
        <el-table-column label="Creator" prop="creator.tchName"/>
        <el-table-column label="Created time" prop="record.createdTime"
                         :formatter="dateTimeFormatter" sortable/>
      </el-table>
    </div>
  </el-card>
</template>

<script>
import {getAllRecord, getGradeList, postDeleteRecord, postNewRecord} from '@/api/grade'
import {getDatetimeStr} from '@/utils/parse-day-time'
import {isStudent, isTeacher} from '@/utils/role'

export default {
  name: 'Gradebook',
  components: {},
  data () {
    return {
      gradeList: [
        {
          index: 1,
          name: 'assignment1',
          createdTime: '12/01/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 96,
          baseContent: 100,
          derived: '',
          comment: '1.2 no pic -2; 1.4 no text -2'
        },
        {
          index: 2,
          name: 'assignment2',
          createdTime: '12/02/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 90,
          baseContent: 100,
          derived: '',
          comment: 'None'
        },
        {
          index: 3,
          name: 'assignment3',
          createdTime: '12/03/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 87,
          baseContent: 100,
          derived: '',
          comment: 'None'
        },
        {
          index: 4,
          name: 'report',
          createdTime: '12/06/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Grade',
          content: 'A',
          baseContent: null,
          derived: '',
          comment: 'report grade'
        },
        {
          index: 5,
          name: 'assignment total',
          createdTime: '12/08/2020 14:00',
          creatorStr: 'Dehua Liu',
          type: 'Point',
          content: 91.0,
          baseContent: 100,
          derived: 'assignment1 assignment2 assignment3',
          comment: 'assignment avg score'
        },
      ],
      addDialogVisible: false,
      newRecord: {
        name: '',
        type: 'Point',
        baseContent: '',
      },
      typeList: [
        {value: 'Comment', label: 'Comment'},
        {value: 'PF', label: 'PF'},
        {value: 'Grade', label: 'Grade'},
        {value: 'Point', label: 'Point'}
      ],
      recordList: [],
    }
  },
  mounted () {
    this.initGradebook()
  },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.record.createdTime)
    },
    gradeSorter (row, col) {
      return row.content
    },
    initGradebook () {
      let projId = this.$store.state.proj.projId

      if (isStudent()) {
        getGradeList(projId).then(resp => {
          if (resp.data.code !== 200) {
            this.$message.error(resp.data.code + '\n' + resp.data.message)
            return false
          }
          this.gradeList.splice(0, this.gradeList.length)   // remove all
          for (let i = 0; i < resp.data.data.length; i++) {
            let record = resp.data.data[i]
            record.modifiedTime=getDatetimeStr(record.modifiedTime)
            record['listIdx'] = i
            this.gradeList.push(record)
          }
          // this.gradeList = resp.data.data
          console.log(this.gradeList)
        }).catch(failResp => {
          console.log('fail in getGradeList. message=' + failResp.message)
        })
      } else {
        getAllRecord(projId).then(resp => {
          if (resp.data.code !== 200) {
            this.$message.error(resp.data.code + '\n' + resp.data.message)
            return false
          }

          this.recordList.splice(0, this.recordList.length)   // remove all
          this.recordList = resp.data.data
          console.log(this.recordList)
        }).catch(failResp => {
          console.log('fail in getGradeList. message=' + failResp.message)
        })
      }
    },

    isStudent () {
      return isStudent()
    },
    isTeacher () {
      return isTeacher()
    },

    addRecord () {
      this.$confirm('Confirm to add?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let recordCreationVO = {
          'baseContent': this.newRecord.baseContent,
          'name': this.newRecord.name,
          'projId': this.$store.state.proj.projId,
          'type': this.newRecord.type
        }
        postNewRecord(recordCreationVO).then(resp => {
          if (resp.data.code === 200) {
            getAllRecord(this.$store.state.proj.projId).then(resp => {
              if (resp.data.code !== 200) {
                this.$message.error(resp.data.code + '\n' + resp.data.message)
                return false
              }

              this.recordList.splice(0, this.recordList.length)   // remove all
              this.recordList = resp.data.data
              console.log(this.recordList)
            }).catch(failResp => {
              console.log('fail in getGradeList. message=' + failResp.message)
            })

            this.$message.success('Add success')
            this.addDialogVisible = false
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Add canceled'
        })
      })
    },
    deleteRecord () {
      this.$confirm('Confirm to delete?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let selectedRecord = this.$refs.recordTable.selection
        let recordDeletionVO = {
          rcdIdList: []
        }

        for (let i = 0; i < selectedRecord.length; ++i) {
          recordDeletionVO.rcdIdList.push(selectedRecord[i].record.rcdId)
        }

        postDeleteRecord(recordDeletionVO).then(resp => {
          if (resp.data.code === 200) {
            getAllRecord(this.$store.state.proj.projId).then(resp => {
              if (resp.data.code !== 200) {
                this.$message.error(resp.data.code + '\n' + resp.data.message)
                return false
              }

              this.recordList.splice(0, this.recordList.length)   // remove all
              this.recordList = resp.data.data
              console.log(this.recordList)
            }).catch(failResp => {
              console.log('fail in getGradeList. message=' + failResp.message)
            })

            this.$message.success('Delete success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Delete canceled'
        })
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

#base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

#title-text {
  font-size: 20px;
}
</style>

<!--            <el-radio v-model="newRecord.type" label="Point">Point</el-radio>-->
<!--            <el-radio v-model="newRecord.type" label="P/F">PF</el-radio>-->
<!--            <el-radio v-model="newRecord.type" label="Grade">Grade</el-radio>-->
<!--            <el-radio v-model="newRecord.type" label="Comment">Comment</el-radio>-->
