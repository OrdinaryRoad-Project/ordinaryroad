import Cookies from 'js-cookie'

export const SELECTED_THEME_OPTION_KEY = 'selectedThemeOption'
export const DRAWER_MINI_VARIANT_KEY = 'drawerMiniVariant'
export const DRAWER_CLIPPED_KEY = 'drawerClipped'

export function getDrawerMiniVariant () {
  const fromCookie = Cookies.get(DRAWER_MINI_VARIANT_KEY)
  return fromCookie ? Boolean(fromCookie) : undefined
}

export function setDrawerMiniVariant (value) {
  Cookies.set(DRAWER_MINI_VARIANT_KEY, value, { expires: 365 })
}

export function getDrawerClipped () {
  const fromCookie = Cookies.get(DRAWER_CLIPPED_KEY)
  return fromCookie ? Boolean(fromCookie) : undefined
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
