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
 * ϵͳ���ⷢ���ʼ����ߡ���������ں�̨����
 * @author yangtao
 * @date 2014-2-26 ����03:52:24
 */
public class MaillTool {
	PfParameterVO paraVo;
	
	public MaillTool(PfParameterVO paraVo) {
		this.paraVo =paraVo;
	}

	/**
	 * ���ݲ���Ա��ѯ��Ӧ��ҵ��Ա
	 * @param userid
	 * @return
	 * @author yangtao
	 * @date 2014-2-26 ����03:54:29
	 */
	public static  String getClerk(PfParameterVO vo)throws BusinessException {
		String userid=(String) vo.m_makeBillOperator;
		return 
		PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool.execFomular("Clerk->getColValue(sm_userandclerk,pk_psndoc,userid,userid)", 
				new String[]{"userid"}, new String[]{userid}));
	}
	
	/**
	 * ��ø�ҵ��Ա��Email��ַ��
	 * @return
	 * @author yangtao
	 * @date 2014-2-26 ����04:07:01
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
	 * �����ʼ��Ĺ��߷�������EJB����
	 * <li>ֻ�ɺ�̨����
	 * <li>�ʼ���������Ϣ������message4pf.xml�ļ���
	 * 
	 * @param sender
	 *            �������ʼ���ַ�����Ϊ�գ���ȡmessage4pf.xml�е�������Ϣ
	 * @param title
	 *            �ʼ�����
	 * @param content
	 *            �ʼ�����
	 * @param recEmails
	 *            �������ʼ���ַ
	 * @param ccEmails
	 *            �������ʼ���ַ
	 * @param attachFiles
	 *            �����ļ�·��
     * @throws BusinessException 
	 */
    public static void sendApprovalEmail(EmaillHVO vo ,PfParameterVO paraVo,
			String[] recEmails, String[] ccEmails, String[] attachFiles)
			throws BusinessException {
    	String sender=vo.getSender();
    	String title= vo.getTitle();
    	String content=vo.getContent()+getUser_Name((String)paraVo.m_makeBillOperator)+"�����ĵ��ݡ���������ɡ�\n" +
    			"�������飺 �������ͣ�"+paraVo.m_billType +",\n" +
    					"���ݺ�Ϊ��"+paraVo.m_billNo+",\n" +
    							"�������¼NCϵͳ�鿴\n" +
    									"";
		Logger.info("==================");
		Logger.info(">> �����ʼ�PfUtilTools.sendEmail() called");
		Logger.info("==================");
		if (recEmails == null || recEmails.length == 0)
			return;

		SysMessageParam smp = WirelessManager.fetchSysMsgParam();
		if (smp == null)
			throw new MailException("����1��û����ȷ�����ʼ��������ļ�/ierp/bin/message4pf.xml");
		DefaultSMTP defaultSmtp = smp.getSmtp();
		if (defaultSmtp == null)
			throw new MailException("����2��û����ȷ�����ʼ��������ļ�/ierp/bin/message4pf.xml");

		String smtpHost = vo.getSmtp();
		String fromAddr = sender;
		String userName = vo.getSender();
		String password = vo.getPassword();
		String senderName = "NCϵͳ�Զ��ʼ�������ظ���";

		if (password != null)
			password = XcPubTool.decrypt(password);
		if (sender == null || sender.length() == 0)
			fromAddr = defaultSmtp.getSender();

		// XXX:���յ�ַ������","�ָ�
		String receivers = "";
		for (int i = 0; i < recEmails.length; i++)
			receivers += recEmails[i] + ",";
		String ccs = "";
		for (int i = 0; i < (ccEmails == null ? 0 : ccEmails.length); i++)
			ccs += ccEmails[i] + ",";

		try {
			Logger.info("�ʼ�������receivers=" + receivers);
			MailTool.sendMailWithAttach(smtpHost, fromAddr, senderName,
					userName, password, receivers, ccs, title, content,
					attachFiles);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new MailException("���󣺷���Emailʧ�ܣ�" + e.getMessage());
		}
	}
}
