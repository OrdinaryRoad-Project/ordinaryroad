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
      access-key="auth:registered_client"
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
            v-model="searchParams.clientId"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="20"
            :label="$t('clientId')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.clientSecret"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="20"
            :label="$t('clientSecret')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.clientName"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="100"
            :label="$t('clientName')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.redirectUris"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="1000"
            :label="$t('redirectUris')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.scopes"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="1000"
            :label="$t('scopes')"
          />
        </v-col>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="registeredClientDialog"
      loading
      :title="$t(action+'What',[$t('registeredClient')])"
      @onConfirm="createOrUpdate"
    >
      <or-form-auth-registered-client-save
        ref="registeredClientForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableAuthRegisteredClient',
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
        clientId: null,
        clientSecret: null,
        clientName: null,
        redirectUris: null,
        scopes: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        clientId: '',
        clientSecret: '',
        clientName: '',
        redirectUris: '',
        scopes: ''
      },
      selectedItem: {
        uuid: null,
        clientId: '',
        clientSecret: '',
        clientName: '',
        redirectUris: '',
        scopes: ''
      },
      defaultItem: {
        uuid: null,
        clientId: '',
        clientSecret: '',
        clientName: '',
        redirectUris: '',
        scopes: ''
      }
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: this.$t('clientId'), value: 'clientId', sortable: false },
        { text: this.$t('clientSecret'), value: 'clientSecret', sortable: false },
        { text: this.$t('clientName'), value: 'clientName', sortable: false },
        { text: this.$t('redirectUris'), value: 'redirectUris', sortable: false },
        { text: this.$t('scopes'), value: 'scopes', sortable: false }
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
        this.$refs.registeredClientDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.registeredClientForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.auth.registered_client[action](this.editedItem)
          .then(() => {
            this.$refs.registeredClientDialog.close()
            this.$refs.dataTable.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.registeredClientDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.registeredClientDialog.cancelLoading()
      }
    },
    onInsertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.registeredClientDialog.show()
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.auth.registered_client.delete(this.selectedItem.uuid)
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
      this.$refs.registeredClientDialog.show()
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
      this.$apis.auth.registered_client.list(offset, limit, this.searchParams)
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
