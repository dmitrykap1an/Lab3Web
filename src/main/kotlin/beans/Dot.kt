package beans

import jakarta.persistence.*

@Entity(name = "dot")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Dot(){
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private val id: Long? = null


    @Column(name = "x")
    private var x: Double? = null

    @Column(name = "y")
    private var y: Double? = null

    @Column(name = "r")
    private var r: Double? = null


    open fun getX(): Double? {
        return x
    }

    open fun setX(x: Double?) {
        this.x = x
    }

    open fun getY(): Double? {
        return y
    }

    open fun setY(y: Double?) {
        this.y = y
    }

    open fun getR(): Double? {
        return r
    }

    open fun setR(r: Double?) {
        this.r = r
    }
}