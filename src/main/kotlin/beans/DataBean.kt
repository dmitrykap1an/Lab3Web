package beans

import jakarta.persistence.*
import java.io.Serializable

@Entity(name = "DataBean")
@Table(name = "datatable")
class DataBean: Serializable{

    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private var id = 1;

    @Column(name = "x")
    private var x: Double? = null;
    @Column(name = "y")
    private var y: Double? = null
    @Column(name = "r")
    private var r: Double? = null
    @Column(name = "hitresult")
    private var hitResult: Boolean? = null
    
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
    
}