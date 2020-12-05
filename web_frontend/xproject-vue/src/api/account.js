import Vue from 'vue'

export function getAccountInfo () {
  return Vue.prototype.$axios.get('api/account-info')
}

export function postSelfIntroduction (impTagList, skillTagList, bio) {
  return Vue.prototype.$axios.post('/self-intro', {
    impTagList: impTagList,
    skillTagList: skillTagList,
    bio:bio
  })
}
