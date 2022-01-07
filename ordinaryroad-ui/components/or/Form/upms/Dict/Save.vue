<template>
  <v-form ref="form">
    <v-text-field
      v-model="model.dictName"
      :rules="[$rules.required,$rules.max100Chars]"
      :label="$t('dictName')"
    />
    <v-text-field
      v-model="model.dictCode"
      :rules="[$rules.required,$rules.max100Chars]"
      :label="$t('dictCode')"
    />
    <v-textarea
      v-model="model.remark"
      rows="1"
      auto-grow
      :rules="[$rules.max200Chars]"
      :label="$t('remark')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormUpmsDictSave',
  props: {
    preset: {
      type: Object,
      default: () => ({
        dictName: null,
        dictCode: null,
        remark: null
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
