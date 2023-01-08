package system;

import system.Process.Status;

public class Queue {
	//kuyruk bilgileri
	private int len;
	  public Process[] queueArray;//kuyruk dizisi
	  private int fore;//şimdiki eleman
	  private int back;//önceki eleman
	  private int size;//boyut
//Kuyruk oluşturma
	  public Queue(int leng)
	  {
	    len = leng;//değer atama
	    fore = 0;
	    back = -1;
	    size = 0;
	    queueArray = new Process[len];
	  }
	  //Kuyruk kontrol
	  public boolean isEmpty() {
	    if (size == 0) return true;
	    else return false;
	  }
	   
	  //kuyruk içine eleman ekleme
	  public void insert (Process eklenecekEleman){
	    if (back == len -1) back = -1;
	    back++;
	    queueArray [back] = eklenecekEleman;//eleman ekleme
	    size++;
	  }
	  //kuyruk prosess indisi
	  public Process index(int index)
	  {
		  if(index> size)
		  {
			  return null;
		  }
		  else {
			  return queueArray[index];//indisli dizideki elemanı çağırma
		  }
		
	  }
	   
	  //kuyruk boyutu
	  public int count() {
	    return size;
	  }
	// RR metodu
	public int shiftProcess()
		  {
			  if(isEmpty()==false)
			  {
				  for(int index1 = 0; index1< size; index1++)
				  {
					  if(size ==1 || (index1+1)== size)
						{
							break;
						}
					  	// kuyruğa düğüm atama sağa sola kaydırma
						int tempId=  queueArray[index1+1].processId;
						queueArray[index1+1].processId =queueArray[index1].processId;
						queueArray[index1].processId =tempId;
						// kuyruğa düğüm atama sağa sola kaydırma
						int tempVaris= queueArray[index1+1].arrival;
						queueArray[index1+1].arrival =queueArray[index1].arrival;
						queueArray[index1].arrival =tempVaris;
						// kuyruğa düğüm atama sağa sola kaydırma
						int tempOncelik=  queueArray[index1+1].priority;
						queueArray[index1+1].priority =queueArray[index1].priority;
						queueArray[index1].priority =tempOncelik;
						// kuyruğa düğüm atama sağa sola kaydırma
						int tempCalismaSure=  queueArray[index1+1].processDuration;
						queueArray[index1+1].processDuration =queueArray[index1].processDuration;
						queueArray[index1].processDuration =tempCalismaSure;
						// kuyruğa düğüm atama sağa sola kaydırma
						int tempSonZaman=  queueArray[index1+1].lastRun;
						queueArray[index1+1].lastRun =queueArray[index1].lastRun;
						queueArray[index1].lastRun =tempSonZaman;
						
						
						Status tempSatus=  queueArray[index1+1].status;
						queueArray[index1+1].status =queueArray[index1].status;
						queueArray[index1].status =tempSatus;
						
						String tempRenk=queueArray[index1+1].color;
						queueArray[index1+1].color =queueArray[index1].color;
						queueArray[index1].color =tempRenk;
				  }
				  return 1;
			  }
			  else
			  {return 0;}
		  }
}
