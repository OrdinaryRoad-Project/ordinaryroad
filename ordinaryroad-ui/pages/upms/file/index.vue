<template>
  <div>
    <base-material-card
      :icon="`mdi-file-multiple`"
      :title="$t('menuTitles.fileManagement')"
    >
      <or-data-table-upms-file />
    </base-material-card>
  </div>
</template>

<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.fileManagement')
    }
  }
}
</script>
