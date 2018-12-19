package model;

public class IgnoreSpeech {

	
	//���� ��� ����������� ������������
		 static boolean ignoreSpeechRecognitionResults = false;
		
		//���������� ����������� ���������� SpeechRecognition
		 
			public synchronized static void stopIgnoreSpeechRecognitionResults() {
				
				//���������� ����������� ����������
				ignoreSpeechRecognitionResults = false;
			}
			
			//���������� ��������� SpeechRecognition
			 
			public synchronized static void ignoreSpeechRecognitionResults() {
				
				//������ ����, ��� �������� ������������ ��������, �� �������� ���� ����������
				ignoreSpeechRecognitionResults = true;
				
			}
			
}
