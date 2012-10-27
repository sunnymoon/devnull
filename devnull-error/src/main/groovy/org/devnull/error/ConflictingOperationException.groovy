package org.devnull.error

class ConflictingOperationException extends DevNullException {

    ConflictingOperationException(String message, Exception e = null) {
        super(message, e)
    }
}
