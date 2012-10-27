package org.devnull.error.web.message

import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.devnull.error.ConflictingOperationException
import org.devnull.error.ValidationException

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

/**
 * Default implementation of HttpErrorMessageConverter which knows how to convert DevNullException instances
 * as well as common Spring and javax.validation exceptions.
 */
@Slf4j
class DefaultHttpErrorMessageConverter implements HttpErrorMessageConverter {

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
    message.statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
  }

  protected populate(ValidationException ex, HttpServletRequest request, HttpErrorMessage message) {
    log.warn("Validation errors: {}", ex.toString())
    message.messages = ex.globalErrors
    message.fieldMessages = ex.fieldErrors
    message.statusCode = HttpServletResponse.SC_NOT_ACCEPTABLE
  }

  protected populate(ConflictingOperationException ex, HttpServletRequest request, HttpErrorMessage message) {
    log.warn("Conflicting operation: {}", ex.message)
    message.statusCode = HttpServletResponse.SC_CONFLICT
    message.messages = [ex.message]
  }

  protected populate(ConstraintViolationException ex, HttpServletRequest request) {
         log.warn("Constraint violation: {}", ex.message)
         this.messages = ex.constraintViolations.collect { it.message }
         this.statusCode = HttpServletResponse.SC_NOT_ACCEPTABLE
         this.user = request.userPrincipal?.toString()
         this.requestUri = request.requestURI
         this.stackTrace = ExceptionUtils.getStackTrace(ex)
     }
}
