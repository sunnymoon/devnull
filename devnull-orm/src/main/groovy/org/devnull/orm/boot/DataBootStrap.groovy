package org.devnull.orm.boot

import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.csv.CsvDataSet
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.io.Resource

import javax.sql.DataSource
import org.dbunit.IDatabaseTester

/**
 *  Import data into your mock database.
 */
class DataBootStrap implements InitializingBean {

    final def log = LoggerFactory.getLogger(this.class)

    /**
     * DBUnit database tester.
     *
     * @see DataSourceDatabasePopulator
     */
    IDatabaseTester tester

    /**
     * Hibernate DDL strategy being used
     */
    String ddlStrategy

    /**
     * If this value matches the injected ddlStrategy, bootstrap will populate data.
     *
     * Default: create-drop
     */
    String triggerStrategy = "create-drop"

    /**
     * Directory containing the CSV files and table-ordering.txt file (see dbunit docs)
     */
    Resource csvImportsPath


    void afterPropertiesSet() {
        if (ddlStrategy == triggerStrategy) {
            log.info("Triggering data bootstrap for {} ...", csvImportsPath.file)
            IDataSet dataSet = new CsvDataSet(csvImportsPath.file)
            tester.setDataSet(dataSet)
            tester.onSetup()
            log.info("Done bootstrapping data.")

        } else {
            log.debug("Skipping data bootstrap because trigger {} != {}", triggerStrategy, ddlStrategy)
        }
    }
}