package online.flowerinsnow.headerhost.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class HandshakeIPSendingEvent extends Event {
    public String ip;
    public int port;

    public HandshakeIPSendingEvent(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
