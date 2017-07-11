package web.controller.localization;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import static web.controller.localization.CommandLanguageUtil.USER_ATTRIBUTE;

@Controller
public class LocalizationController {

    @RequestMapping("/EN")
    public String makeEN(HttpServletRequest request){
        request.getSession(false).setAttribute(USER_ATTRIBUTE, null);
        Config.set(request.getSession(false), Config.FMT_LOCALE, CommandLanguageUtil.ENGLISH);
        return Configuration.getInstance().getConfig(Configuration.LOGIN);
    }

    @RequestMapping("/UKR")
    public String makeUKR(HttpServletRequest request){
        request.getSession(false).setAttribute(USER_ATTRIBUTE, null);
        Config.set(request.getSession(false), Config.FMT_LOCALE, CommandLanguageUtil.UKRAINIAN);
        return Configuration.getInstance().getConfig(Configuration.LOGIN);
    }
}
