package nc.vo.xcgl.pub.stock;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nc.bs.zmpub.pub.tool.stock.BillStockBO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.pub.consts.PubBillTypeConst;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * 出入库单更新现存量时调用,必须保存前调用(保存后VO状态失效，导致现存量处理错误)
 * 查询现存量
 * @author mlr
 */
public class BillStockTool extends BillStockBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6683129237534928560L;
	
	private static BillStockTool  bo=null;
	
	public static BillStockTool getInstrance(){
		if(bo==null){
			bo=new BillStockTool();
		}
		return bo;
	}

	/**
	 * 单据类型 ->交换类 对应关系
	 */
	private Map<String, String> typetoChangeclass = new HashMap<String, String>();
	/**
	 * 单据类型->现存量 数量变化规则
	 */
	private Map<String, UFBoolean[]> typetosetnum = new HashMap<String, UFBoolean[]>();
	/**
	 * 现存量 数量变化字段 库存主数量 库存辅数量
	 */
	private String[] changeNums = new String[] { "nnum",
			"nassistnum","nlock"};
	/**
	 * 现存量实现类 路径
	 */
	private String className = "nc.vo.xcgl.pub.stock.PubStockOnHandVO";
	/**
	 * 先存量定义的最小维度 维度为： 公司 仓库 货位 存货 批次 存货状态 入库日期
	 */
	private String[] def_fields = new String[] { "pk_corp","vdef1", "pk_stordoc",
			"pk_factory", "pk_invmandoc","pk_invbasdoc","pk_father",
		};
	/**
	 * 先存量定义的最小维度 维度为： 公司 仓库 货位 存货 批次 存货状态 入库日期
	 */
	private String[] def_fieldsName = new String[] { "公司", "部门","仓库",
			"选厂", "存货管理主键","存货基本主键","关联矿石主键", 
		};
   
	@Override
	public Map<String, String> getTypetoChangeClass() throws Exception {
		if (typetoChangeclass.size() == 0) {
			typetoChangeclass.put(PubBillTypeConst.billtype_concentratein,
					"nc.bs.pf.changedir.CHGXC21TOXC37");	
			typetoChangeclass.put(PubBillTypeConst.billtype_Generalin,
					"nc.bs.pf.changedir.CHGXC18TOXC37");
			typetoChangeclass.put(PubBillTypeConst.billtype_stocklock,
					"nc.bs.pf.changedir.CHGXC27TOXC37");
			typetoChangeclass.put(PubBillTypeConst.billtype_genotherout,
					"nc.bs.pf.changedir.CHGXC68TOXC37");
			typetoChangeclass.put(PubBillTypeConst.billtype_genotherin,
					"nc.bs.pf.changedir.CHGXC69TOXC37");
			typetoChangeclass.put(PubBillTypeConst.billtype_openingentry,
					"nc.bs.pf.changedir.CHGXC51TOXC37");	
			typetoChangeclass.put(PubBillTypeConst.billtype_Generalout,
					"nc.bs.pf.changedir.CHGXC15TOXC37");//原矿加工出库保存
//			typetoChangeclass.put(PubBillTypeConst.billtype_Drugconsume, 
//					"nc.bs.pf.changedir.CHGXC19TOXC37");//药剂消耗保存
			typetoChangeclass.put(PubBillTypeConst.billtype_saleout, 
					"nc.bs.pf.changedir.CHGXC24TOXC37");//销售出库保存
			typetoChangeclass.put(PubBillTypeConst.billtype_gravity,
			        "nc.bs.pf.changedir.CHGXC78TOXC37");
		}
		return typetoChangeclass;
	}

	@Override
	public Map<String, UFBoolean[]> getTypetosetnum() throws Exception {
		if (typetosetnum.size() == 0) {
			typetosetnum.put(PubBillTypeConst.billtype_stocklock, new UFBoolean[] {
					new UFBoolean(true), new UFBoolean(true),new UFBoolean(false) });
			typetosetnum.put(PubBillTypeConst.billtype_concentratein, new UFBoolean[] {
					new UFBoolean(false), new UFBoolean(false),null });
			typetosetnum.put(PubBillTypeConst.billtype_genotherin, new UFBoolean[] {
					new UFBoolean(false), new UFBoolean(false),null });
			typetosetnum.put(PubBillTypeConst.billtype_genotherout, new UFBoolean[]{
					new UFBoolean(true), new UFBoolean(true),null}); 
			typetosetnum.put(PubBillTypeConst.billtype_Generalin, new UFBoolean[] {
					new UFBoolean(false), new UFBoolean(false),null});
			typetosetnum.put(PubBillTypeConst.billtype_openingentry, new UFBoolean[] {
					new UFBoolean(false), new UFBoolean(false),null});
			typetosetnum.put(PubBillTypeConst.billtype_Generalout, new UFBoolean[]{
					new UFBoolean(true), new UFBoolean(true),null}); //原矿加工出库保存
//			typetosetnum.put(PubBillTypeConst.billtype_Drugconsume, new UFBoolean[]{
//					new UFBoolean(true), new UFBoolean(true),null});	//药剂消耗保存
			typetosetnum.put(PubBillTypeConst.billtype_saleout, new UFBoolean[]{
					new UFBoolean(true), new UFBoolean(true),null}); //销售出库保存
			typetosetnum.put(PubBillTypeConst.billtype_gravity, new UFBoolean[] {
					new UFBoolean(false), new UFBoolean(false),null });

		}
		return typetosetnum;
	}

	/**
	 * 通过where条件查询现存量
	 * 
	 * @param whereSql
	 * @return
	 */
	public SuperVO[] queryStock(String whereSql) throws Exception {
		String clname = getClassName();
		if (clname == null || clname.length() == 0)
			throw new Exception("没有注册现存量实现类全路径");
		Class cl = Class.forName(clname);
		Collection list = getDao().retrieveByClause(cl, whereSql);
		if (list == null || list.size() == 0)
			return null;
		SuperVO[] vos = (SuperVO[]) list
				.toArray((SuperVO[]) java.lang.reflect.Array.newInstance(cl,
						list.size()));
		return vos;

	}

    /**
     * create error msg
     * @param stockvo
     * @return
     */
	public String getErrorMsg(SuperVO stockvo) throws Exception{
		PubStockOnHandVO billvo= (PubStockOnHandVO) stockvo;
		StringBuffer sb=new StringBuffer();
		String pk_invmandoc=billvo.getPk_invmandoc();
		String pk_factory=billvo.getPk_factory();
		String pk_stordoc=billvo.getPk_stordoc();
		String vdef1=billvo.getVdef1();
		sb.append("\n存货为：["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("invcode->getColValue(bd_invbasdoc,invcode,pk_invbasdoc," +
					"getColValue(bd_invmandoc,pk_invbasdoc,pk_invmandoc,pk_invmandoc))",
					new String[]{"pk_invmandoc"}, new String[]{pk_invmandoc}))+"],");
			
			
		sb.append("["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("invname->getColValue(bd_invbasdoc,invname,pk_invbasdoc," +
					"getColValue(bd_invmandoc,pk_invbasdoc,pk_invmandoc,pk_invmandoc))",
					new String[]{"pk_invmandoc"}, new String[]{pk_invmandoc}))+"]   \n");
		
		sb.append("选厂为:["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("vfactorycode->getColValue(xcgl_factory,vfactorycode,pk_factory,pk_factory)",
				new String[]{"pk_factory"}, new String[]{pk_factory}))+"],");
			
		sb.append("["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("vfactoryname->getColValue(xcgl_factory,vfactoryname,pk_factory,pk_factory)",
					new String[]{"pk_factory"}, new String[]{pk_factory}))+"]   \n");
		
		sb.append("部门为:["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.getDepCodeByPk(vdef1))+"],");
			
		sb.append("["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.getDepCodeByPk(vdef1))+"]   \n");
		
		sb.append("仓库为:["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("storcode->getColValue(bd_stordoc,storcode,pk_stordoc,pk_stordoc)",
				new String[]{"pk_stordoc"},new String[]{pk_stordoc}))+"],");
			
		sb.append("["+PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("storname->getColValue(bd_stordoc,storname,pk_stordoc,pk_stordoc)",
					new String[]{"pk_stordoc"},new String[]{pk_stordoc}))+"]    \n");
			
		//sb.append("的现存量为负!");
		return sb.toString();
	}
	
	@Override
	public String[] getChangeNums() {

		return changeNums;
	}

	@Override
	public String getClassName() {

		return className;
	}

	@Override
	public String[] getDef_Fields() {

		return def_fields;
	}
    
	@Override
	public String[] getDef_FieldsName() {
		return def_fieldsName;
	}

	public void setDef_fieldsName(String[] def_fieldsName) {
		this.def_fieldsName = def_fieldsName;
	}

	@Override
	public String getThisClassName() {

		return this.getClass().getName();
	}

	/**
	 * 根据传入的现存量vo 取出维度 查询现存量 SuperVO[] 存放每个查询维度查询出来的现存量(按查询维度合并后)
	 * 
	 * @throws Exception
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-7-2下午12:27:29
	 * 
	 */
	public SuperVO[] queryStockCombin1(SuperVO[] vos,String whereSql) throws Exception {
		return super.queryStockCombin1(vos,whereSql);
	}
	/**
	 * 根据传入的现存量vo 取出维度 查询现存量 SuperVO[] 存放每个查询维度查询出来的现存量(按查询维度合并后)
	 * 
	 * @throws Exception
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-7-2下午12:27:29
	 * 
	 */
	public SuperVO[] queryStockCombin(SuperVO[] vos) throws Exception {
		return super.queryStockCombin(vos);
	}
}
