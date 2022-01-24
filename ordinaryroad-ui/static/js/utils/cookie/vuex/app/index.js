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

import Cookies from 'js-cookie'

export const SELECTED_THEME_OPTION_KEY = 'selectedThemeOption'
export const DRAWER_MINI_VARIANT_KEY = 'drawerMiniVariant'
export const DRAWER_CLIPPED_KEY = 'drawerClipped'

export function getDrawerMiniVariant () {
  return Cookies.get(DRAWER_MINI_VARIANT_KEY) === 'true'
}

export function setDrawerMiniVariant (value) {
  Cookies.set(DRAWER_MINI_VARIANT_KEY, value, { expires: 365 })
}

export function getDrawerClipped () {
  return Cookies.get(DRAWER_CLIPPED_KEY) === 'true'
}

export function setDrawerClipped (value) {
  Cookies.set(DRAWER_CLIPPED_KEY, value, { expires: 365 })
}

export function getSelectedThemeOption () {
  const fromCookie = Cookies.get(SELECTED_THEME_OPTION_KEY)
  return fromCookie ? Number(fromCookie) : undefined
}

export function setSelectedThemeOption (value) {
  Cookies.set(SELECTED_THEME_OPTION_KEY, value, { expires: 365 })
}
