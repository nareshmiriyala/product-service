package au.com.company.productservice.validator

import au.com.company.productservice.domain.Catalog
import au.com.company.productservice.domain.InputData
import au.com.company.productservice.domain.SupplierProductBarcode
import au.com.company.productservice.domain.Suppliers
import au.com.company.productservice.exception.InvalidDataException
import org.springframework.stereotype.Service

@Service
class InputDataValidatorImpl implements Validator {
  @Override
  void validate(InputData inputData) {
    def catalogA = inputData.catalogAList
    def catalogB = inputData.catalogBList
    def barcodesA = inputData.supplierProductBarcodeAList
    def barcodesB = inputData.supplierProductBarcodeBList
    def suppliersA = inputData.suppliersAList
    def suppliersB = inputData.suppliersBList
    if (catalogA.isEmpty()) {
      assert barcodesA.isEmpty(): "barcodesA should be empty as catalogA is empty"
      assert suppliersA.isEmpty(): "supplierA should be empty as catalogA is empty"
    }
    if (catalogB.isEmpty()) {
      assert barcodesB.isEmpty(): "barcodesB should be empty as catalogB is empty"
      assert suppliersB.isEmpty(): "supplierB should be empty as catalogB is empty"
    }
    //verify input products have associated barcodes
    verifyProductBarcodesInCatalog(catalogA, barcodesA)

    verifyProductBarcodesInCatalog(catalogB, barcodesB)

    verifySupplier(barcodesA, suppliersA)
    verifySupplier(barcodesB, suppliersB)
  }

  private static void verifySupplier(ArrayList<SupplierProductBarcode> barcodesA, suppliersA) {
//verify supplier exists
    Optional<SupplierProductBarcode> supplierProductBarcode = barcodesA.stream().filter { SupplierProductBarcode barcode ->
      suppliersA.stream().filter {
        Suppliers suppliers ->
          suppliers.id == barcode.supplierId
      }.count() <= 0
    }.findAny()
    if (supplierProductBarcode.isPresent()) {
      throw new InvalidDataException("Supplier missing for barcode ${supplierProductBarcode.get()}")
    }
  }

  private static void verifyProductBarcodesInCatalog(ArrayList<Catalog> catalogs, barcodes) {

    Optional<Catalog> missingBarCodeProduct = catalogs.stream().filter {
      Catalog catalog ->
        barcodes.stream().filter {
          SupplierProductBarcode barcode ->
            barcode.sku == catalog.sku
        }.count() <= 0
    }.findAny()
    if (missingBarCodeProduct.isPresent()) {
      throw new InvalidDataException("Barcode missing for product ${missingBarCodeProduct.get()} in Catalog")
    }
  }
}
