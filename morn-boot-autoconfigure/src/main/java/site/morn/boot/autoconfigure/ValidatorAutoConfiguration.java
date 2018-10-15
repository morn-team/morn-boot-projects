package site.morn.boot.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 数据校验自动化配置
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/18
 * @since 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "morn.validator", value = "enabled", havingValue = "true")
public class ValidatorAutoConfiguration {

}
