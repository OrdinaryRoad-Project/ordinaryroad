<template>
  <base-material-card
    icon="mdi-account-multiple"
    title="用户角色管理"
  >
    <or-form-upms-user-roles
      :preset="presetModel"
    />
  </base-material-card>
</template>

<script>
export default {
  validate ({ params }) {
    // 必填
    return !!params.orNumber
  },
  data: () => ({
    presetModel: null
  }),
  head () {
    return {
      title: this.$t('userRolesManagement')
    }
  },
  created () {
    if (this.$route.params.item) {
      this.presetModel = this.$route.params.item
    } else {
      // 加载用户
      this.$apis.upms.user.findByUniqueColumn({
        orNumber: this.$route.params.orNumber
      })
        .then((value) => {
          this.presetModel = value.data
        })
    }
  },
  mounted () {
  },
  methods: {}
}
</script>

<style scoped>

</style>
