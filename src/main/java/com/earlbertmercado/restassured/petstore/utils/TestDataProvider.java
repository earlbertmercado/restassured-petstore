package com.earlbertmercado.restassured.petstore.utils;

import com.earlbertmercado.restassured.petstore.config.ConfigManager;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class TestDataProvider {

    private static final String TEST_DATA_FILE = "PetStoreTestData.xlsx";
    private static final String EXCEL_PATH = ConfigManager.getTestDataPath() + TEST_DATA_FILE;

    @DataProvider(name = "userData")
    public static Object[][] getUserData() {
        return getSheetData("Users");
    }

    @DataProvider(name = "petData")
    public static Object[][] getPetData() {
        return getSheetData("Pets");
    }

    @DataProvider(name = "storeData")
    public static Object[][] getStoreData() {
        return getSheetData("Store");
    }

    // Generic helper to convert any Excel sheet into a TestNG Object[][]
    private static Object[][] getSheetData(String sheetName) {
        List<Map<String, String>> data = ExcelUtil.getSheetData(EXCEL_PATH, sheetName);
        Object[][] arr = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            arr[i][0] = data.get(i);
        }
        return arr;
    }
}
