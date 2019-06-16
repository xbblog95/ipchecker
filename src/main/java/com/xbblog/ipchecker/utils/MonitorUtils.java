package com.xbblog.ipchecker.utils;

import org.apache.commons.net.telnet.TelnetClient;


public class MonitorUtils {

    private final static int TIMEOUT = 5 * 1000;
    public static long testTCPActive(String host, int port){
        TelnetClient telnet = new TelnetClient("VT220");
        try
        {
            long time = System.currentTimeMillis();
            telnet.setConnectTimeout(TIMEOUT);
            telnet.connect(host, port);
            telnet.disconnect();
            return System.currentTimeMillis() - time;
        }
        catch (Exception e)
        {
            return 0L;
        }
    }
}