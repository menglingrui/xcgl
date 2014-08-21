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
  // 表头表体查询条件
  private String m_sBodyWhere = "";

  private String m_sHeaderWhere = "";
  
  //小数位数精度
  private int m_iaScale[] = null;

  public SourceRefDlgCtrl5XToXC08(
      String srcbilltype, String targerbilltype, String pk_corp, IBillToBillListPanel refpanel) {
    super(srcbilltype, targerbilltype, pk_corp, refpanel);
  }
  
  /*
   * 获取当前行查询现存量
   */
  public OnHandRefreshVO getOnHandRefreshVO(int irow) {

    OnHandRefreshVO voOnhand = new OnHandRefreshVO();

    if (irow != -1) {
      //获得选中的行对应的VO(此方法返回的表体VO只有一行)
      IBillToBillListPanel avo = getbillListPanel();
      
          //设置现存量查询条件：出货库存组织、出货存货、出货批次、出货仓库
          voOnhand.setCcalbodyid(avo.getSourceBodyValueAt(irow, "ctakeoutcbid").toString());
          voOnhand.setCcorpid(avo.getSourceBodyValueAt(irow, "ctakeoutcorpid").toString());

          voOnhand.setCwhid(avo.getSourceBodyValueAt(irow, "ctakeoutwhid").toString());
          voOnhand.setCinvid(avo.getSourceBodyValueAt(irow, "ctakeoutinvid").toString());
          voOnhand.setVbatch(avo.getSourceBodyValueAt(irow, "vbatch").toString());
          voOnhand.setCcustid(avo.getSourceBodyValueAt(irow, "creceieveid").toString());
          voOnhand.setAttributeValue("castunitid", avo.getSourceBodyValueAt(irow, "castunitid").toString());

          //检查调出库存组织可用量，用预计出的日期
          voOnhand.setDPlanDate(new UFDate(avo.getSourceBodyValueAt(irow, "dplanoutdate").toString()));

          //处理自由项
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
    //隐去表头的调出，调入仓库字段
    return new String[]{"coutwhid","cinwhid","coutwhname","cinwhname"};
  }
  
  public String getSourceBodyCondition() {
    String sBodyCondition = "";

    boolean bHasInvcl = false; //是否包含存货分类条件
    if (m_sBodyWhere.indexOf("bd_invcl.") != -1) {
      bHasInvcl = true;
    }

    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      //调拨出库单：订单数量+退回数+途损数>累计出库数量 的可以参照
      //20050912：by yuanhm调拨出库单：订单数量+退回数+途损数-应发未出库数量>累计出库数量 的可以参照
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
   * 将来源单据设置到表头显示。
   * @throws BusinessException 
   */
  /* 警告：此方法将重新生成。 */
  public void setSourceVOToUI(CircularlyAccessibleValueObject[] srcHeadVOs,
      CircularlyAccessibleValueObject[] srcBodyVOs) throws BusinessException {
    TOBillTool.setNameValueByFlag(new String[]{"fstatusflag","fallocflag","foiwastpartflag","fotwastpartflag"}, srcHeadVOs);
    //解析自由项
    TOBillTool.getFreeInfo((BillItemVO[])srcBodyVOs);
    super.setSourceVOToUI(srcHeadVOs, srcBodyVOs);
    
    if (srcBodyVOs != null) {

      //设置汇率
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
   * 获取源单据表体用户自定义项key
   */
  public String getSourceBodyUserDefKey() {
    return "vbdef";
  }

  /* 主表查询条件 */
  public String getSourceHeadCondition() {

    //第二次输入查询条件后，要重新拆分表头和表体条件
    m_sHeaderWhere = "";
    m_sBodyWhere = "";

    //重新设置条件
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

    boolean bHasInvcl = false; //是否包含存货分类条件
    if (m_sBodyWhere.indexOf("bd_invcl.") != -1) {
      bHasInvcl = true;
    }

    String sField = "to_bill.cincorpid";

    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      //调拨出库单
      sField = "to_bill.ctakeoutcorpid";
    }

    String sField2 = "to_bill.cincbid";

    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      sField2 = "to_bill.ctakeoutcbid";
    }

    //加上公司，和签字标志
    String sPk_corp = ClientEnvironment.getInstance().getCorporation()
        .getPk_corp();
    if (sPk_corp != null && sPk_corp.trim().length() > 0) {
      sHeadCondition = sHeadCondition + sField + " = '" + sPk_corp.trim()
          + "' AND  ";
    }
    //20050603 yhm 没有输入公司条件时 增加调拨出入库单参照调拨订单时对公司的权限控制
    else {
      //得到当前操作员有权限的公司(sbPower形如in('1001','1002'))
      StringBuffer sbPower = new StringBuffer();
      TOQueryPrivilegeTool.querySubSqlCorpsByUserPK(sbPower, ClientEnvironment
          .getInstance().getUser().getPrimaryKey());
      sHeadCondition = sHeadCondition + sField + " '"
          + sbPower.toString() + "' AND  ";
    }
    
    //得到上游为5X下游为4Y的业务流程，组成查询条件（'aaa','bbb','ccc'）
    String sbiztype = TOBillBusinessTool.getBiztypes(ConstVO.m_sBillDBDD,PubBillTypeConst.billtype_weighdoc);

    sHeadCondition = sHeadCondition
        + "  dr = 0 and blatest='Y' and (to_bill.fstatusflag = "
        + ConstVO.IBillStatus_PASSCHECK + ") and to_bill.cbiztypeid in "+sbiztype;

    // 表体where条件
    StringBuffer bodyWhere = new StringBuffer("");
    try {
      new TOQueryPrivilegeTool(ConstVO.m_sBillDBDD).getQueryStrForOrder(bodyWhere,ClientEnvironment.getInstance().getUser().getPrimaryKey());
    }
    catch (Exception e) {
      ExceptionUtils.wrappException(e);
    }
    
    if (getTargetBilltype().equals(ConstVO.m_sBillKCDBCKD)) {
      //调拨出库单：订单数量+退回数+途损数>累计出库数量 的可以参照
      //20050912：by yuanhm 调拨出库单：订单数量+退回数+途损数 -应发未出库数量>累计出库数量 的可以参照
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
   * 创建者：崔勇 功能：拆分查询条件，分为表头和表体的 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   *  
   */
  private void filterWhereStr(String whereStr) {
    String sThisWhere = whereStr;

    //去掉like '%adad%'
//    int iIndex = sThisWhere.indexOf("'%");
//    if (iIndex != -1) {
//      sThisWhere = sThisWhere.substring(0, iIndex + 1)
//          + sThisWhere.substring(iIndex + 2);
//    }

    //将查询条件区分为表头和表体的，用于主表和子表的分别查询
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
   * 初始化源头单据精度，在模板加载后调用。
   */
  public void initHeadPanePrecition() {
    initSysParam();
    /*
     * int[0] ----- 数量的精度 int[1] ----- 单价的精度 int[2] ----- 金额的精度 int[3] -----
     * 辅计量数量的精度 int[4] ----- 换算率的精度
     */

    //列表界面上半部分是订单信息，下半部分是出库单信息
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
          //多个币种显示在一起，用最大的汇率精度8
          biaItems[i].setDecimalDigits(8);
        }
        else if (sKey.indexOf("num") >= 0 && sKey.indexOf("ass") < 0)//数量
        {
          //设置可输入的长度,只有表单需要=整数位+小数点占的一位+小数点后的位数
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
   * 创建者：王乃军 功能：读系统参数 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期：2002-10-17
   * 修改人：赵宇煌 修改原因：
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
        //过滤业务流程为5X43314Y且为蓝字的表体行
        srcBodyVOs = filter5X43314Y(srcBodyVOs);
        
        //设置客商信息
        srcBodyVOs = setCust(srcBodyVOs); 
      }
      //解析自由项
      TOBillTool.getFreeInfo(srcBodyVOs);
      //将处理后的表体VO返回
      reObjects[1] = srcBodyVOs;
      
      //过滤掉只有表头没有表体的数据
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
   * 过滤业务流程为5X43314Y且为蓝字的表体行
   * @param srcBodyVOs
   * @return
   * @throws Exception
   */
  private BillItemVO[] filter5X43314Y(BillItemVO[] srcBodyVOs) throws Exception {
    String beanName = IBusinessTypeTool.class.getName();
    IBusinessTypeTool iBusiTypeTool = (IBusinessTypeTool) nc.bs.framework.common.NCLocator.getInstance().lookup(beanName);
    //得到所有5X43314Y的业务流程id
    ArrayList alBusiTypeId = iBusiTypeTool.getBusiIds45X43314Y();

    ArrayList retItems = new ArrayList();
    BillItemVO item = null;
    UFDouble d0 = new UFDouble(0);
    for(int j=0;j<srcBodyVOs.length;j++){
      item = srcBodyVOs[j];
      //属于5X43314Y的业务流程
      if(alBusiTypeId.contains(item.getCbiztypeid())) {
        //过滤掉蓝字，返回红字
        if(item.getNnum().compareTo(d0)<0){
          retItems.add(item);
        }
      }
      //不属于5X43314Y的业务流程,无需过滤
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
    //表体按调拨类型分组，得到需要补充的5C、5D，5E/5I的不需要补充客商信息
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
    //补充5C
    if(al5C.size()>0){
      BillItemVO[] bvo = new BillItemVO[al5C.size()];
      bvo = (BillItemVO[]) al5C.toArray(bvo);
      String[] sOutCorpValue2 = new String[] { "ctakeoutcorpid","ccustomerid", "pk_cubasdoc"};
      String[] sInCorpValue2 = new String[] { "coutcorpid","cproviderid", "pk_cubasdoc" };
      //补充客商信息
      nc.vo.pub.CircularlyAccessibleValueObject[] vos = i1.setCustomerid(bvo,sOutCorpValue2,sInCorpValue2);
      alRet.addAll(Arrays.asList(vos));
    }
    //补充5D
    if(al5D.size()>0){
      BillItemVO[] bvo = new BillItemVO[al5D.size()];
      bvo = (BillItemVO[]) al5D.toArray(bvo);
      String[] sOutCorpValue = new String[] { "coutcorpid","ccustomerid", "pk_cubasdoc"};
      String[] sInCorpValue = new String[] { "cincorpid","cproviderid", "pk_cubasdoc" };
      //补充客商信息
      //调出公司字段名、调出公司客商档案字段名、调出公司客商基本档案字段名；sInCorpValue类似
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
