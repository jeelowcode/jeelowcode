/*
 v1.0.0 -> v1.0.1
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `bpm_process_definition_ext` MODIFY COLUMN `deleted` int(0) NULL DEFAULT 0 COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `bpm_process_instance_ext` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '流程实例的状态' AFTER `category`;

ALTER TABLE `bpm_process_instance_ext` MODIFY COLUMN `result` int(0) NOT NULL COMMENT '流程实例的结果' AFTER `status`;

ALTER TABLE `bpm_process_instance_ext` MODIFY COLUMN `deleted` int(0) NULL DEFAULT 0 COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `bpm_process_instance_ext_form` MODIFY COLUMN `deleted` int(0) NULL DEFAULT 0 COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `bpm_task_assign_rule` MODIFY COLUMN `type` int(0) NULL DEFAULT NULL COMMENT '规则类型' AFTER `task_definition_key`;

ALTER TABLE `bpm_task_assign_rule` MODIFY COLUMN `deleted` int(0) NULL DEFAULT 0 COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `bpm_task_ext` MODIFY COLUMN `result` int(0) NOT NULL COMMENT '任务的结果' AFTER `task_id`;

ALTER TABLE `bpm_task_ext` MODIFY COLUMN `deleted` int(0) NULL DEFAULT 0 COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `bpm_user_group` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '状态（0正常 1停用）' AFTER `member_user_ids`;

ALTER TABLE `bpm_user_group` MODIFY COLUMN `deleted` int(0) NULL DEFAULT 0 COMMENT '是否删除' AFTER `update_time`;

ALTER TABLE `example_order_pending_orders` ADD COLUMN `xdsj` datetime(0) NULL DEFAULT NULL COMMENT '下单时间' AFTER `is_deleted`;

ALTER TABLE `example_order_pending_orders` DROP COLUMN `date`;

ALTER TABLE `example_order_record` ADD COLUMN `views` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作详情' AFTER `is_deleted`;

ALTER TABLE `example_order_record` DROP COLUMN `view`;

ALTER TABLE `example_tyym_dtsj` COMMENT = '通用页面-动态数据_copy';

ALTER TABLE `infra_api_access_log` MODIFY COLUMN `user_type` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `infra_api_access_log` MODIFY COLUMN `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数' AFTER `request_url`;

ALTER TABLE `infra_api_error_log` MODIFY COLUMN `id` bigint(0) NOT NULL COMMENT '编号' FIRST;

ALTER TABLE `infra_api_error_log` MODIFY COLUMN `user_id` bigint(0) NULL DEFAULT 0 COMMENT '用户编号' AFTER `trace_id`;

ALTER TABLE `infra_api_error_log` MODIFY COLUMN `user_type` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `infra_api_error_log` MODIFY COLUMN `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数' AFTER `request_url`;

ALTER TABLE `infra_api_error_log` MODIFY COLUMN `process_status` int(0) NOT NULL COMMENT '处理状态' AFTER `exception_line_number`;

ALTER TABLE `infra_api_error_log` MODIFY COLUMN `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号';

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `nullable` tinyint(1) NULL DEFAULT NULL COMMENT '是否允许为空' AFTER `column_comment`;

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `primary_key` tinyint(1) NULL DEFAULT NULL COMMENT '是否主键' AFTER `nullable`;

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `auto_increment` tinyint(1) NOT NULL COMMENT '是否自增' AFTER `primary_key`;

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `create_operation` tinyint(1) NULL DEFAULT NULL COMMENT '是否为 Create 创建操作的字段' AFTER `example`;

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `update_operation` tinyint(1) NULL DEFAULT NULL COMMENT '是否为 Update 更新操作的字段' AFTER `create_operation`;

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `list_operation` tinyint(1) NULL DEFAULT NULL COMMENT '是否为 List 查询操作的字段' AFTER `update_operation`;

ALTER TABLE `infra_codegen_column` MODIFY COLUMN `list_operation_result` tinyint(1) NULL DEFAULT NULL COMMENT '是否为 List 查询操作的返回字段' AFTER `list_operation_condition`;

ALTER TABLE `infra_codegen_table` MODIFY COLUMN `scene` int(0) NOT NULL DEFAULT 1 COMMENT '生成场景' AFTER `data_source_config_id`;

ALTER TABLE `infra_codegen_table` MODIFY COLUMN `template_type` int(0) NOT NULL DEFAULT 1 COMMENT '模板类型' AFTER `author`;

ALTER TABLE `infra_codegen_table` MODIFY COLUMN `front_type` int(0) NOT NULL COMMENT '前端类型' AFTER `template_type`;

ALTER TABLE `infra_codegen_table` MODIFY COLUMN `sub_join_many` tinyint(1) NULL DEFAULT NULL COMMENT '主表与子表是否一对多' AFTER `sub_join_column_id`;

ALTER TABLE `infra_config` MODIFY COLUMN `id` bigint(0) NOT NULL COMMENT '参数主键' FIRST;

ALTER TABLE `infra_config` MODIFY COLUMN `type` int(0) NOT NULL COMMENT '参数类型' AFTER `category`;

ALTER TABLE `infra_config` MODIFY COLUMN `visible` tinyint(1) NULL DEFAULT NULL COMMENT '是否可见' AFTER `value`;

ALTER TABLE `infra_config` MODIFY COLUMN `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键';

ALTER TABLE `infra_file_config` MODIFY COLUMN `storage` int(0) NOT NULL COMMENT '存储器' AFTER `name`;

ALTER TABLE `infra_file_config` MODIFY COLUMN `master` tinyint(1) NULL DEFAULT NULL COMMENT '是否为主配置' AFTER `remark`;

ALTER TABLE `infra_job` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '任务状态' AFTER `name`;

ALTER TABLE `infra_job_log` MODIFY COLUMN `execute_index` int(0) NOT NULL DEFAULT 1 COMMENT '第几次执行' AFTER `handler_param`;

ALTER TABLE `infra_job_log` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '任务状态' AFTER `duration`;

ALTER TABLE `lowcode_config` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_button` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_enhance_java` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_enhance_js` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_enhance_sql` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_field` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_field_dict` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_field_export` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_field_foreignkey` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_field_query` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_field_web` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_index` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_role_button` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_role_data_rule` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_role_data_tenant` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_role_field` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_dbform_summary` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_desform` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_group_dbform` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_group_desform` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_group_report` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_log_api` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID' AFTER `id`;

ALTER TABLE `lowcode_log_api_error` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户ID' AFTER `id`;

ALTER TABLE `lowcode_log_excel_file` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_log_excel_file_data` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_log_history_dbform` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_log_history_desform` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_log_module_usage_records` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_report` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `lowcode_report_field` MODIFY COLUMN `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户编号' AFTER `id`;

ALTER TABLE `qrtz_blob_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_blob_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_NAME`;

ALTER TABLE `qrtz_calendars` MODIFY COLUMN `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_NAME`;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `CRON_EXPRESSION` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_GROUP`;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `ENTRY_ID`;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_NAME`;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_GROUP`;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL AFTER `STATE`;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL AFTER `JOB_NAME`;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `JOB_NAME`;

ALTER TABLE `qrtz_paused_trigger_grps` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_scheduler_state` MODIFY COLUMN `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_NAME`;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_NAME`;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `SCHED_NAME`;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_NAME`;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `TRIGGER_GROUP`;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `JOB_NAME`;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL AFTER `END_TIME`;

ALTER TABLE `system_dept` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '部门状态（0正常 1停用）' AFTER `email`;

ALTER TABLE `system_dict_data` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）' AFTER `dict_type`;

ALTER TABLE `system_dict_type` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）' AFTER `type`;

ALTER TABLE `system_error_code` MODIFY COLUMN `type` int(0) NOT NULL DEFAULT 0 COMMENT '错误码类型' AFTER `id`;

ALTER TABLE `system_login_log` MODIFY COLUMN `user_type` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_login_log` MODIFY COLUMN `result` int(0) NOT NULL COMMENT '登陆结果' AFTER `username`;

ALTER TABLE `system_mail_account` MODIFY COLUMN `ssl_enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启 SSL' AFTER `port`;

ALTER TABLE `system_mail_log` MODIFY COLUMN `user_type` int(0) NULL DEFAULT NULL COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_mail_log` MODIFY COLUMN `send_status` int(0) NOT NULL DEFAULT 0 COMMENT '发送状态' AFTER `template_params`;

ALTER TABLE `system_mail_template` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '开启状态' AFTER `params`;

ALTER TABLE `system_menu` MODIFY COLUMN `type` int(0) NOT NULL COMMENT '菜单类型' AFTER `permission`;

ALTER TABLE `system_menu` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '菜单状态' AFTER `component_name`;

ALTER TABLE `system_menu` MODIFY COLUMN `visible` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可见' AFTER `status`;

ALTER TABLE `system_menu` MODIFY COLUMN `keep_alive` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否缓存' AFTER `visible`;

ALTER TABLE `system_menu` MODIFY COLUMN `always_show` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否总是显示' AFTER `keep_alive`;

ALTER TABLE `system_notice` MODIFY COLUMN `type` int(0) NOT NULL COMMENT '公告类型（1通知 2公告）' AFTER `content`;

ALTER TABLE `system_notice` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）' AFTER `type`;

ALTER TABLE `system_notify_message` MODIFY COLUMN `user_type` int(0) NOT NULL COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_notify_message` MODIFY COLUMN `read_status` tinyint(1) NOT NULL COMMENT '是否已读' AFTER `template_params`;

ALTER TABLE `system_oauth2_approve` MODIFY COLUMN `approved` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否接受' AFTER `scope`;

ALTER TABLE `system_oauth2_client` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '状态' AFTER `description`;

ALTER TABLE `system_operate_log` MODIFY COLUMN `user_type` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_operate_log` MODIFY COLUMN `type` int(0) NOT NULL DEFAULT 0 COMMENT '操作分类' AFTER `name`;

ALTER TABLE `system_operate_log` MODIFY COLUMN `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作内容' AFTER `type`;

ALTER TABLE `system_operate_log` MODIFY COLUMN `exts` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '拓展字段' AFTER `content`;

ALTER TABLE `system_operate_log` MODIFY COLUMN `result_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结果提示' AFTER `result_code`;

ALTER TABLE `system_operate_log` MODIFY COLUMN `result_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结果数据' AFTER `result_msg`;

ALTER TABLE `system_operate_log_v2` MODIFY COLUMN `user_type` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_post` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '状态（0正常 1停用）' AFTER `sort`;

ALTER TABLE `system_role` MODIFY COLUMN `data_scope` int(0) NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）' AFTER `sort`;

ALTER TABLE `system_role` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '角色状态（0正常 1停用）' AFTER `data_scope_dept_ids`;

ALTER TABLE `system_role` MODIFY COLUMN `type` int(0) NOT NULL COMMENT '角色类型' AFTER `status`;

ALTER TABLE `system_sensitive_word` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '状态' AFTER `tags`;

ALTER TABLE `system_sms_channel` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '开启状态' AFTER `code`;

ALTER TABLE `system_sms_code` MODIFY COLUMN `scene` int(0) NOT NULL COMMENT '发送场景' AFTER `create_ip`;

ALTER TABLE `system_sms_code` MODIFY COLUMN `today_index` int(0) NOT NULL COMMENT '今日发送的第几条' AFTER `scene`;

ALTER TABLE `system_sms_code` MODIFY COLUMN `used` tinyint(1) NOT NULL COMMENT '是否使用' AFTER `today_index`;

ALTER TABLE `system_sms_log` MODIFY COLUMN `template_type` int(0) NOT NULL COMMENT '短信类型' AFTER `template_code`;

ALTER TABLE `system_sms_log` MODIFY COLUMN `user_type` int(0) NULL DEFAULT NULL COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_sms_log` MODIFY COLUMN `send_status` int(0) NOT NULL DEFAULT 0 COMMENT '发送状态' AFTER `user_type`;

ALTER TABLE `system_sms_log` MODIFY COLUMN `receive_status` int(0) NOT NULL DEFAULT 0 COMMENT '接收状态' AFTER `api_serial_no`;

ALTER TABLE `system_sms_template` MODIFY COLUMN `type` int(0) NOT NULL COMMENT '短信签名' AFTER `id`;

ALTER TABLE `system_sms_template` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '开启状态' AFTER `type`;

ALTER TABLE `system_social_client` MODIFY COLUMN `social_type` int(0) NOT NULL COMMENT '社交平台的类型' AFTER `name`;

ALTER TABLE `system_social_client` MODIFY COLUMN `user_type` int(0) NOT NULL COMMENT '用户类型' AFTER `social_type`;

ALTER TABLE `system_social_client` MODIFY COLUMN `status` int(0) NOT NULL COMMENT '状态' AFTER `agent_id`;

ALTER TABLE `system_social_user` MODIFY COLUMN `type` int(0) NOT NULL COMMENT '社交平台的类型' AFTER `id`;

ALTER TABLE `system_social_user_bind` MODIFY COLUMN `user_type` int(0) NOT NULL COMMENT '用户类型' AFTER `user_id`;

ALTER TABLE `system_social_user_bind` MODIFY COLUMN `social_type` int(0) NOT NULL COMMENT '社交平台的类型' AFTER `user_type`;

ALTER TABLE `system_tenant` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）' AFTER `contact_mobile`;

ALTER TABLE `system_tenant_package` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）' AFTER `name`;

ALTER TABLE `system_users` MODIFY COLUMN `sex` int(0) NULL DEFAULT 0 COMMENT '用户性别' AFTER `mobile`;

ALTER TABLE `system_users` MODIFY COLUMN `status` int(0) NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）' AFTER `avatar`;

ALTER TABLE `lowcode_dbform_field_dict` ADD COLUMN `dict_text_formatter` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典格式化' AFTER `dict_table_column`;


UPDATE `example_contact_manager` SET `xm` = '136', `tx` = 'http://oss.yckxt.com/chatgpt/upload/1/2024-11-02/1/f0ee8a3c7c9638a54940382568c9dpng_2.png', `zw` = '销售总监', `gs` = '广州某科技有限公司', `sj` = '15288888888', `yx` = 'email@xxxx.xxx', `dh` = '0959-99999999', `dz` = '广东省广州市XXXX', `color` = '#52C1F5', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-11-02 16:22:04', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-20 16:20:12', `is_deleted` = 0, `lx` = '2' WHERE `id` = 1852627220146909201;

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847183228894580738, NULL, ' AxureUX网站改版', '专业的交互原型素材原创分享平台', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 15:49:35', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847183611062784001, NULL, ' AxureUX网站改版', '专业的交互原型素材原创分享平台', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 15:51:06', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847184159707107329, NULL, '1', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 15:53:17', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847184821677330433, NULL, '2', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 15:55:55', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847186402095271937, NULL, '123', '12313', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:02:11', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847187452822949889, NULL, 'AxureUX网站改版', '213', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:06:22', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847188935526191106, NULL, 'AxureUX网站改版', 'cs', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:12:15', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847189252665905153, NULL, '123', '1233213', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:13:31', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847189295854653441, NULL, '123', '1233213', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:13:41', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847189960865746945, NULL, '123', '123123', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:16:20', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847190543760756738, NULL, '123', '123132', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:18:39', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847190656352653314, NULL, '123', '123132', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:19:06', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847190785788874753, NULL, '132', '123', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:19:37', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847191292415754241, NULL, '项目名称', '213213', '1', NULL, NULL, 'http://oss.yckxt.com/chatgpt/upload/1/2024-10-18/1/f0ee8a3c7c9638a54940382568c9dpng_7.png', NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:21:37', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847191476403093505, NULL, '123', '1233', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:22:21', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847192143003828225, NULL, '55', NULL, '1', NULL, NULL, 'http://oss.yckxt.com/chatgpt/upload/1/2024-10-18/1/f0ee8a3c7c9638a54940382568c9dpng_8.png', NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:25:00', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `example_glmk_xmgl`(`id`, `jbxx`, `title`, `content`, `zt`, `xmjd`, `zhgx`, `xmcy`, `xmzs`, `jxz`, `ywc`, `yyq`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `fj`, `kssj`, `jssj`, `fzr`, `cyry`, `rwbj`, `kxfw`) VALUES (1847192629786361857, NULL, '886', NULL, '1', '50', '2024-10-18 16:26:59', 'http://oss.yckxt.com/chatgpt/upload/1/2024-10-18/1/f0ee8a3c7c9638a54940382568c9dpng_9.png', NULL, NULL, NULL, NULL, 1, 1, '2024-10-18 16:26:56', 101, NULL, NULL, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

UPDATE `example_mkgl_tdzsk` SET `img` = 'http://oss.yckxt.com/chatgpt/upload/1/2024-11-07/1/snipaste20241107_164216_2.jpg', `title` = '阿里云发布应用负载均衡ALB 加速企业应用交付', `text` = '在阿里云2020年云栖大会上，阿里云智能网络产品研究员祝顺民重磅发布了多款网络新品', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-11-07 16:46:58', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-09 13:37:51', `is_deleted` = 0, `cp_title` = '产品知识', `cp_text` = '产品相关问题', `content` = '<p style=\"text-align: left;\">近日，在阿里云2020年云栖大会上，阿里云智能网络产品研究员祝顺民重磅发布了多款网络新品，其中之一就是应用负载均衡ALB。ALB产品定位应用层高级负载，具备超强性能、安全可靠、面向云原生、即开即用等优势价值，提供弹性自动伸缩、QUIC协议支持、基于内容的高级路由、自带DDoS安全防护、云原生应用、弹性灵活计费等产品能力，满足越来越多元化的应用层负载需求。</p><p style=\"text-align: left;\">说到负载均衡，很多朋友或许都会想到经典负载均衡，阿里云负载均衡SLB发布近十年，为各行各业的用户提供强大稳定的负载分担能力，解决大并发流量负载分担，消除单点故障，提高业务可用性。但随着企业和互联网业务高速发展，业务形态和需求不断变化，诸多业务场景已经无法单纯的用传统负载均衡来满足全部需求。在大互联网业务、电商大促、音视频、移动互联网应用、游戏业务、金融服务、云原生开发应用等场景中，存在大量高性能、弹性、多协议七层转发、安全、云原生等需求，急需新产品设计来满足。</p><p style=\"text-align: left;\">场景例举一：某电商大促业务</p><p style=\"text-align: left;\">电商经常需要在某个节日、某次活动中进行集中促销，直播带货必不可少，但活动前又无法预估业务高峰会达到多大的流量水平；此外电商场景中，常常需要根据地域、时间段、支付流程等进行负载分担。传统的负载均衡虽然具备一定的统一灵活调度能力，但在弹性、自动扩展、高性能方面还有所欠缺；同时实时弹性、高并发、超大新建规格、多协议（HTTP/HTTPS/HOST/URL/Cookie/Http Method））7层负载转发能力也无法通过传统负载均衡来实现，需要全新的应用负载均衡产品来满足业务需求。</p><p style=\"text-align: left;\">场景例举二：某视频直播业务</p><p style=\"text-align: left;\"><br></p><p style=\"text-align: left;\">对于各类视频业务，如影视剧长视频，生活短视频、直播带货、在线教学等等，视频业务对后端的资源需求越来越高。动则上百万新建连接需求，自动弹性扩展，基于用户画像的流量转发机制等等，让当前以四层为主的负载均衡系统无法完全满足业务发展需要。而应用负载均衡ALB基于QUIC的传输转发能力，更快速高效的建立连接，缩短时延，分担业务请求流量，保障大流量业务需求。</p><p style=\"text-align: left;\"><br></p><p style=\"text-align: left;\">场景例举三；某云原生应用业务</p><p style=\"text-align: left;\"><br></p><p style=\"text-align: left;\">近些年诸多业务已经上云，由于云厂商提供了统一的IaaS 能力和云服务，大幅提升了IaaS 层的复用程度，用户也希望IaaS以上层的系统也被统一，使资源、产品可被不断复用，从而能够进一步降低企业运营成本，这正好就是云原生架构专注解决的问题。全新应用型负载均衡ALB基于云原生架构设计，能够很好的满足用户需求，应对灰度发布、流量仿真、微服务等场景。</p>', `llcs` = 39 WHERE `id` = 1854445428243935233;

UPDATE `example_order_pending_orders` SET `bh` = '202290434567655', `name` = '米家声波电动牙刷', `num` = 2, `money` = 300, `status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 10:07:16', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:49:54', `is_deleted` = 0, `xdsj` = '2024-02-23 12:02:04' WHERE `id` = 1847459470194647042;

UPDATE `example_order_pending_orders` SET `bh` = '202290434567656', `name` = '手工毛线针织编织玫瑰永生花', `num` = 2, `money` = 50, `status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 10:18:01', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:49:44', `is_deleted` = 0, `xdsj` = '2024-02-16 02:03:08' WHERE `id` = 1847462174346321922;

UPDATE `example_order_pending_orders` SET `bh` = '45861611145651', `name` = 'KDL826泰坦电视人3.0', `num` = 1, `money` = 10, `status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 10:18:36', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:49:32', `is_deleted` = 0, `xdsj` = '2024-01-20 01:01:01' WHERE `id` = 1847462321453146113;

UPDATE `example_order_pending_orders` SET `bh` = '26914891561581', `name` = 'peak匹克羽毛球拍', `num` = 2, `money` = 100, `status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 10:19:59', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:49:22', `is_deleted` = 0, `xdsj` = '2024-01-15 03:04:00' WHERE `id` = 1847462670427627521;

UPDATE `example_order_pending_orders` SET `bh` = '8941651651541', `name` = '新款卡皮巴拉笔袋', `num` = 3, `money` = 40, `status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 10:20:37', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:43:59', `is_deleted` = 0, `xdsj` = '2025-01-14 00:00:00' WHERE `id` = 1847462831912525825;

UPDATE `example_order_record` SET `time` = '2024-09-09 02:00:00', `manipulator` = '1', `order_status` = '1', `payment_status` = '2', `delivery_status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:32:51', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:39:17', `is_deleted` = 0, `views` = '已完成' WHERE `id` = 1836322482663534593;

UPDATE `example_order_record` SET `time` = '2024-08-19 15:43:23', `manipulator` = '1', `order_status` = '3', `payment_status` = '1', `delivery_status` = '1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:34:31', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:39:04', `is_deleted` = 0, `views` = '已完成' WHERE `id` = 1836322902207180802;

UPDATE `example_primordial_table` SET `bh` = '20241001', `company_name` = '深圳木卫二科技有限公司', `customer_status` = '2', `industry_type` = '3', `customer_source` = '9', `add_people` = '1', `customer_star` = '1', `update_date` = NULL, `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-27 09:32:11', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-09-27 09:32:40', `is_deleted` = 0 WHERE `id` = 1839478107039907841;

UPDATE `example_report_data` SET `table_type` = '订单及销量趋势', `value` = '[\r\n  {\r\n    \"type\": \"1\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"星期一\",\r\n        \"xse\": \"3244\",\r\n        \"ddl\": \"1632\"\r\n      },\r\n      {\r\n        \"sj\": \"星期二\",\r\n        \"xse\": \"3424\",\r\n        \"ddl\": \"1695\"\r\n      },\r\n      {\r\n        \"sj\": \"星期三\",\r\n        \"xse\": \"3232\",\r\n        \"ddl\": \"1697\"\r\n      },\r\n      {\r\n        \"sj\": \"星期四\",\r\n        \"xse\": \"5648\",\r\n        \"ddl\": \"3599\"\r\n      },\r\n      {\r\n        \"sj\": \"星期五\",\r\n        \"xse\": \"2000\",\r\n        \"ddl\": \"2055\"\r\n      },\r\n      {\r\n        \"sj\": \"星期六\",\r\n        \"xse\": \"6441\",\r\n        \"ddl\": \"2687\"\r\n      },\r\n      {\r\n        \"sj\": \"星期日\",\r\n        \"xse\": \"2959\",\r\n        \"ddl\": \"3688\"\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"2\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"上旬\",\r\n        \"xse\": \"3244\",\r\n        \"ddl\": \"1632\"\r\n      },\r\n      {\r\n        \"sj\": \"中旬\",\r\n        \"xse\": \"3424\",\r\n        \"ddl\": \"1695\"\r\n      },\r\n      {\r\n        \"sj\": \"下旬\",\r\n        \"xse\": \"3232\",\r\n        \"ddl\": \"1697\"\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"3\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"第一季度\",\r\n        \"xse\": \"3244\",\r\n        \"ddl\": \"1632\"\r\n      },\r\n      {\r\n        \"sj\": \"第二季度\",\r\n        \"xse\": \"3424\",\r\n        \"ddl\": \"1695\"\r\n      },\r\n      {\r\n        \"sj\": \"第三季度\",\r\n        \"xse\": \"3232\",\r\n        \"ddl\": \"1697\"\r\n      },\r\n      {\r\n        \"sj\": \"第四季度\",\r\n        \"xse\": \"3694\",\r\n        \"ddl\": \"1698\"\r\n      }\r\n    ]\r\n  }\r\n]', `type_index` = '系统首页数据看板1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 15:53:11', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:41:07', `is_deleted` = 0 WHERE `id` = 1847184136874409986;

UPDATE `example_report_data` SET `table_type` = '新增用户及会员趋势', `value` = '[\r\n  {\r\n    \"type\": \"1\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"周一\",\r\n        \"xzyh\": 2500,\r\n        \"xzhy\": 1234\r\n      },\r\n      {\r\n        \"sj\": \"周二\",\r\n        \"xzyh\": 1800,\r\n        \"xzhy\": 3456\r\n      },\r\n      {\r\n        \"sj\": \"周三\",\r\n        \"xzyh\": 4200,\r\n        \"xzhy\": 2345\r\n      },\r\n      {\r\n        \"sj\": \"周四\",\r\n        \"xzyh\": 3100,\r\n        \"xzhy\": 4567\r\n      },\r\n      {\r\n        \"sj\": \"周五\",\r\n        \"xzyh\": 2900,\r\n        \"xzhy\": 123\r\n      },\r\n      {\r\n        \"sj\": \"周六\",\r\n        \"xzyh\": 4700,\r\n        \"xzhy\": 4321\r\n      },\r\n      {\r\n        \"sj\": \"周日\",\r\n        \"xzyh\": 1300,\r\n        \"xzhy\": 3210\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"2\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"上旬\",\r\n        \"xzyh\": 2500,\r\n        \"xzhy\": 1234\r\n      },\r\n      {\r\n        \"sj\": \"中旬\",\r\n        \"xzyh\": 1800,\r\n        \"xzhy\": 3456\r\n      },\r\n      {\r\n        \"sj\": \"下旬\",\r\n        \"xzyh\": 4200,\r\n        \"xzhy\": 2345\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"3\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"第一季度\",\r\n        \"xzyh\": 2500,\r\n        \"xzhy\": 1234\r\n      },\r\n      {\r\n        \"sj\": \"第二季度\",\r\n        \"xzyh\": 1800,\r\n        \"xzhy\": 3456\r\n      },\r\n      {\r\n        \"sj\": \"第三季度\",\r\n        \"xzyh\": 4200,\r\n        \"xzhy\": 2345\r\n      },\r\n      {\r\n        \"sj\": \"第四季度\",\r\n        \"xzyh\": 3200,\r\n        \"xzhy\": 2648\r\n      }\r\n    ]\r\n  }\r\n]', `type_index` = '系统首页数据看板1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 17:31:28', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:40:22', `is_deleted` = 0 WHERE `id` = 1847208870949322754;

UPDATE `example_report_data` SET `table_type` = '用户访问趋势', `value` = '[\r\n  {\r\n    \"type\": \"1\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"周一\",\r\n        \"fwyh\": 2500\r\n      },\r\n      {\r\n        \"sj\": \"周二\",\r\n        \"fwyh\": 1500\r\n      },\r\n      {\r\n        \"sj\": \"周三\",\r\n        \"fwyh\": 1000\r\n      },\r\n      {\r\n        \"sj\": \"周四\",\r\n        \"fwyh\": 2000\r\n      },\r\n      {\r\n        \"sj\": \"周五\",\r\n        \"fwyh\": 2000\r\n      },\r\n      {\r\n        \"sj\": \"周六\",\r\n        \"fwyh\": 2700\r\n      },\r\n      {\r\n        \"sj\": \"周日\",\r\n        \"fwyh\": 2300\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"2\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"上旬\",\r\n        \"fwyh\": 2500\r\n      },\r\n      {\r\n        \"sj\": \"中旬\",\r\n        \"fwyh\": 1500\r\n      },\r\n      {\r\n        \"sj\": \"下旬\",\r\n        \"fwyh\": 1000\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"3\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"第一季度\",\r\n        \"fwyh\": 2200\r\n      },\r\n      {\r\n        \"sj\": \"第二季度\",\r\n        \"fwyh\": 2100\r\n      },\r\n      {\r\n        \"sj\": \"第三季度\",\r\n        \"fwyh\": 2300\r\n      },\r\n      {\r\n        \"sj\": \"第四季度\",\r\n        \"fwyh\": 2000\r\n      }\r\n    ]\r\n  }\r\n]', `type_index` = '系统首页数据看板1', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 17:51:41', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:39:54', `is_deleted` = 0 WHERE `id` = 1847213957767729153;

UPDATE `example_report_data` SET `table_type` = '业绩目标', `value` = '[\r\n  {\r\n    \"sj\": \"1月\",\r\n    \"mbje\": \"1700000\",\r\n    \"cjje\": \"2520000\"\r\n  },\r\n  {\r\n    \"sj\": \"2月\",\r\n    \"mbje\": \"1660000\",\r\n    \"cjje\": \"2650000\"\r\n  },\r\n  {\r\n    \"sj\": \"3月\",\r\n    \"mbje\": \"1420000\",\r\n    \"cjje\": \"2330000\"\r\n  },\r\n  {\r\n    \"sj\": \"4月\",\r\n    \"mbje\": \"1520000\",\r\n    \"cjje\": \"2150000\"\r\n  },\r\n  {\r\n    \"sj\": \"5月\",\r\n    \"mbje\": \"1190000\",\r\n    \"cjje\": \"1840000\"\r\n  },\r\n  {\r\n    \"sj\": \"6月\",\r\n    \"mbje\": \"1030000\",\r\n    \"cjje\": \"1830000\"\r\n  },\r\n  {\r\n    \"sj\": \"7月\",\r\n    \"mbje\": \"1050000\",\r\n    \"cjje\": \"1450000\"\r\n  },\r\n  {\r\n    \"sj\": \"8月\",\r\n    \"mbje\": \"870000\",\r\n    \"cjje\": \"950000\"\r\n  },\r\n  {\r\n    \"sj\": \"9月\",\r\n    \"mbje\": \"690000\",\r\n    \"cjje\": \"690000\"\r\n  },\r\n  {\r\n    \"sj\": \"10月\",\r\n    \"mbje\": \"620000\",\r\n    \"cjje\": \"2650000\"\r\n  },\r\n  {\r\n    \"sj\": \"11月\",\r\n    \"mbje\": \"1320000\",\r\n    \"cjje\": \"1230000\"\r\n  },\r\n  {\r\n    \"sj\": \"12月\",\r\n    \"mbje\": \"1220000\",\r\n    \"cjje\": \"1000000\"\r\n  }\r\n]', `type_index` = '系统首页客户管理', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 13:57:25', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:38:57', `is_deleted` = 0 WHERE `id` = 1847517389778984962;

UPDATE `example_report_data` SET `table_type` = '销售预测', `value` = '[\r\n  {\r\n    \"sj\": \"1月\",\r\n    \"yjxsje\": \"700000\",\r\n    \"glje\": \"390000\"\r\n  },\r\n  {\r\n    \"sj\": \"2月\",\r\n    \"yjxsje\": \"690000\",\r\n    \"glje\": \"420000\"\r\n  },\r\n  {\r\n    \"sj\": \"3月\",\r\n    \"yjxsje\": \"950000\",\r\n    \"glje\": \"570000\"\r\n  },\r\n  {\r\n    \"sj\": \"4月\",\r\n    \"yjxsje\": \"1450000\",\r\n    \"glje\": \"850000\"\r\n  },\r\n  {\r\n    \"sj\": \"5月\",\r\n    \"yjxsje\": \"1840000\",\r\n    \"glje\": \"1190000\"\r\n  },\r\n  {\r\n    \"sj\": \"6月\",\r\n    \"yjxsje\": \"2150000\",\r\n    \"glje\": \"1520000\"\r\n  },\r\n  {\r\n    \"sj\": \"7月\",\r\n    \"yjxsje\": \"2520000\",\r\n    \"glje\": \"1700000\"\r\n  },\r\n  {\r\n    \"sj\": \"8月\",\r\n    \"yjxsje\": \"2650000\",\r\n    \"glje\": \"1660000\"\r\n  },\r\n  {\r\n    \"sj\": \"9月\",\r\n    \"yjxsje\": \"2330000\",\r\n    \"glje\": \"1420000\"\r\n  },\r\n  {\r\n    \"sj\": \"10月\",\r\n    \"yjxsje\": \"1830000\",\r\n    \"glje\": \"1030000\"\r\n  },\r\n  {\r\n    \"sj\": \"11月\",\r\n    \"yjxsje\": \"1390000\",\r\n    \"glje\": \"660000\"\r\n  },\r\n  {\r\n    \"sj\": \"12月\",\r\n    \"yjxsje\": \"960000\",\r\n    \"glje\": \"480000\"\r\n  }\r\n]', `type_index` = '系统首页客户管理', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 13:59:03', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:38:34', `is_deleted` = 0 WHERE `id` = 1847517802133594114;

UPDATE `example_report_data` SET `table_type` = '系统通知', `value` = '[\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  },\r\n  {\r\n    \"label\": \"JeeLowCode低代码平台\",\r\n    \"sj\": \"2024-08-31 12:00:00\"\r\n  }\r\n]', `type_index` = '系统首页客户管理', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:14:18', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:20:39', `is_deleted` = 0 WHERE `id` = 1847521639368105986;

UPDATE `example_report_data` SET `table_type` = '日交易额', `value` = '[\r\n  {\r\n    \"sj\": \"12:00\",\r\n    \"data\": 32000\r\n  },\r\n  {\r\n    \"sj\": \"14:00\",\r\n    \"data\": 35000\r\n  },\r\n  {\r\n    \"sj\": \"16:00\",\r\n    \"data\": 37000\r\n  },\r\n  {\r\n    \"sj\": \"18:00\",\r\n    \"data\": 32000\r\n  },\r\n  {\r\n    \"sj\": \"20:00\",\r\n    \"data\": 34000\r\n  },\r\n  {\r\n    \"sj\": \"22:00\",\r\n    \"data\": 33000\r\n  },\r\n  {\r\n    \"sj\": \"24:00\",\r\n    \"data\": 37000\r\n  }\r\n]', `type_index` = '系统首页商户统计', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:50:18', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:38:06', `is_deleted` = 0 WHERE `id` = 1847530697479299074;

UPDATE `example_report_data` SET `table_type` = '周订单量', `value` = '[\r\n  {\r\n    \"sj\": \"周一\",\r\n    \"data\": 800\r\n  },\r\n  {\r\n    \"sj\": \"周二\",\r\n    \"data\": 3000\r\n  },\r\n  {\r\n    \"sj\": \"周三\",\r\n    \"data\": 2000\r\n  },\r\n  {\r\n    \"sj\": \"周四\",\r\n    \"data\": 3800\r\n  },\r\n  {\r\n    \"sj\": \"周五\",\r\n    \"data\": 3600\r\n  },\r\n  {\r\n    \"sj\": \"周六\",\r\n    \"data\": 4800\r\n  },\r\n  {\r\n    \"sj\": \"周日\",\r\n    \"data\": 4500\r\n  }\r\n]', `type_index` = '系统首页商户统计', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:53:58', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:37:35', `is_deleted` = 0 WHERE `id` = 1847531621232812034;

UPDATE `example_report_data` SET `table_type` = '开拓商家', `value` = '[\r\n  {\r\n    \"sj\": \"周一\",\r\n    \"mbkts\": 1008,\r\n    \"sjkts\": 1800\r\n  },\r\n  {\r\n    \"sj\": \"周二\",\r\n    \"mbkts\": 2000,\r\n    \"sjkts\": 2500\r\n  },\r\n  {\r\n    \"sj\": \"周三\",\r\n    \"mbkts\": 2600,\r\n    \"sjkts\": 2100\r\n  },\r\n  {\r\n    \"sj\": \"周四\",\r\n    \"mbkts\": 1800,\r\n    \"sjkts\": 1300\r\n  },\r\n  {\r\n    \"sj\": \"周五\",\r\n    \"mbkts\": 3500,\r\n    \"sjkts\": 6000\r\n  },\r\n  {\r\n    \"sj\": \"周六\",\r\n    \"mbkts\": 5300,\r\n    \"sjkts\": 2800\r\n  },\r\n  {\r\n    \"sj\": \"周日\",\r\n    \"mbkts\": 3800,\r\n    \"sjkts\": 6000\r\n  }\r\n]', `type_index` = '系统首页商户统计', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:00:59', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:37:19', `is_deleted` = 0 WHERE `id` = 1847533387051573250;

UPDATE `example_report_data` SET `table_type` = '成交金额趋势', `value` = '[\r\n  {\r\n    \"type\": \"1\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"12-01\",\r\n        \"xscj\": \"200000\",\r\n        \"xxcj\": \"100000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-02\",\r\n        \"xscj\": \"330000\",\r\n        \"xxcj\": \"200000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-03\",\r\n        \"xscj\": \"340000\",\r\n        \"xxcj\": \"180000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-04\",\r\n        \"xscj\": \"350000\",\r\n        \"xxcj\": \"320000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-05\",\r\n        \"xscj\": \"360000\",\r\n        \"xxcj\": \"300000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-06\",\r\n        \"xscj\": \"430000\",\r\n        \"xxcj\": \"400000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-07\",\r\n        \"xscj\": \"450000\",\r\n        \"xxcj\": \"380000\"\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"2\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"12-05\",\r\n        \"xscj\": \"350000\",\r\n        \"xxcj\": \"320000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-10\",\r\n        \"xscj\": \"200000\",\r\n        \"xxcj\": \"100000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-15\",\r\n        \"xscj\": \"360000\",\r\n        \"xxcj\": \"300000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-20\",\r\n        \"xscj\": \"330000\",\r\n        \"xxcj\": \"200000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-25\",\r\n        \"xscj\": \"430000\",\r\n        \"xxcj\": \"400000\"\r\n      },\r\n      {\r\n        \"sj\": \"12-30\",\r\n        \"xscj\": \"340000\",\r\n        \"xxcj\": \"180000\"\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"3\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"01\",\r\n        \"xscj\": \"200000\",\r\n        \"xxcj\": \"310000\"\r\n      },\r\n      {\r\n        \"sj\": \"02\",\r\n        \"xscj\": \"200000\",\r\n        \"xxcj\": \"280000\"\r\n      },\r\n      {\r\n        \"sj\": \"03\",\r\n        \"xscj\": \"330000\",\r\n        \"xxcj\": \"400000\"\r\n      },\r\n      {\r\n        \"sj\": \"04\",\r\n        \"xscj\": \"340000\",\r\n        \"xxcj\": \"180000\"\r\n      },\r\n      {\r\n        \"sj\": \"05\",\r\n        \"xscj\": \"430000\",\r\n        \"xxcj\": \"100000\"\r\n      },\r\n      {\r\n        \"sj\": \"06\",\r\n        \"xscj\": \"420000\",\r\n        \"xxcj\": \"350000\"\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"4\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"01\",\r\n        \"xscj\": \"200000\",\r\n        \"xxcj\": \"310000\"\r\n      },\r\n      {\r\n        \"sj\": \"02\",\r\n        \"xscj\": \"200000\",\r\n        \"xxcj\": \"280000\"\r\n      },\r\n      {\r\n        \"sj\": \"03\",\r\n        \"xscj\": \"330000\",\r\n        \"xxcj\": \"400000\"\r\n      },\r\n      {\r\n        \"sj\": \"04\",\r\n        \"xscj\": \"340000\",\r\n        \"xxcj\": \"180000\"\r\n      },\r\n      {\r\n        \"sj\": \"05\",\r\n        \"xscj\": \"430000\",\r\n        \"xxcj\": \"100000\"\r\n      },\r\n      {\r\n        \"sj\": \"06\",\r\n        \"xscj\": \"220000\",\r\n        \"xxcj\": \"350000\"\r\n      },\r\n      {\r\n        \"sj\": \"07\",\r\n        \"xscj\": \"380000\",\r\n        \"xxcj\": \"180000\"\r\n      },\r\n      {\r\n        \"sj\": \"08\",\r\n        \"xscj\": \"350000\",\r\n        \"xxcj\": \"350000\"\r\n      },\r\n      {\r\n        \"sj\": \"09\",\r\n        \"xscj\": \"280000\",\r\n        \"xxcj\": \"100000\"\r\n      },\r\n      {\r\n        \"sj\": \"10\",\r\n        \"xscj\": \"310000\",\r\n        \"xxcj\": \"350000\"\r\n      },\r\n      {\r\n        \"sj\": \"11\",\r\n        \"xscj\": \"330000\",\r\n        \"xxcj\": \"400000\"\r\n      },\r\n      {\r\n        \"sj\": \"12\",\r\n        \"xscj\": \"360000\",\r\n        \"xxcj\": \"350000\"\r\n      }\r\n    ]\r\n  }\r\n]', `type_index` = '系统首页商户统计', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:04:57', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:37:04', `is_deleted` = 0 WHERE `id` = 1847534384184434689;

UPDATE `example_report_data` SET `table_type` = 'CPU使用率', `value` = '[\r\n  {\r\n    \"sj\": \"12:00\",\r\n    \"syl\": \"10\"\r\n  },\r\n  {\r\n    \"sj\": \"13:00\",\r\n    \"syl\": \"22\"\r\n  },\r\n  {\r\n    \"sj\": \"14:00\",\r\n    \"syl\": \"28\"\r\n  },\r\n  {\r\n    \"sj\": \"15:00\",\r\n    \"syl\": \"43\"\r\n  },\r\n  {\r\n    \"sj\": \"16:00\",\r\n    \"syl\": \"49\"\r\n  },\r\n  {\r\n    \"sj\": \"17:00\",\r\n    \"syl\": \"40\"\r\n  }\r\n]', `type_index` = '系统首页系统监控', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:25:26', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:21:20', `is_deleted` = 0 WHERE `id` = 1847539538107441153;

UPDATE `example_report_data` SET `table_type` = '内存使用率', `value` = '[\r\n  {\r\n    \"sj\": \"12:00\",\r\n    \"syl\": \"10\"\r\n  },\r\n  {\r\n    \"sj\": \"13:00\",\r\n    \"syl\": \"22\"\r\n  },\r\n  {\r\n    \"sj\": \"14:00\",\r\n    \"syl\": \"28\"\r\n  },\r\n  {\r\n    \"sj\": \"15:00\",\r\n    \"syl\": \"43\"\r\n  },\r\n  {\r\n    \"sj\": \"16:00\",\r\n    \"syl\": \"49\"\r\n  },\r\n  {\r\n    \"sj\": \"17:00\",\r\n    \"syl\": \"40\"\r\n  }\r\n]', `type_index` = '系统首页系统监控', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:30:04', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:21:41', `is_deleted` = 0 WHERE `id` = 1847540707286458370;

UPDATE `example_report_data` SET `table_type` = '服务器流量', `value` = '[\r\n  {\r\n    \"sj\": \"12:00\",\r\n    \"sc\": 10,\r\n    \"xz\": 20\r\n  },\r\n  {\r\n    \"sj\": \"13:00\",\r\n    \"sc\": 15,\r\n    \"xz\": 12\r\n  },\r\n  {\r\n    \"sj\": \"14:00\",\r\n    \"sc\": 20,\r\n    \"xz\": 23\r\n  },\r\n  {\r\n    \"sj\": \"15:00\",\r\n    \"sc\": 25,\r\n    \"xz\": 22\r\n  },\r\n  {\r\n    \"sj\": \"16:00\",\r\n    \"sc\": 15,\r\n    \"xz\": 10\r\n  },\r\n  {\r\n    \"sj\": \"17:00\",\r\n    \"sc\": 20,\r\n    \"xz\": 5\r\n  }\r\n]', `type_index` = '系统首页系统监控', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:31:25', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:22:03', `is_deleted` = 0 WHERE `id` = 1847541045368332290;

UPDATE `example_report_data` SET `table_type` = '服务器磁盘IO', `value` = '[\r\n  {\r\n    \"sj\": \"12:00\",\r\n    \"sc\": 10,\r\n    \"sr\": 20\r\n  },\r\n  {\r\n    \"sj\": \"13:00\",\r\n    \"sc\": 15,\r\n    \"sr\": 12\r\n  },\r\n  {\r\n    \"sj\": \"14:00\",\r\n    \"sc\": 20,\r\n    \"sr\": 23\r\n  },\r\n  {\r\n    \"sj\": \"15:00\",\r\n    \"sc\": 25,\r\n    \"sr\": 22\r\n  },\r\n  {\r\n    \"sj\": \"16:00\",\r\n    \"sc\": 15,\r\n    \"sr\": 10\r\n  },\r\n  {\r\n    \"sj\": \"17:00\",\r\n    \"sc\": 20,\r\n    \"sr\": 5\r\n  }\r\n]', `type_index` = '系统首页系统监控', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:33:31', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:22:28', `is_deleted` = 0 WHERE `id` = 1847541574995681281;

UPDATE `example_report_data` SET `table_type` = '收支预测', `value` = '[\r\n  {\r\n    \"sj\": \"1月\",\r\n    \"yjsr\": 1008,\r\n    \"yjzc\": 1800\r\n  },\r\n  {\r\n    \"sj\": \"2月\",\r\n    \"yjsr\": 2000,\r\n    \"yjzc\": 2500\r\n  },\r\n  {\r\n    \"sj\": \"3月\",\r\n    \"yjsr\": 2600,\r\n    \"yjzc\": 2100\r\n  },\r\n  {\r\n    \"sj\": \"4月\",\r\n    \"yjsr\": 1800,\r\n    \"yjzc\": 1300\r\n  },\r\n  {\r\n    \"sj\": \"5月\",\r\n    \"yjsr\": 3500,\r\n    \"yjzc\": 6000\r\n  },\r\n  {\r\n    \"sj\": \"6月\",\r\n    \"yjsr\": 5300,\r\n    \"yjzc\": 2800\r\n  },\r\n  {\r\n    \"sj\": \"7月\",\r\n    \"yjsr\": 3200,\r\n    \"yjzc\": 4000\r\n  },\r\n  {\r\n    \"sj\": \"8月\",\r\n    \"yjsr\": 2800,\r\n    \"yjzc\": 5000\r\n  },\r\n  {\r\n    \"sj\": \"9月\",\r\n    \"yjsr\": 3300,\r\n    \"yjzc\": 2500\r\n  },\r\n  {\r\n    \"sj\": \"10月\",\r\n    \"yjsr\": 1100,\r\n    \"yjzc\": 2000\r\n  },\r\n  {\r\n    \"sj\": \"11月\",\r\n    \"yjsr\": 3800,\r\n    \"yjzc\": 2800\r\n  },\r\n  {\r\n    \"sj\": \"12月\",\r\n    \"yjsr\": 3800,\r\n    \"yjzc\": 6000\r\n  }\r\n]', `type_index` = '系统首页数据看板2', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:32:40', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:52:28', `is_deleted` = 0 WHERE `id` = 1847556458613448705;

UPDATE `example_report_data` SET `table_type` = '实时访问量', `value` = '[\r\n  {\r\n    \"sj\": \"10:00\",\r\n    \"ssfkl\": 1500\r\n  },\r\n  {\r\n    \"sj\": \"11:00\",\r\n    \"ssfkl\": 3000\r\n  },\r\n  {\r\n    \"sj\": \"12:00\",\r\n    \"ssfkl\": 2000\r\n  },\r\n  {\r\n    \"sj\": \"13:00\",\r\n    \"ssfkl\": 1000\r\n  },\r\n  {\r\n    \"sj\": \"14:00\",\r\n    \"ssfkl\": 1300\r\n  },\r\n  {\r\n    \"sj\": \"15:00\",\r\n    \"ssfkl\": 1600\r\n  },\r\n  {\r\n    \"sj\": \"16:00\",\r\n    \"ssfkl\": 2100\r\n  },\r\n  {\r\n    \"sj\": \"17:00\",\r\n    \"ssfkl\": 1400\r\n  },\r\n  {\r\n    \"sj\": \"18:00\",\r\n    \"ssfkl\": 1300\r\n  }\r\n]', `type_index` = '系统首页数据会员', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:45:45', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:42:44', `is_deleted` = 0 WHERE `id` = 1847559753813860354;

UPDATE `example_report_data` SET `table_type` = '销售额', `value` = '[\r\n  {\r\n    \"sj\": \"7日\",\r\n    \"xse\": 0\r\n  },\r\n  {\r\n    \"sj\": \"8日\",\r\n    \"xse\": 3000\r\n  },\r\n  {\r\n    \"sj\": \"9日\",\r\n    \"xse\": 2000\r\n  },\r\n  {\r\n    \"sj\": \"10日\",\r\n    \"xse\": 1000\r\n  },\r\n  {\r\n    \"sj\": \"11日\",\r\n    \"xse\": 1300\r\n  },\r\n  {\r\n    \"sj\": \"12日\",\r\n    \"xse\": 1600\r\n  },\r\n  {\r\n    \"sj\": \"13日\",\r\n    \"xse\": 2100\r\n  },\r\n  {\r\n    \"sj\": \"14日\",\r\n    \"xse\": 1400\r\n  },\r\n  {\r\n    \"sj\": \"15日\",\r\n    \"xse\": 0\r\n  }\r\n]', `type_index` = '系统首页数据会员', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:47:48', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:34:33', `is_deleted` = 0 WHERE `id` = 1847560265909018626;

UPDATE `example_report_data` SET `table_type` = '各品类占比', `value` = '[\r\n  {\r\n    \"je\": 3966,\r\n    \"sj\": \"2024-10\",\r\n    \"nz\": 31.25,\r\n    \"xb\": 12.50,\r\n    \"my\": 18.75,\r\n    \"sm\": 37.50\r\n  }\r\n]', `type_index` = '系统首页数据会员', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:51:57', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:34:15', `is_deleted` = 0 WHERE `id` = 1847561312085876737;

UPDATE `example_report_data` SET `table_type` = '销售数据', `value` = '[\r\n  {\r\n    \"sj\": \"44周\",\r\n    \"sczhl\": 120,\r\n    \"je\": 150\r\n  },\r\n  {\r\n    \"sj\": \"45周\",\r\n    \"sczhl\": 132,\r\n    \"je\": 182\r\n  },\r\n  {\r\n    \"sj\": \"46周\",\r\n    \"sczhl\": 101,\r\n    \"je\": 151,\r\n    \"rje\": 100\r\n  },\r\n  {\r\n    \"sj\": \"47周\",\r\n    \"sczhl\": 134,\r\n    \"je\": 164\r\n  },\r\n  {\r\n    \"sj\": \"48周\",\r\n    \"sczhl\": 90,\r\n    \"je\": 120,\r\n    \"rje\": 100\r\n  },\r\n  {\r\n    \"sj\": \"49周\",\r\n    \"sczhl\": 230,\r\n    \"je\": 160\r\n  },\r\n  {\r\n    \"sj\": \"本周\",\r\n    \"sczhl\": 160,\r\n    \"je\": 100,\r\n    \"rje\": 100\r\n  }\r\n]', `type_index` = '系统首页数据首页', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:59:25', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:33:55', `is_deleted` = 0 WHERE `id` = 1847563190903382017;

UPDATE `example_report_data` SET `table_type` = '转化率', `value` = '[\r\n  {\r\n    \"sj\": \"2020-05-12 ~ 2020-07-18\",\r\n    \"cgkhs\": 2454,\r\n    \"xzhys\": 2454,\r\n    \"kdj\": 40.50,\r\n    \"fwzfzhl\": 45\r\n  }\r\n]', `type_index` = '系统首页首页概览', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 14:12:38', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:32:46', `is_deleted` = 0 WHERE `id` = 1849333158003253249;

UPDATE `example_report_data` SET `table_type` = '实时数据', `value` = '[\r\n  {\r\n    \"zfje\": {\r\n      \"jr\": 619.86,\r\n      \"zr\": 407.31,\r\n      \"flag\": 1\r\n    },\r\n    \"zfdd\": {\r\n      \"jr\": 40,\r\n      \"zr\": 88,\r\n      \"flag\": 0\r\n    },\r\n    \"lll\": {\r\n      \"jr\": 2368,\r\n      \"zr\": 2587,\r\n      \"flag\": 0\r\n    },\r\n    \"fks\": {\r\n      \"jr\": 987,\r\n      \"zr\": 1257,\r\n      \"flag\": 0\r\n    },\r\n    \"gxsj\": \"11:41:22\",\r\n    \"data\": [\r\n      {\r\n        \"sj\": \"1\",\r\n        \"zr\": 76,\r\n        \"jr\": 50\r\n      },\r\n      {\r\n        \"sj\": \"2\",\r\n        \"zr\": 60,\r\n        \"jr\": 50\r\n      },\r\n      {\r\n        \"sj\": \"3\",\r\n        \"zr\": 22,\r\n        \"jr\": 32\r\n      },\r\n      {\r\n        \"sj\": \"4\",\r\n        \"zr\": 85,\r\n        \"jr\": 65\r\n      },\r\n      {\r\n        \"sj\": \"5\",\r\n        \"zr\": 50,\r\n        \"jr\": 30\r\n      },\r\n      {\r\n        \"sj\": \"6\",\r\n        \"zr\": 60\r\n      }\r\n    ]\r\n  }\r\n]', `type_index` = '系统首页首页概览', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 14:43:30', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:32:35', `is_deleted` = 0 WHERE `id` = 1849340925292224513;

UPDATE `example_report_data` SET `table_type` = '智能助手', `value` = '[\r\n  {\r\n    \"type\": \"1\",\r\n    \"data\": [\r\n      {\r\n        \"dfhdd\": 3534,\r\n        \"dhfkf\": 7833,\r\n        \"yzxsp\": 3873,\r\n        \"dtkdd\": 3873,\r\n        \"pdzkh\": 2737\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"2\",\r\n    \"data\": [\r\n      {\r\n        \"dfhdd\": 3454,\r\n        \"dhfkf\": 3575,\r\n        \"yzxsp\": 3388,\r\n        \"dtkdd\": 3783,\r\n        \"pdzkh\": 3788\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"3\",\r\n    \"data\": [\r\n      {\r\n        \"dfhdd\": 4876,\r\n        \"dhfkf\": 3788,\r\n        \"yzxsp\": 3786,\r\n        \"dtkdd\": 2837,\r\n        \"pdzkh\": 3787\r\n      }\r\n    ]\r\n  },\r\n  {\r\n    \"type\": \"4\",\r\n    \"data\": [\r\n      {\r\n        \"dfhdd\": 3873,\r\n        \"dhfkf\": 3738,\r\n        \"yzxsp\": 3783,\r\n        \"dtkdd\": 3366,\r\n        \"pdzkh\": 3783\r\n      }\r\n    ]\r\n  }\r\n]', `type_index` = '系统首页首页概览', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 14:55:06', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:37:27', `is_deleted` = 0 WHERE `id` = 1849343844515225601;

INSERT INTO `example_student`(`id`, `sno`, `name`, `sex`, `birthday`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `flag`) VALUES (1879373373178527746, 2, '2', 2, '2025-01-15', 1, 1, '2025-01-15 11:41:43', 101, 1, '2025-01-18 16:09:29', 0, NULL);

INSERT INTO `example_task_management_list`(`id`, `title`, `status`, `jzsj`, `urgent_relief`, `xmmc`, `fj`, `tx`, `kssj`, `rwtx`, `rwms`, `cyry`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `lys`, `color`, `txfs`) VALUES (1847172292277329921, '任务标题文字', NULL, '2024-10-15 11:06:00', '2', NULL, 'http://oss.yckxt.com/chatgpt/upload/1/2024-10-18/1/文件_7.txt,http://oss.yckxt.com/chatgpt/upload/1/2024-10-18/1/文件_8.txt', NULL, '2024-10-15 15:10:00', '3', '111', '1,104', 1, 1, '2024-10-18 15:06:07', 101, NULL, NULL, -1, NULL, '#24D2D3', '1');

INSERT INTO `example_task_management_list`(`id`, `title`, `status`, `jzsj`, `urgent_relief`, `xmmc`, `fj`, `tx`, `kssj`, `rwtx`, `rwms`, `cyry`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `lys`, `color`, `txfs`) VALUES (1847174612045139969, '54', NULL, '2024-10-21 00:00:00', 'zy', NULL, NULL, NULL, '2024-10-22 00:00:00', '1', NULL, NULL, 1, 1, '2024-10-18 15:15:21', 101, NULL, NULL, -1, NULL, '#24D2D3', NULL);

INSERT INTO `example_task_management_list`(`id`, `title`, `status`, `jzsj`, `urgent_relief`, `xmmc`, `fj`, `tx`, `kssj`, `rwtx`, `rwms`, `cyry`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `lys`, `color`, `txfs`) VALUES (1847175929358585858, '53', NULL, '2024-10-02 00:00:00', 'zy', NULL, NULL, NULL, '2024-10-10 00:00:00', '1', NULL, NULL, 1, 1, '2024-10-18 15:20:35', 101, NULL, NULL, -1, NULL, '#24D2D3', NULL);

INSERT INTO `example_task_management_list`(`id`, `title`, `status`, `jzsj`, `urgent_relief`, `xmmc`, `fj`, `tx`, `kssj`, `rwtx`, `rwms`, `cyry`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `lys`, `color`, `txfs`) VALUES (1851190052682604546, '25', NULL, '2024-10-12 00:00:00', 'zy', NULL, NULL, NULL, '2024-10-18 00:00:00', '1', NULL, NULL, 1, 1, '2024-10-29 17:11:16', 101, NULL, NULL, -1, NULL, '#24D2D3', NULL);

UPDATE `example_tree_table` SET `pid` = 0, `type_name` = '箱包/鞋靴', `type_code` = '10001', `type_state` = '1', `remark` = '备注', `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-05 15:19:15', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-20 09:29:07', `is_deleted` = 0 WHERE `id` = 1831592917458231298;

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839496034538156033, '2024-08-28', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/3422c7d048c2899525770fb74ee0ee3a_2.jpg', '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 10:43:25', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839496503452954626, '2024-09-10', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/3422c7d048c2899525770fb74ee0ee3a_3.jpg', '5', NULL, '5', NULL, '5', NULL, NULL, 1, 1, '2024-09-27 10:45:17', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839496820940795905, '2024-09-10', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/3422c7d048c2899525770fb74ee0ee3a_3.jpg', '5', NULL, '5', NULL, '5', NULL, NULL, 1, 1, '2024-09-27 10:46:32', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839497280036728833, '2024-09-10', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/3422c7d048c2899525770fb74ee0ee3a_3.jpg', '5', NULL, '5', NULL, '5', NULL, NULL, 1, 1, '2024-09-27 10:48:22', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839497533376884737, '2024-09-10', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/3422c7d048c2899525770fb74ee0ee3a_3.jpg', '5', NULL, '5', NULL, '5', NULL, NULL, 1, 1, '2024-09-27 10:49:22', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839497985078259713, '2024-09-10', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/3422c7d048c2899525770fb74ee0ee3a_3.jpg', '5', NULL, '5', NULL, '5', NULL, NULL, 1, 1, '2024-09-27 10:51:10', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839504358235099137, '2024-09-10', NULL, '5', NULL, '5', NULL, '5', NULL, NULL, 1, 1, '2024-09-27 11:16:29', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839508175647330306, '2024-08-27', NULL, '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 11:31:40', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839509646707183617, '2024-09-27', NULL, '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 11:37:30', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839509848331571202, '2024-09-27', NULL, '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 11:38:18', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839510052367683585, '2024-09-27', NULL, '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 11:39:07', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839510311795290113, '2024-08-28', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/_我的 (1)_2.png', '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 11:40:09', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839510971907436546, '2024-08-28', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/_我的 (1)_3.png', '1', '1', '1', '1', '1', NULL, NULL, 1, 1, '2024-09-27 11:42:46', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839511282264961025, '2024-09-13', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/_我的 (1)_4.png', '1', '1', '1', '1', NULL, NULL, NULL, 1, 1, '2024-09-27 11:44:00', 101, NULL, NULL, -1, NULL);

INSERT INTO `example_tyym_dtsj`(`id`, `sr`, `tx`, `name`, `sg`, `tz`, `sj`, `dz`, `xs`, `zt`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `xb`) VALUES (1839511696095965186, '2024-09-12', 'http://oss.yckxt.com/chatgpt/upload/1/2024-09-27/1/_我的 (1)_5.png', '1', '1', NULL, '1', NULL, '1', '1', 1, 1, '2024-09-27 11:45:39', 101, NULL, NULL, -1, NULL);

DELETE FROM `infra_api_access_log` WHERE `id` = 35832;

DELETE FROM `infra_api_access_log` WHERE `id` = 35833;

DELETE FROM `infra_api_access_log` WHERE `id` = 35834;

DELETE FROM `infra_api_access_log` WHERE `id` = 35835;

DELETE FROM `infra_api_access_log` WHERE `id` = 35836;

DELETE FROM `infra_api_access_log` WHERE `id` = 35837;

DELETE FROM `infra_api_access_log` WHERE `id` = 35838;

DELETE FROM `infra_api_access_log` WHERE `id` = 35839;

DELETE FROM `infra_api_access_log` WHERE `id` = 35840;

DELETE FROM `infra_api_access_log` WHERE `id` = 35841;

DELETE FROM `infra_api_access_log` WHERE `id` = 35842;

DELETE FROM `infra_api_access_log` WHERE `id` = 35843;

DELETE FROM `infra_api_access_log` WHERE `id` = 35844;

DELETE FROM `infra_api_access_log` WHERE `id` = 35845;

DELETE FROM `infra_api_access_log` WHERE `id` = 35846;

DELETE FROM `infra_api_access_log` WHERE `id` = 35847;

DELETE FROM `infra_api_access_log` WHERE `id` = 35848;

DELETE FROM `infra_api_access_log` WHERE `id` = 35849;

DELETE FROM `infra_api_access_log` WHERE `id` = 35850;

DELETE FROM `infra_api_access_log` WHERE `id` = 35851;

DELETE FROM `infra_api_access_log` WHERE `id` = 35852;

DELETE FROM `infra_api_access_log` WHERE `id` = 35853;

DELETE FROM `infra_api_access_log` WHERE `id` = 35854;

DELETE FROM `infra_api_access_log` WHERE `id` = 35855;

DELETE FROM `infra_api_access_log` WHERE `id` = 35856;

DELETE FROM `infra_api_access_log` WHERE `id` = 35857;

DELETE FROM `infra_api_access_log` WHERE `id` = 35858;

DELETE FROM `infra_api_access_log` WHERE `id` = 35859;

DELETE FROM `infra_api_access_log` WHERE `id` = 35860;

DELETE FROM `infra_api_access_log` WHERE `id` = 35861;

DELETE FROM `infra_api_access_log` WHERE `id` = 35862;

DELETE FROM `infra_api_access_log` WHERE `id` = 35863;

DELETE FROM `infra_api_access_log` WHERE `id` = 35864;

DELETE FROM `infra_api_access_log` WHERE `id` = 35865;

DELETE FROM `infra_api_access_log` WHERE `id` = 35866;

DELETE FROM `infra_api_access_log` WHERE `id` = 35867;

DELETE FROM `infra_api_access_log` WHERE `id` = 35868;

DELETE FROM `infra_api_access_log` WHERE `id` = 35869;

DELETE FROM `infra_api_access_log` WHERE `id` = 35870;

DELETE FROM `infra_api_access_log` WHERE `id` = 35871;

DELETE FROM `infra_api_access_log` WHERE `id` = 35872;

DELETE FROM `infra_api_access_log` WHERE `id` = 35873;

DELETE FROM `infra_api_access_log` WHERE `id` = 35874;

DELETE FROM `infra_api_access_log` WHERE `id` = 35875;

DELETE FROM `infra_api_access_log` WHERE `id` = 35876;

DELETE FROM `infra_api_access_log` WHERE `id` = 35877;

DELETE FROM `infra_api_access_log` WHERE `id` = 35878;

DELETE FROM `infra_api_access_log` WHERE `id` = 35879;

DELETE FROM `infra_api_access_log` WHERE `id` = 35880;

DELETE FROM `infra_api_access_log` WHERE `id` = 35881;

DELETE FROM `infra_api_access_log` WHERE `id` = 35882;

DELETE FROM `infra_api_access_log` WHERE `id` = 35883;

DELETE FROM `infra_api_access_log` WHERE `id` = 35884;

DELETE FROM `infra_api_access_log` WHERE `id` = 35885;

DELETE FROM `infra_api_access_log` WHERE `id` = 35886;

DELETE FROM `infra_api_access_log` WHERE `id` = 35887;

DELETE FROM `infra_api_access_log` WHERE `id` = 35888;

DELETE FROM `infra_api_access_log` WHERE `id` = 35889;

DELETE FROM `infra_api_access_log` WHERE `id` = 35890;

DELETE FROM `infra_api_access_log` WHERE `id` = 35891;

DELETE FROM `infra_api_access_log` WHERE `id` = 35892;

DELETE FROM `infra_api_access_log` WHERE `id` = 35893;

DELETE FROM `infra_api_access_log` WHERE `id` = 35894;

DELETE FROM `infra_api_access_log` WHERE `id` = 35895;

DELETE FROM `infra_api_access_log` WHERE `id` = 35896;

DELETE FROM `infra_api_access_log` WHERE `id` = 35897;

DELETE FROM `infra_api_access_log` WHERE `id` = 35898;

DELETE FROM `infra_api_access_log` WHERE `id` = 35899;

DELETE FROM `infra_api_access_log` WHERE `id` = 35900;

DELETE FROM `infra_api_access_log` WHERE `id` = 35901;

DELETE FROM `infra_api_access_log` WHERE `id` = 35902;

DELETE FROM `infra_api_access_log` WHERE `id` = 35903;

DELETE FROM `infra_api_access_log` WHERE `id` = 35904;

DELETE FROM `infra_api_access_log` WHERE `id` = 35905;

DELETE FROM `infra_api_access_log` WHERE `id` = 35906;

DELETE FROM `infra_api_access_log` WHERE `id` = 35907;

DELETE FROM `infra_api_access_log` WHERE `id` = 35908;

DELETE FROM `infra_api_access_log` WHERE `id` = 35909;

DELETE FROM `infra_api_access_log` WHERE `id` = 35910;

DELETE FROM `infra_api_access_log` WHERE `id` = 35911;

DELETE FROM `infra_api_access_log` WHERE `id` = 35912;

DELETE FROM `infra_api_access_log` WHERE `id` = 35913;

DELETE FROM `infra_api_access_log` WHERE `id` = 35914;

DELETE FROM `infra_api_access_log` WHERE `id` = 35915;

DELETE FROM `infra_api_access_log` WHERE `id` = 35916;

DELETE FROM `infra_api_access_log` WHERE `id` = 35917;

DELETE FROM `infra_api_access_log` WHERE `id` = 35918;

DELETE FROM `infra_api_access_log` WHERE `id` = 35919;

DELETE FROM `infra_api_access_log` WHERE `id` = 35920;

DELETE FROM `infra_api_access_log` WHERE `id` = 35921;

DELETE FROM `infra_api_access_log` WHERE `id` = 35922;

DELETE FROM `infra_api_access_log` WHERE `id` = 35923;

DELETE FROM `infra_api_access_log` WHERE `id` = 35924;

DELETE FROM `infra_api_access_log` WHERE `id` = 35925;

DELETE FROM `infra_api_access_log` WHERE `id` = 35926;

DELETE FROM `infra_api_access_log` WHERE `id` = 35927;

DELETE FROM `infra_api_access_log` WHERE `id` = 35928;

DELETE FROM `infra_api_access_log` WHERE `id` = 35929;

DELETE FROM `infra_api_access_log` WHERE `id` = 35930;

DELETE FROM `infra_api_access_log` WHERE `id` = 35931;

DELETE FROM `infra_api_access_log` WHERE `id` = 35932;

DELETE FROM `infra_api_access_log` WHERE `id` = 35933;

DELETE FROM `infra_api_access_log` WHERE `id` = 35934;

DELETE FROM `infra_api_access_log` WHERE `id` = 35935;

DELETE FROM `infra_api_access_log` WHERE `id` = 35936;

DELETE FROM `infra_api_access_log` WHERE `id` = 35937;

DELETE FROM `infra_api_access_log` WHERE `id` = 35938;

DELETE FROM `infra_api_access_log` WHERE `id` = 35939;

DELETE FROM `infra_api_error_log` WHERE `id` = 15324;

DELETE FROM `infra_api_error_log` WHERE `id` = 15325;

DELETE FROM `infra_api_error_log` WHERE `id` = 15326;

DELETE FROM `infra_api_error_log` WHERE `id` = 15327;

DELETE FROM `infra_api_error_log` WHERE `id` = 15328;

DELETE FROM `infra_api_error_log` WHERE `id` = 15329;

DELETE FROM `infra_api_error_log` WHERE `id` = 15330;

DELETE FROM `infra_api_error_log` WHERE `id` = 15331;

DELETE FROM `infra_api_error_log` WHERE `id` = 15332;

DELETE FROM `infra_api_error_log` WHERE `id` = 15333;

DELETE FROM `infra_api_error_log` WHERE `id` = 15334;

DELETE FROM `infra_api_error_log` WHERE `id` = 15335;

DELETE FROM `infra_api_error_log` WHERE `id` = 15336;

DELETE FROM `infra_api_error_log` WHERE `id` = 15337;

DELETE FROM `infra_api_error_log` WHERE `id` = 15338;

DELETE FROM `infra_api_error_log` WHERE `id` = 15339;

DELETE FROM `infra_api_error_log` WHERE `id` = 15340;

DELETE FROM `infra_api_error_log` WHERE `id` = 15341;

DELETE FROM `infra_api_error_log` WHERE `id` = 15342;

DELETE FROM `infra_api_error_log` WHERE `id` = 15343;

DELETE FROM `infra_api_error_log` WHERE `id` = 15344;

DELETE FROM `infra_api_error_log` WHERE `id` = 15345;

DELETE FROM `infra_api_error_log` WHERE `id` = 15346;

DELETE FROM `infra_api_error_log` WHERE `id` = 15347;

DELETE FROM `infra_api_error_log` WHERE `id` = 15348;

DELETE FROM `infra_api_error_log` WHERE `id` = 15349;

DELETE FROM `infra_api_error_log` WHERE `id` = 15350;

DELETE FROM `infra_api_error_log` WHERE `id` = 15351;

DELETE FROM `infra_api_error_log` WHERE `id` = 15352;

DELETE FROM `infra_api_error_log` WHERE `id` = 15353;

DELETE FROM `infra_api_error_log` WHERE `id` = 15354;

DELETE FROM `infra_api_error_log` WHERE `id` = 15355;

DELETE FROM `infra_api_error_log` WHERE `id` = 15356;

DELETE FROM `infra_api_error_log` WHERE `id` = 15357;

DELETE FROM `infra_api_error_log` WHERE `id` = 15358;

DELETE FROM `infra_api_error_log` WHERE `id` = 15359;

DELETE FROM `infra_api_error_log` WHERE `id` = 15360;

DELETE FROM `infra_api_error_log` WHERE `id` = 15361;

DELETE FROM `infra_api_error_log` WHERE `id` = 15362;

DELETE FROM `infra_api_error_log` WHERE `id` = 15363;

DELETE FROM `infra_api_error_log` WHERE `id` = 15364;

DELETE FROM `infra_api_error_log` WHERE `id` = 15365;

DELETE FROM `infra_api_error_log` WHERE `id` = 15366;

DELETE FROM `infra_api_error_log` WHERE `id` = 15367;

DELETE FROM `infra_api_error_log` WHERE `id` = 15368;

DELETE FROM `infra_api_error_log` WHERE `id` = 15369;

DELETE FROM `infra_api_error_log` WHERE `id` = 15370;

DELETE FROM `infra_api_error_log` WHERE `id` = 15371;

DELETE FROM `infra_api_error_log` WHERE `id` = 15372;

DELETE FROM `infra_api_error_log` WHERE `id` = 15373;

DELETE FROM `infra_api_error_log` WHERE `id` = 15374;

DELETE FROM `infra_api_error_log` WHERE `id` = 15375;

DELETE FROM `infra_api_error_log` WHERE `id` = 15376;

DELETE FROM `infra_api_error_log` WHERE `id` = 15377;

DELETE FROM `infra_api_error_log` WHERE `id` = 15378;

DELETE FROM `infra_api_error_log` WHERE `id` = 15379;

DELETE FROM `infra_api_error_log` WHERE `id` = 15380;

DELETE FROM `infra_api_error_log` WHERE `id` = 15381;

DELETE FROM `infra_api_error_log` WHERE `id` = 15382;

DELETE FROM `infra_api_error_log` WHERE `id` = 15383;

DELETE FROM `infra_api_error_log` WHERE `id` = 15384;

DELETE FROM `infra_api_error_log` WHERE `id` = 15385;

DELETE FROM `infra_api_error_log` WHERE `id` = 15386;

DELETE FROM `infra_api_error_log` WHERE `id` = 15387;

DELETE FROM `infra_api_error_log` WHERE `id` = 15388;

DELETE FROM `infra_api_error_log` WHERE `id` = 15389;

DELETE FROM `infra_api_error_log` WHERE `id` = 15390;

DELETE FROM `infra_api_error_log` WHERE `id` = 15391;

DELETE FROM `infra_api_error_log` WHERE `id` = 15392;

DELETE FROM `infra_api_error_log` WHERE `id` = 15393;

DELETE FROM `infra_api_error_log` WHERE `id` = 15394;

DELETE FROM `infra_api_error_log` WHERE `id` = 15395;

DELETE FROM `infra_api_error_log` WHERE `id` = 15396;

DELETE FROM `infra_api_error_log` WHERE `id` = 15397;

DELETE FROM `infra_api_error_log` WHERE `id` = 15398;

DELETE FROM `infra_api_error_log` WHERE `id` = 15399;

DELETE FROM `infra_api_error_log` WHERE `id` = 15400;

DELETE FROM `infra_api_error_log` WHERE `id` = 15401;

DELETE FROM `infra_api_error_log` WHERE `id` = 15402;

DELETE FROM `infra_api_error_log` WHERE `id` = 15403;

DELETE FROM `infra_api_error_log` WHERE `id` = 15404;

DELETE FROM `infra_api_error_log` WHERE `id` = 15405;

DELETE FROM `infra_api_error_log` WHERE `id` = 15406;

DELETE FROM `infra_api_error_log` WHERE `id` = 15407;

DELETE FROM `infra_api_error_log` WHERE `id` = 15408;

DELETE FROM `infra_api_error_log` WHERE `id` = 15409;

DELETE FROM `infra_api_error_log` WHERE `id` = 15410;

DELETE FROM `infra_api_error_log` WHERE `id` = 15411;

DELETE FROM `infra_api_error_log` WHERE `id` = 15412;

DELETE FROM `infra_api_error_log` WHERE `id` = 15413;

DELETE FROM `infra_api_error_log` WHERE `id` = 15414;

DELETE FROM `infra_api_error_log` WHERE `id` = 15415;

DELETE FROM `infra_api_error_log` WHERE `id` = 15416;

DELETE FROM `infra_api_error_log` WHERE `id` = 15417;

DELETE FROM `infra_api_error_log` WHERE `id` = 15418;

DELETE FROM `infra_api_error_log` WHERE `id` = 15419;

DELETE FROM `infra_api_error_log` WHERE `id` = 15420;

DELETE FROM `infra_api_error_log` WHERE `id` = 15421;

DELETE FROM `infra_api_error_log` WHERE `id` = 15422;

DELETE FROM `infra_api_error_log` WHERE `id` = 15423;

DELETE FROM `infra_api_error_log` WHERE `id` = 15424;

DELETE FROM `infra_api_error_log` WHERE `id` = 15425;

DELETE FROM `infra_api_error_log` WHERE `id` = 15426;

DELETE FROM `infra_api_error_log` WHERE `id` = 15427;

DELETE FROM `infra_api_error_log` WHERE `id` = 15428;

DELETE FROM `infra_api_error_log` WHERE `id` = 15429;

DELETE FROM `infra_api_error_log` WHERE `id` = 15430;

DELETE FROM `infra_api_error_log` WHERE `id` = 15431;

DELETE FROM `infra_api_error_log` WHERE `id` = 15432;

DELETE FROM `infra_api_error_log` WHERE `id` = 15433;

DELETE FROM `infra_api_error_log` WHERE `id` = 15434;

DELETE FROM `infra_api_error_log` WHERE `id` = 15435;

DELETE FROM `infra_api_error_log` WHERE `id` = 15436;

DELETE FROM `infra_api_error_log` WHERE `id` = 15437;

DELETE FROM `infra_api_error_log` WHERE `id` = 15438;

DELETE FROM `infra_api_error_log` WHERE `id` = 15439;

DELETE FROM `infra_api_error_log` WHERE `id` = 15440;

DELETE FROM `infra_api_error_log` WHERE `id` = 15441;

DELETE FROM `infra_api_error_log` WHERE `id` = 15442;

DELETE FROM `infra_api_error_log` WHERE `id` = 15443;

DELETE FROM `infra_api_error_log` WHERE `id` = 15444;

DELETE FROM `infra_api_error_log` WHERE `id` = 15445;

DELETE FROM `infra_api_error_log` WHERE `id` = 15446;

DELETE FROM `infra_api_error_log` WHERE `id` = 15447;

DELETE FROM `infra_api_error_log` WHERE `id` = 15448;

DELETE FROM `infra_api_error_log` WHERE `id` = 15449;

DELETE FROM `infra_api_error_log` WHERE `id` = 15450;

DELETE FROM `infra_api_error_log` WHERE `id` = 15451;

DELETE FROM `infra_api_error_log` WHERE `id` = 15452;

DELETE FROM `infra_api_error_log` WHERE `id` = 15453;

DELETE FROM `infra_api_error_log` WHERE `id` = 15454;

DELETE FROM `infra_api_error_log` WHERE `id` = 15455;

DELETE FROM `infra_api_error_log` WHERE `id` = 15456;

DELETE FROM `infra_api_error_log` WHERE `id` = 15457;

DELETE FROM `infra_api_error_log` WHERE `id` = 15458;

DELETE FROM `infra_api_error_log` WHERE `id` = 15459;

DELETE FROM `infra_api_error_log` WHERE `id` = 15460;

DELETE FROM `infra_api_error_log` WHERE `id` = 15461;

DELETE FROM `infra_api_error_log` WHERE `id` = 15462;

DELETE FROM `infra_api_error_log` WHERE `id` = 15463;

DELETE FROM `infra_api_error_log` WHERE `id` = 15464;

DELETE FROM `infra_api_error_log` WHERE `id` = 15465;

DELETE FROM `infra_api_error_log` WHERE `id` = 15466;

DELETE FROM `infra_api_error_log` WHERE `id` = 15467;

DELETE FROM `infra_api_error_log` WHERE `id` = 15468;

DELETE FROM `infra_api_error_log` WHERE `id` = 15469;

DELETE FROM `infra_api_error_log` WHERE `id` = 15470;

DELETE FROM `infra_api_error_log` WHERE `id` = 15471;

DELETE FROM `infra_api_error_log` WHERE `id` = 15472;

DELETE FROM `infra_api_error_log` WHERE `id` = 15473;

DELETE FROM `infra_api_error_log` WHERE `id` = 15474;

DELETE FROM `infra_api_error_log` WHERE `id` = 15475;

DELETE FROM `infra_api_error_log` WHERE `id` = 15476;

DELETE FROM `infra_api_error_log` WHERE `id` = 15477;

DELETE FROM `infra_api_error_log` WHERE `id` = 15478;

DELETE FROM `infra_api_error_log` WHERE `id` = 15479;

DELETE FROM `infra_api_error_log` WHERE `id` = 15480;

DELETE FROM `infra_api_error_log` WHERE `id` = 15481;

DELETE FROM `infra_api_error_log` WHERE `id` = 15482;

DELETE FROM `infra_api_error_log` WHERE `id` = 15483;

DELETE FROM `infra_api_error_log` WHERE `id` = 15484;

DELETE FROM `infra_api_error_log` WHERE `id` = 15485;

DELETE FROM `infra_api_error_log` WHERE `id` = 15486;

DELETE FROM `infra_api_error_log` WHERE `id` = 15487;

DELETE FROM `infra_api_error_log` WHERE `id` = 15488;

DELETE FROM `infra_api_error_log` WHERE `id` = 15489;

DELETE FROM `infra_api_error_log` WHERE `id` = 15490;

DELETE FROM `infra_api_error_log` WHERE `id` = 15491;

DELETE FROM `infra_api_error_log` WHERE `id` = 15492;

DELETE FROM `infra_api_error_log` WHERE `id` = 15493;

DELETE FROM `infra_api_error_log` WHERE `id` = 15494;

DELETE FROM `infra_api_error_log` WHERE `id` = 15495;

DELETE FROM `infra_api_error_log` WHERE `id` = 15496;

DELETE FROM `infra_api_error_log` WHERE `id` = 15497;

DELETE FROM `infra_api_error_log` WHERE `id` = 15498;

DELETE FROM `infra_api_error_log` WHERE `id` = 15499;

DELETE FROM `infra_api_error_log` WHERE `id` = 15500;

DELETE FROM `infra_api_error_log` WHERE `id` = 15501;

DELETE FROM `infra_api_error_log` WHERE `id` = 15502;

DELETE FROM `infra_api_error_log` WHERE `id` = 15503;

DELETE FROM `infra_api_error_log` WHERE `id` = 15504;

DELETE FROM `infra_api_error_log` WHERE `id` = 15505;

DELETE FROM `infra_api_error_log` WHERE `id` = 15506;

DELETE FROM `infra_api_error_log` WHERE `id` = 15507;

DELETE FROM `infra_api_error_log` WHERE `id` = 15508;

DELETE FROM `infra_api_error_log` WHERE `id` = 15509;

DELETE FROM `infra_api_error_log` WHERE `id` = 15510;

DELETE FROM `infra_api_error_log` WHERE `id` = 15511;

DELETE FROM `infra_api_error_log` WHERE `id` = 15512;

DELETE FROM `infra_api_error_log` WHERE `id` = 15513;

DELETE FROM `infra_api_error_log` WHERE `id` = 15514;

DELETE FROM `infra_api_error_log` WHERE `id` = 15515;

DELETE FROM `infra_api_error_log` WHERE `id` = 15516;

DELETE FROM `infra_api_error_log` WHERE `id` = 15517;

DELETE FROM `infra_api_error_log` WHERE `id` = 15518;

DELETE FROM `infra_api_error_log` WHERE `id` = 15519;

DELETE FROM `infra_api_error_log` WHERE `id` = 15520;

DELETE FROM `infra_api_error_log` WHERE `id` = 15521;

DELETE FROM `infra_api_error_log` WHERE `id` = 15522;

DELETE FROM `infra_api_error_log` WHERE `id` = 15523;

DELETE FROM `infra_api_error_log` WHERE `id` = 15524;

DELETE FROM `infra_api_error_log` WHERE `id` = 15525;

DELETE FROM `infra_api_error_log` WHERE `id` = 15526;

DELETE FROM `infra_api_error_log` WHERE `id` = 15527;

DELETE FROM `infra_api_error_log` WHERE `id` = 15528;

DELETE FROM `infra_api_error_log` WHERE `id` = 15529;

DELETE FROM `infra_api_error_log` WHERE `id` = 15530;

DELETE FROM `infra_api_error_log` WHERE `id` = 15531;

DELETE FROM `infra_api_error_log` WHERE `id` = 15532;

DELETE FROM `infra_api_error_log` WHERE `id` = 15533;

DELETE FROM `infra_api_error_log` WHERE `id` = 15534;

DELETE FROM `infra_api_error_log` WHERE `id` = 15535;

DELETE FROM `infra_api_error_log` WHERE `id` = 15536;

DELETE FROM `infra_api_error_log` WHERE `id` = 15537;

DELETE FROM `infra_api_error_log` WHERE `id` = 15538;

DELETE FROM `infra_api_error_log` WHERE `id` = 15539;

DELETE FROM `infra_api_error_log` WHERE `id` = 15540;

DELETE FROM `infra_api_error_log` WHERE `id` = 15541;

DELETE FROM `infra_api_error_log` WHERE `id` = 15542;

DELETE FROM `infra_api_error_log` WHERE `id` = 15543;

DELETE FROM `infra_api_error_log` WHERE `id` = 15544;

DELETE FROM `infra_api_error_log` WHERE `id` = 15545;

DELETE FROM `infra_api_error_log` WHERE `id` = 15546;

DELETE FROM `infra_api_error_log` WHERE `id` = 15547;

DELETE FROM `infra_api_error_log` WHERE `id` = 15548;

DELETE FROM `infra_api_error_log` WHERE `id` = 15549;

DELETE FROM `infra_api_error_log` WHERE `id` = 15550;

DELETE FROM `infra_api_error_log` WHERE `id` = 15551;

DELETE FROM `infra_api_error_log` WHERE `id` = 15552;

DELETE FROM `infra_api_error_log` WHERE `id` = 15553;

DELETE FROM `infra_api_error_log` WHERE `id` = 15554;

DELETE FROM `infra_api_error_log` WHERE `id` = 15555;

DELETE FROM `infra_api_error_log` WHERE `id` = 15556;

DELETE FROM `infra_api_error_log` WHERE `id` = 15557;

DELETE FROM `infra_api_error_log` WHERE `id` = 15558;

DELETE FROM `infra_api_error_log` WHERE `id` = 15559;

DELETE FROM `infra_api_error_log` WHERE `id` = 15560;

DELETE FROM `infra_api_error_log` WHERE `id` = 15561;

DELETE FROM `infra_api_error_log` WHERE `id` = 15562;

DELETE FROM `infra_api_error_log` WHERE `id` = 15563;

DELETE FROM `infra_api_error_log` WHERE `id` = 15564;

DELETE FROM `infra_api_error_log` WHERE `id` = 15565;

DELETE FROM `infra_api_error_log` WHERE `id` = 15566;

DELETE FROM `infra_api_error_log` WHERE `id` = 15567;

DELETE FROM `infra_api_error_log` WHERE `id` = 15568;

DELETE FROM `infra_api_error_log` WHERE `id` = 15569;

DELETE FROM `infra_api_error_log` WHERE `id` = 15570;

DELETE FROM `infra_api_error_log` WHERE `id` = 15571;

DELETE FROM `infra_api_error_log` WHERE `id` = 15572;

DELETE FROM `infra_api_error_log` WHERE `id` = 15573;

DELETE FROM `infra_api_error_log` WHERE `id` = 15574;

DELETE FROM `infra_api_error_log` WHERE `id` = 15575;

DELETE FROM `infra_api_error_log` WHERE `id` = 15576;

DELETE FROM `infra_api_error_log` WHERE `id` = 15577;

DELETE FROM `infra_api_error_log` WHERE `id` = 15578;

DELETE FROM `infra_api_error_log` WHERE `id` = 15579;

DELETE FROM `infra_api_error_log` WHERE `id` = 15580;

DELETE FROM `infra_api_error_log` WHERE `id` = 15581;

DELETE FROM `infra_api_error_log` WHERE `id` = 15582;

DELETE FROM `infra_api_error_log` WHERE `id` = 15583;

DELETE FROM `infra_api_error_log` WHERE `id` = 15584;

DELETE FROM `infra_api_error_log` WHERE `id` = 15585;

DELETE FROM `infra_api_error_log` WHERE `id` = 15586;

DELETE FROM `infra_api_error_log` WHERE `id` = 15587;

DELETE FROM `infra_api_error_log` WHERE `id` = 15588;

DELETE FROM `infra_api_error_log` WHERE `id` = 15589;

DELETE FROM `infra_api_error_log` WHERE `id` = 15590;

DELETE FROM `infra_api_error_log` WHERE `id` = 15591;

DELETE FROM `infra_api_error_log` WHERE `id` = 15592;

DELETE FROM `infra_api_error_log` WHERE `id` = 15593;

DELETE FROM `infra_api_error_log` WHERE `id` = 15594;

DELETE FROM `infra_api_error_log` WHERE `id` = 15595;

DELETE FROM `infra_api_error_log` WHERE `id` = 15596;

DELETE FROM `infra_api_error_log` WHERE `id` = 15597;

DELETE FROM `infra_api_error_log` WHERE `id` = 15598;

DELETE FROM `infra_api_error_log` WHERE `id` = 15599;

DELETE FROM `infra_api_error_log` WHERE `id` = 15600;

DELETE FROM `infra_api_error_log` WHERE `id` = 15601;

DELETE FROM `infra_api_error_log` WHERE `id` = 15602;

DELETE FROM `infra_api_error_log` WHERE `id` = 15603;

DELETE FROM `infra_api_error_log` WHERE `id` = 15604;

DELETE FROM `infra_api_error_log` WHERE `id` = 15605;

DELETE FROM `infra_api_error_log` WHERE `id` = 15606;

DELETE FROM `infra_api_error_log` WHERE `id` = 15607;

DELETE FROM `infra_api_error_log` WHERE `id` = 15608;

DELETE FROM `infra_api_error_log` WHERE `id` = 15609;

DELETE FROM `infra_api_error_log` WHERE `id` = 15610;

DELETE FROM `infra_api_error_log` WHERE `id` = 15611;

DELETE FROM `infra_api_error_log` WHERE `id` = 15612;

DELETE FROM `infra_api_error_log` WHERE `id` = 15613;

DELETE FROM `infra_api_error_log` WHERE `id` = 15614;

DELETE FROM `infra_api_error_log` WHERE `id` = 15615;

DELETE FROM `infra_api_error_log` WHERE `id` = 15616;

DELETE FROM `infra_api_error_log` WHERE `id` = 15617;

DELETE FROM `infra_api_error_log` WHERE `id` = 15618;

DELETE FROM `infra_api_error_log` WHERE `id` = 15619;

DELETE FROM `infra_api_error_log` WHERE `id` = 15620;

DELETE FROM `infra_api_error_log` WHERE `id` = 15621;

DELETE FROM `infra_api_error_log` WHERE `id` = 15622;

DELETE FROM `infra_api_error_log` WHERE `id` = 15623;

DELETE FROM `infra_api_error_log` WHERE `id` = 15624;

DELETE FROM `infra_api_error_log` WHERE `id` = 15625;

DELETE FROM `infra_api_error_log` WHERE `id` = 15626;

DELETE FROM `infra_api_error_log` WHERE `id` = 15627;

DELETE FROM `infra_api_error_log` WHERE `id` = 15628;

DELETE FROM `infra_api_error_log` WHERE `id` = 15629;

DELETE FROM `infra_api_error_log` WHERE `id` = 15630;

DELETE FROM `infra_api_error_log` WHERE `id` = 15631;

DELETE FROM `infra_api_error_log` WHERE `id` = 15632;

DELETE FROM `infra_api_error_log` WHERE `id` = 15633;

DELETE FROM `infra_api_error_log` WHERE `id` = 15634;

DELETE FROM `infra_api_error_log` WHERE `id` = 15635;

DELETE FROM `infra_api_error_log` WHERE `id` = 15636;

DELETE FROM `infra_api_error_log` WHERE `id` = 15637;

DELETE FROM `infra_api_error_log` WHERE `id` = 15638;

DELETE FROM `infra_api_error_log` WHERE `id` = 15639;

DELETE FROM `infra_api_error_log` WHERE `id` = 15640;

DELETE FROM `infra_api_error_log` WHERE `id` = 15641;

DELETE FROM `infra_api_error_log` WHERE `id` = 15642;

DELETE FROM `infra_api_error_log` WHERE `id` = 15643;

DELETE FROM `infra_api_error_log` WHERE `id` = 15644;

DELETE FROM `infra_api_error_log` WHERE `id` = 15645;

DELETE FROM `infra_api_error_log` WHERE `id` = 15646;

DELETE FROM `infra_api_error_log` WHERE `id` = 15647;

DELETE FROM `infra_api_error_log` WHERE `id` = 15648;

DELETE FROM `infra_api_error_log` WHERE `id` = 15649;

DELETE FROM `infra_api_error_log` WHERE `id` = 15650;

DELETE FROM `infra_api_error_log` WHERE `id` = 15651;

DELETE FROM `infra_api_error_log` WHERE `id` = 15652;

DELETE FROM `infra_api_error_log` WHERE `id` = 15653;

DELETE FROM `infra_api_error_log` WHERE `id` = 15654;

DELETE FROM `infra_api_error_log` WHERE `id` = 15655;

DELETE FROM `infra_api_error_log` WHERE `id` = 15656;

DELETE FROM `infra_api_error_log` WHERE `id` = 15657;

DELETE FROM `infra_api_error_log` WHERE `id` = 15658;

DELETE FROM `infra_api_error_log` WHERE `id` = 15659;

DELETE FROM `infra_api_error_log` WHERE `id` = 15660;

DELETE FROM `infra_api_error_log` WHERE `id` = 15661;

DELETE FROM `infra_api_error_log` WHERE `id` = 15662;

DELETE FROM `infra_api_error_log` WHERE `id` = 15663;

DELETE FROM `infra_api_error_log` WHERE `id` = 15664;

DELETE FROM `infra_api_error_log` WHERE `id` = 15665;

DELETE FROM `infra_api_error_log` WHERE `id` = 15666;

DELETE FROM `infra_api_error_log` WHERE `id` = 15667;

DELETE FROM `infra_api_error_log` WHERE `id` = 15668;

DELETE FROM `infra_api_error_log` WHERE `id` = 15669;

DELETE FROM `infra_api_error_log` WHERE `id` = 15670;

DELETE FROM `infra_api_error_log` WHERE `id` = 15671;

DELETE FROM `infra_api_error_log` WHERE `id` = 15672;

DELETE FROM `infra_api_error_log` WHERE `id` = 15673;

DELETE FROM `infra_api_error_log` WHERE `id` = 15674;

DELETE FROM `infra_api_error_log` WHERE `id` = 15675;

DELETE FROM `infra_api_error_log` WHERE `id` = 15676;

DELETE FROM `infra_api_error_log` WHERE `id` = 15677;

DELETE FROM `infra_api_error_log` WHERE `id` = 15678;

DELETE FROM `infra_api_error_log` WHERE `id` = 15679;

DELETE FROM `infra_api_error_log` WHERE `id` = 15680;

DELETE FROM `infra_api_error_log` WHERE `id` = 15681;

DELETE FROM `infra_api_error_log` WHERE `id` = 15682;

DELETE FROM `infra_api_error_log` WHERE `id` = 15683;

DELETE FROM `infra_api_error_log` WHERE `id` = 15684;

DELETE FROM `infra_api_error_log` WHERE `id` = 15685;

DELETE FROM `infra_api_error_log` WHERE `id` = 15686;

DELETE FROM `infra_api_error_log` WHERE `id` = 15687;

DELETE FROM `infra_api_error_log` WHERE `id` = 15688;

DELETE FROM `infra_api_error_log` WHERE `id` = 15689;

DELETE FROM `infra_api_error_log` WHERE `id` = 15690;

DELETE FROM `infra_api_error_log` WHERE `id` = 15691;

DELETE FROM `infra_api_error_log` WHERE `id` = 15692;

DELETE FROM `infra_api_error_log` WHERE `id` = 15693;

DELETE FROM `infra_api_error_log` WHERE `id` = 15694;

DELETE FROM `infra_api_error_log` WHERE `id` = 15695;

DELETE FROM `infra_api_error_log` WHERE `id` = 15696;

DELETE FROM `infra_api_error_log` WHERE `id` = 15697;

DELETE FROM `infra_api_error_log` WHERE `id` = 15698;

DELETE FROM `infra_api_error_log` WHERE `id` = 15699;

DELETE FROM `infra_api_error_log` WHERE `id` = 15700;

DELETE FROM `infra_api_error_log` WHERE `id` = 15701;

DELETE FROM `infra_api_error_log` WHERE `id` = 15702;

DELETE FROM `infra_api_error_log` WHERE `id` = 15703;

DELETE FROM `infra_api_error_log` WHERE `id` = 15704;

DELETE FROM `infra_api_error_log` WHERE `id` = 15705;

DELETE FROM `infra_api_error_log` WHERE `id` = 15706;

DELETE FROM `infra_api_error_log` WHERE `id` = 15707;

DELETE FROM `infra_api_error_log` WHERE `id` = 15708;

DELETE FROM `infra_api_error_log` WHERE `id` = 15709;

DELETE FROM `infra_api_error_log` WHERE `id` = 15710;

DELETE FROM `infra_api_error_log` WHERE `id` = 15711;

DELETE FROM `infra_api_error_log` WHERE `id` = 15712;

DELETE FROM `infra_api_error_log` WHERE `id` = 15713;

DELETE FROM `infra_api_error_log` WHERE `id` = 15714;

DELETE FROM `infra_api_error_log` WHERE `id` = 15715;

DELETE FROM `infra_api_error_log` WHERE `id` = 15716;

DELETE FROM `infra_api_error_log` WHERE `id` = 15717;

DELETE FROM `infra_api_error_log` WHERE `id` = 15718;

DELETE FROM `infra_api_error_log` WHERE `id` = 15719;

DELETE FROM `infra_api_error_log` WHERE `id` = 15720;

DELETE FROM `infra_api_error_log` WHERE `id` = 15721;

DELETE FROM `infra_api_error_log` WHERE `id` = 15722;

DELETE FROM `infra_api_error_log` WHERE `id` = 15723;

DELETE FROM `infra_api_error_log` WHERE `id` = 15724;

DELETE FROM `infra_api_error_log` WHERE `id` = 15725;

DELETE FROM `infra_api_error_log` WHERE `id` = 15726;

DELETE FROM `infra_api_error_log` WHERE `id` = 15727;

DELETE FROM `infra_api_error_log` WHERE `id` = 15728;

DELETE FROM `infra_api_error_log` WHERE `id` = 15729;

DELETE FROM `infra_api_error_log` WHERE `id` = 15730;

DELETE FROM `infra_api_error_log` WHERE `id` = 15731;

DELETE FROM `infra_api_error_log` WHERE `id` = 15732;

DELETE FROM `infra_api_error_log` WHERE `id` = 15733;

DELETE FROM `infra_api_error_log` WHERE `id` = 15734;

DELETE FROM `infra_api_error_log` WHERE `id` = 15735;

DELETE FROM `infra_api_error_log` WHERE `id` = 15736;

DELETE FROM `infra_api_error_log` WHERE `id` = 15737;

DELETE FROM `infra_api_error_log` WHERE `id` = 15738;

DELETE FROM `infra_api_error_log` WHERE `id` = 15739;

DELETE FROM `infra_api_error_log` WHERE `id` = 15740;

DELETE FROM `infra_api_error_log` WHERE `id` = 15741;

DELETE FROM `infra_api_error_log` WHERE `id` = 15742;

DELETE FROM `infra_api_error_log` WHERE `id` = 15743;

DELETE FROM `infra_api_error_log` WHERE `id` = 15744;

DELETE FROM `infra_api_error_log` WHERE `id` = 15745;

DELETE FROM `infra_api_error_log` WHERE `id` = 15746;

DELETE FROM `infra_api_error_log` WHERE `id` = 15747;

DELETE FROM `infra_api_error_log` WHERE `id` = 15748;

DELETE FROM `infra_api_error_log` WHERE `id` = 15749;

DELETE FROM `infra_api_error_log` WHERE `id` = 15750;

DELETE FROM `infra_api_error_log` WHERE `id` = 15751;

DELETE FROM `infra_api_error_log` WHERE `id` = 15752;

DELETE FROM `infra_api_error_log` WHERE `id` = 15753;

DELETE FROM `infra_api_error_log` WHERE `id` = 15754;

DELETE FROM `infra_api_error_log` WHERE `id` = 15755;

DELETE FROM `infra_api_error_log` WHERE `id` = 15756;

DELETE FROM `infra_api_error_log` WHERE `id` = 15757;

DELETE FROM `infra_api_error_log` WHERE `id` = 15758;

DELETE FROM `infra_api_error_log` WHERE `id` = 15759;

DELETE FROM `infra_api_error_log` WHERE `id` = 15760;

DELETE FROM `infra_api_error_log` WHERE `id` = 15761;

DELETE FROM `infra_api_error_log` WHERE `id` = 15762;

DELETE FROM `infra_api_error_log` WHERE `id` = 15763;

DELETE FROM `infra_api_error_log` WHERE `id` = 15764;

DELETE FROM `infra_api_error_log` WHERE `id` = 15765;

DELETE FROM `infra_api_error_log` WHERE `id` = 15766;

DELETE FROM `infra_api_error_log` WHERE `id` = 15767;

DELETE FROM `infra_api_error_log` WHERE `id` = 15768;

DELETE FROM `infra_api_error_log` WHERE `id` = 15769;

DELETE FROM `infra_api_error_log` WHERE `id` = 15770;

DELETE FROM `infra_api_error_log` WHERE `id` = 15771;

DELETE FROM `infra_api_error_log` WHERE `id` = 15772;

DELETE FROM `infra_api_error_log` WHERE `id` = 15773;

DELETE FROM `infra_api_error_log` WHERE `id` = 15774;

DELETE FROM `infra_api_error_log` WHERE `id` = 15775;

DELETE FROM `infra_api_error_log` WHERE `id` = 15776;

DELETE FROM `infra_api_error_log` WHERE `id` = 15777;

DELETE FROM `infra_api_error_log` WHERE `id` = 15778;

DELETE FROM `infra_api_error_log` WHERE `id` = 15779;

DELETE FROM `infra_api_error_log` WHERE `id` = 15780;

DELETE FROM `infra_api_error_log` WHERE `id` = 15781;

DELETE FROM `infra_api_error_log` WHERE `id` = 15782;

DELETE FROM `infra_api_error_log` WHERE `id` = 15783;

DELETE FROM `infra_api_error_log` WHERE `id` = 15784;

DELETE FROM `infra_api_error_log` WHERE `id` = 15785;

DELETE FROM `infra_api_error_log` WHERE `id` = 15786;

DELETE FROM `infra_api_error_log` WHERE `id` = 15787;

DELETE FROM `infra_api_error_log` WHERE `id` = 15788;

DELETE FROM `infra_api_error_log` WHERE `id` = 15789;

DELETE FROM `infra_api_error_log` WHERE `id` = 15790;

DELETE FROM `infra_api_error_log` WHERE `id` = 15791;

DELETE FROM `infra_api_error_log` WHERE `id` = 15792;

DELETE FROM `infra_api_error_log` WHERE `id` = 15793;

DELETE FROM `infra_api_error_log` WHERE `id` = 15794;

DELETE FROM `infra_api_error_log` WHERE `id` = 15795;

DELETE FROM `infra_api_error_log` WHERE `id` = 15796;

DELETE FROM `infra_api_error_log` WHERE `id` = 15797;

DELETE FROM `infra_job_log` WHERE `id` = 5119;

DELETE FROM `infra_job_log` WHERE `id` = 5120;

DELETE FROM `infra_job_log` WHERE `id` = 5121;

DELETE FROM `infra_job_log` WHERE `id` = 5122;

DELETE FROM `infra_job_log` WHERE `id` = 5123;

INSERT INTO `infra_job_log`(`id`, `job_id`, `handler_name`, `handler_param`, `execute_index`, `begin_time`, `end_time`, `duration`, `status`, `result`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (5179, 32, 'saveModelUsageRecordsJob', '', 1, '2025-01-20 15:29:36', '2025-01-20 15:29:37', 836, 1, '*********** 保存模块使用记录完成，共保存了37条 ************', NULL, '2025-01-20 15:29:36', NULL, '2025-01-20 15:29:37', 0);

INSERT INTO `infra_job_log`(`id`, `job_id`, `handler_name`, `handler_param`, `execute_index`, `begin_time`, `end_time`, `duration`, `status`, `result`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (5180, 32, 'saveModelUsageRecordsJob', '', 1, '2025-01-20 15:30:00', '2025-01-20 15:30:01', 475, 1, '*********** 保存模块使用记录完成，共保存了37条 ************', NULL, '2025-01-20 15:30:00', NULL, '2025-01-20 15:30:01', 0);

DELETE FROM `lowcode_dbform` WHERE `id` = 1872882476103290882;

INSERT INTO `lowcode_dbform`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `table_name`, `table_describe`, `table_type`, `table_classify`, `table_id_type`, `table_select`, `is_db_sync`, `is_des_form`, `sub_table_mapping`, `sub_table_sort`, `sub_table_title`, `theme_template`, `desform_web_id`, `tree_style`, `tree_mode`, `tree_label_field`, `operate_menu_style`, `form_style`, `sub_table_list_str`, `view_default_field`, `group_dbform_id`, `orderby_config`, `where_config`, `data_config`, `basic_function`, `basic_config`, `table_config`, `data_sources_config`, `table_style`) VALUES (1849365535287058433, 1, 1, '2024-10-24 16:21:18', 101, 1, '2024-10-29 14:42:28', 0, 'example_demo', '测试表', 1, 1, 'NATIVE', 'multiple', 'N', 'N', '', 0, '', 'normal', NULL, 'default', 'default', '', 'more', 2, '', 'N', NULL, '[{\"order\":\"desc\",\"column\":\"id\"}]', NULL, 'page,initDataReq,authFalse', 'addBtn,editBtn,viewBtn,delBtn,batchDelBtn,importBtn,exportBtn', '', 'height,header,menu,index,border,rollBottom', '', '{\"singleStyle\":\"default\",\"singleCardSpan\":\"\",\"expandShowNum\":null,\"expandMode\":\"\",\"searchStyle\":\"default\"}');

UPDATE `lowcode_dbform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:06', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `table_name` = 'example_order_record', `table_describe` = '订单详情-操作记录', `table_type` = 1, `table_classify` = 1, `table_id_type` = 'NATIVE', `table_select` = 'multiple', `is_db_sync` = 'Y', `is_des_form` = 'N', `sub_table_mapping` = '', `sub_table_sort` = 0, `sub_table_title` = '', `theme_template` = 'normal', `desform_web_id` = NULL, `tree_style` = 'default', `tree_mode` = 'default', `tree_label_field` = '', `operate_menu_style` = 'more', `form_style` = 2, `sub_table_list_str` = '', `view_default_field` = 'N', `group_dbform_id` = NULL, `orderby_config` = '[{\"order\":\"desc\",\"column\":\"id\"}]', `where_config` = NULL, `data_config` = 'initDataReq,authFalse', `basic_function` = 'addBtn,editBtn,viewBtn,delBtn,batchDelBtn,importBtn,exportBtn', `basic_config` = '', `table_config` = 'stripe', `data_sources_config` = '', `table_style` = '{\"singleStyle\":\"default\",\"singleCardSpan\":\"\",\"expandShowNum\":null,\"expandMode\":\"\",\"searchStyle\":\"default\"}' WHERE `id` = 1836318516001189890;

UPDATE `lowcode_dbform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 09:56:43', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:42:39', `is_deleted` = 0, `table_name` = 'example_order_pending_orders', `table_describe` = '订单管理-待处理订单', `table_type` = 1, `table_classify` = 1, `table_id_type` = 'NATIVE', `table_select` = 'multiple', `is_db_sync` = 'Y', `is_des_form` = 'N', `sub_table_mapping` = '', `sub_table_sort` = 0, `sub_table_title` = '', `theme_template` = 'normal', `desform_web_id` = NULL, `tree_style` = 'default', `tree_mode` = 'default', `tree_label_field` = '', `operate_menu_style` = 'more', `form_style` = 2, `sub_table_list_str` = '', `view_default_field` = 'N', `group_dbform_id` = NULL, `orderby_config` = '[{\"order\":\"desc\",\"column\":\"id\"}]', `where_config` = NULL, `data_config` = 'page,initDataReq,authFalse', `basic_function` = 'addBtn,editBtn,viewBtn,delBtn,batchDelBtn,importBtn,exportBtn', `basic_config` = '', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '', `table_style` = '{\"singleStyle\":\"default\",\"singleCardSpan\":\"\",\"expandShowNum\":null,\"expandMode\":\"\",\"searchStyle\":\"default\"}' WHERE `id` = 1847456813371498498;

UPDATE `lowcode_dbform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-28 10:17:57', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-29 17:22:17', `is_deleted` = 0, `table_name` = 'example_main_date', `table_describe` = '日程管理', `table_type` = 3, `table_classify` = 1, `table_id_type` = 'NATIVE', `table_select` = 'multiple', `is_db_sync` = 'Y', `is_des_form` = 'N', `sub_table_mapping` = '', `sub_table_sort` = 0, `sub_table_title` = '', `theme_template` = 'normal', `desform_web_id` = NULL, `tree_style` = 'default', `tree_mode` = 'default', `tree_label_field` = '', `operate_menu_style` = 'normal', `form_style` = 2, `sub_table_list_str` = 'example_sub_more_date', `view_default_field` = 'N', `group_dbform_id` = NULL, `orderby_config` = '[{\"order\":\"desc\",\"column\":\"id\"}]', `where_config` = NULL, `data_config` = 'page,initDataReq,authFalse', `basic_function` = 'addBtn,editBtn,delBtn', `basic_config` = '', `table_config` = 'height,header,menu,index,border,rollBottom', `data_sources_config` = '', `table_style` = '{\"singleStyle\":\"default\",\"singleCardSpan\":\"\",\"expandShowNum\":null,\"expandMode\":\"\",\"searchStyle\":\"default\"}' WHERE `id` = 1850723645460291586;

UPDATE `lowcode_dbform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-11-07 09:52:01', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-14 17:27:24', `is_deleted` = 0, `table_name` = 'example_mkgl_dlrz', `table_describe` = '模块管理-登录日志', `table_type` = 1, `table_classify` = 1, `table_id_type` = 'NATIVE', `table_select` = 'multiple', `is_db_sync` = 'Y', `is_des_form` = 'N', `sub_table_mapping` = '', `sub_table_sort` = 0, `sub_table_title` = '', `theme_template` = 'normal', `desform_web_id` = NULL, `tree_style` = 'default', `tree_mode` = 'default', `tree_label_field` = '', `operate_menu_style` = 'normal', `form_style` = 2, `sub_table_list_str` = '', `view_default_field` = 'N', `group_dbform_id` = NULL, `orderby_config` = '[{\"order\":\"desc\",\"column\":\"id\"}]', `where_config` = NULL, `data_config` = 'page,initDataReq,authFalse', `basic_function` = 'viewBtn,exportBtn', `basic_config` = 'return {\n    option_str:`return {\n}`,\n    custom_str: `return {\n  searchBtnText: \'查询\',\n  emptyBtnText: \'重置\',\n  searchMenuSpan: 4,\n  searchGutter: 20\n}`,\n  }', `table_config` = 'height,rollBottom,stripe,header', `data_sources_config` = '', `table_style` = '{\"singleStyle\":\"default\",\"singleCardSpan\":\"\",\"expandShowNum\":null,\"expandMode\":\"\",\"searchStyle\":\"default\"}' WHERE `id` = 1854341001851940865;

DELETE FROM `lowcode_dbform_button` WHERE `id` = 1854695849298472962;

DELETE FROM `lowcode_dbform_button` WHERE `id` = 1854695849306861570;

DELETE FROM `lowcode_dbform_button` WHERE `id` = 1854695849306861571;

DELETE FROM `lowcode_dbform_button` WHERE `id` = 1866661316941139970;

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1850083002149924866, 1, 1, '2024-10-26 15:52:15', 101, NULL, '2024-10-26 15:54:36', 1, 1849365535287058433, 'page', 'spring', 'studentAfterEnhancePage', '', 'Y', '', '0', 1);

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1850083737017200642, 1, 1, '2024-10-26 15:55:10', 101, NULL, '2024-10-26 15:55:28', 1, 1849365535287058433, 'page', 'spring', 'studentAfterEnhancePage', '', 'Y', '', '0', 1);

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1850086940156825602, 1, 1, '2024-10-26 16:07:54', 101, NULL, '2024-10-26 16:09:04', 1, 1849365535287058433, 'page', 'spring', ' ', '', 'Y', '', '0', 1);

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1850087121547890690, 1, 1, '2024-10-26 16:08:37', 101, NULL, '2024-10-26 16:09:33', 1, 1849365535287058433, 'page', 'spring', 'studentBeforeEnhancePage', '', 'Y', '', '0', 1);

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1850090927522209793, 1, 1, '2024-10-26 16:23:45', 101, NULL, '2024-10-26 16:24:00', 1, 1849365535287058433, 'page', 'spring', 'exampleXtsyFormReportEnhance', '', 'Y', '', '0', 1);

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1879373748996554753, 1, 1, '2025-01-15 11:43:13', 101, NULL, NULL, 0, 1833031105253642242, 'add', 'spring', 'studentBeforeEnhanceAdd', '', 'Y', '', '0', 1);

INSERT INTO `lowcode_dbform_enhance_java`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `button_code`, `java_type`, `java_class_url`, `online_script`, `active_status`, `remark`, `list_result_handle_type`, `sort`) VALUES (1879374015418744834, 1, 1, '2025-01-15 11:44:17', 101, NULL, NULL, 0, 1833031105253642242, 'edit', 'spring', 'studentBeforeEnhanceEdit', '', 'Y', '', '0', 1);

DELETE FROM `lowcode_dbform_enhance_js` WHERE `id` = 1854695848728047618;

DELETE FROM `lowcode_dbform_enhance_js` WHERE `id` = 1854695848728047619;

DELETE FROM `lowcode_dbform_enhance_js` WHERE `id` = 1856505240385429506;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506882;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506883;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506884;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506885;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506886;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506887;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506888;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506889;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506890;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506891;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506892;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506893;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506894;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506895;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1846853873040506896;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1848263863924097025;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1848268293314641922;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920833;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920834;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920835;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920836;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920837;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920838;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920839;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920840;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920841;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920842;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920843;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1852557613100920844;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615874;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615875;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615876;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615877;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615878;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615879;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615880;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615881;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615882;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615883;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1854695846689615884;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716673;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716674;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716675;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716676;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716677;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716678;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716679;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716680;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716681;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716682;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856505236786716683;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173506;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173507;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173508;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173509;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173510;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173511;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173512;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173513;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173514;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1856507606497173515;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565569;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565570;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565571;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565572;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565573;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565574;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565575;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565576;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565577;

DELETE FROM `lowcode_dbform_field` WHERE `id` = 1872882476195565578;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:06', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `field_name` = '操作详情', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'String', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 7, `is_db` = 'Y' WHERE `id` = 1836318518006067201;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'tenant_id', `field_name` = '租户编号', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 8, `is_db` = 'Y' WHERE `id` = 1836318518538743809;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_user', `field_name` = '创建人', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 9, `is_db` = 'Y' WHERE `id` = 1836318518807179266;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_time', `field_name` = '创建时间', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'DateTime', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 10, `is_db` = 'Y' WHERE `id` = 1836318519067226113;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_dept', `field_name` = '创建部门id', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 11, `is_db` = 'Y' WHERE `id` = 1836318519331467266;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'update_user', `field_name` = '更新人', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 12, `is_db` = 'Y' WHERE `id` = 1836318519599902721;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'update_time', `field_name` = '更新时间', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'DateTime', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 13, `is_db` = 'Y' WHERE `id` = 1836318519859949569;

UPDATE `lowcode_dbform_field` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:07', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'is_deleted', `field_name` = '是否删除', `field_len` = 2, `field_point_len` = 0, `field_default_val` = '0', `field_type` = 'Integer', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 14, `is_db` = 'Y' WHERE `id` = 1836318520128385025;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `field_name` = '下单时间', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'DateTime', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 4, `is_db` = 'Y' WHERE `id` = 1847456813769957380;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `field_name` = '主键', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'Y', `is_null` = 'N', `sort_num` = 1, `is_db` = 'Y' WHERE `id` = 1849365535660351489;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `field_name` = '姓名', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'String', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 2, `is_db` = 'Y' WHERE `id` = 1849365535660351490;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `field_name` = '年龄', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'Integer', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 3, `is_db` = 'Y' WHERE `id` = 1849365535660351491;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `field_name` = '租户编号', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 12, `is_db` = 'Y' WHERE `id` = 1849365535660351492;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `field_name` = '创建人', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 13, `is_db` = 'Y' WHERE `id` = 1849365535660351493;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `field_name` = '创建时间', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'DateTime', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 14, `is_db` = 'Y' WHERE `id` = 1849365535660351494;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `field_name` = '创建部门id', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 15, `is_db` = 'Y' WHERE `id` = 1849365535660351495;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `field_name` = '更新人', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 16, `is_db` = 'Y' WHERE `id` = 1849365535660351496;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `field_name` = '更新时间', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'DateTime', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 17, `is_db` = 'Y' WHERE `id` = 1849365535660351497;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `field_name` = '是否删除', `field_len` = 2, `field_point_len` = 0, `field_default_val` = '0', `field_type` = 'Integer', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 18, `is_db` = 'Y' WHERE `id` = 1849365535660351498;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:43:46', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `field_name` = 'aaa', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'BigInt', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 4, `is_db` = 'Y' WHERE `id` = 1850819895644786689;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `field_name` = 'bbb', `field_len` = 66, `field_point_len` = 29, `field_default_val` = '', `field_type` = 'BigDecimal', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 5, `is_db` = 'Y' WHERE `id` = 1851092531385925634;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:44:10', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `field_name` = 'ccc', `field_len` = 0, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'Date', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 6, `is_db` = 'Y' WHERE `id` = 1851092531385925635;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:45:40', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `field_name` = 'ddd', `field_len` = 0, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'Time', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 7, `is_db` = 'Y' WHERE `id` = 1851092531385925636;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:46:09', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `field_name` = 'eee', `field_len` = 0, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'DateTime', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 8, `is_db` = 'Y' WHERE `id` = 1851092531385925637;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:46:46', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `field_name` = 'fff', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'Text', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 9, `is_db` = 'Y' WHERE `id` = 1851092531385925638;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:11', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `field_name` = 'ggg', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'LongText', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 10, `is_db` = 'Y' WHERE `id` = 1851092531385925639;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 11:47:41', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `field_name` = 'hhh', `field_len` = 1, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'Blob', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 11, `is_db` = 'Y' WHERE `id` = 1851092531385925640;

UPDATE `lowcode_dbform_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `field_name` = '登录IP ', `field_len` = 128, `field_point_len` = 0, `field_default_val` = '', `field_type` = 'String', `field_remark` = '', `is_primary_key` = 'N', `is_null` = 'Y', `sort_num` = 9, `is_db` = 'Y' WHERE `id` = 1854344151786827779;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498114;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498115;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498116;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498117;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498118;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498119;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498120;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498121;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498122;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498123;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498124;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498125;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498126;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498127;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1846853874961498128;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1848263865606012930;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1848268294681985025;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786690;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786691;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786692;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786693;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786694;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786695;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786696;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786697;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786698;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786699;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786700;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1852557613788786701;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258945;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258946;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258947;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258948;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258949;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258950;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258951;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258952;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258953;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258954;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1854695847394258955;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108481;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108482;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108483;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108484;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108485;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108486;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108487;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108488;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108489;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108490;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856505237529108491;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881794;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881795;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881796;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881797;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881798;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881799;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881800;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881801;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881802;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1856507607742881803;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418113;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418114;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418115;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418116;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418117;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418118;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418119;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418120;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418121;

DELETE FROM `lowcode_dbform_field_dict` WHERE `id` = 1872882476451418122;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:08', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1836318522535915521;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1847456815221186565;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719874;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719875;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719876;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719877;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719878;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719879;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719880;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719881;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719882;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1849365536272719883;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:43:46', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1850819896626253825;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307329;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307330;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307331;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307332;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307333;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307334;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1851092532430307335;

UPDATE `lowcode_dbform_field_dict` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `dict_type` = '', `dict_code` = '', `dict_table` = '', `dict_text` = '', `dict_table_column` = '' WHERE `id` = 1854344155381346308;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035521;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035522;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035523;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035524;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035525;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035526;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035527;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035528;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035529;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035530;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035531;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035532;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035533;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035534;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1846853876333035535;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1848263865807339521;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1848268294837174273;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696193;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696194;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696195;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696196;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696197;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696198;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696199;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696200;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696201;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696202;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696203;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1852557614002696204;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002626;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002627;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002628;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002629;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002630;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002631;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002632;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002633;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002634;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002635;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1854695847583002636;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955970;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955971;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955972;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955973;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955974;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955975;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955976;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955977;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955978;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955979;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856505237935955980;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591937;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591938;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591939;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591940;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591941;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591942;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591943;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591944;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591945;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1856507608103591946;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972546;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972547;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972548;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972549;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972550;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972551;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972552;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972553;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972554;

DELETE FROM `lowcode_dbform_field_export` WHERE `id` = 1872882476484972555;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1836318526533087234;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1847456816236208133;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269249;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1849365536457269250;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1849365536457269251;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269252;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269253;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269254;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269255;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269256;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269257;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `is_export` = 'N', `import_example_txt` = '' WHERE `id` = 1849365536457269258;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:43:46', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1850819896789831682;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657346;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657347;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657348;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657349;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657350;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657351;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1851092532761657352;

UPDATE `lowcode_dbform_field_export` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `is_export` = 'Y', `import_example_txt` = '' WHERE `id` = 1854344156249567236;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990018;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990019;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990020;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990021;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990022;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990023;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990024;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990025;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990026;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990027;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990028;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990029;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990030;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990031;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1846853877691990032;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1848263866025443330;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1848268295000752130;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160129;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160130;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160131;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160132;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160133;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160134;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160135;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160136;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160137;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160138;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160139;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1852557614250160140;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940609;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940610;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940611;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940612;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940613;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940614;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940615;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940616;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940617;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940618;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1854695847775940619;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106626;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106627;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106628;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106629;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106630;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106631;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106632;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106633;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106634;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106635;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856505238414106636;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107778;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107779;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107780;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107781;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107782;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107783;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107784;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107785;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107786;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1856507608460107787;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944066;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944067;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944068;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944069;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944070;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944071;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944072;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944073;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944074;

DELETE FROM `lowcode_dbform_field_foreignkey` WHERE `id` = 1872882476505944075;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'id', `main_table` = '', `main_field` = '' WHERE `id` = 1836318528915451906;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'time', `main_table` = '', `main_field` = '' WHERE `id` = 1836318529183887362;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'manipulator', `main_table` = '', `main_field` = '' WHERE `id` = 1836318529448128513;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'order_status', `main_table` = '', `main_field` = '' WHERE `id` = 1836318529716563969;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'payment_status', `main_table` = '', `main_field` = '' WHERE `id` = 1836318529980805121;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'delivery_status', `main_table` = '', `main_field` = '' WHERE `id` = 1836318530240851970;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:09', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `main_table` = '', `main_field` = '' WHERE `id` = 1836318530509287426;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'tenant_id', `main_table` = '', `main_field` = '' WHERE `id` = 1836318531037769730;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_user', `main_table` = '', `main_field` = '' WHERE `id` = 1836318531302010881;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_time', `main_table` = '', `main_field` = '' WHERE `id` = 1836318531562057729;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_dept', `main_table` = '', `main_field` = '' WHERE `id` = 1836318531826298882;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'update_user', `main_table` = '', `main_field` = '' WHERE `id` = 1836318532090540034;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'update_time', `main_table` = '', `main_field` = '' WHERE `id` = 1836318532358975489;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'is_deleted', `main_table` = '', `main_field` = '' WHERE `id` = 1836318532623216642;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `main_table` = '', `main_field` = '' WHERE `id` = 1847456817255424004;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652801;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652802;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652803;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652804;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652805;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652806;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652807;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652808;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652809;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `main_table` = '', `main_field` = '' WHERE `id` = 1849365536616652810;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:43:46', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `main_table` = '', `main_field` = '' WHERE `id` = 1850819896936632322;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007362;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007363;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007364;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007365;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007366;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007367;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `main_table` = '', `main_field` = '' WHERE `id` = 1851092533093007368;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'id', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250433;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlsj', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250435;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'zdlx', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250437;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'llq', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250438;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'czxt', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250439;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'tenant_id', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250440;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'create_user', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250441;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'create_time', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250442;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'create_dept', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250443;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'update_user', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250444;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'update_time', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250445;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'is_deleted', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250446;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'sbmc', `main_table` = '', `main_field` = '' WHERE `id` = 1854341003391250447;

UPDATE `lowcode_dbform_field_foreignkey` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `main_table` = '', `main_field` = '' WHERE `id` = 1854344157256200195;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756610;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756611;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756612;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756613;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756614;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756615;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756616;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756617;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756618;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756619;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756620;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756621;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756622;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756623;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1846853880514756624;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1848263866432290818;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1848268295323713537;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893698;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893699;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893700;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893701;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893702;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893703;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893704;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893705;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893706;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893707;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893708;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1852557614740893709;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427969;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427970;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427971;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427972;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427973;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427974;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427975;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427976;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427977;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427978;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1854695848153427979;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900353;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900354;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900355;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900356;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900357;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900358;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900359;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900360;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900361;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900362;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856505239596900363;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499586;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499587;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499588;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499589;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499590;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499591;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499592;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499593;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499594;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1856507609202499595;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858625;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858626;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858627;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858628;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858629;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858630;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858631;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858632;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858633;

DELETE FROM `lowcode_dbform_field_query` WHERE `id` = 1872882476568858634;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'id', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318536863657986;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'time', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318537127899138;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'manipulator', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318537392140290;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'order_status', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318537660575746;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'payment_status', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318537929011202;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'delivery_status', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318538189058050;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318538461687809;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:11', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'tenant_id', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318538981781505;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318539246022657;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318539510263809;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'create_dept', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318539778699266;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'update_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318540047134721;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'update_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318540311375873;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:39:31', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'is_deleted', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1836318540579811329;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'id', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993090;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'bh', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993091;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'name', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993092;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993093;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'num', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993094;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'money', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993095;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'status', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993096;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'tenant_id', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993097;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'create_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993098;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'create_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993099;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'create_dept', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993100;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'update_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993101;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'update_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993102;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'is_deleted', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1847456819339993103;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585730;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `query_is_db` = 'Y', `query_is_web` = 'Y', `query_mode` = 'LIKE', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585731;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `query_is_db` = 'Y', `query_is_web` = 'Y', `query_mode` = 'LIKE', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585732;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585733;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585734;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585735;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585736;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585737;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585738;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1849365536960585739;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1850819897255399426;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678914;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678915;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678916;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678917;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678918;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678919;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 14:42:28', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1851092533776678920;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'id', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846657;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlsj', `query_is_db` = 'Y', `query_is_web` = 'Y', `query_mode` = 'RANGE', `query_config` = 'return {\n    option_str:`return {\n    \"searchLabelWidth\":1,\n    \"searchSpan\":4,\n    \"searchPlaceholder\":\'登录时间\',\n    \"searchOrder\":2,\n}`,\n    custom_str: `return {\n  searchLabel: \'\'\n}`,\n  }', `query_default_val` = '' WHERE `id` = 1854341003835846659;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'zdlx', `query_is_db` = 'Y', `query_is_web` = 'Y', `query_mode` = 'EQ', `query_config` = 'return {\n    option_str:`return {\n    \"searchLabelWidth\":1,\n    \"searchSpan\":4,\n    \"searchPlaceholder\":\'终端类型\',\n    \"searchOrder\":3,\n}`,\n    custom_str: `return {\n  searchLabel:\'\'\n}`,\n  }', `query_default_val` = '' WHERE `id` = 1854341003835846661;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'llq', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846662;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'czxt', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = 'return {\n    option_str:`return {\n}`,\n    custom_str: `return {\n  \n}`,\n  }', `query_default_val` = '' WHERE `id` = 1854341003835846663;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'tenant_id', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846664;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'create_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846665;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'create_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846666;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'create_dept', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846667;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'update_user', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846668;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'update_time', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846669;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'is_deleted', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854341003835846670;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'sbmc', `query_is_db` = 'Y', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = 'return {\n    option_str:`return {\n}`,\n    custom_str: `return {\n}`,\n  }', `query_default_val` = '' WHERE `id` = 1854341003835846671;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'ss', `query_is_db` = 'Y', `query_is_web` = 'Y', `query_mode` = 'LIKE', `query_config` = 'return {\n    option_str:`return {\n    \"searchLabelWidth\":1,\n    \"searchSpan\":4,\n    \"searchPlaceholder\":\'输入搜索关键字\',\n    \"searchOrder\":4,\n}`,\n    custom_str: `return {\n  searchLabel: \'\'\n}`,\n  }', `query_default_val` = '' WHERE `id` = 1854344158988447745;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'macdz', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854344158988447746;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854344158988447747;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'szdq', `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'EQ', `query_config` = '', `query_default_val` = '' WHERE `id` = 1854344158988447748;

UPDATE `lowcode_dbform_field_query` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlry', `query_is_db` = 'Y', `query_is_web` = 'Y', `query_mode` = 'LIKE', `query_config` = 'return {\n    option_str:`return {\n    \"searchLabelWidth\":1,\n    \"searchSpan\":4,\n    \"searchPlaceholder\":\'登录人员\',\n    \"searchOrder\":1,\n}`,\n    custom_str: `return {\n  searchLabel: \'\'\n}`,\n  }', `query_default_val` = '' WHERE `id` = 1854344158988447749;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110338;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110339;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110340;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110341;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110342;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110343;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110344;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110345;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110346;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110347;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110348;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110349;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110350;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110351;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1846853879076110352;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1848263866226769921;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1848268295160135682;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624066;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624067;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624068;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624069;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624070;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624071;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624072;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624073;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624074;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624075;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624076;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1852557614497624077;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489986;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489987;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489988;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489989;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489990;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489991;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489992;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489993;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489994;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489995;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1854695847960489996;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389570;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389571;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389572;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389573;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389574;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389575;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389576;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389577;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389578;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389579;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856505239089389580;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595138;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595139;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595140;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595141;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595142;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595143;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595144;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595145;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595146;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1856507608837595147;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109890;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109891;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109892;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109893;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109894;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109895;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109896;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109897;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109898;

DELETE FROM `lowcode_dbform_field_web` WHERE `id` = 1872882476531109899;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:36', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'tenant_id', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801478;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:36', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'create_user', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801479;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:37', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'create_time', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801480;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:37', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'create_dept', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'deptSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801481;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:37', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'update_user', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801482;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:37', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'update_time', `is_db_select` = '', `is_show_list` = '', `is_show_form` = '', `is_show_column` = '', `is_show_sort` = '', `is_required` = '', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = '', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801483;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-14 09:23:37', `is_deleted` = 0, `dbform_id` = 1833031105253642242, `field_code` = 'is_deleted', `is_db_select` = '', `is_show_list` = '', `is_show_form` = '', `is_show_column` = '', `is_show_sort` = '', `is_required` = '', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = '', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1833031112941801484;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-09-12 11:23:24', `is_deleted` = 0, `dbform_id` = 1834065859105136642, `field_code` = 'tenant_id', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834065859612647430;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-09-12 11:23:24', `is_deleted` = 0, `dbform_id` = 1834065859105136642, `field_code` = 'create_dept', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'deptSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834065859612647433;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-09-12 11:23:24', `is_deleted` = 0, `dbform_id` = 1834065859105136642, `field_code` = 'update_user', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834065859612647434;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-09-12 11:23:24', `is_deleted` = 0, `dbform_id` = 1834065859105136642, `field_code` = 'update_time', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834065859612647435;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'tenant_id', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516866;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'create_user', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516867;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'create_time', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516868;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'create_dept', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'deptSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516869;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'update_user', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516870;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'update_time', `is_db_select` = '', `is_show_list` = '', `is_show_form` = '', `is_show_column` = '', `is_show_sort` = '', `is_required` = '', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = '', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516871;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = 1, `update_time` = '2024-09-13 17:30:13', `is_deleted` = 0, `dbform_id` = 1834136609367400450, `field_code` = 'is_deleted', `is_db_select` = '', `is_show_list` = '', `is_show_form` = '', `is_show_column` = '', `is_show_sort` = '', `is_required` = '', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = '', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1834136621652516872;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:10', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1836318534477099010;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1847456818291417092;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619266;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619267;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619268;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619269;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619270;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619271;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'deptSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619272;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'userSelect', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619273;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'date', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619274;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `is_db_select` = 'Y', `is_show_list` = 'N', `is_show_form` = 'N', `is_show_column` = 'N', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1849365536788619275;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:43:46', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1850819897091821569;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745985;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745986;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745987;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745988;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745989;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745990;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1851092533432745991;

UPDATE `lowcode_dbform_field_web` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `is_db_select` = 'Y', `is_show_list` = 'Y', `is_show_form` = 'Y', `is_show_column` = 'Y', `is_show_sort` = 'N', `is_required` = 'N', `control_type` = 'input', `controls_config` = '', `cell_width` = '', `cell_width_type` = 'min', `verify_config` = '', `format_config` = '{\"formatType\":\"\",\"formatJson\":{\"sql\":{},\"java\":{},\"fun\":\"\"}}' WHERE `id` = 1854344158111838212;

INSERT INTO `lowcode_dbform_role_field`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `dbform_id`, `field_code`, `list_is_view`, `form_is_view`, `form_is_edit`, `enable_state`) VALUES (1873568108765057025, 158, 1, '2024-12-30 11:13:41', 101, 1, '2024-12-30 11:13:58', 1, 1847167244620152834, 'id', NULL, NULL, NULL, NULL);

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819970;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819971;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819972;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819973;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819974;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819975;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819976;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819977;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819978;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819979;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819980;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819981;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819982;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819983;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1846853881940819984;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1848263868122595329;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1848268296795914242;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637377;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637378;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637379;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637380;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637381;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637382;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637383;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637384;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637385;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637386;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637387;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1852557614929637388;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560257;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560258;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560259;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560260;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560261;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560262;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560263;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560264;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560265;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560266;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1854695848350560267;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364289;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364290;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364291;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364292;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364293;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364294;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364295;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364296;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364297;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364298;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856505239844364299;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194561;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194562;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194563;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194564;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194565;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194566;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194567;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194568;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194569;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1856507610016194570;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413058;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413059;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413060;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413061;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413062;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413063;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413064;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413065;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413066;

DELETE FROM `lowcode_dbform_summary` WHERE `id` = 1872882476602413067;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-18 16:17:12', `create_dept` = 101, `update_user` = NULL, `update_time` = '2025-01-06 17:29:49', `is_deleted` = 0, `dbform_id` = 1836318516001189890, `field_code` = 'views', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1836318542437888001;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-06 17:42:35', `is_deleted` = 0, `dbform_id` = 1847456813371498498, `field_code` = 'xdsj', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1847456820375986181;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'id', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466753;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'name', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466754;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'age', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466755;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'tenant_id', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466756;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_user', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466757;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_time', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466758;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'create_dept', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466759;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_user', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466760;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'update_time', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466761;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'is_deleted', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1849365537195466762;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2024-10-29 10:43:47', `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'aaa', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1850819897800658945;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'bbb', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687169;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ccc', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687170;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ddd', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687171;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'eee', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687172;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'fff', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687173;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'ggg', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687174;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 0, `dbform_id` = 1849365535287058433, `field_code` = 'hhh', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1851092534359687175;

UPDATE `lowcode_dbform_summary` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-14 17:27:08', `is_deleted` = 0, `dbform_id` = 1854341001851940865, `field_code` = 'dlip', `summary_type` = 'bottom', `summary_show` = 'N', `summary_sql` = '', `summary_label` = '', `summary_json` = '{\"sqlType\":\"\",\"sqlValue\":\"\"}' WHERE `id` = 1854344160179630084;

INSERT INTO `lowcode_desform`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `desform_name`, `desform_json`, `group_desform_id`, `is_open`, `is_template`) VALUES (1820725669344575489, 1, 1, '2024-08-06 15:36:42', 101, 1, '2025-01-16 16:36:24', 0, '开发测试', '{\"jsEnhance\":\"return {\\n  afterSubmit(data) { //提交数据后触发\\n    return new Promise((resolve) => {\\n      console.log(\'===afterSubmit===\', data)\\n      resolve(true)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1820725669344575489 {\\n  //样式请写在当前位置内\\n}\",\"labelPosition\":\"right\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"column\":{\"name\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"姓名\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":12,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"value\":\"11\"},\"age\":{\"type\":\"number\",\"controlType\":\"input\",\"label\":\"年龄\",\"readonly\":false,\"controls\":true,\"controlsPosition\":\"\",\"textPosition\":\"left\",\"display\":true,\"span\":12,\"disabled\":false,\"required\":true,\"hideLabel\":false,\"value\":11},\"fields_4037243\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"审核人\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":12,\"disabled\":false,\"required\":false,\"hideLabel\":false},\"explain\":{\"type\":\"textarea\",\"controlType\":\"input\",\"label\":\"申请说明\",\"readonly\":false,\"minRows\":3,\"maxRows\":5,\"display\":true,\"span\":24,\"disabled\":false,\"required\":false,\"hideLabel\":false}},\"tableDesignId\":\"1876555399959760897\",\"isSubmitTable\":true,\"group\":[]}', 1820725650445041666, 'N', 'N');

INSERT INTO `lowcode_desform`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `desform_name`, `desform_json`, `group_desform_id`, `is_open`, `is_template`) VALUES (1881157513604263938, 1, 1, '2025-01-20 09:51:16', 101, 1, '2025-01-20 10:41:11', 0, '表单设计增强演示', '{\"jsEnhance\":\"return {\\n  afterSubmit(data) { //提交数据后触发\\n    return new Promise((resolve) => {\\n      useFun.requestApi(\'post\', \'/jeelowcode/dbform-data/save/1881167517744922626\', {\\n        data\\n      }).then(res => {\\n        message.success(\'表2添加成功\')\\n      })\\n\\n      resolve(true)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1881157513604263938 {\\n  //样式请写在当前位置内\\n}\",\"labelPosition\":\"right\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":true,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"isSubmitTable\":true,\"column\":{\"name\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"姓名\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":12,\"disabled\":false,\"required\":false,\"hideLabel\":false}},\"group\":[]}', NULL, 'N', 'N');

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-09-25 13:59:35', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-30 10:08:17', `is_deleted` = 0, `desform_name` = '通用页面-高级详情页-顶部信息', `desform_json` = '{\"jsEnhance\":\"return {\\n}\\n\",\"scssEnhance\":\".low-form__1838820624684339201 {\\n  padding: 40px 40px 30px 40px !important;\\n  background-color: white;\\n  border: 1px solid #e4e7ed;\\n  border-radius: 4px 4px 0 0;\\n  border-bottom: none;\\n\\n  .el-form {\\n    .el-row {\\n      border: none !important;\\n      margin: 0 !important;\\n    }\\n  }\\n\\n  .location_box {\\n    .el-form-item {\\n      position: absolute;\\n      width: 100%;\\n      left: 0;\\n      top: 0;\\n\\n      .el-form-item__content {\\n        padding: 0 20px;\\n      }\\n    }\\n\\n  }\\n\\n  .btn_group_class {\\n    text-align: center;\\n\\n    .el-button {\\n      .mr-3px {\\n        width: 0;\\n        margin-right: 0;\\n      }\\n    }\\n  }\\n\\n  .tabs_title_class,\\n  .btn_group_class {\\n    height: 60px;\\n  }\\n\\n  .system_number_class,\\n  .type_of_industry_class,\\n  .customer_source_class,\\n  .khzt_class,\\n  .khxj_class,\\n  .create_date_class,\\n  .head_contact_class,\\n  .vesting_officer_class {\\n    height: 50px;\\n    display: flex;\\n    align-items: center;\\n  }\\n\\n  .tabs_title_class,\\n  .btn_group_class,\\n  .system_number_class,\\n  .type_of_industry_class,\\n  .customer_source_class,\\n  .khzt_class,\\n  .khxj_class,\\n  .create_date_class,\\n  .head_contact_class,\\n  .vesting_officer_class {\\n    border: none !important;\\n\\n    .el-form-item {\\n      width: 100%;\\n    }\\n\\n    .el-input__wrapper,\\n    .el-select__selection {\\n      font-size: 14px;\\n    }\\n\\n    .el-select__selected-item {\\n      color: #666666 !important;\\n    }\\n\\n    .el-form-item__label {\\n      background-color: white;\\n      height: auto;\\n      font-weight: 700;\\n      font-size: 13px;\\n      color: #999999;\\n      font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n\\n      &::after {\\n        content: \':\'\\n      }\\n    }\\n\\n    .el-form-item__content {\\n      width: 100%;\\n      border: none;\\n      padding-left: 10px;\\n    }\\n  }\\n\\n  .tabs_title_class {\\n    .el-form-item {\\n      .el-form-item__content {\\n        padding-left: 0;\\n\\n        .avue-title {\\n          p {\\n            color: #666670;\\n            font-size: 22px;\\n            margin-left: 0;\\n          }\\n        }\\n      }\\n\\n\\n    }\\n  }\\n\\n  .khzt_class {\\n    .el-select__selected-item {\\n      font-size: 14px;\\n      text-align: center;\\n    }\\n  }\\n\\n  .khxj_class {\\n    .el-rate {\\n      justify-content: center;\\n    }\\n  }\\n\\n  .khzt_class,\\n  .khxj_class {\\n    .el-form-item__label {\\n      border-bottom: none !important;\\n      display: block;\\n      text-align: center !important;\\n      padding-left: 12px !important;\\n\\n      &::after {\\n        content: \'\'\\n      }\\n    }\\n  }\\n}\",\"labelPosition\":\"right\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"column\":{\"tabs_title\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"\",\"display\":true,\"span\":8,\"hideLabel\":true,\"styles\":{},\"className\":\"tabs_title_class\"},\"btn_group\":{\"type\":\"buttonList\",\"controlType\":\"\",\"label\":\"按钮组\",\"params\":{\"buttonList\":[{\"label\":\"主要操作\",\"prop\":\"master_btn\",\"id\":\"btn_881\",\"icon\":\"\",\"type\":\"primary\",\"display\":true,\"viewShow\":true,\"btnLoading\":true,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  type:\'primary\',\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    console.log(\'点击了按钮\')\\r\\n    if (obj.loading) obj.loading() //关闭loading方法\\r\\n  }\\r\\n}\\r\\n\"},{\"label\":\"辅助操作\",\"prop\":\"support_btn1\",\"id\":\"btn_3844\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":true,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    console.log(\'点击了按钮\')\\r\\n    if (obj.loading) obj.loading() //关闭loading方法\\r\\n  }\\r\\n}\\r\\n\",\"plain\":false,\"text\":false,\"round\":false,\"circle\":false,\"link\":false},{\"label\":\"辅助操作\",\"prop\":\"support_btn2\",\"id\":\"btn_7084\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":true,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  \\r\\n  handleClick: (obj) => {\\r\\n    console.log(\'点击了按钮\')\\r\\n    if (obj.loading) obj.loading() //关闭loading方法\\r\\n  }\\r\\n}\\r\\n\"}],\"location\":\"right\"},\"display\":true,\"span\":16,\"disabled\":false,\"hideLabel\":true,\"offset\":0,\"className\":\"btn_group_class\"},\"system_number\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"系统编号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":80,\"className\":\"system_number_class\"},\"type_of_industry\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"行业类型\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"type_of_industry\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"家用电器\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"交通运输\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"商务服务\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"家居用品\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"电工电气\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"6\",\"label\":\"数码产品\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"7\",\"label\":\"通信产品\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"8\",\"label\":\"办公文教\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"9\",\"label\":\"运动休闲\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"10\",\"label\":\"传媒广电\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"labelWidth\":80,\"className\":\"type_of_industry_class\"},\"customer_source\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"客户来源\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":5,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"crm_customer_source\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"促销\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"搜索引擎\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"广告\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"转介绍\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"线上注册\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"6\",\"label\":\"线上咨询\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"7\",\"label\":\"预约上门\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"8\",\"label\":\"陌拜\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"9\",\"label\":\"电话咨询\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"10\",\"label\":\"邮件咨询\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"labelWidth\":80,\"className\":\"customer_source_class\"},\"customer_status\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"客户状态\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":3,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"customer_status\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"了解产品\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"正在跟进\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"正在试用\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"准备购买\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"准备付款\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"6\",\"label\":\"已经购买\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"7\",\"label\":\"暂时搁置\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"labelWidth\":0,\"labelPosition\":\"top\",\"className\":\"khzt_class location_box\"},\"customer_rating\":{\"type\":\"rate\",\"controlType\":\"rate\",\"label\":\"客户星级\",\"texts\":[],\"colors\":[],\"display\":true,\"span\":4,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"value\":0,\"labelPosition\":\"top\",\"showScore\":false,\"showText\":false,\"allowHalf\":false,\"className\":\"khxj_class location_box\",\"offset\":0,\"labelWidth\":0},\"create_date\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"创建日期\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":80,\"value\":[],\"className\":\"create_date_class\"},\"head_contact\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"首联系人\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":80,\"className\":\"head_contact_class\"},\"vesting_officer\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"归属人员\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":5,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":80,\"className\":\"vesting_officer_class\"}},\"group\":[]}', `group_desform_id` = 1829756131023052802, `is_open` = 'N', `is_template` = 'N' WHERE `id` = 1838820624684339201;

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-11 09:26:50', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:36:00', `is_deleted` = 0, `desform_name` = '管理模块-订单详情-头部', `desform_json` = '{\"jsEnhance\":\"//初始化收货人表单\\nuseFun.controlInit(\'FormView\', \'formView_xgshr\', {\\n  formId: \'1834792963683192834\', //表单设计id\\n  formType: \'add\', //表单类型 add | edit | view\\n  handleType: \'default\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  defaultData: {\\n  }, //默认表单数据\\n  enhanceData: {\\n  }, //传递给表单js增强内部调用配置\\n  showButton: false,\\n  popOption: { //弹窗配置\\n    title: \'收货人信息\', //标题\\n    width: \'35%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n      {\\n        params: {}, //el-button 其他参数\\n        name: \'取消\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          //点击事件\\n          if (loading) loading() //关闭loading\\n          formView_xgshr.value.show = false\\n        }\\n      },\\n      {\\n        params: {\\n          type: \'primary\',\\n        }, //el-button 其他参数\\n        name: \'保存\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          message.success(\'保存成功\')\\n          //点击事件\\n          setTimeout(() => {\\n            if (loading) loading() //关闭loading\\n            formView_xgshr.value.show = false\\n          }, 500)\\n        }\\n      }\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {\\n    } //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    if (type == \'submit\') {\\n      if (obj.loading) obj.loading() //关闭loading方法\\n    }\\n    done()\\n  }\\n})\\n\\n//初始化发票表单\\nuseFun.controlInit(\'FormView\', \'formView_xgfp\', {\\n  formId: \'1834837596136083458\', //表单设计id\\n  formType: \'add\', //表单类型 add | edit | view\\n  handleType: \'default\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  defaultData: {}, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  showButton: false,\\n  popOption: { //弹窗配置\\n    title: \'修改发票\', //标题\\n    width: \'35%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n      {\\n        params: {}, //el-button 其他参数\\n        name: \'取消\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          //点击事件\\n          if (loading) loading() //关闭loading\\n          formView_xgfp.value.show = false\\n        }\\n      },\\n      {\\n        params: {\\n          type: \'primary\',\\n        }, //el-button 其他参数\\n        name: \'保存\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          message.success(\'保存成功\')\\n          //点击事件\\n          setTimeout(() => {\\n            if (loading) loading() //关闭loading\\n            formView_xgfp.value.show = false\\n          }, 500)\\n        }\\n      }\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {\\n    } //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    if (type == \'submit\') {\\n      if (obj.loading) obj.loading() //关闭loading方法\\n\\n    }\\n    done()\\n  }\\n})\\n\\n//初始化修改费用表单\\nuseFun.controlInit(\'FormView\', \'formView_xgfy\', {\\n  formId: \'1834868566172176386\', //表单设计id\\n  formType: \'add\', //表单类型 add | edit | view\\n  handleType: \'default\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  defaultData: props.defaultData, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  showButton: false,\\n  popOption: { //弹窗配置\\n    title: \'修改费用\', //标题\\n    width: \'35%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n      {\\n        params: {}, //el-button 其他参数\\n        name: \'取消\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          //点击事件\\n          if (loading) loading() //关闭loading\\n          formView_xgfy.value.show = false\\n        }\\n      },\\n      {\\n        params: {\\n          type: \'primary\',\\n        }, //el-button 其他参数\\n        name: \'保存\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          message.success(\'保存成功\')\\n          //点击事件\\n          setTimeout(() => {\\n            if (loading) loading() //关闭loading\\n            formView_xgfy.value.show = false\\n          }, 500)\\n        }\\n      }\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {\\n    } //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    if (type == \'submit\') {\\n      if (obj.loading) obj.loading() //关闭loading方法\\n\\n    }\\n    done()\\n  }\\n})\\n\\n//初始化备注订单表单\\nuseFun.controlInit(\'FormView\', \'formView_bzdd\', {\\n  formId: \'1834868736150540290\', //表单设计id\\n  formType: \'add\', //表单类型 add | edit | view\\n  handleType: \'default\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  defaultData: props.defaultData, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  showButton: false,\\n  popOption: { //弹窗配置\\n    title: \'修改费用\', //标题\\n    width: \'35%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n      {\\n        params: {}, //el-button 其他参数\\n        name: \'取消\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          //点击事件\\n          if (loading) loading() //关闭loading\\n          formView_bzdd.value.show = false\\n        }\\n      },\\n      {\\n        params: {\\n          type: \'primary\',\\n        }, //el-button 其他参数\\n        name: \'保存\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          message.success(\'保存成功\')\\n          //点击事件\\n          setTimeout(() => {\\n            if (loading) loading() //关闭loading\\n            formView_bzdd.value.show = false\\n          }, 500)\\n        }\\n      }\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {\\n    } //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    if (type == \'submit\') {\\n      if (obj.loading) obj.loading() //关闭loading方法\\n\\n    }\\n    done()\\n  }\\n})\\n\\n//初始化关闭订单表单\\nuseFun.controlInit(\'FormView\', \'formView_gbdd\', {\\n  formId: \'1836227753657548801\', //表单设计id\\n  formType: \'add\', //表单类型 add | edit | view\\n  handleType: \'default\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  defaultData: {}, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  showButton: false,\\n  popOption: { //弹窗配置\\n    title: \'修改费用\', //标题\\n    width: \'35%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n      {\\n        params: {}, //el-button 其他参数\\n        name: \'取消\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          //点击事件\\n          if (loading) loading() //关闭loading\\n          formView_gbdd.value.show = false\\n        }\\n      },\\n      {\\n        params: {\\n          type: \'primary\',\\n        }, //el-button 其他参数\\n        name: \'保存\', //按钮名称\\n        display: true, //是否显示\\n        loading: true, //点击时是否有loading\\n        icon: \'\', //图标\\n        clickFun: (loading) => {\\n          message.success(\'保存成功\')\\n          //点击事件\\n          setTimeout(() => {\\n            if (loading) loading() //关闭loading\\n            formView_gbdd.value.show = false\\n          }, 500)\\n        }\\n      }\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {\\n    } //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    if (type == \'submit\') {\\n      if (obj.loading) obj.loading() //关闭loading方法\\n\\n    }\\n    done()\\n  }\\n})\\n\\nconst { formView_xgshr, formView_xgfp, formView_xgfy, formView_bzdd, formView_gbdd } = Vue.toRefs(rendControlData.value)\\n\\n//打开按钮的表单\\nuseFun.openForm = (formView_value) => {\\n  formView_value.value.show = true //显示FormView\\n}\\n\\nreturn {\\n  initData(formData) {\\n    return new Promise(resolve => {\\n      if (formData.order_money) {\\n        let je = formData.order_money\\n        if (isNaN(parseFloat(je))) return false\\n        let newJe = Math.round(je * 100) / 100\\n        let newJeString = newJe.toString()\\n        let newJeLength = newJeString.indexOf(\'.\')\\n        if (newJeLength < 0) {\\n          newJeLength = newJeString.length;\\n          newJeString += \'.\'\\n        }\\n        while (newJeString.length <= newJeLength + 2) {\\n          newJeString += \'0\'\\n        }\\n        formData.order_money = \'￥\' + newJeString\\n      }\\n      resolve(formData)\\n    })\\n  },\\n}\\n\\n\\n\\n\",\"scssEnhance\":\".low-form__1844550190467956737 {\\r\\n  .el-form {\\r\\n    .el-row {\\r\\n      border: none !important;\\r\\n    }\\r\\n  }\\r\\n\\r\\n  .btn_group_class {\\r\\n\\r\\n    .el-form-item {\\r\\n      height: 60px;\\r\\n      line-height: 60px;\\r\\n    }\\r\\n\\r\\n    .el-button {\\r\\n      color: rgb(153, 153, 153);\\r\\n      font-size: 14px;\\r\\n    }\\r\\n  }\\r\\n\\r\\n\\r\\n  .tabs_class {\\r\\n    .form-tabs {\\r\\n      margin-top: 50px !important;\\r\\n      border-top: 1px solid #eee;\\r\\n\\r\\n      .el-tabs__header,\\r\\n      .el-col {\\r\\n        border-right: none !important;\\r\\n      }\\r\\n\\r\\n    }\\r\\n  }\\r\\n\\r\\n  .mode_distribution_class,\\r\\n  .order_type_class,\\r\\n  .order_source_class,\\r\\n  .update_date_class,\\r\\n  .mode_payment_class,\\r\\n  .payment_date_class {\\r\\n    height: 50px;\\r\\n    display: flex;\\r\\n    align-items: center;\\r\\n\\r\\n    .el-form-item {\\r\\n      width: 100%;\\r\\n    }\\r\\n  }\\r\\n\\r\\n\\r\\n  .order_bh_class,\\r\\n  .order_source_class,\\r\\n  .order_type_class,\\r\\n  .update_date_class,\\r\\n  .mode_payment_class,\\r\\n  .payment_date_class,\\r\\n  .mode_distribution_class {\\r\\n    .el-form-item__label::after {\\r\\n      content: \':\'\\r\\n    }\\r\\n  }\\r\\n\\r\\n\\r\\n  .order_bh_class,\\r\\n  .btn_group_class,\\r\\n  .mode_distribution_class,\\r\\n  .order_type_class,\\r\\n  .order_source_class,\\r\\n  .order_status_class,\\r\\n  .order_money_class,\\r\\n  .update_date_class,\\r\\n  .mode_payment_class,\\r\\n  .payment_date_class {\\r\\n    border: none !important;\\r\\n\\r\\n    .el-input__wrapper,\\r\\n    .el-select__selection {\\r\\n      font-size: 14px;\\r\\n    }\\r\\n\\r\\n    .el-select__selected-item {\\r\\n      color: #666666 !important;\\r\\n    }\\r\\n\\r\\n    .el-form-item__label {\\r\\n      background-color: white;\\r\\n      height: auto;\\r\\n      font-weight: 700;\\r\\n      font-size: 14px;\\r\\n      color: #999999;\\r\\n      font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\r\\n    }\\r\\n\\r\\n    .el-form-item__content {\\r\\n      border: none;\\r\\n      font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\r\\n      padding-left: 10px;\\r\\n    }\\r\\n  }\\r\\n\\r\\n  // 编号\\r\\n  .order_bh_class {\\r\\n    .el-form-item {\\r\\n      height: 60px;\\r\\n      line-height: 60px;\\r\\n\\r\\n      .el-form-item__label,\\r\\n      .el-form-item__content .el-input__inner {\\r\\n        font-size: 18px;\\r\\n        font-weight: 700;\\r\\n        color: rgb(102, 102, 102);\\r\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\r\\n      }\\r\\n\\r\\n      .el-form-item__label {\\r\\n        padding: 0 !important;\\r\\n        height: 60px;\\r\\n        line-height: 60px;\\r\\n      }\\r\\n\\r\\n      .el-form-item__content {\\r\\n        padding-left: 0;\\r\\n      }\\r\\n    }\\r\\n  }\\r\\n\\r\\n  // 状态\\r\\n  .order_status_class {\\r\\n    .el-form-item__label {\\r\\n      padding-left: 12px;\\r\\n      font-weight: 400;\\r\\n    }\\r\\n\\r\\n    .el-select__selected-item {\\r\\n      font-size: 20px;\\r\\n      text-align: center;\\r\\n    }\\r\\n  }\\r\\n\\r\\n  .order_money_class {\\r\\n    text-align: center;\\r\\n\\r\\n    .el-form-item__label {\\r\\n      font-weight: 400;\\r\\n    }\\r\\n\\r\\n    .el-input-group__prepend {\\r\\n      box-shadow: none;\\r\\n    }\\r\\n\\r\\n    .el-rate__icon {\\r\\n      font-size: 28px;\\r\\n    }\\r\\n\\r\\n    .el-form-item__content {\\r\\n      .el-input__inner {\\r\\n        font-size: 20px;\\r\\n        text-align: center;\\r\\n      }\\r\\n    }\\r\\n  }\\r\\n\\r\\n  .order_money_class,\\r\\n  .order_status_class {\\r\\n    .el-form-item {\\r\\n      position: absolute;\\r\\n      width: 100%;\\r\\n      left: 0;\\r\\n      top: 10px;\\r\\n      font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\r\\n\\r\\n      .el-form-item__content {\\r\\n        padding: 0;\\r\\n      }\\r\\n    }\\r\\n\\r\\n    .el-form-item__label {\\r\\n      display: block;\\r\\n      border-bottom: none !important;\\r\\n      text-align: center !important;\\r\\n      padding: 0 0 10px 0 !important;\\r\\n      margin-bottom: 0px;\\r\\n    }\\r\\n  }\\r\\n\\r\\n}\",\"labelPosition\":\"right\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"column\":{\"order_bh\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"订单编号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":8,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"offset\":0,\"labelWidth\":90,\"className\":\"order_bh_class\",\"labelPosition\":\"left\"},\"btn_group\":{\"type\":\"buttonList\",\"controlType\":\"\",\"label\":\"按钮组\",\"params\":{\"buttonList\":[{\"label\":\"修改收货人\",\"prop\":\"shr_btn\",\"id\":\"btn_881\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    //控件调用\\r\\n    const { formView_xgshr } = Vue.toRefs(rendControlData.value)\\r\\n    useFun.openForm(formView_xgshr)\\r\\n  }\\r\\n}\\r\\n\"},{\"label\":\"修改发票\",\"prop\":\"fp_btn\",\"id\":\"btn_3844\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_xgfp } = Vue.toRefs(rendControlData.value)\\r\\n    useFun.openForm(formView_xgfp)\\r\\n  }\\r\\n}\\r\\n\",\"plain\":false,\"text\":false,\"round\":false,\"circle\":false,\"link\":false},{\"label\":\"修改费用\",\"prop\":\"fy_btn\",\"id\":\"btn_7084\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_xgfy } = Vue.toRefs(rendControlData.value)\\r\\n    useFun.openForm(formView_xgfy)\\r\\n  }\\r\\n}\\r\\n\"},{\"label\":\"备注订单\",\"prop\":\"bzdd_btn\",\"id\":\"btn_5956\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_bzdd } = Vue.toRefs(rendControlData.value)\\r\\n    useFun.openForm(formView_bzdd)\\r\\n  }\\r\\n}\\r\\n\"},{\"label\":\"关闭订单\",\"prop\":\"gbdd_btn\",\"id\":\"btn_8480\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //其他配置请参考element-plus button配置\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_gbdd } = Vue.toRefs(rendControlData.value)\\r\\n    useFun.openForm(formView_gbdd)\\r\\n  }\\r\\n}\\r\\n\"}],\"location\":\"right\"},\"display\":true,\"span\":16,\"disabled\":false,\"hideLabel\":true,\"offset\":0,\"className\":\"btn_group_class\"},\"order_source\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"订单来源\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"app\",\"value\":\"1\"},{\"label\":\"小程序\",\"value\":\"2\"},{\"label\":\"web\",\"value\":\"3\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"codeDicData\":[],\"labelWidth\":90,\"className\":\"order_source_class\",\"dicCode\":\"order_source\"},\"order_type\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"订单类型\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"ddgl-ddlx\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"普通订单\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"秒杀订单\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"拼团订单\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"其他订单\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"labelWidth\":90,\"className\":\"order_type_class\",\"value\":\"1\"},\"update_date\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"提交时间\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":90,\"value\":[],\"className\":\"update_date_class\"},\"order_status\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"订单状态\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":3,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"order_status\",\"codeDicData\":[],\"labelWidth\":0,\"labelPosition\":\"top\",\"className\":\"order_status_class\"},\"order_money\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"订单金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":3,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":0,\"className\":\"order_money_class\",\"prepend\":\"\",\"labelPosition\":\"top\"},\"mode_payment\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"支付方式\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"mode_payment\",\"codeDicData\":[],\"labelWidth\":90,\"className\":\"mode_payment_class\"},\"payment_date\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"支付时间\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":90,\"value\":[],\"className\":\"payment_date_class\"},\"mode_distribution\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"配送方式\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"mode_distribution\",\"codeDicData\":[],\"labelWidth\":90,\"className\":\"mode_distribution_class\"}},\"group\":[]}', `group_desform_id` = 1829756131023052802, `is_open` = 'N', `is_template` = 'N' WHERE `id` = 1844550190467956737;

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-11 09:49:52', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-06 17:37:23', `is_deleted` = 0, `desform_name` = '管理模块-订单详情', `desform_json` = '{\"jsEnhance\":\"return {\\n  initData(formData) {\\n    return new Promise(resolve => { //初始化默认值\\n\\n      let arr = [\'sphjje\', \'yf\', \'yhj\', \'jfzk\', \'hdyh\', \'zkje\', \'ddje\', \'yfkje\']\\n      arr.forEach(ele => {\\n        if (formData[ele]) {\\n          let je = formData[ele]\\n          if (isNaN(parseFloat(je))) return false\\n          let newJe = Math.round(je * 100) / 100\\n          let newJeString = newJe.toString()\\n          let newJeLength = newJeString.indexOf(\'.\')\\n          if (newJeLength < 0) {\\n            newJeLength = newJeString.length;\\n            newJeString += \'.\'\\n          }\\n          while (newJeString.length <= newJeLength + 2) {\\n            newJeString += \'0\'\\n          }\\n          formData[ele] = \'￥\' + newJeString\\n        } else {\\n          formData[ele] = \'￥0.00\'\\n        }\\n      })\\n\\n\\n      resolve(formData)\\n    })\\n  },\\n  beforeSubmit(submitData) { //提交前数据处理\\n    return new Promise((resolve, reject) => {\\n      console.log(submitData, \'biahihg\')\\n      resolve(submitData)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1844555987675561985 {\\n  padding-top: 0 !important;\\n\\n  //样式请写在当前位置内\\n  .el-form {\\n    .el-row {\\n      border: none;\\n      margin: 0 !important;\\n\\n      .red {\\n        .el-form-item__content {\\n          input {\\n            color: red;\\n          }\\n        }\\n      }\\n\\n      .el-form-item {\\n        .el-form-item__label {\\n          background-color: #F5F5F5;\\n          padding: 0 20px !important;\\n          margin-bottom: 0;\\n        }\\n\\n        .el-form-item__content {\\n          padding-left: 20px;\\n        }\\n      }\\n\\n      .border-left-box {\\n        .el-form-item {\\n          .el-form-item__label {\\n            border-left: 1px solid #ebeef5;\\n          }\\n\\n          .el-form-item__content {\\n            border-left: 1px solid #ebeef5 !important;\\n          }\\n        }\\n      }\\n\\n      .ddxq-title-class {\\n        border: none;\\n        border-bottom: 1px solid #ebeef5;\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n        font-weight: 700;\\n        margin-top: 15px !important;\\n\\n        .el-form-item {\\n          .el-form-item__content {\\n            border: none !important;\\n            padding: 0;\\n\\n            p {\\n              height: auto;\\n              line-height: normal;\\n              padding-left: 5px;\\n              margin: 15px 0;\\n              border-left: 6px solid #409EFF;\\n            }\\n          }\\n        }\\n      }\\n\\n      .mtop {\\n        margin-top: 0 !important;\\n      }\\n\\n      .el-form-item__label,\\n      .el-form-item__content,\\n      .el-form-item__content .el-select__selection,\\n      .el-form-item__content .user-input {\\n        height: 55px;\\n        line-height: 55px;\\n      }\\n\\n\\n      .el-form-item__label,\\n      .el-form-item__content .el-input,\\n      .el-form-item__content .el-select__selection {\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n        font-size: 14px;\\n        color: #666666;\\n      }\\n\\n      .el-form-item__label {\\n        line-height: 55px;\\n        font-weight: 700;\\n      }\\n\\n      .el-form-item__content .el-select__wrapper {\\n        padding: 0;\\n      }\\n\\n\\n\\n    }\\n  }\\n}\",\"labelPosition\":\"left\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"column\":{\"fields_8754320\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"订单信息\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{\"fontSize\":\"14px\",\"color\":\"#666666\"},\"className\":\"ddxq-title-class mtop\",\"stylesStr\":\"return {\\r\\n  fontSize: \'14px\',\\r\\n  color: \'#666666\',\\r\\n}\"},\"ddly\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"订单来源\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"order_source\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"app\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"小程序\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"web\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"className\":\"border-left-box\"},\"ddlx\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"订单类型\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"order_type\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"普通订单\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"加急订单\",\"colorType\":\"default\",\"cssClass\":\"\"}]},\"tjsj\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"提交时间\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"value\":[]},\"zffs\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"支付方式\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"mode_payment\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"微信\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"支付宝\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"银行卡\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"其他\",\"colorType\":\"default\",\"cssClass\":\"\"}]},\"zfsj\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"支付时间\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"value\":[],\"className\":\"border-left-box\"},\"psfs\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"配送方式\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"mode_distribution\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"顺丰快递\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"自营配送\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"申通快递\",\"colorType\":\"default\",\"cssClass\":\"\"}]},\"wldh\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"物流单号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"zdqrshsj\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"自动确认收货时间\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"value\":\"15天\"},\"jfhk\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"积分回馈\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"border-left-box\"},\"czhk\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"成长回馈\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"yhzh\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"用户账号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"hdxx\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"活动信息\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"fields_4895475\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"费用信息\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{\"fontSize\":\"14px\",\"color\":\"#666666\"},\"className\":\"ddxq-title-class\",\"stylesStr\":\"return {\\r\\n  fontSize: \'14px\',\\r\\n  color: \'#666666\',\\r\\n}\"},\"sphjje\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"商品合计金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"border-left-box\"},\"yf\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"运费\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"yhj\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"优惠劵\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"jfzk\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"积分折扣\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"hdyh\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"活动优惠\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"border-left-box\"},\"zkje\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"折扣金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"ddje\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"订单金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"red\"},\"yfkje\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"应付款金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"red\"},\"fields_5412361\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"收货人信息\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{\"fontSize\":\"14px\",\"color\":\"#666666\"},\"className\":\"ddxq-title-class\",\"stylesStr\":\"return {\\r\\n  fontSize: \'14px\',\\r\\n  color: \'#666666\',\\r\\n}\"},\"shr\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"收货人\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"border-left-box\"},\"sjhm\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"手机号码\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"yzbm\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"邮政编码\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"shdz\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收货地址\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"fields_2517772\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"发票信息\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{\"fontSize\":\"14px\",\"color\":\"#666666\"},\"className\":\"ddxq-title-class\",\"stylesStr\":\"return {\\r\\n  fontSize: \'14px\',\\r\\n  color: \'#666666\',\\r\\n}\"},\"fplx\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"发票类型\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"Invoice_type\",\"codeDicData\":[],\"className\":\"border-left-box\"},\"fptt\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"发票抬头\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"static\",\"staticDicData\":[{\"label\":\"企业\",\"value\":\"1\"},{\"label\":\"个人\",\"value\":\"2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"Invoice_type\",\"codeDicData\":[]},\"qymc\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"企业名称\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"qysh\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"企业税号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"qydz\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"企业地址\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"border-left-box\"},\"fpnr\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"发票内容\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"sprsj\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收票人手机\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"spryx\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收票人邮箱\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"}},\"group\":[]}', `group_desform_id` = 1829756131023052802, `is_open` = NULL, `is_template` = 'N' WHERE `id` = 1844555987675561985;

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-11 14:54:18', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-12 14:30:49', `is_deleted` = 0, `desform_name` = '管理模块-工单管理', `desform_json` = '{\"jsEnhance\":\"return {\\n  initData(formData) {\\n    return new Promise(resolve => { //初始化默认值\\n\\n      resolve(formData)\\n    })\\n  },\\n  beforeSubmit(submitData) { //提交前数据处理\\n    return new Promise((resolve, reject) => {\\n      var tjTime = useFun.formatDate(new Date(), \'YYYY-MM-DD HH:mm:ss\')\\n      var time = useFun.formatDate(new Date(), \'YYYYMMDDHHmmss\')\\n      submitData.gdbh = time\\n      submitData.tjsj = tjTime\\n      submitData.gdzt = 1\\n      resolve(submitData)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1844632600228409345 {\\n\\n  //样式请写在当前位置内\\n\\n  .avue-form__group {\\n    .avue-form__row {\\n      .el-form-item {\\n        .el-form-item__label {\\n          height: 40px;\\n          line-height: 40px;\\n        }\\n\\n        .el-input__wrapper {\\n          height: 38px;\\n        }\\n\\n        .el-radio-button {\\n          height: 35px;\\n        }\\n\\n        .el-select__wrapper {\\n          height: 40px;\\n        }\\n\\n        .el-upload__tip {\\n          font-family: \'微软雅黑\', sans-serif;\\n          color: #999999;\\n          font-size: 14px;\\n        }\\n      }\\n    }\\n\\n    .avue-title p {\\n      font-size: 14px !important;\\n      font-family: \'微软雅黑\', sans-serif !important;\\n      font-weight: 400 !important;\\n      font-style: normal !important;\\n      color: #999999 !important;\\n      text-align: left !important;\\n      margin-left: 120px;\\n    }\\n\\n  }\\n\\n\\n\\n}\",\"labelPosition\":\"right\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"isSubmitTable\":false,\"column\":{\"gdbt\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"工单标题\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":24,\"disabled\":false,\"required\":true,\"hideLabel\":false,\"offset\":null},\"glkh\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"关联客户\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":24,\"disabled\":false,\"required\":true,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"xx\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"选项一\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"选项二\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"选项三\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"选项四\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"选项五\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"offset\":null},\"gldd\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"关联订单\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":24,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"xx\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"选项一\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"选项二\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"选项三\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"选项四\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"选项五\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"offset\":null},\"gdlx\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"工单类型\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":24,\"disabled\":false,\"required\":true,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"gdlx\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"普通工单\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"售后工单\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"维修工单\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"offset\":null},\"jycd\":{\"type\":\"radio\",\"controlType\":\"select\",\"label\":\"紧要程度\",\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":24,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"dicCode\":\"jycd\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"重要\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"紧急\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"普通\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"较低\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"value\":\"1\",\"labelPosition\":\"left\",\"labelWidth\":null,\"border\":false,\"button\":true,\"offset\":null},\"gdms\":{\"type\":\"textarea\",\"controlType\":\"input\",\"label\":\"工单描述\",\"readonly\":false,\"minRows\":3,\"maxRows\":5,\"display\":true,\"span\":24,\"disabled\":false,\"required\":false,\"hideLabel\":false},\"scfj\":{\"type\":\"file\",\"controlType\":\"upload\",\"label\":\"上传附件\",\"showFileList\":true,\"display\":true,\"span\":24,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"tip\":\"支持格式：.rar .zip .doc .docx .pdf ，单个文件不能超过20MB\"},\"clry\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"处理人员\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":24,\"disabled\":false,\"required\":true,\"hideLabel\":false,\"offset\":null},\"csry\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"抄送人员\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":24,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"offset\":0},\"fields_8201119\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"注：处理完成后，将自动通知到所选人员。\",\"display\":true,\"span\":12,\"hideLabel\":true,\"styles\":{},\"offset\":0,\"className\":\"pour\"}},\"tableDesignId\":\"1844620967619866625\",\"group\":[]}', `group_desform_id` = NULL, `is_open` = 'N', `is_template` = 'N' WHERE `id` = 1844632600228409345;

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-12 15:26:08', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-12 11:39:57', `is_deleted` = 0, `desform_name` = '工单管理-订单详情', `desform_json` = '{\"jsEnhance\":\"return {\\n  initData(formData) {\\n    return new Promise(resolve => { //初始化默认值\\n\\n      let arr = [\'sphjje\', \'yf\', \'yhj\', \'jfzk\', \'hdyh\', \'zkje\', \'ddje\', \'yfkje\']\\n      arr.forEach(ele => {\\n        if (formData[ele]) {\\n          let je = formData[ele]\\n          if (isNaN(parseFloat(je))) return false\\n          let newJe = Math.round(je * 100) / 100\\n          let newJeString = newJe.toString()\\n          let newJeLength = newJeString.indexOf(\'.\')\\n          if (newJeLength < 0) {\\n            newJeLength = newJeString.length;\\n            newJeString += \'.\'\\n          }\\n          while (newJeString.length <= newJeLength + 2) {\\n            newJeString += \'0\'\\n          }\\n          formData[ele] = \'￥\' + newJeString\\n        } else {\\n          formData[ele] = \'￥0.00\'\\n        }\\n      })\\n\\n\\n      resolve(formData)\\n    })\\n  },\\n  beforeSubmit(submitData) { //提交前数据处理\\n    return new Promise((resolve, reject) => {\\n      console.log(submitData, \'biahihg\')\\n      resolve(submitData)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1845002998170267649 {\\n  padding: 0 10px 20px 10px !important;\\n\\n  //样式请写在当前位置内\\n  .el-form {\\n    .el-row {\\n      border: none;\\n      margin: 0 !important;\\n\\n      .red {\\n        .el-form-item__content {\\n          input {\\n            color: red;\\n          }\\n        }\\n      }\\n\\n      .input:nth-child(5) {\\n        .el-form-item {\\n          border-right: 1px solid #e9e9e9;\\n        }\\n      }\\n\\n      .input:nth-child(9) {\\n        .el-form-item {\\n          border-right: 1px solid #e9e9e9;\\n        }\\n      }\\n\\n      .input:nth-child(12) {\\n        .el-form-item {\\n          border-right: 1px solid #e9e9e9;\\n        }\\n      }\\n\\n      .input {\\n        .el-form-item {\\n          border-left: 1px solid #e9e9e9;\\n\\n          border-bottom: 1px solid #e9e9e9;\\n\\n          .el-form-item__label {\\n            background-color: #F5F5F5;\\n            padding: 0 20px !important;\\n          }\\n\\n          .el-form-item__content {\\n            // padding-left: 20px;\\n          }\\n        }\\n      }\\n\\n      .border-left-box {\\n        .el-form-item {\\n          .el-form-item__label {\\n            border-left: 1px solid #ebeef5;\\n          }\\n\\n          .el-form-item__content {\\n            // border-left: 1px solid #ebeef5;\\n          }\\n        }\\n      }\\n\\n      .el-form-item__label,\\n      .el-form-item__content,\\n      .el-form-item__content .el-select__selection,\\n      .el-form-item__content .user-input {\\n        height: 55px;\\n        line-height: 55px;\\n      }\\n\\n      .ddxq-title-class {\\n        border: none;\\n        border-bottom: 1px solid #ebeef5;\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n        font-weight: 700;\\n        margin-top: 15px !important;\\n\\n        .el-form-item {\\n          background: initial;\\n\\n          .el-form-item__content {\\n            height: 60px;\\n            line-height: 60px;\\n            border: none !important;\\n            padding: 0;\\n\\n            p {\\n              height: auto;\\n              line-height: normal;\\n              padding-left: 10px;\\n              margin: 15px 0;\\n              border-left: 7px solid #409EFF;\\n            }\\n          }\\n        }\\n      }\\n\\n      .mtop {\\n        margin-top: 0 !important;\\n        border-left: none !important;\\n\\n\\n      }\\n\\n      .bottom {\\n        margin-top: 25px !important;\\n\\n        .el-form-item__content {\\n          bottom: 5px;\\n        }\\n      }\\n\\n\\n      .el-form-item__label,\\n      .el-form-item__content .el-input,\\n      .el-form-item__content .el-select__selection {\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n        font-size: 14px;\\n        color: #666666;\\n      }\\n\\n      .el-form-item__label {\\n        line-height: 55px;\\n        font-weight: 700;\\n      }\\n\\n      .el-form-item__content .el-select__wrapper {\\n        padding: 0;\\n      }\\n\\n\\n\\n    }\\n  }\\n\\n  // .el-col {\\n  //   padding: 0 !important;\\n  //   margin: 0 !important;\\n  //   border-left: 1px solid #e9e9e9 !important;\\n  // }\\n\\n  .dept-select-box {\\n    height: 55px;\\n    line-height: 55px;\\n    display: flex;\\n    align-items: center;\\n  }\\n\\n  .avue-time {\\n    height: 55px;\\n    line-height: 55px;\\n    display: flex;\\n    align-items: center;\\n    flex-direction: none;\\n  }\\n\\n  .el-input__inner {\\n    margin-top: 20px;\\n  }\\n\\n  .avue--detail .el-form-item__content {\\n    border: none;\\n  }\\n\\n  .text-box {\\n    height: 68px;\\n\\n    .el-form-item {\\n      margin-left: -10px;\\n\\n      .el-form-item__content {\\n        padding: 0;\\n      }\\n    }\\n\\n    .avue-title p {\\n      font-family: \'微软雅黑\', sans-serif;\\n      font-weight: 400;\\n      font-style: normal;\\n      text-align: left;\\n      height: 68px;\\n      line-height: 68px;\\n      color: #666666;\\n      border: 1px solid #e9e9e9;\\n      font-size: 14px;\\n      padding-left: 20px;\\n      margin-top: -8px;\\n      background: #fcfcfc;\\n    }\\n\\n  }\\n}\",\"labelPosition\":\"left\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"column\":{\"fields_8754320\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"工单信息\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{\"fontSize\":\"14px\",\"color\":\"#666666\"},\"className\":\"ddxq-title-class mtop\",\"stylesStr\":\"return {\\r\\n  fontSize: \'14px\',\\r\\n  color: \'#666666\',\\r\\n}\"},\"gdbh\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"工单编号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"input\"},\"glkh\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"关联客户\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"xx\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"选项一\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"选项二\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"选项三\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"选项四\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"选项五\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"className\":\"input\"},\"gldd\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"关联订单\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"xx\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"选项一\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"选项二\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"选项三\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"选项四\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"5\",\"label\":\"选项五\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"className\":\"input\"},\"gdzt\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"当前状态\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"gdzt\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"待处理\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"已完结\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"已退回\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"已撤销\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"className\":\"input\"},\"jycd\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"紧要程度\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"jycd\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"重要\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"紧急\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"普通\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"较低\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"className\":\"input\"},\"tjry\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"提交人员\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"input\"},\"tjbm\":{\"type\":\"deptSelect\",\"controlType\":\"select\",\"label\":\"提交部门\",\"findType\":\"all\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"input\"},\"clry\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"处理人员\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"input\"},\"clbm\":{\"type\":\"deptSelect\",\"controlType\":\"select\",\"label\":\"处理部门\",\"findType\":\"all\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"input\"},\"tjsj\":{\"type\":\"time\",\"controlType\":\"time\",\"label\":\"提交时间\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"value\":\"yyyy-10-Tu 15:36:08\",\"className\":\"input\"},\"csry\":{\"type\":\"userSelect\",\"controlType\":\"select\",\"label\":\"抄送人员\",\"findType\":\"all\",\"columnKey\":[\"mobile\",\"sex\",\"deptName\"],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"input\"},\"fields_4895475\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"工单描述\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{\"fontSize\":\"14px\",\"color\":\"#666666\"},\"className\":\"ddxq-title-class  bottom\",\"stylesStr\":\"return {\\r\\n  fontSize: \'14px\',\\r\\n  color: \'#666666\',\\r\\n}\"},\"gdms\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{},\"className\":\"text-box\"}},\"group\":[]}', `group_desform_id` = 1829756131023052802, `is_open` = NULL, `is_template` = 'N' WHERE `id` = 1845002998170267649;

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-14 11:51:04', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-13 17:34:08', `is_deleted` = 0, `desform_name` = '用户管理-用户管理头部', `desform_json` = '{\"jsEnhance\":\"//初始化控件\\nuseFun.controlInit(\'FormView\', \'formView_xgzl\', {\\n  formId: \'1845639851059728386\', //表单设计id\\n  formType: \'edit\', //表单类型 add | edit | view\\n  handleType: \'\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  showButton: true, //是否显示底部默认操作按钮\\n  dataOption: { //数据配置 \\n    tableId: \'\', //表单开发id\\n    dataId: \'\' //数据id\\n  },\\n  defaultData: {}, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  popOption: { //弹窗配置\\n    title: \'修改资料\', //标题\\n    width: \'580px\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {} //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    // if (type == \'submit\') {\\n    //   // useFun.refreshChange()\\n    // }\\n    done()\\n  }\\n})\\n\\n//初始化控件\\nuseFun.controlInit(\'FormView\', \'formView_szzt\', {\\n  formId: \'1845742751116910593\', //表单设计id\\n  formType: \'edit\', //表单类型 add | edit | view\\n  handleType: \'\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  showButton: true, //是否显示底部默认操作按钮\\n  dataOption: { //数据配置 \\n    tableId: \'\', //表单开发id\\n    dataId: \'\' //数据id\\n  },\\n  defaultData: {}, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  popOption: { //弹窗配置\\n    title: \'设置状态\', //标题\\n    width: \'35%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {} //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    // if (type == \'submit\') {\\n    //   // useFun.refreshChange()\\n    // }\\n    done()\\n  }\\n})\\n\\n\\n//初始化控件\\nuseFun.controlInit(\'FormView\', \'formView_zcmm\', {\\n  formId: \'1845748461452091393\', //表单设计id\\n  formType: \'edit\', //表单类型 add | edit | view\\n  handleType: \'\', //处理类型 default | returnData\\n  showType: \'dialog\', //弹窗类型 dialog | drawer\\n  showButton: true, //是否显示底部默认操作按钮\\n  dataOption: { //数据配置 \\n    tableId: \'\', //表单开发id\\n    dataId: \'\' //数据id\\n  },\\n  defaultData: {}, //默认表单数据\\n  enhanceData: {}, //传递给表单js增强内部调用配置\\n  popOption: { //弹窗配置\\n    title: \'设置状态\', //标题\\n    width: \'27%\', //弹窗宽度\\n    fullscreen: false, //是否全屏\\n    footerBtn: [ //底部按钮配置\\n\\n    ],\\n    headerBtn: [], //顶部按钮配置（配置同上）\\n    dialogParams: {} //弹窗其他配置\\n  },\\n  beforeClose: (type, done, formData, loading) => {\\n    // type:关闭类型  submit:提交后触发  close:直接关闭弹窗触发\\n    // done:关闭弹窗方法 formData:表单数据  loading:关闭loading方法\\n    if (type == \'submit\') {\\n      message.success(\'发送成功\')\\n    }\\n    done()\\n  }\\n})\\n\\n\\n\\nreturn {\\n  initOption() { //表单显示前执行\\n\\n\\n    console.log(useFun.getPropConfig(\'btnList\'), 11111)\\n  },\\n  initData(formData) {\\n    return new Promise(resolve => { //初始化默认值\\n      const { formView_xgzl, formView_szzt, formView_zcmm } = Vue.toRefs(rendControlData.value)\\n\\n      formView_xgzl.value.params.defaultData = formData\\n\\n      formView_szzt.value.params.defaultData = formData\\n\\n      formView_zcmm.value.params.defaultData = { icon_url: \'http://oss.yckxt.com/chatgpt/upload/1/2024-10-14/1/mdi--question-mark-circle-outline (1).png\' }\\n\\n\\n\\n\\n\\n      resolve(formData)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1845673651634458625 {\\n  height: 110px;\\n  display: flex;\\n  align-items: center;\\n\\n  //样式请写在当前位置内\\n  .el-form>.el-row {\\n    border: none;\\n\\n    .avue-form__group {\\n      align-items: center;\\n\\n      .el-col {\\n        border: none;\\n\\n        .el-form-item__label {\\n          border: none !important;\\n          background-color: white;\\n          padding: 0 !important;\\n          display: flex;\\n          justify-content: center;\\n          font-size: 14px;\\n        }\\n\\n        .el-form-item__content {\\n          border: none;\\n\\n          .el-upload {\\n\\n            border: none;\\n\\n            .avue-upload__avatar {\\n              height: 70px;\\n              width: 70px;\\n              border-radius: 50%;\\n\\n            }\\n\\n            img {\\n              height: 100%;\\n              width: 100%;\\n            }\\n          }\\n\\n          .el-select__selection {\\n            .el-select__selected-item {\\n              display: flex;\\n              justify-content: center;\\n              font-size: 16px;\\n              color: #666;\\n            }\\n          }\\n\\n          .el-input {\\n            .el-input-group__prepend {\\n              height: 30px;\\n              font-size: 16px;\\n              padding: 0;\\n              box-shadow: none;\\n              color: #666;\\n            }\\n\\n            .el-input__wrapper {\\n              .el-input__inner {\\n                text-align: center;\\n                font-size: 16px !important;\\n              }\\n            }\\n          }\\n        }\\n      }\\n\\n      .user_name_class {\\n        position: absolute;\\n        top: 13px;\\n        left: 90px;\\n\\n        .el-form-item__content {\\n          .el-input__inner {\\n            text-align: left !important;\\n            padding-left: 4px;\\n          }\\n        }\\n      }\\n\\n      .membership_level_class {\\n        position: absolute;\\n        top: 46px;\\n        left: 90px;\\n\\n        .el-select__selection {\\n          .el-select__selected-item {\\n            width: 80px;\\n            height: 28px;\\n            font-size: 12px !important;\\n            border-radius: 15px;\\n            align-items: center;\\n            background-color: #FFA94C;\\n            color: white !important;\\n          }\\n        }\\n      }\\n\\n      .user_type_class {\\n        margin-left: 100px !important;\\n      }\\n\\n      .xfje {\\n        .el-input {\\n          margin-top: 0;\\n        }\\n      }\\n\\n      .btn_list_class {\\n        margin-left: auto !important;\\n\\n        .el-button {\\n          height: 35px;\\n          width: 100px;\\n          font-size: 14px;\\n          color: #999999;\\n        }\\n\\n        .el-button--primary {\\n          color: white;\\n        }\\n      }\\n\\n    }\\n  }\\n\\n}\",\"labelPosition\":\"left\",\"labelSuffix\":\" \",\"labelWidth\":0,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"isSubmitTable\":true,\"column\":{\"avatar_img\":{\"type\":\"image\",\"controlType\":\"upload\",\"label\":\"\",\"listType\":\"picture-img\",\"accept\":\"image/*\",\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"className\":\"avatar\",\"offset\":0,\"moreOptionStr\":\"return {\\n\\n}\"},\"user_name\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelWidth\":0,\"className\":\"user_name_class\"},\"membership_level\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"left\",\"labelWidth\":0,\"dicCode\":\"user_management-level\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"普通会员\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"黄金会员\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"白金会员\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"钻石会员\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"className\":\"membership_level_class\"},\"user_type\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"用户状态\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"labelWidth\":null,\"dicCode\":\"user_management_type\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"正常\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"异常\",\"colorType\":\"default\",\"cssClass\":\"\"}],\"offset\":0,\"className\":\"user_type_class\"},\"consumption_amount\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"消费金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"prepend\":\"￥\",\"className\":\"xfje\"},\"order_record\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"订单记录\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"available_points\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"可用积分\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":2,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"btnList\":{\"type\":\"buttonList\",\"controlType\":\"\",\"label\":\"按钮组\",\"params\":{\"buttonList\":[{\"label\":\"修改资料\",\"prop\":\"btn_7167\",\"id\":\"btn_7167\",\"icon\":\"\",\"type\":\"primary\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"//控件调用\\r\\nreturn {\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_xgzl } = Vue.toRefs(rendControlData.value)\\r\\n    formView_xgzl.value.show = true //显示FormView\\r\\n  }\\r\\n}\\r\\n\"},{\"label\":\"设置状态\",\"prop\":\"btn_4309\",\"id\":\"btn_4309\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_szzt } = Vue.toRefs(rendControlData.value)\\r\\n    formView_szzt.value.show = true //显示FormView\\r\\n  }\\r\\n}\"},{\"label\":\"重置密码\",\"prop\":\"btn_2221\",\"id\":\"btn_2221\",\"icon\":\"\",\"type\":\"\",\"display\":true,\"viewShow\":true,\"btnLoading\":false,\"configStr\":\"return {\\r\\n  //当前按钮的点击事件\\r\\n  handleClick: (obj) => {\\r\\n    const { formView_zcmm } = Vue.toRefs(rendControlData.value)\\r\\n    formView_zcmm.value.show = true //显示FormView\\r\\n  }\\r\\n}\"}],\"location\":\"right\"},\"display\":true,\"span\":10,\"disabled\":false,\"hideLabel\":true,\"offset\":0,\"className\":\"btn_list_class\"}},\"group\":[]}', `group_desform_id` = NULL, `is_open` = 'N', `is_template` = 'N' WHERE `id` = 1845673651634458625;

UPDATE `lowcode_desform` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-14 17:24:10', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-13 16:00:40', `is_deleted` = 0, `desform_name` = '用户管理-用户详情', `desform_json` = '{\"jsEnhance\":\"return {\\n  beforeSubmit(submitData) { //提交前数据处理\\n    return new Promise((resolve, reject) => {\\n\\n      console.log(submitData,\'submitData\')\\n      resolve(submitData)\\n    })\\n  },\\n}\\n\",\"scssEnhance\":\".low-form__1845757477997731842 {\\n  padding: 6px 28px !important;\\n\\n  //样式请写在当前位置内\\n  .el-form {\\n    .el-row {\\n      border: none;\\n      margin: 0 !important;\\n\\n      .left-border {\\n        border-left: 1px solid #ebeef5;\\n      }\\n\\n      .el-form-item {\\n        .el-form-item__label {\\n          background-color: #F5F5F5;\\n          padding: 0 20px !important;\\n        }\\n\\n        .el-form-item__content {\\n          padding-left: 20px;\\n        }\\n      }\\n\\n      .border-left-box {\\n        .el-form-item {\\n          .el-form-item__label {\\n            border-left: 1px solid #ebeef5;\\n          }\\n\\n          .el-form-item__content {\\n            border-left: 1px solid #ebeef5 !important;\\n          }\\n        }\\n      }\\n\\n      .ddxq-title-class {\\n        border: none;\\n        border-bottom: 1px solid #ebeef5;\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n        font-weight: 700;\\n        margin-top: 15px !important;\\n\\n        .el-form-item {\\n          .el-form-item__content {\\n            height: 60px;\\n            line-height: 60px;\\n            border: none !important;\\n            padding: 0;\\n\\n            p {\\n              font-size: 14px;\\n              color: #666666;\\n              height: auto;\\n              line-height: normal;\\n              padding-left: 5px;\\n              margin: 15px 0;\\n              border-left: 6px solid #409EFF;\\n            }\\n          }\\n        }\\n      }\\n\\n      .mtop {\\n        margin-top: 0 !important;\\n      }\\n\\n      .el-form-item__label,\\n      .el-form-item__content,\\n      .el-form-item__content .el-select__selection,\\n      .el-form-item__content .user-input,\\n      .el-form-item__content .avue-date,\\n      .el-form-item__content .el-cascader .el-input__wrapper {\\n        height: 55px;\\n        line-height: 55px;\\n      }\\n\\n      .rq_class {\\n        .el-form-item__content .avue-date .el-input__wrapper {\\n          height: 55px;\\n          line-height: 55px;\\n        }\\n      }\\n\\n      .el-form-item__content {\\n\\n        .avue-date .el-input__wrapper,\\n        .el-cascader .el-input__wrapper {\\n          padding: 0;\\n\\n        }\\n      }\\n\\n      .el-form-item__label,\\n      .el-form-item__content .el-input,\\n      .el-form-item__content .el-select__selection {\\n        font-family: \'微软雅黑 Bold\', \'微软雅黑 Regular\', \'微软雅黑\', sans-serif;\\n        font-size: 14px;\\n        color: #666666;\\n      }\\n\\n      .el-form-item__label {\\n        line-height: 55px;\\n        font-weight: 700;\\n      }\\n\\n      .el-form-item__content .el-select__wrapper {\\n        padding: 0;\\n      }\\n\\n\\n\\n    }\\n  }\\n}\",\"labelPosition\":\"left\",\"labelSuffix\":\" \",\"labelWidth\":120,\"gutter\":20,\"menuBtn\":true,\"submitBtn\":true,\"submitText\":\"提交\",\"emptyBtn\":false,\"emptyText\":\"清空\",\"menuPosition\":\"center\",\"size\":\"default\",\"isSubmitTable\":true,\"column\":{\"fields_9105030\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"用户资料\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{},\"className\":\"ddxq-title-class mtop\"},\"id\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"ID编号\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"left-border\"},\"user_type\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"用户状态\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"user_management_type\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"正常\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"异常\",\"colorType\":\"default\",\"cssClass\":\"\"}]},\"sjhm\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"手机号码\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"xb\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"性别\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"system_user_sex\",\"codeDicData\":[{\"value\":\"0\",\"label\":\"未知\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"1\",\"label\":\"男\",\"colorType\":\"default\",\"cssClass\":\"A\"},{\"value\":\"2\",\"label\":\"女\",\"colorType\":\"success\",\"cssClass\":\"\"}]},\"sr\":{\"type\":\"date\",\"controlType\":\"date\",\"label\":\"生日\",\"clearable\":true,\"format\":\"YYYY-MM-DD\",\"valueFormat\":\"YYYY-MM-DD\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"offset\":null,\"value\":[],\"className\":\"left-border rq_class\"},\"cs\":{\"type\":\"regionSelect\",\"controlType\":\"select\",\"label\":\"城市\",\"regionType\":\"ssq\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"zy\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"职业\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"gxqm\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"个性签名\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"xhdfl\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"喜欢的分类\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"left-border\"},\"registration_channel\":{\"type\":\"select\",\"controlType\":\"select\",\"label\":\"注册渠道\",\"clearable\":true,\"collapseTags\":true,\"maxCollapseTags\":1,\"dicType\":\"code\",\"staticDicData\":[{\"label\":\"字典1\",\"value\":\"dic_1\"},{\"label\":\"字典2\",\"value\":\"dic_2\"}],\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"delDicValue\":[],\"labelPosition\":\"top\",\"dicCode\":\"user_management_registration_channel\",\"codeDicData\":[{\"value\":\"1\",\"label\":\"APP\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"2\",\"label\":\"网站\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"3\",\"label\":\"小程序\",\"colorType\":\"default\",\"cssClass\":\"\"},{\"value\":\"4\",\"label\":\"其它渠道\",\"colorType\":\"default\",\"cssClass\":\"\"}]},\"create_time\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"注册时间\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"offset\":null,\"value\":[]},\"zhdl\":{\"type\":\"datetime\",\"controlType\":\"date\",\"label\":\"最后登录\",\"clearable\":true,\"format\":\"YYYY-MM-DD HH:mm:ss\",\"valueFormat\":\"YYYY-MM-DD HH:mm:ss\",\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"offset\":null,\"value\":[]},\"fields_8038839\":{\"type\":\"title\",\"controlType\":\"title\",\"label\":\"文本\",\"value\":\"统计信息\",\"display\":true,\"span\":24,\"hideLabel\":true,\"styles\":{},\"className\":\"ddxq-title-class\"},\"consumption_amount\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"消费金额\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"left-border\"},\"order_record\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"订单记录\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"available_points\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"可用积分\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"czz\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"成长值\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"yhj\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"优惠卷（张）\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"left-border\"},\"sppj\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"商品评价\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"thjl\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"退货记录\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"dlcs\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"登录次数\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"gz\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"关注\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"left-border\"},\"fs\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"粉丝\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"scsp\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收藏商品\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"sczt\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收藏专题\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"scht\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收藏话题\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\",\"className\":\"left-border\"},\"scpj\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"收藏评价\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"yqhy\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"邀请好友\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"},\"sycjcs\":{\"type\":\"input\",\"controlType\":\"input\",\"label\":\"剩余抽奖次数\",\"readonly\":false,\"clearable\":true,\"display\":true,\"span\":6,\"disabled\":false,\"required\":false,\"hideLabel\":false,\"labelPosition\":\"top\"}},\"group\":[]}', `group_desform_id` = NULL, `is_open` = 'N', `is_template` = 'N' WHERE `id` = 1845757477997731842;

DELETE FROM `lowcode_group_desform` WHERE `id` = 1814566297392414721;

DELETE FROM `lowcode_group_desform` WHERE `id` = 1820725650445041666;

DELETE FROM `lowcode_group_desform` WHERE `id` = 1829755922813607939;

DELETE FROM `lowcode_group_desform` WHERE `id` = 1829756131023052802;

DELETE FROM `lowcode_group_report` WHERE `id` = 1814566297392414721;

DELETE FROM `lowcode_group_report` WHERE `id` = 1820725650445041666;

DELETE FROM `lowcode_group_report` WHERE `id` = 1829755922813607939;

DELETE FROM `lowcode_group_report` WHERE `id` = 1829756131023052802;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163381379073;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163440099330;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163494625281;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163544956930;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163595288578;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163658203137;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163708534786;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163767255041;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163821780994;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163872112641;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163939221506;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659163993747458;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659164044079105;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659164094410753;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659164144742402;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659164195074049;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866659164253794306;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866661680156717057;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664197354217474;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664197698150401;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664198285352962;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664198390210562;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664198985801730;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664199086465026;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1866664199195516930;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776426274818;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776526938114;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776623407106;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776703098882;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776778596354;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776862482434;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881776937979906;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777013477378;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777080586241;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777147695106;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777223192577;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777307078658;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777378381826;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777453879297;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777516793857;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777588097025;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777655205889;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777726509057;

DELETE FROM `lowcode_log_module_usage_records` WHERE `id` = 1872881777781035009;

INSERT INTO `lowcode_report`(`id`, `tenant_id`, `create_user`, `create_time`, `create_dept`, `update_user`, `update_time`, `is_deleted`, `report_name`, `report_code`, `group_report_id`, `java_config`, `data_config`, `table_config`, `data_sources_config`) VALUES (1848542924271091713, 1, 1, '2024-10-22 09:52:32', 101, 1, '2024-10-22 09:54:17', 0, '测试林-无数据源', 'test_lin_no_datasource', NULL, 'linReportClassEnhance', 'page,authFalse', 'height,header,menu,index,border', '{\"dataOrigin\":\"\",\"executeSql\":\"\",\"optionData\":{\"optionObj\":{\"select\":[],\"from\":[],\"where\":[],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"test_table_01\":\"tbl_tt0\"}}}');

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 10:02:09', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:21:19', `is_deleted` = 0, `report_name` = '系统首页-数据看板1-每日数据', `report_code` = 'example_today_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板1\'\\n  AND tbl_erd.table_type = \'实时数据\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_6114586\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3678822\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_91400\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2229905\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板1\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4911400\",\"controlType\":\"default\",\"conditionValue\":\"实时数据\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_today_data\":\"tbl_etd\",\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847095793067110402;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 16:09:44', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:21:37', `is_deleted` = 0, `report_name` = '系统首页-数据看板1-订单及销量趋势', `report_code` = 'example_order_sales', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板1\'\\n  AND tbl_erd.table_type = \'订单及销量趋势\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1411236\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_8226402\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2872221\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3847160\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板1\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_449374\",\"controlType\":\"default\",\"conditionValue\":\"订单及销量趋势\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847188299477860353;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 17:18:02', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:20:37', `is_deleted` = 0, `report_name` = '系统首页-数据看板1-商品销售占比', `report_code` = 'example_order_sales_percentage', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板1\'\\n  AND tbl_erd.table_type = \'商品销售占比\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_4766454\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6465565\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_3838476\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3514764\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板1\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9465630\",\"controlType\":\"default\",\"conditionValue\":\"商品销售占比\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847205485940473858;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 17:42:47', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:21:52', `is_deleted` = 0, `report_name` = '系统首页-数据看板1-新增用户及会员趋势', `report_code` = 'example_new_user_num', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板1\'\\n  AND tbl_erd.table_type = \'新增用户及会员趋势\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_9276880\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_9929767\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_4034545\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8193637\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板1\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3092772\",\"controlType\":\"default\",\"conditionValue\":\"新增用户及会员趋势\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847211713336938498;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-18 17:55:20', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:22:05', `is_deleted` = 0, `report_name` = '系统首页-数据看板1-用户访问趋势', `report_code` = 'example_user_visits', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板1\'\\n  AND tbl_erd.table_type = \'用户访问趋势\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8599487\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3300613\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_1540788\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2563393\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板1\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1444886\",\"controlType\":\"default\",\"conditionValue\":\"用户访问趋势\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847214874885853186;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 11:11:42', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:17:42', `is_deleted` = 0, `report_name` = '系统首页-数据看板1-本月单品销量排名', `report_code` = 'example_product_sales_rank', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板1\'\\n  AND tbl_erd.table_type = \'本月单品销量排名\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_9187986\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_4577205\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_9322929\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1852922\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板1\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_444915\",\"controlType\":\"default\",\"conditionValue\":\"本月单品销量排名\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847475681523539969;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 11:47:46', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:17:26', `is_deleted` = 0, `report_name` = '系统首页-客户管理-本月数据', `report_code` = 'example_client_month_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'本月数据\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_439382\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_9837434\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_3146376\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_7026782\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3321131\",\"controlType\":\"default\",\"conditionValue\":\"本月数据\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847484758257442818;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 11:59:46', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:17:07', `is_deleted` = 0, `report_name` = '系统首页-客户管理-数据简报', `report_code` = 'example_client_data_briefs', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'数据简报\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_353772\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5028238\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_16559\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_7476099\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_430414\",\"controlType\":\"default\",\"conditionValue\":\"数据简报\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847487778391203842;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:18:26', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:23:09', `is_deleted` = 0, `report_name` = '系统首页-客户管理-业绩目标', `report_code` = 'example_client_sales_target', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'业绩目标\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8331456\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5099294\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_1235208\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_6415915\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2416711\",\"controlType\":\"default\",\"conditionValue\":\"业绩目标\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847522676099391489;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:20:33', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:22:17', `is_deleted` = 0, `report_name` = '系统首页-客户管理-销售预测', `report_code` = 'example_client_sales_forecast', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'销售预测\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_9032445\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_1813385\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_6602209\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9075154\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_366401\",\"controlType\":\"default\",\"conditionValue\":\"销售预测\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847523207228301314;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:25:15', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:13:04', `is_deleted` = 0, `report_name` = '系统首页-客户管理-销售漏斗图', `report_code` = 'example_client_sales_funnelgraphic', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'销售漏斗图\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1189648\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6510102\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5899904\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8615388\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2613005\",\"controlType\":\"default\",\"conditionValue\":\"销售漏斗图\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847524393532661762;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:28:05', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:06:35', `is_deleted` = 0, `report_name` = '系统首页-客户管理-销售排名', `report_code` = 'example_client_salesman_rank', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'销售排名\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_484669\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_1006469\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_8316724\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9634001\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5791513\",\"controlType\":\"default\",\"conditionValue\":\"销售排名\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847525106199437313;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:30:08', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:23:56', `is_deleted` = 0, `report_name` = '系统首页-客户管理-系统通知', `report_code` = 'example_client_system_notification', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页客户管理\'\\n  AND tbl_erd.table_type = \'系统通知\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_9826770\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3615705\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_9940652\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_195453\",\"controlType\":\"default\",\"conditionValue\":\"系统首页客户管理\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8149720\",\"controlType\":\"default\",\"conditionValue\":\"系统通知\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847525621318688769;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:40:44', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 11:37:31', `is_deleted` = 0, `report_name` = '系统首页-商户统计-统计数据', `report_code` = 'example_trader_count_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'统计数据\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2070814\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_924296\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5244133\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9954683\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1963225\",\"controlType\":\"default\",\"conditionValue\":\"统计数据\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847528287872622594;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:42:56', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:58:26', `is_deleted` = 0, `report_name` = '系统首页-商户统计-商户外拓', `report_code` = 'example_trader_expansion', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'商户外拓\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2392070\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6147463\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_4082249\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1308069\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_22575\",\"controlType\":\"default\",\"conditionValue\":\"商户外拓\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847528840077910017;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:51:45', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:24:25', `is_deleted` = 0, `report_name` = '系统首页-商户统计-日交易额', `report_code` = 'example_trader_daily_turnover', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'日交易额\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8414924\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_9349119\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_1789631\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2389872\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4463391\",\"controlType\":\"default\",\"conditionValue\":\"日交易额\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847531061758795778;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 14:58:07', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:22:32', `is_deleted` = 0, `report_name` = '系统首页-商户统计-周订单量', `report_code` = 'example_trader_weekly_orders', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'周订单量\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1615685\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5599345\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_7797528\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3062171\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2551897\",\"controlType\":\"default\",\"conditionValue\":\"周订单量\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847532664964390914;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:02:12', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:22:44', `is_deleted` = 0, `report_name` = '系统首页-商户统计-开拓商家', `report_code` = 'example_trader_expansion_number', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'开拓商家\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8766767\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_9949310\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_1766037\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2163578\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_6804736\",\"controlType\":\"default\",\"conditionValue\":\"开拓商家\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847533689980985346;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:06:24', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:22:57', `is_deleted` = 0, `report_name` = '系统首页-商户统计-成交金额趋势', `report_code` = 'example_trader_transaction_amount', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'成交金额趋势\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8722454\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3130972\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_3521004\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_6285137\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_456472\",\"controlType\":\"default\",\"conditionValue\":\"成交金额趋势\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847534747478269953;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:24:30', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:56:46', `is_deleted` = 0, `report_name` = '系统首页-系统监控-服务器基本信息', `report_code` = 'example_systemmonitor_server_information', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页系统监控\'\\n  AND tbl_erd.table_type = \'服务器基本信息\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_9210875\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_275374\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_9709793\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4570263\",\"controlType\":\"default\",\"conditionValue\":\"系统首页系统监控\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8523467\",\"controlType\":\"default\",\"conditionValue\":\"服务器基本信息\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_xmgl_rwlb\":\"tbl_exr\",\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847539302530162690;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:27:57', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:24:43', `is_deleted` = 0, `report_name` = '系统首页-系统监控-cpu使用率', `report_code` = 'example_systemmonitor_cpu_utilization', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页系统监控\'\\n  AND tbl_erd.table_type = \'CPU使用率\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_194414\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_120838\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_7913725\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_838715\",\"controlType\":\"default\",\"conditionValue\":\"系统首页系统监控\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8696870\",\"controlType\":\"default\",\"conditionValue\":\"CPU使用率\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847540171430567937;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:29:40', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:24:56', `is_deleted` = 0, `report_name` = '系统首页-系统监控-内存使用率', `report_code` = 'example_systemmonitor_memory_utilization', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页系统监控\'\\n  AND tbl_erd.table_type = \'内存使用率\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_7337463\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7660689\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5078497\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9727116\",\"controlType\":\"default\",\"conditionValue\":\"系统首页系统监控\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2684230\",\"controlType\":\"default\",\"conditionValue\":\"内存使用率\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847540601183150081;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:32:53', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:25:14', `is_deleted` = 0, `report_name` = '系统首页-系统监控-服务器流量', `report_code` = 'example_systemmonitor_server_traffic', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页系统监控\'\\n  AND tbl_erd.table_type = \'服务器流量\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_4048182\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5286513\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2987209\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1373735\",\"controlType\":\"default\",\"conditionValue\":\"系统首页系统监控\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3096447\",\"controlType\":\"default\",\"conditionValue\":\"服务器流量\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847541410490884097;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:35:07', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:25:31', `is_deleted` = 0, `report_name` = '系统首页-系统监控-服务器磁盘IO', `report_code` = 'example_systemmonitor_disk_io', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页系统监控\'\\n  AND tbl_erd.table_type = \'服务器磁盘IO\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8954207\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_4152224\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2172530\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4547983\",\"controlType\":\"default\",\"conditionValue\":\"系统首页系统监控\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1707591\",\"controlType\":\"default\",\"conditionValue\":\"服务器磁盘IO\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847541972397596673;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 15:43:22', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:55:08', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-我的待办', `report_code` = 'example_signagetwo_pending', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'我的待办\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_8921880\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_8889024\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2941518\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_183791\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3824921\",\"controlType\":\"default\",\"conditionValue\":\"我的待办\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847544052189372418;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:21:32', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:54:51', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-业务处理', `report_code` = 'example_systemmonitor_business_processing', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'业务处理\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_735951\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5724702\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_7454407\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5241865\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4664686\",\"controlType\":\"default\",\"conditionValue\":\"业务处理\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847553655417479169;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:23:40', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:54:17', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-预警信息', `report_code` = 'example_systemmonitor_warning_messages', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'预警信息\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2865743\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7923724\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_6311478\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1087990\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_6516055\",\"controlType\":\"default\",\"conditionValue\":\"预警信息\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847554193446019074;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:25:46', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:53:57', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-年度经营目标', `report_code` = 'example_systemmonitor_business_target', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'年度经营目标\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_5941749\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6526936\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5390303\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5556558\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9515800\",\"controlType\":\"default\",\"conditionValue\":\"年度经营目标\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847554720070246402;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:28:48', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:53:24', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-年度回款目标', `report_code` = 'example_systemmonitor_payback_target', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'年度回款目标\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_148005\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_4692298\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_8261349\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2286450\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2171895\",\"controlType\":\"default\",\"conditionValue\":\"年度回款目标\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847555485300039681;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:31:13', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:53:07', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-账户余额', `report_code` = 'example_systemmonitor_account_balance', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'账户余额\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2438398\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3783454\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_7956668\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8846483\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4210252\",\"controlType\":\"default\",\"conditionValue\":\"账户余额\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847556092589121537;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:34:42', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:23:20', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-收支预测', `report_code` = 'example_systemmonitor_Income_expenditure_forecasts', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'收支预测\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1014541\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6725136\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_1216641\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_6813755\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1120096\",\"controlType\":\"default\",\"conditionValue\":\"收支预测\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847556968112332802;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:36:58', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:52:34', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-费用结构', `report_code` = 'example_systemmonitor_fee_structure', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'费用结构\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_4531333\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3401274\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_9964473\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1926508\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2754761\",\"controlType\":\"default\",\"conditionValue\":\"费用结构\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847557539405897730;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:39:45', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:52:12', `is_deleted` = 0, `report_name` = '系统首页-数据看板2-收入结构', `report_code` = 'example_systemmonitor_Income_structure', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据看板2\'\\n  AND tbl_erd.table_type = \'收入结构\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_4422418\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6937330\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5310869\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4639924\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据看板2\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8651128\",\"controlType\":\"default\",\"conditionValue\":\"收入结构\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847558240001466370;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:47:08', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:54:42', `is_deleted` = 0, `report_name` = '系统首页-数据会员-实时访问量', `report_code` = 'example_member_realtime_visits', `group_report_id` = NULL, `java_config` = 'exampleMemberRealtimeVisitsReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1850092582611345409\",\"executeSql\":\"SELECT\\r\\n\\tfkl ssfkl,\\r\\n\\tsj sj\\r\\nFROM\\r\\n\\texample_sjhy_ssfkl\\r\\nWHERE\\r\\n\\tis_deleted = 0\",\"optionData\":{\"optionObj\":{\"select\":[],\"from\":[],\"where\":[],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\",\"example_sjhy_ssfkl\":\"tbl_ess\"},\"typeKey\":\"custom\"}}' WHERE `id` = 1847560096136175617;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:49:02', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:31:52', `is_deleted` = 0, `report_name` = '系统首页-数据会员-销售额', `report_code` = 'example_member_sale', `group_report_id` = NULL, `java_config` = 'exampleMemberSaleReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1850094920386371586\",\"executeSql\":\"SELECT\\r\\n\\txse xse,\\r\\n\\trq sj\\r\\nFROM\\r\\n\\texample_sjhy_xse\\r\\nWHERE\\r\\n\\tis_deleted = 0\",\"optionData\":{\"optionObj\":{\"select\":[],\"from\":[],\"where\":[],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\",\"example_sjhy_xse\":\"tbl_esx\"},\"typeKey\":\"custom\"}}' WHERE `id` = 1847560577587748866;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:54:52', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:56:19', `is_deleted` = 0, `report_name` = '系统首页-数据会员-各品类占比', `report_code` = 'example_member_product_percentage', `group_report_id` = NULL, `java_config` = 'exampleMemberProductPercentageReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1850109346514632706\",\"executeSql\":\"SELECT\\r\\n\\tsm,\\r\\n\\tnz,\\r\\n\\tmy,\\r\\n\\txb,\\r\\n\\trq sj,\\r\\n\\tje\\r\\nFROM\\r\\n\\texample_sjhy_gplzb \\r\\nWHERE\\r\\n\\tis_deleted = 0\",\"optionData\":{\"optionObj\":{\"select\":[],\"from\":[],\"where\":[],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\",\"example_sjhy_gplzb\":\"tbl_esg\"},\"typeKey\":\"custom\"}}' WHERE `id` = 1847562045543817218;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 16:58:56', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:50:40', `is_deleted` = 0, `report_name` = '系统首页-数据首页-店铺业绩', `report_code` = 'example_datahome_store_performance', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据首页\'\\n  AND tbl_erd.table_type = \'店铺业绩\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2165318\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3278099\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5718651\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5128590\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据首页\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3770594\",\"controlType\":\"default\",\"conditionValue\":\"店铺业绩\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847563067897028610;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 17:00:37', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:23:33', `is_deleted` = 0, `report_name` = '系统首页-数据首页-销售数据', `report_code` = 'example_datahome_sales_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据首页\'\\n  AND tbl_erd.table_type = \'销售数据\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_4904665\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_9813150\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_3160796\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3383733\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据首页\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2702475\",\"controlType\":\"default\",\"conditionValue\":\"销售数据\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847563490544459778;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 17:04:52', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:50:04', `is_deleted` = 0, `report_name` = '系统首页-数据首页-本周数据详情', `report_code` = 'example_datahome_thisweek_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据首页\'\\n  AND tbl_erd.table_type = \'本周数据详情\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_3172423\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_1000473\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5661428\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9669886\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据首页\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3963024\",\"controlType\":\"default\",\"conditionValue\":\"本周数据详情\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847564562159771649;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-19 17:07:20', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:49:44', `is_deleted` = 0, `report_name` = '系统首页-数据首页-上周综合提升', `report_code` = 'example_datahome_lastweek_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页数据首页\'\\n  AND tbl_erd.table_type = \'上周综合提升\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2387406\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7310409\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_196593\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4945004\",\"controlType\":\"default\",\"conditionValue\":\"系统首页数据首页\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9326966\",\"controlType\":\"default\",\"conditionValue\":\"上周综合提升\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1847565182564442113;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-22 14:41:21', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:49:09', `is_deleted` = 0, `report_name` = '系统首页-商户统计-商户分布', `report_code` = 'example_trader_merchant_distribution', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'商户分布\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_3198271\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5982266\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_1537509\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_6693060\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3266334\",\"controlType\":\"default\",\"conditionValue\":\"商户分布\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1848615607371812865;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-22 14:45:26', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-26 10:48:49', `is_deleted` = 0, `report_name` = '系统首页-商户统计-交易漏斗', `report_code` = 'example_trader_transaction_funneldiagram', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页商户统计\'\\n  AND tbl_erd.table_type = \'交易漏斗\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_6715149\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7422046\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2751707\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4794919\",\"controlType\":\"default\",\"conditionValue\":\"系统首页商户统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5026857\",\"controlType\":\"default\",\"conditionValue\":\"交易漏斗\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1848616633076273154;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 14:20:05', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:24:49', `is_deleted` = 0, `report_name` = '系统首页-首页概览-转化率', `report_code` = 'example_overview_conversion', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'转化率\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_5665411\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_3730703\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_6506735\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5339483\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_2468538\",\"controlType\":\"default\",\"conditionValue\":\"转化率\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_today_data\":\"tbl_etd\",\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849335032441905154;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 14:46:21', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-24 14:51:37', `is_deleted` = 0, `report_name` = '系统首页-首页概览-实时数据', `report_code` = 'example_overview_realtime_data', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'实时数据\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1177523\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_405810\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_93254\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_653354\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4522098\",\"controlType\":\"default\",\"conditionValue\":\"实时数据\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849341642828587010;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 15:07:26', `create_dept` = 101, `update_user` = NULL, `update_time` = '2024-10-24 15:07:26', `is_deleted` = 0, `report_name` = '系统首页-首页概览-智能助手', `report_code` = 'example_overview_intelligent_assistants', `group_report_id` = NULL, `java_config` = 'exampleXtsyFormReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'智能助手\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_3322310\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7008306\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2054759\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8299718\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_9275583\",\"controlType\":\"default\",\"conditionValue\":\"智能助手\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849346947750731777;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 15:23:16', `create_dept` = 101, `update_user` = NULL, `update_time` = '2024-10-24 15:23:16', `is_deleted` = 0, `report_name` = '系统首页-首页概览-智能助手待处理', `report_code` = 'example_overview_pending', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'智能助手待处理\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_6935887\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_6582734\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5548534\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_644829\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_7794184\",\"controlType\":\"default\",\"conditionValue\":\"智能助手待处理\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849350930384986114;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 15:29:34', `create_dept` = 101, `update_user` = NULL, `update_time` = '2024-10-24 15:29:34', `is_deleted` = 0, `report_name` = '系统首页-首页概览-服务通知', `report_code` = 'example_overview_service_notices', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'服务通知\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1761448\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_1770234\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_8372154\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_824956\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_7777219\",\"controlType\":\"default\",\"conditionValue\":\"服务通知\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849352515299221505;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 15:36:33', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-09 10:00:20', `is_deleted` = 0, `report_name` = '系统首页-首页概览-规则中心', `report_code` = 'example_overview_rulescenter', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'规则中心\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_147796\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_5586232\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_7105583\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8549800\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5176110\",\"controlType\":\"default\",\"conditionValue\":\"规则中心\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849354274285461506;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 15:41:26', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 10:03:44', `is_deleted` = 0, `report_name` = '系统首页-首页概览-产品动态', `report_code` = 'example_overview_productnews', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'产品动态\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_2920363\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7544005\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_5870128\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_1345979\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_4289856\",\"controlType\":\"default\",\"conditionValue\":\"产品动态\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849355503283642369;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-24 15:46:04', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-09 09:59:25', `is_deleted` = 0, `report_name` = '系统首页-首页概览-学习中心', `report_code` = 'example_overview_learningcenter', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'学习中心\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1736527\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_8608209\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2315494\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8087389\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5366843\",\"controlType\":\"default\",\"conditionValue\":\"学习中心\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849356669027524609;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-25 15:51:50', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-10-25 15:56:32', `is_deleted` = 0, `report_name` = '系统首页-首页概览-商家', `report_code` = 'example_overview_merchant', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'商家\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1736527\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_8608209\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2315494\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8087389\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5366843\",\"controlType\":\"default\",\"conditionValue\":\"商家\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849720504500207617;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-25 16:02:57', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-09 09:58:34', `is_deleted` = 0, `report_name` = '系统首页-首页概览-商家经验数量', `report_code` = 'example_overview_merchant_cou', `group_report_id` = NULL, `java_config` = 'exampleXtsyLineReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"1847167244620152834\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页首页概览\'\\n  AND tbl_erd.table_type = \'商家经验数量\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_1736527\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_8608209\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_2315494\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_8087389\",\"controlType\":\"default\",\"conditionValue\":\"系统首页首页概览\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5366843\",\"controlType\":\"default\",\"conditionValue\":\"商家经验数量\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1849723305129263105;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-30 16:58:21', `create_dept` = 101, `update_user` = 1, `update_time` = '2025-01-07 09:24:37', `is_deleted` = 0, `report_name` = '系统首页-使用统计-登录使用人数', `report_code` = 'example_sytj_dlsyrs', `group_report_id` = NULL, `java_config` = 'exampleTrafficStatisticsReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"\",\"executeSql\":\"\",\"optionData\":{\"optionObj\":{\"select\":[],\"from\":[],\"where\":[],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_demo\":\"tbl_ed\"},\"typeKey\":\"option\"}}' WHERE `id` = 1851549188884660226;

UPDATE `lowcode_report` SET `tenant_id` = 1, `create_user` = 1, `create_time` = '2024-10-31 14:41:55', `create_dept` = 101, `update_user` = 1, `update_time` = '2024-11-04 15:32:59', `is_deleted` = 0, `report_name` = '系统首页-使用统计-使用人数最多模块', `report_code` = 'example_sytj_syzdmk', `group_report_id` = NULL, `java_config` = 'exampleSytjSyzdmkReportEnhance', `data_config` = 'page,authFalse', `table_config` = 'height,header,menu,index,border', `data_sources_config` = '{\"dataOrigin\":\"\",\"executeSql\":\"SELECT\\n  tbl_erd.value\\nFROM\\n  example_report_data tbl_erd\\nWHERE\\n  tbl_erd.is_deleted = 0\\n  AND tbl_erd.type_index = \'系统首页使用统计\'\\n  AND tbl_erd.table_type = \'使用最多模块\'\",\"optionData\":{\"optionObj\":{\"select\":[{\"label\":\"value（json数据）\",\"value\":\"value\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Text\",\"prop\":\"option_5830526\",\"controlType\":\"text\",\"alias\":\"\"}],\"from\":[{\"tableName\":\"example_report_data\",\"type\":\"table\",\"prop\":\"option_7111734\",\"controlType\":\"text\",\"connectType\":\"INNER JOIN\"}],\"where\":[{\"label\":\"is_deleted（是否删除）\",\"value\":\"is_deleted\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"Integer\",\"prop\":\"option_9099601\",\"controlType\":\"default\",\"conditionValue\":\"0\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"type_index（首页类型）\",\"value\":\"type_index\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_5223893\",\"controlType\":\"default\",\"conditionValue\":\"系统首页使用统计\",\"condition\":\"EQ\",\"width_where\":\"44px\"},{\"label\":\"table_type（报表类型）\",\"value\":\"table_type\",\"tableName\":\"example_report_data\",\"type\":\"field\",\"fieldType\":\"String\",\"prop\":\"option_3337900\",\"controlType\":\"default\",\"conditionValue\":\"使用最多模块\",\"condition\":\"EQ\",\"width_where\":\"44px\"}],\"groupBy\":\"\",\"having\":\"\",\"orderBy\":[]},\"aliasObj\":{\"example_report_data\":\"tbl_erd\"},\"typeKey\":\"option\"}}' WHERE `id` = 1851877239195578370;

DELETE FROM `lowcode_report_field` WHERE `id` = 1846840290047070210;

DELETE FROM `lowcode_report_field` WHERE `id` = 1846840290047070211;

DELETE FROM `lowcode_report_field` WHERE `id` = 1851920004315045889;

DELETE FROM `lowcode_report_field` WHERE `id` = 1853354498272165889;

DELETE FROM `lowcode_report_field` WHERE `id` = 1853364215526653953;

DELETE FROM `lowcode_report_field` WHERE `id` = 1853611411517976577;

DELETE FROM `lowcode_report_field` WHERE `id` = 1853623625841864706;

DELETE FROM `lowcode_report_field` WHERE `id` = 1853634967202131970;

DELETE FROM `lowcode_report_field` WHERE `id` = 1854056873144225794;

DELETE FROM `lowcode_report_field` WHERE `id` = 1854056999048843265;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:21:37', `is_deleted` = 0, `report_id` = 1847188299477860353, `field_code` = 'sj', `field_name` = '日期', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847188299800821761;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:21:52', `is_deleted` = 0, `report_id` = 1847211713336938498, `field_code` = 'sj', `field_name` = '日期', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847211713601179649;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:22:05', `is_deleted` = 0, `report_id` = 1847214874885853186, `field_code` = 'sj', `field_name` = '日期', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847214875200425985;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:23:09', `is_deleted` = 0, `report_id` = 1847522676099391489, `field_code` = 'sj', `field_name` = '日期', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847522676392992769;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:22:17', `is_deleted` = 0, `report_id` = 1847523207228301314, `field_code` = 'sj', `field_name` = '日期', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847523207517708289;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:24:25', `is_deleted` = 0, `report_id` = 1847531061758795778, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847531062039814145;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:22:32', `is_deleted` = 0, `report_id` = 1847532664964390914, `field_code` = 'sj', `field_name` = '周', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847532665241214978;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:22:44', `is_deleted` = 0, `report_id` = 1847533689980985346, `field_code` = 'sj', `field_name` = '周', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847533690253615105;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:22:57', `is_deleted` = 0, `report_id` = 1847534747478269953, `field_code` = 'sj', `field_name` = '日期', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847534747767676930;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:24:43', `is_deleted` = 0, `report_id` = 1847540171430567937, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847540171636088833;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:24:56', `is_deleted` = 0, `report_id` = 1847540601183150081, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847540601447391234;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:25:14', `is_deleted` = 0, `report_id` = 1847541410490884097, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847541410834817026;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:25:31', `is_deleted` = 0, `report_id` = 1847541972397596673, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847541973035130882;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:23:20', `is_deleted` = 0, `report_id` = 1847556968112332802, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847556968443682817;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:23:44', `is_deleted` = 0, `report_id` = 1847560096136175617, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847560096329113601;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:23:56', `is_deleted` = 0, `report_id` = 1847560577587748866, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847560577847795714;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:23:33', `is_deleted` = 0, `report_id` = 1847563490544459778, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1847563490821283841;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:23:56', `is_deleted` = 0, `report_id` = 1847525621318688769, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 2, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1848604803230584833;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:24:09', `is_deleted` = 0, `report_id` = 1847562045543817218, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 2, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849284397768024066;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:24:49', `is_deleted` = 0, `report_id` = 1849335032441905154, `field_code` = 'sj', `field_name` = '时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849335032722923522;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-09 10:00:20', `is_deleted` = 0, `report_id` = 1849354274285461506, `field_code` = 'type', `field_name` = '状态', `field_type` = 'String', `sort_num` = 2, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849354274604228611;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:03:44', `is_deleted` = 0, `report_id` = 1849355503283642369, `field_code` = 'title', `field_name` = '内容', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849355503635963905;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 10:03:44', `is_deleted` = 0, `report_id` = 1849355503283642369, `field_code` = 'type', `field_name` = '状态', `field_type` = 'String', `sort_num` = 2, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849355503635963906;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-09 09:59:25', `is_deleted` = 0, `report_id` = 1849356669027524609, `field_code` = 'type', `field_name` = '状态', `field_type` = 'String', `sort_num` = 2, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849356669321125891;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-09 09:58:34', `is_deleted` = 0, `report_id` = 1849723305129263105, `field_code` = 'cou', `field_name` = '数量', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849723307675205633;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = NULL, `is_deleted` = 1, `report_id` = 1849723305129263105, `field_code` = 'content', `field_name` = '内容', `field_type` = 'String', `sort_num` = 2, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1849723307675205634;

UPDATE `lowcode_report_field` SET `tenant_id` = NULL, `create_user` = NULL, `create_time` = NULL, `create_dept` = NULL, `update_user` = NULL, `update_time` = '2025-01-07 09:24:37', `is_deleted` = 0, `report_id` = 1851549188884660226, `field_code` = 'sj', `field_name` = '日期时间', `field_type` = 'String', `sort_num` = 1, `query_is_db` = 'N', `query_is_web` = 'N', `query_mode` = 'LIKE', `dict_code` = '', `is_export` = 'Y', `is_show_sort` = 'N' WHERE `id` = 1851549189287313410;

DELETE FROM `qrtz_scheduler_state` WHERE `SCHED_NAME` = 'schedulerName' AND `INSTANCE_NAME` = 'WIN-20200825TYM1735364769255';

INSERT INTO `qrtz_scheduler_state`(`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`) VALUES ('schedulerName', 'WIN-20200825TYM1737358167568', 1737358205836, 15000);

UPDATE `qrtz_triggers` SET `JOB_NAME` = 'clearExcelDataJob', `JOB_GROUP` = 'DEFAULT', `DESCRIPTION` = NULL, `NEXT_FIRE_TIME` = 1737388800000, `PREV_FIRE_TIME` = 1737336488237, `PRIORITY` = 5, `TRIGGER_STATE` = 'WAITING', `TRIGGER_TYPE` = 'CRON', `START_TIME` = 1730961513000, `END_TIME` = 0, `CALENDAR_NAME` = NULL, `MISFIRE_INSTR` = 0, `JOB_DATA` = 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000C7800 WHERE `SCHED_NAME` = 'schedulerName' AND `TRIGGER_NAME` = 'clearExcelDataJob' AND `TRIGGER_GROUP` = 'DEFAULT';

UPDATE `qrtz_triggers` SET `JOB_NAME` = 'saveModelUsageRecordsJob', `JOB_GROUP` = 'DEFAULT', `DESCRIPTION` = NULL, `NEXT_FIRE_TIME` = 1737358800000, `PREV_FIRE_TIME` = 1737358200000, `PRIORITY` = 5, `TRIGGER_STATE` = 'WAITING', `TRIGGER_TYPE` = 'CRON', `START_TIME` = 1730961591000, `END_TIME` = 0, `CALENDAR_NAME` = NULL, `MISFIRE_INSTR` = 0, `JOB_DATA` = 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000C7800 WHERE `SCHED_NAME` = 'schedulerName' AND `TRIGGER_NAME` = 'saveModelUsageRecordsJob' AND `TRIGGER_GROUP` = 'DEFAULT';

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4578, 100, '', 1, 2, 'admin', 0, '192.168.5.243', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-18 16:13:38', NULL, '2025-01-18 16:13:38', 0, 1);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4579, 100, '', 1, 2, 'admin', 0, '192.168.5.30', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0', NULL, '2025-01-20 09:29:26', NULL, '2025-01-20 09:29:26', 0, 1);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4580, 100, '', 148, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 09:31:02', NULL, '2025-01-20 09:31:02', 0, 157);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4581, 200, '', 148, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', '148', '2025-01-20 09:31:38', '148', '2025-01-20 09:31:38', 0, 157);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4582, 100, '', 1, 2, 'admin', 0, '192.168.5.243', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 09:31:40', NULL, '2025-01-20 09:31:40', 0, 1);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4583, 100, '', 148, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 09:31:48', NULL, '2025-01-20 09:31:48', 0, 157);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4584, 100, '', 1, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 10:03:15', NULL, '2025-01-20 10:03:15', 0, 1);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4585, 100, '', 149, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 11:07:29', NULL, '2025-01-20 11:07:29', 0, 158);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4586, 200, '', 149, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', '149', '2025-01-20 11:07:46', '149', '2025-01-20 11:07:46', 0, 158);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4587, 100, '', 149, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 11:07:52', NULL, '2025-01-20 11:07:52', 0, 158);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4588, 200, '', 149, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', '149', '2025-01-20 11:09:49', '149', '2025-01-20 11:09:49', 0, 158);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4589, 100, '', 148, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', NULL, '2025-01-20 11:11:01', NULL, '2025-01-20 11:11:01', 0, 157);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4590, 200, '', 148, 2, 'admin', 0, '192.168.5.212', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36', '148', '2025-01-20 11:11:10', '148', '2025-01-20 11:11:10', 0, 157);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4591, 200, '', 1, 2, 'admin', 0, '192.168.5.30', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0', '1', '2025-01-20 14:44:22', '1', '2025-01-20 14:44:22', 0, 1);

INSERT INTO `system_login_log`(`id`, `log_type`, `trace_id`, `user_id`, `user_type`, `username`, `result`, `user_ip`, `user_agent`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (4592, 100, '', 1, 2, 'admin', 0, '192.168.5.30', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0', NULL, '2025-01-20 14:44:30', NULL, '2025-01-20 14:44:30', 0, 1);

DELETE FROM `system_menu` WHERE `id` = 1090;

DELETE FROM `system_menu` WHERE `id` = 1237;

DELETE FROM `system_menu` WHERE `id` = 1238;

DELETE FROM `system_menu` WHERE `id` = 1239;

DELETE FROM `system_menu` WHERE `id` = 1240;

DELETE FROM `system_menu` WHERE `id` = 1241;

DELETE FROM `system_menu` WHERE `id` = 1242;

DELETE FROM `system_menu` WHERE `id` = 1243;

DELETE FROM `system_menu` WHERE `id` = 2930;

DELETE FROM `system_menu` WHERE `id` = 2931;

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (5, 'OA 示例', '', 1, 40, 1185, 'oa', 'eos-icons:content-lifecycle-management', NULL, NULL, 0, 1, 1, 1, 'admin', '2021-09-20 16:26:19', '1', '2024-08-08 17:20:44', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1078, '访问日志', '', 2, 1, 1083, 'api-access-log', 'bi:journal-text', 'infra/apiAccessLog/index', 'InfraApiAccessLog', 0, 1, 1, 1, '', '2021-02-26 01:32:59', '1', '2024-08-08 16:54:35', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1082, '日志导出', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', NULL, 0, 1, 1, 1, '', '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1083, 'API 日志', '', 1, 8, 2, 'log', 'hugeicons:api', NULL, NULL, 0, 1, 1, 1, '', '2021-02-26 02:18:24', '1', '2024-08-08 16:52:43', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1084, '错误日志', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', 'bi:journal-x', 'infra/apiErrorLog/index', 'InfraApiErrorLog', 0, 1, 1, 1, '', '2021-02-26 07:53:20', '1', '2024-08-08 16:54:16', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1085, '日志处理', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', NULL, 0, 1, 1, 1, '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1086, '日志导出', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', NULL, 0, 1, 1, 1, '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1088, '日志查询', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', NULL, 0, 1, 1, 1, '1', '2021-03-10 01:28:04', '1', '2022-04-20 17:03:10', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1089, '日志查询', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', NULL, 0, 1, 1, 1, '1', '2021-03-10 01:29:09', '1', '2022-04-20 17:03:10', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1118, '请假查询', '', 2, 0, 5, 'leave', 'icon-park-outline:history-query', 'bpm/oa/leave/index', 'BpmOALeave', 0, 1, 1, 1, '', '2021-09-20 08:51:03', '1', '2024-08-08 17:26:22', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1119, '请假申请查询', 'bpm:oa-leave:query', 3, 1, 1118, '', '', '', NULL, 0, 1, 1, 1, '', '2021-09-20 08:51:03', '1', '2022-04-20 17:03:10', 0);

INSERT INTO `system_menu`(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1120, '请假申请创建', 'bpm:oa-leave:create', 3, 2, 1118, '', '', '', NULL, 0, 1, 1, 1, '', '2021-09-20 08:51:03', '1', '2022-04-20 17:03:10', 0);

UPDATE `system_menu` SET `name` = '审计日志', `permission` = '', `type` = 1, `sort` = 9, `parent_id` = 1, `path` = 'log', `icon` = 'icon-park-solid:audit', `component` = '', `component_name` = NULL, `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = 'admin', `create_time` = '2021-01-05 17:03:48', `updater` = '1', `update_time` = '2024-08-08 16:20:17', `deleted` = 0 WHERE `id` = 108;

UPDATE `system_menu` SET `name` = 'MySQL 监控', `permission` = '', `type` = 2, `sort` = 9, `parent_id` = 2, `path` = 'druid', `icon` = 'eos-icons:monitoring', `component` = 'infra/druid/index', `component_name` = 'InfraDruid', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = 'admin', `create_time` = '2021-01-05 17:03:48', `updater` = '1', `update_time` = '2024-08-08 16:55:21', `deleted` = 0 WHERE `id` = 111;

UPDATE `system_menu` SET `name` = 'Java 监控', `permission` = '', `type` = 2, `sort` = 11, `parent_id` = 2, `path` = 'admin-server', `icon` = 'material-symbols-light:monitoring-rounded', `component` = 'infra/server/index', `component_name` = 'InfraAdminServer', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = 'admin', `create_time` = '2021-01-05 17:03:48', `updater` = '1', `update_time` = '2024-08-08 16:55:56', `deleted` = 0 WHERE `id` = 112;

UPDATE `system_menu` SET `name` = 'Redis 监控', `permission` = '', `type` = 2, `sort` = 10, `parent_id` = 2, `path` = 'redis', `icon` = 'carbon:cloud-monitoring', `component` = 'infra/redis/index', `component_name` = 'InfraRedis', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = 'admin', `create_time` = '2021-01-05 17:03:48', `updater` = '1', `update_time` = '2024-08-08 16:55:39', `deleted` = 0 WHERE `id` = 113;

UPDATE `system_menu` SET `name` = '地区管理', `permission` = '', `type` = 2, `sort` = 14, `parent_id` = 1, `path` = 'area', `icon` = 'iconify mdi--select-location', `component` = 'system/area/index', `component_name` = 'SystemArea', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = '1', `create_time` = '2022-12-23 17:35:05', `updater` = '1', `update_time` = '2024-08-09 14:06:56', `deleted` = 0 WHERE `id` = 2083;

UPDATE `system_menu` SET `name` = '表单设计示例', `permission` = '', `type` = 1, `sort` = 11, `parent_id` = 2835, `path` = 'formexample', `icon` = 'fluent:form-28-regular', `component` = '', `component_name` = '', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = '1', `create_time` = '2024-07-15 09:40:22', `updater` = '1', `update_time` = '2025-01-20 14:07:50', `deleted` = 0 WHERE `id` = 2765;

UPDATE `system_menu` SET `name` = '表单开发示例', `permission` = '', `type` = 1, `sort` = 10, `parent_id` = 2835, `path` = 'tableexample', `icon` = 'fluent:form-28-regular', `component` = '', `component_name` = '', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = '1', `create_time` = '2024-09-05 14:10:30', `updater` = '1', `update_time` = '2025-01-20 14:07:29', `deleted` = 0 WHERE `id` = 2810;

UPDATE `system_menu` SET `name` = '请求日志', `permission` = '', `type` = 1, `sort` = 9, `parent_id` = 1, `path` = 'apilog', `icon` = 'ep:paperclip', `component` = '', `component_name` = '', `status` = 0, `visible` = 1, `keep_alive` = 1, `always_show` = 1, `creator` = '1', `create_time` = '2024-09-10 14:49:41', `updater` = '1', `update_time` = '2024-09-11 09:44:06', `deleted` = 0 WHERE `id` = 2827;

DELETE FROM `system_notice` WHERE `id` = 1;

DELETE FROM `system_notice` WHERE `id` = 4;

DELETE FROM `system_notice` WHERE `id` = 5;

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19339, 1, 2, '26c4b7ff90d1431d80aa755527cd6c96', 'e9ef8abe903745269bfab9f86863a85e', 'default', NULL, '2025-01-18 16:43:38', NULL, '2025-01-18 16:13:38', NULL, '2025-01-18 16:43:40', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19340, 1, 2, '369067c47927400eb16adb2e05ee44e2', 'e9ef8abe903745269bfab9f86863a85e', 'default', NULL, '2025-01-18 17:13:39', NULL, '2025-01-18 16:43:39', NULL, '2025-01-18 17:18:45', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19341, 1, 2, '816f6ca453a14e9589830e6c2aa9ef1a', 'e9ef8abe903745269bfab9f86863a85e', 'default', NULL, '2025-01-18 17:48:44', NULL, '2025-01-18 17:18:44', NULL, '2025-01-18 17:49:49', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19342, 1, 2, 'e5d4b094f628437db01a85a78e8171ef', 'e9ef8abe903745269bfab9f86863a85e', 'default', NULL, '2025-01-18 18:19:49', NULL, '2025-01-18 17:49:49', NULL, '2025-01-18 17:49:49', 0, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19343, 1, 2, 'a6147dbc6167487baab9f143dcc7f25a', 'e9ef8abe903745269bfab9f86863a85e', 'default', NULL, '2025-01-18 18:19:49', NULL, '2025-01-18 17:49:49', NULL, '2025-01-18 17:49:49', 0, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19344, 1, 2, '488caf189a5843889e9ccd8c68eb129a', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 09:59:26', NULL, '2025-01-20 09:29:26', NULL, '2025-01-20 10:03:31', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19345, 148, 2, 'd3a781c794db41dea2984edc8285d1b8', '334167cf550d4faba1d75ffd599e751e', 'default', NULL, '2025-01-20 10:01:02', NULL, '2025-01-20 09:31:02', NULL, '2025-01-20 09:31:36', 1, 157);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19346, 1, 2, '6c54d185489840aca8611a4f945beb8a', '71e1113ff4e94e1a964aae52cc7e0713', 'default', NULL, '2025-01-20 10:01:40', NULL, '2025-01-20 09:31:40', NULL, '2025-01-20 09:31:40', 0, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19347, 148, 2, '6787a171615945c8aac271fbe0d5eb91', 'c33c18cafc0140c09ba9a1bd2acb55fd', 'default', NULL, '2025-01-20 10:01:48', NULL, '2025-01-20 09:31:48', NULL, '2025-01-20 09:31:48', 0, 157);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19348, 1, 2, '154b452e9a354f728944c0be1c5263df', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 10:33:15', NULL, '2025-01-20 10:03:15', NULL, '2025-01-20 10:33:16', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19349, 1, 2, 'b883bd02043b424989394fa4df24630c', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 10:33:33', NULL, '2025-01-20 10:03:33', NULL, '2025-01-20 10:34:05', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19350, 1, 2, '2af38c57cb67444fb364384b529bf6a6', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 11:03:18', NULL, '2025-01-20 10:33:18', NULL, '2025-01-20 11:03:16', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19351, 1, 2, 'ad14ab7be9184b4eb40620e07e9173af', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 11:04:07', NULL, '2025-01-20 10:34:07', NULL, '2025-01-20 11:04:36', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19352, 1, 2, '20b8a7eeb455463f85dd760089a01c42', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 11:33:18', NULL, '2025-01-20 11:03:18', NULL, '2025-01-20 11:34:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19353, 1, 2, 'f68ecf777f5748028ccddac2c6d99c94', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 11:34:38', NULL, '2025-01-20 11:04:38', NULL, '2025-01-20 11:36:05', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19354, 149, 2, '5f50f852a2f64b1cb2fcff8d9da5fd1d', '353239ca120c446aa7e92cf5adac1ecb', 'default', NULL, '2025-01-20 11:37:29', NULL, '2025-01-20 11:07:29', NULL, '2025-01-20 11:07:44', 1, 158);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19355, 149, 2, '5231c6bfb4344d518114721f16c17753', '784a0677c53c44d193164ea697db8c04', 'default', NULL, '2025-01-20 11:37:52', NULL, '2025-01-20 11:07:52', NULL, '2025-01-20 11:09:47', 1, 158);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19356, 148, 2, '5055a0e3b27d42afa46effe655e42cc6', '13a30523ed9e47f9a2a79e3aae1af5f2', 'default', NULL, '2025-01-20 11:41:01', NULL, '2025-01-20 11:11:01', NULL, '2025-01-20 11:11:08', 1, 157);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19357, 1, 2, '2fb6db8fae624361b5d5b17bd2223687', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 12:04:04', NULL, '2025-01-20 11:34:04', NULL, '2025-01-20 12:04:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19358, 1, 2, 'c18b5038d6444f20a1dbe2be3155e4bb', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 12:06:05', NULL, '2025-01-20 11:36:05', NULL, '2025-01-20 12:06:05', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19359, 1, 2, '81ec99caa9524614add6b9849d734297', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 12:34:04', NULL, '2025-01-20 12:04:04', NULL, '2025-01-20 12:34:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19360, 1, 2, '5c64d9fa05a54361b809ea1241eef2ca', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 12:36:05', NULL, '2025-01-20 12:06:05', NULL, '2025-01-20 12:36:05', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19361, 1, 2, '5dd2883b3f88406a970f0a5c8c8fad10', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 13:04:04', NULL, '2025-01-20 12:34:04', NULL, '2025-01-20 13:04:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19362, 1, 2, '7465217f6bec481fb250a438c2585a92', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 13:06:05', NULL, '2025-01-20 12:36:05', NULL, '2025-01-20 13:31:49', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19363, 1, 2, '393f29b23fc641cba8f2e53c5cef5fbe', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 13:34:04', NULL, '2025-01-20 13:04:04', NULL, '2025-01-20 13:34:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19364, 1, 2, 'e52b4a17d5174da08a8fcef7ef4a2b31', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 14:01:50', NULL, '2025-01-20 13:31:50', NULL, '2025-01-20 14:01:54', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19365, 1, 2, 'ea513dae8f0846b89065def443e0ade9', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 14:04:04', NULL, '2025-01-20 13:34:04', NULL, '2025-01-20 14:05:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19366, 1, 2, 'a07f0b588cac40e3b219e1379b29524c', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 14:31:55', NULL, '2025-01-20 14:01:55', NULL, '2025-01-20 14:32:05', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19367, 1, 2, 'ad99225bf2ff464f88b79255dce0e561', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 14:35:04', NULL, '2025-01-20 14:05:04', NULL, '2025-01-20 14:35:04', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19368, 1, 2, '6a4c5af6f39f479382650f2c9c2553ec', 'da73606868a9483d8af16bb3b3676355', 'default', NULL, '2025-01-20 15:02:05', NULL, '2025-01-20 14:32:05', NULL, '2025-01-20 14:44:21', 1, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19369, 1, 2, 'd40e10a1db934bc08395edf6abb52651', 'c9b5286a8d3343f1bf426070aea5f010', 'default', NULL, '2025-01-20 15:05:04', NULL, '2025-01-20 14:35:04', NULL, '2025-01-20 14:35:04', 0, 1);

INSERT INTO `system_oauth2_access_token`(`id`, `user_id`, `user_type`, `access_token`, `refresh_token`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (19370, 1, 2, 'e8aebea50c1647a49495b74ffa269ff4', '4efa02da121f4757b03218a18b8d96b0', 'default', NULL, '2025-01-20 15:14:30', NULL, '2025-01-20 14:44:30', NULL, '2025-01-20 14:44:30', 0, 1);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2666, 1, 'e9ef8abe903745269bfab9f86863a85e', 2, 'default', NULL, '2025-01-19 04:13:38', NULL, '2025-01-18 16:13:38', NULL, '2025-01-18 16:13:38', 0, 1);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2667, 1, 'da73606868a9483d8af16bb3b3676355', 2, 'default', NULL, '2025-01-20 21:29:26', NULL, '2025-01-20 09:29:26', NULL, '2025-01-20 14:44:21', 1, 1);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2668, 148, '334167cf550d4faba1d75ffd599e751e', 2, 'default', NULL, '2025-01-20 21:31:02', NULL, '2025-01-20 09:31:02', NULL, '2025-01-20 09:31:36', 1, 157);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2669, 1, '71e1113ff4e94e1a964aae52cc7e0713', 2, 'default', NULL, '2025-01-20 21:31:40', NULL, '2025-01-20 09:31:40', NULL, '2025-01-20 09:31:40', 0, 1);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2670, 148, 'c33c18cafc0140c09ba9a1bd2acb55fd', 2, 'default', NULL, '2025-01-20 21:31:48', NULL, '2025-01-20 09:31:48', NULL, '2025-01-20 09:31:48', 0, 157);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2671, 1, 'c9b5286a8d3343f1bf426070aea5f010', 2, 'default', NULL, '2025-01-20 22:03:15', NULL, '2025-01-20 10:03:15', NULL, '2025-01-20 10:03:15', 0, 1);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2672, 149, '353239ca120c446aa7e92cf5adac1ecb', 2, 'default', NULL, '2025-01-20 23:07:29', NULL, '2025-01-20 11:07:29', NULL, '2025-01-20 11:07:44', 1, 158);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2673, 149, '784a0677c53c44d193164ea697db8c04', 2, 'default', NULL, '2025-01-20 23:07:52', NULL, '2025-01-20 11:07:52', NULL, '2025-01-20 11:09:47', 1, 158);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2674, 148, '13a30523ed9e47f9a2a79e3aae1af5f2', 2, 'default', NULL, '2025-01-20 23:11:01', NULL, '2025-01-20 11:11:01', NULL, '2025-01-20 11:11:08', 1, 157);

INSERT INTO `system_oauth2_refresh_token`(`id`, `user_id`, `refresh_token`, `user_type`, `client_id`, `scopes`, `expires_time`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (2675, 1, '4efa02da121f4757b03218a18b8d96b0', 2, 'default', NULL, '2025-01-21 02:44:30', NULL, '2025-01-20 14:44:30', NULL, '2025-01-20 14:44:30', 0, 1);

DELETE FROM `system_operate_log` WHERE `id` = 1;

DELETE FROM `system_operate_log` WHERE `id` = 2;

DELETE FROM `system_operate_log` WHERE `id` = 3;

DELETE FROM `system_operate_log` WHERE `id` = 4;

DELETE FROM `system_operate_log` WHERE `id` = 5;

DELETE FROM `system_operate_log` WHERE `id` = 6;

DELETE FROM `system_operate_log` WHERE `id` = 7;

DELETE FROM `system_operate_log` WHERE `id` = 8;

DELETE FROM `system_operate_log` WHERE `id` = 9;

DELETE FROM `system_operate_log` WHERE `id` = 10;

DELETE FROM `system_operate_log` WHERE `id` = 11;

DELETE FROM `system_operate_log` WHERE `id` = 12;

DELETE FROM `system_operate_log` WHERE `id` = 13;

DELETE FROM `system_operate_log` WHERE `id` = 14;

DELETE FROM `system_operate_log` WHERE `id` = 15;

DELETE FROM `system_operate_log` WHERE `id` = 16;

DELETE FROM `system_operate_log` WHERE `id` = 17;

DELETE FROM `system_operate_log` WHERE `id` = 18;

DELETE FROM `system_operate_log` WHERE `id` = 19;

DELETE FROM `system_operate_log` WHERE `id` = 20;

DELETE FROM `system_operate_log` WHERE `id` = 21;

DELETE FROM `system_operate_log` WHERE `id` = 22;

DELETE FROM `system_operate_log` WHERE `id` = 23;

DELETE FROM `system_operate_log` WHERE `id` = 24;

DELETE FROM `system_operate_log` WHERE `id` = 25;

DELETE FROM `system_operate_log` WHERE `id` = 26;

DELETE FROM `system_operate_log` WHERE `id` = 27;

DELETE FROM `system_operate_log` WHERE `id` = 28;

DELETE FROM `system_operate_log` WHERE `id` = 29;

DELETE FROM `system_operate_log` WHERE `id` = 30;

DELETE FROM `system_operate_log` WHERE `id` = 31;

DELETE FROM `system_operate_log` WHERE `id` = 32;

DELETE FROM `system_operate_log` WHERE `id` = 33;

DELETE FROM `system_operate_log` WHERE `id` = 34;

DELETE FROM `system_operate_log` WHERE `id` = 35;

DELETE FROM `system_operate_log` WHERE `id` = 36;

DELETE FROM `system_operate_log` WHERE `id` = 37;

INSERT INTO `system_role`(`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (151, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role`(`id`, `name`, `code`, `sort`, `data_scope`, `data_scope_dept_ids`, `status`, `type`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (152, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

DELETE FROM `system_role_menu` WHERE `id` = 699;

DELETE FROM `system_role_menu` WHERE `id` = 1498;

DELETE FROM `system_role_menu` WHERE `id` = 1679;

DELETE FROM `system_role_menu` WHERE `id` = 1680;

DELETE FROM `system_role_menu` WHERE `id` = 1681;

DELETE FROM `system_role_menu` WHERE `id` = 1682;

DELETE FROM `system_role_menu` WHERE `id` = 1683;

DELETE FROM `system_role_menu` WHERE `id` = 1684;

DELETE FROM `system_role_menu` WHERE `id` = 1685;

DELETE FROM `system_role_menu` WHERE `id` = 2103;

DELETE FROM `system_role_menu` WHERE `id` = 2104;

DELETE FROM `system_role_menu` WHERE `id` = 2105;

DELETE FROM `system_role_menu` WHERE `id` = 2106;

DELETE FROM `system_role_menu` WHERE `id` = 2107;

DELETE FROM `system_role_menu` WHERE `id` = 2108;

DELETE FROM `system_role_menu` WHERE `id` = 2109;

DELETE FROM `system_role_menu` WHERE `id` = 3024;

DELETE FROM `system_role_menu` WHERE `id` = 3055;

DELETE FROM `system_role_menu` WHERE `id` = 3056;

DELETE FROM `system_role_menu` WHERE `id` = 3057;

DELETE FROM `system_role_menu` WHERE `id` = 3058;

DELETE FROM `system_role_menu` WHERE `id` = 3059;

DELETE FROM `system_role_menu` WHERE `id` = 3060;

DELETE FROM `system_role_menu` WHERE `id` = 3061;

DELETE FROM `system_role_menu` WHERE `id` = 3100;

DELETE FROM `system_role_menu` WHERE `id` = 3131;

DELETE FROM `system_role_menu` WHERE `id` = 3132;

DELETE FROM `system_role_menu` WHERE `id` = 3133;

DELETE FROM `system_role_menu` WHERE `id` = 3134;

DELETE FROM `system_role_menu` WHERE `id` = 3135;

DELETE FROM `system_role_menu` WHERE `id` = 3136;

DELETE FROM `system_role_menu` WHERE `id` = 3137;

DELETE FROM `system_role_menu` WHERE `id` = 3176;

DELETE FROM `system_role_menu` WHERE `id` = 3207;

DELETE FROM `system_role_menu` WHERE `id` = 3208;

DELETE FROM `system_role_menu` WHERE `id` = 3209;

DELETE FROM `system_role_menu` WHERE `id` = 3210;

DELETE FROM `system_role_menu` WHERE `id` = 3211;

DELETE FROM `system_role_menu` WHERE `id` = 3212;

DELETE FROM `system_role_menu` WHERE `id` = 3213;

DELETE FROM `system_role_menu` WHERE `id` = 3275;

DELETE FROM `system_role_menu` WHERE `id` = 3323;

DELETE FROM `system_role_menu` WHERE `id` = 3324;

DELETE FROM `system_role_menu` WHERE `id` = 3325;

DELETE FROM `system_role_menu` WHERE `id` = 3326;

DELETE FROM `system_role_menu` WHERE `id` = 3327;

DELETE FROM `system_role_menu` WHERE `id` = 3328;

DELETE FROM `system_role_menu` WHERE `id` = 3329;

DELETE FROM `system_role_menu` WHERE `id` = 3616;

DELETE FROM `system_role_menu` WHERE `id` = 3617;

DELETE FROM `system_role_menu` WHERE `id` = 3619;

DELETE FROM `system_role_menu` WHERE `id` = 3620;

DELETE FROM `system_role_menu` WHERE `id` = 3621;

DELETE FROM `system_role_menu` WHERE `id` = 3622;

DELETE FROM `system_role_menu` WHERE `id` = 3623;

DELETE FROM `system_role_menu` WHERE `id` = 3960;

DELETE FROM `system_role_menu` WHERE `id` = 4008;

DELETE FROM `system_role_menu` WHERE `id` = 4009;

DELETE FROM `system_role_menu` WHERE `id` = 4010;

DELETE FROM `system_role_menu` WHERE `id` = 4011;

DELETE FROM `system_role_menu` WHERE `id` = 4012;

DELETE FROM `system_role_menu` WHERE `id` = 4013;

DELETE FROM `system_role_menu` WHERE `id` = 4014;

DELETE FROM `system_role_menu` WHERE `id` = 4081;

DELETE FROM `system_role_menu` WHERE `id` = 4129;

DELETE FROM `system_role_menu` WHERE `id` = 4130;

DELETE FROM `system_role_menu` WHERE `id` = 4131;

DELETE FROM `system_role_menu` WHERE `id` = 4132;

DELETE FROM `system_role_menu` WHERE `id` = 4133;

DELETE FROM `system_role_menu` WHERE `id` = 4134;

DELETE FROM `system_role_menu` WHERE `id` = 4135;

DELETE FROM `system_role_menu` WHERE `id` = 4202;

DELETE FROM `system_role_menu` WHERE `id` = 4250;

DELETE FROM `system_role_menu` WHERE `id` = 4251;

DELETE FROM `system_role_menu` WHERE `id` = 4252;

DELETE FROM `system_role_menu` WHERE `id` = 4253;

DELETE FROM `system_role_menu` WHERE `id` = 4254;

DELETE FROM `system_role_menu` WHERE `id` = 4255;

DELETE FROM `system_role_menu` WHERE `id` = 4256;

DELETE FROM `system_role_menu` WHERE `id` = 4467;

DELETE FROM `system_role_menu` WHERE `id` = 5115;

DELETE FROM `system_role_menu` WHERE `id` = 5200;

DELETE FROM `system_role_menu` WHERE `id` = 5201;

DELETE FROM `system_role_menu` WHERE `id` = 5202;

DELETE FROM `system_role_menu` WHERE `id` = 5203;

DELETE FROM `system_role_menu` WHERE `id` = 5204;

DELETE FROM `system_role_menu` WHERE `id` = 5205;

DELETE FROM `system_role_menu` WHERE `id` = 5206;

DELETE FROM `system_role_menu` WHERE `id` = 5354;

DELETE FROM `system_role_menu` WHERE `id` = 5486;

DELETE FROM `system_role_menu` WHERE `id` = 5487;

DELETE FROM `system_role_menu` WHERE `id` = 5488;

DELETE FROM `system_role_menu` WHERE `id` = 5489;

DELETE FROM `system_role_menu` WHERE `id` = 5490;

DELETE FROM `system_role_menu` WHERE `id` = 5491;

DELETE FROM `system_role_menu` WHERE `id` = 5492;

DELETE FROM `system_role_menu` WHERE `id` = 6198;

DELETE FROM `system_role_menu` WHERE `id` = 6199;

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6200, 151, 1024, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6201, 151, 1025, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6202, 151, 1, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6203, 151, 1036, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6204, 151, 1037, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6205, 151, 1038, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6206, 151, 1039, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6207, 151, 100, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6208, 151, 101, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6209, 151, 1063, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6210, 151, 103, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6211, 151, 1064, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6212, 151, 104, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6213, 151, 1001, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6214, 151, 1065, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6215, 151, 1002, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6216, 151, 1003, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6217, 151, 107, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6218, 151, 1004, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6219, 151, 1005, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6220, 151, 1006, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6221, 151, 1007, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6222, 151, 1008, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6223, 151, 1009, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6224, 151, 1010, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6225, 151, 1011, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6226, 151, 1012, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6227, 151, 1017, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6228, 151, 1018, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6229, 151, 1019, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6230, 151, 1020, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6231, 151, 1021, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6232, 151, 1022, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6233, 151, 1023, '1', '2025-01-20 09:30:14', '1', '2025-01-20 09:30:14', 0, 157);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6234, 152, 1024, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6235, 152, 1025, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6236, 152, 1, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6237, 152, 1036, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6238, 152, 1037, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6239, 152, 1038, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6240, 152, 1039, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6241, 152, 100, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6242, 152, 101, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6243, 152, 1063, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6244, 152, 103, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6245, 152, 1064, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6246, 152, 104, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6247, 152, 1001, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6248, 152, 1065, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6249, 152, 1002, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6250, 152, 1003, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6251, 152, 107, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6252, 152, 1004, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6253, 152, 1005, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6254, 152, 1006, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6255, 152, 1007, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6256, 152, 1008, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6257, 152, 1009, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6258, 152, 1010, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6259, 152, 1011, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6260, 152, 1012, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6261, 152, 1017, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6262, 152, 1018, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6263, 152, 1019, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6264, 152, 1020, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6265, 152, 1021, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6266, 152, 1022, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

INSERT INTO `system_role_menu`(`id`, `role_id`, `menu_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (6267, 152, 1023, '1', '2025-01-20 11:07:18', '1', '2025-01-20 11:07:18', 0, 158);

DELETE FROM `system_tenant` WHERE `id` = 156;

DELETE FROM `system_tenant_package` WHERE `id` = 114;


SET FOREIGN_KEY_CHECKS=1;

