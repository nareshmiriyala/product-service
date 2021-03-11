package au.com.company.productservice.domain

class Catalog {
  String sku
  String description

  @Override
  String toString() {
    return "Catalog{" +
      "sku='" + sku + '\'' +
      ", description='" + description + '\'' +
      '}'
  }
}
