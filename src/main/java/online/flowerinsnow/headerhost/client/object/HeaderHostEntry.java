package online.flowerinsnow.headerhost.client.object;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class HeaderHostEntry {
    public From from;
    public To to;
    public HeaderHostEntry(String fromIP, int fromPort, String toIP, int toPort) {
        this.from = new From(fromIP, fromPort);
        this.to = new To(toIP, toPort);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderHostEntry that = (HeaderHostEntry) o;
        return from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HeaderHostEntry{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    public static class From {
        public String ip;
        public int port;

        public From(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            From from = (From) o;
            return port == from.port && Objects.equals(ip, from.ip);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (ip != null ? ip.hashCode() : 0);
            result = 31 * result + port;
            return result;
        }

        @Override
        public String toString() {
            return "From{" +
                    "ip='" + ip + '\'' +
                    ", port=" + port +
                    '}';
        }
    }

    public static class To {
        public String ip;
        public int port;

        public To(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            To to = (To) o;
            return port == to.port && Objects.equals(ip, to.ip);
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + (ip != null ? ip.hashCode() : 0);
            result = 31 * result + port;
            return result;
        }

        @Override
        public String toString() {
            return "To{" +
                    "ip='" + ip + '\'' +
                    ", port=" + port +
                    '}';
        }
    }
}
