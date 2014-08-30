package nc.vo.xcgl.report.sumdayreport;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
/**
 * 日报数据月汇总VO
 * @author mlr
 */
public class ReportDaySumVO extends SuperVO{
	
	private static final long serialVersionUID = -1661383706333407322L;
//	create table xcgl_reportdaysum(
//			pk_corp char(20),
//			pk_factory char(20),
//			pk_classorder char(20),
//			vyear char(4),
//			vmonth char(2),
//			dbilldate char(10),
//			starhours number(20,8),
//			pk_oreinvmandoc char(20),
//			pk_oreinvbasdoc char(20),
//			nwetnum number(20,8),
//			nwater number(20,8),
//			ndrynum number(20,8),
//			ore_pb number(20,8),
//			ore_zn number(20,8),
//			ore_ag number(20,8),
//			
//			pb_noutnum number(20,8),
//			pb_pb number(20,8),
//			pb_zn number(20,8),
//			pb_ag number(20,8),
//			
//			pbt_pb number(20,8),
//			pbt_ag number(20,8),
//			znt_zn number(20,8),
//		
//			pb_pb_recrate number(20,8),
//			pb_ag_recrate number(20,8),
//			zn_zn_recrate number(20,8),
//			
//			ptm_pb number(20,8),
//			ptm_zn number(20,8),
//			pbtm_ag number(20,8),
//			
//			znm_pb number(20,8),
//			znm_zn number(20,8),
//			pbtm_pb number(20,8),
//			zntm_zn number(20,8),
//			pb_tmag number(20,8),
//			
//			pbnrate number(20,8),
//			znnrate number(20,8),
//			dr int,
//			ts char(19)
//			)
		
	//固定单元格字段列表 code
	//     选场，             班次，             开机时间,      原矿,       处理量湿量(吨）,原矿水分(%),处理量干量(吨） ,Pb(%) ,Zn(%) ,Ag(g/t)
	//pk_factory，pk_classorder，starhours,pk_oreinvmandoc,nwetnum,     nwater,  ,ndrynum    ,ore_pb,ore_zn,ore_ag	                           //         pk_oreinvbasdoc
    public String  pk_corp;
	public String  pk_factory;
	public String  pk_classorder;
	public String  vyear;
	public String  vmonth;
	public String  dbilldate;
	public String  starhours;
	public String  pk_oreinvmandoc;
	public String  pk_oreinvbasdoc;
	public UFDouble nwetnum;
	public UFDouble nwater;
	public UFDouble ndrynum;
	public UFDouble ore_pb;
	public UFDouble ore_zn;
	public UFDouble ore_ag;                                
    //产出量（吨）  ,Pb(%),Zn(%),Ag(g/t)---->铅精粉
	//pb_noutnum,pb_pb,pb_zn,pb_ag
	public UFDouble pb_noutnum;
	public UFDouble pb_pb;
	public UFDouble pb_zn;
	public UFDouble pb_ag; 	
	//产出量（吨）   ,Pb(%), Zn(%)---->锌精粉
	//zn_noutnum,zn_pb, zn_zn
	public UFDouble zn_noutnum;
	public UFDouble zn_pb;
	public UFDouble zn_zn;
	//Pb(%),Ag(g/t)------->铅尾矿品位
	//pbt_pb,pbt_ag
	public UFDouble pbt_pb;
	public UFDouble pbt_ag;
	//Zn(%)---->锌尾
	//znt_zn
	public UFDouble znt_zn;
	//铅精粉Pb(%),铅精粉Ag(%),锌精粉Zn(%)
	//pb_pb_recrate,pb_ag_recrate,zn_zn_recrate
	public UFDouble pb_pb_recrate;
	public UFDouble pb_ag_recrate;
	public UFDouble zn_zn_recrate;
	//Pb(t) ,Zn(t) ,Ag(kg)---->铅精粉金属量
	//ptm_pb,ptm_zn,pbtm_ag
	public UFDouble ptm_pb;
	public UFDouble ptm_zn;
	public UFDouble pbtm_ag;
	//Pb(t) ,Zn(t)----->锌精粉金属量
	//znm_pb,znm_zn
	public UFDouble znm_pb;
	public UFDouble znm_zn;
	//Pb(t),    Zn(t),    银(Kg)---->尾矿金属量
	//pbtm_pb, zntm_zn,   pb_tmag
	public UFDouble pbtm_pb;
	public UFDouble zntm_zn;
	public UFDouble pb_tmag;
	//铅精矿,    锌精矿,    银(Kg)---->产出率
	//pbnrate, znnrate,
	public UFDouble pbnrate;
	public UFDouble znnrate;
	
    public Integer dr;
    public UFDateTime ts;
    
    
	
	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getPk_factory() {
		return pk_factory;
	}

	public void setPk_factory(String pk_factory) {
		this.pk_factory = pk_factory;
	}

	public String getPk_classorder() {
		return pk_classorder;
	}

	public void setPk_classorder(String pk_classorder) {
		this.pk_classorder = pk_classorder;
	}

	public String getVyear() {
		return vyear;
	}

	public void setVyear(String vyear) {
		this.vyear = vyear;
	}

	public String getVmonth() {
		return vmonth;
	}

	public void setVmonth(String vmonth) {
		this.vmonth = vmonth;
	}

	public String getDbilldate() {
		return dbilldate;
	}

	public void setDbilldate(String dbilldate) {
		this.dbilldate = dbilldate;
	}

	public String getStarhours() {
		return starhours;
	}

	public void setStarhours(String starhours) {
		this.starhours = starhours;
	}

	public String getPk_oreinvmandoc() {
		return pk_oreinvmandoc;
	}

	public void setPk_oreinvmandoc(String pk_oreinvmandoc) {
		this.pk_oreinvmandoc = pk_oreinvmandoc;
	}

	public String getPk_oreinvbasdoc() {
		return pk_oreinvbasdoc;
	}

	public void setPk_oreinvbasdoc(String pk_oreinvbasdoc) {
		this.pk_oreinvbasdoc = pk_oreinvbasdoc;
	}

	public UFDouble getNwetnum() {
		return nwetnum;
	}

	public void setNwetnum(UFDouble nwetnum) {
		this.nwetnum = nwetnum;
	}

	public UFDouble getNwater() {
		return nwater;
	}

	public void setNwater(UFDouble nwater) {
		this.nwater = nwater;
	}

	public UFDouble getNdrynum() {
		return ndrynum;
	}

	public void setNdrynum(UFDouble ndrynum) {
		this.ndrynum = ndrynum;
	}

	public UFDouble getOre_pb() {
		return ore_pb;
	}

	public void setOre_pb(UFDouble ore_pb) {
		this.ore_pb = ore_pb;
	}

	public UFDouble getOre_zn() {
		return ore_zn;
	}

	public void setOre_zn(UFDouble ore_zn) {
		this.ore_zn = ore_zn;
	}

	public UFDouble getOre_ag() {
		return ore_ag;
	}

	public void setOre_ag(UFDouble ore_ag) {
		this.ore_ag = ore_ag;
	}

	public UFDouble getPb_noutnum() {
		return pb_noutnum;
	}

	public void setPb_noutnum(UFDouble pb_noutnum) {
		this.pb_noutnum = pb_noutnum;
	}

	public UFDouble getPb_pb() {
		return pb_pb;
	}

	public void setPb_pb(UFDouble pb_pb) {
		this.pb_pb = pb_pb;
	}

	public UFDouble getPb_zn() {
		return pb_zn;
	}

	public void setPb_zn(UFDouble pb_zn) {
		this.pb_zn = pb_zn;
	}

	public UFDouble getPb_ag() {
		return pb_ag;
	}

	public void setPb_ag(UFDouble pb_ag) {
		this.pb_ag = pb_ag;
	}

	public UFDouble getZn_noutnum() {
		return zn_noutnum;
	}

	public void setZn_noutnum(UFDouble zn_noutnum) {
		this.zn_noutnum = zn_noutnum;
	}

	public UFDouble getZn_pb() {
		return zn_pb;
	}

	public void setZn_pb(UFDouble zn_pb) {
		this.zn_pb = zn_pb;
	}

	public UFDouble getZn_zn() {
		return zn_zn;
	}

	public void setZn_zn(UFDouble zn_zn) {
		this.zn_zn = zn_zn;
	}

	public UFDouble getPbt_pb() {
		return pbt_pb;
	}

	public void setPbt_pb(UFDouble pbt_pb) {
		this.pbt_pb = pbt_pb;
	}

	public UFDouble getPbt_ag() {
		return pbt_ag;
	}

	public void setPbt_ag(UFDouble pbt_ag) {
		this.pbt_ag = pbt_ag;
	}

	public UFDouble getZnt_zn() {
		return znt_zn;
	}

	public void setZnt_zn(UFDouble znt_zn) {
		this.znt_zn = znt_zn;
	}

	public UFDouble getPb_pb_recrate() {
		return pb_pb_recrate;
	}

	public void setPb_pb_recrate(UFDouble pb_pb_recrate) {
		this.pb_pb_recrate = pb_pb_recrate;
	}

	public UFDouble getPb_ag_recrate() {
		return pb_ag_recrate;
	}

	public void setPb_ag_recrate(UFDouble pb_ag_recrate) {
		this.pb_ag_recrate = pb_ag_recrate;
	}

	public UFDouble getZn_zn_recrate() {
		return zn_zn_recrate;
	}

	public void setZn_zn_recrate(UFDouble zn_zn_recrate) {
		this.zn_zn_recrate = zn_zn_recrate;
	}

	public UFDouble getPtm_pb() {
		return ptm_pb;
	}

	public void setPtm_pb(UFDouble ptm_pb) {
		this.ptm_pb = ptm_pb;
	}

	public UFDouble getPtm_zn() {
		return ptm_zn;
	}

	public void setPtm_zn(UFDouble ptm_zn) {
		this.ptm_zn = ptm_zn;
	}

	public UFDouble getPbtm_ag() {
		return pbtm_ag;
	}

	public void setPbtm_ag(UFDouble pbtm_ag) {
		this.pbtm_ag = pbtm_ag;
	}

	public UFDouble getZnm_pb() {
		return znm_pb;
	}

	public void setZnm_pb(UFDouble znm_pb) {
		this.znm_pb = znm_pb;
	}

	public UFDouble getZnm_zn() {
		return znm_zn;
	}

	public void setZnm_zn(UFDouble znm_zn) {
		this.znm_zn = znm_zn;
	}

	public UFDouble getPbtm_pb() {
		return pbtm_pb;
	}

	public void setPbtm_pb(UFDouble pbtm_pb) {
		this.pbtm_pb = pbtm_pb;
	}

	public UFDouble getZntm_zn() {
		return zntm_zn;
	}

	public void setZntm_zn(UFDouble zntm_zn) {
		this.zntm_zn = zntm_zn;
	}

	public UFDouble getPb_tmag() {
		return pb_tmag;
	}

	public void setPb_tmag(UFDouble pb_tmag) {
		this.pb_tmag = pb_tmag;
	}

	public UFDouble getPbnrate() {
		return pbnrate;
	}

	public void setPbnrate(UFDouble pbnrate) {
		this.pbnrate = pbnrate;
	}

	public UFDouble getZnnrate() {
		return znnrate;
	}

	public void setZnnrate(UFDouble znnrate) {
		this.znnrate = znnrate;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_reportdaysum";
	}

	@Override
	public String getTableName() {
		return "xcgl_reportdaysum";
	}

}
