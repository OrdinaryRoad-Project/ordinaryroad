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

import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)
export default ({ app, store }) => {
  // Set i18n instance on app
  // This way we can use it in middleware and pages asyncData/fetch
  const enOr = require('ordinaryroad-vuetify/src/locales/en.json')
  const zhHansOr = require('ordinaryroad-vuetify/src/locales/zh-Hans.json')

  const enApp = require('~/locales/en.json')
  const zhHansApp = require('~/locales/zh-Hans.json')

  const en = {
    ...enOr,
    ...enApp
  }
  const zhHans = {
    ...zhHansOr,
    ...zhHansApp
  }
  const keysApp = Object.keys(zhHansApp)
  for (const key in enOr) {
    if (keysApp.includes(key)) {
      console.warn('locales key有冲突', key)
    }
  }

  app.i18n = new VueI18n({
    locale: store.getters['i18n/getLocale'],
    fallbackLocale: 'zh-Hans',
    messages: {
      en,
      'zh-Hans': zhHans
    }
  })
  app.i18n.path = (link) => {
    if (app.i18n.locale === app.i18n.fallbackLocale) {
      return `/${link}`
    }
    return `/${app.i18n.locale}/${link}`
  }
  app.i18n.$t = function (key, array) {
    let message = app.i18n.messages[app.i18n.locale][key]
    if (array) {
      for (let i = 0; i < array.length; i++) {
        message = message.replace(`{${i}}`, array[i])
      }
    }
    return message
  }
}
