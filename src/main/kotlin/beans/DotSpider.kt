package beans

import jakarta.persistence.*


@Entity
@Table(name = "dotSpider")
class DotSpider(): Dot(){

    @Column(name = "numberOfLegs")
    private var numberOfLegs: Int = 0

    fun getNumberOfLegs() =
        numberOfLegs;

    fun setNumberOfLegs(numberOfLegs: Int){
        this.numberOfLegs = numberOfLegs
    }
}