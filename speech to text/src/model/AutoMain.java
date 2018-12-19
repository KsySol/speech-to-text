package model;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

public class AutoMain {
	

	private LiveSpeechRecognizer recognizer; //���������� �������� �����
	
	private Logger logger = Logger.getLogger(getClass().getName()); // ��������� �����
	
	static String speechRecognitionResult; //����� ��� ���������� ���������� SpeechRecognizer
	
   //����� ��� �������� �� ������������ ��������
	private boolean RspeechRecognizerThreadRunning = false;
	
	// ����� ��� �������� ������� �������
	private boolean resourcesThreadRunning;
	
	// �� ������ ���������� ��������������� ��� ����, ��� ��䳿 playerState ������������
	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);
	
	
	 //�����������
	public AutoMain() {
		
		// ���������� ���� ��� ��������
		logger.log(Level.INFO, "Loading Speech Recognizer...\n");
				
		// ��������� ������������
		Configuration configuration = new Configuration();
				
		//�������� ����� ��������� �����
		configuration.setAcousticModelPath("auto_acoustic/");
		configuration.setDictionaryPath("resource:/auto/cmudict-auto.dic");
		configuration.setLanguageModelPath("resource:/auto/auto.lm");
		
		
		
		// ���������
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammarauto");
		configuration.setUseGrammar(true);
		
		// ����� ������
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		

		//�������� ������� �������
		startResourcesThread();
		//������� ����������� ��������
		startSpeechRecognition();
	}
	
	//-----------------------------------------------------------------------------------------------
	
	//������� ���������� �������
public synchronized void startSpeechRecognition() {
		
		//�������� ������� � ��������� ����
		if (RspeechRecognizerThreadRunning)
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else
			//���������� �� ExecutorService
			eventsExecutorService.submit(() -> {
				
				//������������ �����
				RspeechRecognizerThreadRunning = true;
				IgnoreSpeech.ignoreSpeechRecognitionResults = false;
				
				//����� ������������
				recognizer.startRecognition(true);
				
				//��������� ���������� � ���			
				logger.log(Level.INFO, "You can start to speak...\n");
				
				try {
					while (RspeechRecognizerThreadRunning) {
						
						//��� ����� �����������, ���� ����������� ����� �����
						SpeechResult speechResult = recognizer.getResult();
						
						//�������� ���� �� �������� ��������� ����������
						if (!IgnoreSpeech.ignoreSpeechRecognitionResults) {
							
							//�������� ����������
							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {
								
								//��������� �������
								speechRecognitionResult = speechResult.getHypothesis();
								
								//��������� ����� ��� �����������
								System.out.println("You said: [" + speechRecognitionResult + "]\n");
								
								//Frame Write = new Frame();
								Frame.printspeachResult(speechRecognitionResult);
								
								
								
								//������ ����ⳳ����� ������
								makeDecision(speechRecognitionResult, speechResult.getWords());
								
							}
						} else
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
						
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					RspeechRecognizerThreadRunning = false;
				}
				
				logger.log(Level.INFO, "SpeechThread has exited...");
				
			});
	}
	
	
	
	//������� ����, ���� �������� �������� �������, ���������� ��� �������� SpeechRecognition
	public void startResourcesThread() {
		
		//��������� �������
		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			//����������� �� ExecutorService
			eventsExecutorService.submit(() -> {
				try {
					
					//��������� ������� - �����������
					resourcesThreadRunning = true;
					
					// �������� ���������
					while (true) {
						
						//���� ������� ������
						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");
						
						// ��������
						Thread.sleep(350);
					}
					
				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}
	
	//����� ������ �� ����� ������ ����������
	public void makeDecision(String speech , List<WordResult> speechWords) {
		
		System.out.println(speech);
		
	}
	
	
	public static void main(String[] args) {
		   
			//new AutoMain();
		}
}