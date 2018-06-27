package br.com.fabiogodoy.oauth2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

    
    private Long id;

    private String name;

    private String password;
    
    private String username;
    
    private String confimation;

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
	public String getConfimation() {
		return confimation;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public void setConfimation(String confimation) {
		this.confimation = confimation;
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
