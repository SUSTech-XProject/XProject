<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Submission</span>
    </div>
    <el-table
      :data="submissionList"
      style="width: 100%">

      <!--      expand part-->
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="Title">
              <span>{{ props.row.title }}</span>
            </el-form-item>
            <el-form-item label="Description">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="Create Time">
              <span>{{ props.row.createdTime }}</span>
            </el-form-item>
            <el-form-item label="Due time">
              <span>{{ props.row.dueTime }}</span>
            </el-form-item>
            <el-form-item label="Modify Time">
              <span>{{ props.row.modifiedTime }}</span>
            </el-form-item>
            <el-form-item label="Status">
              <span>{{ props.row.status }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <!--      expand part-->
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column label="Title" prop="title">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.title}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Modified Time" prop="modifiedTime" :formatter="dateTimeFormatter" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.modifiedTime}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Status" prop="status">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.status}}</span>
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope" style="margin-left: 10%">
            <el-button
              style="margin-left: 10px;"
              type="primary" plain
              icon="el-icon-document-add"
              @click="submit(scope.row.index, scope.row.submId)">Submit</el-button>
        </template>
      </el-table-column>
    </el-table>


    <el-drawer
      title="Submission"
      :visible.sync="submitDrawer"
      :size="submitSize">
      <div>
        <el-card id="detailCard">
          <div slot="header">
            <span id="detail title">Submission : {{currentTitle}}</span>
          </div>
          Choose files and submit:
          <br>
          <el-upload
            class="upload"
            ref="upload"
            action="http://localhost:8443/api/upload"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger"  type="primary">Choose</el-button>
            <el-button style="margin-left: 10px;" type="success" @click="submitUpload">Submit</el-button>
            <div slot="tip" class="el-upload__tip">Click Choose to select files which you want to submit.</div>
            <div slot="tip" class="el-upload__tip">Click Submit to submit chosen files.</div>
          </el-upload>
        </el-card>
      </div>
    </el-drawer>
  </el-card>

</template>

<script>
import {getDatetimeStr} from '@/utils/parse-day-time'
// import timeDrawer from '@/views/main/project/event/timeDrawer'
import {getSubmissionList} from '@/api/submission'
import {getAllSubmissionList} from '@/api/submission'
import {postAddSubmission} from '@/api/submission'
import {postModifySubmission} from '@/api/submission'
import {getDeleteSubmission} from '@/api/submission'

export default {
  name: "SubmissionStu",
  components:{
    // drawer:timeDrawer,
  },
  data(){
    return{
      submissionList:[
        {
          index:1,
          submId: 0,
          title: 'no-back-end test',
          description:'test without init from back-end',
          createdTime:'2020-12-15 15:03',
          modifiedTime:'2020-12-15 15:03',
          dueTime:'2020-12-15 15:03',
          finalTime: '2020-12-15 15:03',
          maxSubmissionTime: 10,
          status: 'S',
          resources: 'test',
        },
        {
          index:2,
          submId: 0,
          title: 'no-back-end test2',
          description:'test2 without init from back-end',
          createdTime:'2020-12-15 15:04',
          modifiedTime:'2020-12-15 15:04',
          dueTime:'2020-12-15 15:04',
          finalTime: '2020-12-15 15:04',
          maxSubmissionTime: null,
          status: 'E',
          resources: 'test2',
        }],
      submissionInstanceList:[
        {
          index:1,
          submissionInsId: 0,
          submissionId:0,
          projInsId:0,
          submitTime: '2020-12-18 15:02',
          submitter: 'Zhang San',
          submissionLeft: 9,
          attachments:'test.jpg'
        },
        {
          index:2,
          submissionInsId: 1,
          submissionId:1,
          projInsId:1,
          submitTime: '2020-12-19 02:02',
          submitter: 'Zhang Si',
          submissionLeft: 4,
          attachments:'test2.txt'
        }
      ],
      fileList:[],
      Status: [{
        status: 'S',
        label: 'Start'
      }, {
        status: 'E',
        label: 'End'
      }, {
        status: 'R',
        label: 'Restart'
      }],
      status: '',
      submitDrawer:false,
      eventId:0,
      submitSize: '50%',
      currentTitle: '',
    }

  },
  // mounted () {
  //   this.init()
  // },
  methods:{
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    initSubmissionList(){
      this.submissionList.splice(0, this.submissionList.length)   // remove all
      let projId = this.$store.state.proj.projId

      getSubmissionList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.submissionList.splice(0, this.submissionList.length)   // remove all
        for (let i = 0; i < resp.data.data.length; i++) {
          let new_row = {
            index: i+1,
            submissionInsId: resp.data.data[i].submissionInsId,
            submissionId: resp.data.data[i].submissionId,
            projInsId: resp.data.data[i].projInsId,
            submitTime: resp.data.data[i].submitTime,
            submitter: resp.data.data[i].submitter,
            submissionLeft: resp.data.data[i].submissionLeft,
            attachments: resp.data.data[i].attachments
          }
          console.log(new_row)
          this.submissionList.push(new_row)
        }
        console.log(this.submissionList)
      }).catch(failResp => {
        console.log('fail in getSubmissionList. message=' + failResp.message)
      })
    },
    submit(index, submId){
      // alert(index)
      this.submitDrawer = true
      this.currentTitle = this.submissionList[index - 1].title
      // alert(this.currentTitle)
      // this.initAllSubmissionList(submId)
      //test without init
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleChange(value) {
      console.log(value);
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
#add_card, #modify_card, #detailCard{
  margin-left: 20px;
  margin-right: 20px;
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
.el-drawer{
  overflow: scroll;
}

/deep/ :focus {
  outline: 0;
}
</style>

