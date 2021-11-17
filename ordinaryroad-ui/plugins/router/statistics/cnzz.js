export const cnzzCountMobileArr = [
  {
    city: '',
    id: '',
    webId: ''
  }
]

export function cnzzCountAllCode (id, webId) {
  (function () {
    // 会叠加 需要 每次执行前，先移除上次插入的代码
    document.getElementById('g_cnzz_1') && document.getElementById('g_cnzz_1').remove()
    const hm1 = document.createElement('script')
    hm1.src = `https://v1.cnzz.com/z_stat.php?id=${id}&web_id=${webId}`
    hm1.id = 'g_cnzz_1'
    document.getElementById('g_cnzz_2') && document.getElementById('g_cnzz_2').remove()
    const hm2 = document.createElement('script')
    hm2.src = `https://c.cnzz.com/core.php?web_id=${webId}&t=z`
    hm2.id = 'g_cnzz_2'
    const s = document.getElementsByTagName('script')[0]
    s.parentNode.insertBefore(hm1, s)
    s.parentNode.insertBefore(hm2, s)
  })()
}

export function cnzzCountCode (id, webId) {
  (function () {
    // 会叠加 需要 每次执行前，先移除上次插入的代码
    document.getElementById('w_cnzz_1') && document.getElementById('w_cnzz_1').remove()
    const hm1 = document.createElement('script')
    hm1.src = `https://v1.cnzz.com/z_stat.php?id=${id}&web_id=${webId}`
    hm1.id = 'w_cnzz_1'
    document.getElementById('w_cnzz_2') && document.getElementById('w_cnzz_2').remove()
    const hm2 = document.createElement('script')
    hm2.src = `https://c.cnzz.com/core.php?web_id=${webId}&t=z`
    hm2.id = 'w_cnzz_2'
    const s = document.getElementsByTagName('script')[0]
    s.parentNode.insertBefore(hm1, s)
    s.parentNode.insertBefore(hm2, s)
  })()
}
