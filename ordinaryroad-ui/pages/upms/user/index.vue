<template>
  <div>
    <base-material-card
      :icon="`mdi-account`"
      :title="$t('menuTitles.userManagement')"
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
                  v-model="searchParams.email"
                  dense
                  outlined
                  clearable
                  hide-details="auto"
                  maxlength="10"
                  :label="$t('email')"
                />
              </v-col>
              <v-col
                cols="6"
                lg="3"
                md="4"
              >
                <v-text-field
                  v-model="searchParams.orNumber"
                  dense
                  outlined
                  clearable
                  hide-details="auto"
                  maxlength="10"
                  :label="$t('orNumber')"
                />
              </v-col>
              <v-col
                cols="6"
                lg="3"
                md="4"
              >
                <v-text-field
                  v-model="searchParams.username"
                  dense
                  outlined
                  clearable
                  hide-details="auto"
                  maxlength="10"
                  :label="$t('username')"
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
              class="mr-2"
              @click="deleteItem(item)"
            >
              mdi-delete-forever
            </v-icon>
            <v-menu
              offset-y
              open-on-hover
            >
              <template #activator="{ on, attrs }">
                <v-btn
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
                <v-list-item @click="resetItemPassword(item)">
                  <v-list-item-avatar size="16">
                    <v-icon small>
                      mdi-lock
                    </v-icon>
                  </v-list-item-avatar>
                  <v-list-item-title>{{ $t('resetPassword') }}</v-list-item-title>
                </v-list-item>
                <v-list-item @click="updateItemRoles(item)">
                  <v-list-item-avatar size="16">
                    <v-icon small>
                      mdi-account-multiple
                    </v-icon>
                  </v-list-item-avatar>
                  <v-list-item-title>{{ $t('userRolesManagement') }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </template>
      </v-data-table>
    </base-material-card>
    <or-base-dialog
      ref="userDialog"
      loading
      :title="$t(action+'What',[$t('user')])"
      @onConfirm="createOrUpdate"
    >
      <or-upms-user
        ref="userForm"
        :preset="selectedItem"
        @update="onItemUpdate"
      />
    </or-base-dialog>
    <or-base-dialog
      ref="resetPasswordDialog"
      loading
      :title="$t('resetPassword')"
      @onConfirm="resetPassword"
    >
      <v-form ref="resetPasswordForm">
        <v-text-field
          v-model="selectedItem.orNumber"
          readonly
          :label="$t('orNumber')"
        />
        <v-text-field
          v-model="editedItem.password"
          :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="[$rules.required,$rules.min6Chars,$rules.max16Chars,$rules.password]"
          :type="showPassword ? 'text' : 'password'"
          :label="$t('password')"
          @click:append="showPassword=!showPassword"
        />
      </v-form>
    </or-base-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      options: {},
      searchParams: {
        email: null,
        orNumber: null,
        username: null
      },
      dataTableParams: {
        loading: true,
        items: [],
        totalItems: 0
      },

      selectedIndex: -1,
      editedItem: {
        uuid: null,
        email: '',
        username: '',
        password: '',
        orNumber: null
      },
      selectedItem: {
        uuid: null,
        email: '',
        username: '',
        password: '',
        orNumber: null
      },
      defaultItem: {
        uuid: null,
        email: '',
        username: '',
        password: '',
        orNumber: null
      },

      showPassword: false
    }
  },
  head () {
    return {
      title: this.$t('menuTitles.userManagement')
    }
  },
  computed: {
    // 放在这为了支持国际化，如果放在data下切换语言不会更新
    headers () {
      return [
        { text: 'UUID', value: 'uuid', sortable: false },
        { text: this.$t('email'), value: 'email', sortable: false },
        { text: this.$t('orNumber'), value: 'orNumber', sortable: false },
        { text: this.$t('username'), value: 'username', sortable: false },
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
    updateItemRoles (item) {
      this.$router.push({ name: 'upms-user_roles-orNumber', params: { orNumber: item.orNumber, item } })
    },
    resetItemPassword (item) {
      this.showPassword = false
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.editedItem = Object.assign({}, this.defaultItem)
      this.$refs.resetPasswordDialog.show()
    },
    resetPassword () {
      if (this.$refs.resetPasswordForm.validate()) {
        // 调用API重置密码
        this.$apis.upms.user.resetPassword(this.selectedItem.uuid, this.editedItem.password)
          .then(() => {
            this.$refs.resetPasswordDialog.close()
            this.getItems()
          }).catch(() => {
            this.$refs.resetPasswordDialog.cancelLoading()
          })
      } else {
        this.$refs.resetPasswordDialog.cancelLoading()
      }
    },
    onItemUpdate (item) {
      this.editedItem = item
    },
    createOrUpdate () {
      if (this.$util.objectEquals(this.editedItem, this.selectedItem)) {
        this.$refs.userDialog.close()
        // 有变动才进行创建或更新
      } else if (this.$refs.userForm.validate()) {
        const action = this.selectedIndex === -1 ? 'create' : 'update'
        this.$apis.upms.user[action](this.editedItem)
          .then(() => {
            this.$refs.userDialog.close()
            this.getItems()
          })
          .catch(() => {
            // 取消loading
            this.$refs.userDialog.cancelLoading()
          })
      } else {
        // 取消loading
        this.$refs.userDialog.cancelLoading()
      }
    },
    insertItem () {
      this.selectedIndex = -1
      this.selectedItem = Object.assign({}, this.defaultItem)
      this.$refs.userDialog.show()
    },
    deleteItem (item) {
      this.selectedIndex = this.dataTableParams.items.indexOf(item)
      this.selectedItem = Object.assign({}, item)
      this.$dialog({
        content: this.$t('deleteDialog.content', [this.selectedItem.uuid]),
        loading: true
      }).then((dialog) => {
        // 删除
        this.$apis.upms.user.delete(this.selectedItem.uuid)
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
      this.$refs.userDialog.show()
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
      this.$apis.upms.user.list(
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
