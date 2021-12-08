<template>
  <div>
    <base-material-card
      :icon="`mdi-account`"
      :title="$t('menuTitles.requestPathManagement')"
    >
      <or-data-table-upms-request-path />
    </base-material-card>
  </div>
</template>

<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.requestPathManagement')
    }
  }
}
</script>
