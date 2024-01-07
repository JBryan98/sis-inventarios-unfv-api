package com.unfv.sistema_inventarios_api.common.reports;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ExcelReportHelper {

    /**
     * Método para manejar el ancho de las columnas de manera automática
     * @param sheet La hoja excel con la que se trabaja
     * @param columns Número total de columnas
     * */
    public void autoSizeSheetColumns(Sheet sheet, int columns){
        for(int i = 0; i <= columns; i++){
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Método para el estilo de las cabeceras de los reportes
     * @param workbook El libro excel del reporte
     * @return retorna la configuración con los estilos
     * */
    public XSSFCellStyle tableHeaderColumnStyle(XSSFWorkbook workbook) throws DecoderException {
        byte[] rgb = Hex.decodeHex("FF5D0D");
        XSSFColor color = new XSSFColor(rgb, null);
        XSSFCellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        style.setFont(font);

        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    /**
     * Método para generar la respuesta del controller para los reportes .xlsx
     * @param response Respuesta que devolverá el controlador con el reporte
     * @param filename Nombre del archivo
     * @return La respuesta con los headers respectivos
     * */
    public HttpServletResponse getExcelReportResponse(HttpServletResponse response, String filename){
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        response.setContentType("application/octect-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + filename + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        return response;
    }
}
