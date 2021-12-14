<template>
  <div>
    <base-material-card
      :icon="`mdi-account-multiple`"
      :title="$t('menuTitles.roleManagement')"
    >
      <or-data-table-upms-role />
    </base-material-card>
  </div>
</template>

<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.roleManagement')
    }
  }
}
</script>
