<template>
  <v-app>
    <v-main>
      <v-container fluid class="pa-0 fill-height">
        <nuxt />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { updateTheme } from 'static/js/utils/vuetify'

export default {
  mounted () {
    // dom初始化完成再初始化主题
    this.$nextTick(() => {
      updateTheme(this.$store.getters['app/getSelectedThemeOption'], this.$vuetify)
    })
  }
}
</script>
