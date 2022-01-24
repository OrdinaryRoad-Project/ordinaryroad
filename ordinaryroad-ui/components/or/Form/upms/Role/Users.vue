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
