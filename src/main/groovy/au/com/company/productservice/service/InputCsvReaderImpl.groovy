package au.com.company.productservice.service

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

import au.com.company.productservice.domain.Catalog
import au.com.company.productservice.domain.InputData
import au.com.company.productservice.domain.SupplierProductBarcode
import au.com.company.productservice.domain.Suppliers
import org.springframework.stereotype.Service

import static au.com.company.productservice.util.CsvReaderUtil.getCsvData

@Service
class InputCsvReaderImpl implements InputCsvReader {
  /**
   * Read input csv files from input folder
   * @param inputCsvDirectoryPath
   * @param catalogAList
   * @param catalogBList
   * @param suppliersAList
   * @param suppliersBList
   * @param supplierProductBarcodeAList
   * @param supplierProductBarcodeBList
   */
  InputData readInputFiles(String inputCsvDirectoryPath) {
    InputData inputData = new InputData()

    Stream<Path> stream = Files.walk(Paths.get(inputCsvDirectoryPath))
    stream.filter { Files.isRegularFile(it) && it.toFile().name.toLowerCase().endsWith(".csv") }
      .forEach {
        String fileName = it.toFile().name
        switch (fileName) {
          case "catalogA.csv":
            inputData.catalogAList = readCatalog(it.toFile())
            break
          case "catalogB.csv":
            inputData.catalogBList = readCatalog(it.toFile())
            break
          case "suppliersA.csv":
            inputData.suppliersAList = readSuppliers(it.toFile())
            break
          case "suppliersB.csv":
            inputData.suppliersBList = readSuppliers(it.toFile())
            break
          case "barcodesA.csv":
            inputData.supplierProductBarcodeAList = readBarcodes(it.toFile())
            break
          case "barcodesB.csv":
            inputData.supplierProductBarcodeBList = readBarcodes(it.toFile())

        }
      }
    return inputData
  }

  @Override
  List<Catalog> readCatalog(File file) {
    List<Catalog> catalogAList = []
    List<String[]> csvData = getCsvData(file)
    csvData.each {
      Catalog catalogA = new Catalog()
      catalogA.sku = it[0]
      catalogA.description = it[1]
      catalogAList.add(catalogA)
    }
    return catalogAList
  }


  @Override
  List<Suppliers> readSuppliers(File file) {
    List<Suppliers> suppliersList = []
    List<String[]> csvData = getCsvData(file)
    csvData.each {
      Suppliers suppliers = new Suppliers()
      suppliers.id = it[0] as Long
      suppliers.name = it[1]
      suppliersList.add(suppliers)
    }
    return suppliersList
  }

  @Override
  List<SupplierProductBarcode> readBarcodes(File file) {
    List<SupplierProductBarcode> supplierProductBarcodes = []
    List<String[]> csvData = getCsvData(file)
    csvData.each {
      SupplierProductBarcode supplierProductBarcode = new SupplierProductBarcode()
      supplierProductBarcode.supplierId = it[0] as Long
      supplierProductBarcode.sku = it[1]
      supplierProductBarcode.barcode = it[2]
      supplierProductBarcodes.add(supplierProductBarcode)
    }
    return supplierProductBarcodes
  }

}
