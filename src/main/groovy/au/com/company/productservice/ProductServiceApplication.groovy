package au.com.company.productservice

import groovy.util.logging.Slf4j

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@Slf4j
class ProductServiceApplication {


  static void main(String[] args) {
    SpringApplication.run(ProductServiceApplication, args)
  }

}
