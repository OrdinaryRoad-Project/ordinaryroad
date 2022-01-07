<template>
  <div>
    <base-material-card
      :icon="`mdi-book-cog`"
      :title="$t('menuTitles.dictManagement')"
    >
      <or-data-table-upms-dict />
    </base-material-card>
  </div>
</template>

<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.dictManagement')
    }
  }
}
</script>
