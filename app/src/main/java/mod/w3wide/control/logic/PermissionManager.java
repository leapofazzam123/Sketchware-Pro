package mod.w3wide.control.logic;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;
import java.util.Map.Entry;

import a.a.a.jC;

public class PermissionManager {

    private final String javaName;
    private final String sc_id;
    public boolean hasPermission = false;

    public PermissionManager(String id, String javaName) {
        this.javaName = javaName;
        sc_id = id;
    }

    private ArrayList<String> addedPermissions() {
        ArrayList<String> permList = new ArrayList<>();
        for (Entry<String, ArrayList<BlockBean>> blocks : jC.a(sc_id).b(javaName).entrySet()) {
            for (BlockBean block : blocks.getValue()) {
                if (block.opCode.equals("addPermission") && !block.parameters.get(0).trim().isEmpty()) {
                    permList.add(block.parameters.get(0));
                }
            }
        }
        return permList;
    }

    private String formatPermission(boolean isAppCompat, String permission) {
        if (isAppCompat) {
            return String.format("ContextCompat.checkSelfPermission(this, %s) == PackageManager.PERMISSION_DENIED", permission);
        } else {
            return String.format("checkSelfPermission(%s) == PackageManager.PERMISSION_DENIED", permission);
        }
    }

    private void addReqPermission(boolean isAppCompat, ArrayList<String> checkPerm, ArrayList<String> reqPerm) {
        for (String permission : addedPermissions()) {
            checkPerm.add(formatPermission(isAppCompat, permission));
            reqPerm.add(permission);
        }
    }

    private void removePermission(boolean isAppCompat, ArrayList<String> checkPerm, ArrayList<String> reqPerm) {
        for (Entry<String, ArrayList<BlockBean>> blocks : jC.a(sc_id).b(javaName).entrySet()) {
            for (BlockBean block : blocks.getValue()) {
                if (block.opCode.equals("removePermission") && !block.parameters.get(0).trim().isEmpty()) {
                    checkPerm.remove(formatPermission(isAppCompat, block.parameters.get(0)));
                    reqPerm.remove(block.parameters.get(0));
                }
            }
        }
    }

    public boolean hasNewPermission() {
        return addedPermissions().size() != 0;
    }

    public String writePermission(boolean isAppCompat, int var1) {
        ArrayList<String> checkPerm = new ArrayList<>();
        ArrayList<String> addPerm = new ArrayList<>();
        StringBuilder permissionCode = new StringBuilder();

        addReqPermission(isAppCompat, checkPerm, addPerm);

        if (isAppCompat) {
            if ((var1 & 1) == 1) {
                checkPerm.add("ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.CALL_PHONE");
            }
            if ((var1 & 16) == 16) {
                checkPerm.add("ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.CAMERA");
            }
            if ((var1 & 32) == 32) {
                checkPerm.add("ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.READ_EXTERNAL_STORAGE");
            }
            if ((var1 & 64) == 64) {
                checkPerm.add("ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            }
            if ((var1 & 128) == 128) {
                checkPerm.add("ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.RECORD_AUDIO");
            }
            if ((var1 & 1024) == 1024) {
                checkPerm.add("ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.ACCESS_FINE_LOCATION");
            }
            removePermission(true, checkPerm, addPerm);

            if (checkPerm.size() != 0 && addPerm.size() != 0) {
                permissionCode.append("if (");

                for (int i = 0; i < checkPerm.size(); i++) {
                    if (i != 0) permissionCode.append("\r\n|| ");
                    permissionCode.append(checkPerm.get(i));
                }

                permissionCode.append(") {\r\nActivityCompat.requestPermissions(this, new String[] {");

                for (int i = 0; i < addPerm.size(); i++) {
                    if (i != 0) permissionCode.append(", ");
                    permissionCode.append(addPerm.get(i));
                }

                permissionCode.append("}, 1000);\r\n} else {\r\ninitializeLogic();\r\n}\r\n");
            }

        } else {
            if ((var1 & 1) == 1) {
                checkPerm.add("checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.CALL_PHONE");
            }
            if ((var1 & 16) == 16) {
                checkPerm.add("checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.CAMERA");
            }
            if ((var1 & 32) == 32) {
                checkPerm.add("checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.READ_EXTERNAL_STORAGE");
            }
            if ((var1 & 64) == 64) {
                checkPerm.add("checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            }
            if ((var1 & 128) == 128) {
                checkPerm.add("checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.RECORD_AUDIO");
            }
            if ((var1 & 1024) == 1024) {
                checkPerm.add("checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED");
                addPerm.add("Manifest.permission.ACCESS_FINE_LOCATION");
            }
            removePermission(false, checkPerm, addPerm);

            if (checkPerm.size() != 0 && addPerm.size() != 0) {
                permissionCode.append("if (Build.VERSION.SDK_INT >= 23) {\r\nif (");

                for (int i = 0; i < checkPerm.size(); i++) {
                    if (i != 0) permissionCode.append("\r\n||");
                    permissionCode.append(checkPerm.get(i));
                }

                permissionCode.append(") {\r\nrequestPermissions(new String[] {");

                for (int i = 0; i < addPerm.size(); i++) {
                    if (i != 0) permissionCode.append(", ");
                    permissionCode.append(addPerm.get(i));
                }

                permissionCode.append("}, 1000);\r\n} else {\r\ninitializeLogic();\r\n}\r\n} else {\r\ninitializeLogic();\r\n}\r\n");
            }
        }

        hasPermission = checkPerm.size() != 0 || addPerm.size() != 0;

        if (permissionCode.toString().trim().isEmpty()) {
            return "initializeLogic();\r\n";
        } else {
            return permissionCode.toString();
        }
    }
}
