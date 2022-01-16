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
  SET_ACCESSIBLE_MENU_ITEMS: (state, value) => (state.accessibleMenuItems = value),
  UPDATE_ACCESSIBLE_MENU_ITEMS: (state, $access) => {
    const menuItems = state.menuItems
    const accessibleMenuItems = []
    menuItems.forEach((item) => {
      if (item.children && item.children.length > 0) {
        const menuItems1 = []
        item.children.forEach((item1) => {
          if (item1.children && item1.children.length > 0) {
            const menuItems2 = []
            item1.children.forEach((item2) => {
              if (!item2.meta || !item2.meta.permission || $access.has(item2.meta.permission)) {
                menuItems2.push(item2)
              }
            })
            if (menuItems2.length > 0) {
              item1.children = menuItems2
              menuItems1.push(item1)
            }
          } else if (!item1.meta || !item1.meta.permission || $access.has(item1.meta.permission)) {
            menuItems1.push(item1)
          }
        })
        if (menuItems1.length > 0) {
          item.children = menuItems1
          accessibleMenuItems.push(item)
        }
      } else if (!item.meta || !item.meta.permission || $access.has(item.meta.permission)) {
        accessibleMenuItems.push(item)
      }
    })
    state.accessibleMenuItems = accessibleMenuItems
  },
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
