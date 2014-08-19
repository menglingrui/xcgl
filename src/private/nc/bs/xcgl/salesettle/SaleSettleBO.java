package nc.bs.xcgl.salesettle;

import java.util.ArrayList;
import java.util.List;

import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pf.PfUtilWorkFlowVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubNodeModelConst;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;
import nc.vo.xcgl.salepresettle.SalepresettleHVO;
import nc.vo.xcgl.salesettledroop.SalesettledroopBVO;

public class SaleSettleBO {
	/**
	 * �����۽�����
	 * 
	 * @param billvo
	 * @param pfvo
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void sendMessage(AggregatedValueObject billvo1, PfParameterVO pfvo)
			throws Exception {
		if (billvo1 != null && billvo1.getParentVO() != null) {
			List<HYBillVO> list = new ArrayList<HYBillVO>();
			// ���㵥
			SalepresettleHVO hvo = (SalepresettleHVO) ObjectUtils.serializableClone(billvo1.getParentVO());
			SalepresettleBVO[] bvos1 = (SalepresettleBVO[]) ObjectUtils.serializableClone(billvo1.getChildrenVO());
			// �������кŷֵ�
			SalepresettleBVO[][] bbvos = (SalepresettleBVO[][]) SplitBillVOs.getSplitVOs(bvos1, new String[] { "vreserve2" });
			for (int n = 0; n < bbvos.length; n++) {
				SalepresettleBVO[] bvos = bbvos[n];
				AggSalepresettleVO billvo = new AggSalepresettleVO();
				billvo.setParentVO(hvo);
				billvo.setChildrenVO(bvos);
				String vlastbillid = null;
				UFDouble notaxmargin1 = null;
				UFDouble npricetaxsum1 = null;
				UFDouble notaxmargin2 = null;
				UFDouble npricetaxsum2 = null;
				UFDouble zc = null;
				// �õ���ͷ�������
				String pk = getPowerPk(bvos);
				// �������壬ȡ���������ͱ�ͷ�����ͬ�� �������Դid
				for (int i = 0; i < bvos.length; i++) {
					String pk_invmandoc_b = bvos[i].getPk_invmandoc();
					if (pk.equals(pk_invmandoc_b)) {
						vlastbillid = bvos[i].getVlastbillid();
					}
				}
				// ����Ԥ���㵥�� ���� �������ͺ�ͬ���Ĵ���ͽ��㵥�е���Դid,��ѯ�ж��ٸ���¼
				String sql2 = "select count(*) from xcgl_salepresettle_b b,xcgl_salepresettle_h h "
						+ " where (h.pk_salepresettle_h=b.pk_salepresettle_h)"
						+
						// " and h.pk_invmandoc='"+pk+"'" +
						" and b.vlastbillid='"
						+ vlastbillid
						+ "'"
						+ " and h.pk_billtype='"
						+ PubBillTypeConst.billtype_salepresettle
						+ "'"
						+ " and isnull(h.dr,0)=0"
						+ " and isnull(b.dr,0)=0"
						+ " and pk_corp='" + hvo.getPk_corp() + "'";
				int m = (Integer) QueryHelper.executeQuery(sql2,
						new ColumnProcessor());
				if (m <= 0) {
					HYBillVO tarBill = (HYBillVO) PfUtilTools.runChangeData(
							PubBillTypeConst.billtype_salesettle,
							PubBillTypeConst.billtype_salesettledroop, billvo,
							pfvo);
					tarBill.getParentVO().setAttributeValue("pk_billtype",
							PubBillTypeConst.billtype_salesettledroop);// ���õ�������
					tarBill.getParentVO().setAttributeValue(
							"vbillno",
							new HYPubBO().getBillNo(
									PubBillTypeConst.billtype_salesettledroop,
									SQLHelper.getCorpPk(), null, null));// ���õ��ݺ�
					tarBill.getParentVO().setAttributeValue("vbillstatus",
							IBillStatus.FREE);// ���õ���״̬
					tarBill.getParentVO().setAttributeValue("dmakedate",
							new UFDate(pfvo.m_currentDate));// �����Ƶ�����
					tarBill.getParentVO().setAttributeValue("voperatorid",
							pfvo.m_operator);// �Ƶ���
					String dbilldate = PuPubVO.getString_TrimZeroLenAsNull(hvo
							.getDbilldate());
					list.add(tarBill);
					PfUtilWorkFlowVO vo2 = new PfUtilWorkFlowVO();
					// new PfUtilBO().processAction("WRITE",
					// PubNodeModelConst.NodeModel_salesettledroop,
					// dbilldate, vo2, tarBill, null);
					// return;
				} else {
					// ������Դid�͵������ͣ���ѯ����Ԥ���㵥����������
					String sql = "select h.pk_salepresettle_h from xcgl_salepresettle_h h,xcgl_salepresettle_b b where"
							+ " (h.pk_salepresettle_h=b.pk_salepresettle_h) and b.vlastbillid='"
							+ vlastbillid
							+ "' "
							+ " and h.pk_billtype='"
							+ PubBillTypeConst.billtype_salepresettle
							+ "' and h.pk_corp='"
							+ hvo.pk_corp
							+ "' "
							+ " and isnull(h.dr,0)=0 and isnull(b.dr,0)=0 ";
					// �õ�����Ԥ���㵥��ͷ����
					String pk_salepresettle_h = (String) QueryHelper
							.executeQuery(sql, new ColumnProcessor());
					isCheckPaass(pk_salepresettle_h);
					// ͨ������Ԥ�����ͷ�����õ�����VO
					List<SalepresettleBVO> list1 = (List<SalepresettleBVO>) QueryHelper
							.retrieveByClause(SalepresettleBVO.class,
									" pk_salepresettle_h='"
											+ pk_salepresettle_h
											+ "' and isnull(dr,0)=0 ");
					SalepresettleBVO[] bvos2 = list1
							.toArray(new SalepresettleBVO[0]);

					bvos2 = spiltBySource(bvos2, vlastbillid);

					HYBillVO tarBill = (HYBillVO) PfUtilTools.runChangeData(
							PubBillTypeConst.billtype_salesettle,
							PubBillTypeConst.billtype_salesettledroop, billvo,
							pfvo);
					tarBill.getParentVO().setAttributeValue("pk_billtype",
							PubBillTypeConst.billtype_salesettledroop);// ���õ�������
					tarBill.getParentVO().setAttributeValue(
							"vbillno",
							new HYPubBO().getBillNo(
									PubBillTypeConst.billtype_salesettledroop,
									SQLHelper.getCorpPk(), null, null));// ���õ��ݺ�
					tarBill.getParentVO().setAttributeValue("vbillstatus",
							IBillStatus.FREE);// ���õ���״̬
					tarBill.getParentVO().setAttributeValue("dmakedate",
							new UFDate(pfvo.m_currentDate));// �����Ƶ�����
					tarBill.getParentVO().setAttributeValue("voperatorid",
							pfvo.m_operator);// �Ƶ���
					CircularlyAccessibleValueObject[] bvo = tarBill
							.getChildrenVO();
					for (int i = 0; i < bvos.length; i++) {
						for (int j = 0; j < bvos2.length; j++) {
							if (bvos[i].getPk_invmandoc().equals(
									bvos2[j].getPk_invmandoc())) {
								zc = PuPubVO.getUFDouble_NullAsZero(bvos[i]
										.getNreserve1());
								notaxmargin1 = PuPubVO
										.getUFDouble_NullAsZero(bvos[i]
												.getNotaxsum());
								notaxmargin2 = PuPubVO
										.getUFDouble_NullAsZero(bvos2[j]
												.getNotaxsum());
								notaxmargin2 = notaxmargin1.sub(notaxmargin2);
								npricetaxsum1 = PuPubVO
										.getUFDouble_NullAsZero(bvos[i]
												.getNpricetaxsum());
								npricetaxsum2 = PuPubVO
										.getUFDouble_NullAsZero(bvos2[j]
												.getNpricetaxsum());
								npricetaxsum2 = npricetaxsum1
										.sub(npricetaxsum2);
								bvo[i].setAttributeValue("notaxmargin",
										notaxmargin2);
								bvo[i].setAttributeValue("ntaxmargin",
										npricetaxsum2);
							}
						}
						for (int j = 0; j < bvos2.length; j++) {
							if (bvos[i].getPk_invmandoc().equals(
									bvos2[j].getPk_invmandoc())) {
				
								notaxmargin1 = PuPubVO
										.getUFDouble_NullAsZero(bvos[i]
												.getNreserve9());
								notaxmargin2 = PuPubVO
										.getUFDouble_NullAsZero(bvos2[j]
												.getNreserve9());
								notaxmargin2 = notaxmargin1.sub(notaxmargin2);
								npricetaxsum1 = PuPubVO
										.getUFDouble_NullAsZero(bvos[i]
												.getNreserve10());
								npricetaxsum2 = PuPubVO
										.getUFDouble_NullAsZero(bvos2[j]
												.getNreserve10());
								npricetaxsum2 = npricetaxsum1
										.sub(npricetaxsum2);
								bvo[i].setAttributeValue("nreserve9",
										notaxmargin2);
								bvo[i].setAttributeValue("nreserve10",
										npricetaxsum2);
							}
						}
					}

					String dbilldate = PuPubVO.getString_TrimZeroLenAsNull(hvo
							.getDbilldate());
					list.add(tarBill);
					// PfUtilWorkFlowVO vo2=new PfUtilWorkFlowVO();
					// new PfUtilBO().processAction("WRITE",
					// PubNodeModelConst.NodeModel_salesettledroop,
					// dbilldate, vo2, tarBill, null);
				}
			}
			if (list == null || list.size() == 0) {
				return;
			}
			HYBillVO tabill = list.get(0);
			List blist = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				HYBillVO dbill = list.get(i);
				if (dbill.getChildrenVO() != null
						&& dbill.getChildrenVO().length > 0) {
					for (int j = 0; j < dbill.getChildrenVO().length; j++) {
						blist.add(dbill.getChildrenVO()[j]);
					}
				}
			}
			tabill.setChildrenVO((CircularlyAccessibleValueObject[]) (blist
					.toArray(new SalesettledroopBVO[0])));
			PfUtilWorkFlowVO vo2 = new PfUtilWorkFlowVO();
			String dbilldate = PuPubVO.getString_TrimZeroLenAsNull(hvo
					.getDbilldate());
			new PfUtilBO().processAction("WRITE",
					PubNodeModelConst.NodeModel_salesettledroop, dbilldate,
					vo2, tabill, null);
		}
	}

	public String getPowerPk(SalepresettleBVO[] bvos) {
		if (bvos == null || bvos.length == 0) {
			return null;
		}
		for (int i = 0; i < bvos.length; i++) {
			if (PuPubVO.getUFBoolean_NullAs(bvos[i].getUreserve2(),
					UFBoolean.FALSE).booleanValue() == true) {
				return bvos[i].getPk_invmandoc();
			}
		}
		return null;
	}

	private void isCheckPaass(String pk_salepresettle_h)
			throws BusinessException {
		String sql = "select h.vbillno,h.vbillstatus from xcgl_salepresettle_h h where "
				+ " isnull(h.dr,0)=0 and  pk_salepresettle_h='"
				+ pk_salepresettle_h + "'";
		// �õ�����Ԥ���㵥��ͷ����
		Object[] objs1 = (Object[]) QueryHelper.executeQuery(sql,
				new ArrayProcessor());
		if (objs1 != null && objs1.length == 2) {
			Integer status = PuPubVO.getInteger_NullAs(objs1[1], -1);
			if (status != IBillStatus.CHECKPASS) {
				throw new BusinessException("���۽��㣬���ɵ��ʱ����������Ԥ����û����ˣ����ݺ�λ��["
						+ objs1[0] + "]");
			}
		}

	}

	private SalepresettleBVO[] spiltBySource(SalepresettleBVO[] bvos2,
			String pk_weight) {
		SalepresettleBVO[][] bbvos = (SalepresettleBVO[][]) SplitBillVOs
				.getSplitVOs(bvos2, new String[] { "vreserve2" });
		SalepresettleBVO[] bvos = null;
		for (int n = 0; n < bbvos.length; n++) {
			SalepresettleBVO[] vos = bbvos[n];
			for (int j = 0; j < vos.length; j++) {
				if (pk_weight.equals(vos[j].getVlastbillid())) {

					return vos;
				}
			}
		}
		return null;
	}
}
