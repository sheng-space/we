package com.demo.cloud.system.controller;


import com.demo.cloud.system.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 *
 * @author sheng
 * @date 2016/07/02
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    /**
     * @api {post} /role/list 角色列表
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 分页查询角色列表
     * @apiParam {String} currpage 当前页
     * @apiParam {String} count 每页多少条
     * @apiParam {String} [key] 模糊搜索，支持名称
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {String} totalpage 总页数
     * @apiSuccess {String} currpage 当前页
     * @apiSuccess {String} count 每页多少条
     * @apiSuccess {String} totalresult 总的条数
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.role_no 角色编号
     * @apiSuccess {String} data.role_name 角色名称
     * @apiSuccess {String} data.role_type 角色组别ID
     * @apiSuccess {Int} data.role_sys 系统角色，0否、1是
     * @apiSuccess {String} data.role_desc 角色描述
     * @apiSuccess {String} data.type_name 角色组别名称
     * @apiSuccess {Int} data.deleted 删除，0未删除、1已删除
     * */
    @RequestMapping("/list")
    public ResponseBean getList(@RequestBody PageData pd) throws Exception {
        String [] item = {"currpage","count"};
        this.checkEmpty(item,pd);
        Page page = this.getPage(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "RoleMapper.getListPage", page);
        return this.outPage(page, list);
    }

    /**
     * @api {post} /role/get 角色详情
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 根据Id查询角色详情
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object} data 对象
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.role_no 角色编号
     * @apiSuccess {String} data.role_name 角色名称
     * @apiSuccess {String} data.role_type 角色组别ID
     * @apiSuccess {Int} data.role_sys 系统角色，0否、1是
     * @apiSuccess {String} data.role_desc 角色描述
     * @apiSuccess {String} data.type_name 角色组别名称
     * @apiSuccess {Int} data.deleted 删除，0未删除、1已删除
     * */
    @RequestMapping("/get")
    public ResponseBean get(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        PageData pd1 = (PageData) this.getDao().findForObject("RoleMapper.get", pd);
        return this.outData(pd1);
    }

    /**
     * @api {post} /role/listAll 所有角色
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 查询所有角色
     * @apiParam {String} [param] 无
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.role_no 角色编号
     * @apiSuccess {String} data.role_name 角色名称
     * @apiSuccess {String} data.role_type 角色组别ID
     * @apiSuccess {Int} data.role_sys 系统角色，0否、1是
     * @apiSuccess {String} data.role_desc 角色描述
     * @apiSuccess {Int} data.deleted 删除，0未删除、1已删除
     * */
    @RequestMapping("/listAll")
    public ResponseBean getAll(@RequestBody PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "RoleMapper.getAll", pd);
        return this.outList(list);
    }

    /**
     * @api {post} /role/save 保存角色
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 新增或修改记录
     * @apiParam {String} [id] 新增时为空，修改时必填
     * @apiParam {String} role_no 角色编号
     * @apiParam {String} role_name 角色名称
     * @apiParam {String} role_type 角色组别
     * @apiParam {Int} roole_sys 系统角色，0否、1是
     * @apiParam {String} [role_desc] 按钮描述
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/save")
    public ResponseBean save(@RequestBody PageData pd) throws Exception {
        String id = pd.getString("id");
        String xml = "";
        String sql = "select 1 from sys_role where role_no='"
                + pd.getString("role_no") + "' and deleted=0";
        if (id != null && id.length() > 0) {
            xml = "RoleMapper.edit";
            sql += " and id<>'" + id + "'";
        } else {
            xml = "RoleMapper.add";
            id = this.getUUID();
        }
        if (this.getDao().findExist(sql)) {
            return this.outError("编号重复");
        }
        pd.put("id", id);
        this.getDao().save(xml, pd);
        return this.outSuccess();
    }

    /**
     * @api {post} /role/delete 删除角色
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 删除一条角色信息
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/delete")
    public ResponseBean delete(@RequestBody PageData pd) throws Exception {
        String id = pd.getString("id");
        this.getDao().delete("RoleMapper.delete", id);
        return this.outSuccess();
    }

    /**
     * @api {post} /role/right 角色权限
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 查询当前有角色有权限的菜单与按钮记录
     * @apiParam {String} role_id 角色Id
     * @apiParam {String} [type] 类型、0默认查询、1递归查询
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.menu_name 菜单名称
     * @apiSuccess {String} data.parent_id 上级ID
     * @apiSuccess {Int} data.checkd 该角色是否拥有此权限，0否、1是
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {String} data.menu_url 角色url
     * @apiSuccess {String} data.btn_code 按钮code
     * @apiSuccess {Int} data.group_ 组别
     * @apiSuccess {String} data.menu_code 菜单code
     * */
    @RequestMapping("/right")
    public ResponseBean right(@RequestBody PageData pd) throws Exception {
        String type = pd.getString("type");
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "RoleMapper.right", pd);
        if("1".equals(type)){
            list = Tools.convertMenu(list,"id","parent_id","0");
        }
        return this.outList(list);
    }

    /**
     * @api {post} /role/saveRight 保存权限
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 保存当前用户的权限信息
     * @apiParam {String} role_id 角色ID
     * @apiParam {String} ids 用逗号分隔的权限标识ID
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/saveRight")
    @Transactional
    public ResponseBean saveRight(@RequestBody PageData pd) {

        String role_id = pd.getString("role_id");
        List<String> list_sql = new ArrayList<String>();
        // 先移除记录
        list_sql.add("delete from sys_mright where role_id='" + role_id + "'");
        list_sql.add("delete from sys_bright where role_id='" + role_id + "'");

        String[] ids = pd.getString("ids").split(",");
        String id;
        for (int i = 0; i < ids.length; i++) {
            id = ids[i];
            if (id.indexOf("BTN") > -1) {
                list_sql.add("insert into sys_bright(id,role_id,btn_id)values('"
                        + this.getUUID()
                        + "','"
                        + role_id
                        + "','"
                        + id.replace("BTN", "") + "')");
            } else {
                list_sql.add("insert into sys_mright(id,role_id,menu_id)values('"
                        + this.getUUID() + "','" + role_id + "','" + id + "')");
            }
        }
        for (int i = 0; i < list_sql.size(); i++) {
            this.getDao().ExecuteSql(list_sql.get(i));
        }
        return this.outString("");
    }

    /**
     * @api {post} /role/getRights 得到拼接权限
     * @apiGroup 角色管理
     * @apiVersion 1.0.0
     * @apiDescription 获得拼接好的权限项，拼接规则：menu_code.btn_code
     * @apiParam {String} role_id 角色ID
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object} data 对象
     * @apiSuccess {String} data.rights 权限项字符串
     * */
    @RequestMapping("/getRights")
    public ResponseBean getRights(@RequestBody PageData pd) throws Exception{
        if(!pd.containsKey("role_id")){
            return this.outError("role_id参数是必须的");
        }
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "RoleMapper.right", pd);
        String rights = "0";
        String menuId = "";
        String btnId = "";
        for(int i=0;i<list.size();i++){
            menuId = list.get(i).getString("id");
            if(menuId.indexOf("BTN") > 0 || "0".equals(list.get(i).getString("checkd"))){
                continue;
            }
            for(int j=0;j<list.size();j++){
                btnId = list.get(j).getString("id");
                if(btnId.indexOf("BTN") < 0 || "0".equals(list.get(j).getString("checkd"))){
                    continue;
                }
                if(!list.get(i).getString("id").equals(list.get(j).getString("parent_id"))){
                    continue;
                }
                rights += "," + list.get(i).getString("menu_code") + "." + list.get(j).getString("btn_code");
            }
        }
        PageData pdRe = new PageData();
        pdRe.put("rights",rights);
        return this.outData(pdRe);
    }

}
