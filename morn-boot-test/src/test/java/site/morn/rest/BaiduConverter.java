package site.morn.rest;

import java.util.Objects;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Target;
import site.morn.rest.RestMessage.Level;
import site.morn.rest.convert.RestConverter;

/**
 * 百度消息转换器
 */
@Component
@Target(BaiduMessage.class)
public class BaiduConverter implements RestConverter<BaiduMessage> {

  @Override
  public BaiduMessage convert(RestMessage restMessage) {
    BaiduMessage baiduMessage = new BaiduMessage();
    baiduMessage.setError(restMessage.isSuccess() ? "0" : "-1");
    baiduMessage.setMsg(restMessage.getMessage());
    return baiduMessage;
  }

  @Override
  public RestMessage revert(BaiduMessage baiduMessage) {
    RestMessage restMessage = new SimpleRestMessage();
    boolean success = isSuccess(baiduMessage);
    restMessage.setSuccess(success);
    restMessage.setLevel(success ? Level.INFO : Level.ERROR);
    restMessage.setCode(baiduMessage.getError());
    restMessage.setMessage(baiduMessage.getMsg());
    return restMessage;
  }

  private boolean isSuccess(BaiduMessage baiduMessage) {
    return Objects.equals(baiduMessage.getError(), "0");
  }
}
