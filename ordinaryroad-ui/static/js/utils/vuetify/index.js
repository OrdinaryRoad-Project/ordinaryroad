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
