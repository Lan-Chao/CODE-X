


public class GuardedObject<T> {    
    // 结果
    private T obj;   
    
    // 获取结果
    public T get(){       
        synchronized (this){           
            //没有结果等待，防止虚假唤醒
            while (obj==null){               
                try {                   
                    this.wait();               
                } catch (InterruptedException e) {                  
                    e.printStackTrace();               
                }           
            }           
            return obj;       
        }   
    }   
    
    // 产生结果
    public void complete(T obj){      
        synchronized (this){           
            // 获取到结果，给obj赋值
            this.obj = obj;           
            // 唤醒等待结果的线程
            this.notifyAll();       
        }   
    } 
}
