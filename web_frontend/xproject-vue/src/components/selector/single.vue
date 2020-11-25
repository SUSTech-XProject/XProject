<template>
  <el-select v-model="value"
             placeholder="请选择"
             size="medium"
             :clearable = "true"
             style="width: 100px">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
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
    console.log("--mounted--")
    console.log(this.inList)
    this.options = this.inList
  },
  watch:{
    value:function(val){
      console.log(val)
      console.log(this.value)
    }
  },
  props:{
    inList:{type:Array,default:()=>[{value: '1', label: 'default1'}]}
  }
}
</script>

<style scoped>
.el-select{
  width: fit-content;
}
</style>
