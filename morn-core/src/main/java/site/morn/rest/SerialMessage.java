package site.morn.rest;

/**
 * 序列消息
 * <p>序列消息接口用于标识可序列化/反序列化的远程交互消息，不同于{@link java.io.Serializable}，
 * 序列消息还代表该消息是约定的、标准的远程交互消息。因此，系统优先保证其结构的稳定，不会对其进行二次包装</p>
 * <p>在{@link SerialMessageRegistry}中注册序列消息，会达到与实现{@link SerialMessage}接口近似的效果</p>
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/25
 */
public interface SerialMessage {

}
