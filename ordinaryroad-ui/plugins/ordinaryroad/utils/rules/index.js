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

let $t = null

export default {
  init: (t) => {
    $t = $t || t
  },
  required: value => !!value || $t('required'),
  notBlank: value => (!!value && String(value).length > 0) || $t('required'),
  min6Chars: value => (!value || String(value).length >= 6) || $t('minNChars', [6]),
  max10Chars: value => (!value || String(value).length <= 10) || $t('maxNChars', [10]),
  max11Chars: value => (!value || String(value).length <= 11) || $t('maxNChars', [11]),
  max16Chars: value => (!value || String(value).length <= 16) || $t('maxNChars', [16]),
  max20Chars: value => (!value || String(value).length <= 20) || $t('maxNChars', [20]),
  max25Chars: value => (!value || String(value).length <= 25) || $t('maxNChars', [25]),
  max32Chars: value => (!value || String(value).length <= 32) || $t('maxNChars', [32]),
  max50Chars: value => (!value || String(value).length <= 50) || $t('maxNChars', [50]),
  max100Chars: value => (!value || String(value).length <= 100) || $t('maxNChars', [100]),
  max200Chars: value => (!value || String(value).length <= 200) || $t('maxNChars', [200]),
  max1000Chars: value => (!value || String(value).length <= 1000) || $t('maxNChars', [1000]),
  max10Size: value => (!value || value.length <= 10) || $t('maxNSize', [10]),
  email: (value) => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || $t('invalidEmail')
  },
  password: (value) => {
    const pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$/
    return !value || pattern.test(value) || $t('invalidPassword')
  }
}
