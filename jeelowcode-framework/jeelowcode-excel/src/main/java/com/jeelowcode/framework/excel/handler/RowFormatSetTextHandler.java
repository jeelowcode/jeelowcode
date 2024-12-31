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
package com.jeelowcode.framework.excel.handler;

import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.jeelowcode.framework.utils.utils.FuncBase;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单元格设置文本格式
 */
public class RowFormatSetTextHandler implements CellWriteHandler {

    Map<Integer, List<String>> dropdownOptionsMap=new HashMap<>();

    Map<Integer, CellStyle> cellStyleMap = new HashMap<>();

    public RowFormatSetTextHandler() {
    }


    @Override
    public void beforeCellCreate(CellWriteHandlerContext context) {

        WriteSheetHolder writeSheetHolder = context.getWriteSheetHolder();
        Sheet sheet = writeSheetHolder.getSheet();
        Integer rowIndex = context.getRowIndex();
        if(rowIndex!=0){
            return;
        }
        Integer columnIndex = context.getColumnIndex();

        //下拉列表
        List<String> dropdownOptionList = dropdownOptionsMap.get(columnIndex);
        if(FuncBase.isEmpty(dropdownOptionList)){
            return;
        }

        createDropdownList(sheet,dropdownOptionList,columnIndex);
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        WriteCellData<?> cellData = context.getFirstCellData();
        // 清除原有单元格颜色
        WriteCellStyle writeCellStyle = cellData.getWriteCellStyle();
        writeCellStyle.setFillForegroundColor(null);
        // 重新设置自定义RGB颜色
        CellStyle cellStyle;
        int cellStyleKey;
        Integer rowIndex = context.getRowIndex();
        if (rowIndex == 0) {
            // 第0行，即表头
            cellStyleKey = 0;
            cellStyle = cellStyleMap.computeIfAbsent(cellStyleKey,
                    value -> getCellStyle(cellData, context, new Color(115, 174, 76)));
        } else if ((rowIndex & 1) == 0) {
            // 偶数行
            cellStyleKey = 2;
            cellStyle = cellStyleMap.computeIfAbsent(cellStyleKey,
                    value -> getCellStyle(cellData, context, new Color(255, 255, 255)));
        } else {
            // 奇数行
            cellStyleKey = 1;
            cellStyle = cellStyleMap.computeIfAbsent(cellStyleKey,
                    value -> getCellStyle(cellData, context, new Color(227, 239, 219)));
        }
        cellData.setOriginCellStyle(cellStyle);
    }

    /**
     * 单元格样式
     */
    private CellStyle getCellStyle(WriteCellData<?> cellData, CellWriteHandlerContext context, Color color) {
        CellStyle style = cellData.getOriginCellStyle();
        if (FuncBase.isEmpty(style)) {
            style = context.getWriteWorkbookHolder().getWorkbook().createCellStyle();
        }
        XSSFColor xssfColor = new XSSFColor(color, new DefaultIndexedColorMap());
        ((XSSFCellStyle) style).setFillForegroundColor(xssfColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    /**
     * 下拉
     * @param sheet
     * @param dropdownOptionList
     */
    public void createDropdownList( Sheet sheet, List<String> dropdownOptionList, int firstCol) {
        if(FuncBase.isEmpty(dropdownOptionList)){
            return;
        }

        DataValidationHelper helper = sheet.getDataValidationHelper();

        String[] dropdownOptionsArray = dropdownOptionList.toArray(new String[0]);

        // 创建下拉列表的约束
        DataValidationConstraint constraint = helper.createExplicitListConstraint(dropdownOptionsArray);

        // 设置下拉列表应用的单元格区域，例如第2行到最后一行的第2列
        int firstRow = 1; // 通常Excel中第一行是1，这里假设第一行为表头
        int lastRow=65536;
        int lastCol = firstCol;
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

        // 创建数据验证规则并添加到工作表
        DataValidation validation = helper.createValidation(constraint, regions);
        sheet.addValidationData(validation);
    }


    public Map<Integer, List<String>> getDropdownOptionsMap() {
        return dropdownOptionsMap;
    }

    public void setDropdownOptionsMap(Map<Integer, List<String>> dropdownOptionsMap) {
        this.dropdownOptionsMap = dropdownOptionsMap;
    }
}