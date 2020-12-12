<template>
  <el-card style="margin: 12px 10px">
    <div slot="header" class="">
      <span id="title-text">Project List</span>
    </div>

    <div v-if="this.$store.state.role.roleType === 'Student'">
      <el-row style="display: flex; margin: 0 0 20px 0; justify-content: flex-end">
        <el-button type="primary" plain icon="el-icon-search" @click="joinDialogVisible = true">Join</el-button>
        <el-button type="primary" plain style="margin-right: 20%"
                   :icon="icn" @click="selectStar">Only star</el-button>
      </el-row>

      <div v-for="(list, index) in listArr">
        <div v-if="list.star||!star" class="proj">
          <card v-bind:proj="list"
                v-bind:index="index"
                @getStarChange = "getStarChange"
                @click.native= "gotoProjOverview(list.id, list.name)"></card>
        </div>
      </div>

      <el-dialog title="Join a project"
                 width="80%"
                 @open="openJoinDialog"
                 :visible.sync="joinDialogVisible">
        <el-table
          ref="projTable"
          v-loading="projTableLoading"
          :data="dialogProjList"
          empty-text="No Other Project Found"
          :default-sort = "{prop: 'index', order: 'increasing'}"
          style="width: 100%">
          <el-table-column label="" type="index" width="50px" sortable/>
          <el-table-column label="Name" prop="projName" width="350px" sortable/>
          <el-table-column label="Course" prop="courseName" width="300px" sortable/>
          <el-table-column label="Description" prop="description"/>
          <el-table-column label="Operation" width="300px">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary" plain
                icon="el-icon-plus"
                @click="openConfirmDialog(scope.row.projId, scope.row.projName)">Join</el-button>
            </template>
          </el-table-column>
        </el-table>

        <span slot="footer" class="dialog-footer">
        <el-button @click="joinDialogVisible = false">Cancel</el-button>
      </span>

        <el-dialog title="Tip"
                   append-to-body
                   :visible.sync="confirmDialogVisible">
          <div>Join project <span style="font-weight: bold">{{this.confirmProjName}}</span> ? </div>
          <span slot="footer" class="dialog-footer">
          <el-button @click="confirmDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="confirmDialogVisible = false; joinProj()">Join</el-button>
        </span>
        </el-dialog>

      </el-dialog>
    </div>

    <div v-if="this.$store.state.role.roleType === 'Teacher'">
      Teacher temp version
      <div v-for="(list, index) in listArr">
        <div v-if="list.star||!star" class="proj">
          <card v-bind:proj="list"
                v-bind:index="index"
                @getStarChange = "getStarChange"
                @click.native= "gotoProjOverview(list.id, list.name)"></card>
        </div>
      </div>
    </div>

  </el-card>
</template>

<script>
import Card   from '@/components/card/projectList/index'
import Selector from '@/components/selector/single'
import {getJoinProj, getProjList, getProjListBySch} from "@/api/home_page";

export default{
  name:'ProjectList',
  components:{
    card:Card,
    sele:Selector
  },
  data () {
    return{
      listArr:[
        // {id:1,name:"PROJECT",course:"COURSE",star:true},
        // {id:2,name:"PROJECT2",course:"COURSE2",star:false},
        // {id:3,name:"PROJECT3",course:"COURSE3",star:true}
      ],
      star:false,
      icn:"el-icon-star-off",
      projTableLoading: true,
      joinDialogVisible: false,
      confirmDialogVisible: false,
      dialogProjList: [],
      confirmProjName: '',
      confirmProjId: null,
    }
  },
  mounted () {
    this.initProjList()
  },
  methods:{
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
        this.dialogProjList = resp.data.data;
        this.projTableLoading = false
      }).catch( failResp => {
        console.log('fail in getProjListBySch, %o', failResp)
        this.joinDialogVisible = false
        this.$alert('Fail to load', 'Tip', {
          confirmButtonText: 'OK'
        })
      })
    },
    openConfirmDialog (projId, projName) {
      this.confirmProjId = projId
      this.confirmProjName = projName
      this.confirmDialogVisible = true
    },
    joinProj () {
      getJoinProj(
        this.confirmProjId
      ).then(resp => {
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }
        this.dialogProjList = resp.data.data;
      }).catch( failResp => {
        console.log('fail in getProjListBySch, %o', failResp)
        this.$alert('Fail to join', 'Tip', {
          confirmButtonText: 'OK'
        })
      })
    },
    getStarChange (val) {
      console.log(val)
      //更改相应project star值
      for (let i = 0; i <this.listArr.length ; i++) {
        var va = this.listArr[i];
        if(va.id===val){
          console.log(this.listArr[i].star)
          var temp = va.star;
          temp = !temp;
          va.star = temp;
          console.log(this.listArr[i].star)
        }
      }
      console.log('testing')
    },

    gotoProjOverview (projId, projName) {
      let projRecord = {projId: projId, projName: projName}
      console.log('goto proj overview.')
      console.log(projRecord)
      this.$store.commit('setProj', projRecord)
      this.$router.push({name:'ProjOverview', params: {proj_id: projId}})
    },

    selectStar (){
      let temp = this.star
      this.star = !temp;
      this.icn = !temp?'el-icon-star-on':'el-icon-star-off'
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
            star: false
          })
        }
        console.log(this.listArr)
      }).catch(failResp => {
        console.log('fail in getProjList. message=' + failResp.message)
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

#temp{
  height: 100%;
}
.proj{
  height: 120px;
  text-align: center;
  padding-left: 10%;
}

.el-col, .el-row {
  margin: 0;
  padding: 0;
  font-size: 0;
}



</style>
