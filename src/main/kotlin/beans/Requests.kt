package beans

import ListBean
import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.SessionScoped
import jakarta.enterprise.inject.Any
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.persistence.*
import java.io.Serializable
import java.util.concurrent.CopyOnWriteArrayList

@Named("requests")
@SessionScoped
open class Requests: Serializable {

    private val persistenceUnit = "Web3"
    private var dataBean: DataBean = DataBean()
    @Inject
    private lateinit var listBean: ListBean

    private var dotAnt: DotAnt? = DotAnt()
    private var dotSpider: DotSpider? = DotSpider()
    private var data: MutableList<DataBean>? = null
    private var entityManagerFactory: EntityManagerFactory? = null
    private var entityManager: EntityManager? = null
    private var transaction: EntityTransaction? = null

    @PostConstruct
    fun init(){
        connection()
        loadData()
        data = listBean.list
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
            data = query.resultList as MutableList<DataBean>
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
            dataBean.setHitResult(checkSuccess())
            dataBean.setExecuteTime((System.nanoTime() - dataBean.getExecuteTime())/1000000)
            entityManager!!.persist(dataBean)
            sendDotAntToDatabase()
            sendDotSpiderToDatabase()
            listBean.addToList(dataBean)
            dataBean = DataBean()
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
            listBean.clearList()
            transaction!!.commit()
        } catch (exception: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            throw exception
        }
    }

    fun setListBean(listBean: ListBean){
        this.listBean = listBean
    }


    fun getDataBean() =
        dataBean

    fun getData() =
        data?.reversed()

    fun setData(data: MutableList<DataBean>?){
        this.data = data
    }


    fun getDotAnt() =
        dotAnt;

    fun setDotAnt(dotAnt: DotAnt?){
        this.dotAnt = dotAnt
    }

    private fun sendDotAntToDatabase(){
        if(dotAnt!!.getMustacheLength() != 0){
            dotAnt!!.setX(dataBean.getX())
            dotAnt!!.setY(dataBean.getY())
            dotAnt!!.setR(dataBean.getR())
            entityManager!!.persist(dotAnt)
            dotAnt = DotAnt()
        }

    }

    private fun sendDotSpiderToDatabase(){
        if(dotSpider!!.getNumberOfLegs() != 0){
            dotSpider!!.setX(dataBean.getX())
            dotSpider!!.setY(dataBean.getY())
            dotSpider!!.setR(dataBean.getR())
            entityManager!!.persist(dotSpider)
            dotSpider = DotSpider()
        }

    }

    fun getDotSpider() =
        dotSpider;

    fun setDotSpider(dotSpider: DotSpider?){
        this.dotSpider = dotSpider
    }

    private fun checkSuccess(): Boolean{
        val x = dataBean.getX();
        val y = dataBean.getY();
        val r = dataBean.getR();
        return (x!! <= 0 && x >= -r!! && y!! >= 0 && y <= r /2) || //проверка на попадание в квадрат
                (x >= 0 && y!! <= 0 && x * x + y * y <= (r!!/2) * (r /2)) || //проверка на попадание в четверть окружности
                (y!! >= -x - r!! && y <= 0 && x <= 0) //проверка на попадание в треугольник
    }

}