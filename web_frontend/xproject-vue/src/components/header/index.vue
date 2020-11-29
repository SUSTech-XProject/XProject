<template>
  <div id = "header">

<!--      <i v-bind:class="[{'el-icon-d-arrow-right':isFolded},'el-icon-d-arrow-left']"></i>-->
<!--      <div v-if="this.$route.path.indexOf('projectlist')!== -1">-->
<!--        <Breadcrumb></Breadcrumb>-->
<!--      </div>-->
<!--      <div v-else id="partname">{{part}}</div>-->
    <el-button style="margin: auto 20px auto 50px" class="header-btm"
               icon="el-icon-message-solid">
    </el-button>



    <el-dropdown @command="handleCommand">
      <el-button style="margin: auto 50px auto 20px" class="header-btm"
                 icon="el-icon-user-solid">
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
      part: this.$route.path.split('/')[1]
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
  background-color: #edf4ff;
  color: #333;
  line-height: 60px;
  width:100%;
  height: 100%;
  font-size: 20px;
  text-align: left;
  display: flex;
  align-content: center;
  justify-content: flex-end;
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

  }

</style>
