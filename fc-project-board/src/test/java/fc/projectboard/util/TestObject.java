package fc.projectboard.util;

import java.math.BigDecimal;

public class TestObject {

    String str;
    String listStr1;
    String listStr2;
    String nullStr;
    Integer number;
    Double floatingNumber;
    Boolean bool;
    BigDecimal bigDecimal;
    TestEnum testEnum;

    private TestObject(String str,
                       String listStr1,
                       String listStr2,
                       String nullStr,
                       Integer number,
                       Double floatingNumber,
                       Boolean bool,
                       BigDecimal bigDecimal,
                       TestEnum testEnum) {
        this.str = str;
        this.listStr1 = listStr1;
        this.listStr2 = listStr2;
        this.nullStr = nullStr;
        this.number = number;
        this.floatingNumber = floatingNumber;
        this.bool = bool;
        this.bigDecimal = bigDecimal;
        this.testEnum = testEnum;
    }

    public static TestObject of(String str,
                                String listStr1,
                                String listStr2,
                                String nullStr,
                                Integer number,
                                Double floatingNumber,
                                Boolean bool,
                                BigDecimal bigDecimal,
                                TestEnum testEnum) {
        return new TestObject(str, listStr1, listStr2, nullStr, number, floatingNumber, bool, bigDecimal, testEnum);
    }

    public String getStr() {
        return str;
    }

    public String getListStr1() {
        return listStr1;
    }

    public String getListStr2() {
        return listStr2;
    }

    public String getNullStr() {
        return nullStr;
    }

    public Integer getNumber() {
        return number;
    }

    public Double getFloatingNumber() {
        return floatingNumber;
    }

    public Boolean getBool() {
        return bool;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public TestEnum getTestEnum() {
        return testEnum;
    }
}
