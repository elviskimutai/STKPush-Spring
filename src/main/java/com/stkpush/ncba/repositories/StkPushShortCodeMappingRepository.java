package com.stkpush.ncba.repositories;
import com.stkpush.ncba.models.StkPushShortCodeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StkPushShortCodeMappingRepository extends JpaRepository<StkPushShortCodeMapping,Long> {
    StkPushShortCodeMapping findByShortcode(String shortcode);
}
