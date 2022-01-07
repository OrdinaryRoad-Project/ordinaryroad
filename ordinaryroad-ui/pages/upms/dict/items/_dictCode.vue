<template>
  <base-material-card
    icon="mdi-book-open"
    :title="$t('dictItemsManagement')"
  >
    <or-form-upms-dict-items :preset="presetModel" />
  </base-material-card>
</template>
<script>
export default {
  validate ({ params }) {
    // 必填
    return !!params.dictCode
  },
  data: () => ({
    presetModel: null
  }),
  head () {
    return {
      title: this.$t('dictItemsManagement')
    }
  },
  created () {
    if (this.$route.params.item) {
      this.presetModel = this.$route.params.item
    } else {
      // 加载字典
      this.$apis.upms.dict.findByUniqueColumn({
        dictCode: this.$route.params.dictCode
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
