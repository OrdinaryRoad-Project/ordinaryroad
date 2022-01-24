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
    <or-base-data-table
      ref="dataTable"
      :sort-by="['sort']"
      :sort-desc="[false]"
      :single-select="singleSelect"
      :select-return-object="selectReturnObject"
      :show-select="showSelect"
      :show-actions-when-selecting="showActionsWhenSelecting"
      :preset-selected-items="presetSelectedItems"
      :table-headers="headers"
      access-key="upms:dict_item"
      @getItems="onGetItems"
      @insertItem="onInsertItem"
      @deleteItem="onDeleteItem"
      @editItem="onEditItem"
      @itemsSelected="onItemsSelected"
      @resetSearch="onResetSearch"
    >
      <template #searchFormBefore>
        <v-row align="center">
          <v-col
            cols="6"
            lg="3"
            md="4"
          >
            <v-autocomplete
              v-model="searchParams.dictUuid"
              dense
              outlined
              :items="dictUuidOption.items"
              :loading="dictUuidOption.loading"
              hide-no-data
              hide-selected
              hide-details="auto"
              clearable
              item-text="dictName"
              item-value="uuid"
              :label="$t('dict')"
              :placeholder="$t('inputWhatToSearchPlaceHolder',[$t('dictName')])"
              @update:search-input="searchDicts"
              @input="onSelectedDictUuidChange"
            />
          </v-col>
        </v-row>
      </template>

      <template #searchFormBody>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.label"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('label')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.value"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('value')"
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

      <template #actionsTopAfter>
        <v-btn
          outlined
          @click="$router.push({path:'/upms/dict'})"
        >
          <v-icon>mdi-arrow-left</v-icon>
          {{ $t('back') }}
        </v-btn>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="dictItemDialog"
      loading
      :title="$t(action+'What',[$t('dictItem')])"
      @onConfirm="createOrUpdate"
    >
      <or-form-upms-dict-item-save
        ref="dictItemForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableUpmsDictItem',
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
    },

    /**
     * 默认选中的字典uuid
     */
    defaultDictUuid: {
      type: String,
      default: null
    }
  },
  data () {
    return {
      dictUuidOption: {
        select: null,
        loading: false,
        items: []
      },
      searchParams: {
        dictUuid: null,
        dictName: null,
        dictCode: null,
        remark: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        dictUuid: null,
        dictName: '',
        dictCode: '',
        remark: ''
      },
      selectedItem: {
        uuid: null,
        dictUuid: null,
        dictName: '',
        dictCode: '',
        remark: ''
      },
      defaultItem: {
        uuid: null,
        dictUuid: null,
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
        { text: this.$t('label'), value: 'label' },
        { text: this.$t('value'), value: 'value' },
        { text: this.$t('sort'), value: 'sort' },
        { text: this.$t('remark'), value: 'remark', width: '200' }
      ]
    },
    action () {
      return this.selectedIndex === -1 ? 'create' : 'update'
    }
  },
  watch: {
    defaultDictUuid: {
      handler (val) {
        if (val) {
          this.searchParams.dictUuid = val
        }
      },
      deep: true,
      immediate: true
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    onResetSearch () {
      this.searchParams.dictUuid = this.defaultDictUuid
    },
    onSelectedDictUuidChange (val) {
      val && this.$refs.dataTable.getItems()
    },
    searchDicts (inputDictName) {
      this.dictUuidOption.loading = true
      this.$apis.upms.dict.findAll({ dictName: inputDictName })
        .then((value) => {
          this.dictUuidOption.loading = false
          this.dictUuidOption.items = value.data
        })
        .catch(() => {
          this.dictUuidOption.loading = false
        })
    },
    onItemUpdate (item) {
      this.editedItem = item
    },
    createOrUpdate () {
      if (this.$util.objectEquals(this.editedItem, this.selectedItem)) {
        this.$refs.dictItemDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.dictItemForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.upms.dict_item[action](this.editedItem)
          .then(() => {
            this.$refs.dictItemDialog.close()
            this.$refs.dataTable.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.dictItemDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.dictItemDialog.cancelLoading()
      }
    },
    onInsertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, { ...this.defaultItem, dictUuid: this.searchParams.dictUuid })
      this.$refs.dictItemDialog.show()
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.upms.dict_item.delete(this.selectedItem.uuid)
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
      this.$refs.dictItemDialog.show()
    },
    onGetItems ({ options, offset, limit, orderBy, orderByDesc }) {
      /* 支持排序
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
      this.$apis.upms.dict_item.list(offset, limit, orderBy, orderByDesc, this.searchParams)
        .then(({ data }) => {
          this.$refs.dataTable.loadSuccessfully(data.list, data.total)
        })
        .catch(() => {
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
