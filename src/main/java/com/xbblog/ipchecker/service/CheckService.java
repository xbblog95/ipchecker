package com.xbblog.ipchecker.service;


import com.xbblog.ipchecker.utils.MonitorUtils;
import org.springframework.stereotype.Service;

@Service
public class CheckService {


    public long testTcpActive(String host, int port) {
        return MonitorUtils.testTCPActive(host,port);
    }
}
