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
                transaction!!.commit()
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
            entityManager!!.persist(dataBean)
            list.add(dataBean)
            dataBean = DataBean()
            transaction!!.commit()
        } catch (e: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            throw e
        }
    }

    fun clearData(): String{
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
        return "redirect"
    }


    fun getData(): MutableList<DataBean> {
        return list
    }

    fun setData(list: MutableList<DataBean>) {
        this.list = list
    }

    fun getDataBean() =
        dataBean;
}