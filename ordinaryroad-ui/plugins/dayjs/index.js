// plugins/day.js
import LocalizedFormat from 'dayjs/plugin/localizedFormat'
// 默认格式化方法
import FormatTime from './plugin/formatTime'

export default ({ app }) => {
  const dayjs = app.$dayjs
  dayjs.extend(LocalizedFormat)
  dayjs.extend(FormatTime)
}
