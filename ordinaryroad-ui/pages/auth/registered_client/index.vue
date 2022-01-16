<template>
  <div>
    <base-material-card
      :icon="`mdi-web`"
      :title="$t('menuTitles.registeredClientManagement')"
    >
      <or-data-table-auth-registered-client />
    </base-material-card>
  </div>
</template>
<script>
export default {
  head () {
    return {
      title: this.$t('menuTitles.registeredClientManagement')
    }
  }
}
</script>
