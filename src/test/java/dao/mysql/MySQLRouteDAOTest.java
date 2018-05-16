package dao.mysql;

import dao.ConnectionPool;
import model.entity.Route;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionPool.class)
public class MySQLRouteDAOTest {
    private static final String FIND_ALL_QUERY = "SELECT * FROM route";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM ROUTE WHERE id=? ";

    private ConnectionPool connectionPool;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private MySQLRouteDAO mySQLRouteDAO;

    @Captor
    private ArgumentCaptor<String> queryArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ConnectionPool.class);
        connectionPool = mock(ConnectionPool.class);
        connection = mock(Connection.class);
        statement = mock(Statement.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        mySQLRouteDAO = MySQLRouteDAO.getInstance();

        when(ConnectionPool.getInstance()).thenReturn(connectionPool);
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void shouldBeSimilarQueriesFindAllRoutes() throws Exception {
        List<Route> resultList = mySQLRouteDAO.findAll();
        verify(statement).executeQuery(queryArgumentCaptor.capture());
        assertEquals(FIND_ALL_QUERY, queryArgumentCaptor.getValue());
    }

    @Test
    public void shouldBeSimilarQueriesFindById() throws Exception {
        Route route = mySQLRouteDAO.findById(1L);
        verify(connection).prepareStatement(queryArgumentCaptor.capture());
        assertEquals(FIND_BY_ID_QUERY, queryArgumentCaptor.getValue());
    }

}
