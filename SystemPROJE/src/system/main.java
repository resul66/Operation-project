package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

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
		
        // Run a java app in a separate system process
        java.lang.Process proc = Runtime.getRuntime().exec("java -jar proje.jar");
        // Then retreive the process output
        InputStream in = proc.getInputStream();
        InputStream err = proc.getErrorStream();
	
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
