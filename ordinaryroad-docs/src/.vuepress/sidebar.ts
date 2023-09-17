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

import {sidebar} from "vuepress-theme-hope";

export default sidebar({
    "/": [
        "",
        {
            text: "开始",
            icon: "laptop",
            prefix: 'start',
            children: ['',
                {
                    text: "部署",
                    prefix: "deploy",
                    collapsible: true,
                    children: ['dev/', 'pro/'],
                },
                {
                    text: "开发",
                    collapsible: true,
                    prefix: "develop",
                    children: ['backend/', 'frontend/'],
                }
            ]
        },
        {
            text: "通用模块",
            icon: "gear",
            prefix: 'commons',
            children: ['log/'],
        },
        {
            text: "OR生态",
            icon: "book",
            prefix: 'or_module',
            children: [
                'blog/',
                'live-chat-client/',
                'vuetify/',
                'im/',
                'ioe/',
            ],
        },
        {
            text: "其他",
            icon: "book",
            prefix: 'others',
            children: ['changelog/'],
        },
    ],
});
