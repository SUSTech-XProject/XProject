import Vue from 'vue'

export function getAccountInfo () {
  return Vue.prototype.$axios.get('/all/account/info')
}

export function postStdPersonalInfo (acInfoStdUpdateVO) {
  return Vue.prototype.$axios.post('/student/self-intro', acInfoStdUpdateVO)
}

export function postTchPersonalInfo (acInfoStdUpdateVO) {
  return Vue.prototype.$axios.post('/teacher/self-intro', acInfoStdUpdateVO)
}

export function postChangePassword(changePwdVO){
  return Vue.prototype.$axios.post('/all/change/password', changePwdVO)
}
