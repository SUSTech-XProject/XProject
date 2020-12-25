<template>
  <el-card class="base-card">
    <div slot="header" class="">
      <span id="title-text">Project List</span>
    </div>

    <div v-if="isStudent()">
      <el-row style="display: flex; margin: 0 0 20px 0; justify-content: flex-end">
        <el-button type="primary" plain icon="el-icon-search" @click="joinDialogVisible = true">Manage</el-button>
        <el-button type="primary" plain style="margin-right: 20%"
                   :icon="icn" @click="selectStar">Only star
        </el-button>
      </el-row>

      <div v-if="this.listArr.length===0" align="center">
        <el-card class="proj-card" shadow="never"
                 :body-style="{ paddingTop: '10px'}">
          <div style="font-size: 20px; color: #585858; margin-top: 28px">No data</div>
        </el-card>
      </div>

      <div v-for="(list, index) in listArr">
        <div v-if="list.isStar||!star" class="proj">
          <card v-bind:proj="list"
                v-bind:index="index"
                @getStarChange="getStarChange"
                @click.native="gotoProjOverview(list.id, list.name)"></card>
        </div>
      </div>

      <el-dialog title="Manage project"
                 width="80%"
                 @open="openJoinDialog"
                 :visible.sync="joinDialogVisible">
        <el-table
          ref="projTable"
          v-loading="projTableLoading"
          :data="dialogProjList"
          empty-text="No Other Project Found"
          :default-sort="{prop: 'index', order: 'increasing'}"
          style="width: 100%">
          <el-table-column label="" type="index" width="50px" sortable/>
          <el-table-column label="Name" prop="projName" width="300px" sortable/>
          <el-table-column label="Course" prop="courseName" width="160px" sortable/>
          <el-table-column label="Description" prop="description"/>
          <el-table-column label="Operation" width="280px">
            <template slot-scope="scope">
              <span v-if="checkInProj(scope.row.projId)">
                <el-button
                  size="mini"
                  type="danger" plain
                  icon="el-icon-minus"
                  @click="openQuitDialog(scope.row.projId, scope.row.projName)">Quit
              </el-button>
              </span>
              <span v-else>
                <el-button
                  size="mini"
                  type="primary" plain
                  icon="el-icon-plus"
                  @click="openConfirmDialog(scope.row.projId, scope.row.projName)">Join
              </el-button>
              </span>

            </template>
          </el-table-column>
        </el-table>

        <span slot="footer" class="dialog-footer">
        <el-button @click="joinDialogVisible = false">Cancel</el-button>
      </span>

        <el-dialog title="Tip"
                   append-to-body
                   :visible.sync="confirmDialogVisible">
          <div>Join project <span style="font-weight: bold">{{ this.confirmProjName }}</span> ?</div>
          <div style="padding-top: 20px">Group mark:
            <el-input v-model="groupMark" style="width: 50%;padding-left: 20px"
                      placeholder="input your group mark.."></el-input>
          </div>
          <span slot="footer" class="dialog-footer">
          <el-button @click="confirmDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="confirmDialogVisible = false; joinProj()">Join</el-button>
        </span>
        </el-dialog>

      </el-dialog>
    </div>

    <div v-if="isTeacher()">
      <el-row style="display: flex; margin: 0 0 20px 0; justify-content: flex-end">
        <el-button type="primary" icon="el-icon-plus" @click="addDialogVisible = true">Add</el-button>
        <el-button type="primary" plain icon="el-icon-edit" @click="joinDialogVisible = true">Manage</el-button>
        <el-button plain style="margin-right: 20%"
                   :icon="icn" @click="selectStar">Only star
        </el-button>
      </el-row>

      <div v-if="this.listArr.length===0" align="center">
        <el-card class="proj-card" shadow="never"
                 :body-style="{ paddingTop: '10px'}">
          <div style="font-size: 20px; color: #585858; margin-top: 28px">No data</div>
        </el-card>
      </div>

      <div v-for="(list, index) in listArr">
        <div v-if="list.isStar||!star" class="proj">
          <card v-bind:proj="list"
                v-bind:index="index"
                @getStarChange="getStarChange"
                @click.native="gotoProjOverview(list.id, list.name)"></card>
        </div>
      </div>
      <el-dialog title="Manage project"
                 width="80%"
                 @open="openJoinDialog"
                 :visible.sync="joinDialogVisible">
        <el-table
          ref="projTable"
          v-loading="projTableLoading"
          :data="dialogProjList"
          empty-text="No Other Project Found"
          :default-sort="{prop: 'index', order: 'increasing'}"
          style="width: 100%">
          <el-table-column label="" type="index" width="50px" sortable/>
          <el-table-column label="Name" prop="projName" width="300px" sortable/>
          <el-table-column label="Course" prop="courseName" width="160px" sortable/>
          <el-table-column label="Description" prop="description"/>
          <el-table-column label="Operation" width="280px">
            <template slot-scope="scope">
              <span v-if="checkInProj(scope.row.projId)">
                <el-button
                  size="mini"
                  type="info" plain
                  icon="el-icon-minus"
                  @click="openQuitDialog(scope.row.projId, scope.row.projName)">Quit
                </el-button>
                <el-button
                  size="mini"
                  type="danger" plain
                  icon="el-icon-delete"
                  @click="deleteProj(scope.row.projId, scope.row.projName)">Delete
              </el-button>
              </span>
              <span v-else>
                <el-button
                  size="mini"
                  type="primary" plain
                  icon="el-icon-plus"
                  @click="openConfirmDialog(scope.row.projId, scope.row.projName)">Join
              </el-button>
              </span>

            </template>
          </el-table-column>
        </el-table>

        <span slot="footer" class="dialog-footer">
        <el-button @click="joinDialogVisible = false">Cancel</el-button>
      </span>

        <el-dialog title="Tip"
                   append-to-body
                   :visible.sync="confirmDialogVisible">
          <div>Join project <span style="font-weight: bold">{{ this.confirmProjName }}</span> ?</div>
          <span slot="footer" class="dialog-footer">
          <el-button @click="confirmDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="confirmDialogVisible = false; joinProj()">Join</el-button>
        </span>
        </el-dialog>

      </el-dialog>
      <el-dialog title="Add a project"
                 width="30%"
                 :visible.sync="addDialogVisible">

        <el-form :model="newProj" label-width="auto" style="width: 90%; margin-left: 5%;">
          <!--          todo: 判空-->
          <el-form-item label="Project name">
            <el-input v-model="newProj.projName"></el-input>
          </el-form-item>
          <el-form-item label="Course name">
            <el-input v-model="newProj.courseName"></el-input>
          </el-form-item>
          <el-form-item label="Description">
            <el-input v-model="newProj.description"></el-input>
          </el-form-item>
          <el-form-item label="Year">
            <el-input v-model="newProj.year"></el-input>
          </el-form-item>
          <el-form-item label="Semester">
            <el-radio v-model="newProj.semester" label="01">Spring</el-radio>
            <el-radio v-model="newProj.semester" label="02">Fall</el-radio>
          </el-form-item>
        </el-form>

        <div align="right">
          <el-button type="primary" @click="handleAddProj()">Add</el-button>
          <el-button @click="addDialogVisible = false">Cancel</el-button>
        </div>
      </el-dialog>
    </div>

  </el-card>
</template>

<script>
import Card from '@/components/card/projectList/index'
import Selector from '@/components/selector/single'
import {postJoinProj, getProjList, getProjListBySch, postProjQuit} from '@/api/home_page'
import {isStudent, isTeacher} from '@/utils/role'
import {postAddProj, postDeleteProj, postProjectOverview} from '@/api/proj_overview'

export default {
  name: 'ProjectList',
  components: {
    card: Card,
    sele: Selector
  },
  data () {
    return {
      listArr: [
        // {id:1,name:"PROJECT",course:"COURSE",star:true},
        // {id:2,name:"PROJECT2",course:"COURSE2",star:false},
        // {id:3,name:"PROJECT3",course:"COURSE3",star:true}
      ],
      star: false,
      icn: 'el-icon-star-off',
      groupMark: '',

      projTableLoading: true,
      joinDialogVisible: false,
      addDialogVisible: false,
      confirmDialogVisible: false,
      dialogProjList: [],
      confirmProjName: '',
      confirmProjId: null,

      newProj: {
        projName: '',
        courseName: '',
        semester: '01',
        topic: null,
        settings: null,
        year: '',
        description: ''
      },
    }
  },
  mounted () {
    this.initProjList()
  },
  methods: {
    //adding
    //join/quit proj
    deleteProj(id,name){
      console.log(id, name)
      this.$confirm('Are you sure to quit ' + name + ' ?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        postDeleteProj(id).then(resp => {
          if (resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Delete successfully'
            })
            this.openJoinDialog()
            this.initProjList()
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
    },
    checkInProj (val) {
      console.log(val)
      for (let i = 0; i < this.listArr.length; i++) {
        if (this.listArr[i].id === val) {
          return true
        }
      }
      return false
    },
    //todo: quit proj dialog
    openQuitDialog (id, name) {
      console.log(id, name)
      this.$confirm('Are you sure to quit ' + name + ' ?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        postProjQuit(id).then(resp => {
          if (resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Quit successfully'
            })

            this.initProjList()
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
    },
    openJoinDialog () {
      this.projTableLoading = true
      this.confirmDialogVisible = false

      getProjListBySch().then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.dialogProjList = resp.data.data
        console.log('+++++++')
        console.log(this.dialogProjList)
      }).catch(failResp => {
        console.log('fail in getProjListBySch, %o', failResp)
        this.joinDialogVisible = false
        this.$alert('Fail to load', 'Tip', {
          confirmButtonText: 'OK'
        })
      })

      this.projTableLoading = false
    },
    openConfirmDialog (projId, projName) {
      this.confirmProjId = projId
      this.confirmProjName = projName
      this.confirmDialogVisible = true
    },
    openManageDialog () {
      this.projTableLoading = true
      this.confirmDialogVisible = false
      if (isStudent()) {
        getProjListBySch().then(resp => {
          if (resp.data.code !== 200) {
            this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
              confirmButtonText: 'OK'
            })
            return false
          }
          this.dialogProjList = resp.data.data
          console.log('+++++++')
          console.log(this.dialogProjList)
        }).catch(failResp => {
          console.log('fail in getProjListBySch, %o', failResp)
          this.joinDialogVisible = false
          this.$alert('Fail to load', 'Tip', {
            confirmButtonText: 'OK'
          })
        })
      }

      this.projTableLoading = false
    },
    joinProj () {
      postJoinProj(
        this.confirmProjId, this.groupMark
      ).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        } else {
          this.$message({
            type: 'success',
            message: 'Join successfully'
          })
          this.initProjList()
        }
        //this.dialogProjList = resp.data.data
      }).catch(failResp => {
        console.log('fail in getProjListBySch, %o', failResp)
        this.$alert('Fail to join', 'Tip', {
          confirmButtonText: 'OK'
        })
      })
    },
    getStarChange (val) {
      console.log(val)
      //更改相应project star值
      for (let i = 0; i < this.listArr.length; i++) {
        var va = this.listArr[i]
        if (va.id === val) {
          console.log(this.listArr[i].star)
          var temp = va.star
          temp = !temp
          va.star = temp
          console.log(this.listArr[i].star)
        }
      }
      console.log('testing')
      console.log(this.listArr)
    },

    gotoProjOverview (projId, projName) {
      let projRecord = {projId: projId, projName: projName}
      console.log('goto proj overview.')
      console.log(projRecord)
      this.$store.commit('setProj', projRecord)
      this.$router.push({name: 'ProjOverview', params: {proj_id: projId}})
    },

    selectStar () {
      for (let i = 0; i < this.listArr.length; i++) {
        console.log(this.listArr[i].isStar)
      }
      let temp = this.star
      this.star = !temp
      this.icn = !temp ? 'el-icon-star-on' : 'el-icon-star-off'
      console.log(this.star)
    },
    initProjList () {
      getProjList().then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        let projList = resp.data.data
        this.listArr.splice(0, this.listArr.length)   // remove all
        for (let i = 0; i < projList.length; i++) {
          let proj = projList[i]
          this.listArr.push({
            id: proj.projId,
            name: proj.projName,
            course: proj.courseName,
            description: proj.description,
            star: false,
            isStar: false
          })
        }
        console.log(this.listArr)
      }).catch(failResp => {
        console.log('fail in getProjList. message=' + failResp.message)
      })
    },
    isStudent () {
      return isStudent()
    },
    isTeacher () {
      return isTeacher()
    },

    handleAddProj () {
      this.$confirm('Confirm to add?', 'Tip', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        type: 'warning'
      }).then(() => {
        let projectVO = {
          'courseName': this.newProj.courseName,
          'description': this.newProj.description,
          'projId': null,
          'projName': this.newProj.projName,
          'projSettings': null,
          'term': this.newProj.year + this.newProj.semester,
          'topics': null
        }

        postAddProj(projectVO).then(resp => {
          if (resp.data.code !== 200) {
            this.$message.error(resp.data.code)
            return false
          }
          this.initProjList()

          this.joinDialogVisible = false
          this.confirmDialogVisible = false
          this.addDialogVisible = false
          this.newProj = {
            projName: '',
            courseName: '',
            semester: '01',
            topic: null,
            settings: null,
            year: '',
            description: ''
          }
          this.$message.success('Add success')
        }).catch(failResp => {
          this.$message.error(failResp.message)
          console.log(projectVO)
        })
      }).catch(() => {
        this.$message.info('Add canceled')
      })
    }
  }
}
</script>

<style scoped>
html, body {
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0;
  /*外部间距也是如此设置*/
  margin: 0;
  /*统一设置高度为100%*/
  height: 100%;
}

#temp {
  height: 100%;
}

.proj {
  height: 120px;
  text-align: center;
  padding-left: 10%;
}

.el-col, .el-row {
  margin: 0;
  padding: 0;
  font-size: 0;
}

.base-card {
  margin: 15px 10px;
  min-height: 95.2%;
}

#title-text {
  font-size: 20px;
}

.proj-card {
  width: 80%;
  height: 100px;
}
</style>
