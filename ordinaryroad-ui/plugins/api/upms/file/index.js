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

let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  upload: ({ file }) => {
    const data = new FormData()
    data.append('clientId', process.env.CLIENT_ID)
    data.append('clientSecret', process.env.CLIENT_SECRET)
    data.append('file', file)
    return $axios({ url: '/upms/file/upload', method: 'post', data })
  },
  getFileDownloadPath: ({ bucketAndObjectName, showInLine }) => {
    let path = '/api/upms/file/download' + bucketAndObjectName
    path += (showInLine !== null && typeof showInLine !== 'undefined')
      ? ('?showInline=' + showInLine)
      : ''
    return path
  },
  list: (offset, limit, orderBy, orderByDesc, { bucketName, objectName, originalFilename }) => {
    const data = { offset, limit, orderBy, orderByDesc, bucketName, objectName, originalFilename }
    return $axios({ url: '/upms/file/list', method: 'post', data })
  }
}
