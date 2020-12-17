import Vue from 'vue'
//特定时间
export function getEATaskList(projId){
  return Vue.prototype.$axios.get('all/event',{
    params:{projId:projId}
  })
}
export function postEventInstApply(){
  return Vue.prototype.$axios.post('',{

  })
}
export function postEventDeletion(){
  return Vue.prototype.$axios.post('',{

  })
}
export function postEventCreation(){
  return Vue.prototype.$axios.post('',{

  })
}
