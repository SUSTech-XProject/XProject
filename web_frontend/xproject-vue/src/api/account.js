import Vue from 'vue'

export function getAccountInfo () {
  return Vue.prototype.$axios.get('/all/account/info')
}

export function postSelfIntroduction (acInfoStdUpdateVO) {
  return Vue.prototype.$axios.post('/student/self-intro', acInfoStdUpdateVO)
}
