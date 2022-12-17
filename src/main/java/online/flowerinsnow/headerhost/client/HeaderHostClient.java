package online.flowerinsnow.headerhost.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import online.flowerinsnow.headerhost.client.event.HandshakeIPSendingCallback;
import online.flowerinsnow.headerhost.client.listener.HandshakeListener;
import online.flowerinsnow.headerhost.client.object.HeaderHostEntry;
import online.flowerinsnow.saussureautils.io.CopyOption;
import online.flowerinsnow.saussureautils.io.IOUtils;
import online.flowerinsnow.xml.XMLFactory;
import online.flowerinsnow.xml.node.XMLNodeDocument;
import online.flowerinsnow.xml.node.XMLNodeElement;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Environment(EnvType.CLIENT)
public class HeaderHostClient implements ClientModInitializer {
    private static final HashSet<HeaderHostEntry> entries = new HashSet<>();

    @Override
    public void onInitializeClient() {
        loadConfiguration(new File(FabricLoader.getInstance().getConfigDir().toFile(), "headerhost.xml"));

        HandshakeIPSendingCallback.EVENT.register(new HandshakeListener());
    }

    public void loadConfiguration(File file) {
        entries.clear();
        if (!file.isFile()) {
            try {
                IOUtils.copy(HeaderHostClient.class.getResourceAsStream("/headerhost.xml"), file, CopyOption.CLOSE_INPUT);
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
        return entries;
    }
}
