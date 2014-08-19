package nc.bs.xcgl.mailltool;

import java.util.ArrayList;

import nc.bs.logging.Logger;
import nc.bs.pub.mail.MailTool;
import nc.bs.pub.mobile.WirelessManager;
import nc.bs.uap.sf.excp.MailException;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.msg.DefaultSMTP;
import nc.vo.pub.msg.SysMessageParam;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.xcgl.emaill.EmaillHVO;
import nc.vo.xcgl.pub.tool.XcPubTool;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 系统向外发送邮件工具。该类必须在后台调用
 * @author yangtao
 * @date 2014-2-26 下午03:52:24
 */
public class MaillTool {
	PfParameterVO paraVo;
	
	public MaillTool(PfParameterVO paraVo) {
		this.paraVo =paraVo;
	}

	/**
	 * 根据操作员查询对应的业务员
	 * @param userid
	 * @return
	 * @author yangtao
	 * @date 2014-2-26 下午03:54:29
	 */
	public static  String getClerk(PfParameterVO vo)throws BusinessException {
		String userid=(String) vo.m_makeBillOperator;
		return 
		PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("Clerk->getColValue(sm_userandclerk,pk_psndoc,userid,userid)", 
				new String[]{"userid"}, new String[]{userid}));
	}
	
	/**
	 * 获得该业务员的Email地址；
	 * @return
	 * @author yangtao
	 * @date 2014-2-26 下午04:07:01
	 */
    public static String getEmaillAdd(String pk_psnbasdoc)throws BusinessException {
    	
  
    	return PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(bd_psnbasdoc,email,pk_psnbasdoc,pk_psnbasdoc)", 
				new String[]{"pk_psnbasdoc"}, new String[]{pk_psnbasdoc}));
    	
    }
    
    public static String getUser_Name(String cuserid) throws BusinessException{
    	return PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(sm_user,user_name,cuserid,cuserid)", 
				new String[]{"cuserid"}, new String[]{cuserid}));
    }
    
    public static  EmaillHVO getmaillinfo(String use) throws BusinessException  {
    	EmaillHVO vo = new EmaillHVO();
    	vo.setPassword(PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(xcgl_emaill,password,use,use)", 
				new String[]{"use"}, new String[]{use})));
    	vo.setSender(PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(xcgl_emaill,sender,use,use)", 
				new String[]{"use"}, new String[]{use})));
    	vo.setSmtp(PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(xcgl_emaill,smtp,use,use)", 
				new String[]{"use"}, new String[]{use})));
    	vo.setTitle(PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(xcgl_emaill,title,use,use)", 
				new String[]{"use"}, new String[]{use})));
    	vo.setContent(PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("emaill->getColValue(xcgl_emaill,content,use,use)", 
				new String[]{"use"}, new String[]{use})));
        return vo;
	}
    /**
	 * 发送邮件的工具方法，非EJB调用
	 * <li>只可后台调用
	 * <li>邮件服务器信息配置在message4pf.xml文件中
	 * 
	 * @param sender
	 *            发送人邮件地址，如果为空，则取message4pf.xml中的配置信息
	 * @param title
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param recEmails
	 *            接收人邮件地址
	 * @param ccEmails
	 *            抄送人邮件地址
	 * @param attachFiles
	 *            附件文件路径
     * @throws BusinessException 
	 */
    public static void sendApprovalEmail(EmaillHVO vo ,PfParameterVO paraVo,
			String[] recEmails, String[] ccEmails, String[] attachFiles)
			throws BusinessException {
    	String sender=vo.getSender();
    	String title= vo.getTitle();
    	String content=vo.getContent()+getUser_Name((String)paraVo.m_makeBillOperator)+"制作的单据。已审批完成。\n" +
    			"单据详情： 单据类型："+paraVo.m_billType +",\n" +
    					"单据号为："+paraVo.m_billNo+",\n" +
    							"详情请登录NC系统查看\n" +
    									"";
		Logger.info("==================");
		Logger.info(">> 发送邮件PfUtilTools.sendEmail() called");
		Logger.info("==================");
		if (recEmails == null || recEmails.length == 0)
			return;

		SysMessageParam smp = WirelessManager.fetchSysMsgParam();
		if (smp == null)
			throw new MailException("错误1：没有正确配置邮件服务器文件/ierp/bin/message4pf.xml");
		DefaultSMTP defaultSmtp = smp.getSmtp();
		if (defaultSmtp == null)
			throw new MailException("错误2：没有正确配置邮件服务器文件/ierp/bin/message4pf.xml");

		String smtpHost = vo.getSmtp();
		String fromAddr = sender;
		String userName = vo.getSender();
		String password = vo.getPassword();
		String senderName = "NC系统自动邮件（请勿回复）";

		if (password != null)
			password = XcPubTool.decrypt(password);
		if (sender == null || sender.length() == 0)
			fromAddr = defaultSmtp.getSender();

		// XXX:接收地址必须以","分隔
		String receivers = "";
		for (int i = 0; i < recEmails.length; i++)
			receivers += recEmails[i] + ",";
		String ccs = "";
		for (int i = 0; i < (ccEmails == null ? 0 : ccEmails.length); i++)
			ccs += ccEmails[i] + ",";

		try {
			Logger.info("邮件接收者receivers=" + receivers);
			MailTool.sendMailWithAttach(smtpHost, fromAddr, senderName,
					userName, password, receivers, ccs, title, content,
					attachFiles);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new MailException("错误：发送Email失败：" + e.getMessage());
		}
	}
}
