import beans.DataBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

@Named("listBean")
@ApplicationScoped
public class ListBean implements Serializable {

    private CopyOnWriteArrayList<DataBean> list = new CopyOnWriteArrayList<DataBean>();

    public ListBean(){

    }

    public void clearList(){
        list.clear();
    }

    public void addToList(DataBean dataBean){
        list.add(dataBean);
    }

    public void setList(CopyOnWriteArrayList<DataBean> list) {
        this.list = list;
    }

    public CopyOnWriteArrayList<DataBean> getList() {
        return list;
    }

}