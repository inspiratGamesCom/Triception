package com.libgdx.triception.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.libgdx.triception.Triception;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {

	private static boolean rebuildAtlas = true;
	private static boolean drawDebugOutline = true;

	public static void main (String[] arg) {

		 /*if (rebuildAtlas) {
            Settings settings = new Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            settings.duplicatePadding = false;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "images", "sprites2",
                    "canyonbunny");

        }*/

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Application app = new LwjglApplication(new Triception(), config);
	}
}
