package site.morn.rest;

import java.util.Objects;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Source;
import site.morn.bean.annotation.Target;
import site.morn.rest.constant.RestMessageLevel;
import site.morn.rest.constant.RestMessageStatus;
import site.morn.rest.support.SimpleRestMessage;

/**
 * 百度消息转换器
 */
@Component
@Source(RestMessage.class)
@Target(BaiduMessage.class)
public class BaiduMessageConverter implements RestMessageConverter<BaiduMessage> {

  @Override
  public BaiduMessage convert(RestMessage restMessage) {
    BaiduMessage baiduMessage = new BaiduMessage();
    baiduMessage.setError(RestMessageStatus.isSuccess(restMessage.getStatus()) ? "0" : "-1");
    baiduMessage.setMsg(restMessage.getMessage());
    baiduMessage.setData(restMessage.getData());
    return baiduMessage;
  }

  @Override
  public RestMessage revert(BaiduMessage baiduMessage) {
    RestMessage restMessage = new SimpleRestMessage();
    boolean success = isSuccess(baiduMessage);
    restMessage.setStatus(success ? RestMessageStatus.SUCCESS : RestMessageStatus.FAILURE);
    restMessage.setLevel(success ? RestMessageLevel.INFO : RestMessageLevel.ERROR);
    restMessage.setCode(baiduMessage.getError());
    restMessage.setMessage(baiduMessage.getMsg());
    restMessage.setData(baiduMessage.getData());
    return restMessage;
  }

  private boolean isSuccess(BaiduMessage baiduMessage) {
    return Objects.equals(baiduMessage.getError(), "0");
  }
}
