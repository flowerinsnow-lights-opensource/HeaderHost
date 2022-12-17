package online.flowerinsnow.headerhost.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import online.flowerinsnow.headerhost.client.event.HandshakeIPSendingCallback;
import online.flowerinsnow.headerhost.client.object.HeaderHostEntry;
import online.flowerinsnow.saussureautils.reflect.ReflectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
@Environment(EnvType.CLIENT)
public class MixinNetworkManager {
    @Inject(
            method = "sendPacket",
            at = @At("HEAD")
    )
    public void sendPacket(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof HandshakeC2SPacket p) {
            HeaderHostEntry.To to = HandshakeIPSendingCallback.EVENT.invoker().onHandshake(new HeaderHostEntry.From(p.getAddress(), p.getPort()));
            if (to != null) {
                ReflectUtils.putField(p, "field_13159", to.ip);
                ReflectUtils.putField(p, "field_13157", to.port);
            }
        }
    }
}
