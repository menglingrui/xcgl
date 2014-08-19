package nc.bs.xcgl.pub.bill;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nc.bs.dao.DAOException;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.bs.zmpub.pub.bill.SonBillSave;
import nc.itf.zmpub.pub.ISonVO;
import nc.itf.zmpub.pub.ISonVOH;
import nc.jdbc.framework.SQLParameter;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.pub.itf.ISonExtendVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
import nc.vo.xcgl.pub.logger.XCLogger;
/**
 * ֧�����������Ķ����ű�������
 * @author mlr
 */
public class XCSonExtBillSave extends SonBillSave{
	
	public XCZmPubDAO getDao1(){
        return XCZmPubDAO.getDAO();
	}
	
	public java.util.ArrayList saveBill1(nc.vo.pub.AggregatedValueObject billVo) throws Exception{
		XCLogger.printInfor("����XCSonBillSave���淽����ʼ��");
		XCLogger.printInfor("�������ͣ�"+billVo.getParentVO().getAttributeValue("pk_billtype"));
		if(billVo==null){
			XCLogger.printError("��������Ϊ��");
			throw new Exception("��������Ϊ��");
		}	
		if(billVo.getParentVO()==null ){
			XCLogger.printError("��ͷ����Ϊ��");
			throw new Exception("��ͷ����Ϊ��");
		}
		if(billVo.getChildrenVO()==null || billVo.getChildrenVO().length==0){
			XCLogger.printError("���岻��Ϊ��");
			throw new Exception("���岻��Ϊ��");
		}	
		if(!(billVo instanceof ISonVOH)){
			XCLogger.printError("�ۺ�voû��ʵ��ISonVOH�ӿ�");
			throw new Exception("�ۺ�voû��ʵ��ISonVOH�ӿ�");
		}
		
		if(!(billVo.getChildrenVO()[0] instanceof ISonVO)){
			XCLogger.printError("�ӱ�voû��ʵ��ISonVO�ӿ�");
			throw new Exception("�ӱ�voû��ʵ��ISonVO�ӿ�");
		}
		ioh=(ISonVOH)billVo;
	    HYBillVO  oldbillvo=(HYBillVO) ObjectUtils.serializableClone(billVo);
	    HYBillVO billvov=(HYBillVO)billVo;
	    XCLogger.printInfor("����������Ϣ���濪ʼ��");
	    java.util.ArrayList retAry = super.saveBill(billvov);
	  
		if(retAry == null || retAry.size() == 0){
			throw new BusinessException("����������Ϣ����ʧ��");
		}
		XCLogger.printInfor("����������Ϣ���������");
		HYBillVO newBillVo = (HYBillVO)retAry.get(1);
		if(oldbillvo.getParentVO().getPrimaryKey()==null){
			//���������ֶ����������Ϣ 
			//��Ϊ��������Ļ�  
			//����� ���صľۺ�vo �ǲ���������Ϣ��
			setChildData(newBillVo,getSuperDMO());			
		}
		XCLogger.printInfor("ͬ���ӱ�����������п�ʼ��");
		insertBody_Pk(newBillVo,oldbillvo);		
		XCLogger.printInfor("ͬ���ӱ�����������н�����");
		
		XCLogger.printInfor("����濪ʼ��");
		if(oldbillvo.getParentVO().getPrimaryKey()==null){
			//�������� 
			//�������������Ļ���
			// ����Ҫ  ��billvo ����¡һ��  ��Ϊ ������Ϊ  oldbillvo
			// ����billvo  ��÷��ؽ��   Ȼ��ȡ�� billvo�ı��� ����  �����к���Ϣ  
			// ����������  ͬ���� oldbillvo�������
			// Ȼ�� ���������Ϣ
		     //������������			
			saveXiHa(oldbillvo);			
		}else{
			//����� �޸ı���Ļ�   
			// ����Ҫ  ��billvo ����¡һ��  ��Ϊ ������Ϊ  oldbillvo
			// ����billvo  ��÷��ؽ��   Ȼ��ȡ�� billvo�ı��� ����  �����к���Ϣ  
			// ����������  ͬ���� oldbillvo�������
			
			//Ȼ�� �ӽڵ�  oldbillvo �и���vo״̬ �ҳ�  ���� ɾ�� �޸ĵı�����Ϣ
			//������  ���������Ϣ
			//�޸ĵ�  ��ɾ��ԭ���������Ϣ  Ȼ�󱣴����µ������Ϣ
			//ɾ����  ɾ�������Ϣ		
		   //�����޸ı���
		   Map<String,List<SuperVO>>  map=splitVO((SuperVO[])oldbillvo.getChildrenVO());
		   List<SuperVO> adds=map.get("add");
		   List<SuperVO> edits=map.get("edit");
		   List<SuperVO> detes=map.get("dete");
		   List<SuperVO> unchg=map.get("unchg");
		   if(adds!=null && adds.size()!=0){
			  for(int i=0;i<adds.size();i++){
				 saveXiHa(((ISonVO)(adds.get(i))).getSonVOS());
				 saveXiHa(((ISonExtendVO)(adds.get(i))).getSonVOS1());
			  } 
		   }
           if(edits!=null && edits.size()!=0){
        	   for(int i=0;i<edits.size();i++){
				 updateXiHa(((ISonVO)(edits.get(i))).getSonVOS());
				 updateXiHa(((ISonExtendVO)(edits.get(i))).getSonVOS1());
  			  } 
		   }
           if(detes!=null && detes.size()!=0){
        	   for(int i=0;i<detes.size();i++){
          		 deleteXiHa((detes.get(i)).getPrimaryKey());
          		 deleteXiHa1((detes.get(i)).getPrimaryKey());
    		  } 
		   }	
           if(unchg!=null && unchg.size()!=0){
        	   for(int i=0;i<unchg.size();i++){
        		   updateXiHa(((ISonVO)(unchg.get(i))).getSonVOS());
        		   updateXiHa(((ISonExtendVO)(unchg.get(i))).getSonVOS1());
      		    } 
		   }	
		}
		ioh=null;
		sql=null;
		XCLogger.printInfor("����������");		
		XCLogger.printInfor("�������ͣ�"+billVo.getParentVO().getAttributeValue("pk_billtype")+"����ֵΪ��"+retAry);
		XCLogger.printInfor("����XCSonBillSave���淽��������");		
        return retAry;
	}
	
	public void deleteXiHa1(String pk) throws InstantiationException, IllegalAccessException, ClassNotFoundException, DAOException {
		SQLParameter para=new SQLParameter();
		para.addParam(pk.trim());
	    String sql=getSql1();	
		getDao1().executeUpdate(sql, para);		
	}
	public String sql1=null;
	private String getSql1() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		  if(sql1==null){
			String str=((ISonExtendVOH)ioh).getSonClass1();
			SuperVO ivo=(SuperVO) Class.forName(str).newInstance();
			sql1=" update "+ivo.getTableName()+" set dr=1 where "+ivo.getParentPKFieldName()+"=?";
		  }	
		  return sql1;
		}
	/**
	 * ��newBillVo����ʱ���ɵı�ͷ����������Ϣ��ͬ����oldbillvo��
	 * @author mlr
	 * @˵�������׸ڿ�ҵ��
	 * 2011-10-12����06:07:16
	 * @param newBillVo
	 * @param oldbillvo
	 * @throws Exception 
	 */
	public void insertBody_Pk(HYBillVO newBillVo,HYBillVO oldbillvo) throws Exception {
		 super.insertBody_Pk(newBillVo,oldbillvo);
		 insertBody_Pk1(newBillVo,oldbillvo);
	}

	public void insertBody_Pk1(HYBillVO newBillVo, HYBillVO oldbillvo) throws Exception{
		//���� �к� ͬ����������    ���Ǵ��ڱ����޸�ɾ����     �ֽ��������л�����к��ظ�������		
		//�������ӱ�  �Ѿ�ȥ����ɾ����  ��û��ȥ�������Ϣ  newChilds
	    //����ǰ���ӱ�  û��ȥ��ɾ���� ������vo״̬�� ��������ϢoldChilds		
		SuperVO[]  newChilds=(SuperVO[]) newBillVo.getChildrenVO();
		if(newChilds==null || newChilds.length==0 ){
			return;
		}
		SuperVO[] oldChilds=(SuperVO[]) oldbillvo.getChildrenVO();
		for(int i=0;i<newChilds.length;i++){
			String pk=newChilds[i].getPrimaryKey();
	//		String pk_h=PuPubVO.getString_TrimZeroLenAsNull(newChilds[i].getAttributeValue(newChilds[i].getParentPKFieldName()));
			ISonExtendVO newso=(ISonExtendVO)newChilds[i];
			String crowno=PuPubVO.getString_TrimZeroLenAsNull(newChilds[i].getAttributeValue(newso.getRowNumName1()));   
			if(crowno==null || crowno.length()==0)
				throw new Exception("�кŲ���Ϊ��");
            for(int j=0;j<oldChilds.length;j++){
               if(oldChilds[j].getStatus()==VOStatus.DELETED){
            	   continue;
               }
               ISonExtendVO oldso=(ISonExtendVO) oldChilds[j];
               if(crowno.equalsIgnoreCase(PuPubVO.getString_TrimZeroLenAsNull(oldChilds[j].getAttributeValue(oldso.getRowNumName1())))){
            	   oldChilds[j].setPrimaryKey(newChilds[i].getPrimaryKey());
            	   List list= oldso.getSonVOS1();
            	   if(list!=null && list.size()!=0){
            	      for(int k=0;k<list.size();k++){
       				  // ((SuperVO)list.get(k)).setPrimaryKey(pk);
       				((SuperVO)list.get(k)).setAttributeValue(((SuperVO)list.get(k)).getParentPKFieldName(), pk);
       			      }
            	   }
               }
           }
		}}
	
	/**
	 * ֱ�ӽ�������ݴ������ݿ�
	 * @param oldbillvo
	 * @throws Exception
	 */
	public void saveXiHa(HYBillVO oldbillvo) throws Exception {
	   super.saveXiHa(oldbillvo);
	   saveXiHa1(oldbillvo);
		
	}

	public void saveXiHa1(HYBillVO oldbillvo) throws Exception {
		SuperVO[] oldchilds=(SuperVO[]) oldbillvo.getChildrenVO();
		if(oldchilds!=null && oldchilds.length!=0){
			List<SuperVO> list=new ArrayList<SuperVO>();
				for(int i=0;i<oldchilds.length;i++){	
					if(((ISonExtendVO)oldchilds[i]).getSonVOS1()!=null&& ((ISonExtendVO)oldchilds[i]).getSonVOS1().size()>0){
					  list.addAll(((ISonExtendVO)oldchilds[i]).getSonVOS1());
					}
				}
				saveXiHa(list);
		}		
	}
}
