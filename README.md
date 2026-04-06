public Map<String, List<String>> getAgGridTableDataUsingColId() {

    Map<String, List<String>> tableData = new LinkedHashMap<>();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Step 1: Get headers with col-id
    List<WebElement> headers = wait.until(ExpectedConditions
            .visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[@role='columnheader']")
            ));

    Map<String, String> colIdToName = new LinkedHashMap<>();

    for (WebElement header : headers) {
        String colId = header.getAttribute("col-id");
        String colName = header.getText().trim();

        colIdToName.put(colId, colName);
        tableData.put(colName, new ArrayList<>());
    }

    // Step 2: Get rows
    List<WebElement> rows = driver.findElements(
            By.xpath("//div[@role='row' and @row-index]")
    );

    // Step 3: Iterate rows
    for (WebElement row : rows) {

        for (Map.Entry<String, String> entry : colIdToName.entrySet()) {

            String colId = entry.getKey();
            String colName = entry.getValue();

            WebElement cell = row.findElement(
                    By.xpath(".//div[@col-id='" + colId + "']")
            );

            String value = cell.getText().trim();

            tableData.get(colName).add(value);
        }
    }

    return tableData;
}
