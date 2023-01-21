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
      v-model="model.permissionUuid"
      :items="permissionUuidOption.items"
      :loading="permissionUuidOption.loading"
      hide-no-data
      hide-selected
      clearable
      item-text="permissionCode"
      item-value="uuid"
      :label="$t('permission')"
      :placeholder="$t('inputWhatToSearchPlaceHolder',[$t('permissionCode')])"
      @update:search-input="searchPermissions"
    >
      <template #selection="{item}">
        {{ item.permissionCode }} {{ item.description }}
      </template>
      <template #item="{item}">
        <div>
          <v-list-item-title>{{ item.permissionCode }}</v-list-item-title>
          <v-list-item-subtitle>{{ item.description }}</v-list-item-subtitle>
        </div>
      </template>
    </v-autocomplete>
    <v-text-field
      v-model="model.path"
      :rules="[$or.rules.required,$or.rules.max200Chars]"
      :label="$t('path')"
    />
    <v-text-field
      v-model="model.pathName"
      :rules="[$or.rules.required,$or.rules.max100Chars]"
      :label="$t('pathName')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormUpmsRequestPathSave',
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
    permissionUuidOption: {
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
    searchPermissions (inputPermissionCode) {
      this.permissionUuidOption.loading = true
      this.$apis.upms.permission.findAll({ permissionCode: inputPermissionCode })
        .then((value) => {
          this.permissionUuidOption.loading = false
          this.permissionUuidOption.items = value.data
        })
        .catch(() => {
          this.permissionUuidOption.loading = false
        })
    }
  }
}
</script>

<style scoped>

</style>
