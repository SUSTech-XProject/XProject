<template>

  <el-dialog title="Creating Team"
             :before-close="closing"
             :visible.sync="dialogFormVisible">

    <el-form :model="form">
      <el-form-item label="Team name" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" style="width: 70%"></el-input>
        <el-checkbox v-model="form.withRank" style="margin-left: 20px">with index</el-checkbox>
      </el-form-item>
      <el-form-item label="Topic" :label-width="formLabelWidth">
        <selector :in-list="topicList" :index.sync="topicInd"></selector>
      </el-form-item>
      <el-form-item label="Members" :label-width="formLabelWidth">
        <selector :in-list="memberList" :index.sync="memberInd"></selector>
      </el-form-item>
      <el-form-item label="Description" :label-width="formLabelWidth" style="width: 80%">
        <el-input v-model="form.desc" type="textarea" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Counts" :label-width="formLabelWidth" style="width: 40%">
        <el-input v-model="form.number" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closing">Cancel</el-button>
      <el-button type="primary" @click="onSubmit">Create</el-button>
    </div>

  </el-dialog>

</template>

<script>
import single from '@/components/selector/single'
import {getProjectTopics} from '@/api/team'

export default {
  name: "CreateTeam",
  components:{
    selector:single
  },
  data() {
    return {
      dialogFormVisible: this.visible,
      form: {
        name: '',
        topic: '',
        member:'',
        desc: '',
        number:'',
        withRank:'',
      },

      topicList:[],
      topicInd:'',

      memberList:[],
      memberInd:'',
      formLabelWidth: '120px'
    };
  },
  methods: {
    init(){
      //获取topicList
      let id = this.$store.state.proj.projId
      getProjectTopics(parseInt(id)).then(resp=>{
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }

        let topics = resp.data.data
        this.topicList.splice(0,this.topicList.length)
        for (let i = 0; i <topics.length ; i++) {
            this.topicList.push(topics[i].topicName)
          //队员数绑定？
        }



      }).catch(failResp=>{
        console.log('fail in getProjTitle. message=' + failResp.message)
      })

    },
    closing(){
      console.log('closed')
      this.$emit('closeForm',this.dialogFormVisible)
    },
    onSubmit() {
      console.log('submit!');
      this.closing()
    },

  },
  mounted () {
    this.init()
  },
  created () {
    this.dialogFormVisible = this.visible
  },
  watch:{
    visible(val){
      this.dialogFormVisible = val
    }
  },
  props:{visible:{type:Boolean,default:false}}
}
</script>

<style scoped>

</style>
