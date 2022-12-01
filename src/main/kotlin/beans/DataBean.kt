package beans

import jakarta.persistence.*
import java.io.Serializable
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(name = "DataBean")
@Table(name = "datatable")
class DataBean: Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id = 1;

    @Column(name = "x")
    private var x: Double? = null;
    @Column(name = "y")
    private var y: Double? = null
    @Column(name = "r")
    private var r: Double? = null
    @Column(name = "hitresult")
    private var hitResult: Boolean? = null
    @Column(name = "serverTime")
    private var serverTime: String? = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString()
    @Column(name = "executeTime")
    private var executeTime: Long = System.nanoTime()

    
    fun getX() =
        x;

    fun getY() =
        y;

    fun getR() =
        r;

    fun setX(x: Double?){
        this.x = x;
    }

    fun setY(y: Double?){
        this.y = y;
    }

    fun setR(r: Double?){
        this.r = r;
    }

    fun getServerTime() =
        serverTime

    fun setServerTime(serverTime: String){
        this.serverTime = serverTime
    }

    fun getExecuteTime()
        = executeTime

    fun setExecuteTime(executeTime: Long){
        this.executeTime = executeTime
    }

    private fun checkSuccess(): Boolean{
        return (x!! <= 0 && x!! >= -r!! && y!! >= 0 && y!! <= r!!/2) || //проверка на попадание в квадрат
                (x!! >= 0 && y!! <= 0 && x!! * x!! + y!! * y!! <= (r!!/2) * (r!!/2)) || //проверка на попадание в четверть окружности
                (y!! >= -x!! - r!! && y!! <= 0 && x!! <= 0) //проверка на попадание в треугольник
    }

    fun checkHit() {
        hitResult = checkSuccess()
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getHitResult(): Boolean? {
        return hitResult
    }

    fun setHitResult(hitResult: Boolean) {
        this.hitResult = hitResult
    }

    override fun toString(): String {
        return "DataBean(id: $id, x: $x, y: $y, r: $r, hitResult :$hitResult"
    }
    
}