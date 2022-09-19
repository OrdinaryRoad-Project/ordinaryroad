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
  <v-data-table
    ref="table"
    multi-sort
    :sort-by="sortBy"
    :sort-desc="sortDesc"
    :height="showSelect?'50vh':null"
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
    @click:row="onClickRow"
    @item-selected="onItemSelected"
    @toggle-select-all="onToggleSelectAll"
    @current-items="onCurrentItems"
  >
    <template #top>
      <slot name="searchFormBefore" />
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
              @click="options={...options,page:1}"
            >
              <v-icon left>
                mdi-magnify
              </v-icon>
              {{ $t('search') }}
            </v-btn>
            <v-btn
              small
              outlined
              @click="resetSearch"
            >
              <v-icon left>
                mdi-refresh
              </v-icon>
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
          <slot name="actionsTop">
            <v-btn
              v-if="(!accessKey||$access.has(accessKey+':create'))&&!hideActions"
              outlined
              color="primary"
              dark
              @click="insertItem"
            >
              <v-icon left>
                mdi-plus
              </v-icon>
              {{ $t('insert') }}
            </v-btn>
          </slot>

          <v-btn
            outlined
            color="success"
            dark
            @click="getItems"
          >
            <v-icon left>
              mdi-reload
            </v-icon>
            {{ $t('refresh') }}
          </v-btn>

          <slot name="actionsTopAfter" />
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
        <slot name="actionsBefore" :item="item" />

        <slot name="actions" :item="item">
          <v-btn
            v-if="(!accessKey||$access.has(accessKey+':update'))"
            icon
            color="accent"
            class="mr-2"
            @click="editItem(item)"
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn
            v-if="(!accessKey||$access.has(accessKey+':delete'))"
            icon
            color="error"
            @click="deleteItem(item)"
          >
            <v-icon>mdi-delete-forever</v-icon>
          </v-btn>
        </slot>

        <slot name="actionsAfter" :item="item" />

        <or-base-menu
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
        </or-base-menu>
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
    showBaseHeadersWhenSelecting: {
      type: Boolean,
      default: false
    },
    presetSelectedItems: {
      type: Array,
      default: () => []
    },
    sortBy: {
      type: Array,
      default: () => []
    },
    sortDesc: {
      type: Array,
      default: () => []
    },
    showUpdateHeaders: {
      type: Boolean,
      default: true
    },

    tableHeaders: {
      type: Array,
      required: true
    },
    /**
     * 用于控制创建、编辑和删除按钮是否生效
     */
    accessKey: {
      type: String,
      default: null
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
      selectedItem: {},
      // 考虑到需要选中的当前没有加载到的情况，null代表还没初始化，空代表全部加载完了
      presetSelectedItemsModel: null
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

      // 业务headers
      headers.push(...this.tableHeaders)

      // 基础headers
      if (!this.showSelect || this.showBaseHeadersWhenSelecting) {
        headers.push(
          {
            text: this.$t('createdTime'),
            value: 'createdTime',
            width: '220'
          },
          {
            text: this.$t('createBy'),
            value: 'createBy',
            width: '100'
          }
        )
        if (this.showUpdateHeaders) {
          headers.push(
            {
              text: this.$t('updateTime'),
              value: 'updateTime',
              width: '220'
            },
            {
              text: this.$t('updateBy'),
              value: 'updateBy',
              width: '100'
            }
          )
        }
      }

      // 操作headers
      if (!this.hideActions) {
        headers.push(
          {
            text: this.$t('dataTable.actions'),
            value: 'actions',
            sortable: false,
            align: 'center',
            class: 'sticky-right',
            cellClass: 'sticky-right'
          }
        )
      }

      return headers
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
    onCurrentItems (items) {
      this.$emit('currentItems', items)
    },
    onClickRow (item, { isSelected }) {
      this.showSelect && this.select({
        item,
        value: !isSelected,
        emit: true
      })
    },
    resetSearch () {
      this.$refs.searchForm.reset()
      this.$emit('resetSearch')
      const options = Object.assign({}, this.options)
      options.page = 1
      this.options = options
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
        if (dialog.isConfirm) {
          this.$emit('deleteItem', {
            item: this.selectedItem,
            index: this.selectedIndex
          })
        }
      })
    },
    editItem (item) {
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$emit('editItem', {
        item: this.selectedItem,
        index: this.selectedIndex
      })
    },
    getItems () {
      this.dataTableParams.loading = true

      const sortBy = this.options.sortBy
      const sortDesc = this.options.sortDesc
      this.$emit('getItems', {
        options: this.options,
        offset: (this.options.page - 1) * this.options.itemsPerPage,
        limit: this.options.itemsPerPage,
        sortBy,
        sortDesc
      })
    },
    onItemSelected ({
      item,
      value
    }) {
      if (!item) {
        return
      }
      if (value) {
        // 防止重复
        if (!this.$util.query(this.selectedItems, 'uuid', this.selectReturnObject ? item.uuid : item)[0]) {
          this.selectedItems.push(this.selectReturnObject ? Object.assign({}, item) : item.uuid)
        }
      } else {
        this.selectedItems = this.$util.remove(this.selectedItems, 'uuid', this.selectReturnObject ? item.uuid : item)
      }
      this.$emit('itemsSelected', this.selectedItems)
    },
    onToggleSelectAll ({
      items,
      value
    }) {
      // 考虑不在当前显示的items情况，items:1,2,3,4,5和selectedItems:1,3,5,7两个合并
      items.forEach((item) => {
        const searchElement = this.selectReturnObject ? Object.assign({}, item) : item.uuid
        const index = this.$util.indexOf(this.selectedItems, searchElement)
        if (value) {
          // 全选直接添加items
          if (index === -1) {
            this.selectedItems.push(searchElement)
          }
        } else if (index !== -1) {
          // 删除取消选择的items
          this.selectedItems.splice(index, 1)
        }
      })

      // 更新UI
      this.selectedItems.forEach((selectedItem) => {
        if (!this.$refs.table.isSelected(selectedItem)) {
          // 不需要VDataTable emit
          this.$refs.table.select(selectedItem, true, false)
        }
      })
      this.$emit('itemsSelected', this.selectedItems)
    },
    setLoading (loading) {
      this.dataTableParams.loading = loading
    },

    select ({
      item,
      value,
      emit
    }) {
      this.$refs.table.select(item, value, emit)
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
      this.dataTableParams = {
        loading: false,
        items,
        totalItems
      }
      if (this.showSelect) {
        // 每次加载完成需要设置选择的item
        this.setPresetSelectedItems()
      }
    },
    /**
     * 仅取消加载动画
     */
    loadFinish () {
      this.setLoading(false)
    },
    /**
     * 初始化预先选中的item，在 loadSuccessfully 后自动调用
     */
    setPresetSelectedItems () {
      setTimeout(() => {
        // 初始化内部的presetSelectedItemsModel
        if (this.presetSelectedItemsModel === null) {
          this.presetSelectedItemsModel = [...this.presetSelectedItems]
        }
        // 已经更新到UI的，需要从presetSelectedItemsModel中删除掉，防止重新选中
        const needDelete = []
        // 遍历更新UI
        this.presetSelectedItemsModel.forEach((presetSelectedItem) => {
          const selectedItem = this.$util.query(
            this.dataTableParams.items, 'uuid',
            this.selectReturnObject ? presetSelectedItem.uuid : presetSelectedItem
          )[0]
          if (selectedItem) {
            // 已加载，更新UI，触发VDataTable回调
            if (!this.$refs.table.isSelected(selectedItem)) {
              this.$refs.table.select(selectedItem)
            }
            needDelete.push(selectedItem)
          }
        })
        // 删除已经更新到UI上的
        needDelete.forEach((item) => {
          this.presetSelectedItemsModel = this.$util.remove(this.presetSelectedItemsModel, 'uuid', item.uuid)
        })
        // 剩余选中的，但是VDataTable还没有加载到
        this.presetSelectedItemsModel.forEach((item) => {
          this.onItemSelected({
            item,
            value: true
          })
        })
      }, 200)
    }
  }
}
</script>
