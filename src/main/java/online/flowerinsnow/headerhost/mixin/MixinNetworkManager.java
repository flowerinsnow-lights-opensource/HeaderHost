package online.flowerinsnow.headerhost.mixin;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.headerhost.event.HandshakeIPSendingEvent;
import online.flowerinsnow.saussureautils.reflect.ReflectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
@SideOnly(Side.CLIENT)
public class MixinNetworkManager {
    @Inject(
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void sendPacket(Packet<?> packetIn, CallbackInfo ci) {
        if (packetIn instanceof C00Handshake) {
            String ip = ReflectUtils.getField(packetIn, "field_149598_b", String.class);
            int port = ReflectUtils.getField(packetIn, "field_149599_c", Integer.TYPE);
            HandshakeIPSendingEvent event = new HandshakeIPSendingEvent(ip, port);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                ci.cancel();
            }
            ReflectUtils.putField(packetIn, "field_149598_b", event.ip);
            ReflectUtils.putField(packetIn, "field_149599_c", event.port);
        }
    }
}
