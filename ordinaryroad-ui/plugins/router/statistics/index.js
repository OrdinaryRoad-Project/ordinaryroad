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
