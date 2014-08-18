package nc.vo.xcgl.pub.logger;
import nc.bs.logging.Logger;
import nc.vo.xcgl.pub.consts.PubOtherConst;
/**
 * xew选厂管理日志
 * @author mlr
 */
public class XCLogger extends Logger{
	/**
	 * 输出日志
	 * @param msg
	 */
	public static void printInfor(String Infor){
		XCLogger.init(PubOtherConst.loggername);
		XCLogger.info("Infor->"+Infor);
		XCLogger.init();
	}
	/**
	 * 输出日志
	 * @param msg
	 */
	public static void printDebug(String Debug){
		XCLogger.init(PubOtherConst.loggername);
		XCLogger.debug("Debug->"+Debug);
		XCLogger.init();
	}
	/**
	 * 输出日志
	 * @param msg
	 */
	public static void printError(String Error){
		XCLogger.init(PubOtherConst.loggername);
		XCLogger.error("Error->"+Error);
		XCLogger.init();
	}
}
