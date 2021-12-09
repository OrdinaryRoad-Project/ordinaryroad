<template>
  <div>
    <or-base-data-table
      ref="dataTable"
      :single-select="singleSelect"
      :select-return-object="selectReturnObject"
      :show-select="showSelect"
      :show-actions-when-selecting="showActionsWhenSelecting"
      :preset-selected-items="presetSelectedItems"
      :table-headers="headers"
      @getItems="onGetItems"
      @insertItem="onInsertItem"
      @deleteItem="onDeleteItem"
      @editItem="onEditItem"
      @itemsSelected="onItemsSelected"
    >
      <template #searchFormBody>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.email"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="10"
            :label="$t('email')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.orNumber"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="10"
            :label="$t('orNumber')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.username"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="10"
            :label="$t('username')"
          />
        </v-col>
      </template>

      <template #[`item.enabled`]="{ item }">
        <v-switch
          v-model="item.enabled"
          readonly
          :disabled="showSelect&&!showActionsWhenSelecting"
          inset
          @click="updateItemEnabled(item)"
        />
      </template>

      <template #moreActions="{item}">
        <v-list-item @click="resetItemPassword(item)">
          <v-list-item-avatar size="16">
            <v-icon small>
              mdi-lock
            </v-icon>
          </v-list-item-avatar>
          <v-list-item-title>{{ $t('resetPassword') }}</v-list-item-title>
        </v-list-item>
        <v-list-item @click="updateItemRoles(item)">
          <v-list-item-avatar size="16">
            <v-icon small>
              mdi-account-multiple
            </v-icon>
          </v-list-item-avatar>
          <v-list-item-title>{{ $t('userRolesManagement') }}</v-list-item-title>
        </v-list-item>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="userDialog"
      loading
      :title="$t(action+'What',[$t('user')])"
      @onConfirm="createOrUpdate"
    >
      <or-form-upms-user-save
        ref="userForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
    <or-base-dialog
      ref="resetPasswordDialog"
      loading
      :title="$t('resetPassword')"
      @onConfirm="resetPassword"
    >
      <or-form-upms-user-reset-password
        ref="resetPasswordForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableUpmsUser',
  props: {
    /**
     * 选中返回完整Object数组，默认只返回uuid数组
     */
    selectReturnObject: {
      type: Boolean,
      default: false
    },
    singleSelect: {
      type: Boolean,
      default: false
    },
    showSelect: {
      type: Boolean,
      default: false
    },
    showActionsWhenSelecting: {
      type: Boolean,
      default: false
    },
    presetSelectedItems: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      searchParams: {
        email: null,
        orNumber: null,
        username: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        email: '',
        username: '',
        password: '',
        orNumber: null
      },
      selectedItem: {
        uuid: null,
        email: '',
        username: '',
        password: '',
        orNumber: null
      },
      defaultItem: {
        uuid: null,
        email: '',
        username: '',
        password: '',
        orNumber: null
      }
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: this.$t('email'), value: 'email', sortable: false },
        { text: this.$t('orNumber'), value: 'orNumber', sortable: false },
        { text: this.$t('username'), value: 'username', sortable: false },
        { text: this.$t('enabled'), value: 'enabled', sortable: false }
      ]
    },
    action () {
      return this.selectedIndex === -1 ? 'create' : 'update'
    }
  },
  watch: {},
  created () {
  },
  mounted () {
  },
  methods: {
    updateItemEnabled (item) {
      if (this.showSelect && !this.showActionsWhenSelecting) {
        return
      }
      const actionKey = item.enabled ? 'disable' : 'enable'
      const operationString = this.$t(actionKey) + ' ' + item.orNumber + ' '
      this.$dialog({
        content: this.$t('areYouSureToDoWhat', [operationString]),
        loading: true
      }).then((dialog) => {
        this.$apis.upms.user.updateEnabled(item.uuid, !item.enabled)
          .then(() => {
            this.$snackbar.success(this.$t('whatSuccessfully', [operationString]))
            item.enabled = !item.enabled
            dialog.cancel()
          })
          .catch(() => {
            dialog.cancel()
          })
      })
    },
    updateItemRoles (item) {
      this.$router.push({ name: 'upms-user-roles-orNumber', params: { orNumber: item.orNumber, item } })
    },
    resetItemPassword (item) {
      this.showPassword = false
      this.selectedItem = Object.assign({}, item)
      this.editedItem = Object.assign({}, this.defaultItem)
      this.selectedItem.password = null
      this.editedItem.password = null
      this.$refs.resetPasswordDialog.show()
    },
    resetPassword () {
      if (this.$refs.resetPasswordForm.validate()) {
        // 调用API重置密码
        this.$apis.upms.user.resetPassword(this.selectedItem.uuid, this.editedItem.password)
          .then(() => {
            this.$refs.resetPasswordDialog.close()
          }).catch(() => {
            this.$refs.resetPasswordDialog.cancelLoading()
          })
      } else {
        this.$refs.resetPasswordDialog.cancelLoading()
      }
    },
    onItemUpdate (item) {
      this.editedItem = item
    },
    createOrUpdate () {
      if (this.$util.objectEquals(this.editedItem, this.selectedItem)) {
        this.$refs.userDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.userForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.upms.user[action](this.editedItem)
          .then(() => {
            this.$refs.userDialog.close()
            this.$refs.dataTable.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.userDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.userDialog.cancelLoading()
      }
    },
    onInsertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.userDialog.show()
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.upms.user.delete(this.selectedItem.uuid)
        .then(() => {
          this.$refs.dataTable.deleteSuccessfully()
        })
        .catch(() => {
          this.$refs.dataTable.deleteFailed()
        })
    },
    onEditItem ({ item, index }) {
      this.selectedIndex = index
      this.selectedItem = Object.assign({}, item)
      this.$refs.userDialog.show()
    },
    onGetItems ({ options, offset, limit }) {
      /* TODO 排序支持
      options:
        groupBy: Array(0)
        groupDesc: Array(0)
        itemsPerPage: 10
        multiSort: false
        mustSort: false
        page: 1
        sortBy: Array(1)
        sortDesc: Array(1)
       */
      this.$apis.upms.user.list(offset, limit, this.searchParams
      ).then(({ data }) => {
        this.$refs.dataTable.loadSuccessfully(data.list, data.total)
      }).catch(() => {
        this.$refs.dataTable.loadFinish()
      })
    },
    onItemsSelected (items) {
      this.$emit('itemsSelected', items)
    },
    unSelectItem (item) {
      this.$refs.dataTable.select({ item, value: false, emit: true })
    }
  }
}
</script>
