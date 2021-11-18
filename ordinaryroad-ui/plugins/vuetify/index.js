// plugins/vuetify.js client mode
import Vue from 'vue'
import 'vuetify/dist/vuetify.min.css'
import { dialog, VDateTimePicker } from 'vuetify2-expand'
import Snackbar from 'vuetify2-expand/components/Snackbar'

export default ({ app, store }) => {
  const vuetify = app.vuetify
  // 在created里会报错，在mounted里用
  Vue.use(dialog, { vuetify })
  Vue.use({
    install (Vue, { vuetify, params }) {
      const SnackbarConstructor = Vue.extend(Snackbar)
      const instance = new SnackbarConstructor({ data: { params } })

      instance.$vuetify = vuetify.framework
      const vm = instance.$mount()
      const defaultSnackbar = ({ content, color, icon, showClose, top, bottom, timeout, onClose }) => {
        if (!document.getElementById('v-snackbar--box')) {
          document.getElementById('app').appendChild(vm.$el)
        }
        instance.execute({ content, color, icon, showClose, top, bottom, timeout, onClose }, vuetify.framework)
      }

      Vue.prototype.$snackbar = (content) => {
        defaultSnackbar({ content, showClose: true, top: true })
      }
      Vue.prototype.$snackbar.closeAll = instance.closeAll()
      // 自定义常用样式 info、success、warning、error TODO onClose
      Vue.prototype.$snackbar.info = (content) => {
        defaultSnackbar({ content, color: 'info', icon: 'mdi-information', showClose: true, top: true })
      }
      Vue.prototype.$snackbar.success = (content) => {
        defaultSnackbar({ content, color: 'success', icon: 'mdi-check-circle', showClose: true, top: true })
      }
      Vue.prototype.$snackbar.warning = (content) => {
        defaultSnackbar({ content, color: 'warning', showClose: true, icon: 'mdi-alert-circle' })
      }
      Vue.prototype.$snackbar.error = (content) => {
        defaultSnackbar({
          content, color: 'error', icon: 'mdi-close-circle', showClose: true, bottom: true, timeout: 10000
        })
      }
    }
  }, { vuetify, params: { top: true, showClose: true } })
  // 全局引入 VDateTimePicker 组件
  Vue.use(VDateTimePicker)
}
