/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.udp.netio;

import java.net.InetAddress;
import java.util.Date;

/**
 *
 * @author Jens Kapitza
 */
public class ConnectionLink {
    private InetAddress address;
    private User user;
    private Date startDate;
    private Date expireDate;
    
    
    // pgp für arme?
    private byte[] privateKey;
    private byte[] publicKey;
    
    
}
