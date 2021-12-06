<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.permissionCode"
      :rules="[$rules.required,$rules.max100Chars]"
      :label="$t('permissionCode')"
    />
    <v-textarea
      v-model="model.description"
      rows="1"
      auto-grow
      :rules="[$rules.max200Chars]"
      :label="$t('description')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'Permission',
  props: {
    preset: {
      type: Object,
      default: () => ({
        permissionCode: null,
        description: null
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
