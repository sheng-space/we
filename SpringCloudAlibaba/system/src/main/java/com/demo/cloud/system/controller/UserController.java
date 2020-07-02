package com.demo.cloud.system.controller;

import com.demo.cloud.system.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 *
 * @author sheng
 * @date 2016/07/02
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private RoleController roleController1;

    @RequestMapping("/login")
    public ResponseBean login(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "UserMapper.login", pd);
        if (list == null || list.size() == 0) {
            return this.outError("账号或密码错误");
        }
        if (list.size() > 1) {
            return this.outError("账号异常,请联系管理员");
        }
        PageData Account = list.get(0);
        Object org_status = Account.get("org_status");
        if (org_status == null || org_status.toString().length() == 0) {
            return this.outError("此账号不存在部门信息,请联系管理员");
        }
        if (!"1".equals(org_status.toString())) {
            return this.outError("此账号组织信息被禁用,请联系管理员");
        }
        Object role_name = Account.get("role_name");
        if (role_name == null || role_name.toString().length() == 0) {
            return this.outError("此账号不存在角色信息,请联系管理员");
        }
        String user_status = Account.getString("user_status");
        if ("0".equals(user_status)) {
            return this.outError("账号已被禁用,请联系管理员");
        }
        String token_group = pd.getString("token_group");
        PageData pdToken = new PageData();
        pdToken.put("rights", "");
        pdToken.putAll(Account);
        Account.put("token_group", token_group);
        return this.outData(Account);
    }



    @RequestMapping("/exitLogin")
    public ResponseBean exitLogin(@RequestBody PageData pd) throws Exception {

        return this.outSuccess();
    }

    @RequestMapping("/get")
    public ResponseBean get(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        PageData User = (PageData) this.getDao().findForObject("UserMapper.get", pd);
        return this.outData(User);
    }


    @Transactional
    @RequestMapping("/save")
    public ResponseBean save(@RequestBody PageData pd) throws Exception {
        Object id = pd.get("id");
        String xml = "";
        String sql = "select 1 from sys_user where account='"
                + pd.getString("account") + "' and deleted=0";
        if (id != null && id.toString().length() > 0) {
            xml = "UserMapper.edit";
            sql += " and id<>'" + id + "'";
        } else {
            pd.put("add_time", DateUtil.getTime());
            xml = "UserMapper.add";
            id = this.getUUID();
        }
        if (this.getDao().findExist(sql)) {
            return this.outError("账号重复");
        }
        pd.put("id", id);
        this.getDao().save(xml, pd);
        return this.outSuccess();
    }

    @RequestMapping("/getList")
    public ResponseBean getList(@RequestBody PageData pd) throws Exception {
        String [] item = {"currpage","count"};
        this.checkEmpty(item,pd);
        Page page = this.getPage(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "UserMapper.getListPage", page);
        return this.outPage(page, list);
    }


    @RequestMapping("/listAll")
    public ResponseBean getAll(@RequestBody PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "UserMapper.getAll", pd);
        return this.outList(list);
    }

    @RequestMapping("/delete")
    public ResponseBean delete(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        this.getDao().delete("UserMapper.delete", pd);
        return this.outString("");
    }

    @RequestMapping("/status")
    public ResponseBean status(@RequestBody PageData pd) throws Exception {
        String id = pd.getString("id");
        PageData Tem = new PageData();
        Tem.put("user_id", id);
        this.getDao().save("UserMapper.status", pd);
        return this.outString("");
    }


    @RequestMapping("/getUserRights")
    public ResponseBean getUserRights(@RequestBody PageData pd) throws Exception {
        if(!pd.containsKey("user_id")){
            return this.outError("参数user_id是必须的");
        }
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "UserMapper.getUserRights", pd);
        String type = pd.getString("type");
        if("1".equals(type)){
            list = Tools.convertMenu(list,"id","parent_id","0");
        }
        return this.outList(list);
    }

    @Transactional
    @RequestMapping("/saveUserRights")
    public ResponseBean saveUserRights(@RequestBody PageData pd) {
        if(!pd.containsKey("user_id")){
            return this.outError("参数user_id是必须的");
        }
        if(!pd.containsKey("org_ids")){
            return this.outError("参数org_ids是必须的");
        }
        String org_ids = pd.getString("org_ids");
        String user_id = pd.getString("user_id");
        List<String> listsql = new ArrayList<String>();
        String sql = "delete from sys_user_rights where user_id='" + user_id
                + "'";
        listsql.add(sql);
        String[] strs = org_ids.split(",");
        for (int i = 0; i < strs.length; i++) {
            sql = "insert into sys_user_rights(id,user_id,org_id) values('"
                    + this.getUUID() + "','" + user_id + "','" + strs[i] + "')";
            listsql.add(sql);
        }
        for (int i = 0; i < listsql.size(); i++) {
            this.getDao().ExecuteSql(listsql.get(i));
        }
        return this.outString("");
    }

    /**
     * @功能：修改密码
     * @作者：RC
     * */
    @RequestMapping("/changePwd")
    public ResponseBean changePwd(@RequestBody PageData pd) throws Exception {
        this.getDao().save("UserMapper.changePwd", pd);
        return this.outString("");
    }

}
