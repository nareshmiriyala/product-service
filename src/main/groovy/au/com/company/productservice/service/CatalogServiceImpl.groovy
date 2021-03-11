package au.com.company.productservice.service

import groovy.util.logging.Slf4j
import java.util.stream.Collectors

import au.com.company.productservice.domain.Catalog
import au.com.company.productservice.domain.InputData
import au.com.company.productservice.domain.MergedCatalog
import au.com.company.productservice.domain.SupplierProductBarcode
import au.com.company.productservice.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
class CatalogServiceImpl implements CatalogService {

  @Autowired
  InputCsvReader inputCsvReader

  @Autowired
  OutputCsvWriter outputCsvWriter

  @Autowired
  Validator validator

  @Override
  void merge(String inputCsvDirectoryPath, String outputCsvDirectoryPath) {
    if (isValidDirectories(inputCsvDirectoryPath, outputCsvDirectoryPath)) {
      InputData inputData = inputCsvReader.readInputFiles(inputCsvDirectoryPath)
      validator.validate(inputData)
      filterProductsAndWriteOutputFile(inputData, outputCsvDirectoryPath)
      log.info("Successfully generated output file in ${outputCsvDirectoryPath} ")
    }
  }

  static boolean isValidDirectories(String inputCsvDirectoryPath, String outputCsvDirectoryPath) {
    File inputDirectory = new File(inputCsvDirectoryPath)
    if (!inputDirectory.isDirectory()) {
      log.error("Input directory ${inputCsvDirectoryPath} is invalid")
      return false
    }
    File outDirectory = new File(outputCsvDirectoryPath)
    if (!outDirectory.isDirectory()) {
      log.error("Output directory ${outputCsvDirectoryPath} is invalid")
      return false
    }
    return true
  }
  /**
   *
   * @param supplierProductBarcodeAList
   * @param supplierProductBarcodeBList
   * @param catalogAList
   * @param catalogBList
   * @param outputCsvDirectoryPath
   */
  private void filterProductsAndWriteOutputFile(InputData inputData, String outputCsvDirectoryPath) {
    Set<String> sameBarcodeAndDifferentSku = getProductsHavingSameBarcodeAndDifferentSku(inputData.supplierProductBarcodeAList, inputData.supplierProductBarcodeBList)
    List<Catalog> aList = inputData.catalogAList.stream().filter { !(it.sku in sameBarcodeAndDifferentSku) }.collect(Collectors.toList())
    List<Catalog> bList = inputData.catalogBList.stream().filter { !(it.sku in sameBarcodeAndDifferentSku) }.collect(Collectors.toList())
    Set<MergedCatalog> mergedCatalogs = []
    aList.forEach {
      mergedCatalogs.add(new MergedCatalog(sku: it.sku, description: it.description, source: 'A'))
    }
    bList.forEach {
      mergedCatalogs.add(new MergedCatalog(sku: it.sku, description: it.description, source: 'B'))
    }
    outputCsvWriter.write(outputCsvDirectoryPath, mergedCatalogs)
  }
  /**
   * Get the products having same barcode but different sku
   * @param supplierProductBarcodeAList
   * @param supplierProductBarcodeBList
   * @return
   */
  private static Set<String> getProductsHavingSameBarcodeAndDifferentSku(List<SupplierProductBarcode> supplierProductBarcodeAList, List<SupplierProductBarcode> supplierProductBarcodeBList) {
    Set<String> sameProductsInAAndB = []
    supplierProductBarcodeAList.forEach { SupplierProductBarcode barcodeA ->

      List<SupplierProductBarcode> sameBarcodeAndDifferentSku = supplierProductBarcodeBList.stream()
        .filter { SupplierProductBarcode barcodeB ->
          (barcodeB.barcode == barcodeA.barcode
            && barcodeA.sku != barcodeB.sku)
        }.collect(Collectors.toList())
      sameBarcodeAndDifferentSku.forEach {
        sameProductsInAAndB.add(it.sku)
      }
    }
    return sameProductsInAAndB
  }


}
