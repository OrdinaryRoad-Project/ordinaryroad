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
      access-key="upms:dict"
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
            v-model="searchParams.dictName"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('dictName')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.dictCode"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('dictCode')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.remark"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="100"
            :label="$t('remark')"
          />
        </v-col>
      </template>

      <template #actionsBefore="{item}">
        <v-btn
          v-if="$access.has('upms:dict_item:list')"
          icon
          color="accent"
          class="mr-2"
          @click="goToDictItem(item)"
        >
          <v-icon>mdi-book-open</v-icon>
        </v-btn>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="dictDialog"
      loading
      :title="$t(action+'What',[$t('dict')])"
      @onConfirm="createOrUpdate"
    >
      <or-form-upms-dict-save
        ref="dictForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableUpmsDict',
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
        dictName: null,
        dictCode: null,
        remark: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        dictName: '',
        dictCode: '',
        remark: ''
      },
      selectedItem: {
        uuid: null,
        dictName: '',
        dictCode: '',
        remark: ''
      },
      defaultItem: {
        uuid: null,
        dictName: '',
        dictCode: '',
        remark: ''
      }
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: this.$t('dictName'), value: 'dictName', sortable: false },
        { text: this.$t('dictCode'), value: 'dictCode', sortable: false },
        { text: this.$t('remark'), value: 'remark', sortable: false, width: '200' }
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
    goToDictItem (item) {
      this.$router.push({ name: 'upms-dict-items-dictCode', params: { dictCode: item.dictCode, item } })
    },
    onItemUpdate (item) {
      this.editedItem = item
    },
    createOrUpdate () {
      if (this.$util.objectEquals(this.editedItem, this.selectedItem)) {
        this.$refs.dictDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.dictForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.upms.dict[action](this.editedItem)
          .then(() => {
            this.$refs.dictDialog.close()
            this.$refs.dataTable.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.dictDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.dictDialog.cancelLoading()
      }
    },
    onInsertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.dictDialog.show()
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.upms.dict.delete(this.selectedItem.uuid)
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
      this.$refs.dictDialog.show()
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
      this.$apis.upms.dict.list(offset, limit, this.searchParams)
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
