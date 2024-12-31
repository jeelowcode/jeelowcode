
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

import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 列宽样式策略
 */
public class CustomColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

    @Override
    protected void setColumnWidth(CellWriteHandlerContext context) {
        // 只需设置首行
        if (context.getRowIndex() != 0) {
            return;
        }
        DataFormatter dataFormatter = new DataFormatter();
        Cell cell = context.getCell();
        int length = dataFormatter.formatCellValue(cell).length();
        if (length < 6) {
            length = 6;
        }
        Sheet sheet = context.getWriteSheetHolder().getSheet();
        sheet.setColumnWidth(context.getColumnIndex(), length * 1000);
    }
}
