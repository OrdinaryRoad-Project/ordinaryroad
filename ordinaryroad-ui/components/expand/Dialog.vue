<!--
  - MIT License
  -
  - Copyright (c) 2021 苗锦洲
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

<template>
  <v-dialog
    ref="dialog"
    v-model="dialog"
    :width="params.width||400"
    content-class="rounded-0"
    :dark="params.dark"
    :light="params.light"
    :persistent="loading||(params.persistent==null?params.loading:params.persistent)"
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
        {{ params.title || i18n.$t('attention') }}
      </v-card-title>
      <v-card-text v-if="params.content" class="text-subtitle-1" style="white-space: pre-line">
        {{ params.content }}
      </v-card-text>
      <v-card-actions v-show="!params.hideActions" class="pa-2">
        <v-spacer />
        <v-btn
          v-show="!params.hideCancel"
          :disabled="loading"
          :color="params.cancelColor||'grey'"
          text
          @click="cancel"
        >
          {{ params.cancelText || i18n.$t('cancel') }}
        </v-btn>
        <v-btn
          v-show="!params.hideConfirm"
          :color="params.confirmColor||'primary'"
          :loading="loading"
          text
          @click="confirm"
        >
          {{ params.confirmText || i18n.$t('confirm') }}
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
        this.isConfirm = true
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
