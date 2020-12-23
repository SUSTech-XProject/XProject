<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Resources</span>
    </div>

    <el-button @click="add_drawer = true"
               icon="el-icon-plus"
               type="success" style="margin-left: 75%;">
      Add
    </el-button>

    <el-drawer
      title="Add New Resources"
      :visible.sync="add_drawer"
      size="60%">

      <el-card id="add-card">
        Upload New Resources:
        <el-upload
          class="upload"
          ref="uploadDrawer"
          :action="'not-matter'"
          multiple
          :auto-upload="false"
          :on-change="batchImportChange"
          :file-list="fileList">

          <el-button slot="trigger" type="primary">Choose</el-button>
          <el-button style="margin-left: 10px;" type="success" @click="upload">Submit</el-button>
          <div slot="tip" class="el-upload__tip">Click Choose to select resources which you want to upload.</div>
          <div slot="tip" class="el-upload__tip">Click Submit to upload chosen resources.</div>
        </el-upload>
      </el-card>
    </el-drawer>

    <el-table
      :data="resourcesList"
      empty-text="No Data Found"
      :default-sort="{prop: 'index', order: 'increasing'}"
      style="width: 100%">
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column label="File Name" prop="resource.fileName" sortable/>
      <el-table-column label="Created time" prop="resource.createdTime" sortable/>
      <el-table-column label="Size" prop="resource.size" sortable/>
      <el-table-column>
        <template slot-scope="scope">
          <el-button @click="download(scope.row)" type="primary">Download</el-button>
          <el-button @click="deleteResource(scope.row)" type="danger"
                     style="margin-left: 10px;">
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
import {getDeleteResources, getResourcesList, postAddResources, getDownload} from '@/api/resources'

export default {
  name: 'ResourcesTch',
  components: {
    LeftBar, Header, card: Card, sele: Selector, drawer: Drawer
  },
  data () {
    return {
      add_drawer: false,
      newTitle: '',
      resourcesList: [],

      fileList: [],
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      getResourcesList(this.$store.state.proj.projId).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.resourcesList.splice(0, this.resourcesList.length)
        this.resourcesList = resp.data.data

        for (let i = 0; i < this.resourcesList.length; ++i) {
          this.resourcesList[i].resource.createdTime = getDatetimeStr(this.resourcesList[i].resource.createdTime)
        }
      }).catch(failResp => {
        console.log('fail in getAnnouncementList. message=' + failResp.message)
      })
    },
    deleteResource (row) {
      this.$confirm('Are you sure to delete?')
        .then(_ => {
          getDeleteResources(row.resource.srcId).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.init()
              this.$message.success('Delete success')
            } else if (resp.data.code === 400) {
              console.log(resp.data.message)
              this.$message.error(resp.data.message)
            }
          }).catch(failResp => {
            this.$alert('Error ' + failResp.message, 'Tips', {
              confirmButtonText: 'OK'
            })
          })
        })
        .catch(_ => {
          this.$message.info('Delete canceled')
        })
    },
    download (row) {
      window.open('http://localhost:8443/api/all/resource/download?srcId=' + row.resource.srcId)
    },

    batchImportChange (file, fileList) {
      this.fileList = fileList
    },
    upload () {
      let formData = new window.FormData()
      this.fileList.forEach(file=>{
        formData.append('files', file.raw)
      })
      formData.append('projId', this.$store.state.proj.projId)

      postAddResources(formData).then(resp => {
        if (resp.data.code === 200) {
          this.init()
          this.$message.success('Add successfully!')
        } else if (resp.data.code === 400) {
          this.$message.error(resp.data.message)
        }
      }).catch(failResp => {
        this.$message.error(failResp.message)
      })
    }
  }
}
</script>

<style scoped>
#base-card {
  margin: 15px 10px
}

#add-card {
  margin-left: 20px;
  margin-right: 20px;
}

.el-drawer {
  overflow: scroll;
}
</style>

<!--// getDownload(row.resource.srcId).then(resp => {-->
<!--//   console.log(resp)-->
<!--//   if (resp.code !== 200) {-->
<!--//     this.$message.error(resp.data.message)-->
<!--//     return false-->
<!--//   }-->
<!--//-->
<!--//   this.$message.success('Download success')-->
<!--// }).catch(failResp => {-->
<!--//   this.$message.error(failResp.message)-->
<!--//   console.log(failResp)-->
<!--// })-->

<!--      <el-table-column>-->
<!--        <template slot-scope="scope">-->
<!--          <el-button @click="deleteResource(scope.row)" type="danger"-->
<!--                     style="margin-left: 10px;">-->
<!--            Delete-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->

<!--:http-request="uploadResource"-->
<!--// this.$refs.uploadDrawer.submit()-->
<!--// uploadResource (param) {-->
<!--//   let file = param.file-->
<!--//   let formData = new window.FormData()-->
<!--//   this.fileList.forEach(file=>{-->
<!--//     formData.append('files', file.raw)-->
<!--//   })-->
<!--//   console.log(formData)-->
<!--//   // formData.append('files', file)-->
<!--//   formData.append('projId', this.$store.state.proj.projId)-->
<!--//   postAddResources(formData).then(resp => {-->
<!--//     if (resp.data.code === 200) {-->
<!--//       this.init()-->
<!--//       this.$message.success('Add successfully!')-->
<!--//     } else if (resp.data.code === 400) {-->
<!--//       this.$message.error(resp.data.message)-->
<!--//     }-->
<!--//   }).catch(failResp => {-->
<!--//     this.$message.error(failResp.message)-->
<!--//   })-->
<!--// },-->


<!--index: 1,-->
<!--resourcesId: 0,-->
<!--fileName: 'No-back-end-test',-->
<!--createdTime: '12/04/2020 12:24',-->
<!--size: '10kb'-->
<!--}-->
