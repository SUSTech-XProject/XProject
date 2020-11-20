import Vue from 'vue'

export function loginPost (username, password, rememberMe) {
  return Vue.prototype.$axios.post('/account', {
    username: username,
    password: password,
    rememberMe: rememberMe
  })
}

export function registerPost (type, username, password) {
  return Vue.prototype.$axios.post('/register', {
    type: type,
    username: username,
    password: password
  })
}

export function authGet () {
  return Vue.prototype.$axios.get('/authentication')
}
