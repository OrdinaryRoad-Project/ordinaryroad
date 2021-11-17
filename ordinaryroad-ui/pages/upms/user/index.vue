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
      <div>
        <v-data-table
          :headers="headers"
          :items="desserts"
        >
          <template #[`item.name`]="props">
            <v-edit-dialog
              :return-value.sync="props.item.name"
              @save="save"
              @cancel="cancel"
              @open="open"
              @close="close"
            >
              {{ props.item.name }}
              <template #input>
                <v-text-field
                  v-model="props.item.name"
                  :rules="[max25chars]"
                  label="Edit"
                  single-line
                  counter
                />
              </template>
            </v-edit-dialog>
          </template>
          <template #[`item.iron`]="props">
            <v-edit-dialog
              :return-value.sync="props.item.iron"
              large
              persistent
              @save="save"
              @cancel="cancel"
              @open="open"
              @close="close"
            >
              <div>{{ props.item.iron }}</div>
              <template #input>
                <div class="mt-4 text-h6">
                  Update Iron
                </div>
                <v-text-field
                  v-model="props.item.iron"
                  :rules="[max25chars]"
                  label="Edit"
                  single-line
                  counter
                  autofocus
                />
              </template>
            </v-edit-dialog>
          </template>
        </v-data-table>

        <v-snackbar
          v-model="snack"
          :timeout="3000"
          :color="snackColor"
        >
          {{ snackText }}

          <template #action="{ attrs }">
            <v-btn
              v-bind="attrs"
              text
              @click="snack = false"
            >
              Close
            </v-btn>
          </template>
        </v-snackbar>
      </div>
    </base-material-card>
  </v-container>
</template>

<script>
export default {
  data () {
    return {
      snack: false,
      snackColor: '',
      snackText: '',
      max25chars: v => v.length <= 25 || 'Input too long!',
      pagination: {},
      headers: [
        {
          text: 'Dessert (100g serving)',
          align: 'start',
          sortable: false,
          value: 'name'
        },
        { text: 'Calories', value: 'calories' },
        { text: 'Fat (g)', value: 'fat' },
        { text: 'Carbs (g)', value: 'carbs' },
        { text: 'Protein (g)', value: 'protein' },
        { text: 'Iron (%)', value: 'iron' }
      ],
      desserts: [
        {
          name: 'Frozen Yogurt',
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: '1%'
        },
        {
          name: 'Ice cream sandwich',
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: '1%'
        },
        {
          name: 'Eclair',
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: '7%'
        },
        {
          name: 'Cupcake',
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: '8%'
        },
        {
          name: 'Gingerbread',
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: '16%'
        },
        {
          name: 'Jelly bean',
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: '0%'
        },
        {
          name: 'Lollipop',
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: '2%'
        },
        {
          name: 'Honeycomb',
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: '45%'
        },
        {
          name: 'Donut',
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: '22%'
        },
        {
          name: 'KitKat',
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: '6%'
        }
      ]
    }
  },
  head () {
    return {
      title: this.$t('menuTitles.userManagement')
    }
  },
  created () {
  },
  mounted () {
    this.$snackbar('这是一段消息文本，默认所有风格')
    this.$dialog('确定要删除这条数据吗？')
    console.log(this.$util.uuid())
  },
  methods: {
    test () {
      this.$apis.user.list(0, 10).then((value) => {
        console.log(value)
      }).catch((reason) => {
        console.log(reason)
      })
    },
    save () {
      this.snack = true
      this.snackColor = 'success'
      this.snackText = 'Data saved'
    },
    cancel () {
      this.snack = true
      this.snackColor = 'error'
      this.snackText = 'Canceled'
    },
    open () {
      this.snack = true
      this.snackColor = 'info'
      this.snackText = 'Dialog opened'
    },
    close () {
      console.log('Dialog closed')
    }
  }
}
</script>

<style scoped>

</style>
