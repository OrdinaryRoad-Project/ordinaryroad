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
  <v-list :nav="nav">
    <template v-for="(item,i) in items">
      <v-list-group
        v-if="item.children&&item.children.length > 0"
        :key="i"
        :prepend-icon="item.icon"
        no-action
      >
        <template #prependIcon>
          <slot :name="'prependIcon_'+i" />
        </template>
        <template #activator>
          <slot :name="'activator_'+i">
            <v-list-item-title>{{ $t(item.titleKey) }}</v-list-item-title>
          </slot>
        </template>
        <template #appendIcon>
          <slot :name="'appendIcon_'+i" />
        </template>
        <OrBaseTreeList
          :nav="nav"
          :items="item.children"
          @clickListItem="onClickListItem"
        />
      </v-list-group>

      <v-list-item
        v-else
        :key="i"
        class="px-2"
        :to="item.to||null"
        :router="item.to"
        exact
        dense
        active-class="primary white--text elevation-4"
        @click="!item.to && $emit('clickListItem', item)"
      >
        <slot :name="'item_'+i">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ $t(item.titleKey) }}</v-list-item-title>
          </v-list-item-content>
        </slot>
      </v-list-item>
    </template>
  </v-list>
</template>

<script>

export default {
  name: 'OrBaseTreeList',
  props: {
    items: {
      type: Array,
      default: () => []
    },
    nav: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    onClickListItem (item) {
      this.$emit('clickListItem', item)
    }
  }
}
</script>
