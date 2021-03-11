package au.com.company.productservice.util

class CsvReaderUtil {
  static List<String[]> getCsvData(File csvFile) {
    List<String[]> csvRecords = []
    if (csvFile.exists()) {
      csvFile.eachWithIndex { String line, int index ->
        if (index == 0)
          return //skip header
        if (line.isEmpty()) return //return empty lines
        csvRecords.add(line.split(","))

      }
    }
    return csvRecords
  }
}
