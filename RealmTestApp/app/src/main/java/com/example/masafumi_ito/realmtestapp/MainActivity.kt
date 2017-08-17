package com.example.masafumi_ito.realmtestapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import kotlin.properties.Delegates
import io.realm.RealmResults
import io.realm.annotations.PrimaryKey
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // realmインスタンスの作成
    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Realmを開始
        realm = Realm.getDefaultInstance()

        // Realmの処理はこの中で
        realm.executeTransaction {

            // Add a person
            var person = realm.createObject(Person::class.java) // PrimaryKeyを指定している場合は第二引数に与える
            person.name = "Young Person"
            person.age = 14

        }

        // 保存したpersonの取得
        val person = realm.where(Person::class.java).findFirst()!!

        // とりあえず表示してみる
        nameLabel.text = person.name
        ageLabel.text = person.age.toString()

    }

    override fun onDestroy() {
        super.onDestroy()

        // Realmをクローズ
        realm.close()
    }
}

// ぬこクラス
open class Cat(var name: String = "", var age: Int? = null) : RealmObject()

// ひとクラス
open class Person(
        // You can put properties in the constructor as long as all of them are initialized with
        // default values. This ensures that an empty constructor is generated.
        // All properties are by default persisted.
        // Properties can be annotated with PrimaryKey or Index.
        // If you use non-nullable types, properties must be initialized with non-null values.
//        @PrimaryKey var id: Long = 0,

        var name: String = "",

        var age: Int = 0,

        // One-to-many relations is simply a RealmList of the objects which also subclass RealmObject
        var cats: RealmList<Cat> = RealmList()

) : RealmObject() {
    // The Kotlin compiler generates standard getters and setters.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.
}
