import Vue from 'vue'

export function isTeacher() {
  let roleType = Vue.prototype.$store.state.role.roleType;
  if (!roleType) {
    return false;
  }
  return roleType === 'Teacher'
}

export function isStudent() {
  let roleType = Vue.prototype.$store.state.role.roleType;
  if (!roleType) {
    return false;
  }
  return roleType === 'Student'
}

export function isAdmin() {
  let roleType = Vue.prototype.$store.state.role.roleType;
  if (!roleType) {
    return false;
  }
  return roleType === 'Admin'
}
