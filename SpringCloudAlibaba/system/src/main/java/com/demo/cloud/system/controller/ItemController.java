package com.demo.cloud.system.controller;

import com.demo.cloud.system.util.BaseController;
import com.demo.cloud.system.util.Page;
import com.demo.cloud.system.util.PageData;
import com.demo.cloud.system.util.ResponseBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统参数配置
 *
 * @author sheng
 * @date 2016/07/02
 */
@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {


    /**
     * @api {post} /item/getTypeList 字典分类查询
     * @apiGroup 系统字典
     * @apiVersion 1.0.0
     * @apiDescription 查询所有的字典分类记录
     * @apiParam {String} [param] 无
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object[]} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.number 编号
     * @apiSuccess {String} data.name 名称
     * @apiSuccess {String} data.type_ 类型，作用待定
     * @apiSuccess {String} data.remark 备注
     * @apiSuccess {String} data.deleted 删除，0未删除、1已删除
     * @apiSuccess {String} data.parent_id	 父级
     * */
    @RequestMapping("/getList")
    public ResponseBean getList(@RequestBody PageData pd) throws Exception {
        String [] item = {"currpage","count"};
        this.checkEmpty(item,pd);
        Page page = this.getPage(pd);
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "ItemMapper.getListPage", page);
        return this.outPage(page,list);
    }

    /**
     * @api {post} /item/save 字典分类保存
     * @apiGroup 系统字典
     * @apiVersion 1.0.0
     * @apiDescription 保存字典分类记录
     * @apiParam {String} [id] 新增时为空，修改时必填
     * @apiParam {String} number 分类编号
     * @apiParam {String} name 分类名称
     * @apiParam {String} type_ 类型，请传1
     * @apiParam {String} [remark] 备注
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/save")
    public ResponseBean save(@RequestBody PageData pd) throws Exception {
        String id = pd.getString("id");
        String xml = "";
        String sql = "select 1 from sys_item where number='"
                + pd.getString("number") + "' and deleted=0";
        if (id != null && id.length() > 0) {
            xml = "ItemMapper.editType";
            sql += " and id<>'" + id + "'";
        } else {
            xml = "ItemMapper.addType";
            id = this.getUUID();
        }
        pd.put("id", id);
        if (this.getDao().findExist(sql)) {
            return this.outError("编号重复");
        }
        this.getDao().save(xml, pd);
        return this.outSuccess();
    }

    /**
     * @api {post} /item/delete 字典分类删除
     * @apiGroup 系统字典
     * @apiVersion 1.0.0
     * @apiDescription 删除一条字典分类记录
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * */
    @RequestMapping("/delete")
    public ResponseBean deleteType(@RequestBody PageData pd) throws Exception {
        String [] item = {"id"};
        this.checkEmpty(item,pd);
        this.getDao().delete("ItemMapper.deleteType", pd);
        return this.outSuccess();
    }

    /**
     * @api {post} /item/getType 字典分类详情
     * @apiGroup 系统字典
     * @apiVersion 1.0.0
     * @apiDescription 查询一条字典分类记录
     * @apiParam {String} id 主键
     * @apiSuccess {String} status 状态，0正常，1异常
     * @apiSuccess {String} msg 消息，异常时的错误消息
     * @apiSuccess {Object} data 数组
     * @apiSuccess {String} data.id 主键
     * @apiSuccess {String} data.number 编号
     * @apiSuccess {String} data.name 名称
     * @apiSuccess {String} data.type_ 类型，作用待定
     * @apiSuccess {String} data.remark 备注
     * @apiSuccess {String} data.deleted 删除，0未删除、1已删除
     * */
    @RequestMapping("/getAll")
    public ResponseBean getAll(@RequestBody PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) this.getDao().findForList(
                "ItemMapper.getAll", pd);
        return this.outList(list);
    }

}
