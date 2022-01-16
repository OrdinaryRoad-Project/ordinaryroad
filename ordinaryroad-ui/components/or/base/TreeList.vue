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
