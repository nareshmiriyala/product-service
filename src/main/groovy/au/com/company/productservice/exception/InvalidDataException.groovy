package au.com.company.productservice.exception

class InvalidDataException extends RuntimeException {
  InvalidDataException(String msg) {
    super(msg)
  }
}
