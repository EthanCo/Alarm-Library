package com.lib.alarm.rule;

import com.lib.alarm.enums.AlarmRepeat;
import com.lib.alarm.rule.abs.IRule;
import com.lib.alarm.week.WeekFlags;

/**
 * Rule 工厂
 *
 * @author EthanCo
 * @since 2017/6/16
 */

public class RuleFactory {
    public static IRule create(@AlarmRepeat int type, Object ruleEnforce) {
        IRule rule = null;

        switch (type) {
            case AlarmRepeat.ONCE:
                rule = new OnceRule();
                break;
            case AlarmRepeat.EVERYDAY:
                rule = new EverydayRule();
                break;
            case AlarmRepeat.WEEKDAY:
                rule = new WeekRule();
                WeekFlags weekdayFlags = new WeekFlags();
                weekdayFlags.setWeekdayFlag();
                rule.setRuleEnforce(weekdayFlags);
                break;
            case AlarmRepeat.WEEKEND:
                rule = new WeekRule();
                WeekFlags weekendFlags = new WeekFlags();
                weekendFlags.setWeekendFlag();
                rule.setRuleEnforce(weekendFlags);
                break;
            case AlarmRepeat.CUSTOME:
                //fall through
            default:
                rule = new WeekRule();
                rule.setRuleEnforce(ruleEnforce);
        }

        return rule;
    }
//
//    public static IRule createOnceRule(Date time) {
//        IRule<Date> rule = new OnceRule();
//        rule.setRuleEnforce(time);
//        return rule;
//    }
//
//    public static IRule createEveryDayRule() {
//        return new EverydayRule();
//    }
//
//    public static IRule createCustomeRule(WeekFlags weekFlags) {
//        IRule<WeekFlags> weekRule = new WeekRule();
//        weekRule.setRuleEnforce(weekFlags);
//        return weekRule;
//    }
}
