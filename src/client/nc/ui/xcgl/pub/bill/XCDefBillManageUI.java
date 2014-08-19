package nc.ui.xcgl.pub.bill;

import java.util.ArrayList;
import java.util.List;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.zmpub.pub.bill.BillRowNo;
import nc.ui.zmpub.pub.bill.DefBillManageUI;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.helper.IndexFineHeler;
import nc.vo.xcgl.pub.stock.BillStockTool;
import nc.vo.xcgl.pub.stock.PubStockOnHandVO;
import nc.vo.zmpub.pub.tool.ZmPubTool;
public abstract class XCDefBillManageUI extends DefBillManageUI{
	private static final long serialVersionUID = -7381247020087070897L;
	
	
	public boolean beforeEdit(BillEditEvent e) {
		String key=e.getKey();
		String minarea=null;
		if(getBillCardPanel().getHeadItem("pk_minarea")!=null){
		 minarea=PuPubVO.getString_TrimZeroLenAsNull(
				getBillCardPanel().getHeadItem("pk_minarea").getValueObject());//部门
		}
		if("invname".equalsIgnoreCase(key)&&e.getPos()==IBillItem.BODY){
			 if(isStockBill()==false){
			    return true;
			 }
			if(minarea==null){
				this.showWarningMessage("部门为空，请先选择部门!");
			}
		}
		
		return super.beforeEdit(e);
	}
	
	
	@Override
	public void afterEdit(BillEditEvent e) {
     //super.afterEdit(e);
	 String key = e.getKey();
	 if(BillItem.HEAD == e.getPos()){		
	     if("pk_invmandoc".equalsIgnoreCase(key) || "vdef20".equalsIgnoreCase(key)){
				 getBillCardPanel().execHeadEditFormulas();
	     }
	 }
	 if("invname".equalsIgnoreCase(key)&&e.getPos()==IBillItem.BODY){
		    if(isStockBill()==false){
		    	return;
		    }
		    int row=e.getRow();//当前选择的行
		    int rowno=getBillCardPanel().getBodyPanel().getTable().getRowCount();//统计当前表体有多少行
            String invmandoc=PuPubVO.getString_TrimZeroLenAsNull(
					getBillCardPanel().getBodyValueAt(e.getRow(), "pk_invmandoc"));
            String crowno=PuPubVO.getString_TrimZeroLenAsNull(
					getBillCardPanel().getBodyValueAt(e.getRow(), "crowno"));//当前选择行的行号
            String minarea=PuPubVO.getString_TrimZeroLenAsNull(
					getBillCardPanel().getHeadItem("pk_minarea").getValueObject());//部门
            String pk_corp=ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
            getBillCardPanel().setBodyValueAt(invmandoc, e.getRow(), "pk_father");
            getBillCardPanel().getBillModel().execEditFormulaByKey(e.getRow(), "father");
		    getBillCardPanel().execBodyFormula(e.getRow(), "father");
		    setCurrentStockNum(e.getRow(),isSetLockNum());		    
            IndexParaVO para=new IndexParaVO();
    		para.setPk_corp(pk_corp);
    		para.setPk_invmandoc(invmandoc);
    		para.setPk_minarea(minarea);
    		para.setIsstandingcrop(UFBoolean.TRUE);//是否产生现存量
            try {
				getIndexSet(para,row,rowno,crowno);
			} catch (Exception e1) {
				this.showErrorMessage(e1.getMessage());
				e1.printStackTrace();
			}	
		}
	 
	}
	/**
	 * is setlocknum
	 * @return
	 */
	public boolean isSetLockNum() {
		return false;
	}


	/**
	 * 
	 * @param row
	 */
	public void setCurrentStockNum(int row,boolean isSetLockNum) {
	      if(isStockOutBill()){
	    	  PubStockOnHandVO stockvo=new PubStockOnHandVO();
	    	  stockvo.setPk_corp(PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_corp").getValueObject()));
	    	  stockvo.setPk_factory(PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_factory").getValueObject()));
	    	  stockvo.setPk_stordoc(PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getHeadItem("pk_stordoc").getValueObject()));
	    	  stockvo.setPk_cargdoc(null);	
	    	  String pk_father=PuPubVO.getString_TrimZeroLenAsNull(
						getBillCardPanel().getBodyValueAt(row, "pk_father"));
	    	  if(pk_father==null || pk_father.length()==0){
	    		  pk_father=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getBodyValueAt(row, "pk_invmandoc"));
	    	  }
	    	  stockvo.setPk_father(pk_father);
	    	  stockvo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(
						getBillCardPanel().getBodyValueAt(row, "pk_invbasdoc")));
	    	  String pk_invmandoc=PuPubVO.getString_TrimZeroLenAsNull(getBillCardPanel().getBodyValueAt(row, "pk_invmandoc"));
	    	  if(pk_invmandoc==null || pk_invmandoc.length()==0){
	    		 return;
	    	  }
	    	  stockvo.setPk_invmandoc(pk_invmandoc);	
	    	  SuperVO[] cstocks=null;
			try {
				cstocks = BillStockTool.getInstrance().queryStockCombinForClient(new PubStockOnHandVO[]{stockvo});
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    	  PubStockOnHandVO cstock=(PubStockOnHandVO) cstocks[0];  
	    	  getBillCardPanel().setBodyValueAt(cstock.getNnum(), row, "ndryweight");
	    	  getBillCardPanel().setBodyValueAt(cstock.getNnum(), row, "nwetweight");
	    	  if(isSetLockNum){
	    		  getBillCardPanel().setBodyValueAt(cstock.getNlock(), row, "nlocked");  
	    	  }
	      }
		
	}


	public abstract boolean isStockBill();

	public  boolean isStockOutBill(){
		return false;
	}

	public void getIndexSet(IndexParaVO para,int row,int rowno,String crowno) throws Exception {
		try {
			List<LabIndexSetBVO> list=IndexFineHeler.getIndexFine(para);
			int[] s=delLine(crowno);
			if(s!=null&&s.length!=0){
				//删除多行
				getBillCardPanel().getBillModel().delLine(s);
			}
			int count=0;
			if(list!=null&&list.size()!=0){
				if(rowno-row>1){
				for(int i=0;i<list.size();i++){
					LabIndexSetBVO vo=list.get(i);
					  insertLine(row+count+1);
                      getBillCardPanel().setBodyValueAt(
                    		  vo.getPk_invmandoc(), row+count+1, "pk_invmandoc");
                      getBillCardPanel().setBodyValueAt(
                    		  vo.getPk_invbasdoc(), row+count+1, "pk_invbasdoc");
					  getBillCardPanel().setBodyValueAt(para.getPk_invmandoc()
                    		  , row+count+1, "pk_father");
					  getBillCardPanel().setBodyValueAt(crowno
                    		  , row+count+1, "vcrowno");
					  getBillCardPanel().getBillModel().execEditFormulaByKey(row+count+1, "invname");
				      getBillCardPanel().execBodyFormula(row+count+1, "invname");
				      getBillCardPanel().getBillModel().execEditFormulaByKey(row+count+1, "father");
				      getBillCardPanel().execBodyFormula(row+count+1, "father");
				      //if outstockbill need set current stocknum
				      setCurrentStockNum(row+count+1,isSetLockNum());	
				      count++;
				}
				}
				else{
					for(int i=0;i<list.size();i++){
						LabIndexSetBVO vo=list.get(i);
						getBillCardWrapper().addLine(); 
						 getBillCardPanel().setBodyValueAt(
	                    		  vo.getPk_invmandoc(), row+count+1, "pk_invmandoc");
	                      getBillCardPanel().setBodyValueAt(
	                    		  vo.getPk_invbasdoc(), row+count+1, "pk_invbasdoc");
						  getBillCardPanel().setBodyValueAt(para.getPk_invmandoc()
	                    		  , row+count+1, "pk_father");
						  getBillCardPanel().setBodyValueAt(crowno
	                    		  , row+count+1, "vcrowno");
						  getBillCardPanel().getBillModel().execEditFormulaByKey(row+count+1, "invname");
					      getBillCardPanel().execBodyFormula(row+count+1, "invname");
					      getBillCardPanel().getBillModel().execEditFormulaByKey(row+count+1, "father");
					      getBillCardPanel().execBodyFormula(row+count+1, "father");
					      //if outstockbill need set current stocknum
					      setCurrentStockNum(row+count+1,isSetLockNum());
					      count++;						
					}
				}
			}
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
	}	
	/**
	* 增行
	*/
	public void insertLine(int row) throws Exception {
		getBillCardPanel().stopEditing();
		//在row前面插入行
		getBillCardPanel().getBillModel().insertRow(row);
		//
		BillRowNo.addLineRowNo(getBillCardPanel(), getBillCardPanel().getBillType(), "crowno");
		if(row>=0){
		  BillRowNo.insertLineRowNos(getBillCardPanel(), getBillCardPanel().getBillType(), "crowno", row+1, 1);
		}
	}
	public int[] delLine(String crowno){
		List list=new ArrayList();
		int[] a;
		if(crowno==null){
			return null;
		}
		else{
	    	CircularlyAccessibleValueObject[] bodyvos = 
	    		getBillCardWrapper().getBillCardPanel().getBillModel().getBodyValueVOs(
	    				getUIControl().getBillVoName()[2]);
		if(bodyvos==null||bodyvos.length==0){
			return null;
		}
		else{
		for( int i=0;i<bodyvos.length;i++){
			if(crowno.equalsIgnoreCase(
					PuPubVO.getString_TrimZeroLenAsNull(bodyvos[i].getAttributeValue("vcrowno")))){
			list.add(i);	
			}
		}	
		if(list.size()==0||list==null){
           return null;			
		}
		else{
            a=new int[list.size()];
		  for(int j=0;j<list.size();j++){
			  a[j]=PuPubVO.getInteger_NullAs(list.get(j), -1);
		  }
		  return a;
		}
		}
		}
	}
	

}
