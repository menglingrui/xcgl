

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
//import com.ufsoft.ntb.table.test.DateCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import nc.vo.pub.SuperVO;
import nc.vo.uapbd.itembasedoc.JobbasfilVO;

public class ImpExcel {
//	 private static final String ENCOD = "GBK";
//	 public ImpExcel() {} 
//	 
//	 public static String getfile(JPanel p) {
//		JFileChooser jfile = new JFileChooser();
//
//		jfile.setDialogType(JFileChooser.SAVE_DIALOG);
//		// 打开文件
//		if (jfile.showOpenDialog(p) == javax.swing.JFileChooser.CANCEL_OPTION) {
//			return jfile.getSelectedFile().toString();
//		}
//		// 文件名
//		String file = jfile.getSelectedFile().toString();
//
//		return file;
//	}
//	 
//	// 根据文件目录获得Excel对象
//	public Workbook getExcel(String path) {
//			Workbook wb = null;
//			try {
//				InputStream is = new FileInputStream(new File(path));
//				wb = Workbook.getWorkbook(is);
//			} catch (BiffException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return wb;
//	
//		}
//	
//	//通过Excel对象获得表格对象
//	public Sheet[] getEachSheet(Workbook wb) {
//			if (wb != null) {
//				return wb.getSheets();
//			}
//			return null;
//		}
//	//获取表标题某字段的位置(所在列号)
//	public int findColIndex(Sheet st, String setHead) {
//			int intIndex = 0;
//			for (int i = 0; i < st.getColumns(); i++) {
//				String strCon = st.getCell(i, 0).getContents().toString().trim();
//				if (setHead.equals(strCon)) {
//					intIndex = i;
//				}
//			}
//			return intIndex;
//		}
//	//获取单元格内容rowIndex：行号数。colIndex：列号树
//	public String getValueAt(Sheet st, int rowIndex, int colIndex) {
//			String strValueAt = "";
//			if (null != st.getCell(colIndex, rowIndex)) {
//				Cell cellUnit = st.getCell(colIndex, rowIndex);
//				if (null != cellUnit.getContents().trim()
//						&& !"".equals(cellUnit.getContents().toString().trim())) {
//
//					if (cellUnit.getType() == CellType.NUMBER
//							|| cellUnit.getType() == CellType.NUMBER_FORMULA) {// 单元格是数值类型
//						NumberCell nc = (NumberCell) cellUnit;
//
//						strValueAt = nc.getContents();
//					} else if (cellUnit.getType() == CellType.DATE
//							|| cellUnit.getType() == CellType.DATE_FORMULA) {// 单元格是日期类型
//						DateCell dt = (DateCell) cellUnit;
//						strValueAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.0")
//								.format(dt.getValue());
//					} else {// 字符串类型
//						try {
//							String temp = cellUnit.getContents().trim();
//							strValueAt = new String(temp.getBytes(), ENCOD).trim();
//						} catch (Exception e) {
//							// log4gm.error(e.toString());
//						}
//					}
//				}
//			}
//
//			return strValueAt;
//		}
//
//	//获得所有对象
//	public List<JobbasfilVO> readExcelStr(String file){
////			 获得Excel对象
//			Workbook wb = this.getExcel(file);
//			Sheet[] sheets=wb.getSheets();
//			//Sheet[] sheets = this.getEachSheet(wb);// 获得Excel表格对象
//			Sheet sheet = sheets[0];// 获得文件所有表对象
//			String columns = "";
//			String values = "";
//			// 需要操作的字段名
//			String[] colArr = { "jobcode", "jobname", "begindate", "forecastenddate",
//					"finishedflag", "enddate" };
//			int[] iArr = new int[colArr.length];
//			for (int i = 0; i < colArr.length; i++) {
//				iArr[i] = this.findColIndex(sheet, colArr[i]);// 将各字段在Excel文件中的列好存入数组
//				columns += colArr[i];// 累加字符串?
//				if (i != colArr.length - 1) {
//					columns += ",";
//				}
//			}
//			//接收多数据Excel结果集
//			List<String [] > list = new ArrayList<String [] >();
//	 		String [] strs = null;//.getSepRow()
//			for (int i = 1; i < sheet.getRows(); i++) {
//				strs = new String[colArr.length];
//				for (int j = 0; j < iArr.length; j++) {
//					
////					values = values + "'" + this.getValueAt(sheet, i, j) + "'";// 将各字段值串起来
//					strs[j] = this.getValueAt(sheet, i, j);
////					if (j != iArr.length - 1) {
////						values += ",";
////					}
//				}
//				list.add(strs);
//			}
//			List<JobbasfilVO> listUser = new ArrayList<JobbasfilVO>();//获得所有VO对象
//				for(String [] str:list){
//					JobbasfilVO JobbasfilVO = new JobbasfilVO();
//					JobbasfilVO.setJobcode(str[0]);
//					JobbasfilVO.setJobname(str[1]);
//					
//					
//					listUser.add(JobbasfilVO);
//				}
//				//reportbasevo detail
////				List<reportbasev> reps = new ArrayList<JobbasfilVO>();//获得所有VO对象
////				for(String [] str:list){
////					JobbasfilVO JobbasfilVO = new JobbasfilVO();
////					JobbasfilVO.setJobcode(str[0]);
////					JobbasfilVO.setJobname(str[1]);
////					
////					
////					listUser.add(JobbasfilVO);
////				}
//			
//			return listUser;
//		}    
//	
//	//获得所有对象
//	public List<SuperVO> readExcelStr(String file,String[] names,String classname){
////			 获得Excel对象
//			Workbook wb = this.getExcel(file);
//			Sheet[] sheets=wb.getSheets();
//			//Sheet[] sheets = this.getEachSheet(wb);// 获得Excel表格对象
//			Sheet sheet = sheets[0];// 获得文件所有表对象
//			String columns = "";
//			String values = "";
//			// 需要操作的字段名
//			String[] colArr = names;
//			int[] iArr = new int[colArr.length];
//			for (int i = 0; i < colArr.length; i++) {
//				iArr[i] = this.findColIndex(sheet, colArr[i]);// 将各字段在Excel文件中的列好存入数组
//				columns += colArr[i];// 累加字符串?
//				if (i != colArr.length - 1) {
//					columns += ",";
//				}
//			}
//			//接收多数据Excel结果集
//			List<String [] > list = new ArrayList<String [] >();
//	 		String [] strs = null;//.getSepRow()
//			for (int i = 1; i < sheet.getRows(); i++) {
//				strs = new String[colArr.length];
//				for (int j = 0; j < iArr.length; j++) {
//					
////					values = values + "'" + this.getValueAt(sheet, i, j) + "'";// 将各字段值串起来
//					strs[j] = this.getValueAt(sheet, i, j);
////					if (j != iArr.length - 1) {
////						values += ",";
////					}
//				}
//				list.add(strs);
//			}
//			List<JobbasfilVO> listUser = new ArrayList<JobbasfilVO>();//获得所有VO对象
//				for(String [] str:list){
//					JobbasfilVO JobbasfilVO = new JobbasfilVO();
//					JobbasfilVO.setJobcode(str[0]);
//					JobbasfilVO.setJobname(str[1]);
//					
//					
//					listUser.add(JobbasfilVO);
//				}
//				//reportbasevo detail
////				List<reportbasev> reps = new ArrayList<JobbasfilVO>();//获得所有VO对象
////				for(String [] str:list){
////					JobbasfilVO JobbasfilVO = new JobbasfilVO();
////					JobbasfilVO.setJobcode(str[0]);
////					JobbasfilVO.setJobname(str[1]);
////					
////					
////					listUser.add(JobbasfilVO);
////				}
//			
//			return listUser;
//		}    
	     

}
