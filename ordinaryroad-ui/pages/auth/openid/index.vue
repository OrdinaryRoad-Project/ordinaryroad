<template>
  <div>
    <base-material-card
      :icon="`mdi-identifier`"
      :title="$t('menuTitles.openidManagement')"
    >
      <or-data-table-auth-openid />
    </base-material-card>
  </div>
</template>
<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.openidManagement')
    }
  }
}
</script>
