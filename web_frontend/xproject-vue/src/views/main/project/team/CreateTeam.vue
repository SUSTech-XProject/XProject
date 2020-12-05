<template>

  <el-dialog title="Creating Team"
             :before-close="closing"
             :visible.sync="dialogFormVisible">

    <el-form :model="form">
      <el-form-item label="Team name" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Topic" :label-width="formLabelWidth">
        <selector :in-list="topicList" :index.sync="topicInd"></selector>
      </el-form-item>
      <el-form-item label="Members" :label-width="formLabelWidth">
        <selector :in-list="memberList" :index.sync="memberInd"></selector>
      </el-form-item>
      <el-form-item label="Description" :label-width="formLabelWidth">
        <el-input v-model="form.desc" type="textarea" autocomplete="off"></el-input>
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
        desc: ''
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
    },
    closing(done){
      console.log('closed')
      this.$emit('closeForm',this.dialogFormVisible)
    },
    onSubmit() {
      console.log('submit!');
      this.closing()
    },

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
