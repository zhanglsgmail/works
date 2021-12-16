package com.asiainfo.ai.framework.web.controller;

import com.asiainfo.ai.framework.web.domain.Result;

/**
 * 通用结果返回
 *
 * @author zhangls
 */
public class BaseController {

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected Result result(int rows) {
        return rows > 0 ? Result.success() : Result.error();
    }

    /**
     * 响应返回结果
     *
     * @param flag 操作是否成功flag
     * @return 操作结果
     */
    protected Result result(boolean flag) {
        return flag ? Result.success() : Result.error();
    }

}
