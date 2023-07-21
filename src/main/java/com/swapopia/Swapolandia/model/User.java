package com.swapopia.Swapolandia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotEmpty(message = "O nome é obrigatório")
    private String name;

    @NotEmpty(message = "O login é obrigatório")
    private String login;

    @NotEmpty(message = "A senha é obrigatória")
    private String password;

    @NotNull(message = "O valor do dinheiro é obrigatório")
    private Float cash;

    
    private String ProfilePicture;


    

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the login
     */
    public  String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }



    /**
     * @return String return the ProfilePicture
     */
    public String getProfilePicture() {
        return ProfilePicture;
    }

    /**
     * @param ProfilePicture the ProfilePicture to set
     */
    public void setProfilePicture(String ProfilePicture) {
        this.ProfilePicture = ProfilePicture;
    }


    /**
     * @return Long return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }


    /**
     * @return Float return the cash
     */
    public Float getCash() {
        return cash;
    }

    /**
     * @param cash the cash to set
     */
    public void setCash(Float cash) {
        this.cash = cash;
    }

}
