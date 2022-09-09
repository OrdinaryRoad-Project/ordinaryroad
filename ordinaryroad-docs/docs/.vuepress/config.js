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

module.exports = {
    port: 80,
    title: "OrdinaryRoad开源框架",
    description: 'OrdinaryRoad开源框架',
    themeConfig: {
        displayAllHeaders: false,
        activeHeaderLinks: true,
        smoothScroll: true,
        nav: [
            {text: '首页', link: '/'},
            {
                text: 'OR生态',
                items: [
                    {text: 'IM模块', link: '/or_module/im'},
                    {text: 'IoE模块', link: '/or_module/ioe'},
                ]
            },
            {text: '在线演示', link: 'https://ordinaryroad.tech:8021'},
            {text: 'GitHub', link: 'https://github.com/1962247851/ordinaryroad'},
            /* TODO 历史版本 {
                text: 'v1.3.2',
                // ariaLabel: 'Language Menu',
                items: [
                    {text: 'v1.0.0', link: '/v/1.0.0/', target: "_blank"},
                ]
            }*/
        ],
        sidebar: [
            {
                title: '开始',   // 必要的
                path: '/start/',      // 可选的, 标题的跳转链接，应为绝对路径且必须存在
                collapsable: true, // 可选的, 默认值是 true,
                sidebarDepth: 1,    // 可选的, 默认值是 1
                children: [
                    {
                        title: '介绍',
                        path: '/start/',
                    },
                    {
                        title: '部署',
                        children: [
                            '/start/deploy/dev',
                            '/start/deploy/pro'
                        ]
                    }
                ]
            },
            {
                title: "OR生态",
                children: [
                    '/or_module/im',
                    '/or_module/ioe'
                ]
            }
        ],
    },
    plugins: ['@vuepress/medium-zoom']
}