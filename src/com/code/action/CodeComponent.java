package com.code.action;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @Classname CodeComponent
 * @Description TODO
 * @Date 2020/1/22 18:11
 * @Created by limeng
 */
public class CodeComponent implements ApplicationComponent {
    @Override
    public void initComponent() {

        // TODO: insert component initialization logic here

    }



    @Override
    public void disposeComponent() {

        // TODO: insert component disposal logic here

    }



    @Override
    @NotNull
    public String getComponentName() {
        if ("CreateMicroServiceProjectComponent" == null) {
            CodeComponent.reportNull(0);
        }
        return "CreateMicroServiceProjectComponent";
    }

    private static  void reportNull(int n) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/code/action/CodeComponent", "getComponentName"));
    }

}
