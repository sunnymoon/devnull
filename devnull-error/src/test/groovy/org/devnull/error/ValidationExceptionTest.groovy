package org.devnull.error

import org.junit.Test
import org.springframework.validation.BeanPropertyBindingResult

class ValidationExceptionTest {

  @Test
  void shouldGroupErrorsByField() {
    def widget = new Widget()
    def errors = new BeanPropertyBindingResult(widget, "widget")
    errors.rejectValue("name", null, "Must have at least 2 characters")
    errors.rejectValue("name", null, "Must not contain special characters")
    errors.rejectValue("components", null, "Too many components (100 max)")
    def ex = new ValidationException(errors)
    println ex

    assert ex.fieldErrors == [
            name: ["Must have at least 2 characters", "Must not contain special characters"],
            components: ["Too many components (100 max)"]
    ]
    assert !ex.globalErrors
  }

  @Test
  void shouldFindGlobalErrorsAndFieldErrors() {
    def user = new Widget()
    def errors = new BeanPropertyBindingResult(user, "user")
    errors.reject(null, "Duplicate widget")
    errors.rejectValue("components", null, "Components must be a positive value")
    def ex = new ValidationException(errors)
    println ex

    assert ex.fieldErrors == [
            components: ["Components must be a positive value"]
    ]
    assert ex.globalErrors == ["Duplicate widget"]
  }
}


