import Vue from 'vue'

export function getUserHomeInfo () {
  return Vue.prototype.$axios.get('/all/home-page/info')
}

export function getProjList () {
  return Vue.prototype.$axios.get('/all/project/list')
}

export function getProjListBySch () {
  return Vue.prototype.$axios.get('/all/project/by-sch')
}

export function getAddProj (newProj) {
  return Vue.prototype.$axios.get('/teacher/project/add', {
    // todo
  })
}

export function getJoinProj (projId) {
  return Vue.prototype.$axios.get('/student/project/join', {
    params: {projId: projId}
  })
}
