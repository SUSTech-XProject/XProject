<template>
  <el-card class="base-card">
    <div slot="header">
      <span id="title-text">Events</span>
    </div>
    <div style="text-align: right;padding-right: 30px">
      <el-button type="primary" plain
                 icon="el-icon-circle-plus-outline" @click="openForm">Create
      </el-button>
      <el-button type="danger" plain
                 icon="el-icon-delete" @click="deleteTask">Delete
      </el-button>
    </div>
    <el-card class="base-card" style="min-height:455px">
      <el-table
        v-loading="tableLoading"
        :data="events"
        ref="singleTable"
        highlight-current-row
        @current-change="handleCurrentChange"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="Title">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="Creator">
                <span>{{ props.row.creator }}</span>
              </el-form-item>
              <el-form-item label="Create Time">
                <span>{{ props.row.createdTime }}</span>
              </el-form-item>
              <el-form-item label="Modify Time">
                <span>{{ props.row.modifiedTime }}</span>
              </el-form-item>
              <el-form-item label="Mode">
                <span>{{ props.row.mode }}</span>
              </el-form-item>
              <el-form-item label="Description">
                <span>{{ props.row.description }}</span>
              </el-form-item>

            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="Title"
          prop="title">
        </el-table-column>
        <el-table-column
          label="Creator"
          prop="creator">
        </el-table-column>
        <el-table-column
          label="Due time"
          prop="modifiedTime"
          sortable>
        </el-table-column>
        <el-table-column>
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary" plain
              icon="el-icon-search"
              @click="openEvent(scope.row.id)">Detail
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>


    <drawer :drawer.sync="drawerCtrl"
            :ID="eventId"
            :role="true"
            @closeEvent="closeEvent"></drawer>

    <el-dialog title="Create Task" :visible.sync="dialogFormVisible" width="500px">
      <el-form :model="form">
        <el-form-item label="Title" :label-width="formLabelWidth">
          <el-input v-model="form.title" style="width: 65%"></el-input>
        </el-form-item>
        <el-form-item label="Due date" :label-width="formLabelWidth">
          <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 65%"
            v-model="form.due"
            type="datetime"
            placeholder="selecting...">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Description" :label-width="formLabelWidth" style="width: 85%">
          <el-input v-model="form.desc" type="textarea" :clearable="true"></el-input>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth">
          <el-switch
            v-model="form.auto"
            active-text="Auto distribution">
          </el-switch>

        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="creatingTask">Confirm</el-button>
      </div>
    </el-dialog>
  </el-card>

</template>

<script>
import InstDrawer from '@/views/main/project/event/InstDrawer'
import {getEATaskList, postEventDeletion} from '@/api/event'
import {postEventCreation} from '@/api/event'

export default {
  name: 'TchEvent',
  components: {
    drawer: InstDrawer,
  },
  data () {
    return {
      events: [
        {
          id: 1,
          creator: 'Teacher 1',
          title: 'Midterm presentation',
          description: 'desc1',
          mode: '?',
          createdTime: '2020-12-32',
          modifiedTime: '2021-01-00'
        }, {
          id: 2,
          creator: 'Teacher 2',
          title: 'Final presentation',
          description: 'desc2',
          mode: '?',
          createdTime: '2020-12-32',
          modifiedTime: '2021-01-00'
        }],
      drawerCtrl: false,
      tableLoading: false,
      eventId: 0,
      currentRow: '',
      //
      dialogFormVisible: false,
      form: {
        title: '',
        due: '',
        desc: '',
        id: this.$store.state.proj.projId,
        auto: false,
      },
      formLabelWidth: '120px'
    }

  },
  mounted () {
    this.reLoad()
  },
  methods: {
    init () {
      let id = this.$store.state.proj.projId
      console.log('init tchEvent')
      getEATaskList(parseInt(id)).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let EAlist = resp.data.data
        console.log(EAlist)
        this.events.splice(0, this.events.length)
        for (let i = 0; i < EAlist.length; i++) {
          let EA = EAlist[i]
          this.events.push({
            id: EA.eaTask.eaTaskId,
            creator: EA.creator.tchName,
            title: EA.eaTask.title,
            description: EA.eaTask.description,
            mode: EA.eaTask.stdAdaptable,
            createdTime: EA.eaTask.createdTime.substr(0, 10) + ' ' + EA.eaTask.createdTime.substr(11, 8),
            modifiedTime: EA.eaTask.dueTime.substr(0, 10) + ' ' + EA.eaTask.dueTime.substr(11, 8),
          })
        }

      }).catch(failResp => {
        console.log('fail in getEAlist. message=' + failResp.message)
      })
    },
    reLoad () {
      this.tableLoading = true
      this.init()
      setTimeout(() => {
        this.tableLoading = false
      }, 1000)
    },
    openEvent (val) {
      this.drawerCtrl = true
      this.eventId = val
      console.log(val)
    },
    closeEvent () {
      this.drawerCtrl = false
    },
    handleCurrentChange (val) {
      this.currentRow = val
    },
    openForm () {
      this.dialogFormVisible = true
    },
    creatingTask () {
      //console.log(this.form)
      this.$confirm('Submit form?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        console.log(this.form)
        postEventCreation(this.form).then(resp => {
          if (resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Create task successfully'
            })
            this.dialogFormVisible = false
            this.reLoad()
          } else {
            this.$message.error(resp.data.message)
          }
        }).catch(failResp => {
          this.$message.error('Back-end no response')
        })
      }).catch(() => {
        console.log('???')
        this.$message({
          type: 'info',
          message: 'Canceled'
        })
      })
    },
    deleteTask () {
      //入参 this.currentRow
      if (this.currentRow === '') {
        this.$message.error('No task selected')
      } else {
        console.log(this.currentRow)
        this.$confirm('Delete selected task?', 'Warning', {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          console.log(this.currentRow.id)
          postEventDeletion(this.currentRow.id).then(resp => {
            if (resp.data.code === 200) {
              this.$message({
                type: 'success',
                message: 'Delete successfully'
              })
              this.reLoad()
            } else {
              this.$message.error(resp.data.message)
            }
          }).catch(failResp => {
            this.$message.error('Back-end no response')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Canceled'
          })
        })
      }

    },
  }

}
</script>

<style scoped>
.base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

#title-text {
  font-size: 20px;
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

</style>
