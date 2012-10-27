package org.devnull.error

abstract class DevNullException extends RuntimeException {
  DevNullException(String message, Throwable ex = null) {
    super(message, ex)
  }
}
