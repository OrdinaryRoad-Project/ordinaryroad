<template>
  <v-form ref="form">
    <v-autocomplete
      v-model="model.dictUuid"
      disabled
      :items="dictUuidOption.items"
      :loading="dictUuidOption.loading"
      hide-no-data
      hide-selected
      clearable
      item-text="dictName"
      item-value="uuid"
      :label="$t('dict')"
      :placeholder="$t('inputWhatToSearchPlaceHolder',[$t('dictName')])"
      @update:search-input="searchDicts"
    >
      <template #selection="{item}">
        {{ item.dictName }} {{ item.dictCode }}
      </template>
      <template #item="{item}">
        <div>
          <v-list-item-title>{{ item.dictName }}</v-list-item-title>
          <v-list-item-subtitle>{{ item.dictCode }}</v-list-item-subtitle>
        </div>
      </template>
    </v-autocomplete>
    <v-text-field
      v-model="model.label"
      :rules="[$rules.required,$rules.max50Chars]"
      :label="$t('label')"
    />
    <v-text-field
      v-model="model.value"
      :rules="[$rules.required,$rules.max10Chars]"
      :label="$t('value')"
    />
    <v-text-field
      v-model="model.sort"
      type="number"
      :rules="[$rules.max10Chars]"
      :label="$t('sort')"
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
  name: 'OrFormUpmsDictItemSave',
  props: {
    preset: {
      type: Object,
      default: () => ({
        dictUuid: null,
        label: null,
        value: null,
        sort: null,
        remark: null
      })
    }
  },
  data: () => ({
    dictUuidOption: {
      select: null,
      loading: false,
      items: []
    },
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
    },
    searchDicts (inputDictName) {
      this.dictUuidOption.loading = true
      this.$apis.upms.dict.findAll({ dictName: inputDictName })
        .then((value) => {
          this.dictUuidOption.loading = false
          this.dictUuidOption.items = value.data
        })
        .catch(() => {
          this.dictUuidOption.loading = false
        })
    }
  }
}
</script>

<style scoped>

</style>
