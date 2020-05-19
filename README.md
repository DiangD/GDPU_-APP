# GDPU_DailyHealthClockIn_APP 广药每日健康打卡助手app
疫情期间，学校要求每日到学信网站填写提交健康情况，每次都要打开电脑，十分麻烦。
要是在手机上也能完成打卡就好了。于是就有了这个app（虽然性能不咋地~）
如果大家不是GDPU学生，也可以学习一下思路，为自己学校的网站也做一个打卡助手
J
## 使用的一些技术
* 纯java开发 kotlin不会~~
* GreenDao 一款orm框架，非常简单实现易上手
* SmartTable 一款Android 表格的UI库
* soup - HTML解析器

## 思路历程

**抓包分析：**
这里我用的工具是`WireShark`  
对登录请求、以及打卡post请求进行抓包分析，获取详细的参数列表  
包括header、cookie等等 http请求的数据

这个的项目的流程是：
1. 使用学号、密码登录，服务器返回sessionId，保存进cookie
2. 获取保存cookie，用于后面的http请求。
3. 封装打卡表单数据，设置请求header（ContentType...）,设置cookie，表单数据填入request body
4. 提交表单，校验response body，完成打卡

有了整个流程思路，就可以通过编码实现功能了！！
