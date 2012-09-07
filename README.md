## CalendarWatcher ##

##### 监视日历中的事件，并自动调整铃声模式 #####


#### TODO ####

增加必要的测试用例

#### 开发、测试环境 ####

*   win7, intellij idea 11, defy

#### 日志 ####

*   2012/9/7T16:59 - 0.0.004.a

	api level升至15，将原来的Calendar api换至新的XxContract后，各项功能可以正常工作

	修正了已知的bug

*   2012/3/17T16:32 - 0.0.003.c

	一些bug修正

*   2012/3/17T01:58 - 0.0.002.c

	设计成服务的模式，可以转回原来的模式了，但是有些事件读取得不正确，读出的dtend是0（即1970-1-1），貌似是如果是直接在手机上设置的事件
	dtend就是对的，否则就是0，但是手机上的Calendar却可以知道结束时间

*   2012/03/16T16:26 - 0.0.001.b

	目前设计成服务的模式，可以在事件开始时转为无声模式，但是转不回来。
	有待进一步调试

*   2012/03/15T22:57 - 0.0.000.a

	原型建立

	第一次commit


<mailto:chsc4698@gmail.com>

this project is licensed under gpl, except the app icon :p
