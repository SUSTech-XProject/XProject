<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">School</span>
    </div>

    <el-button @click="addDrawer = true" type="success" style="margin-left: 75%;">
      Add
    </el-button>

    <el-drawer
      title="Add New School"
      :visible.sync="addDrawer"
      :size="addSize">
      <div>
        <el-card id="add_card">
          Input New School Name:
          <el-input
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 3}"
            placeholder="Please input"
            v-model="newSchoolName">
          </el-input>
          <br>
          <br>
          Input New School's Location:
          <br>
          (In form of "Province, City")
          <el-input
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 3}"
            placeholder="Please input"
            v-model="newSchoolLocation">
          </el-input>
          <br>
        </el-card>
      </div>
      <br>
      <el-button @click="commitAdd" type="primary" style="margin-left: 50px;">
        Add
      </el-button>
    </el-drawer>

    <el-table
      :data="schoolList"
      height="400"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%">
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column label="School Name" prop="schName" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.schName}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Location" prop="location" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.location}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Allow Register Teacher" prop="enable" sortable>
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.allowTch"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="setTchStatus(scope.row.index)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="Allow Register Student" prop="enable" sortable>
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.allowStu"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="setStuStatus(scope.row.index)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button @click="edit(scope.row.index, scope.row.schId)" type="primary" style="margin-left: 10px;">
            Edit
          </el-button>
          <el-button @click="del(scope.row.index, scope.row.schId)" type="danger" style="margin-left: 10px;">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
      title="Details"
      :visible.sync="modifyDrawer">
      <div>
        <el-card id="modify_card">
          Edit School Name:
          <el-input
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 3}"
            placeholder="Please input"
            v-model="editSchoolName">
          </el-input>
          <br>
          <br>
          Edit School Location:
          <br>
          (In form of "Province, City")
          <el-input
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 3}"
            placeholder="Please input"
            v-model="editSchoolLocation">
          </el-input>
          <br>
        </el-card>
      </div>
      <br>
      <el-button @click="commitEdit()" type="primary" style="margin-left: 50px;">
        Edit
      </el-button>
    </el-drawer>
  </el-card>
</template>

<script>
import LeftBar from '@/components/sidebar/index'
import Header from '@/components/header/index'
import Card from '@/components/card/announceList/index'
import Selector from '@/components/selector/single'
import Drawer from '@/components/drawer/announcement/index'
// import {getDeleteResources, getResourcesList, postAddResources, postDownload} from '../../api/resources'
import {
  getDeleteSchool,
  getSchoolList,
  postAddSchool,
  postEditSchool
} from '../../../api/admin'

export default {
  name: 'School',
  components: {
    LeftBar, Header, card: Card, sele: Selector, drawer: Drawer
  },
  data () {
    return {
      newSchoolName: '',
      newSchoolLocation: '',
      modifyDrawer: false,
      editSchoolName: '',
      editSchoolLocation: '',
      currentSchId: null,
      currentIndex: null,
      addDrawer: false,
      addSize: '40%',
      schoolList: [
        {
          index: 1,
          schId: 1,
          schName: 'SUSTech',
          location: 'Guangdong, Shenzhen',
          allowTch: true,
          allowStu: true
        }
      ]
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    commitAdd (param) {
      console.log('send created data')
      if (this.newSchoolName === '') {
        this.$alert('School name can not be null', 'Warning')
        return false
      }
      if (this.newSchoolLocation === '') {
        this.$alert('Location can not be null', 'Warning')
        return false
      }
      postAddSchool(
        this.newSchoolName,
        this.newSchoolLocation
      ).then(resp => {
        console.log('get response : %o', resp)
        if (resp.data.code === 200) {
          this.newSchoolName = ''
          this.newSchoolLocation = ''
          this.init()
          this.addDrawer = false
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
    edit (index, schId) {
      this.modifyDrawer = true
      this.editSchoolName = this.schoolList[index - 1].schName
      this.editSchoolLocation = this.schoolList[index - 1].location
      this.currentSchId = schId
      this.currentIndex = index
    },
    commitEdit () {
      console.log('send edited data')
      alert(this.editSchoolName)
      postEditSchool(
        this.currentSchId,
        this.editSchoolName,
        this.editSchoolLocation,
        this.schoolList[this.currentIndex - 1].allowStu,
        this.schoolList[this.currentIndex - 1].allowTch
      ).then(resp => {
        console.log('get response : ' + resp)
        if (resp.data.code === 200) {
          this.schoolList[this.currentSchId].schName = this.editSchoolName
          this.schoolList[this.currentSchId].location = this.editSchoolLocation
          this.editSchoolName = ''
          this.editSchoolLocation = ''
          this.$alert('Modify successfully!', 'Tip')
          this.init()
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
    del (index, schId) {
      // this.announcementlist.splice(index - 1, 1)

      this.$confirm('Are you sure to delete?')
        .then(_ => {
          console.log('deleting school')
          getDeleteSchool(
            JSON.stringify(schId)
          ).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.schoolList.splice(index - 1, 1)
              this.$alert('Delete successfully!', 'Tip')
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
        })
        .catch(_ => {})
    },
    init () {
      this.schoolList.splice(0, this.schoolList.length) // remove all
      // let projId = this.$store.state.proj.projId

      getSchoolList().then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.schoolList.splice(0, this.schoolList.length) // remove all
        for (let i = 0; i < resp.data.data.length; i++) {
          let newRow = {
            index: i + 1,
            schId: resp.data.data[i].schId,
            schName: resp.data.data[i].schName,
            location: resp.data.data[i].location,
            allowTch: resp.data.data[i].tchCreate,
            allowStu: resp.data.data[i].stdCreate
          }
          console.log(newRow)
          this.schoolList.push(newRow)
        }
        console.log(this.schoolList)
      }).catch(failResp => {
        console.log('fail in getSchoolList. message=' + failResp.message)
      })
    },
    setTchStatus (index) {
      this.$confirm('Are you sure to change status?')
        .then(_ => {
          postEditSchool(
            this.schoolList[index - 1].schId,
            this.schoolList[index - 1].schName,
            this.schoolList[index - 1].location,
            this.schoolList[index - 1].allowStu,
            !this.schoolList[index - 1].allowTch
          ).then(resp => {
            if (resp.data.code !== 200) {
              this.init()
              this.$alert('Change successfully!', 'Tip')
            } else if (resp.data.code === 400) {
              console.log(resp.data.message)
              this.$alert(resp.data.message, 'Tip', {
                confirmButtonText: 'OK'
              })
              this.init()
            }
            console.log(this.schoolList)
          }).catch(failResp => {
            console.log('fail in setTeacherStatus. message=' + failResp.message)
          })
        }).catch(_ => {
          this.init()
        })
    },
    setStuStatus (index) {
      this.$confirm('Are you sure to change status?')
        .then(_ => {
          postEditSchool(
            this.schoolList[index - 1].schId,
            this.schoolList[index - 1].schName,
            this.schoolList[index - 1].location,
            !this.schoolList[index - 1].allowStu,
            this.schoolList[index - 1].allowTch
          ).then(resp => {
            if (resp.data.code !== 200) {
              this.init()
              this.$alert('Change successfully!', 'Tip')
            } else if (resp.data.code === 400) {
              console.log(resp.data.message)
              this.$alert(resp.data.message, 'Tip', {
                confirmButtonText: 'OK'
              })
              this.init()
            }
            console.log(this.schoolList)
          }).catch(failResp => {
            console.log('fail in setStudentStatus. message=' + failResp.message)
          })
        }).catch(_ => {
          this.init()
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
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.el-drawer{
  overflow: scroll;
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
