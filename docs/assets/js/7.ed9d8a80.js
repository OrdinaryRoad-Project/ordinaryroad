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

(window.webpackJsonp=window.webpackJsonp||[]).push([[7],{251:function(e,t,a){},299:function(e,t,a){"use strict";a(251)},303:function(e,t,a){"use strict";a.r(t);var o={name:"CodeGroup",data:()=>({codeTabs:[],activeCodeTabIndex:-1}),watch:{activeCodeTabIndex(e){this.activateCodeTab(e)}},mounted(){this.loadTabs()},methods:{changeCodeTab(e){this.activeCodeTabIndex=e},loadTabs(){this.codeTabs=(this.$slots.default||[]).filter(e=>Boolean(e.componentOptions)).map((e,t)=>(""===e.componentOptions.propsData.active&&(this.activeCodeTabIndex=t),{title:e.componentOptions.propsData.title,elm:e.elm})),-1===this.activeCodeTabIndex&&this.codeTabs.length>0&&(this.activeCodeTabIndex=0),this.activateCodeTab(0)},activateCodeTab(e){this.codeTabs.forEach(e=>{e.elm&&e.elm.classList.remove("theme-code-block__active")}),this.codeTabs[e].elm&&this.codeTabs[e].elm.classList.add("theme-code-block__active")}}},s=(a(299),a(13)),c=Object(s.a)(o,(function(){var e=this,t=e._self._c;return t("ClientOnly",[t("div",{staticClass:"theme-code-group"},[t("div",{staticClass:"theme-code-group__nav"},[t("ul",{staticClass:"theme-code-group__ul"},e._l(e.codeTabs,(function(a,o){return t("li",{key:a.title,staticClass:"theme-code-group__li"},[t("button",{staticClass:"theme-code-group__nav-tab",class:{"theme-code-group__nav-tab-active":o===e.activeCodeTabIndex},on:{click:function(t){return e.changeCodeTab(o)}}},[e._v("\n            "+e._s(a.title)+"\n          ")])])})),0)]),e._v(" "),e._t("default"),e._v(" "),e.codeTabs.length<1?t("pre",{staticClass:"pre-blank"},[e._v("// Make sure to add code blocks to your code group")]):e._e()],2)])}),[],!1,null,"deefee04",null);t.default=c.exports}}]);