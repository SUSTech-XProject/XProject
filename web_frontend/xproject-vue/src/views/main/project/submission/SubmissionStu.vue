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
          <span style="margin-left: 0px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Modified Time" prop="modifiedTime" :formatter="dateTimeFormatter" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{ scope.row.modifiedTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Status" prop="status">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{ scope.row.status }}</span>
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope" style="margin-left: 10%">
          <el-button
            style="margin-left: 10px;"
            type="primary" plain
            icon="el-icon-document-add"
            @click="submit(scope.row)">Submit
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
      title="Submission"
      :visible.sync="submitDrawer"
      :size="submitSize">

      <el-card v-if="haveTeam" class="detailCard">
        <div slot="header">
          <span id="detail title">Submission : {{ currentTitle }}</span>
        </div>

        <div v-if="haveSbmInst"
             style="margin-bottom: 20px">
          History:
          <div>Submitter: {{ submitter.stdName }} {{ submitter.stdNo }}</div>
          <div>Submitted time: {{ sbmInstStd.submitTime }}</div>
          <div>Submissions remaining: {{ sbmInstStd.sbmLeft }}</div>
        </div>

        <div v-else style="margin-bottom: 20px">
          No submit history
        </div>

        <el-upload
          class="upload"
          ref="uploadDrawer"
          :action="'not-matter'"
          multiple
          :auto-upload="false"
          :on-change="batchImportChange"
          :file-list="fileList"
          style="margin-top: 20px">

          <el-button slot="trigger" type="primary">Choose</el-button>
          <el-button style="margin-left: 10px;" type="success" @click="submitUpload">Submit</el-button>

          <div style="margin-top: 20px;">
            <div slot="tip" class="el-upload__tip">Click Choose to select files which you want to submit.</div>
            <div slot="tip" class="el-upload__tip">Click Submit to submit chosen files.</div>
          </div>
        </el-upload>
      </el-card>
      <el-card v-else class="detailCard" >
        <div style="margin-top: 20px;margin-bottom: 20px;text-align: center;color: #6d7178">No Team Yet :(</div>
      </el-card>
    </el-drawer>
  </el-card>

</template>

<script>
import {getDatetimeStr} from '@/utils/parse-day-time'
import {getSbmInstStd, getSubmissionList} from '@/api/submission'
import {postUploadSubmission} from '@/api/submission'

export default {
  name: 'SubmissionStu',
  components: {},
  data () {
    return {
      //
      haveTeam:true,
      //
      sbmObj: null,
      submissionList: [
        {
          index: 1,
          submId: 0,
          title: 'no-back-end test',
          description: 'test without init from back-end',
          createdTime: '2020-12-15 15:03',
          modifiedTime: '2020-12-15 15:03',
          dueTime: '2020-12-15 15:03',
          finalTime: '2020-12-15 15:03',
          maxSubmissionTime: 10,
          status: 'S'
        },
        {
          index: 2,
          submId: 0,
          title: 'no-back-end test2',
          description: 'test2 without init from back-end',
          createdTime: '2020-12-15 15:04',
          modifiedTime: '2020-12-15 15:04',
          dueTime: '2020-12-15 15:04',
          finalTime: '2020-12-15 15:04',
          maxSubmissionTime: null,
          status: 'E'
        }],
      fileList: [],
      status: '',
      submitDrawer: false,
      eventId: 0,
      submitSize: '50%',
      currentTitle: '',
      currentSbmId: 0,
      currentProjInsId: 0,

      sbmInstStd: {},
      submitter: {},
      haveSbmInst: true
    }
  },
  mounted () {
    this.initSubmissionList()
  },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    initSubmissionList () {
      let projId = this.$store.state.proj.projId

      getSubmissionList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$message(resp.data.message)
          return false
        }
        this.submissionList.splice(0, this.submissionList.length)
        console.log(resp.data.data)

        for (let i = 0; i < resp.data.data.length; i++) {
          let dueTime = resp.data.data[i].submission.dueTime == null ? null : getDatetimeStr(resp.data.data[i].submission.dueTime)
          let finalTime = resp.data.data[i].submission.finalTime == null ? null : getDatetimeStr(resp.data.data[i].submission.finalTime)

          let new_row = {
            index: i + 1,
            sbmId: resp.data.data[i].submission.sbmId,
            title: resp.data.data[i].submission.title,
            description: resp.data.data[i].submission.description,
            createdTime: getDatetimeStr(resp.data.data[i].submission.createdTime),
            modifiedTime: getDatetimeStr(resp.data.data[i].submission.modifiedTime),
            dueTime: dueTime,
            finalTime: finalTime,
            maxSubmissionTime: resp.data.data[i].submission.maxSubmissionTime,
            status: resp.data.data[i].submission.status,
            resources: resp.data.data[i].submission.resources,
            creator: resp.data.data[i].teacher.tchName
          }
          this.submissionList.push(new_row)
        }
        console.log(this.submissionList)
      }).catch(failResp => {
        console.log('fail in getSubmissionList. message=' + failResp.message)
      })
    },

    submit (sbmObj) {
      this.submitDrawer = true
      this.sbmObj = sbmObj

      getSbmInstStd(sbmObj.sbmId).then(resp => {
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }
        console.log(resp)
        if(resp.data.message==="No team yet"){
          console.log("no team")
          this.haveTeam=false
        }else  if (resp.data.data != null) {
          this.haveTeam=true
          this.haveSbmInst = true
          this.sbmInstStd = resp.data.data.submissionInst
          this.submitter = resp.data.data.submitter

          this.sbmInstStd.submitTime = getDatetimeStr(this.sbmInstStd.submitTime)
        } else {
          this.haveTeam=true
          this.haveSbmInst = false
          this.sbmInstStd = {}
          this.submitter = {}
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })
    },
    submitUpload () {
      let formData = new window.FormData()
      this.fileList.forEach(file => {
        formData.append('files', file.raw)
      })
      formData.append('sbmId', this.sbmObj.sbmId)

      postUploadSubmission(formData).then(resp => {
        console.log(resp)
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }
        this.initSubmissionList()
        this.$message.success('Submit success')
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })
    },
    batchImportChange (file, fileList) {
      this.fileList = fileList
    },

    openEvent (val) {
      this.drawerCtrl = true
      this.eventId = val
      console.log(val)
    },
    closeEvent () {
      this.drawerCtrl = false
    }
  }

}
</script>

<style scoped>

#base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

#title-text {
  font-size: 20px;
}

#add_card, #modify_card, .detailCard {
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

.el-drawer {
  overflow: scroll;
}

/deep/ :focus {
  outline: 0;
}
</style>
<!--// uploadFiles (file) {-->
<!--//   let formData = new window.FormData()-->
<!--//   // for (let i = 0; i < this.fileList.length; i++) {-->
<!--//   //   formData.append('file', file)-->
<!--//   // }-->
<!--//   console.log(this.sbmObj)-->
<!--//-->
<!--//   formData.append('files', file)-->
<!--//   formData.append('sbmId', this.sbmObj.submId)-->
<!--//   formData.append('projId', this.$store.state.proj.projId)-->
<!--//   postUploadSubmission(formData).then(resp => {-->
<!--//     // console.log('In uploadExcel: %o', resp)-->
<!--//     console.log('???')-->
<!--//   }).catch(failResp => {-->
<!--//     console.log('fail in uploadExcel: %o', failResp)-->
<!--//   })-->
<!--//   this.submitDrawer = false-->
<!--// },-->

<!--// this.currentTitle = this.submissionList[index - 1].title-->
<!--// this.currentSbmId = this.submissionList[index - 1].submId-->
<!--// alert(typeof this.currentSbmId)-->
<!--// alert(typeof ('' + this.currentSbmId))-->
<!--// alert(this.currentTitle)-->
<!--// this.initAllSubmissionList(submId)-->
<!--// test without init-->
