package online.flowerinsnow.headerhost.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import online.flowerinsnow.headerhost.client.object.HeaderHostEntry;

/**
 * 当Handshake包被发送前调用
 * 返回null时表示不处理该事件，交由下一个监听器处理
 * 否则将会处理该事件，将返回的值设为发送的值，并立即结束调用
 */
@Environment(EnvType.CLIENT)
public interface HandshakeIPSendingCallback {
    Event<HandshakeIPSendingCallback> EVENT = EventFactory.createArrayBacked(HandshakeIPSendingCallback.class,
            (listeners) -> (object) -> {
                for (HandshakeIPSendingCallback listener : listeners) {
                    HeaderHostEntry.To to = listener.onHandshake(object);
                    if (to != null) {
                        return to;
                    }
                }
                return null;
    });

    HeaderHostEntry.To onHandshake(HeaderHostEntry.From object);
}
