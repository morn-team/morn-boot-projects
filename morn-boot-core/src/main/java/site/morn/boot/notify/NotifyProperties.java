package site.morn.boot.notify;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 通知配置项
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/10
 */
@Getter
@Setter
@ConfigurationProperties("morn.notify")
public class NotifyProperties {

}
