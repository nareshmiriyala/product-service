package au.com.company.productservice.service

import java.nio.charset.Charset

import au.com.company.productservice.domain.MergedCatalog
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Service

@Service
class OutputCsvWriterImpl implements OutputCsvWriter {
  @Override
  void write(String outputDirectory, Set<MergedCatalog> mergedCatalogs) {
    mergedCatalogs = mergedCatalogs.sort { MergedCatalog a, MergedCatalog b -> a.description < b.description ? -1 : 1 }
    File outputFile = new File(outputDirectory + "/result_output.csv")
    FileUtils.writeStringToFile(outputFile, "SKU,Description,Source" + System.lineSeparator(), Charset.defaultCharset(), true)
    mergedCatalogs.forEach {
      FileUtils.writeStringToFile(outputFile, it.toString() + System.lineSeparator(), Charset.defaultCharset(), true)
    }
  }
}
