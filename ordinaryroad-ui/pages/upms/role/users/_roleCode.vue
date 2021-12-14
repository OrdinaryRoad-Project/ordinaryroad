<template>
  <base-material-card
    icon="mdi-account"
    :title="$t('roleUsersManagement')"
  >
    <or-form-upms-role-users :preset="presetModel" />
  </base-material-card>
</template>
<script>
export default {
  validate ({ params }) {
    // 必填
    return !!params.roleCode
  },
  data: () => ({
    presetModel: null
  }),
  head () {
    return {
      title: this.$t('roleUsersManagement')
    }
  },
  created () {
    if (this.$route.params.item) {
      this.presetModel = this.$route.params.item
    } else {
      // 加载角色
      this.$apis.upms.role.findByUniqueColumn({
        roleCode: this.$route.params.roleCode
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
