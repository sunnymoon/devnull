package org.devnull.error.web.resolver

import groovy.util.logging.Slf4j
import org.devnull.error.web.message.HttpErrorMessageConverter
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.*

/**
 * Renders HttpErrorMessages as HTML to HTTP clients
 */
@Slf4j
class DefaultErrorResolver implements HandlerExceptionResolver {

  /**
   * Converts an exception to an appropriate HttpErrorMessage
   */
  HttpErrorMessageConverter httpErrorMessageConverter

  /**
   * <p>Mapping of HTTP status codes to views to render upon error. Http status code is
   * determined by the HttpErrorMessage created from the httpErrorMessageConverter</p>
   *
   * Defaults:
   * <ul>
   *    <li>SC_FORBIDDEN=/error/denied</li>
   *    <li>SC_FORBIDDEN=/error/conflict</li>
   *    <li>SC_NOT_ACCEPTABLE=/error/invalid</li>
   *    <li>SC_NOT_FOUND=/error/notFound</li>
   * </ul>
   */
  Map<Integer, String> statusToViewMappings = [
          (SC_FORBIDDEN): "/error/denied",
          (SC_CONFLICT): "/error/conflict",
          (SC_NOT_ACCEPTABLE): "/error/invalid",
          (SC_NOT_FOUND): "/error/notFound",
  ]

  /**
   * View to use if a HTTP status mapping is not found
   */
  String defaultView = "/error/default"

  /**
   * Converts the exception to a HttpErrorMessage and passes it to the configured view in the model (errorMessage)
   */
  ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    def errorMessage = httpErrorMessageConverter.convert(ex, request)
    response.status = errorMessage.statusCode
    def view = statusToViewMappings[errorMessage.statusCode] ?: defaultView
    return new ModelAndView(view, [errorMessage: errorMessage])
  }

}
