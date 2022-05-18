package ch.bbw.pr.sospri.captcha;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @class: CaptchaSettings
 * @author: Anes Hodza
 * @version: 16.05.2022
 **/

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaSettings {
    private String site;
    private String secret;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
