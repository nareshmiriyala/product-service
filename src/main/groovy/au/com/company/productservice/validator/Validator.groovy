package au.com.company.productservice.validator

import au.com.company.productservice.domain.InputData

interface Validator {
  void validate(InputData inputData)
}