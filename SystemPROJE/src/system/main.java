package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;




import system.Process.Status;
//
//	İSLETİM SİTEMLERİ PROJESİ
// 
//	B201210092	RESUL DASPİNAR 
//
//  B201210556  Muhammad Najmuddin Farid
//
//

public class main {
	
	public static void main(String[] args) throws IOException {
	
		if(args.length<=0)
		{}
		else
		{
			FileRead.path =args[0];					//argüman okuma çağırıldı
			FileRead.firstRead();
			FileRead.SecondTimeRead(FileRead.firstArrival,0);	//ilk satır okuma çağırıldı
		}

	}

}
