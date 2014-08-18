package nc.bs.xcgl.salesettle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.zmpub.formula.calc.DoCalc;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.helper.QueryHelper;
import nc.vo.xcgl.pub.tool.XcPubTool;
import nc.vo.xcgl.qualitygrade.QualityGradeBVO;
import nc.vo.xcgl.qualityprice.QualityPriceBVO;
import nc.vo.xcgl.qualitypro.QualityPorB1VO;
import nc.vo.xcgl.qualitypro.QualityProB2VO;
import nc.vo.xcgl.salepresettle.AggSalepresettleVO;
import nc.vo.xcgl.salepresettle.SalepresettleBVO;
import nc.vo.xcgl.salepresettle.SalepresettleHVO;
import nc.vo.xcgl.sample.SampleBVO;
import nc.vo.xcgl.soct.SoctB7VO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 有关价格计算的后台处理类
 * 
 * @author yangtao
 * @param <E>
 * @date 2014-3-31 上午10:06:45
 */
public class Price {
	/**
	 * 公式运行BO
	 */
	public static DoCalc calformulabo = null;

	public static DoCalc getCalFormulaBO() {
		if (calformulabo == null) {
			calformulabo = new DoCalc();
		}
		return calformulabo;
	}

	private Map<String, SoctB7VO> contractMap = new HashMap<String, SoctB7VO>();

	private String pk_contract = null;

	private UFDate settleDate = null;

	private UFDate sendDate = null;

	// 计算精粉的含税单价=计价金属金额之和/精粉数量
	public void calPowerPrice(SalepresettleBVO[] bvos) {
		UFDouble taxmny = new UFDouble(0);
		for (int i = 0; i < bvos.length; i++) {
			if (PuPubVO.getUFBoolean_NullAs(bvos[i].getUimpurity(),
					new UFBoolean(false)).booleanValue() == true) {
				UFDouble ntaxsum = PuPubVO.getUFDouble_NullAsZero(
						bvos[i].getNamounted())
						.multiply(bvos[i].getNtaxprice());
				taxmny = taxmny.add(ntaxsum);
			}
		}
		UFDouble num = PuPubVO.getUFDouble_NullAsZero(bvos[0].getNamounted());
		if (PuPubVO.getUFBoolean_NullAs(bvos[0].getUreserve1(),
				new UFBoolean(false)).booleanValue() == true) {
			taxmny = PuPubVO.getUFDouble_NullAsZero(bvos[0].getNreserve2())
					.multiply(num);
		}
		UFDouble ntaxprice = taxmny.div(num);
		bvos[0].setNtaxprice(ntaxprice);
	}

	/**
	 * 计算预结算、结算价格（后台）
	 * 
	 * @param aggvo
	 * @return
	 * @author yangtao
	 * @throws BusinessException
	 * @date 2014-3-31 上午10:45:39
	 */

	@SuppressWarnings( { "unchecked" })
	public SalepresettleBVO[] CountPrice(AggSalepresettleVO aggvo,
			UFBoolean isPreSettle) throws Exception {
		SalepresettleHVO hvo = (SalepresettleHVO) aggvo.getParentVO();
		settleDate = hvo.getDbilldate();
		SalepresettleBVO[] bvos1 = (SalepresettleBVO[]) aggvo.getChildrenVO();

		SalepresettleBVO[][] bbvos = (SalepresettleBVO[][]) SplitBillVOs
				.getSplitVOs(bvos1, new String[] { "vreserve2" });
		List<SalepresettleBVO> blist = new ArrayList<SalepresettleBVO>();

		for (int n = 0; n < bbvos.length; n++) {
			contractMap.clear();
			SalepresettleBVO[] bvos = bbvos[n];
			sortByRowNo(bvos);
			String pk_weight = bvos[0].getVlastbillid();
			String sendsql = "select dbilldate from xcgl_weighdoc where pk_weighdoc='"
					+ pk_weight + "'";
			sendDate = PuPubVO.getUFDate(QueryHelper.executeQuery(sendsql,
					new ColumnProcessor()));

			// 拿到优质优价id
			pk_contract = bvos[0].getVsourcebillid();
			String qualityplan = PuPubVO
					.getString_TrimZeroLenAsNull(QueryHelper.executeQuery(
							"select vreserve1 from xcgl_soct where pk_soct='"
									+ pk_contract + "' and isnull(dr,0)=0 ",
							new ColumnProcessor()));
			// String qualityplan =hvo.getQualityplan();
			// 初始化调整后数量
			for (int i = 0; i < bvos.length; i++) {
				bvos[i].setNamounted(bvos[i].getNamount());
				bvos[i].setNgraded(bvos[i].getNgrade());
			}
			// 拿到优质优价-品位表体vo
			List<QualityGradeBVO> list = (List<QualityGradeBVO>) QueryHelper
					.retrieveByClause(QualityGradeBVO.class,
							"pk_qualitygrade_h='"
									+ getqualitygrade(qualityplan)
									+ "' and isnull(dr,0)=0");
			// ---如果不存在优质优价品味设置 --那就不用扣减品味了
			if (list != null && list.size() > 0) {

				QualityGradeBVO[] QualityGradeBVOS = list
						.toArray(new QualityGradeBVO[0]);

				// 对品位扣减品位的金属元素分组
				QualityGradeBVO[][] qgadess = (QualityGradeBVO[][]) SplitBillVOs
						.getSplitVOs(QualityGradeBVOS, new String[] {
								"pk_mandoc", "pk_invmandoc" });

				for (int i = 0; i < qgadess.length; i++) {
					QualityGradeBVO[] gads = qgadess[i];
					/**
					 * index
					 */
					String pk_mandoc = gads[0].getPk_mandoc();
					/**
					 * 杂质
					 */
					String pk_invmandoc = gads[0].getPk_invmandoc();

					/**
					 * 查找结算扣减指标杂志
					 */
					SalepresettleBVO bvo = getSettleVOByIndex(bvos,
							pk_invmandoc);
					if (bvo == null) {
						continue;
					}
					/**
					 * 查找结算扣减指标
					 */
					SalepresettleBVO invdexbvo = getSettleVOByIndex(bvos,
							pk_mandoc);
					if (invdexbvo == null) {
						continue;
					}
					/**
					 * 杂志检测品位
					 */
					UFDouble ngrade = bvo.getNgrade();
					/**
					 * index检测品位
					 */
					UFDouble invngrade = invdexbvo.getNgrade();
					// 拿到符合条件的表体vo
					QualityGradeBVO vo = getscope(ngrade, gads);

					if (vo != null) {
						// 执行公式计算出调整后的品位
						UFDouble res = PuPubVO
								.getUFDouble_NullAsZero(exengradeformula(vo,
										ngrade, PuPubVO.getUFBoolean_NullAs(
												isPreSettle,
												new UFBoolean(false))
												.booleanValue()));
						Integer vadjtype = vo.getVadjtype();
						if (vadjtype == 0) {
							invngrade = invngrade.add(res);
						}// 上调
						if (vadjtype == 1) {
							invngrade = invngrade.sub(res);
						}// 下调
						/**
						 * 设置调整后品位
						 */
						invdexbvo.setNgraded(invngrade);
						String pk_invdexc = gads[0].getPk_invdoc();
						String pk_mes = XcPubTool.getMeasByIndex(pk_invdexc);
						if (PubOtherConst.pk_labgrade_ag.equals(pk_mes)) {
							invdexbvo.setNamounted(PuPubVO
									.getUFDouble_NullAsZero(
											bvos[0].getNamount()).multiply(
											invngrade.div(1)));
						} else {
							invdexbvo.setNamounted(PuPubVO
									.getUFDouble_NullAsZero(
											bvos[0].getNamount()).multiply(
											invngrade.div(100)));
						}

					}
				}
			}
			// 现执行询价，然后执行优质优价-价格公式
			for (int i = 0; i < bvos.length; i++) {
				calContractPrice(bvos[0].getVsourcebillid(), bvos[i], hvo);
			}
			// 执行优质优价-价格公式
			// 拿到优质优价-价格表体VO
			List<QualityPriceBVO> list1 = (List<QualityPriceBVO>) QueryHelper
					.retrieveByClause(QualityPriceBVO.class,
							"pk_qualityprice_h='"
									+ getqualityPrice(qualityplan)
									+ "' and isnull(dr,0)=0");
			if (list1 != null && list1.size() > 0) {
				// 如果优质优价价格设置没有 那就不调了
				QualityPriceBVO[] QualityPriceBVOS = list1
						.toArray(new QualityPriceBVO[0]);
				// 如果优质优价表体有，但没有符合条件的区间，那就不调价格了
				QualityPriceBVOS = getscopeofPrice(bvos, QualityPriceBVOS);
				bvos = exepriceformula(QualityPriceBVOS, bvos, hvo, PuPubVO
						.getUFBoolean_NullAs(isPreSettle, new UFBoolean(false))
						.booleanValue());
			} else {

			}
			calPowerPrice(bvos);
			for (int x = 0; x < bvos.length; x++) {
				blist.add(bvos[x]);
			}
		}
		return blist.toArray(new SalepresettleBVO[0]);
	}

	Map<Integer, SalepresettleBVO[]> resmap = new HashMap<Integer, SalepresettleBVO[]>();

	/**
	 * 取化验结果
	 * 
	 * @author yangtao
	 * @date 2014-3-26 下午03:47:08
	 * 
	 */
	@SuppressWarnings("unchecked")
	public AggSalepresettleVO ReadAssayres(AggSalepresettleVO aggvo,
			UFBoolean isPreSettle) throws Exception {
		resmap.clear();
		SalepresettleHVO hvo = (SalepresettleHVO) aggvo.getParentVO();
		SalepresettleBVO[] bvos = getOnlyPower((SalepresettleBVO[]) aggvo
				.getChildrenVO());
		// 先拿到化验类型
		Integer analystple = (Integer) PuPubVO.getInteger_NullAs(hvo
				.getVassaytype(), -1);
		if (analystple == -1 && isPreSettle.booleanValue() == true) {
			// 如果化验类型为空。抛出异常
			throw new Exception("表头化验类型不能为空");
		} else {
			//
			if (bvos == null || bvos.length == 0) {
				return aggvo;
			}

			for (int n = 0; n < bvos.length; n++) {
				SalepresettleBVO bvo = bvos[n];
				// 拿到过磅登记的id
				String vlastbillid = (String) bvo.getVlastbillid();
				String vlastbillrowid = (String) bvo.getVlastbillrowid();
				if (vlastbillid != null) {
					if (isPreSettle.booleanValue() == true) {
						// 根据过磅登记id找到送样单id
						// 根据送样单找到化验单id
						String sql = "select pk_sample from xcgl_sample where isnull(dr,0)=0  and pk_corp='"
								+ hvo.getPk_corp()
								+ "'"
								+ " and isampletype='"
								+ analystple
								+ "' and"
								+ // 这一行要有。过滤的是样品类型
								" pk_sample in ("
								+ " select pk_sample  from xcgl_sample_b "// hua
																			// yan
																			// jieguo
								+ " where isnull(dr,0)=0  and vlastbillid in ( "
								+ " select pk_sample  from xcgl_sample_b  "// hua
																			// yan
								+ " where  isnull(dr,0)=0  and vlastbillid in ("
								+ " select pk_sample from xcgl_sample_b "// song
																			// yang
								+ " where isnull(dr,0)=0  and vlastbillid ='"
								+ vlastbillid
								+ "' "
								+ " and vlastbillrowid='"
								+ vlastbillrowid + "' " + " )))";
						String sampleid = PuPubVO
								.getString_TrimZeroLenAsNull(QueryHelper
										.executeQuery(sql,
												new ColumnProcessor()));
						if (sampleid == null) {
							// throw new Exception("化验结果没有找到");
						} else {
							String nsql = "select vbillno from xcgl_sample where isnull(dr,0)=0  and pk_sample='"
									+ sampleid + "'";
							String vbillno = PuPubVO
									.getString_TrimZeroLenAsNull(QueryHelper
											.executeQuery(nsql,
													new ColumnProcessor()));
							// 根据化验单id找到化验结果。
							List<SampleBVO> list = (List<SampleBVO>) QueryHelper
									.retrieveByClause(SampleBVO.class,
											"pk_sample='" + sampleid + "'");
							SampleBVO[] sampleBVO = list
									.toArray(new SampleBVO[0]);
							if (isPreSettle.booleanValue()) {
								// downGrade(sampleBVO,bvo.getVsourcebillid());
								// upRounding(sampleBVO);
							}
							SalepresettleBVO[] dexvos = new SalepresettleBVO[sampleBVO.length];
							// 将查出来的化验结果填在界面上
							for (int i = 0; i < sampleBVO.length; i++) {
								dexvos[i] = new SalepresettleBVO();
								// 判断界面当前有多少行数据
								for (int j = 0; j < sampleBVO[i]
										.getAttributeNames().length; j++) {
									if ("crowno".equals(sampleBVO[i]
											.getAttributeNames()[j])) {
										continue;
									}
									dexvos[i]
											.setAttributeValue(
													sampleBVO[i]
															.getAttributeNames()[j],
													sampleBVO[i]
															.getAttributeValue(sampleBVO[i]
																	.getAttributeNames()[j]));
								}
								dexvos[i].setAttributeValue("vlastbillid",
										sampleBVO[i].getPk_sample());
								dexvos[i].setAttributeValue("vlastbillrowid",
										sampleBVO[i].getPk_sample_b());
								dexvos[i].setAttributeValue("vlastbillcode",
										vbillno);
								dexvos[i].setAttributeValue("vlastbilltype",
										PubBillTypeConst.billtype_saleassayres);
								// 计算出数量
								// 先得到精粉数量
								UFDouble namount = PuPubVO
										.getUFDouble_NullAsZero(bvo
												.getNamount());
								if (namount == null) {
									throw new Exception("精粉数量不能为空");
								}
								// 再得到当前行的品位
								UFDouble ngrade = PuPubVO
										.getUFDouble_NullAsZero(dexvos[i]
												.getNgrade());
								// 将当前行品位*精粉数量的值填到当前数量
								// 这里要把当前品位转换成百分值。不能直接乘
								String pk_invbasdoc = PuPubVO
										.getString_TrimZeroLenAsNull(dexvos[i]
												.getPk_invbasdoc());
								String pk_mes = XcPubTool
										.getMeasByIndex(pk_invbasdoc);
								if (PubOtherConst.pk_labgrade_ag.equals(pk_mes)) {
									dexvos[i].setAttributeValue("namount",
											namount.multiply(ngrade));
								} else {
									dexvos[i].setAttributeValue("namount",
											namount.multiply(ngrade
													.multiply(0.01)));
								}

							}
							resmap.put(n, dexvos);
						}
					} else {
						int[] analystples = new int[] { 0, 3, 2 };

						Map<String, SalepresettleBVO> hmap = new HashMap<String, SalepresettleBVO>();

						for (int n1 = 0; n1 < analystples.length; n1++) {
							// 根据过磅登记id找到送样单id
							// 根据送样单找到化验单id
							String sql = "select pk_sample from xcgl_sample where isnull(dr,0)=0  and pk_corp='"
									+ hvo.getPk_corp()
									+ "'"
									+ " and isampletype='"
									+ analystples[n1]
									+ "' and"
									+ // 这一行要有。过滤的是样品类型
									" pk_sample in ("
									+ " select pk_sample  from xcgl_sample_b "// hua
																				// yan
																				// jieguo
									+ " where isnull(dr,0)=0  and vlastbillid in ( "
									+ " select pk_sample  from xcgl_sample_b  "// hua
																				// yan
									+ " where  isnull(dr,0)=0  and vlastbillid in ("
									+ " select pk_sample from xcgl_sample_b "// song
																				// yang
									+ " where isnull(dr,0)=0  and vlastbillid ='"
									+ vlastbillid
									+ "' "
									+ " and vlastbillrowid='"
									+ vlastbillrowid
									+ "' " + " )))";
							String sampleid = PuPubVO
									.getString_TrimZeroLenAsNull(QueryHelper
											.executeQuery(sql,
													new ColumnProcessor()));
							if (sampleid == null) {
								// throw new Exception("化验结果没有找到");
							} else {
								String nsql = "select vbillno from xcgl_sample where isnull(dr,0)=0  and pk_sample='"
										+ sampleid + "'";
								String vbillno = PuPubVO
										.getString_TrimZeroLenAsNull(QueryHelper
												.executeQuery(nsql,
														new ColumnProcessor()));
								// 根据化验单id找到化验结果。
								List<SampleBVO> list = (List<SampleBVO>) QueryHelper
										.retrieveByClause(SampleBVO.class,
												"pk_sample='" + sampleid + "'");
								SampleBVO[] sampleBVO = list
										.toArray(new SampleBVO[0]);
								if (isPreSettle.booleanValue()) {
									// downGrade(sampleBVO,bvo.getVsourcebillid());
									// upRounding(sampleBVO);
								}
								SalepresettleBVO[] dexvos = new SalepresettleBVO[sampleBVO.length];
								// 将查出来的化验结果填在界面上
								for (int i = 0; i < sampleBVO.length; i++) {
									dexvos[i] = new SalepresettleBVO();
									// 判断界面当前有多少行数据
									for (int j = 0; j < sampleBVO[i]
											.getAttributeNames().length; j++) {
										if ("crowno".equals(sampleBVO[i]
												.getAttributeNames()[j])) {
											continue;
										}
										dexvos[i]
												.setAttributeValue(
														sampleBVO[i]
																.getAttributeNames()[j],
														sampleBVO[i]
																.getAttributeValue(sampleBVO[i]
																		.getAttributeNames()[j]));
									}
									dexvos[i].setAttributeValue("vlastbillid",
											sampleBVO[i].getPk_sample());
									dexvos[i].setAttributeValue(
											"vlastbillrowid", sampleBVO[i]
													.getPk_sample_b());
									dexvos[i].setAttributeValue(
											"vlastbillcode", vbillno);
									dexvos[i]
											.setAttributeValue(
													"vlastbilltype",
													PubBillTypeConst.billtype_saleassayres);
									// 计算出数量
									// 先得到精粉数量
									UFDouble namount = PuPubVO
											.getUFDouble_NullAsZero(bvo
													.getNamount());
									if (namount == null) {
										throw new Exception("精粉数量不能为空");
									}
									// 再得到当前行的品位
									UFDouble ngrade = PuPubVO
											.getUFDouble_NullAsZero(dexvos[i]
													.getNgrade());
									// 将当前行品位*精粉数量的值填到当前数量
									// 这里要把当前品位转换成百分值。不能直接乘
									String pk_invbasdoc = PuPubVO
											.getString_TrimZeroLenAsNull(dexvos[i]
													.getPk_invbasdoc());
									String pk_mes = XcPubTool
											.getMeasByIndex(pk_invbasdoc);
									if (PubOtherConst.pk_labgrade_ag
											.equals(pk_mes)) {
										dexvos[i].setAttributeValue("namount",
												namount.multiply(ngrade));
									} else {
										dexvos[i].setAttributeValue("namount",
												namount.multiply(ngrade
														.multiply(0.01)));
									}
									if (ngrade.doubleValue() > 0) {
										hmap.put(dexvos[i].getPk_invmandoc(),
												dexvos[i]);
									}
								}

							}
							if (hmap != null && hmap.size() > 0) {
								SalepresettleBVO[] dexvos = new SalepresettleBVO[hmap
										.size()];
								int x = 0;
								for (String key : hmap.keySet()) {
									dexvos[x] = hmap.get(key);
									x++;
								}
								resmap.put(n, dexvos);
							}
						}
					}
				} else {
					if (PuPubVO.getUFBoolean_NullAs(bvos[n].getUreserve1(),
							new UFBoolean(false)).booleanValue() == true) {

					} else {
						throw new Exception("读取化验结果失败");
					}
				}
			}
			List<SalepresettleBVO> list = new ArrayList<SalepresettleBVO>();
			int row = 1;
			int rno = 0;
			for (int i = 0; i < bvos.length; i++) {
				String glrowno = (1 + i + rno) * 10 + "";
				bvos[i].setCrowno(glrowno);
				bvos[i].setVreserve2(glrowno);
				bvos[i].setUreserve2(new UFBoolean(true));
				list.add(bvos[i]);
				if (resmap.get(i) != null && resmap.get(i).length > 0) {
					SalepresettleBVO[] dexs = resmap.get(i);
					if (dexs != null || dexs.length > 0) {
						for (int j = 0; j < dexs.length; j++) {
							dexs[j].setCrowno((2 + i + rno) * 10 + "");
							dexs[j].setVreserve2(glrowno);
							rno = rno + 1;
							list.add(dexs[j]);
						}
					}
				}
			}
			aggvo.setChildrenVO(list.toArray(new SalepresettleBVO[0]));
		}
		return aggvo;
	}

	// /**
	// * 按合同下调品位
	// * @param sampleBVO
	// * @throws BusinessException
	// */
	// public void downGrade(SampleBVO[] bvos,String contractid) throws
	// BusinessException {
	// contractMap.clear();
	// List<SoctB7VO> rules = (List<SoctB7VO>) QueryHelper.retrieveByClause(
	// SoctB7VO.class, "pk_soct='" +contractid + "' and isnull(dr,0)=0");
	// if(rules!=null&&rules.size()>0){
	// for(int i=0;i<rules.size();i++){
	// contractMap.put(rules.get(i).getPk_mandex(), rules.get(i));
	// }
	// }
	//	
	// if(bvos==null || bvos.length==0){
	// return ;
	// }
	// for(int i=0;i<bvos.length;i++){
	// UFDouble grade=PuPubVO.getUFDouble_NullAsZero(bvos[i].getNgrade());
	// if(contractMap.get(bvos[i].getPk_invmandoc())!=null){
	// UFDouble
	// gradedown=PuPubVO.getUFDouble_NullAsZero(contractMap.get(bvos[i].getPk_invmandoc()).getNdownnum());
	// grade=grade.sub(gradedown);
	// }
	// bvos[i].setNgrade(grade);
	// }
	//		
	// }
	// /**
	// * 化验结果向上取整
	// * @param sampleBVO
	// */
	// public void upRounding(SampleBVO[] bvos) {
	//		
	// if(bvos==null || bvos.length==0){
	// return ;
	// }
	// for(int i=0;i<bvos.length;i++){
	// UFDouble grade=PuPubVO.getUFDouble_NullAsZero(bvos[i].getNgrade());
	//			
	// int value=(int) Math.ceil(grade.doubleValue());
	//			
	// bvos[i].setNgrade(new UFDouble(value));
	// }
	// }
	public CircularlyAccessibleValueObject[] setRowNo(SalepresettleBVO[] vos) {
		if (vos == null || vos.length == 0) {
			return null;
		}
		for (int i = 0; i < vos.length; i++) {
			vos[i].setCrowno((i + 1) * 10 + "");
		}
		return vos;
	}

	public SalepresettleBVO[] getOnlyPower(SalepresettleBVO[] bvos) {
		if (bvos == null || bvos.length == 0) {
			return bvos;
		}
		List<SalepresettleBVO> list = new ArrayList<SalepresettleBVO>();
		for (int i = 0; i < bvos.length; i++) {
			if (bvos[i].getVlastbilltype().equals(
					PubBillTypeConst.billtype_saleweighdoc)) {
				list.add(bvos[i]);
			}
		}
		return list.toArray(new SalepresettleBVO[0]);
	}

	public void sortByRowNo(SalepresettleBVO[] bvos) {
		if (bvos == null || bvos.length == 0) {
			return;
		}
		for (int i = 0; i < bvos.length; i++) {
			for (int j = i; j < bvos.length; j++) {
				UFDouble pre = PuPubVO.getUFDouble_NullAsZero(bvos[i]
						.getCrowno());
				UFDouble aft = PuPubVO.getUFDouble_NullAsZero(bvos[j]
						.getCrowno());
				if ((pre.sub(aft)).doubleValue() > 0) {
					SalepresettleBVO swap = bvos[i];
					bvos[i] = bvos[j];
					bvos[j] = swap;
				}
			}
		}
	}

	public SalepresettleBVO getSettleVOByIndex(SalepresettleBVO[] bvos,
			String pk_mandoc) {
		if (bvos == null || bvos.length == 0) {
			return null;
		}
		for (int i = 0; i < bvos.length; i++) {
			if (bvos[i].getPk_invmandoc().equals(pk_mandoc)) {
				return bvos[i];
			}
		}
		return null;
	}

	private QualityPriceBVO[] getscopeofPrice(SalepresettleBVO[] bvos,
			QualityPriceBVO[] QualityPriceBVOS) {
		List<QualityPriceBVO> list = new ArrayList<QualityPriceBVO>();
		if (bvos == null || bvos.length == 0) {
			return null;
		}
		for (int i = 0; i < bvos.length; i++) {
			double grade = bvos[i].getNgraded() == null ? 0.0 : bvos[i]
					.getNgraded().toDouble();// 品位
			QualityPriceBVO vo = null;
			for (int j = 0; j < QualityPriceBVOS.length; j++) {
				if (bvos[i].getPk_invmandoc().equals(
						QualityPriceBVOS[j].getPk_invmandoc())) {
					// 上限
					double nupvalue = QualityPriceBVOS[j].getNupvalue() == null ? 1000.0
							: QualityPriceBVOS[j].getNupvalue().toDouble();
					// 下限
					double nlowvalue = QualityPriceBVOS[j].getNlowvalue() == null ? 0.0
							: QualityPriceBVOS[j].getNlowvalue().toDouble();
					// 是否在范围内
					Boolean flog = false;

					// 是否包含下限
					UFBoolean uincludelow = QualityPriceBVOS[j]
							.getUincludelow();
					// 是否包含上限
					UFBoolean uincludeup = QualityPriceBVOS[j].getUincludeup();
					if (uincludelow == null) {
						uincludelow = new UFBoolean(false);
					}
					if (uincludeup == null) {
						uincludeup = new UFBoolean(false);
					}

					if (uincludelow.booleanValue() && uincludeup.booleanValue()) {
						flog = (nlowvalue <= grade && grade <= nupvalue);

					} else if (uincludelow.booleanValue() == false
							&& uincludeup.booleanValue()) {
						flog = (nlowvalue < grade && grade <= nupvalue);

					} else if (uincludelow.booleanValue()
							&& uincludeup.booleanValue() == false) {
						flog = (nlowvalue <= grade && grade < nupvalue);
					} else if (uincludelow.booleanValue() == false
							&& uincludeup.booleanValue() == false) {
						flog = (nlowvalue < grade && grade < nupvalue);

					}
					if (flog) {
						vo = QualityPriceBVOS[j];
						list.add(vo);
						break;
					}
				}
			}
		}

		return list.toArray(new QualityPriceBVO[0]);
	}

	/**
	 * 优质优价品位执行公式
	 * 
	 * @param gradevo
	 * @param fvo
	 * @author yangtao
	 * @throws Exception
	 * @date 2014-4-2 上午10:13:19
	 */
	public UFDouble exengradeformula(QualityGradeBVO gradevo, UFDouble ngrade,
			boolean ispresettle) throws Exception {
		if (gradevo == null) {
			return null;
		}
		String item[] = gradevo.getAttributeNames();
		ReportBaseVO vo = new ReportBaseVO();

		for (int i = 0; i < item.length; i++) {
			vo.setAttributeValue(item[i], gradevo.getAttributeValue(item[i]));
		}

		vo.setAttributeValue("raworenum", ngrade);

		vo.setAttributeValue("isresettle", new UFBoolean(ispresettle));
		if (getContractMap(pk_contract).get(gradevo.getPk_mandoc()) != null) {
			vo.setAttributeValue("resettlenum", getContractMap(pk_contract)
					.get(gradevo.getPk_mandoc()).getNdownnum());
		}

		String code1 = PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue("vformulacode"));
		String name1 = PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue("vformula"));

		List<UFDouble> list = null;
		if (code1 != null) {
			list = getCalFormulaBO().doCalcStart(code1, name1,
					new ReportBaseVO[] { vo });
		}

		return list.get(0);
	}

	/**
	 * 执行优质优价价格公式
	 * 
	 * @param gradevo
	 * @param ngrade
	 * @return
	 * @throws Exception
	 * @author yangtao
	 * @throws Exception
	 * @date 2014-4-2 下午05:16:01
	 */
	public SalepresettleBVO[] exepriceformula(QualityPriceBVO[] pricevo,
			SalepresettleBVO[] bvos, SalepresettleHVO hvo, boolean ispresettle)
			throws Exception {
		if (pricevo != null && pricevo.length > 0) {
			for (int i = 0; i < pricevo.length; i++) {
				String pk_invmandoc = (String) pricevo[i]
						.getAttributeValue("pk_invmandoc");
				for (int j = 0; j < bvos.length; j++) {
					if (pk_invmandoc.equals(bvos[j].getPk_invmandoc())) {
						String item[] = pricevo[i].getAttributeNames();
						ReportBaseVO vo = new ReportBaseVO();
						for (int l = 0; l < item.length; l++) {
							vo.setAttributeValue(item[l], pricevo[i]
									.getAttributeValue(item[l]));
						}
						vo.setAttributeValue("raworenum", bvos[j].getNgraded());
						calContractPrice(bvos[0].getVsourcebillid(), bvos[j],
								hvo);
						vo.setAttributeValue("isresettle", new UFBoolean(
								ispresettle));
						if (getContractMap(pk_contract).get(
								pricevo[i].getPk_invmandoc()) != null) {
							vo
									.setAttributeValue("resettlenum",
											getContractMap(pk_contract).get(
													pricevo[i]
															.getPk_invmandoc())
													.getNdownnum());
						}
						vo.setAttributeValue("avgtovmanth", bvos[j]
								.getNtaxprice());
						String code1 = PuPubVO.getString_TrimZeroLenAsNull(vo
								.getAttributeValue("vformulacode"));
						String name1 = PuPubVO.getString_TrimZeroLenAsNull(vo
								.getAttributeValue("vformula"));
						List<UFDouble> list = null;
						if (code1 != null) {
							list = getCalFormulaBO().doCalcStart(code1, name1,
									new ReportBaseVO[] { vo });
						}
						bvos[j].setNtaxprice(list.get(0));
					}
				}
			}
			return bvos;
		}
		return bvos;
	}

	public Map<String, SoctB7VO> getContractMap(String contractid)
			throws BusinessException {
		if (contractMap == null || contractMap.size() == 0) {
			List<SoctB7VO> rules = (List<SoctB7VO>) QueryHelper
					.retrieveByClause(SoctB7VO.class, "pk_soct='" + contractid
							+ "' and isnull(dr,0)=0");
			if (rules != null && rules.size() > 0) {
				for (int i = 0; i < rules.size(); i++) {
					contractMap.put(rules.get(i).getPk_mandex(), rules.get(i));
				}
			}
		}
		return contractMap;
	}

	public void calContractPrice(String contractid, SalepresettleBVO salevo,
			SalepresettleHVO hvo) throws BusinessException {
		getContractMap(contractid);
		if (contractMap.get(salevo.getPk_invmandoc()) != null) {
			SoctB7VO pricerule = contractMap.get(salevo.getPk_invmandoc());

			String pk_soruce = pricerule.getPk_sourceprice();
			String pk_invmandoc = pricerule.getPk_invdex();
			UFBoolean isprice = PuPubVO.getUFBoolean_NullAs(pricerule
					.getIsprice(), new UFBoolean(false));
			// 计价规则
			Integer priceru = PuPubVO.getInteger_NullAs(pricerule
					.getPricerule(), -1);
			// 计价日期类型
			Integer dayrule = PuPubVO.getInteger_NullAs(pricerule.getDayrule(),
					-1);
			// 计价天数
			Integer days = PuPubVO.getInteger_NullAs(pricerule.getDays(), -1);
			// 计价系数
			Integer para = PuPubVO.getInteger_NullAs(pricerule.getPara(), -1);
			UFDouble price = new UFDouble();
			if (priceru == 0) {
				// 旬均价
				if (dayrule == 0) {
					// 发货日
					price = AvgtoVmanth(pk_invmandoc, sendDate, pk_soruce, hvo);
				} else {
					// 结算日
					price = AvgtoVmanth(pk_invmandoc, settleDate, pk_soruce,
							hvo);
				}
			} else if (priceru == 1) {
				// 发货日后n日均价
				if (dayrule == 0) {
					// 发货日
					price = AvgtoForAfterSendDate(pk_invmandoc, sendDate, days,
							pk_soruce, hvo);
				} else {
					// 结算日
					price = AvgtoForAfterSendDate(pk_invmandoc, settleDate,
							days, pk_soruce, hvo);
				}

			} else if (priceru == 2) {
				// 当日价
				if (dayrule == 0) {
					// 发货日
					price = currentSendDate(pk_invmandoc, sendDate, pk_soruce,
							hvo);
				} else {
					// 结算日
					price = currentSendDate(pk_invmandoc, settleDate,
							pk_soruce, hvo);
				}
			} else if (priceru == 3) {
				// 发货日前n日均价
				if (dayrule == 0) {
					// 发货日
					price = AvgtoForBeforeSendDate(pk_invmandoc, sendDate,
							days, pk_soruce, hvo);
				} else {
					// 结算日
					price = AvgtoForBeforeSendDate(pk_invmandoc, settleDate,
							days, pk_soruce, hvo);
				}
			} else if (priceru == 4) {
				// 月均价
				if (dayrule == 0) {
					price = AvgtoForMonthSendDate(pk_invmandoc, sendDate,
							pk_soruce, hvo);
				} else {
					price = AvgtoForMonthSendDate(pk_invmandoc, settleDate,
							pk_soruce, hvo);
				}
			} else if (priceru == 5) {
				// 周均价
				if (dayrule == 0) {
					price = AvgtoForWeekSendDate(pk_invmandoc, sendDate,
							pk_soruce, hvo);
				} else {
					price = AvgtoForWeekSendDate(pk_invmandoc, settleDate,
							pk_soruce, hvo);
				}

			} else {
				// moren
				// 发货日
				price = currentSendDate(pk_invmandoc, sendDate, pk_soruce, hvo);
				para = 1;
			}
			salevo.setNtaxprice(PuPubVO.getUFDouble_NullAsZero(price).multiply(
					para));
		} else {
			// moren
			// 发货日
			UFDouble price = PuPubVO.getUFDouble_NullAsZero(currentSendDate(
					salevo.getPk_invmandoc(), sendDate, "0001XH10000000000BM1",
					hvo));
			salevo.setNtaxprice(price.multiply(1));
		}
	}

	/**
	 * 获得优质优价-品位的主键 这个是根据优质优价方案优先级筛选出来的 pk_billtype vbillstatus 优先级是 int 类型
	 * 数值越小的优先
	 * 
	 * @param qualityplan
	 *            优质优价方案id
	 * @author yangtao
	 * @throws BusinessException
	 * @date 2014-3-31 下午03:53:18
	 */
	private String getqualitygrade(String qualityplan) throws BusinessException {
		@SuppressWarnings("unchecked")
		List<QualityProB2VO> list = (List<QualityProB2VO>) QueryHelper
				.retrieveByClause(QualityProB2VO.class, "pk_qualitypro_h='"
						+ qualityplan + "' and isnull(dr,0)=0");
		QualityProB2VO[] QualityProB2VO = list.toArray(new QualityProB2VO[0]);

		String[] strs = new String[] { "ipriority" };
		int[] ins = new int[] { VOUtil.ASC };
		VOUtil.sort(QualityProB2VO, strs, ins, true);

		return QualityProB2VO[0].getPk_qualitygrade_h();
	}

	/**
	 * 获得优质优价-价格id 这个是根据优质优价方案优先级筛选出来的
	 * 
	 * @param qualityplan
	 * @return
	 * @throws BusinessException
	 * @author yangtao
	 * @date 2014-4-2 下午04:42:30
	 */
	@SuppressWarnings( { "unchecked" })
	private String getqualityPrice(String qualityplan) throws BusinessException {

		List<QualityPorB1VO> list = (List<QualityPorB1VO>) QueryHelper
				.retrieveByClause(QualityPorB1VO.class, "pk_qualitypro_h='"
						+ qualityplan + "' and isnull(dr,0)=0");
		QualityPorB1VO[] QualityPorB1VO = list.toArray(new QualityPorB1VO[0]);

		String[] strs = new String[] { "ipriority" };
		int[] ins = new int[] { VOUtil.ASC };
		VOUtil.sort(QualityPorB1VO, strs, ins, true);

		return QualityPorB1VO[0].getPk_qualityprice_h();
	}

	/**
	 * 判断该品位。在哪个范围,把符合条件的那一条vo返回。
	 * 
	 * @param pk_main
	 * @param QualityGradeBVO
	 * @return
	 * @author yangtao
	 * @date 2014-4-1 上午09:57:37
	 */
	private QualityGradeBVO getscope(UFDouble ngrade,
			QualityGradeBVO[] QualityGradeBVO) {

		double grade = ngrade.toDouble();// 品位

		QualityGradeBVO vo = null;
		for (int i = 0; i < QualityGradeBVO.length; i++) {
			// 上限
			double nupvalue = QualityGradeBVO[i].getNupvalue() == null ? 1000.0
					: QualityGradeBVO[i].getNupvalue().toDouble();
			// 下限
			double nlowvalue = QualityGradeBVO[i].getNlowvalue() == null ? 0
					: QualityGradeBVO[i].getNlowvalue().toDouble();
			// 是否在范围内
			Boolean flog = false;

			// 是否包含下限
			UFBoolean uincludelow = QualityGradeBVO[i].getUincludelow();
			// 是否包含上限
			UFBoolean uincludeup = QualityGradeBVO[i].getUincludeup();
			if (uincludelow == null) {
				uincludelow = new UFBoolean(false);
			}
			if (uincludeup == null) {
				uincludeup = new UFBoolean(false);
			}

			if (uincludelow.booleanValue() && uincludeup.booleanValue()) {
				flog = (nlowvalue <= grade && grade <= nupvalue);

			} else if (uincludelow.booleanValue() == false
					&& uincludeup.booleanValue()) {
				flog = (nlowvalue < grade && grade <= nupvalue);

			} else if (uincludelow.booleanValue()
					&& uincludeup.booleanValue() == false) {
				flog = (nlowvalue <= grade && grade < nupvalue);
			} else if (uincludelow.booleanValue() == false
					&& uincludeup.booleanValue() == false) {
				flog = (nlowvalue < grade && grade < nupvalue);
			}

			if (flog) {
				vo = QualityGradeBVO[i];
				break;
			}
		}
		return vo;

	}

	/**
	 * 品位范围---优质优价价格
	 * 
	 * @param pk_main
	 * @param ngrade
	 * @param QualityPriceBVOS
	 * @return
	 * @author yangtao
	 * @date 2014-4-2 下午05:54:37
	 */

	@SuppressWarnings("unused")
	private QualityPriceBVO getscopeofPrice(String pk_main, UFDouble ngrade,
			QualityPriceBVO[] QualityPriceBVOS) {

		double grade = ngrade.toDouble();// 品位

		QualityPriceBVO vo = null;
		for (int i = 0; i < QualityPriceBVOS.length; i++) {
			if (pk_main.equals(QualityPriceBVOS[i].getPk_invmandoc())) {
				// 上限
				double nupvalue = QualityPriceBVOS[i].getNupvalue().toDouble();
				// 下限
				double nlowvalue = QualityPriceBVOS[i].getNlowvalue()
						.toDouble();
				// 是否在范围内
				Boolean flog = false;

				// 是否包含下限
				UFBoolean uincludelow = QualityPriceBVOS[i].getUincludelow();
				// 是否包含上限
				UFBoolean uincludeup = QualityPriceBVOS[i].getUincludeup();
				if (uincludelow == null) {
					uincludelow = new UFBoolean(false);
				}
				if (uincludeup == null) {
					uincludeup = new UFBoolean(false);
				}

				if (uincludelow.booleanValue() && uincludeup.booleanValue()) {
					flog = (nlowvalue <= grade && grade <= nupvalue);

				} else if (uincludelow.booleanValue() == false
						&& uincludeup.booleanValue()) {
					flog = (nlowvalue <= grade && grade < nupvalue);

				} else if (uincludelow.booleanValue()
						&& uincludeup.booleanValue() == false) {
					flog = (nlowvalue < grade && grade <= nupvalue);
				} else if (uincludelow.booleanValue() == false
						&& uincludeup.booleanValue() == false) {
					flog = (nlowvalue < grade && grade < nupvalue);

				}
				if (flog) {
					vo = QualityPriceBVOS[i];
					break;
				}
			}
		}
		return vo;

	}

	/**
	 * 计算旬均价的方法（用于后台） 这里需要两个参数
	 * 
	 * @param pk_invmandoc
	 *            存货信息
	 * @param pk_sourcemanage
	 *            价格来源信息
	 * @author yangtao
	 * @return
	 * @throws BusinessException
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble currentSendDate(String pk_invmandoc,
			UFDate senddate, String pk_sourcemanage, SalepresettleHVO hvo)
			throws BusinessException {
		// UFDate afdate=senddate;
		// 首先获得当前时间
		String date = senddate.toString().substring(0, 10);
		String sql = " select nprice from xcgl_pricemanage_b "
				+ " where nprice is not null  and isnull(dr,0)=0 "
				+ " and ddate='" + date.toString() + "' "
				+ " and pk_pricemanage_h in "
				+ "(select pk_pricemanage_h from xcgl_pricemanage_h "
				+ " where pk_invmandoc = '" + pk_invmandoc + "'and vyear = '"
				+ date.substring(0, 4) + "' " + " and vsourcemanage = '"
				+ pk_sourcemanage + "' " + " and isnull(dr,0)=0  )";
		// 查询出当前寻的均价
		UFDouble d = PuPubVO.getUFDouble_NullAsZero(QueryHelper.executeQuery(
				sql, new ColumnProcessor()));
		return d;
	}

	/**
	 * 计算旬均价的方法（用于后台） 这里需要两个参数
	 * 
	 * @param pk_invmandoc
	 *            存货信息
	 * @param pk_sourcemanage
	 *            价格来源信息
	 * @author yangtao
	 * @return
	 * @throws BusinessException
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble AvgtoForAfterSendDate(String pk_invmandoc,
			UFDate senddate, Integer days, String pk_sourcemanage,
			SalepresettleHVO hvo) throws BusinessException {
		UFDate afdate = senddate.getDateAfter(days);
		// 首先获得当前时间
		String date = afdate.toString().substring(0, 10);
		String sql = " select avg(nprice) from xcgl_pricemanage_b "
				+ " where nprice is not null and isnull(dr,0)=0 "
				+ " and ddate>='" + senddate.toString() + "' and ddate<='"
				+ afdate.toString() + "' " + " and pk_pricemanage_h in "
				+ "(select pk_pricemanage_h from xcgl_pricemanage_h "
				+ " where pk_invmandoc = '" + pk_invmandoc + "'and vyear = '"
				+ date.substring(0, 4) + "' " + " and vsourcemanage = '"
				+ pk_sourcemanage + "' " + " and isnull(dr,0)=0  )";

		// 查询出当前寻的均价
		UFDouble d = PuPubVO.getUFDouble_NullAsZero(QueryHelper.executeQuery(
				sql, new ColumnProcessor()));
		return d;
	}

	/**
	 * 计算旬均价的方法（用于后台） 这里需要两个参数
	 * 
	 * @param pk_invmandoc
	 *            存货信息
	 * @param pk_sourcemanage
	 *            价格来源信息
	 * @author yangtao
	 * @return
	 * @throws BusinessException
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble AvgtoForBeforeSendDate(String pk_invmandoc,
			UFDate senddate, Integer days, String pk_sourcemanage,
			SalepresettleHVO hvo) throws BusinessException {
		UFDate afdate = senddate.getDateBefore(days);
		// 首先获得当前时间
		String date = afdate.toString().substring(0, 10);
		// 得到当前月
		String sql = " select avg(nprice) from xcgl_pricemanage_b "
				+ " where nprice is not null and isnull(dr,0)=0 "
				+ " and ddate>='" + afdate.toString() + "' and ddate<='"
				+ senddate.toString() + "' " + " and pk_pricemanage_h in "
				+ "(select pk_pricemanage_h from xcgl_pricemanage_h "
				+ " where pk_invmandoc = '" + pk_invmandoc + "'and vyear = '"
				+ date.substring(0, 4) + "' " + " and vsourcemanage = '"
				+ pk_sourcemanage + "' " + " and isnull(dr,0)=0  )";
		// 查询出当前寻的均价
		UFDouble d = PuPubVO.getUFDouble_NullAsZero(QueryHelper.executeQuery(
				sql, new ColumnProcessor()));
		return d;
	}

	/**
	 * 计算旬均价的方法（用于后台） 这里需要两个参数
	 * 
	 * @param pk_invmandoc
	 *            存货信息
	 * @param pk_sourcemanage
	 *            价格来源信息
	 * @author yangtao
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble AvgtoForWeekSendDate(String pk_invmandoc,
			UFDate senddate, String pk_sourcemanage, SalepresettleHVO hvo)
			throws BusinessException {
		// 首先获得当前时间
		String date = senddate.toString().substring(0, 10);

		String sdate = ZmPubTool.getSartdateOfWeek(date);

		String edate = ZmPubTool.getEnddateOfweek(date);

		// 得到当前月
		String sql = " select avg(nprice) from xcgl_pricemanage_b "
				+ " where nprice is not null and isnull(dr,0)=0 "
				+ " and ddate>='" + sdate.toString() + "' and ddate<='"
				+ edate.toString() + "' " + " and pk_pricemanage_h in "
				+ "(select pk_pricemanage_h from xcgl_pricemanage_h "
				+ " where pk_invmandoc = '" + pk_invmandoc + "'and vyear = '"
				+ date.substring(0, 4) + "' " + " and vsourcemanage = '"
				+ pk_sourcemanage + "' " + " and isnull(dr,0)=0  )";
		// 查询出当前寻的均价
		UFDouble d = PuPubVO.getUFDouble_NullAsZero(QueryHelper.executeQuery(
				sql, new ColumnProcessor()));
		return d;
	}

	/**
	 * 计算旬均价的方法（用于后台） 这里需要两个参数
	 * 
	 * @param pk_invmandoc
	 *            存货信息sssssssaz
	 * @param pk_sourcemanage
	 *            价格来源信息
	 * @author yangtao
	 * @return
	 * @throws BusinessException
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble AvgtoForMonthSendDate(String pk_invmandoc,
			UFDate senddate, String pk_sourcemanage, SalepresettleHVO hvo)
			throws BusinessException {
		// 首先获得当前时间
		String date = senddate.toString().substring(0, 10);

		String sdate = date.substring(0, 7) + "-01";

		UFDate sdate1 = new UFDate(sdate);

		int days = senddate.getDaysMonth();

		UFDate endate = sdate1.getDateAfter(days - 1);

		// 得到当前月
		String sql = " select avg(nprice) from xcgl_pricemanage_b "
				+ " where nprice is not null and isnull(dr,0)=0 "
				+ " and ddate>='" + sdate.toString() + "' and ddate<='"
				+ endate.toString() + "' " + " and pk_pricemanage_h in "
				+ "(select pk_pricemanage_h from xcgl_pricemanage_h "
				+ " where pk_invmandoc = '" + pk_invmandoc + "'and vyear = '"
				+ date.substring(0, 4) + "' " + " and vsourcemanage = '"
				+ pk_sourcemanage + "' " + " and isnull(dr,0)=0  )";
		// 查询出当前寻的均价
		UFDouble d = PuPubVO.getUFDouble_NullAsZero(QueryHelper.executeQuery(
				sql, new ColumnProcessor()));
		return d;
	}

	/**
	 * 计算旬均价的方法（用于后台） 这里需要两个参数
	 * 
	 * @param pk_invmandoc
	 *            存货信息
	 * @param pk_sourcemanage
	 *            价格来源信息
	 * @author yangtao
	 * @return
	 * @throws BusinessException
	 * @date 2014-3-28 上午10:29:06
	 */
	public static UFDouble AvgtoVmanth(String pk_invmandoc, UFDate senddate,
			String pk_sourcemanage, SalepresettleHVO hvo)
			throws BusinessException {
		// 首先获得当前时间
		String date = senddate.toString().substring(0, 10);
		// 得到当前月
		Integer day = Integer.parseInt(date.substring(8, 10));
		String wheresql = " and isnull(dr,0)=0 ";
		// 这里有3种情况。当前处于什么月段就查询什么月段的价格。。。。。
		if (day > 0 && day <= 10) {
			wheresql += " and vmanth ='上旬'";
		} else if (day > 10 && day <= 20) {
			wheresql += " and vmanth ='中旬'";
		} else if (day > 20 && day <= 31) {
			wheresql += " and vmanth ='下旬'";
		}
		// wheresql+=" and ddate<='"+date+"'";
		String sql = " select avg(nprice) from xcgl_pricemanage_b "
				+ " where nprice is not null and pk_pricemanage_h in "
				+ "(select pk_pricemanage_h from xcgl_pricemanage_h "
				+ " where pk_invmandoc = '" + pk_invmandoc + "'and vyear = '"
				+ date.substring(0, 4) + "' " + " and vmonth = '"
				+ date.substring(5, 7) + "'and vsourcemanage = '"
				+ pk_sourcemanage + "' " + " and isnull(dr,0)=0 )";

		sql += wheresql;
		// 查询出当前寻的均价
		UFDouble d = PuPubVO.getUFDouble_NullAsZero(QueryHelper.executeQuery(
				sql, new ColumnProcessor()));
		return d;
	}
}
