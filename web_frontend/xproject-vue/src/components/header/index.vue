<template>
  <div id="header">
<!--        <span>{{part}}</span>-->
    <div v-if="this.$store.state.proj.projId !== null" id="bread-crumb-div">
      <Breadcrumb></Breadcrumb>
    </div>

<!--        <el-button style="margin: auto 20px auto 50px" class="header-btm"-->
<!--                   icon="el-icon-message-solid">-->
<!--        </el-button>-->

    <el-dropdown @command="handleCommand" trigger="click">
      <el-button class="header-btm" type="text" icon="el-icon-user-solid">
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>Home Page</el-dropdown-item>
        <el-dropdown-item command="logout">Log out</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import Breadcrumb from '@/components/breadcrumb/index'
import {getLogout} from "@/api/role";

export default {
  name: 'index',

  data () {
    return {
      isFolded: false,
      part: this.$route.path.split('/')[2]
    }
  },
  methods: {
    handleCommand (command) {
      if (command === 'logout') {
        this.logout()
      }
    },
    logout () {
      console.log('logout')
      getLogout().then(resp => {
        if (resp.data.code === 200) {
          this.$store.commit('logout')
          this.$router.push({name:'Login'})
        } else if (resp.data.code === 400) {
          console.log(resp.data.message)
          this.$alert(resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
        }
        return true
      }).catch(failResp => {
        this.$alert('Error' + failResp.message, 'Tips', {
          confirmButtonText: 'OK'
        })
        return false
      })
    }
  },
  components: {
    Breadcrumb
  }
}
</script>

<style scoped>
#header {
  background-color: #ffffff;
  color: #333;
  line-height: 60px;
  width:100%;
  height: 100%;
  font-size: 20px;
  text-align: left;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
#bread-crumb-div {
  margin-left: 20px;
  margin-right: auto;
}
.i {
  text-align: center;
}
#Breadcrumb {
  margin-top: 20px;
  position: center;
}
#partname{
  width: 20px;
  position: relative;
  height: 10%;
  bottom: 10%;
}
  .header-btm {
    margin: auto 50px auto 20px;
    font-size: 20px;
  }

</style>
