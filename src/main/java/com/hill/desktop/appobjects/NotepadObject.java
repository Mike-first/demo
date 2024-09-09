package com.hill.desktop.appobjects;

import com.hill.desktop.core.DDManager;
import com.hill.desktop.utilities.DT;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.Keys;

public class NotepadObject {

    public static final String app = "C:\\Windows\\System32\\notepad.exe";

    public NotepadObject minimize() {
        DT.Get.elementByName("Minimize").click();
        return this;
    }

    public NotepadObject maximize() {
        DT.Get.elementByName("Maximize").click();
        return this;
    }

    public void close() {
        DT.Get.elementByName("Close").click();
        DDManager.tearDown();
    }

    public NotepadObject file() {
        DT.Get.elementByName("File").click();
        return this;
    }

    public NotepadObject edit() {
        DT.Get.elementByName("Edit").click();
        return this;
    }

    public NotepadObject format() {
        DT.Get.elementByName("Format").click();
        return this;
    }

    public NotepadObject view() {
        DT.Get.elementByName("View").click();
        return this;
    }

    public NotepadObject help() {
        DT.Get.elementByName("File").click();
        return this;
    }

    public NotepadObject type(String text) {
        DT.Get.elementByName("Text Editor").sendKeys(text);
        return this;
    }


    public NotepadObject saveAs(String path) {
        DT.Get.elementByName("Save As...\tCtrl+Shift+S").click();

        WindowsElement we = DT.Get.elementByName("File name:");
        DT.Wait.forMillis(50);
        DT.acts().sendKeys(we, Keys.BACK_SPACE).build().perform();
        DT.Wait.forMillis(150);
        DT.acts().sendKeys(we, path).build().perform();

        DT.Get.elementByName("Save").click();
        return this;
    }
}
