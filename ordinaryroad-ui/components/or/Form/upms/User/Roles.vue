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
    <v-text-field
      v-model="model.orNumber"
      readonly
      :label="$t('orNumber')"
    />
    <v-autocomplete
      v-model="model.roleUuids"
      clearable
      :disabled="roleUuidsOption.initializing"
      :items="roleUuidsOption.items"
      :loading="roleUuidsOption.initializing||roleUuidsOption.loading"
      hide-no-data
      hide-selected
      item-text="roleCode"
      item-value="uuid"
      multiple
      :label="$t('role')"
      :placeholder="$t('inputWhatToSearchPlaceHolder',[$t('roleCode')])"
      @update:search-input="searchRoles"
    >
      <template #selection="{item}">
        <v-chip
          close
          @click:close="model.roleUuids.splice(model.roleUuids.indexOf(item.uuid),1)"
        >
          {{ item.roleCode }} {{ item.roleName }}
        </v-chip>
      </template>
      <template #item="{item}">
        <div>
          <v-list-item-title>{{ item.roleCode }}</v-list-item-title>
          <v-list-item-subtitle>{{ item.roleName }}</v-list-item-subtitle>
        </div>
      </template>
    </v-autocomplete>
    <v-row>
      <v-spacer />
      <v-btn
        text
        @click="$router.push({path:'/upms/user'})"
      >
        {{ $t('back') }}
      </v-btn>
      <v-btn
        :loading="updating"
        text
        color="primary"
        @click="confirm"
      >
        {{ $t('confirm') }}
      </v-btn>
    </v-row>
  </v-form>
</template>
<script>
export default {
  name: 'OrFormUpmsUserRoles',
  props: {
    preset: {
      type: Object,
      default: () => ({
        uuid: null,
        orNumber: null
      })
    }
  },
  data: () => ({
    model: {},
    updating: false,
    roleUuidsOption: {
      initializing: true,
      loading: false,
      items: []
    }
  }),
  watch: {
    preset: {
      handler (val) {
        if (val) {
          this.model = Object.assign({ roleUuids: [] }, val)
          // 查询拥有的角色
          this.initRoles()
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
    },
    searchRoles (inputRoleCode) {
      this.roleUuidsOption.loading = true
      this.$apis.upms.role.findAll({ roleCode: inputRoleCode })
        .then((value) => {
          this.roleUuidsOption.loading = false
          this.roleUuidsOption.items = value.data
        })
        .catch(() => {
          this.roleUuidsOption.loading = false
        })
    },
    initRoles () {
      // 根据用户uuid查询roles
      this.roleUuidsOption.initializing = true
      this.$apis.upms.role.findAllByUserUuid(this.preset.uuid)
        .then((value) => {
          this.model.roleUuids = []
          value.data.forEach((value) => {
            this.model.roleUuids.push(value.uuid)
          })
          this.roleUuidsOption.initializing = false
        })
        .catch(() => {
          this.roleUuidsOption.initializing = false
        })
    },
    confirm () {
      if (this.$refs.form.validate()) {
        this.updating = true
        this.$apis.upms.user.updateUserRoles(this.model)
          .then(() => {
            this.$emit('finish')
            this.updating = false
            this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('userRoles')]))
          })
          .catch(() => {
            this.updating = false
          })
      }
    }
  }
}
</script>

<style scoped>

</style>
