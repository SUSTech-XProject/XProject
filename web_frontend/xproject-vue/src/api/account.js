import Vue from 'vue'

export function getAccountInfo () {
  return Vue.prototype.$axios.get('account-info')
}

export function postSelfIntroduction (acInfoStdUpdateVO) {
  return Vue.prototype.$axios.post('/self-intro', {
    acInfoStdUpdateVO: acInfoStdUpdateVO
  })
}
