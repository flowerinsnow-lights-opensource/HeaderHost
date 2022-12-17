package online.flowerinsnow.headerhost.listener;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import online.flowerinsnow.headerhost.HeaderHost;
import online.flowerinsnow.headerhost.event.HandshakeIPSendingEvent;

public class HandshakeListener {
    @SubscribeEvent
    public void onHandshake(HandshakeIPSendingEvent e) {
        //noinspection ResultOfMethodCallIgnored
        HeaderHost.getEntries().stream().anyMatch(entry -> {
            if (entry.from.ip.equals(e.ip) && entry.from.port == e.port) {
                e.ip = entry.to.ip;
                e.port = entry.to.port;
                return true;
            }
            return false;
        });
    }
}
