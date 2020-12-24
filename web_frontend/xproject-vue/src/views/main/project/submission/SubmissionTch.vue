<template>
  <el-card id="base-card">
    <div slot="header">
      <span id="title-text">Submission</span>
      <el-button icon="el-icon-circle-plus-outline" @click="addDrawer=true" type="success" style="margin-left: 75%;">
        Create
      </el-button>

      <el-drawer
        title="Add New Submission"
        :visible.sync="addDrawer"
        :size="addSize">
        <div>
          <el-card id="add_card">
            Input submission title:
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 5}"
              placeholder="Please input"
              v-model="newTitle">
            </el-input>
            <br>
            <br>
            Input description:
            <el-input
              type="textarea"
              :autosize="{ minRows: 7, maxRows: 10}"
              placeholder="Please input"
              v-model="newDescription">
            </el-input>
            <br>
            <br>
            Due time:
            <el-date-picker
              v-model="dueTime"
              type="datetime"
              placeholder="choose due time"
              default-time="19:00:00"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
            <br>
            <br>
            Final time:
            <el-date-picker
              v-model="finalTime"
              type="datetime"
              placeholder="choose final time"
              default-time="19:00:00"
              value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
            <br>
            <br>
            Maximum Submission Time:
            <el-checkbox v-model="unlimited">Unlimited</el-checkbox>
            Or
            <el-input-number v-model="submissionTimes" :disabled="unlimited" @change="handleChange" :min="1"
                             :max="2147483646" label="maxSbm"></el-input-number>
            <br>
            <br>
            Status:
            <el-select v-model="status" placeholder="Choose Status">
              <el-option
                v-for="item in Status"
                :key="item.label"
                :label="item.label"
                :value="item.label">
                <span style="float: left">{{ item.status }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.label }}</span>
              </el-option>
            </el-select>
            <br>
          </el-card>
        </div>
        <br>
        <el-button @click="commitAdd" type="primary" style="margin-left: 50px;">
          Add
        </el-button>
      </el-drawer>

    </div>
    <el-table
      :data="submissionList"
      style="width: 100%">

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
            <el-form-item label="Final Time">
              <span>{{ props.row.finalTime }}</span>
            </el-form-item>
            <el-form-item label="Due Time">
              <span>{{ props.row.dueTime }}</span>
            </el-form-item>
            <el-form-item label="Creator">
              <span>{{ props.row.creator }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column min-width="160%" label="Title" prop="title">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="90%" label="Modified Time" prop="modifiedTime" :formatter="dateTimeFormatter"
                       sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{ scope.row.modifiedTime }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="45%" label="Status" prop="status">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{ scope.row.status }}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="170%">
        <template slot-scope="scope" style="margin-left: 10%">
          <el-button-group>
            <el-button
              style="margin-left: 10px;"
              size="mini"
              type="primary" plain
              icon="el-icon-info"
              @click="viewDetail(scope.row.index, scope.row.submId)">Detail
            </el-button>
            <el-button
              type="success" plain
              size="mini"
              icon="el-icon-edit"
              @click="modifySubmission(scope.row.index)">Modify
            </el-button>
            <el-button
              size="mini"
              @click="deleterow(scope.row.index, scope.row.submId)"
              type="danger"
              icon="el-icon-delete">
              Delete
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
      title="Modify The Submission"
      :visible.sync="modifyDrawer"
      :size="addSize">
      <div>
        <el-card id="modify_card">
          Modify submission title:
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 5}"
            placeholder="Please input"
            v-model="modTitle">
          </el-input>
          <br>
          <br>
          Modify description:
          <el-input
            type="textarea"
            :autosize="{ minRows: 7, maxRows: 10}"
            placeholder="Please input"
            v-model="modDescription">
          </el-input>
          <br>
          <br>
          Due time:
          <el-date-picker
            v-model="modDueTime"
            type="datetime"
            placeholder="choose due time"
            default-time="19:00:00"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
          <br>
          <br>
          Final time:
          <el-date-picker
            v-model="modFinalTime"
            type="datetime"
            placeholder="choose final time"
            default-time="19:00:00"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
          <br>
          <br>
          Maximum Submission Time:
          <el-checkbox v-model="modUnlimited">Unlimited</el-checkbox>
          Or
          <el-input-number v-model="modSubmissionTimes" :disabled="modUnlimited" @change="handleChange" :min="1"
                           :max="2147483646" label="maxSbm"></el-input-number>
          <br>
          <br>
          Status:
          <el-select v-model="status" placeholder="Choose Status">
            <el-option
              v-for="item in Status"
              :key="item.status"
              :label="item.label"
              :value="item.status">
              <span style="float: left">{{ item.status }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.label }}</span>
            </el-option>
          </el-select>
          <br>

        </el-card>
      </div>
      <br>
      <el-button @click="commitModify" type="primary" style="margin-left: 50px;">
        Modify
      </el-button>
    </el-drawer>

    <el-drawer
      title="Submission"
      :visible.sync="detailDrawer"
      :size="detailSize">

      <el-card id="detailCard">
        <div slot="header">
          <span id="detail title">Submission List : {{ currentTitle }}</span>
          <el-button @click="handleDownloadSbmInst"
                     style="float: right; padding: 3px 0"
                     type="text">
            Download
          </el-button>
        </div>

        <el-table :data="submissionInstanceList">
          <el-table-column label="" type="index" width="50px"/>
          <el-table-column label="Submitter" prop="submitter.stdName" sortable/>
          <el-table-column label="Submit Time" prop="submissionInst.submitTime" sortable/>
          <el-table-column label="Submissions Left" prop="submissionInst.sbmLeft" sortable/>
          <el-table-column label="Attachment" prop="submissionInst.attachments"/>
        </el-table>
      </el-card>
    </el-drawer>
  </el-card>

</template>

<script>
import {getDatetimeStr} from '@/utils/parse-day-time'
// import timeDrawer from '@/views/main/project/event/timeDrawer'
import {
  getDeleteSubmission,
  getSubmissionList,
  postAddSubmission,
  postModifySubmission,
  getSbmInsList
} from '@/api/submission'

export default {
  name: 'SubmissionTch',
  components: {
    // drawer:timeDrawer,
  },
  data () {
    return {
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
          status: 'S',
          creator: 'De hualiu'
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
          status: 'E',
          creator: 'ssss'
        }],
      submissionInstanceList: [
        {
          index: 1,
          submissionInsId: 0,
          submissionId: 0,
          projInsId: 0,
          submitTime: '2020-12-18 15:02',
          submitter: 'Zhang San',
          submissionLeft: 9,
          attachments: 'test.jpg'
        },
        {
          index: 2,
          submissionInsId: 1,
          submissionId: 1,
          projInsId: 1,
          submitTime: '2020-12-19 02:02',
          submitter: 'Zhang Si',
          submissionLeft: 4,
          attachments: 'test2.txt'
        }
      ],
      Status: [{
        status: 'En',
        label: 'Enabled'
      }, {
        status: 'Dis',
        label: 'Disabled'
      }],
      status: '',
      detailDrawer: false,
      eventId: 0,
      detailSize: '70%',
      addSize: '50%',
      addDrawer: false,
      newTitle: '',
      newDescription: '',
      currentTitle: '',
      dueTime: '',
      finalTime: '',
      submissionTimes: 1,
      unlimited: false,
      modTitle: '',
      modDescription: '',
      modDueTime: '',
      modFinalTime: '',
      modSubmissionTimes: 1,
      modUnlimited: false,
      modifyDrawer: false,
      currentIndex: null,

      viewingSbmId: ''
    }
  },
  mounted () {
    this.initSubmissionList()
  },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    dateFormat (date) {
      let seperator1 = '-'
      let seperator2 = ':'
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let strDate = date.getDate()
      let hh = new Date().getHours() < 10 ? '0' + new Date().getHours() : new Date().getHours()
      let mm = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes()
      let ss = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds()
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = '0' + strDate
      }
      return year + seperator1 + month + seperator1 + strDate + ' ' + hh + seperator2 + mm + seperator2 + ss
    },
    initSubmissionList () {
      this.submissionList.splice(0, this.submissionList.length) // remove all
      let projId = this.$store.state.proj.projId

      getSubmissionList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.submissionList.splice(0, this.submissionList.length) // remove all

        for (let i = 0; i < resp.data.data.length; i++) {
          // alert(resp.data.data[i].submission.title)
          // alert(resp.data.data[i].teacher.tchName)
          let dueTime = resp.data.data[i].submission.dueTime == null ? null : getDatetimeStr(resp.data.data[i].submission.dueTime)
          let finalTime = resp.data.data[i].submission.finalTime == null ? null : getDatetimeStr(resp.data.data[i].submission.finalTime)
          // eslint-disable-next-line camelcase
          let new_row = {
            index: i + 1,
            submId: resp.data.data[i].submission.sbmId,
            title: resp.data.data[i].submission.title,
            description: resp.data.data[i].submission.description,
            // createdTime:getDatetimeStr(resp.data.data[i].submission.createdTime),
            // modifiedTime:getDatetimeStr(resp.data.data[i].submission.modifiedTime),
            // dueTime:getDatetimeStr(resp.data.data[i].submission.dueTime),
            // finalTime:getDatetimeStr(resp.data.data[i].submission.finalTime),
            createdTime: getDatetimeStr(resp.data.data[i].submission.createdTime),
            modifiedTime: getDatetimeStr(resp.data.data[i].submission.modifiedTime),
            dueTime: dueTime,
            finalTime: finalTime,
            maxSubmissionTime: resp.data.data[i].submission.maxSbm,
            status: resp.data.data[i].submission.status,
            creator: resp.data.data[i].teacher.tchName
          }
          console.log(new_row)
          this.submissionList.push(new_row)
        }
        console.log(this.submissionList)
      }).catch(failResp => {
        console.log('fail in getSubmissionList. message=' + failResp.message)
      })
    },
    initAllSubmissionList (sbmId) {
      let projId = this.$store.state.proj.projId

      getSbmInsList(sbmId, projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$message.error(resp.data.message)
          return false
        }

        this.submissionInstanceList.splice(0, this.submissionInstanceList.length)
        this.submissionInstanceList = resp.data.data

        for (let i = 0; i < this.submissionInstanceList.length; i++) {
          this.submissionInstanceList[i].submissionInst.submitTime =
            getDatetimeStr(this.submissionInstanceList[i].submissionInst.submitTime)
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })
    },
    viewDetail (index, sbmId) {
      this.detailDrawer = true
      this.currentTitle = this.submissionList[index - 1].title
      this.viewingSbmId = sbmId
      this.initAllSubmissionList(sbmId)
    },
    modifySubmission (index) {
      this.currentIndex = index
      this.modifyDrawer = true
      this.currentTitle = this.submissionList[index - 1].title
      this.modTitle = this.submissionList[index - 1].title
      this.modDescription = this.submissionList[index - 1].description
      this.modDueTime = this.submissionList[index - 1].dueTime
      this.modFinalTime = this.submissionList[index - 1].finalTime
      this.modSubmissionTimes = this.submissionList[index - 1].maxSubmissionTime
      alert(index)
      alert(this.submissionList[index - 1].maxSubmissionTime)
      this.modUnlimited = this.submissionList[index - 1].maxSubmissionTime === 0
      this.status = this.submissionList[index - 1].status
      this.modResource = this.submissionList[index - 1].resources
    },
    commitAdd () {
      alert(this.unlimited)
      // alert(this.dueTime <= this.finalTime)
      // alert(getDatetimeStr(new Date()) >= this.dueTime)
      // alert(this.dateFormat(new Date()) <= this.dueTime)
      // alert(this.dueTime === '')
      // console.log(this.dueTime)
      // alert("duetime:"+this.dueTime)
      // alert("fianltime:"+this.finalTime)
      // alert(this.finalTime)
      if ((this.dueTime !== '' && this.dueTime < this.dateFormat(new Date())) || (this.finalTime !== '' && this.finalTime < this.dateFormat(new Date()))) {
        this.$alert('Due time or Final time can not before now!', 'Warning')
        return false
      }
      if (this.dueTime !== '' && this.finalTime !== '') {
        if (this.dueTime > this.finalTime) {
          this.$alert('Final time should not be earlier than due time!', 'Warning')
          return false
        }
      }
      if (this.status === '') {
        this.$alert('Status is not set!')
        return false
      }
      console.log('send new submission item')
      let projId = this.$store.state.proj.projId
      let maxSbmTime = this.unlimited ? 0 : this.submissionTimes
      // alert(this.unlimited)
      // alert(this.submissionTimes)
      // alert('Im here')
      postAddSubmission(
        projId,
        this.newTitle,
        this.newDescription,
        this.dueTime,
        this.finalTime,
        maxSbmTime,
        this.status
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.newTitle = ''
          this.newDescription = ''
          this.dueTime = ''
          this.finalTime = ''
          this.status = ''
          this.initSubmissionList()
          this.$alert('Add successfully!', 'Tip')
          this.addDrawer = false
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error ' + failResp.message, 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    },
    commitModify () {
      console.log('send modified data')
      let projId = this.$store.state.proj.projId
      let maxSbmTime = this.modUnlimited ? null : this.modSubmissionTimes
      postModifySubmission(
        this.submissionList[this.currentIndex - 1].submId,
        projId,
        this.modTitle,
        this.modDescription,
        this.modDueTime,
        this.modFinalTime,
        maxSbmTime,
        this.status,
        this.modResource
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.$alert('Modify successfully!', 'Tip')
          this.initSubmissionList()
          this.modifyDrawer = false
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
      }).catch(failResp => {
        this.$alert('Error ' + failResp.message, 'Tips', {
          confirmButtonText: 'OK'
        })
      })
    },
    deleterow (index, sbmId) {
      // this.announcementlist.splice(index - 1, 1)

      this.$confirm('Are you sure to delete?')
        .then(_ => {
          console.log('deleting data')
          // alert(annId)
          getDeleteSubmission(
            sbmId
            // annId
          ).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.initSubmissionList()
              this.$alert('Delete successfully!', 'Tip')
            } else if (resp.data.code === 400) {
              // this.announcementlist.splice(index - 1, 1)
              console.log(resp.data.message)
              this.$alert(resp.data.message, 'Tip', {
                confirmButtonText: 'OK'
              })
            }
          }).catch(failResp => {
            this.$alert('Error ' + failResp.message, 'Tips', {
              confirmButtonText: 'OK'
            })
          })
        })
        .catch(_ => {
        })
    },
    handleChange (value) {
      console.log(value)
    },
    openEvent (val) {
      this.drawerCtrl = true
      this.eventId = val
      console.log(val)
    },
    closeEvent () {
      this.drawerCtrl = false
    },
    handleDownloadSbmInst () {
      window.open('http://localhost:8443/api/teacher/submission/download?sbmId=' + this.viewingSbmId)
    }
  }

}
</script>

<style scoped>

#base-card {
  margin: 15px 10px;
}

#title-text {
  font-size: 20px;
}

#add_card, #modify_card, #detailCard {
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
>> .el-drawer :focus {
  outline: 0;
}
</style>

<!--// for (let i = 0; i < resp.data.data.length; i++) {-->
<!--//   // eslint-disable-next-line camelcase-->
<!--//-->
<!--//   let new_row = {-->
<!--//     index: i + 1,-->
<!--//     submissionInsId: resp.data.data[i].sbmInstId,-->
<!--//     submissionId: resp.data.data[i].sbmId,-->
<!--//     projInsId: resp.data.data[i].projInstId,-->
<!--//     submitTime: getDatetimeStr(resp.data.data[i].submitTime),-->
<!--//     submitter: resp.data.data[i].submitter,-->
<!--//     submissionLeft: resp.data.data[i].sbmLeft,-->
<!--//     attachments: resp.data.data[i].attachments-->
<!--//   }-->
<!--//   console.log(new_row)-->
<!--//   this.submissionInstanceList.push(new_row)-->
<!--// }-->
<!--// console.log(this.submissionInstanceList)-->

<!--      <br>-->
<!--      <el-button @click="commit_modify()" type="primary" style="margin-left: 50px;">-->
<!--        Update-->
<!--      </el-button>-->

<!--    <drawer :drawer.sync="drawerCtrl"-->
<!--            :ID="eventId"-->
<!--            @closeEvent = "closeEvent"></drawer>-->
