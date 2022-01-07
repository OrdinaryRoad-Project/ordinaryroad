<template>
  <base-material-card
    icon="mdi-account-multiple"
    :title="$t('userRolesManagement')"
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
  async asyncData ({ route, $apis }) {
    let presetModel
    if (route.params.item) {
      presetModel = route.params.item
    } else {
      // 加载用户
      presetModel = (await $apis.upms.user.findByUniqueColumn({
        orNumber: route.params.orNumber
      })).data
    }
    return { presetModel }
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
  },
  mounted () {
  },
  methods: {}
}
</script>

<style scoped>

</style>
