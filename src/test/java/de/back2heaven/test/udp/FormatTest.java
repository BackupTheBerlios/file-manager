/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.test.udp;

import org.junit.Test;

/**
 *
 * @author Jens Kapitza
 */
public class FormatTest {
    
    @Test
    public void format( ){
        String f = "Some %d %<d dogs";
        System.out.println(String.format(f, 2));
    }
}
