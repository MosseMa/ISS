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

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IOUtil {
	ArrayList<Float> result = new ArrayList<Float>();
	Logger log=Logger.getLogger(getClass());
	Properties Configurationproperty=new Properties();
	{
		InputStream proIn=null;
		String jarPath=System.getProperty("user.dir");
		String proPath=jarPath+"\\config\\Config.properties";
		File proFile=new File(proPath);
		if(proFile!=null) {
			try {
				proIn=new FileInputStream(proFile);
				try {
					Configurationproperty.load(proIn);
					log.info("property loaded");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("propert load eroor");
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("can not find the property file");
			}
			finally {
				if(proIn!=null) {
					try {
						proIn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("IO close error");
					}
				}
			}
		}
		
	}
	/**
	 * @return the result
	 */
	public ArrayList<Float> getResult() {
		return result;
	}
	/**
	 * @return the property
	 */
	public Properties getConfigurationproperty() {
		return Configurationproperty;
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

	public void setExcelArray() throws IOException {
		String excelPath=getConfigurationproperty().getProperty("ResultReadPath");//C:\Users\212710307\Desktop\Task Routine\Atals Collimator\TEST.xlsx
		String excelName=getConfigurationproperty().getProperty("ResultFileName");
		String filename=excelPath+excelName;
		int sheetNum=Integer.parseInt(getConfigurationproperty().getProperty("ResultSheetNumber"));//0
		int rowNum=Integer.parseInt(getConfigurationproperty().getProperty("ResultRowNumber"));//2
		int StancolumnNum=Integer.parseInt(getConfigurationproperty().getProperty("ResultStanColumnNumber"));
		int columnNum=Integer.parseInt(getConfigurationproperty().getProperty("ResultColumnNumber"));//12
		File excelFile = new File(filename); // config	
		InputStream in=null;
		try {
			in = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			if (workbook != null) {
				XSSFSheet worksheet = workbook.getSheetAt(sheetNum); // config index
				if (worksheet != null) {
					int ok = worksheet.getLastRowNum();
					for (int i = rowNum; i < worksheet.getLastRowNum(); i++) { // config index
						XSSFRow row = worksheet.getRow(i + 1);
						XSSFCell cell = row.getCell(columnNum); // config index
						XSSFCell stancell = row.getCell(StancolumnNum); // config index		
						float ce = (float) cell.getNumericCellValue();
						float stance = (float) stancell.getNumericCellValue();
						result.add(ce);
						Stan_result.add(stance);						
					}
					log.info("gained the measure result and stan	result");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("can not find the result excel on gainning measure step");
		}
		finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("IO error on closing the measure value step");
				}
			}
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
		if(head==0) {
		log.info("gained the ID side max");
		}
		else {
			log.info("gained the OD side max");
		}
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
		if(head==0) {
		log.info("gained the ID side min");
		}
		else {
			log.info("gained the OD side min");
		}
		return min;
	}

	// 获取OD面bottom侧测试数据
	public ArrayList<Float> getID_Bottom() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(0, getResult().size()/2-1);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i));
			}
		}
		log.info("get the ID bottom array");
		return Array;
	}

	// 获取OD面top侧测试数据
	public ArrayList<Float> getID_Top() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(0, getResult().size()/2-1);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i + 3));
			}
		}
		log.info("get the ID top array");
		return Array;
	}

	// 获取ID面top侧测试数据
	public ArrayList<Float> getOD_Top() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(getResult().size()/2, getResult().size()-1);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i + 3));
			}
		}
		log.info("get the OD top array");
		return Array;
	}

	// 获取ID面top侧测试数据
	public ArrayList<Float> getOD_Bottom() {
		ArrayList<Float> Array = new ArrayList<Float>();
		List<Float> ResultArray = getResult().subList(getResult().size()/2, getResult().size()-1);
		for (int i = 0; i < ResultArray.size() / 4; i++) {
			if (i % 4 == 0) {
				Array.add(ResultArray.get(i));
			}
		}
		log.info("get the OD BOTTOM array");
		return Array;
	}

	// ID与OD面bottom侧偏差
	public ArrayList<Float> Bottom_variant() {
		ArrayList<Float> Array = new ArrayList<Float>();
		for (int i = 0; i < getID_Bottom().size(); i++) {
			Array.add(getID_Bottom().get(i) - getOD_Bottom().get(i));
		}
		log.info("get the bottom variant array");
		return Array;
	}

	// ID与OD面top侧偏差
	public ArrayList<Float> Top_variant() {
		ArrayList<Float> Array = new ArrayList<Float>();
		for (int i = 0; i < getID_Bottom().size(); i++) {
			Array.add(getID_Top().get(i) - getOD_Top().get(i));
		}
		log.info("get the top variant array");
		return Array;
	}

	// 判定collimator等级
	public String classResult() {
		float USL=Float.parseFloat(getConfigurationproperty().getProperty("MaxClassSpec"));//0.006
		float LSL=Float.parseFloat(getConfigurationproperty().getProperty("MinClassSpec"));//-0.006
		ArrayList<Float> Array = new ArrayList<Float>();
		String classResult = "";
		float sum = 0;
		for (int i = 0; i < getID_Bottom().size(); i++) {
			Array.add(Bottom_variant().get(i) - Top_variant().get(i));
			sum = sum + Array.get(i);
		}
		float mean = sum / Array.size();
		if (mean > LSL && mean < USL) {
			classResult = "C1";
		} else {
			classResult = "NO";
		}
		log.info("get the classresult:"+classResult);
		return classResult;
	}

	// PASS / FAIL 判定
	public String passResult(String sn) {
		float CTQ_USL=Float.parseFloat(getConfigurationproperty().getProperty("MaxSpec"));
		float CTQ_LSL=Float.parseFloat(getConfigurationproperty().getProperty("MinSpec"));
		int RT_arrayLength=Integer.parseInt(getConfigurationproperty().getProperty("RT_Length"));
		int T_arrayLength=Integer.parseInt(getConfigurationproperty().getProperty("T_Length"));
		String pass = "";
		try {
			if(sn.contains("R")) { //reference 判断
			if (min(0, RT_arrayLength/2-1) > CTQ_LSL && max(0, RT_arrayLength/2-1) < CTQ_USL && min(RT_arrayLength/2, RT_arrayLength) > CTQ_LSL && max(RT_arrayLength/2, RT_arrayLength) < CTQ_USL) {
				pass = "PASS";
			} else {
				pass = "FAIL";
			}
			}else{			//normal 判断
				if (min(0, T_arrayLength/2-1) > CTQ_LSL && max(0, T_arrayLength/2-1) < CTQ_USL && min(T_arrayLength/2, T_arrayLength) > CTQ_LSL && max(T_arrayLength/2, RT_arrayLength) < CTQ_USL) {
					pass = "PASS";
				} else {
					pass = "FAIL";
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pass = "ERROR";
		}
		log.info("get the passresult:"+pass);
		return pass;
	}

	public void exportResult(String SN) throws IOException {
		String excelPath=getConfigurationproperty().getProperty("ResultReadPath");//C:\Users\212710307\Desktop\Task Routine\Atals Collimator\TEST.xlsx
		String excelName=getConfigurationproperty().getProperty("ResultFileName");
		String filename=excelPath+excelName;
		File excelFile=new File(filename);
		String exportPath=getConfigurationproperty().getProperty("ResultExportPath");
		String DateFormat=getConfigurationproperty().getProperty("DateFormat");
		Writer writer = null;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DateFormat); // 日期格式中月份字母需要大写
		File exportResult = new File(exportPath + SN + "_"+ format.format(date) + ".csv");
		StringBuffer buffer = new StringBuffer();
		try {
			writer = new FileWriter(exportResult);
			for (int i = 0; i < getResult().size(); i++) {
				buffer.append(getResult().get(i).toString());
				buffer.append("\n");
			}
			writer.write(buffer.toString());
			log.info("write into the csv and saved");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
				if(excelFile.exists()) {
					excelFile.delete();
				}
			}
		}
	}
	
	public boolean autoRun() {
		String autorunPath=getConfigurationproperty().getProperty("AutorunPath"); 
		String autorunFile=getConfigurationproperty().getProperty("AutorunFileName");
		String taskPath=getConfigurationproperty().getProperty("TETZK_TaskPath");
		String taskName=getConfigurationproperty().getProperty("TETZK_Task");		
		String autorun=autorunPath+autorunFile;
		String task=taskPath+taskName;
		
		File TETZKfile = new File(autorun);
		Writer writer = null;
		try {
			writer = new FileWriter(TETZKfile);
			if (TETZKfile.exists()) {
				TETZKfile.exists();
				writer.write(task);
				writer.flush();
				log.info("created the autorun file");
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
		return true;
	}
}
