package ch.bbw.pr.sospri.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @class: ReCaptchaValidationService
 * @author: Anes Hodza
 * @version: 16.05.2022
 **/

@Service
public class ReCaptchaValidationService {

    @Autowired
    CaptchaSettings captchaSettings;

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", captchaSettings.getSecret());
        requestMap.add("response", captchaResponse);

        ReCaptchResponseType apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap,
                ReCaptchResponseType.class);
        if (apiResponse == null) {
            return false;
        }
        return Boolean.TRUE.equals(apiResponse.isSuccess());
    }
}
