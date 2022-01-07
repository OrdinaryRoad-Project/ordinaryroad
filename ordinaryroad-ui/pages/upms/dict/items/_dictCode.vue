<template>
  <base-material-card
    icon="mdi-book-open"
    :title="$t('dictItemsManagement')"
  >
    <or-data-table-upms-dict-item :default-dict-uuid="presetModel.uuid" />
  </base-material-card>
</template>
<script>
export default {
  validate ({ params }) {
    // 必填
    return !!params.dictCode
  },
  async asyncData ({ route, $apis }) {
    let presetModel
    if (route.params.item) {
      presetModel = route.params.item
    } else {
      // 服务端渲染，先从服务器获取dict的uuid
      presetModel = (await $apis.upms.dict.findByUniqueColumn({
        dictCode: route.params.dictCode
      })).data
    }
    return { presetModel }
  },
  data: () => ({
    presetModel: {
      uuid: null
    }
  }),
  head () {
    return {
      title: this.$t('dictItemsManagement')
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
