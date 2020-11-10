import Vue from 'vue'

export function loginPost (username, password, rememberMe) {
  return Vue.prototype.$axios.post('/login', {
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
