<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Announcement</span>
    </div>

    <el-table
      :data="announcementlist"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%">

      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="Create Time: ">
              <span>{{ props.row.createdTime }}</span>
            </el-form-item>
            <br>
            <el-form-item label="Message: ">
              <span>{{ props.row.message }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

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
<!--          <el-button @click="opendrawer(scope.row.annId, scope.row.title, scope.row.message, scope.row.index)"-->
<!--                     type="primary" style="margin-left: 10px;">-->
<!--            Detail-->
<!--          </el-button>-->
<!--          <div>-->
<!--            <el-button @click="handleConfirm()"-->
<!--                       v-if="!confirmed"-->
<!--                       style="margin-top: 20px;  margin-left: 30px">-->
<!--              I know that-->
<!--            </el-button>-->
<!--            <div v-else-->
<!--                 style="margin-top: 30px; margin-left: 20px">-->
<!--              Already confirmed-->
<!--            </div>-->
<!--          </div>-->

        </template>
      </el-table-column>
    </el-table>

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
  name: 'AnnouncementStu',
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
      confirmed: false,
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
      this.mod_annId = annId
      this.modify_drawer = true
      this.mod_message = message
      this.mod_title = title
      this.mod_id = index - 1
    },
    commit_modify () {
      console.log('send modified data')
      postModifyAnnouncement(
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
          this.modify_drawer = false
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
      postAddAnnouncement(
        this.$store.state.proj.projId,
        this.new_title,
        this.new_message
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.init()
          this.add_drawer = false
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
          // eslint-disable-next-line camelcase
          let new_row = {
            index: i + 1,
            annId: resp.data.data[i].annId,
            projId: resp.data.data[i].projId,
            creatorId: resp.data.data[i].creatorId,
            title: resp.data.data[i].title,
            message: resp.data.data[i].message,
            modifiedTime: getDatetimeStr(resp.data.data[i].modifiedTime),
            createdTime: getDatetimeStr(resp.data.data[i].createdTime),
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
.el-drawer{
  overflow: scroll;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
#base-card {
  margin: 15px 10px
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
