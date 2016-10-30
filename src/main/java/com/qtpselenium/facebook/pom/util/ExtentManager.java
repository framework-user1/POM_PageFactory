package com.qtpselenium.facebook.pom.util;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            Date d = new Date();
            String fileName = "ExtentReports_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
            String reportsPath = System.getProperty("user.dir") + FBConstants.EXTENT_REPORTS_PATH + "\\" + fileName;

            extent = new ExtentReports(reportsPath, true, DisplayOrder.NEWEST_FIRST);
            extent.loadConfig(new File(System.getProperty("user.dir") + "\\ReportsConfig.xml"));
            extent.addSystemInfo("Selenium Version", "2.53.1").addSystemInfo("Environment", "QA");
        }
        return extent;
    }
}