package com.github.airhockey.server;

import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;

@ServerEndpoint("/air-hockey")
public class GameServer {
    private Server server;

    public void initServer() {
        this.server = new Server("127.0.0.1", 8080, "", null, this.getClass());
    }

    public void launchServer() throws DeploymentException {
        server.start();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameServer that = (GameServer) o;
        return Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server);
    }
}
