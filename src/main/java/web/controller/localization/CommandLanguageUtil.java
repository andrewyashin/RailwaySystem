package web.controller.localization;

import java.util.Locale;

class CommandLanguageUtil {
    private static final String LANGUAGE_UK = "uk";
    private static final String COUNTRY_UA = "UA";

    private static final String LANGUAGE_EN = "en";
    private static final String COUNTRY_US = "US";

    static final Locale UKRAINIAN = new Locale(LANGUAGE_UK, COUNTRY_UA);
    static final Locale ENGLISH = Locale.ENGLISH;

    static final String USER_ATTRIBUTE = "user";
}
