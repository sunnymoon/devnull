package org.devnull.orm.boot

import org.dbunit.IDatabaseTester
import org.junit.Test
import org.springframework.core.io.ClassPathResource

import static org.mockito.Mockito.*

class DataBootStrapTest {

    @Test
    void afterPropertiesSetShouldInvokeTesterIfDdlMatchesTrigger() {
        def bootStrap = new DataBootStrap(
                ddlStrategy: "foo",
                triggerStrategy: "foo",
                tester: mock(IDatabaseTester),
                csvImportsPath: new ClassPathResource("/data")
        )
        bootStrap.afterPropertiesSet()
        verify(bootStrap.tester).onSetup()
    }

    @Test
    void afterPropertiesSetShouldNotInvokeTesterIfDdlDoesNotMatchTrigger() {
        def bootStrap = new DataBootStrap(
                ddlStrategy: "foo",
                triggerStrategy: "bar",
                tester: mock(IDatabaseTester),
                csvImportsPath: new ClassPathResource("/data")
        )
        bootStrap.afterPropertiesSet()
        verify(bootStrap.tester, never()).onSetup()
    }
}
