package vn.vnpay.backend.qrmcc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpay.backend.qrmcc.service.IQrDomesticMccService;
import vn.vnpay.backend.qrmcc.util.Utility;
import vn.vnpay.common.qrcommon.bean.request.mcc.DomesticMccRequest;
import vn.vnpay.common.qrcommon.bean.response.BaseResponse;
import vn.vnpay.common.qrcommon.util.QrDomesticMccResponse;

@RestController
@RequestMapping("/domesticMcc")
@Slf4j
public class DomesticMccController {
    @Autowired
    private IQrDomesticMccService iQrDomesticMccService;
    @Autowired
    private Utility utility;
    private BaseResponse response = new BaseResponse();
    private boolean isValidated;

    @PostMapping("/findAllDomestic")
    public ResponseEntity<BaseResponse> findAllDomestic(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin findAllDomestic with request: {}", dataRequest);
        isValidated = utility.validateGetPageable(dataRequest.getPageSize(), dataRequest.getCurrentPage());
        log.info("End validate pageable!");
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        int pageSize = Integer.parseInt(dataRequest.getPageSize());
        int currentPage = Integer.parseInt(dataRequest.getCurrentPage());
        response = iQrDomesticMccService.findAllByPageAble(currentPage, pageSize);

        long endTimeAPI = System.currentTimeMillis();
        log.info("End findAllDomestic with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/findByTypeOrBrandName")
    public ResponseEntity<BaseResponse> findByTypeOrBrandName(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin findByTypeOrBrandName with request: {}", dataRequest);
        isValidated = utility.validateGetDomesticMccRequest(dataRequest);
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        int pageSize = Integer.parseInt(dataRequest.getPageSize());
        int currentPage = Integer.parseInt(dataRequest.getCurrentPage());
        response = iQrDomesticMccService.findByMcCodeOrName(dataRequest, currentPage, pageSize);
        long endTimeAPI = System.currentTimeMillis();
        log.info("End findByTypeOrBrandName with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/findById")
    public ResponseEntity<BaseResponse> findById(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin findById with id: {}", dataRequest.getId());
        isValidated = utility.validateId(dataRequest.getId());
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Long id = Long.valueOf(dataRequest.getId());

        response = iQrDomesticMccService.findById(id);
        long endTimeAPI = System.currentTimeMillis();
        log.info("End findById with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/saveDomesticMcc")
    public ResponseEntity<BaseResponse> saveDomesticMcc(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin saveDomesticMcc with request: {}", dataRequest);
        isValidated = utility.validateInsertDomesticMcc(dataRequest);
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = iQrDomesticMccService.saveDomesticMcc(dataRequest);
        long endTimeAPI = System.currentTimeMillis();
        log.info("End saveDomesticMcc with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateDomesticMcc")
    public ResponseEntity<BaseResponse> updateDomesticMcc(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin updateDomesticMcc with request: {}", dataRequest);
        isValidated = utility.validateUpdateDomesticMcc(dataRequest);
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response = iQrDomesticMccService.updateDomesticMcc(dataRequest);
        long endTimeAPI = System.currentTimeMillis();
        log.info("End updateDomesticMcc with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/lockDomesticMcc")
    public ResponseEntity<BaseResponse> lockDomesticMcc(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin lockDomesticMcc with request: {}", dataRequest);
        isValidated = utility.validateId(dataRequest.getId());
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Long id = Long.parseLong(dataRequest.getId());
        response = iQrDomesticMccService.lockDomesticMcc(id);
        long endTimeAPI = System.currentTimeMillis();
        log.info("End lockDomesticMcc with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/unlockDomesticMcc")
    public ResponseEntity<BaseResponse> unlockDomesticMcc(@RequestBody DomesticMccRequest dataRequest) {
        long startTimeAPI = System.currentTimeMillis();
        log.info("Begin unlockDomesticMcc with request: {}", dataRequest);
        isValidated = utility.validateId(dataRequest.getId());
        if (!isValidated) {
            response.setCode(QrDomesticMccResponse.VALIDATE_FAIL.getCode());
            response.setMessage(QrDomesticMccResponse.VALIDATE_FAIL.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Long id = Long.parseLong(dataRequest.getId());
        response = iQrDomesticMccService.unlockDomesticMcc(id);
        long endTimeAPI = System.currentTimeMillis();
        log.info("End unlockDomesticMcc with data: {} - total handle time: [{} ms].", response, endTimeAPI - startTimeAPI);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
