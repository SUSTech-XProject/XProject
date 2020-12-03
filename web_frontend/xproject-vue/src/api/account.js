import Vue from 'vue'

export function getAccountInfo () {
  return Vue.prototype.$axios.get('api/account-info')
}
