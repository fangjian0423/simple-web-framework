package org.format.framework.test;

import org.format.framework.base.FrameworkAction;

public class TestAction extends FrameworkAction {

    public String test() {
        req.setAttribute("name", "format");
        return forward("test");
    }

    public String argument(Integer a, int b, String str) {
        return forward("test");
    }

}
