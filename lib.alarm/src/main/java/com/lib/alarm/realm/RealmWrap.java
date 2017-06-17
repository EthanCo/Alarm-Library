package com.lib.alarm.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * @author EthanCo
 * @since 2017/6/10
 */

public class RealmWrap {
    private static RealmConfiguration config = null;

    public static void init(Context context) {
        Realm.init(context);

        if (config == null) {
            config = new RealmConfiguration.Builder()
                    .name("testRealm9.realm")
                    .schemaVersion(0)
                    //.migration(new MyMigration())
                    .build();
        }
    }

    public static Realm getRealm() {
        return Realm.getInstance(config);
    }

    //自增长ID
    public static synchronized <E extends RealmModel> int autoIncrement(Class<E> cls) {
        Realm realm = RealmWrap.getRealm();
        Number maxValue = realm.where(cls).max("id");
        return (maxValue != null) ? maxValue.intValue() + 1 : 0;
    }

//    private static class MyMigration implements RealmMigration {
//        @Override
//        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
//            RealmSchema schema = realm.getSchema();
//
//            if (oldVersion == 0) {
//                schema.create("Alarm")
//                        .addField("name", String.class)
//                        .addField("age", int.class);
//                oldVersion++;
//            }
//            if (oldVersion == 1) {
//                schema.get("Person")
//                        .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
//                        .addRealmObjectField("favoriteDog", schema.get("Dog"))
//                        .addRealmListField("dogs", schema.get("Dog"));
//                oldVersion++;
//            }
//        }
//    }
}
