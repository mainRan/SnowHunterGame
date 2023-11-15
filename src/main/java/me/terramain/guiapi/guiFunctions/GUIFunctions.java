package me.terramain.guiapi.guiFunctions;

import me.terramain.guiapi.GUI;

public interface GUIFunctions {
    public void onCreate(GUI gui);

    public void onClick(GUIClickData clickData);
    public void onOpen(GUIOpenData openData);
    public void onClose(GUICloseData closeData);
}
