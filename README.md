simple-web-framework
====================

a simple web framework for java with struts2,ibatis,poi etc。

Action simulates SpringMVC's Controller to auto resolve arguments.

for example:
====================
a method of Action:

    public String argument(Integer a, int b, @Obj("e") Employee e) {
        return forward("test");
    }

you can visit:
http://ip:port/contextPath/test/test.do?act=argument&a=1&b=2&e.name=name&e.age=12

then param **a** will be 1 and param **b** will be 2.

the Employee is a custom Class with name and age properties. Framework also can resolve Object param.

**@Obj** annotation has a property "value", it means the prefix of object param, like e.name, e.age means the name, age of object's property. If @Obj do not has a "value" property, it is auto filled like: **Employee -> employee, Dept -> dept**

**the "act" param means the method name of Action.**
