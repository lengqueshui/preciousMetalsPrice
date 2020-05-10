package com.bsyd.preciousMetalsPrice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.function.Function;

@Slf4j
public class DigitalUtil {
    private static final int DEF_DIV_SCALE = 2;

    private DigitalUtil() {
    }

    interface Add extends Function<Double, Double> {

    }

    /**
     * 提供精确的加法运算。
     * 
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * 
     * @param v1
     *            被减数
     * @param v2
     *            减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * 
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字截位。
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字截位。
     * 
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 提供精确的小数位截位处理（非四舍五入）。
     * 
     * @param v
     *            需要截位的数字
     * @param scale
     *            小数点后保留几位
     * @return 截位后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 向上取整
     * @param v
     * @param scale
     * @return
     */
    public static double roundUp(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();
    }

    /**
     * <p>
     * 浮点类型的“元”转换为long类型的“分”。
     * 
     * 具体转换算法位： 浮点类型乘以1000，四舍五入后取整，再除以10，取整即可
     * 
     * 例如：100.6699999，乘以1000变为100669.999， 四舍五入后为100670，再除以10，取整，为10067
     * 
     * 这样可以避免100.4459999取整为100.45
     * 
     * @param amount
     * @return
     */
    public static long yuan2fen(Double amount) {
        if (amount == null) {
            return 0L;
        }

        double d1 = amount * 1000;
        long l1 = Math.round(d1);
        double d2 = l1 / 10.0;
        return (long) d2;
    }

    public static Double fen2yuan(long amount) {
        if (0 == amount) {
            return 0d;
        }
        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 元转分并合成为12位字符串
     * 
     * @param amount
     * @return
     */
    public static String yuan2fen12(Double amount) {
        if (amount == null) {
            return "000000000000";
        }

        double d1 = amount * 1000;
        long l1 = Math.round(d1);
        long result = new Double(l1 / 10.0).longValue();
        int length = String.valueOf(result).length();
        String resultStr = "";
        for (int i = 0; i < (12 - length); i++) {
            resultStr += "0";
        }
        return resultStr + result;
    }

    /**
     * 返回一定范围内的随机整数
     * 
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        Random r = new Random();
        int i = r.nextInt(max - min) + min;
        return i;
    }

    /**
     * 格式化两位小数(金额)
     **/
    public static String formatTwoDecimal(double d) {
        return String.format("%.2f", d);
    }

    /**
     * 四舍五入
     * 
     * @param amount
     * @return
     */
    public static double halfUp(double amount) {
        return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 金额四舍五入处理
     * 
     * @param amount
     * @return
     */
    public static double getDoubleAmount(double amount) {
        if (amount <= 0) {
            return 0;
        }

        return new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * long类型的单位分金额转换为小数点后两位的String类型
     * 
     * @param amount
     * @return
     */
    public static String getStringAmount(long amount) {
        String strAmount = String.valueOf(amount);
        if (strAmount.length() < 3) {
            for (int i = 0; i < (4 - strAmount.length()); i++) {
                strAmount = "0" + strAmount;
            }
        }
        StringBuffer amounts = new StringBuffer(strAmount);
        amounts.insert(strAmount.length() - 2, ".");
        return amounts.toString();
    }

    /**
     * 小数点后两位的String类型金额转换为long类型的单位分
     * 
     * @param amount
     * @return
     */
    public static long getLongAmount(String amount) {
        if(StringUtils.isEmpty(amount)){
            return 0;
        }

        String strAmount;
        if (amount.contains(".")) {
            //100.0
            if ((amount.length() - (amount.indexOf(".") + 1)) == 1) {
                strAmount = amount.replace(".", "") + "0";
            } else {
                //100.00
                strAmount = amount.replace(".", "");
            }
        } else {
            //100
            strAmount = amount + "00";
        }


        try {
            return Long.parseLong(strAmount);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 财务报告金额显示: 
     * 每3位中间间隔逗号同时不显示两位小数
     */
	public static String getFinFormatAmount(double amount) {
		if (amount < 1000 && amount > -1000) {
			BigDecimal b = new BigDecimal(Double.toString(amount));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, 0, BigDecimal.ROUND_DOWN).toString();
		}
		
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###");
		return myformat.format(amount);
	}

    /**
     * 财务报告金额显示:
     * 每3位中间间隔逗号同时不显示两位小数
     */
    public static BigDecimal getBigDecimal(String value) {
        if (StringUtils.isEmpty(value)) {
            return new BigDecimal(0);
        }

        try {
            BigDecimal bigDecimal = new BigDecimal(Double.parseDouble(value));
            BigDecimal one = new BigDecimal(1);
            return bigDecimal.divide(one, 2, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            log.error("value" + value, e);
        }

        return new BigDecimal(0);
    }

    public static BigDecimal getNegativeBigDecimal(String value) {
        if (StringUtils.isEmpty(value)) {
            return new BigDecimal(0);
        }

        try {
            BigDecimal bigDecimal = new BigDecimal(Double.parseDouble(value));
            BigDecimal one = new BigDecimal(-1);
            return bigDecimal.divide(one, 2, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            log.error("value" + value, e);
        }

        return new BigDecimal(0);
    }

    public static void main(String[] args) {
        System.out.println(getBigDecimal("100.01"));
        System.out.println(getNegativeBigDecimal("100.01"));
    }

    /**
     * @param v
     * @return 返回中文的金额
     */
    public static String toChineseAmt(double v) {
        String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
        String DIGIT = "零壹贰叁肆伍陆柒捌玖";
        double MAX_VALUE = 9999999999999.99D;
        if (v < 0 || v > MAX_VALUE) {
            return "参数非法!";
        }
        long l = Math.round(v * 100);
        if (l == 0) {
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }

}
