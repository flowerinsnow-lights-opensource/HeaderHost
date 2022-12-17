package online.flowerinsnow.headerhost;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.headerhost.listener.HandshakeListener;
import online.flowerinsnow.headerhost.object.HeaderHostEntry;
import online.flowerinsnow.saussureautils.io.CopyOption;
import online.flowerinsnow.saussureautils.io.IOUtils;
import online.flowerinsnow.xml.XMLFactory;
import online.flowerinsnow.xml.node.XMLNodeDocument;
import online.flowerinsnow.xml.node.XMLNodeElement;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Mod(
        modid = HeaderHost.MODID,
        version = HeaderHost.VERSION,
        clientSideOnly = true
)
@SideOnly(Side.CLIENT)
public class HeaderHost {
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.12.1";

    private static final HashSet<HeaderHostEntry> entries = new HashSet<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        loadConfiguration(new File(event.getModConfigurationDirectory(), "headerhost.xml"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new HandshakeListener());
    }

    public void loadConfiguration(File file) {
        entries.clear();
        if (!file.isFile()) {
            try {
                //noinspection DataFlowIssue
                IOUtils.copy(HeaderHost.class.getResourceAsStream("/headerhost.xml"), file, CopyOption.CLOSE_INPUT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            XMLNodeDocument document = XMLFactory.parse(file);
            XMLNodeElement headerHosts = document.getElement("configuration").getElement("headerhosts");
            List<XMLNodeElement> headerHostList = headerHosts.getElementList("headerhost");
            headerHostList.forEach(hh -> {
                XMLNodeElement from = hh.getElement("from");
                XMLNodeElement to = hh.getElement("to");
                entries.add(new HeaderHostEntry(
                        from.getTextList().get(0), Integer.parseInt(from.getAttribute("port")),
                        to.getTextList().get(0), Integer.parseInt(to.getAttribute("port"))
                ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashSet<HeaderHostEntry> getEntries() {
        return new HashSet<>(entries);
    }
}
