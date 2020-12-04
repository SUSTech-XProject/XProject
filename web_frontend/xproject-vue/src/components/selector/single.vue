<template>
  <el-select v-model="value"
             placeholder="Selecting..."
             size="medium"
             :clearable = "true"
             style="width: 120px">
    <el-option
      v-for="i in options.length"
      :key="i-1"
      :label="options[i-1]"
      :value="i-1">
    </el-option>
  </el-select>
</template>

<script>
export default {
name: "index",
  data(){
    return{
      options:[],
      value: '',
    }
  },
  mounted () {
    this.options = this.inList
  },
  watch:{
    value:function(val){
      console.log(val)
      this.$emit('update:index',val.toString())
    }
  },
  props:{
    inList:{type:Array,default:()=>['default0']},
    index:{type:String}
  }
}
</script>

<style scoped>
.el-form-item__content .el-select__tags{
  flex-wrap: nowrap
}
</style>
