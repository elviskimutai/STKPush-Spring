package com.stkpush.ncba.repositories;
import com.stkpush.ncba.models.StkPushRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface StkPushRequestsRepository extends JpaRepository<StkPushRequests,Long> {
    StkPushRequests findByTransactionID(String transactionID);
    StkPushRequests findByMerchantRequestID(String merchantRequestID);
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE stk_push_requests set run_id=:run_id,status=3 WHERE ROWNUM < :transactionToProcessPerCycle AND status=0", nativeQuery = true)
    int updateRequestsRunID(@Param("run_id") String run_id,@Param("transactionToProcessPerCycle") int transactionToProcessPerCycle);
    @Query(value = " SELECT * FROM  stk_push_requests  WHERE run_id=:run_id and status=3 ", nativeQuery = true)
    List<StkPushRequests> getRequestToprocess(@Param("run_id") String run_id);
}
