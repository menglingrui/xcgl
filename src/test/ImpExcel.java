

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
//		// ���ļ�
//		if (jfile.showOpenDialog(p) == javax.swing.JFileChooser.CANCEL_OPTION) {
//			return jfile.getSelectedFile().toString();
//		}
//		// �ļ���
//		String file = jfile.getSelectedFile().toString();
//
//		return file;
//	}
//	 
//	// �����ļ�Ŀ¼���Excel����
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
//	//ͨ��Excel�����ñ�����
//	public Sheet[] getEachSheet(Workbook wb) {
//			if (wb != null) {
//				return wb.getSheets();
//			}
//			return null;
//		}
//	//��ȡ�����ĳ�ֶε�λ��(�����к�)
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
//	//��ȡ��Ԫ������rowIndex���к�����colIndex���к���
//	public String getValueAt(Sheet st, int rowIndex, int colIndex) {
//			String strValueAt = "";
//			if (null != st.getCell(colIndex, rowIndex)) {
//				Cell cellUnit = st.getCell(colIndex, rowIndex);
//				if (null != cellUnit.getContents().trim()
//						&& !"".equals(cellUnit.getContents().toString().trim())) {
//
//					if (cellUnit.getType() == CellType.NUMBER
//							|| cellUnit.getType() == CellType.NUMBER_FORMULA) {// ��Ԫ������ֵ����
//						NumberCell nc = (NumberCell) cellUnit;
//
//						strValueAt = nc.getContents();
//					} else if (cellUnit.getType() == CellType.DATE
//							|| cellUnit.getType() == CellType.DATE_FORMULA) {// ��Ԫ������������
//						DateCell dt = (DateCell) cellUnit;
//						strValueAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.0")
//								.format(dt.getValue());
//					} else {// �ַ�������
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
//	//������ж���
//	public List<JobbasfilVO> readExcelStr(String file){
////			 ���Excel����
//			Workbook wb = this.getExcel(file);
//			Sheet[] sheets=wb.getSheets();
//			//Sheet[] sheets = this.getEachSheet(wb);// ���Excel������
//			Sheet sheet = sheets[0];// ����ļ����б����
//			String columns = "";
//			String values = "";
//			// ��Ҫ�������ֶ���
//			String[] colArr = { "jobcode", "jobname", "begindate", "forecastenddate",
//					"finishedflag", "enddate" };
//			int[] iArr = new int[colArr.length];
//			for (int i = 0; i < colArr.length; i++) {
//				iArr[i] = this.findColIndex(sheet, colArr[i]);// �����ֶ���Excel�ļ��е��кô�������
//				columns += colArr[i];// �ۼ��ַ���?
//				if (i != colArr.length - 1) {
//					columns += ",";
//				}
//			}
//			//���ն�����Excel�����
//			List<String [] > list = new ArrayList<String [] >();
//	 		String [] strs = null;//.getSepRow()
//			for (int i = 1; i < sheet.getRows(); i++) {
//				strs = new String[colArr.length];
//				for (int j = 0; j < iArr.length; j++) {
//					
////					values = values + "'" + this.getValueAt(sheet, i, j) + "'";// �����ֶ�ֵ������
//					strs[j] = this.getValueAt(sheet, i, j);
////					if (j != iArr.length - 1) {
////						values += ",";
////					}
//				}
//				list.add(strs);
//			}
//			List<JobbasfilVO> listUser = new ArrayList<JobbasfilVO>();//�������VO����
//				for(String [] str:list){
//					JobbasfilVO JobbasfilVO = new JobbasfilVO();
//					JobbasfilVO.setJobcode(str[0]);
//					JobbasfilVO.setJobname(str[1]);
//					
//					
//					listUser.add(JobbasfilVO);
//				}
//				//reportbasevo detail
////				List<reportbasev> reps = new ArrayList<JobbasfilVO>();//�������VO����
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
//	//������ж���
//	public List<SuperVO> readExcelStr(String file,String[] names,String classname){
////			 ���Excel����
//			Workbook wb = this.getExcel(file);
//			Sheet[] sheets=wb.getSheets();
//			//Sheet[] sheets = this.getEachSheet(wb);// ���Excel������
//			Sheet sheet = sheets[0];// ����ļ����б����
//			String columns = "";
//			String values = "";
//			// ��Ҫ�������ֶ���
//			String[] colArr = names;
//			int[] iArr = new int[colArr.length];
//			for (int i = 0; i < colArr.length; i++) {
//				iArr[i] = this.findColIndex(sheet, colArr[i]);// �����ֶ���Excel�ļ��е��кô�������
//				columns += colArr[i];// �ۼ��ַ���?
//				if (i != colArr.length - 1) {
//					columns += ",";
//				}
//			}
//			//���ն�����Excel�����
//			List<String [] > list = new ArrayList<String [] >();
//	 		String [] strs = null;//.getSepRow()
//			for (int i = 1; i < sheet.getRows(); i++) {
//				strs = new String[colArr.length];
//				for (int j = 0; j < iArr.length; j++) {
//					
////					values = values + "'" + this.getValueAt(sheet, i, j) + "'";// �����ֶ�ֵ������
//					strs[j] = this.getValueAt(sheet, i, j);
////					if (j != iArr.length - 1) {
////						values += ",";
////					}
//				}
//				list.add(strs);
//			}
//			List<JobbasfilVO> listUser = new ArrayList<JobbasfilVO>();//�������VO����
//				for(String [] str:list){
//					JobbasfilVO JobbasfilVO = new JobbasfilVO();
//					JobbasfilVO.setJobcode(str[0]);
//					JobbasfilVO.setJobname(str[1]);
//					
//					
//					listUser.add(JobbasfilVO);
//				}
//				//reportbasevo detail
////				List<reportbasev> reps = new ArrayList<JobbasfilVO>();//�������VO����
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
