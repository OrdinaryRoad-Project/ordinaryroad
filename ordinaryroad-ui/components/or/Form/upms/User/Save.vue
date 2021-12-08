<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.email"
      :rules="[$rules.required,$rules.max100Chars,$rules.email]"
      :label="$t('email')"
    />
    <v-text-field
      v-model="model.username"
      :rules="[$rules.max10Chars]"
      :label="$t('username')"
    />
    <v-text-field
      v-if="model.uuid===null"
      v-model="model.password"
      :hint="$t('hints.password')"
      :rules="[$rules.min6Chars,$rules.max16Chars,$rules.password]"
      :label="$t('password')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormUpmsUserSave',
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
