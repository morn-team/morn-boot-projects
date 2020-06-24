package site.morn.boot.notify.support;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.morn.bean.annotation.Objective;
import site.morn.core.CriteriaMap;
import site.morn.notify.NotifyMeta;
import site.morn.notify.annotation.Notify;
import site.morn.notify.annotation.NotifyReceiver;
import site.morn.notify.support.AbstractAnnotationNotifyMetaAdapter;
import site.morn.notify.support.NotifyArgs;
import site.morn.notify.support.NotifyArguments;
import site.morn.template.TemplateMeta;
import site.morn.template.annotation.Template;
import site.morn.util.SpareArrayUtils;

/**
 * 注解式通知元数据初始化适配器
 * <p>对通知元数据进行初始化</p>
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Slf4j
@Objective
public class AnnotationNotifyMetaInitialAdapter extends AbstractAnnotationNotifyMetaAdapter {

  @Override
  public NotifyMeta adaptionNotifyMeta() {
    NotifyMeta notifyMeta = getNotifyMeta();

    Notify notify = getNotify();
    notifyMeta.setNotifyType(notify.type());
    notifyMeta.setNotifyName(notify.value());
    NotifyReceiver receiver = getReceiver();
    boolean hasReceiver = Objects.nonNull(receiver);
    if (hasReceiver) {
      notifyMeta.setReceiverType(receiver.type());
    }

    // 从线程变量中获取通知参数
    NotifyArgs notifyArgs = NotifyArguments
        .getNotifyArgs(notifyMeta.getNotifyType(), notifyMeta.getNotifyName());
    List<String> receiverValues;
    if (!CollectionUtils.isEmpty(notifyArgs.getReceiverValues())) {
      // 从线程变量中获取接收人标识
      receiverValues = notifyArgs.getReceiverValues();
    } else if (hasReceiver && !SpareArrayUtils.isEmpty(receiver.value())) {
      // 从注解中获取接收人标识
      receiverValues = Arrays.stream(receiver.value()).collect(Collectors.toList());
    } else {
      receiverValues = Collections.emptyList();
    }
    notifyMeta.setReceiverValues(receiverValues);
    return notifyMeta;
  }

  @Override
  public TemplateMeta adaptionTemplateMeta() {
    NotifyMeta notifyMeta = getNotifyMeta();
    TemplateMeta templateMeta = getTemplateMeta();

    Template template = getTemplate();
    String type = template.type();
    String name =
        StringUtils.isEmpty(template.name()) ? notifyMeta.getNotifyName() : template.name();
    templateMeta.setType(type);
    templateMeta.setName(name);

    // 从线程变量中获取模板参数
    CriteriaMap templateArgs = NotifyArguments
        .getTemplateArgs(notifyMeta.getNotifyType(), notifyMeta.getNotifyName());
    templateMeta.setArgs(templateArgs);
    return templateMeta;
  }
}
