package online.flowerinsnow.headerhost.client.listener;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import online.flowerinsnow.headerhost.client.HeaderHostClient;
import online.flowerinsnow.headerhost.client.event.HandshakeIPSendingCallback;
import online.flowerinsnow.headerhost.client.object.HeaderHostEntry;

@Environment(EnvType.CLIENT)
public class HandshakeListener implements HandshakeIPSendingCallback {
    @Override
    public HeaderHostEntry.To onHandshake(HeaderHostEntry.From object) {
        for (HeaderHostEntry entry : HeaderHostClient.getEntries()) {
            if (entry.from.ip.equals(object.ip) && entry.from.port == object.port) {
                object.ip = entry.to.ip;
                object.port = entry.to.port;
                return new HeaderHostEntry.To(entry.to.ip, entry.to.port);
            }
        }
        return null;
    }
}
