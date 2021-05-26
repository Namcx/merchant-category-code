package vn.vnpay.backend.qrmcc.service;

import vn.vnpay.common.qrcommon.bean.request.mcc.DomesticMccRequest;
import vn.vnpay.common.qrcommon.bean.response.BaseResponse;

public interface IQrDomesticMccService {
    BaseResponse findAllByPageAble(int currentPage,int pageSize);

    BaseResponse findByMcCodeOrName(DomesticMccRequest mccRequest, int currentPage, int pageSize);

    BaseResponse unlockDomesticMcc(Long id);

    BaseResponse lockDomesticMcc(Long id);

    BaseResponse findById(Long id);

    BaseResponse saveDomesticMcc(DomesticMccRequest mccRequest);

    BaseResponse updateDomesticMcc(DomesticMccRequest mccRequest);
}
