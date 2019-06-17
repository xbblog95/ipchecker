package com.xbblog.ipchecker.controller;

import com.xbblog.ipchecker.service.CheckService;
import com.xbblog.ipchecker.vo.ResultDataVo;
import com.xbblog.ipchecker.vo.ResultInsideVo;
import com.xbblog.ipchecker.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CheckController {

    @Autowired
    private CheckService checkService;

    @RequestMapping("api_v1")
    @ResponseBody
    public ResultVo testTcpActive(String host, String port) {
        ResultVo resultVo = new ResultVo();
        if (StringUtils.isEmpty(host)) {
            resultVo.setSuccess(false);
            resultVo.setMsg("host and port is must");
            return resultVo;
        }
        if (StringUtils.isEmpty(port)) {
            resultVo.setSuccess(false);
            resultVo.setMsg("host and port is must");
            return resultVo;
        }
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        //如果是ip
        if (host.matches(rexp))
        {
            if (isprivateIp(host))
            {
                resultVo.setSuccess(false);
                resultVo.setMsg(host + " is a priavte IP");
                return resultVo;
            }
        }
        try {
            Integer.parseInt(port);
        } catch (Exception e) {
            resultVo.setSuccess(false);
            resultVo.setMsg("port must be a number");
            return resultVo;
        }
        long time = checkService.testTcpActive(host, Integer.parseInt(port));
        ResultDataVo resultDataVo = new ResultDataVo();
        ResultInsideVo resultInsideVo = new ResultInsideVo();
        resultInsideVo.setTcp(time != 0);
        resultInsideVo.setTcptime(time);
        resultDataVo.setInside(resultInsideVo);
        resultVo.setData(resultDataVo);
        resultVo.setSuccess(time != 0);
        return resultVo;
    }

    public static boolean isprivateIp(String ip) {
        long ipNum = getIpNum(ip);
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        long ringBegin = getIpNum("127.0.0.0");
        long ringEnd = getIpNum("127.255.255.255");
        boolean isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd)
                || isInner(ipNum, cBegin, cEnd) || isInner(ipNum, ringBegin, ringEnd);
        return isInnerIp;
    }
    private static long getIpNum(String ip) {
        String[] ipArray = ip.split("\\.");
        long a = Integer.parseInt(ipArray[0]);
        long b = Integer.parseInt(ipArray[1]);
        long c = Integer.parseInt(ipArray[2]);
        long d = Integer.parseInt(ipArray[3]);
        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    /**
     *  判断IP转化后的数字是否是区间内
     *     
     */
    private static boolean isInner(long ipNum, long begin, long end) {
        return (ipNum >= begin) && (ipNum <= end);
    }

}
