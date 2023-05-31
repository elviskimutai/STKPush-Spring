package com.stkpush.ncba.services;
import com.google.gson.Gson;
import com.stkpush.ncba.models.*;
import com.stkpush.ncba.repositories.StkPushRequestsRepository;
import com.stkpush.ncba.repositories.StkPushShortCodeMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.regex.Pattern;
@Service
public class ReceiveStkPushService implements IReceiveStkPushService {
    @Autowired
    ApplicationSettingsImpl applicationSettingsService;
    @Autowired
    StkPushRequestsRepository stkPushRequestsRepository;
    @Autowired
    StkPushShortCodeMappingRepository shortCodeMappingRepository;
    private Logger logger = LoggerFactory.getLogger(IReceiveStkPushService.class);
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    ApiResponse validateBody(StkPushRequestBody body) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode("0");
        if (body.getUsername() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("UserName field is required");
        }
        if (body.getPassword() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("Password field is required");
        }
        if (body.getAccountNo() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("AccountNo field is required");
        }
        if (body.getNetwork() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("Network field is required");
        }
        if (body.getTransactionType() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("TransactionType field is required");
        }else if(!body.getTransactionType().trim().equals("CustomerPayBillOnline") && !Objects.equals(body.getTransactionType(), "CustomerBuyGoodsOnline")){
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("TransactionType field is accepts only two values :: CustomerPayBillOnline or CustomerBuyGoodsOnline");
        }
        if (body.getPayBillNo() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("PayBillNo field is required");
        }
        if (body.getTelephoneNo() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("Telephone number field is required");
        } else {
            if (body.getTelephoneNo().trim().length() < 10 || !isNumeric(body.getTelephoneNo().trim()) || !"254".equals(body.getTelephoneNo().trim().substring(0, 3))) {
                apiResponse.setStatusCode("1");
                apiResponse.setStatusDescription("Invalid Telephone number, It should include the 254 prefix");
            }
        }
        if (body.getAmount() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("Amount field is required");
        } else {
            if (!isNumeric(body.getAmount().trim()) || body.getAmount().trim().contains(".") || Integer.parseInt(body.getAmount().trim()) < 1) {
                apiResponse.setStatusCode("1");
                apiResponse.setStatusDescription("Invalid Amount");
            }
        }
        if (body.getTransactionID() == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("TransactionID field is required");
        }
        return apiResponse;
    }

    @Override
    public ApiResponse receiveStkPush(StkPushRequestBody body) {
        logger.info("Called to create new stk push {}", body);
        ApiResponse apiResponse1 = validateBody(body);
        if (Objects.equals(apiResponse1.getStatusCode(), "1")) {
            logger.warn("Called to create new stk push {} failed validation failed => {}", body ,apiResponse1);
            return apiResponse1;
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTransactionID(body.getTransactionID());
        String stkPushApiUsername = applicationSettingsService.getValue("STKPUSHAPIUSERNAME").getValue();
        String stkPushApiPassword = applicationSettingsService.getValue("STKPUSHAPIPASSWORD").getValue();
        if (!Objects.equals(stkPushApiUsername, body.getUsername()) && !Objects.equals(stkPushApiPassword, body.getPassword())) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("Invalid api credentials");
            logger.warn("Called to create new stk push {} failed Invalid api credential provided", body);
            return apiResponse;
        }
        //check duplicate
        StkPushRequests byTransactionID = stkPushRequestsRepository.findByTransactionID(body.getTransactionID());
        if (byTransactionID != null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("Duplicate transaction id " + body.getTransactionID());
            logger.warn("Called to create new stk push {} failed Duplicate transaction id", body);
            return apiResponse;
        }
        //check shortcode if enabled for stk push
        StkPushShortCodeMapping byShortcode = shortCodeMappingRepository.findByShortcode(body.getPayBillNo());
        if (byShortcode == null) {
            apiResponse.setStatusCode("1");
            apiResponse.setStatusDescription("shortcode not set for stk push " + body.getPayBillNo());
            logger.warn("Called to create new stk push {} failed shortcode {} not mapped for stk push", body,body.getPayBillNo());
            return apiResponse;
        }
        StkPushRequests stkPushRequests = new StkPushRequests();
        stkPushRequests.setStatus(0);
        stkPushRequests.setTransactionType(body.getTransactionType());
        stkPushRequests.setTelephoneNo(body.getTelephoneNo());
        stkPushRequests.setAmount(body.getAmount());
        stkPushRequests.setTransactionID(body.getTransactionID());
        stkPushRequests.setPayBillNo(body.getPayBillNo());
        stkPushRequests.setAccountNo(body.getAccountNo());
        stkPushRequests.setNetwork(body.getNetwork());
        stkPushRequests.setRemarks("Stk push request received");
        final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp now = Timestamp.valueOf(sdf3.format(timestamp));
        stkPushRequests.setCreatedAt(now);
        stkPushRequests.setUpdatedAt(now);
        StkPushRequests save = stkPushRequestsRepository.save(stkPushRequests);
        apiResponse.setStatusDescription("Received successfully");
        apiResponse.setTransactionID(body.getTransactionID());
        apiResponse.setReferenceID(String.valueOf(save.getId()));
        apiResponse.setStatusCode("0");
        logger.info("Create new stk push {} success  returning response {}", stkPushRequests, apiResponse);
        return apiResponse;
    }

    @Override
    public void updateStkPushResult(StkResult request) {
        logger.info("Received STK Result {}" ,request);
        Body body = request.getBody();
        com.stkpush.ncba.models.stkCallback callBack=   body.getStkCallback();
        String merchantRequestID = callBack.getMerchantRequestID();
        StkPushRequests byMerchantRequestID = stkPushRequestsRepository.findByMerchantRequestID(merchantRequestID);
        if(byMerchantRequestID != null){
            byMerchantRequestID.setCheckoutRequestID(callBack.getCheckoutRequestID());
            byMerchantRequestID.setResponseDesc(callBack.getResultDesc());

            Gson gson = new Gson();
            String json = gson.toJson(callBack.getCallbackMetadata());
            byMerchantRequestID.setResponseBody(json);
            stkPushRequestsRepository.save(byMerchantRequestID);
            logger.info("Updated STK Result {} for request {} " ,request,byMerchantRequestID);
        }else{
            logger.info("StkPushRequests record is null  STK Result merchant id  {} for request {} " ,merchantRequestID,request);
        }

    }
}
