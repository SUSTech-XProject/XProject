import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    role: {
      // this is for init
      username: window.localStorage.getItem('role') == null ?
        '' : JSON.parse(window.localStorage.getItem('role' || '[]')).username,
      // '' for visitor, 'S' for student, 'T' for teacher, 'A' for admin
      // this will be updated before visiting each jump
      roleType: '',
    },
    proj: {
      projId: window.localStorage.getItem('proj') == null ?
        null : JSON.parse(window.localStorage.getItem('proj' || '[]')).projId,
      projName: window.localStorage.getItem('proj') == null ?
        null : JSON.parse(window.localStorage.getItem('proj' || '[]')).projName,
    }
  },
  mutations: {
    login (state, loginForm) {
      state.role.username = loginForm.username
      window.localStorage.setItem('role', JSON.stringify(state.role))

      window.localStorage.removeItem('proj')
      state.proj.projId = null
      state.proj.projName = null
    },
    logout (state) {
      state.role.username = null
      state.role.roleType = null
      window.localStorage.removeItem('role')

      state.proj.projId = null
      state.proj.projName = null
      window.localStorage.removeItem('proj')
    },
    updateRoleType (state, roleType) {
      state.role.roleType = roleType
      window.localStorage.setItem('role', JSON.stringify(state.role))
    },

    setProj(state, proj) {
      state.proj = proj
      window.localStorage.setItem('proj', JSON.stringify(proj))
    },
    removeProj(state) {
      state.proj.projId = null
      state.proj.projName = null
      window.localStorage.removeItem('proj')
    }
  }
})
