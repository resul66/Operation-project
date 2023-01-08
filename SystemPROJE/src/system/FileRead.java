package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;

import system.Process.Status;

public class FileRead {
	static int rowsTtl =0;//değişkenler tanımlandı
	static int firstArrival =0;
	static int timePassed =0;
	static int pid, arrival, priority, processDuration;
	static int realTimeQueueSize =0, firstPriorityQueueSize =0,
			scdPriorityQueueSize =0, trdPriorityQueueSize =0;
	static int m_priority;
	static int satir;
	static boolean frstRun;
	

	static Queue realTimeQueue, firstPriorityQueue, scdPriorityQueue, trdPriorityQueue;//kuyruk sınıfından  nesne çağırma
	
	static int frstWhile =0, scdWhile =0;
	
	static String path;
	
	static void firstRead() throws IOException//prosess sayıları belirlendi ve kuyruk oluşturma
	{
		File file = new File(path);//okunacak dosya
		if(!file.exists()) 
		{
			file.createNewFile();//Dosya Olusturma
		}
		
		Scanner scan =new Scanner(file);
		
		while(scan.hasNextLine())
		{   String data = scan.nextLine();
			if(data!=null)
			{
				String[] split=data.split(",");

			if(rowsTtl ==0) // ilk satir işlemleri
			{
				firstArrival =Integer.valueOf(split[0]);
				m_priority =Integer.valueOf(split[1]);
				timePassed = firstArrival;
			}

			if(Integer.valueOf(split[1])==0)//0 öncelik sayısı
				realTimeQueueSize++;

			if(Integer.valueOf(split[1])==1)//1 öncelik sayısı
				firstPriorityQueueSize++;

			if(Integer.valueOf(split[1])==2)//2 öncelik sayısı
				scdPriorityQueueSize++;

			if(Integer.valueOf(split[1])==3)//3 öncelik sayısı
				trdPriorityQueueSize++;}
			else
			{
				System.out.println("satır bos");//satır kontrol
			}

			rowsTtl++;
		}
		scdPriorityQueueSize += firstPriorityQueueSize ;//kuyruk değiştirilme
		trdPriorityQueueSize += scdPriorityQueueSize ;
		
		//Kuyruk oluşturma
		realTimeQueue =new Queue(realTimeQueueSize);
		firstPriorityQueue =new Queue(firstPriorityQueueSize);
		scdPriorityQueue =new Queue(scdPriorityQueueSize);
		trdPriorityQueue =new Queue(trdPriorityQueueSize);
	}

	static void SecondTimeRead(int readAll, int checkPointLine) throws IOException //kuyruğa eleman eklenmek için okuma
	{
		
		File file = new File(path);//okunacak dosya
		if(!file.exists())  //Dosya Olusturma
		{file.createNewFile();}
		
		FileReader fReader=new FileReader(file);
		String line;
		BufferedReader bReader=new BufferedReader(fReader);//ilk satır kontrol
		
		FileReader fReader2=new FileReader(file);
		String secondline;
		BufferedReader bReader2=new BufferedReader(fReader2);//ikinci satır kontrol
		
		int i=0;
		frstWhile =0; //ilk satır 0 olmalı
		while((line=bReader.readLine()) != null)//okumada satır kontrolü yapıldı
		{
			if(line.length()<=0 || line==null || line=="")//satır kontrolü
			{}
			else 
			{
				if(i==checkPointLine && i!=0) 
				{
				
					String[] bol=line.split(",");//satır okuma ve zaman, öncelik,varış ekleme
					pid=i;
					arrival =Integer.parseInt(bol[0]);//varış zamanı ataması
					priority =Integer.parseInt(bol[1]);//öncelik sırası ataması
					processDuration =Integer.parseInt(bol[2]);//proses süresiataması
					if (processDuration>20) {
						processDuration=20;
					}//20 sn kontrolü
					
					while((secondline=bReader2.readLine()) != null) //satır bitene kadar okuma
					{
						
						if(secondline.length()<=0 || secondline==null || secondline=="") // satir kontrolü
						{
						
						}
						else 
						{
							if(frstWhile +1== rowsTtl) //ilk satır
							{											  
								int i0baslangicDegeri= arrival;//ilk satırın başlangıç zamanını atama
								readAll=i0baslangicDegeri;
							}
							
							else
							{
								
								if(scdWhile ==(frstWhile +1))
								{
									
									String[] bol2=secondline.split(","); 
									int varisZamani2;
									varisZamani2=Integer.parseInt(bol2[0]);
									
									int i0baslangicDegeri= arrival;
									int i1baslangicDegeri=varisZamani2;
									if(i1baslangicDegeri>i0baslangicDegeri) 
									{
										if(processDuration <(varisZamani2- timePassed)) {break;}
										timePassed =i1baslangicDegeri;
									}
									else
									{}
									break;
								}
							}
							scdWhile++;
						}
					}
					scdWhile =0;
				}
				else if(i==checkPointLine && i==0 && arrival <= timePassed)
				{
					
					String[] bol=line.split(","); 
					pid=i;//pid tanımlama
					arrival =Integer.parseInt(bol[0]);//varış zamanı ataması
					priority =Integer.parseInt(bol[1]);//öncelik ataması
					processDuration =Integer.parseInt(bol[2]);//proses süresi ataması
					
					if (processDuration>20) {
						processDuration=20;
					}
					//proses oluşturma
					Process proses=new Process(pid, arrival, priority, processDuration,Status.ready, Color.randomcolor[i%7]);
					checkPointLine=i+1;
					if(priority ==0)
					{
						if(realTimeQueueSize !=0)// realtimeKuyruk'a eleman ekleme
							realTimeQueue.insert(proses);
					}
					else if(priority ==1)
					{
						if(firstPriorityQueueSize !=0) // birOncelikliKuyruk'a eleman ekleme
							firstPriorityQueue.insert(proses);
					}
					else if(priority ==2)
					{
						if(scdPriorityQueueSize !=0) // ikiOncelikliKuyruk'a eleman ekleme
							scdPriorityQueue.insert(proses);
					}
					else if(priority ==3)
					{
						if(trdPriorityQueueSize !=0)// ucOncelikliKuyruk'a eleman ekleme
							trdPriorityQueue.insert(proses);
					}
				}
				
				
				if(i>=checkPointLine && arrival <= timePassed && i!=0)
				{
					String[] bol=line.split(","); 
					pid=i;
					arrival =Integer.parseInt(bol[0]);
					priority =Integer.parseInt(bol[1]);
					processDuration =Integer.parseInt(bol[2]);
					if (processDuration>20) {
						processDuration=20;
					}
					//proses oluşturma
					Process proses=new Process(pid, arrival, priority, processDuration,Status.ready, Color.randomcolor[i%7]);
					checkPointLine=i+1;//şimdiki tutulanı 1 artırma
					
					if(priority ==0)
					{
						if(realTimeQueueSize !=0) // realtimeKuyruk'a eleman ekleme
							realTimeQueue.insert(proses);
					}
					else if(priority ==1)
					{
						if(firstPriorityQueueSize !=0) // birOncelikliKuyruk'a eleman ekleme
							firstPriorityQueue.insert(proses);
					}
					else if(priority ==2)
					{
						if(scdPriorityQueueSize !=0) // ikiOncelikliKuyruk'a eleman ekleme
							scdPriorityQueue.insert(proses);
					}
					else if(priority ==3)
					{
						if(trdPriorityQueueSize !=0) // ucOncelikliKuyruk'a eleman ekleme
							trdPriorityQueue.insert(proses);
					}
				}
			}
			i++;
			frstWhile++;
		}

		satir =checkPointLine;//şimdiki satırı artırma
		
		
		//Kuyruk islemleri

		if(frstRun ==true)
		{
			if(m_priority ==0)//Realtime kuyruk fonksiyonu çağırma
			{
				zeroPriority();
			}
			else if(m_priority ==1||m_priority ==2||m_priority ==3)//kullanıcı zamanlı kuyruk fonksiyonu çağırma
			{
				
				if (m_priority ==1) {//ilkoncelikli kuyruk fonksiyonu çağırma
					firstPriority();
				}
				if (m_priority ==2) {//ikinciOncelikli kuyruk fonksiyonu çağırma
					secondPriority();
				}
				if (m_priority ==3) {//ucuncuoncelikli kuyruk fonksiyonu çağırma
					thirdPriority();
				}
			
			
			}
			
			
			frstRun =false;
		}
		else 
		{
			zeroPriority();
			firstPriority();
			secondPriority();
			thirdPriority();
		}
		
		
	}
	
	
	//realtimekuyruk işlemleri
	static void zeroPriority() throws IOException
	{
		//kuyruk içinde gezinme
		for(int j = 0; j< realTimeQueue.count(); j++)
		{
			if(realTimeQueue.index(j).processDuration ==0)//süre kontrolü
			{}
			else
			{

				realTimeQueue.index(j).startProcess();
				
				timePassed = timePassed + realTimeQueue.index(j).processDuration;

				
				int prosesId= realTimeQueue.index(j).processId;
				int prosesinCalismaSuresi= realTimeQueue.index(j).processDuration;
				
				for(int k=0; k<prosesinCalismaSuresi; k++)
				{
					realTimeQueue.index(j).processBuilder( realTimeQueue.index(j).processId, realTimeQueue.index(j).processDuration, timePassed);
				}

				realTimeQueue.index(j).arrival =0;
				realTimeQueue.index(j).priority =0;
				realTimeQueue.index(j).processDuration =0;
				realTimeQueue.index(j).terminatedProcess();

				SecondTimeRead(timePassed, satir);//ikinci okuma fonksiyonu çağırma
			}
		}
	}	
	//kullanıcı zamanlı işlemleri
	static void firstPriority() throws IOException
	{
		//kuyruk içinde gezinme
		for(int p = 0; p< firstPriorityQueue.count(); p++)
		{
			if(firstPriorityQueue.index(p).processDuration ==0 && firstPriorityQueue.index(p).status ==Status.killed) 
			{}
			else if(firstPriorityQueue.index(p).processDuration ==1) 
			{
				if(firstPriorityQueue.index(p).status ==Status.waiting) 
				{}
				else
				{
				
					firstPriorityQueue.index(p).startProcess();
					timePassed +=1;
					
					
					int prosesId= firstPriorityQueue.index(p).processId;
					int prosesinCalismaSuresi= firstPriorityQueue.index(p).processDuration;
					firstPriorityQueue.index(p).processBuilder(prosesId, prosesinCalismaSuresi, timePassed);
					
					
					firstPriorityQueue.index(p).arrival =0;
					firstPriorityQueue.index(p).priority =0;
					firstPriorityQueue.index(p).processDuration =0;
					firstPriorityQueue.index(p).terminatedProcess();

					SecondTimeRead(timePassed, satir);
					
				}
			}
			else if(firstPriorityQueue.index(p).status == Status.waiting) 
			{
				
			}
			else 
			{
				
				firstPriorityQueue.index(p).startProcess();
				timePassed +=1;
				
				
				int prosesId= firstPriorityQueue.index(p).processId;
				int prosesinCalismaSuresi= firstPriorityQueue.index(p).processDuration;
				firstPriorityQueue.index(p).processBuilder(prosesId, prosesinCalismaSuresi, timePassed);
				
				int varisZamani= firstPriorityQueue.index(p).arrival;
				int oncelik=2;
				Status durum=Status.ready;
				String renk= firstPriorityQueue.index(p).color;
				int kalanCalismaSuresi= firstPriorityQueue.index(p).processDuration;
				Process proses=new Process(prosesId,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
				proses.lastRun = timePassed;
				scdPriorityQueue.insert(proses); 
				
			
				firstPriorityQueue.index(p).hangedProcess();

				SecondTimeRead(timePassed, satir);
				
				
			}
		} 
		
	}
	
	
	static void secondPriority() throws IOException
	{
		//kuyruk içinde gezinme
		for(int r = 0; r< scdPriorityQueue.count(); r++)
		{
			if(scdPriorityQueue.index(r).processDuration ==0 && scdPriorityQueue.index(r).status ==Status.killed) 
			{}
			else if(scdPriorityQueue.index(r).processDuration ==1) 
			{
				if(scdPriorityQueue.index(r).status ==Status.waiting) 
				{}
				else
				{
					
					scdPriorityQueue.index(r).startProcess();
					timePassed +=1;
					
					
					int prosesId= scdPriorityQueue.index(r).processId;
					int prosesinCalismaSuresi= scdPriorityQueue.index(r).processDuration;
					scdPriorityQueue.index(r).processBuilder(prosesId, prosesinCalismaSuresi, timePassed);
					
				
					scdPriorityQueue.index(r).arrival =0;
					scdPriorityQueue.index(r).priority =0;
					scdPriorityQueue.index(r).processDuration =0;
					scdPriorityQueue.index(r).terminatedProcess();
					
					
					SecondTimeRead(timePassed, satir);
				}
			}
			else if(scdPriorityQueue.index(r).status ==Status.waiting) 
			{
				
			}
			else 
			{
				
				scdPriorityQueue.index(r).startProcess();
				timePassed +=1;
				
				
				int prosesId= scdPriorityQueue.index(r).processId;
				int prosesinCalismaSuresi= scdPriorityQueue.index(r).processDuration;
				scdPriorityQueue.index(r).processBuilder(prosesId, prosesinCalismaSuresi, timePassed);
				int varisZamani= scdPriorityQueue.index(r).arrival;
				int oncelik=3;
				Status durum=Status.ready;
				String renk= scdPriorityQueue.index(r).color;
				int kalanCalismaSuresi= scdPriorityQueue.index(r).processDuration;
				Process proses=new Process(prosesId,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
				proses.lastRun = timePassed;
				trdPriorityQueue.insert(proses); 
				
				
				scdPriorityQueue.index(r).hangedProcess();
				
				
				SecondTimeRead(timePassed, satir);
				
				
			}
		} 
		
	}
	
	
	static void thirdPriority() throws IOException
	{
		//kuyruk içinde gezinme
		for(int s = 0; s< trdPriorityQueue.count(); s++)
		{
			if(trdPriorityQueue.index(s).processDuration ==0 && trdPriorityQueue.index(s).status ==Status.killed)
			{} 
			else if(trdPriorityQueue.index(s).processDuration ==1)
			{
					
					trdPriorityQueue.index(s).startProcess();
					
				
					int prosesId= trdPriorityQueue.index(s).processId;
					int prosesinCalismaSuresi= trdPriorityQueue.index(s).processDuration;
					trdPriorityQueue.index(s).processBuilder(prosesId, prosesinCalismaSuresi, timePassed);
					
					
					trdPriorityQueue.index(s).arrival =0;
					trdPriorityQueue.index(s).priority =0;
					trdPriorityQueue.index(s).processDuration =0;
					trdPriorityQueue.index(s).terminatedProcess();
					
					timePassed +=1;
					
					SecondTimeRead(timePassed, satir);
			}
			else 
			{
				
				trdPriorityQueue.index(s).startProcess();
				
				
				int prosesId= trdPriorityQueue.index(s).processId;
				int prosesinCalismaSuresi= trdPriorityQueue.index(s).processDuration;
				trdPriorityQueue.index(s).processBuilder(prosesId, prosesinCalismaSuresi, timePassed);
			
				trdPriorityQueue.index(s).hangedProcess();
				timePassed +=1;
				trdPriorityQueue.shiftProcess();
				
				SecondTimeRead(timePassed, satir);
				
			}
		} 
		
	}}
	
