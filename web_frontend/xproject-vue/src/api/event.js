import Vue from 'vue'
export function getEventInst(projId){
  return Vue.prototype.$axios.get('',{
    params:{projId:projId}
  })
}
