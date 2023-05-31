package com.stkpush.ncba.services;
import com.stkpush.ncba.controllers.StkPushRequestsController;
import com.stkpush.ncba.models.StkPushRequests;
import com.stkpush.ncba.repositories.StkPushRequestsRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Service
public class scheduledService {
    @Autowired
    DarajaService darajaService;
    @Autowired
    StkPushRequestsRepository stkPushRequestsRepository;
    @Value("${application.stkpush.transaction-to-process-per-cycle}")
    int transactionToProcessPerCycle;
    private  org.slf4j.Logger logger = LoggerFactory.getLogger(StkPushRequestsController.class);
    public Timestamp getCurrentTimeStamp() {
        final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return Timestamp.valueOf(sdf3.format(timestamp));
    }
    @Async
    public void sendRetailNotificationsAsync(  List<StkPushRequests> pushNotificationsList) {
        CompletableFuture.runAsync(() -> {
            try {
                for (StkPushRequests notification : pushNotificationsList) {
                    darajaService.sendToDaraja(notification);
                }
            } catch (Exception e) {
                logger.error("ASYNC POST STK PUSH ERROR MESSAGE  => {}" , e.getMessage());
            }
        });
    }
    @Scheduled(fixedDelayString = "${application.stkpush.to-push-api-polling-frequency-in-millis}")
    public void ProcessStkPushRequests() {
        try {
            GenerateUID generateUID=new GenerateUID();
            String guid = generateUID.getAlphaNumericString(10);
            String runID = guid + "_" + getCurrentTimeStamp();
            //update run id
            stkPushRequestsRepository.updateRequestsRunID(runID,transactionToProcessPerCycle);
            List<StkPushRequests> pushNotificationsList = stkPushRequestsRepository.getRequestToprocess(runID);
            if (pushNotificationsList.size() > 0) {
                logger.info("UPDATE PUSH STK PUSH  REQUESTS FROM THE DATABASE WITH RUN ID {}" , runID);
                logger.info("FOUND STK PUSH " + pushNotificationsList.size() + "  REQUESTS FROM THE DATABASE");
                sendRetailNotificationsAsync(pushNotificationsList);
            }
        } catch (Exception e) {
            logger.error("Process STK Push requests ERROR MESSAGE  => {}" , e.getMessage());
        }
    }
}
