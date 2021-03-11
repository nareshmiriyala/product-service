package au.com.company.productservice.service

import au.com.company.productservice.domain.MergedCatalog

interface OutputCsvWriter {
  void write(String outputDirectory, Set<MergedCatalog> mergedCatalogs)
}