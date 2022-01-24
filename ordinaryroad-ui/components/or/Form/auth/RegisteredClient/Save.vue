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
  <v-form ref="form">
    <v-text-field
      v-model="model.clientId"
      :rules="[$rules.required,$rules.max20Chars]"
      :label="$t('clientId')"
    />
    <v-text-field
      v-model="model.clientSecret"
      :rules="[$rules.required,$rules.max20Chars]"
      :label="$t('clientSecret')"
    />
    <v-text-field
      v-model="model.clientName"
      :rules="[$rules.required,$rules.max100Chars]"
      :label="$t('clientName')"
    />
    <v-text-field
      v-model="model.redirectUris"
      :rules="[$rules.max1000Chars]"
      :label="$t('redirectUris')"
    />
    <v-text-field
      v-model="model.scopes"
      :rules="[$rules.max1000Chars]"
      :label="$t('scopes')"
    />
  </v-form>
</template>

<script>
export default {
  name: 'OrFormAuthRegisteredClientSave',
  props: {
    preset: {
      type: Object,
      default: () => ({
        clientId: null,
        clientSecret: null,
        clientName: null,
        redirectUris: null,
        scopes: null
      })
    }
  },
  data: () => ({
    model: {}
  }),
  watch: {
    preset: {
      handler (val) {
        this.model = Object.assign({}, val)
      },
      deep: true,
      immediate: true
    },
    model: {
      handler (val) {
        this.$emit('update', val)
      },
      deep: true,
      immediate: true
    }
  },
  mounted () {
  },
  methods: {
    validate () {
      return this.$refs.form.validate()
    }
  }
}
</script>

<style scoped>

</style>
