<template>
  <el-select v-model="value"
             multiple
             style="margin-left: 20px;width: 240px;"
             collapse-tags
             :clearable = "true"
             placeholder="Selecting...">
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
  data(){
    return{
      options:[],
      value: [],
    }
  },
  mounted () {
    this.options = this.inList
  },
  watch:{
    value:function (val){
      console.log(val)
      this.$emit('update:index',val)
    }
  },
  props:{
    inList:{type:Array,default:()=>['default0']},
    index:{type:Array,default:()=>[]}
  }
}
</script>

<style scoped>
.el-form-item__content .el-select__tags{
  flex-wrap: nowrap
}
</style>
