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

public class Main {
	
	
	private LiveSpeechRecognizer recognizer; //Оголошення приватної змінної
	
	private Logger logger = Logger.getLogger(getClass().getName()); // Реєстрація даних
	
	static String speechRecognitionResult; //змінна для повернення результату SpeechRecognizer
	
	
	
	//Змінна для перевірки чи розпізнавання запущено
	private boolean speechRecognizerThreadRunning = false;
	
	// Змінна для перевірки запуску ресурсів
	private boolean resourcesThreadRunning;
	
	// Ця служба виконувача використовується для того, щоб події playerState виконувалися
	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);
	// Екземпляр конфігурації
			Configuration configuration = new Configuration();
	
	public static boolean stopMarker = false;
	public static boolean Marker = false;
	
	
		
	// Конструктор
	public Main() {
		
		
		// Виведеення логу про загрузку
		logger.log(Level.INFO, "Loading Speech Recognizer...\n");
		
		
		
		//Загрузка даних акустичної моделі
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		
		
		//configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin"); Розпізнавання кожного словаа окремо
		
		
		// Заргузка файла граматики 
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);
		
		// Старт запису
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		
		
		//Перевірка наявніть ресурсів
		startResourcesThread();
		//Початок розпізнвання мовлення
		startSpeechRecognition();
	}
	
	
	//Функція розпізнання мовленя
	public synchronized void startSpeechRecognition() {
		
		
		
		//Перевірка запуску і виведення логу
		if (speechRecognizerThreadRunning)
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else
			//Надсилання до ExecutorService
			eventsExecutorService.submit(() -> {
				
				//Встановлення стану
				speechRecognizerThreadRunning = true;
				IgnoreSpeech.ignoreSpeechRecognitionResults = false;
				
				//Старт розпізнавання
				recognizer.startRecognition(true);
				
				//Виведення інформації в лог			
				logger.log(Level.INFO, "You can start to speak...\n");
				
				try {
					while (speechRecognizerThreadRunning) {
						if(stopMarker) {
							Marker = true;
							break;
							}
						
						//Цей метод повернеться, коли досягнеться кінець слова
						SpeechResult speechResult = recognizer.getResult();
						
						//Перевірка якщо ми ігноруємо результат розпізнання
						if (!IgnoreSpeech.ignoreSpeechRecognitionResults) {
							
							//Перевірка результату
							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {
								
								//отримання гіпотези
								speechRecognitionResult = speechResult.getHypothesis();
								
								//виведення слова яке розпізналося
								System.out.println("You said: [" + speechRecognitionResult + "]\n");
								
								//Frame Write = new Frame();
								Frame.printspeachResult(speechRecognitionResult);
								
								
								
								//Виклик відповіідного методу
								makeDecision(speechRecognitionResult, speechResult.getWords());
								
							}
						} else
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
						
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					speechRecognizerThreadRunning = false;
				}
				
				logger.log(Level.INFO, "SpeechThread has exited...");
				
			});
	
	}
	
	
	
	//-----------------------------------------------------------------------------------------------
	
	//Початок гілки, який перевіряє наявність ресурсів, необхідних для бібліотеки SpeechRecognition
	public void startResourcesThread() {
		
		//Виведення статусу
		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			//Надсдилання до ExecutorService
			eventsExecutorService.submit(() -> {
				try {
					
					//установка статусу - заблоковано
					resourcesThreadRunning = true;
					
					// Перевірка мцкрофона
					while (true) {
						
						//Якщо мікрофон працює
						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");
						
						// затримка
						Thread.sleep(350);
					}
					
				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}
	
	//риймає рішення на основі даного результату
	public void makeDecision(String speech , List<WordResult> speechWords) {
		
		System.out.println(speech);
		
	}
	
	
	// Основний метод
	public static void main(String[] args) {
		FrameLanguage FrameLanguage = new FrameLanguage();
		FrameLanguage.frameLang();
	   
		//new Main();
	}
}
