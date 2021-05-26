package vn.vnpay.backend.qrmcc.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.vnpay.backend.qrmcc.constant.Constants;
import vn.vnpay.backend.qrmcc.repository.IQrDomesticMccRepository;
import vn.vnpay.backend.qrmcc.service.IQrDomesticMccService;
import vn.vnpay.common.qrbean.entity.mcc.QrDomesticMcc;
import vn.vnpay.common.qrcommon.bean.request.mcc.DomesticMccRequest;
import vn.vnpay.common.qrcommon.bean.response.BaseResponse;
import vn.vnpay.common.qrcommon.bean.response.ResponseCode;
import vn.vnpay.common.qrcommon.util.QrDomesticMccResponse;

import java.util.Optional;

@Service
@Slf4j
public class QrDomesticMccServiceImpl implements IQrDomesticMccService {

    @Autowired
    private IQrDomesticMccRepository iQrDomesticMccRepository;

    private final BaseResponse response = new BaseResponse();

    @Override
    public BaseResponse findAllByPageAble(int currentPage, int pageSize) {
        Pageable pageRequest = PageRequest.of(currentPage, pageSize);
        log.info("Start findAllByPageAble function.");
        try {
            Page<QrDomesticMcc> qrDomesticMccs = iQrDomesticMccRepository.findAll(pageRequest);
            log.info("End getMerchantDeniedDetail function.");
            if (qrDomesticMccs.isEmpty()) {
                response.setCode(QrDomesticMccResponse.LIST_NOT_FOUND.getCode());
                response.setMessage(QrDomesticMccResponse.LIST_NOT_FOUND.getMessage());
                return response;
            }
            response.setCode(QrDomesticMccResponse.SUCCESS.getCode());
            response.setMessage(QrDomesticMccResponse.SUCCESS.getMessage());
            response.setData(qrDomesticMccs);
            return response;
        } catch (Exception ex) {
            log.error("Function findAllByPageAble has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }

    @Override
    public BaseResponse findByMcCodeOrName(DomesticMccRequest domesticMccRequest, int currentPage, int pageSize) {
        Pageable pageRequest = PageRequest.of(currentPage, pageSize);
        log.info("Start findByMcCodeOrName function.");
        try {
            Page<QrDomesticMcc> qrDomesticMccs = iQrDomesticMccRepository.findByTypeCodeOrBrandName(
                    domesticMccRequest.getTypeCode(), domesticMccRequest.getBrandName(), pageRequest);
            log.info("End findByMcCodeOrName function.");
            if (qrDomesticMccs.isEmpty()) {
                response.setCode(QrDomesticMccResponse.LIST_NOT_FOUND.getCode());
                response.setMessage(QrDomesticMccResponse.LIST_NOT_FOUND.getMessage());
                return response;
            }
            response.setCode(QrDomesticMccResponse.SUCCESS.getCode());
            response.setMessage(QrDomesticMccResponse.SUCCESS.getMessage());
            response.setData(qrDomesticMccs);
            return response;
        } catch (Exception ex) {
            log.error("Function findAllByPageAble has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }

    @Override
    public BaseResponse unlockDomesticMcc(Long id) {
        log.info("Start unlockDomesticMcc function.");
        QrDomesticMcc qrDomesticMcc;
        try {
            Optional<QrDomesticMcc> opsQrDomesticMcc = iQrDomesticMccRepository.findById(id);
            if (opsQrDomesticMcc.isPresent()) {
                qrDomesticMcc = opsQrDomesticMcc.get();
                if (qrDomesticMcc.getStatus() == Constants.IS_UNLOCK_DOMESTIC_MCC) {
                    response.setCode(QrDomesticMccResponse.WAS_UNLOCKED.getCode());
                    response.setMessage(QrDomesticMccResponse.WAS_UNLOCKED.getMessage());
                    return response;
                }
                qrDomesticMcc.setStatus(Constants.IS_UNLOCK_DOMESTIC_MCC);
                iQrDomesticMccRepository.saveAndFlush(qrDomesticMcc);
                log.info("End unlockDomesticMcc function.");
                response.setCode(QrDomesticMccResponse.UNLOCK_SUCCESS.getCode());
                response.setMessage(QrDomesticMccResponse.UNLOCK_SUCCESS.getMessage());
                return response;
            }
            response.setCode(QrDomesticMccResponse.NOT_EXIST.getCode());
            response.setMessage(QrDomesticMccResponse.NOT_EXIST.getMessage());
            return response;
        } catch (Exception ex) {
            log.error("Function unlockDomesticMcc has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }

    @Override
    public BaseResponse lockDomesticMcc(Long id) {
        QrDomesticMcc qrDomesticMcc;
        log.info("Start lockDomesticMcc function.");
        try {
            Optional<QrDomesticMcc> opsQrDomesticMcc = iQrDomesticMccRepository.findById(id);
            if (opsQrDomesticMcc.isPresent()) {
                qrDomesticMcc = opsQrDomesticMcc.get();
                if (qrDomesticMcc.getStatus() == Constants.IS_LOCK_DOMESTIC_MCC) {
                    response.setCode(QrDomesticMccResponse.WAS_LOCKED.getCode());
                    response.setMessage(QrDomesticMccResponse.WAS_LOCKED.getMessage());
                    return response;
                }
                qrDomesticMcc.setStatus(Constants.IS_LOCK_DOMESTIC_MCC);
                iQrDomesticMccRepository.saveAndFlush(qrDomesticMcc);
                log.info("End lockDomesticMcc function.");
                response.setCode(QrDomesticMccResponse.LOCK_SUCCESS.getCode());
                response.setMessage(QrDomesticMccResponse.LOCK_SUCCESS.getMessage());
                return response;
            }
            response.setCode(QrDomesticMccResponse.NOT_EXIST.getCode());
            response.setMessage(QrDomesticMccResponse.NOT_EXIST.getMessage());
            return response;
        } catch (Exception ex) {
            log.error("Function lockDomesticMcc has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }

    @Override
    public BaseResponse findById(Long id) {
        log.info("Start findById function.");
        try {
            Optional<QrDomesticMcc> qrDomesticMccs = iQrDomesticMccRepository.findById(id);
            log.info("End findById function.");
            if (qrDomesticMccs.isPresent()) {
                response.setCode(QrDomesticMccResponse.SUCCESS.getCode());
                response.setMessage(QrDomesticMccResponse.SUCCESS.getMessage());
                response.setData(qrDomesticMccs.get());
                return response;
            }
            response.setCode(QrDomesticMccResponse.NOT_EXIST.getCode());
            response.setMessage(QrDomesticMccResponse.NOT_EXIST.getMessage());
            return response;
        } catch (Exception ex) {
            log.error("Function findById has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }

    @Override
    public BaseResponse saveDomesticMcc(DomesticMccRequest mccRequest) {
        log.info("Start saveDomesticMcc function.");
        try {
            QrDomesticMcc qrDomesticMcc = QrDomesticMcc.builder()
                    .brandName(mccRequest.getBrandName())
                    .typeCode(mccRequest.getTypeCode())
                    .fullName(mccRequest.getBrandName())
                    .status(Constants.IS_UNLOCK_DOMESTIC_MCC)
                    .description(mccRequest.getDescription()).build();
            iQrDomesticMccRepository.saveAndFlush(qrDomesticMcc);
            log.info("End saveDomesticMcc function.");
            response.setCode(QrDomesticMccResponse.INSERT_SUCCESS.getCode());
            response.setMessage(QrDomesticMccResponse.INSERT_SUCCESS.getMessage());
            return response;
        } catch (Exception ex) {
            log.error("Function saveDomesticMcc has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }

    @Override
    public BaseResponse updateDomesticMcc(DomesticMccRequest mccRequest) {
        log.info("Start updateDomesticMcc function.");
        try {
            Optional<QrDomesticMcc> opsQrDomesticMcc = iQrDomesticMccRepository.findById(Long.parseLong(mccRequest.getId()));
            if (opsQrDomesticMcc.isPresent()) {
                QrDomesticMcc qrDomesticMcc = opsQrDomesticMcc.get();
                if (null != mccRequest.getTypeCode()) {
                    qrDomesticMcc.setTypeCode(mccRequest.getTypeCode());
                }
                if (null != mccRequest.getBrandName()) {
                    qrDomesticMcc.setBrandName(mccRequest.getBrandName());
                    qrDomesticMcc.setFullName(mccRequest.getBrandName());
                }
                if (null != mccRequest.getDescription()) {
                    qrDomesticMcc.setDescription(mccRequest.getDescription());
                }
                iQrDomesticMccRepository.saveAndFlush(qrDomesticMcc);
                log.info("End updateDomesticMcc function.");
                response.setCode(QrDomesticMccResponse.UPDATE_SUCCESS.getCode());
                response.setMessage(QrDomesticMccResponse.UPDATE_SUCCESS.getMessage());
                return response;
            }
            response.setCode(QrDomesticMccResponse.NOT_EXIST.getCode());
            response.setMessage(QrDomesticMccResponse.NOT_EXIST.getMessage());
            return response;
        } catch (Exception ex) {
            log.error("Function updateDomesticMcc has exception: ", ex);
            response.setCode(ResponseCode.EXCEPTION.getCode());
            response.setMessage(ResponseCode.EXCEPTION.getMessage());
            return response;
        }
    }
}
