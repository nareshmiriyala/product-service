package au.com.company.productservice.service

import au.com.company.productservice.domain.Catalog
import au.com.company.productservice.domain.SupplierProductBarcode
import au.com.company.productservice.domain.Suppliers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
class InputCsvReaderImplTest extends Specification {
  @Autowired
  InputCsvReader csvReader

  def "Given catalog input csv file, verify data is read successfully"() {
    given:
    File file = new File("src/test/resources/input/catalogA.csv")
    when:
    List<Catalog> aCatalogData = csvReader.readCatalog(file)
    then:
    aCatalogData.size() == 5
    aCatalogData[0].sku == '647-vyk-317'
    aCatalogData[0].description == 'Walkers Special Old Whiskey'
  }

  def "Given barcode input csv file, verify data is read successfully"() {
    given:
    File file = new File("src/test/resources/input/barcodesB.csv")
    when:
    List<SupplierProductBarcode> supplierProductBarcodes = csvReader.readBarcodes(file)
    then:
    supplierProductBarcodes.size() == 48
    supplierProductBarcodes[0].supplierId == 00001
    supplierProductBarcodes[0].sku == '999-vyk-317'
    supplierProductBarcodes[0].barcode == 'z2783613083817'
  }

  def "Given suppliers input csv file, verify data is read successfully"() {
    given:
    File file = new File("src/test/resources/input/suppliersA.csv")
    when:
    List<Suppliers> suppliers = csvReader.readSuppliers(file)
    then:
    suppliers.size() == 5
    suppliers[0].id == 00001
    suppliers[0].name == 'Twitterbridge'
  }
}
