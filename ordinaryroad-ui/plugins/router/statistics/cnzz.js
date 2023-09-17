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
