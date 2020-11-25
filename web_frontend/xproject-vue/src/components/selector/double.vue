<template>
  <el-select v-model="value"
             multiple
             style="margin-left: 20px;width: 250px"
             :multiple-limit="3"
             placeholder="请选择">
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
  data(){
    return{
      options:[],
      value: [],
    }
  },
  mounted () {
    this.options = this.inList

  },
  props:{
    inList:{type:Array,default:()=>[{value: '1', label: 'default1'}]}
  }
}
</script>

<style scoped>

</style>
