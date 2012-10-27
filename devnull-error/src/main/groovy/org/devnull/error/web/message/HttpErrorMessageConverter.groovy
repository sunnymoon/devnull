package org.devnull.error.web.message

import javax.servlet.http.HttpServletRequest

interface HttpErrorMessageConverter {
  HttpErrorMessage convert(Throwable throwable, HttpServletRequest request)
}
