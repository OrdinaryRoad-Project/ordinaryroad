<template>
  <base-material-card
    icon="mdi-account-multiple"
    title="用户角色管理"
  >
    <or-upms-user-roles
      :preset="$route.params.item"
    />
  </base-material-card>
</template>

<script>
export default {
  data: () => ({}),
  head () {
    return {
      title: this.$t('userRolesManagement')
    }
  },
  created () {
    if (!this.$route.params.item) {
      this.$router.replace({ name: '404' })
    }
  },
  mounted () {
  },
  methods: {}
}
</script>

<style scoped>

</style>
