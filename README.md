 JavascriptExecutor js = (JavascriptExecutor) driver;
        List<String> visibleOrder = (List<String>) js.executeScript(
            "return [...document.querySelectorAll('ul#sortedList li')].map(el => el.innerText);"
        );

        System.out.println("Visible Order: " + visibleOrder);
        driver.quit();
