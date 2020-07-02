package com.demo.cloud.system.controller;

import com.demo.cloud.system.util.BaseController;
import com.demo.cloud.system.util.Page;
import com.demo.cloud.system.util.PageData;
import com.demo.cloud.system.util.ResponseBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试
 *
 * @author sheng
 * @date 2020/07/01
 */
@RestController
@RefreshScope
public class TestController extends BaseController {

    @Value("${config.info}")
    private String info;

    @GetMapping(value = "/test/hi")
    public String test() {
        return info;
    }

    @GetMapping(value = "/getUser/{id}")
    public ResponseBean getUser(@PathVariable("id") String id) throws Exception {
        PageData pd = (PageData) this.getDao().findForObject("TestMapper.get",new PageData("id",id));
        return this.outData(pd);
    }

    @GetMapping(value = "/getUser2/{id}")
    public ResponseBean getUser2(@PathVariable("id") String id) throws Exception {
        PageData pd = (PageData) this.getDao().findForObject("TestMapper.get2",new PageData("id",id));
        return this.outData(pd);
    }

    @GetMapping(value = "/getUserList")
    public ResponseBean getUserList() throws Exception {
        PageData pd = new PageData();
        Page page = new Page();
        page.setCurrentPage(1);
        page.setShowCount(10);
        page.setPd(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "TestMapper.getListPage", page);
        return this.outPage(page,list);
    }

    @GetMapping(value = "/getUserList2")
    public ResponseBean getUserList2() throws Exception {
        PageData pd = new PageData();
        Page page = new Page();
        page.setCurrentPage(1);
        page.setShowCount(10);
        page.setPd(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "TestMapper.getListPage2", page);
        return this.outPage(page,list);
    }
}
