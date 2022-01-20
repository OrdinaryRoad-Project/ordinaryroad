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

import { baiduCountAllCode, baiduCountCode, baiduCountMobileArr } from './baidu'
import { cnzzCountAllCode, cnzzCountCode, cnzzCountMobileArr } from './cnzz'

export default ({ app: { router }, store }) => {
  // 每次路由变更时进行pv统计
  router.afterEach((to, from) => {
    // 加上try 不然会报错
    try {
      const city = to.params.city
      if (city !== '') {
        baiduCountMobileArr.forEach((value, index) => {
          if (value.city === city) {
            baiduCountCode(value.code)
          }
        })
        cnzzCountMobileArr.forEach((value, index) => {
          if (value.city === city) {
            cnzzCountCode(value.id, value.webId)
          }
        })
      }
      // 平台主域名统计
      baiduCountAllCode(baiduCountMobileArr[0].code)
      cnzzCountAllCode(cnzzCountMobileArr[0].id, cnzzCountMobileArr[0].webId)
    } catch (e) {
    }
  })
}
