package nc.ui.xcgl.weighdoc;

import nc.ui.scm.pub.sourceref.BillRefListPanel;
import nc.ui.scm.pub.sourceref.DefaultSrcRefCtl;
import nc.ui.to.pub.TOBillTool;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.to.pub.BillItemVO;

public class DoubleBillRefListPanel5XToXC08 extends BillRefListPanel{

  /**
	 * 
	 */
	private static final long serialVersionUID = -5187437331850987981L;

public DoubleBillRefListPanel5XToXC08(
      String biztype, String sourcetype, String targettype, String pk_corp) {
    super(biztype, sourcetype, targettype, pk_corp);
    // TODO 自动生成构造函数存根
  }
  
  public DefaultSrcRefCtl getSourcectl(){
    if(sourcectl==null){
         sourcectl = new SourceRefDlgCtrl5XToXC08(getCsourcetype(),getCtargettype(),getPk_corp(),this);
       }
    return sourcectl;
 }
  
  /**
   * 获取参照单据模板的结点号
   */
  public String getRefNodeCode(){
    return "REFTEMPLET";
  }
  
  /**
   * 将来源单据设置到表体显示。
   */
  protected void setBodyVOToUI( CircularlyAccessibleValueObject[] srcBodyVOs){
    
    if(srcBodyVOs!=null && srcBodyVOs.length>=0){
    	// 解析批次字段
    	TOBillTool.getBatchCodeInfo((BillItemVO[]) srcBodyVOs);
		
      setBodyValueVO(srcBodyVOs);
      //getBatchCodeInfo(srcBodyVOs);
      getBodyBillModel().execLoadFormula();
    }else{
      getBodyBillModel().clearBodyData();
    }
  }
}
