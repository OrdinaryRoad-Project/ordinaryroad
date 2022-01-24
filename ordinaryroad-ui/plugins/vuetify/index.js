/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// plugins/vuetify.js client mode
import Vue from 'vue'
import 'vuetify/dist/vuetify.min.css'
import { VDateTimePicker } from 'vuetify2-expand'
import Snackbar from 'vuetify2-expand/components/Snackbar'
import Dialog from '~/components/expand/Dialog'

export default ({ app, store }, inject) => {
  const vuetify = app.vuetify
  const i18n = app.i18n
  // 在created里会报错，在mounted里用
  Vue.use((Vue, { vuetify, i18n, params = {} }) => {
    Vue.prototype.$dialog = function (arg) {
      return new Promise((resolve) => {
        const DialogConstructor = Vue.extend(Dialog)

        const instanceDialog = new DialogConstructor({
          data: {
            params: Object.assign({}, params)
          },
          updated () {
            this.loading && resolve(this)
          },
          beforeDestroy () {
            this.$el.remove()
            this.isConfirm && resolve(this)
          }
        })

        instanceDialog.$vuetify = vuetify.framework
        instanceDialog.i18n = i18n
        const vmDialog = instanceDialog.$mount()

        document.getElementById('app').appendChild(vmDialog.$el)
        instanceDialog.execute(arg, vuetify.framework)
      })
    }
  }, { vuetify, i18n })
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

      Vue.prototype.$snackbar = (content, onClose) => {
        defaultSnackbar({ content, showClose: true, top: true, onClose })
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
        this.$snackbar('默认onClose回调', () => alert('默认onClose回调'))
        this.$snackbar.info('info onClose回调', () => alert('info onClose回调'))
       */
      Vue.prototype.$snackbar.info = (content, onClose) => {
        defaultSnackbar({ content, color: 'info', icon: 'mdi-information', showClose: true, top: true, onClose })
      }
      Vue.prototype.$snackbar.success = (content, onClose) => {
        defaultSnackbar({ content, color: 'success', icon: 'mdi-check-circle', showClose: true, top: true, onClose })
      }
      Vue.prototype.$snackbar.warning = (content, onClose) => {
        defaultSnackbar({ content, color: 'warning', showClose: true, icon: 'mdi-alert-circle', onClose })
      }
      Vue.prototype.$snackbar.error = (content, onClose) => {
        defaultSnackbar({
          content, color: 'error', icon: 'mdi-close-circle', showClose: true, bottom: true, timeout: 10000, onClose
        })
      }
    }
  }, { vuetify, params: { top: true, showClose: true } })
  // 全局引入 VDateTimePicker 组件
  Vue.use(VDateTimePicker)

  inject('dialog', Vue.prototype.$dialog)
  inject('snackbar', Vue.prototype.$snackbar)
}
