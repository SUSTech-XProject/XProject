import Vue from 'vue'
export function getEventInst(projId){
  return Vue.prototype.$axios.get('',{
    params:{projId:projId}
  })
}
export function postEventInstApply(){
  return Vue.prototype.$axios.post('',{

  })
}
