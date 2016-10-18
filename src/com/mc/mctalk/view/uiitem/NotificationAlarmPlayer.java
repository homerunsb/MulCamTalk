package com.mc.mctalk.view.uiitem;

import javax.sound.sampled.*; // sound를 이용하기 위해 연결.
import java.io.File;

public class NotificationAlarmPlayer {
	private static int EXTERNAL_BUFFER_SIZE = 128000;

	public void playAudioFile() throws Exception {// 메소드
		String audioFile = "sounds/notify.wav"; // 연결할 wav파일 위치

		File soundFile = new File(audioFile);
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();

			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					line.write(abData, 0, nBytesRead);
				}
			}
			line.drain();
			line.close();
			audioInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
