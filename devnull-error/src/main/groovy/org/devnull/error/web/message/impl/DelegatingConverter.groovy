package org.devnull.error.web.message.impl

import org.apache.commons.lang.exception.ExceptionUtils
import org.devnull.error.web.message.HttpErrorMessage
import org.devnull.error.web.message.HttpErrorMessageConverter

import javax.servlet.http.HttpServletRequest

/**
 * Chains HttpErrorMessageConverters. Delegates to first match that supports any of the exception types
 * in the exception chain.
 */
class DelegatingConverter implements HttpErrorMessageConverter<Throwable> {
  List<HttpErrorMessageConverter> converters = []

  HttpErrorMessage convert(Throwable error, HttpServletRequest request) {
    for (HttpErrorMessageConverter c : converters) {
      def supportedType = getAssignedType(c)
      def supportedThrowable = findSupportedError(error, supportedType)
      if (supportedType && supportedThrowable) {
        return c.convert(supportedThrowable, request)
      }
    }
    return null
  }

  protected Class getAssignedType(HttpErrorMessageConverter converter) {
    return converter.class.genericSuperclass.actualTypeArguments.first()
  }

  protected Throwable findSupportedError(Throwable error, Class type) {
    ExceptionUtils.getThrowables(error).find { type.isAssignableFrom(it.class) }
  }

}
