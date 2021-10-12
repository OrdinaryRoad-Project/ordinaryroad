package top.ordinaryroad.commons.swagger.annotation;

import org.springframework.context.annotation.Import;
import top.ordinaryroad.commons.swagger.config.SwaggerAutoConfiguration;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableCustomSwagger2 {

}
