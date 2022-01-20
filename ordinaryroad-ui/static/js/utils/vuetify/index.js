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

import colors from 'vuetify/es5/util/colors'

/**
 * 更新主题
 * @param index 主题下标
 * @param $vuetify $vuetify
 */
export function updateTheme (index, $vuetify) {
  switch (index) {
    case 0:
      $vuetify.theme.themes.light = {
        primary: '#1976D2',
        secondary: '#424242',
        accent: '#82B1FF',
        error: '#FF5252',
        info: '#2196F3',
        success: '#4CAF50',
        warning: '#FFC107'
      }
      $vuetify.theme.dark = false
      break
    case 1:
      $vuetify.theme.themes.dark = {
        primary: colors.blue.darken2,
        accent: colors.grey.darken3,
        secondary: colors.amber.darken3,
        info: colors.teal.lighten1,
        warning: colors.amber.base,
        error: colors.deepOrange.accent4,
        success: colors.green.accent3
      }
      $vuetify.theme.dark = true
      break
    case 2:
      $vuetify.theme.themes.light = {
        primary: colors.pink.base,
        secondary: colors.lime.base,
        accent: colors.cyan.base,
        error: colors.red.base,
        warning: colors.yellow.base,
        info: colors.blue.base,
        success: colors.green.base
      }
      $vuetify.theme.dark = false
      break
    case 3:
      $vuetify.theme.themes.dark = {
        primary: colors.pink.base,
        secondary: colors.lime.base,
        accent: colors.cyan.base,
        error: colors.red.base,
        warning: colors.yellow.base,
        info: colors.blue.base,
        success: colors.green.base
      }
      $vuetify.theme.dark = true
      break
    default:
  }
}
