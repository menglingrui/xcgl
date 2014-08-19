import java.util.ArrayList;
import java.util.List;

import nc.ui.pub.bill.BillCardPanel;
import nc.vo.zmpub.pub.report.ReportBaseVO;


public class ReportTest {
	
	 public static void main(String[] args) {
		List list=new ArrayList() ;
		String classname="nc.vo.xcgl.assay.AssayBBVO";
		String[] names=new String[]{"a","b","c"};
		List<String[]> conlist=null;
		
		ReportBaseVO[] rvos=new ReportBaseVO[conlist.size()];
		
		for(int i=0;i<rvos.length;i++){
			rvos[i]=new ReportBaseVO();
			for(int j=0;j<names.length;j++){
				rvos[i].setAttributeValue(names[j], conlist.get(i)[j]);
			}
		}
		
		BillCardPanel panel=new BillCardPanel();
		panel.getBillModel().setBodyDataVO(rvos);
		
	}

}
