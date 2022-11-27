package beans

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Named
import jakarta.persistence.*
import java.io.Serializable
import java.lang.NullPointerException
import java.sql.DriverManager

@Named("requests")
@ApplicationScoped
class Requests: Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int = 1;

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
            val query: Query = entityManager!!.createQuery("SELECT e FROM DataBean e")
            list = query.resultList as MutableList<DataBean>
            transaction!!.commit()
        } catch (e : RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            throw e
        }
    }

    fun addData(): String{
        try {
            transaction!!.begin()
            dataBean.checkHit()
            entityManager!!.persist(dataBean)
            list.add(dataBean)
            dataBean = DataBean()
            transaction!!.commit()
        } catch (exception: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            throw exception
        }
        return "redirect"
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