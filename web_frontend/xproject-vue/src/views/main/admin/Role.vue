<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Role</span>
    </div>

    <el-table
      :data="roleList"
      empty-text="No Data Found"
      :default-sort = "{prop: 'index', order: 'increasing'}"
      style="width: 100%">
      <el-table-column label="" type="index" width="50px"/>
      <el-table-column label="User Name" prop="username" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.username}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Role Type" prop="roleType" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.roleType}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Register Time" prop="registerTime" :formatter="dateTimeFormatter" sortable>
        <template slot-scope="scope">
          <span style="margin-left: 0px">{{scope.row.registerTime}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Enable" prop="enable" sortable>
        <template slot-scope="scope">
<!--          <span style="margin-left: 0px">{{scope.row.status}}</span>-->
          <el-switch
            style="display: block"
            v-model="scope.row.statusSwitch"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="setStatus(scope.row.index)">
          </el-switch>
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
  name: 'Role',
  components: {
    LeftBar, Header, card: Card, sele: Selector, drawer: Drawer
  },
  data () {
    return {
      statusSwitch: true,
      roleList: [
        {
          index: 1,
          roleId: 0,
          username: 'test',
          roleType: 'Teacher',
          registerTime: '12/11/2020 1:05:02',
          status: 'Enable',
          statusSwitch: true
        },
        {
          index: 2,
          roleId: 4,
          username: 'test1',
          roleType: 'Student',
          registerTime: '12/11/2020 1:05:12',
          status: 'Disable',
          statusSwitch: false
        }
      ]
    }
  },
  // mounted () {
  //   this.init()
  // },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.registerTime)
    },
    init () {
      this.roleList.splice(0, this.roleList.length) // remove all

      getRoleList().then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.roleList.splice(0, this.roleList.length) // remove all
        for (let i = 0; i < resp.data.data.length; i++) {
          let newRow = {
            index: i + 1,
            roleId: resp.data.data[i].roleId,
            username: resp.data.data[i].username,
            roleType: resp.data.data[i].roleType,
            registerTime: getDatetimeStr(resp.data.data[i].registerTime),
            status: resp.data.data[i].status,
            statusSwitch: resp.data.data[i].status === 'Enable'
          }
          console.log(newRow)
          this.roleList.push(newRow)
        }
        console.log(this.roleList)
      }).catch(failResp => {
        console.log('fail in getRoletList. message=' + failResp.message)
      })
    },
    setStatus (index) {
      // alert(index)
      this.$confirm('Are you sure to change status?')
        .then(_ => {
          console.log('Changing status')
          postChangeStatus(this.roleList[index - 1].roleId).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.roleList[index - 1].statusSwitch = !this.roleList[index - 1].statusSwitch
              this.roleList[index - 1].status = this.roleList[index - 1].statusSwitch ? 'Enable' : 'Disable'
              this.$alert('Change successfully!', 'Tip')
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
        }).catch(_ => {
          this.roleList[index - 1].statusSwitch = !this.roleList[index - 1].statusSwitch
        })
      // this.statusSwitch = !this.statusSwitch
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
#title-text {
  font-size: 20px;
}
/deep/ :focus {
  outline: 0;
}
</style>
