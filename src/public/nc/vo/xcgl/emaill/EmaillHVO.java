package nc.vo.xcgl.emaill;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;

public class EmaillHVO extends SuperVO{

	/**
	 * @date 2014-2-26����10:34:42
	 */
	private static final long serialVersionUID = 1L;
	private String pk_emaill;
	private String sender;
	private String password;
	private String smtp;
	private String pop;
	private String vmemo;
	private String title;
	private String content;
	private UFDate dbilldate;
	private String pk_corp;
	private String explain;
	private Integer dr;
	private Integer use;  //use =1 �õ����ѱ���ɹ�   use=2�õ����ѱ��޸ģ� use=3 �õ����Ѿ��ύ  use=4 �õ����Ѿ�������� use=5 �����ѱ�ɾ��
	/**
	 * use =1 �õ����ѱ���ɹ�;   use=2�õ����ѱ��޸ģ� use=3 �õ����Ѿ��ύ ; use=4 �õ����Ѿ�������� ;use=5 �����ѱ�ɾ��
	 * @return
	 * @author yangtao
	 * @date 2014-2-26 ����03:49:01
	 */
	public Integer getUse() {
		return use;
	}
	/**
	 * use =1 �õ����ѱ���ɹ�;   use=2�õ����ѱ��޸ģ� use=3 �õ����Ѿ��ύ ; use=4 �õ����Ѿ�������� ;use=5 �����ѱ�ɾ��
	 * @param use
	 * @author yangtao
	 * @date 2014-2-26 ����03:49:35
	 */
	public void setUse(Integer use) {
		this.use = use;
	}
	public String getPk_emaill() {
		return pk_emaill;
	}
	public void setPk_emaill(String pk_emaill) {
		this.pk_emaill = pk_emaill;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getPop() {
		return pop;
	}
	public void setPop(String pop) {
		this.pop = pop;
	}
	public String getVmemo() {
		return vmemo;
	}
	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}
	@Override
	public String getPKFieldName() {
		
		return "pk_emaill";
	}
	@Override
	public String getParentPKFieldName() {
		
		return null;
	}
	@Override
	public String getTableName() {
		
		return "xcgl_emaill";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UFDate getDbilldate() {
		return dbilldate;
	}
	public void setDbilldate(UFDate dbilldate) {
		this.dbilldate = dbilldate;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	
}
