import Vue from 'vue'

export function getProjOverview (projId) {
  return Vue.prototype.$axios.get('/all/project/overview', {
    params: {projId: projId}
  })
}

export function postProjectOverview (projectVO) {
  return Vue.prototype.$axios.post('/teacher/update-overview', projectVO)
}

export function postAddProj(projectVO){
  return Vue.prototype.$axios.post('/teacher/project/add', projectVO)

}

export function postDeleteProj(projId){
  return Vue.prototype.$axios.post('/teacher/project/delete', null,{
    params:{projId:projId}
  })

}
