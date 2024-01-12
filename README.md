# E-Tools
## 基础介绍
一套JAVA的基础开发工具包。

提供了日常开发中最常用的各种工具类，包括：日期、字符串、数学运算、地理位置、国际化、Canvas、JSON、ID生成器、验证码等。

### 开始使用
在 pom.xml 文件中引入：
```xml
<dependency>
    <groupId>top.efcloud</groupId>
    <artifactId>e-tools</artifactId>
    <version>1.1.1</version>
</dependency>
```
---

## 代码示例
### 日期的计算与格式转换

```java
public static void main(String[] args) {

    // 获取昨日开始时间
    EasyDate.now().yesterday().startTime().value();

    // 获取7天前的结束时间
    EasyDate.now().addDays(-7).endTime().value();

    // 获取某一年的最后一天
    EasyDate.of("2020-05-01 12:00:00").yearEndDay().value();

    // 获取本月的第一天的12:00
    EasyDate.now().monthStartDay().time("12:00").value();

    // 获取格式化日期字符串
    EasyDate.now().endTime().stringValue("HH:mm:ss");

    // 自定义额外操作
    EasyDate.now().extra(c -> c.set(Calendar.MONTH, 1)).endTime().value();

    // 获取今天是周几
    DateFormatUtils.toWeek(new Date(), new CnTextDict());

    // 获取可读的时间差：(几秒前/几分钟前/几天前...)
    DateFormatUtils.toDiffText(EasyDate.now().addDays(-2).value(), new CnTextDict());

}
```


### 数学运算

```java
public static void main(String[] args) {

    // 浮点运算（解决进度丢失问题）
    EasyNumber.of(0.1).add(0.2).toDouble();

    // 只精确到整数
    EasyNumber.zero().integerRule().add(9).multiply(0.9).toString();

    // 获取除法过程中产生的余数
    EasyNumber number = EasyNumber.of(10).integerRule().divide(3);
    BigDecimal value = number.value();      // print：3
    BigDecimal rem = number.remainder();    // print：1

    // 获取计算过程：(10.00+20.00)×(1.00+2.00)÷20.00=4.50(~10.00)
    EasyNumber.of(10).add(20).multiply(EasyNumber.of(1).add(2)).divide(20).course();
}
```

// TODO