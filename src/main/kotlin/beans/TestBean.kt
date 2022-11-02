package beans

import jakarta.annotation.ManagedBean
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.context.SessionScoped
import java.io.Serializable

class TestBean: Serializable{

    companion object{
        private val serialVersionUID = 1L
    }
    private var name: String? = null
    private var age: Int? = null

    fun setName(name: String){
        this.name = name
    }

    fun setAge(age: Int){
        this.age = age
    }
    fun getName() =
        name

    fun getAge() =
        age
}