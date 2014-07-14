<html>
<head>
    <title></title>
</head>
<body>
test.ftl
<div>
    name: ${name!}
</div>
<div>
    a: ${a!}
</div>
<div>
    b: ${b!}
</div>
</body>
</html>

[#list 1..10 as i]
    ${i}...
[/#list]
</body>
</html>
