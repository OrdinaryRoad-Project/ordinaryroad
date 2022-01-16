<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.clientId"
      :rules="[$rules.required,$rules.max20Chars]"
      :label="$t('clientId')"
    />
    <v-text-field
      v-model="model.clientSecret"
      :rules="[$rules.required,$rules.max20Chars]"
      :label="$t('clientSecret')"
    />
    <v-text-field
      v-model="model.clientName"
      :rules="[$rules.required,$rules.max100Chars]"
      :label="$t('clientName')"
    />
    <v-text-field
      v-model="model.redirectUris"
      :rules="[$rules.max1000Chars]"
      :label="$t('redirectUris')"
    />
    <v-text-field
      v-model="model.scopes"
      :rules="[$rules.max1000Chars]"
      :label="$t('scopes')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormAuthRegisteredClientSave',
  props: {
    preset: {
      type: Object,
      default: () => ({
        clientId: null,
        clientSecret: null,
        clientName: null,
        redirectUris: null,
        scopes: null
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
