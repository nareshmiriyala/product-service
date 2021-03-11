package au.com.company.productservice.controller

import groovy.util.logging.Slf4j

import au.com.company.productservice.service.CatalogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1.0/product")
@Slf4j
class ProductController {
  @Autowired
  CatalogService catalogService

  @GetMapping("/merge")
  String mergeProducts(@RequestParam(name = "input", required = true) String inputFolder, @RequestParam(name = "output", required = true) String outputFolder) {
    log.info("Starting merging products from input folder ${inputFolder}")
    catalogService.merge(inputFolder, outputFolder)
    return "Successfully generated csv file in ${outputFolder}"
  }
}
