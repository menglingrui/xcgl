package nc.vo.xcgl.pub.consts;
/**
 * 单据模版类型常量
 * @author mlr
 *
 */
public class PubNodeModelConst {
   /**
    * 工序档案
    */
   public static String NodeModel_Process="XC01";
   /**
    * 班次档案
    */
   public static String NodeModel_Classorder="XC02";
   /**
    * 选厂档案
    */
   public static String NodeModel_Factory="XC03";
   /**
    * 工艺档案
    */
   public static String  NodeModel_Technology="XC04";
   /**
    * 生产线档案
    */
   public static String NodeModel_Beltline="XC05";
   /**
    * 设备运行记录--单据类型
    */
   public static String NodeModel_runrecord="XC07";
   /**
    * 过磅登记
    */
   public static String NodeModel_Weighdoc="XC08";  
   /**
    * 化验指标定义档案
    */
   public static String NodeModel_Labindexset="XC09";
   /**
    * 设备档案
    */
   public static String NodeModel_Equipment="XC10";
   /**
    * 班组档案
    */
   public static String NodeModel_Teamgroup="XC11";
   /**
    * 车辆档案
    */
   public static String NodeModel_Vehicle="XC06";

   /**
    * 送样单
    */ 
   public static String NodeModel_Sample="XC13";
   /**
    * 生产流程设置
    */
   public static String NodeModel_Produceset="XC14";
   /**
    * 原矿加工出库
    */
   public static String NodeModel_Generalout="XC15";
   /**
    * 生产流程_投入设置查看
    */
   public static String NodeModel_Produceset_in="XC16";
   /**
    * 生产流程_产出设置查看
    */
   public static String NodeModel_Produceset_out="XC17";
   /**
    * 矿区入库
    */
   public static String NodeModel_General_in="XC18";
   /**
    * 药剂消耗岗位记录表
    */
   public static String NodeModel_Drugconsume="XC19";
   /**
    * 精粉产量计算
    */
   public static String NodeModel_flouryield="XC20";
   /**
    * 精矿入库
    */
   public static String NodeModel_concentratein="XC21";
   /**
    * 生产流程_投入设置_录入
    */
   public static String NodeModel_Produceset_inwrite="XC22";
   /**
    * 生产流程_产出设置_录入
    */
   public static String NodeModel_Producese_outwrite="XC23";
   /**
    *销售出库 
    */
   public static String NodeModel_saleout="XC24";
   /**
    * 销售过磅登记
    */
   public static String NodeModel_saleweighdoc="XC25"; 
   /**
    * 销售过磅登记
    */
   public static String NodeModel_saleweighdoc_ref="XC25REF"; 
   /**
    *月末库存盘点 
    */
   public static String NodeModel_monstocount="XC26"; 
   /**
    * 库存锁定解锁
    */
   public static String NodeModel_stocklock="XC27";
   /**
    * 矿石加工月汇总计算
    */
   public static String NodeModel_oreprocount="XC28";
   /**
    * 销售出库矿区分摊计算
    */
   public static String NodeModel_saleshareout="XC29";
   /**
    * 销售按矿区比例分摊计算
    */
   public static String NodeModel_saleminenanteil="XC30";
   /**
    * 化验单
    */
   public static String NodeModel_assay="XC31";
   /**
	* 月末结账
   */
  public static String NodeModel_latecheckout="XC32";
   /**
    * 月末关账
    */
   public static String NodeModel_closing="XC33";
   /**
    * 化验结果
    */
   public static String NodeModel_assayres="XC34";
   /**
    *化验单--指标录入 
    */
   public static String NodeModel_vindexentry="XC35";
   /**
    *化验结果--指标录入 
    */
   public static String NodeModel_assayquota="XC36";
   /**
    * 现存量
    */
   public static String NodeModel_stockonhand="XC37";
   /**
    *化验单--指标查看
    */
   public static String NodeModel_vindexentryview="XC38";
   /**
    *化验结果--指标查看
    */
   public static String NodeModel_assayquotaview="XC39";
   /**
    * 结存量
    */
   public static String NodeModel_monthaccount="XC41";
   /**
	* 现存量修复
    */
   public static String NodeModel_stockmod=" ";
   /**
    * 销售出库矿区分摊计算孙表
    * 矿区分摊 
    */
   public static String NodeNodel_minareashare="XC43";
   /**
    * 销售出库矿区分摊计算
    * 矿区分摊 
    */
   public static String NodeNodel_minareashareview="XC44";
   /**
    * 销售送样单
    */
   public static String NodeModel_salesample="XC45";
   /**
    * 销售送样单 中样品编码 没有实际模板
    */
   public static String NodeModel_salesampleNo="XC46";
   /**
    * 销售化验单
    */
   public static String NodeModel_saleassay="XC47";
   /**
    * 销售化验单 指标录入
    */
   public static String NodeModel_saleindexentry="XC48";
   /**
    * 销售化验单--指标查看
    */
   public static String NodeModel_saleindexentryview="XC49";
   /**
    * 销售化验结果
    */
   public static String NodeModel_saleassayres="XC50";
   /**
    * 期初录入
    */
   public static String NodeModel_openingentry="XC51";
   //XC52  XC53 已经用了
   /**
    * 样品编码；
    * 为了系统能自动生成样品编码，专门注册了这个单据类型
    * 这个单据类型是用来在打印时生成条形码，并存入数据库
    */
   public static String NodeModel_SampleNoView="XC54";
   /**
    * 价格维护
    */
   public static String NodeModel_PriceManage="XC55";
   /**
    *合同条框类型 
    */
   public static String NodeModel_cttermtype="XC56";
   /**
    * 收发类别
    */
   public static String NodeModel_resetype="XC57";
   /**
    *合同类型 
    */
   public static String NodelModel_cttype="XC58";
   /**
    * 优质优价--价格
    */
   public static String NodeModel_qualityprice="XC59";
   /**
    * 优质优价--品位
    */
   public static String NodeModel_qualitygrade="XC60";
   /**
    *合同条款 
    */
   public static String NodeModel_cttermset="XC61";
   /**
    * 优质优价方案
    */
   public static String NodeModel_qualitypro="XC62";
   /**
    * 销售预结算
    */
   public static String NodeModel_salepresettle="XC65";
   /**
    * 销售结算
    */
   public static String NodeModel_salesettle="XC66";
   /**
    * 销售结算调差 
    */
   public static String NodeModel_salesettledroop="XC67";
   /**
    * 其他出库
    */
   public static String NodeModel_genotherout="XC68";
   /**
    * 其他入库
    */
   public static String NodeModel_genotherin="XC69";
   /**
    * 销售合同
    */
   public static String NodeModel_soct="XC70";
   
   /**
    * 邮件设置
    */
   public static String NodeModel_EMAILL="XC71";
   /**
    * 设备维护记录
    */
   public static String NodeModel_defendrecord="XC72";
   /**
    * 合同费用
    */
   public static String NodeModel_ctexpset="XC73";
   /**
    *销售出库矿区分摊查看 
    */
   public static String NodeModel_share="XC74";
   /**
    * 生产加工
    * 水分化验单
    */
   public static String NodeModel_watertest="XC75";
   /**
    * 精粉销售
    * 销售水分化验单
    */
   public static String NodeModel_salewatertest="XC76";
   /**
    * 精粉调拨单
    */
   public static String NodeModel_allocateofflour="XC77";
   /**
    * 重选入库单
    */
   public static String NodeModel_gravity="XC78";
}
