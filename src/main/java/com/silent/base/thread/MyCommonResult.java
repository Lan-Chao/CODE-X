


public class MyCommonResult<T> {    
    // 这个是结果  GuardedObject
    private T obj;   

    private boolean end;
    
    // 获取结果
    public T get(){       
        synchronized (this){           
            //没有结果等待，防止虚假唤醒
            while (!end && obj==null){               
                try {                   
                    this.wait();               
                } catch (InterruptedException e) {                  
                    // todo              
                }           
            }           
            return obj;       
        }   
    }   
    
    // 拿到结果了
    public void complete(T obj){      
        synchronized (this){           
            this.obj = obj;           
            // 唤醒等待结果的线程
            this.notifyAll();       
        }   
    } 
}
