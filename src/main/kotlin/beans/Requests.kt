package beans

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Named
import jakarta.persistence.*
import java.io.Serializable

@Named("requests")
@ApplicationScoped
class Requests: Serializable {

    private val persistenceUnit = "Web3"
    private var dataBean: DataBean = DataBean();
    private var list = mutableListOf<DataBean>()
    private var entityManagerFactory: EntityManagerFactory? = null
    private var entityManager: EntityManager? = null
    private var transaction: EntityTransaction? = null

    init{
        connection()
        loadData()
    }

    private fun connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit)
        entityManager = entityManagerFactory!!.createEntityManager()
        transaction = entityManager!!.transaction
    }

        private fun loadData() {
            try {
                transaction!!.begin()
                val query: Query = entityManager!!.createQuery("SELECT u FROM DataBean u")
                list = query.resultList as MutableList<DataBean>
                entityManager!!.flush()
                transaction!!.commit()
                list.forEach{
                    println(it.toString())
                }
            } catch (e : RuntimeException) {
                if (transaction!!.isActive) {
                    transaction!!.rollback()
                }
                throw e
            }
        }

    fun addData(){
        try {
            transaction!!.begin()
            dataBean.checkHit()
            dataBean.setExecuteTime((System.nanoTime() - dataBean.getExecuteTime())/1000000)
            entityManager!!.persist(dataBean)
            list.add(dataBean)
            dataBean = DataBean()
            entityManager!!.flush()
            transaction!!.commit()
        } catch (e: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            println("ошибочка вышла")
            throw e
        }
    }

    fun clearData(){
        try {
            transaction!!.begin()
            val query = entityManager!!.createQuery("DELETE FROM DataBean")
            query.executeUpdate()
            list.clear()
            transaction!!.commit()
        } catch (exception: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            throw exception
        }
    }


    fun getData(): List<DataBean> {
        return list.reversed()
    }

    fun setData(list: MutableList<DataBean>) {
        this.list = list
    }

    fun getDataBean() =
        dataBean;
}