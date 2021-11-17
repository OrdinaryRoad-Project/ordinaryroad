// plugins/vuetify.js client mode
import Vue from 'vue'
import 'vuetify/dist/vuetify.min.css'
import { dialog, snackbar, VDateTimePicker } from 'vuetify2-expand'

export default ({ app, store }) => {
  const vuetify = app.vuetify
  // 在created里会报错，在mounted里用
  Vue.use(dialog, { vuetify })
  Vue.use(snackbar, { vuetify })
  // 全局引入 VDateTimePicker 组件
  Vue.use(VDateTimePicker)
}
