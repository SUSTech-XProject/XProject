<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Resources</span>
    </div>
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
          <el-button @click="download(scope.row.index, scope.row.resourcesId)" type="primary" style="margin-left: 10px;">
            Download
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
import {getResourcesList, getDownload} from '../../../../api/resources'

export default {
  name: 'ResourcesStu',
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
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.modifiedTime)
    },
    init () {
      this.resourceslist.splice(0, this.resourceslist.length) // remove all
      // let projId = this.$store.state.proj.projId

      getResourcesList().then(resp => {
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
        console.log('fail in resoucesList. message=' + failResp.message)
      })
    },
    download (index, resoucesId) {
      let projId = this.$store.state.proj.projId
      getDownload(projId, resoucesId).then(resp => {
        console.log('success')
      }).catch(failResp => {
        console.log('fail in download. message=' + failResp.message)
      })
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
