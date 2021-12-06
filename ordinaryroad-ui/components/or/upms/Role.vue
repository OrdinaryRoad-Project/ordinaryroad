<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.roleName"
      :rules="[$rules.required,$rules.max10Chars]"
      :label="$t('roleName')"
    />
    <v-text-field
      v-model="model.roleCode"
      :rules="[$rules.required,$rules.max10Chars]"
      :label="$t('roleCode')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'Role',
  props: {
    preset: {
      type: Object,
      default: () => ({
        roleName: null,
        roleCode: null
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
