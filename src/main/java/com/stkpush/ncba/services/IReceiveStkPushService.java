package com.stkpush.ncba.services;

import com.stkpush.ncba.models.ApiResponse;
import com.stkpush.ncba.models.StkPushRequestBody;

public interface IReceiveStkPushService {
    ApiResponse receiveStkPush(StkPushRequestBody body);
    void updateStkPushResult(com.stkpush.ncba.models.StkResult request);
}
