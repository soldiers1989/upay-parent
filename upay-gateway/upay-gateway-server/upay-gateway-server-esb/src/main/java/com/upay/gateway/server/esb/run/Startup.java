/**
 * 
 */
package com.upay.gateway.server.esb.run;

import com.pactera.dipper.presys.cp.httpsvr.run.Bootstrap;


/**
 * @author hing
 * 
 */
public class Startup {
    public static void main(String[] args) {
        Bootstrap.main(new String[] { "upay-gateway-server-esb" });
    }
}
