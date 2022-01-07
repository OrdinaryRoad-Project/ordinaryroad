<template>
  <base-material-card
    icon="mdi-account"
    :title="$t('rolePermissionsManagement')"
  >
    <or-form-upms-role-permissions :preset="presetModel" />
  </base-material-card>
</template>
<script>
export default {
  validate ({ params }) {
    // 必填
    return !!params.roleCode
  },
  async asyncData ({ route, $apis }) {
    let presetModel
    if (route.params.item) {
      presetModel = route.params.item
    } else {
      // 加载角色
      presetModel = (await $apis.upms.role.findByUniqueColumn({
        roleCode: route.params.roleCode
      })).data
    }
    return { presetModel }
  },
  data: () => ({
    presetModel: null
  }),
  head () {
    return {
      title: this.$t('rolePermissionsManagement')
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {}
}
</script>

<style scoped>

</style>
