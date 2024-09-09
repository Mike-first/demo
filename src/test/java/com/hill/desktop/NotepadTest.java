package com.hill.desktop;

import com.hill.desktop.appobjects.NotepadObject;
import com.hill.desktop.utilities.DT;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.File;

public class NotepadTest extends DesktopBaseTest {

    NotepadObject notepad = new NotepadObject();
    String filePath = "D:\\downloads\\DesktopTest.txt";

    @Test
    public void helloWorld() {
        notepad.type("hello world!")
                .file()
                .saveAs(filePath)
                .close();
        DT.Wait.forMillis(300);
        Assertions.assertTrue(new File(filePath).exists());
    }

    @AfterAll
    public void clear() {
        File f = new File(filePath);
        if (f.exists()) {
            logger.info(String.format("File '%s' removed: %s", filePath, f.delete()));
        }
    }
}
