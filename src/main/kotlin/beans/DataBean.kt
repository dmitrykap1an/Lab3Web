package beans

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import java.io.Serializable

@Entity
class DataBean: Serializable{

    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private var id = 0

    private var x: Double? = null;
    private var y: Double? = null
    private var r: Double? = null
    private var hitResult: String? = null
    
    fun getX() =
        x;

    fun getY() =
        y;

    fun getR() =
        r;

    fun setX(x: String){
        this.x = x.toDouble();
    }

    fun setY(y: Double){
        this.y = y;
    }

    fun setR(r: Double){
        this.r = r;
    }

    private fun checkSuccess(): Boolean{
        return (x!! <= 0 && x!! >= -r!! && y!! >= 0 && y!! <= r!!/2) || //проверка на попадание в квадрат
                (x!! >= 0 && y!! <= 0 && x!! * x!! + y!! * y!! <= (r!!/2) * (r!!/2)) || //проверка на попадание в четверть окружности
                (y!! >= -x!! - r!! && y!! <= 0 && x!! <= 0) //проверка на попадание в треугольник
    }

    fun checkHit() {
        hitResult = if (checkSuccess()) "Попадание" else "Промах"
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getHitResult(): String? {
        return hitResult
    }

    fun setHitResult(hitResult: String?) {
        this.hitResult = hitResult
    }
    
}