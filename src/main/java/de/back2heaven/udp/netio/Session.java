/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.udp.netio;

import java.util.Date;

/**
 *
 * @author Jens Kapitza
 */
public class Session {
    
    private ConnectionLink leftLink;
    private ConnectionLink rightLink;
    
    // um datentransfer zu regeln
    private byte[] sessionKey;
    private Date expireDate;
    private Date startDate;
    
    // gilt nur für eine übertragung
    private Long dataId;
    
}
