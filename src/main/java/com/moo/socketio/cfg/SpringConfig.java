package com.moo.socketio.cfg;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.moo.socketio.model.LogFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.moo")
@PropertySource("classpath:server.properties")
public class SpringConfig {

    @Bean(name="webSocketServer")
    public SocketIOServer webSocketServer() {

        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addJsonObjectListener(LogFile.class, new DataListener<LogFile>() {
            @Override
            public void onData(SocketIOClient client, LogFile data, AckRequest ackSender) {
                server.getBroadcastOperations().sendJsonObject(data);
            }
        });

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {

                  for(int i=0;i<10;i++){
                      LogFile log = new LogFile();
                      log.setLine("data from server line ["+i+"]");
                      server.getBroadcastOperations().sendJsonObject(log);
                  }

            }
        });

        return server;

    }

}
