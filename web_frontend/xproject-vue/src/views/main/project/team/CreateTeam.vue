<template>

  <el-dialog title="Creating Team"
             :before-close="closing"
             width="35%"
             :visible.sync="dialogFormVisible">
    <div v-if="this.$store.state.role.roleType==='Teacher'">
      <el-form
        :model="form">
<!--        :rules="rules" -->

        <el-form-item label="Team name" :label-width="formLabelWidth">
          <el-input v-model="form.name" :clearable = "clearable" style="width: 60%"></el-input>
          <el-checkbox v-model="form.withRank" style="margin-left: 20px">with index</el-checkbox>
        </el-form-item>
        <el-form-item label="Topic" :label-width="formLabelWidth" prop="topic">
          <selector :in-list="topicList" :index.sync="topicInd"></selector>
        </el-form-item>
        <el-form-item label="Members" :label-width="formLabelWidth" prop="maxTeamMem" style="width: 45%">
          <el-input v-model="form.member" :clearable = "clearable"></el-input>
        </el-form-item>
        <el-form-item label="Description" :label-width="formLabelWidth" style="width: 70%">
          <el-input v-model="form.desc" type="textarea" :clearable = "clearable"></el-input>
        </el-form-item>
        <el-form-item label="Counts" :label-width="formLabelWidth" style="width: 45%">
          <el-input v-model="form.cnt" :clearable = "clearable"></el-input>
        </el-form-item>
      </el-form>
    </div>

    <div v-else>
      <el-form
        :model="form">
        <!--        :rules="rules" -->

        <el-form-item label="Team name" :label-width="formLabelWidth">
          <el-input v-model="form.name" :clearable = "clearable" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="Topic" :label-width="formLabelWidth" prop="topic">
          <selector :in-list="topicList" :index.sync="topicInd"></selector>
        </el-form-item>
        <el-form-item label="Members" :label-width="formLabelWidth" prop="maxTeamMem" style="width: 70%">
          <el-input v-model="form.member" :clearable = "clearable"></el-input>
        </el-form-item>
        <el-form-item label="Description" :label-width="formLabelWidth" style="width: 70%">
          <el-input v-model="form.desc" type="textarea" :clearable = "clearable"></el-input>
        </el-form-item>
      </el-form>



    </div>





    <div slot="footer" class="dialog-footer">
      <el-button @click="closing">Cancel</el-button>
      <el-button type="primary" @click="onSubmit">Create</el-button>
    </div>

  </el-dialog>

</template>

<script>
import single from '@/components/selector/single'
import {getProjectTopics} from '@/api/team'
import {postTeamCreation} from '@/api/team'

export default {
  name: "CreateTeam",
  components:{
    selector:single
  },
  data() {
    var validateTeamSize = (rule, value, callback) => {
      value = this.form.member
      value = Number(value)
      if (typeof value === 'number' && !isNaN(value)) {
        if (value < 0 || value > this.teamSizeLimit) {
          callback(new Error('team members should not greater than '+this.teamSizeLimit))
        } else {
          callback()
        }
      } else {
        callback(new Error('please input valid number'))
      }
    };
    return {
      dialogFormVisible: this.visible,
      clearable:true,
      form: {
        name: '',
        topic: '',
        member:'',
        desc: '',
        cnt:'',
        projId:'',
        withRank:false,
      },
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        topic: [
          { required: true, message: 'Please select project topic', trigger: 'change' }
        ],
        maxTeamMem: [
          {required: true, message: 'please input maximum team size', trigger: 'blur'},
          {type: 'number', message: 'please input valid number',trigger: 'blur'},
          {validator:validateTeamSize, trigger: 'blur'}
        ],
        count: [
          {type: 'number', required: true, message: 'please input team counts', trigger: 'blur'},
          {}
        ],
      },

      topicList:[],
      topicInd:'',
      //?
      memLimit:[],
      teamSizeLimit:0,

      submitted:false,


      formLabelWidth: '120px'
    };
  },
  methods: {
    init(){
      //获取topicList
      let id = this.$store.state.proj.projId
      this.form.projId = id
      getProjectTopics(parseInt(id)).then(resp=>{
        if (resp.data.code !== 200) {
          this.$alert(resp.data.code + '\n' + resp.data.message, 'Tip', {
            confirmButtonText: 'OK'
          })
          return false
        }

        let topics = resp.data.data
        console.log(topics)
        this.topicList.splice(0,this.topicList.length)
        this.memLimit.splice(0,this.memLimit.length)
        for (let i = 0; i <topics.length ; i++) {
          this.topicList.push(topics[i].topicName)
          this.memLimit.push(topics[i].maxTeamSize)
          //队员数绑定？
        }
      }).catch(failResp=>{
        console.log('fail in getProjTitle. message=' + failResp.message)
      })

    },
    closing(){
      console.log('closed')
      this.$emit('closeForm',this.submitted)
      this.submitted = false
    },
    clearForm(){
      this.form.name = ''
      this.form.topic = ''
      this.form.member = ''
      this.form.desc = ''
      this.form.cnt = ''
      this.form.projId = ''
      this.form.withRank = false
      this.topicInd = ''
    },
    onSubmit() {
      console.log(this.form);
      this.$confirm('Submit form?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        console.log(this.form)
        postTeamCreation(
          this.form
        ).then(resp => {
          console.log(resp)
          if (resp.data.code === 200) {
            this.$message({
              type: 'success',
              message: 'Create teams successfully'
            });
            this.submitted = true
            this.clearForm()
            this.closing()
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
        });
      });

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
    },
    topicInd(val){
      this.topicInd = val;
      if(val!==''){
        this.form.topic = this.topicList[val]
        this.teamSizeLimit = this.memLimit[val]
      }
    }
  },
  props:{visible:{type:Boolean,default:false}}
}
</script>

<style scoped>

</style>
