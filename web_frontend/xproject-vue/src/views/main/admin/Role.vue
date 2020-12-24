<template>
  <el-card id="base-card">
    <div slot="header" class="">
      <span id="title-text">Role</span>
    </div>

    <el-button @click="addDrawer = true" type="success" style="margin-left: 75%;">
      Add
    </el-button>

    <el-drawer
      title="Add New Role"
      :visible.sync="addDrawer"
      :size="addSize">
      <el-card id="add_card">
        <el-form class="reg-container" label-position="right" :model="registerForm"
                 label-width="100px" status-icon :rules="rules" ref="registerForm">
          <el-form-item label="Username" prop="username" style="margin-bottom: 40px">
            <el-input type="text" v-model="registerForm.username"
                      prefix-icon="el-icon-user" auto-complete="off"
                      placeholder="Please input username"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="password" style="margin-bottom: 40px">
            <el-input type="password" v-model="registerForm.password"
                      prefix-icon="el-icon-lock" auto-complete="off"
                      placeholder="Please input password" show-password></el-input>
          </el-form-item>
          <el-form-item label="Repeat" prop="passwordRepeat" style="margin-bottom: 40px">
            <el-input type="password" v-model="registerForm.passwordRepeat"
                      prefix-icon="el-icon-lock" auto-complete="off"
                      placeholder="Please input password again" show-password></el-input>
          </el-form-item>

          <el-form-item label="Role" style="margin-bottom: 40px">
            <el-radio-group v-model="registerForm.roleType">
              <el-radio border label="Admin">Administrator</el-radio>
              <el-radio border label="Teacher">Teacher</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item v-if="registerForm.roleType==='Teacher'" label="School" style="margin-bottom: 40px">
            <el-select v-model="registerForm.schId" placeholder="selecting..." style="width: 80%">
              <el-option

                v-for="item in schoolList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 8px">{{ item.location }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <!--      add school info return school id -->

          <div style="width: 100%; display: flex; align-content: center; justify-content: center;">
            <el-button class='reg-btm' type="primary" v-on:click="register" ref="reg-btm">Create</el-button>
          </div>
        </el-form>
      </el-card>

    </el-drawer>

    <el-table
      :data="roleList"
      height="600"
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
      <el-table-column label="Enabled" prop="enable" sortable>
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
import {getRoleList, postAddRole, postChangeStatus, postRegister} from '../../../api/role'
import {getSchoolList, postAddSchool} from '../../../api/admin'
import {validPassword, validUsername} from '@/utils/validate'

export default {
  name: 'Role',
  components: {
    LeftBar, Header, card: Card, sele: Selector, drawer: Drawer
  },
  data () {
    const validateUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input username'));
      }
      setTimeout(() => {
        if (!validUsername(value)) {
          return callback(new Error('The username must have at least 8 characters and 24 at most. ' +
            'It should not start with a number.'));
        } else {
          return callback();
        }
      }, 1000);
    };

    const validatePwd = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input password'));
      }
      setTimeout(() => {
        if (!validPassword(value)) {
          return callback(new Error('The password must have at least 8 characters and 32 at most. ' +
            'Must contain number and letter.'));
        } else {
          return callback();
        }
      }, 1000);
    };

    const validatePwd2 = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Please input password again'));
      }
      setTimeout(() => {
        if (value !== this.registerForm.password) {
          return callback(new Error('Two passwords are not matched.'));
        } else {
          return callback();
        }
      }, 1000);
    };

    return {
      //
      schoolList: [],

      registerForm: {
        username: '',
        password: '',
        passwordRepeat: '',
        roleType: 'Student',
        schId:''
      },
      rules: {
        username: [
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { validator: validatePwd, trigger: 'blur' }
        ],
        passwordRepeat: [
          { validator: validatePwd2, trigger: 'blur'}
        ]
      },
      //

      statusSwitch: true,
      addSize: '40%',
      addDrawer: false,
      newRoleName: '',
      newPassword: '',
      roleList: [
        {
          index: 1,
          roleId: 0,
          username: 'test',
          roleType: 'Teacher',
          registerTime: '12/11/2020 1:05:02',
          status: 'Enabled',
          statusSwitch: true
        },
        {
          index: 2,
          roleId: 4,
          username: 'test1',
          roleType: 'Student',
          registerTime: '12/11/2020 1:05:12',
          status: 'Disabled',
          statusSwitch: false
        }
      ],
      options: [{
        value: '0',
        label: 'Teacher'
      }, {
        value: '1',
        label: 'Admin'
      }],
      statusList: [
        {
          value: '0',
          label: 'Enable'
        }, {
          value: '1',
          label: 'Disable'
        }],
      roleType: '',
      roleStatus: ''
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    dateTimeFormatter (row, col) {
      return getDatetimeStr(row.registerTime)
    },
    // commitAdd () {
    //   console.log('send created data')
    //   if (this.roleType === '' || this.roleStatus === '') {
    //     this.$alert('Role Type or Status can not be null!', 'Warning')
    //     return false
    //   }
    //   if (this.newRoleName === '') {
    //     this.$alert('Role name can not be null', 'Warning')
    //     return false
    //   }
    //   if (this.newPassword === '') {
    //     this.$alert('Password can not be null', 'Warning')
    //     return false
    //   }
    //   postAddRole(
    //     this.newRoleName,
    //     this.newPassword,
    //     this.roleStatus,
    //     this.roleType
    //   ).then(resp => {
    //     console.log('get response : ' + resp)
    //     if (resp.data.code === 200) {
    //       this.newRoleName = ''
    //       this.newPassword = ''
    //       this.roleStatus = ''
    //       this.roleType = ''
    //       this.init()
    //       this.addDrawer = false
    //       this.$alert('Add successfully!', 'Tip')
    //     } else if (resp.data.code === 400) {
    //       console.log(resp.data.message)
    //       this.$alert(resp.data.message, 'Tip', {
    //         confirmButtonText: 'OK'
    //       })
    //     }
    //   }).catch(failResp => {
    //     this.$alert('Error ' + failResp.message, 'Tips', {
    //       confirmButtonText: 'OK'
    //     })
    //   })
    // },
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
            statusSwitch: resp.data.data[i].status === 'Enabled'
          }
          // alert(newRow.status)
          console.log(newRow)
          this.roleList.push(newRow)
        }
        console.log(this.roleList)
      }).catch(failResp => {
        console.log('fail in getRoleList. message=' + failResp.message)
      })

      getSchoolList().then(resp=>{
          if(resp.data.code!=200){
            this.$message.error(resp.data.code + '\n' + resp.data.message)
            return false
          }
          this.schoolList.splice(0,this.schoolList.length)
          let schools = resp.data.data
          console.log(schools)
          for (let i = 0; i <schools.length ; i++) {
            let school = schools[i];
            this.schoolList.push({
              value:school.schId,
              label:school.schName,
              location:school.location,
            })
          }


        }

      ).catch(failResp=>{
        console.log('fail in getSchoolList. message=' + failResp.message)
      })
    },
    setStatus (index) {
      // alert(this.roleList[index - 1].roleId)
      // alert(index)
      this.$confirm('Are you sure to change status?')
        .then(_ => {
          console.log('Changing status')
          postChangeStatus(this.roleList[index - 1].roleId).then(resp => {
            console.log('get response : ' + resp)
            if (resp.data.code === 200) {
              this.roleList[index - 1].status = this.roleList[index - 1].statusSwitch ? 'Enabled' : 'Disabled'
              this.$alert('Change successfully!', 'Tip')
            } else if (resp.data.code === 400) {
              console.log(resp.data.message)
              this.$alert(resp.data.message, 'Tip', {
                confirmButtonText: 'OK'
              })
              this.roleList[index - 1].statusSwitch = !this.roleList[index - 1].statusSwitch
            }
          }).catch(failResp => {
            this.$alert('Error ' + failResp.message, 'Tips', {
              confirmButtonText: 'OK'
            })
            this.roleList[index - 1].statusSwitch = !this.roleList[index - 1].statusSwitch
          })
        }).catch(_ => {
          this.roleList[index - 1].statusSwitch = !this.roleList[index - 1].statusSwitch
        })
      // this.statusSwitch = !this.statusSwitch
    },
    register () {
      const _this = this
      this.$refs['reg-btm'].loading = true
      this.$refs['registerForm'].validate((valid) => {
        if (!valid) {
          this.$alert('You have not filled in all required fields correctly.', 'Tips', {
            confirmButtonText: 'OK'
          })
          _this.$refs['reg-btm'].loading = false
          return false
        }

        console.log('send account data')
        console.log(this.registerForm)
        postAddRole(
          this.newRoleName,
          this.newPassword,
          this.roleStatus,
          this.roleType
        ).then(resp => {
          console.log('get response : ' + resp)
          if (resp.data.code === 200) {
            this.newRoleName = ''
            this.newPassword = ''
            this.roleStatus = ''
            this.roleType = ''
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
      });
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
#add_card, #modify_card{
  margin-left: 40px;
  margin-right: 40px;
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
>> .el-drawer :focus {
  outline: 0;
}
</style>
