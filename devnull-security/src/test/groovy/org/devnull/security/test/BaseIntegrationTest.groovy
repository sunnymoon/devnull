package org.devnull.security.test

import org.springframework.transaction.annotation.Transactional
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = [ 'classpath:test-context.xml' ])
@Transactional('transactionManager')
abstract class BaseIntegrationTest {
}
