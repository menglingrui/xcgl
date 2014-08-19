package nc.bs.xcgl.saleweighdoc;

import java.util.ArrayList;
import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.bs.xcgl.pub.XCZmPubDAO;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.MonthCloseHelper;
import nc.vo.xcgl.saleweighdoc.SaleWeighdocHVO;
import nc.vo.xcgl.sample.SampleBVO;
import nc.vo.xcgl.soct.SoctB7VO;

/**
 * ���۹����Ǽ�����ʱ�Զ�����
 * �����족�����ͻ��������ٲá���
 * �����족������������������Ʒ����
 * Ʒ���͵�����̬������������
 * @author Jay
 *
 */

public class SaleWeighdocBO {

	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		if(valueVo==null||vo==null){
			return;
		}else{
			List<SampleBVO> list=new ArrayList<SampleBVO>();
			HYBillVO tarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_saleweighdoc, PubBillTypeConst.billtype_salesample, valueVo,vo);
			SaleWeighdocHVO hvo=(SaleWeighdocHVO) ObjectUtils.serializableClone(valueVo.getParentVO());
			SampleBVO[] bvos = (SampleBVO[]) ObjectUtils.serializableClone(tarBill.getChildrenVO());
			/**
			 *  ��Դ��Ϣ
			 * 	vlastbillcode vlastbillid vlastbillrowid vlastbilltype
			 *  vsourcebillid vsourcebillrowid vsourcebilltype csourcebillcode
			 */
			String vlastbillcode=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillcode"));
			String vlastbillid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillid"));
			String vlastbillrowid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbillrowid"));
			String vlastbilltype=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vlastbilltype"));
			String vsourcebillid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vsourcebillid"));
			String vsourcebillrowid=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vsourcebillrowid"));
			String vsourcebilltype=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("vsourcebilltype"));
			String csourcebillcode=PuPubVO.getString_TrimZeroLenAsNull(bvos[0].getAttributeValue("csourcebillcode"));
            if(vsourcebillid!=null&&vsourcebillid.trim()!=""){
            List<SoctB7VO> indexlist=(List<SoctB7VO>) XCZmPubDAO.getDAO().retrieveByClause(SoctB7VO.class, "pk_soct='"+vsourcebillid+"' and isnull(dr,0)=0");
            if(indexlist!=null&&indexlist.size()!=0){
            	for(int i=0;i<indexlist.size();i++){
            		SampleBVO sambvo=new SampleBVO();
            		sambvo.setCrowno(PuPubVO.getString_TrimZeroLenAsNull((i+1)*10));
            		sambvo.setVlastbillcode(vlastbillcode);
            		sambvo.setVlastbillid(vlastbillid);
            		sambvo.setVlastbillrowid(vlastbillrowid);
            		sambvo.setVlastbilltype(vlastbilltype);
            		sambvo.setVsourcebillid(vsourcebillid);
            		sambvo.setVsourcebillrowid(vsourcebillrowid);
            		sambvo.setVsourcebilltype(vsourcebilltype);
            		sambvo.setCsourcebillcode(csourcebillcode);
            		sambvo.setPk_invmandoc(PuPubVO.getString_TrimZeroLenAsNull(indexlist.get(i).getAttributeValue("pk_mandex")));
            		sambvo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(indexlist.get(i).getAttributeValue("pk_invdex")));
            		sambvo.setPk_measdoc(PuPubVO.getString_TrimZeroLenAsNull(indexlist.get(i).getAttributeValue("pk_measdoc")));
            		sambvo.setUimpurity(PuPubVO.getUFBoolean_NullAs(indexlist.get(i).getAttributeValue("isprice"), UFBoolean.FALSE));
            		list.add(sambvo);
            	}
    			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_salesample);//���õ�������
    			//String vbillno=new HYPubBO().getBillNo(PubBillTypeConst.billtype_salesample, SQLHelper.getCorpPk(), null, null);
    			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
    			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//�Ƶ���
    			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//�Ƶ�����
    			//���ñ����ָ����Ϣ
    			tarBill.setChildrenVO(list.toArray(new SampleBVO[0]));
    			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
    			UFDate curdate=UFDate.getDate(vo.m_currentDate);
    			UFBoolean isclose=PuPubVO.getUFBoolean_NullAs(MonthCloseHelper.isMonthClose(curdate,vo.m_coId),UFBoolean.FALSE);
    			if(!isclose.booleanValue()){
    			//���ɸ�����Ʒ���͵�������
    			//0 ������ 1 �ͻ��� 2 �ٲ��� 3 ������ 4 ���� 5����� 6Ԥ������
    			for(int i=0;i<7;i++){
    				HYBillVO targetvo=(HYBillVO) ObjectUtils.serializableClone(tarBill);
    				targetvo.getParentVO().setAttributeValue("isampletype",i);
    				targetvo.getParentVO().setAttributeValue("vbillno", 
    						new HYPubBO().getBillNo(PubBillTypeConst.billtype_salesample, SQLHelper.getCorpPk(), null, null));//���õ��ݺţ�
    			PfUtilWorkFlowVO vo1=new PfUtilWorkFlowVO();
    		     new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_salesample, 
    					dbilldate, vo1, targetvo, null);
    		}
    			}
    			else{
    				throw new BusinessException("��ǰ�����ѹ���!");
    			}
            	
            }
            else{
            	//�򾫷۲��Ƽۣ����ԾͲ���������������
            	//�򾫷۵����ۺ�ͬ�Ƽ�ҳǩû����
            	return;
            }
            }

		}
	}
		
	}


