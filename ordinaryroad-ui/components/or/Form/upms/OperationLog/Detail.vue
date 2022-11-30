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
  <div>
    <v-card flat>
      <v-card-title class="d-flex">
        <span :class="`${$apis.statusColor(model)}--text`">
          <span class="me-2">{{ model.method }}</span> {{ model.path }}
        </span>
        <span class="ml-auto">
          <span :class="`${$apis.statusColor(model)}--text`">{{ model.consumedTime }}ms</span><v-chip
            label
            class="ms-2"
            :color="$apis.statusColor(model)"
          >{{
            model.status
          }}</v-chip>
        </span>
      </v-card-title>
      <v-card-subtitle>
        {{ model.ip }} {{ model.createBy }} {{ model.createdTime }}
      </v-card-subtitle>

      <div class="mb-2">
        <!-- 路径参数 -->
        <v-card outlined flat class="mb-4">
          <v-card-title>
            路径参数{{
              pathParams && Object.keys(pathParams).length ? $t('parenthesesWithSpace', [Object.keys(pathParams).length]) : ''
            }}
          </v-card-title>
          <v-divider />
          <v-simple-table
            v-if="pathParams && Object.keys(pathParams).length"
            dense
          >
            <thead>
              <tr>
                <th style="width: 200px">
                  Name
                </th>
                <th>
                  Value
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="key in Object.keys(pathParams)"
                :key="key"
              >
                <td>{{ key }}</td>
                <td>
                  {{ pathParams[key] ? pathParams[key].join(',') : '无' }}
                </td>
              </tr>
            </tbody>
          </v-simple-table>
          <v-alert
            v-else
            text
            class="ma-2"
            border="left"
            dense
            colored-border
          >
            <span class="text-body-2">没有路径参数</span>
          </v-alert>
        </v-card>
        <!-- 查询参数 -->
        <v-card outlined flat>
          <v-card-title>
            查询参数{{
              queryParams && Object.keys(queryParams).length ? $t('parenthesesWithSpace', [Object.keys(queryParams).length]) : ''
            }}
          </v-card-title>
          <v-divider />
          <v-simple-table
            v-if="queryParams&&Object.keys(queryParams).length"
            dense
          >
            <thead>
              <tr>
                <th style="width: 200px">
                  Name
                </th>
                <th>
                  Value
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="key in Object.keys(queryParams)"
                :key="key"
              >
                <td>{{ key }}</td>
                <td>
                  {{ queryParams[key] ? queryParams[key].join(',') : '无' }}
                </td>
              </tr>
            </tbody>
          </v-simple-table>
          <v-alert
            v-else
            text
            class="ma-2"
            border="left"
            dense
            colored-border
          >
            <span class="text-body-2">没有查询参数</span>
          </v-alert>
        </v-card>
      </div>
    </v-card>

    <v-tabs v-model="tabModel" class="sticky-top">
      <v-tab>响应</v-tab>
      <v-tab>请求</v-tab>
      <v-tab>Header</v-tab>
      <v-tab>Cookie</v-tab>
    </v-tabs>

    <v-tabs-items v-model="tabModel">
      <!-- 响应 -->
      <v-tab-item>
        <v-container fluid class="ma-0 px-0 py-2">
          <v-sheet
            v-if="model.response.trim()"
            rounded
            outlined
          >
            <json-viewer
              :theme="`jv-${$vuetify.theme.dark ? 'dark' : 'light'}`"
              :value="jsonObject(model.response)"
              copyable
              expanded
              expand-depth="5"
            >
              <template #copy="{ copied }">
                <v-btn :disabled="copied" depressed small>
                  {{ copied ? $t('copied') : $t('copy') }}
                </v-btn>
              </template>
            </json-viewer>
          </v-sheet>

          <v-alert
            v-else
            text
            class="ma-0"
            border="left"
            dense
            colored-border
          >
            <span class="text-body-2">没有响应体</span>
          </v-alert>
        </v-container>
      </v-tab-item>
      <!-- 请求 -->
      <v-tab-item>
        <v-container fluid class="ma-0 px-0 py-2">
          <v-sheet
            v-if="model.request.trim()"
            rounded
            outlined
          >
            <json-viewer
              :theme="`jv-${$vuetify.theme.dark ? 'dark' : 'light'}`"
              :value="jsonObject(model.request)"
              copyable
              expanded
              expand-depth="5"
            >
              <template #copy="{ copied }">
                <v-btn :disabled="copied" depressed small>
                  {{ copied ? $t('copied') : $t('copy') }}
                </v-btn>
              </template>
            </json-viewer>
          </v-sheet>

          <v-alert
            v-else
            text
            class="ma-0"
            border="left"
            dense
            colored-border
          >
            <span class="text-body-2">没有请求体</span>
          </v-alert>
        </v-container>
      </v-tab-item>
      <!-- Header -->
      <v-tab-item>
        <!-- 请求Header -->
        <v-card outlined flat class="mb-4">
          <v-card-title>
            请求Header{{
              headers && Object.keys(headers).length ? $t('parenthesesWithSpace', [Object.keys(headers).length]) : ''
            }}
          </v-card-title>
          <v-divider />
          <v-simple-table
            v-if="headers&&Object.keys(headers).length"
            dense
          >
            <thead>
              <tr>
                <th style="width: 200px">
                  Name
                </th>
                <th>
                  Value
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="key in Object.keys(headers)"
                :key="key"
              >
                <td>{{ key }}</td>
                <td>
                  {{ headers[key] ? headers[key].join(',') : '无' }}
                </td>
              </tr>
            </tbody>
          </v-simple-table>
          <v-alert
            v-else
            text
            class="ma-2"
            border="left"
            dense
            colored-border
          >
            <span class="text-body-2">没有请求Header</span>
          </v-alert>
        </v-card>

        <!-- 响应Header -->
        <v-card outlined flat>
          <v-card-title>
            响应Header{{
              responseHeaders && Object.keys(responseHeaders).length ? $t('parenthesesWithSpace', [Object.keys(responseHeaders).length]) : ''
            }}
          </v-card-title>
          <v-divider />
          <v-simple-table
            v-if="responseHeaders && Object.keys(responseHeaders).length"
            dense
          >
            <thead>
              <tr>
                <th style="width: 200px">
                  Name
                </th>
                <th>
                  Value
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="key in Object.keys(responseHeaders)"
                :key="key"
              >
                <td>{{ key }}</td>
                <td>
                  {{ responseHeaders[key] ? responseHeaders[key].join(',') : '无' }}
                </td>
              </tr>
            </tbody>
          </v-simple-table>
          <v-alert
            v-else
            text
            class="ma-2"
            border="left"
            dense
            colored-border
          >
            <span class="text-body-2">没有响应Header</span>
          </v-alert>
        </v-card>
      </v-tab-item>
      <!-- Cookie -->
      <v-tab-item>
        <v-container fluid class="ma-0 px-0 py-2">
          <!-- 请求Cookie -->
          <v-card outlined flat class="mb-4">
            <v-card-title>
              请求Cookie{{
                cookies && Object.keys(cookies).length ? $t('parenthesesWithSpace', [Object.keys(cookies).length]) : ''
              }}
            </v-card-title>
            <v-divider />
            <v-simple-table
              v-if="cookies&&Object.keys(cookies).length"
              dense
            >
              <template #default>
                <thead>
                  <tr>
                    <th class="text-left">
                      Name
                    </th>
                    <th class="text-left">
                      Value
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="key in Object.keys(cookies)"
                    :key="key"
                  >
                    <td>{{ key }}</td>
                    <td>{{ cookies[key] ? decodeURIComponent(cookies[key].replace(`${key}=`, '')) : '无' }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>

            <v-alert
              v-else
              text
              class="ma-2"
              border="left"
              dense
              colored-border
            >
              <span class="text-body-2">没有请求Cookie</span>
            </v-alert>
          </v-card>

          <!-- 响应Cookie -->
          <v-card outlined flat>
            <v-card-title>
              响应Cookie{{
                responseCookies && Object.keys(responseCookies).length ? $t('parenthesesWithSpace', [Object.keys(responseCookies).length]) : ''
              }}
            </v-card-title>
            <v-divider />
            <v-simple-table
              v-if="responseCookies&&Object.keys(responseCookies).length"
              dense
            >
              <template #default>
                <thead>
                  <tr>
                    <th class="text-left">
                      Name
                    </th>
                    <th class="text-left">
                      Value
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="key in Object.keys(responseCookies)"
                    :key="key"
                  >
                    <td>{{ key }}</td>
                    <td>
                      {{
                        responseCookies[key] ? decodeURIComponent(cookies[key].replace(`${key}=`, '')) : '无'
                      }}
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            <v-alert
              v-else
              text
              class="ma-2"
              border="left"
              dense
              colored-border
            >
              <span class="text-body-2">没有响应Cookie</span>
            </v-alert>
          </v-card>
        </v-container>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import JsonViewer from 'vue-json-viewer/ssr'
import 'vue-json-viewer/style.css'

export default {
  name: 'OrFormUpmsOperationLogDetail',
  components: {
    JsonViewer
  },
  props: {
    preset: {
      type: Object,
      default: () => ({
        ip: null
      })
    }
  },
  data: () => ({
    tabModel: 0,
    model: {},
    pathParams: {},
    queryParams: {},
    headers: {},
    cookies: {},
    responseHeaders: {},
    responseCookies: {}
  }),
  computed: {
    jsonObject () {
      return (string) => {
        try {
          return JSON.parse(string)
        } catch {
          return string
        }
      }
    }
  },
  watch: {
    preset: {
      handler (val) {
        this.model = Object.assign({}, val)
        this.pathParams = JSON.parse(this.model.pathParams)
        this.queryParams = JSON.parse(this.model.queryParams)
        this.headers = JSON.parse(this.model.headers)
        this.cookies = JSON.parse(this.model.cookies)
        this.responseHeaders = JSON.parse(this.model.responseHeaders)
        this.responseCookies = JSON.parse(this.model.responseCookies)
      },
      deep: true,
      immediate: true
    }
  },
  mounted () {
  },
  methods: {}
}
</script>

<style scoped>

</style>
