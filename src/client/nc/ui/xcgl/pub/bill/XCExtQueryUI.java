package nc.ui.xcgl.pub.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.ui.zmpub.pub.bill.QueryUI;
import nc.ui.zmpub.pub.bill.SonDefBillManageUI;
import nc.vo.pub.SuperVO;
/**
 * 第二种孙表查看对话框
 * @author mlr
 */
public class XCExtQueryUI extends QueryUI{
	private static final long serialVersionUID = -4464946133488938593L;
	public XCExtQueryUI(String type, String m_operator, String m_pkcorp,
			String key, SonDefBillManageUI myClientUI, boolean isEdit,
			String tile) {
		super(type, m_operator, m_pkcorp, key, myClientUI, isEdit, tile);
	}
    
	public Map<String, List<SuperVO>> cloneBufferData() {
		XCSonExtDefBillManageUI ui=(XCSonExtDefBillManageUI) getMyClientUI();
		Map<String, Map<String,SuperVO>> map1 = ui.getHl12();
		Map<String, List<SuperVO>> map2 = new HashMap<String, List<SuperVO>>();
		if (map1.size() > 0) {
			Iterator<String> it = map1.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				List<SuperVO> list = new ArrayList<SuperVO>();
				Map<String,SuperVO> bmap=map1.get(key);
				if(bmap!=null&&bmap.size()>0){
					for(String bkey:bmap.keySet()){
						list.add(bmap.get(bkey));
					}
				}
				map2.put(key, cloneBBVO(list));
			}
		}
		return map2;
	}

}
