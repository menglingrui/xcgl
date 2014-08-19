package nc.vo.xcgl.pub.consts;
/**
 * 单据类型常量
 * @author mlr
 *
 */
public class PubBillTypeConst {
   /**
    * 设备运行记录--单据类型
    */
   public static String billtype_runrecord="XC07";
   /**
    * 过磅登记
    */
   public static String billtype_weighdoc="XC08";
   
   /**
    * 送样单--单据类型
    */
   public static String billtype_sample="XC13";
   
   /**
    * 送样单-参照
    */
   public static String billtype_sample_ref="XC13REF";
   /**
    *生产流程设置--单据类型 
    */
   public static String billtype_prduceset="XC14";
   /**
    *原矿加工出库--单据类型 
    */
   public static String billtype_Generalout="XC15";
   /**
    * 矿区入库
    */
   public static String billtype_Generalin="XC18";
   /**
    * 药剂消耗登记表
    */
   public static String billtype_Drugconsume="XC19";
   /**
    * 精粉产量计算
    */
   public static String billtype_flouryield="XC20";
   /**
    * 精矿入库
    */
   public static String billtype_concentratein="XC21";
   /**
    *销售出库 
    */
   public static String billtype_saleout="XC24";
   /**
    * 销售过磅登记
    */
   public static String billtype_saleweighdoc="XC25";
   /**
    * 销售过磅登记参照
    */
   public static String billtype_saleweighdoc_ref="XC25REF";
   /**
    *月末库存盘点 
    */
   public static String billtype_monstocount="XC26";
   /**
    * 库存锁定解锁
    */
   public static String billtype_stocklock="XC27";
   /**
    * 矿石加工月汇总计算
    */
   public static String billtype_oreprocount="XC28";
   /**
    * 销售出库矿区分摊计算
    */
   public static String billtype_saleshareout="XC29";
   /**
    * 销售按矿区比例分摊计算
    */
   public static String billtype_saleminenanteil="XC30";
   /**
    * 化验单
    */
   public static String billtype_assay="XC31";
   /**
	* 月末结账
    */
   public static String billtype_latecheckout="XC32";
   /**
    * 月末关账
    */
   public static String billtype_closing="XC33";
   /**
    * 化验结果
    */
   public static String billtype_assayres="XC34";
   
   /**
    *化验单--指标录入 
    */
   public static String billtype_vindexentry="XC35";
   /**
    *化验结果-- 指标录入
    */
   public static String billtype_assayquota="XC36";
   /**
    *化验单--指标明细查看
    */
   public static String billtype_vindexentryview="XC38";
   /**
    *化验结果--指标明细查看
    */
   public static String billtype_assayquotaview="XC39";
   /**
    * 送样单参照。。。。
    */
   
   public static String billtype_sampleref="XC31REF";
   /**
    * 样品编码；
    * 为了系统能自动生成样品编码，专门注册了这个单据类型
    */
   public static String billtype_SampleNo="XC40";
   /**
    * 结存量
    */
   public static String billtype_monthaccount="XC41";
   /**
	* 现存量修复
	*/
   public static String billtype_stockmod=" ";
   /**
    * 销售出库矿区分摊计算孙表
    * 矿区分摊 
    */
   public static String billtype_minareashare="XC43";
   /**
    * 销售出库矿区分摊计算孙表明细查看
    * 矿区分摊 
    */
   public static String billtype_minareashareview="XC44";
   /**
    * 销售送样单
    */
   public static String billtype_salesample="XC45";
   /**
    * 销售送样单中 样品编码
    */
   public static String billtype_salesampleNo="XC46";
   /**
    * 销售化验单
    */
   public static String billtype_saleassay="XC47";
   /**
    * 销售化验单 指标录入
    */
   public static String billtype_saleindexentry="XC48";
   /**
    * 销售送样单参照
    */
   public static String billtype_salesampleref="XC47REF";
   /**
    * 销售化验单--指标查看
    */
   public static String billtype_saleindexentryview="XC49";
   /**
    * 销售化验结果
    */
   public static String billtype_saleassayres="XC50";
   /**
    * 期初录入
    */
   public static String billtype_openingentry="XC51";
   /**
    * 销售化验结果--指标录入
    */
   public static String billtype_saleassayquota="XC52";
   /**
    * 销售化验结果--指标明细查看
    */
   public static String billtype_saleassayquotaview="XC53";
   /**
    * 样品编码；
    * 为了系统能自动生成样品编码，专门注册了这个单据类型
    * 这个单据类型是用来在打印时生成条形码，并存入数据库
    */
   public static String billtype_SampleNoView="XC54";
   /**
    * 价格维护
    */
   public static String billtype_pricemanage="XC55";
   /**
    *合同条框类型 
    */
   public static String billtype_cttermtype="XC56";
   /**
    * 收发类别
    */
   public static String billtype_resetype="XC57";
   /**
    *合同类型 
    */
   public static String billtype_cttype="XC58";
   /**
    * 优质优价--价格
    */
   public static String billtype_qualityprice="XC59";
   /**
    * 优质优价--品位
    */
   public static String billtype_qualitygrade="XC60";	
   /**
    *合同条款 
    */
   public static String billtype_cttermset="XC61";
   /**
    * 优质优价方案
    */
   public static String billtype_qualitypro="XC62";
   /**
    * 销售预结算
    */
   public static String billtype_salepresettle="XC65";
   /**
    * 销售结算
    */
   public static String billtype_salesettle="XC66";
   /**
    * 销售结算调差 
    */
   public static String billtype_salesettledroop="XC67";
   /**
    * 其他出库
    */
   public static String billtype_genotherout="XC68";
   /**
    * 其他入库
    */
   public static String billtype_genotherin="XC69";

   /**
    * 销售合同
    */
   public static String billtype_soct="XC70";
   
   /**
    * 邮件设置
    */
   
   public static String biltype_EMAIL="XC71";
   /**
    * 设备维护记录
    */
   public static String biltype_defendrecord="XC72";
   /**
    * 合同费用
    */
   public static String billtype_ctexpset="XC73";
   /**
    *销售出库矿区分摊查看 
    */
   public static String billtype_share="XC74";
   /**
    * 生产加工
    * 水分化验单
    */
   public static String billtype_watertest="XC75";
   /**
    * 精粉销售
    * 销售水分化验单
    */
   public static String billtype_salewatertest="XC76";
   /**
    * 精粉调拨单
    */
   public static String billtype_allocateofflour="XC77";
   /**
    * 重选入库单
    */
   public static String billtype_gravity="XC78";
}
