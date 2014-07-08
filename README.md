simple-web-framework
====================

a simple web framework for java with struts2,ibatis,poi etc。

Action simulates SpringMVC's Controller to auto resolve arguments.

for example:
a method of Action:

public String argument(Integer a, int b) {
    req.setAttribute("a", a);
    req.setAttribute("b", b);
    return forward("test");
}

you can visit:
http://ip:port/contextPath/test/test.do?act=argument&a=1&b=2

then param a will be 1 and param b will be 2.

the "act" param means the method name of Action.
