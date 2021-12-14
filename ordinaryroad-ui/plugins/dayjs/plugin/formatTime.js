export default (option, dayjsClass, dayjsFactory) => {
  // overriding existing API
  // e.g. extend dayjs().format()
  const oldFormat = dayjsClass.prototype.format
  dayjsClass.prototype.format = function (params) {
    // original format result
    const result = oldFormat.bind(this)(params || 'llll')
    // return modified result
    return result === 'Invalid Date' ? '' : result
  }
}
