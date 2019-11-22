package com.example.idtk.controller;

import com.example.idtk.model.ReceiveModel;
import com.example.idtk.service.IDTKService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 面向设备终端的接口
 */
@RestController
@RequestMapping("/idtk")
public class IDTKController {

    private static Logger logger = Logger.getLogger(IDTKController.class);

    private String idtkUrl = "http://idtk.buyou.net/v1/idtk";

    @Autowired
    private IDTKService idtkService;

    @PermitAll
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public String receive(HttpServletRequest request) {

        String returnRemote = idtkService.transferToServer(request, idtkUrl);

        ReceiveModel model = new ReceiveModel();
        model.setCmd(request.getParameter("cmd"));
        model.setCount(request.getParameter("count"));
        model.setFlag(request.getParameter("flag"));
        model.setStatus(request.getParameter("status"));
        model.setData(Arrays.asList(request.getParameterValues("data")));
//        return idtkService.receiveData(model);

        String returnLocal = idtkService.receiveData(model);

        logger.info("from remote server: " + returnRemote.toUpperCase());
        logger.info("from local server:  " + returnLocal.toUpperCase());
        logger.info("equal: " + returnLocal.equals(returnRemote));
        return returnRemote;
    }
    
}