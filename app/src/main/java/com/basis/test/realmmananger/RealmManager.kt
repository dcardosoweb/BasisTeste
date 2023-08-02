package com.basis.test.realmmananger

import com.basis.test.model.Pessoa
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class RealmManager {
    private val config: RealmConfiguration =
        RealmConfiguration.Builder()
            .name("basis.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1).build()
    private val realm: Realm = Realm.getInstance(config)

    fun addOrUpdate(pessoa: Pessoa) {
        realm.beginTransaction()
        pessoa.let { i ->
            realm.insertOrUpdate(i)
            realm.commitTransaction()
        }
    }

    val getAllPessoas: RealmResults<Pessoa>
        get() = realm.where(Pessoa::class.java).findAll()

    fun deleteById(id: String) {
        val itemRealmObject = realm.where(Pessoa::class.java).equalTo("id", id).findFirst()
        if (itemRealmObject != null) {
            realm.beginTransaction()
            itemRealmObject.deleteFromRealm()
            realm.commitTransaction()
        }
    }

    fun deleteAll(){
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }
}