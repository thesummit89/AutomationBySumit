System.out.println("File Download path: "+downloadFilepath);
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("download.default_directory",  downloadFilepath);
                    chromePrefs.put("safebrowsing.enabled", true);
                    chromePrefs.put("download.prompt_for_download", false);
                    ChromeOptions options = new ChromeOptions();
