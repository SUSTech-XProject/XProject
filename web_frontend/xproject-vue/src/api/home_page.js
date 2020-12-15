import Vue from 'vue'

export function getUserHomeInfo () {
  return Vue.prototype.$axios.get('/home-page-info')
}

export function getProjList () {
  return Vue.prototype.$axios.get('/project-list')
}

export function getProjListBySch () {
  return Vue.prototype.$axios.get('/project-list/school')
}

export function getAddProj (newProj) {
  return Vue.prototype.$axios.get('/teacher/project/add', {
    // todo
  })
}

export function getJoinProj (projId) {
  return Vue.prototype.$axios.get('/project/join', {
    params: {projId: projId}
  })
}
