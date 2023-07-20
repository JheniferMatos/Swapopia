package com.swapopia.Swapolandia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Swap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    // Lista de itens enviados
    @OneToMany
    private List<Item> sentItems;

    // Lista de itens recebidos
    @OneToMany
    private List<Item> receivedItems;

    private boolean accepted;


    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return User return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * @return User return the receiver
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    /**
     * @return Item return the sentItems
     */
    public List<Item> getSentItems() {
        return sentItems;
    }

    /**
     * @param sentItems the sentItems to set
     */
    public void setSentItems(List<Item> sentItems) {
        this.sentItems = sentItems;
    }

    /**
     * @return Item return the receivedItems
     */
    public List<Item> getReceivedItems() {
        return receivedItems;
    }

    /**
     * @param receivedItems the receivedItems to set
     */
    public void setReceivedItems(List<Item> receivedItems) {
        this.receivedItems = receivedItems;
    }


    /**
     * @return boolean return the accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @param accepted the accepted to set
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

}