<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.orNumber"
      :rules="[$rules.required,$rules.max11Chars]"
      :label="$t('orNumber')"
    />
    <v-text-field
      v-model="model.clientId"
      :rules="[$rules.required,$rules.max20Chars]"
      :label="$t('clientId')"
    />
    <v-text-field
      v-model="model.openid"
      :rules="[$rules.required,$rules.max50Chars]"
      :label="$t('openid')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormAuthOpenidSave',
  props: {
    preset: {
      type: Object,
      default: () => ({
        orNumber: null,
        clientId: null,
        openid: null
      })
    }
  },
  data: () => ({
    model: {}
  }),
  watch: {
    preset: {
      handler (val) {
        this.model = Object.assign({}, val)
      },
      deep: true,
      immediate: true
    },
    model: {
      handler (val) {
        this.$emit('update', val)
      },
      deep: true,
      immediate: true
    }
  },
  mounted () {
  },
  methods: {
    validate () {
      return this.$refs.form.validate()
    }
  }
}
</script>

<style scoped>

</style>
