<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Resources</span>
    </div>

    <el-button @click="add_drawer = true" type="success" style="margin-left: 75%;">
      Add
    </el-button>

    <el-drawer
      title="Add new resources"
      :visible.sync="add_drawer">
      <div>
        <el-card id="add_card">
          Input title:
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 5}"
            placeholder="Please input"
            v-model="newTitle">
          </el-input>
          <br>
          <br>
          Upload resources:
          <el-upload
            class="upload"
            ref="uploadfiles"
            :action="null"
            :before-upload="uploadFiles"
            :auto-upload="false"
            :multiple="false">
            <el-button slot="trigger"  type="primary">Choose</el-button>
            <el-button style="margin-left: 10px;" type="success" @click="submitUpload">Submit</el-button>
            <div slot="tip" class="el-upload__tip">Click Choose to select files which you want to submit.</div>
            <div slot="tip" class="el-upload__tip">Click Submit to submit chosen files.</div>
          </el-upload>
          <br>
        </el-card>
      </div>
      <br>
      <el-button @click="commitAdd" type="primary" style="margin-left: 50px;">
        Add
      </el-button>
    </el-drawer>

    <el-table
      :data="resourceslist"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%">
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column label="Title" prop="rcdName" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.title}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Created time" prop="createdTime" :formatter="dateTimeFormatter" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.createdTime}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Size">
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.size}}</span>
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button @click="deleterow(scope.row.index, scope.row.resourcesId)" type="danger" style="margin-left: 10px;">
            Delete
          </el-button>
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

export default {
  name: 'ResourcesTch',
  components: {
    LeftBar, Header, card: Card, sele: Selector, drawer: Drawer
  },
  data () {
    return {
      add_drawer: false,
      newTitle: '',
      resourceslist: [
        {
          index: 1,
          resourcesId: 0,
          title: 'No-back-end-test',
          createdTime: '12/04/2020 12:24',
          size: '10kb'
        }
      ]
    }
  },
  // mounted () {
  //   this.init()
  // },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    commit_add () {
      console.log('send created data')
      postAddResources(
        this.$store.state.proj.projId,
        this.newTitle
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
    deleterow (index, resourcesId) {
      // this.announcementlist.splice(index - 1, 1)

      this.$confirm('Are you sure to delete?')
        .then(_ => {
          console.log('deleting data')
          // alert(annId)
          getDeleteResources(
            JSON.stringify(resourcesId)
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
      this.resourceslist.splice(0, this.resourceslist.length) // remove all
      let projId = this.$store.state.proj.projId

      getResourcesList(projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.resourceslist.splice(0, this.resourceslist.length) // remove all
        for (let i = 0; i < resp.data.data.length; i++) {
          let newRow = {
            index: i + 1,
            resourcesId: resp.data.data[i].resourcesId,
            title: resp.data.data[i].title,
            createdTime: resp.data.data[i].createdTime,
            size: resp.data.data[i].size
          }
          console.log(newRow)
          this.resourceslist.push(newRow)
        }
        console.log(this.resourceslist)
      }).catch(failResp => {
        console.log('fail in getAnnouncementList. message=' + failResp.message)
      })
    },
    uploadFiles (file) {
      let formData = new window.FormData()
      // for (let i = 0; i < this.fileList.length; i++) {
      //   formData.append('file', file)
      // }
      console.log(this.sbmObj)

      formData.append('files', file)
      formData.append('sbmId', 1)
      formData.append('projId', 1)
      postUpload(formData).then(resp => {
        // console.log('In uploadExcel: %o', resp)
        console.log('???')
      }).catch(failResp => {
        console.log('fail in uploadExcel: %o', failResp)
      })
      this.submitDrawer = false
    },
    submitUpload () {
      this.$refs.uploadfiles.submit()
    }
  }
}
</script>

<style scoped>
#base-card {
  margin: 15px 10px
}
#add_card{
  margin-left: 20px;
  margin-right: 20px;
}
.el-drawer{
  overflow: scroll;
}
</style>
