package au.com.company.productservice.service

import au.com.company.productservice.domain.Catalog
import au.com.company.productservice.domain.InputData
import au.com.company.productservice.domain.SupplierProductBarcode
import au.com.company.productservice.domain.Suppliers

interface InputCsvReader {
  InputData readInputFiles(String inputDirectory)

  List<Catalog> readCatalog(File file)

  List<Suppliers> readSuppliers(File file)

  List<SupplierProductBarcode> readBarcodes(File file)

}