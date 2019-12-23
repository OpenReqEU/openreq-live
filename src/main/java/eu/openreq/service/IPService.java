package eu.openreq.service;

import eu.openreq.Util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.util.Arrays;

@Service
public class IPService {

    private static final Logger logger = LoggerFactory.getLogger(IPService.class);

    private String hostName = "live.openreq.eu";

    public long getPort() {
        return Constants.PORT;
    }

    public String getHost() {
        if (hostName != null) {
            return hostName;
        }

        hostName = InetAddress.getLoopbackAddress().getHostName();
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
            try(final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                hostName = socket.getLocalAddress().getHostAddress();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return hostName;
    }

}
