package au.com.company.productservice.domain

class SupplierProductBarcode {
  Long supplierId
  String sku
  String barcode

  @Override
  String toString() {
    return "SupplierProductBarcode{" +
      "supplierId=" + supplierId +
      ", sku='" + sku + '\'' +
      ", barcode='" + barcode + '\'' +
      '}'
  }
}
