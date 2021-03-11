package au.com.company.productservice.domain


class MergedCatalog {
  String sku
  String description
  String source

  @Override
  String toString() {
    return sku + ',' + description + ',' + source
  }

  boolean equals(o) {
    if (this.is(o)) return true
    if (getClass() != o.class) return false

    MergedCatalog that = (MergedCatalog) o

    if (description != that.description) return false
    if (sku != that.sku) return false

    return true
  }

  int hashCode() {
    int result
    result = (sku != null ? sku.hashCode() : 0)
    result = 31 * result + (description != null ? description.hashCode() : 0)
    return result
  }
}
