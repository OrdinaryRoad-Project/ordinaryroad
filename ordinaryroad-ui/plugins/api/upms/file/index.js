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
  list: (offset, limit, { bucketName, objectName, originalFilename }) => {
    const data = { limit, offset, bucketName, objectName, originalFilename }
    return $axios({ url: '/upms/file/list', method: 'post', data })
  }
}
