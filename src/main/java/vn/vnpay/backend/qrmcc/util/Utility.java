package vn.vnpay.backend.qrmcc.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.vnpay.backend.qrmcc.constant.Constants;
import vn.vnpay.common.qrcommon.bean.request.mcc.DomesticMccRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doubleboo
 * Date: 4/20/2021
 * Time: 1:35 PM
 */

@Component
@Slf4j
public class Utility {
    public boolean validateRegexWithPattern(String str, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * @param domesticMccRequest
     * @return
     */
    public boolean validateGetDomesticMccRequest(DomesticMccRequest domesticMccRequest) {
        if (!validateGetPageable(domesticMccRequest.getPageSize()
                , domesticMccRequest.getCurrentPage())) {
            log.warn("Pageable is invalid Pls try again");
            return false;
        }
        return validateOptionalField(domesticMccRequest);
    }

    public boolean validateGetPageable(String pageSize, String currentPage) {
        log.info("Start validatePageable!");
        if (!validateRegexWithPattern(pageSize, Constants.REGEX_NUMBER) ||
                !validateRegexWithPattern(currentPage, Constants.REGEX_NUMBER)) {
            return false;
        }
        return Integer.parseInt(pageSize) > 0 || Integer.parseInt(currentPage) > 0;
    }

    /**
     * check id not null
     *
     * @param id
     * @return boolean
     */
    public boolean validateId(String id) {
        log.info("Start validate id");
        if (validateRegexWithPattern(id, Constants.REGEX_NUMBER)) {
            return false;
        }
        return id.length() > 0 && Integer.parseInt(id) > 0;
    }

    public boolean validateInsertDomesticMcc(DomesticMccRequest domesticMccRequest) {
        log.info("Start validate insertDomesticMcc");
        if (domesticMccRequest.getTypeCode() == null ||
                domesticMccRequest.getTypeCode().trim().length() != Constants.MAX_LENGTH_TYPE_CODE) {
            return false;
        }
        if (domesticMccRequest.getBrandName() == null ||
                (domesticMccRequest.getBrandName().length() > Constants.MAX_LENGTH_BRAND_NAME
                        || domesticMccRequest.getBrandName().length() < Constants.MIN_LENGTH_BRAND_NAME)) {
            return false;
        }
        return true;
    }

    public boolean validateOptionalField(DomesticMccRequest domesticMccRequest) {
        if (domesticMccRequest.getTypeCode() != null) {
            return domesticMccRequest.getTypeCode().trim().length() == Constants.MAX_LENGTH_TYPE_CODE;
        }
        if (domesticMccRequest.getBrandName() != null) {
            return domesticMccRequest.getBrandName().length() <= Constants.MAX_LENGTH_BRAND_NAME
                    || domesticMccRequest.getBrandName().length() >= Constants.MIN_LENGTH_BRAND_NAME;
        }
        return true;
    }

    public boolean validateUpdateDomesticMcc(DomesticMccRequest domesticMccRequest) {
        if (!validateId(domesticMccRequest.getId())) {
            return false;
        }
        return validateOptionalField(domesticMccRequest);
    }

}
