package org.format.framework.test;

import org.format.framework.annotation.Action;
import org.format.framework.annotation.Obj;
import org.format.framework.base.FrameworkAction;

@Action("test")
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

    public String arr(int[] a, int[] b) {
        return forward("test");
    }

}
