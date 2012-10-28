package org.devnull.error.web.message.impl

import groovy.util.logging.Slf4j
import org.devnull.error.web.message.HttpErrorMessage

import javax.servlet.http.HttpServletRequest

import static org.devnull.error.web.message.ErrorCodes.*
import org.devnull.error.ConflictingOperationException


@Slf4j
class ConflictingOperationConverter extends BaseConverter<ConflictingOperationException> {

  /**
   * Default: {@value}
   */
  Integer statusCode = CLIENT_ERROR_CONFLICTING_OPERATION

  void populate(ConflictingOperationException ex, HttpServletRequest request, HttpErrorMessage message) {
    message.messages = ["Conflicting Operation", ex.message]
  }

}
