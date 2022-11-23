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
        // 假定是 GitHub. 同时也可以是一个完整的 GitLab URL
        repo: '1962247851/ordinaryroad',
        // 自定义仓库链接文字。默认从 `themeConfig.repo` 中自动推断为
        // "GitHub"/"GitLab"/"Bitbucket" 其中之一，或是 "Source"。
        repoLabel: 'GitHub',

        // 以下为可选的编辑链接选项

        // 假如你的文档仓库和项目本身不在一个仓库：
        docsRepo: '1962247851/ordinaryroad',
        // 假如文档不是放在仓库的根目录下：
        docsDir: 'ordinaryroad-docs/docs',
        // 假如文档放在一个特定的分支下：
        docsBranch: 'dev',
        // 默认是 false, 设置为 true 来启用
        editLinks: true,
        // 默认为 "Edit this page"
        editLinkText: '帮助我们改善此页面！'
    },
    plugins: ['@vuepress/medium-zoom']
}