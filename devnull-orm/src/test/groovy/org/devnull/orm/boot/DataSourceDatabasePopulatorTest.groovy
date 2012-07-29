package org.devnull.orm.boot

import org.junit.Test

import javax.sql.DataSource

import static org.mockito.Mockito.*
import org.dbunit.database.DatabaseDataSourceConnection
import java.sql.Connection

class DataSourceDatabasePopulatorTest {

    @Test
    void getConnectionShouldUseConfiguredDataSource() {
        def mockDataSource = mock(DataSource)
        def mockConnection = mock(Connection)
        when(mockDataSource.getConnection()).thenReturn(mockConnection)
        def populator = new DataSourceDatabasePopulator(mockDataSource)
        def connection = populator.connection.connection
        verify(mockDataSource).getConnection()
        assert mockConnection.is(connection)
    }
}
