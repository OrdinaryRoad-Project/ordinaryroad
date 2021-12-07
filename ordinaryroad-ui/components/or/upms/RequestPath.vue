<template>
  <v-form ref="form">
    <v-autocomplete
      v-model="model.permissionUuid"
      :items="permissionUuidOption.items"
      :loading="permissionUuidOption.loading"
      hide-no-data
      hide-selected
      item-text="permissionCode"
      item-value="uuid"
      :label="$t('permission')"
      :placeholder="$t('inputSearchPlaceHolder')"
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
          const data = value.data
          this.permissionUuidOption.items = []
          data.forEach((value) => {
            // this.permissionUuidOption.items.push(value.permissionCode)
            this.permissionUuidOption.items.push(value)
          })
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
