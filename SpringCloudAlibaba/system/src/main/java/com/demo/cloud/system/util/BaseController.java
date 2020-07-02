package com.demo.cloud.system.util;

import com.demo.cloud.system.dao.Dao;
import com.demo.cloud.system.dao.OtherDao;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 通用Controller
 *
 * @author sheng
 * @date 2020/07/01
 */
public class BaseController<T> {

    @Resource(name="Dao")
    private Dao dao;

    @Resource(name="OtherDao")
    private OtherDao otherDao;

    public PageData getPageData(){
        return new PageData(this.getRequest());
    }

    public ModelAndView getModelAndView(){
        return new ModelAndView();
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public Dao getDao() {
        return this.dao;
    }

    public Dao getOtherDao() {
        return this.otherDao;
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

	public Page getPage(PageData pd){
        if(!pd.containsKey("count")){
            pd.put("count",20);
        }
        if(!pd.containsKey("currpage")){
            pd.put("currpage",1);
        }
        Page page = new Page();
        page.setCurrentPage(pd.getInt("currpage"));
        page.setShowCount(pd.getInt("count"));
        pd.put("orderBy",this.getOrderBy(pd));
        page.setPd(pd);
		return page;
	}

    public void checkEmpty(String[] strs, PageData pd)throws Exception {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < strs.length; ++i) {
            if (!pd.containsKey(strs[i]) || pd.getString(strs[i]).length() == 0) {
                list.add(strs[i]);
            }
        }
        if(list.size() > 0){
            throw new Exception(list.toString());
        }
    }

    public String getOrderBy(PageData pd) {
        if (pd.containsKey("sidx") && pd.containsKey("sord")) {
            String sidx = pd.getString("sidx");
            String sord = pd.getString("sord");
            return sidx != null && sidx.length() != 0 ? "order by " + sidx + " " + sord : null;
        } else {
            return null;
        }
    }

    public ResponseBean outSuccess(){
        ResponseBean responseBean = new ResponseBean(true,ResponseCode.SUCCESS_OPTION);
        return responseBean;
    }

    public ResponseBean outString(String msg){
        ResponseBean responseBean = new ResponseBean(true,msg,ResponseCode.SUCCESS_OPTION);
        return responseBean;
    }

    public ResponseBean outData(PageData pd){
        ResponseBean responseBean = new ResponseBean(true,pd, ResponseCode.SUCCESS_OPTION);
        return responseBean;
    }

    public ResponseBean outList(List<PageData> list){
        ResponseBean responseBean = new ResponseBean(true,list, ResponseCode.SUCCESS_OPTION);
        return responseBean;
    }

    public ResponseBean outError(String msg){
        ResponseBean responseBean = new ResponseBean(false,msg,ResponseCode.BAD_REQUEST);
        return responseBean;
    }

    public ResponseBean outPage(Page page,List<PageData> list){
        PageData pd = new PageData();
        pd.put("list",list);
        pd.put("showCount",page.getShowCount());
        pd.put("currentPage",page.getCurrentPage());
        pd.put("currentResult",page.getCurrentResult());
        pd.put("totalPage",page.getTotalPage());
        pd.put("totalResult",page.getTotalResult());
        ResponseBean responseBean = new ResponseBean(true,pd,ResponseCode.SUCCESS_OPTION);
        return responseBean;
    }

    public ResponseBean outOtherMsg(boolean success ,String errCode, String errMsg){
        ResponseBean responseBean = new ResponseBean(success,errCode,errMsg);
        return responseBean;
    }

    public ResponseBean outOtherMsg(String errCode, String errMsg){
        return this.outOtherMsg(true,errCode,errMsg);
    }

}

