package nc.ui.xcgl.saleweighdoc;


import java.awt.Container;

import nc.ui.ic.pub.pf.ICSourceRefBaseDlg;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.scm.pub.sourceref.IBillReferQueryProxy;
import nc.ui.scm.pub.sourceref.BillToBillRefPanel.ShowState;
import nc.vo.ic.pub.ICConst;
import nc.vo.pub.BusinessException;
/**
 * sale order dig
 * @author mlr
 *
 */
public class ICBillSourceDlg extends ICSourceRefBaseDlg{
	
	protected String IC035; //分单方式
	/**
	 * 
	 */
	private static final long serialVersionUID = -4824411222223441591L;

	public ICBillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, parent);
	}

	public ICBillSourceDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			String nodeKey, Object userObj, Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType, businessType,
				templateId, currentBillType, nodeKey, userObj, parent);
	}
    /**
     * 此处插入方法说明。 创建日期：(2001-4-23 9:17:37)
     */
    public void loadHeadData() {
        try {
            //清现存量
            //showInvOnhand(null);
            //返回查询条件
            
            if(getQueyDlg() instanceof IBillReferQueryProxy){
              if(((IBillReferQueryProxy)getQueyDlg()).isShowDoubleTableRef()){
                getBilltobillrefpanel().switchShow(ShowState.DoubleTable);
              }else{
                getBilltobillrefpanel().switchShow(ShowState.OneTable);
              }
            }
            if(m_whereStr==null || m_whereStr.length()==0){
            	m_whereStr=" coalesce(so_saleorder_b.nnumber,0)-to_number(coalesce(so_saleexecute.vdef20,0)) > 0 ";
            }else{
            	m_whereStr=m_whereStr+" and coalesce(so_saleorder_b.nnumber,0)-to_number(coalesce(so_saleexecute.vdef20,0)) > 0 ";
            }
            getbillListPanel().loadBillData(m_whereStr, null);
           
            isUpdateUIDataWhenSwitch = true;
            
          } catch (Exception e) {
            nc.vo.scm.pub.SCMEnv.out("数据加载失败！");
            nc.vo.scm.pub.SCMEnv.out(e);
            showErroMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000255")/*@res "数据加载失败"*/);
          }  
        }

	  /**
	   * 此处插入方法说明。 创建日期：(2001-7-6 19:38:38)
	   */
	@Override
	  protected void onOk() {		
		try {
			retBillVos = getSelectedSourceVOs();

			if (retBillVos == null || retBillVos.length == 0) {
				showErroMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000199")/* @res "请选择单据" */);
				return;
			}
			retBillVo = retBillVos.length > 0 ? retBillVos[0] : null;
			retBillVos =retBillVos;
			// 需要进行VO交换
			retBillVo = PfChangeBO_Client.pfChangeBillToBill(retBillVo, getBillType(),
						getCurrentBillType());
			
			retBillVos= PfChangeBO_Client.pfChangeBillToBillArray(retBillVos, getBillType(),
					getCurrentBillType());
			// retSrcBillVos=spiltVOS(retBillVos);
			this.getAlignmentX();
			this.closeOK();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.showErroMessage(e.getMessage());
		}
	 }     


	public String getIC035() {
			if(IC035==null){
				//分单方式
				try{
//					this.sysVo = SysInitBO_Client.queryByParaCode(
//							ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), "IC035");
				  this.IC035 = SysInitBO_Client.getParaString(ClientEnvironment
		            .getInstance().getCorporation().getPrimaryKey(), "IC035");
//					this.IC035 = (this.sysVo==null || this.sysVo.m_value==null)?null:this.sysVo.m_value.toString().trim(); 
									
				}catch(Exception e){
					nc.vo.scm.pub.SCMEnv.error(e);
				}
				if(IC035==null)
					IC035 = ICConst.SByWarehouse;
			}
			return IC035;
		}


}
