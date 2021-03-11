package au.com.company.productservice.domain

class Suppliers {
  Long id
  String name

  @Override
  String toString() {
    return "Suppliers{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}'
  }
}
