package org.devnull.orm.boot

import org.dbunit.AbstractDatabaseTester
import org.dbunit.database.DatabaseDataSourceConnection
import org.dbunit.database.IDatabaseConnection

import javax.sql.DataSource

/**
 * Extension to DB Unit's framework which takes a configured
 * DataSource instead of JDBC url, password, etc.
 */
class DataSourceDatabasePopulator extends AbstractDatabaseTester {
    DataSource dataSource

    DataSourceDatabasePopulator(DataSource dataSource) {
        this.dataSource = dataSource
    }

    IDatabaseConnection getConnection() {
        return new DatabaseDataSourceConnection(dataSource)
    }
}
