package model;

public class IgnoreSpeech {

	
	//Змінні для ігнорування розпізнавання
		 static boolean ignoreSpeechRecognitionResults = false;
		
		//Припинення ігнорування результату SpeechRecognition
		 
			public synchronized static void stopIgnoreSpeechRecognitionResults() {
				
				//Припинення ігнорування результату
				ignoreSpeechRecognitionResults = false;
			}
			
			//Ігнорувати резутьтат SpeechRecognition
			 
			public synchronized static void ignoreSpeechRecognitionResults() {
				
				//Замість того, щоб зупинити розпізнавання мовлення, ми ігноруємо його результати
				ignoreSpeechRecognitionResults = true;
				
			}
			
}
