package eu.openreq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.DatagramSocket;

@Service
public class IPService {

    private static final Logger logger = LoggerFactory.getLogger(IPService.class);

    private static String hostName = "innosensr.com";

    @Autowired
    private Environment environment;

    public long getPort() {
        return Long.parseLong(environment.getProperty("server.port"));
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
            logger.error(e.getStackTrace().toString());
        }
        return hostName;
    }

}
