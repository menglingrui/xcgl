package nc.ui.xcgl.weighdoc;


import java.awt.Container;

import nc.ui.ic.pub.pf.ICSourceRefBaseDlg;
import nc.ui.ic.pub.tools.GenMethod;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.scm.pub.sourceref.IBillReferQueryProxy;
import nc.ui.scm.pub.sourceref.BillToBillRefPanel.ShowState;
import nc.vo.ic.pub.ICConst;
import nc.vo.ic.pub.bill.IItemKey;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
/**
 * ���������Ի���
 * @author mlr
 *
 */
public class ICBillSourceDlg extends ICSourceRefBaseDlg{
	
	protected String IC035; //�ֵ���ʽ
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
     * �˴����뷽��˵���� �������ڣ�(2001-4-23 9:17:37)
     */
    public void loadHeadData() {
      try {
        //���ִ���
        //showInvOnhand(null);
        //���ز�ѯ����
        
        if(getQueyDlg() instanceof IBillReferQueryProxy){
          if(((IBillReferQueryProxy)getQueyDlg()).isShowDoubleTableRef()){
            getBilltobillrefpanel().switchShow(ShowState.DoubleTable);
          }else{
            getBilltobillrefpanel().switchShow(ShowState.OneTable);
          }
        }
        if(m_whereStr==null || m_whereStr.length()==0){
        	m_whereStr=" coalesce(to_bill_b.nnum,0)-to_number(coalesce(to_bill_b.vbdef20,0)) > 0 ";
        }else{
        	m_whereStr=m_whereStr+" and  coalesce(to_bill_b.nnum,0)-to_number(coalesce(to_bill_b.vbdef20,0)) > 0  ";
        }
        getbillListPanel().loadBillData(m_whereStr, null);
       
        isUpdateUIDataWhenSwitch = true;
        
      } catch (Exception e) {
        nc.vo.scm.pub.SCMEnv.out("���ݼ���ʧ�ܣ�");
        nc.vo.scm.pub.SCMEnv.out(e);
        showErroMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000255")/*@res "���ݼ���ʧ��"*/);
      }  
    }

	  /**
	   * �˴����뷽��˵���� �������ڣ�(2001-7-6 19:38:38)
	   */
	@Override
	  protected void onOk() {
//	    try{
//	      retBillVos = null;
//	      retBillVo = null;
//	      retSrcBillVos = null;
//	      retSrcBillVo = null;
//	    
//	      retBillVos = getSelectedSourceVOs();//getbillListPanel().getSelectedSourceVOs();
//	      retSrcBillVos = retBillVos;
//	      
//	      if(Bill53Const.cbilltype.equals(getCurrentBillType()))
//	        retBillVos = PfChangeBO_Client.pfChangeBillToBillArray(retBillVos, getBillType(), getCurrentBillType());
//	      else
//	        retBillVos = ICDefaultSrcRefCtl.getTargetVOsForUIRef(retBillVos,getBillType() , getCurrentBillType(), 
//	          ((BillRefListPanel)getbillListPanel()).getHeadBillModel().getFormulaParse(), 
//	          getIC035());
//	    
//	    }catch(BusinessException e){
//	      showErroMessage(e.getMessage());
//	      return;
//	    }
	    
	    

		
		try {
			retBillVos = getSelectedSourceVOs();

			if (retBillVos == null || retBillVos.length == 0) {
				showErroMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000199")/* @res "��ѡ�񵥾�" */);
				return;
			}
			retBillVo = retBillVos.length > 0 ? retBillVos[0] : null;
			retBillVos = spiltVOS(retBillVos);
			// ��Ҫ����VO����
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
		}
	  }
	
      
	  /**
	   * ֧�ֵַ�
	   * @param sourcebillvos
	   * @return
	   */
	  public AggregatedValueObject[] spiltVOS(
			AggregatedValueObject[] sourcebillvos) {
//			 �޸��ˣ������� �޸�ʱ�䣺2009-7-15 ����10:23:24 �޸�ԭ����IItemKey.bdirecttranflag���IItemKey.bdrictflag
		    String[] headcond = new String[]{
		        "csettlepathid","cbiztypeid",IItemKey.bdirecttranflag,"cincorpid","cincbid","coutcorpid","coutcbid","ctakeoutcorpid","ctakeoutcbid"
		    };
		    
		    String[] bodycond = new String[]{
		        "ctypecode","bretractflag",IItemKey.bdirecttranflag,"cincorpid","cincbid","cinwhid","cindeptid","coutcorpid","coutcbid","ctakeoutcorpid",
		        "ctakeoutcbid","ctakeoutwhid","ctakeoutdeptid","pk_sendtype","coutpsnid","cinpsnid" //,"vreceiveaddress"
		    };
		    return SplitBillVOs.getSplitVOs(sourcebillvos[0].getClass().getName(),
		        sourcebillvos[0].getParentVO().getClass().getName(),sourcebillvos[0].getChildrenVO()[0].getClass().getName(),
		        sourcebillvos,headcond,bodycond
		        );
		    
	}

	public String getIC035() {
			if(IC035==null){
				//�ֵ���ʽ
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
