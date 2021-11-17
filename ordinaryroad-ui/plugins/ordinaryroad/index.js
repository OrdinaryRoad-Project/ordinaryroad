import Vue from 'vue'
/* 自定义常量 */
import constants from './constants'
/* 自定义工具类 */
import util from './utils/util'

Vue.prototype.$constants = constants
Vue.prototype.$util = util
