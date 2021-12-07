<template>
  <div>
    <base-material-card
      :icon="`mdi-account`"
      :title="$t('menuTitles.roleManagement')"
    >
      <v-data-table
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
      >
        <template #top>
          <v-form ref="searchForm">
            <v-row align="center">
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

        <template #[`item.createdTime`]="{ item }">
          {{ $dayjs(item.createdTime).format() }}
        </template>

        <template #[`item.updateTime`]="{ item }">
          {{ $dayjs(item.updateTime).format() }}
        </template>

        <template #[`item.actions`]="{ item }">
          <div
            :class="$vuetify.breakpoint.xs?''
              :$vuetify.theme.dark ?'v-sheet theme--dark elevation-1'
                :'v-sheet theme--light elevation-1'"
          >
            <v-icon
              color="accent"
              class="mr-2"
              @click="editItem(item)"
            >
              mdi-pencil
            </v-icon>
            <v-icon
              color="error"
              @click="deleteItem(item)"
            >
              mdi-delete-forever
            </v-icon>
          </div>
        </template>
      </v-data-table>
    </base-material-card>
    <or-base-dialog
      ref="roleDialog"
      loading
      :title="$t(action+'What',[$t('role')])"
      @onConfirm="createOrUpdate"
    >
      <or-upms-role
        ref="roleForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      options: {},
      searchParams: {
        roleName: null,
        roleCode: null
      },
      dataTableParams: {
        loading: true,
        items: [],
        totalItems: 0
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
  head () {
    return {
      title: this.$t('menuTitles.roleManagement')
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: 'UUID', value: 'uuid', sortable: false },
        { text: this.$t('roleName'), value: 'roleName', sortable: false },
        { text: this.$t('roleCode'), value: 'roleCode', sortable: false },
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
      ]
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
            this.getItems()
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
    insertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.roleDialog.show()
    },
    deleteItem (item) {
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$dialog({
        content: this.$t('deleteDialog.content', [this.selectedItem.uuid]),
        loading: true
      }).then((dialog) => {
        // 删除
        this.$apis.upms.role.delete(this.selectedItem.uuid)
          .then(() => {
            // 手动关闭对话框
            this.getItems()
            dialog.cancel()
            this.$snackbar.success(this.$t('deleteSuccess'))
          })
          .catch(() => {
            dialog.cancel()
          })
      })
    },
    editItem (item) {
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$refs.roleDialog.show()
    },
    getItems () {
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
      const options = this.options
      this.dataTableParams.loading = true
      this.$apis.upms.role.list(
        options.page === 1 ? 0 : this.dataTableParams.items.length,
        options.itemsPerPage,
        this.searchParams
      ).then(({ data }) => {
        this.dataTableParams = {
          loading: false,
          items: data.list,
          totalItems: data.total
        }
      }).catch(() => {
        this.dataTableParams.loading = false
      })
    },
    resetSearch () {
      this.$refs.searchForm.reset()
      this.options = { page: 1 }
    }
  }
}
</script>
