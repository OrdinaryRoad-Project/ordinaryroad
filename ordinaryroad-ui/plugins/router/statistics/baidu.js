export const baiduCountMobileArr = [
  {
    city: '',
    code: ''
  }
]

export function baiduCountAllCode (code) {
  (function () {
    // 会叠加 需要 每次执行前，先移除上次插入的代码
    document.getElementById('g_baidu') && document.getElementById('g_baidu').remove()
    const hm = document.createElement('script')
    hm.src = 'https://hm.baidu.com/hm.js?' + code
    hm.id = 'g_baidu'
    const s = document.getElementsByTagName('script')[0]
    s.parentNode.insertBefore(hm, s)
  })()
}

export function baiduCountCode (code) {
  (function () {
    // 会叠加 需要 每次执行前，先移除上次插入的代码
    document.getElementById('w_baidu') && document.getElementById('w_baidu').remove()
    const hm = document.createElement('script')
    hm.src = 'https://hm.baidu.com/hm.js?' + code
    hm.id = 'w_baidu'
    const s = document.getElementsByTagName('script')[0]
    s.parentNode.insertBefore(hm, s)
  })()
}
