package org.devnull.error.web.message

import javax.servlet.http.HttpServletRequest

interface HttpErrorMessageConverter<T extends Throwable> {
  HttpErrorMessage convert(T error, HttpServletRequest request)
}
