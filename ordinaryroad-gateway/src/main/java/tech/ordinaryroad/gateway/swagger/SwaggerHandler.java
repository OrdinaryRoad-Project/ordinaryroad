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

package tech.ordinaryroad.gateway.swagger;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerHandler {
//
//    @Autowired(required = false)
//    private SecurityConfiguration securityConfiguration;
//
//    @Autowired(required = false)
//    private UiConfiguration uiConfiguration;
//
//    private final SwaggerResourcesProvider swaggerResources;
//
//    @Autowired
//    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
//        this.swaggerResources = swaggerResources;
//    }
//
//    @GetMapping("/swagger-resources/configuration/security")
//    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
//        return Mono.just(new ResponseEntity<>(
//                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
//    }
//
//    @GetMapping("/swagger-resources/configuration/ui")
//    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
//        return Mono.just(new ResponseEntity<>(
//                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
//    }
//
//    @GetMapping("/swagger-resources")
//    public Mono<ResponseEntity<?>> swaggerResources() {
//        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
//    }
}