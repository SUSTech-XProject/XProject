import Vue from 'vue'

export function getAccountInfo () {
  return Vue.prototype.$axios.get('account-info')
}

export function postSelfIntroduction (impTagList, skillTagList, bio) {
  return Vue.prototype.$axios.post('/self-intro', {
    flags: impTagList,
    skills: skillTagList,
    bio:bio
  })
}
