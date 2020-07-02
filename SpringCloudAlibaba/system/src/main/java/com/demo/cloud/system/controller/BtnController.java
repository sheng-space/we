package com.demo.cloud.system.controller;

import com.demo.cloud.system.util.BaseController;
import com.demo.cloud.system.util.Page;
import com.demo.cloud.system.util.PageData;
import com.demo.cloud.system.util.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * 菜单按钮
 *
 * @author sheng
 * @date 2016/07/02
 */
@RestController
@RequestMapping("/menu/btn")
public class BtnController extends BaseController {

    @Resource
    private MenuController menu;

    /**
     * @api {post} /menu/btn/list 01_按钮列表
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 分页查询按钮列表
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
     * */
    @RequestMapping("/getList")
    public ResponseBean getList(@RequestBody PageData pd) throws Exception {
        String [] item = {"currpage","count"};
        this.checkEmpty(item,pd);
        Page page = this.getPage(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "BtnMapper.getListPage", page);
        return this.outPage(page, list);
    }

    /**
     * @api {post} /menu/btn/save 02_保存按钮
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 新增或修改记录
     * @apiParam {String} [id] 新增时为空，修改时必填
     * @apiParam {String} btn_code 按钮标识
     * @apiParam {String} btn_name 按钮名称
     * @apiParam {String} menu_id 菜单ID
     * @apiParam {Int} seq 顺序
     * @apiParam {Int} group_ 分组
     * @apiParam {String} [icon] 按钮图标
     * @apiParam {String} [remark] 按钮描述
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/save")
    public ResponseBean save(@RequestBody PageData pd) throws Exception {
        String [] item = {"number"};
        this.checkEmpty(item,pd);
        String xml = "";
        String id = pd.getString("id");
        if (StringUtils.isEmpty(id)) {
            xml = "BtnMapper.edit";
        } else {
            pd.put("id", this.getUUID());
            xml = "BtnMapper.add";
        }
        this.getDao().save(xml, pd);
        return this.outSuccess();
    }

    /**
     * @api {post} /menu/btn/get 03_按钮详情
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 查询一条按钮信息
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object} data 对象
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.btn_code 按钮标识
     * @apiSuccess {String} data.btn_name 按钮名称
     * @apiSuccess {String} data.menu_id 菜单ID
     * @apiSuccess {String} data.btn_icon 按钮图标
     * @apiSuccess {String} data.btn_icon1 按钮图标1
     * @apiSuccess {String} data.btn_desc 按钮描述
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {Int} data.group_ 分组
     * */
    @RequestMapping("/get")
    public ResponseBean get(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        PageData pd1 = (PageData) this.getDao().findForObject("BtnMapper.get",pd);
        return this.outData(pd1);
    }

    /**
     * @api {post} /btn/getUserRightInfo 查询权限（当前用户所能看到的）
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 查询一条按钮信息
     * @apiParam {String} user_id 用户ID
     * @apiParam {String} url 菜单地址
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.btn_code 按钮标识
     * @apiSuccess {String} data.btn_name 按钮名称
     * @apiSuccess {String} data.menu_id 菜单ID
     * @apiSuccess {String} data.btn_icon 按钮图标
     * @apiSuccess {String} data.btn_icon1 按钮图标1
     * @apiSuccess {String} data.btn_desc 按钮描述
     * @apiSuccess {Int} data.index_ 顺序
     * @apiSuccess {Int} data.group_ 分组
     * */
    @RequestMapping("/getUserRightInfo")
    public ResponseBean getUserRightInfo(@RequestBody PageData pd) throws Exception {
        String [] item = {"user_id"};
        this.checkEmpty(item,pd);
        String menu_id = "";
        PageData pdMenu = null;
        List<LinkedHashMap> listAllMenu = (List<LinkedHashMap>)pdMenu.get("data");
        String url = pd.getString("url");
        for (int i = 0; i < listAllMenu.size(); i++) {
            if (url.indexOf(listAllMenu.get(i).get("menu_url").toString()) > -1
                    && !"0".equals(listAllMenu.get(i).get("parent_id").toString())) {
                menu_id = listAllMenu.get(i).get("id").toString();
                break;
            }
        }
        pd.put("menu_id", menu_id);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "BtnMapper.getUserRightInfo", pd);
        return this.outList(list);
    }

    /**
     * @api {post} /btn/delete 删除按钮
     * @apiGroup 菜单管理
     * @apiVersion 1.0.0
     * @apiDescription 删除一条按钮信息
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/delete")
    public ResponseBean delete(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        this.getDao().delete("BtnMapper.delete", pd);
        return this.outSuccess();
    }

}
