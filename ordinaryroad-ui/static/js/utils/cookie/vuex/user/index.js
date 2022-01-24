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

export const REMEMBER_ME_KEY = 'rememberMe'
export const TOKEN_INFO_KEY = 'tokenInfo'

export function getRememberMe () {
  return Cookies.get(REMEMBER_ME_KEY) === 'true'
}

export function setRememberMe (value) {
  Cookies.set(REMEMBER_ME_KEY, value, { expires: 365 })
}

export function getTokenInfo () {
  const fromCookie = Cookies.get(TOKEN_INFO_KEY)
  return fromCookie ? JSON.parse(fromCookie) : undefined
}

export function setTokenInfo (tokenInfo) {
  Cookies.set(TOKEN_INFO_KEY, JSON.stringify(tokenInfo), {
    expires: getRememberMe() ? 30 : null
  })
}

export function removeTokenInfo () {
  Cookies.remove(TOKEN_INFO_KEY)
}
