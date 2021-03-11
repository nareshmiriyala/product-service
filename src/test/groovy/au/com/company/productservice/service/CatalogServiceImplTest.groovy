package au.com.company.productservice.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
class CatalogServiceImplTest extends Specification {
  @Autowired
  CatalogService productMergeService

  def "merge catalog"() {
    given:
    File outFile = new File("src/test/resources/output/result_output.csv")
    outFile.delete()
    when:
    productMergeService.merge("src/test/resources/input/", "src/test/resources/output/")
    then:
    outFile.text == """SKU,Description,Source
280-oad-768,Bread - Raisin,A
650-epd-782,Carbonated Water - Lemon Lime,A
999-epd-782,Carbonated Water - Lemon Lime,B
167-eol-949,Cheese - Grana Padano,A
999-eol-949,Cheese - Grana Padano,B
165-rcy-650,Tea - Decaf 1 Cup,A
647-vyk-317,Walkers Special Old Whiskey,A
"""
  }
}
