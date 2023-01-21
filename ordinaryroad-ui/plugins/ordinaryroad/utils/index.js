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

function formatTime (timeStamp) {
  const date = new Date(timeStamp)
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()
  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

/**
 * 格式化秒
 * @param  value int 总秒数
 * @return result string 格式化后的字符串
 */
function formatSeconds (value) {
  let theTime = parseInt(value)// 需要转换的时间秒
  let theTime1 = 0// 分
  let theTime2 = 0// 小时
  let theTime3 = 0// 天
  if (theTime > 60) {
    theTime1 = parseInt(String(theTime / 60))
    theTime = parseInt(String(theTime % 60))
    if (theTime1 > 60) {
      theTime2 = parseInt(String(theTime1 / 60))
      theTime1 = parseInt(String(theTime1 % 60))
      if (theTime2 > 24) {
        // 大于24小时
        theTime3 = parseInt(String(theTime2 / 24))
        theTime2 = parseInt(String(theTime2 % 24))
      }
    }
  }
  let result = ''
  if (theTime > 0) {
    result = '' + parseInt(String(theTime)) + '秒'
  }
  if (theTime1 > 0) {
    result = '' + parseInt(String(theTime1)) + '分' + result
  }
  if (theTime2 > 0) {
    result = '' + parseInt(String(theTime2)) + '小时' + result
  }
  if (theTime3 > 0) {
    result = '' + parseInt(String(theTime3)) + '天' + result
  }
  return result
}

function formatNumber (n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

/**
 * 数据分页
 * @param jsonList 列表
 * @param start 开始的位置
 * @param count 个数
 */
function limit (jsonList, start, count) {
  return jsonList.filter((ele, index) => {
    return index >= start && index <= start + count - 1
  })
}

/**
 * 数据精确筛选
 *  @param jsonList 列表
 * @param propertyName 属性名
 * @param value 想要的的值
 */
function query (jsonList, propertyName, value) {
  return jsonList.filter((ele) => {
    return ele[propertyName] === value
  })
}

/**
 * 数据精确筛选
 * @param jsonList 列表
 * @param propertyName 属性名
 * @param value 不要的值
 */
function remove (jsonList, propertyName, value) {
  return jsonList.filter((ele) => {
    return ele[propertyName] !== value
  })
}

/**
 * 数据模糊筛选
 * @param jsonList 列表
 * @param propertyName 属性名
 * @param value 包含的值
 */
function contain (jsonList, propertyName, value) {
  return jsonList.filter((ele) => {
    return ele[propertyName].includes(value)
  })
}

/**
 * 时间戳转化为年 月 日 时 分 秒
 * number: 传入时间戳
 * format：返回格式，支持自定义，但参数必须与formateArr里保持一致
 */
function formatTimeTwo (number, format) {
  const formateArr = ['Y', 'M', 'D', 'h', 'm', 's']
  const returnArr = []

  const date = new Date(number * 1000)
  returnArr.push(date.getFullYear())
  returnArr.push(formatNumber(date.getMonth() + 1))
  returnArr.push(formatNumber(date.getDate()))

  returnArr.push(formatNumber(date.getHours()))
  returnArr.push(formatNumber(date.getMinutes()))
  returnArr.push(formatNumber(date.getSeconds()))

  for (const i in returnArr) {
    format = format.replace(formateArr[i], returnArr[i])
  }
  return format
}

/**
 * 已知两个时间戳
 * 计算两个时间差
 */
function diffTime (startDate, endDate) {
  // 时间差的毫秒数
  const diff = endDate - startDate
  // 计算出相差天数
  const days = Math.floor(diff / (24 * 3600 * 1000))
  // 计算天数后剩余的毫秒数
  const leave1 = diff % (24 * 3600 * 1000)
  // 计算出小时数
  const hours = Math.floor(leave1 / (3600 * 1000))
  // 计算小时数后剩余的毫秒数
  const leave2 = leave1 % (3600 * 1000)
  // 计算相差分钟数
  const minutes = Math.floor(leave2 / (60 * 1000))
  // 计算分钟数后剩余的毫秒数
  const leave3 = leave2 % (60 * 1000)
  // 计算相差秒数
  const seconds = Math.round(leave3 / 1000)
  return { days, hours, minutes, seconds }
}

function uuid () {
  const s = []
  const hexDigits = '0123456789abcdef'
  for (let i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
  }
  s[12] = '4' // bits 12-15 of the time_hi_and_version field to 0010
  s[16] = hexDigits.substr((s[16] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
  return s.join('')
}

/**
 * 对比两个对象的值是否完全相等
 * @param a 对象A
 * @param b 对象B
 * @returns {boolean}
 */
function objectEquals (a, b) {
  // falsy value, return
  if (!a && !b) {
    return true
  } else if ((!a && b) || (!b && a)) {
    return false
  }
  // 取对象a和b的属性名
  const aProps = Object.keys(a)
  const bProps = Object.keys(b)
  // 判断属性名的length是否一致
  if (aProps.length !== bProps.length) {
    return false
  }
  // 排除这种情况： { a: 1, b: [] }, { a: 1, b: {} }
  if (aProps.length === 0) {
    return a === b
  }
  aProps.sort()
  // 循环取出属性名，再判断属性值是否一致
  for (let i = 0; i < aProps.length; i++) {
    const propName = aProps[i]
    if (a[propName] instanceof Array && b[propName] instanceof Array) {
      if (!arrayEquals(a[propName], b[propName])) {
        return false
      }
    } else if (a[propName] !== b[propName]) {
      return false
    }
  }
  return true
}

/**
 * 比较两个数组内容是否一样，元素顺序无关
 * @param a Array A
 * @param b Array B
 * @returns {boolean}
 */
function arrayEquals (a, b) {
  // falsy value, return
  if (!a && !b) {
    return true
  } else if ((!a && b) || (!b && a)) {
    return false
  }

  // compare lengths - can save a lot of time
  if (a.length !== b.length) {
    return false
  }

  a.sort()
  b.sort()

  for (let i = 0, l = a.length; i < l; i++) {
    // Check if we have nested arrays
    if (a[i] instanceof Array && b[i] instanceof Array) {
      if (!arrayEquals(a[i], b[i])) {
        return false
      }
    } else if (!objectEquals(a[i], b[i])) {
      return false
    }
  }
  return true
}

/**
 * 根据item内容获取index
 * @param array Array
 * @param searchItem Item
 * @returns {number} 下标，不存在返回-1
 */
function indexOf (array, searchItem) {
  for (let i = 0; i < array.length; i++) {
    const item = array[i]
    if (objectEquals(item, searchItem)) {
      return i
    }
  }
  return -1
}

function getFileSizeString (size) {
  if (size < 1024) {
    return size + ' BYTE'
  } else {
    size /= 1024
  }
  if (size < 1024) {
    return size.toFixed(2) + ' KB'
  } else {
    size /= 1024
  }
  if (size < 1024) {
    return size.toFixed(2) + ' MB'
  } else {
    size /= 1024
  }
  if (size < 1024) {
    return size.toFixed(2) + ' GB'
  } else {
    size /= 1024
  }
  return size.toFixed(2) + ' TB'
}

/** 将对象转换为url参数形式，数组类型不带下标[i]
 * @param {Object} param 将要转换为URL参数的字符串对象
 * @param {String} key URL 参数字符串的前缀
 * @param {Boolean} encode 是否进行URL编码，默认为true
 * @return {String} URL参数字符串
 */
function urlEncode (param, key = null, encode = true) {
  if (param == null) {
    return ''
  }
  let paramStr = ''
  const t = typeof (param)
  if (t === 'string' || t === 'number' || t === 'boolean') {
    paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param)
  } else {
    for (const i in param) {
      let k
      if (key == null) {
        k = i
      } else if (param instanceof Array) {
        // k = key + '[' + i + ']'
        k = key
      } else {
        k = key + '.' + i
      }
      paramStr += urlEncode(param[i], k, encode)
    }
  }
  return paramStr
}

/**
 * 获取当前登录的浏览器
 * @returns {string}
 */
function getBrowserInfo () {
  // 取得浏览器的userAgent字符串
  const userAgent = navigator.userAgent || ''
  if (!userAgent) {
    return ''
  }
  // 判断是否Opera浏览器
  if (userAgent.includes('Opera')) {
    return 'Opera'
  }

  // 判断是否Edge浏览器
  if (userAgent.includes('Edg')) {
    return 'Edge'
  }

  // 判断是否Firefox浏览器
  if (userAgent.includes('Firefox')) {
    return 'firefox'
  }

  // 判断是否Chrome浏览器
  if (userAgent.includes('Chrome')) {
    return 'Chrome'
  }

  // 判断是否Safari浏览器
  if (userAgent.includes('Safari')) {
    return 'Safari'
  }

  // 判断是否IE浏览器
  if (userAgent.includes('compatible') && userAgent.includes('MSIE')) {
    return 'IE'
  }
  if (userAgent.includes('Trident')) {
    return 'IE'
  }

  return ''
}

/**
 * 前序遍历树形结构
 *
 * @param root 根结点
 * @param callback 回掉函数
 * @param childrenKey children数组的key
 * @return 数组
 */
function preorderTraversal (root, callback = null, childrenKey = 'children') {
  let p = null
  const stack = [root]
  const res = []
  while (stack.length > 0) {
    p = stack.pop()
    res.push(p)

    let stop = false
    if (callback) {
      stop = callback(p) === true
    }
    if (stop) {
      break
    }

    if (p[childrenKey]) {
      const children = p[childrenKey]
      for (let i = children.length - 1; i >= 0; i--) {
        stack.push(children[i])
      }
    }
  }
  return res
}

module.exports = {
  formatSeconds,
  formatTime,
  formatTimeTwo,
  diffTime,
  limit,
  query,
  remove,
  contain,
  uuid,
  objectEquals,
  arrayEquals,
  indexOf,
  getFileSizeString,
  urlEncode,
  getBrowserInfo,
  preorderTraversal
}
