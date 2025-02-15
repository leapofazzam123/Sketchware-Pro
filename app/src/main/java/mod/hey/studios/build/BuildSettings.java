package mod.hey.studios.build;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.ProjectSettings;

public class BuildSettings extends ProjectSettings {

    public static final String SETTING_ANDROID_JAR_PATH = "android_jar";
    public static final String SETTING_CLASSPATH = "classpath";
    public static final String SETTING_DEXER = "dexer";
    public static final String SETTING_INCREMENTAL_BUILD_ACTIVE = "incremental_build";
    public static final String SETTING_JAVA_VERSION = "java_ver";
    public static final String SETTING_NO_HTTP_LEGACY = "no_http_legacy";
    public static final String SETTING_NO_WARNINGS = "no_warn";
    public static final String SETTING_OUTPUT_FORMAT = "output_format";
    public static final String SETTING_RESOURCE_PROCESSOR = "resource_processor";

    public static final String SETTING_DEXER_D8 = "D8";
    public static final String SETTING_DEXER_DX = "Dx";
    public static final String SETTING_JAVA_VERSION_1_7 = "1.7";
    public static final String SETTING_JAVA_VERSION_1_8 = "1.8";
    public static final String SETTING_RESOURCE_PROCESSOR_AAPT = "AAPT";
    public static final String SETTING_RESOURCE_PROCESSOR_AAPT2 = "AAPT2";
    public static final String SETTING_OUTPUT_FORMAT_AAB = "AAB";
    public static final String SETTING_OUTPUT_FORMAT_APK = "APK";

    public static final String SETTING_GENERIC_VALUE_TRUE = "true";
    public static final String SETTING_GENERIC_VALUE_FALSE = "false";

    public BuildSettings(String sc_id) {
        super(sc_id);
    }

    @Override
    public String getPath() {
        return FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/build_config";
    }
}
