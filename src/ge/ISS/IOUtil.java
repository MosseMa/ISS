package ge.ISS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IOUtil {
	
	Properties property=new Properties();
	ArrayList<Float> result = new ArrayList<Float>();

	/**
	 * @return the result
	 */
	public ArrayList<Float> getResult() {
		return result;
	}

	/**
	 * @return the stan_result
	 */
	public ArrayList<Float> getStan_result() {
		return Stan_result;
	}

	ArrayList<Float> Stan_result = new ArrayList<Float>();

	public IOUtil() {
		// TODO Auto-generated constructor stub
	}

	public void setStan_ResultArray() {

		File excelFile = new File("C:\\Users\\212710307\\Desktop\\Task Routine\\Atals Collimator\\TEST.xlsx"); // config
																												// index
		InputStream in;

		try {
			in = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			if (workbook != null) {
				XSSFSheet worksheet = workbook.getSheetAt(0); // config index
				if (worksheet != null) {
					int ok = worksheet.getLastRowNum();
					for (int i = 2; i < worksheet.getLastRowNum(); i++) { // config index
						XSSFRow row = worksheet.getRow(i + 1);
						XSSFCell cell1 = row.getCell(5); // config index
						float ce1 = (float) cell1.getNumericCellValue();
						Stan_result.add(ce1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setExcelArray() {

		File excelFile = new File("C:\\Users\\212710307\\Desktop\\Task Routine\\Atals Collimator\\TEST.xlsx"); // config
																												// index
		InputStream in;
		try {
			in = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			if (workbook != null) {
				XSSFSheet worksheet = workbook.getSheetAt(0); // config index
				if (worksheet != null) {
					int ok = worksheet.getLastRowNum();
					for (int i = 2; i < worksheet.getLastRowNum(); i++) { // config index
						XSSFRow row = worksheet.getRow(i + 1);
						XSSFCell cell = row.getCell(12); // config index
						float ce = (float) cell.getNumericCellValue();
						result.add(ce);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 求取OD&ID面测试最大值
	public Float max(int head, int rail) throws IOException {
		float max = 0;
		List<Float> Resultarray = getResult().subList(head, rail);
		List<Float> Stan_Array = getStan_result().subList(head, rail);
		List<Float> Array = new ArrayList<Float>();
		for (int i = 0; i < Stan_Array.size(); i++) {
			Array.add(Resultarray.get(i) - Stan_Array.get(i));
		}
		max = Collections.max(Array);
		return max;
	}

	// 获取OD&ID面测试最小值
	public Float min(int head, int rail) throws IOException {
		float min = 0;
		List<Float> Resultarray = getResult().subList(head, rail);
		List<Float> Stan_Array = getStan_result().subList(head, rail);
		List<Float> Array = new ArrayList<Float>();
		for (int i = 0; i < Stan_Array.size(); i++) {
			Array.add(Resultarray.get(i) - Stan_Array.get(i));
		}
		min = Collections.min(Array);
		return min;
	}

	// 获取OD面bottom侧测试数据
	public ArrayList<Float> getID_Bottom() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(0, 547);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i));
			}
		}
		return Array;
	}

	// 获取OD面top侧测试数据
	public ArrayList<Float> getID_Top() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(0, 547);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i + 3));
			}
		}
		return Array;
	}

	// 获取ID面top侧测试数据
	public ArrayList<Float> getOD_Top() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(548, 1095);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i + 3));
			}
		}
		return Array;
	}

	// 获取ID面top侧测试数据
	public ArrayList<Float> getOD_Bottom() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(548, 1095);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i));
			}
		}
		return Array;
	}

	// ID与OD面bottom侧偏差
	public ArrayList<Float> Bottom_variant() {
		ArrayList<Float> Array = new ArrayList<Float>();
		for (int i = 0; i < getID_Bottom().size(); i++) {
			Array.add(getID_Bottom().get(i) - getOD_Bottom().get(i));
		}
		return Array;
	}

	// ID与OD面top侧偏差
	public ArrayList<Float> Top_variant() {
		ArrayList<Float> Array = new ArrayList<Float>();
		for (int i = 0; i < getID_Bottom().size(); i++) {
			Array.add(getID_Top().get(i) - getOD_Top().get(i));
		}
		return Array;
	}

	// 判定collimator等级
	public String classResult() {
		ArrayList<Float> Array = new ArrayList<Float>();
		String classResult = "";
		float sum = 0;
		for (int i = 0; i < getID_Bottom().size(); i++) {
			Array.add(Bottom_variant().get(i) - Top_variant().get(i));
			sum = sum + Array.get(i);
		}
		float mean = sum / Array.size();
		if (mean > -0.006 && mean < 0.006) {
			classResult = "C1";
		} else {
			classResult = "NO";
		}
		return classResult;
	}

	// PASS / FAIL 判定
	public String passResult() {
		String pass = "";
		try {
			if (min(0, 547) > -0.045 && max(0, 547) < 0.045 && min(548, 1095) > -0.045 && max(548, 1095) < 0.045) {
				pass = "PASS";
			} else {
				pass = "FAIL";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pass = "ERROR";
		}
		return pass;
	}

	public void exportResult(String SN) throws IOException {
		Writer writer = null;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss"); // 日期格式中月份字母需要大写
		File exportResult = new File("C:\\Users\\212710307\\Desktop\\Task Routine\\Atals Collimator\\" + SN + "_"
				+ format.format(date) + ".csv");
		StringBuffer buffer = new StringBuffer();
		try {
			writer = new FileWriter(exportResult);
			int ok = getResult().size();
			for (int i = 0; i < getResult().size(); i++) {
				buffer.append(getResult().get(i).toString());
				buffer.append("\n");
			}
			writer.write(buffer.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public boolean autoRun() {
		String path = "C:\\Users\\212710307\\Desktop\\Task Routine\\Atals Collimator\\TEST.txt";
		File TETZKfile = new File(path);
		Writer writer = null;
		try {
			writer = new FileWriter(TETZKfile);
			if (TETZKfile.exists()) {
				TETZKfile.delete();
				writer.write(path);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		;
		return true;
	}
}
