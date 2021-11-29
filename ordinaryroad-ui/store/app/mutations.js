import colors from 'vuetify/es5/util/colors'

export default {
  SET_DRAWER_LEFT: (state, value) => (state.drawerLeft = value),
  SET_DRAWER_RIGHT: (state, value) => (state.drawerRight = value),
  SET_IMAGE: (state, value) => (state.image = value),
  SET_TITLE_KEY: (state, value) => (state.titleKey = value),
  SET_SELECTED_THEME_OPTION (state, { value, $vuetify }) {
    state.selectedThemeOption = value
    switch (value) {
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
        break
      default:
    }
  },
  SET_MENU_ITEMS: (state, value) => (state.menuItems = value),
  TOGGLE_DRAWER_LEFT: state => (state.drawerLeft = !state.drawerLeft),
  TOGGLE_DRAWER_RIGHT: state => (state.drawerRight = !state.drawerRight)
}
