<template>
  <or-data-table-upms-dict-item :default-dict-uuid="model.uuid" />
</template>
<script>
export default {
  name: 'OrFormUpmsDictItems',
  props: {
    preset: {
      type: Object,
      default: () => ({
        uuid: null,
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
        if (val) {
          this.model = Object.assign({ permissionUuids: [] }, val)
        }
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
