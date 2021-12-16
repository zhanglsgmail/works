-- create by zhangls
CREATE DATABASE IF NOT EXISTS works DEFAULT CHARSET UTF8MB4 COLLATE UTF8MB4_UNICODE_CI;

USE works;
-- ---------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '自增ID',
    user_id     VARCHAR(30)         NOT NULL UNIQUE COMMENT '研发云用户名',
    pass_word   CHAR(64)            NOT NULL COMMENT '登陆密码',
    pass_salt   CHAR(32)            NOT NULL COMMENT '盐值',
    real_name   VARCHAR(20)         NOT NULL COMMENT '真实姓名',
    card_id     CHAR(18)            NOT NULL UNIQUE COMMENT '身份证',
    email       VARCHAR(60)         NOT NULL UNIQUE COMMENT '电子邮件',
    phone_num   CHAR(18)            NOT NULL UNIQUE COMMENT '手机号码',
    pro_id      INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '所属项目ID',
    user_type   TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户类型 0:未知; 1:项目经理; 2:需求分析; 3:方案设计; 4:开发; 5:测试; 6:运维',
    state       TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态 1:在职状态; 2:锁定状态; 3:离职状态',
    create_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '创建人ID',
    create_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '修改人ID',
    update_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id) USING BTREE,
    KEY IDX_USER_STATE (state),
    KEY IDX_USER_ID (user_id),
    KEY IDX_USER_TYPE (user_type)
) ENGINE = INNODB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT ='用户表'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS sys_perm;

CREATE TABLE sys_perm
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '权限id',
    perm_name   VARCHAR(50)         NOT NULL COMMENT '权限名称',
    perm_key    VARCHAR(50)         NOT NULL UNIQUE COMMENT '权限标识',
    perm_remark VARCHAR(100)        NOT NULL DEFAULT '' COMMENT '备注',
    state       TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态 1:可用状态; 2:锁定状态; 3:弃用状态',
    create_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '创建人ID',
    create_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '修改人ID',
    update_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX IDX_PERM_KEY (perm_key) USING BTREE COMMENT '权限key唯一索引'
) ENGINE = INNODB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT = '权限表'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS sys_role;

CREATE TABLE sys_role
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '角色id',
    role_name   VARCHAR(50)         NOT NULL COMMENT '角色名称',
    role_key    VARCHAR(50)         NOT NULL COMMENT '角色权限字符串',
    role_remark VARCHAR(500)        NOT NULL DEFAULT '' COMMENT '备注',
    state       TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态 1:可用状态; 2:锁定状态; 3:弃用状态',
    create_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '创建人ID',
    create_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '修改人ID',
    update_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX IDX_ROLE_KEY (role_key) USING BTREE COMMENT '角色权限key唯一索引'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT = '角色信息'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS sys_role_perm;

CREATE TABLE sys_role_perm
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '主键id',
    role_id     INT UNSIGNED NOT NULL COMMENT '角色ID',
    perm_id     INT UNSIGNED NOT NULL COMMENT '权限ID',
    create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX IDX_PERM_ROLE_KEY (role_id, perm_id) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT = '角色和菜单关联表'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS sys_user_role;

CREATE TABLE sys_user_role
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '主键id',
    user_id     INT UNSIGNED NOT NULL COMMENT '用户ID',
    role_id     INT UNSIGNED NOT NULL COMMENT '角色ID',
    create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX IDX_ROLE_USER_KEY (user_id, role_id) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT = '用户和角色关联表'
  ROW_FORMAT = DYNAMIC;


-- ---------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS tb_project;

CREATE TABLE tb_project
(
    id          INT UNSIGNED AUTO_INCREMENT COMMENT '项目ID',
    pro_name    VARCHAR(50)         NOT NULL UNIQUE COMMENT '项目名：坐席生产应用-样本管理',
    pare_id     INT UNSIGNED        NOT NULL COMMENT '父项目Id',
    pare_name   VARCHAR(50)         NOT NULL COMMENT '父项目名：一体化客服系统（亚信）',
    belong      VARCHAR(50)         NOT NULL COMMENT '厂商名称：亚信科技（中国）有限公司',
    team        VARCHAR(100)        NOT NULL COMMENT '组织名称：服营系统开发中心-融合坐席支撑团队',
    contract    VARCHAR(100)        NOT NULL COMMENT '合同名称：在线服务公司与亚信科技（中国）有限公司关于2020年第二批次及2021年客服系统一级应用服务支撑项目（包2）合同（2020.12）',
    user_id     INT                 NOT NULL DEFAULT 1000 COMMENT '负责人Id',
    state       TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态 1:可用状态; 2:废弃状态; 3:锁定状态',
    create_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '创建人ID',
    create_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '修改人ID',
    update_date TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    KEY IDX_PRO_STATE (state)
) ENGINE = INNODB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT ='项目表'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS tb_work;

CREATE TABLE tb_work
(
    id           INT UNSIGNED AUTO_INCREMENT COMMENT '主键自增',
    work_date    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '工作项发生时间',
    work_user_id INT UNSIGNED        NOT NULL COMMENT '工作项发生人',
    extra_hour   TINYINT UNSIGNED    NOT NULL DEFAULT 0 COMMENT '加班时长；单位小时',
    work_detail  VARCHAR(100)        NOT NULL UNIQUE COMMENT '工作内容',
    state        TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态 0:待审核状态; 1:审核通过; 2:审核未通过;',
    create_by    INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '创建人ID',
    create_date  TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by    INT UNSIGNED        NOT NULL DEFAULT 1000 COMMENT '修改人ID',
    update_date  TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    KEY IDX_WORK_STATE (state),
    KEY IDX_WORK_CREATE_BY (create_by),
    KEY IDX_WORK_DATE (work_date)
) ENGINE = INNODB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  AUTO_INCREMENT = 1000 COMMENT ='工作项表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- ADD Records
-- ----------------------------
-- 用户表
INSERT INTO works.sys_user(user_id, pass_word, pass_salt, real_name, card_id, email, phone_num, pro_id, user_type)
VALUES ('zhanglingsi', 'f79afece9d07c1e2f7e8bc22fbdbd11a4ed944b53c56fe142f1410b328e46282',
        '0f53817dacf14b0abf186099bfd256ad', '张凌思', '410105198311011656', 'zhanglingsi@gmail.com', '18939242600', '1000',
        '1');

-- 权限表
INSERT INTO works.sys_perm(perm_name, perm_key)
VALUES ('用户所有权限', 'system:user:*'),
       ('添加用户权限', 'system:user:insert'),
       ('删除用户权限', 'system:user:remove'),
       ('更新用户权限', 'system:user:update'),
       ('查看用户权限', 'system:user:list');

-- 角色表
INSERT INTO works.sys_role(role_name, role_key, role_remark)
VALUES ('管理员', 'admin', '管理员'),
       ('普通角色', 'common', '普通角色');

-- 角色权限关系表
INSERT INTO works.sys_role_perm(role_id, perm_id)
VALUES ('1000', '1000');

-- 用户角色关系表
INSERT INTO works.sys_user_role(user_id, role_id)
VALUES ('1000', '1000');


-- 项目表
INSERT INTO works.tb_project(pro_name, pare_id, pare_name, belong, team, contract, user_id)
VALUES ('一体化客服系统（亚信）', 0, '-', '亚信科技（中国）有限公司', '服营系统开发中心-融合坐席支撑团队',
        '在线服务公司与亚信科技（中国）有限公司关于2020年第二批次及2021年客服系统一级应用服务支撑项目（包2）合同（2020.12）', '1000');
INSERT INTO works.tb_project(pro_name, pare_id, pare_name, belong, team, contract, user_id)
VALUES ('坐席生产应用-样本管理', 1000, '一体化客服系统（亚信）', '亚信科技（中国）有限公司', '服营系统开发中心-融合坐席支撑团队',
        '在线服务公司与亚信科技（中国）有限公司关于2020年第二批次及2021年客服系统一级应用服务支撑项目（包2）合同（2020.12）', '1000');