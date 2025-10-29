<p align="center">
	<img alt="logo" width=200" src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/logo.png">
</p>
<h4 align="center">在线轻松驾驭99.99%编程挑战。</h4>
<p align="center">
 <img src="https://img.shields.io/badge/JeeLowCode-1.0.0-red.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.7.18-yellow.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/Vue-3.2-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/license-Apache 2.0-green.svg" alt="Downloads">
 <img src="https://gitee.com/jeelowecode/JeeLowCode/badge/star.svg" alt="Downloads">
 <img src="https://img.shields.io/github/stars/jeelowcode/jeelowcode.svg" alt="Downloads">
 <img src="https://gitcode.com/jeelowcode/jeelowcode/star/badge.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/可商用-免费-purple.svg" alt="Downloads">
</p>

<div align="center">
  <p>
    💻 <strong>低代码演示：</strong><a href="http://demo.jeelowcode.com">demo.jeelowcode.com</a> |
     💦️ <strong>自研工作流：</strong><a href="http://flow.jeelowcode.com">flow.jeelowcode.com</a> |
    ☕️ <strong>技术社区：</strong><a href="http://doc.jeelowcode.com">doc.jeelowcode.com</a>
  </p>
</div>

## 📱 项目简介

**JeeLowCode** 是一款基于SpringBoot专为企业打造的低代码开发框架，以低代码为核心，实现快速开发。提供可视化界面，拖拽组件即可搭建应用，无需复杂代码编写，极大提升开发效率。企业可免费使用，灵活适配业务需求，助力高效数字化转型，是企业降本增效的利器。
<br><br>
<img src="https://img.shields.io/badge/注意:-JeeLowCode 可以用于个人或公司的项目，不允许任何形式的二次开源并且禁止售卖源代码-red.svg" alt="Downloads">

| 分支           | 简介                         |
|--------------|----------------------------|
| master       | 使用jdk8  + SpringBoot2.7.18 |
| master_jdk17 | 使用jdk17 + SpringBoot3.3.1  |


## 💻 代码架构
``` 
com.jeelowcode  
    ├── jeelowcode-admin                     // 启动模块
    ├── jeelowcode-code                      // JeelowCode核心模块
    ├── jeelowcode-dependencies              // 项目版本依赖模块
    ├── jeelowcode-framework                 // 功能模块
    │       └── jeelowcode-excel             // Excel导入导出模块
    │       └── jeelowcode-exception         // 自定义异常模块
    │       └── jeelowcode-global            // 全局配置模块
    │       └── jeelowcode-plus              // SQL/DDL 语构建模块
    │       └── jeelowcode-tenant            // 租户模块
    │       └── jeelowcode-utils             // 工具类模块
    ├── jeelowcode-module                    // 个人业务相关（写在这个模块）
    │       └── jeelowcode-module-api        // 个人业务模块API
    │       └── jeelowcode-module-biz        // 个人业务业务
```

## 👥 项目关系

| 项目 | Star | 简介      |
|--|--|---------|
| [JeeLowCode](https://gitcode.com/jeelowcode/jeelowcode)| [![Gitee star](https://gitee.com/jeelowecode/JeeLowCode/badge/star.svg)](https://gitee.com/jeelowecode/JeeLowCode) [![GitHub stars](https://img.shields.io/github/stars/jeelowcode/jeelowcode.svg?style=social&label=Stars)](https://github.com/jeelowcode/jeelowcode) [![star](https://gitcode.com/jeelowcode/jeelowcode/star/badge.svg)](https://gitcode.com/jeelowcode/jeelowcode)      | 基于 Spring Boot 多模块架构   |
| [jeelowcode-ui-admin-vue3](https://gitcode.com/jeelowcode/jeelowcode-ui-admin-vue3)| [![Gitee star](https://gitee.com/jeelowecode/jeelowcode-ui-admin-vue3/badge/star.svg)](https://gitee.com/jeelowecode/jeelowcode-ui-admin-vue3) [![GitHub stars](https://img.shields.io/github/stars/jeelowcode/jeelowcode-ui-admin-vue3.svg?style=social&label=Stars)](https://github.com/jeelowcode/jeelowcode-ui-admin-vue3) [![star](https://gitcode.com/jeelowcode/jeelowcode-ui-admin-vue3/star/badge.svg)](https://gitcode.com/jeelowcode/jeelowcode-ui-admin-vue3)      | 基于 Vue3 + Element Plus + Avue 实现的后台管理 |

## 🖼️ 系统架构图

<img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/jsjg2.png"/>


## 🐛 Hello World入门
只需要**4步** 带你了解低代码JeeLowCode
<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/hello1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/hello2.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/hello3.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/hello4.png"/></td>
    </tr>
</table>
<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/hello5.png"/></td>
    </tr>
</table>

## 💻 演示效果图

### 1. 手机端效果图：
<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/app1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/app2.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/app3.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/app5.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/app6.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/app7.png"/></td>
    </tr>
</table>

### 2. 电脑端效果图：
<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/pc1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/pc2.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/pc3.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/pc4.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/pc5.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/pc6.png"/></td>
    </tr>
</table>

### 3. 动态效果图：
<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/gif1.gif"/></td>
    </tr>
    <tr>
       <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/gif2.gif"/></td>
    </tr>
</table>

## 🌟 框架核心亮点

### 1. 首页：

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/jeelowcode-sy.png"/></td>
    </tr>
</table>

### 2. 在线表构建：

通过图形用户界面直观地创建数据表，一旦创建完成，即刻享有增、删、改、查、数据导入和导出的全面功能，操作简便，效率倍增。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/bcj1.png"/></td>
    </tr>
        <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/bcj2.png"/></td>
    </tr>
</table>

### 3. 在线统计报表：

只需配置简单的SQL语句，即可迅速生成包括柱状图、折线图、饼图以及卡片视图在内的多样化统计报表，直观展现数据分析结果。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/jeelowcode-tj-pz.png"/></td>
    </tr>
        <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/jeelowcode-tj.png"/></td>
    </tr>
</table>

### 4. 在线多表关联：

利用界面化的灵活配置，实现多表关联的直观展示，确保所见即所得的数据处理体验。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/duobiao1.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/duobiao2.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/duobiao3.png"/></td>
    </tr>
</table>

### 5. 在线格式化字段显示：

为满足界面字段合并显示需求，提供三种简捷处理方法：数据库层面的SQL函数拼接，查询时的SQL字符串合并，以及应用层的Java代码处理。选择适合的方法以优化性能和灵活性。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/zdgshxs1.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/zdgshxs2.png"/></td>
    </tr>
</table>

### 6. 在线排序设置：

通过拖放操作，用户可以直观地设置初始化数据的排序规则。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/zdpx1.png"/></td>
    </tr>
</table>

### 7. 在线租户数据权限配置：

提供在线配置工具，轻松实现租户级别的数据权限设定，确保数据访问的安全性和合规性，提升**多租户**场景下的应用灵活性。**JeeLowCode**以其卓越的灵活性、高效性和易用性，成为加速数字化转型、驱动业务创新的理想选择。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/8.jpeg"/></td>
    </tr>
</table>

### 8. 广泛数据库兼容：

<table>
    <tr>
        <th >数据库</th>
        <th width="80%">是否支持</th>
    </tr>
    <tr>
        <td >MySql</td>
        <td >支持</td>
    </tr>
    <tr>
        <td >Oracle11g</td>
        <td >支持</td>
    </tr>
    <tr>
        <td >PostgreSQL</td>
        <td >支持</td>
    </tr>
    <tr>
        <td >达梦</td>
        <td >支持</td>
    </tr> 
</table>

### 9. 在线表单设计：

提供丰富多样的控件，全面支持各类页面的开发需求。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/zxbd1.png"/></td>
    </tr>
</table>

### 10. 丰富完善的示例：

丰富完善的示例，便于用户快速学习和上手

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/lizi1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/lizi2.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/lizi3.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/lizi4.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/lizi5.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/lizi6.png"/></td>
    </tr>
</table>

### 11. 100+常用模板：

提供了丰富多样的常用页面模板，以便开发者能够迅速采纳和应用。

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/mb-sy1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/mb-sy2.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/mb-ty1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/mb-ty2.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/mb-ty3.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/mb-ty4.png"/></td>
    </tr>
</table>

## 📥 启动步骤
**温馨提示：** JDK、Maven、Node等可以从网盘下载<br>
[百度网盘地址：https://pan.baidu.com/s/158EwoXY7nvBvm_P1JKnvZQ?pwd=1234](https://pan.baidu.com/s/158EwoXY7nvBvm_P1JKnvZQ?pwd=1234)
<br>
[阿里云盘地址：https://www.alipan.com/s/X4kF7Hshuc2](https://www.alipan.com/s/X4kF7Hshuc2)
<br>
### 1. 后台JAVA代码运行步骤

**环境配置**

**运行环境**：JDK1.8

**开发工具：** Idea、Maven

**默认已启动中间件：（推荐使用宝塔）**[Mysql8.0](http://mysql.p2hp.com/)、[Redis](https://redis.io/)、[Minio](http://www.minio.org.cn/)


<table>
    <tr>
        <td>第一步：下载JeelowCode项目并导入IDEA中</td>
    </tr>
    <tr>
        <td>第二步：导入数据库文件到mysql中，并修改项目的数据库以及redis配置</td>
    </tr>
    <tr>
        <td>第三步：根据示例配置好对应数据库，刷新Maven,再进行Complie编译,启动项目</td>
    </tr>
</table>    

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/bz1.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/bz2.png"/></td>
    </tr>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/bz3.png"/></td>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/bz4.png"/></td>
    </tr>
</table>

### 2. 前端VUE代码运行步骤：

**后台运行环境：** node18.0.0

**开发工具：** VsCode

<table>
    <tr>
        <td>第一步：检查本地Node环境</td>
    </tr>
    <tr>
        <td>第二步：安装PNPM</td>
    </tr>
    <tr>
        <td>第三步：使用PNPM安装项目依赖</td>
    </tr>
    <tr>
        <td>第四步：配置项目后台地址</td>
    </tr>
    <tr>
        <td>第五步：启动项目</td>
    </tr>
</table>  


**第一步：检查本地环境：Node.js: 18.0.0**

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/13.jpg"/></td>
    </tr>
</table>

**第二步：安装PNPM，提升依赖的安装速度**

1.先配置NPM仓库

```
npm config set registry https://registry.npmmirror.com
```

2.安装PNPM

```
npm install -g pnpm
```

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/14.jpg"/></td>
    </tr>
</table>

**第三步：安装依赖**

进 入项目目录，执行

```
pnpm install
```

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/15.jpg"/></td>
    </tr>
</table>

**第四步：配置后台地址**

修改项目根目录下文件.env.dev中的请求路径为本地后台地址

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/16.jpg"/></td>
    </tr>
</table>

```
VITE_BASE_URL='http://127.0.0.1:48080'
```

**第五步:启动项目**

```
pnpm run dev-server
```

**访问地址**

<table>
    <tr>
        <td ><img src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/18.jpg"/></td>
    </tr>
</table>

## 👏 微信交流群/合作

<table>
  <tr>
    <td >微信交流群（添加时备注：进群）</td>
    <td >官方公众号</td>
  </tr>
  <tr>
    <td ><img height="320" width="250" src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/jeelowcode-kf.png"/></td>  
    <td ><img height="250" width="250" src="https://oss.mj.ink/chatgpt/jeelowcode/gitee/wxgzh.jpg"/></td>  
  </tr>
</table>

## 📋 提交规范

```
[init] 初始化  
[feat] 增加新功能  
[fix] 修复问题/BUG  
[perf] 优化/性能提升  
[refactor] 重构  
[revert] 撤销修改  
[style] 代码风格相关无影响运行结果的  
[update] 其他修改  
[upgrade] 升级版本
```

## 🔗 文章收录

- [企业级低代码平台，轻松应对99.99%编程挑战](https://mp.weixin.qq.com/s/M4h9wiZ1U7Fd3E4Ze4Yq_A)
- [[开源]精心二次封装的企业级低代码开发框架，全开源低代码开发平台，在线轻松驾驭99.99%编程挑战](https://mp.weixin.qq.com/s/TZ094u7jm4wq3tacjPEExQ)
- [无需编码，JeeLowCode 助你轻松构建强大应用！-JeeLowCode](https://mp.weixin.qq.com/s/h4jVs8DBAzaeSWKpDVo0xw)
- [体验JeeLowCode，感受低代码开发的魅力与高效！](https://mp.weixin.qq.com/s/JNwFWHRPFwcEIE5JV5xT3A)

