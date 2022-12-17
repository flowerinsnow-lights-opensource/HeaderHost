package online.flowerinsnow.headerhost.mixin;

import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(C00Handshake.class)
@SideOnly(Side.CLIENT)
public interface AccessorC00Handshake {
    @Accessor("ip")
    String getIP();
    @Accessor("ip")
    void setIP(String ip);

    @Accessor("port")
    int getPort();
    @Accessor("port")
    void setPort(int port);
}
