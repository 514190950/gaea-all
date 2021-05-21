package com.gxz.gaea.src.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author gongxuanzhang
 */
public class GaeaSrcBanner implements Banner {

    private static final String[] BANNER = {"", "   _____                                _____ ",
            "  / ____|                              / ____|   ", " | |  __    __ _    ___    __ _       | (___    _ " +
			"__    ___ ",
            " | | |_ |  / _` |  / _ \\  / _` |       \\___ \\  | '__|  / __|", " | |__| | | (_| | |  __/ | (_| |     " +
			"  ____) | | |    | (__ ",
            "  \\_____|  \\__,_|  \\___|  \\__,_|      |_____/  |_|     \\___|"};

    private static final String SPRING_BOOT = " :: Spring Boot :: ";

    private static final int STRAP_LINE_SIZE = 42;

    private final String module;

    public GaeaSrcBanner(String module) {

        this.module = " :: "+module+"模块 :: ";

    }

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        for (String line : BANNER) {
            printStream.println(line);
        }
        String version = SpringBootVersion.getVersion();
        version = (version != null) ? " (v" + version + ")" : "";
        StringBuilder padding = new StringBuilder();
        while (padding.length() < STRAP_LINE_SIZE - (version.length() + SPRING_BOOT.length())) {
            padding.append(" ");
        }

        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT, AnsiColor.DEFAULT, padding.toString(),
                AnsiStyle.FAINT, version));

		printStream.println(AnsiOutput.toString(AnsiColor.GREEN, module, AnsiColor.DEFAULT, padding.toString(),
				AnsiStyle.FAINT));
        printStream.println();
    }


}
