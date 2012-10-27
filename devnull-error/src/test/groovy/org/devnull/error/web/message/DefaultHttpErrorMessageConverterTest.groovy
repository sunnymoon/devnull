package org.devnull.error.web.message

import com.sun.net.httpserver.HttpPrincipal
import org.apache.commons.lang.exception.ExceptionUtils
import org.devnull.error.ConflictingOperationException
import org.devnull.error.ValidationException
import org.devnull.error.Widget
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.validation.BeanPropertyBindingResult

class DefaultHttpErrorMessageConverterTest {
  DefaultHttpErrorMessageConverter converter
  MockHttpServletRequest mockRequest

  @Before
  void createConverter() {
    converter = new DefaultHttpErrorMessageConverter()
    mockRequest = new MockHttpServletRequest()
    mockRequest.requestURI = "/test"
    mockRequest.userPrincipal = new HttpPrincipal("testUser", "testRealm")
  }

  @Test
  void shouldUseRootExceptionWhenUseRootCauseFlagIsTrue() {
    converter.useRootCause = true
    def ex = new RuntimeException("test container", new IllegalArgumentException("test cause"))
    assert converter.convert(ex, mockRequest).messages == ["test cause"]
  }


  @Test
  void shouldUseGivenExceptionWhenUseRootCauseIsTrueButThereIsNoRootCause() {
    converter.useRootCause = true
    def ex = new RuntimeException("test container")
    assert converter.convert(ex, mockRequest).messages == ["test container"]
  }

  @Test
  void shouldUseGivenExceptionWhenUseRootCauseFlagIsFalse() {
    converter.useRootCause = false
    def ex = new RuntimeException("test container", new IllegalArgumentException("test cause"))
    assert converter.convert(ex, mockRequest).messages == ["test container"]
  }

  @Test
  void shouldHaveNullUserIfNoUserPrincipalIsSet() {
    mockRequest.userPrincipal = null
    def ex = new RuntimeException("test container")
    assert !converter.convert(ex, mockRequest).user
  }


  @Test
  void shouldConvertValidationExceptions() {
    def widget = new Widget()
    def errors = new BeanPropertyBindingResult(widget, "widget")
    errors.reject(null, "There are validation errors")
    errors.rejectValue("name", null, "Must have at least 2 characters")
    errors.rejectValue("name", null, "Must not contain special characters")
    errors.rejectValue("components", null, "Too many components (100 max)")
    def ex = new ValidationException(errors)
    def message = converter.convert(ex, mockRequest)
    assert message.messages == ex.globalErrors
    assert message.fieldMessages == ex.fieldErrors
    assert message.statusCode == 406
    assertCommonValuesAreSet(ex, message)
  }

  @Test
  void shouldConvertConflictingOperationExceptions() {
    def ex = new ConflictingOperationException("test conflict")
    def message = converter.convert(ex, mockRequest)
    assert message.messages == ["test conflict"]
    assert !message.fieldMessages
    assert message.statusCode == 409
    assertCommonValuesAreSet(ex, message)
  }

  void assertCommonValuesAreSet(Throwable t, HttpErrorMessage message) {
    assert message.requestUri == "/test"
    assert message.user == "testUser"
    assert message.stackTrace == ExceptionUtils.getStackTrace(t)
  }
}
