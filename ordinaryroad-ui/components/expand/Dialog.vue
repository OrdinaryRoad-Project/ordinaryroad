<template>
  <v-dialog
    ref="dialog"
    v-model="dialog"
    :width="params.width||400"
    content-class="rounded-0"
    :dark="params.dark"
    :light="params.light"
    :persistent="params.persistent==null?params.loading:params.persistent"
    :internal-activator="params.internalActivator"
    :overlay-color="params.overlayColor"
    :overlay-opacity="params.overlayOpacity"
    :close-delay="params.closeDelay"
    :transition="params.transition"
    @input="destroyDom"
  >
    <v-card :tile="params.tile">
      <v-card-title>
        <v-icon class="me-3">
          {{ params.icon || 'mdi-information' }}
        </v-icon>
        {{ params.title || i18n.messages[i18n.locale].attention }}
      </v-card-title>
      <v-card-text class="text-subtitle-1" v-html="params.content" />
      <v-card-actions v-show="!params.hideActions" class="pa-2">
        <v-spacer />
        <v-btn
          v-show="!params.hideCancel"
          :disabled="loading"
          :color="params.cancelColor||'grey'"
          text
          @click="cancel"
        >
          {{ params.cancelText || i18n.messages[i18n.locale].cancel }}
        </v-btn>
        <v-btn
          v-show="!params.hideConfirm"
          :color="params.confirmColor||'primary'"
          :loading="loading"
          text
          @click="confirm"
        >
          {{ params.confirmText || i18n.messages[i18n.locale].confirm }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data () {
    return {
      dialog: false,
      params: {},
      isConfirm: null,
      loading: false
    }
  },
  methods: {
    execute (arg, framework) {
      this.dialog = true
      this.params.dark = framework.theme.isDark
      if (typeof arg === 'string') {
        this.params.title = arg
      } else {
        Object.assign(this.params, arg)
      }
    },
    confirm () {
      if (this.params.loading) {
        this.loading = true
      } else {
        this.dialog = false
        setTimeout(() => {
          this.isConfirm = true
          this.$destroy()
        }, 300)
      }
    },
    cancel () {
      this.dialog = false
      setTimeout(() => {
        this.isConfirm = false
        this.$destroy()
      }, 300)
    },
    destroyDom (v) {
      if (!v) {
        this.dialog = false
        setTimeout(() => {
          this.$destroy()
        }, 300)
      }
    }
  }
}
</script>
