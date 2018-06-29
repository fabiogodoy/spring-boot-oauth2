package br.com.fabiogodoy.oauth2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;
    
    @NotNull
    private String username;
    
    private String confirmation;

    public User() {
    }

    public User(String username, String password) {

        this.name = username;
        this.password = password;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
    
    @Column
    public String getPassword() {
        return password;
    }
    
    @Column
    public String getName() {
        return name;
    }
    
    @Column
    public String getUsername() {
		return username;
	}

    @Transient
	public String getConfirmation() {
		return confirmation;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public void setConfirmation(String confimation) {
		this.confirmation = confimation;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public void setName(String username) {
        this.name = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
