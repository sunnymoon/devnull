package org.devnull.error.web.message

import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.devnull.error.ConflictingOperationException
import org.devnull.error.ValidationException

import javax.servlet.http.HttpServletRequest

import javax.validation.ConstraintViolationException

import static javax.servlet.http.HttpServletResponse.*

/**
 * Default implementation of HttpErrorMessageConverter which knows how to convert DevNullException instances
 * as well as common Spring and javax.validation exceptions.
 */
@Slf4j
class DefaultHttpErrorMessageConverter implements HttpErrorMessageConverter {

  public static Integer SC_UNPROCESSABLE_ENTITY = 422

  /**
   * If true, use the root cause of the exception for conversion instead of the parent
   */
  Boolean useRootCause = true

  HttpErrorMessage convert(Throwable throwable, HttpServletRequest request) {
    def error = useRootCause ? ExceptionUtils.getRootCause(throwable) : throwable
    if (!error) error = throwable
    def message = new HttpErrorMessage()
    message.stackTrace = ExceptionUtils.getStackTrace(error)
    message.user = request.userPrincipal?.toString()
    message.requestUri = request.requestURI
    populate(error, request, message)
    return message
  }

  protected populate(Throwable throwable, HttpServletRequest request, HttpErrorMessage message) {
    log.warn("Unhandled error", throwable)
    message.messages = [throwable.message]
    message.statusCode = SC_INTERNAL_SERVER_ERROR
  }

  protected populate(ValidationException ex, HttpServletRequest request, HttpErrorMessage message) {
    log.warn("Validation errors: {}", ex.toString())
    message.messages = ex.globalErrors
    message.fieldMessages = ex.fieldErrors
    message.statusCode = SC_UNPROCESSABLE_ENTITY
  }

  protected populate(ConflictingOperationException ex, HttpServletRequest request, HttpErrorMessage message) {
    log.warn("Conflicting operation: {}", ex.message)
    message.statusCode = SC_CONFLICT
    message.messages = [ex.message]
  }

  protected populate(ConstraintViolationException ex, HttpServletRequest request,  HttpErrorMessage message) {
    log.warn("Constraint violation: {}", ex.message)
    message.messages = ex.constraintViolations.collect { it.message }
    message.statusCode = SC_UNPROCESSABLE_ENTITY
  }
}
