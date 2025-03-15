package com.ctet.common;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;

import com.ctet.Exceptions.AssessmentGenericException;

public class ExcelReader {

	public static <T> List<T> parseExcelFileToBeans(final InputStream xlsFile, final File jxlsConfigFile) throws Exception {
		final XLSReader xlsReader = ReaderBuilder.buildFromXML(jxlsConfigFile);

		final List<T> result = new ArrayList<>();
		final Map<String, Object> beans = new HashMap<>();
		beans.put("result", result);
		try {
			xlsReader.read(xlsFile, beans);
		} catch (Exception r) {
			r.printStackTrace();
			throw new AssessmentGenericException("Problems in converting excel into java beans");
		}
		return result;
	}
}
