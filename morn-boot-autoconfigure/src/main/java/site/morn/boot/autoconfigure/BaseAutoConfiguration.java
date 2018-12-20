package site.morn.boot.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 基础自动化配置
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/18
 */
@Configuration
@PropertySource("classpath:default.properties")
public class BaseAutoConfiguration {

}
