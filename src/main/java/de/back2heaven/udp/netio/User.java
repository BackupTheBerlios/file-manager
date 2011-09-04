/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.udp.netio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jens Kapitza
 */
@Entity
@Table(name="user_list")
public class User  {
    @Id
    @Column(name="userid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="netid")
    private String netID;
    
    //private List<String> aliases;

    public User() {
        super();
    }
    
    public User(String netID) {
        this();
        this.netID = netID;
    }
       

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNetID() {
        return netID;
    }
    
    
}
