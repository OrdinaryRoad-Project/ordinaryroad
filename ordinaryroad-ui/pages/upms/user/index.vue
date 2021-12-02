<template>
  <v-container
    id="regular-tables"
    fluid
    tag="section"
  >
    <base-material-card
      :icon="`mdi-account`"
      :title="$t('menuTitles.userManagement')"
      class="px-5 py-3"
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
          <v-form
            ref="searchForm"
          >
            <v-row align="center">
              <v-col
                cols="12"
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
                cols="12"
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
        </template>

        <template #[`item.username`]="props">
          <v-edit-dialog
            :return-value.sync="props.item.username"
            large
            persistent
            @save="save"
            @cancel="cancel"
            @open="open"
            @close="close"
          >
            {{ props.item.username }}
            <template #input>
              <v-text-field
                v-model="props.item.username"
                :rules="[$rules.required, $rules.max25chars]"
                label="Edit"
                single-line
                counter
              />
            </template>
          </v-edit-dialog>
        </template>

        <template #[`item.actions`]="{ item }">
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
        </template>
      </v-data-table>
    </base-material-card>
  </v-container>
</template>

<script>
export default {
  data () {
    return {
      options: {},
      searchParams: {
        username: null
      },
      dataTableParams: {
        loading: true,
        items: [],
        totalItems: 0
      },
      editedIndex: -1,
      editedItem: {
        uuid: '',
        username: '',
        orNumber: null
      },
      defaultItem: {
        uuid: '',
        username: '',
        orNumber: null
      }
    }
  },
  head () {
    return {
      title: this.$t('menuTitles.userManagement')
    }
  },
  computed: {
    // 放在这为了支持国际化
    headers: (vm) => {
      return [
        { text: 'UUID', value: 'uuid', sortable: false },
        { text: vm.$t('email'), value: 'email', sortable: false },
        { text: vm.$t('orNumber'), value: 'orNumber', sortable: false },
        { text: vm.$t('username'), value: 'username', sortable: false },
        { text: vm.$t('createdTime'), value: 'createdTime', sortable: false },
        { text: vm.$t('createBy'), value: 'createBy', sortable: false },
        { text: vm.$t('updateTime'), value: 'updateTime', sortable: false },
        { text: vm.$t('updateBy'), value: 'updateBy', sortable: false },
        { text: vm.$t('dataTable.actions'), value: 'actions', sortable: false }
      ]
    }
  },
  watch: {
    options: {
      handler () {
        this.getItems()
      },
      deep: true
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    insertItem () {
    },
    deleteItem (item) {
      this.editedIndex = this.dataTableParams.items.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.$dialog({
        content: this.$t('deleteDialog.content', [this.editedItem.uuid]),
        loading: true
      }).then((dialog) => {
        // TODO 删除
        // setTimeout 模拟接口请求的响应时间
        setTimeout(() => {
          // 手动关闭对话框
          this.getItems()
          dialog.cancel()
          this.$snackbar.success(this.$t('deleteSuccess'))
        }, 1000)
      })
    },
    editItem (item) {
      this.editedIndex = this.dataTableParams.items.indexOf(item)
      this.editedItem = Object.assign({}, item)
      // TODO 编辑对话框
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
      console.log(options)
      this.dataTableParams.loading = true
      this.$apis.upms.user.list(
        options.page === 1 ? 0 : this.dataTableParams.items.length,
        options.itemsPerPage,
        this.searchParams.username
      ).then(({ data }) => {
        console.log(data)
        this.dataTableParams = {
          loading: false,
          items: data.list,
          totalItems: data.total
        }
      }).catch((reason) => {
        this.dataTableParams.loading = false
      })
    },
    resetSearch () {
      this.$refs.searchForm.reset()
      this.options = { page: 1 }
    },
    save () {
      this.$snackbar.success('Data saved')
    },
    cancel () {
      this.$snackbar.error('Canceled')
    },
    open () {
      this.$snackbar.info('Dialog opened')
    },
    close () {
      this.$snackbar.info('Dialog closed')
    }
  }
}
</script>

<style scoped>

</style>
