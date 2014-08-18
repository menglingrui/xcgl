package nc.vo.xcgl.pub.tool;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.RuntimeEnv;
import nc.itf.zmpub.pub.ISonVO;
import nc.itf.zmpub.pub.ISonVOH;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.ui.scm.util.ObjectUtils;
import nc.vo.pf.changeui02.VotableVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.xcgl.flouryield.FlouryieldBVO;
import nc.vo.xcgl.pub.bill.IndexResultParaVO;
import nc.vo.xcgl.pub.bill.ProSetParaVO;
import nc.vo.xcgl.pub.bill.PubSampleBVO;
import nc.vo.xcgl.pub.consts.PubOtherConst;
import nc.vo.xcgl.pub.itf.ISonExtendVO;
import nc.vo.xcgl.pub.itf.ISonExtendVOH;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 常用工具类
 * 
 * @author mlr
 */
public class XcPubTool extends ZmPubTool {
	
    /**
     * 顶层topMap计算维度
     * topMapKey+pk_invmandoc
     * @param para
     * @return
     * @throws BusinessException 
     */
	public static String getCalMapKey(ProSetParaVO para,String pk_invmandoc) throws BusinessException{
	    if(pk_invmandoc==null || pk_invmandoc.length()==0){
	    	throw new BusinessException("存货为空");
	    }
		return getMapKey(para)+pk_invmandoc;
	}
	public static String getMapKey(IndexResultParaVO para) throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp())==null){
			throw new BusinessException("公司为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull( para.getPk_factory())==null){
			throw new BusinessException("选厂为空");
		}
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
        	throw new BusinessException("生产线为空");
		}
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_classorder())==null){
        	throw new BusinessException("班次为空");
		}
        if(para.getDbilldate()==null){
        	throw new BusinessException("日期为空");
		}
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea())==null){
        	throw new BusinessException("矿区为空");
         }
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minetype())==null){
        	throw new BusinessException("矿石类型为空");
        }
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc1())==null){
        	throw new BusinessException("加工原矿矿石");
        }
		String key = para.getPk_corp()+para.getPk_factory() + para.getPk_beltline()
		+ para.getPk_classorder() + para.getDbilldate()
		+ para.getPk_minarea()+para.getPk_minetype()
		+ para.getPk_invmandoc1();
		return key;
	}
	public static String getMapKey(ProSetParaVO para) throws BusinessException{
		if(para==null){
			throw new BusinessException("参数为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_corp())==null){
			throw new BusinessException("公司为空");
		}
		if(PuPubVO.getString_TrimZeroLenAsNull( para.getPk_factory())==null){
			throw new BusinessException("选厂为空");
		}
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_beltline())==null){
        	throw new BusinessException("生产线为空");
		}
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_classorder())==null){
        	throw new BusinessException("班次为空");
		}
        if(para.getDbilldate()==null){
        	throw new BusinessException("日期为空");
		}
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minarea())==null){
        	throw new BusinessException("矿区为空");
         }
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_minetype())==null){
        	throw new BusinessException("矿石类型为空");
        }
        if(PuPubVO.getString_TrimZeroLenAsNull(para.getPk_invmandoc())==null){
        	throw new BusinessException("加工原矿为空");
        }
		String key = para.getPk_corp()+para.getPk_factory() + para.getPk_beltline()
		+ para.getPk_classorder() + para.getDbilldate()
		+ para.getPk_minarea()+para.getPk_minetype()
		+ para.getPk_invmandoc();
		return key;
	}
	/**
	 * 根据单据类型和单据id查询聚合vo 支持两种孙表的查询
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static AggregatedValueObject[] getExtBillVOByTypeAndPk(
			String nextbilltype, String pk) throws BusinessException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		String wsql = " isnull(dr,0)=0 and pk_billtype='" + nextbilltype
				+ "' and  coalesce(headbodyflag,'N')='N'";
		List<VotableVO> tables = (List<VotableVO>) new BaseDAO()
				.retrieveByClause(VotableVO.class, wsql);
		if (tables == null || tables.size() == 0)
			throw new BusinessException("没有找到vo对照表,单据类型为:" + nextbilltype);
		VotableVO table = tables.get(0);
		if (table.getDef3() == null || table.getDef3().length() == 0)
			throw new BusinessException("单据类型为:" + nextbilltype
					+ " 表体来源单据id没有设置");
		String bodyclass = table.getHeaditemvo();
		if (bodyclass == null || bodyclass.length() == 0)
			throw new BusinessException("单据类型为:" + nextbilltype
					+ " 没有设置表体vo对应类");
		String pkfield = table.getPkfield();
		if (pkfield == null || pkfield.length() == 0)
			throw new BusinessException("单据类型为:" + nextbilltype + " 没有设置  单据id");
		String sql = " select * from " + table.getVotable() + " h where h."
				+ pkfield + " = '" + pk + "' and isnull(h.dr,0)=0";
		Class bclass = Class.forName(bodyclass);
		List bodys = (List) new BaseDAO().executeQuery(sql,
				new BeanListProcessor(bclass));
		if (bodys == null)
			return null;
		SuperVO[] costbvos = (SuperVO[]) bodys
				.toArray((SuperVO[]) java.lang.reflect.Array.newInstance(
						bclass, 0));
		String hwsql = " isnull(dr,0)=0 and pk_billtype='" + nextbilltype
				+ "' and  coalesce(headbodyflag,'N')='Y'";
		List<VotableVO> htables = (List<VotableVO>) new BaseDAO()
				.retrieveByClause(VotableVO.class, hwsql);
		if (tables == null || tables.size() == 0)
			throw new BusinessException("没有找到vo对照表,单据类型为:" + nextbilltype);
		VotableVO htable = htables.get(0);
		String headclass = htable.getHeaditemvo();
		if (headclass == null || headclass.length() == 0)
			throw new BusinessException("单据类型为:" + nextbilltype
					+ " 没有设置表头vo对应类");
		Class hclass = Class.forName(headclass);
		String billvoclass = htable.getBillvo();
		if (billvoclass == null || billvoclass.length() == 0)
			throw new BusinessException("单据类型为:" + nextbilltype
					+ " 没有设置聚合vo对应类");
		Class billclass = Class.forName(billvoclass);
		// 按主键分担
		SuperVO[][] costbvoss = (SuperVO[][]) SplitBillVOs.getSplitVOs(
				costbvos, new String[] { pkfield });
		if (costbvoss == null || costbvoss.length == 0) {
			// 如果等于空 ，说名没有表体
			List li = (List) new BaseDAO().retrieveByClause(hclass,
					" isnull(dr,0)=0 and " + pkfield + "='" + pk + "'");
			if (li == null || li.size() == 0)
				return null;
			SuperVO ahvo = (SuperVO) li.get(0);
			AggregatedValueObject billvo = (AggregatedValueObject) Class
					.forName(billvoclass).newInstance();
			billvo.setParentVO(ahvo);
			return new AggregatedValueObject[] { billvo };
		}
		// 构造直接成本核算单的聚合vo
		AggregatedValueObject[] abillvos = (AggregatedValueObject[]) java.lang.reflect.Array
				.newInstance(billclass, costbvoss.length);
		for (int i = 0; i < costbvoss.length; i++) {
			SuperVO[] abvos = costbvoss[i];
			if (abvos == null || abvos.length == 0) {
				continue;
			}
			String pk_costaccount = (String) abvos[0]
					.getAttributeValue(abvos[0].getParentPKFieldName());
			List li = (List) new BaseDAO().retrieveByClause(hclass,
					" isnull(dr,0)=0 and " + pkfield + "='" + pk_costaccount
							+ "'");
			if (li == null || li.size() == 0)
				continue;
			SuperVO ahvo = (SuperVO) li.get(0);
			abillvos[i] = (AggregatedValueObject) Class.forName(billvoclass)
					.newInstance();
			abillvos[i].setParentVO(ahvo);
			// 如果存在孙表设置孙表
			SuperVO[] bodyvos = abvos;
			if (bodyvos != null && bodyvos.length > 0) {
				HYBillVO sbill = (HYBillVO) Class.forName(htable.getBillvo())
						.newInstance();
				if (sbill instanceof ISonVOH) {
					ISonVOH sh = (ISonVOH) sbill;
					String sonclass = sh.getSonClass();
					Class sclass = Class.forName(sonclass);
					SuperVO vo = (SuperVO) sclass.newInstance();
					for (int k = 0; k < bodyvos.length; k++) {
						List slist = (List) getDao().retrieveByClause(
								sclass,
								" isnull(dr,0)=0 and "
										+ bodyvos[k].getPKFieldName() + "='"
										+ bodyvos[k].getPrimaryKey() + "'");
						((ISonVO) bodyvos[k]).setSonVOS(slist);
					}
				}

			}
			if (bodyvos != null && bodyvos.length > 0) {
				HYBillVO sbill = (HYBillVO) Class.forName(htable.getBillvo())
						.newInstance();
				if (sbill instanceof ISonExtendVOH) {
					ISonExtendVOH sh = (ISonExtendVOH) sbill;
					String sonclass = sh.getSonClass1();
					Class sclass = Class.forName(sonclass);
					SuperVO vo = (SuperVO) sclass.newInstance();
					for (int k = 0; k < bodyvos.length; k++) {
						List slist = (List) getDao().retrieveByClause(
								sclass,
								" isnull(dr,0)=0 and "
										+ bodyvos[k].getPKFieldName() + "='"
										+ bodyvos[k].getPrimaryKey() + "'");
						((ISonExtendVO) bodyvos[k]).setSonVOS1(slist);
					}
				}

			}
			abillvos[i].setChildrenVO(abvos);
		}
		return abillvos;
	}

	/**
	 * 加密算法
	 * @param content
	 *            要生成密文的明文
	 * @param password
	 *            密钥（用来解密）
	 * @return 生成的密文
	 * @author yangtao
	 * @throws Exception 
	 * @date 2013-12-9 下午01:33:34
	 */
	public static String encrypt(String content) throws Exception {
		
		String password = PubOtherConst.password;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			String encryptResultStr = parseByte2HexStr(result);
			return encryptResultStr; // 加密
		} catch (Exception e) {
			throw new Exception("加密过程中出现异常");
		} 
	}

	/**
	 * 解密算法
	 * @param content
	 *            密文
	 * @param password
	 *            密钥
	 * @return 明文
	 * @author yangtao
	 * @date 2013-12-9 下午01:35:00
	 */
	public static String decrypt(String content) {
		String password = PubOtherConst.password;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] decryptFrom = parseHexStr2Byte(content);
			byte[] result = cipher.doFinal(decryptFrom);
			return new String(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	/**
	 * 过滤掉表体金属，只留精粉的行
	 * 金属的关联行号不为空
	 * @throws BusinessException 
	 */
	public static HYBillVO fliterPowder(HYBillVO aggvo) throws BusinessException{
		HYBillVO billvo=null;
		try {
			billvo = (HYBillVO) ObjectUtils.serializableClone(aggvo);
	
		if(aggvo==null){
			return billvo;
		}
		CircularlyAccessibleValueObject[] bvos = aggvo.getChildrenVO();
		if(bvos==null||bvos.length==0){
			return billvo;
		}
		List<CircularlyAccessibleValueObject> list=new ArrayList<CircularlyAccessibleValueObject>();
		for(int i=0;i<bvos.length;i++){
			String vcrowno=PuPubVO.getString_TrimZeroLenAsNull(bvos[i].getAttributeValue("vcrowno"));
			if(vcrowno==null||vcrowno.trim()==""){
				list.add(bvos[i]);
			}
		}
		billvo.setParentVO(aggvo.getParentVO());
		billvo.setChildrenVO(list.toArray(new CircularlyAccessibleValueObject[0]));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return billvo;
	}
	/**
	 * will g/t  to  %
	 * @param bvolist
	 */
	public static void dealLabGrade(List<PubSampleBVO> list) {
      if(list==null || list.size()==0){
    	  return ;
      }
	  for(int i=0;i<list.size();i++){
		 if(PubOtherConst.pk_labgrade_ag.equals(list.get(i).getPk_measdoc()))  {
			UFDouble ngrade=PuPubVO.getUFDouble_NullAsZero(list.get(i).getNgrade());	
			ngrade=ngrade.div(new UFDouble(10000));
			list.get(i).setNgrade(ngrade);
		}
	  }
	}
	/**
	 * will % to  g/t  and  output to g
	 * @param templist
	 * @throws BusinessException 
	 */
	public static void dealLabGradeAndIndex(List<FlouryieldBVO> templist) throws BusinessException {
		if(templist==null || templist.size()>0){
		    for(int i=0;i<templist.size();i++){
		    	FlouryieldBVO fvo=templist.get(i);
		    	String pk_invdex=fvo.getPk_invindex();
		    	String pk_mea=getMeasByIndex(pk_invdex);
		    	if(PubOtherConst.pk_labgrade_ag.equals(pk_mea)){		    		
		    		fvo.setNcontent(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent()).multiply(new UFDouble(1000000)));
		    		fvo.setNcontent1(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent1()).multiply(new UFDouble(1000000)));
		    		fvo.setNcontent2(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent2()).multiply(new UFDouble(1000000)));		
		    		fvo.setNcontent3(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent3()).multiply(new UFDouble(1000000)));	
                    fvo.setNcrudescontent(PuPubVO.getUFDouble_NullAsZero(fvo.getNcrudescontent()).multiply(new UFDouble(1000000)));
                    fvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(fvo.getNmetalamount()).multiply(new UFDouble(1000000)));                   
		    	}else{
		    		fvo.setNcontent(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent()).multiply(new UFDouble(100)));
		    		fvo.setNcontent1(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent1()).multiply(new UFDouble(100)));
		    		fvo.setNcontent2(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent2()).multiply(new UFDouble(100)));	
		    		fvo.setNcontent3(PuPubVO.getUFDouble_NullAsZero(fvo.getNcontent3()).multiply(new UFDouble(100)));		
                    fvo.setNcrudescontent(PuPubVO.getUFDouble_NullAsZero(fvo.getNcrudescontent()).multiply(new UFDouble(100)));
                   // fvo.setNmetalamount(PuPubVO.getUFDouble_NullAsZero(fvo.getNmetalamount()).multiply(new UFDouble(100)));  
		    	}
		    }	
		}
	}
	
	public static String getMeasByIndex(String pk_invbasdoc) throws BusinessException {
		if (RuntimeEnv.getInstance().isRunningInServer()){
			return PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("invcode->getColValue(bd_invbasdoc,def4, pk_invbasdoc,pk_invbasdoc  )", 
					new String[]{"pk_invbasdoc "}, new String[]{pk_invbasdoc}));
		}else{
			return PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomularClient("invcode->getColValue(bd_invbasdoc,def4, pk_invbasdoc,pk_invbasdoc  )", 
					new String[]{"pk_invbasdoc "}, new String[]{pk_invbasdoc}));
		}
	}

}
