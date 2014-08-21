package nc.ui.xcgl.weighdoc;

import java.util.ArrayList;
import java.util.Arrays;

import nc.bs.framework.common.NCLocator;
import nc.itf.scm.to.pub.IBusinessTypeTool;
import nc.itf.scm.to.service.IOuter;
import nc.ui.pub.ClientEnvironment;
import nc.ui.scm.pub.sourceref.DefaultSrcRefCtl;
import nc.ui.scm.pub.sourceref.IBillToBillListPanel;
import nc.ui.to.icredun.DoubleBillRefListPanel5XTo4Y;
import nc.ui.to.icredun.SimpBillRefListPanel5XTo4Y;
import nc.ui.to.pub.ClientCommonDataHelper;
import nc.ui.to.pub.TOBillBusinessTool;
import nc.ui.to.pub.TOBillTool;
import nc.ui.to.pub.TOEnvironment;
import nc.ui.to.pub.power.TOQueryPrivilegeTool;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pub.OnHandRefreshVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.to.pub.BillHeaderVO;
import nc.vo.to.pub.BillItemVO;
import nc.vo.to.pub.ConstVO;
import nc.vo.to.pub.Log;
import nc.vo.to.pub.tools.ExceptionUtils;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;

public class SourceRefDlgCtrl5XToXC08 extends DefaultSrcRefCtl{
  private String m_whereStr = "";
  // ��ͷ�����ѯ����
  private String m_sBodyWhere = "";

  private String m_sHeaderWhere = "";
  
  //С��λ������
  private int m_iaScale[] = null;

  public SourceRefDlgCtrl5XToXC08(
      String srcbilltype, String targerbilltype, String pk_corp, IBillToBillListPanel refpanel) {
    super(srcbilltype, targerbilltype, pk_corp, refpanel);
  }
  
  /*
   * ��ȡ��ǰ�в�ѯ�ִ���
   */
  public OnHandRefreshVO getOnHandRefreshVO(int irow) {

    OnHandRefreshVO voOnhand = new OnHandRefreshVO();

    if (irow != -1) {
      //���ѡ�е��ж�Ӧ��VO(�˷������صı���VOֻ��һ��)
      IBillToBillListPanel avo = getbillListPanel();
      
          //�����ִ�����ѯ���������������֯������������������Ρ������ֿ�
          voOnhand.setCcalbodyid(avo.getSourceBodyValueAt(irow, "ctakeoutcbid").toString());
          voOnhand.setCcorpid(avo.getSourceBodyValueAt(irow, "ctakeoutcorpid").toString());

          voOnhand.setCwhid(avo.getSourceBodyValueAt(irow, "ctakeoutwhid").toString());
          voOnhand.setCinvid(avo.getSourceBodyValueAt(irow, "ctakeoutinvid").toString());
          voOnhand.setVbatch(avo.getSourceBodyValueAt(irow, "vbatch").toString());
          voOnhand.setCcustid(avo.getSourceBodyValueAt(irow, "creceieveid").toString());
          voOnhand.setAttributeValue("castunitid", avo.getSourceBodyValueAt(irow, "castunitid").toString());

          //�����������֯����������Ԥ�Ƴ�������
          voOnhand.setDPlanDate(new UFDate(avo.getSourceBodyValueAt(irow, "dplanoutdate").toString()));

          //����������
          voOnhand.setAttributeValue("vfree1", avo.getSourceBodyValueAt(irow, "vfree1").toString());
          voOnhand.setAttributeValue("vfree2", avo.getSourceBodyValueAt(irow, "vfree2").toString());
          voOnhand.setAttributeValue("vfree3", avo.getSourceBodyValueAt(irow, "vfree3").toString());
          voOnhand.setAttributeValue("vfree4", avo.getSourceBodyValueAt(irow, "vfree4").toString());
          voOnhand.setAttributeValue("vfree5", avo.getSourceBodyValueAt(irow, "vfree5").toString());

          voOnhand.setAttributeValue("crowno", String.valueOf(0));
          voOnhand.setAttributeValue("cinvbasdoc", avo.getSourceBodyValueAt(irow, "cinvbasdoc").toString());

          TOEnvironment te = new TOEnvironment();
          voOnhand.setAttributeValue("userid", te.getUser()
              .getPrimaryKey());

          return voOnhand;
    }
    return null;
  }
  
  @Override
  public String[] getSourceHeadHideCol() {
    //��ȥ��ͷ�ĵ���������ֿ��ֶ�
    return new String[]{"coutwhid","cinwhid","coutwhname","cinwhname"};
  }
  
  public String getSourceBodyCondition() {
    String sBodyCondition = "";

    boolean bHasInvcl = false; //�Ƿ���������������
    if (m_sBodyWhere.indexOf("bd_invcl.") != -1) {
      bHasInvcl = true;
    }

    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      //�������ⵥ����������+�˻���+;����>�ۼƳ������� �Ŀ��Բ���
      //20050912��by yuanhm�������ⵥ����������+�˻���+;����-Ӧ��δ��������>�ۼƳ������� �Ŀ��Բ���
      sBodyCondition = sBodyCondition
          + " and abs(isnull(to_bill_b.nnum,0))+abs(isnull(to_bill_b.norderbacknum,0))+abs(isnull(to_bill_b.norderwaylossnum,0))-abs(isnull(to_bill_b.nordershouldoutnum,0))> abs(isnull(to_bill_b.norderoutnum,0)) and to_bill_b.boutendflag='N' and to_bill_b.dr = 0 ";

    } 
    
    if (bHasInvcl) {
      sBodyCondition = sBodyCondition
          + " and exists (select 1 from bd_invbasdoc,bd_invcl where to_bill_b.cinvbasid = bd_invbasdoc.pk_invbasdoc and bd_invbasdoc.pk_invcl = bd_invcl.pk_invcl and to_bill_b.dr = 0 ";
    }

    if (m_sBodyWhere.trim().length() != 0)
      sBodyCondition = sBodyCondition + " and " + m_sBodyWhere;

    if (bHasInvcl) {
      sBodyCondition = sBodyCondition + ")";
    }

    return sBodyCondition;
  }

  /**
   * ����Դ�������õ���ͷ��ʾ��
   * @throws BusinessException 
   */
  /* ���棺�˷������������ɡ� */
  public void setSourceVOToUI(CircularlyAccessibleValueObject[] srcHeadVOs,
      CircularlyAccessibleValueObject[] srcBodyVOs) throws BusinessException {
    TOBillTool.setNameValueByFlag(new String[]{"fstatusflag","fallocflag","foiwastpartflag","fotwastpartflag"}, srcHeadVOs);
    //����������
    TOBillTool.getFreeInfo((BillItemVO[])srcBodyVOs);
    super.setSourceVOToUI(srcHeadVOs, srcBodyVOs);
    
    if (srcBodyVOs != null) {

      //���û���
      try
      {
        for(int i = 0; i < srcBodyVOs.length; i++)
        {
          for(int j = 0; j < srcHeadVOs.length; j++)
            if(srcBodyVOs[i].getAttributeValue("cbillid").equals(srcHeadVOs[j].getAttributeValue("cbillid")))
            {
              srcBodyVOs[i].setAttributeValue("nexchangeotobrate",srcHeadVOs[j].getAttributeValue("nexchangeotobrate"));
              srcBodyVOs[i].setAttributeValue("nexchangeotoarate", srcHeadVOs[j].getAttributeValue("nexchangeotoarate"));
              srcBodyVOs[i].setAttributeValue("coutcurrtype", srcHeadVOs[j].getAttributeValue("coutcurrtype"));
            }
        }
      }
      catch(Exception ex)
      {
        Log.error(ex);
      }
    }
    
  }
  
  /*
   * ��ȡԴ���ݱ����û��Զ�����key
   */
  public String getSourceBodyUserDefKey() {
    return "vbdef";
  }

  /* �����ѯ���� */
  public String getSourceHeadCondition() {

    //�ڶ��������ѯ������Ҫ���²�ֱ�ͷ�ͱ�������
    m_sHeaderWhere = "";
    m_sBodyWhere = "";

    //������������
    filterWhereStr(m_whereStr);

    if (m_sBodyWhere.length() != 0)
      m_sBodyWhere = m_sBodyWhere + " and to_bill_b.frowstatuflag != "
          + ConstVO.IBillStatus_CLOSED
          + " and to_bill_b.frowstatuflag != "
          + ConstVO.IBillStatus_FREEZED + " ";
    else
      m_sBodyWhere = " to_bill_b.frowstatuflag != "
          + ConstVO.IBillStatus_CLOSED
          + " and to_bill_b.frowstatuflag != "
          + ConstVO.IBillStatus_FREEZED + " ";

    if (m_whereStr != null) {
      String sThisWhere = m_whereStr;
      String sNewWhere = "";

      int index = sThisWhere.indexOf("and");
      while (index != -1) {
        String sTemp = sThisWhere.substring(0, index);
        sThisWhere = sThisWhere.substring(index + 3);

        if (sTemp.indexOf("to_bill_b") == -1
            && sTemp.indexOf("bd_invcl") == -1) {
          sNewWhere = sNewWhere + sTemp + "and ";
        }

        index = sThisWhere.indexOf("and");
      }

      if (sThisWhere.indexOf("to_bill_b") == -1
          && sThisWhere.indexOf("bd_invcl") == -1) {
        sNewWhere = sNewWhere + sThisWhere + "and ";
      }

      if (sNewWhere.trim().length() != 0) {
        sNewWhere = sNewWhere.substring(0, sNewWhere.length() - 4);
        m_whereStr = sNewWhere;
      } else {
        m_whereStr = null;
      }
    }

    String sHeadCondition = m_sHeaderWhere;

    if (sHeadCondition != null && sHeadCondition.length() != 0) {
      sHeadCondition = sHeadCondition + " and ";
    }
    else
    {
      sHeadCondition = "";
    }

    boolean bHasInvcl = false; //�Ƿ���������������
    if (m_sBodyWhere.indexOf("bd_invcl.") != -1) {
      bHasInvcl = true;
    }

    String sField = "to_bill.cincorpid";

    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      //�������ⵥ
      sField = "to_bill.ctakeoutcorpid";
    }

    String sField2 = "to_bill.cincbid";

    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      sField2 = "to_bill.ctakeoutcbid";
    }

    //���Ϲ�˾����ǩ�ֱ�־
    String sPk_corp = ClientEnvironment.getInstance().getCorporation()
        .getPk_corp();
    if (sPk_corp != null && sPk_corp.trim().length() > 0) {
      sHeadCondition = sHeadCondition + sField + " = '" + sPk_corp.trim()
          + "' AND  ";
    }
    //20050603 yhm û�����빫˾����ʱ ���ӵ�������ⵥ���յ�������ʱ�Թ�˾��Ȩ�޿���
    else {
      //�õ���ǰ����Ա��Ȩ�޵Ĺ�˾(sbPower����in('1001','1002'))
      StringBuffer sbPower = new StringBuffer();
      TOQueryPrivilegeTool.querySubSqlCorpsByUserPK(sbPower, ClientEnvironment
          .getInstance().getUser().getPrimaryKey());
      sHeadCondition = sHeadCondition + sField + " '"
          + sbPower.toString() + "' AND  ";
    }
    
    //�õ�����Ϊ5X����Ϊ4Y��ҵ�����̣���ɲ�ѯ������'aaa','bbb','ccc'��
    String sbiztype = TOBillBusinessTool.getBiztypes(ConstVO.m_sBillDBDD,PubBillTypeConst.billtype_weighdoc);

    sHeadCondition = sHeadCondition
        + "  dr = 0 and blatest='Y' and (to_bill.fstatusflag = "
        + ConstVO.IBillStatus_PASSCHECK + ") and to_bill.cbiztypeid in "+sbiztype;

    // ����where����
    StringBuffer bodyWhere = new StringBuffer("");
    try {
      new TOQueryPrivilegeTool(ConstVO.m_sBillDBDD).getQueryStrForOrder(bodyWhere,ClientEnvironment.getInstance().getUser().getPrimaryKey());
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
    
    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      //�������ⵥ����������+�˻���+;����>�ۼƳ������� �Ŀ��Բ���
      //20050912��by yuanhm �������ⵥ����������+�˻���+;���� -Ӧ��δ��������>�ۼƳ������� �Ŀ��Բ���
      if (bHasInvcl) {
        sHeadCondition = sHeadCondition
            + " and exists (select 1 from to_bill_b,bd_invbasdoc,bd_invcl where to_bill.cbillid = to_bill_b.cbillid and to_bill_b.cinvbasid = bd_invbasdoc.pk_invbasdoc and bd_invbasdoc.pk_invcl = bd_invcl.pk_invcl "+bodyWhere.toString()+" and abs(isnull(to_bill_b.nnum,0))+abs(isnull(to_bill_b.norderbacknum,0))+abs(isnull(to_bill_b.norderwaylossnum,0))-abs(isnull(to_bill_b.nordershouldoutnum,0))> abs(isnull(to_bill_b.norderoutnum,0)) and to_bill_b.boutendflag='N' and to_bill_b.dr = 0 ";
      } else
        sHeadCondition = sHeadCondition
            + " and exists (select 1 from to_bill_b where to_bill.cbillid = to_bill_b.cbillid "+bodyWhere.toString()+" and abs(isnull(to_bill_b.nnum,0))+abs(isnull(to_bill_b.norderbacknum,0))+abs(isnull(to_bill_b.norderwaylossnum,0))-abs(isnull(to_bill_b.nordershouldoutnum,0))> abs(isnull(to_bill_b.norderoutnum,0)) and to_bill_b.boutendflag='N' and to_bill_b.dr = 0 ";

      if (m_sBodyWhere.length() != 0)
        sHeadCondition = sHeadCondition + " and " + m_sBodyWhere + ") ";
    } 

    return sHeadCondition;
  }
  
  /**
   * �����ߣ����� ���ܣ���ֲ�ѯ��������Ϊ��ͷ�ͱ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   *  
   */
  private void filterWhereStr(String whereStr) {
    String sThisWhere = whereStr;

    //ȥ��like '%adad%'
//    int iIndex = sThisWhere.indexOf("'%");
//    if (iIndex != -1) {
//      sThisWhere = sThisWhere.substring(0, iIndex + 1)
//          + sThisWhere.substring(iIndex + 2);
//    }

    //����ѯ��������Ϊ��ͷ�ͱ���ģ�����������ӱ�ķֱ��ѯ
    while (sThisWhere.indexOf("and") != -1) {
      String sTemp = sThisWhere.substring(0, sThisWhere.indexOf("and"));

      sThisWhere = sThisWhere.substring(sThisWhere.indexOf("and") + 3);

      if (sTemp.trim().equals("1=1")) {
        m_sHeaderWhere = m_sHeaderWhere + " 1=1 ";
      } else if (sTemp.trim().indexOf("(to_bill.") >=0) {
        if (m_sHeaderWhere.trim().length() != 0) {
          m_sHeaderWhere = m_sHeaderWhere + " and ";
        }

        m_sHeaderWhere = m_sHeaderWhere + sTemp;
      } else if (sTemp.trim().indexOf("to_bill_b.") >= 0
          || sTemp.trim().indexOf("bd_invcl") >= 0) {
        if (m_sBodyWhere.trim().length() != 0) {
          m_sBodyWhere = m_sBodyWhere + " and ";
        }

        m_sBodyWhere = m_sBodyWhere + sTemp;
      }
    }

    if (sThisWhere.trim().equals("1=1")) {
      m_sHeaderWhere = m_sHeaderWhere + " 1=1 ";
    } else if (sThisWhere.trim().indexOf("(to_bill.") >= 0) {
      if (m_sHeaderWhere.trim().length() != 0) {
        m_sHeaderWhere = m_sHeaderWhere + " and ";
      }

      m_sHeaderWhere = m_sHeaderWhere + sThisWhere;
    } else if (sThisWhere.trim().indexOf("(to_bill_b.") >= 0
        || sThisWhere.trim().indexOf("bd_invcl") >= 0) {
      if (m_sBodyWhere.trim().length() != 0) {
        m_sBodyWhere = m_sBodyWhere + " and ";
      }

      m_sBodyWhere = m_sBodyWhere + sThisWhere;
    }

    if (m_sHeaderWhere.trim().equals("1=1")
        || m_sHeaderWhere.trim().length() == 0)
      m_sHeaderWhere = null;

    m_whereStr = null;

    if (m_sBodyWhere.indexOf("vsourcecode") != -1) {
      m_sBodyWhere = m_sBodyWhere + "and csourcetypecode = '30' ";
    }
  }
  
  /*
   * ��ʼ��Դͷ���ݾ��ȣ���ģ����غ���á�
   */
  public void initHeadPanePrecition() {
    initSysParam();
    /*
     * int[0] ----- �����ľ��� int[1] ----- ���۵ľ��� int[2] ----- ���ľ��� int[3] -----
     * �����������ľ��� int[4] ----- �����ʵľ���
     */

    //�б�����ϰ벿���Ƕ�����Ϣ���°벿���ǳ��ⵥ��Ϣ
    nc.ui.pub.bill.BillItem[] biaItems = null;
    IBillToBillListPanel billToBillListPanel = getbillListPanel();
    if (billToBillListPanel instanceof DoubleBillRefListPanel5XTo4Y) {
      DoubleBillRefListPanel5XTo4Y new_name = (DoubleBillRefListPanel5XTo4Y) billToBillListPanel;
      biaItems = new_name.getBillListData().getBodyItems();
    }else if (billToBillListPanel instanceof SimpBillRefListPanel5XTo4Y) {
      SimpBillRefListPanel5XTo4Y new_name = (SimpBillRefListPanel5XTo4Y) billToBillListPanel;
      biaItems = new_name.getBillListData().getHeadItems();
    }
    
    if (biaItems != null) {
      for (int i = 0; i < biaItems.length; i++) {
        String sKey = biaItems[i].getKey();
        if (sKey.indexOf("nexchangeotobrate") >= 0 || sKey.indexOf("nexchangeotoarate") >= 0) {
          //���������ʾ��һ�������Ļ��ʾ���8
          biaItems[i].setDecimalDigits(8);
        }
        else if (sKey.indexOf("num") >= 0 && sKey.indexOf("ass") < 0)//����
        {
          //���ÿ�����ĳ���,ֻ�б���Ҫ=����λ+С����ռ��һλ+С������λ��
          biaItems[i]
              .setLength(nc.vo.scm.pub.bill.SCMDoubleScale.INT_LENGTH
                  + 1 + m_iaScale[0]);
          biaItems[i].setDecimalDigits(m_iaScale[0]);
        } else if (sKey.indexOf("price") >= 0) {
          biaItems[i]
              .setLength(nc.vo.scm.pub.bill.SCMDoubleScale.INT_LENGTH
                  + 1 + m_iaScale[1]);
          biaItems[i].setDecimalDigits(m_iaScale[1]);
        } else if (sKey.indexOf("mny") >= 0) {
          biaItems[i]
              .setLength(nc.vo.scm.pub.bill.SCMDoubleScale.INT_LENGTH
                  + 1 + m_iaScale[2]);
          biaItems[i].setDecimalDigits(m_iaScale[2]);
        } else if (sKey.indexOf("num") >= 0 && sKey.indexOf("ass") >= 0) {
          biaItems[i]
              .setLength(nc.vo.scm.pub.bill.SCMDoubleScale.INT_LENGTH
                  + 1 + m_iaScale[3]);
          biaItems[i].setDecimalDigits(m_iaScale[3]);
        } else if (sKey.indexOf("changerate") >= 0
            || sKey.indexOf("nquoteunitrate") >= 0) {
          biaItems[i]
              .setLength(nc.vo.scm.pub.bill.SCMDoubleScale.INT_LENGTH
                  + 1 + m_iaScale[4]);
          biaItems[i].setDecimalDigits(m_iaScale[4]);
        }
      }
    }
  }
  
  /**
   * �����ߣ����˾� ���ܣ���ϵͳ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ�2002-10-17
   * �޸��ˣ������ �޸�ԭ��
   */
  private void initSysParam() {
    try {
      Integer[] iTemp = ClientCommonDataHelper
          .getDataPrecision(ClientEnvironment.getInstance()
              .getCorporation().getPk_corp());

      m_iaScale = new int[iTemp.length];
      for (int i = 0; i < iTemp.length; i++) {
        m_iaScale[i] = iTemp[i].intValue();
      }
    } catch (Exception e) {
      SCMEnv.error("can not get para" + e.getMessage());
    }
  }

  public Object[] queryAllData(String sWhere) throws Exception {
    m_whereStr = sWhere;
    Object[] reObjects = super.queryAllData(null);
    if(reObjects!=null && reObjects[0]!=null && ((Object[])reObjects[0]).length>0){
      TOBillTool.setNameValueByFlag(new String[]{"fstatusflag","fallocflag","foiwastpartflag","fotwastpartflag"}, (BillHeaderVO[])reObjects[0]);
      
      BillItemVO[] srcBodyVOs = (BillItemVO[])reObjects[1];
      if(null!=srcBodyVOs){
        //����ҵ������Ϊ5X43314Y��Ϊ���ֵı�����
        srcBodyVOs = filter5X43314Y(srcBodyVOs);
        
        //���ÿ�����Ϣ
        srcBodyVOs = setCust(srcBodyVOs); 
      }
      //����������
      TOBillTool.getFreeInfo(srcBodyVOs);
      //�������ı���VO����
      reObjects[1] = srcBodyVOs;
      
      //���˵�ֻ�б�ͷû�б��������
      CircularlyAccessibleValueObject[] retHeadVOs = filterHVOwithoutBVO("cbillid",(CircularlyAccessibleValueObject[])reObjects[0],(CircularlyAccessibleValueObject[])reObjects[1]);
      reObjects[0] = retHeadVOs;
    }
    return reObjects;
  }
  
  private CircularlyAccessibleValueObject[] filterHVOwithoutBVO(String pkname, CircularlyAccessibleValueObject[] headvos, CircularlyAccessibleValueObject[] bodyvos) throws BusinessException {
    if (headvos == null || headvos.length <= 0 || bodyvos == null || bodyvos.length <= 0)
      return null;

    CircularlyAccessibleValueObject[] retHeadVOs = null;
    if (pkname == null || pkname.trim().length() <= 0 )
      return headvos;
    ArrayList<String> alPK = new ArrayList<String>();
    String key = null;
    for(CircularlyAccessibleValueObject bvo : bodyvos){
      key = (String) bvo.getAttributeValue(pkname);
      alPK.add(key);
    }

    ArrayList<CircularlyAccessibleValueObject> alHVO = new ArrayList<CircularlyAccessibleValueObject>();
    for(CircularlyAccessibleValueObject hvo : headvos){
      key = hvo.getPrimaryKey();
      if(alPK.contains(key)){
        alHVO.add(hvo);
      }
    }
    retHeadVOs = new CircularlyAccessibleValueObject[alHVO.size()];
    retHeadVOs = alHVO.toArray(retHeadVOs);
    return retHeadVOs;
  }

  /**
   * ����ҵ������Ϊ5X43314Y��Ϊ���ֵı�����
   * @param srcBodyVOs
   * @return
   * @throws Exception
   */
  private BillItemVO[] filter5X43314Y(BillItemVO[] srcBodyVOs) throws Exception {
    String beanName = IBusinessTypeTool.class.getName();
    IBusinessTypeTool iBusiTypeTool = (IBusinessTypeTool) nc.bs.framework.common.NCLocator.getInstance().lookup(beanName);
    //�õ�����5X43314Y��ҵ������id
    ArrayList alBusiTypeId = iBusiTypeTool.getBusiIds45X43314Y();

    ArrayList retItems = new ArrayList();
    BillItemVO item = null;
    UFDouble d0 = new UFDouble(0);
    for(int j=0;j<srcBodyVOs.length;j++){
      item = srcBodyVOs[j];
      //����5X43314Y��ҵ������
      if(alBusiTypeId.contains(item.getCbiztypeid())) {
        //���˵����֣����غ���
        if(item.getNnum().compareTo(d0)<0){
          retItems.add(item);
        }
      }
      //������5X43314Y��ҵ������,�������
      else {
        retItems.add(item);
      }
    }
    BillItemVO[] retVOs = new BillItemVO[retItems.size()];
    retVOs = (BillItemVO[]) retItems.toArray(retVOs);
    return retVOs;
  }
  
  private BillItemVO[] setCust(BillItemVO[] srcBodyVOs) throws BusinessException {
    ArrayList alRet = new ArrayList();
    String sBillTypeCode = null;
    BillItemVO item = null;
    ArrayList al5C = new ArrayList();
    ArrayList al5D = new ArrayList();
    //���尴�������ͷ��飬�õ���Ҫ�����5C��5D��5E/5I�Ĳ���Ҫ���������Ϣ
    for(int j=0;j<srcBodyVOs.length;j++){
      item = srcBodyVOs[j];
      sBillTypeCode = item.getCtypecode();
      if(sBillTypeCode.equals(ConstVO.m_sBillSFDBDD)){
        al5C.add(item);
      } else if (sBillTypeCode.equals(ConstVO.m_sBillGSJDBDD)) {
        al5D.add(item);
      } else {
        alRet.add(item);
      }
    }
    
    IOuter i1 = (IOuter)NCLocator.getInstance().lookup(IOuter.class.getName());  
    //����5C
    if(al5C.size()>0){
      BillItemVO[] bvo = new BillItemVO[al5C.size()];
      bvo = (BillItemVO[]) al5C.toArray(bvo);
      String[] sOutCorpValue2 = new String[] { "ctakeoutcorpid","ccustomerid", "pk_cubasdoc"};
      String[] sInCorpValue2 = new String[] { "coutcorpid","cproviderid", "pk_cubasdoc" };
      //���������Ϣ
      nc.vo.pub.CircularlyAccessibleValueObject[] vos = i1.setCustomerid(bvo,sOutCorpValue2,sInCorpValue2);
      alRet.addAll(Arrays.asList(vos));
    }
    //����5D
    if(al5D.size()>0){
      BillItemVO[] bvo = new BillItemVO[al5D.size()];
      bvo = (BillItemVO[]) al5D.toArray(bvo);
      String[] sOutCorpValue = new String[] { "coutcorpid","ccustomerid", "pk_cubasdoc"};
      String[] sInCorpValue = new String[] { "cincorpid","cproviderid", "pk_cubasdoc" };
      //���������Ϣ
      //������˾�ֶ�����������˾���̵����ֶ�����������˾���̻��������ֶ�����sInCorpValue����
      nc.vo.pub.CircularlyAccessibleValueObject[] vos = i1.setCustomerid(bvo,sOutCorpValue,sInCorpValue);
      alRet.addAll(Arrays.asList(vos));
/*      for (int i = 0; i < srcBodyVOs.length; i++) {
        srcBodyVOs[i].setAttributeValue("ccustomerid", (String)vos[i].getAttributeValue("ccustomerid"));
      }*/
    }
    BillItemVO[] aBodyVOs = new BillItemVO[alRet.size()];
    aBodyVOs = (BillItemVO[]) alRet.toArray(aBodyVOs);
    return aBodyVOs;
  }
}
