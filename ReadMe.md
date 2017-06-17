# Alarm Library
对Android 闹铃功能的封装，方便进行闹铃的设置及其他操作 - AlarmManager

## 使用
### 在Application中进行初始化  
参数一: Context  
参数二: 日志工具类，可将闹铃的日志进行输出(本地、服务器等)，详情见[TraceLog](https://github.com/EthanCo/TraceLog)  

	AlarmFacade.init(context,IEntireLog);  

### 创建闹铃  
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Date time = calendar.getTime();

        Alarm alarm = new AlarmFacade.Builder()
                .setTime(time)
                .setMode(AlarmMode.AROUSE)
                .setRule(RuleFactory.create(AlarmRepeat.ONCE, null))
                .setTargetIntentAction("action.ethanco.AlarmActivity")
                .setBell(new PlayListBell(), "Boy.pm3")
                .build();
### 设置闹铃

        alarm.setAlarmClock(MainActivity.this);

### 取消闹铃

		alarm.cancelAlarmClock(MainActivity.this);

### AlarmFacade#Builder类参数说明  

	//设置闹铃模式 AROUSE:音乐唤醒 SLEEP:音乐睡眠
	public Builder setMode(@AlarmMode int mode);
	//设置是否启用
    public Builder setEnabled(boolean enabled);
	//设置年份
    public Builder setYear(int year);
	//设置月份
    public Builder setMonth(int month);
	//设置当前月的第几天
    public Builder setDayOfMonth(int dayOfMonth);
	//设置小时(12小时制)
    public Builder setHour(int hour);
	//设置小时(24小时制)
    public Builder setHourOfDay(int hourOfDay);
	//设置分钟
    public Builder setMinute(int minute);
	//设置秒
    public Builder setSecond(int second);
	//设置时间
    public Builder setTime(Date time);
	//设置闹铃规则
    public Builder setRule(IRule rule);
	//设置铃声 bell:铃声执行类 BellName:铃声名称
    public Builder setBell(IBell bell, String BellName);
	//设置路由Intent的Action，通常不用设置，默认为中转的BroadcastReceiver
    public Builder setRouterIntentAction(String intentAction);
	//设置目标Intent的Action，为一个Activity的action (activity需要加android.intent.category.DEFAULT)
    public Builder setTargetIntentAction(String targetIntentAction);

### 注意事项
1. 由于程序退出后，闹铃将无法响应，故应确保程序不会被退出 (通过添加白名单或双进程守护等方式)
2. 程序应需要开机自启