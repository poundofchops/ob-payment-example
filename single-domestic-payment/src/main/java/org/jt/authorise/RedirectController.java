package org.jt.authorise;

import org.jt.configuration.RestClientConfiguration;
import org.jt.submit.SingleImmediatePaymentSubmit;
import org.jt.submit.SubmitRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RedirectController {

    private static final Logger logger = LoggerFactory.getLogger(RedirectController.class);

    private RestClientConfiguration restClientConfiguration;

    private SingleImmediatePaymentSubmit singleImmediatePaymentSubmit;

    @Autowired
    private void setSubmitService(SingleImmediatePaymentSubmit singleImmediatePaymentSubmit){
        this.singleImmediatePaymentSubmit = singleImmediatePaymentSubmit;
    }

    @Autowired
    private void setConfiguration(RestClientConfiguration restClientConfiguration){
        this.restClientConfiguration = restClientConfiguration;
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request){
        return "callback";
    }

    @PostMapping("/submit")
    @ResponseBody
    public String submit(@RequestParam("code") String code,
                         @RequestParam("id_token") String idToken,
                         @RequestParam("state") String state) {
        logger.info("++ Submit request received ++");
        logger.info("++ Code="+code);
        logger.info("++ id_token="+idToken);
        logger.info("++ State="+state);

        SubmitRequest submitRequest = new SubmitRequest(code, idToken, state);
        singleImmediatePaymentSubmit.submit(submitRequest);

        return "{\"consent_id\":\""+submitRequest.getConsentId()+"\"}";
    }

}
