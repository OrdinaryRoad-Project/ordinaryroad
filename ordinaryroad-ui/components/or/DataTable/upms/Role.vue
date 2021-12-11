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
            v-model="searchParams.roleName"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="10"
            :label="$t('roleName')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.roleCode"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="10"
            :label="$t('roleCode')"
          />
        </v-col>
      </template>

      <template #moreActions="{item}">
        <v-list-item
          @click="$router.push({ name: 'upms-role-permissions-roleCode', params: { roleCode: item.roleCode, item } })"
        >
          <v-list-item-avatar size="16">
            <v-icon small>
              mdi-lock
            </v-icon>
          </v-list-item-avatar>
          <v-list-item-title>{{ $t('rolePermissionsManagement') }}</v-list-item-title>
        </v-list-item>
        <v-list-item
          @click="$router.push({ name: 'upms-role-users-roleCode', params: { roleCode: item.roleCode, item } })"
        >
          <v-list-item-avatar size="16">
            <v-icon small>
              mdi-account
            </v-icon>
          </v-list-item-avatar>
          <v-list-item-title>{{ $t('roleUsersManagement') }}</v-list-item-title>
        </v-list-item>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="roleDialog"
      loading
      :title="$t(action+'What',[$t('role')])"
      @onConfirm="createOrUpdate"
    >
      <or-form-upms-role-save
        ref="roleForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableUpmsRole',
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
        roleName: null,
        roleCode: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        roleName: '',
        roleCode: ''
      },
      selectedItem: {
        uuid: null,
        roleName: '',
        roleCode: ''
      },
      defaultItem: {
        uuid: null,
        roleName: '',
        roleCode: ''
      }
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: this.$t('roleName'), value: 'roleName', sortable: false },
        { text: this.$t('roleCode'), value: 'roleCode', sortable: false }
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
    onItemUpdate (item) {
      this.editedItem = item
    },
    createOrUpdate () {
      if (this.$util.objectEquals(this.editedItem, this.selectedItem)) {
        this.$refs.roleDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.roleForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.upms.role[action](this.editedItem)
          .then(() => {
            this.$refs.roleDialog.close()
            this.$refs.dataTable.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.roleDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.roleDialog.cancelLoading()
      }
    },
    onInsertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.roleDialog.show()
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.upms.role.delete(this.selectedItem.uuid)
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
      this.$refs.roleDialog.show()
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
      this.$apis.upms.role.list(offset, limit, this.searchParams)
        .then(({ data }) => {
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
