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
package tech.ordinaryroad.upms.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.ordinaryroad.commons.core.base.dto.BaseModelDTO;

import java.io.Serial;

/**
 * @author mjz
 * @date 2022/1/13
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@Schema
public class SysFileDTO extends BaseModelDTO {

    @Serial
    private static final long serialVersionUID = -5956078428416671355L;

    @EqualsAndHashCode.Include
    @Schema(title = "桶名称")
    private String bucketName;

    @EqualsAndHashCode.Include
    @Schema(title = "对象名称")
    private String objectName;

    @EqualsAndHashCode.Include
    @Schema(title = "原文件名")
    private String originalFilename;

    @EqualsAndHashCode.Include
    @Schema(title = "文件大小（byte）")
    private Long size;

}
