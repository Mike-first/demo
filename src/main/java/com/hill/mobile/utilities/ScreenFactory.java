package com.hill.mobile.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;

public class ScreenFactory {
    private final AppiumDriver<MobileElement> driver;

    public ScreenFactory(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public <T> T init(Class<T> clazz) {
        T screen = null;
        try {
            screen = clazz.getDeclaredConstructor().newInstance();

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(AndroidFindBy.class)) {
                    AndroidFindBy androidFindBy = field.getAnnotation(AndroidFindBy.class);
                    By by = getByFromAndroidFindBy(androidFindBy);
                    field.setAccessible(true);
                    field.set(screen, driver.findElement(by));
                }
            }

            PageFactory.initElements(new AppiumFieldDecorator(driver), screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screen;
    }

    private By getByFromAndroidFindBy(AndroidFindBy androidFindBy) {
        if (!androidFindBy.id().isEmpty()) {
            return By.id(androidFindBy.id());
        } else if (!androidFindBy.xpath().isEmpty()) {
            return By.xpath(androidFindBy.xpath());
        } else if (!androidFindBy.accessibility().isEmpty()) {
            return By.id(androidFindBy.accessibility());
        } else if (!androidFindBy.uiAutomator().isEmpty()) {
            return MobileBy.AndroidUIAutomator(androidFindBy.uiAutomator());
        }
        throw new IllegalArgumentException("No valid selector found in AndroidFindBy annotation");
    }
}
