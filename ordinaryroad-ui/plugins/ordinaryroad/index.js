import Vue from 'vue'
/* 自定义常量 */
import constants from './constants'
/* 自定义工具类 */
import util from './utils/util'
import rules from './utils/rules/index'

Vue.prototype.$constants = constants
Vue.prototype.$util = util

export default function (context, inject) {
  const { app } = context
  const i18n = app.i18n
  rules.init(i18n.$t)

  // 将自定义form rules注入nuxt上下文 $rules
  inject('rules', rules)
}
