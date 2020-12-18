import Vue from 'vue'
//特定时间
export function getEATaskList(projId){
  return Vue.prototype.$axios.get('/all/event',{
    params:{projId:projId}
  })
}
export function postEventInstApply(){
  return Vue.prototype.$axios.post('',{

  })
}
export function postEventDeletion(taskID){
  return Vue.prototype.$axios.post('/teacher/event/del',null, {
    params:{eaTaskId:parseInt(taskID)}
  })
}
export function postEventCreation(form){
  return Vue.prototype.$axios.post('/teacher/event/create',{
    description: form.desc,
    dueTime:form.due,
    projId:form.id,
    stdAdaptable:form.auto,
    title:form.title,
  })
}
export function postEventInstCreation(form){
  return Vue.prototype.$axios.post('/teacher/event/inst/create',{
    counts: parseInt(form.counts),
    date: form.date,
    duration: parseInt(form.duration),
    eaTaskId: parseInt(form.eaTaskId),
    startTime: form.startTime
  })
}

export function getEventTaskList(eaTaskId){
  return Vue.prototype.$axios.get('/all/event/inst',{
    params:{eaTaskId:eaTaskId}
  })
}

export function postEventInstDeletion(list){
  return Vue.prototype.$axios.post('/teacher/event/inst/delete',{
    eventInstIdList:list
  })
}

export function postEventInstClearTch(list){
  return Vue.prototype.$axios.post('/teacher/event/inst/clear',{
    eventInstIdList:list
  })
}
