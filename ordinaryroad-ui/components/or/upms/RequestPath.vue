<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.permissionUuid"
      :rules="[$rules.max32Chars]"
      :label="$t('permissionUuid')"
    />
    <v-text-field
      v-model="model.path"
      :rules="[$rules.required,$rules.max200Chars]"
      :label="$t('path')"
    />
    <v-text-field
      v-model="model.pathName"
      :rules="[$rules.required,$rules.max100Chars]"
      :label="$t('pathName')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'RequestPath',
  props: {
    preset: {
      type: Object,
      default: () => ({
        permissionUuid: null,
        path: null,
        pathName: null
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
