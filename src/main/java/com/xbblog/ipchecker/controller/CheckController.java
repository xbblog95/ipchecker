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
    public ResultVo testTcpActive(String host, int port)
    {
        ResultVo resultVo = new ResultVo();
        if(StringUtils.isEmpty(host))
        {
            resultVo.setSuccess(false);
            resultVo.setMsg("host and port is must");
            return resultVo;
        }
        long time = checkService.testTcpActive(host, port);

        ResultDataVo resultDataVo = new ResultDataVo();
        ResultInsideVo resultInsideVo = new ResultInsideVo();
        resultInsideVo.setTcp(time != 0);
        resultInsideVo.setTcptime(time);
        resultDataVo.setInside(resultInsideVo);
        resultVo.setData(resultDataVo);
        resultVo.setSuccess(time != 0);
        return resultVo;
    }
}
