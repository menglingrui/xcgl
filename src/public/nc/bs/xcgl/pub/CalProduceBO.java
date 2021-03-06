package nc.bs.xcgl.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nc.bs.zmpub.formula.calc.DoCalc;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.labindexset.LabIndexSetBVO;
import nc.vo.xcgl.produceset.ProductSetBB1VO;
import nc.vo.xcgl.produceset.ProductSetBB2VO;
import nc.vo.xcgl.pub.bill.CalYieldNumVO;
import nc.vo.xcgl.pub.bill.CalYieldVO;
import nc.vo.xcgl.pub.bill.IndexParaVO;
import nc.vo.xcgl.pub.bill.IndexResultParaVO;
import nc.vo.xcgl.pub.bill.ProSetEnum;
import nc.vo.xcgl.pub.bill.ProSetFormulaVO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.bill.PubGeneralBVO;
import nc.vo.xcgl.pub.bill.PubSampleBVO;
import nc.vo.xcgl.pub.bill.RepeatSelFlorVO;
import nc.vo.xcgl.pub.bill.XCHYBillVO;
import nc.vo.xcgl.pub.helper.IndexFineHeler;
import nc.vo.xcgl.pub.helper.IndexResultHeler;
import nc.vo.xcgl.pub.helper.ProduceSetHelper;
import nc.vo.xcgl.pub.tool.XcPubTool;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * 生产加工产量计算
 * 
 * @author mlr
 */
public class CalProduceBO {
	/**
	 * 顶层topMap 主要用于构建用于计算的数据和存放计算数据 结构为树形结构、有一定的解析规则
	 */
	public  Map<String, Map<ProSetEnum, List<CalYieldVO>>> topmap = new HashMap<String, Map<ProSetEnum, List<CalYieldVO>>>();
	/**
	 * 矿石加工数量计算map key=topMapKey 嵌套map的key=calMapKey
	 */
	public  Map<String, Map<String, UFDouble>> calOutNumMap = new HashMap<String, Map<String, UFDouble>>();
	/**
	 * 矿石、精矿、尾矿品位计算map key=topMapKey 嵌套map的key=calMapKey
	 */
	public  Map<String, Map<String, List<PubSampleBVO>>> calGradeNumMap = new HashMap<String, Map<String, List<PubSampleBVO>>>();
	
	/**
	 * 矿石、精矿、尾矿品位计算map key=topMapKey 嵌套map的key=calMapKey  recselect
	 */
	public  Map<String, Map<String, List<PubSampleBVO>>> calGradeNumMap1 = new HashMap<String, Map<String, List<PubSampleBVO>>>();
	/**
	 * 公式计算结果信息
	 */
	public  Map<String, List<ProSetFormulaVO>> calFormulaResults = new HashMap<String, List<ProSetFormulaVO>>();
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
	/**
	 * 生产加工计算精粉产量 返回topMap mlr
	 * 
	 * @return
	 */
	public  Map<String, Map<ProSetEnum, List<CalYieldVO>>> calPowderYield(
			ProSetParaVO[] paras) throws BusinessException {
		/**
		 * 初始化计算topMap
		 */
		createTopMap(paras);
		/**
		 * 计算topMap
		 */
		calTopMap(paras);
		return topmap;
	}

	/**
	 * 生产加工计算精粉产量 返回公式结果 mlr
	 * 
	 * @return
	 */
	public  Map<String, List<ProSetFormulaVO>> calPowderYield1(
			ProSetParaVO[] paras) throws BusinessException {
		/**
		 * 初始化计算topMap
		 */
		createTopMap(paras);
		/**
		 * 计算topMap
		 */
		calTopMap(paras);
		return calFormulaResults;
	}

	/**
	 * 计算topmap
	 * 
	 * @param paras
	 * @throws BusinessException
	 */
	public  void calTopMap(ProSetParaVO[] paras) throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		/**
		 * 创建加工数量计算map
		 */
		createCalOutNumMap(paras);
		/**
		 * 创建品位计算map
		 */
		createCalGradeNumMap(paras);
		/**
		 * 分配加工数量到topmap
		 */
		doCostCalOutNumToTopMap(paras);
		/**
		 * 分配品位数量到topmap
		 */
		doCostCalGradeNumToTopMap(paras);
		/**
		 * 分配品位数量到topmap
		 */
		doCostCalGradeNumToTopMap1(paras);
		/**
		 * 执行top中的公式
		 */
		exeTopMapFormulas(paras);
	}

	/**
	 * 执行top中的公式运算
	 * 
	 * @author mlr
	 * @param paras
	 * @throws BusinessException
	 */
	public  void exeTopMapFormulas(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		for (int i = 0; i < paras.length; i++) {
			List<ProSetFormulaVO> list = exeTopMapFormulas(paras[i]);
			calFormulaResults.put(XcPubTool.getMapKey(paras[i]), list);
		}
		doCostFormulaResultsToTopMap(paras);
	}

	/**
	 * 分配公式执行结果到topMap
	 * 
	 * @param paras
	 * @throws BusinessException
	 */
	public  void doCostFormulaResultsToTopMap(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		for (int i = 0; i < paras.length; i++) {
			ProSetParaVO para = paras[i];
			Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(XcPubTool
					.getMapKey(para));
			List<CalYieldVO> plist=new ArrayList<CalYieldVO>();
			List<CalYieldVO> aplist = map.get(ProSetEnum.Apowder);
			List<CalYieldVO> bplist = map.get(ProSetEnum.Bpowder);
			List<CalYieldVO> cplist = map.get(ProSetEnum.Cpowder);
			if (aplist != null && aplist.size() > 0) {
				plist.addAll(aplist);
			}
			if (bplist != null && bplist.size() > 0) {
				plist.addAll(bplist);
			}
			if (cplist != null && cplist.size() > 0) {
				plist.addAll(cplist);
			}
			if (plist != null && plist.size() > 0) {
				for (int j = 0; j < plist.size(); j++) {
					CalYieldVO calvo = plist.get(j);
					CalYieldNumVO cvo = calvo.getMainnumvo();
					setFormulaExeValue(para, cvo,calvo.getOutsetvo());
					setFormulaExeValue(para, calvo.getNumList(),calvo.getOutsetvo());
					setRepeatFormulaExeValue(para,calvo);
				}
			}
            
			List<CalYieldVO> clist=new ArrayList<CalYieldVO>();
			List<CalYieldVO> aclist = map.get(ProSetEnum.Atails);
			List<CalYieldVO> bclist = map.get(ProSetEnum.Btails);
			List<CalYieldVO> cclist = map.get(ProSetEnum.Ctails);
			if (aclist != null && aclist.size() > 0) {
				clist.addAll(aclist);
			}
			if (bclist != null && bclist.size() > 0) {
				clist.addAll(bclist);
			}
			if (cclist != null && cclist.size() > 0) {
				clist.addAll(cclist);
			}
			if (clist != null && clist.size() > 0) {
				for (int j = 0; j < clist.size(); j++) {
					CalYieldVO calvo = clist.get(j);
					CalYieldNumVO cvo = calvo.getMainnumvo();
					setFormulaExeValue(para, cvo,calvo.getOutsetvo());
					setFormulaExeValue(para, calvo.getNumList(),calvo.getOutsetvo());
				}
			}
		}
	}
    /**
     * set repeat select powder formula value
     * @param para
     * @param calvo
     * @throws BusinessException 
     */
	public void setRepeatFormulaExeValue(ProSetParaVO para, CalYieldVO cvo) throws BusinessException {

		if (para == null || cvo == null) {
			return;
		}
		List<CalYieldVO>   recals= cvo.getOutsetvos();
		if(recals==null || recals.size()==0){
			return;
		}
		
		Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(XcPubTool
				.getMapKey(para));
		for(int i=0;i<recals.size();i++){
			CalYieldVO revo=recals.get(i);
			List<CalYieldVO> plist=new ArrayList<CalYieldVO>();
			List<CalYieldVO> aplist = map.get(ProSetEnum.Apowder);
			List<CalYieldVO> bplist = map.get(ProSetEnum.Bpowder);
			List<CalYieldVO> cplist = map.get(ProSetEnum.Cpowder);
			if (aplist != null && aplist.size() > 0) {
				plist.addAll(aplist);
			}
			if (bplist != null && bplist.size() > 0) {
				plist.addAll(bplist);
			}
			if (cplist != null && cplist.size() > 0) {
				plist.addAll(cplist);
			}
			if (plist != null && plist.size() > 0) {
				for (int j = 0; j < plist.size(); j++) {
					CalYieldVO calvo = plist.get(j);
					CalYieldNumVO cvo1 = revo.getMainnumvo();
					setRepeatFormulaExeValue(para,cvo1,revo.getOutsetvo());
					setRepeatFormulaExeValue(para,revo.getNumList(),revo.getOutsetvo());
				}
			}			
		}
	}
	
	public void setRepeatFormulaExeValue(ProSetParaVO para,
			List<CalYieldNumVO> list, ProductSetBB2VO outsetvo) throws BusinessException {
		if (list == null || list.size() == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				setRepeatFormulaExeValue(para, list.get(i),outsetvo);
			}
		}	
	}
	public void setRepeatFormulaExeValue(ProSetParaVO para, CalYieldNumVO cvo, ProductSetBB2VO bb2vo) throws BusinessException {

		if (para == null || cvo == null) {
			return;
		}
		List<ProSetFormulaVO> forlist = calFormulaResults.get(XcPubTool
				.getMapKey(para));
		if(forlist==null || forlist.size()==0){
			return ;
		}
		List<ProSetFormulaVO> prolist = getFormulaVOByIndex(cvo
				.getPk_invmandoc(), forlist);
		if (prolist != null && prolist.size() > 0) {
			ProSetFormulaVO fvo = prolist.get(0);
			
			List<RepeatSelFlorVO> relist=fvo.getReplist();	
		    if(relist==null || relist.size()==0){
		    	return;
		    }
		    for(int i=0;i<relist.size();i++){
		    	RepeatSelFlorVO rvo=relist.get(i);
		    	if(PuPubVO.getUFBoolean_NullAs(bb2vo.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
		    		if(rvo.getPk_reinvmandoc()!=null && rvo.getPk_reinvmandoc().length()>0){
		    			/**
		    			 * 设置精粉
		    			 */
		    			cvo.setNrecoveryrate(rvo.getNrerate());
		    			cvo.setNflouryield(rvo.getNreflour());
		    			cvo.setNmetal(rvo.getNremetal());
		    			/**
		    			 * set raw infor
		    			 */
		    			cvo.setNrawore(rvo.getRawinfor().getNflour());
		    		}
		    	}else{
		    		if(rvo.getPk_reinvmandoc1()!=null && rvo.getPk_reinvmandoc1().length()>0){
		    			/**
		    			 * 设置精粉
		    			 */
		    			cvo.setNrecoveryrate1(rvo.getNrerate1());
		    			cvo.setNflouryield1(rvo.getNreflour1());
		    			cvo.setNmetal1(rvo.getNremetal1());
		    			/**
		    			 * set raw infor
		    			 */
		    			cvo.setNrawore(rvo.getRawinfor().getNflour());
		    		}
		    	}
		    }
		}				
	}
	public  void setFormulaTailExeValue(ProSetParaVO para,
			List<CalYieldNumVO> list) throws BusinessException {
		if (list == null || list.size() == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				setFormulaTailExeValue(para, list.get(i));
			}
		}
	}

	public  void setFormulaTailExeValue(ProSetParaVO para,
			CalYieldNumVO cvo) throws BusinessException {
		if (para == null || cvo == null) {
			return;
		}
		List<ProSetFormulaVO> forlist = calFormulaResults.get(XcPubTool
				.getMapKey(para));
		List<ProSetFormulaVO> prolist = getFormulaVOByIndex(cvo
				.getPk_invmandoc(), forlist);
		if (prolist != null && prolist.size() > 0) {
			ProSetFormulaVO fvo = prolist.get(0);
			cvo.setNrecoveryrate1(fvo.getNrate1());
			cvo.setNflouryield1(fvo.getNflour1());
			cvo.setNmetal1(fvo.getNmetal1());
		}

	}

	public  void setFormulaExeValue(ProSetParaVO para,
			List<CalYieldNumVO> list, ProductSetBB2VO outvo) throws BusinessException {
		if (list == null || list.size() == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				setFormulaExeValue(para, list.get(i),outvo);
			}
		}
	}
	public  void setFormulaExeValue(ProSetParaVO para, CalYieldNumVO cvo,ProductSetBB2VO outvo)
			throws BusinessException {
		if (para == null || cvo == null) {
			return;
		}
		List<ProSetFormulaVO> forlist = calFormulaResults.get(XcPubTool
				.getMapKey(para));
		List<ProSetFormulaVO> prolist = getFormulaVOByIndex(cvo
				.getPk_invmandoc(), forlist);
		if (prolist != null && prolist.size() > 0) {
			
			for(int i=0;i<prolist.size();i++){
				if(isPowerEql(prolist.get(i),outvo)){
					ProSetFormulaVO fvo = prolist.get(i);
					/**
					 * 设置精粉
					 */
					cvo.setNrecoveryrate(fvo.getNrate());
					cvo.setNflouryield(fvo.getNflour());
					cvo.setNmetal(fvo.getNmetal());
					/**
					 * 设置尾矿
					 */
					cvo.setNrecoveryrate1(fvo.getNrate1());
					cvo.setNflouryield1(fvo.getNflour1());
					cvo.setNmetal1(fvo.getNmetal1());	
				}

			}
		}

	}

	public boolean isPowerEql(ProSetFormulaVO provo,
			ProductSetBB2VO outvo) {
		if(provo.getInvtype()==ProSetEnum.Apowder){
			if(outvo.getPk_invmandoc().equals(provo.getPk_apowdermanid())
					|| outvo.getPk_invmandoc().equals(provo.getPk_atailsmanid())){
				return true;
			}
		}else if(provo.getInvtype()==ProSetEnum.Bpowder){
			if(outvo.getPk_invmandoc().equals(provo.getPk_bpowdermanid())
				   || outvo.getPk_invmandoc().equals(provo.getPk_btailsmanid())){
				return true;
			}			
		}else if(provo.getInvtype()==ProSetEnum.Cpowder){
			if(outvo.getPk_invmandoc().equals(provo.getPk_cpowdermanid())
					|| outvo.getPk_invmandoc().equals(provo.getPk_ctailsmanid())){
				return true;
			}
		}	
		return false;
	}
	/**
	 * 分配品位数量到topmap
	 * 
	 * @author mlr
	 * @param paras
	 * @throws BusinessException
	 */
	public  void doCostCalGradeNumToTopMap(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		for (int n = 0; n < paras.length; n++) {
			ProSetParaVO para = paras[n];
			String topkey = XcPubTool.getMapKey(para);
			Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(topkey);
			if (map == null || map.size() == 0) {
				return;
			}
			Map<String, List<PubSampleBVO>> calgmap = calGradeNumMap
					.get(topkey);
			if (calgmap == null || calgmap.size() == 0) {
				continue;
			}
			for (String calkey : calgmap.keySet()) {
				List<PubSampleBVO> vlist = calgmap.get(calkey);
				List<CalYieldNumVO> nlist = getCalYieldNumVO(calkey, para);
				setIndexValue(vlist, nlist);
			}
		}
	}

	/**
	 * 分配品位数量到topmap
	 * @author mlr
	 * @param paras
	 * @throws BusinessException
	 */
	public  void doCostCalGradeNumToTopMap1(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		for (int n = 0; n < paras.length; n++) {
			ProSetParaVO para = paras[n];
			String topkey = XcPubTool.getMapKey(para);
			Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(topkey);
			if (map == null || map.size() == 0) {
				return;
			}
			Map<String, List<PubSampleBVO>> calgmap = calGradeNumMap1
					.get(topkey);
			if (calgmap == null || calgmap.size() == 0) {
				continue;
			}
			for (String calkey : calgmap.keySet()) {
				List<PubSampleBVO> vlist = calgmap.get(calkey);
				List<CalYieldNumVO> nlist = getCalYieldNumVO1(calkey, para);
				setIndexValue(vlist,nlist);
			}
		}
	}
	/**
	 * 
	 * 将vlist中的品位值信息设置到计算vo中 vlist和nlist的topKey一样
	 * 
	 * @param vlist
	 * @param nlist
	 */
	public  void setIndexValue(List<PubSampleBVO> vlist,
			List<CalYieldNumVO> nlist) {
		if (nlist == null || nlist.size() == 0) {
			return;
		}
		if (vlist == null || vlist.size() == 0) {
			return;
		}
		for (int i = 0; i < nlist.size(); i++) {
			CalYieldNumVO nvo = nlist.get(i);
			for (int j = 0; j < vlist.size(); j++) {
				if (nvo.getPk_invmandoc().equals(vlist.get(j).getPk_invmandoc())) {
					nvo.setNgrade((PuPubVO.getUFDouble_NullAsZero(vlist.get(j).getNgrade())).div(new UFDouble(100)));
					break;
				}
			}
		}
	}
	/**
	 * 根据计算key即calkey从topMap中获得指标信息
	 * 
	 * @param calkey
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldNumVO> getCalYieldNumVO1(String calkey,
			ProSetParaVO para) throws BusinessException {
		String topkey = XcPubTool.getMapKey(para);

		Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(topkey);

		if (map == null || map.size() == 0) {
			return null;
		}
		List<CalYieldNumVO> nlist = new ArrayList<CalYieldNumVO>();
		for (ProSetEnum key : map.keySet()) {
			List<CalYieldVO> ylist = map.get(key);
			if (ylist == null || ylist.size() == 0) {
				continue;
			}
			for (int i = 0; i < ylist.size(); i++) {
				CalYieldVO cvo = ylist.get(i);
				if (cvo == null) {
					continue;
				}
				/**
				 * 分配二次选矿的品位
				 */
				if(cvo.getOutsetvos()!=null &&cvo.getOutsetvos().size()>0){
					for(int j=0;j<cvo.getOutsetvos().size();j++){
						CalYieldVO recvo=cvo.getOutsetvos().get(j);

						String pk_invmandoc = recvo.getOutsetvo().getPk_invmandoc();
						String ncalkey = XcPubTool.getCalMapKey(para, pk_invmandoc);
						if (ncalkey.equals(calkey)) {
							List<CalYieldNumVO> yilist = getIndexVos(recvo);
							if(yilist!=null && yilist.size()>0){
								for(int k=0;k<yilist.size();k++){
									yilist.get(k).setIsresel(UFBoolean.TRUE);
								}
							}
							nlist.addAll(yilist);
						}
					
					}
				}
			}
		}
		return nlist;
	}
	/**
	 * 根据计算key即calkey从topMap中获得指标信息
	 * 
	 * @param calkey
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldNumVO> getCalYieldNumVO(String calkey,
			ProSetParaVO para) throws BusinessException {
		String topkey = XcPubTool.getMapKey(para);

		Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(topkey);

		if (map == null || map.size() == 0) {
			return null;
		}
		List<CalYieldNumVO> nlist = new ArrayList<CalYieldNumVO>();
		for (ProSetEnum key : map.keySet()) {
			List<CalYieldVO> ylist = map.get(key);
			if (ylist == null || ylist.size() == 0) {
				continue;
			}
			for (int i = 0; i < ylist.size(); i++) {
				CalYieldVO cvo = ylist.get(i);
				if (cvo == null) {
					continue;
				}
				if (cvo.getInsetvo() != null) {
					String pk_invmandoc = cvo.getInsetvo().getPk_invmandoc();
					String ncalkey = XcPubTool.getCalMapKey(para, pk_invmandoc);
					if (ncalkey.equals(calkey)) {
						List<CalYieldNumVO> yilist = getIndexVos(cvo);
						nlist.addAll(yilist);
					}
				} else if (cvo.getOutsetvo() != null) {
					String pk_invmandoc = cvo.getOutsetvo().getPk_invmandoc();
					String ncalkey = XcPubTool.getCalMapKey(para, pk_invmandoc);
					if (ncalkey.equals(calkey)) {
						List<CalYieldNumVO> yilist = getIndexVos(cvo);
						nlist.addAll(yilist);
					}
				}
				/**
				 * 分配二次选矿的品位
				 */
				if(cvo.getOutsetvos()!=null &&cvo.getOutsetvos().size()>0){
					for(int j=0;j<cvo.getOutsetvos().size();j++){
						CalYieldVO recvo=cvo.getOutsetvos().get(j);

						String pk_invmandoc = recvo.getOutsetvo().getPk_invmandoc();
						String ncalkey = XcPubTool.getCalMapKey(para, pk_invmandoc);
						if (ncalkey.equals(calkey)) {
							List<CalYieldNumVO> yilist = getIndexVos(recvo);
							if(yilist!=null && yilist.size()>0){
								for(int k=0;k<yilist.size();k++){
									yilist.get(k).setIsresel(UFBoolean.TRUE);
								}
							}
							nlist.addAll(yilist);
						}
					
					}
				}
			}
		}
		return nlist;
	}

	/**
	 * 从计算vo获取指标信息
	 * 
	 * @param cvo
	 * @return
	 */
	private  List<CalYieldNumVO> getIndexVos(CalYieldVO cvo) {
		List<CalYieldNumVO> nlist = new ArrayList<CalYieldNumVO>();
		if (cvo.getMainnumvo() != null) {
			nlist.add(cvo.getMainnumvo());
		}
		if (cvo.getNumList() != null && cvo.getNumList().size() > 0) {
			nlist.addAll(cvo.getNumList());
		}
		if (cvo.getOthernumList() != null && cvo.getOthernumList().size() > 0) {
			nlist.addAll(cvo.getOthernumList());
		}
		return nlist;
	}

	/**
	 * 创建品位计算map 主要用于归集矿石、精粉、尾矿的化验品位
	 * 
	 * @param paras
	 * @throws BusinessException
	 */
	public  void createCalGradeNumMap(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		/**
		 * 清空品位计算计算map
		 */
		calGradeNumMap.clear();
		calGradeNumMap1.clear();
		/**
		 * 重新计算map
		 */
		for (int i = 0; i < paras.length; i++) {
			Map<String, List<PubSampleBVO>> calmap = calGradeNum(paras[i],UFBoolean.FALSE,UFBoolean.FALSE);
			Map<String, List<PubSampleBVO>> calmap1 = calGradeNum(paras[i],UFBoolean.FALSE,UFBoolean.TRUE);
			calGradeNumMap.put(XcPubTool.getMapKey(paras[i]), calmap);
			calGradeNumMap1.put(XcPubTool.getMapKey(paras[i]), calmap1);
		}
	}

	/**
	 * 根据参数创建计算map 主要归集计算矿石加工数量 calOutNumMap
	 * 
	 * @param paras
	 * @throws BusinessException
	 */
	public  void createCalOutNumMap(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		/**
		 * 清空矿石加工量计算map
		 */
		calOutNumMap.clear();
		/**
		 * 重新计算map
		 */
		for (int i = 0; i < paras.length; i++) {
			Map<String, UFDouble> calmap = calOutNum(paras[i]);
			calOutNumMap.put(XcPubTool.getMapKey(paras[i]), calmap);
		}
	}

	/**
	 * 执行公式计算回收率、产量、金属量
	 * 
	 * @param para
	 * @param topmap
	 * @throws BusinessException
	 */
	public  List<ProSetFormulaVO> exeTopMapFormulas(ProSetParaVO para)
			throws BusinessException {
		if (topmap == null || topmap.size() == 0) {
			return null;
		}
		/**
		 * 初始化计算公式
		 */
		List<ProSetFormulaVO> initlist=null;
		try {
			initlist = getInitFormulaVO(para);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		/**
		 * 执行公式计算
		 */
		exeFormulas(para,initlist);
		return initlist;
	}

	/**
	 * 生产流程设置公式
	 * 
	 * @param initlist
	 * @throws Exception
	 */
	public  void exeFormulas(ProSetParaVO para,List<ProSetFormulaVO> initlist)
			throws BusinessException {
		sortByEnumAndIndex(initlist);
		ReportBaseVO[] vos = getReportBaseVO(initlist);
		if (vos == null || vos.length == 0) {
			return;
		}
		
		try {
			int count=vos.length*6;
			for(int i=0;i<count;i++){
				exePowerFormula(para,vos,initlist);
				exeTailFormula(para,vos,initlist);
			}		
			exeRepeatFormulas(para,initlist);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
	
    private void exeTailFormula(ProSetParaVO para, ReportBaseVO[] vos,
			List<ProSetFormulaVO> initlist) throws Exception {
		for (int i = 0; i < vos.length; i++) {
			ReportBaseVO vo = vos[i];
		
			String code3 = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("vformulacode33"));
			String name3 = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("vformulaname33"));
			if (code3 != null) {
				List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code3,
						name3, new ReportBaseVO[] { vo });
				UFDouble nflour1 = PuPubVO.getUFDouble_NullAsZero(list1
						.get(0));
				vo.setAttributeValue("nflour1", nflour1);
				initlist.get(i).setNflour1(nflour1);
			}
			String code1 = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("vformulacode11"));
			String name1 = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("vformulaname11"));
			if (code1 != null) {
				List<UFDouble> list1;
				list1 = getCalFormulaBO().doCalcStart(code1, name1,
						new ReportBaseVO[] { vo });
				UFDouble nrate1 = PuPubVO.getUFDouble_NullAsZero(list1
						.get(0));
				vo.setAttributeValue("nrate1", nrate1);
				initlist.get(i).setNrate1(nrate1);
			}
			String code2 = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("vformulacode22"));
			String name2 = PuPubVO.getString_TrimZeroLenAsNull(vo
					.getAttributeValue("vformulaname22"));
			if (code2 != null) {
				List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code2,
						name2, new ReportBaseVO[] { vo });
				UFDouble nmetal1 = PuPubVO.getUFDouble_NullAsZero(list1
						.get(0));
				vo.setAttributeValue("nmetal1", nmetal1);
				initlist.get(i).setNmetal1(nmetal1);
				String pk_invdex=initlist.get(i).getPk_invindex();
				if(initlist.get(i).getInvtype()==ProSetEnum.Apowder ){	
					
		//			for(int n=0;n<initlist.size();n++){
		//				if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
					//	initlist.get(n).setNoutput1(nmetal);
						vos[i].setAttributeValue("nmetal121", nmetal1);
		//			}
				}
				if(initlist.get(i).getInvtype()==ProSetEnum.Bpowder ){
		//			for(int n=0;n<initlist.size();n++){
					//	initlist.get(n).setNoutput2(nmetal);
		//				if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
						vos[i].setAttributeValue("nmetal122", nmetal1);
		//			}
				}
				if(initlist.get(i).getInvtype()==ProSetEnum.Cpowder ){
	//				for(int n=0;n<initlist.size();n++){
	//					if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
					//	initlist.get(n).setNoutput3(nmetal);
						vos[i].setAttributeValue("nmetal123", nmetal1);
//					}
				}
			
			}
			
			
		
		}
		
	}
	private void exePowerFormula(ProSetParaVO para, ReportBaseVO[] vos,
			List<ProSetFormulaVO> initlist) throws Exception {		
    	for (int i = 0; i < vos.length; i++) {
				ReportBaseVO vo = vos[i];
	
				String code3 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vformulacode3"));
				String name3 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vformulaname3"));
				if (code3 != null) {
					List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code3,
							name3, new ReportBaseVO[] { vo });
					UFDouble nflour = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nflour", nflour);
					initlist.get(i).setNflour(nflour);
					if(initlist.get(i).getInvtype()==ProSetEnum.Apowder &&PuPubVO.getInteger_NullAs(initlist.get(i).getItype(), -1)==0){						
						for(int n=0;n<initlist.size();n++){
							initlist.get(n).setNoutput1(nflour);
							vos[n].setAttributeValue("noutput1", nflour);
						}
					}
					if(initlist.get(i).getInvtype()==ProSetEnum.Bpowder &&PuPubVO.getInteger_NullAs(initlist.get(i).getItype(), -1)==0){
						for(int n=0;n<initlist.size();n++){
							initlist.get(n).setNoutput2(nflour);
							vos[n].setAttributeValue("noutput2", nflour);
						}
					}
					if(initlist.get(i).getInvtype()==ProSetEnum.Cpowder &&PuPubVO.getInteger_NullAs(initlist.get(i).getItype(), -1)==0){
						for(int n=0;n<initlist.size();n++){
							initlist.get(n).setNoutput3(nflour);
							vos[n].setAttributeValue("noutput3", nflour);
						}
					}
				}
				String code1 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vformulacode1"));
				String name1 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vformulaname1"));
				if (code1 != null) {
					List<UFDouble> list1;
					list1 = getCalFormulaBO().doCalcStart(code1, name1,
							new ReportBaseVO[] { vo });
					UFDouble nrate = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nrate", nrate);
					initlist.get(i).setNrate(nrate);
					String pk_invdex=initlist.get(i).getPk_invindex();
					
							
					if(initlist.get(i).getInvtype()==ProSetEnum.Apowder ){						
//						for(int n=0;n<initlist.size();n++){
//							if(pk_invdex.equals(initlist.get(n).getPk_invindex())&&initlist.get(n).getInvtype()==ProSetEnum.Apowder )
						//	initlist.get(n).setNoutput1(nmetal);
							vos[i].setAttributeValue("nrate1", nrate);
//						}
					}
					if(initlist.get(i).getInvtype()==ProSetEnum.Bpowder ){
					//	for(int n=0;n<initlist.size();n++){
	//						if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
						//	initlist.get(n).setNoutput2(nmetal);
							vos[i].setAttributeValue("nrate2", nrate);
//						}
					}
					if(initlist.get(i).getInvtype()==ProSetEnum.Cpowder ){
//						for(int n=0;n<initlist.size();n++){
//							if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
						//	initlist.get(n).setNoutput3(nmetal);
							vos[i].setAttributeValue("nrate3", nrate);
//						}
					}
				}
				String code2 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vformulacode2"));
				String name2 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vformulaname2"));
				if (code2 != null) {
					List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code2,
							name2, new ReportBaseVO[] { vo });
					UFDouble nmetal = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nmetal", nmetal);
					initlist.get(i).setNmetal(nmetal);
					String pk_invdex=initlist.get(i).getPk_invindex();
					if(initlist.get(i).getInvtype()==ProSetEnum.Apowder ){						
//						for(int n=0;n<initlist.size();n++){
//						//	initlist.get(n).setNoutput1(nmetal);
//							if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
							vos[i].setAttributeValue("nmetal11", nmetal);
//						}
					}
					if(initlist.get(i).getInvtype()==ProSetEnum.Bpowder ){
//						for(int n=0;n<initlist.size();n++){
//							if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
						//	initlist.get(n).setNoutput2(nmetal);
							vos[i].setAttributeValue("nmetal112", nmetal);
//						}
					}
					if(initlist.get(i).getInvtype()==ProSetEnum.Cpowder){
//						for(int n=0;n<initlist.size();n++){
//							if(pk_invdex.equals(initlist.get(n).getPk_invindex()))
						//	initlist.get(n).setNoutput3(nmetal);
							vos[i].setAttributeValue("nmetal113", nmetal);
//						}
					}
				}
			}}
	public void sortByEnumAndIndex(List<ProSetFormulaVO> initlist) throws BusinessException {
    	List<ProSetFormulaVO> alist=new ArrayList<ProSetFormulaVO>();
    	
    	List<ProSetFormulaVO> ablist=new ArrayList<ProSetFormulaVO>();
    	
    	List<ProSetFormulaVO> blist=new ArrayList<ProSetFormulaVO>();
    	
    	List<ProSetFormulaVO> bblist=new ArrayList<ProSetFormulaVO>();
    	
    	List<ProSetFormulaVO> clist=new ArrayList<ProSetFormulaVO>();
    	
    	List<ProSetFormulaVO> cblist=new ArrayList<ProSetFormulaVO>();
    	
      	List<ProSetFormulaVO> olist=new ArrayList<ProSetFormulaVO>();
    	try {
		for(int i=0;i<initlist.size();i++){
			ProSetEnum type=initlist.get(i).getInvtype();
	            int index=PuPubVO.getInteger_NullAs(initlist.get(i).getItype(), -1);	
	            if(initlist.get(i).getInvtype()==ProSetEnum.Apowder && index==0){				
				    alist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));		
					continue;
				}else  if(initlist.get(i).getInvtype()==ProSetEnum.Apowder && index==1){				
				    ablist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));		
					continue;
				}
				if(initlist.get(i).getInvtype()==ProSetEnum.Bpowder&& index==0){
					blist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));
					continue;
				}else if(initlist.get(i).getInvtype()==ProSetEnum.Bpowder&& index==1){
					bblist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));
					continue;
				}	
				if(initlist.get(i).getInvtype()==ProSetEnum.Cpowder && index==0){
					blist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));
					continue;
				}else if(initlist.get(i).getInvtype()==ProSetEnum.Cpowder  && index==1){
					cblist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));
					continue;			
				}else{
					olist.add((ProSetFormulaVO)ObjectUtils.serializableClone(initlist.get(i)));
				}
		}
    	} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		initlist.clear();
		initlist.addAll(alist);
		initlist.addAll(ablist);
		initlist.addAll(blist);
		initlist.addAll(bblist);
		initlist.addAll(clist);
		initlist.addAll(cblist);
		initlist.addAll(olist);

	}
	/**
     * 执行二次选择精粉的公式
     * @param para 
     * @param initlist
     * @throws BusinessException 
     */
	public void exeRepeatFormulas(ProSetParaVO para, List<ProSetFormulaVO> initlist) throws BusinessException {
       if(initlist==null || initlist.size()==0){
    	   return;
       }
       createRawNumMap(para,initlist);
	   for(int i=0;i<initlist.size();i++){
		   ProSetFormulaVO fvo=initlist.get(i);
		  
		   exeRepeatFormula(para,fvo);
	   }
	}
	public Map<String,UFDouble> reselRawMap=new HashMap<String, UFDouble>();
	/**
	 * 
	 * @param para
	 * @param initlist
	 * @throws BusinessException 
	 */
    public void createRawNumMap(ProSetParaVO para,
			List<ProSetFormulaVO> initlist) throws BusinessException {
    	for(int j=0;j<initlist.size();j++){
    		ProSetFormulaVO fvo=initlist.get(j);
    	       if(fvo==null){
    	    	   return ;
    	       }		
    	    	   /**
    	    	    * 构建二次选矿执行公式vo
    	    	    */
    	    	   Map<ProSetEnum, List<CalYieldVO>> map= topmap.get(XcPubTool.getMapKey(para));
    	    	   List<CalYieldVO> list=new ArrayList<CalYieldVO>();
    	    	   if(map==null || map.size()==0 ){
    	    		   return ;
    	    	   }else if(fvo.getInvtype()==null){
    	               return ;
    	    	   }else{
    	    		   if(map.get(fvo.getInvtype())!=null&&map.get(fvo.getInvtype()).size()>0)
    	    		   list=map.get(fvo.getInvtype());
    	    	   }    	
    	    	   
    	    	   for(int i=0;i<list.size();i++){
    	    		   CalYieldVO cal=list.get(i);
    	    		   String pk_invmanindex=null;
    	    		   if(cal.getMainnumvo()!=null){
    	    			   pk_invmanindex=cal.getMainnumvo().getPk_invmandoc();
    	    		   }
    	    		   //if ismaninxdex will set rawnum
    	    		   if(fvo.getPk_manindex().equals(pk_invmanindex)){
    	    			   reselRawMap.put(XcPubTool.getMapKey(para)+fvo.getInvtype(), fvo.getNflour());
    	    		   }	
    	    	   }
    	}
	}
	/**
     * 执行二次精粉选择公式
     * @param para 
     * @param fvo
     * @throws BusinessException 
     */
	public void exeRepeatFormula(ProSetParaVO para, ProSetFormulaVO fvo) throws BusinessException {
       if(fvo==null){
    	   return ;
       }		
    	   /**
    	    * 构建二次选矿执行公式vo
    	    */
    	   Map<ProSetEnum, List<CalYieldVO>> map= topmap.get(XcPubTool.getMapKey(para));
    	   List<CalYieldVO> list=new ArrayList<CalYieldVO>();
    	   if(map==null || map.size()==0 ){
    		   return ;
    	   }else if(fvo.getInvtype()==null){
               return ;
    	   }else{
    		   if(map.get(fvo.getInvtype())!=null&&map.get(fvo.getInvtype()).size()>0)
    		   list=map.get(fvo.getInvtype());
    	   }    	
    	   
    	   for(int i=0;i<list.size();i++){
    		   CalYieldVO cal=list.get(i);
    		   String pk_invmanindex=null;
    		   if(cal.getMainnumvo()!=null){
    			   pk_invmanindex=cal.getMainnumvo().getPk_invmandoc();
    		   }
    		   //if ismaninxdex will set rawnum
    		   if(fvo.getPk_manindex().equals(pk_invmanindex)){
    			   
    		   }
    		   List<CalYieldVO> recals=cal.getOutsetvos();
    		   List<RepeatSelFlorVO> lrs=new ArrayList<RepeatSelFlorVO>();
    		   /**
    		    *二次选精粉的精粉，指标，品位，产量，金属量，回收率
    		    *二次选择后的精粉，指标，品位
    		    */
    		  if(recals==null || recals.size()==0){
    			  continue;
    		  }
    		  RepeatSelFlorVO rsfvo=createRepeatVO(fvo,recals);
    		  
    		  //if resselect powder is mainindex  will set rawnum
    	      if(PuPubVO.getUFDouble_NullAsZero(reselRawMap.get(XcPubTool.getMapKey(para)+fvo.getInvtype())).doubleValue()>0){
    	    	  fvo.setNflour(reselRawMap.get(XcPubTool.getMapKey(para)+fvo.getInvtype()));
    	      }
    		 
             lrs.add(rsfvo);
    		 fvo.setReplist(lrs);
    	   }
    	   /**
    	    * 执行二次选矿公式
    	    */
    	   /**
    	    * 从公式vo中取到二次选择前的精粉的，指标，品位，产量，金属量，回收率
    	    * 设置到动态vo
    	    * 
    	    * 从公式vo中取到二次选择后的精粉指标，品位
    	    * 
    	    * 根据以上数据计算二次选矿后的精粉产量，金属量，回收率
    	    */
    	    if(fvo.getReplist()==null || fvo.getReplist().size()==0){
    	    	return;
    	    }
    	    ReportBaseVO[] rvos=new ReportBaseVO[fvo.getReplist().size()];
    	    for(int i=0;i<rvos.length;i++){
    	    	rvos[i]=new ReportBaseVO();
    	    	for(int j=0;j<fvo.getAttributeNames().length;j++){   	    		
    	    		rvos[i].setAttributeValue(fvo.getAttributeNames()[j], fvo.getAttributeValue(fvo.getAttributeNames()[j]));		  	    		
    	    	}
    	    }
    	    
    		List<RepeatSelFlorVO>  replist=fvo.getReplist();
            for(int i=0;i<rvos.length;i++){
            	RepeatSelFlorVO rsvo= replist.get(i);
            	for(int j=0;j<rsvo.getAttributeNames().length;j++){
            		rvos[i].setAttributeValue(rsvo.getAttributeNames()[j], rsvo.getAttributeValue(rsvo.getAttributeNames()[j]));		
            	}
            	rsvo.setRawinfor(fvo);
            } 
            /**
             * 执行二次选矿公式
             */
           try {
        	for (int i = 0; i < rvos.length; i++) {
				ReportBaseVO vo = rvos[i];
				String code1 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulacode1"));
				String name1 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulaname1"));
				if (code1 != null) {
					List<UFDouble> list1;
					
						list1 = getCalFormulaBO().doCalcStart(code1, name1,
								new ReportBaseVO[] { vo });
				
					UFDouble nrate = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nrerate", nrate);
					replist.get(i).setNrerate(nrate);
				}
				String code2 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulacode2"));
				String name2 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulaname2"));
				if (code2 != null) {
					List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code2,
							name2, new ReportBaseVO[] { vo });
					UFDouble nmetal = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nremetal", nmetal);
					replist.get(i).setNremetal(nmetal);
				}
				String code3 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulacode3"));
				String name3 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulaname3"));
				if (code3 != null) {
					List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code3,
							name3, new ReportBaseVO[] { vo });
					UFDouble nflour = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nreflour", nflour);
					replist.get(i).setNreflour(nflour);
				}
			}
        	
        	for (int i = 0; i < rvos.length; i++) {
				ReportBaseVO vo = rvos[i];
				String code1 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulacode11"));
				String name1 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulaname11"));
				if (code1 != null) {
					List<UFDouble> list1;
					
						list1 = getCalFormulaBO().doCalcStart(code1, name1,
								new ReportBaseVO[] { vo });
				
					UFDouble nrate = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nrerate1", nrate);
					replist.get(i).setNrerate1(nrate);
				}
				String code2 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulacode22"));
				String name2 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulaname22"));
				if (code2 != null) {
					List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code2,
							name2, new ReportBaseVO[] { vo });
					UFDouble nmetal = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nremetal1", nmetal);
					replist.get(i).setNremetal1(nmetal);
				}
				String code3 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulacode33"));
				String name3 = PuPubVO.getString_TrimZeroLenAsNull(vo
						.getAttributeValue("vreformulaname33"));
				if (code3 != null) {
					List<UFDouble> list1 = getCalFormulaBO().doCalcStart(code3,
							name3, new ReportBaseVO[] { vo });
					UFDouble nflour = PuPubVO.getUFDouble_NullAsZero(list1
							.get(0));
					vo.setAttributeValue("nreflour1", nflour);
					replist.get(i).setNreflour1(nflour);
				}
			}
        	} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
    	   
 //      }
	}
    /**
     * CalYieldVO ->RepeatSelFlorVO 转换
     * @param fvo
     * @param recals
     * @return
     */
	public RepeatSelFlorVO createRepeatVO(ProSetFormulaVO fvo,
			List<CalYieldVO> recals) {
		String pk_mandex=PuPubVO.getString_TrimZeroLenAsNull(fvo.getPk_manindex());
			if (recals == null || recals.size() == 0) {
				return null;
			}

			RepeatSelFlorVO provo = new RepeatSelFlorVO();

			for (int i = 0; i < recals.size(); i++) {

				CalYieldVO recal = recals.get(i);
				ProductSetBB2VO bb1vo = recal.getOutsetvo();
				if (recal.getMainnumvo() != null) {
					CalYieldNumVO indexvo = recal.getMainnumvo();
					if(indexvo.getPk_invmandoc().equals(fvo.getPk_manindex())){
                      setRepSelFlorVO(provo,bb1vo,indexvo);			
					}
				}
				if(recal.getNumList()!=null&& recal.getNumList().size()>0){
					for(int j=0;j<recal.getNumList().size();j++){
						CalYieldNumVO ffvo=recal.getNumList().get(j);
						if(ffvo.getPk_invmandoc().equals(fvo.getPk_manindex())){

						setRepSelFlorVO(provo,bb1vo,ffvo);
						}
					}
				}
				if(recal.getOthernumList()!=null&& recal.getOthernumList().size()>0){
					for(int j=0;j<recal.getOthernumList().size();j++){
						CalYieldNumVO ffvo=recal.getOthernumList().get(j);
						if(ffvo.getPk_invmandoc().equals(fvo.getPk_manindex())){

						setRepSelFlorVO(provo,bb1vo,ffvo);
						}
					}
				}
			}
			return provo;
	}

	public void setRepSelFlorVO(RepeatSelFlorVO provo,ProductSetBB2VO bb1vo, CalYieldNumVO indexvo) {
		if (bb1vo == null || indexvo == null) {
			return ;
		}
			/**
			 * 设置二次选后精粉
			 */
			if (PuPubVO.getUFBoolean_NullAs(bb1vo.getUispowder(),
					new UFBoolean(false)).booleanValue() == true) {
				provo.setPk_reinvmandoc(bb1vo.getPk_invmandoc());
				provo.setPk_reinvbasdoc(bb1vo.getPk_invbasdoc());
				
				/**
				 * set execute formulas
				 */			
				provo.setVreformulacode1(bb1vo.getVformulacode1());
				provo.setVreformulaname1(bb1vo.getVformulaname1());
				provo.setVreformulacode2(bb1vo.getVformulacode2());
				provo.setVreformulaname2(bb1vo.getVformulaname2());
				provo.setVreformulacode3(bb1vo.getVformulacode3());
				provo.setVreformulaname3(bb1vo.getVformulaname3());
				provo.setVreformulacode4(bb1vo.getVformulacode4());
				provo.setVreformulaname4(bb1vo.getVformulaname4());
				provo.setVreformulacode5(bb1vo.getVformulacode5());
				provo.setVreformulaname5(bb1vo.getVformulaname5());
				
			} else {
				/**
				 * 设置二次选后尾矿
				 */
				provo.setPk_reinvmandoc1(bb1vo.getPk_invmandoc());
				provo.setPk_reinvbasdoc1(bb1vo.getPk_invbasdoc());
				
				/**
				 * set execute formulas
				 */			
				provo.setVreformulacode11(bb1vo.getVformulacode1());
				provo.setVreformulaname11(bb1vo.getVformulaname1());
				provo.setVreformulacode22(bb1vo.getVformulacode2());
				provo.setVreformulaname22(bb1vo.getVformulaname2());
				provo.setVreformulacode33(bb1vo.getVformulacode3());
				provo.setVreformulaname33(bb1vo.getVformulaname3());
				provo.setVreformulacode44(bb1vo.getVformulacode4());
				provo.setVreformulaname44(bb1vo.getVformulaname4());
				provo.setVreformulacode55(bb1vo.getVformulacode5());
				provo.setVreformulaname55(bb1vo.getVformulaname5());
				
				
			}
			/**
			 * 设置二次选后品位
			 */
			if (PuPubVO.getUFBoolean_NullAs(bb1vo.getUispowder(),
					new UFBoolean(false)).booleanValue() == true) {
				provo.setNregrade(indexvo.getNgrade());
			} else {
				provo.setNregrade1(indexvo.getNgrade());
			}

			return ;		
	}

	/**
	 * 
	 * @param initlist
	 * @return
	 */
	private  ReportBaseVO[] getReportBaseVO(List<ProSetFormulaVO> initlist) {
		if (initlist == null || initlist.size() == 0) {
			return null;
		}
		ReportBaseVO[] rvos = new ReportBaseVO[initlist.size()];
		for (int i = 0; i < rvos.length; i++) {
			ProSetFormulaVO vo = initlist.get(i);
			String[] names = vo.getAttributeNames();
			ReportBaseVO retvo=new ReportBaseVO();
			for (int n = 0; n < names.length; n++) {
				retvo.setAttributeValue(names[n], vo.getAttributeValue(names[n]));
			}
			rvos[i]=retvo;
		}
		return rvos;
	}

	/**
	 * 获得初始化计算公式
	 * 
	 * @author mlr
	 * @param topmap
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public  List<ProSetFormulaVO> getInitFormulaVO(ProSetParaVO para)
			throws Exception {
		String topkey = XcPubTool.getMapKey(para);
		Map<ProSetEnum, List<CalYieldVO>> flowmap = topmap.get(topkey);
		List<ProSetFormulaVO> forvos = new ArrayList<ProSetFormulaVO>();
		/**
		 * 设置原矿数量、品位
		 */
		List<CalYieldVO> list = flowmap.get(ProSetEnum.RawOre);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CalYieldVO calvo = list.get(i);
				ProductSetBB1VO bb1vo = calvo.getInsetvo();
				ProSetFormulaVO provo = initFormulaVO(bb1vo, calvo
						.getMainnumvo());
				if (provo != null) {
					forvos.add(provo);
				}

				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						ProSetFormulaVO provo1 = initFormulaVO(bb1vo, calvo
								.getNumList().get(j));
						if (provo1 != null) {
							forvos.add(provo1);
						}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						ProSetFormulaVO provo2 = initFormulaVO(bb1vo, calvo
								.getOthernumList().get(j));
						if (provo2 != null) {
							forvos.add(provo2);
						}
					}
				}
			}
		}

		/**
		 * 设置一道工艺精矿品位
		 */
		List<CalYieldVO> aplist = flowmap.get(ProSetEnum.Apowder);
		if (aplist != null && aplist.size() > 0) {
			for (int i = 0; i < aplist.size(); i++) {
				CalYieldVO calvo = aplist.get(i);
				ProductSetBB2VO bb1vo = calvo.getOutsetvo();
				if (calvo.getMainnumvo() != null) {
					List<ProSetFormulaVO> provos = getFormulaVOByIndex(calvo
							.getMainnumvo().getPk_invmandoc(), forvos);
					if (provos != null && provos.size() > 0) {
						for(int n=0;n<provos.size();n++){
							ProSetFormulaVO provo = provos.get(n);
							
							if (provo.isSharePowerFormula() == false) {
								/**
								 * 初始化公式
								 */
								initApowderFormula(provo, bb1vo, calvo
										.getMainnumvo(), 0);

								provo.setInvtype(ProSetEnum.Apowder);
								provo.setSharePowerFormula(true);
								/**
								 * 设置执行公式
								 */
								initFormulas(provo, bb1vo);
							}else{
								/**
								 * 初始化公式
								 */
								initApowderFormula(provo, bb1vo, calvo
										.getMainnumvo(), -3);
															
								ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
								/**
								 * 初始化公式
								 */
								initApowderFormula(cprovo, bb1vo, calvo
										.getMainnumvo(), 0);

								cprovo.setInvtype(ProSetEnum.Apowder);
								
								cprovo.setSharePowerFormula(true);
								/**
								 * 设置执行公式
								 */
								initFormulas(cprovo, bb1vo);
							
								forvos.add(cprovo);
							}
						}
						
					}
				}
				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getNumList().get(j).getPk_invmandoc(),
								forvos);
						if (provos != null && provos.size() > 0) {
							
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								
								if(provo.isSharePowerFormula()==false){
									/**
									 * 初始化公式
									 */
									initApowderFormula(provo, bb1vo, calvo.getNumList()
											.get(j), 1);
									provo.setInvtype(ProSetEnum.Apowder);
									
									provo.setSharePowerFormula(true);
									/**
									 * 设置执行公式
									 */
									initFormulas(provo, bb1vo);
								}else{
									
									initApowderFormula(provo, bb1vo, calvo.getNumList()
											.get(j), -3);
//									provo.setInvtype(ProSetEnum.Apowder);
									
//									provo.setSharePowerFormula(true);
									
									ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
									/**
									 * 初始化公式
									 */
									initApowderFormula(cprovo, bb1vo, calvo.getNumList()
											.get(j), 1);
									cprovo.setInvtype(ProSetEnum.Apowder);
									/**
									 * 设置执行公式
									 */
									initFormulas(cprovo, bb1vo);
									
									cprovo.setSharePowerFormula(true);
								
									forvos.add(cprovo);
								}
						
							}
							
						}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getOthernumList().get(j)
										.getPk_invmandoc(), forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								/**
								 * 初始化公式
								 */
								initApowderFormula(provo, bb1vo, calvo
										.getOthernumList().get(j), -3);
							}
				
						}
					}
				}
			}
		}

		/**
		 * 设置二道工艺精矿品位
		 */
		List<CalYieldVO> bplist = flowmap.get(ProSetEnum.Bpowder);
		if (bplist != null && bplist.size() > 0) {
			for (int i = 0; i < bplist.size(); i++) {
				CalYieldVO calvo = bplist.get(i);
				ProductSetBB2VO bb1vo = calvo.getOutsetvo();
				if (calvo.getMainnumvo() != null) {
					List<ProSetFormulaVO> provos = getFormulaVOByIndex(calvo
							.getMainnumvo().getPk_invmandoc(), forvos);
					if (provos != null && provos.size() > 0) {
						for(int n=0;n<provos.size();n++){
							ProSetFormulaVO provo = provos.get(n);
							
							if (provo.isSharePowerFormula() == false) {
								/**
								 * 初始化公式
								 */
								initBpowderFormula(provo, bb1vo, calvo
										.getMainnumvo(), 0);
								provo.setInvtype(ProSetEnum.Bpowder);
								
								provo.setSharePowerFormula(true);
								/**
								 * 设置执行公式
								 */
								initFormulas(provo, bb1vo);
							}else{
								
								initBpowderFormula(provo, bb1vo, calvo
										.getMainnumvo(), -3);
								
								ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
								/**
								 * 初始化公式
								 */
								initBpowderFormula(cprovo, bb1vo, calvo
										.getMainnumvo(), 0);
								cprovo.setInvtype(ProSetEnum.Bpowder);
								/**
								 * 设置执行公式
								 */
								initFormulas(cprovo, bb1vo);
								
								cprovo.setSharePowerFormula(true);
								
								forvos.add(cprovo);
								
							}
						}
						
					}
				}
				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getNumList().get(j).getPk_invmandoc(),
								forvos);
						if (provos != null && provos.size() > 0) {
							//for modify mlr 
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								if(provo.isSharePowerFormula()==false){									
									/**
									 * 初始化公式
									 */
									initBpowderFormula(provo, bb1vo, calvo.getNumList()
											.get(j), 1);
									provo.setInvtype(ProSetEnum.Bpowder);
									
									provo.setSharePowerFormula(true);
									/**
									 * 设置执行公式
									 */
									initFormulas(provo, bb1vo);										
									
								}else{
									
									initBpowderFormula(provo, bb1vo, calvo.getNumList()
											.get(j), -3);
									
									ProSetFormulaVO mcprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
									/**
									 * 初始化公式
									 */
									initBpowderFormula(mcprovo, bb1vo, calvo.getNumList()
											.get(j), 1);
									mcprovo.setInvtype(ProSetEnum.Bpowder);
									/**
									 * 设置执行公式
									 */
									initFormulas(mcprovo, bb1vo);	
									
									mcprovo.setSharePowerFormula(true);
									forvos.add(mcprovo);
								}
							}
					
						}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getOthernumList().get(j)
										.getPk_invmandoc(), forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								/**
								 * 初始化公式
								 */
								initBpowderFormula(provo, bb1vo, calvo
										.getOthernumList().get(j), -3);
							}
					
						}
					}
				}
			}
		}

		/**
		 * 设置三道工艺精矿品位
		 */
		List<CalYieldVO> cplist = flowmap.get(ProSetEnum.Cpowder);
		if (cplist != null && cplist.size() > 0) {
			for (int i = 0; i < cplist.size(); i++) {
				CalYieldVO calvo = cplist.get(i);
				ProductSetBB2VO bb1vo = calvo.getOutsetvo();
				if (calvo.getMainnumvo() != null) {
					List<ProSetFormulaVO> provos = getFormulaVOByIndex(calvo
							.getMainnumvo().getPk_invmandoc(), forvos);
					if (provos != null && provos.size() > 0) {
						for(int n=0;n<provos.size();n++){
							ProSetFormulaVO provo = provos.get(n);
							
							if (provo.isSharePowerFormula() == false) {
								/**
								 * 初始化公式
								 */
								initCpowderFormula(provo, bb1vo, calvo
										.getMainnumvo(), 0);
								provo.setInvtype(ProSetEnum.Cpowder);

								provo.setSharePowerFormula(true);
								/**
								 * 设置执行公式
								 */
								initFormulas(provo, bb1vo);
							}else{
								initCpowderFormula(provo, bb1vo, calvo
										.getMainnumvo(),-3);
								ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo) ;
								
								initCpowderFormula(cprovo, bb1vo, calvo
										.getMainnumvo(), 0);
								cprovo.setInvtype(ProSetEnum.Cpowder);

								cprovo.setSharePowerFormula(true);
								
								initFormulas(cprovo, bb1vo);
								
								forvos.add(cprovo);
							}
						}
						
					}
				}
				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getNumList().get(j).getPk_invmandoc(),
								forvos);
						if (provos != null && provos.size() > 0) {
						
							//for modify mlr 
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								
								if (provo.isSharePowerFormula() == false) {
									/**
									 * 初始化公式
									 */
									initBpowderFormula(provo, bb1vo, calvo.getNumList()
											.get(j), 1);
									provo.setInvtype(ProSetEnum.Cpowder);

									provo.setSharePowerFormula(true);
									/**
									 * 设置执行公式
									 */
									initFormulas(provo, bb1vo);
								}else{
									 
									initBpowderFormula(provo, bb1vo, calvo.getNumList()
												.get(j), -3);
									ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo) ;
									

									initBpowderFormula(cprovo, bb1vo, calvo.getNumList()
											.get(j), 1);
									cprovo.setInvtype(ProSetEnum.Cpowder);

									cprovo.setSharePowerFormula(true);
									
									initFormulas(cprovo, bb1vo);
									
									forvos.add(cprovo);
								}
							
							}
						}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getOthernumList().get(j)
										.getPk_invmandoc(), forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								/**
								 * 初始化公式
								 */
								initCpowderFormula(provo, bb1vo, calvo
										.getOthernumList().get(j), -6);
							}
						
						}
					}
				}
			}
		}

		/**
		 * 设置一道工艺尾矿
		 */
		List<CalYieldVO> atlist = flowmap.get(ProSetEnum.Atails);
		if (atlist != null && atlist.size() > 0) {
			for (int i = 0; i < atlist.size(); i++) {
				CalYieldVO calvo = atlist.get(i);
				ProductSetBB2VO bb1vo = calvo.getOutsetvo();
				if (calvo.getMainnumvo() != null) {
					List<ProSetFormulaVO> provos = getFormulaVOByIndex(calvo
							.getMainnumvo().getPk_invmandoc(), forvos);
					if (provos != null && provos.size() > 0) {
						for(int n=0;n<provos.size();n++){
							ProSetFormulaVO provo = provos.get(n);
							
						
							
							if (provo.isShareTailFormula() == false) {
								/**
								 * 初始化公式
								 */
								initAtailsFormula(provo, bb1vo, calvo
										.getMainnumvo(), -3);

								provo.setShareTailFormula(true);
								/**
								 * 设置执行公式
								 */
								if(provo.getInvtype()==ProSetEnum.Apowder){
									initTailFormulas(provo, bb1vo);
								}
								
							}else{
								/**
								 * 初始化公式
								 */
								initAtailsFormula(provo, bb1vo, calvo
										.getMainnumvo(), -3);
								
								ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
								/**
								 * 初始化公式
								 */
								initAtailsFormula(cprovo, bb1vo, calvo
										.getMainnumvo(), -3);

								cprovo.setShareTailFormula(true);
								
								
								/**
								 * 设置执行公式
								 */
								if(provo.getInvtype()==ProSetEnum.Apowder){
									initTailFormulas(cprovo, bb1vo);
								}
							
								
								forvos.add(cprovo);
								
							}
						}
						
					}
				}
				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getNumList().get(j).getPk_invmandoc(),
								forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								if (provo.isShareTailFormula() == false) {
									/**
									 * 初始化公式
									 */
									/**
									 * 初始化公式
									 */
									initAtailsFormula(provo, bb1vo, calvo.getNumList()
											.get(j), -3);

									provo.setShareTailFormula(true);
									/**
									 * 设置执行公式
									 */
									if(provo.getInvtype()==ProSetEnum.Apowder){
										initTailFormulas(provo, bb1vo);
									}
									
								}else{
									/**
									 * 初始化公式
									 */
									initAtailsFormula(provo, bb1vo, calvo.getNumList()
											.get(j), -3);
									
									ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
									/**
									 * 初始化公式
									 */
									initAtailsFormula(cprovo, bb1vo, calvo.getNumList()
											.get(j), -3);

									cprovo.setShareTailFormula(true);
									
									
									/**
									 * 设置执行公式
									 */
									if(provo.getInvtype()==ProSetEnum.Apowder){
										initTailFormulas(cprovo, bb1vo);
									}
								
									
									forvos.add(cprovo);
									
								}
						
							}
							
						
						}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getOthernumList().get(j)
										.getPk_invmandoc(), forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								/**
								 * 初始化公式
								 */
								initAtailsFormula(provo, bb1vo, calvo
										.getOthernumList().get(j), -3);
							}
					
						}
					}
				}
			}

		}

		/**
		 * 设置二道工艺尾矿
		 */
		List<CalYieldVO> btlist = flowmap.get(ProSetEnum.Btails);
		if (btlist != null && btlist.size() > 0) {
			for (int i = 0; i < btlist.size(); i++) {
				CalYieldVO calvo = btlist.get(i);
				ProductSetBB2VO bb1vo = calvo.getOutsetvo();
				if (calvo.getMainnumvo() != null) {
					List<ProSetFormulaVO> provos = getFormulaVOByIndex(calvo
							.getMainnumvo().getPk_invmandoc(), forvos);
					if (provos != null && provos.size() > 0) {
						for(int n=0;n<provos.size();n++){
							ProSetFormulaVO provo = provos.get(n);														
							if (provo.isShareTailFormula() == false) {
								/**
								 * 初始化公式
								 */
								initBtailsFormula(provo, bb1vo, calvo.getMainnumvo(),
										-3);
								provo.setShareTailFormula(true);
								/**
								 * 设置执行公式
								 */
								if(provo.getInvtype()==ProSetEnum.Bpowder){
									initTailFormulas(provo, bb1vo);
								}								
							}else{
								/**
								 * 初始化公式
								 */
								initBtailsFormula(provo, bb1vo, calvo.getMainnumvo(),
										-3);
								
								ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
								/**
								 * 初始化公式
								 */
                                initBtailsFormula(cprovo, bb1vo, calvo.getMainnumvo(),
    										-3);
								cprovo.setShareTailFormula(true);														
								/**
								 * 设置执行公式
								 */								
								if(provo.getInvtype()==ProSetEnum.Bpowder){
									initTailFormulas(cprovo, bb1vo);
								}								
								forvos.add(cprovo);								
							}
						}
						
				

					}
				}
				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getNumList().get(j).getPk_invmandoc(),
								forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								
								if (provo.isShareTailFormula() == false) {
									/**
									 * 初始化公式
									 */
									initBtailsFormula(provo, bb1vo, calvo.getNumList()
											.get(j), -3);

									provo.setShareTailFormula(true);
									/**
									 * 设置执行公式
									 */
									 if(provo.getInvtype()==ProSetEnum.Bpowder){
										 initTailFormulas(provo, bb1vo);
									 }
									
								}else{
									/**
									 * 初始化公式
									 */
									initBtailsFormula(provo, bb1vo, calvo.getNumList()
											.get(j), -3);
									
									ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
									/**
									 * 初始化公式
									 */
									initBtailsFormula(cprovo, bb1vo, calvo.getNumList()
											.get(j), -3);

									cprovo.setShareTailFormula(true);
									
									
									/**
									 * 设置执行公式
									 */
									 if(provo.getInvtype()==ProSetEnum.Bpowder){
										 initTailFormulas(cprovo, bb1vo);
										}
									
									
									forvos.add(cprovo);
									
								}
							}
							
						}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getOthernumList().get(j)
										.getPk_invmandoc(), forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								/**
								 * 初始化公式
								 */
								initBtailsFormula(provo, bb1vo, calvo
										.getOthernumList().get(j), -3);
							}
				
						}
					}
				}
			}
		}

		/**
		 * 设置三道工艺尾矿
		 */
		List<CalYieldVO> ctlist = flowmap.get(ProSetEnum.Ctails);
		if (ctlist != null && ctlist.size() > 0) {
			for (int i = 0; i < ctlist.size(); i++) {
				CalYieldVO calvo = ctlist.get(i);
				ProductSetBB2VO bb1vo = calvo.getOutsetvo();
				if (calvo.getMainnumvo() != null) {
					List<ProSetFormulaVO> provos = getFormulaVOByIndex(calvo
							.getMainnumvo().getPk_invmandoc(), forvos);
					if (provos != null && provos.size() > 0) {
						 for(int n=0;n<provos.size();n++){
							 ProSetFormulaVO provo = provos.get(n);
								
								if (provo.isShareTailFormula() == false) {
									/**
									 * 初始化公式
									 */
									initCtailsFormula(provo, bb1vo, calvo.getMainnumvo(),
											-3);

									provo.setShareTailFormula(true);
									/**
									 * 设置执行公式
									 */
									if(provo.getInvtype()==ProSetEnum.Cpowder){
										initTailFormulas(provo, bb1vo);
									}
									
								}else{
									/**
									 * 初始化公式
									 */
									initCtailsFormula(provo, bb1vo, calvo.getMainnumvo(),
											-3);									
									ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
									/**
									 * 初始化公式
									 */
									initCtailsFormula(cprovo, bb1vo, calvo.getMainnumvo(),
											-3);
									cprovo.setShareTailFormula(true);																		
									/**
									 * 设置执行公式
									 */
                                    if(provo.getInvtype()==ProSetEnum.Cpowder){
                                    	initTailFormulas(cprovo, bb1vo);
									}																	
									forvos.add(cprovo);
									
								} 
						 }
						
					}
				}
				if (calvo.getNumList() != null && calvo.getNumList().size() > 0) {
					for (int j = 0; j < calvo.getNumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getNumList().get(j).getPk_invmandoc(),
								forvos);
						if (provos != null && provos.size() > 0) {
                            for(int n=0;n<provos.size();n++){
                        		ProSetFormulaVO provo = provos.get(n);
    							if (provo.isShareTailFormula() == false) {
    								/**
    								 * 初始化公式
    								 */
    								initCtailsFormula(provo, bb1vo, calvo.getNumList()
    										.get(j), -3);
    								provo.setShareTailFormula(true);
    								/**
    								 * 设置执行公式
    								 */
    								   if(provo.getInvtype()==ProSetEnum.Cpowder){
    									   initTailFormulas(provo, bb1vo);
   									}
    								
    							}else{
    								/**
    								 * 初始化公式
    								 */
    								initCtailsFormula(provo, bb1vo, calvo.getNumList()
    										.get(j), -3);    								
    								ProSetFormulaVO cprovo=(ProSetFormulaVO) ObjectUtils.serializableClone(provo);
    								/**
    								 * 初始化公式
    								 */
    								initCtailsFormula(cprovo, bb1vo, calvo.getNumList()
    										.get(j), -3);
    								cprovo.setShareTailFormula(true);   								   								
    								/**
    								 * 设置执行公式
    								 */
    								   if(provo.getInvtype()==ProSetEnum.Cpowder){
    									   initTailFormulas(cprovo, bb1vo);
   									}   								    								
    								forvos.add(cprovo);    								
    							}
							}
					}
					}
				}

				if (calvo.getOthernumList() != null
						&& calvo.getOthernumList().size() > 0) {
					for (int j = 0; j < calvo.getOthernumList().size(); j++) {
						List<ProSetFormulaVO> provos = getFormulaVOByIndex(
								calvo.getOthernumList().get(j)
										.getPk_invmandoc(), forvos);
						if (provos != null && provos.size() > 0) {
							for(int n=0;n<provos.size();n++){
								ProSetFormulaVO provo = provos.get(n);
								/**
								 * 初始化公式
								 */
								initCtailsFormula(provo, bb1vo, calvo
										.getOthernumList().get(j), -3);
							}
				
						}
					}
				}
			}

		}

		return forvos;
	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 * @param calvo
	 * @param i
	 */
	public  void initApowderFormula(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo, CalYieldNumVO calvo, int itype) {
		/**
		 * 设置精粉
		 */
		provo.setPk_apowdermanid(bb1vo.getPk_invmandoc());
		provo.setPk_apowderinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setConcentrate1(calvo.getNgrade());
		/**
		 * 设置主指标
		 */
		if(itype>=0)
		provo.setItype(itype);

	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 * @param calvo
	 * @param i
	 */
	public  void initBpowderFormula(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo, CalYieldNumVO calvo, int itype) {
		/**
		 * 设置精粉
		 */
		provo.setPk_bpowdermanid(bb1vo.getPk_invmandoc());
		provo.setPk_bpowderinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setConcentrate2(calvo.getNgrade());
		/**
		 * 设置主指标
		 */	
		if(itype>=0)
		provo.setItype(itype);
	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 * @param calvo
	 * @param i
	 */
	public  void initCpowderFormula(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo, CalYieldNumVO calvo, int itype) {

		/**
		 * 设置精粉
		 */
		provo.setPk_cpowdermanid(bb1vo.getPk_invmandoc());
		provo.setPk_cpowderinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setConcentrate3(calvo.getNgrade());
		/**
		 * 设置主指标
		 */	
		if(itype>=0)
		provo.setItype(itype);

	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 * @param calvo
	 * @param i
	 */
	public  void initAtailsFormula(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo, CalYieldNumVO calvo, int itype) {
		/**
		 * 设置精粉
		 */
		provo.setPk_atailsmanid(bb1vo.getPk_invmandoc());
		provo.setPk_atailsinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setTailingsgrade1(calvo.getNgrade());
		/**
		 * 设置主指标
		 */
		if(itype>=0)
		provo.setItype(itype);

	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 * @param calvo
	 * @param i
	 */
	public  void initBtailsFormula(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo, CalYieldNumVO calvo, int itype) {
		/**
		 * 设置精粉
		 */
		provo.setPk_btailsmanid(bb1vo.getPk_invmandoc());
		provo.setPk_btailsinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setTailingsgrade2(calvo.getNgrade());
		/**
		 * 设置主指标
		 */
		if(itype>=0)
		provo.setItype(itype);
	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 * @param calvo
	 * @param i
	 */
	public  void initCtailsFormula(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo, CalYieldNumVO calvo, int itype) {
		/**
		 * 设置精粉
		 */
		provo.setPk_ctailsmanid(bb1vo.getPk_invmandoc());
		provo.setPk_ctailsinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setTailingsgrade3(calvo.getNgrade());
		/**
		 * 设置主指标
		 */
		if(itype>=0)
		provo.setItype(itype);

	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 */
	public  void initFormulas(ProSetFormulaVO provo, ProductSetBB2VO bb1vo) {
		if (provo == null || bb1vo == null) {
			return;
		}
		provo.setVformulacode1(bb1vo.getVformulacode1());
		provo.setVformulaname1(bb1vo.getVformulaname1());
		provo.setVformulacode2(bb1vo.getVformulacode2());
		provo.setVformulaname2(bb1vo.getVformulaname2());
		provo.setVformulacode3(bb1vo.getVformulacode3());
		provo.setVformulaname3(bb1vo.getVformulaname3());
		provo.setVformulacode4(bb1vo.getVformulacode4());
		provo.setVformulaname4(bb1vo.getVformulaname4());
		provo.setVformulacode5(bb1vo.getVformulacode5());
		provo.setVformulaname5(bb1vo.getVformulaname5());
	}

	/**
	 * 初始化公式
	 * 
	 * @param provo
	 * @param bb1vo
	 */
	public  void initTailFormulas(ProSetFormulaVO provo,
			ProductSetBB2VO bb1vo) {
		if (provo == null || bb1vo == null) {
			return;
		}
		provo.setVformulacode11(bb1vo.getVformulacode1());
		provo.setVformulaname11(bb1vo.getVformulaname1());
		provo.setVformulacode22(bb1vo.getVformulacode2());
		provo.setVformulaname22(bb1vo.getVformulaname2());
		provo.setVformulacode33(bb1vo.getVformulacode3());
		provo.setVformulaname33(bb1vo.getVformulaname3());
		provo.setVformulacode44(bb1vo.getVformulacode4());
		provo.setVformulaname44(bb1vo.getVformulaname4());
		provo.setVformulacode55(bb1vo.getVformulacode5());
		provo.setVformulaname55(bb1vo.getVformulaname5());
	}

	/**
	 * 初始化公式
	 * 
	 * @param bb1vo
	 * @param mainnumvo
	 * @return
	 */
	public  ProSetFormulaVO initFormulaVO(ProductSetBB1VO bb1vo,
			CalYieldNumVO indexvo) {
		if (bb1vo == null || indexvo == null) {
			return null;
		}
		ProSetFormulaVO provo = new ProSetFormulaVO();
		/**
		 * 设置原矿
		 */
		provo.setPk_raworemanid(bb1vo.getPk_invmandoc());
		provo.setPk_raworeinvid(bb1vo.getPk_invbasdoc());
		/**
		 * 设置指标
		 */
		provo.setPk_manindex(indexvo.getPk_invmandoc());
		provo.setPk_invindex(indexvo.getPk_invbasdoc());
		/**
		 * 设置品位
		 */
		provo.setRaworegrade(indexvo.getNgrade());
		
		provo.setRaworenum(indexvo.getNrawore());
		return provo;
	}

	/**
	 * 获取对应指标的公式vo
	 * 
	 * @param pk_invmandoc
	 * @param forvos
	 * @return
	 */
	public  List<ProSetFormulaVO> getFormulaVOByIndex(
			String pk_invmandoc, List<ProSetFormulaVO> forvos) {
		List<ProSetFormulaVO> list = new ArrayList<ProSetFormulaVO>();

		if (forvos == null || forvos.size() == 0 || pk_invmandoc == null
				|| pk_invmandoc.length() == 0) {
			return null;
		}
		for (int i = 0; i < forvos.size(); i++) {
			if (forvos.get(i).getPk_manindex().equals(pk_invmandoc)) {
				list.add(forvos.get(i));
			}
		}
		return list;
	}

	/**
	 * 设置加工数量到topMap
	 * 
	 * @param caloutmap
	 * @param topmap
	 * @param para
	 * @throws BusinessException
	 */
	public  void doCostCalOutNumToTopMap(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0) {
			return;
		}
		for (int n = 0; n < paras.length; n++) {
			ProSetParaVO para = paras[n];
			String topkey = XcPubTool.getMapKey(para);
			Map<ProSetEnum, List<CalYieldVO>> map = topmap.get(topkey);
			if (map == null || map.size() == 0) {
				return;
			}
			/**
			 * 取到加工数量
			 */
			List<CalYieldVO> orelist = map.get(ProSetEnum.RawOre);
			UFDouble outnum = new UFDouble(0.00000);
			if (orelist != null && orelist.size() > 0) {
				for (int i = 0; i < orelist.size(); i++) {
					CalYieldVO calvo = orelist.get(i);
					ProductSetBB1VO bb1vo = calvo.getInsetvo();
					if (bb1vo == null) {
						continue;
					}
					String pk_invmandoc = bb1vo.getPk_invmandoc();
					String calkey = XcPubTool.getCalMapKey(para, pk_invmandoc);
					Map<String, UFDouble> caloutnummap = calOutNumMap
							.get(topkey);
					if (caloutnummap != null && caloutnummap.size() > 0) {
						outnum = PuPubVO.getUFDouble_NullAsZero(caloutnummap
								.get(calkey));
					}
				}
			}
			/**
			 * 设置加工数量
			 */
			List<ProSetEnum> enlist = getEnumList();
			for (int i = 0; i < enlist.size(); i++) {
				List<CalYieldVO> callist = map.get(enlist.get(i));
				setRawOreNumToCalVO(outnum, callist);
			}
		}
	}

	/**
	 * 将原矿加工数量设置到计算vo
	 * 
	 * @author mlr
	 * @param outnum
	 * @param calvo
	 */
	public  void setRawOreNumToCalVO(UFDouble outnum, CalYieldVO calvo) {
		if (PuPubVO.getUFDouble_NullAsZero(outnum).doubleValue() > 0) {
			if (calvo != null) {
				CalYieldNumVO mvo = calvo.getMainnumvo();
				List<CalYieldNumVO> numlist = calvo.getNumList();
				List<CalYieldNumVO> otherlist = calvo.getOthernumList();
				/**
				 * 新建集合存放指标数据
				 */
				List<CalYieldNumVO> nlist = new ArrayList<CalYieldNumVO>();
				nlist.addAll(getIndexVos(calvo));
				for (int i = 0; i < nlist.size(); i++) {
					CalYieldNumVO nvo = nlist.get(i);
					if (nvo != null) {
						nvo.setNrawore(outnum);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 将原矿加工数量设置到计算vo
	 * 
	 * @author mlr
	 * @param outnum
	 * @param calvo
	 */
	public  void setRawOreNumToCalVO(UFDouble outnum,
			List<CalYieldVO> calvos) {
		if (calvos != null && calvos.size() > 0) {
			for (int i = 0; i < calvos.size(); i++) {
				setRawOreNumToCalVO(outnum, calvos.get(i));
			}
		}
	}

	/**
	 * topMap的key=选厂+生产线+班次+单据日期+矿区 +存货(管理id) 计算品位信息
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  Map<String, List<PubSampleBVO>> calGradeNum(ProSetParaVO para,UFBoolean iswater,UFBoolean isrepeatSel)
			throws BusinessException {
		IndexResultParaVO dexpara = new IndexResultParaVO();
		dexpara.setPk_factory(para.getPk_factory());
		dexpara.setPk_beltline(para.getPk_beltline());
		dexpara.setPk_classorder(para.getPk_classorder());
		dexpara.setPk_minarea(para.getPk_minarea());
		dexpara.setPk_corp(para.getPk_corp());
		dexpara.setDbilldate(para.getDbilldate());
		dexpara.setIswater(iswater);
		dexpara.setIsrepeatsel(isrepeatSel);
		dexpara.setPk_minetype(para.getPk_minetype());
	    dexpara.setPk_invmandoc1(para.getPk_invmandoc());
		return IndexResultHeler.getIndexResults(dexpara);
	}

	/**
	 * 计算加工出库量
	 * 
	 * @author mlr
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  Map<String, UFDouble> calOutNum(ProSetParaVO para)
			throws BusinessException {
		// topMap的key=选厂+生产线+班次+单据日期+矿区
		// 汇总原矿量：
		// 根据【生产线】取流程设置
		// 从流程设置取到属性为原矿的出库单据类型whl
		List<String> listtype = ProduceSetHelper.getProInType(para);
		// 按选厂、生产线、班次、单据日期过滤出库信息 ddk
		Map<String, List<XCHYBillVO>> billlist = getOutDatas(para, listtype);
		// 按率掉不属于原矿的表体 ddk
		Map<String, List<XCHYBillVO>> sbilllist = spiltOutDatas(para, billlist);
		// 按选厂、生产线、班次、单据日期、矿区、存货汇总出加工原矿量
		// 按选厂、生产线、班次、单据日期、矿区、存货分单
		// key=选厂、生产线、班次、单据日期、矿区、存货 lxh
		Map<String, List<PubGeneralBVO>> spiltmap = getSpiltMap(para, sbilllist);
		// key=选厂、生产线、班次、单据日期、矿区、存货
		// 计算原矿加工出库量 lxh
		Map<String, UFDouble> calmap = calspiltMap(spiltmap);
		//div water rate
		Map<String, List<PubSampleBVO>> wategrade=calGradeNum(para,UFBoolean.TRUE,UFBoolean.FALSE);
		if(wategrade!=null && wategrade.size()>0){
			for(String key:calmap.keySet()){
				if(wategrade.get(key)!=null && wategrade.get(key).size()>0){
					PubSampleBVO svo=wategrade.get(key).get(0);
					UFDouble waterrate=PuPubVO.getUFDouble_NullAsZero(svo.getNgrade());
					UFDouble rawnum=PuPubVO.getUFDouble_NullAsZero(calmap.get(key));
					rawnum=rawnum.multiply((new UFDouble(1.00000000).sub(waterrate.div(100))));
					calmap.put(key, rawnum);
				}
			}
		} 
		return calmap;
	}

	/**
	 * 计算出库数量
	 * 
	 * @author lxh
	 * @param spiltmap
	 * @return
	 */
	public  Map<String, UFDouble> calspiltMap(
			Map<String, List<PubGeneralBVO>> spiltmap) {
		Map<String, UFDouble> map = new HashMap<String, UFDouble>();
		Set<Entry<String, List<PubGeneralBVO>>> set = spiltmap.entrySet();
		Iterator<Entry<String, List<PubGeneralBVO>>> iterator = set.iterator();
		while (iterator.hasNext()) {
			UFDouble znum = new UFDouble(0.00000000);
			Entry<String, List<PubGeneralBVO>> entry = iterator.next();
			String key = (String) entry.getKey();
			List<PubGeneralBVO> publist = (List<PubGeneralBVO>) entry
					.getValue();
			if (publist == null || publist.size() == 0) {
				continue;
			}
			for (int i = 0; i < publist.size(); i++) {
				if (PuPubVO.getUFDouble_ZeroAsNull(
						publist.get(i).getNdryweight()).doubleValue() > 0) {
					znum = znum.add(publist.get(i).getNdryweight());
				} else {
					znum = znum.add(publist.get(i).getNwetweight());
				}
			}
			map.put(key, znum);
		}

		return map;
	}

	/**
	 * 按选厂、生产线、班次、单据日期、矿区、存货汇总出加工原矿量 按选厂、生产线、班次、单据日期、矿区、存货分单
	 * key=选厂、生产线、班次、单据日期、矿区、存货
	 * 
	 * @author lxh
	 * @param para
	 * @param sbilllist
	 * @return
	 * @throws BusinessException
	 */
	public  Map<String, List<PubGeneralBVO>> getSpiltMap(
			ProSetParaVO para, Map<String, List<XCHYBillVO>> sbilllist)
			throws BusinessException {
		Map<String, List<PubGeneralBVO>> map = new HashMap<String, List<PubGeneralBVO>>();
		String key = XcPubTool.getMapKey(para);

		Set<Entry<String, List<XCHYBillVO>>> set = sbilllist.entrySet();
		Iterator<Entry<String, List<XCHYBillVO>>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Entry<String, List<XCHYBillVO>> entry = iterator.next();
			List<XCHYBillVO> publist = (List<XCHYBillVO>) entry.getValue();

			for (int i = 0; i < publist.size(); i++) {
				PubGeneralBVO[] bvos = (PubGeneralBVO[]) publist.get(i)
						.getChildrenVO();
				for (int j = 0; j < bvos.length; j++) {
					String calkey = XcPubTool.getCalMapKey(para, bvos[j]
							.getPk_invmandoc());
					if (!map.containsKey(calkey)) {
						List<PubGeneralBVO> list = new ArrayList<PubGeneralBVO>();
						map.put(calkey, list);
						list.add(bvos[j]);
					} else {
						List<PubGeneralBVO> ylist = map.get(calkey);
						ylist.add(bvos[j]);

					}
				}
			}
		}
		return map;
	}

	/**
	 * 创建计算流程顶层的topMap
	 * 
	 * @author mlr
	 * @param paras
	 * @return
	 * @throws BusinessException
	 */
	public  void createTopMap(ProSetParaVO[] paras)
			throws BusinessException {
		if (paras == null || paras.length == 0)
			return;
		/**
		 * 清空topmap
		 */
		topmap.clear();

		for (int i = 0; i < paras.length; i++) {
			ProSetParaVO para = paras[i];
			createTopMap(para);
		}
	}

	/**
	 * 根据参数创建topMap(一个参数)
	 * 
	 * @param para
	 * @throws BusinessException
	 */
	public  void createTopMap(ProSetParaVO para) throws BusinessException {
		/**
		 * 获得topMapKey
		 */
		String key = XcPubTool.getMapKey(para);
		/**
		 * 创建流程计算map
		 */
		Map<ProSetEnum, List<CalYieldVO>> amap = new HashMap<ProSetEnum, List<CalYieldVO>>();
		topmap.put(key, amap);
		/**
		 * 得到加工流程
		 */
		List<ProSetEnum> enumlist = getEnumList();
		for (int n = 0; n < enumlist.size(); n++) {
			/**
			 * 按加工流程创建计算流程
			 */
			List<CalYieldVO> enumvolist = createCalFlow(enumlist.get(n), para);
			amap.put(enumlist.get(n), enumvolist);
		}
	}

	/**
	 * 按加工流程创建计算流程
	 * 
	 * @author mlr
	 * @param calflow
	 *            计算流程标示
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createCalFlow(ProSetEnum calFlow,
			ProSetParaVO para) throws BusinessException {
		switch (calFlow) {
		case RawOre:
			return createRawOreCalFlow(para);
		case Materials:
			return createMaterialsCalFlow(para);
		case Apowder:
			return createAPowderCalFlow(para);
		case Bpowder:
			return createBPowderCalFlow(para);
		case Cpowder:
			return createCPowderCalFlow(para);
		case Atails:
			return createATailsCalFlow(para);
		case Btails:
			return createBTailsCalFlow(para);
		case Ctails:
			return createCTailsCalFlow(para);
		case Gravity:
			return createGravityCalFlow(para);
		default:
			return null;
		}
	}
    /**
     * 创建重选工艺
     * @param para
     * @return
     * @throws BusinessException 
     */
	public List<CalYieldVO> createGravityCalFlow(ProSetParaVO para) throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllGravityProOutSetInfor(para);
		if (list != null && list.size() > 0) {
			CalYieldVO  mldvo=null;
			for (int i = 0; i < list.size(); i++) {
				/**
				 * 增加二次选矿代码
				 * 
				 */
			   if(PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==false&&list.size()==1){
				   yieldlist.add(createCalYieldVO(list.get(i), para));
			   }else if((PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==true&&list.size()>1) ){
				   mldvo=createCalYieldVO(list.get(i), para);
				   yieldlist.add(mldvo);
			   }else{
				   if (mldvo != null) {
						List<CalYieldVO> relist = mldvo.getOutsetvos();
						if (relist == null) {
							relist = new ArrayList<CalYieldVO>();
						}
						mldvo.setOutsetvos(relist);
						relist.add(createCalYieldVO(list.get(i), para));
					}else{
						yieldlist.add(createCalYieldVO(list.get(i), para));
					}
			   }	
			}
		}
		return yieldlist;
	}
	/**
	 * 创建原矿计算流程
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createRawOreCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB1VO> list = ProduceSetHelper
				.getAllRawOreProInSetInfor(para);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				yieldlist.add(createCalYieldVO(list.get(i), para));
			}
		}
		return yieldlist;
	}

	/**
	 * 创建材料计算流程
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createMaterialsCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB1VO> list = ProduceSetHelper
				.getAllMaterialsProInSetInfor(para);
		if (list != null && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				yieldlist.add(createCalYieldVO(list.get(i), para));
			}
		}
		return yieldlist;
	}

	/**
	 * 创建计算vo
	 * 
	 * @param vo
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  CalYieldVO createCalYieldVO(ProductSetBB1VO vo,
			ProSetParaVO para) throws BusinessException {
		CalYieldVO yvo = new CalYieldVO();
		yvo.setInsetvo(vo);
		createIndexInfor(yvo, para);
		/**
		 * 校验化验指标定义是否存在
		 */
		if( (yvo.getMainnumvo()==null) && (yvo.getNumList()==null || yvo.getNumList().size()==0)
				 && (yvo.getOthernumList()==null || yvo.getOthernumList().size()==0)){
			String pk_invbasdoc=vo.getPk_invbasdoc();
			String pk_deptdoc=para.getPk_minarea();
			String invcode=XcPubTool.getInvcodeByPk(pk_invbasdoc);
			String invname=XcPubTool.getInvNameByPk(pk_invbasdoc);			
			String deptcode=XcPubTool.getDepCodeByPk(pk_deptdoc);
			String deptname=XcPubTool.getDepNameByPk(pk_deptdoc);		
			throw new BusinessException("化验指标未定义存货名称为：["+invname+"]编码为：["+invcode+"]，" +
					"部门名称为：["+deptname+"]编码为：["+deptcode+"]");
		}	
//		/**
//		 * 校验猪主指标不能为空
//		 */
//		if( (yvo.getMainnumvo()==null) ){
//			String pk_invbasdoc=vo.getPk_invbasdoc();
//			String pk_deptdoc=para.getPk_minarea();
//			String invcode=XcPubTool.getInvcodeByPk(pk_invbasdoc);
//			String invname=XcPubTool.getInvNameByPk(pk_invbasdoc);			
//			String deptcode=XcPubTool.getDepCodeByPk(pk_deptdoc);
//			String deptname=XcPubTool.getDepNameByPk(pk_deptdoc);		
//			throw new BusinessException("化验指标定义主指标未定义存货名称为：["+invname+"]编码为：["+invcode+"]，" +
//					"部门名称为：["+deptname+"]编码为：["+deptcode+"]");
//		}
		return yvo;
	}

	/**
	 * 创建计算vo
	 * 
	 * @param vo
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  CalYieldVO createCalYieldVO(ProductSetBB2VO vo,
			ProSetParaVO para) throws BusinessException {
		CalYieldVO yvo = new CalYieldVO();
		yvo.setOutsetvo(vo);
		createIndexInfor(yvo, para);
		/**
		 * 校验化验指标定义是否存在
		 */
		if( (yvo.getMainnumvo()==null) && (yvo.getNumList()==null || yvo.getNumList().size()==0)
				 && (yvo.getOthernumList()==null || yvo.getOthernumList().size()==0)){
			String pk_invbasdoc=vo.getPk_invbasdoc();
			String pk_deptdoc=para.getPk_minarea();
			String invcode=XcPubTool.getInvcodeByPk(pk_invbasdoc);
			String invname=XcPubTool.getInvNameByPk(pk_invbasdoc);			
			String deptcode=XcPubTool.getDepCodeByPk(pk_deptdoc);
			String deptname=XcPubTool.getDepNameByPk(pk_deptdoc);		
			throw new BusinessException("化验指标未定义存货名称为：["+invname+"]编码为：["+invcode+"]，" +
					"部门名称为：["+deptname+"]编码为：["+deptcode+"]");
		}	
//		/**
//		 * 校验主指标不能为空
//		 */
//		if( (yvo.getMainnumvo()==null) ){
//			String pk_invbasdoc=vo.getPk_invbasdoc();
//			String pk_deptdoc=para.getPk_minarea();
//			String invcode=XcPubTool.getInvcodeByPk(pk_invbasdoc);
//			String invname=XcPubTool.getInvNameByPk(pk_invbasdoc);			
//			String deptcode=XcPubTool.getDepCodeByPk(pk_deptdoc);
//			String deptname=XcPubTool.getDepNameByPk(pk_deptdoc);		
//			throw new BusinessException("化验指标定义主指标未定义存货名称为：["+invname+"]编码为：["+invcode+"]，" +
//					"部门名称为：["+deptname+"]编码为：["+deptcode+"]");
//		}	
		return yvo;
	}

	/**
	 * 创建一道工艺计算流程(精矿)
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createAPowderCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllAPowderProOutSetInfor(para);
		if (list != null && list.size() > 0) {
			CalYieldVO  mldvo=null;
			for (int i = 0; i < list.size(); i++) {
				/**
				 * 增加二次选矿代码
				 * 
				 */
			   if(PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==false&&list.size()==1){
				   yieldlist.add(createCalYieldVO(list.get(i), para));
			   }else if((PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==true&&list.size()>1) ){
				   mldvo=createCalYieldVO(list.get(i), para);
				   yieldlist.add(mldvo);
			   }else{
				   if (mldvo != null) {
						List<CalYieldVO> relist = mldvo.getOutsetvos();
						if (relist == null) {
							relist = new ArrayList<CalYieldVO>();
						}
						mldvo.setOutsetvos(relist);
						relist.add(createCalYieldVO(list.get(i), para));
					}else{
						yieldlist.add(createCalYieldVO(list.get(i), para));
					}
			   }	
			}
		}
		return yieldlist;
	}

	/**
	 * 创建二道工艺计算流程（精矿）
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createBPowderCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllBPowderProOutSetInfor(para);
		if (list != null && list.size() > 0) {
			CalYieldVO  mldvo=null;
			for (int i = 0; i < list.size(); i++) {
				/**
				 * 增加二次选矿代码
				 * 
				 */
			   if(PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==false&&list.size()==1){
				   yieldlist.add(createCalYieldVO(list.get(i), para));
			   }else if((PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==true&&list.size()>1) ){
				   mldvo=createCalYieldVO(list.get(i), para);
				   yieldlist.add(mldvo);
			   }else{
				   if (mldvo != null) {
						List<CalYieldVO> relist = mldvo.getOutsetvos();
						if (relist == null) {
							relist = new ArrayList<CalYieldVO>();
						}
						mldvo.setOutsetvos(relist);
						relist.add(createCalYieldVO(list.get(i), para));
					}else{
						yieldlist.add(createCalYieldVO(list.get(i), para));
					}
			   }	
			}
		}
		return yieldlist;
	}

	/**
	 * 创建三道工艺计算流程（精矿）
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createCPowderCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllCPowderProOutSetInfor(para);
		CalYieldVO  mldvo=null;
		for (int i = 0; i < list.size(); i++) {
			/**
			 * 增加二次选矿代码
			 * 
			 */
		   if(PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==false&&list.size()==1){
			   yieldlist.add(createCalYieldVO(list.get(i), para));
		   }else if((PuPubVO.getUFBoolean_NullAs(list.get(i).getUisreselect(), new UFBoolean(false)).booleanValue()==true&&list.size()>1) ){
			   mldvo=createCalYieldVO(list.get(i), para);
			   yieldlist.add(mldvo);
		   }else{
			   if (mldvo != null) {
					List<CalYieldVO> relist = mldvo.getOutsetvos();
					if (relist == null) {
						relist = new ArrayList<CalYieldVO>();
					}
					mldvo.setOutsetvos(relist);
					relist.add(createCalYieldVO(list.get(i), para));
				}else{
					yieldlist.add(createCalYieldVO(list.get(i), para));
				}
		   }	
		}
		return yieldlist;
	}

	/**
	 * 创建一道工艺计算流程（尾矿）
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createATailsCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllATailsProOutSetInfor(para);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				yieldlist.add(createCalYieldVO(list.get(i), para));
			}
		}
		return yieldlist;
	}

	/**
	 * 创建二道工艺计算流程（尾矿）
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createBTailsCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllBTailsProOutSetInfor(para);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				yieldlist.add(createCalYieldVO(list.get(i), para));
			}
		}
		return yieldlist;
	}

	/**
	 * 创建三道工艺计算流程（尾矿）
	 * 
	 * @param para
	 * @return
	 * @throws BusinessException
	 */
	public  List<CalYieldVO> createCTailsCalFlow(ProSetParaVO para)
			throws BusinessException {
		List<CalYieldVO> yieldlist = new ArrayList<CalYieldVO>();
		List<ProductSetBB2VO> list = ProduceSetHelper
				.getAllCTailsProOutSetInfor(para);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				yieldlist.add(createCalYieldVO(list.get(i), para));
			}

		}
		return yieldlist;
	}

	public  void createIndexInfor(CalYieldVO yvo, ProSetParaVO para)
			throws BusinessException { // 设置主指标信息
		createMainIndexInfor(yvo, para);
		// 设置副指标信息
		createInvIndexInfor(yvo, para);
		// 设置其他指标信息
		createOtherIndexInfor(yvo, para);
	}

	public  void createOtherIndexInfor(CalYieldVO yvo, ProSetParaVO para)
			throws BusinessException {
		// 取副指标：
		// 根据【存货】、【矿区】从化验指标定义取存货化验的指标信息
		IndexParaVO inpara = getIndexParaVO(yvo, para);
		List<LabIndexSetBVO> list = IndexFineHeler.getOtherIndexFine(inpara);
		List<CalYieldNumVO> numList = getCalYieldNumVO(list);
		yvo.setOthernumList(numList);
	}

	// 设置副指标信息
	public  void createInvIndexInfor(CalYieldVO yvo, ProSetParaVO para)
			throws BusinessException {
		// 取副指标：
		// 根据【存货】、【矿区】从化验指标定义取存货化验的指标信息
		IndexParaVO inpara = getIndexParaVO(yvo, para);
		List<LabIndexSetBVO> list = IndexFineHeler.getInvIndexFine(inpara);
		List<CalYieldNumVO> numList = getCalYieldNumVO(list);
		yvo.setNumList(numList);
	}

	/**
	 * 参数类型转换流程设置参数VO->指标参数VO
	 * 
	 * @param yvo
	 * @param para
	 * @return
	 */
	public  IndexParaVO getIndexParaVO(CalYieldVO yvo, ProSetParaVO para) {
		IndexParaVO inpara = new IndexParaVO();
		inpara.setPk_corp(para.getPk_corp());
		inpara.setPk_minarea(para.getPk_minarea());
		inpara.setPk_invmandoc1(para.getPk_invmandoc());
		ProductSetBB1VO bbvo = getInSetInfor(yvo);
		if (bbvo != null) {
			inpara.setPk_invmandoc(bbvo.getPk_invmandoc());
		} else {
			ProductSetBB2VO bbvo1 = getOutSetInfor(yvo);
			if (bbvo1 != null) {
				inpara.setPk_invmandoc(bbvo1.getPk_invmandoc());
			}
		}
		return inpara;
	}

	/**
	 * 类型转换指标定义vo->产量计算vo
	 * 
	 * @param list
	 * @return
	 */
	public  List<CalYieldNumVO> getCalYieldNumVO(List<LabIndexSetBVO> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<CalYieldNumVO> numlist = new ArrayList<CalYieldNumVO>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				numlist.add(getCalYieldNumVO(list.get(i)));
			}
		}
		return numlist;
	}

	/**
	 * 类型转换指标定义vo->产量计算vo
	 * 
	 * @param list
	 * @return
	 */
	public  CalYieldNumVO getCalYieldNumVO(LabIndexSetBVO vo) {
		if (vo == null) {
			return null;
		}
		CalYieldNumVO numvo = new CalYieldNumVO();
		/**
		 * 设置金属元素
		 */
		numvo.setPk_invbasdoc(vo.getPk_invbasdoc());
		numvo.setPk_invmandoc(vo.getPk_invmandoc());
		/**
		 * 设置指标类型
		 */
		numvo.setItype(vo.getItype());
		return numvo;
	}

	public  ProductSetBB1VO getInSetInfor(CalYieldVO vo) {
		if (vo == null) {
			return null;
		}
		return vo.getInsetvo();
	}

	public  ProductSetBB2VO getOutSetInfor(CalYieldVO vo) {
		if (vo == null) {
			return null;
		}
		return vo.getOutsetvo();
	}

	// 设置主指标信息
	public  void createMainIndexInfor(CalYieldVO yvo, ProSetParaVO para)
			throws BusinessException {
		// 取产出精粉、尾矿:
		// 根据【生产线】取流程设置
		// 从流程设置取到产出精粉类型、主指标、回收率公式、精粉产量公式、金属量产量公式
		IndexParaVO inpara = getIndexParaVO(yvo, para);
		LabIndexSetBVO invo = IndexFineHeler.getMainIndexFine(inpara);
		if (invo == null)
			return;
		CalYieldNumVO calvo = getCalYieldNumVO(invo);
		yvo.setMainnumvo(calvo);
	}

	/**
	 * 获得枚举list
	 * 
	 * @return
	 */
	public  List<ProSetEnum> getEnumList() {
		List<ProSetEnum> list = new ArrayList<ProSetEnum>();
		list.add(ProSetEnum.RawOre);
		list.add(ProSetEnum.Apowder);
		list.add(ProSetEnum.Bpowder);
		list.add(ProSetEnum.Cpowder);
		list.add(ProSetEnum.Atails);
		list.add(ProSetEnum.Btails);
		list.add(ProSetEnum.Ctails);
		list.add(ProSetEnum.Gravity);
		return list;
	}

	/**
	 * 过滤掉不属于原矿的表体
	 * 
	 * @param billlist
	 * @author whl
	 */
	public  Map<String, List<XCHYBillVO>> spiltOutDatas(
			ProSetParaVO para, Map<String, List<XCHYBillVO>> billlist)
			throws BusinessException {
		// 1 判断传过来的map是否为空
		// 2 遍历map的 进行筛选，得到投入是原矿的
		// 3 将得到的值放到新的map中 返回
		Map<String, List<XCHYBillVO>> map = new HashMap<String, List<XCHYBillVO>>();
		List<XCHYBillVO> translist = new ArrayList<XCHYBillVO>();
		if (para != null) {
			if (PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp()) != null) {
				List<String> invman = new ArrayList<String>();
				List<ProductSetBB1VO> orelist = ProduceSetHelper
						.getAllRawOreProInSetInfor(para);
				if(orelist==null || orelist.size()==0){
					throw new BusinessException("生产流程设置没有对应原矿");
				}
				if (orelist != null && orelist.size() != 0) {
					for (int i = 0; i < orelist.size(); i++) {
						if (PuPubVO.getUFBoolean_NullAs(
								orelist.get(i).getUisore(),
								new UFBoolean(false)).booleanValue()) {
							if (PuPubVO.getString_TrimZeroLenAsNull(orelist
									.get(i).getPk_invmandoc()) != null) {
								invman.add(PuPubVO
										.getString_TrimZeroLenAsNull(orelist
												.get(i).getPk_invmandoc()));
							}
						}
					}
				}
				if (billlist != null && billlist.size() != 0) {
					for (String key : billlist.keySet()) {
						List<XCHYBillVO> templist = billlist.get(key);
						for (int i = 0; i < templist.size(); i++) {
							XCHYBillVO vo = templist.get(i);
							XCHYBillVO aggvo = new XCHYBillVO();
							aggvo.setParentVO(vo.getParentVO());
							CircularlyAccessibleValueObject[] bodyvos = vo
									.getChildrenVO();
							List<CircularlyAccessibleValueObject> list = new ArrayList<CircularlyAccessibleValueObject>();
							if (bodyvos != null && bodyvos.length != 0) {
								Class classname = bodyvos[0].getClass();
								for (int j = 0; j < bodyvos.length; j++) {

									for (int k = 0; k < invman.size(); k++) {
										if (bodyvos[j].getAttributeValue(
												"pk_invmandoc").equals(
												invman.get(k))) {
											if (bodyvos[j] != null) {
												list.add(bodyvos[j]);
											}
										}
									}
								}
								aggvo
										.setChildrenVO(list
												.toArray((CircularlyAccessibleValueObject[]) java.lang.reflect.Array
														.newInstance(classname,
																0)));

							}

							translist.add(aggvo);
						}
						map.put(key, translist);
					}
				}
				return map;
			} else {
				throw new BusinessException("公司为空");
			}
		} else {
			throw new BusinessException("参数为空");
		}
	}

	/**
	 * 根据选厂、生产线、班次、单据日期、单据类型取到加工出库信息
	 * 
	 * @param para
	 *            可以取到选厂、生产线、班次、单据日期
	 * @param listtype
	 *            可以取到单据类型
	 * @author ddk
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, List<XCHYBillVO>> getOutDatas(ProSetParaVO para,
			List<String> listtype) throws BusinessException {
		// key =单据类型
		// PubGeneralBVO
		// PubGeneralHVO
		// XcPubTool.getBillVOByTypeAndPk("当前单据类型", "表头id");
		Map<String, List<XCHYBillVO>> map = new HashMap<String, List<XCHYBillVO>>();
		if (listtype != null && listtype.size() != 0) {
			String factory = PuPubVO.getString_TrimZeroLenAsNull(para.getPk_factory());
			String beltline = PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline());
			String classorder = PuPubVO.getString_TrimZeroLenAsNull(para.getPk_classorder());
			String minarea = PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea());
			String corp = PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp());
			String minetype= PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minetype());
			String pk_invmandoc1=PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc());
			UFDate dbilldate=para.getDbilldate();
			for (int i = 0; i < listtype.size(); i++) {
				String billtype = PuPubVO.getString_TrimZeroLenAsNull(listtype
						.get(i));
				String sql = "select pk_general_h from xcgl_general_h h "
						+ "where h.pk_billtype='" + billtype + "'";
				if (corp != null) {
					sql = sql + " and h.pk_corp='" + corp + "'";
				}
				if (factory != null) {
					sql = sql + " and h.pk_factory='" + factory + "'";
				}
				if (beltline != null) {
					sql = sql + " and h.pk_beltline='" + beltline + "'";
				}
				if (classorder != null) {
					sql = sql + " and h.pk_classorder='" + classorder + "'";
				}
				if (minarea != null) {
					sql = sql + " and h.pk_minarea='" + minarea + "'";
				}
				if(dbilldate!=null){
					sql = sql + " and h.dbilldate='" + dbilldate.toString() + "'";
				}
				if(minetype!=null){
					sql = sql + " and h.vreserve1='" + minetype + "'";
				}
				if(pk_invmandoc1!=null){
					sql = sql + " and h.vdef20='" + pk_invmandoc1 + "'";
				}
				List<Object[]> o = null;
				List<XCHYBillVO> list1 = new ArrayList<XCHYBillVO>();
				try {
					o = (List<Object[]>) XCZmPubDAO.getDAO().executeQuery(
							sql + " and isnull(h.dr,0)=0",
							new ArrayListProcessor());

					if (o != null && o.size() != 0) {
						for (int n = 0; n < o.size(); n++) {
							Object[] objs = o.get(n);
							if (objs != null && objs.length > 0) {
								String pk = PuPubVO
										.getString_TrimZeroLenAsNull(objs[0]);
								XCHYBillVO[] conn = (XCHYBillVO[]) XcPubTool
										.getBillVOByTypeAndPk(billtype, pk);
								if (conn != null && conn.length > 0) {
									for (int j = 0; j < conn.length; j++) {
										list1.add(conn[j]);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(e);
				}
				map.put(listtype.get(i), list1);
			}
		}
		return map;
	}
}
