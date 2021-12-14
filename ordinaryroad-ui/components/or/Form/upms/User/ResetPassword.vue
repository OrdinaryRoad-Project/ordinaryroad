<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.orNumber"
      readonly
      :label="$t('orNumber')"
    />
    <v-text-field
      v-model="model.password"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      :rules="[$rules.required,$rules.min6Chars,$rules.max16Chars,$rules.password]"
      :type="showPassword ? 'text' : 'password'"
      :label="$t('password')"
      @click:append="showPassword=!showPassword"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormUpmsResetPassword',
  props: {
    preset: {
      type: Object,
      default: () => ({
        email: null,
        username: null
      })
    }
  },
  data: () => ({
    model: {},

    showPassword: false
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
