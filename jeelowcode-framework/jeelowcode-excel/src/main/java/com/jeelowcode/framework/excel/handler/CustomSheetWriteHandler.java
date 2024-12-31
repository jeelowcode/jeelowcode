
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

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFSheet;

/**
 *
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {

    private static final Integer COLUMN = 26;

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        for (int i = 0; i < COLUMN; i++) {
            // 设置为文本格式
            SXSSFSheet sxssfSheet = (SXSSFSheet) writeSheetHolder.getSheet();
            CellStyle cellStyle = writeWorkbookHolder.getCachedWorkbook().createCellStyle();
            // 49为文本格式
            cellStyle.setDataFormat((short) 49);
            // i为列，一整列设置为文本格式
            sxssfSheet.setDefaultColumnStyle(i, cellStyle);
        }
    }
}
