<template>
  <div>
    <v-form ref="form">
      <v-text-field
        v-model="model.roleCode"
        readonly
        :label="$t('roleCode')"
      />
      <or-base-menu
        min-width="40%"
        eager
        :close-on-content-click="false"
      >
        <template #activator="{ on, attrs }">
          <v-autocomplete
            v-model="selectedPermissions"
            v-bind="attrs"
            :items="selectedPermissions"
            multiple
            :label="$t('permission')"
            readonly
            hide-spin-buttons
            v-on="on"
          >
            <template #selection="{item}">
              <v-chip
                close
                @click:close="$refs.permissionDataTable.unSelectItem(item)"
              >
                {{ item.permissionCode }} {{ item.description }}
              </v-chip>
            </template>
          </v-autocomplete>
        </template>
        <v-sheet class="pa-2">
          <or-data-table-upms-permission
            ref="permissionDataTable"
            :preset-selected-items="presetPermissions"
            select-return-object
            show-select
            @itemsSelected="onPermissionsSelected"
          />
        </v-sheet>
      </or-base-menu>
    </v-form>
    <v-row>
      <v-spacer />
      <v-btn
        text
        @click="$router.push({path:'/upms/role'})"
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
  </div>
</template>
<script>
export default {
  name: 'OrFormUpmsRolePermissions',
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
    presetPermissions: [],
    selectedPermissions: [],
    model: {},
    updating: false
  }),
  watch: {
    preset: {
      handler (val) {
        if (val) {
          this.model = Object.assign({ permissionUuids: [] }, val)
          // 查询关联的权限
          this.initPermissions()
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
    onPermissionsSelected (permissions) {
      this.selectedPermissions = permissions
      this.model.permissionUuids = []
      this.selectedPermissions.forEach((permission) => {
        this.model.permissionUuids.push(permission.uuid)
      })
    },
    validate () {
      return this.$refs.form.validate()
    },
    initPermissions () {
      // 根据角色code查询permissions
      this.$apis.upms.permission.findAllByForeignColumn({ roleUuid: this.preset.uuid })
        .then(({ data }) => {
          this.presetPermissions = data
        })
    },
    confirm () {
      if (this.$refs.form.validate()) {
        this.updating = true
        this.$apis.upms.role.updateRolePermissions({
          roleUuid: this.model.uuid, permissionUuids: this.model.permissionUuids
        })
          .then(() => {
            this.$emit('finish')
            this.updating = false
            this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('rolePermissions')]))
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
