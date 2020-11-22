import Vue from 'vue'

export function getUserHomeInfo () {
  return Vue.prototype.$axios.get('/home-page-info')
}

export function getProjList () {
  return Vue.prototype.$axios.get('/project-list')
}
