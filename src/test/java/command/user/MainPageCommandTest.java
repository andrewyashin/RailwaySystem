package command.user;

import model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import util.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Configuration.class})
public class MainPageCommandTest {
    private static final String USER_ATTRIBUTE = "user";
    private static final String LOGIN_JSP = "login.jsp";
    private static final String DATE_JSP = "date.jsp";


    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private User user;
    private Configuration configuration;
    private MainPageCommand mainPageCommand;

    @Before
    public void setUp() {
        mainPageCommand = new MainPageCommand();
        PowerMockito.mockStatic(Configuration.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        user = mock(User.class);
        configuration = mock(Configuration.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
        when(Configuration.getInstance()).thenReturn(configuration);

    }

    @Test
    public void shouldRedirectToLoginPageWhenUserNotInSession() throws Exception {
        when(configuration.getConfig(Configuration.LOGIN)).thenReturn(LOGIN_JSP);
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(null);
        String result = mainPageCommand.execute(request, response);

        assertEquals(LOGIN_JSP, result);
    }

    @Test
    public void shouldReturnDatePage() throws Exception {
        when(configuration.getConfig(Configuration.DATE)).thenReturn(DATE_JSP);

        String result = mainPageCommand.execute(request, response);

        assertEquals(DATE_JSP, result);
    }

}
