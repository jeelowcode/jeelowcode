
/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.excel.write;

import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class CellStyleUtils {

    /**
     * 对行使用相同的样式<br/>
     * 头和内容分别使用不同样式
     */
    public static HorizontalCellStyleStrategy getCellStyleStrategy(){
//        List<WriteCellStyle> contentStyleList = new ArrayList<>();// 内容奇偶数行样式不一致
//        contentStyleList.add(getOddRowContentStyle());
//        contentStyleList.add(getEvenRowContentStyle());
        return new HorizontalCellStyleStrategy(getHeadStyle(), getContentStyle());
    }

    /**
     * 头样式
     */
    public static WriteCellStyle getHeadStyle() {
        WriteCellStyle style = new WriteCellStyle();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setFormat("@");
        style.setDataFormatData(dataFormatData);// 单元格格式
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(false);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setWriteFont(font);// 字体
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直对齐方式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);// 边框样式
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    /**
     * 内容样式
     */
    public static WriteCellStyle getContentStyle() {
        WriteCellStyle style = new WriteCellStyle();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setFormat("@");
        style.setDataFormatData(dataFormatData);// 单元格格式
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 12);
        style.setWriteFont(font);// 字体
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直对齐方式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);// 边框样式
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    /**
     * 奇数行内容样式
     */
    public static WriteCellStyle getOddRowContentStyle() {
        WriteCellStyle style = new WriteCellStyle();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setFormat("@");
        style.setDataFormatData(dataFormatData);// 单元格格式
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 12);
        style.setWriteFont(font);// 字体
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直对齐方式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);// 边框样式
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    /**
     * 偶数行内容样式
     */
    public static WriteCellStyle getEvenRowContentStyle() {
        WriteCellStyle style = new WriteCellStyle();
        WriteFont font = new WriteFont();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setFormat("@");
        style.setDataFormatData(dataFormatData);// 文本格式
        font.setFontHeightInPoints((short) 12);
        style.setWriteFont(font);// 字体
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);// 水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直对齐方式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);// 边框样式
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());// 边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
}
