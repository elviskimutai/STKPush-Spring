package com.stkpush.ncba.controllers;
import com.stkpush.ncba.models.ApiResponse;
import com.stkpush.ncba.services.IReceiveStkPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/c2b")
public class StkPushRequestsController {
    private Logger logger = LoggerFactory.getLogger(StkPushRequestsController.class);
    static HttpServletRequest request;
    @Autowired
    IReceiveStkPushService receiveStkPushService;
    @Autowired
    public void setRequest(HttpServletRequest request) {
        StkPushRequestsController.request = request;
    }
    private  String getClientIp() {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stkpush")
    ApiResponse receiveNotification(@RequestBody com.stkpush.ncba.models.StkPushRequestBody request) {
        String clientIP = getClientIp();
        logger.info("Received StkPush Request Body ===> {} From IP===> {}", request, clientIP);
        ApiResponse apiResponse = receiveStkPushService.receiveStkPush(request);
        return apiResponse;
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/stkpushresult")
    void receiveStkPushResult(@RequestBody com.stkpush.ncba.models.StkResult request) {
        String clientIP = getClientIp();
        receiveStkPushService.updateStkPushResult(request);
        logger.info("Received StkPush Result Body ===> {} From IP===> {}", request, clientIP);

    }
}
