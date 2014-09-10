package nc.bs.pub.action;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.xcgl.assay.AssayBVO;
import nc.vo.xcgl.assay.AssayHVO;
import nc.vo.xcgl.assayres.AssayResBVO;
/**
 * ���鵥�����̨�����ű���
 * @author jay
 * 
 */
public class N_XC31_WRITE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;

public N_XC31_WRITE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass (PfParameterVO vo)  throws BusinessException {
try{
	
	AssayHVO headvo= (AssayHVO) vo.m_preValueVo.getParentVO();
	BsUniqueCheck.FieldUniqueCheck(headvo, 
			new String[]{"pk_billtype","dbilldate","pk_factory","pk_minarea","pk_beltline","pk_classorder","pk_invmandoc","isresel","vdef20"},
			"�������ͣ��������ڣ�ѡ�������ţ������ߣ���Σ������������ʯ ���Ƿ����ѡ����ظ�!");
	//Ϊ��Ʒ���븳ֵ��
	//����֮�����ò��ձ�־
	CircularlyAccessibleValueObject[] bvos= vo.m_preValueVo.getChildrenVO();
//	 for(int i=0;i<bvos.length;i++){
		 String vlastbillid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillid"));
		 if(vlastbillid!=null&&vlastbillid.trim()!=""){
			 String sql="update xcgl_sample set isref='Y'"+ " where pk_sample='"+vlastbillid+"'";
			 XCZmPubDAO.getDAO().executeUpdate(sql); 
		 }
//		 if((String)bvos[i].getAttributeValue("vsamplenumber")==null||
//					(String)bvos[i].getAttributeValue("vsamplenumber").toString().trim()==""){
//		    bvos[i].setAttributeValue("vsamplenumber", new HYPubBO().getBillNo(
//		    		PubBillTypeConst.billtype_SampleNo, SQLHelper.getCorpPk(), null, null));}
//	        }

//	//����ϵͳ�����Ƿ����Ʒ�������;
//         String prara=PuPubVO.getString_TrimZeroLenAsNull(
//			XCZmPubDAO.getDAO().executeQuery("select value from pub_sysinit where  " +
//			" initcode='SAMPNO' and  pk_org='"+SQLHelper.getCorpPk()+"'", 
//			new ColumnProcessor()));
//        if(prara!=null&&prara.equals("Y")){
//	      for(int i=0;i<bvos.length;i++){
//	    	  if(PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("vsamplenumber")).length()<32){
//		    String newno=XcPubTool.encrypt((String)bvos[i].getAttributeValue("vsamplenumber"));
//		    bvos[i].setAttributeValue("vsamplenumber", newno);
//	        }
//	      }
//	      }
	
	super.m_tmpVo=vo;
	try {
			super.m_tmpVo = vo;
			Object retObj = null;
			
			updateNextGrade(vo);
			
		//	vo.m_preValueVo 
//			if(vo.m_preValueVo.getParentVO()!=null){
//				FassetsHVO cvo=(FassetsHVO)vo.m_preValueVo.getParentVO();
//				BsUniqueCheck.FieldUniqueChecks(cvo, new String[]{"pk_corp","pk_billtype"},null,
//						" ��˾["+ZmPubTool.getCorpCodeByPk(cvo.getPk_corp())+","+ZmPubTool.getCorpNameByPk(cvo.getPk_corp())+"];" +
//					    " ��������["+cvo.getPk_billtype()+"];" +
//					   
//					    " ��Ϣ����ظ��ظ�!");
//			}
			retObj = runClass("nc.bs.xcgl.pub.bill.XCSonBillSave", "saveBill1","nc.vo.pub.AggregatedValueObject:01", vo, m_keyHas,	m_methodReturnHas);
			return retObj;
		} catch (Exception ex) {
			if (ex instanceof BusinessException)
				throw (BusinessException) ex;
			else
				throw new PFBusinessException(ex.getMessage(), ex);
		}
} catch (Exception ex) {
	if (ex instanceof BusinessException)
		throw (BusinessException) ex;
	else 
    throw new PFBusinessException(ex.getMessage(), ex);
}
}
public  void updateNextGrade(PfParameterVO vo) throws DAOException {  
   if(vo.m_preValueVo==null || vo.m_preValueVo.getParentVO()==null
	  ||vo.m_preValueVo.getChildrenVO()==null || vo.m_preValueVo.getChildrenVO().length==0 ){
	   return;
   }
   BaseDAO dao=new BaseDAO();
   AssayHVO headvo= (AssayHVO) vo.m_preValueVo.getParentVO();
   AssayBVO[] bvos=(AssayBVO[]) vo.m_preValueVo.getChildrenVO();
   String sourtype=headvo.getPk_billtype();
   if(PuPubVO.getUFBoolean_NullAs(headvo.getUreserve10(), new UFBoolean(false)).booleanValue()==false){
	   return ;
   }
   List<AssayResBVO> rlist=new ArrayList<AssayResBVO>();
   for(int i=0;i<bvos.length;i++){
	   AssayBVO bvo=bvos[i];
	   String sourbodyid=bvo.getPk_sample_b();
	   
	 List list=(List) dao.retrieveByClause(AssayResBVO.class, 
			   " isnull(dr,0)=0 and vlastbillrowid='"+sourbodyid+"' " +
			   " and vlastbilltype='"+sourtype+"' ");
	 if(list!=null && list.size()>0){
		 for(int j=0;j<list.size();j++){
			 AssayResBVO rbvo=(AssayResBVO) list.get(j);
			 rbvo.setNgrade(bvo.getNgrade());
			 rlist.add(rbvo);
		 }
		
	 }
   }
   if(rlist!=null && rlist.size()>0){
	   dao.updateVOArray(rlist.toArray(new AssayResBVO[0]));
   }   
}
/*
* ��ע��ƽ̨��дԭʼ�ű�
*/
public String getCodeRemark(){
	return null;
}
//	return "	try {\n			super.m_tmpVo = vo;\n			Object retObj = null;\n			// ���漴�ύ\n			retObj = runClass(\"nc.bs.gt.pub.HYBillSave\", \"saveBill\",\"nc.vo.pub.AggregatedValueObject:01\", vo, m_keyHas,	m_methodReturnHas);\n			return retObj;\n		} catch (Exception ex) {\n			if (ex instanceof BusinessException)\n				throw (BusinessException) ex;\n			else\n				throw new PFBusinessException(ex.getMessage(), ex);\n		}\n";}
/*
* ��ע�����ýű�������HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}
