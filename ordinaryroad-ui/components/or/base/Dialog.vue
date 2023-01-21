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
    v-model="value"
    :persistent="loadingModel||(persistent==null?loading:persistent)"
    width="80%"
    scrollable
    @input="input"
  >
    <v-card>
      <v-toolbar color="transparent" flat>
        <v-toolbar-title>{{ title }}</v-toolbar-title>
        <v-spacer />
        <v-btn
          :disabled="loadingModel"
          icon
          @click="close"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-toolbar>

      <v-card-text v-if="!destroyBody">
        <slot name="default" />
      </v-card-text>

      <v-card-actions class="pa-2">
        <v-spacer />
        <slot
          v-if="$slots.actions"
          name="actions"
        />
        <div v-else>
          <v-btn
            :disabled="loadingModel"
            text
            @click="close"
          >
            {{ $t('cancel') }}
          </v-btn>
          <v-btn
            :loading="loadingModel"
            text
            color="primary"
            @click="confirm"
          >
            {{ $t('confirm') }}
          </v-btn>
        </div>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'OrBaseDialog',
  props: {
    persistent: {
      type: Boolean,
      default: null
    },
    loading: {
      type: Boolean,
      required: false
    },
    title: {
      type: String,
      required: true
    }
  },
  data: () => ({
    value: null,
    loadingModel: false,
    /**
     * 关闭对话框销毁body
     */
    destroyBody: false
  }),
  watch: {
    value (val) {
      if (!val) {
        this.loadingModel = false
        setTimeout(() => {
          this.destroyBody = true
        }, 300)
      } else {
        this.destroyBody = false
      }
    }
  },
  methods: {
    show () {
      this.value = true
    },
    cancelLoading () {
      this.loadingModel = false
    },
    input (input) {
      if (!input) {
        // 点击外部关闭也需要回调
        this.close()
      }
    },
    close () {
      this.value = false
      this.loadingModel = false
      this.$emit('onClose')
    },
    confirm () {
      if (this.loading) {
        this.loadingModel = true
      }
      this.$emit('onConfirm')
    }
  }
}
</script>
