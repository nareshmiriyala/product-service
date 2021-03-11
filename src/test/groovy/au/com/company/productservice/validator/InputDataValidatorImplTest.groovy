package au.com.company.productservice.validator

import au.com.company.productservice.domain.Catalog
import au.com.company.productservice.domain.InputData
import au.com.company.productservice.domain.SupplierProductBarcode
import au.com.company.productservice.domain.Suppliers
import au.com.company.productservice.exception.InvalidDataException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
class InputDataValidatorImplTest extends Specification {
  @Autowired
  Validator validator

  def "Verify missing barcode for product in catalog A"() {
    given:
    Catalog aCatalog = new Catalog()
    aCatalog.sku = "647-vyk-317"
    aCatalog.description = "Walkers Special Old Whiskey"
    SupplierProductBarcode productBarcode = new SupplierProductBarcode()
    productBarcode.supplierId = 00002
    productBarcode.sku = "280-oad-768"
    productBarcode.barcode = "f4322915485228"
    InputData inputData = new InputData()
    inputData.catalogAList = [aCatalog]
    inputData.supplierProductBarcodeAList = [productBarcode]
    when:
    validator.validate(inputData)
    then:
    final InvalidDataException exception = thrown()

    exception.message == 'Barcode missing for product Catalog{sku=\'647-vyk-317\', description=\'Walkers Special Old Whiskey\'} in Catalog'
  }

  def "Verify supplier exists for barcodeA"() {
    given:
    InputData inputData = new InputData()
    SupplierProductBarcode barcodeA = new SupplierProductBarcode()
    barcodeA.supplierId = 1000
    barcodeA.barcode = '121121212'
    barcodeA.sku = '280-0ad-768'
    Suppliers suppliersA = new Suppliers()
    suppliersA.name = "Twitterbridge"
    suppliersA.id = 23232
    Catalog aCatalog = new Catalog()
    aCatalog.sku = '280-0ad-768'
    aCatalog.description = "Walkers Special Old Whiskey"
    inputData.suppliersAList = [suppliersA]
    inputData.supplierProductBarcodeAList = [barcodeA]
    inputData.catalogAList = [aCatalog]
    when:
    validator.validate(inputData)
    then:
    final InvalidDataException exception = thrown()

    exception.message == 'Supplier missing for barcode SupplierProductBarcode{supplierId=1000, sku=\'280-0ad-768\', barcode=\'121121212\'}'
  }
}
