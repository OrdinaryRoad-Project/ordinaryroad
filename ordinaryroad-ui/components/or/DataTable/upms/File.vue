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
      :single-select="singleSelect"
      :select-return-object="selectReturnObject"
      :show-select="showSelect"
      :show-actions-when-selecting="showActionsWhenSelecting"
      :preset-selected-items="presetSelectedItems"
      :table-headers="headers"
      access-key="upms:file"
      @getItems="onGetItems"
      @deleteItem="onDeleteItem"
      @itemsSelected="onItemsSelected"
    >
      <template #searchFormBefore>
        <v-row align="center">
          <v-col
            cols="6"
            lg="3"
            md="4"
          >
            <v-select
              v-model="showTypeOptions.item"
              :items="showTypeOptions.items"
              dense
              outlined
              item-text="label"
              item-value="value"
              hide-details="auto"
              :label="$t('showType')"
            >
              <template #item="{item}">
                <div>
                  <v-list-item-title>{{ item.label }}</v-list-item-title>
                  <v-list-item-subtitle v-if="item.remark">
                    {{ item.remark }}
                  </v-list-item-subtitle>
                </div>
              </template>
            </v-select>
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
            v-model="searchParams.bucketName"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="100"
            :label="$t('bucketName')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.objectName"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('objectName')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.originalFilename"
            dense
            outlined
            clearable
            hide-details="auto"
            maxlength="200"
            :label="$t('originalFilename')"
          />
        </v-col>
      </template>

      <template #actionsTop>
        <v-file-input
          v-show="false"
          ref="fileInput"
          @change="onFileSelect"
        />
        <v-btn
          v-if="($access.has('upms:file:upload'))&&!(showSelect && !showActionsWhenSelecting)"
          :loading="fileOptions.uploading"
          outlined
          color="primary"
          dark
          @click="$refs.fileInput.$refs.input.click()"
        >
          <v-icon>mdi-upload</v-icon>
          {{ $t('upload') }}
        </v-btn>
      </template>

      <template #[`item.size`]="{item}">
        {{ $util.getFileSizeString(item.size) }}
      </template>

      <template #actions="{item}">
        <v-btn
          icon
          target="_blank"
          :to="$apis.upms.file.getFileDownloadPath({
            bucketAndObjectName: '/'+item.bucketName+item.objectName,
            showInLine: showTypeOptions.item
          })"
        >
          <v-icon>mdi-link</v-icon>
        </v-btn>
      </template>
    </or-base-data-table>
  </div>
</template>

<script>
export default {
  name: 'OrDataTableUpmsFile',
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
      showTypeOptions: {
        item: null,
        items: [
          {
            label: '自动',
            value: null,
            remark: '图片inline，其它attachment'
          },
          {
            label: 'inline',
            value: true,
            remark: null
          },
          {
            label: 'attachment',
            value: false,
            remark: null
          }
        ]
      },
      fileOptions: {
        uploading: false
      },
      searchParams: {
        bucketName: null,
        objectName: null,
        originalFilename: null
      },
      selectedIndex: -1,
      editedItem: {
        uuid: null,
        bucketName: '',
        objectName: '',
        originalFilename: ''
      },
      selectedItem: {
        uuid: null,
        bucketName: '',
        objectName: '',
        originalFilename: ''
      },
      defaultItem: {
        uuid: null,
        bucketName: '',
        objectName: '',
        originalFilename: ''
      }
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: this.$t('bucketName'), value: 'bucketName', sortable: false },
        { text: this.$t('objectName'), value: 'objectName', sortable: false },
        { text: this.$t('originalFilename'), value: 'originalFilename', sortable: false },
        { text: this.$t('fileSize'), value: 'size', sortable: false }
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
    onFileSelect (file) {
      if (!file) {
        return
      }
      if (file.size > 50 * 1024 * 1024) {
        this.$snackbar.error(this.$t('whatCannotGreaterThan', [this.$t('file'), '50MB']))
        return
      }
      this.fileOptions.uploading = true
      this.$apis.upms.file.upload({ file })
        .then(() => {
          this.fileOptions.uploading = false
          this.$refs.dataTable.getItems()
        })
        .catch(() => {
          this.fileOptions.uploading = false
        })
    },
    onItemUpdate (item) {
      this.editedItem = item
    },
    onDeleteItem ({ item, index }) {
      this.selectedItem = Object.assign({}, item)
      // 删除
      this.$apis.upms.file.delete(this.selectedItem.uuid)
        .then(() => {
          this.$refs.dataTable.deleteSuccessfully()
        })
        .catch(() => {
          this.$refs.dataTable.deleteFailed()
        })
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
      this.$apis.upms.file.list(offset, limit, this.searchParams)
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
