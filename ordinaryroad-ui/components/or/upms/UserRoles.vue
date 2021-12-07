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
  name: 'UserRoles',
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
        this.model = Object.assign({ roleUuids: [] }, val)
        // 查询拥有的角色
        this.initRoles()
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
        this.$apis.upms.role.updateUserRoles({
          userUuid: this.model.uuid, roleUuids: this.model.roleUuids
        })
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
