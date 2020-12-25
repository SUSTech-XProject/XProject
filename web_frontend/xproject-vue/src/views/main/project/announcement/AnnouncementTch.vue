<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Announcement</span>
    </div>

    <el-button @click="dialogVisible = true" type="success" style="margin-left: 75%;">
      Create
    </el-button>

    <el-dialog
      title="Create new announcement"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose">
      <div>
        <el-card id="add_card">
          Title:
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 5}"
            placeholder="Please input title"
            v-model="new_title">
          </el-input>
          <br>
          <br>
          Message:
          <el-input
            type="textarea"
            :autosize="{ minRows: 7, maxRows: 10}"
            placeholder="Please input message"
            v-model="new_message">
          </el-input>
          <br>
          <br>
          <el-button @click="commit_add" type="primary" style="margin-left: 35%;">
            Create
          </el-button>
        </el-card>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="dialogVisible = false">Confirm</el-button>
  </span>
    </el-dialog>

    <el-table
      :data="announcementlist"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%">
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column label="Title" prop="rcdName" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.title}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Modified time" prop="modifiedTime" :formatter="dateTimeFormatter" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.modifiedTime}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Creator">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.tchName}}</span>
          <span style="margin-left: 5px; color: #afafaf;">{{scope.row.email}}</span>
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button @click="opendrawer(scope.row.annId, scope.row.title, scope.row.message, scope.row.index)"
                     type="primary" style="margin-left: 10px;">
            Detail
          </el-button>
          <el-button @click="deleterow(scope.row.index, scope.row.annId)" type="danger" style="margin-left: 10px;">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="Details"
      :visible.sync="details"
      width="50%"
      :before-close="handleClose">
      <div>
        <el-card id="modify_card">
          Modify title:
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 5}"
            placeholder="Please input title"
            v-model="mod_title">
          </el-input>
          <br>
          <br>
          Modify message:
          <el-input
            type="textarea"
            :autosize="{ minRows: 7, maxRows: 10}"
            placeholder="Please input message"
            v-model="mod_message">
          </el-input>
          <br>
          <br>
          <el-button @click="commit_modify()" type="primary" style="margin-left: 35%;">
          Update
        </el-button>
        </el-card>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="details = false">Cancel</el-button>
    <el-button type="primary" @click="details = false">Confirm</el-button>
  </span>
    </el-dialog>
  </el-card>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card from '@/components/card/announceList/index'
import Selector from '@/components/selector/single'
import Drawer from '@/components/drawer/announcement/index'
import {getDatetimeStr} from '@/utils/parse-day-time'
import {postModifyAnnouncement, getAnnouncementList, postAddAnnouncement, getDeleteAnnouncement} from '@/api/announcement'

export default{
  name: 'AnnouncementTch',
  components: {
    LeftBar, Header, card: Card, sele: Selector, drawer: Drawer
  },
  data () {
    return {
      add_drawer: false,
      modify_drawer: false,
      mod_id: 0,
      mod_message: '',
      mod_title: '',
      mod_annId: 0,
      new_message: '',
      new_title: '',
      new_name: '',
      dialogVisible: false,
      details: false,
      announcementlist: [
        {
          index: 1,
          annId: 0,
          projId: 0,
          creatorId: 1,
          title: 'Project of OOAD has been released',
          message: 'This is truly reliable!Start your project as soon as possible',
          modifiedTime: '12/04/2020 12:24',
          createdTime: '12/04/2020 12:24',
          tchName: 'ZYM',
          email: '30000000@mail.sustech.edu.cn'
        }
      ]
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    opendrawer (annId, title, message, index) {
      // alert(annId)
      this.details = true
      this.mod_annId = annId
      // this.modify_drawer = true
      this.mod_message = message
      this.mod_title = title
      this.mod_id = index - 1
    },
    commit_modify () {
      console.log('send modified data')
      if (this.mod_title === '') {
        this.$alert('Title can not be null', 'Warning')
        return false
      }
      if (this.mod_message === '') {
        this.$alert('Message can not be null', 'Warning')
        return false
      }
      let projId = this.$store.state.proj.projId
      postModifyAnnouncement(
        projId,
        this.mod_annId,
        this.mod_title,
        this.mod_message
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.announcementlist[this.mod_id].message = this.mod_message
          this.announcementlist[this.mod_id].title = this.mod_title
          this.$alert('Modify successfully!', 'Tip')
          this.init()
          this.details = false
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
    commit_add () {
      console.log('send created data')
      if (this.new_title === '') {
        this.$alert('Title can not be null', 'Warning')
        return false
      }
      if (this.new_message === '') {
        this.$alert('Message can not be null', 'Warning')
        return false
      }
      postAddAnnouncement(
        this.$store.state.proj.projId,
        this.new_title,
        this.new_message
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.init()
          this.dialogVisible = false
          this.new_title = ''
          this.new_message = ''
          this.new_name = ''
          this.$alert('Add successfully!', 'Tip')
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
    deleterow (index, annId) {
      // this.announcementlist.splice(index - 1, 1)

      this.$confirm('Are you sure to delete?')
        .then(_ => {
          console.log('deleting data')
          // alert(annId)
          getDeleteAnnouncement(
            JSON.stringify(annId)
            // annId
          ).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.announcementlist.splice(index - 1, 1)
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
        .catch(_ => {})
    },
    init () {
      this.announcementlist.splice(0, this.announcementlist.length) // remove all
      let projId = this.$store.state.proj.projId

      getAnnouncementList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.announcementlist.splice(0, this.announcementlist.length) // remove all
        for (let i = 0; i < resp.data.data.length; i++) {
          let new_row = {
            index: i + 1,
            annId: resp.data.data[i].annId,
            projId: resp.data.data[i].projId,
            creatorId: resp.data.data[i].creatorId,
            title: resp.data.data[i].title,
            message: resp.data.data[i].message,
            modifiedTime: getDatetimeStr(resp.data.data[i].modifiedTime),
            createdTime: resp.data.data[i].createdTime,
            tchName: resp.data.data[i].tchName,
            email: resp.data.data[i].email
          }
          console.log(new_row)
          this.announcementlist.push(new_row)
        }
        console.log(this.announcementlist)
      }).catch(failResp => {
        console.log('fail in getAnnouncementList. message=' + failResp.message)
      })
    },
    handleClose(done) {
      this.$confirm('Are you sure to close?')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  }
}
</script>

<style scoped>
html,body{
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0;
  /*外部间距也是如此设置*/
  margin: 0;
  /*统一设置高度为100%*/
  height: 100%;
}

.proj{
  height: 150px;
  text-align: center;
  margin: 0;
}
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
.el-drawer{
  overflow: scroll;
}
#base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}
#add_card, #modify_card{
  margin-left: 20px;
  margin-right: 20px;
}
#title-text {
  font-size: 20px;
}
  /deep/ :focus {
    outline: 0;
  }
>> .el-drawer :focus {
  outline: 0;
}
</style>
