package com.demo.cloud.system.controller;


import com.demo.cloud.system.util.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    //模块标识
    final String menu_code = "system.menu";

    /**
     * @api {post} /menu/save 保存菜单
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 新增或修改记录
     * @apiParam {String} [id] 新增时为空，修改时必填
     * @apiParam {String} menu_name 菜单名称
     * @apiParam {String} menu_code 菜单标识
     * @apiParam {String} menu_url 菜单rul
     * @apiParam {Int} index_ 顺序
     * @apiParam {String} parent_id 父级ID
     * @apiParam {String} [menu_icon] 菜单图标
     * @apiParam {String} [menu_icon1] 菜单图标1
     * @apiParam {String} sys_menu 系统菜单，0否、1是
     * @apiParam {String} open_type 菜单类型，0外链，1内链
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/save")
    public ResponseBean save(@RequestBody PageData pd) throws Exception {
        String id = pd.getString("id");
        String xml = "";
        if (id != null && id.length() > 0) {
            xml = "MenuMapper.edit";
        } else {
            xml = "MenuMapper.add";
        }
        if (pd.getString("id").equals(pd.getString("parent_id"))) {
            return this.outError("不能把自己选为顶级");
        }
        this.getDao().save(xml, pd);
        return this.outString("");
    }

    /**
     * @api {post} /menu/get 菜单详情
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 查询一条菜单信息
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object} data 对象
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.menu_name 菜单名称
     * @apiSuccess {String} data.menu_code 菜单标识
     * @apiSuccess {String} data.menu_url 菜单url
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {String} data.parent_id 父级
     * @apiSuccess {String} data.menu_icon 菜单图标
     * @apiSuccess {String} data.menu_icon1 菜单图标1
     * @apiSuccess {Int} data.sys_menu 系统菜单、0否、1是
     * @apiSuccess {Int} data.open_type 打开类型、0外链、1内链
     * */
    @RequestMapping("/get")
    public ResponseBean get(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        PageData pd1 = (PageData) this.getDao().findForObject("MenuMapper.get",pd);
        return this.outData(pd1);
    }

    /**
     * @api {post} /menu/delete 删除菜单
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 删除一条菜单信息
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/delete")
    public ResponseBean delete(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        this.getDao().delete("MenuMapper.delete", pd);
        return this.outSuccess();
    }

    /**
     * @api {post} /menu/list 菜单列表
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 分页查询菜单列表
     * @apiParam {String} currpage 当前页
     * @apiParam {String} count 每页多少条
     * @apiParam {String} [parent_id] 父级
     * @apiParam {String} [key] 模糊搜索，支持名称
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {String} totalpage 总页数
     * @apiSuccess {String} currpage 当前页
     * @apiSuccess {String} count 每页多少条
     * @apiSuccess {String} totalresult 总的条数
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.menu_name 菜单名称
     * @apiSuccess {String} data.menu_code 菜单标识
     * @apiSuccess {String} data.menu_url 菜单url
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {String} data.parent_id 父级
     * @apiSuccess {String} data.menu_icon 菜单图标
     * @apiSuccess {String} data.menu_icon1 菜单图标1
     * @apiSuccess {Int} data.sys_menu 系统菜单、0否、1是
     * @apiSuccess {Int} data.open_type 打开类型、0外链、1内链
     * */
    @RequestMapping("/list")
    public ResponseBean getList(@RequestBody PageData pd) throws Exception {
        String [] item = {"currpage","count"};
        this.checkEmpty(item,pd);
        Page page = this.getPage(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "MenuMapper.getListPage", page);
        return this.outPage(page, list);
    }

    /**
     * @api {post} /menu/menuList 菜单列表(一级下所有的菜单)
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 菜单列表(一级下所有的菜单)
     * @apiParam {String} user_id 用户ID
     * @apiParam {String} url 上级页面的url
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.menu_name 菜单名称
     * @apiSuccess {String} data.menu_code 菜单标识
     * @apiSuccess {String} data.menu_url 菜单url
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {String} data.parent_id 父级
     * @apiSuccess {String} data.menu_icon 菜单图标
     * @apiSuccess {String} data.menu_icon1 菜单图标1
     * @apiSuccess {Int} data.sys_menu 系统菜单、0否、1是
     * @apiSuccess {Int} data.open_type 打开类型、0外链、1内链
     * */
    @RequestMapping("/menuList")
    public ResponseBean getMenuList(@RequestBody PageData pd) throws Exception {
        String menu_id = "";
        PageData pdMenu = null; //获得全部菜单
        List<LinkedHashMap> listAllMenu = (List<LinkedHashMap>)pdMenu.get("data");
        // 根据url获取到当前菜单的ID
        String url = pd.getString("url");
        for (int i = 0; i < listAllMenu.size(); i++) {
            if (url.indexOf(listAllMenu.get(i).get("menu_url").toString()) > -1
                    && !"0".equals(listAllMenu.get(i).get("parent_id").toString())) {
                menu_id = listAllMenu.get(i).get("id").toString();
                break;
            }
        }
        String sql = "SELECT id FROM sys_menu WHERE FIND_IN_SET(id,queryParentMenu('"
                + menu_id + "')) AND parent_id='0'";
        Object obj = this.getDao().findSinge(sql);
        if (obj == null || obj.toString().length() == 0) {
            return this.outError("参数值错误");
        }
        pd.put("parent_id", obj.toString()); // 父级ID
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "MenuMapper.getMenuList", pd);
        pd.put("page", list);
        return this.outData(pd);
    }

    /**
     * @api {post} /menu/oneMenuList 菜单列表(一级下所有的菜单)
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 查询当前登录人一级菜单权限
     * @apiParam {String} user_id 用户ID
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.menu_name 菜单名称
     * @apiSuccess {String} data.menu_code 菜单标识
     * @apiSuccess {String} data.menu_url 菜单url
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {String} data.parent_id 父级
     * @apiSuccess {String} data.menu_icon 菜单图标
     * @apiSuccess {String} data.menu_icon1 菜单图标1
     * @apiSuccess {Int} data.sys_menu 系统菜单、0否、1是
     * @apiSuccess {Int} data.open_type 打开类型、0外链、1内链
     * */
    @RequestMapping("/oneMenuList")
    public ResponseBean getOneMenuList(@RequestBody PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "MenuMapper.getOneMenuList", pd);
        return this.outList(list);
    }


}
