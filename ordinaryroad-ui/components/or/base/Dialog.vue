<template>
  <v-dialog
    v-model="value"
    :persistent="persistent==null?loading:persistent"
    width="80%"
    @input="input"
  >
    <v-card>
      <v-card-title>
        <v-toolbar-title>{{ title }}</v-toolbar-title>
        <v-spacer />
        <v-btn
          :disabled="loadingModel"
          icon
          @click="close"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

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
  name: 'Dialog',
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
