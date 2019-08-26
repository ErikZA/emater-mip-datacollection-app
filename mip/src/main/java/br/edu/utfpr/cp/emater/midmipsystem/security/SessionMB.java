package br.edu.utfpr.cp.emater.midmipsystem.security;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.security.core.context.SecurityContextHolder;

@Named
@ViewScoped
public class SessionMB implements Serializable {

    private String currentUser;

    @PostConstruct
    public void init() {
        currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}