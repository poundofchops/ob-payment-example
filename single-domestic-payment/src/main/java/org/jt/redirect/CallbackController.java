package org.jt.redirect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CallbackController {

    private static final Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @RequestMapping("/callback")
    public RedirectResponse callback(@RequestParam Map<String,String> allParams){
        logger.info("++++ callback received with params -> "+allParams);
        return new RedirectResponse(200,
                            String.format("Auth code %s received", allParams.get("code")));
    }
}
