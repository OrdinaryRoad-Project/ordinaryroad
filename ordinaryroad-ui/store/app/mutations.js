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
