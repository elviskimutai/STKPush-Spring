package com.stkpush.ncba.repositories;
import com.stkpush.ncba.models.ApplicationSettingsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationSettingsRepository extends JpaRepository<ApplicationSettingsModel,Long> {
    @Query(value = "SELECT * FROM APPLICATION_SETTINGS WHERE name=:name",nativeQuery = true)
    ApplicationSettingsModel getValue(@Param("name") String name);
}
