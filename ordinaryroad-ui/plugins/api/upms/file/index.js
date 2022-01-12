let $axios = null

export default {
  initAxios (axios) {
    $axios = $axios || axios
  },
  upload: ({ bucketName, file }) => {
    const data = new FormData()
    data.append('bucketName', bucketName)
    data.append('file', file)
    return $axios({ url: '/upms/file/upload', method: 'post', data })
  },
  getFileDownloadPath: (bucketAndObjectName) => {
    return '/api/upms/file/download' + bucketAndObjectName
  }
}
