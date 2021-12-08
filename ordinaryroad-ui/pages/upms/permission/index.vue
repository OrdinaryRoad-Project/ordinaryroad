<template>
  <div>
    <base-material-card
      :icon="`mdi-account`"
      :title="$t('menuTitles.permissionManagement')"
    >
      <or-data-table-upms-permission />
    </base-material-card>
  </div>
</template>

<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.permissionManagement')
    }
  }
}
</script>
