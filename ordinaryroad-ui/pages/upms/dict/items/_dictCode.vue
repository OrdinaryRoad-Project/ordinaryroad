<!--
  - MIT License
  -
  - Copyright (c) 2021 苗锦洲
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy
  - of this software and associated documentation files (the "Software"), to deal
  - in the Software without restriction, including without limitation the rights
  - to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  - copies of the Software, and to permit persons to whom the Software is
  - furnished to do so, subject to the following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all
  - copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  - IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  - FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  - AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  - LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  - OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  - SOFTWARE.
  -->

<template>
  <base-material-card
    icon="mdi-book-open"
    :title="$t('dictItemsManagement')"
  >
    <or-data-table-upms-dict-item :default-dict-uuid="presetModel.uuid" />
  </base-material-card>
</template>
<script>
export default {
  validate ({ params }) {
    // 必填
    return !!params.dictCode
  },
  async asyncData ({ route, $apisServer, store }) {
    let presetModel
    if (route.params.item) {
      presetModel = route.params.item
    } else {
      // 服务端渲染，先从服务器获取dict的uuid
      presetModel = (await $apisServer.upms.dict.findByUniqueColumn(
        store.getters['user/getTokenInfo'].satoken, { dictCode: route.params.dictCode }
      )).data
    }
    return { presetModel }
  },
  data: () => ({
    presetModel: {
      uuid: null
    }
  }),
  head () {
    return {
      title: this.$t('dictItemsManagement')
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {}
}
</script>

<style scoped>

</style>
