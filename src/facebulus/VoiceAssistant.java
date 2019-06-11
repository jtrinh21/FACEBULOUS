/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebulus;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 *
 * @author dttri
 */
public class VoiceAssistant {
    private Voice voice;
    private final String VOICENAME = "kevin16";
    
    public VoiceAssistant()
    {
        
    }
    
    public void speakUp(String text)
    {
        VoiceManager vm = VoiceManager.getInstance();
        voice = vm.getVoice(VOICENAME);
        voice.allocate();
        voice.speak("Welcome to Facebulous" + text);
    }
}
