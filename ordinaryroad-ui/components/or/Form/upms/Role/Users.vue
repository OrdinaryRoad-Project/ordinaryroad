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
            v-model="selectedUsers"
            v-bind="attrs"
            :items="selectedUsers"
            multiple
            :label="$t('user')"
            readonly
            hide-spin-buttons
            v-on="on"
          >
            <template #selection="{item}">
              <v-chip
                close
                @click:close="$refs.userDataTable.unSelectItem(item)"
              >
                {{ item.orNumber }} {{ item.username }}
              </v-chip>
            </template>
          </v-autocomplete>
        </template>
        <v-sheet class="pa-2">
          <or-data-table-upms-user
            ref="userDataTable"
            :preset-selected-items="presetUsers"
            select-return-object
            show-select
            @itemsSelected="onUserSelected"
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
  name: 'OrFormUpmsRoleUsers',
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
    presetUsers: [],
    selectedUsers: [],
    model: {},
    updating: false
  }),
  watch: {
    preset: {
      handler (val) {
        if (val) {
          this.model = Object.assign({ userUuids: [] }, val)
          // 查询关联的用户
          this.initUsers()
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
    onUserSelected (users) {
      this.selectedUsers = users
      this.model.userUuids = []
      this.selectedUsers.forEach((user) => {
        this.model.userUuids.push(user.uuid)
      })
    },
    validate () {
      return this.$refs.form.validate()
    },
    initUsers () {
      // 根据角色code查询users
      this.$apis.upms.user.findAllByForeignColumn({ roleUuid: this.preset.uuid })
        .then(({ data }) => {
          this.presetUsers = data
        })
    },
    confirm () {
      if (this.$refs.form.validate()) {
        this.updating = true
        this.$apis.upms.role.updateRoleUsers({
          roleUuid: this.model.uuid, userUuids: this.model.userUuids
        })
          .then(() => {
            this.$emit('finish')
            this.updating = false
            this.$snackbar.success(this.$t('whatUpdateSuccessfully', [this.$t('roleUsers')]))
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
