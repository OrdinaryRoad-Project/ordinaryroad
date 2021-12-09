<template>
  <div>
    <or-base-data-table
      ref="dataTable"
      :single-select="singleSelect"
      :select-return-object="selectReturnObject"
      :show-select="showSelect"
      :show-actions-when-selecting="showActionsWhenSelecting"
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
            v-model="searchParams.path"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('path')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.pathName"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="100"
            :label="$t('pathName')"
          />
        </v-col>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="requestPathDialog"
      loading
      :title="$t(action+'What',[$t('requestPath')])"
      @onConfirm="createOrUpdate"
    >
      <or-form-upms-request-path-save
        ref="requestPathForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableUpmsRequestPath',
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
    }
  },
  data () {
    return {
      searchParams: {
        path: null,
        pathName: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        permissionUuid: '',
        path: '',
        pathName: ''
      },
      selectedItem: {
        uuid: null,
        permissionUuid: '',
        path: '',
        pathName: ''
      },
      defaultItem: {
        uuid: null,
        permissionUuid: '',
        path: '',
        pathName: ''
      }
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: this.$t('permissionUuid'), value: 'permissionUuid', sortable: false, width: '300' },
        { text: this.$t('path'), value: 'path', sortable: false },
        { text: this.$t('pathName'), value: 'pathName', sortable: false, width: '200' }
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
        this.$refs.requestPathDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.requestPathForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.upms.request_path[action](this.editedItem)
          .then(() => {
            this.$refs.requestPathDialog.close()
            this.$refs.dataTable.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.requestPathDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.requestPathDialog.cancelLoading()
      }
    },
    onInsertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.requestPathDialog.show()
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.upms.request_path.delete(this.selectedItem.uuid)
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
      this.$refs.requestPathDialog.show()
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
      this.$apis.upms.request_path.list(offset, limit, this.searchParams)
        .then(({ data }) => {
          this.$refs.dataTable.loadSuccessfully(data.list, data.total)
        }).catch(() => {
          this.$refs.dataTable.loadFinish()
        })
    },
    onItemsSelected (items) {
      this.$emit('itemsSelected', items)
    }
  }
}
</script>