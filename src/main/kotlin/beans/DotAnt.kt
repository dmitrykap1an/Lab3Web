package beans

import jakarta.persistence.*

@Entity
@Table(name = "dotAnt")
class DotAnt(): Dot() {

    @Column(name = "mustacheLength")
    private var mustacheLength: Int = 0

    fun getMustacheLength() =
        mustacheLength

    fun setMustacheLength(mustacheLength: Int){
        this.mustacheLength = mustacheLength
    }
}