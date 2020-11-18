<template>
  <div>
    Do the main design here !
    <br>
    Use sub dir to create page for project/announcement ...
    <br>
    Hi, {{this.$store.state.role.username}}
    <br>
    Your roleType is {{this.$store.state.role.roleType}}
  </div>
</template>

<script>
export default {
  name: 'HomePage',
}
</script>

<style scoped>

</style>
