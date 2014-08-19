package nc.vo.xcgl.pub.tool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.flouryield.FlouryieldBVO;
import nc.vo.xcgl.produceset.ProductSetBB2VO;
import nc.vo.xcgl.pub.bill.CalYieldNumVO;
import nc.vo.xcgl.pub.bill.CalYieldVO;
import nc.vo.xcgl.pub.bill.ProSetEnum;
import nc.vo.xcgl.pub.bill.ProSetFormulaVO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xew.pub.XewPubTool;

public class CalProduceTool {
	
	//save calculte date from topmap
	public  List<FlouryieldBVO> templist=new ArrayList<FlouryieldBVO>();
	//prase topmap
	public  Map<String, Map<ProSetEnum, List<CalYieldVO>>> topmap=new HashMap<String, Map<ProSetEnum,List<CalYieldVO>>>();
	//parse Parameters
	public ProSetParaVO[] paras=new ProSetParaVO[0];
	//para+stock content
	Map<String,UFDouble> grademap=new HashMap<String, UFDouble>();
	/**
	 * 解析topMap获得产量VO
	 * @param topMap
	 * @return
	 */
	public FlouryieldBVO[] parseTopMap(Map<String, Map<ProSetEnum, List<CalYieldVO>>> topMap,ProSetParaVO[] paras) throws BusinessException {
		if (paras == null || paras.length == 0) {
			throw new BusinessException("参数为空");
		}
		if (topMap == null || topMap.size() == 0) {
			throw new BusinessException("公式计算结果信息为空");
		}
		// clear templist
		templist.clear();
		// clear topmap
		topmap.clear();
		topmap = topMap;
		this.paras = paras;
		for (int i = 0; i < paras.length; i++) {
			List<ProSetEnum> enlist = getEnumList();
			for (ProSetEnum env : enlist) {
				parseTopMap(env, paras[i]);
			}
		}
		XcPubTool.dealLabGradeAndIndex(templist);
		return templist.toArray(new FlouryieldBVO[0]);
	}
	
    /**
     * parse toepmap
     * @param env
     * @param proSetParaVO
     * @throws BusinessException 
     */
	public void parseTopMap(ProSetEnum env, ProSetParaVO para) throws BusinessException {
			switch (env) {
			case RawOre:
				 parseRawOreTomMap(para);
				 break;
			case Materials:
				 parseMaterialsTopMap(para);
				 break;
			case Apowder:
				 parseAPowderTopMap(para);
				 break;
			case Bpowder:
				 parseBPowderTopMap(para);
				 break;
			case Cpowder:
				 parseCPowderTopMap(para);
				 break;
			case Atails:
				 parseATailsTopMap(para);
				 break;
			case Btails:
				 parseBTailsTopMap(para);
				 break;
			case Ctails:
				 parseCTailsTopMap(para);
				 break;
			case Gravity:
				 parseGravityTopMap(para);
				 break;
			default:
				return ;
			}		
	}

	
	public void parseGravityTopMap(ProSetParaVO para) throws BusinessException {	
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
	    List<CalYieldVO>  gravitylist=map.get(ProSetEnum.Gravity);
        if(gravitylist!=null&&gravitylist.size()!=0){
			for(int j=0;j<gravitylist.size();j++){
				CalYieldVO vo=gravitylist.get(j);
				//get powder form output settings
				ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
					if (bb2vo != null) {
						CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Gravity,bb2vo, mainnumvo,para));
						//negative index information
					    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Gravity,bb2vo, anumList.get(n),para));
							}
						}
						List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo = othernumList.get(n);
								String key = "Gravity"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
							}
						}
				
			}         
		}
	}
	}

	public void parseCTailsTopMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
	
		List<CalYieldVO>  ctailslist=map.get(ProSetEnum.Ctails);
        if(ctailslist!=null&&ctailslist.size()!=0){
			for(int j=0;j<ctailslist.size();j++){
				CalYieldVO vo=ctailslist.get(j);
				//get powder form output settings
				ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
					if (bb2vo != null) {
						CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Ctails, bb2vo, mainnumvo,para));
						templist.get(templist.size()-1).setNcontent3(mainnumvo.getNgrade());
						//negative index information
					    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Ctails,bb2vo, anumList.get(n),para));
								templist.get(templist.size()-1).setNcontent3( anumList.get(n).getNgrade());
							}
						}
						List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo = othernumList.get(n);
								String key = "Ctail"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
							}
						}
				
			}         
		}
	}
	}

	public void parseBTailsTopMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
		List<CalYieldVO>  btailslist=map.get(ProSetEnum.Btails);
        if(btailslist!=null&&btailslist.size()!=0){
    			for(int j=0;j<btailslist.size();j++){
    				CalYieldVO vo=btailslist.get(j);
    				//get powder form output settings
    				ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
    					if (bb2vo != null) {
    						CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
    						//create calculate data
    						//add apbvo to templist
    						templist.add(createFlourieldVO(ProSetEnum.Btails,bb2vo, mainnumvo,para));
    						//negative index information
    					    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
    						if (anumList != null && anumList.size() != 0) {
    							for (int n = 0; n < anumList.size(); n++) {						
    								templist.add(createFlourieldVO(ProSetEnum.Btails,bb2vo, anumList.get(n),para));
    							}
    						}
    						List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
    						if (othernumList != null && othernumList.size() != 0) {
    							/**
    							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
    							 * value=品味值 存放到grademap中
    							 */
    							for (int n = 0; n < othernumList.size(); n++) {
    								CalYieldNumVO cvo = othernumList.get(n);
    								String key = "Btail"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
    										+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
    								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
    							}
    						}
    				
    			}         
			}
		}
	}

	public void parseATailsTopMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
	
		List<CalYieldVO>  atailslist=map.get(ProSetEnum.Atails);
        if(atailslist!=null&&atailslist.size()!=0){

			for(int j=0;j<atailslist.size();j++){
				CalYieldVO vo=atailslist.get(j);
				//get powder form output settings
				ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
					if (bb2vo != null) {
						CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Atails,bb2vo, mainnumvo,para));
						//negative index information
					    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Atails,bb2vo, anumList.get(n),para));
							}
						}
						List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo = othernumList.get(n);
								String key = "Atail"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
							}
						}
					}
			}
        }
	
	}

	public void parseCPowderTopMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
	
		List<CalYieldVO>  cpowderlist=map.get(ProSetEnum.Cpowder);
        if(cpowderlist!=null&&cpowderlist.size()!=0){
			for(int j=0;j<cpowderlist.size();j++){
				CalYieldVO vo=cpowderlist.get(j);
				//get powder form output settings
				ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
					if (bb2vo != null) {
						CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Cpowder,bb2vo, mainnumvo,para));
						templist.get(templist.size()-1).setNcontent3(mainnumvo.getNgrade());
						//negative index information
					    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Cpowder,bb2vo, anumList.get(n),para));
								templist.get(templist.size()-1).setNcontent3(anumList.get(n).getNgrade());
							}
						}
						List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo = othernumList.get(n);
								String key = "Cpowder"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
							}
						}
						//calculate repeat select powder	
						calCpowderRepeatSePower(vo,para);
				
			}         
		}
	}
	}

	public void calCpowderRepeatSePower(CalYieldVO vo, ProSetParaVO para) {

		if(vo.getOutsetvos()!=null && vo.getOutsetvos().size()>0){
			List<FlouryieldBVO> flist=new ArrayList<FlouryieldBVO>();
		  	int count=0;
		    if(vo.getMainnumvo()!=null){
		    	count=count+1;
		    }
		    if(vo.getNumList()!=null){
		    	count=count+vo.getNumList().size();
		    }
			if(templist.size()>0){
				for(int i=1;i<count+1;i++){
        		   templist.get(templist.size()-i).setItype(0);
        		   flist.add( templist.get(templist.size()-i));
				}
        	}  
            for(int i=0;i<vo.getOutsetvos().size();i++){
    			CalYieldVO cvo=vo.getOutsetvos().get(i);
				//get powder form output settings
				ProductSetBB2VO bb2vo1=cvo.getOutsetvo();//产出信息->精粉
					if (bb2vo1 != null) {
						CalYieldNumVO mainnumvo = cvo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Cpowder,bb2vo1, mainnumvo,para));
						if(PuPubVO.getUFBoolean_NullAs(bb2vo1.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
							   templist.get(templist.size()-1).setItype(1);
							   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,mainnumvo);	
							}else{
							   templist.get(templist.size()-1).setItype(2);
							   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,mainnumvo);	
							}
						//negative index information
					    List<CalYieldNumVO> anumList = cvo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Cpowder,bb2vo1, anumList.get(n),para));
								if(PuPubVO.getUFBoolean_NullAs(bb2vo1.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
									   templist.get(templist.size()-1).setItype(1);
									   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,anumList.get(n));	
									}else{
									   templist.get(templist.size()-1).setItype(2);
									   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,anumList.get(n));	
									}
							}
						}
						List<CalYieldNumVO> othernumList = cvo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo1 = othernumList.get(n);
								String key = "Cpowder"+ PuPubVO.getString_TrimZeroLenAsNull(cvo1.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo1.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo1.getNgrade()));
							}
						}
				}
            }
		}		
	}

	public void parseBPowderTopMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
		List<CalYieldVO>  bpowderlist=map.get(ProSetEnum.Bpowder);
		if(bpowderlist!=null&&bpowderlist.size()!=0){
			for(int j=0;j<bpowderlist.size();j++){
				CalYieldVO vo=bpowderlist.get(j);
				//get powder form output settings
				ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
					if (bb2vo != null) {
						CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Bpowder,bb2vo, mainnumvo,para));
						//negative index information
					    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Bpowder,bb2vo, anumList.get(n),para));
							}
						}
						List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo = othernumList.get(n);
								String key = "Bpowder"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
							}
						}
				}
				//calculate repeat select powder	
				calBpowderRepeatSePower(vo,para);
			}         
		}
	}

	public void calBpowderRepeatSePower(CalYieldVO vo, ProSetParaVO para) {

		if(vo.getOutsetvos()!=null && vo.getOutsetvos().size()>0){
			List<FlouryieldBVO> flist=new ArrayList<FlouryieldBVO>();
		  	int count=0;
		    if(vo.getMainnumvo()!=null){
		    	count=count+1;
		    }
		    if(vo.getNumList()!=null){
		    	count=count+vo.getNumList().size();
		    }
			if(templist.size()>0){
				for(int i=1;i<count+1;i++){
        		   templist.get(templist.size()-i).setItype(0);
        		   flist.add( templist.get(templist.size()-i));
				}
        	}  
            for(int i=0;i<vo.getOutsetvos().size();i++){
    			CalYieldVO cvo=vo.getOutsetvos().get(i);
				//get powder form output settings
				ProductSetBB2VO bb2vo1=cvo.getOutsetvo();//产出信息->精粉
					if (bb2vo1 != null) {
						CalYieldNumVO mainnumvo = cvo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Bpowder,bb2vo1, mainnumvo,para));
						if(PuPubVO.getUFBoolean_NullAs(bb2vo1.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
							   templist.get(templist.size()-1).setItype(1);
							   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,mainnumvo);	
							}else{
							   templist.get(templist.size()-1).setItype(2);
							   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,mainnumvo);	
							}
						//negative index information
					    List<CalYieldNumVO> anumList = cvo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Bpowder,bb2vo1, anumList.get(n),para));
								if(PuPubVO.getUFBoolean_NullAs(bb2vo1.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
									   templist.get(templist.size()-1).setItype(1);
									   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,anumList.get(n));	
									}else{
									   templist.get(templist.size()-1).setItype(2);
									   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,anumList.get(n));	
									}
							}
						}
						List<CalYieldNumVO> othernumList = cvo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo1 = othernumList.get(n);
								String key = "Bpowder"+ PuPubVO.getString_TrimZeroLenAsNull(cvo1.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo1.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo1.getNgrade()));
							}
						}
				}
            }
		}		
	}

	public void parseAPowderTopMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
		//get  powder form topmap
		List<CalYieldVO>  apowderlist=map.get(ProSetEnum.Apowder);
		if(apowderlist!=null&&apowderlist.size()!=0){
		for(int j=0;j<apowderlist.size();j++){
			CalYieldVO vo=apowderlist.get(j);
			//get powder form output settings
			ProductSetBB2VO bb2vo=vo.getOutsetvo();//产出信息->精粉
				if (bb2vo != null) {
					CalYieldNumVO mainnumvo = vo.getMainnumvo();// 主指标信息
					//create calculate data
					//add apbvo to templist
					templist.add(createFlourieldVO(ProSetEnum.Apowder,bb2vo, mainnumvo,para));
					//negative index information
				    List<CalYieldNumVO> anumList = vo.getNumList();// 副指标信息
					if (anumList != null && anumList.size() != 0) {
						for (int n = 0; n < anumList.size(); n++) {						
							templist.add(createFlourieldVO(ProSetEnum.Apowder,bb2vo, anumList.get(n),para));
						}
					}
					List<CalYieldNumVO> othernumList = vo.getOthernumList();// 其他指标信息
					if (othernumList != null && othernumList.size() != 0) {
						/**
						 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
						 * value=品味值 存放到grademap中
						 */
						for (int n = 0; n < othernumList.size(); n++) {
							CalYieldNumVO cvo = othernumList.get(n);
							String key = "Apowder"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
									+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
							grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
						}
					}
				}
				//calculate repeat select powder	
				calApowderRepeatSePower(vo,para);
		  }	  
		}
	}

	public void calApowderRepeatSePower(CalYieldVO vo, ProSetParaVO para) {
		if(vo.getOutsetvos()!=null && vo.getOutsetvos().size()>0){
			List<FlouryieldBVO> flist=new ArrayList<FlouryieldBVO>();
		  	int count=0;
		    if(vo.getMainnumvo()!=null){
		    	count=count+1;
		    }
		    if(vo.getNumList()!=null){
		    	count=count+vo.getNumList().size();
		    }
			if(templist.size()>0){
				for(int i=1;i<count+1;i++){
        		   templist.get(templist.size()-i).setItype(0);
        		   flist.add( templist.get(templist.size()-i));
				}
        	}  
            for(int i=0;i<vo.getOutsetvos().size();i++){              	
    			CalYieldVO cvo=vo.getOutsetvos().get(i);
				//get powder form output settings
				ProductSetBB2VO bb2vo1=cvo.getOutsetvo();//产出信息->精粉
					if (bb2vo1 != null) {
						CalYieldNumVO mainnumvo = cvo.getMainnumvo();// 主指标信息
						//create calculate data
						//add apbvo to templist
						templist.add(createFlourieldVO(ProSetEnum.Apowder,bb2vo1, mainnumvo,para));
						if(PuPubVO.getUFBoolean_NullAs(bb2vo1.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
						   templist.get(templist.size()-1).setItype(1);
						   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,mainnumvo);						   						   
						}else{
						   templist.get(templist.size()-1).setItype(2);
						   setSelectInfor1(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,mainnumvo);	
						}
						//negative index information
					    List<CalYieldNumVO> anumList = cvo.getNumList();// 副指标信息
						if (anumList != null && anumList.size() != 0) {
							for (int n = 0; n < anumList.size(); n++) {						
								templist.add(createFlourieldVO(ProSetEnum.Apowder,bb2vo1, anumList.get(n),para));
								if(PuPubVO.getUFBoolean_NullAs(bb2vo1.getUispowder(), UFBoolean.FALSE).booleanValue()==true){
									   templist.get(templist.size()-1).setItype(1);
									   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,anumList.get(n));	
									}else{
									   templist.get(templist.size()-1).setItype(2);
									   setSelectInfor(flist,templist.get(templist.size()-1),ProSetEnum.Apowder,anumList.get(n));	
									}
							}
						}
						List<CalYieldNumVO> othernumList = cvo.getOthernumList();// 其他指标信息
						if (othernumList != null && othernumList.size() != 0) {
							/**
							 *遍历其它指标信息集合，并将 其他指标信息以 key=工艺顺序+金属元素管理主键+金属元素基本主键
							 * value=品味值 存放到grademap中
							 */
							for (int n = 0; n < othernumList.size(); n++) {
								CalYieldNumVO cvo1 = othernumList.get(n);
								String key = "Apowder"+ PuPubVO.getString_TrimZeroLenAsNull(cvo1.getPk_invmandoc())
										+ PuPubVO.getString_TrimZeroLenAsNull(cvo1.getPk_invbasdoc()+para);
								grademap.put(key,PuPubVO.getUFDouble_NullAsZero(cvo1.getNgrade()));
							}
						}
				}
            }
		}
		
	}
    /**
     * 
       设置二次选择的信息
     * @param flist
     * @param index 
     * @param bb2vo1 
     * @param para 
     * @param flouryieldBVO
     * @param apowder
     */
	public void setSelectInfor(List<FlouryieldBVO> flist,
			FlouryieldBVO fvo, ProSetEnum env, CalYieldNumVO index) {
        String pk_invmandoc=fvo.getPk_manindex();
        boolean isExist=false;
        for(int i=0;i<flist.size();i++){
           if(pk_invmandoc.equals(flist.get(i).getPk_manindex())){
        	   isExist=true;
        	   fvo.setNcrudes(flist.get(i).getNoutput());
        	   fvo.setNcrudescontent(flist.get(i).getNcontent());
        	   if(ProSetEnum.Apowder==env){
        		 fvo.setNcontent1(flist.get(i).getNcontent());
        	   }else if(ProSetEnum.Bpowder==env){
        		 fvo.setNcontent2(flist.get(i).getNcontent());  
        	   }else if(ProSetEnum.Cpowder==env){
        		 fvo.setNcontent3(flist.get(i).getNcontent());  
        	   }
          	   fvo.setNrecover(PuPubVO.getUFDouble_NullAsZero(index.getNrecoveryrate()));
               fvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(index.getNmetal()));
               fvo.setNoutput(PuPubVO.getUFDouble_NullAsZero(index.getNflouryield()));
               break;
           }	
        }
        if (isExist == false) {
        	fvo.setNcrudes(fvo.getNoutput());
        	fvo.setNcrudescontent(fvo.getNcontent());
    	   if(ProSetEnum.Apowder==env){
    		   fvo.setNcontent1(fvo.getNcontent());
    	   }else if(ProSetEnum.Bpowder==env){
    		   fvo.setNcontent2(fvo.getNcontent());  
    	   }else if(ProSetEnum.Cpowder==env){
    		   fvo.setNcontent3(fvo.getNcontent());  
    	   }
    	   fvo.setNrecover(PuPubVO.getUFDouble_NullAsZero(index.getNrecoveryrate()));
    	   fvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(index.getNmetal()));
    	   fvo.setNoutput(PuPubVO.getUFDouble_NullAsZero(index.getNflouryield()));
		}      
	}
    /**
     * 
       设置二次选择的信息
     * @param flist
     * @param flouryieldBVO
     * @param apowder
     */
	public void setSelectInfor1(List<FlouryieldBVO> flist,
			FlouryieldBVO fvo, ProSetEnum env,CalYieldNumVO index) {
        String pk_invmandoc=fvo.getPk_manindex();
        boolean isExist=false;
        for(int i=0;i<flist.size();i++){
           if(pk_invmandoc.equals(flist.get(i).getPk_manindex())){
        	   isExist=true;
        	   fvo.setNcrudes(flist.get(i).getNoutput());
        	   fvo.setNcrudescontent(flist.get(i).getNcontent());
        	   if(ProSetEnum.Apowder==env){
        		 fvo.setNcontent1(flist.get(i).getNcontent());
        	   }else if(ProSetEnum.Bpowder==env){
        		 fvo.setNcontent2(flist.get(i).getNcontent());  
        	   }else if(ProSetEnum.Cpowder==env){
        		 fvo.setNcontent3(flist.get(i).getNcontent());  
        	   }
          	   fvo.setNrecover(PuPubVO.getUFDouble_NullAsZero(index.getNrecoveryrate1()));
               fvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(index.getNmetal1()));
               fvo.setNoutput(PuPubVO.getUFDouble_NullAsZero(index.getNflouryield1()));
               break;
           }	
        }
        if (isExist == false) {
        	fvo.setNcrudes(fvo.getNoutput());
        	fvo.setNcrudescontent(fvo.getNcontent());
    	   if(ProSetEnum.Apowder==env){
    		   fvo.setNcontent1(fvo.getNcontent());
    	   }else if(ProSetEnum.Bpowder==env){
    		   fvo.setNcontent2(fvo.getNcontent());  
    	   }else if(ProSetEnum.Cpowder==env){
    		   fvo.setNcontent3(fvo.getNcontent());  
    	   }
    	   fvo.setNrecover(PuPubVO.getUFDouble_NullAsZero(index.getNrecoveryrate1()));
    	   fvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(index.getNmetal1()));
    	   fvo.setNoutput(PuPubVO.getUFDouble_NullAsZero(index.getNflouryield1()));
		}      
	}


	public void parseMaterialsTopMap(ProSetParaVO para) {
		return ;
	}

	public void parseRawOreTomMap(ProSetParaVO para) throws BusinessException {
		String topMapkey=XcPubTool.getMapKey(para);
		//get rawore information from  topmap by topmapkey
		Map<ProSetEnum, List<CalYieldVO>> map=topmap.get(topMapkey);
		List<CalYieldVO> raworelist=map.get(ProSetEnum.RawOre);
		if(raworelist!=null&&raworelist.size()!=0){
			for(int j=0;j<raworelist.size();j++){
				CalYieldVO vo=raworelist.get(j);
				//main index
				CalYieldNumVO mainvo=vo.getMainnumvo();
				//negative index
				List<CalYieldNumVO>  negalist=vo.getNumList();
				//other index
				List<CalYieldNumVO>  otherList=vo.getOthernumList();
				//all index
				List<CalYieldNumVO>   alllist=new ArrayList<CalYieldNumVO>();				
				//add alll list to  alllist
				if(mainvo!=null){
					alllist.add(mainvo);
				}
				if(negalist!=null && negalist.size()>0){
					alllist.addAll(negalist);
				}
				if(otherList!=null && otherList.size()>0){
					alllist.addAll(otherList);
				}
				//put rawore grade to grademap
				if (alllist != null && alllist.size() != 0) {
					for (int n = 0; n < alllist.size(); n++) {
						CalYieldNumVO cvo = alllist.get(n);
						String key = "RawOre"+ PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invmandoc())
						                     + PuPubVO.getString_TrimZeroLenAsNull(cvo.getPk_invbasdoc()+para);
						grademap.put(key, PuPubVO.getUFDouble_NullAsZero(cvo.getNgrade()));
					}
				}
			}
		}
		return ;
	}

	/**
	 * parse calculate data from output settings
	 * @param bb2vo
	 * @param mainnumvo
	 * @param grademap
	 * @param para
	 * @return
	 */
	public  FlouryieldBVO createFlourieldVO(ProSetEnum category,ProductSetBB2VO bb2vo,
			CalYieldNumVO mainnumvo, ProSetParaVO para) {
	
		FlouryieldBVO apbvo=new FlouryieldBVO();
		apbvo.setPk_deptdoc(para.getPk_minarea());
		apbvo.setPk_invmandoc(PuPubVO.getString_TrimZeroLenAsNull(bb2vo.getPk_invmandoc()));
		apbvo.setPk_invbasdoc(PuPubVO.getString_TrimZeroLenAsNull(bb2vo.getPk_invbasdoc()));
		
	    if(mainnumvo!=null){
	    	apbvo.setPk_manindex(PuPubVO.getString_TrimZeroLenAsNull(mainnumvo.getPk_invmandoc()));
			apbvo.setPk_invindex(PuPubVO.getString_TrimZeroLenAsNull(mainnumvo.getPk_invbasdoc()));
			apbvo.setNcrudes(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNrawore()));
			apbvo.setNcrudescontent(PuPubVO.getUFDouble_NullAsZero(grademap.get("RawOre"+mainnumvo.getPk_invmandoc()+mainnumvo.getPk_invbasdoc()+para)));
			if(category==ProSetEnum.Apowder || category==ProSetEnum.Bpowder || category==ProSetEnum.Cpowder || category==ProSetEnum.Gravity){
			     apbvo.setNcontent1(PuPubVO .getUFDouble_NullAsZero(grademap.get("Apowder"+mainnumvo.getPk_invmandoc()+mainnumvo.getPk_invbasdoc()+para)));
			     apbvo.setNcontent2(PuPubVO.getUFDouble_NullAsZero(grademap.get("Bpowder"+mainnumvo.getPk_invmandoc()+mainnumvo.getPk_invbasdoc()+para)));
	             apbvo.setItype(3);
	     		 apbvo.setNrecover(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNrecoveryrate()));
				 apbvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNmetal()));
				 apbvo.setNcontent(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 if(category==ProSetEnum.Apowder){
					 apbvo.setNcontent1(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 }
				 if(category==ProSetEnum.Bpowder){
					 apbvo.setNcontent2(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 }
				 if(category==ProSetEnum.Cpowder){
					 apbvo.setNcontent3(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 }
				
				 apbvo.setNoutput(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNflouryield()));
			     
			}else if(category==ProSetEnum.Atails || category==ProSetEnum.Btails || category==ProSetEnum.Ctails){
				 apbvo.setNcontent1(PuPubVO .getUFDouble_NullAsZero(grademap.get("Atail"+mainnumvo.getPk_invmandoc()+mainnumvo.getPk_invbasdoc()+para)));
				 apbvo.setNcontent2(PuPubVO.getUFDouble_NullAsZero(grademap.get("Btail"+mainnumvo.getPk_invmandoc()+mainnumvo.getPk_invbasdoc()+para)));
				 apbvo.setItype(4);
				 apbvo.setNrecover(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNrecoveryrate1()));
				 apbvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNmetal1()));
				 apbvo.setNcontent(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 if(category==ProSetEnum.Atails){
					 apbvo.setNcontent1(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 }
				 if(category==ProSetEnum.Btails){
					 apbvo.setNcontent2(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 }
				 if(category==ProSetEnum.Ctails){
					 apbvo.setNcontent3(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNgrade()));
				 }
				 apbvo.setNoutput(PuPubVO.getUFDouble_NullAsZero(mainnumvo.getNflouryield1()));
			}		
	
		}	
		return apbvo;
	}
	/**
	 * 解析forMap获得产量VO
	 * @param topMap
	 * @return
	 */
	public  FlouryieldBVO[] parseFormulaVO(Map<String,List<ProSetFormulaVO>> forMap,ProSetParaVO[] paras)throws BusinessException{
		if(paras==null||paras.length==0){
			throw new BusinessException("参数为空");
		}
		if(forMap==null||forMap.size()==0){
			throw new BusinessException("公式计算结果信息为空");
		}
		List<FlouryieldBVO> templist=new ArrayList<FlouryieldBVO>();
		for(int i=0;i<paras.length;i++){
			String topMapkey=XcPubTool.getMapKey(paras[i]);
			List<ProSetFormulaVO> list=forMap.get(topMapkey);
			for(int j=0;j<list.size();j++){
				ProSetFormulaVO vo=list.get(j);
				ProSetEnum invtype=vo.getInvtype();
				Integer itype=PuPubVO.getInteger_NullAs(vo.getItype(), -3);
				if(invtype!=null){//存货类型不为空
					FlouryieldBVO bvo=new FlouryieldBVO();
					FlouryieldBVO tailbvo=new FlouryieldBVO();
					//一道工艺精粉
					if(invtype.equals(ProSetEnum.Apowder)){
						    String  minera=paras[i].getPk_minarea();
							String	raworemanid=vo.getPk_apowdermanid();
							String  raworeinvid=vo.getPk_apowderinvid();
							String	atailsmanid=vo.getPk_atailsmanid();
							String  atailsinvid=vo.getPk_atailsinvid();
							String  invindex=vo.getPk_invindex();
							String  manindex=vo.getPk_manindex();
							UFDouble raworenum=vo.getRaworenum();//原矿数量
							UFDouble raworegrade=vo.getRaworegrade();//原矿品位
							UFDouble concentrate1=vo.getConcentrate1();	//精矿品位
							UFDouble tailingsgrade1=vo.getTailingsgrade1();//尾矿品位
							UFDouble nrate=vo.getNrate();//回收率
							UFDouble nmetal=vo.getNmetal();//金属量
							UFDouble nflour=vo.getNflour();//精粉量--产量
							UFDouble nmetal1=vo.getNmetal1();
							UFDouble nflour1=vo.getNflour1();
							bvo.setPk_minarea(minera);
							bvo.setPk_invmandoc(raworemanid);
							bvo.setPk_invbasdoc(raworeinvid);
							bvo.setPk_manindex(manindex);
							bvo.setPk_invindex(invindex);
							bvo.setNcrudescontent(raworegrade);
							bvo.setNcontent(concentrate1);
							bvo.setNrecover(nrate);
							bvo.setNmetalamount(nmetal);
							if(itype==0){
							    bvo.setNoutput(nflour);	
							}
							bvo.setNcontent1(concentrate1);
							bvo.setNcrudes(raworenum);
							tailbvo.setPk_manindex(manindex);
							tailbvo.setPk_invindex(invindex);
							tailbvo.setPk_minarea(minera);
							tailbvo.setPk_invmandoc(atailsmanid);
							tailbvo.setPk_invbasdoc(atailsinvid);
							tailbvo.setNcrudes(raworenum);
							tailbvo.setNcontent1(tailingsgrade1);
							tailbvo.setNcrudescontent(raworegrade);
							tailbvo.setNcontent(tailingsgrade1);
							tailbvo.setNmetalamount(nmetal1);
							tailbvo.setNoutput(nflour1);
							templist.add(bvo);
							templist.add(tailbvo);
					}
					//二道工艺精粉
                    if(invtype.equals(ProSetEnum.Bpowder)){
                    	String  minera=paras[i].getPk_minarea();
                    	String	raworemanid=vo.getPk_bpowdermanid();
						String  raworeinvid=vo.getPk_bpowderinvid();
						String	btailsmanid=vo.getPk_btailsmanid();
						String  btailsinvid=vo.getPk_btailsinvid();
						String  invindex=vo.getPk_invindex();
						String  manindex=vo.getPk_manindex();
						UFDouble raworenum=vo.getRaworenum();//原矿数量
						UFDouble raworegrade=vo.getRaworegrade();//原矿品位
						UFDouble concentrate1=vo.getConcentrate1();	//精矿品位1
						UFDouble concentrate2=vo.getConcentrate2();	//精矿品位2
						UFDouble tailingsgrade1=vo.getTailingsgrade1();//尾矿品位1
						UFDouble tailingsgrade2=vo.getTailingsgrade2();//尾矿品位2
						UFDouble nrate=vo.getNrate();//回收率
						UFDouble nmetal=vo.getNmetal();//金属量
						UFDouble nflour=vo.getNflour();//精粉量--产量
						UFDouble nmetal1=vo.getNmetal1();
						UFDouble nflour1=vo.getNflour1();
						bvo.setPk_minarea(minera);
						bvo.setPk_invmandoc(raworemanid);
						bvo.setPk_invbasdoc(raworeinvid);
						bvo.setPk_manindex(manindex);
						bvo.setPk_invindex(invindex);
						bvo.setNcrudescontent(raworegrade);
						bvo.setNcontent(concentrate2);
						bvo.setNrecover(nrate);
						bvo.setNmetalamount(nmetal);
						bvo.setNcontent1(concentrate1);
						bvo.setNcontent2(concentrate2);
						if(itype==0){
						    bvo.setNoutput(nflour);	
						}
						bvo.setNcrudes(raworenum);
						tailbvo.setPk_manindex(manindex);
						tailbvo.setPk_invindex(invindex);
						tailbvo.setPk_minarea(minera);
						tailbvo.setPk_invmandoc(btailsmanid);
						tailbvo.setPk_invbasdoc(btailsinvid);
						tailbvo.setNcontent1(tailingsgrade1);
						tailbvo.setNcontent2(tailingsgrade2);
						tailbvo.setNcrudes(raworenum);
						tailbvo.setNcrudescontent(raworegrade);
						tailbvo.setNcontent(tailingsgrade2);
						tailbvo.setNmetalamount(nmetal1);
						//tailbvo.setNoutput(nflour1);
						templist.add(bvo);
						templist.add(tailbvo);
					}
					//三道工艺精粉
                   if(invtype.equals(ProSetEnum.Cpowder)){
                	    String  minera=paras[i].getPk_minarea();
                		String	raworemanid=vo.getPk_cpowdermanid();
						String  raworeinvid=vo.getPk_cpowderinvid();
						String	ctailsmanid=vo.getPk_ctailsmanid();
						String  ctailsinvid=vo.getPk_ctailsinvid();
						String  invindex=vo.getPk_invindex();
						String  manindex=vo.getPk_manindex();
						UFDouble raworenum=vo.getRaworenum();//原矿数量
						UFDouble raworegrade=vo.getRaworegrade();//原矿品位
						UFDouble concentrate1=vo.getConcentrate1();	//精矿品位1
						UFDouble concentrate2=vo.getConcentrate2();	//精矿品位2
						UFDouble concentrate3=vo.getConcentrate3();	//精矿品位3
						UFDouble tailingsgrade1=vo.getTailingsgrade1();//尾矿品位1
						UFDouble tailingsgrade2=vo.getTailingsgrade2();//尾矿品位2
						UFDouble tailingsgrade3=vo.getTailingsgrade3();//尾矿品位3
						UFDouble nrate=vo.getNrate();//回收率
						UFDouble nmetal=vo.getNmetal();//金属量
						UFDouble nflour=vo.getNflour();//精粉量--产量
						UFDouble nmetal1=vo.getNmetal1();
						UFDouble nflour1=vo.getNflour1();
						bvo.setPk_minarea(minera);
						bvo.setPk_invmandoc(raworemanid);
						bvo.setPk_invbasdoc(raworeinvid);
						bvo.setPk_manindex(manindex);
						bvo.setPk_invindex(invindex);
						bvo.setNcrudescontent(raworegrade);
						bvo.setNcontent(concentrate3);
						bvo.setNrecover(nrate);
						bvo.setNmetalamount(nmetal);
						bvo.setNcontent1(concentrate1);
						bvo.setNcontent2(concentrate2);
						bvo.setNcontent3(concentrate3);
						if(itype==0){
						    bvo.setNoutput(nflour);	
						}	
						bvo.setNcrudes(raworenum);
						tailbvo.setPk_manindex(manindex);
						tailbvo.setPk_invindex(invindex);
						tailbvo.setPk_minarea(minera);
						tailbvo.setPk_invmandoc(ctailsmanid);
						tailbvo.setPk_invbasdoc(ctailsinvid);
						tailbvo.setNcrudes(raworenum);
						tailbvo.setNcrudescontent(raworegrade);
						tailbvo.setNcontent1(tailingsgrade1);
						tailbvo.setNcontent2(tailingsgrade2);
						tailbvo.setNcontent3(tailingsgrade3);
						tailbvo.setNcontent(tailingsgrade3);
						tailbvo.setNmetalamount(nmetal1);
						tailbvo.setNoutput(nflour1);
						templist.add(bvo);
						templist.add(tailbvo);
                }
					}
				}
			}
		return templist.toArray(new FlouryieldBVO[0]);	
	}
	/**
	 * 获得枚举list
	 * 
	 * @return
	 */
	public   List<ProSetEnum> getEnumList() {
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
}
