package system;

public class Process {
	

	
	public int processId;//proses değişkenleri tanımlandı
	public int arrival;
	public int priority;
	public int processDuration;
	static int lastRun;
	public String color;
	Status status;
	int gec=0;
	enum Status{//değişmez değerler atandı
		running,
		waiting,
		ready,
		killed,
	}
	//proses değerleri çağırıldı
	public Process(int _prosesId, int _varisZamani, int _oncelik, int _calismaSuresi, Status _durum, String _renk)
	{
		processId =_prosesId;
		arrival =_varisZamani;
		priority =_oncelik;
		processDuration =_calismaSuresi;
		status =_durum;
		color =_renk;
	}
	private static final String RESET = "\u001B[0m";
	//proses oluşturuldu
	public void processBuilder(int _prosesId, int _calismaSuresi,int gecenSure) {

		
		lastRun =gecenSure;
		
		System.out.println(color +gec+" sn poses yürütülüyor            (id: " + this.processId + " öncelik: " +this.priority +" kalan süre: " +this.processDuration +")" +RESET);
		processDuration =_calismaSuresi-1;
		try {//proses bekleme
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}gec++;
	}
	public void startProcess()//proses başladı mesajı
	{
		status =Status.running;
		System.out.println(color +" proses başladı" +RESET);
	}

	public void terminatedProcess()//proses sonlandı mesajı
	{
		status =Status.killed;
		System.out.println(color +" proses sonlandı" +RESET);
	}
	
	public void hangedProcess()//proses askıya alındı mesajı
	{
		status =Status.waiting;
		System.out.println(color +" proses askida "+RESET);
	}
	
}
