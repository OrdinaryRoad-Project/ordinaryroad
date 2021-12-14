<template>
  <v-menu
    :min-width="minWidth"
    :eager="eager&&eagerModel"
    :offset-y="offsetY"
    :open-on-hover="openOnHover"
    :close-on-content-click="closeOnContentClick"
  >
    <template #activator="{ on, attrs }">
      <slot name="activator" :on="on" :attrs="attrs" />
    </template>
    <slot />
  </v-menu>
</template>

<script>

export default {
  name: 'OrBaseMenu',
  props: {
    eager: {
      type: Boolean,
      default: false
    },
    closeOnContentClick: {
      type: Boolean,
      default: true
    },
    openOnHover: {
      type: Boolean,
      default: false
    },
    minWidth: {
      type: String,
      default: null
    },
    offsetY: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    eagerModel: false
  }),
  created () {
  },
  mounted () {
    this.$nextTick(() => {
      setTimeout(() => {
        this.$nextTick(() => {
          this.eagerModel = true
        })
      }, 500)
    })
  }
}
</script>

<style scoped>

</style>
