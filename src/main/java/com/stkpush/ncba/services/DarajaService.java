package com.stkpush.ncba.services;
import com.google.gson.Gson;
import com.stkpush.ncba.controllers.StkPushRequestsController;
import com.stkpush.ncba.models.StkPushJsonRequest;
import com.stkpush.ncba.models.StkPushRequests;
import com.stkpush.ncba.models.StkPushShortCodeMapping;
import com.stkpush.ncba.repositories.StkPushShortCodeMappingRepository;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import com.stkpush.ncba.repositories.StkPushRequestsRepository;
import java.util.Base64;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
@Service
public class DarajaService {
    private  org.slf4j.Logger logger = LoggerFactory.getLogger(StkPushRequestsController.class);
    @Autowired
     StkPushRequestsRepository stkPushRepository;
    @Autowired
    StkPushShortCodeMappingRepository shortCodeMappingRepository;
    @Autowired
    ApplicationSettingsImpl applicationSettingsService;
    @Value("${application.stkpush.connection-timeout}")
    int connectionTimeout;
    @Value("${application.stkpush.socket-timeout}")
    int socketTimeout;
    public  String getAccessToken(String username, String password, String smsTokenApi) {
        String token = null;
        try {
            Unirest.setTimeouts(connectionTimeout, socketTimeout);
            HttpResponse<JsonNode> response = Unirest.get(smsTokenApi)
                    .header("Content-Type", "application/json")
                    .basicAuth(username,password)
                    .asJson();
            JSONObject myObj = response.getBody().getObject();
            logger.info("Received Response on stkpush token api {}" , myObj);
            token = myObj.get("access_token").toString();
            logger.info("Received Access Token");
        } catch (UnirestException e) {
            logger.error("An Error while getting access token {} " , e.getMessage());
        }
        return token;
    }
    protected void sendToDaraja(StkPushRequests stkPush)  {
        logger.info("Sending to daraja "+stkPush.getId());
        StkPushShortCodeMapping byShortcode = shortCodeMappingRepository.findByShortcode(stkPush.getPayBillNo());
        String darajaStkPushUrl = applicationSettingsService.getValue("DarajaStkPushUrl").getValue();
        String darajaTokenApiUrl = applicationSettingsService.getValue("DarajaStkPushTokenUrl").getValue();
        String partyB = byShortcode.getPartyB();
        String businessShortCode = byShortcode.getShortcode();
        String c2BPasskey = byShortcode.getPassKey();
        String stkPushCallbackUrl = byShortcode.getCallBackURL();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        Base64.Encoder enc = Base64.getEncoder();
        String password =enc.encodeToString((businessShortCode+c2BPasskey+timeStamp).getBytes());
        StkPushJsonRequest payload = new StkPushJsonRequest();
        payload.setBusinessShortCode(businessShortCode);
        payload.setPassword(password);
        payload.setTimestamp(timeStamp);
        payload.setTransactionType(stkPush.getTransactionType());
        payload.setAmount(stkPush.getAmount() );
        payload.setPartyA(String.valueOf(stkPush.getTelephoneNo())); //todo decrypt this
        payload.setPartyB(partyB);
        payload.setPhoneNumber(String.valueOf(stkPush.getTelephoneNo()));
        payload.setCallBackURL(stkPushCallbackUrl);
        payload.setAccountReference(stkPush.getAccountNo() );
        payload.setTransactionDesc("StkPush Payment");
        logger.info("Payload to safaricom for stk push id "+stkPush.getId()+" ::: "+payload);
        String token= getAccessToken(byShortcode.getConsumerKey(), byShortcode.getConsumerSecret(), darajaTokenApiUrl);
        if(token == null){
            logger.warn("Unable to get token abort now!! {}" , stkPush);
            return;
        }
        //send to daraja
        Gson gson = new Gson();
        String json = gson.toJson(payload);
        logger.info("Payload to safaricom for stkpush id "+stkPush.getId()+" :: Json String :: "+json);
        try {
            Unirest.setTimeouts(connectionTimeout, socketTimeout);
            HttpResponse<JsonNode> response = Unirest.post(darajaStkPushUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " +token)
                    .body(json)
                    .asJson();
            JSONObject myObj = response.getBody().getObject();
            logger.info("Received Response on stkpush api {} {}" , stkPush,myObj);
            if (myObj != null && myObj.has("ResponseCode") && myObj.getInt("ResponseCode") == 0) {
                logger.info("Success posting stkpush request to safaricom :: {}" ,stkPush);
                        //success. Update DB
                        stkPush.setStatus(1);
                        stkPush.setMerchantRequestID(myObj.getString("MerchantRequestID"));
                        stkPush.setCheckoutRequestID(myObj.getString("CheckoutRequestID"));
                        stkPush.setResponseDesc(myObj.getString("ResponseDescription"));
                        stkPush.setResponseCode(String.valueOf(myObj.getInt("ResponseCode")));

            }else{
                stkPush.setStatus(2);
                stkPush.setResponseDesc(myObj.toString());
            }
            stkPushRepository.save(stkPush);
        }catch (Exception e){
            logger.error("Error occured while posting stkpush request to safaricom :: {}" ,e.getMessage());
            //Failed. Update DB
            stkPush.setStatus(2);
            stkPush.setResponseDesc(e.getMessage());
            stkPushRepository.save(stkPush);
        }

    }
}
