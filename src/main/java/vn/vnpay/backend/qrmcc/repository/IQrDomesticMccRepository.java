package vn.vnpay.backend.qrmcc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.vnpay.common.qrbean.entity.mcc.QrDomesticMcc;


public interface IQrDomesticMccRepository extends JpaRepository<QrDomesticMcc, Long>, JpaSpecificationExecutor<QrDomesticMcc> {
    Page<QrDomesticMcc> findByTypeCodeOrBrandName(String typeName, String branchName, Pageable pageable);
}
