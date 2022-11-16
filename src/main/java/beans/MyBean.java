package beans;



import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;

@ManagedBean
@ApplicationScoped
public class MyBean implements Serializable {
    private String name = "test";
    
    public String getName() {return name;}

    public void setName(String name){this.name = name;}
    
}
