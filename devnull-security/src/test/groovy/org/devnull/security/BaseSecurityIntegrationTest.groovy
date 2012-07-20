package org.devnull.security

import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.runner.RunWith

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = [ 'classpath:test-data-context.xml', 'classpath:sample-security-context.xml' ])
@Transactional('transactionManager')
abstract class BaseSecurityIntegrationTest  {
}
