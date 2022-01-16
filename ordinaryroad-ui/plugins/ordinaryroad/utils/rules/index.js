let $t = null

export default {
  init: (t) => {
    $t = $t || t
  },
  required: value => !!value || $t('required'),
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
  email: (value) => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || $t('invalidEmail')
  },
  password: (value) => {
    const pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$/
    return pattern.test(value) || $t('invalidPassword')
  }
}
