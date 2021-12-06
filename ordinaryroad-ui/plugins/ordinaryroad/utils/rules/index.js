let $t = null

export default {
  init: (t) => {
    $t = $t || t
  },
  required: value => !!value || $t('required'),
  min6Chars: value => (!value || value.length >= 6) || $t('minNChars', [6]),
  max10Chars: value => value.length <= 10 || $t('maxNChars', [10]),
  max16Chars: value => value.length <= 16 || $t('maxNChars', [16]),
  max25chars: value => value.length <= 25 || $t('maxNChars', [25]),
  max100Chars: value => value.length <= 100 || $t('maxNChars', [100]),
  email: (value) => {
    const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return pattern.test(value) || $t('invalidEmail')
  },
  password: (value) => {
    const pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$/
    return pattern.test(value) || $t('invalidPassword')
  }
}
