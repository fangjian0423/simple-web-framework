package org.format.framework.test;

import org.format.framework.annotation.Obj;
import org.format.framework.base.FrameworkAction;

public class TestAction extends FrameworkAction {

    public String test() {
        req.setAttribute("name", "format");
        return forward("test");
    }

    public String argument(Integer a, int b, boolean bool, Boolean Bool, String str, @Obj("e") Employee e) {
        req.setAttribute("a", a);
        req.setAttribute("b", b);
        return forward("test");
    }

}
