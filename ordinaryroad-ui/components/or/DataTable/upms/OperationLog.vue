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
  <v-container>
    <or-base-data-table
      ref="dataTable"
      :sort-by="['createdTime']"
      :sort-desc="[true]"
      :single-select="singleSelect"
      :select-return-object="selectReturnObject"
      :show-select="showSelect"
      :show-base-headers-when-selecting="showBaseHeadersWhenSelecting"
      :show-actions-when-selecting="showActionsWhenSelecting"
      :show-update-headers="false"
      :preset-selected-items="presetSelectedItems"
      :table-headers="headers"
      access-key="upms:operation_log"
      @getItems="onGetItems"
      @deleteItem="onDeleteItem"
      @itemsSelected="onItemsSelected"
      @resetSearch="onResetSearch"
    >
      <template #searchFormBody>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-select
            v-model="searchParams.type"
            clearable
            dense
            hide-details
            :items="typeOptions.items"
            outlined
            :loading="typeOptions.loading"
            :label="$t('operationLog.type')"
            item-text="description"
            return-object
            @input="onTypeSelect"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-select
            v-model="searchParams.method"
            clearable
            dense
            hide-details
            :items="methodOptions.items"
            outlined
            :loading="methodOptions.loading"
            :label="$t('operationLog.method')"
            @input="onMethodSelect"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-select
            v-model="searchParams.status"
            clearable
            dense
            hide-details
            :items="statusOptions.items"
            outlined
            :loading="statusOptions.loading"
            :label="$t('operationLog.status')"
          />
        </v-col>
        <v-col
          cols="6"
          lg="3"
          md="4"
        >
          <v-text-field
            v-model="searchParams.createBy"
            outlined
            dense
            hide-details
            clearable
            :label="$t('createBy')"
          />
        </v-col>
      </template>
      <template #actionsTop>
        <span />
      </template>

      <template #[`item.type`]="{ item }">
        <v-chip label color="accent">
          {{ item.type.description }}
        </v-chip>
      </template>

      <template #[`item.method`]="{ item }">
        <v-chip label>
          {{ item.method }}
        </v-chip>
      </template>

      <template #[`item.status`]="{ item }">
        <v-chip label :color="statusColor(item)">
          {{ item.status }}
        </v-chip>
      </template>

      <template #[`item.consumedTime`]="{ item }">
        <v-chip label :color="consumedTimeColor(item)">
          {{ item.consumedTime }}
        </v-chip>
      </template>

      <template #actions="{ item }">
        <v-btn
          icon
          class="me-2"
          color="primary"
          @click="onViewLogDetail(item)"
        >
          <v-icon>mdi-eye</v-icon>
        </v-btn>
        <v-btn
          v-if="$access.has('upms:operation_log:delete')"
          icon
          color="error"
          @click="$refs.dataTable.deleteItem(item)"
        >
          <v-icon>mdi-delete-forever</v-icon>
        </v-btn>
      </template>
    </or-base-data-table>
    <or-base-dialog
      ref="operationLogDialog"
      :title="$t('operationLogForm.title')"
      @onConfirm="$refs.operationLogDialog.close()"
    >
      <or-form-upms-operation-log-detail
        :preset="selectedItem"
      />
    </or-base-dialog>
  </v-container>
</template>

<script>
export default {
  name: 'OrDataTableUpmsOperationLog',
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
    }
  },
  data: () => ({
    typeOptions: {
      loading: true,
      items: []
    },
    methodOptions: {
      loading: false,
      items: ['GET', 'POST', 'DELETE', 'PUT']
    },
    statusOptions: {
      loading: true,
      items: []
    },
    searchParams: {
      createBy: '',
      type: {},
      method: '',
      status: ''
    },
    selectedIndex: -1,
    selectedItem: {
      uuid: null,
      type: {}
    },
    defaultItem: {
      uuid: null,
      type: {}
    }
  }),
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      const headers = [
        { text: this.$t('operationLog.ip'), value: 'ip', width: 100 },
        { text: this.$t('operationLog.method'), value: 'method', width: 100 },
        { text: this.$t('operationLog.path'), value: 'path', width: 200 },
        { text: this.$t('operationLog.status'), value: 'status', width: 100 },
        { text: this.$t('operationLog.consumedTime'), value: 'consumedTime', width: 100 }
      ]
      return headers
    },
    statusColor () {
      return (item) => {
        return this.$apis.statusColor(item)
      }
    },
    consumedTimeColor () {
      return (item) => {
        if (item.consumedTime > 3000) {
          return 'warning'
        } else {
          return null
        }
      }
    }
  },
  watch: {},
  created () {
    this.findAllTypes()
    this.findAllStatus()
  },
  mounted () {
  },
  methods: {
    onMethodSelect (method) {
      if (method) {
        this.searchParams.type = null
      }
    },
    onTypeSelect (type) {
      if (type) {
        this.searchParams.method = type.method
      } else {
        this.searchParams.type = null
      }
    },
    findAllStatus () {
      this.$apis.upms.operation_log.findAllStatus()
        .then(({ data }) => {
          this.statusOptions.loading = false
          this.statusOptions.items = data
        })
        .catch(() => {
          this.statusOptions.loading = false
        })
    },
    findAllTypes () {
      this.$apis.upms.operation_log.findAllTypes()
        .then(({ data }) => {
          this.typeOptions.loading = false
          this.typeOptions.items = data
        })
        .catch(() => {
          this.typeOptions.loading = false
        })
    },
    onViewLogDetail (item) {
      // this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$refs.operationLogDialog.show()
    },
    onResetSearch () {
    },
    onDeleteItem ({ item, index }) {
      this.$apis.upms.operation_log.delete(item.uuid)
        .then(() => {
          this.$refs.dataTable.deleteSuccessfully()
        })
        .catch(() => {
          this.$refs.dataTable.deleteFailed()
        })
    },
    onGetItems ({
      options,
      offset,
      limit,
      sortBy,
      sortDesc
    }) {
      // 分页查询分类
      this.$apis.upms.operation_log.list(offset, limit, sortBy, sortDesc, {
        ...this.searchParams,
        type: this.searchParams.type ? this.searchParams.type.code : null
      })
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
      this.$refs.dataTable.select({
        item,
        value: false,
        emit: true
      })
    }
  }
}
</script>
