package com.example.demo.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;

/* this is a simple bean class instantiated in session */

@Component
public class Messages implements Serializable {
    private ArrayList<String> messages;

    public Messages() {
        this.messages = new ArrayList<>();
    }

    public ArrayList<String>  getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String>  messages) {
        this.messages = messages;
    }

    public void add (String m) {
        messages.add(m);
    }

    /* BEAN using ctor - session scope */
    @Bean
    @SessionScope
    public Messages sessionBeanExample () {
        Messages m = new Messages();
        m.add("I'm session bean Messages");
        return m;
    }
}