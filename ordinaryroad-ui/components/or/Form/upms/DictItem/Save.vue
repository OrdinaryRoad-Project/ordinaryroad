<!--
  - MIT License
  -
  - Copyright (c) 2021 苗锦洲
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

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
      :rules="[$or.rules.required,$or.rules.max50Chars]"
      :label="$t('label')"
    />
    <v-text-field
      v-model="model.value"
      :rules="[$or.rules.required,$or.rules.max10Chars]"
      :label="$t('value')"
    />
    <v-text-field
      v-model="model.sort"
      type="number"
      :rules="[$or.rules.max10Chars]"
      :label="$t('sort')"
    />
    <v-textarea
      v-model="model.remark"
      rows="1"
      auto-grow
      :rules="[$or.rules.max200Chars]"
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
