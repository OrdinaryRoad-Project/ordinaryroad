import { setDrawerClipped, setDrawerMiniVariant, setSelectedThemeOption } from 'static/js/utils/cookie/vuex/app'
import { updateTheme } from 'static/js/utils/vuetify'

export default {
  SET_DRAWER_LEFT: (state, value) => (state.drawerLeft = value),
  SET_DRAWER_RIGHT: (state, value) => (state.drawerRight = value),
  SET_IMAGE: (state, value) => (state.image = value),
  SET_DRAWER_MINI_VARIANT: (state, value) => {
    state.drawerMiniVariant = value
    setDrawerMiniVariant(value)
  },
  SET_DRAWER_CLIPPED: (state, value) => {
    state.drawerClipped = value
    setDrawerClipped(value)
  },
  SET_TITLE_KEY: (state, value) => (state.titleKey = value),
  SET_SELECTED_THEME_OPTION (state, { value, $vuetify }) {
    state.selectedThemeOption = value
    setSelectedThemeOption(value)
    updateTheme(value, $vuetify)
  },
  SET_MENU_ITEMS: (state, value) => (state.menuItems = value),
  TOGGLE_DRAWER_MINI_VARIANT: (state) => {
    state.drawerMiniVariant = !state.drawerMiniVariant
    setDrawerMiniVariant(state.drawerMiniVariant)
  },
  TOGGLE_DRAWER_CLIPPED: (state) => {
    state.drawerClipped = !state.drawerClipped
    setDrawerClipped(state.drawerClipped)
  },
  TOGGLE_DRAWER_LEFT: state => (state.drawerLeft = !state.drawerLeft),
  TOGGLE_DRAWER_RIGHT: state => (state.drawerRight = !state.drawerRight)
}
