package com.github.airhockey.entities;

import javax.websocket.Session;
import java.util.Objects;

public class Player {
    private String nickname;
    private Session session;

    public Player(){}

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return nickname.equals(player.nickname) &&
                session.equals(player.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, session);
    }
}
