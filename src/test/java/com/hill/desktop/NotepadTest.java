package com.hill.desktop;

import com.hill.desktop.appobjects.NotepadObject;
import com.hill.desktop.utilities.DT;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;

public class NotepadTest extends DesktopBaseTest {

    NotepadObject notepad = new NotepadObject();
    String filePath = "D:\\downloads\\DesktopTest.txt";

    @Test
    public void helloWorld() {
        notepad.type("hello world!")
                .file().saveAs(filePath)
                .close();
        DT.Wait.forMillis(300);
        Assert.assertTrue(new File(filePath).exists());
    }

    @AfterMethod
    public void clear() {
        File f = new File(filePath);
        if (f.exists()) {
            System.out.println(String.format("File '%s' removed: %s", filePath, f.delete()));
        }
    }
}
