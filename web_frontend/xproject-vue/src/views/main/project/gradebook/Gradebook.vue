<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Gradebook</span>
    </div>

    <div v-if="isStudent()">
      <el-table
        :data="gradeList"
        empty-text="No Data Found"
        :default-sort="{prop: 'index', order: 'increasing'}"
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
            <div v-if="scope.row.comments==null&&scope.row.content==null">Not Scored Yet</div>
            <div v-else>
              <div v-if="scope.row.content==null">
                {{scope.row.comments}}
              </div>
              <div v-else>
                <span style="margin-left: 10px">{{ scope.row.content }}</span>
                <span v-if="scope.row.baseContent"
                      style="margin-left: 2px">/ {{ scope.row.baseContent }}</span>
              </div>

            </div>

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
        <el-button type="primary" icon="el-icon-plus"
                   @click="handleOpenCombineDialog">
          Generate
        </el-button>
        <el-button icon="el-icon-minus"
                   @click="deleteRecord">
          Delete
        </el-button>
        <el-button icon="el-icon-upload2"
                   @click="uploadDrawer=true">
          Upload
        </el-button>
        <el-button icon="el-icon-download"
                   @click="downloadGradeBook">
          Download
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
          <el-button @click="handleCancelAdd">Cancel</el-button>
        </div>
      </el-dialog>

      <el-dialog title="Produce new record"
                 width="60%"
                 :visible.sync="combineDialogVisible">
        <el-form label-width="auto" style="width: 90%; margin-left: 5%;">
          <el-form-item label="Record name">
            <el-input v-model="combineRecordName" style="width: 80%"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12" v-for="record in combineList" :key="record.name">
              <el-form-item :label="record.name" label-width="auto">
                <el-input v-model="record.proportion" style="width: 200px"></el-input>
                %
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <div align="right">
          <el-button type="primary" @click="combineRecord">Produce</el-button>
          <el-button @click="handleCancelCombine">Cancel</el-button>
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
              <el-form-item label="Email of creator: " label-width="auto">
                {{ props.row.creator.email }}
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

      <el-drawer
        title="Add New Resources"
        :visible.sync="uploadDrawer"
        size="60%">

        <el-card id="add-card">
          Upload New Resources:
          <el-upload
            class="upload"
            ref="uploadDrawer"
            :action="'not-matter'"
            multiple
            :auto-upload="false"
            :limit="1"
            :on-exceed="handleExceed"
            :on-change="batchImportChange"
            :file-list="fileList">

            <el-button slot="trigger" type="primary">Choose</el-button>
            <el-button style="margin-left: 10px;" type="success" @click="upload">Submit</el-button>
            <div slot="tip" class="el-upload__tip">Click Choose to select resources which you want to upload.</div>
            <div slot="tip" class="el-upload__tip">Click Submit to upload chosen resources.</div>
          </el-upload>
        </el-card>

      </el-drawer>
    </div>
  </el-card>
</template>

<script>
import {getAllRecord, getGradeList, postCombineRecordInst, postDeleteRecord, postNewRecord} from '@/api/grade'
import {getDatetimeStr} from '@/utils/parse-day-time'
import {isStudent, isTeacher} from '@/utils/role'
import {postRecordUnitImportFromExcel} from '@/api/resources'

export default {
  name: 'Gradebook',
  components: {},
  data () {
    return {
      gradeList: [],
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
      combineDialogVisible: false,
      combineList: [],
      combineRecordName: '',

      uploadDrawer: false,
      fileList: []
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
            if(record.modifiedTime==null){
              record.modifiedTime = 'Not Scored Yet'
            }else{
              record.modifiedTime = getDatetimeStr(record.modifiedTime)
            }

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
        this.$message.info('Delete canceled')
      })
    },
    handleOpenCombineDialog () {
      let selectedRecord = this.$refs.recordTable.selection
      if (selectedRecord.length > 0) {
        this.combineList.splice(0, this.combineList.length)

        let notPointNum = 0
        for (let i = 0; i < selectedRecord.length; ++i) {
          if (selectedRecord[i].record.type === 'Point') {
            this.combineList.push({
              name: selectedRecord[i].record.rcdName,
              proportion: '',
              rcdId: selectedRecord[i].record.rcdId,
            })
          } else {
            notPointNum++
          }
        }
        if (this.combineList.length > 0) {
          this.combineDialogVisible = true

          if (notPointNum > 0) {
            this.$message.info(notPointNum + ' records are removed, which type is not Point')
          }
        } else {
          this.$message.error('No Point type record selected')
        }
      } else {
        this.$message.info('No record selected')
      }
    },
    combineRecord () {
      this.$confirm('Confirm to produce?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let combineRcdInstParamVO = {
          'coeList': [],
          'rcdIdList': [],
          'recordName': this.combineRecordName
        }

        for (let i = 0; i < this.combineList.length; ++i) {
          combineRcdInstParamVO.coeList.push(this.combineList[i].proportion)
          combineRcdInstParamVO.rcdIdList.push(this.combineList[i].rcdId)
        }
        console.log(combineRcdInstParamVO)

        postCombineRecordInst(combineRcdInstParamVO).then(resp => {
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
            this.combineRecordName = ''
            this.combineDialogVisible = false
            this.$message.success('Produce success')
          } else if (resp.data.code === 400) {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error(failResp.message)
        })
      }).catch(() => {
        this.$message.info('Produce canceled')
      })
    },

    downloadGradeBook () {
      window.open('http://localhost:8443/api/teacher/records/export?projId=' + this.$store.state.proj.projId)
    },
    handleCancelAdd () {
      this.addDialogVisible = false
      this.newRecord = {
        name: '',
        type: 'Point',
        baseContent: '',
      }
    },
    handleCancelCombine () {
      this.combineDialogVisible = false
      this.combineRecordName = ''
    },

    upload () {
      let formData = new window.FormData()
      this.fileList.forEach(file => {
        formData.append('files', file.raw)
      })
      formData.append('projId', this.$store.state.proj.projId)

      postRecordUnitImportFromExcel(formData).then(resp => {
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }

        this.initGradebook()
        this.uploadDrawer = false
        this.fileList.splice(0, this.fileList.length)
        this.$message.success('Upload success')
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })
    },
    batchImportChange (file, fileList) {
      this.fileList = fileList

      this.fileList.forEach(file => {
        if (file.name.substring(file.name.lastIndexOf('.') + 1) !== 'xlsx') {
          this.$message.error('Can only upload .xlsx')
          this.fileList.splice(0, this.fileList.length)
        }
      })
    },
    handleExceed () {
      this.$message.info('Can only upload one excel every time')
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

<!--{-->
<!--index: 1,-->
<!--name: 'assignment1',-->
<!--createdTime: '12/01/2020 14:00',-->
<!--creatorStr: 'Dehua Liu',-->
<!--type: 'Point',-->
<!--content: 96,-->
<!--baseContent: 100,-->
<!--derived: '',-->
<!--comment: '1.2 no pic -2; 1.4 no text -2'-->
<!--},-->
<!--{-->
<!--index: 2,-->
<!--name: 'assignment2',-->
<!--createdTime: '12/02/2020 14:00',-->
<!--creatorStr: 'Dehua Liu',-->
<!--type: 'Point',-->
<!--content: 90,-->
<!--baseContent: 100,-->
<!--derived: '',-->
<!--comment: 'None'-->
<!--},-->
<!--{-->
<!--index: 3,-->
<!--name: 'assignment3',-->
<!--createdTime: '12/03/2020 14:00',-->
<!--creatorStr: 'Dehua Liu',-->
<!--type: 'Point',-->
<!--content: 87,-->
<!--baseContent: 100,-->
<!--derived: '',-->
<!--comment: 'None'-->
<!--},-->
<!--{-->
<!--index: 4,-->
<!--name: 'report',-->
<!--createdTime: '12/06/2020 14:00',-->
<!--creatorStr: 'Dehua Liu',-->
<!--type: 'Grade',-->
<!--content: 'A',-->
<!--baseContent: null,-->
<!--derived: '',-->
<!--comment: 'report grade'-->
<!--},-->
<!--{-->
<!--index: 5,-->
<!--name: 'assignment total',-->
<!--createdTime: '12/08/2020 14:00',-->
<!--creatorStr: 'Dehua Liu',-->
<!--type: 'Point',-->
<!--content: 91.0,-->
<!--baseContent: 100,-->
<!--derived: 'assignment1 assignment2 assignment3',-->
<!--comment: 'assignment avg score'-->
<!--},-->
