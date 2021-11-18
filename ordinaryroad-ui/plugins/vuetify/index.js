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
        if (typeof content === 'object') {
          onClose = content.onClose
          content = content.content
        }
        instance.execute({ content, color, icon, showClose, top, bottom, timeout, onClose }, vuetify.framework)
      }

      Vue.prototype.$snackbar = (params) => {
        defaultSnackbar({ content: params, showClose: true, top: true })
      }
      Vue.prototype.$snackbar.closeAll = instance.closeAll()
      /*
      自定义常用样式 info、success、warning、error
      调用方式：
          this.$snackbar('默认')
          this.$snackbar.info('info')
          this.$snackbar.success('success')
          this.$snackbar.warning('warning')
          this.$snackbar.error('error')
      调用方式：
         this.$snackbar({
            content: '默认',
            onClose: () => {
              alert('默认onClose回调')
            }
        })
       */
      Vue.prototype.$snackbar.info = (params) => {
        defaultSnackbar({ content: params, color: 'info', icon: 'mdi-information', showClose: true, top: true })
      }
      Vue.prototype.$snackbar.success = (params) => {
        defaultSnackbar({ content: params, color: 'success', icon: 'mdi-check-circle', showClose: true, top: true })
      }
      Vue.prototype.$snackbar.warning = (params) => {
        defaultSnackbar({ content: params, color: 'warning', showClose: true, icon: 'mdi-alert-circle' })
      }
      Vue.prototype.$snackbar.error = (params) => {
        defaultSnackbar({
          content: params, color: 'error', icon: 'mdi-close-circle', showClose: true, bottom: true, timeout: 10000
        })
      }
    }
  }, { vuetify, params: { top: true, showClose: true } })
  // 全局引入 VDateTimePicker 组件
  Vue.use(VDateTimePicker)
}
