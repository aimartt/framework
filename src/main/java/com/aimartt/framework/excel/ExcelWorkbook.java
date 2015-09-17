package com.aimartt.framework.excel;

import org.apache.poi.ss.usermodel.Workbook;

public class ExcelWorkbook {

	/** 后缀名 */
	private final String suffix;
	/** 工作表 */
	private final Workbook workbook;

	public ExcelWorkbook(Workbook workbook, String suffix) {
		this.workbook = workbook;
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

}