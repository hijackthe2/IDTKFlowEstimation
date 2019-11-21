package com.example.idtk.controller;

import com.example.idtk.model.ReceiveModel;
import com.example.idtk.service.IDTKService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 面向设备终端的接口
 */
@RestController
@RequestMapping("/idtk")
public class IDTKController {

    private static Logger logger = Logger.getLogger(IDTKController.class);

    @Autowired
    private IDTKService idtkService;

    @PermitAll
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public String receive(HttpServletRequest request) {
        ReceiveModel model = new ReceiveModel();
        model.setCmd(request.getParameter("cmd"));
        model.setCount(request.getParameter("count"));
        model.setFlag(request.getParameter("flag"));
        model.setStatus(request.getParameter("status"));
        model.setData(Arrays.asList(request.getParameterValues("data")));
        return idtkService.receiveData(model);
    }

    public String transferToServer(HttpServletRequest request){
        FormBody.Builder builder = new FormBody.Builder();

        //TODO request
//        builder.add("cmd", request.getParameter("cmd"));
//        builder.add("flag", request.getParameter("flag"));
//        builder.add("status", request.getParameter("status"));
//        builder.add("data", Arrays.toString(request.getParameterValues("data")));
//        builder.add("count", request.getParameter("count"));
        Map<String, String[]> map = request.getParameterMap();
        for(Map.Entry<String, String[]> m : map.entrySet()){
            builder.add(m.getKey(), Arrays.toString(m.getValue()));
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = builder.build();
        Request req = new Request.Builder()
                .url("url")
                .post(formBody)
                .build();
        String responseBody = null;
        try (Response response = okHttpClient.newCall(req).execute()) {
            if (response.body() != null) {
                responseBody = response.body().string();
            }
            return "result=" + responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "result=" + "00";
    }
}