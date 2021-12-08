<template>
  <v-data-table
    ref="table"
    :single-select="singleSelect"
    :show-select="showSelect"
    item-key="uuid"
    :headers="headers"
    :items="dataTableParams.items"
    :options.sync="options"
    :server-items-length="dataTableParams.totalItems"
    :loading="dataTableParams.loading"
    :items-per-page="20"
    :footer-props="{
      showFirstLastPage: true,
      firstIcon: 'mdi-page-first',
      lastIcon: 'mdi-page-last',
      itemsPerPageOptions:[ 10, 20, 50, 100 ]
    }"
    @item-selected="onItemSelected"
    @toggle-select-all="onToggleSelectAll"
  >
    <template #top>
      <v-form ref="searchForm">
        <v-row align="center">
          <slot name="searchFormBody" />
          <v-col
            cols="6"
            lg="3"
            md="4"
          >
            <v-btn
              small
              color="primary"
              outlined
              @click="getItems"
            >
              <v-icon>mdi-magnify</v-icon>
              {{ $t('search') }}
            </v-btn>
            <v-btn
              small
              outlined
              @click="resetSearch"
            >
              <v-icon>mdi-refresh</v-icon>
              {{ $t('reset') }}
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
      <v-row class="mt-2">
        <v-col
          cols="12"
          md="4"
        >
          <v-btn
            v-if="!hideActions"
            outlined
            color="primary"
            dark
            @click="insertItem"
          >
            <v-icon>mdi-plus</v-icon>
            {{ $t('insert') }}
          </v-btn>
          <v-btn
            outlined
            color="success"
            dark
            @click="getItems"
          >
            <v-icon>mdi-reload</v-icon>
            {{ $t('refresh') }}
          </v-btn>
        </v-col>
      </v-row>
      <v-divider class="mt-2" />
    </template>

    <template
      v-if="!$vuetify.breakpoint.xs"
      #[`header.actions`]="{ header }"
    >
      <v-sheet elevation="1">
        {{ header.text }}
      </v-sheet>
    </template>

    <!-- 动态生成插槽 -->
    <template v-for="slot in itemSlotNames" #[slot]="{ item }">
      <slot :name="slot" :item="item" />
    </template>

    <template #[`item.createdTime`]="{ item }">
      {{ $dayjs(item.createdTime).format() }}
    </template>

    <template #[`item.updateTime`]="{ item }">
      {{ $dayjs(item.updateTime).format() }}
    </template>

    <template #[`item.actions`]="{ item }">
      <div
        :class="$vuetify.breakpoint.xs?''
          :$vuetify.theme.dark ?'v-sheet theme--dark elevation-1 d-flex'
            :'v-sheet theme--light elevation-1 d-flex'"
      >
        <v-btn
          icon
          color="accent"
          class="mr-2"
          @click="editItem(item)"
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn
          icon
          color="error"
          @click="deleteItem(item)"
        >
          <v-icon>mdi-delete-forever</v-icon>
        </v-btn>

        <slot name="actions" :item="item" />

        <v-menu
          v-if="$scopedSlots.moreActions"
          offset-y
          open-on-hover
        >
          <template #activator="{ on, attrs }">
            <v-btn
              class="ml-2"
              color="primary"
              dark
              v-bind="attrs"
              icon
              v-on="on"
            >
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list dense>
            <slot name="moreActions" :item="item" />
          </v-list>
        </v-menu>
      </div>
    </template>
  </v-data-table>
</template>

<script>
export default {
  name: 'OrBaseDataTable',
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

    tableHeaders: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      deleteDialog: null,
      options: {},
      dataTableParams: {
        loading: true,
        items: [],
        totalItems: 0
      },
      selectedItems: [],
      selectedIndex: -1,
      selectedItem: {}
    }
  },
  computed: {
    /**
     * 获取VDataTable的名称为items.*的Slot
     * @returns {*[]}
     */
    itemSlotNames () {
      const slotNames = Object.keys(this.$scopedSlots)
      const itemSlotNames = []
      slotNames.forEach((slotName) => {
        if (slotName.includes('item.')) {
          itemSlotNames.push(slotName)
        }
      })
      return itemSlotNames
    },
    hideActions () {
      return this.showSelect && !this.showActionsWhenSelecting
    },
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      const headers = []
      headers.push(
        { text: 'UUID', value: 'uuid', sortable: false },
        ...this.tableHeaders,
        { text: this.$t('createdTime'), value: 'createdTime', sortable: false, width: '220' },
        { text: this.$t('createBy'), value: 'createBy', sortable: false },
        { text: this.$t('updateTime'), value: 'updateTime', sortable: false, width: '220' },
        { text: this.$t('updateBy'), value: 'updateBy', sortable: false },
        {
          text: this.$t('dataTable.actions'),
          value: 'actions',
          sortable: false,
          align: 'center',
          class: 'sticky-right',
          cellClass: 'sticky-right'
        }
      )
      return this.hideActions ? this.$util.remove(headers, 'value', 'actions') : headers
    },
    action () {
      return this.selectedIndex === -1 ? 'create' : 'update'
    }
  },
  watch: {
    options () {
      this.getItems()
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    resetSearch () {
      this.$refs.searchForm.reset()
      this.options = { page: 1 }
    },
    insertItem () {
      this.$emit('insertItem')
    },
    deleteItem (item) {
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$dialog({
        content: this.$t('deleteDialog.content', [this.selectedItem.uuid]),
        loading: true
      }).then((dialog) => {
        this.deleteDialog = dialog
        // 删除
        this.$emit('deleteItem', {
          item: this.selectedItem, index: this.selectedIndex
        })
      })
    },
    editItem (item) {
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$emit('editItem', {
        item: this.selectedItem, index: this.selectedIndex
      })
    },
    getItems () {
      this.dataTableParams.loading = true
      this.$emit('getItems', {
        options: this.options,
        offset: this.options.page === 1 ? 0 : this.dataTableParams.items.length,
        limit: this.options.itemsPerPage
      })
    },
    onItemSelected ({ item, value }) {
      if (value) {
        this.selectedItems.push(this.selectReturnObject ? Object.assign({}, item) : item.uuid)
      } else if (this.selectReturnObject) {
        this.selectedItems = this.$util.remove(this.selectedItems, 'uuid', item.uuid)
      } else {
        this.selectedItems.splice(this.selectedItems.indexOf(item.uuid), 1)
      }
      this.$emit('itemsSelected', this.selectedItems)
    },
    onToggleSelectAll ({ items, value }) {
      this.selectedItems = []
      if (value) {
        items.forEach((item) => {
          this.selectedItems.push(this.selectReturnObject ? Object.assign({}, item) : item.uuid)
        })
      }
      this.$emit('itemsSelected', this.selectedItems)
    },
    setLoading (loading) {
      this.dataTableParams.loading = loading
    },

    /**
     * 删除成功后通过$refs手动调用
     */
    deleteSuccessfully () {
      if (this.deleteDialog) {
        this.getItems()
        this.deleteDialog.cancel()
        this.$snackbar.success(this.$t('deleteSuccess'))
        this.deleteDialog = null
      }
    },
    /**
     * 删除失败后通过$refs手动调用
     */
    deleteFailed () {
      if (this.deleteDialog) {
        this.deleteDialog.cancel()
        this.deleteDialog = null
      }
    },
    /**
     * 加载完成后通过$refs手动调用
     */
    loadSuccessfully (items, totalItems) {
      const oldItems = this.dataTableParams.items
      this.dataTableParams = { loading: false, items, totalItems }
      if (this.showSelect) {
        // 判断items是否发生变化，发生变化才取消全选
        if (!this.$util.arrayEquals(oldItems, items)) {
          this.$refs.table.toggleSelectAll(false)
        }
      }
    },
    /**
     * 仅取消加载动画
     */
    loadFinish () {
      this.setLoading(false)
    },
    /**
     * 初始化预先选中的，在 loadSuccessfully 后调用
     * @param presetSelectedItems 预先选中items
     */
    presetSelectedItems (presetSelectedItems) {
      presetSelectedItems.forEach((presetSelectedItem) => {
        const selectedItem = this.$util.query(
          this.dataTableParams.items, 'uuid',
          this.selectReturnObject ? presetSelectedItem.uuid : presetSelectedItem
        )[0]
        this.$refs.table.select(selectedItem)
      })
    }
  }
}
</script>
