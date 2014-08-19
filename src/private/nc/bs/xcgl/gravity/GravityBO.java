package nc.bs.xcgl.gravity;

import java.util.ArrayList;
import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.IndexFineHeler;
import nc.vo.xcgl.sample.SampleBVO;
import nc.vo.xcgl.sample.SampleHVO;
/**
 * ��ѡ��ⵥ���������������������ӹ�ģ���µ���������
 * @author Jay
 *
 */
public class GravityBO {

	public void sendMessage(AggregatedValueObject valueVo, PfParameterVO vo) throws Exception {
		if(valueVo==null||vo==null){
			return;
		}else{
			List<SampleBVO> tarlist=new ArrayList<SampleBVO>();
			//�߽����࣬����������Ϣ(��Ҫ����ѡ�ı�ͷ��Ϣ�ͱ���Ĵ����Ϣ)
			HYBillVO tarBill = (HYBillVO)PfUtilTools.runChangeData(PubBillTypeConst.billtype_gravity, PubBillTypeConst.billtype_sample, valueVo,vo);
			SampleHVO hvo = (SampleHVO) tarBill.getParentVO();
			tarBill.getParentVO().setAttributeValue("pk_billtype", PubBillTypeConst.billtype_sample);//���õ�������
			//String vbillno=new HYPubBO().getBillNo(PubBillTypeConst.billtype_salesample, SQLHelper.getCorpPk(), null, null);
			tarBill.getParentVO().setAttributeValue("vbillstatus",IBillStatus.FREE);
			tarBill.getParentVO().setAttributeValue("voperatorid",PuPubVO.getString_TrimZeroLenAsNull(vo.m_operator));//�Ƶ���
			tarBill.getParentVO().setAttributeValue("dmakedate", new UFDate(vo.m_currentDate));//�Ƶ�����
			tarBill.getParentVO().setAttributeValue("vbillno", 
					new HYPubBO().getBillNo(PubBillTypeConst.billtype_sample, SQLHelper.getCorpPk(), null, null));//���õ��ݺţ�
			String dbilldate=PuPubVO.getString_TrimZeroLenAsNull(hvo.getDbilldate());
			String minarea=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_minarea"));
			String invmandoc=PuPubVO.getString_TrimZeroLenAsNull(hvo.getAttributeValue("pk_invmandoc"));
			IndexParaVO para=new IndexParaVO();
			para.setAttributeValue("pk_invmandoc",invmandoc );
			para.setAttributeValue("pk_minarea", minarea);
			para.setPk_corp(SQLHelper.getCorpPk());
			//���մ����Ϣ�Ͳ��ţ���ѯ����ָ�궨�壬ȡ��ָ����Ϣ����������������
			List<LabIndexSetBVO> list = IndexFineHeler.getIndexFine(para);
			PfUtilWorkFlowVO vo1=new PfUtilWorkFlowVO();
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					LabIndexSetBVO labvo = list.get(i);
					//����һ��������Դ��Ϣ������������vo
					SampleBVO bvo=(SampleBVO) ObjectUtils.serializableClone(tarBill.getChildrenVO()[0]);
					//���������ɵı���ָ���кŵ���Ϣ��ֵ
					bvo.setCrowno(PuPubVO.getString_TrimZeroLenAsNull((i+1)*10));//�к�
					bvo.setPk_invmandoc(labvo.getPk_invmandoc());
					bvo.setPk_invbasdoc(labvo.getPk_invbasdoc());
					tarlist.add(bvo);
				}
				tarBill.setChildrenVO(tarlist.toArray(new SampleBVO[0]));
   		     new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_Sample, 
   					dbilldate, vo1, tarBill, null);
			}
			else{
				new PfUtilBO().processAction("WRITE", PubNodeModelConst.NodeModel_Sample, 
	   					dbilldate, vo1, tarBill, null);
			}
		}
		
	}

}
